package spring.data.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.data.domain.Spitter;

@Repository
@Transactional
@EnableJpaRepositories
public interface JpaSpitterRepository
extends JpaRepository<Spitter, Long>
{
	Spitter findByUsername(String username);
}
