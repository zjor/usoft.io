package io.sharedox.manager;

import com.google.inject.Inject;
import io.sharedox.model.Document;
import me.zjor.auth.manager.AuthUserManager;
import me.zjor.auth.model.AuthUser;
import me.zjor.guice.TestRunner;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.persistence.EntityManager;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author: Sergey Royz
 * Date: 30.03.2014
 */
@RunWith(TestRunner.class)
public class DocumentManagerImplTest {

	@Inject
	private EntityManager em;

	@Inject
	private DocumentManagerImpl documentManager;

	@Inject
	private AuthUserManager authUserManager;

	@Before
	public void setUp() throws Exception {
		em.getTransaction().begin();
	}

	@After
	public void tearDown() throws Exception {
		if (em.isOpen()) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
		}
	}

	@Test
	public void createAndFindDocumentTest() {
		AuthUser user = authUserManager.create("login", "password");
		documentManager.create(user, "title", "descr", "imageUrl", "/tmp/file.txt", "file.txt");

		List<Document> docs = documentManager.findAll(user);
		assertEquals("title", docs.get(0).getTitle());
	}

}
