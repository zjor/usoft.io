package io.sharedox.web.controller;

import com.google.inject.Inject;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import io.sharedox.web.model.Document;
import io.sharedox.web.storage.DocumentStorage;
import io.sharedox.web.util.UserAgentUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author: Sergey Royz
 * Date: 22.03.2014
 */
@Slf4j
@Path("/")
public class ShareDoxController {

	@Inject
	private DocumentStorage documentStorage;

	@Context
	private HttpServletRequest httpServletRequest;

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response index() {
		String generatedId = UUID.randomUUID().toString();
		return Response.temporaryRedirect(URI.create(generatedId + "/edit")).build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}")
	public Response showDocument(@PathParam("id") String documentId, @HeaderParam("User-Agent") String userAgent) {

		String ssid = httpServletRequest.getSession().getId();
		Document document = documentStorage.findById(documentId);

		if (document == null) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		if (UserAgentUtils.isFacebookCrawler(userAgent) ||
				ssid.equals(document.getOwnerSessionId())) {
			Map<String, Object> model = new HashMap<String, Object>();
			model.put("document", document);
			return Response.ok(new Viewable("/document", model)).build();
		} else {
			try {
				FileInputStream fin = new FileInputStream("/tmp/" + document.getFilename());
				return Response.ok(fin).header("content-disposition","attachment; filename = " + document.getOriginalFilename()).build();
			} catch (IOException e) {
				log.error("Unable to read file", e);
				return Response.serverError().build();
			}
		}
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}/edit")
	public Response editDocumentForm(@PathParam("id") String documentId) {

		String ssid = httpServletRequest.getSession().getId();
		Document document = documentStorage.findById(documentId);

		if (document == null) {
			documentStorage.store(document = new Document(documentId, ssid));
		}

		if (!ssid.equals(document.getOwnerSessionId())) {
			return Response.status(Response.Status.FORBIDDEN).entity("You are not the owner of the document").build();
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("document", document);

		return Response.ok(new Viewable("/edit", model)).build();

	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	@Path("/{id}/edit")
	public Response saveDocument(
			@PathParam("id") String documentId,
			@FormDataParam("title") String title,
			@FormDataParam("description") String description,
			@FormDataParam("file") InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {

		String ssid = httpServletRequest.getSession().getId();
		Document document = documentStorage.findById(documentId);

		if (document == null) {
			documentStorage.store(document = new Document(documentId, ssid));
		}

		if (!ssid.equals(document.getOwnerSessionId())) {
			return Response.status(Response.Status.FORBIDDEN).entity("You are not the owner of the document").build();
		}

		document.setTitle(title);
		document.setDescription(description);

		String filename = documentId + "_" + contentDispositionHeader.getFileName();
		document.setFilename(filename);
		document.setOriginalFilename(contentDispositionHeader.getFileName());

		try {
			FileOutputStream fout = new FileOutputStream("/tmp/" + filename);
			IOUtils.copy(fileInputStream, fout);
			fout.flush();
			fout.close();
			fileInputStream.close();
		} catch (IOException e) {
			log.error("Failed to save file", e);
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("document", document);
		model.put("message", "Document was saved successfully");

		return Response.ok(new Viewable("/edit", model)).build();
	}


}
