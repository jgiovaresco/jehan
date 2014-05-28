// -----------------------------------------------------------------------------
// Projet : jehan
// Client : Pôle Emploi
// Auteur : giovarej / Bull S.A.S.
// -----------------------------------------------------------------------------
package com.github.jehan.services;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.jehan.model.Instance;
import com.github.jehan.model.builder.InstanceBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigRenderOptions;
import com.typesafe.config.ConfigResolveOptions;
import com.typesafe.config.ConfigSyntax;

/**
 * Implements {@link InstanceService}
 */
@Service
public class InstanceServiceImpl implements InstanceService
{
   // ------------------------- private constants -------------------------

   /** The logger. */
   private static final Logger LOGGER = LoggerFactory.getLogger(InstanceServiceImpl.class);

   // ------------------------- private members -------------------------

   /** The instances configured. */
   private Map<String, Instance> m_instances = new HashMap<>();

   // ------------------------- constructor -------------------------

   /**
    * Constructor.
    */
   public InstanceServiceImpl()
   {
      Config m_jenkinsUrl = ConfigFactory.load("jehan", ConfigParseOptions.defaults().setSyntax(ConfigSyntax.JSON), ConfigResolveOptions.defaults());

      for (Config co : m_jenkinsUrl.getConfigList("instances"))
      {
         String name = co.getString("name");
         String url = co.getString("url");
         m_instances.put(name, InstanceBuilder.create().withName(name).withUrl(url).withVersion("1.564").get());
      }
   }

   // ------------------------- public methods -------------------------

   /**
    * {@inheritDoc}
    * @see InstanceService#findAll()
    */
   @Override
   public Collection<Instance> findAll()
   {
      LOGGER.debug("start of findAll()");

      Collection<Instance> instances = m_instances.values();

      LOGGER.debug("end of findAll() : {}", instances);
      return instances;
   }

   /**
    * {@inheritDoc}
    * @see InstanceService#findById(String)
    */
   @Override
   public Instance findById(String p_id)
   {
      LOGGER.debug("start of findById() with {}", p_id);

      Instance instance = m_instances.get(p_id);

      LOGGER.debug("end of findById({}) : {}", p_id, instance);
      return instance;
   }
}
