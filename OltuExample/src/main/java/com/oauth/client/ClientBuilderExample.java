package com.oauth.client;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.GitHubTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthAuthzResponse;
import org.apache.oltu.oauth2.common.OAuthProviderType;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

public class ClientBuilderExample {

	public void endUserAuthorizationRequest() throws OAuthSystemException {
		OAuthClientRequest request = OAuthClientRequest.authorizationProvider(OAuthProviderType.FACEBOOK)
				.setClientId("your-facebook-application-client-id").setRedirectURI("http://www.example.com/redirect")
				.buildQueryMessage();
	}

	public void endUserAuthorizationRequestManualAuthProvider() throws OAuthSystemException {
		OAuthClientRequest request = OAuthClientRequest
				.authorizationLocation("https://graph.facebook.com/oauth/authorize")
				.setClientId("your-facebook-application-client-id").setRedirectURI("http://www.example.com/redirect")
				.buildQueryMessage();
	}

	// Think it is servlet method we need to implement like this Auth server response in below callback method 
	protected void doGet(HttpServletRequest servletRequest, HttpServletResponse servletResponse)
			throws ServletException, IOException, OAuthSystemException {

		OAuthClientRequest request = OAuthClientRequest
				.authorizationLocation("https://graph.facebook.com/oauth/authorize")
				.setClientId("your-facebook-application-client-id").setRedirectURI("http://www.example.com/redirect")
				.buildQueryMessage();
		;

		// ... omitted code ...

		servletResponse.sendRedirect(request.getLocationUri());

	}
	public void callback(HttpServletRequest request, HttpServletResponse servletResponse) throws OAuthProblemException{
		OAuthAuthzResponse oar = OAuthAuthzResponse.oauthCodeAuthzResponse(request);
		String code = oar.getCode();
	}
	
	
	public void exchangeOAuthCodeForAnAccessToken(String authCode) throws OAuthSystemException, OAuthProblemException{
		OAuthClientRequest request = OAuthClientRequest
                .tokenProvider(OAuthProviderType.FACEBOOK)
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId("your-facebook-application-client-id")
                .setClientSecret("your-facebook-application-client-secret")
                .setRedirectURI("http://www.example.com/redirect")
                .setCode(authCode)
                .buildQueryMessage();
 
            //create OAuth client that uses custom http client under the hood
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
 
            //Facebook is not fully compatible with OAuth 2.0 draft 10, access token response is
            //application/x-www-form-urlencoded, not json encoded so we use dedicated response class for that
            //Custom response classes are an easy way to deal with oauth providers that introduce modifications to
            //OAuth 2.0 specification
            GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);
 
            String accessToken = oAuthResponse.getAccessToken();
            Long expiresIn = oAuthResponse.getExpiresIn();
	}
	
	
	public void exchangeOAuthCodeForAnAccessTokenWithTokenProviderLocation(String authCode) throws OAuthSystemException, OAuthProblemException{
		OAuthClientRequest request = OAuthClientRequest
				.tokenLocation("https://graph.facebook.com/oauth/access_token")
                .setGrantType(GrantType.AUTHORIZATION_CODE)
                .setClientId("your-facebook-application-client-id")
                .setClientSecret("your-facebook-application-client-secret")
                .setRedirectURI("http://www.example.com/redirect")
                .setCode(authCode)
                .buildQueryMessage();
 
            //create OAuth client that uses custom http client under the hood
            OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
 
            //Facebook is not fully compatible with OAuth 2.0 draft 10, access token response is
            //application/x-www-form-urlencoded, not json encoded so we use dedicated response class for that
            //Custom response classes are an easy way to deal with oauth providers that introduce modifications to
            //OAuth 2.0 specification
            GitHubTokenResponse oAuthResponse = oAuthClient.accessToken(request, GitHubTokenResponse.class);
 
            String accessToken = oAuthResponse.getAccessToken();
            Long expiresIn = oAuthResponse.getExpiresIn();
	}
}
