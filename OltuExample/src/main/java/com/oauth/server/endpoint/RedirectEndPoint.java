package com.oauth.server.endpoint;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.common.exception.OAuthSystemException;

@Path("/redirect")
public class RedirectEndPoint {
	@GET
	public Response authorize(@Context HttpServletRequest request) throws URISyntaxException, OAuthSystemException {

		System.out.println("Request Params:" + request.getParameterMap());

		return Response.ok().build();
	}
}
