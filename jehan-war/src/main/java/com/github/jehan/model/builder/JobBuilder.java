package com.github.jehan.model.builder;

import com.github.jehan.model.Job;

/**
 * The builder for {@link com.github.jehan.model.Job}
 */
public class JobBuilder
{
   // ------------------------- private members -------------------------

   /** */
   private Job m_job;

   // ------------------------- constructor -------------------------

   /**
    * Constructor.
    */
   private JobBuilder()
   {
      m_job = new Job();
   }

   // ------------------------- Méthodes public -------------------------

   public static JobBuilder create()
   {
      return new JobBuilder();
   }

   public Job get()
   {
      return m_job;
   }

   public JobBuilder withName(String p_name)
   {
      m_job.setName(p_name);
      return this;
   }

   public JobBuilder withUrl(String p_url)
   {
      m_job.setUrl(p_url);
      return this;
   }

   public JobBuilder withColor(String p_color)
   {
      m_job.setColor(p_color);
      return this;
   }

   // ------------------------- Méthodes private -------------------------
}
