/**
 * 
 */
package Guide_Json;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * @author Edwin_Paul
 *
 */



public class GuideValidations {
	static Logger log = Logger.getLogger(GuideValidations.class.getName());
	Connection excelConn = null;
	
	public boolean ValidateByFilter(String filterType, String filterValue, int numberofChannels, String FileName) throws SQLException
	{
		PropertyConfigurator.configure("log4j.properties");
		PreparedStatement StmtSelectChannels = null;
		String sqlQuery = "", selectClause="", whereClause="", groupByClause="";
		int recordCount =0;
		boolean isValid = false;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String t=s.format(c.getTime());
		
		log.info("Validating Channels By Filter Type...Started at : " + t);
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			excelConn = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ FileName +";ReadOnly=True");
			selectClause = "Select Count(ChannelNumber) As RecordCount From [ChannelLineup$] ";
			whereClause = "Where " + filterType + " Like ";
			whereClause += "'" + filterValue + "%'"; 
			groupByClause = " Group By " + filterType; 
			
			sqlQuery = selectClause + whereClause + groupByClause;
			StmtSelectChannels = excelConn.prepareStatement(sqlQuery);
			ResultSet rs = StmtSelectChannels.executeQuery();
			while(rs.next()) {
				recordCount += rs.getInt("RecordCount");
			}
			rs.close();
			
