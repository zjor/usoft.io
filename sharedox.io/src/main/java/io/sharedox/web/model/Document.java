package io.sharedox.web.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: Sergey Royz
 * Date: 22.03.2014
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {

	private String id;
	private String ownerSessionId;
	private String title;
	private String description;
	private String originalFilename;
	private String filename;

	public Document(String id, String ownerSessionId) {
		this.id = id;
		this.ownerSessionId = ownerSessionId;
	}

}
