package com.uefa.fifamanager.payload;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.uefa.fifamanager.model.Team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class LeagueAppoggioPlayer {
	private String name;
	private List<String> teams= new ArrayList<String>();

}
