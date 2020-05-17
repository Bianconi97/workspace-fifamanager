package com.uefa.fifamanager.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class Ranking {
	
	private String teamName;
	private Integer points;
	private Integer vinte;
	private Integer pari;
	private Integer perse;
	private Integer scoredGoal;
	private Integer concededGoal;

}
