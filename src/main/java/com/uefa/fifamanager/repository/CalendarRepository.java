package com.uefa.fifamanager.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uefa.fifamanager.model.Calendar;


@Repository
public interface CalendarRepository extends JpaRepository<Calendar, Long>  {

	List<Calendar> findAllBySeasonIdAndLeagueId(Long id, Long id2);

	List<Calendar> findAllByHomeTeamIdAndVisitingTeamId(Long id, Long id2);

	List<Calendar> findBySeasonIdAndLeagueIdOrderByGiornata(Long season_id, Long league_id);

	List<Calendar> findBySeasonIdAndLeagueIdAndGiornata(Long season_id, Long league_id, Integer giornata);

	List<Calendar> findBySeasonIdAndLeagueId(Long season_id, Long league_id);

	List<Calendar> findBySeasonIdOrderByGiornata(Long season_id);

	List<Calendar> findByLeagueIdAndSeasonIdAndGirone(Long league_id, Long season_id, String girone);

	List<Calendar> findBySeasonIdAndGirone(Long season_id, String string);

}
