package com.uefa.fifamanager.controller;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uefa.fifamanager.model.CompositeKeyTeamSeason;
import com.uefa.fifamanager.model.Player;
import com.uefa.fifamanager.model.PlayerSeasonTeam;
import com.uefa.fifamanager.model.TeamSeasonLeague;
import com.uefa.fifamanager.payload.LeagueAppoggio;
import com.uefa.fifamanager.payload.LeagueAppoggioPlayer;
import com.uefa.fifamanager.payload.PaginRequest;
import com.uefa.fifamanager.payload.SeasonAppoggio;
import com.uefa.fifamanager.repository.PlayerRepository;
import com.uefa.fifamanager.repository.PlayerSeasonTeamRepository;
import com.uefa.fifamanager.repository.TeamSeasonLeagueRepository;
import com.uefa.fifamanager.service.PlayerService;



@RestController
public class PlayerController {
	@Value ("${player.page.sort}")
	private String defaultSortValue;
	
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	PlayerService playerService;
	@Autowired
	PlayerSeasonTeamRepository playerSeasonTeamRepository;
	@Autowired
	TeamSeasonLeagueRepository teamSeasonLeagueRepository;
	
	
	Sort sort = Sort.by("surname").ascending();
	@GetMapping("/allplayers")
	public List <Player> allPlayer(){
		return playerRepository.findAll(sort);
	}
	

	@GetMapping("/public/allPlayersPaged/")
	public Page<Player> allPlayerPaged(@RequestBody PaginRequest paginRequest){
		PageRequest pageable;
		if (paginRequest.getSort() == null) 
		{
			paginRequest.setSort(defaultSortValue);
		}
		if(paginRequest.getSort().equals("descending")) {
			 pageable = PageRequest.of(paginRequest.getNumber(),paginRequest.getSize(),Sort.by(paginRequest.getSort()).descending());
		Page<Player> page = playerRepository.findAll(pageable);
		return page;
		}
		else {
		pageable = PageRequest.of(paginRequest.getNumber(),paginRequest.getSize(),Sort.by(paginRequest.getSort()).ascending());
		Page<Player> page = playerRepository.
				findAll(pageable);
		return page;
		}
	}
	
	
	@GetMapping("/public/player_email/{email}")
	public Optional<Player> player(@PathVariable String email) {
		return playerRepository.findByEmail(email);
	}
	
	@GetMapping("/public/player_id/{id}")
	public Optional<Player> player(@PathVariable Long id) {
		return playerRepository.findById(id);
	}
	
	@GetMapping("/public/storico_player_id/{id}")
	public List <SeasonAppoggio>storicoPLayer(@PathVariable Long id) {
		List <SeasonAppoggio>seasonA=new ArrayList<SeasonAppoggio>();
		Optional<Player> player=playerRepository.findById(id);
		List <LeagueAppoggioPlayer> leagueA=new ArrayList<LeagueAppoggioPlayer>();
		int seasonTrovata=0;
		int indice = -1; int flag=0;
		if(player.isEmpty())
			return null;
		Player p=player.get();
		
		for(PlayerSeasonTeam pst: p.getPlayerSeasonTeam()) {
			CompositeKeyTeamSeason teamSeason=new CompositeKeyTeamSeason(pst.getSeason(), pst.getTeam());
			if( seasonA.isEmpty()) { 
				List<String>teams= new ArrayList<String>();
				teams.add(teamSeason.getTeam().getName());
				leagueA.add(new LeagueAppoggioPlayer(teamSeasonLeagueRepository.findByTeamseason(teamSeason).getLeague().getName(),teams));
				flag=1;
			}
			for(SeasonAppoggio s: seasonA) 
				if(s.getStagioneId().equals(teamSeason.getSeason().getId())) {
					seasonTrovata=1;				
					indice=seasonA.indexOf(s);
				}
			if(seasonTrovata==0) {
				List <LeagueAppoggioPlayer> leagueApp=new ArrayList<LeagueAppoggioPlayer>();
				List<String>teams= new ArrayList<String>();
				teams.add(teamSeason.getTeam().getName());
				leagueApp.add(new LeagueAppoggioPlayer(teamSeasonLeagueRepository.findByTeamseason(teamSeason).getLeague().getName(),teams));	
				seasonA.add(new SeasonAppoggio(pst.getSeason().getId(), leagueApp));
			}
			else {
				if(seasonA.get(indice).getLeagues().size()==2) {
					flag=1;
				//List <LeagueAppoggioPlayer> leagueApp=new ArrayList<LeagueAppoggioPlayer>();
				List<String>teams= new ArrayList<String>();
				teams.add(teamSeason.getTeam().getName());
				seasonA.get(indice).getLeagues().add(new LeagueAppoggioPlayer(teamSeasonLeagueRepository.findByTeamseason(teamSeason).getLeague().getName(),teams));	
				}
			}
			
			if(flag==0 && seasonTrovata==1) {
			if(seasonA.get(indice).getLeagues().get(0).getName().equals((teamSeasonLeagueRepository.findByTeamseason(teamSeason)).getLeague().getName()))
				seasonA.get(indice).getLeagues().get(0).getTeams().add(pst.getTeam().getName());
			else {
				List<String>teams= new ArrayList<String>();
				teams.add(teamSeason.getTeam().getName());
				seasonA.get(indice).getLeagues().add(new LeagueAppoggioPlayer(teamSeasonLeagueRepository.findByTeamseason(teamSeason).getLeague().getName(),teams));
			}
			}
			seasonTrovata=0;
			flag=0;
		}
			
		 return seasonA;
	}
	
	@GetMapping("/public/player_birth/{birth}")
	public List <Player> player1(@PathVariable String birth) {
		return playerRepository.findByBirthYear(birth);
	}
	
	@GetMapping("/public/player_name/{name}")
	public List <Player> player2(@PathVariable String name) {
		return playerRepository.findByName(name);
	}
	
	@GetMapping("/public/player_surname/{surname}")
	public List <Player> player3(@PathVariable String surname) {
		return playerRepository.findBySurname(surname);
	}
	
	@PostMapping("/protected/player/insert")
	public void insertPlayer(@RequestBody Player p){

		Player player=new Player();
		
		player.setSurname(p.getSurname());
		player.setName(p.getName());
		player.setBirthYear(p.getBirthYear());
		player.setEmail(p.getEmail());
		//player.setPlayerSeasonTeam(p.getPlayerSeasonTeam());
		
		
		
		playerRepository.saveAndFlush(player);
	}

	@PostMapping("/protected/player/file-insert")
	public void fileInsert(){
		
		List<Player> list = playerService.readPlayerLineByLine("C:\\fileDirectorySpring\\file.csv");
		playerRepository.saveAll(list);
	}
	
	@PutMapping("/protected/player/put")
	public void putPlayer(@RequestBody Player p){

		Optional <Player> player=playerRepository.findById(p.getId());
		if(player.isPresent()) {
			player.get().setSurname(p.getSurname());
			player.get().setName(p.getName());
			player.get().setBirthYear(p.getBirthYear());
			playerRepository.saveAndFlush(player.get());
		}
		
	}

}
