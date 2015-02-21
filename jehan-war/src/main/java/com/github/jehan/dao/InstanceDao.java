package com.github.jehan.dao;

import com.github.jehan.model.Instance;

import java.util.Collection;

/**
 * Interface of DAO handling {@link com.github.jehan.model.Instance}
 */
public interface InstanceDao
{
	/**
	 * Retrieves all Jenkins server instances.
	 *
	 * @return All Jenkins server instances.
	 */
	Collection<Instance> findAll();

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
