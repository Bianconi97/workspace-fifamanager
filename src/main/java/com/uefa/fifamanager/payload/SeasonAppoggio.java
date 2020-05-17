package com.uefa.fifamanager.payload;

import java.util.ArrayList;
import java.util.List;

import com.uefa.fifamanager.model.Team;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class SeasonAppoggio {
	private Long stagioneId;
	private List <LeagueAppoggioPlayer> leagues= new ArrayList<LeagueAppoggioPlayer>();
}
