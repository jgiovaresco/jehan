package com.github.jehan.services;

import com.github.jehan.dao.InstanceDao;
import com.github.jehan.model.Instance;
import com.github.jehan.model.Job;
import com.github.jehan.model.builder.InstanceBuilder;
import com.github.jehan.model.builder.JobBuilder;
import org.glassfish.jersey.internal.util.collection.MultivaluedStringMap;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link com.github.jehan.services.InstanceServiceImpl}
 */
@RunWith(MockitoJUnitRunner.class)
public class InstanceServiceImplTest
{
	// ------------------------- private members -------------------------

	/** The class to test. */
	private InstanceServiceImpl m_service;

	/** The mock of {@link com.github.jehan.services.JobService}. */
	@Mock
	private JobService m_mockJobService;

	/** The mock of {@link com.github.jehan.dao.InstanceDao}. */
	@Mock
	private InstanceDao m_mockInstanceDao;

	// ------------------------- public methods -------------------------

	@Before
	public void setUp() throws Exception
	{
		m_service = new InstanceServiceImpl();
		Whitebox.setInternalState(m_service, InstanceDao.class, m_mockInstanceDao);
		Whitebox.setInternalState(m_service, JobService.class, m_mockJobService);
	}

	/**
	 * Test of the method {@link InstanceServiceImpl#findAll()}
	 */
	@Test
	public void testFindAll() throws Exception
	{
		Instance instance1 = InstanceBuilder.create().withName("instance1").get();
		Instance instance2 = InstanceBuilder.create().withName("instance2").get();
		Instance instance3 = InstanceBuilder.create().withName("instance3").get();
		when(m_mockInstanceDao.findAll()).thenReturn(Arrays.asList(instance1, instance2, instance3));

		when(m_mockJobService.findAll(instance1)).thenReturn(Arrays.asList(JobBuilder.create().withName("job1").get()));
		when(m_mockJobService.findAll(instance2)).thenReturn(Collections.<Job>emptyList());
		when(m_mockJobService.findAll(instance3)).thenReturn(Arrays.asList(JobBuilder.create().withName("job2").get()));

		Collection<Instance> resultat = m_service.findAll();

		assertThat(resultat).hasSize(3)
				.contains(InstanceBuilder.create().withName("instance1").get(), InstanceBuilder.create().withName("instance2").get(),
				          InstanceBuilder.create().withName("instance3").get());
		assertThat(instance1.getJobs()).hasSize(1).contains(JobBuilder.create().withName("job1").get());
		assertThat(instance2.getJobs()).isEmpty();
		assertThat(instance3.getJobs()).hasSize(1).contains(JobBuilder.create().withName("job2").get());
	}

	/**
	 * Test of the method {@link com.github.jehan.services.InstanceServiceImpl#findAllWithJobsKo()}
	 */
	@Test
	public void testFindAllWithJobsKo() throws Exception
	{
		Instance instance1 = InstanceBuilder.create().withName("instance1").get();
		Instance instance2 = InstanceBuilder.create().withName("instance2").get();
		Instance instance3 = InstanceBuilder.create().withName("instance3").get();
		when(m_mockInstanceDao.findAll()).thenReturn(Arrays.asList(instance1, instance2, instance3));

		when(m_mockJobService.findAll(instance1)).thenReturn(Arrays.asList(JobBuilder.create().withName("job1").withColor("blue").get()));
		when(m_mockJobService.findAll(instance2)).thenReturn(Arrays.asList(JobBuilder.create().withName("job2").withColor("red").get()));
		when(m_mockJobService.findAll(instance3)).thenReturn(Arrays.asList(JobBuilder.create().withName("job3").withColor("yellow").get()));

		assertThat(m_service.findAllWithJobsKo()).hasSize(2).contains(instance2, instance3);
	}

	/**
	 * Test of the method {@link com.github.jehan.services.InstanceServiceImpl#findByName(String)}
	 */
	@Test
	public void testFindByName() throws Exception
	{
		Instance instance1 = InstanceBuilder.create().withName("instance1").get();
		when(m_mockInstanceDao.findByName("instance1")).thenReturn(instance1);

		assertThat(m_service.findByName("instance1")).isSameAs(instance1);
	}

	/**
	 * Test of the method {@link com.github.jehan.services.InstanceServiceImpl#findInstancesWithFilter(String, javax.ws.rs.core.MultivaluedMap)}
	 * <p>
	 * Filter by one name
	 * </p>
	 */
	@Test
	public void testFindInstancesWithFilter_ByOneName() throws Exception
	{
		MultivaluedMap<String, String> filterValues = new MultivaluedStringMap();
		filterValues.putSingle("name", "instance1");

		Instance instance1 = InstanceBuilder.create().withName("instance1").get();
		when(m_mockInstanceDao.findAllByName("instance1")).thenReturn(Arrays.asList(
				instance1));

		when(m_mockJobService.findAll(instance1)).thenReturn(Arrays.asList(JobBuilder.create().withName("job1").get()));

		Collection<Instance> resultat = m_service.findInstancesWithFilter("name", filterValues);

		assertThat(resultat).hasSize(1)
				.contains(InstanceBuilder.create().withName("instance1").get());
		assertThat(instance1.getJobs()).hasSize(1).contains(JobBuilder.create().withName("job1").get());
	}

