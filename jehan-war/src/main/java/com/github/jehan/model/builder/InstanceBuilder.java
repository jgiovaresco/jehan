package com.github.jehan.model.builder;

import com.github.jehan.model.Instance;

/**
 * The builder for {@link com.github.jehan.model.Instance}
 */
public class InstanceBuilder
{
   // ------------------------- private members -------------------------

   /** */
   private Instance m_instance;

   // ------------------------- constructor -------------------------

   /**
    * Constructor.
    */
   private InstanceBuilder()
   {
      m_instance = new Instance();
   }

   // ------------------------- public methods -------------------------

   public static InstanceBuilder create()
   {
      return new InstanceBuilder();
   }

   public Instance get()
   {
      return m_instance;
   }

   public InstanceBuilder withName(String p_name)
   {
      m_instance.setName(p_name);
      return this;
   }

   public InstanceBuilder withUrl(String p_url)
   {
      m_instance.setUrl(p_url);
      return this;
   }

   public InstanceBuilder withVersion(String p_version)
   {
      m_instance.setVersion(p_version);
      return this;
   }

   public InstanceBuilder withSecureActive()
   {
      m_instance.setSecure(true);
      return this;
   }

   public InstanceBuilder withLogin(String p_login)
   {
      m_instance.setLogin(p_login);
      return this;
   }

   public InstanceBuilder withToken(String p_token)
   {
      m_instance.setToken(p_token);
      return this;
   }

   // ------------------------- private methods -------------------------
}
