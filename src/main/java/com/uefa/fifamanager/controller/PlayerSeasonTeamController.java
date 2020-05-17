package com.uefa.fifamanager.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.Nation;
import com.uefa.fifamanager.model.Player;
import com.uefa.fifamanager.model.PlayerSeasonTeam;
import com.uefa.fifamanager.model.Season;
import com.uefa.fifamanager.model.Team;
import com.uefa.fifamanager.repository.PlayerRepository;
import com.uefa.fifamanager.repository.PlayerSeasonTeamRepository;
import com.uefa.fifamanager.repository.SeasonRepository;
import com.uefa.fifamanager.repository.TeamRepository;


@RestController

public class PlayerSeasonTeamController {
	
	@Autowired
	PlayerSeasonTeamRepository playerSeasonTeamRepository;
	@Autowired
	TeamRepository teamRepository;
	@Autowired
	PlayerRepository playerRepository;
	@Autowired
	SeasonRepository seasonRepository;
	
	@GetMapping("/public/allplayerSeasonTeam")
	public List <PlayerSeasonTeam> allplayerSeasonTeam(){
		return playerSeasonTeamRepository.findAll();
	}
	@GetMapping("/public/playerSeasonTeam_id/{id}")
	public Optional<PlayerSeasonTeam> playerSeasonTeam(@PathVariable Long id) {
		return playerSeasonTeamRepository.findById(id);
	}
	
	@GetMapping("/public/pst_playerId/{id}")
	public Set<PlayerSeasonTeam> pstPlayerId(@PathVariable Long id){
		return playerSeasonTeamRepository.findByPlayerId(id);
	}
	
	@GetMapping("/public/pst_seasonId/{id}")
	public Set<PlayerSeasonTeam> pst_seasonId(@PathVariable Long id){
		return playerSeasonTeamRepository.findBySeasonId(id);
	}
	
	@GetMapping("/public/pst_teamId/{id}")
	public Set<PlayerSeasonTeam> pst_teamId(@PathVariable Long id){
		return playerSeasonTeamRepository.findByTeamId(id);
	}
	
	@GetMapping ("/public/pst_idSeason_idPlayer/{id_season}/{id_player}")
	public Set<PlayerSeasonTeam> pstId(@PathVariable Long id_season,@PathVariable Long id_player ){
		return playerSeasonTeamRepository.findBySeasonIdAndPlayerId(id_season,id_player);
	}
	
	@PostMapping("/protected/player_season_team/insert")
	public void insertPlayerSeasonTeam(@RequestBody PlayerSeasonTeam t)
	{
		PlayerSeasonTeam pst = new PlayerSeasonTeam();
		List<Team> teams = teamRepository.findAll();
		List<Player> players = playerRepository.findAll();
		List<Season> seasons = seasonRepository.findAll();
		boolean condition1 = seasons.stream().filter(k->k.getId()==t.getSeason().getId()).findAny().isPresent();
		boolean condition2 = players.stream().filter(k->k.getId()==t.getPlayer().getId()).findAny().isPresent();
		boolean condition3 = teams.stream().filter(k->k.getId()==t.getTeam().getId()).findAny().isPresent();
		if (condition1 && condition2 && condition3)
		{
			pst.setPlayer(t.getPlayer());
			pst.setSeason(t.getSeason());
			pst.setTeam(t.getTeam());
		}
		playerSeasonTeamRepository.saveAndFlush(pst);
		
	}

	@PutMapping("/protected/playerSeasonTeam/put")
	public void putPlayerSeasonTeam(@RequestBody PlayerSeasonTeam t){

		Optional <PlayerSeasonTeam> pst=playerSeasonTeamRepository.findById(t.getId());
		List<Team> teams = teamRepository.findAll();
		List<Player> players = playerRepository.findAll();
		List<Season> seasons = seasonRepository.findAll();
		boolean condition1 = seasons.stream().filter(k->k.getId()==t.getSeason().getId()).findAny().isPresent();
		boolean condition2 = players.stream().filter(k->k.getId()==t.getPlayer().getId()).findAny().isPresent();
		boolean condition3 = teams.stream().filter(k->k.getId()==t.getTeam().getId()).findAny().isPresent();
		if(condition1 && condition2 && condition3)
		{
			pst.get().setPlayer(t.getPlayer());
			pst.get().setSeason(t.getSeason());
			pst.get().setTeam(t.getTeam());
			
		}
		playerSeasonTeamRepository.saveAndFlush(pst.get());
	}

	@DeleteMapping("/protected/playerSeasonTeam/more-delete/{ids}")
	public void deletePlayerSeasonTeam(@PathVariable List <Long> ids) {
		
		List <PlayerSeasonTeam> pst=playerSeasonTeamRepository.findByIdIn(ids);
		for(PlayerSeasonTeam p : pst ) {
			playerSeasonTeamRepository.deleteById(p.getId());
		}
	}
	
}
