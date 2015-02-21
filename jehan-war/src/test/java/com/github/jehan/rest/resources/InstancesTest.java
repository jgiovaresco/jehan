package com.github.jehan.rest.resources;

import com.github.jehan.model.Instance;
import com.github.jehan.model.builder.InstanceBuilder;
import com.github.jehan.rest.JehanApplication;
import com.github.jehan.services.InstanceService;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.*;
import java.util.Arrays;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Tests for {@link com.github.jehan.rest.resources.Instances}
 */
public class InstancesTest extends JerseyTest
{
	// ------------------------- private members -------------------------

	/** The mock for {@link com.github.jehan.services.InstanceService}. */
	private static InstanceService s_mockInstanceService = mock(InstanceService.class);

	// ------------------------- public methods -------------------------

	@Override
	public Application configure()
	{
		AbstractBinder binder = new AbstractBinder()
		{
			@Override
			protected void configure()
			{
				bindFactory(MockInstanceServiceFactory.class).to(InstanceService.class);
			}
		};
		ResourceConfig config = new JehanApplication();
		config.register(binder);
		return config;
	}

	/**
	 * Test for {@link Instances#findAll()}
	 * <p>
	 * No query string provided
	 * </p>
	 */
	@Test
	public void testFindAll_WithNoQueryString() throws Exception
	{
		when(s_mockInstanceService.findAll()).thenReturn(Arrays.asList(
				InstanceBuilder.create().withName("instance1").get(),
				InstanceBuilder.create().withName("instance2").get(),
				InstanceBuilder.create().withName("instance3").get()
		));


		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:9998/Instances").request(MediaType.APPLICATION_JSON_TYPE).get();

		Collection<Instance> result = response.readEntity(new GenericType<Collection<Instance>>()
		{
		});

		assertThat(result).hasSize(3).extracting("name").containsExactly("instance1", "instance2", "instance3");
		verify(s_mockInstanceService, times(1)).findAll();

		response.close();
		client.close();
	}

	/**
	 * Test for {@link Instances#findAll()}
	 * <p>
	 * filter provided
	 * </p>
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testFindAll_WithFilter() throws Exception
	{
		when(s_mockInstanceService.findInstancesWithFilter(eq("name"), notNull(MultivaluedHashMap.class))).thenReturn(Arrays.asList(
				InstanceBuilder.create().withName("instance1").get()
		));


		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:9998/Instances")
				.queryParam("filter", "name")
				.queryParam("name", "instance1")
				.request(MediaType.APPLICATION_JSON_TYPE).get();

		Collection<Instance> result = response.readEntity(new GenericType<Collection<Instance>>()
		{
		});

		assertThat(result).hasSize(1).extracting("name").containsExactly("instance1");
		ArgumentCaptor<MultivaluedMap> arg = ArgumentCaptor.forClass(MultivaluedMap.class);
		verify(s_mockInstanceService, times(1)).findInstancesWithFilter(eq("name"), arg.capture());

		assertThat(arg.getValue()).containsEntry("name", Arrays.asList("instance1"));

		response.close();
		client.close();
	}

	/**
	 * Test for {@link Instances#findAll()}
	 * <p>
	 * Bad query string provided
	 * </p>
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testFindAll_WithBadQueryString() throws Exception
	{
		when(s_mockInstanceService.findInstancesWithFilter(eq("name"), notNull(MultivaluedHashMap.class))).thenReturn(Arrays.asList(
				InstanceBuilder.create().withName("instance1").get()
		));


		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:9998/Instances")
				.queryParam("name", "instance1")
				.request(MediaType.APPLICATION_JSON_TYPE).get();


		assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(400);

		response.close();
		client.close();
	}

	/**
	 * Test for {@link Instances#findAll()}
	 * <p>
	 * Bad query string provided
	 * </p>
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testFindAll_WithBadFilterValue() throws Exception
	{
		when(s_mockInstanceService.findInstancesWithFilter(eq("name"), notNull(MultivaluedHashMap.class))).thenReturn(Arrays.asList(
				InstanceBuilder.create().withName("instance1").get()
		));

		Client client = ClientBuilder.newClient();
		Response response = client.target("http://localhost:9998/Instances")
				.queryParam("filter", "jobs")
				.request(MediaType.APPLICATION_JSON_TYPE).get();

		assertThat(response.getStatusInfo().getStatusCode()).isEqualTo(400);

		response.close();
		client.close();
	}

	// ------------------------- inner class -------------------------

	/**
	 * Factory to use a mock for {@link com.github.jehan.services.InstanceService}
	 */
	private static class MockInstanceServiceFactory implements Factory<InstanceService>
	{
		@Override
		public InstanceService provide()
		{
			return s_mockInstanceService;
		}

		@Override
		public void dispose(InstanceService p_instanceService)
		{
		}
	}
}