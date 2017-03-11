package spring.data.jdbc;

import java.util.List;

import spring.data.domain.Spitter;

public interface SpitterRepository
{
	long count();
	public Spitter findOne(Long id);
	public List<Spitter> find();
	public Spitter save(Spitter spitter);
	public Spitter findByUsername(String username);
}
