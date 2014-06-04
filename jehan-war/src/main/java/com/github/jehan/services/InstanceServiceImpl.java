// -----------------------------------------------------------------------------
// Projet : jehan
// Client : Pôle Emploi
// Auteur : giovarej / Bull S.A.S.
// -----------------------------------------------------------------------------
package com.github.jehan.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.jehan.config.ConfigurationLocator;
import com.github.jehan.model.Instance;
import com.github.jehan.model.Job;
import com.github.jehan.model.builder.InstanceBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
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

   /** */
   @Inject
   private ConfigurationLocator m_configurationLocator;

   /** The job service. */
   @Autowired
   private JobService m_jobService;

   /** The instances configured. */
   private Map<String, Instance> m_instances = new TreeMap<>();

   // ------------------------- public methods -------------------------

   /**
    * Initialize the service.
    */
   @PostConstruct
   public void initialize()
   {
      Config m_jenkinsUrl = ConfigFactory
              .parseFile(m_configurationLocator.locateConfigurationFile(), ConfigParseOptions.defaults().setSyntax(ConfigSyntax.JSON));

      for (Config co : m_jenkinsUrl.getConfigList("instances"))
      {
         String name = co.getString("name");
         String url = co.getString("url");
         m_instances.put(name, InstanceBuilder.create().withName(name).withUrl(url).withVersion("1.564").get());
      }
   }

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
    * @see com.github.jehan.services.InstanceService#findAllWithJobsKo()
    */
   @Override
   public Collection<Instance> findAllWithJobsKo()
   {
      List<Instance> instancesWithJobsKo = new ArrayList<>();

      for (Instance instance : m_instances.values())
      {
         Collection<Job> jobs = m_jobService.findAll(instance);
         Iterator<Job> jobIterator = jobs.iterator();
         boolean jobsKo = false;

         while (!jobsKo && jobIterator.hasNext())
         {
            jobsKo = jobIterator.next().isLastBuildFailed();
         }

         if (jobsKo)
         {
            instancesWithJobsKo.add(instance);
         }
      }

      return instancesWithJobsKo;
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
