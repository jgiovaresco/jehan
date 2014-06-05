package com.github.jehan.config;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.RestoreSystemProperties;

/**
 * Test class for {@link com.github.jehan.config.DefaultConfigurationLocator}
 */
public class DefaultConfigurationLocatorImplTest
{
   private static final String DIR_SYS_PROP = "/tmp/sysprop/jehan.json";
   private static final String DIR_HOME_DIR = "/tmp/homedir";

   @Rule
   public final RestoreSystemProperties restoreSystemProperties = new RestoreSystemProperties("JEHAN.DIR", "user.home");

   // ------------------------- private members -------------------------

   /** The locator to test. */
   private DefaultConfigurationLocator m_locator;

   // ------------------------- public methods -------------------------

   @Before
   public void setUp() throws Exception
   {
      FileUtils.copyURLToFile(getClass().getClassLoader().getResource("jehan_sysprop.json"), new File(DIR_SYS_PROP));
      FileUtils.copyURLToFile(getClass().getClassLoader().getResource("jehan_homedir.json"), new File(DIR_HOME_DIR + "/.jehan/jehan.json"));
      m_locator = new DefaultConfigurationLocator();
   }

   @After
   public void tearDown() throws Exception
   {
      FileUtils.deleteQuietly(new File(DIR_SYS_PROP));
      FileUtils.deleteQuietly(new File(DIR_HOME_DIR));
   }

   /**
    * Test of the method {@link DefaultConfigurationLocator#locateConfigurationFile()}
    * <p>
    * Use the system property.
    * </p>
    */
   @Test
   public void testLocateConfigurationFile_FromSystemProperty() throws Exception
   {
      System.setProperty("JEHAN.DIR", DIR_SYS_PROP);
      assertThat(FileUtils.readFileToString(m_locator.locateConfigurationFile())).isEqualTo("{\"sysprop\":\"sysprop\"}\n");
   }

   /**
    * Test of the method {@link DefaultConfigurationLocator#locateConfigurationFile()}
    * <p>
    * Use the home directory.
    * </p>
    */
   @Test
   public void testLocateConfigurationFile_FromHomeDir() throws Exception
   {
      System.setProperty("user.home", DIR_HOME_DIR);
      assertThat(FileUtils.readFileToString(m_locator.locateConfigurationFile())).isEqualTo("{\"homedir\":\"homedir\"}\n");
   }

   /**
    * Test of the method {@link DefaultConfigurationLocator#locateConfigurationFile()}
    * <p>
    * Use the default behavior.
    * </p>
    */
   @Test
   public void testLocateConfigurationFile_Default() throws Exception
   {
      System.setProperty("user.home", "");
      assertThat(FileUtils.readFileToString(m_locator.locateConfigurationFile())).isEqualTo("{\"defaut\":\"defaut\"}");
   }
}