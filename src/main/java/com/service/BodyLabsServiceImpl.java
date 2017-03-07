package com.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.security.cert.CertificateException;
import javax.security.cert.X509Certificate;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import com.data.BodyLabsRequest;
import com.data.BodyLabsResponse;
import com.exception.BodyLabsServiceException;
import com.google.gson.Gson;

public class BodyLabsServiceImpl implements BodyLabsService {

	private static final String BODY_LABS_MEASUMENTS_URL = "https://api.bodylabs.com/instant/measurements";

	private static final String ACCESS_KEY = "AK8c1daff843b6935c94df0650e11ddb65";
	private static final String SECRET = "317af6dfa5d57325b576649f3010e210";

	private static Logger LOGGER = Logger.getLogger(BodyLabsServiceImpl.class.getName());

	@Override
	public BodyLabsResponse retrieveMeasurements(BodyLabsRequest bodyLabsRequest) throws BodyLabsServiceException {

		HttpPost httpPost = connect();

		return execute(bodyLabsRequest, httpPost);
	}

	/**
	 * 
	 * @return
	 */
	private HttpPost connect() {
		LOGGER.info("START: connect()");

		HttpPost post = new HttpPost(BODY_LABS_MEASUMENTS_URL);
		String auth = new StringBuffer("SecretPair ").append("accesskey=").append(ACCESS_KEY).append(",")
				.append("secret=").append(SECRET).toString();
		post.setHeader("Authorization", auth);
		post.setHeader("Content-Type", "application/json");
		post.setHeader("Accept", "application/json");
		post.setHeader("X-Stream", "true");

		LOGGER.info("END: connect()");
		return post;
	}

	/**
	 * 
	 * @param bodyDetails
	 * @param httpPost
	 * @return
	 * @throws BodyLabsServiceException
	 */
	private BodyLabsResponse execute(BodyLabsRequest bodyDetails, HttpPost httpPost) throws BodyLabsServiceException {

		try {
			return executeHttpRequest(bodyDetails, httpPost);

		} catch (UnsupportedEncodingException e) {
			LOGGER.info("Error while encoding api url : " + e);
			throw new BodyLabsServiceException("Error while encoding api url.", e);
		} catch (IOException e) {
			LOGGER.info("ioException occured while sending http request : " + e);
			throw new BodyLabsServiceException("ioException occured while sending http request.", e);
		} catch (Exception e) {
			LOGGER.info("Exception occured while sending http request : " + e);
			throw new BodyLabsServiceException("Exception occured while sending http request.", e);
		} finally {
			httpPost.releaseConnection();
		}

	}

	/**
	 * 
	 * @param bodyDetails
	 * @param httpPost
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws KeyStoreException 
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	private BodyLabsResponse executeHttpRequest(BodyLabsRequest bodyDetails, HttpPost httpPost)
			throws UnsupportedEncodingException, IOException, KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		LOGGER.info("START: executeHttpRequest()");
		Gson gson = new Gson();

		// Java object to JSON, and assign to a String
		String jsonData = gson.toJson(bodyDetails);

		HttpResponse response = null;
		String line = "";
		StringBuffer result = new StringBuffer();
		httpPost.setEntity(new StringEntity(jsonData));
		HttpClient client = createHttpClientAcceptsUntrustedCerts();
		response = client.execute(httpPost);
		LOGGER.info("Post parameters : " + jsonData);
		LOGGER.info("Response Code : " + response.getStatusLine().getStatusCode());

		BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		while ((line = reader.readLine()) != null) {
			result.append(line);
		}

		BodyLabsResponse responseData = gson.fromJson(result.toString(), BodyLabsResponse.class);

		LOGGER.info("END: executeHttpRequest()");
		return responseData;
	}
	
	
	
	public HttpClient createHttpClientAcceptsUntrustedCerts() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
	    HttpClientBuilder b = HttpClientBuilder.create();
	 
	    // setup a Trust Strategy that allows all certificates.
	    //
	    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
	        public boolean isTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
	            return true;
	        }

			public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws java.security.cert.CertificateException {
				return true;
			}
	    }).build();
	    b.setSslcontext( sslContext);
	 
	    // don't check Hostnames, either.
	    //      -- use SSLConnectionSocketFactory.getDefaultHostnameVerifier(), if you don't want to weaken
	    HostnameVerifier hostnameVerifier = SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER;
	    
	    // here's the special part:
	    //      -- need to create an SSL Socket Factory, to use our weakened "trust strategy";
	    //      -- and create a Registry, to register it.
	    //
	    SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, (X509HostnameVerifier) hostnameVerifier);
	    Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
	            .register("http", PlainConnectionSocketFactory.getSocketFactory())
	            .register("https", sslSocketFactory)
	            .build();
	 
	    // now, we create connection-manager using our Registry.
	    //      -- allows multi-threaded use
	    PoolingHttpClientConnectionManager connMgr = new PoolingHttpClientConnectionManager( socketFactoryRegistry);
	    b.setConnectionManager( connMgr);
	 
	    // finally, build the HttpClient;
	    //      -- done!
	    HttpClient client = b.build();
	    return client;
	}
}
