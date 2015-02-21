package com.github.jehan.services;

import com.github.jehan.dao.JobDao;
import com.github.jehan.exception.FilterException;
import com.github.jehan.model.Instance;
import com.github.jehan.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Implementation of {@link com.github.jehan.services.JobService}
 */
@Service
public class JobServiceImpl implements JobService
{
	// ------------------------- private constants -------------------------

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

	private enum ACCEPTED_FILTERS
	{
		name, color
	}

	// ------------------------- private members-------------------------

	/** DAO handling {@link Job}. */
	@Autowired
	private JobDao m_jobDao;

	// ------------------------- public methods -------------------------

	/**
	 * {@inheritDoc}
	 *
	 * @see com.github.jehan.services.JobService#findAll(com.github.jehan.model.Instance)
	 */
	@Override
	public Collection<Job> findAll(Instance p_instance)
	{
		LOGGER.debug("start of findAll() with {}", p_instance);
		Collection<Job> result = m_jobDao.findAll(p_instance);
		LOGGER.debug("end of findAll({}) : {}", p_instance, result);
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.github.jehan.services.JobService#findJobsWithFilter(com.github.jehan.model.Instance, String, javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	public Collection<Job> findJobsWithFilter(Instance p_instance, String p_filter, MultivaluedMap<String, String> p_filterValues)
	{
		Collection<Job> result;
		ACCEPTED_FILTERS filter;
		Iterator<Job> it;

		LOGGER.debug("start of findJobsWithFilter() with {} {} {}", p_instance, p_filter, p_filterValues);
		try
		{
			filter = ACCEPTED_FILTERS.valueOf(p_filter);
		}
		catch (IllegalArgumentException e)
		{
			throw new FilterException("Unacceptable filter's name");
		}

		switch (filter)
		{
			case name:
				List<String> names = p_filterValues.get("name");
				if (names.isEmpty())
				{
					throw new FilterException("Unknown filter's value");
				}

				result = m_jobDao.findAll(p_instance);
				it = result.iterator();
				while (it.hasNext())
				{
					Job courant = it.next();
					if (!names.contains(courant.getName()))
					{
						it.remove();
					}
				}
				break;

			case color:
				List<String> colors = p_filterValues.get("color");
				if (colors.isEmpty())
				{
					throw new FilterException("Unknown filter's value");
				}

				result = m_jobDao.findAll(p_instance);
				it = result.iterator();
				while (it.hasNext())
				{
					Job courant = it.next();
					if (!colors.contains(courant.getColor()))
					{
						it.remove();
					}
				}
				break;

			default:
				throw new FilterException("Unacceptable filter's name");
		}
		LOGGER.debug("end of findJobsWithFilter({}) : {}", p_instance, result);
		return result;
	}
}
