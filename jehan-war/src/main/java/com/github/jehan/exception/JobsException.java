package com.github.jehan.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

/**
 * Exception on Jobs operation.
 */
public class JobsException extends WebApplicationException
{
	// ------------------------- Membres private -------------------------
	// ------------------------- Constructeur -------------------------
	// ------------------------- Méthodes public -------------------------

	/**
	 * Constructor
	 *
	 * @param p_Message The message.
	 */
	public JobsException(String p_Message)
	{
		super(Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(p_Message).type("text/plain").build());
	}


	// ------------------------- Méthodes private -------------------------
}
