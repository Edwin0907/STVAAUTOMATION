
package ctva_rest_api_client;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Delivery{
   	private String deliveryId;
   	private String deliveryType;
   	private String duration;
   	private String endDate;
   	private String guidePeriodMinutes;
   	private String _new;
   	private String startDate;
   	private String titleId;
   	private List<Transport> transport;
   	private Qualifiers qualifiers;

   	@JsonProperty("Qualifiers")
   	public Qualifiers getQualifiers(){
		return this.qualifiers;
	}
	public void setQualifiers(Qualifiers qualifiers){
		this.qualifiers = qualifiers;
	}
	
	@JsonProperty("DeliveryId")
 	public String getDeliveryId(){
		return this.deliveryId;
	}
	public void setDeliveryId(String deliveryId){
		this.deliveryId = deliveryId;
	}
	
	@JsonProperty("DeliveryType")
 	public String getDeliveryType(){
		return this.deliveryType;
	}
	public void setDeliveryType(String deliveryType){
		this.deliveryType = deliveryType;
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
	
	@JsonProperty("GuidePeriodMinutes")
 	public String getGuidePeriodMinutes(){
		return this.guidePeriodMinutes;
	}
	public void setGuidePeriodMinutes(String guidePeriodMinutes){
		this.guidePeriodMinutes = guidePeriodMinutes;
	}
	
	@JsonProperty("New")
 	public String getNew(){
		return this._new;
	}
	public void setNew(String _new){
		this._new = _new;
	}
	
	@JsonProperty("StartDate")
 	public String getStartDate(){
		return this.startDate;
	}
	public void setStartDate(String startDate){
		this.startDate = startDate;
	}
	
	@JsonProperty("TitleId")
 	public String getTitleId(){
		return this.titleId;
	}
	public void setTitleId(String titleId){
		this.titleId = titleId;
	}
	
	@JsonProperty("Transport")
 	public List<Transport> getTransport(){
		return this.transport;
	}
	public void setTransport(List<Transport> transport){
		this.transport = transport;
	}
}
