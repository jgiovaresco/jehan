package com.github.jehan.dao;

import com.github.jehan.config.ConfigurationLocator;
import com.github.jehan.model.Instance;
import com.github.jehan.model.builder.InstanceBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigParseOptions;
import com.typesafe.config.ConfigSyntax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.*;

/**
 * Implements {@link com.github.jehan.dao.InstanceDao}
 */
@Repository
public class InstanceDaoImpl implements InstanceDao
{
	// ------------------------- private constants -------------------------

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(InstanceDaoImpl.class);

	// ------------------------- private members -------------------------

	/** */
	@Inject
	private ConfigurationLocator m_configurationLocator;

	/** The instances configured. */
	private Map<String, Instance> m_instances = new TreeMap<>();

	// ------------------------- public methods -------------------------

	/**
	 * Initialize the bean.
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
			InstanceBuilder builder = InstanceBuilder.create().withName(name).withUrl(url);

			if (co.hasPath("credentials"))
			{
				Config credentials = co.getConfig("credentials");
				builder.withSecureActive().withLogin(credentials.getString("login")).withToken(credentials.getString("token"));
			}

			if (co.hasPath("proxy"))
			{
				Config proxy = co.getConfig("proxy");
				builder.withProxyUrl(proxy.getString("url"));
			}
			m_instances.put(name, builder.get());
		}
		LOGGER.info("Instance configured : {}", m_instances);
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see InstanceDao#findAll()
	 */
	@Override
	public Collection<Instance> findAll()
	{
		LOGGER.debug("start of findAll()");

		List<Instance> instances = new ArrayList<>();
		for (Instance i : m_instances.values())
		{
			instances.add(i.clone());
		}

		LOGGER.debug("end of findAll() : {}", instances);
		return instances;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see InstanceDao#findAllByName(String...)
	 */
	@Override
	public Collection<Instance> findAllByName(String... p_names)
	{
		List<Instance> result = new ArrayList<>();

		for (String name : p_names)
		{
			Instance i = findByName(name);
			if (null != i)
			{
				result.add(i);
			}
		}
		return result;
	}

	/**
	 * {@inheritDoc}
	 *
	 * @see InstanceDao#findByName(String)
	 */
	@Override
	public Instance findByName(String p_name)
	{
		LOGGER.debug("start of findByName() with {}", p_name);

		Instance instance = m_instances.get(p_name);

		LOGGER.debug("end of findByName({}) : {}", p_name, instance);
		return instance.clone();
	}
}
