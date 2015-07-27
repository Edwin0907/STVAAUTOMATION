package Batch;

import java.util.HashMap;
import java.util.Map;





public class Globals {
	public static int StepCounter;          		// Variable for holding the step counter for a test case
	public static int TD;                      		// Variable containing current Test case number under execution 
	public static int TDColumn_Count;           	// Variable containing Total number of Columns in Test Data sheet
	public static int TDRow_Count;             		// Variable containing Total number of Rows in Test Data sheet
	
	public static String BinPath="";           		//String containing the BIN path for the executing project, ie parent path
	public static String Current_Device=""; 		//String containing the name for the current device under execution
	public static String Current_OS="";     		//String containing the name for the current OS under execution
	public static String Current_TestCase="";   	//String containing the name for the current Test Case under execution
	public static String TestData_Path="";      	//String containing path for the test data
	public static String Current_Build="";     	 	//String containing current build name used in reporting 
	public static String Result_Path="";        	//Path for storing the execution log for each and every run
	public static String Actual_Report_Path=""; 	//String containing execution log folder ( Result_Path+CTVA_Automation_Report\Release_Test\ExecutionReport_18122014_16_55_31)
	public static String OS_Report_Path = "";   	//String containing path for execution log generation (Result_Path+CTVA_Automation_Report\Release_Test\ExecutionReport_18122014_16_55_31\Android)  
	public static String TC_Start_Time="";      	//Variable to store the starting time for the executing Test Script.
	public static String TC_Execution_Status="Pass";//Variable to hold the Test script result 
	
	public static Map ObjectCollection;     		// Map for object name and the xpath ie object references
	public static Map TestData;             		// Map for test data for a executing test case  
	public static Map DeviceList;		   			// Map for Device Name and OS
	
	public static String TC_Result_Folder = "",TC_Result_Excel="",Batch_Result_Excel;
	public static String HTML_Reference_Path="",Current_Release_Report_Path="";  
	public static String HTML_Reference_excel_Path="",HTML_Path="",HTML_Step_Report_Path="";
	
	public static Boolean TestStatusFlag= true;
	//For HTML index reporting module counters
	//Submodule list
		
	public static int Login_Pass=0,Login_Fail=0,Login_Pending=0,Login_Total_TC=0;
	public static int Library_Pass=0,Library_Fail=0,Library_Pending=0,Library_Total_TC;
	public static int Guide_Pass=0,Guide_Fail=0,Guide_Pending=0,Guide_Total_TC;
	public static int LiveTV_Pass=0,LiveTV_Fail=0,LiveTV_Pending=0,LiveTV_Total_TC;
	public static int OnDemand_Pass=0,OnDemand_Fail=0,OnDemand_Pending=0,OnDemand_Total_TC;
	public static int KidZone_Pass=0,KidZone_Fail=0,KidZone_Pending=0,KidZone_Total_TC;
	public static int SportZone_Pass=0,SportZone_Fail=0,SportZone_Pending=0,SportZone_Total_TC;
	public static int Settings_Pass=0,Settings_Fail=0,Settings_Pending=0,Settings_Total_TC;
	public static int IPVOD_Pass=0,IPVOD_Fail=0,IPVOD_Pending=0,IPVOD_Total_TC;
	public static int Downloads_Pass=0,Downloads_Fail=0,Downloads_Pending=0,Downloads_Total_TC;
	//Time taken for each module
	public static long Login_Time=0,Library_Time=0,Guide_Time=0,LiveTV_Time=0,OnDemand_Time=0,KidZone_Time=0,SportZone_Time=0,Settings_Time=0,IPVOD_Time=0,Downloads_Time=0;
		    
	//For Metadata
	public static String LoginSymp = "https://symphony.charter.com/symphony/auth/login";
	public static String GuideSymp = "https://symphony.charter.com/symphony/services/v1/catalog/video/guide";
	public static String LoginQueryParam = "";    
	public static String ContentType = "application/x-www-form-urlencoded";
	public static String GetMethod = "POST";
	public static String UserName = "";
	public static String Password = "";
	public static String Token="";
	public static String Method="";
	public static String Xml_Path="";
	//public static Map Channel_List
	public static Map<String, String> Channel_List = new HashMap<String, String>();		   			
	
	
	//For SeeTest Report Path
	public static String SeeTestReportPath = "";
	public static void main(String[] args) {
		
	}
	
	
	
}
