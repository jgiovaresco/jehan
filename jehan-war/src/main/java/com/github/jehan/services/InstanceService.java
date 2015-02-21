package com.github.jehan.services;

import com.github.jehan.model.Instance;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Collection;

/**
 * Defines operations on Jenkins server instances.
 */
public interface InstanceService
{
	/**
	 * Retrieves all Jenkins server instances.
	 *
	 * @return All Jenkins server instances.
	 */
	Collection<Instance> findAll();


	/**
	 * Retrieves Jenkins server instances with a filter.
	 *
	 * @param p_filter       The filter name. <p>Accepted values : <code>name,jobs</code></p>
	 * @param p_filterValues The filter values.
	 * @return Jenkins server instances matching the filter.
	 */
	Collection<Instance> findInstancesWithFilter(String p_filter, MultivaluedMap<String, String> p_filterValues);

	/**
	 * Retrieves all Jenkins server instances with jobs KO.
	 *
	 * @return All Jenkins server instances with jobs KO.
	 */
	Collection<Instance> findAllWithJobsKo();

	/**
	 * Retrieves all Jenkins server instances with theirs names.
	 *
	 * @param p_names The names to find.
	 * @return All Jenkins server instances with theirs names.
	 */
	Collection<Instance> findAllByName(String... p_names);

	/**
	 * Retrieve a Jenkins server instance with its name.
	 *
	 * @param p_name The name.
	 * @return The {@link com.github.jehan.model.Instance} or null.
	 */
	Instance findByName(String p_name);
}
