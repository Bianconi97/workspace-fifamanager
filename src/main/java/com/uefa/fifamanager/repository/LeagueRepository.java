package com.uefa.fifamanager.repository;

import java.util.List;
import java.util.Optional;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uefa.fifamanager.model.League;

@Repository
public interface LeagueRepository extends JpaRepository<League, Long> {

	Optional<League> findByName(String name);

	List<League> findByIdIn(List<Long> ids);


}
