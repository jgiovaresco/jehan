package com.github.jehan.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.stereotype.Component;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

/**
 * Provider used to create a {@link ObjectMapper} confirgured.
 */
@Provider
@Component
public class JehanObjectMapperProvider implements ContextResolver<ObjectMapper>
{
	// ------------------------- private members -------------------------

	/** The {@link ObjectMapper} to use. */
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
	 *
	 * @see javax.ws.rs.ext.ContextResolver#getContext(Class)
	 */
	public ObjectMapper getContext(Class<?> p_Type)
	{
		return m_DefaultObjectMapper;
	}

	// ------------------------- private methods -------------------------

	/**
	 * Build and configure the {@link ObjectMapper} to use.
	 *
	 * @return The {@link ObjectMapper} to use.
	 */
	private static ObjectMapper createDefaultMapper()
	{
		ObjectMapper result = new ObjectMapper();
		result.enable(SerializationFeature.INDENT_OUTPUT);
		result.setSerializationInclusion(JsonInclude.Include.NON_NULL);

		return result;
	}
}
