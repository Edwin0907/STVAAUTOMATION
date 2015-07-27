package ctva_rest_api_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLException;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;

public class SymphonyRestClient {
	
	private String endPoint;
	private HttpVerb method;
	private String contentType;
	private String postData;
	public static final int HTTP_OK = 200;
	
	public SymphonyRestClient()
	{
		this.endPoint = "";
		this.method = HttpVerb.GET;
		this.contentType = "application/xml";
		this.postData = "";
	}
	
	public SymphonyRestClient(String endpoint)
	{
		this.endPoint = endpoint;
		this.method = HttpVerb.GET;
		this.contentType = "application/xml";
		this.postData = "";
	}
	public SymphonyRestClient(String endpoint, HttpVerb httpVerb)
	{
		this.endPoint = endpoint;
		this.method = httpVerb;
		this.contentType = "application/xml";
		this.postData = "";
	}
	public SymphonyRestClient(String endpoint, HttpVerb httpVerb, String requestData)
	{
		this.endPoint = endpoint;
		this.method = httpVerb;
		this.contentType = "application/xml";
		this.postData = requestData;
	}
	
	public String getEndPoint(){
		return this.endPoint;
	}
	public void setEndPoint(String endPoint){
		this.endPoint = endPoint;
	}

	public HttpVerb getMethod(){
		return this.method;
	}
	public void setMethod(HttpVerb method){
		this.method = method;
	}
	
	public String getContentType(){
		return this.contentType;
	}
	public void setContentType(String contentType){
		this.contentType = contentType;
	}
	
	public String getPostData(){
		return this.postData;
	}
	public void setPostData(String postData){
		this.postData = postData;
	}
	
	public String SendRequest(){
		String response = "";
		try {
			response = SendRequest("");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
		return response;
	}
	
	
	public String SendRequest(String queryParams) throws ClientProtocolException, IOException {
		String httpResponse = "";
		String[] formEntity = null;
		StringBuilder sbResponse = new StringBuilder();
		CloseableHttpClient httpClient = null;
		CloseableHttpResponse response = null;
		HttpGet httpGet = null;
		HttpPost httpPost = null;
		List<BasicNameValuePair> userCredentials = null;
		try{
			HttpRequestRetryHandler myRetryHandler = new HttpRequestRetryHandler() {
				public boolean retryRequest(IOException exception, int executionCount,
						HttpContext context) {
					if (executionCount >= 5) {
						// Do not retry if over max retry count
						return false;
					}
					if (exception instanceof InterruptedIOException) {
						// Timeout
						return true;
					}
					if (exception instanceof UnknownHostException) {
						// Unknown host
						return true;
					}
					if (exception instanceof ConnectTimeoutException) {
						// Connection refused
						return true;
					}
					if (exception instanceof SSLException) {
						// SSL handshake exception
						return true;
					}
					HttpClientContext clientContext = HttpClientContext.adapt(context);
					HttpRequest request = clientContext.getRequest();
					boolean idempotent = !(request instanceof HttpEntityEnclosingRequest);
					if (idempotent) {
						// Retry if the request is considered idempotent
						return true;
					}
					return false;
				}
			};
				

			httpClient = HttpClients.custom()
			.setRetryHandler(myRetryHandler)
			.build();

			RequestConfig requestConfig = RequestConfig.custom()
			.setConnectTimeout(260 * 1000)
			.build();

			if(this.getMethod()==HttpVerb.GET)
			{
				httpGet = new HttpGet(this.getEndPoint() + queryParams);
				httpGet.addHeader("accept", "application/json");
				httpGet.setConfig(requestConfig);
				response = httpClient.execute(httpGet);
			}
			else if(this.getMethod()==HttpVerb.POST)
			{
				httpPost = new HttpPost(this.getEndPoint() + queryParams);
				httpPost.setConfig(requestConfig);
				if(this.getContentType().contains("urlencoded"))
				{
					formEntity = this.getPostData().split("&");
					userCredentials = new ArrayList<BasicNameValuePair>();
					userCredentials.add(new BasicNameValuePair(formEntity[0].split("=")[0], formEntity[0].split("=")[1]));
					userCredentials.add(new BasicNameValuePair(formEntity[1].split("=")[0], formEntity[1].split("=")[1]));
					httpPost.setEntity(new UrlEncodedFormEntity(userCredentials));
					response = httpClient.execute(httpPost);
				}
			}
			if (response.getStatusLine().getStatusCode() == HTTP_OK){
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent())); 
				String line = ""; 
				while ((line = rd.readLine()) != null) { 
					sbResponse.append(line); 
				}
				httpResponse = sbResponse.toString();
			}
			else
				httpResponse = "Request Failed";
		}
		catch(ClientProtocolException cpe)
		{
			throw cpe;
		}
		catch(IOException ex)
		{
			throw ex;
		}
		finally{
			if(response!=null)
				response.close();
			if(httpClient!=null)
				httpClient.close();
		}
		return httpResponse;
	}
	
}
