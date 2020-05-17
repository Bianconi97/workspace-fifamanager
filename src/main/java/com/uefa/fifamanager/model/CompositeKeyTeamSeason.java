package com.uefa.fifamanager.model;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable

@NoArgsConstructor
@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "season")
public class CompositeKeyTeamSeason implements Serializable  {
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name= "season_id")
	private Season season=new Season();
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name= "team_id")
	private Team team=new Team();
	
	
	public Season getSeason() {
		return season;
	}
	public void setSeason(Season season) {
		this.season = season;
	}
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}


}
