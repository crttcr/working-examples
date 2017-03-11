package spring.data.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Objects;

import javax.inject.Inject;
import javax.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import spring.data.domain.Spitter;

/**
 * Simple class to demonstrate the code required for a simple insert with JDBC
 *
 */
@Slf4j
public class RawJdbcInsert
{
	private final String SQL_INSERT = "INSERT INTO Spitter (username, password, fullname) VALUES (?, ?, ?)";

	@Inject
	private DataSource ds;
	public RawJdbcInsert(DataSource datasource)
	{
		this.ds = datasource;
	}


	public void addSpitter(Spitter spitter) {
		Objects.requireNonNull(spitter);

		PreparedStatement sx = null;

		try (Connection cx = ds.getConnection())
		{
			sx = cx.prepareStatement(SQL_INSERT);

			sx.setString(1, spitter.getUsername());
			sx.setString(2, spitter.getPassword());
			sx.setString(3, spitter.getFullname());

			sx.execute();
		}
		catch (SQLException e)
		{
			// Do something ...
			//
			log.error(e.getLocalizedMessage());
		}
		finally
		{
			try
			{
				if (sx != null)
				{
					sx.close();
				}
			}
			catch (SQLException e)
			{
				// Do something here too ...
				log.error(e.getLocalizedMessage());
			}
		}
	}
}
