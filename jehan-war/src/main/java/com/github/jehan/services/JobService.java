package com.github.jehan.services;

import com.github.jehan.model.Instance;
import com.github.jehan.model.Job;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Collection;

/**
 * Defines operations on Jobs.
 */
public interface JobService
{
	/**
	 * Retrieves all jobs of a Jenkins instance.
	 *
	 * @param p_instance The Jenkins instance.
	 * @return The jobs of the Jenkins instance.
	 */
	Collection<Job> findAll(Instance p_instance);

	/**
	 * Retrieves jobs of a Jenkins instance matching the filter.
	 *
	 * @param p_instance     The Jenkins instance.
	 * @param p_filter       The filter name. <p>Accepted values : <code>name,color</code></p>
	 * @param p_filterValues The filter values.
	 * @return The jobs of the Jenkins instance.
	 */
	Collection<Job> findJobsWithFilter(Instance p_instance, String p_filter, MultivaluedMap<String, String> p_filterValues);
}
