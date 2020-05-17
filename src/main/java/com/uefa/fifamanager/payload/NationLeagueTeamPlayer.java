package com.uefa.fifamanager.payload;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.Nation;
import com.uefa.fifamanager.model.Player;
import com.uefa.fifamanager.model.Team;



public class NationLeagueTeamPlayer {
	Set <Nation> nations;
	Map <Long,Set<League>> leagues;
	Map<Long,Set<Team>> teams;
	Map<Long,Set<Player>> players;
	
	
	
	public Set<Nation> getNations() {
		return nations;
	}
	public void setNations(Set<Nation> nations) {
		this.nations = nations;
	}
	public Map<Long, Set<League>> getLeagues() {
		return leagues;
	}
	public void setLeagues(Map<Long, Set<League>> leagues) {
		this.leagues = leagues;
	}
	public Map<Long, Set<Team>> getTeams() {
		return teams;
	}
	public void setTeams(Map<Long, Set<Team>> teams) {
		this.teams = teams;
	}
	public Map<Long, Set<Player>> getPlayers() {
		return players;
	}
	public void setPlayers(Map<Long, Set<Player>> players) {
		this.players = players;
	}
	
	
	
	
	
	
	
}
