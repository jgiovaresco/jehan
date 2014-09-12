package com.github.jehan.rest.resources;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.jehan.model.Job;
import com.github.jehan.rest.CacheControl;
import com.github.jehan.services.InstanceService;
import com.github.jehan.services.JobService;

/**
 * Defines the resources used to get Jobs.
 */
@Path("/Jobs")
@Produces(MediaType.APPLICATION_JSON)
@Service
public class Jobs
{
   // ------------------------- private constants -------------------------

   /** The logger. */
   private static final Logger LOGGER = LoggerFactory.getLogger(Jobs.class);

   // ------------------------- private members -------------------------

   /** */
   @Inject
   private InstanceService m_instanceService;

   /** */
   @Inject
   private JobService m_jobService;

   // ------------------------- constructor -------------------------

   // ------------------------- public methods -------------------------

   /**
    * Find all Jenkins server instances.
    * @param p_instanceId The instance id.
    * @return All Jenkins server instances.
    */
   @GET
   @Path("/{instanceId}")
   @CacheControl("no-cache")
   public Collection<Job> getAll(@PathParam("instanceId") String p_instanceId)
   {
      LOGGER.debug("getAll jobs of {}", p_instanceId);
      return m_jobService.findAll(m_instanceService.findById(p_instanceId));
   }

   // ------------------------- private methods -------------------------
}
