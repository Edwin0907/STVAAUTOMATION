
package ctva_rest_api_client;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class NetworkCategories
{
    private List<String> networkCategory;

    @JsonProperty("NetworkCategory")
 	public List<String> getNetworkCategory(){
		return this.networkCategory;
	}
	public void setNetworkCategory(List<String> networkCategory){
		this.networkCategory = networkCategory;
	}
}
