package com.socialnetwork.social.linkedin;

import com.socialnetwork.social.linkedin.oauth.OAuthInterface;

/**
 * Created by anand on 9/3/2016.**
 * Main entry point for the Instagram API.
 */
public class Linkedin
{

	/**
	 * The default endpoint host.
	 */
	public static final String DEFAULT_API_HOST = "www.linkedin.com";

	private UserInterface userInterface;
	/**
	 * If set to true, trace messages will be printed to STDOUT.
	 */
	public static boolean tracing = false;

	private String apiKey;
	private String sharedSecret;
	private Transport transport;
	private OAuthInterface oAuthInterface;

	/**
	 * Construct a new Flickr gateway instance.
	 *
	 * @param apiKey       The API key, must be non-null
	 * @param sharedSecret
	 * @param transport
	 */
	public Linkedin(String apiKey, String sharedSecret, Transport transport)
	{
		setApiKey(apiKey);
		setSharedSecret(sharedSecret);
		setTransport(transport);
	}

	/**
	 * Get the API key.
	 *
	 * @return The API key
	 */
	public String getApiKey()
	{
		return apiKey;
	}

	/**
	 * Set the API key to use which must not be null.
	 *
	 * @param apiKey The API key which cannot be null
	 */
	public void setApiKey(String apiKey)
	{
		if (apiKey == null)
		{
			throw new IllegalArgumentException("API key must not be null");
		}
		this.apiKey = apiKey;
	}

	/**
	 * Get the Shared-Secret.
	 *
	 * @return The Shared-Secret
	 */
	public String getSharedSecret()
	{
		return sharedSecret;
	}

	/**
	 * Set the Shared-Secret to use which must not be null.
	 *
	 * @param sharedSecret The Shared-Secret which cannot be null
	 */
	public void setSharedSecret(String sharedSecret)
	{
		if (sharedSecret == null)
		{
			throw new IllegalArgumentException("Shared-Secret must not be null");
		}
		this.sharedSecret = sharedSecret;
	}

	/**
	 * Get the Transport interface.
	 *
	 * @return The Tranport interface
	 */
	public Transport getTransport()
	{
		return transport;
	}

	/**
	 * Set the Transport which must not be null.
	 *
	 * @param transport
	 */
	public void setTransport(Transport transport)
	{
		if (transport == null)
		{
			throw new IllegalArgumentException("Transport must not be null");
		}
		this.transport = transport;
	}

	public OAuthInterface getOAuthInterface()
	{
		if (oAuthInterface == null)
		{
			oAuthInterface = new OAuthInterface(apiKey, sharedSecret, transport);
		}
		return oAuthInterface;
	}

	public UserInterface getUserInterface(String oauthToken)
	{
		if (userInterface == null)
		{
			userInterface = new UserInterface(oauthToken, transport);
		}

		return userInterface;
	}

	public void setUserInterface(UserInterface userInterface)
	{
		this.userInterface = userInterface;
	}

}
