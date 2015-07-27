
package ctva_rest_api_client;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Guide{
   	private List<GuidePeriod> guidePeriod;
   	private String timeZoneOffset;

   	@JsonProperty("GuidePeriod")
 	public List<GuidePeriod> getGuidePeriod(){
		return this.guidePeriod;
	}
	public void setGuidePeriod(List<GuidePeriod> guidePeriod){
		this.guidePeriod = guidePeriod;
	}
	
	@JsonProperty("TimeZoneOffset")
 	public String getTimeZoneOffset(){
		return this.timeZoneOffset;
	}
	public void setTimeZoneOffset(String timeZoneOffset){
		this.timeZoneOffset = timeZoneOffset;
	}
}
