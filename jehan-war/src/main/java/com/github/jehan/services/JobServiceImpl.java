package com.github.jehan.services;

import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_PASSWORD;
import static org.glassfish.jersey.client.authentication.HttpAuthenticationFeature.HTTP_AUTHENTICATION_BASIC_USERNAME;

import java.io.IOException;
import java.util.Collection;

import javax.ws.rs.ProcessingException;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ContextResolver;

import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

   /** The resolver used to find an {@link org.codehaus.jackson.map.ObjectMapper}. */
   @Autowired
   private ContextResolver<ObjectMapper> m_contextResolver;

   /** The jersey client. */
   private Client m_client;

   /** The jersey client. */
   private Client m_securedClient;

   // ------------------------- constructor -------------------------

   /**
    * Constructor
    */
   public JobServiceImpl()
   {
      m_client = ClientBuilder.newClient(new ClientConfig().register(JacksonFeature.class)).register(JehanObjectMapperProvider.class);
      m_securedClient = ClientBuilder.newClient(new ClientConfig().register(JacksonFeature.class))
              .register(JehanObjectMapperProvider.class);
      m_securedClient.register(HttpAuthenticationFeature.basicBuilder().build());
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
      JobCollection jobs;
      Response response;
      Invocation.Builder request;

      request = getClient(p_instance).target(p_instance.getUrl()).path("/api/json").queryParam("tree", "jobs[name,url,color]")
              .request(MediaType.APPLICATION_JSON_TYPE);

      if (p_instance.isSecure())
      {
         request.property(HTTP_AUTHENTICATION_BASIC_USERNAME, p_instance.getLogin())
                 .property(HTTP_AUTHENTICATION_BASIC_PASSWORD, p_instance.getToken());
      }

      response = request.get();
      jobs = deserializeEntity(response);

      LOGGER.debug("end of findAll({}) : {}", p_instance, jobs);
      return jobs.getJobs();
   }

   // ------------------------- private methods -------------------------

   /**
    * Returns the client to use for the instance passed in parameter.
    * @param p_instance The instance.
    * @return The client to use.
    */
   private Client getClient(Instance p_instance)
   {
      Client client = m_client;
      if (p_instance.isSecure())
      {
         client = m_securedClient;
      }
      return client;
   }

   /**
    * Deserialize the entity in the response.
    * <ul>
    * <li>If the response contains a correct Content-Type header, we use the Jersey api to deserialize the entity</li>
    * <li>If the response contains an incorrect Content-Type header (old Jenkins version), we use Jackson directly to deserialize the entity</li>
    * </ul>
    * @param p_response The response containing the entity.
    * @return {@link com.github.jehan.model.JobCollection}
    */
   private JobCollection deserializeEntity(Response p_response)
   {
      JobCollection jobs;

      String contentType = p_response.getHeaderString("Content-Type");
      if (null != contentType && contentType.contains("application/json"))
      {
         jobs = p_response.readEntity(JobCollection.class);
      }
      else if (null != contentType && contentType.contains("application/javascript"))
      {
         //
         String json = null;
         try
         {
            json = p_response.readEntity(String.class);
            jobs = m_contextResolver.getContext(JobCollection.class).readValue(json, JobCollection.class);
         }
         catch (IOException e)
         {
            LOGGER.error("Error while deserialisation of the entity {} : {}", json, e);
            throw new ProcessingException(e);
         }
      }
      else
      {
         jobs = null;
         LOGGER.error("Impossible to deserialize entity : the Content-Type is incorrect");
      }
      return jobs;
   }
}
