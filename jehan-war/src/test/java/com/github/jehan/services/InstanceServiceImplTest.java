package com.github.jehan.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import com.github.jehan.model.BuildStatus;
import com.github.jehan.model.Instance;
import com.github.jehan.model.builder.InstanceBuilder;
import com.github.jehan.model.builder.JobBuilder;

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

   /** The map of instances. */
   private Map<String, Instance> m_instancesMap = new TreeMap<>();

   // ------------------------- public methods -------------------------

   @Before
   public void setUp() throws Exception
   {
      m_service = new InstanceServiceImpl();
      Whitebox.setInternalState(m_service, JobService.class, m_mockJobService);
      Whitebox.setInternalState(m_service, Map.class, m_instancesMap);
   }

   /**
    * Test of the method {@link InstanceServiceImpl#findAll()}
    */
   @Test
   public void testFindAll() throws Exception
   {
      m_instancesMap.put("instance1", InstanceBuilder.create().withName("instance1").get());
      m_instancesMap.put("instance2", InstanceBuilder.create().withName("instance2").get());
      m_instancesMap.put("instance3", InstanceBuilder.create().withName("instance3").get());

      assertThat(m_service.findAll()).hasSize(3)
              .contains(InstanceBuilder.create().withName("instance1").get(), InstanceBuilder.create().withName("instance2").get(),
                      InstanceBuilder.create().withName("instance3").get());
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
      m_instancesMap.put("instance1", instance1);
      m_instancesMap.put("instance2", instance2);
      m_instancesMap.put("instance3", instance3);

      when(m_mockJobService.findAll(instance1))
              .thenReturn(Arrays.asList(JobBuilder.create().withName("job1").withColor(BuildStatus.SUCCESS).get()));
      when(m_mockJobService.findAll(instance2))
              .thenReturn(Arrays.asList(JobBuilder.create().withName("job2").withColor(BuildStatus.FAILED).get()));
      when(m_mockJobService.findAll(instance3))
              .thenReturn(Arrays.asList(JobBuilder.create().withName("job3").withColor(BuildStatus.TEST_FAILED).get()));

      assertThat(m_service.findAllWithJobsKo()).hasSize(2).contains(instance2, instance3);
   }

   @Test
   public void testFindById() throws Exception
   {
      Instance instance1 = InstanceBuilder.create().withName("instance1").get();
      Instance instance2 = InstanceBuilder.create().withName("instance2").get();
      Instance instance3 = InstanceBuilder.create().withName("instance3").get();
      m_instancesMap.put("instance1", instance1);
      m_instancesMap.put("instance2", instance2);
      m_instancesMap.put("instance3", instance3);

      assertThat(m_service.findById("instance1")).isSameAs(instance1);
      assertThat(m_service.findById("instance2")).isSameAs(instance2);
      assertThat(m_service.findById("instance3")).isSameAs(instance3);
   }
}