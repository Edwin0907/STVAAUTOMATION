
package ctva_rest_api_client;

import org.codehaus.jackson.annotate.JsonProperty;

public class TitleCategories{
   	private Object category;

   	@JsonProperty("Category")
 	public Object getCategory(){
		return this.category;
	}
	public void setCategory(Object category){
		this.category = category;
	}
}
