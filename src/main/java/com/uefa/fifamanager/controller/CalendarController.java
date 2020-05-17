package com.uefa.fifamanager.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uefa.fifamanager.model.Calendar;
import com.uefa.fifamanager.model.CompositeKeyTeamSeason;
import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.Nation;
import com.uefa.fifamanager.model.Season;
import com.uefa.fifamanager.model.Team;
import com.uefa.fifamanager.model.TeamSeasonLeague;
import com.uefa.fifamanager.payload.DayAppoggio;
import com.uefa.fifamanager.payload.EliminazioneAppoggio;
import com.uefa.fifamanager.payload.GiornataRanking;
import com.uefa.fifamanager.payload.LeagueAppoggioPlayer;
import com.uefa.fifamanager.payload.NationAppoggioCalendar;
import com.uefa.fifamanager.payload.Ranking;
import com.uefa.fifamanager.repository.CalendarRepository;
import com.uefa.fifamanager.repository.LeagueRepository;
import com.uefa.fifamanager.repository.NationRepository;
import com.uefa.fifamanager.repository.SeasonRepository;
import com.uefa.fifamanager.repository.TeamRepository;
import com.uefa.fifamanager.repository.TeamSeasonLeagueRepository;
import com.uefa.fifamanager.service.CalendarService;

@RestController
public class CalendarController {
	
	
	@Autowired
	TeamSeasonLeagueRepository teamSeasonLeagueRepository;
	@Autowired
	LeagueRepository leagueRepository;
	@Autowired
	SeasonRepository seasonRepository;
	@Autowired
	CalendarService calendarService;
	@Autowired
	CalendarRepository calendarRepository;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	NationRepository nationRepository;
	
	
	@GetMapping("/public/matches/{season_id}/{league_id}")
	public List<DayAppoggio> league(@PathVariable Long season_id,@PathVariable Long league_id) {
		List <Calendar> calendars= new ArrayList <Calendar>();
		List <DayAppoggio> days= new ArrayList<DayAppoggio>();
		List<Team> teams= new ArrayList<Team>();
		int index=0;
		
		calendars= calendarRepository.findBySeasonIdAndLeagueIdOrderByGiornata(season_id,league_id);
		
		List<TeamSeasonLeague> tsl = teamSeasonLeagueRepository.findByLeagueId(leagueRepository.findById(league_id).get().getId());
		for(TeamSeasonLeague t :tsl)
			if(t.getTeamseason().getSeason().getId().equals(seasonRepository.findById(season_id).get().getId()))
				teams.add(t.getTeamseason().getTeam());
		
		for(int i=1;i<teams.size()*2-1;i++) {
			List<String> matches= new ArrayList<String>();
			for(int j=0; j<teams.size()/2;j++) {
				matches.add(calendars.get(index).getHomeTeam().getName().concat("-").concat(calendars.get(index).getVisitingTeam().getName()));
				index++;
			}
			days.add(new DayAppoggio(i,matches));
			}	
		return days;
	}
	
	@GetMapping("/public/matches_champions_league/{season_id}")
	public List<DayAppoggio> championsMatches(@PathVariable Long season_id) {
		List <Calendar> calendars= new ArrayList <Calendar>();
		List <DayAppoggio> days= new ArrayList<DayAppoggio>();
		Set<Team> teams= new HashSet<Team>();
		Optional<League> l=leagueRepository.findByName("championsLeague");
		calendars= calendarRepository.findBySeasonIdAndLeagueIdOrderByGiornata(season_id,l.get().getId());
		int index=0;
		
		for(Calendar c: calendars) {
			teams.add(c.getHomeTeam());
		}
		for(int i=1;i<teams.size()*2-1;i++) {
			List<String> matches= new ArrayList<String>();
			for(int j=0; j<teams.size()/2;j++) {
				matches.add(calendars.get(index).getHomeTeam().getName().concat("-").concat(calendars.get(index).getVisitingTeam().getName()));
				index++;
			}
			days.add(new DayAppoggio(i,matches));
			}
		return days;
	}
	
