package com.github.jehan.rest.resources;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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

   /** The logger. */
   private static final Logger LOGGER = LoggerFactory.getLogger(Instances.class);

   // ------------------------- private members -------------------------

   /** */
   @Inject
   private InstanceService m_instanceService;

   // ------------------------- constructor -------------------------

   // ------------------------- public methods -------------------------

   /**
    * Find all Jenkins server instances.
    * @return All Jenkins server instances.
    */
   @GET
   public Collection<Instance> getAll()
   {
      LOGGER.debug("getAll instances");
      return m_instanceService.findAll();
   }

   // ------------------------- private methods -------------------------
}
