package spring.data.jdbc.raw;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import spring.data.domain.Spitter;

/**
 * Simple class demonstrates the code required to query with
 * straight JDBC. To drive home how much boilerplate code is required
 * and how often it is repeated, two queries are defined. However,
 * a couple of helper methods make it a little less insane.
 *
 * Spitter example drawn from Spring in Action 4th Edition by Craig Walls and Manning Publications Co.
 */
@Slf4j
public class RawJdbcQuery
{
	private static final String SQL_QUERY_FIND = "SELECT id, username, password, fullname FROM Spitter";
	private static final String SQL_QUERY_FIND_ONE = "SELECT id, username, password, fullname FROM Spitter WHERE id = ?";

	private DataSource ds;

	public RawJdbcQuery(DataSource datasource)
	{
		this.ds = datasource;
	}

	public Spitter findOne(long id)
	{
		PreparedStatement sx = null;
		ResultSet         rs = null;
		Spitter      spitter = null;

		try (Connection cx = ds.getConnection())
		{
			sx = cx.prepareStatement(SQL_QUERY_FIND_ONE);
			sx.setLong(1, id);
			rs = sx.executeQuery();

			if (rs.next())
			{
				spitter = rs2spitter(rs);
			}
		}
		catch (SQLException e)
		{
			// Do something ... even if it's just logging out the error.
			//
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		finally
		{
			try
			{
				closeIfNotNull(rs);
				closeIfNotNull(sx);
			}
			catch (Exception e)
			{
				// Do something here too ...
				//
				log.error(e.toString());
				e.printStackTrace();
			}
		}
		return spitter;
	}

	public List<Spitter> find()
	{
		PreparedStatement sx = null;
		ResultSet         rs = null;
		List<Spitter> rv = new ArrayList<>();

		try (Connection cx = ds.getConnection())
		{
			sx = cx.prepareStatement(SQL_QUERY_FIND);
			rs = sx.executeQuery();

			while (rs.next())
			{
				Spitter spitter = rs2spitter(rs);
				rv.add(spitter);
			}
		}
		catch (SQLException e)
		{
			// Yep, writing out more error handling code
			//
			log.error(e.getLocalizedMessage());
			e.printStackTrace();
		}
		finally
		{
			try
			{
				closeIfNotNull(rs);
				closeIfNotNull(sx);
			}
			catch (Exception e)
			{
				// Getting fairly tired of this by now.
				//
				log.error(e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		return rv;
	}

	// Abstract the null test into a method, simply makes the call
	// query methods' resource cleanup less bulky.
	//
	private void closeIfNotNull(AutoCloseable resource) throws Exception
	{
		if (resource != null)
		{
			resource.close();
		}
	}

	private Spitter rs2spitter(ResultSet rs) throws SQLException
	{
		Spitter spitter;
		spitter = new Spitter();
		spitter.setId(rs.getLong("id"));
		spitter.setUsername(rs.getString("username"));
		spitter.setPassword(rs.getString("password"));
		spitter.setFullname(rs.getString("fullname"));
		return spitter;
	}
}