	@GetMapping("/public/day_results/{season_id}/{league_id}/{giornata}")
	public DayAppoggio dayResult(@PathVariable Long season_id,@PathVariable Long league_id,@PathVariable Integer giornata) {
		List <Calendar> calendars= new ArrayList <Calendar>();
		DayAppoggio day= new DayAppoggio();
		List<String> appoggio= new ArrayList<String>();
				
		calendars= calendarRepository.findBySeasonIdAndLeagueIdAndGiornata(season_id,league_id,giornata);
		
		for(int i=0;i<calendars.size();i++) {
				String matches = "";
				matches=matches.concat(calendars.get(i).getHomeTeam().getName().concat("-").concat(calendars.get(i).getVisitingTeam().getName()).concat(" ").concat(calendars.get(i).getGoalHomeTeam().toString()).concat("-").concat(calendars.get(i).getGoalVisitingTeam().toString()));
				appoggio.add(matches);
		}
		day.setDay(giornata);
		day.setMatches(appoggio);
		return day;
	}
	
	class SortbyPoint implements Comparator<Ranking> 
	{ 
		@Override
		public int compare(Ranking o1, Ranking o2) {
			return o1.getPoints()-o2.getPoints();
		} 
	} 
	
	@GetMapping("/public/day_results_ranking/{season_id}/{league_id}")
	public GiornataRanking dayResultRanking(@PathVariable Long season_id,@PathVariable Long league_id) {
		List <TeamSeasonLeague> tmp= new ArrayList<TeamSeasonLeague>();
		List <Team> teams= new ArrayList<Team>();
		List<Ranking> rankings= new ArrayList<Ranking>();

		tmp=teamSeasonLeagueRepository.findByLeagueId(league_id);
		for(TeamSeasonLeague tsl:tmp)
			if(tsl.getTeamseason().getSeason().getId().equals(season_id)) {
				teams.add(tsl.getTeamseason().getTeam());
				rankings.add(new Ranking(tsl.getTeamseason().getTeam().getName(), 0, 0, 0, 0, 0, 0));
			}
		return calendarService.ordinaClassifica(rankings,season_id,league_id);
	}
	
	@GetMapping("/public/girone_results_ranking/{season_id}/{league_id}/{girone}")
	public GiornataRanking dayResultRanking(@PathVariable Long season_id,@PathVariable Long league_id,@PathVariable Integer girone) {
		List <Calendar> tmp= new ArrayList<Calendar>();
		List<Ranking> rankings= new ArrayList<Ranking>();
		Set<Team>teams= new HashSet<Team>();

		tmp=calendarRepository.findByLeagueIdAndSeasonIdAndGirone(league_id,season_id,girone.toString());
		for(Calendar c :tmp)
			teams.add(c.getHomeTeam());
		for(Team t:teams)
				rankings.add(new Ranking(t.getName(), 0, 0, 0, 0, 0, 0));
		return calendarService.ordinaClassifica(rankings,season_id,league_id);
	}
	
