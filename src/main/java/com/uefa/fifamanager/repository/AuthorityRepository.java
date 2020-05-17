package com.uefa.fifamanager.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.uefa.fifamanager.model.Authority;
import com.uefa.fifamanager.model.AuthorityName;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
	Authority findByName(AuthorityName name);

}