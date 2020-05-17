package com.uefa.fifamanager.payload;



import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.Season;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor

public class CalendarRequest {
	private Season season;
	private League league;

}