	@GetMapping("/public/elimination_phase/{season_id}")
	public List<EliminazioneAppoggio> dayResultRanking(@PathVariable Long season_id) {
		List<Calendar> tmp= new ArrayList<Calendar>();
		List<EliminazioneAppoggio> ans=new ArrayList<EliminazioneAppoggio>();
		
		tmp=calendarRepository.findBySeasonIdAndGirone(season_id, "ottavi di finale");
		for(Calendar c:tmp)
			if(c.getGoalHomeTeam()!=null)
			ans.add(new EliminazioneAppoggio("ottavi di finale", c.getGiornata(),c.getHomeTeam().getName().concat(" - ").concat(c.getVisitingTeam().getName()).concat(" ").concat(c.getGoalHomeTeam().toString()).concat(" - ").concat(c.getGoalVisitingTeam().toString()),c.getHomeTeam().getName(),c.getVisitingTeam().getName(),c.getGoalHomeTeam(),c.getGoalVisitingTeam()));
		tmp=calendarRepository.findBySeasonIdAndGirone(season_id, "quarti di finale");
		for(Calendar c:tmp)
			if(c.getGoalHomeTeam()!=null)
			ans.add(new EliminazioneAppoggio("quarti di finale", c.getGiornata(),c.getHomeTeam().getName().concat(" - ").concat(c.getVisitingTeam().getName()).concat(" ").concat(c.getGoalHomeTeam().toString()).concat(" - ").concat(c.getGoalVisitingTeam().toString()),c.getHomeTeam().getName(),c.getVisitingTeam().getName(),c.getGoalHomeTeam(),c.getGoalVisitingTeam()));
		tmp=calendarRepository.findBySeasonIdAndGirone(season_id, "semifinale");
		for(Calendar c:tmp)
			if(c.getGoalHomeTeam()!=null)
			ans.add(new EliminazioneAppoggio("semifinale", c.getGiornata(),c.getHomeTeam().getName().concat(" - ").concat(c.getVisitingTeam().getName()).concat(" ").concat(c.getGoalHomeTeam().toString()).concat(" - ").concat(c.getGoalVisitingTeam().toString()),c.getHomeTeam().getName(),c.getVisitingTeam().getName(),c.getGoalHomeTeam(),c.getGoalVisitingTeam()));
		tmp=calendarRepository.findBySeasonIdAndGirone(season_id, "finale");
		for(Calendar c:tmp)
			if(c.getGoalHomeTeam()!=null)
			ans.add(new EliminazioneAppoggio("finale", c.getGiornata(),c.getHomeTeam().getName().concat(" - ").concat(c.getVisitingTeam().getName()).concat(" ").concat(c.getGoalHomeTeam().toString()).concat(" - ").concat(c.getGoalVisitingTeam().toString()),c.getHomeTeam().getName(),c.getVisitingTeam().getName(),c.getGoalHomeTeam(),c.getGoalVisitingTeam()));

		return ans;
	}
	
	@GetMapping("/public/first_team/{season_id}")
	public List<NationAppoggioCalendar> firstTeam(@PathVariable Long season_id) {
		List<NationAppoggioCalendar> result= new ArrayList<NationAppoggioCalendar>();
		List<Nation> nations=nationRepository.findAll();
		for(Nation n: nations) {
			Set<League> league= n.getLeagues();
			List<LeagueAppoggioPlayer>leagueA= new ArrayList<LeagueAppoggioPlayer>();
			for(League l: league) {
				List<String> team= new ArrayList<String>();
				GiornataRanking g=dayResultRanking(season_id,l.getId());
				if(g.getGiornata()==-1)
					leagueA.add(new LeagueAppoggioPlayer(l.getName(),null));
				else {
					team.add(g.getRankings().get(0).getTeamName());
					leagueA.add(new LeagueAppoggioPlayer(l.getName(),team));
				}
			}
			result.add(new NationAppoggioCalendar(n.getCode(), leagueA));
		}
		
		return result;
	}
	
	@PostMapping("/publisher/calendar/{season_id}/{league_id}")
	public String insertNation(@PathVariable Long season_id,@PathVariable Long league_id) {
		Optional<Season> s = seasonRepository.findById(season_id);
		Optional<League> l = leagueRepository.findById(league_id);
		List <Team> teams= new ArrayList<Team>();
		List<TeamSeasonLeague> tsl= new ArrayList<TeamSeasonLeague>();
		
		
		
		List <Calendar> c = calendarRepository.findAllBySeasonIdAndLeagueId(s.get().getId(),l.get().getId());
		if(!c.isEmpty())
			return "Calendario già presente";

		
		tsl=teamSeasonLeagueRepository.findByLeagueId(l.get().getId());
		for(TeamSeasonLeague t :tsl)
			if(t.getTeamseason().getSeason().getId().equals(s.get().getId()))
				teams.add(t.getTeamseason().getTeam());
		
		if(teams.size()%2!=0)
			return "Numero squadre dispari";
		
		//teams.size();
		calendarService.generaPermutazioni(teams,s.get(),l.get());
		return null;
	}
	
