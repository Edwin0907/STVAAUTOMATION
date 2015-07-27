
package ctva_rest_api_client;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Advisory
{
    private List<String> advisoryValue;

    @JsonProperty("AdvisoryValue")
 	public List<String> getAdvisoryValue(){
		return this.advisoryValue;
	}
	public void setAdvisoryValue(List<String> advisoryValue){
		this.advisoryValue = advisoryValue;
	}
}
