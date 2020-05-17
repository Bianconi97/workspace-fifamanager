package com.uefa.fifamanager.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
//@Data 

//@AllArgsConstructor 
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
property = "id")
public class League {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	//@NaturalId
	@Size(max=50)
	private String name;
	@NotNull 
	@ManyToOne(fetch = FetchType.EAGER) 
	@JoinColumn
	private Nation nation;
	private Boolean legaMaggiore;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Nation getNation() {
		return nation;
	}
	public void setNation(Nation nation) {
		this.nation = nation;
	}
	public Boolean getLegaMaggiore() {
		return legaMaggiore;
	}
	public void setLegaMaggiore(Boolean legaMaggiore) {
		this.legaMaggiore = legaMaggiore;
	}
	public League(@Size(max = 50) String name, @NotNull Nation nation,Boolean legaMaggiore) {
		super();
		//this.id = id;
		this.legaMaggiore = legaMaggiore;
		this.name = name;
		this.nation = nation;
	}
	public League(@Size(max = 50) String name, @NotNull Nation nation) {
		//this.id = id;
		this.name = name;
		this.nation = nation;
	}

	
	
	
	
}