	@PostMapping("/protected/champion_league_calendar/{season_id}")
	public String championsLeague(@PathVariable Long season_id) {
		Optional<Season> s = seasonRepository.findById(season_id);
		List<League> leagues = leagueRepository.findAll();
		List <Team> teams= new ArrayList<Team>();
		

		
		for(League l: leagues) {
			GiornataRanking g=dayResultRanking(season_id,l.getId());
			if(g.getGiornata()!=-1 && l.getLegaMaggiore()) {
				teams.add(teamRepository.findByName(g.getRankings().get(0).getTeamName()).get());
				teams.add(teamRepository.findByName(g.getRankings().get(1).getTeamName()).get());
			}
		}

		Collections.shuffle(teams);
		Optional<League> l=leagueRepository.findByName("championsLeague");
		
		int i=1;
		Integer index=0;
		List<Team> teamG= new ArrayList<Team>();
		for(Team t:teams) {		
			teamG.add(t);
			if(i==4) {
				calendarService.generaPermutazioniGironi(teamG,s.get(),l.get(),index.toString());
				teamG.clear();
				index++;
				i=0;
			}
			i++;
		}
		return null;
	}
	
	@PostMapping("/protected/champion_league_elimination/{season_id}")
	public void championsLeagueElimination(@PathVariable Long season_id) {
		List<Ranking> rankings= new ArrayList<Ranking>();
		List<Calendar> calendars= calendarRepository.findAll();
		Set <Team> teams= new HashSet<Team>();
		List<Team> qualified= new ArrayList<Team>();
		List <Calendar> tmp= new ArrayList<Calendar>();
		Integer nGironi=-1;
		Long league_id=(long) -1;
		
		for(Calendar c: calendars) {
			Integer n = -1;
			if(c.getGirone()!= null) {
				n=Integer.valueOf(c.getGirone());
				if(n>nGironi) {
					nGironi=Integer.valueOf(c.getGirone());
					league_id=c.getLeague().getId();
				}
			}
		}
		
		for(Integer i=0;i<=nGironi;i++) {
			tmp = calendarRepository.findByLeagueIdAndSeasonIdAndGirone(league_id,season_id,i.toString());
		for(Calendar c :tmp)
			teams.add(c.getHomeTeam());
		for(Team t:teams)
			rankings.add(new Ranking(t.getName(), 0, 0, 0, 0, 0, 0));
			GiornataRanking g=calendarService.ordinaClassifica(rankings,season_id,league_id);
			if(g.getGiornata()!=-1) {
				qualified.add(teamRepository.findByName(g.getRankings().get(0).getTeamName()).get());
				qualified.add(teamRepository.findByName(g.getRankings().get(1).getTeamName()).get());
				teams.clear();
				rankings.clear();
			}
		}
		Collections.shuffle(qualified);
		int giornata=1;
		
		if(qualified.size()==16) {
			for(int i=0;i<qualified.size();i=i+2) {
				Calendar c= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i),qualified.get(i+1),giornata,"ottavi di finale");
				Calendar c1= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i+1),qualified.get(i),giornata+1,"ottavi di finale");
				calendarRepository.saveAndFlush(c);
				calendarRepository.saveAndFlush(c1);
        	}
		}
		
		if(qualified.size()==8) {
			for(int i=0;i<qualified.size();i=i+2) {
				Calendar c= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i),qualified.get(i+1),giornata,"quarti di finale");
				Calendar c1= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i+1),qualified.get(i),giornata+1,"quarti di finale");
				calendarRepository.saveAndFlush(c);
				calendarRepository.saveAndFlush(c1);
        	}
		}
		
		if(qualified.size()==4) {
			for(int i=0;i<qualified.size();i=i+2) {
				Calendar c= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i),qualified.get(i+1),giornata,"semifinale");
				Calendar c1= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i+1),qualified.get(i),giornata+1,"semifinale");
				calendarRepository.saveAndFlush(c);
				calendarRepository.saveAndFlush(c1);
        	}
		}
		
		if(qualified.size()==2) {
			for(int i=0;i<qualified.size();i=i+2) {
				Calendar c= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i),qualified.get(i+1),giornata,"finale");
				Calendar c1= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i+1),qualified.get(i),giornata+1,"finale");
				calendarRepository.saveAndFlush(c);
				calendarRepository.saveAndFlush(c1);
        	}
		}
		return;
	}
	
	@PostMapping("/protected/champion_league_next_phase/{season_id}/{girone}")
	public void championsLeagueQuarterFinal(@PathVariable Long season_id,@PathVariable String girone) {
		List<Calendar> calendars= new ArrayList<Calendar>();
		List<Team> qualified= new ArrayList<Team>();
		Team tmp= new Team();
		long league_id=-1;
		
		if(girone.equals("quarti_di_finale"))
			calendars=calendarRepository.findBySeasonIdAndGirone(season_id, "ottavi di finale");
		if(girone.equals("semifinale"))
			calendars=calendarRepository.findBySeasonIdAndGirone(season_id, "quarti di finale");
		if(girone.equals("finale"))
			calendars=calendarRepository.findBySeasonIdAndGirone(season_id, "semifinale");
		
		league_id=calendars.get(0).getLeague().getId();
		for(int i=0;i<calendars.size();i=i+2) {
			tmp=calendarService.nextPhase(calendars,i);
			qualified.add(tmp);
		}
		
		int giornata=1;
		if(qualified.size()==8) {
			for(int i=0;i<qualified.size();i=i+2) {
				Calendar c= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i),qualified.get(i+1),giornata,"quarti di finale");
				Calendar c1= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i+1),qualified.get(i),giornata+1,"quarti di finale");
				calendarRepository.saveAndFlush(c);
				calendarRepository.saveAndFlush(c1);
        	}
		}
		
		if(qualified.size()==4) {
			for(int i=0;i<qualified.size();i=i+2) {
				Calendar c= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i),qualified.get(i+1),giornata,"semifinale");
				Calendar c1= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i+1),qualified.get(i),giornata+1,"semifinale");
				calendarRepository.saveAndFlush(c);
				calendarRepository.saveAndFlush(c1);
        	}
		}
		
		if(qualified.size()==2) {
			for(int i=0;i<qualified.size();i=i+2) {
				Calendar c= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i),qualified.get(i+1),giornata,"finale");
				Calendar c1= new Calendar(seasonRepository.findById(season_id).get(),leagueRepository.findById(league_id).get(),qualified.get(i+1),qualified.get(i),giornata+1,"finale");
				calendarRepository.saveAndFlush(c);
				calendarRepository.saveAndFlush(c1);
        	}
		}
		
	}
	
	@PutMapping("protected/risultato_giornata/{id}/{goalHome}/{goalVisiting}")
	public String risultato(@PathVariable Long id,@PathVariable Integer goalHome,@PathVariable Integer goalVisiting){
		Optional<Calendar> calendar=calendarRepository.findById(id);
		
		if(calendar==null)
			return "giornata non trovata";
		if(goalHome<0 || goalVisiting<0)
			return "i goal non possono essere negativi";
		Calendar c=calendar.get();
		c.setGoalHomeTeam(goalHome);
		c.setGoalVisitingTeam(goalVisiting);
		calendarRepository.saveAndFlush(c);
		return "risultato inserito correttamente";
	}
	
	
	
	
}

