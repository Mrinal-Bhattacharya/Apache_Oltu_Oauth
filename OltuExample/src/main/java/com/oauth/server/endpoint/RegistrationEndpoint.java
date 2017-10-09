package com.oauth.server.endpoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.OAuthResponse;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.JSONHttpServletRequestWrapper;
import org.apache.oltu.oauth2.ext.dynamicreg.server.request.OAuthServerRegistrationRequest;
import org.apache.oltu.oauth2.ext.dynamicreg.server.response.OAuthServerRegistrationResponse;

@Path("/register")
public class RegistrationEndpoint {
	public static final String CLIENT_ID = "someclientid";
	public static final String CLIENT_SECRET = "someclientsecret";
	public static final String ISSUED_AT = "0123456789";
	public static final Long EXPIRES_IN = 987654321l;

	@POST
	@Consumes("application/json")
	@Produces("application/json")
	public Response register(@Context HttpServletRequest request) throws OAuthSystemException {

		OAuthServerRegistrationRequest oauthRequest = null;
		try {
			oauthRequest = new OAuthServerRegistrationRequest(new JSONHttpServletRequestWrapper(request));
			oauthRequest.getType();
			oauthRequest.discover();
			oauthRequest.getClientName();
			oauthRequest.getClientUrl();
			oauthRequest.getClientDescription();
			oauthRequest.getRedirectURI();

			OAuthResponse response = OAuthServerRegistrationResponse.status(HttpServletResponse.SC_OK)
					.setClientId(CLIENT_ID).setClientSecret(CLIENT_SECRET).setIssuedAt(ISSUED_AT)
					.setExpiresIn(EXPIRES_IN).buildJSONMessage();
			return Response.status(response.getResponseStatus()).entity(response.getBody()).build();

		} catch (OAuthProblemException e) {
			OAuthResponse response = OAuthServerRegistrationResponse.errorResponse(HttpServletResponse.SC_BAD_REQUEST)
					.error(e).buildJSONMessage();
			return Response.status(response.getResponseStatus()).entity(response.getBody()).build();
		}

	}
}