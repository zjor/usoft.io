package io.sharedox.manager;

import com.google.inject.persist.Transactional;
import io.sharedox.model.Document;
import me.zjor.auth.model.AuthUser;
import me.zjor.manager.AbstractManager;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.List;

/**
 * @author: Sergey Royz
 * Date: 30.03.2014
 */
public class DocumentManagerImpl extends AbstractManager implements DocumentManager {

	@Override
	@Transactional
	public Document create(AuthUser user, String title, String description, String imageURL, String resourceLocation, String filename) {
		return Document.create(jpa(), user, title, description, imageURL, resourceLocation, filename);
	}

	@Override
	public List<Document> findAll(AuthUser user) {
		TypedQuery<Document> query = jpa()
				.createQuery("SELECT d FROM Document d WHERE d.user.id = :uid ORDER BY d.creationTime DESC", Document.class)
				.setParameter("uid", user.getId());
		return query.getResultList();
	}

	@Override
	public Document findById(String id) {
		return jpa().find(Document.class, id);
	}

	@Override
	@Transactional
	public void remove(String id) {
		Document entity = findById(id);
		if (entity != null) {
			jpa().remove(entity);
		}
	}

	@Override
	@Transactional
	public void increaseDownloads(String id) {
		Document document = findById(id);
		if (document != null) {
			document.setModificationTime(new Date());
			document.increaseDownloads();
		}

	}


}
