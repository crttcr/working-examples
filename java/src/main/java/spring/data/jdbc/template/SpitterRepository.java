package spring.data.jdbc.template;

import java.util.List;

import spring.data.domain.Spitter;

/**
 * Repository interface for Spitter persistence
 *
 * Spitter example drawn from Spring in Action 4th Edition by Craig Walls and Manning Publications Co.
 */
public interface SpitterRepository
{
	/**
	 * Returns the number of records in the Spitter table
	 *
	 * @return the number of records in the Spitter database
	 */
	long count();

	/**
	 * Finds the Spitter with the specified id, or null if not found.
	 *
	 * @param id the id of the desired Spitter record
	 * @return the Spitter domain object representing the database record, or null
	 */
	public Spitter findOne(Long id);

	/**
	 * Returns a list of all the Spitters in the database, or an empty list if
	 * there are none.
	 *
	 * @return List of Spitter objects representing the database records in the Spitter table.
	 */
	public List<Spitter> find();

	/**
	 * Performs an Upsert of the Spitter provided as an argument.
	 * Requires that the Spitter argument not be null or a NullPointerException will be thrown.
	 *
	 *
	 * @param spitter the Spitter domain object to save
	 * @throws NullPointerException if the spitter argument is null
	 *
	 * @return a Spitter object as available after the save operation
	 *
	 */
	public Spitter save(Spitter spitter);

	/**
	 * Returns the single Spitter with the given username or null if not found.
	 *
	 * @param username the name to search for.
	 * @throws NullPointerException if the username argument is null
	 * @return the spitter with the given username or null if there isn't one
	 */
	public Spitter findByUsername(String username);
}
