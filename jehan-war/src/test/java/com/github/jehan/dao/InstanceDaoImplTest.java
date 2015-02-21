package com.github.jehan.dao;

import com.github.jehan.model.Instance;
import com.github.jehan.model.builder.InstanceBuilder;
import com.github.jehan.services.InstanceServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.reflect.Whitebox;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Test class for {@link com.github.jehan.dao.InstanceDaoImpl}
 */
@RunWith(MockitoJUnitRunner.class)
public class InstanceDaoImplTest
{
	// ------------------------- private members -------------------------

	/** The class to test. */
	private InstanceDaoImpl m_dao;

	/** The map of instances. */
	private Map<String, Instance> m_instancesMap = new TreeMap<>();

	// ------------------------- public methods -------------------------

	@Before
	public void setUp() throws Exception
	{
		m_dao = new InstanceDaoImpl();
		Whitebox.setInternalState(m_dao, Map.class, m_instancesMap);
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

		assertThat(m_dao.findAll()).hasSize(3)
				.extracting("name")
				.contains("instance1", "instance2", "instance3");
		assertThat(m_dao.findAll()).usingElementComparator(new Comparator<Instance>()
		{
			@Override
			public int compare(Instance o1, Instance o2)
			{
				return new Integer(System.identityHashCode(o1)).compareTo(System.identityHashCode(o2));
			}
		}).doesNotContain(m_instancesMap.get("instance1"), m_instancesMap.get("instance2"), m_instancesMap.get("instance3"));
	}

	/**
	 * Test of the method {@link InstanceServiceImpl#findByName(String)}
	 */
	@Test
	public void testFindByName() throws Exception
	{
		Instance instance1 = InstanceBuilder.create().withName("instance1").get();
		Instance instance2 = InstanceBuilder.create().withName("instance2").get();
		Instance instance3 = InstanceBuilder.create().withName("instance3").get();
		m_instancesMap.put("instance1", instance1);
		m_instancesMap.put("instance2", instance2);
		m_instancesMap.put("instance3", instance3);

		assertThat(m_dao.findByName("instance1")).isEqualTo(instance1).isNotSameAs(instance1);
		assertThat(m_dao.findByName("instance2")).isEqualTo(instance2).isNotSameAs(instance2);
		assertThat(m_dao.findByName("instance3")).isEqualTo(instance3).isNotSameAs(instance3);
	}
}