package com.github.jehan.model;

import java.io.Serializable;

import com.google.common.base.Objects;

/**
 * Defines a Jenkins' server instance.
 */
public class Instance implements Serializable
{
   // ------------------------- private members -------------------------

   /** The name. */
   private String m_name;

   /** The url of the Jenkins server. */
   private String m_url;

   /** The version of the Jenkins server. */
   private String m_version;

   // ------------------------- public methods -------------------------

   @Override
   public String toString()
   {
      return Objects.toStringHelper(this).add("name", m_name).add("url", m_url).add("version", m_version).toString();
   }

   /**
    * Returns the name.
    * @return The name.
    */
   public String getName()
   {
      return m_name;
   }

   /**
    * Sets the name.
    * @param p_name The name.
    */
   public void setName(String p_name)
   {
      m_name = p_name;
   }

   /**
    * Returns the url.
    * @return The url.
    */
   public String getUrl()
   {
      return m_url;
   }

   /**
    * Sets the url.
    * @param p_url The url.
    */
   public void setUrl(String p_url)
   {
      m_url = p_url;
   }

   /**
    * Returns the version.
    * @return The version.
    */
   public String getVersion()
   {
      return m_version;
   }

   /**
    * Sets the version.
    * @param p_version The version.
    */
   public void setVersion(String p_version)
   {
      m_version = p_version;
   }

   // ------------------------- private methods -------------------------
}