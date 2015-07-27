/**
 * 
 */
package ctva_rest_api_client;

import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.List;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * @author velmurugan_k02
 *
 */
public class CTVA_Request_Helper {
	
	public String UserName = "", Password = "";
	SymphonyRestClient restClient = null;
	private static Logger logger = Logger.getLogger(CTVA_Request_Helper.class);
	public CTVA_Request_Helper(String userName, String passWord){
		this.UserName = userName;
		this.Password = passWord;
	}
	
	public String GetAuthenticationToken(String AuthenticationService)
	{
		PropertyConfigurator.configure("log4j.properties");
		AuthenticationResponse authenticationResponse;
		String token = null, requestUrl = "", queryParams = "", httpResponse = "";
		requestUrl = AuthenticationService + "login";
		try{
			restClient = new SymphonyRestClient(requestUrl);
			queryParams = "username=" + this.UserName + "&password=" + this.Password;
			restClient.setMethod(HttpVerb.POST);
			restClient.setContentType("application/x-www-form-urlencoded");
			restClient.setPostData(queryParams);
			httpResponse = restClient.SendRequest();
			authenticationResponse = GetAuthResponseObject(httpResponse);
			
			token = authenticationResponse.getAuthResponse().getToken();
		}catch(Exception ex)
		{}
		return token;
	}
	
