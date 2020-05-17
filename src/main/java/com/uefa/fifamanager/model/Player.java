package com.uefa.fifamanager.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "email")
public class Player {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NaturalId 
	@Size(max=100)
	private String email;
	@NotNull 
	@Size(max=50)
	private String name;
	@NotNull 
	@Size(max=50)
	private String surname;
	@NotNull
	@Size(max=4)
	private String birthYear;
	@JsonIgnore
	@OneToMany(mappedBy="player",fetch = FetchType.EAGER) 
	private Set <PlayerSeasonTeam> playerSeasonTeam;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getBirthYear() {
		return birthYear;
	}
	public void setBirthYear(String birthYear) {
		this.birthYear = birthYear;
	}
	public Set<PlayerSeasonTeam> getPlayerSeasonTeam() {
		return playerSeasonTeam;
	}
	public void setPlayerSeasonTeam(Set<PlayerSeasonTeam> playerSeasonTeam) {
		this.playerSeasonTeam = playerSeasonTeam;
	}
	public Player(Long id, @Size(max = 100) String email, @NotNull @Size(max = 50) String name,
			@NotNull @Size(max = 50) String surname, @NotNull @Size(max = 4) String birthYear) {
		super();
		this.id = id;
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.birthYear = birthYear;
	}
	@Override
	public String toString() {
		return "Player [id=" + id + ", email=" + email + ", name=" + name + ", surname=" + surname + ", birthYear="
				+ birthYear + "]";
	}
	public Player() {}
	public Player( @Size(max = 100) String email, @NotNull @Size(max = 50) String name,
			@NotNull @Size(max = 50) String surname, @NotNull @Size(max = 4) String birthYear) {
		super();
		this.email = email;
		this.name = name;
		this.surname = surname;
		this.birthYear = birthYear;
	}
}