			isValid=((recordCount == numberofChannels)?true:false);
		}
		catch(SQLException sqlex){
			sqlex.printStackTrace();
		}
		catch(Exception ex){
			System.out.println("Throw Error : "+ ex);
		}
		finally {
			if (StmtSelectChannels!=null)
				StmtSelectChannels.close();
			if (excelConn!=null)
				excelConn.close();
		}
		t=s.format(c.getTime());
		log.info("Validating Channels By Filter Type...Ended at : " + t);
		if (isValid)
			log.info("Validation Comments : Filtered Channels Count is Equivalent to Number of Channels returned by Guide API Call");
		else
			log.error("Validation Comments : Filtered Channels Count is not Equivalent to Number of Channels returned by Guide API Call");
		return isValid;
	}
	
	public boolean ValidateChannels(String[] channelIds,String FileName) throws SQLException
	{
		PropertyConfigurator.configure("log4j.properties");
		PreparedStatement StmtSelectChannels = null;
		String sqlQuery = "", selectClause="", whereClause="", groupByClause="";
		boolean isValid=false;
		int numberofChannels=0;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String t=s.format(c.getTime());
		log.info("Validating Channels By Channel Number...Started at : " + t);
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			excelConn = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ FileName + ";ReadOnly=True");
			int recordCount = channelIds.length;
			selectClause = "Select Count(ChannelNumber) As RecordCount From [ChannelLineup$] ";
			whereClause = "Where ChannelNumber In (";
			
			for (int channelIndex=0; channelIndex < channelIds.length; channelIndex++)
			{
				whereClause += "'" + channelIds[channelIndex]+ "'" + ((channelIndex < channelIds.length-1)? ",": "");
			}
			
			whereClause += ") "; 
			groupByClause = "Group By ChannelNumber";
			
			sqlQuery = selectClause + whereClause + groupByClause;
			StmtSelectChannels = excelConn.prepareStatement(sqlQuery);
			ResultSet rs = StmtSelectChannels.executeQuery();
			
			while(rs.next()) {
				numberofChannels += rs.getInt("RecordCount");
			}
			rs.close();
			
			isValid=((recordCount == numberofChannels)?true:false);
		}
		catch(SQLException sqlex){
			sqlex.printStackTrace();
		}
		catch(Exception ex){
			System.out.println("Throw Error : "+ ex);
		}
		finally {
			if (StmtSelectChannels!=null)
				StmtSelectChannels.close();
			if (excelConn!=null)
				excelConn.close();
		}
		t=s.format(c.getTime());
		log.info("Validating Channels By Channel Number...Ended at : " + t);
		if (isValid)
			log.info("Validation Comments : Filtered ChannelNumbers are Equivalent to ChannelNumbers returned by Guide API Call");
		else
			log.error("Validation Comments : Filtered ChannelNumbers are Equivalent to ChannelNumbers returned by Guide API Call");
		
		return isValid;
	}
	
	public boolean ValidateChannelLineup(String userName, String channelLineUpMapFile, String channelLineupFile, String guideFileName, String[] channelIds ) throws SQLException
	{
		PropertyConfigurator.configure("log4j.properties");
		PreparedStatement StmtValidateGuideResp = null, StmtSelectUserLineup = null, StmtSelectChannels = null, StmtValidateChannelLineup = null;
		String sqlQuery = "", selectClause="", whereClause="", groupByClause="", strUserLineup="", strChannelLineup = "", invalidChannels = "";
		int /*recordCount =0,*/ numberOfChannels = 0, invalidRecordCount = 0, totalChannelLineupGuide = 0;// totalMarkerChannelLineup = 0;
		boolean isValid = false;
		Calendar c = Calendar.getInstance();
		SimpleDateFormat s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		String t=s.format(c.getTime());
		
		log.info("Validating Channels By Channel Lineup...Started at : " + t);
		try{
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			
			////Summary : 	The following section fetches all the Channel Numbers from 
			////			the UI and validates it against the Channel Numbers returned 
			////			from the Guide response 
			////			(Condition : UI Channel Count == Guide Response Channels count)
			excelConn = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ guideFileName + ";ReadOnly=True");
			//recordCount = channelIds.length;
			selectClause = "Select Count(ChannelNumber) As RecordCount From [ChannelLineup$] ";
			whereClause = "Where ChannelNumber Not In (";
			
			for (int channelIndex=0; channelIndex < channelIds.length; channelIndex++)
			{
				whereClause += "'" + channelIds[channelIndex]+ "'" + ((channelIndex < channelIds.length-1)? ",": "");
			}
			
			whereClause += ") "; 
			groupByClause = "Group By ChannelNumber";
			
			sqlQuery = selectClause + whereClause + groupByClause;
			StmtValidateGuideResp = excelConn.prepareStatement(sqlQuery);
			ResultSet rs = StmtValidateGuideResp.executeQuery();
			
			while(rs.next()) {
				numberOfChannels += rs.getInt("RecordCount");
			}
			rs.close();
			if (excelConn != null) excelConn.close();
			
			////Summary : 	The following section fetches the Line up from 
			////			UserLineupMap sheet 
			excelConn = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ channelLineUpMapFile +";ReadOnly=True");
			selectClause = "Select Lineup From [RegionUserMap$] ";
			whereClause = "Where Username Like ";
			whereClause += "'" + userName + "%'"; 
			
			sqlQuery = selectClause + whereClause;
			StmtSelectUserLineup = excelConn.prepareStatement(sqlQuery);
			ResultSet rsUserLineup = StmtSelectUserLineup.executeQuery();
			while(rsUserLineup.next()) {
				strUserLineup = rsUserLineup.getString("Lineup");
			}
			rsUserLineup.close();
			if (excelConn != null) excelConn.close();
			
			////Summary : 	The following section fetches all the Channel Numbers  
			////			from the Guide response excel sheet 
			excelConn = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ guideFileName +";ReadOnly=True");
			selectClause = "Select ChannelNumber From [ChannelLineup$]";
			sqlQuery = selectClause;
			StmtSelectChannels = excelConn.prepareStatement(sqlQuery);
			ResultSet rsChannels = StmtSelectChannels.executeQuery();
			
			while(rsChannels.next()){
				strChannelLineup += "'" + rsChannels.getString("ChannelNumber")+ "'" + ",";
				totalChannelLineupGuide++;
			}
			strChannelLineup = strChannelLineup.substring(0, strChannelLineup.length()-1);
			System.out.println("Channel Lineups For User from Guide Response: \n" + strChannelLineup);
			System.out.println("Total Channel Lineups From Guide Response: " + totalChannelLineupGuide);
			
			rsChannels.close();
			if (excelConn != null) excelConn.close();
			
			////Summary : 	The following section fetches all the Channel Numbers that   
			////			are missed out or invalid from the market Channel Line up excel sheet
			////			for the user who belongs to the Line up
			excelConn = DriverManager.getConnection( "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls, *.xlsx, *.xlsm, *.xlsb)};DBQ="+ channelLineupFile +";ReadOnly=True");
			selectClause = "Select distinct(Channel), [Service Level] As Package From [Sheet1$] ";
			whereClause = "Where Channel Not In (";
			whereClause += strChannelLineup + ") ";
			whereClause += "And Lineup Like ";
			whereClause += "'" + strUserLineup + "%'"; 
			
			sqlQuery = selectClause + whereClause;
			StmtValidateChannelLineup = excelConn.prepareStatement(sqlQuery);
			ResultSet rsChannelLineup = StmtValidateChannelLineup.executeQuery();
			
			while(rsChannelLineup.next()){
				invalidChannels += "'" + rsChannelLineup.getString("Channel")+ "-" + rsChannelLineup.getString("Package") + "'" + "|";
				invalidRecordCount++;
			}
			rsChannelLineup.close();
			if (excelConn != null) excelConn.close();
			
			System.out.println("Channel Lineups in Market Channel Lineup but not in the Guide Response: \n" + invalidChannels);
			System.out.println("Total Channel Lineups not in the Guide Response: " + invalidRecordCount);
			isValid=((invalidRecordCount==0 && numberOfChannels == 0)?true:false);
		}
		catch(SQLException sqlex){
			sqlex.printStackTrace();
		}
		catch(Exception ex){
			System.out.println("Throw Error : "+ ex);
		}
		finally {
			if (StmtValidateGuideResp!=null)
				StmtValidateGuideResp.close();
			if (StmtSelectUserLineup!=null)
				StmtSelectUserLineup.close();
			if (StmtSelectChannels!=null)
				StmtSelectChannels.close();
			if (StmtValidateChannelLineup!=null)
				StmtValidateChannelLineup.close();
			if (excelConn!=null)
				excelConn.close();
		}
		t=s.format(c.getTime());
		log.info("Validating Channels By Filter Type...Ended at : " + t);
		if (isValid)
			log.info("Validation Comments : Channel Line up of the user is in compliance with the market Channel Lineup");
		else
			log.error("Validation Comments : Channel Line up of the user is not in compliance with the market Channel Lineup");
		return isValid;
	}

}
