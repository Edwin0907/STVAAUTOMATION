
package ctva_rest_api_client;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Qualifiers
{
    private List<String> qualifier;

    @JsonProperty("Qualifier")
 	public List<String> getQualifier(){
		return this.qualifier;
	}
	public void setQualifiers(List<String> qualifier){
		this.qualifier = qualifier;
	}
}
