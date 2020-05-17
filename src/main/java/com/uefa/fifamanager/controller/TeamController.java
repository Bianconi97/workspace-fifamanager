package com.uefa.fifamanager.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.uefa.fifamanager.model.Season;
import com.uefa.fifamanager.model.Team;
import com.uefa.fifamanager.repository.TeamRepository;

@RestController
public class TeamController {

	@Autowired
	TeamRepository teamRepository;
	
	@GetMapping("/public/allteams")
	public List <Team> allTeam(){
		return teamRepository.findAll();
	}
	

	@GetMapping("/public/team_id/{id}")
	public Optional<Team> team(@PathVariable Long id) {
		return teamRepository.findById(id);
	}
	
	@GetMapping("/public/team_name/{name}")
	public Optional<Team> team(@PathVariable String name) {
		return teamRepository.findByName(name);
	}
	
	@GetMapping("/public/team_city/{city}")
	public List<Team> teams(@PathVariable String city) {
		return teamRepository.findByCity(city);
	}
	
	@PostMapping("/protected/team/insert")
	public void insertTeam(@RequestBody Team t){

		Team team=new Team();
		team.setCity(t.getCity());
		team.setName(t.getName());
		teamRepository.saveAndFlush(team);
	}
	
	@PutMapping("/protected/team/put")
	public void putTeam(@RequestBody Team t){

		Optional <Team> team=teamRepository.findById(t.getId());
		if(team.isPresent()) {
			team.get().setCity(t.getCity());
			team.get().setName(t.getName());
			teamRepository.saveAndFlush(team.get());
		}
		
	}
	@DeleteMapping("/protected/team/more-delete/{ids}")
	public void deleteTeam(@PathVariable List <Long> ids) {
		
		List <Team> teams=teamRepository.findByIdIn(ids);
		for(Team t : teams ) {
			teamRepository.deleteById(t.getId());
		}
	}
	
}
