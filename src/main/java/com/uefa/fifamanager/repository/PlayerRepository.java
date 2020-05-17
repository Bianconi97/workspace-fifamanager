package com.uefa.fifamanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uefa.fifamanager.model.Player;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

	List<Player> findAll();
	
	Optional<Player> findByEmail(String email);

	List<Player> findByBirthYear(String birth);

	List<Player> findByName(String name);

	List<Player> findBySurname(String surname);

	void saveAndFlush(List<Player> players);
	

}