	/**
	 * Test of the method {@link com.github.jehan.services.InstanceServiceImpl#findInstancesWithFilter(String, javax.ws.rs.core.MultivaluedMap)}
	 * <p>
	 * Filter by many names
	 * </p>
	 */
	@Test
	public void testFindInstancesWithFilter_ByManyNames() throws Exception
	{
		MultivaluedMap<String, String> filterValues = new MultivaluedStringMap();
		filterValues.put("name", Arrays.asList("instance1", "instance3", "instance3"));

		Instance instance1 = InstanceBuilder.create().withName("instance1").get();
		Instance instance2 = InstanceBuilder.create().withName("instance2").get();
		Instance instance3 = InstanceBuilder.create().withName("instance3").get();
		when(m_mockInstanceDao.findAllByName("instance1", "instance3", "instance3"))
				.thenReturn(Arrays.asList(instance1, instance2, instance3));

		when(m_mockJobService.findAll(instance1)).thenReturn(Arrays.asList(JobBuilder.create().withName("job1").get()));
		when(m_mockJobService.findAll(instance2)).thenReturn(Collections.<Job>emptyList());
		when(m_mockJobService.findAll(instance3)).thenReturn(Arrays.asList(JobBuilder.create().withName("job2").get()));

		Collection<Instance> resultat = m_service.findInstancesWithFilter("name", filterValues);

		assertThat(resultat).hasSize(3)
				.contains(InstanceBuilder.create().withName("instance1").get(), InstanceBuilder.create().withName("instance2").get(),
				          InstanceBuilder.create().withName("instance3").get());
		assertThat(instance1.getJobs()).hasSize(1).contains(JobBuilder.create().withName("job1").get());
		assertThat(instance2.getJobs()).isEmpty();
		assertThat(instance3.getJobs()).hasSize(1).contains(JobBuilder.create().withName("job2").get());
	}


	/**
	 * Test of the method {@link com.github.jehan.services.InstanceServiceImpl#findInstancesWithFilter(String, javax.ws.rs.core.MultivaluedMap)}
	 * <p>
	 * Filter by one job's color.
	 * </p>
	 */
	@Test
	public void testFindInstancesWithFilter_ByOneJobColor() throws Exception
	{
		MultivaluedMap<String, String> filterValues = new MultivaluedStringMap();
		filterValues.putSingle("color", "blue");

		Instance instance1 = InstanceBuilder.create().withName("instance1").get();
		Instance instance2 = InstanceBuilder.create().withName("instance2").get();
		Instance instance3 = InstanceBuilder.create().withName("instance3").get();
		when(m_mockInstanceDao.findAll()).thenReturn(Arrays.asList(instance1, instance2, instance3));

		when(m_mockJobService.findJobsWithFilter(instance1, "color", filterValues)).thenReturn(Collections.<Job>emptyList());
		when(m_mockJobService.findJobsWithFilter(instance2, "color", filterValues)).thenReturn(Arrays.asList(
				JobBuilder.create().withName("job1").get(),
				JobBuilder.create().withName("job2").get()
		));
		when(m_mockJobService.findJobsWithFilter(instance3, "color", filterValues)).thenReturn(Collections.<Job>emptyList());

		Collection<Instance> resultat = m_service.findInstancesWithFilter("jobs", filterValues);

		assertThat(resultat).hasSize(1).contains(instance2);
		assertThat(instance2.getJobs()).hasSize(2)
				.contains(JobBuilder.create().withName("job1").get())
				.contains(JobBuilder.create().withName("job2").get());
	}

	/**
	 * Test of the method {@link com.github.jehan.services.InstanceServiceImpl#findInstancesWithFilter(String, javax.ws.rs.core.MultivaluedMap)}
	 * <p>
	 * Filter by many job's color.
	 * </p>
	 */
	@Test
	public void testFindInstancesWithFilter_ByManyJobColor() throws Exception
	{
		MultivaluedMap<String, String> filterValues = new MultivaluedStringMap();
		filterValues.put("color", Arrays.asList("red", "yellow"));

		Instance instance1 = InstanceBuilder.create().withName("instance1").get();
		Instance instance2 = InstanceBuilder.create().withName("instance2").get();
		Instance instance3 = InstanceBuilder.create().withName("instance3").get();
		when(m_mockInstanceDao.findAll()).thenReturn(Arrays.asList(instance1, instance2, instance3));

		when(m_mockJobService.findJobsWithFilter(instance1, "color", filterValues)).thenReturn(Arrays.asList(
				JobBuilder.create().withName("job_red1").get(),
				JobBuilder.create().withName("job_yellow2").get()));
		when(m_mockJobService.findJobsWithFilter(instance2, "color", filterValues)).thenReturn(Collections.<Job>emptyList());
		when(m_mockJobService.findJobsWithFilter(instance3, "color", filterValues)).thenReturn(Arrays.asList(
				JobBuilder.create().withName("job_yellow1").get(),
				JobBuilder.create().withName("job_red2").get()));

		Collection<Instance> resultat = m_service.findInstancesWithFilter("jobs", filterValues);

		assertThat(resultat).hasSize(2).contains(instance1, instance3);
		assertThat(instance1.getJobs()).hasSize(2)
				.contains(JobBuilder.create().withName("job_red1").get())
				.contains(JobBuilder.create().withName("job_yellow2").get());
		assertThat(instance3.getJobs()).hasSize(2)
				.contains(JobBuilder.create().withName("job_yellow1").get())
				.contains(JobBuilder.create().withName("job_red2").get());
	}
}