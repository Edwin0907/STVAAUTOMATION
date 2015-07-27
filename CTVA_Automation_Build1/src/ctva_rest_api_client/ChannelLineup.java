
package ctva_rest_api_client;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class ChannelLineup{
   	private Channel channel;
   	private List<Title> title;

   	@JsonProperty("Channel")
 	public Channel getChannel(){
		return this.channel;
	}
	public void setChannel(Channel channel){
		this.channel = channel;
	}
	
	@JsonProperty("Title")
 	public List<Title> getTitle(){
		return this.title;
	}
	public void setTitle(List<Title> title){
		this.title = title;
	}
}
