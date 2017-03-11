package spring.data.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.data.SpitterRepository;
import spring.data.domain.Spitter;

@Repository
@Transactional
public class HibernateSpitterRepository
implements SpitterRepository
{

	private final SessionFactory sf;

	@Inject
	public HibernateSpitterRepository(SessionFactory sessionFactory)
	{
		this.sf = sessionFactory;
	}

	private Session session()
	{
		Session rv = null;
		try
		{
			rv = sf.getCurrentSession();
		}
		catch (HibernateException e)
		{
			rv = sf.openSession();
		}

		return rv;
	}

	@Override
	public long count()
	{
		return find().size();
	}

	@Override
	public Spitter findOne(Long id)
	{
		return session().get(Spitter.class, id);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Spitter> find()
	{
		return session().createCriteria(Spitter.class).list();
	}

	@Override
	public Spitter save(Spitter spitter)
	{
		Objects.requireNonNull(spitter);

		Serializable id = session().save(spitter);

		return Spitter.copyFrom((Long) id, spitter);
	}

	@Override
	public Spitter findByUsername(String username)
	{
		Criterion usernameFilter = Restrictions.eq("username", username);

		Spitter rv = (Spitter) session()
			.createCriteria(Spitter.class)
			.add(usernameFilter)
			.list()
			.get(0);

		return rv;
	}

}
