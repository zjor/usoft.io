package io.usoft.properties;

import lombok.Data;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * @author: Sergey Royz
 * Date: 18.05.2014
 */
public class FilePropertiesLoaderTest {

	@Test
	public void shouldLoad() throws Exception {
		FilePropertiesLoader loader = new FilePropertiesLoader("test.properties");

		TestProperties properties = new TestProperties();
		loader.load(properties);

		assertEquals("admin", properties.getUsername());
		assertEquals("superpassword", properties.getPassword());

	}

}

@Data
class TestProperties {

	@Value("user")
	private String username;

	@Value("pass")
	private String password;

}
