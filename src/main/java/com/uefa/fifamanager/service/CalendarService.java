package com.uefa.fifamanager.service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.uefa.fifamanager.model.Calendar;
import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.Season;
import com.uefa.fifamanager.model.Team;
import com.uefa.fifamanager.payload.GiornataRanking;
import com.uefa.fifamanager.payload.Ranking;
import com.uefa.fifamanager.repository.CalendarRepository;
import com.uefa.fifamanager.repository.LeagueRepository;
import com.uefa.fifamanager.repository.NationRepository;
import com.uefa.fifamanager.repository.SeasonRepository;
import com.uefa.fifamanager.repository.TeamRepository;
import com.uefa.fifamanager.repository.TeamSeasonLeagueRepository;

@Service
public class CalendarService {
	
	@Autowired
	CalendarRepository calendarRepository;

	
	
//	int giornata;
//	public void generaPermutazioni(List<Team> t, int estremoinferiore,Season s,League l,int g){
//		
//		Team temp= new Team();
//		int cnt=0;
//		// memorizza permutazione attuale
//		if (estremoinferiore==t.size()-1) {
//			for (int j=0; j<t.size(); j=j+2) {
//				
//				if(!t.get(j).equals(t.get(j+1)) ) {
//					Calendar c= new Calendar(s,l,t.get(j),t.get(j+1),giornata);
//					calendarRepository.saveAndFlush(c);
//					c= new Calendar(s,l,t.get(j+1),t.get(j),giornata/2);
//					calendarRepository.saveAndFlush(c);
//				}
//				cnt++;
//				if(cnt==t.size()/2) {
//					giornata--;
//					cnt=0;
//				}
//			}
//
//		}
//		for (int i=estremoinferiore; i<t.size()-1; i++){
//			if(i==0)
//				giornata=t.size();
//			temp = t.get(estremoinferiore);
//			t.remove(estremoinferiore);
//			t.add(estremoinferiore, t.get(i));
//			t.remove(i);
//			t.add(i, temp);
//			generaPermutazioni(t, estremoinferiore+1,s,l,g);
//			temp = t.get(estremoinferiore);
//			t.remove(estremoinferiore);
//			t.add(estremoinferiore, t.get(i));
//			t.remove(i);
//			t.add(i, temp);
//		}
//	}

	public void generaPermutazioni(List <Team> teams,Season s,League l){
		 
	    int n = teams.size();
	    int giornate = n - 1;
	 
	    /* crea gli array per le due liste in casa e fuori */
	    List <Team> home = new ArrayList<Team>();
	    List <Team> visiting = new ArrayList<Team>();
	 
	    for (int i = 0; i < n /2; i++) {
	        home.add(i, teams.get(i));
	        visiting.add(i, teams.get(n-1-i)); 
	    }
	    int gRitorno=giornate*2; 
	    int gAbdata=giornate;
	    for (int i = 0; i < giornate; i++) {
	    	
	        /* alterna le partite in casa e fuori */
	        if (i % 2 == 0) {
	            for (int j = 0; j < n /2 ; j++) {
	            	Calendar c= new Calendar(s,l,visiting.get(j),home.get(j),gRitorno);
	            	calendarRepository.saveAndFlush(c);
	            	c= new Calendar(s,l,home.get(j),visiting.get(j),gAbdata);
	            	calendarRepository.saveAndFlush(c);
	            	}
	        }
	        else {
	            for (int j = 0; j < n /2 ; j++) {
	            	Calendar c= new Calendar(s,l,home.get(j),visiting.get(j),gRitorno);
					calendarRepository.saveAndFlush(c);
					c= new Calendar(s,l,visiting.get(j),home.get(j),gAbdata);
					calendarRepository.saveAndFlush(c);
	            } 
	        }
	 
	        // Ruota in gli elementi delle liste, tenendo fisso il primo elemento
	        // Salva l'elemento fisso
	        Team pivot = home.get(0);
	 
	        /* sposta in avanti gli elementi di "trasferta" inserendo 
	           all'inizio l'elemento casa[1] e salva l'elemento uscente in "riporto" */
			   
	 		Team riporto = visiting.get(visiting.size()-1);
			visiting = shiftRight(visiting, home.get(1));

	        /* sposta a sinistra gli elementi di "casa" inserendo all'ultimo 
	           posto l'elemento "riporto" */
			   
	        home = shiftLeft(home, riporto);
	 
	        // ripristina l'elemento fisso
	        home.set(0, pivot);
	        
	        gAbdata--;
	        gRitorno--;
	    } 
	}
	
