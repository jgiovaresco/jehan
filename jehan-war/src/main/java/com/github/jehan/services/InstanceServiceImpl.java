package com.github.jehan.services;

import com.github.jehan.dao.InstanceDao;
import com.github.jehan.exception.FilterException;
import com.github.jehan.model.Instance;
import com.github.jehan.model.Job;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.MultivaluedMap;
import java.util.*;

/**
 * Implements {@link InstanceService}
 */
@Service
public class InstanceServiceImpl implements InstanceService
{
	// ------------------------- private constants -------------------------

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InstanceServiceImpl.class);

	/** Accepted filters. */
	private enum ACCEPTED_FILTERS
	{
		name, jobs
	}

	// ------------------------- private members -------------------------

	/** DAO handling {@link com.github.jehan.model.Instance}. */
	@Autowired
	private InstanceDao m_instanceDao;

	/** The job service. */
	@Autowired
	private JobService m_jobService;

	// ------------------------- public methods -------------------------

	/**
	 * {@inheritDoc}
	 *
	 * @see InstanceService#findAll()
	 */
	@Override
	public Collection<Instance> findAll()
	{
		LOGGER.debug("start of findAll()");

		Collection<Instance> instances = m_instanceDao.findAll();

		for (Instance i : instances)
		{
			i.getJobs().addAll(m_jobService.findAll(i));
		}
		LOGGER.debug("end of findAll() : {}", instances);
		return instances;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.github.jehan.services.InstanceService#findInstancesWithFilter(String, javax.ws.rs.core.MultivaluedMap)
	 */
	@Override
	public Collection<Instance> findInstancesWithFilter(String p_filter, MultivaluedMap<String, String> p_filterValues)
	{
		Collection<Instance> result;
		ACCEPTED_FILTERS filter;

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
				result = findAllByName(names.toArray(new String[names.size()]));
				for (Instance instance : result)
				{
					instance.getJobs().addAll(m_jobService.findAll(instance));
				}
				break;

			case jobs:
				result = new ArrayList<>();

				String jobAttribute;
				Set<String> keys = p_filterValues.keySet();
				if (keys.isEmpty())
				{
					throw new FilterException("Unknown filter's value");
				}

				jobAttribute = keys.iterator().next();
				for (Instance instance : m_instanceDao.findAll())
				{
					Collection<Job> jobs = m_jobService.findJobsWithFilter(instance, jobAttribute, p_filterValues);
					if (!jobs.isEmpty())
					{
						instance.getJobs().addAll(jobs);
						result.add(instance);
					}
				}
				break;

			default:
				throw new FilterException("Unacceptable filter's name");
		}

		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see com.github.jehan.services.InstanceService#findAllWithJobsKo()
	 */
	@Override
	public Collection<Instance> findAllWithJobsKo()
	{
		List<Instance> instancesWithJobsKo = new ArrayList<>();

		for (Instance instance : m_instanceDao.findAll())
		{
			Collection<Job> jobs = m_jobService.findAll(instance);
			Iterator<Job> jobIterator = jobs.iterator();
			boolean jobsKo = false;

			while (!jobsKo && jobIterator.hasNext())
			{
				jobsKo = jobIterator.next().isLastBuildFailed();
			}
			if (jobsKo)
			{
				instancesWithJobsKo.add(instance);
			}
		}

		return instancesWithJobsKo;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see InstanceService#findAllByName(String...)
	 */
	@Override
	public Collection<Instance> findAllByName(String... p_names)
	{
		LOGGER.debug("start of findAllByName() with {}", p_names);

		Collection<Instance> result = m_instanceDao.findAllByName(p_names);
		LOGGER.debug("end of findAllByName({}) : {}", p_names, result);
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see InstanceService#findByName(String)
	 */
	@Override
	public Instance findByName(String p_name)
	{
		LOGGER.debug("start of findByName() with {}", p_name);

		Instance instance = m_instanceDao.findByName(p_name);

		LOGGER.debug("end of findByName({}) : {}", p_name, instance);
		return instance;
	}
}
