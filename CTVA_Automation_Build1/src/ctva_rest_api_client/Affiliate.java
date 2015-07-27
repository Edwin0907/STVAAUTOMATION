
package ctva_rest_api_client;

import org.codehaus.jackson.annotate.JsonProperty;

public class Affiliate
{
	private String channelId;
	private String name;
	private String callSign;
	
	@JsonProperty("ChannelId")
	public String getChannelId(){
		return this.channelId;
	}
	public void setChannelId(String channelId){
		this.channelId = channelId;
	}
	
	@JsonProperty("Name")
	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	@JsonProperty("CallSign")
	public String getCallSign(){
		return this.callSign;
	}
	public void setCallSign(String callSign){
		this.callSign = callSign;
	}
}
