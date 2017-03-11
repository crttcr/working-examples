package spring.data.jdbc.template;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import spring.data.domain.Spitter;

/**
 * SpitterRepository implemented using JdbcTemplate.
 *
 * Spitter example drawn from Spring in Action 4th Edition by Craig Walls and Manning Publications Co.
 */
@Repository
public class JdbcTemplateSpitterRepository
implements SpitterRepository
{
	private static final String DB_TABLE_NAME     = "Spitter";
	private static final String DB_FIELD_ID       = "id";
	private static final String DB_FIELD_USERNAME = "username";
	private static final String DB_FIELD_PASSWORD = "password";
	private static final String DB_FIELD_FULLNAME = "fullname";

	private static final String SQL_FIND_ALL         = "SELECT id, username, password, fullname FROM Spitter ORDER by id";
	private static final String SQL_FIND_BY_ID       = "SELECT id, username, password, fullname FROM Spitter WHERE id=?";
	private static final String SQL_FIND_BY_USERNAME = "SELECT id, username, password, fullname FROM Spitter WHERE username=?";
	private static final String SQL_COUNT            = "SELECT count(id) FROM Spitter";
	private static final String SQL_UPDATE           = "UPDATE Spitter set username=?, password=?, fullname=? WHERE id=?";

	private JdbcTemplate jdbc;

	@Inject
	public JdbcTemplateSpitterRepository(JdbcTemplate jdbc)
	{
		this.jdbc = jdbc;
	}

	@Override
	public long count()
	{

		return jdbc.queryForObject(SQL_COUNT, Long.class);
	}

	@Override
	public List<Spitter> find()
	{
		return jdbc.query(SQL_FIND_ALL, new SpitterRowMapper());
	}

	// The initial implementation of findOne uses a RowMapper class
	//
	@Override
	public Spitter findOne(Long id)
	{
		SpitterRowMapper rm = new SpitterRowMapper();
		return jdbc.queryForObject(SQL_FIND_BY_ID, rm, id);
	}

	// Another implementation of findOne using an embedded lamba expression.
	// I find this to be the most inscrutable and least desirable. Only with
	// a lot of alignment formatting effort does it become clear what that the
	// queryForObject method is still receiving 3 paramters.
	//
	public Spitter findOneLambda(Long id)
	{
		return jdbc.queryForObject(
			SQL_FIND_BY_ID,
			(rs, rowNum) ->
			{
				return new Spitter(
					rs.getLong(DB_FIELD_ID),
					rs.getString(DB_FIELD_USERNAME),
					rs.getString(DB_FIELD_PASSWORD),
					rs.getString(DB_FIELD_FULLNAME)
					);
			},
			id);
	}

	// A better alternative than the embedded lambda is to reference a function
	// that does the same thing as {@see SpitterRowMapper}
	//
	public Spitter findOneMethodReference(Long id)
	{
		return jdbc.queryForObject(SQL_FIND_BY_ID, this::mapSpitter, id);

	}

	@Override
	public Spitter findByUsername(String username)
	{
		SpitterRowMapper rm = new SpitterRowMapper();
		return jdbc.queryForObject(SQL_FIND_BY_USERNAME, rm, username);
	}

	@Override
	public Spitter save(Spitter spitter)
	{
		Objects.requireNonNull(spitter);

		Long id = spitter.getId();
		if (id == null) {
			long spitterId = insertSpitterAndReturnId(spitter);
			return new Spitter(spitterId, spitter.getUsername(), spitter.getPassword(), spitter.getFullname());
		}
		else {
			jdbc.update(SQL_UPDATE,
				spitter.getUsername(),
				spitter.getPassword(),
				spitter.getFullname(),
				id);
		}

		return spitter;
	}

	private long insertSpitterAndReturnId(Spitter spitter)
	{
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbc).withTableName(DB_TABLE_NAME);
		insert.setGeneratedKeyName(DB_FIELD_ID);
		Map<String, Object> map = mapFromSpitter(spitter);

		long id = insert.executeAndReturnKey(map).longValue();
		return id;
	}

	private Map<String, Object> mapFromSpitter(Spitter spitter)
	{
		Map<String, Object> map = new HashMap<>();
		map.put(DB_FIELD_ID,       spitter.getId());
		map.put(DB_FIELD_USERNAME, spitter.getUsername());
		map.put(DB_FIELD_PASSWORD, spitter.getPassword());
		map.put(DB_FIELD_FULLNAME, spitter.getFullname());

		return map;
	}

	// The mapSpitter method and SpitterRowMapper class perform the same function,
	// converting the current record in a ResultSet to a domain object.
	//
	private Spitter mapSpitter(ResultSet rs, int row)
		throws SQLException
	{
		long   id = rs.getLong(DB_FIELD_ID);
		String  u = rs.getString(DB_FIELD_USERNAME);
		String  p = rs.getString(DB_FIELD_PASSWORD);
		String  f = rs.getString(DB_FIELD_FULLNAME);

		return new Spitter(id, u, p, f);
	}

	private static final class SpitterRowMapper
	implements RowMapper<Spitter>
	{
		@Override
		public Spitter mapRow(ResultSet rs, int rowNum)
			throws SQLException
		{
			long  id = rs.getLong(DB_FIELD_ID);
			String u = rs.getString(DB_FIELD_USERNAME);
			String p = rs.getString(DB_FIELD_PASSWORD);
			String f = rs.getString(DB_FIELD_FULLNAME);

			return new Spitter(id, u, p, f);
		}
	}


}
