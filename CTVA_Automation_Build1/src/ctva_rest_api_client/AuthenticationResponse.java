package ctva_rest_api_client;

import org.codehaus.jackson.annotate.JsonProperty;


public class AuthenticationResponse {
	
	private AuthResponse authResponse;
	
	@JsonProperty("AuthResponse")
	public AuthResponse getAuthResponse(){
		return this.authResponse;
	}
	
	public void setAuthResponse(AuthResponse authResponse){
		this.authResponse = authResponse;
	}
}
