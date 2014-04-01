package io.sharedox.manager;

import io.sharedox.model.Document;
import me.zjor.auth.model.AuthUser;

import java.util.List;

/**
 * @author: Sergey Royz
 * Date: 30.03.2014
 */
public interface DocumentManager {

	Document create(AuthUser user, String title, String description, String imageURL, String resourceLocation, String filename);

	List<Document> findAll(AuthUser user);

	Document findById(String id);

}
