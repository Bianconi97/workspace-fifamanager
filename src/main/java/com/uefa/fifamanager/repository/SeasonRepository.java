package com.uefa.fifamanager.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uefa.fifamanager.model.Season;

@Repository
public interface SeasonRepository extends JpaRepository<Season, Long>{

	List<Season> findAll();

	Optional<Season> findById(Long id);

	List<Season> findByIdIn(List<Long> ids);

}
