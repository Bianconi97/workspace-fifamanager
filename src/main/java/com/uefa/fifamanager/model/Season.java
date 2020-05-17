package com.uefa.fifamanager.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "id")
public class Season {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name= "start_date") 
	private Date startDate;
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name= "end_date")
	private Date endDate;
	@JsonIgnore
	//@NotNull
	@OneToMany(mappedBy="season")
	private Set <PlayerSeasonTeam> playerSeasonTeam;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Set<PlayerSeasonTeam> getPlayerSeasonTeam() {
		return playerSeasonTeam;
	}
	public void setPlayerSeasonTeam(Set<PlayerSeasonTeam> playerSeasonTeam) {
		this.playerSeasonTeam = playerSeasonTeam;
	}

	

}
