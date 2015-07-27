package TRDServiceClient;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.ServiceClient;

import Fillo.Connection;
import Fillo.Fillo;
import Fillo.Recordset;
import TRDServiceClient.TestRailDataServiceStub.ArrayOfKeyValueOfstringArrayOfTestStatusNeh7CBqX;
import TRDServiceClient.TestRailDataServiceStub.ArrayOfTestStatus;
import TRDServiceClient.TestRailDataServiceStub.DoUpdateTestCaseStatus;
import TRDServiceClient.TestRailDataServiceStub.DoUpdateTestCaseStatusResponse;
import TRDServiceClient.TestRailDataServiceStub.KeyValueOfstringArrayOfTestStatusNeh7CBqX_type0;
import TRDServiceClient.TestRailDataServiceStub.TestStatus;
import TRDServiceClient.TestRailDataServiceStub.TestStatusDictionary;

public class FindAPI {

	/**
	 * @param args
	 * @throws RemoteException 
	 */
	
	static String strTestID = "";
	static int strResult = 0;
	static Map<String,String> TitleList = new HashMap<String,String>();
	public void CallTestRail() throws RemoteException 
	{
		TestRailDataServiceStub trdService = new TestRailDataServiceStub();
		DoUpdateTestCaseStatus tStatus = new DoUpdateTestCaseStatus();
		TestStatusDictionary tsDictionary = new TestStatusDictionary();
		ArrayOfKeyValueOfstringArrayOfTestStatusNeh7CBqX arrayVals = new ArrayOfKeyValueOfstringArrayOfTestStatusNeh7CBqX();
		KeyValueOfstringArrayOfTestStatusNeh7CBqX_type0 kvPair = new KeyValueOfstringArrayOfTestStatusNeh7CBqX_type0();
		ArrayOfTestStatus testStatus = new ArrayOfTestStatus();
		
		/*
		 * Workout area
		 * file:///C:\Users\edwin_paul\workspace\CTVA_Automation_Integrated\Report\Test_Edwin_Integrate_0\Reference\Test_Edwin_Integrate_0.xls
		 */
		
		String path = "C:\\Users\\edwin_paul\\workspace\\CTVA_Automation_Integrated\\Report\\Test_Edwin_Integrate_0\\Reference\\Test_Edwin_Integrate_0.xls";
		
		FnGetSheet(path);
		
		for (Map.Entry<String, String> entry : TitleList.entrySet()) 
		{
		    //System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
		    TestStatus singleTestStatus = new TestStatus();
			singleTestStatus.setTest_id(entry.getKey().toString());
			singleTestStatus.setStatus_id(entry.getValue().toString());
			
			testStatus.addTestStatus(singleTestStatus);
		}
		kvPair.setKey("results");
		kvPair.setValue(testStatus);
		
		arrayVals.addKeyValueOfstringArrayOfTestStatusNeh7CBqX(kvPair);
		tsDictionary.setTestStatusDic(arrayVals);
		tStatus.setName("mselvaraj");
		tStatus.setPassword("Ctva1001");
		tStatus.setUri("/add_results/5456");
		tStatus.setRequestObject(tsDictionary);
		DoUpdateTestCaseStatusResponse resp = trdService.doUpdateTestCaseStatus(tStatus);

	}
	
	public static Map FnGetSheet(String SheetName)
	{
		try
		{ 
			Fillo fillo=new Fillo();
			int Counter=1;
			String TestStatus = "";
			System.out.println("SheetName:"+SheetName);
			Connection conn=fillo.getConnection(SheetName);
			//System.out.println(SheetName);
			//String DeviceQuery="Select Title from Title_Sheet";
			String DeviceQuery="Select ManualTestCase,ExecutionStatus From OverallTC";
			Recordset recordset=conn.executeQuery(DeviceQuery);
			while(recordset.next())
			{
				if(recordset.getField("ExecutionStatus").equalsIgnoreCase("Pass"))
				{
					TestStatus = "1";
				}
				else if(recordset.getField("ExecutionStatus").equalsIgnoreCase("Fail"))
				{
					TestStatus = "5";
				}
				else
				{
					TestStatus = "2";
				}
				TitleList.put(recordset.getField("ManualTestCase").substring(1), TestStatus);
				System.out.println("map "+TitleList);
				TestStatus = "2";
			}
			recordset.close();
			conn.close();
		}
		catch (Exception e)
		{
			System.err.println("Engine Function error :");
			System.err.println("Function Name: FnGetDevice");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
		}
		return TitleList;
	}
}