	public AuthenticationResponse GetAuthResponseObject(String json) throws JsonParseException, JsonMappingException, IOException{
	
		AuthenticationResponse authResponse = null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			//mapper.configure(DeserializationConfig.Feature.UNWRAP_ROOT_VALUE, true);
			authResponse = mapper.readValue(json, AuthenticationResponse.class);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}
		logger.info("Java Object created from JSON String ");
		logger.info("JSON String : " + json);
		logger.info("Java Object : " + authResponse);
		return authResponse;
	}

	public void ExecuteCTVARequest(String baseUrl, String symphonyService, String authenticationToken, HttpVerb httpVerb, String FileName) {
		
		PropertyConfigurator.configure("log4j.properties");
		Guide guideResponse = null;
		String requestUrl = "", queryParams = "", httpResponse = "";
		requestUrl = baseUrl + symphonyService + "catalog/video/guide";
		try{
			restClient = new SymphonyRestClient(requestUrl);
			queryParams = "?token=" + authenticationToken;
			restClient.setMethod(httpVerb);
			restClient.setContentType("application/json");
			httpResponse = restClient.SendRequest(queryParams);
			guideResponse = GetGuideObject(httpResponse);
			
			try {
				WriteGuideDataToExcel(guideResponse,FileName);
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
			
		}catch(Exception ex)
		{}
	}
	
	public Guide GetGuideObject(String json) throws JsonParseException, JsonMappingException, IOException{
		
		Guide guideResponse = null;
		try{
			ObjectMapper mapper = new ObjectMapper();
			guideResponse = mapper.readValue(json, Guide.class);
		}
		catch(Exception ex)
		{
			System.out.println(ex.getStackTrace());
		}
		logger.info("Java Object created from JSON String ");
		logger.info("JSON String : " + json);
		logger.info("Java Object : " + guideResponse);
		
		return guideResponse;
	}
	
	public void WriteGuideDataToExcel(Guide guideResponse,String FileName) throws SQLException{//Guide guideResponse
		
		String sqlQuery = "", networkCategory = "", titleCategory = "", genreName = "", tvAdvisory = "", advisory = "", qualifiers = "";
		Connection excelConn = null;
		PreparedStatement StmtTimeZoneOffset = null, StmtGuidePeriod=null, StmtChannelLineup = null, StmtNetworkLogoUri = null, StmtTitle = null, StmtTransport = null, StmtDelivery = null;
		try
		{
			System.out.println("In Here buddy");
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			excelConn = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+FileName+";ReadOnly=False");
			//excelConn.setAutoCommit(false);
			
			StmtTimeZoneOffset = excelConn.prepareStatement("INSERT INTO [TimeZoneOffset$] (TimeZoneOffset) VALUES (?)");
			StmtTimeZoneOffset.setString(1, guideResponse.getTimeZoneOffset());
			StmtTimeZoneOffset.executeUpdate();
			excelConn.commit();
			
			sqlQuery = "INSERT INTO [GuidePeriod$] (TotalSize,StartDate,EndDate,StartTime,EndTime,Duration) VALUES (?,?,?,?,?,?)";
			StmtGuidePeriod = excelConn.prepareStatement(sqlQuery);
			
			sqlQuery = 	"INSERT INTO [ChannelLineup$] (ChannelId,ChannelNumber,Entitled,Format,Network,NetworkCode,SDHDPair,Streamable,StreamableLocation,VODFolderID,CallSignDisplayLabel,NetworkCategory,AffiliateChannelId,AffiliateName,AffiliateCallSign) " +
			"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			StmtChannelLineup = excelConn.prepareStatement(sqlQuery);
			
			sqlQuery = 	"INSERT INTO [NetworkLogoUri$] (ChannelId,DefaultImage,ImageUri,ImageType,Height,Width,ReferenceType) " +
			"VALUES (?,?,?,?,?,?,?)";
			StmtNetworkLogoUri = excelConn.prepareStatement(sqlQuery);
			
			sqlQuery = 	"INSERT INTO [Title$] (ChannelId,TitleId,Name,TitleType,TitleCategories,Episode,EpisodeName,GenreNames,OriginalDate,RunTime,TVAdvisory,TVRating,MPAARating,Advisory,IsAvailableViaVod) " +
			"VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			StmtTitle = excelConn.prepareStatement(sqlQuery);
			
			sqlQuery = 	"INSERT INTO [Delivery$] (TitleId,DeliveryId,DeliveryType,StartDate,EndDate,Duration,GuidePeriodInMinutes,New,Qualifiers) " +
			"VALUES (?,?,?,?,?,?,?,?,?)";
			StmtDelivery = excelConn.prepareStatement(sqlQuery);
			
			sqlQuery = 	"INSERT INTO [Transport$] (TitleId,TransportType,TransportFormat) " +
			"VALUES (?,?,?)";
			StmtTransport = excelConn.prepareStatement(sqlQuery);

			for (int guideIndex = 0; guideIndex < guideResponse.getGuidePeriod().size(); guideIndex++)
			{
				GuidePeriod gPeriod = guideResponse.getGuidePeriod().get(guideIndex);
				if(gPeriod != null)
				{
					StmtGuidePeriod.setString(1, ((gPeriod!=null && gPeriod.getTotalSize()!=null)? gPeriod.getTotalSize() : ""));
					StmtGuidePeriod.setString(2, ((gPeriod!=null && gPeriod.getStartDate()!=null)? gPeriod.getStartDate() : ""));
					StmtGuidePeriod.setString(3, ((gPeriod!=null && gPeriod.getEndDate()!=null)? gPeriod.getEndDate() : ""));
					StmtGuidePeriod.setString(4, ((gPeriod!=null && gPeriod.getStartTime()!=null)? gPeriod.getStartTime() : ""));
					StmtGuidePeriod.setString(5, ((gPeriod!=null && gPeriod.getEndTime()!=null)? gPeriod.getEndTime() : ""));
					StmtGuidePeriod.setString(6, ((gPeriod!=null && gPeriod.getDuration()!=null)? gPeriod.getDuration() : ""));
					StmtGuidePeriod.executeUpdate();
					excelConn.commit();
					
					List<ChannelLineup> lstChannelLineUp = gPeriod.getChannelLineup();
					for (int channelIndex = 0; channelIndex < lstChannelLineUp.size(); channelIndex++)
					{
						ChannelLineup cLineUp = lstChannelLineUp.get(channelIndex);
						if(cLineUp != null)
						{
							Channel channel = cLineUp.getChannel();
							Affiliate affiliate = channel.getAffiliate();
							List<NetworkLogoUri> lstNWLogoUri = channel.getNetworkLogoUri();
							StmtChannelLineup.setString(1, ((channel!=null && channel.getChannelId()!=null)? channel.getChannelId() : ""));
							StmtChannelLineup.setString(2, ((channel!=null && channel.getChannelNumber()!=null)? channel.getChannelNumber() : ""));
							StmtChannelLineup.setString(3, ((channel!=null && channel.getEntitled()!=null)? channel.getEntitled() : ""));
							StmtChannelLineup.setString(4, ((channel!=null && channel.getFormat()!=null)? channel.getFormat() : ""));
							StmtChannelLineup.setString(5, ((channel!=null && channel.getNetwork()!=null)? channel.getNetwork() : ""));
							StmtChannelLineup.setString(6, ((channel!=null && channel.getNetworkCode()!=null)? channel.getNetworkCode() : ""));
							StmtChannelLineup.setString(7, ((channel!=null && channel.getSDHDPair()!=null)? channel.getSDHDPair() : ""));
							StmtChannelLineup.setString(8, ((channel!=null && channel.getStreamable()!=null)? channel.getStreamable() : ""));
							StmtChannelLineup.setString(9, ((channel!=null && channel.getStreamableLocation()!=null)? channel.getStreamableLocation() : ""));
							StmtChannelLineup.setString(10, ((channel!=null && channel.getVodFolderId()!=null)? channel.getVodFolderId() : ""));
							StmtChannelLineup.setString(11, ((channel!=null && channel.getCallSignDisplayLabel()!=null)? channel.getCallSignDisplayLabel() : ""));
							if(channel.getNetworkCategories() != null){
								if(channel.getNetworkCategories().getNetworkCategory()!=null){
									for (int netCatIndex = 0; netCatIndex < channel.getNetworkCategories().getNetworkCategory().size(); netCatIndex++)
									{
										String categoryByIndex = channel.getNetworkCategories().getNetworkCategory().get(netCatIndex);
										if(!categoryByIndex.isEmpty() && categoryByIndex!= null)
											networkCategory += categoryByIndex + ((netCatIndex == channel.getNetworkCategories().getNetworkCategory().size() - 1)? "" : "|");
									}
								}
							}
							StmtChannelLineup.setString(12, networkCategory);
							StmtChannelLineup.setString(13, ((affiliate!=null && affiliate.getChannelId()!=null)? affiliate.getChannelId() : ""));
							StmtChannelLineup.setString(14, ((affiliate!=null && affiliate.getName()!=null)? affiliate.getName() : ""));
							StmtChannelLineup.setString(15, ((affiliate!=null && affiliate.getCallSign()!=null)? affiliate.getCallSign() : ""));
							StmtChannelLineup.executeUpdate();
							excelConn.commit();
							
							if(lstNWLogoUri!=null){
								for(int nwLogoUriIndex = 0; nwLogoUriIndex < lstNWLogoUri.size(); nwLogoUriIndex++)
								{
									NetworkLogoUri nwLogoUri = lstNWLogoUri.get(nwLogoUriIndex);
								
									StmtNetworkLogoUri.setString(1, channel.getChannelId());
									StmtNetworkLogoUri.setString(2, ((nwLogoUri!=null && nwLogoUri.getDefaultImage()!=null)? nwLogoUri.getDefaultImage() : ""));
									StmtNetworkLogoUri.setString(3, ((nwLogoUri!=null && nwLogoUri.getImageUri()!=null)? nwLogoUri.getImageUri() : ""));
									StmtNetworkLogoUri.setString(4, ((nwLogoUri!=null && nwLogoUri.getImageType()!=null)? nwLogoUri.getImageType() : ""));
									StmtNetworkLogoUri.setString(5, ((nwLogoUri!=null && nwLogoUri.getHeight()!=null)? nwLogoUri.getHeight() : ""));
									StmtNetworkLogoUri.setString(6, ((nwLogoUri!=null && nwLogoUri.getHeight()!=null)? nwLogoUri.getHeight() : ""));
									StmtNetworkLogoUri.setString(7, ((nwLogoUri!=null && nwLogoUri.getReferenceType()!=null)? nwLogoUri.getReferenceType() : ""));
									StmtNetworkLogoUri.executeUpdate();
									excelConn.commit();
								}
							}
							
							List<Title> lstTitle = cLineUp.getTitle();
							if(lstTitle!=null){
								for(int titleIndex = 0; titleIndex < lstTitle.size(); titleIndex++)
								{
									Title title = lstTitle.get(titleIndex);
									if(title != null)
									{
										StmtTitle.setString(1, channel.getChannelId());
										StmtTitle.setString(2, ((title!=null && title.getTitleId()!=null)? title.getTitleId() : ""));
										StmtTitle.setString(3, ((title!=null && title.getName()!=null)? title.getName() : ""));
										StmtTitle.setString(4, ((title!=null && title.getTitleType()!=null)? title.getTitleType() : ""));
										if(title.getTitleCategories()!=null){
											if(title.getTitleCategories().getCategory()!=null){
												List<Object> titleCategories = Arrays.asList(title.getTitleCategories().getCategory());
												if(titleCategories != null){
													for (int titleCatIndex = 0; titleCatIndex < titleCategories.size(); titleCatIndex++)
													{
														titleCategory += titleCategories.get(titleCatIndex).toString() + ((titleCatIndex == titleCategories.size() - 1)? "" : "|");
													}
												}
											}
										}
										StmtTitle.setString(5, titleCategory);
										StmtTitle.setString(6, ((title!=null && title.getEpisode()!=null)? title.getEpisode() : ""));
										StmtTitle.setString(7, ((title!=null && title.getEpisodeName()!=null)? title.getEpisodeName() : ""));
										if(title.getGenreName()!=null){
											for (int genreNameIndex = 0; genreNameIndex < title.getGenreName().size(); genreNameIndex++)
											{
												genreName += title.getGenreName().get(genreNameIndex) + ((genreNameIndex == title.getGenreName().size() - 1)? "" : "|");
											}
										}
										StmtTitle.setString(8, genreName);
										StmtTitle.setString(9, ((title!=null && title.getOriginalDate()!=null)? title.getOriginalDate() : ""));
										StmtTitle.setString(10, ((title!=null && title.getRunTime()!=null)? title.getRunTime() : ""));
										if(title.getTVAdvisory()!=null){
											for (int tvAdvsioryIndex = 0; tvAdvsioryIndex < title.getTVAdvisory().size(); tvAdvsioryIndex++)
											{
												tvAdvisory += title.getTVAdvisory().get(tvAdvsioryIndex) + ((tvAdvsioryIndex == title.getTVAdvisory().size() - 1)? "" : "|");
											}
										}
										StmtTitle.setString(11, tvAdvisory);
										StmtTitle.setString(12, ((title!=null && title.getTVRating()!=null)? title.getTVRating() : ""));
										StmtTitle.setString(13, ((title!=null && title.getMPAARating()!=null)? title.getMPAARating() : ""));
										if(title.getAdvisory()!=null){
											if(title.getAdvisory().getAdvisoryValue()!=null){
												for (int advisoryIndex = 0; advisoryIndex < title.getAdvisory().getAdvisoryValue().size(); advisoryIndex++)
												{
													advisory += title.getAdvisory().getAdvisoryValue().get(advisoryIndex) + ((advisoryIndex == title.getAdvisory().getAdvisoryValue().size() - 1)? "" : "|");
												}
											}
										}
										StmtTitle.setString(14, advisory);
										StmtTitle.setString(15, ((title!=null && title.getIsAvailableViaVod()!=null)? title.getIsAvailableViaVod() : ""));
										StmtTitle.executeUpdate();
										excelConn.commit();
										
										List<Delivery> lstDelivery = title.getDelivery();
										if(lstDelivery!=null){
											for(int deliveryIndex = 0; deliveryIndex < lstDelivery.size(); deliveryIndex++)
											{
												Delivery delivery = lstDelivery.get(deliveryIndex);
												if (delivery != null){
													StmtDelivery.setString(1, title.getTitleId());
													StmtDelivery.setString(2, ((delivery!=null && delivery.getDeliveryId()!=null)? delivery.getDeliveryId() : ""));
													StmtDelivery.setString(3, ((delivery!=null && delivery.getDeliveryType()!=null)? delivery.getDeliveryType() : ""));
													StmtDelivery.setString(4, ((delivery!=null && delivery.getStartDate()!=null)? delivery.getStartDate() : ""));
													StmtDelivery.setString(5, ((delivery!=null && delivery.getEndDate()!=null)? delivery.getEndDate() : ""));
													StmtDelivery.setString(6, ((delivery!=null && delivery.getDuration()!=null)? delivery.getDuration() : ""));
													StmtDelivery.setString(7, ((delivery!=null && delivery.getGuidePeriodMinutes()!=null)? delivery.getGuidePeriodMinutes() : ""));
													StmtDelivery.setString(8, ((delivery!=null && delivery.getNew()!=null)? delivery.getNew() : ""));
													if(delivery.getQualifiers()!=null){
														if(delivery.getQualifiers().getQualifier()!=null){
															for (int qualifierIndex = 0; qualifierIndex < delivery.getQualifiers().getQualifier().size(); qualifierIndex++)
															{
																qualifiers += delivery.getQualifiers().getQualifier().get(qualifierIndex) + ((qualifierIndex == delivery.getQualifiers().getQualifier().size() - 1)? "" : "|");
															}
														}
													}
													StmtDelivery.setString(9, qualifiers);
													StmtDelivery.executeUpdate();
													excelConn.commit();
													
													List<Transport> lstTransport = delivery.getTransport();
													if(lstTransport!=null){
														for(int transportIndex = 0; transportIndex < lstTransport.size(); transportIndex++)
														{
															Transport transport = lstTransport.get(transportIndex);
															if(transport!=null){
																StmtTransport.setString(1, delivery.getDeliveryId());
																StmtTransport.setString(2, ((transport!=null && transport.getTransportType()!=null)? transport.getTransportType() : ""));
																StmtTransport.setString(3, ((transport!=null && transport.getFormat()!=null)? transport.getFormat() : ""));
																StmtTransport.executeUpdate();
																excelConn.commit();
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
						networkCategory = "";
						titleCategory = "";
						genreName = "";
						tvAdvisory = "";
						advisory = "";
						qualifiers = "";
					}
				}
			}
			
		} catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		} catch (SQLException e) {
			
			e.printStackTrace();
			if (excelConn != null) {
	            try {
	                System.err.print("Transaction is being rolled back");
	                excelConn.rollback();
	            } catch(SQLException excep) {
	            	excep.printStackTrace();
	            }
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		finally{
			if (StmtTimeZoneOffset!=null)
				StmtTimeZoneOffset.close();
			if (StmtGuidePeriod!=null)
				StmtGuidePeriod.close();
			if (StmtChannelLineup!=null)
				StmtChannelLineup.close();
			if (StmtNetworkLogoUri!=null)
				StmtNetworkLogoUri.close();
			if (StmtTitle!=null)
				StmtTitle.close();
			if (StmtTransport!=null)
				StmtTransport.close();
			if (StmtDelivery!=null)
				StmtDelivery.close();
			if (excelConn!=null)
				excelConn.close();
		}
	}
}
