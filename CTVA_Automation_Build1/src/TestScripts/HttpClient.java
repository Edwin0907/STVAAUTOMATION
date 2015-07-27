package TestScripts;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import static java.net.HttpURLConnection.HTTP_OK;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;

import Batch.Globals;
public class HttpClient {
	 public void httpRequest(String Type) throws ClientProtocolException, IOException, JSONException
	    {
	        
	    String LoginSymp = Globals.LoginSymp;   
	    String LoginQueryParam =Globals.LoginQueryParam;    
	    String ContentType = Globals.ContentType;
	    String GetMethod = Globals.Method;
	    String UserName = Globals.UserName;
	    String Password = Globals.Password;
	    String CredString = "username="+UserName+"&password="+Password;
	    String GuideSymp = Globals.GuideSymp;
	    Path FinalPath= Paths.get(Globals.Xml_Path+Type+".xml");
	    Charset charset = StandardCharsets.UTF_8;
	    String Token = Globals.Token; 
		
	    String GuideToken = "?token="+Token;
	    String httpResponse="";
	    String[] formEntity= null;
	    StringBuilder sbResponse = new StringBuilder();
	    CloseableHttpClient httpClient = null;
	    CloseableHttpResponse response = null;
	    HttpGet httpGet = null;
	    HttpPost httpPost = null;
	    List<BasicNameValuePair> userCredentials = null;
	    try
	    {
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

				if("GET".equals(GetMethod))
				{
					httpGet = new HttpGet(GuideSymp + GuideToken);
					httpGet.addHeader("accept", "application/XML");
					httpGet.setConfig(requestConfig);
					response = httpClient.execute(httpGet);
	            }
				else if("POST".equals(GetMethod))
				{
					httpPost = new HttpPost(LoginSymp + LoginQueryParam);
					httpPost.addHeader("accept", "application/XML");
					httpPost.setConfig(requestConfig);
					if(ContentType.contains("urlencoded"))
					{
						formEntity = CredString.split("&");
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
	    File f = new File(FinalPath.toString());
	            if(f.exists())
	            {
	               f.delete();
	            }
	            f.createNewFile();
	    
	    //JSONObject Login_Auth = new JSONObject(httpResponse);
	     
	    //String Out_Xml = XML.toString(httpResponse);
	    //String content = new String(Out_Xml.getBytes(), charset);
	    Files.write(FinalPath, httpResponse.getBytes());
	    //return httpResponse;
		
	   
	    }
}
