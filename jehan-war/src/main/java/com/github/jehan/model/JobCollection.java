package com.github.jehan.model;

import java.io.Serializable;
import java.util.Collection;

import com.google.common.base.Objects;

/**
 * Defines a Jenkins job.
 */
public class JobCollection implements Serializable
{
	// ------------------------- private constants -------------------------

	// ------------------------- private members-------------------------

	/** The jobs. */
	private Collection<Job> m_jobs;

	// ------------------------- public methods -------------------------

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this).add("jobs", m_jobs).toString();
	}

	/**
	 * Returns the jobs.
	 *
	 * @return The jobs.
	 */
	public Collection<Job> getJobs()
	{
		return m_jobs;
	}

	/**
	 * Sets the jobs.
	 *
	 * @param p_jobs The jobs.
	 */
	public void setJobs(Collection<Job> p_jobs)
	{
		m_jobs = p_jobs;
	}
}
