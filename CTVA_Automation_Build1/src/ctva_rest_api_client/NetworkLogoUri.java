
package ctva_rest_api_client;

import org.codehaus.jackson.annotate.JsonProperty;

public class NetworkLogoUri{
   	private String defaultImage;
   	private String height;
   	private String imageType;
   	private String imageUri;
   	private String referenceType;
   	private String width;

   	@JsonProperty("DefaultImage")
 	public String getDefaultImage(){
		return this.defaultImage;
	}
	public void setDefaultImage(String defaultImage){
		this.defaultImage = defaultImage;
	}
	
	@JsonProperty("Height")
 	public String getHeight(){
		return this.height;
	}
	public void setHeight(String height){
		this.height = height;
	}
	
	@JsonProperty("ImageType")
 	public String getImageType(){
		return this.imageType;
	}
	public void setImageType(String imageType){
		this.imageType = imageType;
	}
	
	@JsonProperty("ImageUri")
 	public String getImageUri(){
		return this.imageUri;
	}
	public void setImageUri(String imageUri){
		this.imageUri = imageUri;
	}
	
	@JsonProperty("ReferenceType")
 	public String getReferenceType(){
		return this.referenceType;
	}
	public void setReferenceType(String referenceType){
		this.referenceType = referenceType;
	}
	
	@JsonProperty("Width")
 	public String getWidth(){
		return this.width;
	}
	public void setWidth(String width){
		this.width = width;
	}
}
