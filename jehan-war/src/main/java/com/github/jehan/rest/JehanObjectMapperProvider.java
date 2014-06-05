package com.github.jehan.rest;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.springframework.stereotype.Component;

/**
 * Provider used to create a {@link org.codehaus.jackson.map.ObjectMapper} confirgured.
 */
@Provider
@Component
public class JehanObjectMapperProvider implements ContextResolver<ObjectMapper>
{
   // ------------------------- private members -------------------------

   /** The {@link org.codehaus.jackson.map.ObjectMapper} to use. */
   private final ObjectMapper m_DefaultObjectMapper;

   // ------------------------- constructor -------------------------

   /**
    * Constructor.
    */
   public JehanObjectMapperProvider()
   {
      m_DefaultObjectMapper = createDefaultMapper();
   }

   // ------------------------- public methods -------------------------

   /**
    * {@inheritDoc}
    * @see javax.ws.rs.ext.ContextResolver#getContext(Class)
    */
   public ObjectMapper getContext(Class<?> p_Type)
   {
      return m_DefaultObjectMapper;
   }

   // ------------------------- private methods -------------------------

   /**
    * Build and configure the {@link org.codehaus.jackson.map.ObjectMapper} to use.
    * @return The {@link org.codehaus.jackson.map.ObjectMapper} to use.
    */
   private static ObjectMapper createDefaultMapper()
   {
      ObjectMapper result = new ObjectMapper();
      result.configure(Feature.INDENT_OUTPUT, true);
      result.configure(Feature.SORT_PROPERTIES_ALPHABETICALLY, true);
      result.setSerializationInclusion(JsonSerialize.Inclusion.NON_NULL);

      return result;
   }
}
