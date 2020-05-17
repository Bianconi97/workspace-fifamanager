package com.uefa.fifamanager.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.uefa.fifamanager.model.CompositeKeyTeamSeason;
import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.Nation;
import com.uefa.fifamanager.model.Player;
import com.uefa.fifamanager.model.PlayerSeasonTeam;
import com.uefa.fifamanager.model.Season;
import com.uefa.fifamanager.model.Team;
import com.uefa.fifamanager.model.TeamSeasonLeague;
import com.uefa.fifamanager.payload.LeagueAppoggio;
import com.uefa.fifamanager.payload.NationAppoggio;
import com.uefa.fifamanager.payload.NationLeagueTeamPlayer;
import com.uefa.fifamanager.payload.PlayerAppoggio;
import com.uefa.fifamanager.payload.TeamAppoggio;
import com.uefa.fifamanager.repository.LeagueRepository;
import com.uefa.fifamanager.repository.PlayerSeasonTeamRepository;
import com.uefa.fifamanager.repository.SeasonRepository;
import com.uefa.fifamanager.repository.TeamSeasonLeagueRepository;
import com.uefa.fifamanager.controller.TeamSeasonLeagueController;

@RestController
public class SeasonController {

	@Autowired
	SeasonRepository seasonRepository;
	@Autowired
	TeamSeasonLeagueRepository teamSeasonLeagueRepository;
	@Autowired
	LeagueRepository leagueRepository;
	@Autowired
	PlayerSeasonTeamRepository playerSeasonTeamRepository;

	
	@GetMapping("/public/allseason")
	public List <Season> allSeason(){
		return seasonRepository.findAll();
	}
	
	@GetMapping("/public/season_id/{id}")
	public Optional<Season> player(@PathVariable Long id) {
		return seasonRepository.findById(id);
	}
	
	@GetMapping("/public/tsl_seasonId/{id}")
	public List<NationAppoggio> tslSeasonId(@PathVariable Long id){
		
		
		List<NationAppoggio> nationAppoggioList = new ArrayList<NationAppoggio>();
		Set<LeagueAppoggio> leagueAppoggioList = new HashSet<LeagueAppoggio>();
		Set<TeamAppoggio> teamAppoggioList = new HashSet<TeamAppoggio>();
		List<CompositeKeyTeamSeason> composite = new ArrayList<CompositeKeyTeamSeason>();
		List<Nation> nations= new ArrayList<Nation>();
		List<Team> teams= new ArrayList<Team>();
		Integer flagL=0;
		Integer flagN=0;
		
		List<TeamSeasonLeague> tsl = teamSeasonLeagueRepository.findAll();
		
		
		for(TeamSeasonLeague t : tsl)
			composite.add(t.getTeamseason());
		
		
		for(CompositeKeyTeamSeason c : composite) {
			if(c.getSeason().getId().equals(id))
				//leagues.add(teamSeasonLeagueRepository.findByTeamseason(c).getLeague());
				nations.add(teamSeasonLeagueRepository.findByTeamseason(c).getLeague().getNation());
		}
				
		
		Set<String> nationCode = new HashSet<>();
		List<Nation> uniqueNations = nations.stream()
		            .filter(e -> nationCode.add(e.getCode()))
		            .collect(Collectors.toList());
		
		for(Nation nation : uniqueNations) {
			Set<LeagueAppoggio> leagueAppoggioListmp = new HashSet<LeagueAppoggio>();
			for(League l : nation.getLeagues()) {
				Set<TeamAppoggio> teamAppoggioListmp = new HashSet<TeamAppoggio>();
				for(TeamSeasonLeague ts : teamSeasonLeagueRepository.findByLeagueId(l.getId())) {
					if(ts.getLeague().getId().equals(l.getId()) && ts.getTeamseason().getSeason().getId().equals(id)) {
						flagL=1;
						teams.add(ts.getTeamseason().getTeam());
						
						for(Team t : teams) {
							List<PlayerSeasonTeam> pst = new ArrayList<>();
							pst.addAll(playerSeasonTeamRepository.findByTeamId(t.getId()));
							List <PlayerAppoggio> players= new ArrayList<PlayerAppoggio>();	
							for(PlayerSeasonTeam ps : pst) {
								if(ps.getSeason().getId().equals(id))
									players.add(new PlayerAppoggio(ps.getPlayer().getSurname().toString().concat(" ").concat(ps.getPlayer().getName().toString())));
							}
							teamAppoggioListmp.add(new TeamAppoggio(t.getName(),players));
							teamAppoggioList=teamAppoggioListmp;
						}
					}
					teams.clear();
				}
				if(flagL==1) {
					leagueAppoggioListmp.add(new LeagueAppoggio(l.getName(),teamAppoggioList));
					leagueAppoggioList=leagueAppoggioListmp;
					flagN=1;
				}
				flagL=0;
			}
			if(flagN==1)
		nationAppoggioList.add(new NationAppoggio(nation.getCode(),leagueAppoggioList));
		}
		flagN=0;
		return nationAppoggioList;	
	}
	

	
	@GetMapping("/public/nlsp_season_id/{id}")
	public void nltp(@PathVariable Long id) {
	
}
	
	@GetMapping("/public/season_date/{date}")
	public List<Season> seasonDate(@PathVariable 
	  @DateTimeFormat(pattern = "yyyy-MM-dd") Date date)
	{
		
		List<Season> seasons = this.allSeason();
		List<Season> returnValues = new ArrayList<Season>();
		for (Season s : seasons)
		{
			if (date.after(s.getStartDate()) && date.before(s.getEndDate()) )
			{
				returnValues.add(s);
			}
		}
		return returnValues;
	}
	
	@PostMapping("/protected/season/insert")
	public void insertSeason(@RequestBody Season s){

		Season season=new Season();
		
		season.setEndDate(s.getEndDate());
		season.setStartDate(s.getStartDate());

		seasonRepository.saveAndFlush(season);
	}
	
	@PutMapping("/protected/season/put")
	public void putSeason(@RequestBody Season s){

		Optional <Season> season=seasonRepository.findById(s.getId());
		if(season.isPresent()) {
			season.get().setEndDate(s.getEndDate());
			season.get().setStartDate(s.getStartDate());
			seasonRepository.saveAndFlush(season.get());
		}
		
	}
	
	@DeleteMapping("/protected/season/more-delete/{ids}")
	public void deleteSeason(@PathVariable List <Long> ids) {
		
		List <Season> seasons=seasonRepository.findByIdIn(ids);
		for(Season s : seasons ) {
			seasonRepository.deleteById(s.getId());
		}
	}
	
}
