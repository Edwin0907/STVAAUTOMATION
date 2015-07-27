
package ctva_rest_api_client;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class GuidePeriod{
   	private List<ChannelLineup> channelLineup;
   	private String duration;
   	private String endDate;
   	private String endTime;
   	private String startDate;
   	private String startTime;
   	private String totalSize;

   	@JsonProperty("ChannelLineup")
 	public List<ChannelLineup> getChannelLineup(){
		return this.channelLineup;
	}
	public void setChannelLineup(List<ChannelLineup> channelLineup){
		this.channelLineup = channelLineup;
	}
	
	@JsonProperty("Duration")
 	public String getDuration(){
		return this.duration;
	}
	public void setDuration(String duration){
		this.duration = duration;
	}
	
	@JsonProperty("EndDate")
 	public String getEndDate(){
		return this.endDate;
	}
	public void setEndDate(String endDate){
		this.endDate = endDate;
	}
	
	@JsonProperty("EndTime")
 	public String getEndTime(){
		return this.endTime;
	}
	public void setEndTime(String endTime){
		this.endTime = endTime;
	}
	
	@JsonProperty("StartDate")
 	public String getStartDate(){
		return this.startDate;
	}
	public void setStartDate(String startDate){
		this.startDate = startDate;
	}
	
	@JsonProperty("StartTime")
 	public String getStartTime(){
		return this.startTime;
	}
	public void setStartTime(String startTime){
		this.startTime = startTime;
	}
	
	@JsonProperty("TotalSize")
 	public String getTotalSize(){
		return this.totalSize;
	}
	public void setTotalSize(String totalSize){
		this.totalSize = totalSize;
	}
}
