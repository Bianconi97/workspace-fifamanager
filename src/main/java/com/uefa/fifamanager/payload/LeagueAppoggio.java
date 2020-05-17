package com.uefa.fifamanager.payload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.Nation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class LeagueAppoggio {
	private String name;
	private Set <TeamAppoggio> teamsA= new HashSet<TeamAppoggio>();

}
