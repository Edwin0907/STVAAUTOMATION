/**
 * 
 */
package ctva_rest_api_client;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author velmurugan_k02
 *
 */

public class AuthResponse {
	private String username;
	private String token;
	private Long expiration;
	private String expirationCookie;
	private String fullname;
	private int issueDate;
	private String accountNumber;
	private String zipCode;
	
	@JsonProperty("Username")
	public String getUsername(){
		return this.username;
	}
	public void setUsername(String username){
		this.username = username;
	}
	
	@JsonProperty("Token")
	public String getToken(){
		return this.token;
	}
	public void setToken(String token){
		this.token = token;
	}
	
	@JsonProperty("Expiration")
	public Long getExpiration(){
		return this.expiration;
	}
	public void setExpiration(Long expiration){
		this.expiration = expiration;
	}
	
	@JsonProperty("ExpirationCookie")
	public String getExpirationCookie(){
		return this.expirationCookie;
	}
	public void setExpirationCookie(String expirationCookie){
		this.expirationCookie = expirationCookie;
	}
	
	@JsonProperty("Fullname")
	public String getFullname(){
		return this.fullname;
	}
	public void setFullname(String fullname){
		this.fullname = fullname;
	}
	
	@JsonProperty("IssueDate")
	public int getIssueDate(){
		return this.issueDate;
	}
	public void setIssueDate(int issueDate){
		this.issueDate = issueDate;
	}
	
	@JsonProperty("AccountNumber")
	public String getAccountNumber(){
		return this.accountNumber;
	}
	public void setAccountNumber(String accountNumber){
		this.accountNumber = accountNumber;
	}
	
	@JsonProperty("ZipCode")
	public String getZipCode(){
		return this.zipCode;
	}
	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}
}
