package com.github.jehan.config;


import java.io.File;

/**
 * Defines the component responsible for locating the configuration file.
 */
public interface ConfigurationLocator
{
   /**
    * Locates the configuration file.
    * @return The configuration file found.
    */
   File locateConfigurationFile();
}
