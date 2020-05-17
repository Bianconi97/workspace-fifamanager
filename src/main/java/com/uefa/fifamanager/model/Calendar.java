package com.uefa.fifamanager.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@AllArgsConstructor @NoArgsConstructor @Data

public class Calendar {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	@NotNull
	@ManyToOne
	@JoinColumn(name="season")
	private Season season;
	@NotNull 
	@ManyToOne
	@JoinColumn(name="league")
	private League league;
	@NotNull
	@ManyToOne
	@JoinColumn(name="homeTeam")
	private Team homeTeam;
	@NotNull
	@ManyToOne
	@JoinColumn(name="visitingTeam")
	private Team visitingTeam;	
	@NotNull
	private Integer giornata;
	private Integer goalHomeTeam;
	private Integer goalVisitingTeam;
	private String girone;
	
	
	public Calendar( Season season,  League league,  Team homeTeam,  Team visitingTeam,
			Integer giornata) {
		super();
		this.season = season;
		this.league = league;
		this.homeTeam = homeTeam;
		this.visitingTeam = visitingTeam;
		this.giornata = giornata;
		this.goalHomeTeam= null;
		this.goalVisitingTeam= null;
	}
	
	public Calendar( Season season,  League league,  Team homeTeam,  Team visitingTeam,
			Integer giornata,String girone) {
		super();
		this.season = season;
		this.league = league;
		this.homeTeam = homeTeam;
		this.visitingTeam = visitingTeam;
		this.giornata = giornata;
		this.goalHomeTeam= null;
		this.goalVisitingTeam= null;
		this.girone=girone;
	}
	
	
	
}
