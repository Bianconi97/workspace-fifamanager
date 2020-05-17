package com.uefa.fifamanager.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uefa.fifamanager.model.CompositeKeyTeamSeason;
import com.uefa.fifamanager.model.League;
import com.uefa.fifamanager.model.TeamSeasonLeague;

@Repository
public interface TeamSeasonLeagueRepository extends JpaRepository<TeamSeasonLeague, CompositeKeyTeamSeason>{


	List<TeamSeasonLeague> findByLeagueId(Long id);

	TeamSeasonLeague findByTeamseason(CompositeKeyTeamSeason c);

	void deleteByTeamseason(CompositeKeyTeamSeason c);

	List<TeamSeasonLeague> findByTeamseason(Long id);




}
