package com.uefa.fifamanager.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uefa.fifamanager.model.Nation;

@Repository
public interface NationRepository extends JpaRepository<Nation, Long>  {

	List<Nation> findAll();

	Optional<Nation> findByCode(String code);

	Optional<Nation> findByName(String name);

	List<Nation> findByIdIn(List<Long> ids);

	Nation findById(Nation nation);

}
