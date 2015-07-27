
package ctva_rest_api_client;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Channel{
   	
   	private String channelId;
   	private String channelNumber;
   	private String entitled;
   	private String format;
   	private String network;
   	private String networkCode;
   	private List<NetworkLogoUri> networkLogoUri;
   	private String sDHDPair;
   	private String streamable;
   	private String streamableLocation;
   	private String vodFolderId;
   	private Affiliate affiliate;
   	private String callSignDisplayLabel;
   	private NetworkCategories networkCategories;

   	@JsonProperty("NetworkCategories")
    public NetworkCategories getNetworkCategories(){
		return this.networkCategories;
	}
	public void setNetworkCategories(NetworkCategories networkCategories){
		this.networkCategories = networkCategories;
	}
	
	@JsonProperty("CallSignDisplayLabel")
   	public String getCallSignDisplayLabel(){
		return this.callSignDisplayLabel;
	}
	public void setallSignDisplayLabel(String callSignDisplayLabel){
		this.callSignDisplayLabel = callSignDisplayLabel;
	}
	
	@JsonProperty("Affiliate")
	public Affiliate getAffiliate(){
		return this.affiliate;
	}
	public void setAffiliate(Affiliate affiliate){
		this.affiliate = affiliate;
	}
	
	@JsonProperty("ChannelId")
 	public String getChannelId(){
		return this.channelId;
	}
	public void setChannelId(String channelId){
		this.channelId = channelId;
	}
	
	@JsonProperty("ChannelNumber")
 	public String getChannelNumber(){
		return this.channelNumber;
	}
	public void setChannelNumber(String channelNumber){
		this.channelNumber = channelNumber;
	}
	
	@JsonProperty("Entitled")
 	public String getEntitled(){
		return this.entitled;
	}
	public void setEntitled(String entitled){
		this.entitled = entitled;
	}
	
	@JsonProperty("Format")
 	public String getFormat(){
		return this.format;
	}
	public void setFormat(String format){
		this.format = format;
	}
	
	@JsonProperty("Network")
 	public String getNetwork(){
		return this.network;
	}
	public void setNetwork(String network){
		this.network = network;
	}
	
	@JsonProperty("NetworkCode")
 	public String getNetworkCode(){
		return this.networkCode;
	}
	public void setNetworkCode(String networkCode){
		this.networkCode = networkCode;
	}
	
	@JsonProperty("NetworkLogoUri")
 	public List<NetworkLogoUri> getNetworkLogoUri(){
		return this.networkLogoUri;
	}
	public void setNetworkLogoUri(List<NetworkLogoUri> networkLogoUri){
		this.networkLogoUri = networkLogoUri;
	}
	
	@JsonProperty("SDHDPair")
 	public String getSDHDPair(){
		return this.sDHDPair;
	}
	public void setSDHDPair(String sDHDPair){
		this.sDHDPair = sDHDPair;
	}
	
	@JsonProperty("Streamable")
 	public String getStreamable(){
		return this.streamable;
	}
	public void setStreamable(String streamable){
		this.streamable = streamable;
	}
	
	@JsonProperty("StreamableLocation")
 	public String getStreamableLocation(){
		return this.streamableLocation;
	}
	public void setStreamableLocation(String streamableLocation){
		this.streamableLocation = streamableLocation;
	}
	
	@JsonProperty("VodFolderId")
 	public String getVodFolderId(){
		return this.vodFolderId;
	}
	public void setVodFolderId(String vodFolderId){
		this.vodFolderId = vodFolderId;
	}
}
