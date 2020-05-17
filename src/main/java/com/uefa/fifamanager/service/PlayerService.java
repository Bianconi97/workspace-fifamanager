package com.uefa.fifamanager.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;


import com.uefa.fifamanager.model.Player;




@Service
public class PlayerService {
	public List<Player> readPlayerLineByLine(String file)
	{
		List<Player> lista = new ArrayList<Player>();
		try {
			FileReader fr = new FileReader(file);
			BufferedReader br = new  BufferedReader(fr);
			for (String str : br.lines().skip(1).collect(Collectors.toList()))
			{
				String s[] = str.split("[,;]");
				lista.add(new Player(s[0], s[1], s[2], s[3]));
			}
		
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return lista;
	}
	
	

}