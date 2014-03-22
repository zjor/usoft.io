package io.sharedox.web.storage;

import io.sharedox.web.model.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: Sergey Royz
 * Date: 22.03.2014
 */
public class DocumentStorage {

	private Map<String, Document> storage = new HashMap<String, Document>();

	public Document findById(String id) {
		return storage.get(id);
	}

	public void store(Document document) {
		storage.put(document.getId(), document);
	}

}
