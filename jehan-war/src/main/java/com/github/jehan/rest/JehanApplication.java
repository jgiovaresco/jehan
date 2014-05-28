package com.github.jehan.rest;

import org.glassfish.jersey.filter.LoggingFilter;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines Jersey Application.
 */
public class JehanApplication extends ResourceConfig
{
   // ------------------------- private constants -------------------------

   /** The logger. */
   private static final Logger LOGGER = LoggerFactory.getLogger(JehanApplication.class);

   // ------------------------- constructor -------------------------

   /**
    * Constructeur.
    */
   public JehanApplication()
   {
      LOGGER.info("Initialisation of Jersey application.");

      if (true == LOGGER.isDebugEnabled())
      {
         register(new LoggingFilter(java.util.logging.Logger.getLogger("rest_request"), false));
      }
      register(RequestContextFilter.class);
      packages("com.github.jehan.rest.resources");

      // pour Jackson
      register(JehanObjectMapperProvider.class);
      register(JacksonFeature.class);
   }
}
