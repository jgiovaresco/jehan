package com.github.jehan.rest.resources;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.jehan.model.Instance;
import com.github.jehan.services.InstanceService;

/**
 * Defines the resources used to get Jenkins' instances servers.
 */
@Path("/Instances")
@Produces(MediaType.APPLICATION_JSON)
@Service
public class Instances
{
   // ------------------------- private constants -------------------------

   /** The name of the parameter used to filter Jenkins instances. */
   private static final String FILTER_PARAMETER = "filter";

   /** The value of the filter parameter used to get only the instance with jobs KO. */
   private static final String JOBS_KO_FILTER_PARAMETER = "jobsKO";


   // --- logs

   /** The logger. */
   private static final Logger LOGGER = LoggerFactory.getLogger(Instances.class);

   // ------------------------- private members -------------------------

   /** */
   @Inject
   private InstanceService m_instanceService;

   /** Provides URI information. */
   @Context
   private UriInfo m_UriInfo;

   // ------------------------- constructor -------------------------

   // ------------------------- public methods -------------------------

   /**
    * Find all Jenkins server instances.
    * @return All Jenkins server instances.
    */
   @GET
   public Collection<Instance> getAll()
   {
      Collection<Instance> instances;

      LOGGER.debug("getAll instances");

      MultivaluedMap<String, String> params = m_UriInfo.getQueryParameters();
      if (null == params || params.isEmpty())
      {
         instances = m_instanceService.findAll();
      }
      else
      {
         String value = params.getFirst(FILTER_PARAMETER);
         if (null != value && JOBS_KO_FILTER_PARAMETER.equals(value))
         {
            instances = m_instanceService.findAllWithJobsKo();
         }
         else
         {
            throw new BadRequestException();
         }
      }

      return instances;
   }

   // ------------------------- private methods -------------------------
}
