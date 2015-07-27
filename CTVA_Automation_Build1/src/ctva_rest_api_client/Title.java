
package ctva_rest_api_client;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Title{
   	private List<Delivery> delivery;
   	private String episode;
   	private String episodeName;
   	private List<String> genreName;
   	private String name;
   	private String originalDate;
   	private String runTime;
   	private String season;
   	private List<String> tVAdvisory;
   	private String tVRating;
   	private TitleCategories titleCategories;
   	private String titleId;
   	private String titleType;
   	private String mPAARating;
    private Advisory advisory;
    private String isAvailableViaVod;

    @JsonProperty("IsAvailableViaVod")
    public String getIsAvailableViaVod(){
		return this.isAvailableViaVod;
	}
	public void setIsAvailableViaVod(String isAvailableViaVod){
		this.isAvailableViaVod = isAvailableViaVod;
	}
	
	@JsonProperty("Advisory")
    public Advisory getAdvisory(){
		return this.advisory;
	}
	public void setAdvisory(Advisory advisory){
		this.advisory = advisory;
	}
	
	@JsonProperty("MPAARating")
	public String getMPAARating(){
		return this.mPAARating;
	}
	public void setMPAARating(String mPAARating){
		this.mPAARating = mPAARating;
	}
	
	@JsonProperty("Delivery")
 	public List<Delivery> getDelivery(){
		return this.delivery;
	}
	public void setDelivery(List<Delivery> delivery){
		this.delivery = delivery;
	}
	
	@JsonProperty("Episode")
 	public String getEpisode(){
		return this.episode;
	}
	public void setEpisode(String episode){
		this.episode = episode;
	}
	
	@JsonProperty("EpisodeName")
 	public String getEpisodeName(){
		return this.episodeName;
	}
	public void setEpisodeName(String episodeName){
		this.episodeName = episodeName;
	}
	
	@JsonProperty("GenreName")
 	public List<String> getGenreName(){
		return this.genreName;
	}
	public void setGenreName(List<String> genreName){
		this.genreName = genreName;
	}
	
	@JsonProperty("Name")
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
	
	@JsonProperty("OriginalDate")
 	public String getOriginalDate(){
		return this.originalDate;
	}
	public void setOriginalDate(String originalDate){
		this.originalDate = originalDate;
	}
	
	@JsonProperty("RunTime")
 	public String getRunTime(){
		return this.runTime;
	}
	public void setRunTime(String runTime){
		this.runTime = runTime;
	}
	
	@JsonProperty("Season")
 	public String getSeason(){
		return this.season;
	}
	public void setSeason(String season){
		this.season = season;
	}
	
	@JsonProperty("TVAdvisory")
 	public List<String> getTVAdvisory(){
		return this.tVAdvisory;
	}
	public void setTVAdvisory(List<String> tVAdvisory){
		this.tVAdvisory = tVAdvisory;
	}
	
	@JsonProperty("TVRating")
 	public String getTVRating(){
		return this.tVRating;
	}
	public void setTVRating(String tVRating){
		this.tVRating = tVRating;
	}
	
	@JsonProperty("TitleCategories")
 	public TitleCategories getTitleCategories(){
		return this.titleCategories;
	}
	public void setTitleCategories(TitleCategories titleCategories){
		this.titleCategories = titleCategories;
	}
	
	@JsonProperty("TitleId")
 	public String getTitleId(){
		return this.titleId;
	}
	public void setTitleId(String titleId){
		this.titleId = titleId;
	}
	
	@JsonProperty("TitleType")
 	public String getTitleType(){
		return this.titleType;
	}
	public void setTitleType(String titleType){
		this.titleType = titleType;
	}
}
