package com.uefa.fifamanager.payload;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.Nation;
import com.uefa.fifamanager.model.Player;
import com.uefa.fifamanager.model.Team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class TeamAppoggio {
	private String name;
	private List <PlayerAppoggio> players= new ArrayList<PlayerAppoggio>();
	
}
