package io.sharedox.controller;

import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.sun.jersey.api.view.Viewable;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import io.sharedox.Routes;
import io.sharedox.dto.DocumentDTO;
import io.sharedox.manager.DocumentManager;
import io.sharedox.model.Document;
import io.sharedox.util.UserAgentUtils;
import lombok.extern.slf4j.Slf4j;
import me.zjor.auth.service.AuthUserService;
import me.zjor.auth.service.SocialProfileService;
import me.zjor.util.HttpUtils;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.*;

/**
 * @author: Sergey Royz
 * Date: 30.03.2014
 */
@Slf4j
@Path(Routes.ROOT)
public class SharedoxController {

	@Inject
	private AuthUserService authUserService;

	@Inject
	private SocialProfileService socialProfileService;

	@Context
	private HttpServletRequest httpServletRequest;

	@Inject
	private DocumentManager documentManager;

	/**
	 * Routes to landing page if user was not authorized or to /documents otherwise
	 *
	 * @return
	 */
	@GET
	public Response index() {
		String baseURL = HttpUtils.getBaseURL(httpServletRequest);

		return Response
				.temporaryRedirect(URI.create(baseURL + (authUserService.get() == null ? Routes.LANDING : "/documents")))
				.build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path(Routes.LANDING)
	public Response landing() {
		return Response.ok(new Viewable("/landing")).build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/documents")
	public Response documents() {
		List<Document> documents = documentManager.findAll(authUserService.get());

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("documents", documents);
		model.put("profile", socialProfileService.findByAuthUser(authUserService.get()));

		return Response.ok(new Viewable("/documents/list", model)).build();
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/api/json/documents")
	public Response getDocumentsAsJSON() {
		List<Document> documents = documentManager.findAll(authUserService.get());
		return Response.ok().entity(
				new GsonBuilder()
						.create()
						.toJson(DocumentDTO.fromModelCollection(documents, HttpUtils.getBaseURL(httpServletRequest)))
		).build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/api/upload")
	public Response upload(@FormDataParam("file") java.io.InputStream fileInputStream,
						   @FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {
		String filename = contentDispositionHeader.getFileName();
		String resourceLocation = UUID.randomUUID().toString() + "_" + filename;

		try {
			FileOutputStream fout = new FileOutputStream("/tmp/" + resourceLocation);
			IOUtils.copy(fileInputStream, fout);
			fout.flush();
			fout.close();
			fileInputStream.close();
			log.info("File was saved successfully. Filename: {}", resourceLocation);
		} catch (IOException e) {
			log.error("Failed to save file", e);
			return Response.serverError().entity(e.getMessage()).build();
		}
		Map<String, String> result = new HashMap<String, String>();
		result.put("filename", filename);
		result.put("resource_location", resourceLocation);
		return Response.ok().entity(result).build();
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	@Path("/api/documents/create")
	public Response create(
			@FormParam("title") String title,
			@FormParam("description") String description,
			@FormParam("filename") String filename,
			@FormParam("resource_location") String resourceLocation
	) {
		documentManager.create(authUserService.get(), title, description, null, resourceLocation, filename);
		return Response.ok().build();
	}

	@DELETE
	@Path("/api/documents/delete/{id}")
	public Response remove(@PathParam("id") String id) {
		documentManager.remove(id);
		return Response.ok().build();
	}


	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/documents/create")
	public Response showCreateDocumentForm() {
		return Response.ok(new Viewable("/documents/create")).build();
	}

	@POST
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.TEXT_HTML)
	@Path("/documents/create")
	public Response createDocument(
			@FormDataParam("title") String title,
			@FormDataParam("description") String description,
			@FormDataParam("file") java.io.InputStream fileInputStream,
			@FormDataParam("file") FormDataContentDisposition contentDispositionHeader) {
		String resourceLocation = UUID.randomUUID().toString() + "_" + contentDispositionHeader.getFileName();

		documentManager.create(authUserService.get(), title, description, null, resourceLocation, contentDispositionHeader.getFileName());

		try {
			FileOutputStream fout = new FileOutputStream("/tmp/" + resourceLocation);
			IOUtils.copy(fileInputStream, fout);
			fout.flush();
			fout.close();
			fileInputStream.close();
		} catch (IOException e) {
			log.error("Failed to save file", e);
		}

		return Response.ok(new Viewable("/redirect", Collections.singletonMap("url", HttpUtils.getBaseURL(httpServletRequest) + "/documents"))).build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/documents/{id}")
	public Response showDetails(@PathParam("id") String documentId) {
		Document document = documentManager.findById(documentId);
		if (document == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("ID: " + document).build();
		}

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("document", document);
		model.put("profile", socialProfileService.findByAuthUser(authUserService.get()));

		return Response.ok(new Viewable("/documents/details", model)).build();
	}

	@GET
	@Produces(MediaType.TEXT_HTML)
	@Path("/public/documents/{id}")
	public Response retrieveDocument(
			@PathParam("id") String documentId,
			@HeaderParam("User-Agent") String userAgent) {
		Document document = documentManager.findById(documentId);
		if (document == null) {
			return Response.status(Response.Status.NOT_FOUND).entity("ID: " + document).build();
		}
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("document", document);

		if (UserAgentUtils.isFacebookCrawler(userAgent)) {
			return Response.ok(new Viewable("/documents/snippet.jsp", model)).build();
		} else {
			try {
				FileInputStream fin = new FileInputStream("/tmp/" + document.getResourceLocation());
				return Response.ok(fin).header("content-disposition","attachment; filename = " + document.getFilename()).build();
			} catch (IOException e) {
				log.error("Unable to read file", e);
				return Response.serverError().build();
			}
		}
	}

}
