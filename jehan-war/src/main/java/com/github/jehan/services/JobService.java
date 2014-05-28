package com.github.jehan.services;

import java.util.Collection;

import com.github.jehan.model.Instance;
import com.github.jehan.model.Job;

/**
 * Defines operations on Jobs.
 */
public interface JobService
{
   /**
    * Retrieves all jobs of a Jenkins instance.
    * @param p_instance The Jenkins instance.
    * @return The jobs of the Jenkins instance.
    */
   Collection<Job> findAll(Instance p_instance);
}
