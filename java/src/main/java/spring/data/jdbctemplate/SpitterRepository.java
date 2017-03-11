package spring.data.jdbctemplate;

import java.util.List;

import spring.data.domain.Spitter;

/**
 * Repository interface for Spitter persistence
 *
 * Spitter example drawn from Spring in Action 4th Edition by Craig Walls and Manning Publications Co.
 */
public interface SpitterRepository
{
	long count();
	public Spitter findOne(Long id);
	public List<Spitter> find();
	public Spitter save(Spitter spitter);
	public Spitter findByUsername(String username);
}