	public void generaPermutazioniGironi(List <Team> teams,Season s,League l,String girone){
		 
	    int n = teams.size();
	    int giornate = n - 1;
	 
	    /* crea gli array per le due liste in casa e fuori */
	    List <Team> home = new ArrayList<Team>();
	    List <Team> visiting = new ArrayList<Team>();
	 
	    for (int i = 0; i < n /2; i++) {
	        home.add(i, teams.get(i));
	        visiting.add(i, teams.get(n-1-i)); 
	    }
	    int gRitorno=giornate*2; 
	    int gAbdata=giornate;
	    for (int i = 0; i < giornate; i++) {
	    	
	        /* alterna le partite in casa e fuori */
	        if (i % 2 == 0) {
	            for (int j = 0; j < n /2 ; j++) {
	            	Calendar c= new Calendar(s,l,visiting.get(j),home.get(j),gRitorno,girone);
	            	calendarRepository.saveAndFlush(c);
	            	c= new Calendar(s,l,home.get(j),visiting.get(j),gAbdata,girone);
	            	calendarRepository.saveAndFlush(c);
	            	}
	        }
	        else {
	            for (int j = 0; j < n /2 ; j++) {
	            	Calendar c= new Calendar(s,l,home.get(j),visiting.get(j),gRitorno,girone);
					calendarRepository.saveAndFlush(c);
					c= new Calendar(s,l,visiting.get(j),home.get(j),gAbdata,girone);
					calendarRepository.saveAndFlush(c);
	            } 
	        }
	 
	        // Ruota in gli elementi delle liste, tenendo fisso il primo elemento
	        // Salva l'elemento fisso
	        Team pivot = home.get(0);
	 
	        /* sposta in avanti gli elementi di "trasferta" inserendo 
	           all'inizio l'elemento casa[1] e salva l'elemento uscente in "riporto" */
			   
	 		Team riporto = visiting.get(visiting.size()-1);
			visiting = shiftRight(visiting, home.get(1));

	        /* sposta a sinistra gli elementi di "casa" inserendo all'ultimo 
	           posto l'elemento "riporto" */
			   
	        home = shiftLeft(home, riporto);
	 
	        // ripristina l'elemento fisso
	        home.set(0, pivot);
	        
	        gAbdata--;
	        gRitorno--;
	    } 
	}
	 
	 private List<Team> shiftLeft(List<Team> home, Team riporto) {
			List <Team> temp = new ArrayList<Team>();
			for(int i=0;i<home.size();i++)
				temp.add(new Team());
			for (int i = 0; i < home.size()-1; i++) {
				temp.set(i, home.get(i+1));
			}
			temp.set(home.size()-1,riporto);
			return temp;
		}

	private List<Team> shiftRight(List<Team> visiting, Team team) {
			List <Team> temp = new ArrayList<Team>();
			for(int i=0;i<visiting.size();i++)
				temp.add(new Team());
			for (int i = 1; i < visiting.size(); i++) {
				temp.set(i, visiting.get(i-1));
			}
			temp.set(0, team);
			return temp;
		}
		
