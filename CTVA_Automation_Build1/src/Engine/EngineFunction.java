package Engine; 

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import jxl.Workbook;
import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import Batch.Globals;
import Fillo.Connection;
import Fillo.Fillo;
import Fillo.Recordset;
import TestScripts.HttpClient;
import TestScripts.AppNewFunctions;

public class EngineFunction {
	
	
	/*=====================================================================================================
	* Function Name: Fn_ExecuteTC
	* Parameters: strCaseName, Map object 
	* Description: This function is used to execute the Method. The test case name is the method name and 
	* all the data table value are passed as Map object
	* Author: Dhivya Durairaj
	* Created Date: Jan 10, 2014
	=====================================================================================================*/
	public void FnCom_ExecuteTC()
	{
		String strTestName=Globals.Current_TestCase;
		try
		{
			Class cls = Class.forName("TestScripts.NewTestScripts");
			Object obj = cls.newInstance();
			Method method = cls.getDeclaredMethod(strTestName);
			method.invoke(obj);
		}
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : FnCom_ExecuteTC");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}	
	}
	/*=====================================================================================================
	Fn_ExecuteTC End .....
	=====================================================================================================*/
	
	/*=====================================================================================================
	* Function Name:FnGetDevice
	* Parameters: DeviceSheetName 
	* Description: This function is to get the list of devices with execution flag = "Y"
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public Map FnGetDevice(String DeviceSheetName)
	{
		Map DeviceList= new HashMap<String, String>();
		try
		{
			Fillo fillo=new Fillo();
			int Counter=1;
			Connection connection=fillo.getConnection(DeviceSheetName);
			System.out.println(DeviceSheetName);
			String DeviceQuery="Select * from Device_Sheet where Execution_Flag = 'Y'";// a=1,b=10,c=test
			Recordset recordset=connection.executeQuery(DeviceQuery);
			while(recordset.next())
			{
				DeviceList.put(""+Counter, recordset.getField("Device")+"||"+recordset.getField("OS"));
				Counter=Counter+1;
		    }
			recordset.close();
			connection.close();
		}
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name: FnGetDevice");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		return DeviceList;
	}
    /*=====================================================================================================
    FnGetDevice End .....
    /*=====================================================================================================

	/*=====================================================================================================
	* Function Name: FnGetTestData
	* Parameters: Test Data Sheet name,Row count, Column count, Current Row to be executed 
	* Description: This function is to get Test Data from Data sheet where execution flag is = "Y"
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public Map FnGetTestData(String TDSheetName, int TDRow, int TDColumn,int CurrentRow)
	{
		Map TDMap = new HashMap<String, String>();
		String[] arrColHeader=new String[TDColumn];
		try
		{
			Workbook strWorkBook = Workbook.getWorkbook(new File(TDSheetName));
			//String strExecFlag=strWorkBook.getSheet("TestData").getCell(3, CurrentRow).getContents();
			//String strCaseName=strWorkBook.getSheet("TestData").getCell(2, CurrentRow).getContents();
			//System.out.println(strCaseName);
			for(int i=0;i<TDColumn;i++)
			{
				String strVal=strWorkBook.getSheet("TestData").getCell(i, 0).getContents();				
				arrColHeader[i]=strVal;						
			}
			for(int j = 0;j < TDColumn;j++)	
			{
				TDMap.put(arrColHeader[j],strWorkBook.getSheet("TestData").getCell(j, CurrentRow).getContents());
				//System.out.println(TDMap.get(arrColHeader[j]).toString());
			}
		}
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : FnGetTestData");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		return TDMap;
	}
	/*=====================================================================================================
	FnGetTestData End .....
	=====================================================================================================*/

	/*=====================================================================================================
	* Function Name: FnTableLength
	* Parameters: Test Data Workbook path 
	* Description: This function is to get the column and row count form the test data sheet 
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public String FnTableLength(String TDSheetName)
	{
		int RowCount=0,ColumnCount=0;
		try
		{
			Workbook strWorkBook = Workbook.getWorkbook(new File(TDSheetName));
			RowCount=strWorkBook.getSheet("TestData").getRows();
			ColumnCount=strWorkBook.getSheet("TestData").getColumns();
		}
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : FnTableLength");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		return RowCount+"||"+ColumnCount;
	}
	/*=====================================================================================================
	FnTableLength End .....
	=====================================================================================================*/
	
	/*=====================================================================================================
	* Function Name: FnGetObject
	* Parameters: Test OR Workbook path, Device, OS 
	* Description: This function is to get object reference form the OR sheet based on the OS and Device selected 
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public Map FnGetObject(String ORSheetName,String Device,String OS)
	{
		Map ORCollection= new HashMap<String, String>();
		int ColumnCount;
		boolean DeviceSpecific = false;
		String ORQuery,ORColumn;
		try
		{
			Workbook strWorkBook = Workbook.getWorkbook(new File(ORSheetName));
			ColumnCount=strWorkBook.getSheet("ORSheet").getColumns();
			for(int i=0;i<ColumnCount;i++)
			{
				String strVal=strWorkBook.getSheet("ORSheet").getCell(i,0).getContents();	
				if(strVal.equals(Device+"||"+OS))
				{
					DeviceSpecific = true;
				}
			}
			strWorkBook.close();
			Fillo fillo=new Fillo();
			Connection connection=fillo.getConnection(ORSheetName);
			if(DeviceSpecific)
			{
				ORColumn = Device+"||"+OS;
			}
			else
			{
				ORColumn = OS;
			}
			ORQuery = "Select Object_Name ,\""+ORColumn+"\" from ORSheet";
			Recordset recordset=connection.executeQuery(ORQuery);
			while(recordset.next())
			{
				ORCollection.put(recordset.getField("Object_Name"), recordset.getField(ORColumn));	
			}
			recordset.close();
			connection.close();
		}
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : FnGetObject");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		return ORCollection;
	}

	/*=====================================================================================================
	FnGetObject End .....
	=====================================================================================================*/

	/*=====================================================================================================
	* Function Name: StepCounter
	* Parameters:NULL 
	* Description: This function is to count steps in each and every test case
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public int StepCounter()
	{
		Globals g= new Globals();
		g.StepCounter=g.StepCounter+1;
		return g.StepCounter;
	}

	/*=====================================================================================================
	StepCounter End .....
	=====================================================================================================*/

	/*=====================================================================================================
	* Function Name: Create_ReportBaseFolder
	* Parameters:NULL 
	* Description: This function is to create base folder structure for reporting
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Create_Log_ReportBaseFolder()
	{
		try
		{
			//Creating Reporting Base Folder
			String Reporting_Base_Folder = Globals.Result_Path + "CTVA_Automation_Report\\";
			File f=new File(Reporting_Base_Folder);
			if (!(f.exists()&&f.isDirectory()))
			{
				f.mkdir();
			}
			f = null;
			
			//Creating Build or Release Folder
			String Reporting_Release_Folder = Reporting_Base_Folder + Globals.Current_Build;
			File f1=new File(Reporting_Release_Folder);
			if (!(f1.exists()&&f1.isDirectory()))
			{
				f1.mkdir();
			}
			f1=null;
			
			//Creating folder with time stamp
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf1= new SimpleDateFormat("ddMMyyyy_HH_mm_ss");
			String strTime=sdf1.format(cal.getTime());
			String Reporting_Execution_Folder = Reporting_Release_Folder +"\\ExecutionReport_"+strTime;
			File f2=new File(Reporting_Execution_Folder);
			f2.mkdir();
			Globals.Actual_Report_Path = Reporting_Execution_Folder;
		}
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : Create_ReportBaseFolder");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
	}	
	

	/*=====================================================================================================
	Create_ReportBaseFolder End .....
	=====================================================================================================*/

	/*=====================================================================================================
	* Function Name: Create_Log_ReportFolder
	* Parameters: NULL
	* Description: This function is to create OS specific folder structure for reporting
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Create_Log_ReportFolder()
	{
		try
		{
			//Creating Report folder OS wise
			Globals.OS_Report_Path=Globals.Actual_Report_Path+"\\"+Globals.Current_OS;
			File f1=new File(Globals.OS_Report_Path);
			if (!(f1.exists()&&f1.isDirectory()))
			{
				f1.mkdir();
			}
			f1 = null;	
			File f2 = new File(Globals.OS_Report_Path+"\\TestCaseReport");
			if (!(f2.exists()&&f2.isDirectory()))
			{
				f2.mkdir();
			}
			f2 = null;	
			//Copying excel from template to base folder
			Globals.Batch_Result_Excel =Globals.OS_Report_Path+"\\Bath_Summary_Report.xls" ;
			File SourceFile = new File(Globals.BinPath.replace("bin", "Template\\Bath_Summary_Report.xls"));
			File DestinationFile = new File(Globals.Batch_Result_Excel);
			FileUtils.copyFile(SourceFile, DestinationFile);
		}
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : Create_ReportFolder");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
	}	
	/*=====================================================================================================
	Create_ReportFolder End .....
	=====================================================================================================*/

