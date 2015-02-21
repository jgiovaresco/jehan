package com.github.jehan.dao;

import com.github.jehan.model.Instance;
import com.github.jehan.model.Job;

import java.util.Collection;

/**
 * Interface of DAO handling {@link com.github.jehan.model.Job}
 */
public interface JobDao
{
	/**
	 * Retrieves all jobs of a Jenkins instance.
	 *
	 * @param p_instance The Jenkins instance.
	 * @return The jobs of the Jenkins instance.
	 */
	Collection<Job> findAll(Instance p_instance);
}
