package com.github.jehan.services;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;

import org.glassfish.jersey.apache.connector.ApacheConnectorProvider;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;
import org.glassfish.jersey.jackson.JacksonFeature;

import com.github.jehan.model.Instance;
import com.github.jehan.rest.JehanObjectMapperProvider;

/**
 * The Jersey client factory.
 */
public final class JerseyClientFactory
{
   // ------------------------- public methods -------------------------

   /**
    * Creates a Jersey client to connect to a Jenkins instance.
    * @param p_instance The Jenkins instance.
    * @return {@link javax.ws.rs.client.Client} configured.
    */
   public static Client create(Instance p_instance)
   {
      ClientConfig clientConfig = new ClientConfig();
      clientConfig.property(ClientProperties.READ_TIMEOUT, 2000);
      clientConfig.property(ClientProperties.CONNECT_TIMEOUT, 2000);
      clientConfig.connectorProvider(new ApacheConnectorProvider());

      if (null != p_instance.getProxyUrl())
      {
         clientConfig.property(ClientProperties.PROXY_URI, p_instance.getProxyUrl());
      }

      Client client = ClientBuilder.newClient(clientConfig);
      client.register(JacksonFeature.class).register(JehanObjectMapperProvider.class);

      if (true == p_instance.isSecure())
      {
         client.register(HttpAuthenticationFeature.basic(p_instance.getLogin(), p_instance.getToken()));
      }
      return client;
   }

}
