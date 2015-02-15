package com.github.jehan.config;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Defines default locator use to retrieve the configuration file using many strategies
 * <ul>
 * <li>Checks the existence of a system property</li>
 * <li>Checks the existence of an environment variable</li>
 * <li>Checks the existence of directory in the home directory of the user</li>
 * </ul>
 */
@Service
public class DefaultConfigurationLocator implements ConfigurationLocator
{
	// ------------------------- private constantes -------------------------

	private static final String JEHAN_CONFIGURATION_FILE = "jehan.json";
	private static final String ENV_VARIABLE = "JEHAN.DIR";
	private static final String SYSTEM_VARIABLE = ENV_VARIABLE;
	private static final String DIRECTORY_IN_HOME_DIR = "/.jehan";

	// ------------------------- private members -------------------------

	/** Strategies used to find the configuration file. */
	private List<ConfigurationLocatorStrategy> m_strategies = Collections.synchronizedList(new ArrayList<ConfigurationLocatorStrategy>());

	// ------------------------- constructors -------------------------

	/**
	 * Default constructor.
	 */
	public DefaultConfigurationLocator()
	{
		m_strategies.add(new SystemPropertyStrategy());
		m_strategies.add(new EnvironmentVariableStrategy());
		m_strategies.add(new HomeDirectoryStrategy());
		m_strategies.add(new DefaultStrategy());
	}

	// ------------------------- public methods -------------------------

	/**
	 * {@inheritDoc}
	 *
	 * @see ConfigurationLocator#locateConfigurationFile()
	 */
	@Override
	public File locateConfigurationFile()
	{
		File configFile = null;
		Iterator<ConfigurationLocatorStrategy> it = m_strategies.iterator();

		while (null == configFile && it.hasNext())
		{
			configFile = it.next().locateConfigurationFile();
		}
		return configFile;
	}

	// ------------------------- inner class -------------------------

	class SystemPropertyStrategy implements ConfigurationLocatorStrategy
	{
		public File locateConfigurationFile()
		{
			String propValue = System.getProperty(SYSTEM_VARIABLE);
			File configFile = null == propValue ? null : new File(propValue);
			return (null != configFile && configFile.exists()) ? configFile : null;
		}
	}

	class EnvironmentVariableStrategy implements ConfigurationLocatorStrategy
	{
		public File locateConfigurationFile()
		{
			String propValue = System.getenv(ENV_VARIABLE);
			File configFile = null == propValue ? null : new File(propValue);
			return (null != configFile && configFile.exists()) ? configFile : null;
		}
	}

	class HomeDirectoryStrategy implements ConfigurationLocatorStrategy
	{
		public File locateConfigurationFile()
		{
			String dir = System.getProperty("user.home").replace(File.separatorChar, '/') + DIRECTORY_IN_HOME_DIR;
			File configFile = new File(dir + '/' + JEHAN_CONFIGURATION_FILE);
			return configFile.exists() ? configFile : null;
		}
	}

	class DefaultStrategy implements ConfigurationLocatorStrategy
	{
		public File locateConfigurationFile()
		{
			URL url = getClass().getClassLoader().getResource(JEHAN_CONFIGURATION_FILE);
			File configFile = null;

			if (null != url)
			{
				configFile = new File(url.getFile());
			}

			return (null != configFile && configFile.exists()) ? configFile : null;
		}
	}
}
