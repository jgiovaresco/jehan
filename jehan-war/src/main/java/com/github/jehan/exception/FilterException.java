package com.github.jehan.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Exception with filter use.
 */
public class FilterException extends WebApplicationException
{
	// ------------------------- constructors -------------------------

	/**
	 * Constructor
	 *
	 * @param p_Message The message.
	 */
	public FilterException(String p_Message)
	{
		super(Response.status(Response.Status.BAD_REQUEST).entity(p_Message).type("text/plain").build());
	}

}
