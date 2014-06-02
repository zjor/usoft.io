package io.usoft.properties;

import io.usoft.properties.annotations.ResourceFileProperties;
import io.usoft.properties.annotations.Value;
import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

/**
 * @author: Sergey Royz
 * Date: 18.05.2014
 */
public class PropertiesLoaderTest {

	@Test
	public void shouldLoad() throws Exception {
		TestProperties properties = PropertiesLoader.load(new TestProperties());

		assertEquals("admin", properties.getUsername());
		assertEquals("superpassword", properties.getPassword());
		assertEquals(27, properties.getAge());
		assertEquals(175.4, properties.getHeight(), 0.5);
		assertTrue(properties.isAlive());
		assertNull(properties.getNonExistent());
	}

}

@Data
@ResourceFileProperties("test.properties")
class TestProperties implements PropertiesHolder {

	@Value("user")
	private String username;

	@Value("pass")
	private String password;

	@Value("age")
	private int age;

	@Value("height")
	private double height;

	@Value("isAlive")
	private boolean alive;

	@Value("nonExistent")
	private String nonExistent;

}
