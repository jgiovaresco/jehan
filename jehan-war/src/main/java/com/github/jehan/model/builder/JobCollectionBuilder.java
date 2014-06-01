package com.github.jehan.model.builder;

import java.util.ArrayList;
import java.util.List;

import com.github.jehan.model.Job;
import com.github.jehan.model.JobCollection;

/**
 * The builder for {@link com.github.jehan.model.JobCollection}
 */
public class JobCollectionBuilder
{
   // ------------------------- private members -------------------------

   /** */
   private JobCollection m_jobCollection;

   // ------------------------- constructor -------------------------

   /**
    * Constructor.
    */
   private JobCollectionBuilder()
   {
      m_jobCollection = new JobCollection();
   }

   // ------------------------- Méthodes public -------------------------

   public static JobCollectionBuilder create()
   {
      return new JobCollectionBuilder();
   }

   public JobCollection get()
   {
      return m_jobCollection;
   }

   public JobCollectionBuilder addJob(Job p_job)
   {
      if (null == m_jobCollection.getJobs())
      {
         m_jobCollection.setJobs(new ArrayList<Job>());
      }
      m_jobCollection.getJobs().add(p_job);
      return this;
   }

   // ------------------------- Méthodes private -------------------------
}
