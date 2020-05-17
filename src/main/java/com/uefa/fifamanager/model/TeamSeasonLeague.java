package com.uefa.fifamanager.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
//@IdClass(CompositeKeyTeamSeason.class)
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "teamseason")
public class TeamSeasonLeague{
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="league_id")
	private League  league;
	@EmbeddedId
	@NaturalId (mutable = true)
	private CompositeKeyTeamSeason teamseason=new CompositeKeyTeamSeason();
	
	public League getLeague() {
		return league;
	}
	public void setLeague(League league) {
		this.league = league;
	}
	public CompositeKeyTeamSeason getTeamseason() {
		return teamseason;
	}
	public void setTeamseason(CompositeKeyTeamSeason teamseason) {
		this.teamseason = teamseason;
	}
	
	
}
