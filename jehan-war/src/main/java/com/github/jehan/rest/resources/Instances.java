package com.github.jehan.rest.resources;

import com.github.jehan.model.Instance;
import com.github.jehan.services.InstanceService;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import java.util.Collection;

/**
 * Defines the resources used to get Jenkins' instances servers.
 */
@Path("/Instances")
@Produces(MediaType.APPLICATION_JSON)
@Service
public class Instances
{
	// ------------------------- private constants -------------------------

	/** The name of the parameter used to filter Jenkins instances. */
	private static final String FILTER_PARAMETER = "filter";

	// --- logs

	/** The logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(Instances.class);

	// ------------------------- private members -------------------------

	/** */
	@Inject
	private InstanceService m_instanceService;

	/** Provides URI information. */
	@Context
	private UriInfo m_uriInfo;

	// ------------------------- constructors -------------------------

	// ------------------------- public methods -------------------------

	/**
	 * Find all Jenkins server instances.
	 * <p>Query string allowed :</p>
	 * <ul>
	 * <li>filter : used to select an attribute of {@link com.github.jehan.model.Instance} to filter instances. <br />
	 * Values accepted : <code>name, jobs</code>
	 * </li>
	 * <li>The filter's value :
	 * <ul>
	 * <li>for simple attribute, the parameter's name is the attribute itself and the parameter's value is the value used to filter instances. <br />
	 * Ex: to filter on instance' name : <code>name=my_name</code></li>
	 * <li>for object attribute, the parameter's name is an attribute of the object and the parameter's value is the value used to filter instances. <br />
	 * Ex: to filter on job's status : <code>color=red&color=yellow</code></li>
	 * </ul>
	 * </li>
	 * </ul>
	 *
	 * @return All Jenkins server instances.
	 */
	@GET
	public Collection<Instance> findAll()
	{
		Collection<Instance> instances;

		LOGGER.debug("findAll instances");

		MultivaluedMap<String, String> params = new MultivaluedStringMap(m_uriInfo.getQueryParameters());
		if (params.isEmpty())
		{
			instances = m_instanceService.findAll();
		}
		else
		{
			String filter = params.getFirst(FILTER_PARAMETER);
			if (null != filter)
			{
				params.remove(FILTER_PARAMETER);
				if (!params.isEmpty())
				{
					instances = m_instanceService.findInstancesWithFilter(filter, params);
				}
				else
				{
					throw new BadRequestException();
				}
			}
			else
			{
				throw new BadRequestException();
			}
		}

		return instances;
	}

	// ------------------------- private methods -------------------------
}
