package Batch;

import java.io.File;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import TestScripts.AppNewFunctions;
import TRDServiceClient.FindAPI;
import org.testng.annotations.Test;

import Engine.EngineFunction;


public class MasterScript 
{
	EngineFunction EF = new EngineFunction();
	Globals g = new Globals();
	AppNewFunctions app = new AppNewFunctions();
	FindAPI TR = new FindAPI();
	@Test
	public void fnTestMain()
	{
		//TR.Connect_TestRail();
		//Release name or Build name
		g.Current_Build = "iOS_Execution";
		g.Result_Path = "D:\\";
		
		/*Modified to retrieve path Using Main class name - modified by Deepak*/
		final File f = new File(MasterScript.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		System.out.println(f.getPath());
		try
		{
			g.BinPath = URLDecoder.decode(f.getPath(),"UTF-8");
		}
		catch(Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name : MasterScript");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		
		//Creating base folder for reporting
		//EF.Get_Channel_List("Music");
		EF.Create_Log_ReportBaseFolder();
		EF.Create_HTML_Reference();
		
		try
		{
			//Getting Device List and details 
			String FinalPath = g.BinPath.replace("bin", "Data\\Device_Sheet.xls");
			g.DeviceList=EF.FnGetDevice(FinalPath);
			
			//iteration for each and every device 
			for(int DC=1;DC<=g.DeviceList.size();DC++)
			{
				g.Current_OS = g.DeviceList.get(Integer.toString(DC)).toString().split("\\|\\|")[1];	
				g.Current_Device = g.DeviceList.get(Integer.toString(DC)).toString().split("\\|\\|")[0];
				EF.Create_Log_ReportFolder();
				
				//Getting all the objects based on the current OS
				FinalPath = g.BinPath.replace("bin", "Data\\OR_Sheet.xls");
				g.ObjectCollection = EF.FnGetObject(FinalPath,g.Current_Device,g.Current_OS);
				
				//Getting Test Case Details
				g.TestData_Path = g.BinPath.replace("bin", "Data\\TestData.xls");
				g.TDColumn_Count = Integer.parseInt((EF.FnTableLength(g.TestData_Path)).split("\\|\\|")[1]) ;
				g.TDRow_Count = Integer.parseInt((EF.FnTableLength(g.TestData_Path)).split("\\|\\|")[0]) ;
				
				//Executing Test case wise
				for(g.TD=1 ;g.TD<g.TDRow_Count;g.TD++)
				{
					g.TestData = EF.FnGetTestData(g.TestData_Path,g.TDRow_Count,g.TDColumn_Count,g.TD);
					if (g.TestData.get("Execution_Flag").toString().equalsIgnoreCase("Y"))
					{
						Calendar cal = Calendar.getInstance();
						SimpleDateFormat sdf1= new SimpleDateFormat("HH:mm:ss");
				    	g.TC_Start_Time=""+sdf1.format(cal.getTime());
				    	//Globals.TC_Execution_Status="Pass";
				    	g.Current_TestCase =  g.TestData.get("TestCaseName").toString();
				    	EF.Clear_Previous_TC_Batch_Report();
						//Creating result folder specific to Test Cases
				    	EF.Create_TCReport();
						g.StepCounter = 0;
						app.Fn_SetSeeTestReport();
						EF.FnCom_ExecuteTC ();
						app.Fn_GenerateSeeTestReport();
						//Update Test Case result in the Bath summary sheet
						EF.Auto_Update_TC_Report();
					}
				}
				//TR.CallTestRail();
			}	
		}
		catch(Exception e)
		{
			System.err.println("Master Script error :");
			System.err.println("	Function : fnTestMain");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
	}
	
}
