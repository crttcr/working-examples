package spring.data.jdbctemplate;

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
import spring.data.jdbc.SpitterRepository;

@Repository
public class JdbcSpitterRepository
implements SpitterRepository
{
	private static final String SQL_FIND_ALL = "SELECT id, username, password, fullname FROM Spitter ORDER by id";
	private static final String SQL_FIND_BY_ID = "SELECT id, username, password, fullname FROM Spitter WHERE id = ?";
	private static final String SQL_FIND_BY_USERNAME = "SELECT id, username, password, fullname FROM Spitter WHERE username = ?";
	private static final String SQL_COUNT = "SELECT count(id) FROM Spitter";
	private static final String SQL_UPDATE = "UPDATE Spitter set username=?, password=?, fullname=? WHERE id = ?";


	private JdbcTemplate jdbc;
	@Inject
	public JdbcSpitterRepository(JdbcTemplate jdbc)
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

	@Override
	public Spitter findOne(Long id)
	{
		SpitterRowMapper rm = new SpitterRowMapper();
		return jdbc.queryForObject(SQL_FIND_BY_ID, rm, id);
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
		SimpleJdbcInsert insert = new SimpleJdbcInsert(jdbc).withTableName("Spitter");
		insert.setGeneratedKeyName("id");
		Map<String, Object> map = mapFromSpitter(spitter);

		long id = insert.executeAndReturnKey(map).longValue();
		return id;
	}

	private Map<String, Object> mapFromSpitter(Spitter spitter)
	{
		Map<String, Object> map = new HashMap<>();
		map.put("username",  spitter.getUsername());
		map.put("password",  spitter.getPassword());
		map.put("fullname",  spitter.getFullname());
		map.put("id",        spitter.getId());

		return map;
	}

	private static final class SpitterRowMapper
	implements RowMapper<Spitter>
	{
		@Override
		public Spitter mapRow(ResultSet rs, int rowNum)
				throws SQLException
		{
			long id = rs.getLong("id");
			String u = rs.getString("username");
			String p = rs.getString("password");
			String f = rs.getString("fullname");

			return new Spitter(id, u, p, f);
		}
	}


}
