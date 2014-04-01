package io.sharedox.model;

import lombok.Data;
import me.zjor.auth.model.AuthUser;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * @author: Sergey Royz
 * Date: 30.03.2014
 */
@Data
@Entity
@Table(name = "documents")
public class Document {

	@Id
	@GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid2")
	@Column(name = "id", unique = true)
	private String id;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private AuthUser user;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "description", nullable = true)
	private String description;

	@Column(name = "image_url", nullable = true)
	private String imageURL;

	@Column(name = "resource_location", nullable = false)
	private String resourceLocation;

	@Column(name = "filename", nullable = false)
	private String filename;

	@Column(name = "creation_time", nullable = false)
	private Date creationTime;

	public static Document create(EntityManager em, AuthUser user, String title, String description, String imageURL, String resourceLocation, String filename) {
		Document doc = new Document();
		doc.setUser(user);
		doc.setTitle(title);
		doc.setDescription(description);
		doc.setImageURL(imageURL);
		doc.setResourceLocation(resourceLocation);
		doc.setFilename(filename);
		doc.setCreationTime(new Date());
		em.persist(doc);
		return doc;
	}

}
