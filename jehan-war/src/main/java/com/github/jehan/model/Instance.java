package com.github.jehan.model;

import java.io.Serializable;
import java.util.Objects;

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

   // -- credentials

   /** The flag to know if the Jenkins server needs credentials. */
   private boolean m_secure;

   /** The login. */
   private String m_login;

   /** The token api. */
   private String m_token;

   // -- proxy

   /** The proxy url for the connection to the instance. */
   private String m_proxyUrl;

   // ------------------------- public methods -------------------------

   @Override
   public boolean equals(Object p_o)
   {
      boolean equals = false;
      if (null != p_o && getClass() == p_o.getClass())
      {
         final Instance other = (Instance) p_o;

         equals = Objects.equals(m_name, other.m_name) && Objects.equals(m_url, other.m_url) &&
                 Objects.equals(m_version, other.m_version) && Objects.equals(m_secure, other.m_secure) &&
                 Objects.equals(m_login, other.m_login) && Objects.equals(m_token, other.m_token) &&
                 Objects.equals(m_proxyUrl, other.m_proxyUrl);
      }
      return equals;
   }

   @Override
   public int hashCode()
   {
      return Objects.hash(m_name, m_url, m_version, m_secure, m_login, m_token, m_proxyUrl);
   }

   @Override
   public String toString()
   {
      return com.google.common.base.Objects.toStringHelper(this).omitNullValues().add("name", m_name).add("url", m_url)
              .add("version", m_version).add("secure", m_secure).add("login", m_login).add("tokenApi", m_token).add("proxyUrl", m_proxyUrl)
              .toString();
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

   /**
    * Returns the secure flag.
    * @return The secure flag.
    */
   public boolean isSecure()
   {
      return m_secure;
   }

   /**
    * Sets the secure flag.
    * @param p_secure The secure flag.
    */
   public void setSecure(boolean p_secure)
   {
      m_secure = p_secure;
   }

   /**
    * Returns the login.
    * @return The login.
    */
   public String getLogin()
   {
      return m_login;
   }

   /**
    * Sets the login.
    * @param p_login The login.
    */
   public void setLogin(String p_login)
   {
      m_login = p_login;
   }

   /**
    * Returns the token.
    * @return The token.
    */
   public String getToken()
   {
      return m_token;
   }

   /**
    * Sets the token.
    * @param p_token The token.
    */
   public void setToken(String p_token)
   {
      m_token = p_token;
   }

   /**
    * Returns the proxy url.
    * @return The proxy url.
    */
   public String getProxyUrl()
   {
      return m_proxyUrl;
   }

   /**
    * Sets the proxy url.
    * @param p_proxyUrl The proxy url.
    */
   public void setProxyUrl(String p_proxyUrl)
   {
      m_proxyUrl = p_proxyUrl;
   }

   // ------------------------- private methods -------------------------
}
