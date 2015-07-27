
package ctva_rest_api_client;

import org.codehaus.jackson.annotate.JsonProperty;

public class Transport{
   	private String format;
   	private String transportType;

   	@JsonProperty("Format")
 	public String getFormat(){
		return this.format;
	}
	public void setFormat(String format){
		this.format = format;
	}
	
	@JsonProperty("TransportType")
 	public String getTransportType(){
		return this.transportType;
	}
	public void setTransportType(String transportType){
		this.transportType = transportType;
	}
}