	/*=====================================================================================================
	* Function Name: Create_TCFolder
	* Parameters: NULL
	* Description: This function is to create Excel for TC Step results
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Create_TCReport()
	{
		try
		{
			
			Globals.TC_Result_Excel =  Globals.OS_Report_Path+"\\TestCaseReport\\"+Globals.Current_TestCase+".xls";
			File SourceFile = new File(Globals.BinPath.replace("bin", "Template\\TestCase_Step_Report.xls"));
			File DestinationFile = new File(Globals.TC_Result_Excel);
			FileUtils.copyFile(SourceFile, DestinationFile);
	    }
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : Create_TCReport");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		
	}	
	/*=====================================================================================================
	Create_TCReport End .....
	=====================================================================================================*/


	/*=====================================================================================================
	* Function Name: Update_Step_Report
	* Parameters: Step Description, Pass Description|| Fail Description, Boolean true or false
	* Description: This function is to update step results in the Test case specific excel sheet
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Update_Step_Report(String Descritpion,String ActualResult,Boolean Result_Status)
	{
		try
		{
			int Current_Step = this.StepCounter();
			String Result="Fail",ResultDesc="",ImagePath="";
			String AppendDetails = "";
			/*if (Current_Step == 1 && Result_Status)
				Result = "Pass";*/
			if(Result_Status)
			{
				Result = "Pass";
				Globals.TestStatusFlag = true;
				ResultDesc = ActualResult.split("\\|\\|")[0];
				ImagePath=Globals.HTML_Path.replace("WebContent","Source\\Image\\green dot.png");
			}
			else
			{
				Result = "Fail";
				Globals.TestStatusFlag = false;
				ResultDesc = ActualResult.split("\\|\\|")[1];
				ImagePath=Globals.HTML_Path.replace("WebContent","Source\\Image\\red dot.jpg");
			}
			String Current_Step_Str = ""+ Current_Step;
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf1= new SimpleDateFormat("HH:mm:ss");
	    	String ReportingTime=""+sdf1.format(cal.getTime());
			Fillo fillo=new Fillo();
	       	Connection connection=fillo.getConnection(Globals.TC_Result_Excel);
	       	String strQuery= "INSERT INTO StepReport(Step,Description,ActualResult,ExecutionStatus,ExecutionEndTime) VALUES("+"'"+Current_Step_Str+"'"+","+"'"+Descritpion+"'"+","+"'"+ResultDesc+"'"+","+"'"+Result+"'"+","+"'"+ReportingTime+"'"+")";
	      	System.out.println(strQuery);
	       	connection.executeUpdate(strQuery);
	    	connection.close();
	    	
