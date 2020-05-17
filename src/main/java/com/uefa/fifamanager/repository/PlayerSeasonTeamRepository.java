package com.uefa.fifamanager.repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uefa.fifamanager.model.Player;
import com.uefa.fifamanager.model.PlayerSeasonTeam;

@Repository
public interface PlayerSeasonTeamRepository extends JpaRepository<PlayerSeasonTeam, Long> {

	List<PlayerSeasonTeam> findByIdIn(List<Long> ids);

	 Set<PlayerSeasonTeam> findByPlayerId(Long id);

	Set<PlayerSeasonTeam> findBySeasonId(Long id);

	Set<PlayerSeasonTeam> findByTeamId(Long id);

	Set<PlayerSeasonTeam> findBySeasonIdAndPlayerId(Long id_season, Long id_player);



}
