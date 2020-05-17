package com.uefa.fifamanager.payload;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor @NoArgsConstructor @Data
public class GiornataRanking {
	private Integer giornata;
	List<Ranking> rankings;
}
