package com.github.jehan.services;

import com.github.jehan.model.Instance;

import java.util.Collection;

/**
 * Defines operations on Jenkins server instances.
 */
public interface InstanceService
{
    /**
     * Retrieves all Jenkins server instances.
     * @return All Jenkins server instances.
     */
    Collection<Instance> findAll();

   /**
    * Retrieves all Jenkins server instances with jobs KO.
    * @return All Jenkins server instances with jobs KO.
    */
   Collection<Instance> findAllWithJobsKo();

   /**
    * Retrieve a Jenkins server instance for its id.
    * @param p_Id The id.
    * @return The {@link com.github.jehan.model.Instance} or null.
    */
   Instance findById(String p_Id);
}
