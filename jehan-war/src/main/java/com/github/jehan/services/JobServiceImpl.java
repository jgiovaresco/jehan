package com.github.jehan.services;

import java.util.Collection;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.jehan.model.Instance;
import com.github.jehan.model.Job;
import com.github.jehan.model.JobCollection;
import com.github.jehan.rest.JehanObjectMapperProvider;

/**
 * Implementation of {@link com.github.jehan.services.JobService}
 */
@Service
public class JobServiceImpl implements JobService
{
   // ------------------------- private constants -------------------------

   /** The logger. */
   private static final Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);

   // ------------------------- private members-------------------------

   /** The jersey client. */
   private Client m_client;

   // ------------------------- constructor -------------------------

   /**
    * Constructor
    */
   public JobServiceImpl()
   {
      m_client = ClientBuilder.newClient(new ClientConfig().register(JacksonFeature.class)).register(JehanObjectMapperProvider.class);
   }

   // ------------------------- public methods -------------------------

   /**
    * {@inheritDoc}
    * @see com.github.jehan.services.JobService#findAll(com.github.jehan.model.Instance)
    */
   @Override
   public Collection<Job> findAll(Instance p_instance)
   {
      LOGGER.debug("start of findAll() with {}", p_instance);

      WebTarget target = m_client.target(p_instance.getUrl()).path("/api/json");

      JobCollection jobs =
         target.queryParam("tree", "jobs[name,url,color]").request(MediaType.APPLICATION_JSON_TYPE).get(JobCollection.class);

      LOGGER.debug("end of findAll({}) : {}", p_instance, jobs);
      return jobs.getJobs();
   }
}
