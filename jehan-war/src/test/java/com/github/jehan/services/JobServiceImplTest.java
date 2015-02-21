package com.github.jehan.services;

import com.github.jehan.dao.JobDao;
import com.github.jehan.model.Instance;
import com.github.jehan.model.Job;
import com.github.jehan.model.builder.InstanceBuilder;
import com.github.jehan.model.builder.JobBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * Test class for {@link com.github.jehan.services.JobServiceImpl}
 */
@RunWith(MockitoJUnitRunner.class)
public class JobServiceImplTest
{
	// ------------------------- private members -------------------------

	/** The class to test. */
	private JobServiceImpl m_service;

	/** The mock of {@link com.github.jehan.dao.JobDao}. */
	@Mock
	private JobDao m_mockJobDao;

	// ------------------------- public methods -------------------------

	@Before
	public void setUp() throws Exception
	{
		m_service = new JobServiceImpl();
		Whitebox.setInternalState(m_service, JobDao.class, m_mockJobDao);
	}

	/**
	 * Test of the method {@link com.github.jehan.services.JobServiceImpl#findAll(com.github.jehan.model.Instance)}
	 */
	@Test
	public void testFindAll() throws Exception
	{
		Instance i = InstanceBuilder.create().withName("my_instance").get();
		ArrayList<Job> jobs = new ArrayList<>();
		jobs.add(JobBuilder.create().withName("job1").withColor("blue").get());
		jobs.add(JobBuilder.create().withName("job2").withColor("red").get());
		jobs.add(JobBuilder.create().withName("job3").withColor("yellow").get());
		jobs.add(JobBuilder.create().withName("job4").withColor("gray").get());

		when(m_mockJobDao.findAll(i)).thenReturn(jobs);

		assertThat(m_service.findAll(i)).hasSize(4)
				.isSameAs(jobs);
	}

	/**
	 * Test of the method {@link com.github.jehan.services.JobServiceImpl#findJobsWithFilter(com.github.jehan.model.Instance, String, javax.ws.rs.core.MultivaluedMap)}
	 * <p>
	 * Filter by one name.
	 * </p>
	 */
	@Test
	public void testFindJobsWithFilter_ByOneName() throws Exception
	{
		Instance i = InstanceBuilder.create().withName("my_instance").get();
		List<Job> jobs = new ArrayList<>();
		jobs.add(JobBuilder.create().withName("job1").withColor("blue").get());
		jobs.add(JobBuilder.create().withName("job2").withColor("red").get());
		jobs.add(JobBuilder.create().withName("job3").withColor("yellow").get());
		jobs.add(JobBuilder.create().withName("job4").withColor("gray").get());

		List<Job> expectedJobs = new ArrayList<>();
		expectedJobs.add(jobs.get(1));

		when(m_mockJobDao.findAll(i)).thenReturn(jobs);

		MultivaluedMap<String, String> filterValues = new MultivaluedHashMap<>();
		filterValues.putSingle("name", "job2");

		assertThat(m_service.findJobsWithFilter(i, "name", filterValues)).hasSize(1)
				.isEqualTo(expectedJobs);
	}

	/**
	 * Test of the method {@link com.github.jehan.services.JobServiceImpl#findJobsWithFilter(com.github.jehan.model.Instance, String, javax.ws.rs.core.MultivaluedMap)}
	 * <p>
	 * Filter by many names.
	 * </p>
	 */
	@Test
	public void testFindJobsWithFilter_ByManyName() throws Exception
	{
		Instance i = InstanceBuilder.create().withName("my_instance").get();
		List<Job> jobs = new ArrayList<>();
		jobs.add(JobBuilder.create().withName("job1").withColor("blue").get());
		jobs.add(JobBuilder.create().withName("job2").withColor("red").get());
		jobs.add(JobBuilder.create().withName("job3").withColor("yellow").get());
		jobs.add(JobBuilder.create().withName("job4").withColor("gray").get());

		List<Job> expectedJobs = new ArrayList<>();
		expectedJobs.add(jobs.get(0));
		expectedJobs.add(jobs.get(2));
		expectedJobs.add(jobs.get(3));

		when(m_mockJobDao.findAll(i)).thenReturn(jobs);

		MultivaluedMap<String, String> filterValues = new MultivaluedHashMap<>();
		filterValues.put("name", Arrays.asList("job1", "job3", "job4"));

		assertThat(m_service.findJobsWithFilter(i, "name", filterValues)).hasSize(3)
				.containsExactly(expectedJobs.toArray(new Job[expectedJobs.size()]));
	}

	/**
	 * Test of the method {@link com.github.jehan.services.JobServiceImpl#findJobsWithFilter(com.github.jehan.model.Instance, String, javax.ws.rs.core.MultivaluedMap)}
	 * <p>
	 * Filter by one color.
	 * </p>
	 */
	@Test
	public void testFindJobsWithFilter_ByOneColor() throws Exception
	{
		Instance i = InstanceBuilder.create().withName("my_instance").get();
		List<Job> jobs = new ArrayList<>();
		jobs.add(JobBuilder.create().withName("job1").withColor("blue").get());
		jobs.add(JobBuilder.create().withName("job2").withColor("blue").get());
		jobs.add(JobBuilder.create().withName("job3").withColor("yellow").get());
		jobs.add(JobBuilder.create().withName("job4").withColor("red").get());
		jobs.add(JobBuilder.create().withName("job5").withColor("gray").get());

		List<Job> expectedJobs = new ArrayList<>();
		expectedJobs.add(jobs.get(0));
		expectedJobs.add(jobs.get(1));

		when(m_mockJobDao.findAll(i)).thenReturn(jobs);

		MultivaluedMap<String, String> filterValues = new MultivaluedHashMap<>();
		filterValues.putSingle("color", "blue");

		assertThat(m_service.findJobsWithFilter(i, "color", filterValues)).hasSize(2)
				.containsExactly(expectedJobs.toArray(new Job[expectedJobs.size()]));
	}


	/**
	 * Test of the method {@link com.github.jehan.services.JobServiceImpl#findJobsWithFilter(com.github.jehan.model.Instance, String, javax.ws.rs.core.MultivaluedMap)}
	 * <p>
	 * Filter by many colors.
	 * </p>
	 */
	@Test
	public void testFindJobsWithFilter_ByManyColor() throws Exception
	{
		Instance i = InstanceBuilder.create().withName("my_instance").get();
		List<Job> jobs = new ArrayList<>();
		jobs.add(JobBuilder.create().withName("job1").withColor("blue").get());
		jobs.add(JobBuilder.create().withName("job2").withColor("blue").get());
		jobs.add(JobBuilder.create().withName("job3").withColor("yellow").get());
		jobs.add(JobBuilder.create().withName("job4").withColor("red").get());
		jobs.add(JobBuilder.create().withName("job5").withColor("gray").get());

		List<Job> expectedJobs = new ArrayList<>();
		expectedJobs.add(jobs.get(3));
		expectedJobs.add(jobs.get(4));

		when(m_mockJobDao.findAll(i)).thenReturn(jobs);

		MultivaluedMap<String, String> filterValues = new MultivaluedHashMap<>();
		filterValues.put("color", Arrays.asList("red", "gray"));

		assertThat(m_service.findJobsWithFilter(i, "color", filterValues)).hasSize(2)
				.containsExactly(expectedJobs.toArray(new Job[expectedJobs.size()]));
	}
}