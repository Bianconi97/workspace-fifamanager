package com.uefa.fifamanager.payload;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor @NoArgsConstructor @Data
public class DayAppoggio {
	
	private int day;
	private List<String> matches= new ArrayList<String>();
}
