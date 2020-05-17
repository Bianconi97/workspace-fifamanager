package com.uefa.fifamanager.controller;

import java.util.ArrayList;
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
import com.uefa.fifamanager.repository.LeagueRepository;
import com.uefa.fifamanager.repository.NationRepository;

@RestController
public class LegueController {

	@Autowired
	LeagueRepository leagueRepository;
	@Autowired
	NationRepository nationRepository;
	
	@GetMapping("/public/all_leagues")
	public List <League> allLeague(){
		return leagueRepository.findAll();
	}
	
	@GetMapping("/public/league_by_league_id/{id}")
	public Optional<League> league(@PathVariable Long id) {
		return leagueRepository.findById(id);
	}
	
	@GetMapping("/public/league_by_nation_id/{id}")
	public Set<League> leagueNationId(@PathVariable Long id){
		Optional<Nation> n= nationRepository.findById(id);
		return n.get().getLeagues();
	}
	
	@GetMapping("/public/league_by_league_name/{name}")
	public Optional<League> league(@PathVariable String name) {
		return leagueRepository.findByName(name);
	}
	
	@PostMapping("/protected/league/insert")
	public void insertLeague(@RequestBody League l){

		League league=new League();
		if(nationRepository.findById(l.getNation().getId()) != null) {
			league.setName(l.getName());
			league.setNation(l.getNation());
			Optional<Nation> n=nationRepository.findById(l.getNation().getId());
			n.get().getLeagues().add(league);
		}
		
		leagueRepository.saveAndFlush(league);
	}
	
	@PutMapping("/protected/league/put")
	public void putLeague(@RequestBody League l){

		Optional <League> league=leagueRepository.findById(l.getId());
		Optional<Nation> n = nationRepository.findById(l.getNation().getId());
		if(league.isPresent() && n.isPresent()) {
			league.get().getNation().getLeagues().remove(league.get());
			league.get().setName(l.getName());
			league.get().setNation(l.getNation());
			n.get().getLeagues().add(l);
			}
		leagueRepository.saveAndFlush(league.get());
	}
	
	@DeleteMapping("/protected/league/delete/{id}")
	public void deleteLeague(@PathVariable Long id) {
		Optional<League> league=leagueRepository.findById(id);
		if(league.isPresent()) {
			league.get().getNation().getLeagues().remove(league.get());
			leagueRepository.deleteById(league.get().getId());
		}
	}
	
	
	  @DeleteMapping("/protected/league/more-delete/{ids}")
	  public void deleteLeagues(@PathVariable List<Long> ids) 
	  { 
		  List <League> leagues= leagueRepository.findByIdIn(ids); 
		  for(League l : leagues ) {
			  this.deleteLeague(l.getId());
		  } 
	  }
	 
}