	class SortbyPoint implements Comparator<Ranking> 
	{ 
		@Override
		public int compare(Ranking o1, Ranking o2) {
			return o1.getPoints()-o2.getPoints();
		} 
	} 
	public GiornataRanking ordinaClassifica(List <Ranking> rankings,Long season_id,Long league_id) {
		Integer giornata=-1;
		GiornataRanking result;
		List <Calendar> calendars= new ArrayList <Calendar>();
			
		calendars = calendarRepository.findBySeasonIdAndLeagueIdOrderByGiornata(season_id,league_id);
		for(Calendar c: calendars) {
			if(c.getGoalHomeTeam()!=null && c.getVisitingTeam()!=null) {
				giornata=c.getGiornata();
				for(Ranking r: rankings) {
					if(c.getHomeTeam().getName().equals(r.getTeamName())) {
						r.setConcededGoal(c.getGoalVisitingTeam()+ r.getConcededGoal());
						r.setScoredGoal(c.getGoalHomeTeam()+r.getScoredGoal());
						if(c.getGoalHomeTeam()<c.getGoalVisitingTeam())
							r.setPerse(r.getPerse()+1);
						if(c.getGoalHomeTeam()>c.getGoalVisitingTeam()) {
							r.setVinte(r.getVinte()+1);
							r.setPoints(r.getPoints()+3);
						}
						if(c.getGoalHomeTeam()==c.getGoalVisitingTeam()) {
							r.setPari(r.getPari()+1);
							r.setPoints(r.getPoints()+1);
						}
					}
					if(c.getVisitingTeam().getName().equals(r.getTeamName())) {
						r.setConcededGoal(c.getGoalHomeTeam()+r.getConcededGoal());
						r.setScoredGoal(c.getGoalVisitingTeam()+ r.getScoredGoal());
						if(c.getGoalHomeTeam()<c.getGoalVisitingTeam()) {
							r.setVinte(r.getVinte()+1);
							r.setPoints(r.getPoints()+3);
							}
						if(c.getGoalHomeTeam()>c.getGoalVisitingTeam()) {
							r.setPerse(r.getPerse()+1);
						}
						if(c.getGoalHomeTeam()==c.getGoalVisitingTeam()) {
							r.setPari(r.getPari()+1);
							r.setPoints(r.getPoints()+1);
						}
					}
				}
			}
		}			
		Collections.sort(rankings, new SortbyPoint().reversed());			
		Integer index=1;
		for(int i=0; i<rankings.size()-1;i++) 
			if(rankings.get(i).getPoints()== rankings.get(i+1).getPoints())
				index++;
			
		if(index>1) {
			index=rankings.size();
			int[] puntiDiretti = new int[index];
			int[] scoredDiretti= new int[index];
			int[] concededDiretti= new int[index];
				
			for(Calendar c : calendars) {
				if(c.getGoalHomeTeam()!=null && c.getVisitingTeam()!=null) {
					for(int i=0;i<index;i++) {
						for(int j=0;j<index;j++) {
							if(c.getHomeTeam().getName().equals(rankings.get(i).getTeamName()) && c.getVisitingTeam().getName().equals(rankings.get(j).getTeamName())) {
								if(c.getGoalHomeTeam()<c.getGoalVisitingTeam()) {
									puntiDiretti[j]=puntiDiretti[j]+3;
								}
								if(c.getGoalHomeTeam()>c.getGoalVisitingTeam())
									puntiDiretti[i]=puntiDiretti[i]+3;
								if(c.getGoalHomeTeam()==c.getGoalVisitingTeam()) {
									puntiDiretti[i]=puntiDiretti[i]+1;
									puntiDiretti[j]=puntiDiretti[j]+1;
								}					
								scoredDiretti[j]=scoredDiretti[j]+c.getGoalVisitingTeam();
								scoredDiretti[i]=scoredDiretti[i]+c.getGoalHomeTeam();
								concededDiretti[i]=concededDiretti[i]+c.getGoalVisitingTeam();
								concededDiretti[j]=concededDiretti[j]+c.getGoalHomeTeam();
							}
						}
					}
				}
			}
			int i=0,swap=0;
			while(i<index-1) {
				for(int j=i+1;j<index;j++) {
					if(puntiDiretti[i]<puntiDiretti[j]) {
						int tmpP,tmpS,tmpC;
						Ranking tmpR= new Ranking();
						tmpR=rankings.get(i);
						tmpP=puntiDiretti[i];
						puntiDiretti[i]=puntiDiretti[j];
						puntiDiretti[j]=tmpP;
						tmpS=scoredDiretti[i];
						scoredDiretti[i]=scoredDiretti[j];
						scoredDiretti[j]=tmpS;
						tmpC=concededDiretti[i];
						concededDiretti[i]=concededDiretti[j];
						concededDiretti[j]=tmpC;
						rankings.set(i, rankings.get(j));
						rankings.set(j,tmpR);
						swap=1;
					}
					if(puntiDiretti[i]==puntiDiretti[j]) {
						if(scoredDiretti[i]-concededDiretti[i]<scoredDiretti[j]-concededDiretti[j]) {
							int tmpP,tmpS,tmpC;
							Ranking tmpR= new Ranking();
							tmpR=rankings.get(i);
							tmpP=puntiDiretti[i];
							puntiDiretti[i]=puntiDiretti[j];
							puntiDiretti[j]=tmpP;
							tmpS=scoredDiretti[i];
							scoredDiretti[i]=scoredDiretti[j];
							scoredDiretti[j]=tmpS;
							tmpC=concededDiretti[i];
							concededDiretti[i]=concededDiretti[j];
							concededDiretti[j]=tmpC;
							rankings.set(i, rankings.get(j));
							rankings.set(j,tmpR);
							swap=1;
						}
						if(scoredDiretti[i]-concededDiretti[i] == scoredDiretti[j]-concededDiretti[j]) {
							if(rankings.get(i).getScoredGoal()-rankings.get(i).getConcededGoal() < rankings.get(j).getScoredGoal()-rankings.get(j).getConcededGoal()) {
								int tmpP,tmpS,tmpC;
								Ranking tmpR= new Ranking();
								tmpR=rankings.get(i);
								tmpP=puntiDiretti[i];
								puntiDiretti[i]=puntiDiretti[j];
								puntiDiretti[j]=tmpP;
								tmpS=scoredDiretti[i];
								scoredDiretti[i]=scoredDiretti[j];
								scoredDiretti[j]=tmpS;
								tmpC=concededDiretti[i];
								concededDiretti[i]=concededDiretti[j];
								concededDiretti[j]=tmpC;
								rankings.set(i, rankings.get(j));
								rankings.set(j,tmpR);
								swap=1;
							}
							if(rankings.get(i).getScoredGoal()-rankings.get(i).getConcededGoal() == rankings.get(j).getScoredGoal()-rankings.get(j).getConcededGoal()) {
								if(rankings.get(i).getScoredGoal() < rankings.get(j).getScoredGoal()) {
									int tmpP,tmpS,tmpC;
									Ranking tmpR= new Ranking();
									tmpR=rankings.get(i);
									tmpP=puntiDiretti[i];
									puntiDiretti[i]=puntiDiretti[j];
									puntiDiretti[j]=tmpP;
									tmpS=scoredDiretti[i];
									scoredDiretti[i]=scoredDiretti[j];
									scoredDiretti[j]=tmpS;
									tmpC=concededDiretti[i];
									concededDiretti[i]=concededDiretti[j];
									concededDiretti[j]=tmpC;
									rankings.set(i, rankings.get(j));
									rankings.set(j,tmpR);
									swap=1;
								}
							}
						}
					}
				}
				if(swap==0)
					i++;
				swap=0;
			}
		}

		result = new GiornataRanking(giornata, rankings);
		return result;
		}
	
		public Team nextPhase(List <Calendar> calendars,int index) {
			int visitingGoal=0;
			int visitingGoalT=0;
			int homeGoal=0;
			int homegGoalT=0;
			
			visitingGoal=visitingGoal+calendars.get(index).getGoalVisitingTeam();
			homeGoal=homeGoal+calendars.get(index).getGoalHomeTeam();
			visitingGoalT=calendars.get(index).getGoalVisitingTeam();
			for(Calendar c: calendars)
				if(c.getHomeTeam().equals(calendars.get(index).getVisitingTeam()) && c.getVisitingTeam().equals(calendars.get(index).getHomeTeam())) {
					visitingGoal=visitingGoal+c.getGoalHomeTeam();
					homeGoal=homeGoal+c.getGoalVisitingTeam();
					homegGoalT=c.getGoalVisitingTeam();
				}
			if(homeGoal==visitingGoal)
				if(homegGoalT>visitingGoalT)
					return calendars.get(index).getHomeTeam();
				else
					return calendars.get(index).getVisitingTeam();
					
			if(homeGoal>visitingGoal)
				return calendars.get(index).getHomeTeam();
			else
				return calendars.get(index).getVisitingTeam();
		}
}




