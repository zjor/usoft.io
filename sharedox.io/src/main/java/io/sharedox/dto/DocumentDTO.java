package io.sharedox.dto;

import com.google.gson.annotations.SerializedName;
import io.sharedox.model.Document;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * @author: Sergey Royz
 * Date: 13.04.2014
 */
@Data
public class DocumentDTO implements Serializable {

	@SerializedName("id")
	private String id;

	@SerializedName("title")
	private String title;

	@SerializedName("description")
	private String description;

	@SerializedName("url")
	private String url;

	@SerializedName("creation_date")
	private Date creationDate;

	@SerializedName("downloads")
	private long downloads;

	public static DocumentDTO fromModel(Document doc, String baseURL) {
		DocumentDTO dto = new DocumentDTO();
		dto.setId(doc.getId());
		dto.setTitle(doc.getTitle());
		dto.setDescription(doc.getDescription());
		dto.setUrl(baseURL + "/public/documents/" + doc.getId());
		dto.setCreationDate(doc.getCreationTime());
		dto.setDownloads(doc.getDownloads());
		return dto;
	}

	public static Collection<DocumentDTO> fromModelCollection(Collection<Document> docs, String baseURL) {
		List<DocumentDTO> result = new ArrayList<DocumentDTO>();
		for (Document doc: docs) {
			result.add(DocumentDTO.fromModel(doc, baseURL));
		}
		return result;
	}

}
