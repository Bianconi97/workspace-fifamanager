package com.uefa.fifamanager.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class EliminazioneAppoggio {
	private String phase;
	private int giornata;
	private String match;
	private String homeTeam;
	private String visitingTeam;
	private int goalHomeTeam;
	private int goalVisitingTeam;
}
