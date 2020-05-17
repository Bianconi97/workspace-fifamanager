package com.uefa.fifamanager.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.Nation;
import com.uefa.fifamanager.repository.NationRepository;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@RestController
@Api
public class NationController {
	
	@Autowired
	NationRepository nationRepository;
	
	Sort sort = Sort.by("code").ascending();
	
	@ApiOperation(value = "restituisce tutte le nazioni")
	@GetMapping("/public/allnations")
	public List <Nation> allNation(){
		return nationRepository.findAll(sort);
		
	}
	
	@GetMapping("/public/nation_id/{id}")
	public Optional<Nation> nation(@PathVariable Long id) {
		return nationRepository.findById(id);
	}
	
	@GetMapping("/public/nation_code/{code}")
	public Optional <Nation> nationCode(@PathVariable String code){
		return nationRepository.findByCode(code);
	}
	@GetMapping("/public/nation_name/{name}")
	public Optional <Nation> nationName(@PathVariable String name){
		return nationRepository.findByName(name);
	}
	@GetMapping("/public/nation_leagues/{id}")
	public Set<League> nationLeague(@PathVariable Long id){
		return nationRepository.findById(id).get().getLeagues();
	}
	
	@PostMapping("/protected/nation/insert")
	public void insertNation(@RequestBody Nation n){

		Nation nation=new Nation();
		nation.setCode(n.getCode());
		nation.setName(n.getName());
		
		nationRepository.saveAndFlush(nation);
	}
	
	@PutMapping("/protected/nation/put")
	public void putNation(@RequestBody Nation n){

		Optional <Nation> nation=nationRepository.findById(n.getId());
		if(nation.isPresent()) {
			nation.get().setCode(n.getCode());
			nation.get().setName(n.getName());
			nationRepository.saveAndFlush(nation.get());
		}
		
	}
	@DeleteMapping("/protected/nation/delete/{id}")
	public void deleteNation(@PathVariable Long id) {
		Optional <Nation> nation=nationRepository.findById(id);
		if(nation.isPresent()) {
			nationRepository.deleteById(nation.get().getId());
		}
	}
	@DeleteMapping("/protected/nation/more-delete/{ids}")
	public void deleteNation(@PathVariable List <Long> ids) {
		
		List <Nation> nation=nationRepository.findByIdIn(ids);
		for(Nation n : nation ) {
			nationRepository.deleteById(n.getId());
		}
	}
}
