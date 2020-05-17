package com.uefa.fifamanager.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uefa.fifamanager.model.PlayerSeasonTeam;
import com.uefa.fifamanager.model.Season;
import com.uefa.fifamanager.model.Team;
import com.uefa.fifamanager.model.TeamSeasonLeague;
import com.uefa.fifamanager.repository.LeagueRepository;
import com.uefa.fifamanager.repository.PlayerRepository;
import com.uefa.fifamanager.repository.PlayerSeasonTeamRepository;
import com.uefa.fifamanager.repository.SeasonRepository;
import com.uefa.fifamanager.repository.TeamRepository;
import com.uefa.fifamanager.repository.TeamSeasonLeagueRepository;
import com.uefa.fifamanager.model.CompositeKeyTeamSeason;
import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.Player;

@RestController
public class TeamSeasonLeagueController {

	@Autowired
	TeamSeasonLeagueRepository teamSeasonLeagueRepository;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	LeagueRepository leagueRepository;
	@Autowired
	SeasonRepository seasonRepository;


	
	@GetMapping("/public/allteamSeasonLeague")
	public List<TeamSeasonLeague> allTeamSeasonLeague(){
		return teamSeasonLeagueRepository.findAll();
	}
	
	@GetMapping("/public/tsl_leagueId/{id}")
	public List<TeamSeasonLeague> tslLeagueId(@PathVariable Long id){
		return teamSeasonLeagueRepository.findByLeagueId(id);
	}

	@GetMapping ("/public/tsl_idTeam_idSeason/{id_team}/{id_season}")
	public TeamSeasonLeague tslTS(@PathVariable Long id_team,@PathVariable Long id_season ){
		List<TeamSeasonLeague> tsl = teamSeasonLeagueRepository.findAll();		
		List<CompositeKeyTeamSeason>composite= new ArrayList<CompositeKeyTeamSeason>();
		
		for(TeamSeasonLeague t : tsl)
			composite.add(t.getTeamseason());
		
		for(CompositeKeyTeamSeason c : composite) {
			if(c.getSeason().getId().equals(id_season) && c.getTeam().getId().equals(id_team))
				return teamSeasonLeagueRepository.findByTeamseason(c);
		}
		return null;
	}
	
	@PostMapping("/protected/league_season_team/insert")
	public void insertTeamSeasonLeague(@RequestBody TeamSeasonLeague t)
	{
		TeamSeasonLeague tsl = new TeamSeasonLeague(); 
		List<Team> teams = teamRepository.findAll();
		CompositeKeyTeamSeason key = new CompositeKeyTeamSeason();
		
		List<League> leagues = leagueRepository.findAll();
		List<Season> seasons = seasonRepository.findAll();
		boolean condition1 = seasons.stream().filter(k->k.getId()==t.getTeamseason().getSeason().getId()).findAny().isPresent();
		boolean condition2 = leagues.stream().filter(k->k.getId()==t.getLeague().getId()).findAny().isPresent();
		boolean condition3 = teams.stream().filter(k->k.getId()==t.getTeamseason().getTeam().getId()).findAny().isPresent();
		if (condition1 && condition2 && condition3)
		{
			tsl.setLeague(t.getLeague());
			key.setTeam(t.getTeamseason().getTeam());
			key.setSeason(t.getTeamseason().getSeason());
			tsl.setTeamseason(key);

		}
		teamSeasonLeagueRepository.saveAndFlush(tsl);
		
	}
	

	@PutMapping("/protected/team_season_league/put")
	public void putPlayerSeasonTeam(@RequestBody TeamSeasonLeague t)
	{
		Optional<TeamSeasonLeague> pst = teamSeasonLeagueRepository.findById(t.getTeamseason());
		List<Team> teams = teamRepository.findAll();		
		List<League> leagues = leagueRepository.findAll();
		List<Season> seasons = seasonRepository.findAll();
		boolean condition1 = seasons.stream().filter(k->k.getId()==t.getTeamseason().getSeason().getId()).findAny().isPresent();
		boolean condition2 = leagues.stream().filter(k->k.getId()==t.getLeague().getId()).findAny().isPresent();
		boolean condition3 = teams.stream().filter(k->k.getId()==t.getTeamseason().getTeam().getId()).findAny().isPresent();
		if (pst.isPresent() && condition1 && condition2 && condition3)
		{
			pst.get().setTeamseason(t.getTeamseason());
			pst.get().setLeague(t.getLeague());
		}
		teamSeasonLeagueRepository.saveAndFlush(pst.get());
		
	}
	
	@Transactional
	@DeleteMapping("/protected/league_season_team/delete/{id_team}/{id_season}")
	public void deleteLeagueSeasonTeam(@PathVariable Long id_team,@PathVariable Long id_season ) {
		List<TeamSeasonLeague> tsl = teamSeasonLeagueRepository.findAll();		

		
		for(TeamSeasonLeague t : tsl)
			if(t.getTeamseason().getTeam().getId().equals(id_team) && t.getTeamseason().getSeason().getId().equals(id_season))
				teamSeasonLeagueRepository.deleteByTeamseason(t.getTeamseason());
	}
	
}