	    	//Globals.TestStatusFlag = (Globals.TestStatusFlag & Result_Status);
	    	/*if(Result.contains("Fail" )&& Globals.TC_Execution_Status.contains("Pass"))
	    	{
	    		 Globals.TC_Execution_Status="Fail";
	    	}*/
	    	/*if (Globals.TestStatusFlag)
	    		Globals.TC_Execution_Status="Pass";
	    	else 
	    		Globals.TC_Execution_Status="Fail";*/
	    	//String AppendDetails = "<tr align = \"center\"  id = \""+AutomationID +"\"class=\"Pending\"><td></td><td>"+AutomationID+"</td><td></td><td align=\"center\" ><img src=\""+"../Source/Image/yellow dot.png"+"\"></img></td><td></td><td></td><td></td></tr>";
	    	AppendDetails = "<tr><td>"+Current_Step+"</td><td>"+Descritpion+"</td><td>"+ResultDesc+"</td><td><img src=\""+ImagePath+"\"></img></td><td >"+ReportingTime+"</td></tr>"; 
	    	String Path = Globals.HTML_Step_Report_Path+Globals.Current_TestCase+".html";
			File source = new File(Path);
	        Document report = null;
	        try 
	        {
	            report = Jsoup.parse(source, "UTF-8");
	        } 
	        catch (IOException e) 
	        {
	            System.out.println("Unable to open ["+source.getAbsolutePath()+"] for parsing!");
	        }
	        Elements dom = report.children();
	    	dom.select("#TestStep_contents").append(AppendDetails);
			if(!source.canWrite()) System.out.println("Can't write this file!"); //Just check if the file is writable or not
			BufferedWriter bw = new BufferedWriter(new FileWriter(source));
	        bw.write(dom.toString()); 
	        bw.close(); 
	    	
		}
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : Update_Step_Report");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
}

	/*=====================================================================================================
	Update_Step_Report End .....
	=====================================================================================================*/
	
	/*=====================================================================================================
	* Function Name: Update_TC_Report
	* Parameters: NULL
	* Description: This function is to update final result of test case at the end of the TC execution
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Update_TC_Report()
	{
		try
		{
			AppNewFunctions Ap=new AppNewFunctions();
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf1= new SimpleDateFormat("HH:mm:ss");
	    	String ReportingTime=""+sdf1.format(cal.getTime());
	    	
	    	/*
	    	 * *****************************Vel code fix for fixing Report Status Issue**************
	    	 */
	    	
	    	Boolean bOverallStatus = true;
	    	
	    	Fillo filloCascadeStepStatus=new Fillo();
	       	Connection connectionStepResults=filloCascadeStepStatus.getConnection(Globals.TC_Result_Excel);
	       	String strQueryStepReport = "SELECT ExecutionStatus FROM StepReport";
	       	Recordset rdStepResults =  connectionStepResults.executeQuery(strQueryStepReport);
	       	while(rdStepResults.next())
			{
	       		String StepStatus = ((rdStepResults.getField("ExecutionStatus") != null) ? rdStepResults.getField("ExecutionStatus") : "Fail" );
	       		if (!StepStatus.isEmpty())
	       		{
	       			bOverallStatus = bOverallStatus & (StepStatus.equalsIgnoreCase("Pass") ? true:false);
	       		}
			}
	       	connectionStepResults.close();
	       	Globals.TC_Execution_Status = ((bOverallStatus) ? "Pass" : "Fail");
	       	
	       	/*
	    	 * ***************************Vel code fix for fixing Report Status Issue****************
	    	 */
	       	
			Fillo fillo=new Fillo();
	     	Connection connection=fillo.getConnection(Globals.Batch_Result_Excel);
	       	String strQuery= "INSERT INTO BatchReport(ManualTestCase,TestScriptName,TCDescription,ExecutionStatus,FolderLocation,ExecutionStartTime,ExecutionEndTime) VALUES("+"'"+Globals.TestData.get("ManulTCReference").toString()+"'"+","+"'"+Globals.TestData.get("TestCaseName").toString()+"'"+","+"'"+Globals.TestData.get("Descritpion").toString()+"'"+","+"'"+Globals.TC_Execution_Status+"'"+","+"'"+Globals.TC_Result_Excel+"'"+","+"'"+""+Globals.TC_Start_Time+"'"+","+"'"+ReportingTime+"'"+")";
	       	System.out.println(strQuery);
	       	connection.executeUpdate(strQuery);
	       	connection.close();
	       	
	       	Connection connection1=fillo.getConnection(Globals.HTML_Reference_excel_Path);
	       	String strQuery1="UPDATE OverallTC SET ManualTestCase='"+ Globals.TestData.get("ManulTCReference").toString()+"',TCDescription='"+Globals.TestData.get("Descritpion").toString()+"',ExecutionStatus='"+Globals.TC_Execution_Status+"',FolderLocation='"+Globals.TC_Result_Excel+"',ExecutionStartTime='"+Globals.TC_Start_Time+"',ExecutionEndTime='"+ReportingTime+"' WHERE TestScriptName='"+Globals.Current_TestCase+"'";
	       	//String strQuery2= "INSERT INTO BatchReport(ManualTestCase,TestScriptName,TCDescription,ExecutionStatus,FolderLocation,ExecutionStartTime,ExecutionEndTime) VALUES("+"'"+Globals.TestData.get("ManulTCReference").toString()+"'"+","+"'"+Globals.TestData.get("TestCaseName").toString()+"'"+","+"'"+Globals.TestData.get("Descritpion").toString()+"'"+","+"'"+Globals.TC_Execution_Status+"'"+","+"'"+Globals.TC_Result_Excel+"'"+","+"'"+""+Globals.TC_Start_Time+"'"+","+"'"+ReportingTime+"'"+")";
	       	System.out.println(strQuery1);
	       	connection1.executeUpdate(strQuery1);
	    	connection1.close();
	    	//String ModuleName = Globals.Current_TestCase.split("_")[1] ;
	    	//SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	    	//long TimeTaken = format.parse(ReportingTime).getTime()-format.parse(Globals.TC_Start_Time).getTime();
			//TimeTaken=(TimeTaken/1000);
	    	this.Initialize_Module_Count();
			//this.Update_Module_Count(ModuleName, Globals.TC_Execution_Status, TimeTaken, "Increment");
	    	this.Update_HTML_Report();
	    	Globals.TD++;
	    	Globals.TC_Start_Time=""+sdf1.format(cal.getTime());
	    	Globals.TestData = this.FnGetTestData(Globals.TestData_Path,Globals.TDRow_Count,Globals.TDColumn_Count,Globals.TD);
	    	Globals.TC_Execution_Status="Pass";
	    	Globals.Current_TestCase =  Globals.TestData.get("TestCaseName").toString();
	    	this.Clear_Previous_TC_Batch_Report();
	    	
			//Creating result folder specific to Test Cases
	        this.Create_TCReport();
	        Globals.TC_Execution_Status="Pass";
			Globals.StepCounter = 0;
			//this.FnCom_ExecuteTC ();
	    	
		}
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : Update_TC_Report");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
	}

	/*=====================================================================================================
	Update_TC_Report End .....
	=====================================================================================================*/
	
	/*=====================================================================================================
	* Function Name: Auto_Update_TC_Report
	* Parameters: NULL
	* Description: This function is to update final result of test case at the end of the TC execution
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Auto_Update_TC_Report()
	{
		try
		{
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf1= new SimpleDateFormat("HH:mm:ss");
	    	String ReportingTime=""+sdf1.format(cal.getTime());
	    	/*
	    	 * *****************************Vel code fix for fixing Report Status Issue**************
	    	 */
	    	
	    	Boolean bOverallStatus = true;
	    	
	    	Fillo filloCascadeStepStatus=new Fillo();
	       	Connection connectionStepResults=filloCascadeStepStatus.getConnection(Globals.TC_Result_Excel);
	       	String strQueryStepReport = "SELECT ExecutionStatus FROM StepReport";
	       	Recordset rdStepResults =  connectionStepResults.executeQuery(strQueryStepReport);
	       	while(rdStepResults.next())
			{
	       		String StepStatus = ((rdStepResults.getField("ExecutionStatus") != null) ? rdStepResults.getField("ExecutionStatus") : "Fail" );
	       		if (!StepStatus.isEmpty())
	       		{
	       			bOverallStatus = bOverallStatus & (StepStatus.equalsIgnoreCase("Pass") ? true:false);
	       		}
			}
	       	connectionStepResults.close();
	       	Globals.TC_Execution_Status = ((bOverallStatus) ? "Pass" : "Fail");
	       	
	       	/*
	    	 * ***************************Vel code fix for fixing Report Status Issue****************
	    	 */
	       	
			Fillo fillo=new Fillo();
	       	Connection connection=fillo.getConnection(Globals.Batch_Result_Excel);
	       	String strQuery= "INSERT INTO BatchReport(ManualTestCase,TestScriptName,TCDescription,ExecutionStatus,FolderLocation,ExecutionStartTime,ExecutionEndTime) VALUES("+"'"+Globals.TestData.get("ManulTCReference").toString()+"'"+","+"'"+Globals.TestData.get("TestCaseName").toString()+"'"+","+"'"+Globals.TestData.get("Descritpion").toString()+"'"+","+"'"+Globals.TC_Execution_Status+"'"+","+"'"+Globals.TC_Result_Excel+"'"+","+"'"+""+Globals.TC_Start_Time+"'"+","+"'"+ReportingTime+"'"+")";
	       	System.out.println(strQuery);
	       	connection.executeUpdate(strQuery);
	       	connection.close();
	       	
	       	Connection connection1=fillo.getConnection(Globals.HTML_Reference_excel_Path);
	    	String strQuery1="UPDATE OverallTC SET ManualTestCase='"+ Globals.TestData.get("ManulTCReference").toString()+"',TCDescription='"+Globals.TestData.get("Descritpion").toString()+"',ExecutionStatus='"+Globals.TC_Execution_Status+"',FolderLocation='"+Globals.TC_Result_Excel+"',ExecutionStartTime='"+Globals.TC_Start_Time+"',ExecutionEndTime='"+ReportingTime+"' WHERE TestScriptName='"+Globals.Current_TestCase+"'";
	       	System.out.println(strQuery1);
	       	connection1.executeUpdate(strQuery1);
	    	connection1.close();
	       	
	    	//String ModuleName = Globals.Current_TestCase.split("_")[1] ;
	    	//SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
	    	//long TimeTaken = format.parse(ReportingTime).getTime()-format.parse(Globals.TC_Start_Time).getTime();
			//TimeTaken=(TimeTaken/1000);
			this.Initialize_Module_Count();
	    	//this.Update_Module_Count(ModuleName, Globals.TC_Execution_Status, TimeTaken, "Increment");
	    	this.Update_HTML_Report();
	    	//Globals.TC_Execution_Status = "Pass";
	    	}
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : Update_TC_Report");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
	}

	/*=====================================================================================================
	Auto_Update_TC_Report End .....
	=====================================================================================================*/
	
	/*=====================================================================================================
	* Function Name: Create_HTML_Reference
	* Parameters: NULL
	* Description: This function is to create reference excel file for html reporting
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Create_HTML_Reference()
	{
		try
		{
			//Creating Release folder for consolidated report and for HTML report
			Globals.Current_Release_Report_Path = Globals.BinPath.replace("bin", "Report\\")+Globals.Current_Build;
			Globals.HTML_Reference_Path=Globals.Current_Release_Report_Path+"\\"+"Reference";
			Globals.HTML_Reference_excel_Path=Globals.HTML_Reference_Path+"\\"+Globals.Current_Build+".xls";
			Globals.HTML_Path=Globals.Current_Release_Report_Path+"\\HTML\\WebContent";
			Globals.HTML_Step_Report_Path=Globals.Current_Release_Report_Path+"\\HTML\\WebContent\\TestCaseStepReport\\";
			
			File f1=new File(Globals.Current_Release_Report_Path);
			if (!(f1.exists()&&f1.isDirectory()))
			{
				f1.mkdir();
				//Globals.TC_Result_Excel =  Globals.OS_Report_Path+"\\TestCaseReport\\"+Globals.Current_TestCase+".xls";
				File SourceFile1 = new File(Globals.BinPath.replace("bin", "Template\\HTML"));
				File DestinationFile1 = new File(Globals.Current_Release_Report_Path+"\\HTML");
				if (!(DestinationFile1.exists()&&DestinationFile1.isDirectory()))
				{
					FileUtils.copyDirectory(SourceFile1, DestinationFile1);
				}
				
				File DelFile = new File(Globals.Current_Release_Report_Path+"\\HTML\\WebContent\\TestStepReport.html");
		    	DelFile.delete();
				
				File f2=new File(Globals.HTML_Step_Report_Path);
				f2.mkdir();
				f2= null;
				
				File f3=new File(Globals.HTML_Reference_Path);
				if (!(f3.exists()&&f3.isDirectory()))
				{
					f3.mkdir();
					
					File SourceFile = new File(Globals.BinPath.replace("bin", "Template\\TC_Overall.xls"));
					File DestinationFile = new File(Globals.HTML_Reference_excel_Path);
					FileUtils.copyFile(SourceFile, DestinationFile);
					this.Create_HTML_File();
				
				
					
				}
				f3 = null;
			}	
			this.Initialize_Module_Count();	
			f1 = null;
			
			//Globals.Suite_Report_Path=Globals.BinPath.replace("bin", "Report\\");
			
			
			 
			
			}
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : Update_TC_Report");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
	}

	/*=====================================================================================================
	Auto_Update_TC_Report End .....
	=====================================================================================================*/
	/*=====================================================================================================
	* Function Name: Create_HTML_Reference
	* Parameters: NULL
	* Description: This function is to create reference excel file for html reporting
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Clear_Previous_TC_Batch_Report()
	{
		try
		{

			//Globals.Modules_List ML = Globals.Modules_List.AssetDetails;
			Fillo fillo=new Fillo();
	       	Connection connection=fillo.getConnection(Globals.HTML_Reference_excel_Path);
	       	String strQuery = "select * from OverallTC WHERE TestScriptName='"+Globals.Current_TestCase+"'";
	       	Recordset recordset=connection.executeQuery(strQuery);
				
	       	while(recordset.next())
			{
				
				String ModuleName= recordset.getField("TestScriptName").split("_")[1];
				String Status = recordset.getField("ExecutionStatus");
				String StartTime = recordset.getField("ExecutionStartTime");
				String EndTime = recordset.getField("ExecutionEndTime");
				if (!StartTime.isEmpty()&&!EndTime.isEmpty())
				{
				SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
				long TimeTaken = format.parse(EndTime).getTime()-format.parse(StartTime).getTime();
				TimeTaken=(TimeTaken/1000);
				/*if (Status != "")
				{
				//this.Update_Module_Count(ModuleName, Status, TimeTaken, "Decrement");
				}*/
				}
				
				
			}
				
			Fillo fillo1=new Fillo();
	       	Connection connection1=fillo1.getConnection(Globals.HTML_Reference_excel_Path);
	       	String strQuery1 = "UPDATE OverallTC SET ManualTestCase='',TCDescription='',ExecutionStatus='',FolderLocation='',ExecutionStartTime='',ExecutionEndTime='' WHERE TestScriptName='"+Globals.Current_TestCase+"'";
	       	//System.out.println(strQuery1);
	       	connection1.executeUpdate(strQuery1);
	    	connection1.close();
	    	
	    	File PreStep = new File(Globals.HTML_Step_Report_Path+Globals.Current_TestCase+".html");
	    	PreStep.delete();
			
	    	File SourceFile2 = new File(Globals.BinPath.replace("bin", "Template\\HTML\\WebContent\\TestStepReport.html"));
			File DestinationFile2 = new File(Globals.HTML_Step_Report_Path+Globals.Current_TestCase+".html");
			
			FileUtils.copyFile(SourceFile2, DestinationFile2);
			
		}
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : Update_TC_Report");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
    }
	
	/*=====================================================================================================
	* Function Name: Create_HTML_Reference
	* Parameters: NULL
	* Description: This function is to create reference excel file for html reporting
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Create_HTML_File()
	{
		try
		{
			String Path = Globals.HTML_Path+"\\TestCaseReport.html";
			String excelpath=Globals.HTML_Reference_excel_Path;
			File source = new File(Path);
	        Document report = null;
	        try {
	            report = Jsoup.parse(source, "UTF-8");
	        } catch (IOException e) {
	            System.out.println("Unable to open ["+source.getAbsolutePath()+"] for parsing!");
	        }
	        
	        Fillo fillo=new Fillo();
			//Connection connection=fillo.getConnection(Globals.HTML_Reference_excel_Path);
			Connection connection=fillo.getConnection(excelpath);
			String DataQuery="Select * from OverallTC";
			Recordset recordset=connection.executeQuery(DataQuery);
			String AppendDetails="";
			while(recordset.next())
			{
				Elements dom = report.children();
				String AutomationID = recordset.getField("TestScriptName");
				AppendDetails = "<tr align = \"center\"  id = \""+AutomationID +"\"class=\"Pending\"><td></td><td>"+AutomationID+"</td><td></td><td align=\"center\" ><img src=\""+"../Source/Image/yellow dot.png"+"\"></img></td><td></td><td></td><td></td></tr>";
				dom.select("#tc_contents").append(AppendDetails);
				if(!source.canWrite()) System.out.println("Can't write this file!"); //Just check if the file is writable or not
				BufferedWriter bw = new BufferedWriter(new FileWriter(source));
		        bw.write(dom.toString()); 
		        bw.close();  
			}
			recordset.close();
			connection.close();
		}
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : Update_TC_Report");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
	}

	/*=====================================================================================================
	Auto_Update_TC_Report End .....
	=====================================================================================================*/
	/*=====================================================================================================
	* Function Name: Create_HTML_Reference
	* Parameters: NULL
	* Description: This function is to create reference excel file for html reporting
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Update_HTML_Report()
	{
		try
		{
			String Path = Globals.HTML_Path+"\\TestCaseReport.html";
			String excelpath=Globals.HTML_Reference_excel_Path;
			File source = new File(Path);
	        Document report = null;
	        try 
	        {
	            report = Jsoup.parse(source, "UTF-8");
	        } 
	        catch (IOException e) 
	        {
	            System.out.println("Unable to open ["+source.getAbsolutePath()+"] for parsing!");
	        }
	        
	        Fillo fillo=new Fillo();
			//Connection connection=fillo.getConnection(Globals.HTML_Reference_excel_Path);
			Connection connection=fillo.getConnection(excelpath);
			String DataQuery="Select * from OverallTC where TestScriptName =\'"+Globals.Current_TestCase+"\'" ;
			System.out.println("Fix Querry :"+DataQuery);
			Recordset recordset=connection.executeQuery(DataQuery);
			String AppendDetails="";
			while(recordset.next())
			{
				Elements dom = report.children();
				String ManualReference = recordset.getField("ManualTestCase");
				String AutomationID = recordset.getField("TestScriptName");
				String TDDescription = recordset.getField("TCDescription");
				String ExecutionStatus = recordset.getField("ExecutionStatus");
				String StartTime = recordset.getField("ExecutionStartTime");
				String EndTime = recordset.getField("ExecutionEndTime");
				String ImagePath="",ImagePath2="";
				SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
				long TimeTaken = format.parse(EndTime).getTime()-format.parse(StartTime).getTime();
				TimeTaken=(TimeTaken/1000);
				
				if (ExecutionStatus.contains("Pass"))
				{
					ImagePath="..//Source//Image//green dot.png";
					ImagePath2=Globals.HTML_Path.replace("WebContent","Source\\Image\\green dot.png");
				}
				else if (ExecutionStatus.contains("Fail"))
				{
					ImagePath="..//Source//Image//red dot.jpg";
					ImagePath2=Globals.HTML_Path.replace("WebContent","Source\\Image\\red dot.jpg");
				}
				else
				{
					ExecutionStatus = "Pending";
					ImagePath="..//Source//Image//yellow dot.png";
				}
				AppendDetails = "<td  align=\"center\" >"+ManualReference+"</td><td align=\"center\" >"+AutomationID+"</td><td>"+TDDescription+"</td><td align=\"center\" ><img id = \""+ExecutionStatus+"\" src=\""+ImagePath+"\"></img></td><td align=\"center\" >"+StartTime+"</td><td align=\"center\" >"+EndTime+"</td><td align=\"center\" name=\"TimeTaken\">"+TimeTaken+"</td>";
				String SelectionVar = "#"+AutomationID;
				dom.select(SelectionVar).attr("class",ExecutionStatus);
				String TCSTep="document.location='../WebContent/TestCaseStepReport/"+Globals.Current_TestCase+".html';";
				dom.select(SelectionVar).attr("onclick",TCSTep);
				dom.select(SelectionVar).html(AppendDetails);
				if(!source.canWrite()) System.out.println("Can't write this file!"); //Just check if the file is writable or not
				BufferedWriter bw = new BufferedWriter(new FileWriter(source));
		        bw.write(dom.toString()); //toString will give all the elements as a big string
		        bw.close();  //Close to apply the changes
		        
		        
		        
		    	 Path = Globals.HTML_Step_Report_Path+Globals.Current_TestCase+".html";
				 source = new File(Path);
		        report = null;
		        try {
		            report = Jsoup.parse(source, "UTF-8");
		        } catch (IOException e) {
		            System.out.println("Unable to open ["+source.getAbsolutePath()+"] for parsing!");
		        }
		         dom = report.children();
		    	dom.select("#TC_name_Step").html(Globals.Current_TestCase);
		    	dom.select("#TC_Overall_Status_Step").html("<img src=\""+ImagePath2+"\"></img>");
		    	dom.select("#TC_Start_Time_Step").html(StartTime);
		    	dom.select("#TC_End_Time_Step").html(EndTime);
		    	dom.select("#TC_Total_Time_Step").html(""+TimeTaken);
				if(!source.canWrite()) System.out.println("Can't write this file!"); //Just check if the file is writable or not
				 bw = new BufferedWriter(new FileWriter(source));
		        bw.write(dom.toString()); 
		        bw.close(); 
			}
			recordset.close();
			connection.close();
			//Globals.
		}
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : Update_TC_Report");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
	}

	/*=====================================================================================================
	Auto_Update_TC_Report End .....
	=====================================================================================================*/
	
	/*=====================================================================================================
	* Function Name: Create_HTML_Reference
	* Parameters: NULL
	* Description: This function is to create reference excel file for html reporting
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Initialize_Module_Count() throws IOException
	{
		
		Globals g =new Globals();
		g.Login_Pass=0;
		g.Login_Fail=0;g.Login_Pending=0;g.Login_Total_TC=0;
		g.Library_Pass=0;g.Library_Fail=0;g.Library_Pending=0;g.Library_Total_TC=0;
	g.Guide_Pass=0;g.Guide_Fail=0;g.Guide_Pending=0;g.Guide_Total_TC=0;
	g.LiveTV_Pass=0;g.LiveTV_Fail=0;g.LiveTV_Pending=0;g.LiveTV_Total_TC=0;
	g.OnDemand_Pass=0;g.OnDemand_Fail=0;g.OnDemand_Pending=0;g.OnDemand_Total_TC=0;
	g.KidZone_Pass=0;g.KidZone_Fail=0;g.KidZone_Pending=0;g.KidZone_Total_TC=0;
	g.SportZone_Pass=0;g.SportZone_Fail=0;g.SportZone_Pending=0;g.SportZone_Total_TC=0;
	g.Settings_Pass=0;g.Settings_Fail=0;g.Settings_Pending=0;g.Settings_Total_TC=0;
	g.IPVOD_Pass=0;g.IPVOD_Fail=0;g.IPVOD_Pending=0;g.IPVOD_Total_TC=0;
	g.Downloads_Pass=0;g.Downloads_Fail=0;g.Downloads_Pending=0;g.Downloads_Total_TC=0;
		//Time taken for each module
	g.Login_Time=0;g.Library_Time=0;g.Guide_Time=0;g.LiveTV_Time=0;g.OnDemand_Time=0;g.KidZone_Time=0;g.SportZone_Time=0;g.Settings_Time=0;g.IPVOD_Time=0;g.Downloads_Time=0;
		
		String ModuleName="",Status,EndTime,StartTime,ModuleVar;
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		try
		{
			//Globals.Modules_List ML = Globals.Modules_List.AssetDetails;
			Fillo fillo=new Fillo();
	       	Connection connection=fillo.getConnection(Globals.HTML_Reference_excel_Path);
	       	String strQuery = "select * from OverallTC";
	       	Recordset recordset=connection.executeQuery(strQuery);
			while(recordset.next())
			{
				
				ModuleName= recordset.getField("TestScriptName").split("_")[1];
				//System.out.println(recordset.getField("TestScriptName"));
				Status = recordset.getField("ExecutionStatus");
				
				StartTime = recordset.getField("ExecutionStartTime");
				EndTime = recordset.getField("ExecutionEndTime");
				long TimeTaken;
				if (EndTime != null && !EndTime.isEmpty())
				{
				TimeTaken = format.parse(EndTime).getTime()-format.parse(StartTime).getTime();
				TimeTaken=(TimeTaken/1000);
				}
				else
				{
				TimeTaken=0;
				}
				
				ModuleVar=ModuleName;
				//System.out.println(recordset.getCount());
			
				//Login module
     			if(ModuleVar.contains("Login")||ModuleVar.contains("Generic"))
				{
     				Globals.Login_Time+=TimeTaken;
     				if (Status.contains("Pass"))
     					Globals.Login_Pass++;
					else if (Status.contains("Fail"))
						Globals.Login_Fail++;
					else
						Globals.Login_Pending++;
					
				}
     			//Library
     			if(ModuleVar.contains("Home")||ModuleVar.contains("MYLibrary")||ModuleVar.contains("BrowseAll")||ModuleVar.contains("HelpOverlays")||ModuleVar.contains("Search")||ModuleVar.contains("Watchlist")||ModuleVar.contains("ContentSearch")||ModuleVar.contains("Others"))
				{
     				Globals.Library_Time+=TimeTaken;
     				if (Status.contains("Pass"))
						Globals.Library_Pass++;
					else if (Status.contains("Fail"))
						Globals.Library_Fail++;
					else
						Globals.Library_Pending++;
				}
     			//Guide module
     			if(ModuleVar.contains("Guide")||ModuleVar.contains("AssetDetails")||ModuleVar.contains("Metadata"))
				{
     				Globals.Guide_Time+=TimeTaken;
     				if (Status.contains("Pass"))
     					Globals.Guide_Pass++;
					else if (Status.contains("Fail"))
						Globals.Guide_Fail++;
					else
						Globals.Guide_Pending++;
				}
     			//LIVETV module
     			if(ModuleVar.contains("LiveTV")||ModuleVar.contains("VideoPlayer")||ModuleVar.contains("LinearStreaming"))
				{
     				Globals.LiveTV_Time+=TimeTaken;
     				if (Status.contains("Pass"))
     					Globals.LiveTV_Pass++;
					else if (Status.contains("Fail"))
						Globals.LiveTV_Fail++;
					else
						Globals.LiveTV_Pending++;
				}
     			//OnDemand module
     			if(ModuleVar.contains("OnDemand"))
				{
     				Globals.OnDemand_Time+=TimeTaken;
     				if (Status.contains("Pass"))
     					Globals.OnDemand_Pass++;
					else if (Status.contains("Fail"))
						Globals.OnDemand_Fail++;
					else
						Globals.OnDemand_Pending++;
				}
     			//KidZone module
     			if(ModuleVar.contains("KidZone"))
				{
     				Globals.KidZone_Time+=TimeTaken;
     				if (Status.contains("Pass"))
     					Globals.KidZone_Pass++;
					else if (Status.contains("Fail"))
						Globals.KidZone_Fail++;
					else
						Globals.KidZone_Pending++;
				}
     			//SportZone module
     			if(ModuleVar.contains("SportZone"))
				{
     				Globals.SportZone_Time+=TimeTaken;
     				if (Status.contains("Pass"))
     					Globals.SportZone_Pass++;
					else if (Status.contains("Fail"))
						Globals.SportZone_Fail++;
					else
						Globals.SportZone_Pending++;
				}
     			//Settings module
     			if(ModuleVar.contains("Settings"))
				{
     				Globals.Settings_Time+=TimeTaken;
     				if (Status.contains("Pass"))
     					Globals.Settings_Pass++;
					else if (Status.contains("Fail"))
						Globals.Settings_Fail++;
					else
						Globals.Settings_Pending++;
				}
     			//IPVOD
     			if(ModuleVar.contains("IPVOD"))
				{
     				Globals.IPVOD_Time+=TimeTaken;
     				if (Status.contains("Pass"))
						Globals.IPVOD_Pass++;
					else if (Status.contains("Fail"))
						Globals.IPVOD_Fail++;
					else
						Globals.IPVOD_Pending++;
				}
     			//Downloads
     			if(ModuleVar.contains("D2G"))
				{
     				Globals.Downloads_Time+=TimeTaken;
     				if (Status.contains("Pass"))
						Globals.Downloads_Pass++;
					else if (Status.contains("Fail"))
						Globals.Downloads_Fail++;
					else
						Globals.Downloads_Pending++;
				}
		    }
			recordset.close();
			connection.close();
		}
		catch(Exception h){}
			//Updating HTML index page
			float per,com,pertc;
			String Path = Globals.HTML_Path+"\\Index.html";
			File  source = new File(Path);
	        Document report = null;
	        try {
	            report = Jsoup.parse(source, "UTF-8");
	        } catch (IOException e) {
	            System.out.println("Unable to open ["+source.getAbsolutePath()+"] for parsing!");
	        }
	        Elements dom = report.children();
	        
	        /*
	         * Edwin Code fix for dynamic generation of Build number and OS check
	         */
	        
	        //Updating Current build id
	        dom.select("#Region").html(""+Globals.Current_Build);
	        //Updating Tick mark against OS
	        if(Globals.Current_OS.equalsIgnoreCase("IOS"))
	        {
	        	dom.select("#android_logo").attr("src", "../Source/Image/No.jpg");
	        	dom.select("#ios_logo").attr("src", "../Source/Image/Tick.png");
	        	dom.select("#kindle_logo").attr("src", "../Source/Image/No.jpg");
	        }
	        if(Globals.Current_OS.equalsIgnoreCase("Android"))
	        {
	        	dom.select("#android_logo").attr("src", "../Source/Image/Tick.png");
	        	dom.select("#ios_logo").attr("src", "../Source/Image/No.jpg");
	        	dom.select("#kindle_logo").attr("src", "../Source/Image/No.jpg");
	        }
	        if(Globals.Current_OS.equalsIgnoreCase("Kindle"))
	        {
	        	dom.select("#android_logo").attr("src", "../Source/Image/No.jpg");
	        	dom.select("#ios_logo").attr("src", "../Source/Image/No.jpg");
	        	dom.select("#kindle_logo").attr("src", "../Source/Image/Tick.png");
	        }
	        
	        /*
	         * End : Code fix for dynamic generation of Build number and OS check
	         * By : Edwin
	         * At : 27/3/2015
	         */
	        //Login
	    	dom.select("#Login_Pass").html(""+Globals.Login_Pass);
	    	dom.select("#Login_Fail").html(""+Globals.Login_Fail);
	    	dom.select("#Login_Pending").html(""+Globals.Login_Pending);
	    	Globals.Login_Total_TC= Globals.Login_Pass+Globals.Login_Fail+Globals.Login_Pending;
	    	dom.select("#Login_Total").html(""+Globals.Login_Total_TC);
	    	dom.select("#Login_Time").html(""+Globals.Login_Time);
	    	if ((Globals.Login_Pass+Globals.Login_Fail)!=0)
	    	{
	    	float executed = Globals.Login_Fail+Globals.Login_Pass;
		    float execfrac = executed/Globals.Login_Total_TC;
		    com = execfrac*100;
	    	pertc=Math.round((Globals.Login_Time/(Globals.Login_Pass+Globals.Login_Fail)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	dom.select("#Login_Completion").html(""+com);
	    	dom.select("#Login_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	//Library
	    	dom.select("#Library_Pass").html(""+Globals.Library_Pass);
	    	dom.select("#Library_Fail").html(""+Globals.Library_Fail);
	    	dom.select("#Library_Pending").html(""+Globals.Library_Pending);
	    	Globals.Library_Total_TC= Globals.Library_Pass+Globals.Library_Fail+Globals.Library_Pending;
	    	dom.select("#Library_Total").html(""+Globals.Library_Total_TC);
	    	dom.select("#Library_Time").html(""+Globals.Library_Time);
	    	if ((Globals.Library_Fail+Globals.Library_Pass)!= 0)
	    	{
	    	float executed = Globals.Library_Fail+Globals.Library_Pass;
	        float execfrac = executed/Globals.Library_Total_TC;
	        com = execfrac*100;
	    	pertc=Math.round((Globals.Library_Time/(Globals.Library_Fail+Globals.Library_Pass)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	dom.select("#Library_Completion").html(""+com);
	    	dom.select("#Library_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	//Guide
	    	dom.select("#Guide_Pass").html(""+Globals.Guide_Pass);
	    	dom.select("#Guide_Fail").html(""+Globals.Guide_Fail);
	    	dom.select("#Guide_Pending").html(""+Globals.Guide_Pending);
	    	Globals.Guide_Total_TC= Globals.Guide_Pass+Globals.Guide_Fail+Globals.Guide_Pending;
	    	dom.select("#Guide_Total").html(""+Globals.Guide_Total_TC);
	    	dom.select("#Guide_Time").html(""+Globals.Guide_Time);
	    	if ((Globals.Guide_Fail+Globals.Guide_Pass) != 0)
	    	{
	    	float executed = Globals.Guide_Fail+Globals.Guide_Pass;
		    float execfrac = executed/Globals.Guide_Total_TC;
		    com = execfrac*100;
	    	pertc=Math.round((Globals.Guide_Time/(Globals.Guide_Fail+Globals.Guide_Pass)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	dom.select("#Guide_Completion").html(""+com);
	    	dom.select("#Guide_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	//LiveTV
	    	dom.select("#LiveTV_Pass").html(""+Globals.LiveTV_Pass);
	    	dom.select("#LiveTV_Fail").html(""+Globals.LiveTV_Fail);
	    	dom.select("#LiveTV_Pending").html(""+Globals.LiveTV_Pending);
	    	Globals.LiveTV_Total_TC= Globals.LiveTV_Pass+Globals.LiveTV_Fail+Globals.LiveTV_Pending;
	    	dom.select("#LiveTV_Total").html(""+Globals.LiveTV_Total_TC);
	    	dom.select("#LiveTV_Time").html(""+Globals.LiveTV_Time);
	    	if ((Globals.LiveTV_Fail+Globals.LiveTV_Pass) != 0)
	    	{
	    		float executed = Globals.LiveTV_Fail+Globals.LiveTV_Pass;
			    float execfrac = executed/Globals.LiveTV_Total_TC;
			    com = execfrac*100;
	    	pertc=Math.round((Globals.LiveTV_Time/(Globals.LiveTV_Fail+Globals.LiveTV_Pass)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	dom.select("#LiveTV_Completion").html(""+com);
	    	dom.select("#LiveTV_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	//OnDemand
	    	dom.select("#OnDemand_Pass").html(""+Globals.OnDemand_Pass);
	    	dom.select("#OnDemand_Fail").html(""+Globals.OnDemand_Fail);
	    	dom.select("#OnDemand_Pending").html(""+Globals.OnDemand_Pending);
	    	Globals.OnDemand_Total_TC= Globals.OnDemand_Pass+Globals.OnDemand_Fail+Globals.OnDemand_Pending;
	    	dom.select("#OnDemand_Total").html(""+Globals.OnDemand_Total_TC);
	    	dom.select("#OnDemand_Time").html(""+Globals.OnDemand_Time);
	    	if ((Globals.OnDemand_Fail+Globals.OnDemand_Pass)!= 0)
	    	{
	    		float executed = Globals.OnDemand_Fail+Globals.OnDemand_Pass;
			    float execfrac = executed/Globals.OnDemand_Total_TC;
			    com = execfrac*100;
	    	pertc=Math.round((Globals.OnDemand_Time/(Globals.OnDemand_Fail+Globals.OnDemand_Pass)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	dom.select("#OnDemand_Completion").html(""+com);
	    	dom.select("#OnDemand_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	//SportZone
	    	dom.select("#SportZone_Pass").html(""+Globals.SportZone_Pass);
	    	dom.select("#SportZone_Fail").html(""+Globals.SportZone_Fail);
	    	dom.select("#SportZone_Pending").html(""+Globals.SportZone_Pending);
	    	Globals.SportZone_Total_TC= Globals.SportZone_Pass+Globals.SportZone_Fail+Globals.SportZone_Pending;
	    	dom.select("#SportZone_Total").html(""+Globals.SportZone_Total_TC);
	    	dom.select("#SportZone_Time").html(""+Globals.SportZone_Time);
	    	if ((Globals.SportZone_Fail+Globals.SportZone_Pass)!= 0)
	    	{
	    		float executed = Globals.SportZone_Fail+Globals.SportZone_Pass;
			    float execfrac = executed/Globals.SportZone_Total_TC;
			    com = execfrac*100;
	    	pertc=Math.round((Globals.SportZone_Time/(Globals.SportZone_Fail+Globals.SportZone_Pass)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	dom.select("#SportZone_Completion").html(""+com);
	    	dom.select("#SportZone_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	//KidZone
	    	dom.select("#KidZone_Pass").html(""+Globals.KidZone_Pass);
	    	dom.select("#KidZone_Fail").html(""+Globals.KidZone_Fail);
	    	dom.select("#KidZone_Pending").html(""+Globals.KidZone_Pending);
	    	Globals.KidZone_Total_TC= Globals.KidZone_Pass+Globals.KidZone_Fail+Globals.KidZone_Pending;
	    	dom.select("#KidZone_Total").html(""+Globals.KidZone_Total_TC);
	    	dom.select("#KidZone_Time").html(""+Globals.KidZone_Time);
	    	if ((Globals.KidZone_Fail+Globals.KidZone_Pass)!= 0)
	    	{
	    		float executed = Globals.KidZone_Fail+Globals.KidZone_Pass;
			    float execfrac = executed/Globals.KidZone_Total_TC;
			    com = execfrac*100;
	    	pertc=Math.round((Globals.KidZone_Time/(Globals.KidZone_Fail+Globals.KidZone_Pass)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	dom.select("#KidZone_Completion").html(""+com);
	    	dom.select("#KidZone_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	//Settings
	    	dom.select("#Settings_Pass").html(""+Globals.Settings_Pass);
	    	dom.select("#Settings_Fail").html(""+Globals.Settings_Fail);
	    	dom.select("#Settings_Pending").html(""+Globals.Settings_Pending);
	    	Globals.Settings_Total_TC= Globals.Settings_Pass+Globals.Settings_Fail+Globals.Settings_Pending;
	    	dom.select("#Settings_Total").html(""+Globals.Settings_Total_TC);
	    	dom.select("#Settings_Time").html(""+Globals.Settings_Time);
	    	if ((Globals.Settings_Fail+Globals.Settings_Pass) != 0)
	    	{
	    		float executed = Globals.Settings_Fail+Globals.Settings_Pass;
			    float execfrac = executed/Globals.Settings_Total_TC;
			    com = execfrac*100;
	    	pertc=Math.round((Globals.Settings_Time/(Globals.Settings_Fail+Globals.Settings_Pass)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	dom.select("#Settings_Completion").html(""+com);
	    	dom.select("#Settings_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	//IPVOD
	    	dom.select("#IPVOD_Pass").html(""+Globals.IPVOD_Pass);
	    	dom.select("#IPVOD_Fail").html(""+Globals.IPVOD_Fail);
	    	dom.select("#IPVOD_Pending").html(""+Globals.IPVOD_Pending);
	    	Globals.IPVOD_Total_TC= Globals.IPVOD_Pass+Globals.IPVOD_Fail+Globals.IPVOD_Pending;
	    	dom.select("#IPVOD_Total").html(""+Globals.IPVOD_Total_TC);
	    	dom.select("#IPVOD_Time").html(""+Globals.IPVOD_Time);
	    	if ((Globals.IPVOD_Fail+Globals.IPVOD_Pass) != 0)
	    	{
	    		float executed = Globals.IPVOD_Fail+Globals.IPVOD_Pass;
			    float execfrac = executed/Globals.IPVOD_Total_TC;
			    com = execfrac*100;
	    	pertc=Math.round((Globals.IPVOD_Time/(Globals.IPVOD_Fail+Globals.IPVOD_Pass)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	dom.select("#IPVOD_Completion").html(""+com);
	    	dom.select("#IPVOD_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	//Downloads
	    	dom.select("#Downloads_Pass").html(""+Globals.Downloads_Pass);
	    	dom.select("#Downloads_Fail").html(""+Globals.Downloads_Fail);
	    	dom.select("#Downloads_Pending").html(""+Globals.Downloads_Pending);
	    	Globals.Downloads_Total_TC= Globals.Downloads_Pass+Globals.Downloads_Fail+Globals.Downloads_Pending;
	    	dom.select("#Downloads_Total").html(""+Globals.Downloads_Total_TC);
	    	dom.select("#Downloads_Time").html(""+Globals.Downloads_Time);
	    	if ((Globals.Downloads_Fail+Globals.Downloads_Pass) != 0)
	    	{
	    		float executed = Globals.Downloads_Fail+Globals.Downloads_Pass;
			    float execfrac = executed/Globals.Downloads_Total_TC;
			    com = execfrac*100;
	    	pertc=Math.round((Globals.Downloads_Time/(Globals.Downloads_Fail+Globals.Downloads_Pass)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	dom.select("#Downloads_Completion").html(""+com);
	    	dom.select("#Downloads_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	//total
	    	int total_Pass = Globals.Login_Pass + Globals.Library_Pass + Globals.Guide_Pass + Globals.LiveTV_Pass + Globals.OnDemand_Pass + Globals.KidZone_Pass + Globals.SportZone_Pass + Globals.Settings_Pass + Globals.IPVOD_Pass + Globals.Downloads_Pass ;
	    	int total_Fail = Globals.Login_Fail + Globals.Library_Fail + Globals.Guide_Fail + Globals.LiveTV_Fail + Globals.OnDemand_Fail + Globals.KidZone_Fail + Globals.SportZone_Fail + Globals.Settings_Fail + Globals.IPVOD_Fail + Globals.Downloads_Fail ;
	    	int total_Pending = Globals.Login_Pending + Globals.Library_Pending + Globals.Guide_Pending + Globals.LiveTV_Pending + Globals.OnDemand_Pending + Globals.KidZone_Pending + Globals.SportZone_Pending + Globals.Settings_Pending + Globals.IPVOD_Pending + Globals.Downloads_Pending ;
	    	int total_Total_TC = Globals.Login_Total_TC + Globals.Library_Total_TC + Globals.Guide_Total_TC + Globals.LiveTV_Total_TC + Globals.OnDemand_Total_TC + Globals.KidZone_Total_TC + Globals.SportZone_Total_TC + Globals.Settings_Total_TC + Globals.IPVOD_Total_TC + Globals.Downloads_Total_TC ;
	    	long total_Time = Globals.Login_Time + Globals.Library_Time + Globals.Guide_Time + Globals.LiveTV_Time + Globals.OnDemand_Time + Globals.KidZone_Time + Globals.SportZone_Time + Globals.Settings_Time + Globals.IPVOD_Time + Globals.Downloads_Time ;
	    	if ((total_Fail+total_Pass) != 0)
	    	{
	    		float executed =total_Fail+total_Pass;
			    float execfrac = executed/total_Total_TC;
			    com = execfrac*100;
	    	   	pertc=Math.round((total_Time/(total_Fail+total_Pass)));
	    	}
	    	else
	    	{
	    		com=0;
	    		pertc=0;
	    	}
	    	
	    	dom.select("#Total_Pass").html(""+total_Pass);
	    	dom.select("#Total_Fail").html(""+total_Fail);
	    	dom.select("#Total_Pending").html(""+total_Pending);
	    	dom.select("#Total_Total").html(""+total_Total_TC);
	    	dom.select("#Total_Time").html(""+total_Time);
	    	dom.select("#Total_Completion").html(""+com);
	    	dom.select("#Total_TimePerTC").html(""+pertc);
	    	per=0;
	    	com=0;
	    	pertc=0;
	    	
	    	if(!source.canWrite()) System.out.println("Can't write this file!"); //Just check if the file is writable or not
			BufferedWriter bw = new BufferedWriter(new FileWriter(source));
	        bw.write(dom.toString()); 
	        bw.close(); 
			
		}
	

	/*=====================================================================================================
	Auto_Update_TC_Report End .....
	=====================================================================================================*/

	/*=====================================================================================================
	* Function Name:FnGetDevice
	* Parameters: DeviceSheetName 
	* Description: This function is to get the list of devices with execution flag = "Y"
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Metadata_Validation()
	{
		try{
			Globals.UserName = Globals.TestData.get("UserName").toString();
 			Globals.Password = Globals.TestData.get("Password").toString();
 			Globals.Xml_Path = Globals.BinPath.replace("bin", "XML\\");
 			this.Get_Token();
 			this.Get_Guide_Xml();
 			this.Parse_Guide_Xml();
 		}
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name: FnGetDevice");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		
	}
    /*=====================================================================================================
    FnGetDevice End .....
    /*=====================================================================================================*/	
	
	/*=====================================================================================================
	* Function Name:FnGetDevice
	* Parameters: DeviceSheetName 
	* Description: This function is to get the list of devices with execution flag = "Y"
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Get_Token()
	{
		try
		{
			Globals.Method="POST";
			HttpClient HC = new HttpClient();
 			HC.httpRequest("Login");
 			
 			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder parser = factory.newDocumentBuilder();
			String LoginXml = Globals.Xml_Path+"Login.xml";
			org.w3c.dom.Document doc = parser.parse(LoginXml);
			NodeList nodelst  = doc.getElementsByTagName("AuthResponse");
			Node node = nodelst.item(0);
			Element ele = (Element) node;
			NodeList nl = ele.getChildNodes();
			for(int i=0;i<nl.getLength();i++)
			{
				Node child = nl.item(i);
				if(child.getNodeType()==Node.ELEMENT_NODE)
				{
				Element fin = (Element)child;
				if(fin.getTagName()=="Token")
				{
				Globals.Token=fin.getTextContent();
				System.out.println(Globals.Token);
				}
				//System.out.println(fin.getTagName());
				}
			}
		}
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name: FnGetDevice");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		
	}
    /*=====================================================================================================
    FnGetDevice End .....
    /*=====================================================================================================*/	
	

	/*=====================================================================================================
	* Function Name:FnGetDevice
	* Parameters: DeviceSheetName 
	* Description: This function is to get the list of devices with execution flag = "Y"
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Get_Guide_Xml()
	{
		try
		{
			Globals.Method="GET";
			HttpClient HC = new HttpClient();
 			HC.httpRequest("Guide");
 		}
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name: FnGetDevice");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		
	}
    /*=====================================================================================================
    FnGetDevice End .....
    /*=====================================================================================================*/

	/*=====================================================================================================
	* Function Name:FnGetDevice
	* Parameters: DeviceSheetName 
	* Description: This function is to get the list of devices with execution flag = "Y"
	* Author: Deepak_t04
	* Created Date: Nov 22, 2014
	=====================================================================================================*/
	public void Parse_Guide_Xml()
	{
		try{
			String content="",Query="";
			String GuideXml = Globals.Xml_Path+"Guide.xml";
			//Path path_Processed = Paths.get(Globals.Xml_Path+"Guide_Out.xml");
			Path path = Paths.get(GuideXml);
            //Path path = Paths.get("C:\\Users\\Deepak T M S\\Desktop\\Guide.xml");
            Charset charset = StandardCharsets.UTF_8;
            content = new String(Files.readAllBytes(path), charset);
            content = content.replaceAll("&", "&amp;");
            Files.write(path, content.getBytes(charset));
            //Copy channel excel from templates
            File SourceFile = new File(Globals.BinPath.replace("bin", "Template\\Channel.xls"));
			File DestinationFile = new File(Globals.BinPath.replace("bin", "Data\\Channel.xls"));
			if (DestinationFile.exists())
			{
				DestinationFile.delete();
			}
			FileUtils.copyFile(SourceFile, DestinationFile);
			
            Fillo fillo = new Fillo();
            Connection connection = fillo.getConnection(Globals.BinPath.replace("bin", "Data\\Channel.xls"));
            
            //File f = new File(pathout.toString());
            //if(f.exists())
            //{
              // f.delete();
            //}
             //f.createNewFile();
           
            
            //XML parsing
            String ChannelNumber="",ChannelName="",AdultContent="",Entitled="N",Favourite="N",LiveTV="N",HD="N",FamilyandEducation="N",Lifestyle="N",Movies="N",Music="N",NewsandWeather="N",Sports="N",Locals="N",Premium="N";
            DocumentBuilderFactory DBF = DocumentBuilderFactory.newInstance();
            DocumentBuilder DB = DBF.newDocumentBuilder();
            org.w3c.dom.Document Doc = DB.parse(path.toString());
            NodeList CHList = Doc.getElementsByTagName("Channel");
            for(int i =0;i<CHList.getLength();i++)
            {
                NodeList CHDList = CHList.item(i).getChildNodes();
                for (int j=0;j<CHDList.getLength();j++)
                {
                    if (CHDList.item(j).getNodeType()==Node.ELEMENT_NODE)
                    {
                        Element Temp = (Element)CHDList.item(j);
                        //Channel Number
                        if("ChannelNumber".equals(Temp.getTagName()))
                        {
                            ChannelNumber = Temp.getTextContent();
                            System.out.println(Temp.getTextContent());
                            continue;
                        }
                        //Channel Name
                        if("Network".equals(Temp.getTagName()))
                        {
                            ChannelName = Temp.getTextContent();
                            String act = ""+'\'';
                            String rep = ""+'\''+'\'';
                            ChannelName=ChannelName.replace(act, rep);
                            System.out.println(Temp.getTextContent());
                            continue;
                        }
                        //Entitled
                         if("Entitled".equals(Temp.getTagName()))
                        {
                            String valentitled =  Temp.getTextContent();
                            if (valentitled.equals("true"))
                            {
	                            Entitled = "Y";
                            }
                            System.out.println(Temp.getTextContent());
                            continue;
                        }
                         //LiveTV
                         if("Streamable".equals(Temp.getTagName()))
                        {
                            String valStreamable =  Temp.getTextContent();
                            if (valStreamable.equals("true"))
                            {
                            LiveTV = "Y";    
                            }
                            System.out.println(Temp.getTextContent());
                            continue;
                        } 
                         //HD
                          if("Format".equals(Temp.getTagName()))
                        {
                            String valFormat =  Temp.getTextContent();
                            if (valFormat .equals("HD"))
                            {
                            HD = "Y";    
                            }
                            System.out.println(Temp.getTextContent());
                            continue;
                        }
                          if (ChannelNumber.equals("649"))
                                  {
                                    System.out.println("test");
                                  }
                        if("NetworkCategories".equals(Temp.getTagName()))
                        {
                            NodeList NetworkList = CHDList.item(j).getChildNodes();
                            System.out.println("Camein $$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
                            for (int k=0;k<NetworkList.getLength();k++)
                            {
                                if (NetworkList.item(k).getNodeType()==Node.ELEMENT_NODE)
                                {
                                    Element TempNet = (Element)NetworkList.item(k);
                                    if("NetworkCategory".equals(TempNet.getTagName()))
                                    {
                                        String NetText = TempNet.getTextContent();
                                        //Family and Education
                                        if("Family & Education".equals(NetText))
                                        {
                                          FamilyandEducation="Y"; 
                                          System.out.println(NetText);
                                          continue;
                                        }
                                        //Lifestyle
                                        if("Lifestyle".equals(NetText))
                                        {
                                          Lifestyle="Y";
                                          System.out.println(NetText);
                                          continue;
                                        }
                                        //Movies
                                        if("Non-Premium Movies".equals(NetText))
                                        {
                                          Movies="Y";
                                          System.out.println(NetText);
                                          continue;
                                        }
                                        //Music
                                        if("Music".equals(NetText))
                                        {
                                          Music="Y";
                                          System.out.println(NetText);
                                          continue;
                                        }
                                        //News and Weather
                                        if("News & Weather".equals(NetText))
                                        {
                                          NewsandWeather="Y";
                                          System.out.println(NetText);
                                          continue;
                                        }
                                        //Sports
                                        if("Sports".equals(NetText))
                                        {
                                          Sports="Y";
                                          System.out.println(NetText);
                                          continue;
                                        }
                                        //Local
                                         if("Local".equals(NetText))
                                        {
                                          Locals="Y";
                                          System.out.println(NetText);
                                          continue;
                                        }
                                        //Premium
                                          if("Premiums".equals(NetText))
                                        {
                                          Premium="Y"; 
                                          System.out.println(NetText);
                                          continue;
                                        }
                                        
                                          //Add for favourites
                                    }
                                }
                            } 
                        } 
                    }
                   
                }
            //update in excel
            
               Query = "INSERT INTO ChannelDetails (ChannelNumber,ChannelName,Entitled,Favourite,LiveTV,HD,FamilyandEducation,Lifestyle,Movies,Music,NewsandWeather,Sports,Locals,Premium) Values (\'";
               Query=Query+ChannelNumber+"\',\'"+ChannelName+"\',\'"+Entitled+"\',\'"+Favourite+"\',\'"+LiveTV+"\',\'"+HD+"\',\'"+FamilyandEducation+"\',\'"+Lifestyle+"\',\'"+Movies+"\',\'"+Music+"\',\'"+NewsandWeather+"\',\'"+Sports+"\',\'"+Locals+"\',\'"+Premium+"\')";
               System.out.println(Query);
               connection.executeUpdate(Query);
               
               ChannelNumber="";ChannelName="";Entitled="N";Favourite="N";LiveTV="N";HD="N";FamilyandEducation="N";Lifestyle="N";Movies="N";Music="N";NewsandWeather="N";Sports="N";Locals="N";Premium="N";
            }
            
            
            System.out.println(CHList.getLength()); 
            connection.close();
         }
                catch (ParserConfigurationException ex) {
           
        } 
                catch (IOException ex) {
            
        }

 		
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name: FnGetDevice");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		
	}
    /*=====================================================================================================
    FnGetDevice End .....
    /*=====================================================================================================*/


	/*=====================================================================================================
	* Function Name:Get_Channel_List
	* Parameters: Filter option(s) 
	* Description: The function is to get the 1 or more filter option and validate it with the channel list
	* Author: Edwin_Paul
	* Created Date: Jan 23, 2015
	=====================================================================================================*/
	public void Get_Channel_List(String Filter)
	{
		String Column = "", Query = "";
		String str1 = new String(Filter);
		//Filter options are: Entitled, Favourite, LiveTV, HD, FamilyandEducation, Lifestyle, Movies, Music, NewsandWeather, Sports, Locals, Premium
		//Globals.Channel_List.put("Temp", "Temp");
		//Globals.Channel_List.clear();
		try{
			System.out.println("Error : "+Filter);
			String[] list = str1.split("-");
			int ncount = list.length;
			if(ncount == 1)
			{
				Query = "Select * from ChannelDetails where "+list[0]+"= 'Y'";
				System.out.println(Query);
			}
			else if(ncount == 2)
			{
				Query = "Select * from ChannelDetails where "+list[0]+"= 'Y' and "+list[1]+"= 'Y'";
				System.out.println(Query);
			}
			else if(ncount == 3)
			{
				Query = "Select * from ChannelDetails where "+list[0]+"= 'Y' and "+list[1]+"= 'Y' and "+list[2]+"= 'Y'";
				System.out.println(Query);
			}
			else if(ncount == 4)
			{
				Query = "Select * from ChannelDetails where "+list[0]+"= 'Y' and "+list[1]+"= 'Y' and "+list[2]+"= 'Y' and "+list[3]+"= 'Y'";
				System.out.println(Query);
			}
			else if(ncount == 5)
			{
				Query = "Select * from ChannelDetails where "+list[0]+"= 'Y' and "+list[1]+"= 'Y' and "+list[2]+"= 'Y' and "+list[3]+"= 'Y' and "+list[4]+"= 'Y'";
				System.out.println(Query);
			}
			else
			{
				System.err.println("Console Error: We able to handle upto 5 filters");
			}
			Fillo fillo=new Fillo();
			
			Connection connection=fillo.getConnection(Globals.BinPath.replace("bin", "Data\\Channel.xls"));
			//system.out.println(DeviceSheetName);
			//String Query = "Select * from ChannelDetails where "+Column+"= 'Y'";
			Recordset recordset=connection.executeQuery(Query);
			while(recordset.next())
			{
				Globals.Channel_List.put(recordset.getField("ChannelNumber").toString(),"N".toString());
				System.out.println(recordset.getField("ChannelNumber").toString());
		    }
			recordset.close();
			connection.close();
 		}
		
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name: FnGetDevice");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
	}
    /*=====================================================================================================
    Get_Channel_List End .....
    /*=====================================================================================================*/
	
}