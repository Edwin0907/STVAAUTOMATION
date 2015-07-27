package TestScripts;

//import imagestudio.model.FileUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import jsystem.utils.FileUtils;
import jxl.Workbook;
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableWorkbook;

import com.experitest.client.Client;

public class SupportFunctions {


	public int intRowCount=0;
	public int intColCount=0;
	public String strCaseName=null;
	public static Map m1= new HashMap<String, String>();
	public static String FileName;
	Client client;

	public static String[] Strconfig =new String[10];
	/*=====================================================================================================
	
	Function Name:FnGetParam
	Parameters: strWorkBookName 
	Description: This function is used to execute the test scripts with the Execution flag "Y"
	Author: Dhivya Durairaj
	Created Date: Jan 10, 2014
	=====================================================================================================*/
	
	public String FnCom_GetParam(String strWorkBookName)
	{
		try{
			
			Calendar c = Calendar.getInstance();
			SimpleDateFormat s=new SimpleDateFormat("ddMMyyyy_ssmmHH");
			String t=s.format(c.getTime());
			
			//  path setting based on the framework folder structure - Deepak
			
			FileName = strWorkBookName.replace("TestData.xls", "UIVal_"+t+".xls");
			//FileName="C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\UIVal_"+t+".xls";
			FnCreateXLS(FileName);
			Workbook strWorkBook = Workbook.getWorkbook(new File(strWorkBookName));
			intRowCount=strWorkBook.getSheet("TestData").getRows();
			intColCount=strWorkBook.getSheet("TestData").getColumns();			
			String[] arrColHeader=new String[intColCount];
			
			for(int iLoop=0;iLoop<intRowCount;iLoop++)
			{
				String strExecFlag=strWorkBook.getSheet("TestData").getCell(1, iLoop).getContents();
				if(strExecFlag.equalsIgnoreCase("Y"))
				{
					strCaseName=strWorkBook.getSheet("TestData").getCell(0, iLoop).getContents();
					System.out.println(strCaseName);
					//int intR=strWorkBook.getSheet("TestData").findCell(strCaseName).getRow();
					//int intC=strWorkBook.getSheet("TestData").findCell(strCaseName).getColumn();
				//	System.out.println("TC row -- "+intR+ " -- TC Col" +intC);
					
					for(int i=0;i<intColCount;i++)
					{
						String strVal=strWorkBook.getSheet("TestData").getCell(i, 0).getContents();				
						arrColHeader[i]=strVal;						
					}
					
					for(int j=0;j<intColCount;j++)	
					{
						m1.put(arrColHeader[j],strWorkBook.getSheet("TestData").getCell(j, iLoop).getContents());
						//System.out.println(m1.size());
						System.out.println(m1.get(arrColHeader[j]).toString());
						
					}
					
					System.out.println("Map size: == " +m1.size());
					//FnCom_ExecuteTC(strCaseName,m1);
					m1.clear();
				}
			}			
			// return intRowCount;
		}catch (BiffException e) {
			
			e.printStackTrace();
		}catch (IOException e) {
			
			e.printStackTrace();
		}
		return strCaseName;		
	}
	
	
	
	/*=====================================================================================================	
	Function Name: Fn_ExecuteTC
	Parameters: strCaseName, Map object 
	Description: This function is used to execute the Method. The test case name is the method name and 
	all the data table value are passed as Map object
	Author: Dhivya Durairaj
	Created Date: Jan 10, 2014
	=====================================================================================================*/
	
	/*public void FnCom_ExecuteTC(String strCaseName, Map m1)
	{
		String strTestName=strCaseName;
		Class noparams[] = {};
		try{
			//Class cls = Class.forName("TestScripts.TestScripts");
			Class cls = Class.forName("TestScripts.NewTestScripts");
			Object obj = cls.newInstance();
			
			Class[] paramString = new Class[1];	
			paramString[0] = Map.class;
		
			Method method = cls.getDeclaredMethod(strTestName, paramString);
			method.invoke(obj, m1);
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}*/
	
	public void FnCreateXLS(String FileName)
	{
		m1.put("FileName", FileName);
		try {
		   File file = new File(FileName);
		   WritableWorkbook wrk = Workbook.createWorkbook(file);
		   wrk.createSheet("Sheet1", 0);
		   wrk.write();
		   wrk.close();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			
		}
	}
	

	public String FnCreateXLSGuideResponse() throws BiffException, IOException
	{
		String f="C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\Guide_Template.xlsx"; 
		Calendar c = Calendar.getInstance();
		SimpleDateFormat s=new SimpleDateFormat("ddMMyyyy_ssmmHH");
		String t=s.format(c.getTime());
		String FileName="C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\GuideResp_"+t+".xlsx";
		m1.put("JSONFileName", FileName);
		try {


			File outputWorkbook = new File(f);
			File file = new File(FileName);
			/*Workbook strWorkBook = Workbook.getWorkbook(new File(f));  
		   WritableWorkbook wrk=Workbook.createWorkbook(file,strWorkBook);		   
		  // file1.copy(strWorkBook);

		//   WritableWorkbook wrk = Workbook.createWorkbook(outputWorkbook,strWorkBook);
		   wrk.copy(strWorkBook);

		   wrk.write();
		   wrk.close();*/
			//Alter change after the upgrade of SeeTest 8.2 version
			FileUtils.copyFile(outputWorkbook, file);
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getCause());

		}
		return FileName;
	}
	
	
	public void FnWriteXL(String strWB,String SheetName,Map Map1)
	{
		try {

				
				 System.out.println("file Name: "+strWB);
			//	 strWB="C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\UIVal_02082014_364615.xls";
			   
				 
			    int intChNumCol=0, intChNameCol=1;
			    String[] MapKeys=new String[Map1.size()];
		        String[] MapVal=new String[Map1.size()];
		        
		        File outputWorkbook = new File(strWB);
                Workbook strWorkBook = Workbook.getWorkbook(new File(strWB));
                WritableWorkbook w2 = Workbook.createWorkbook(outputWorkbook, strWorkBook);
                w2.copy(strWorkBook);   

                w2.createSheet(SheetName, 0);
		        Iterator<String> itr = Map1.keySet().iterator();
		        int intC=0;
				while(itr.hasNext())
				{
				String key = (String) itr.next();
				MapKeys[intC]=key;
				System.out.println("Map size is : "+Map1.size());
				if(intC<Map1.size())
				{
				        intC++;
				}
				}
				
				for(int i=0;i<MapKeys.length;i++)
				{				
				   String strKey=MapKeys[i]; 
				   String strValue=(String) Map1.get(strKey).toString();
				   System.out.println(strKey + "--- "+ strValue);                 
				   MapVal[i]= strValue; 		
				   
				   w2.getSheet(SheetName).addCell(new Label(intChNumCol, i+1, strKey));
				   w2.getSheet(SheetName).addCell(new Label(intChNameCol, i+1, strValue));
				   
				}
				
				w2.write();
				w2.close();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
	}
	
	

	public void FnWriteXLAsset(String strWB,String SheetName,Map Map1)
	{
		try {

				
				 System.out.println("file Name: "+strWB);
			//	 strWB="C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\UIVal_02082014_364615.xls";
			   
				 
			    int intChNumCol=0, intChNameCol=1;
			    String[] MapKeys=new String[Map1.size()];
		        String[] MapVal=new String[Map1.size()];
		        
		        File outputWorkbook = new File(strWB);
                Workbook strWorkBook = Workbook.getWorkbook(new File(strWB));
                WritableWorkbook w2 = Workbook.createWorkbook(outputWorkbook, strWorkBook);
                w2.copy(strWorkBook);   

                w2.createSheet(SheetName, 0);
		        Iterator<String> itr = Map1.keySet().iterator();
		        int intC=0;
				while(itr.hasNext())
				{
				String key = (String) itr.next();
				MapKeys[intC]=key;
				System.out.println("Map size is : "+Map1.size());
				if(intC<Map1.size())
				{
				        intC++;
				}
				}
				
				for(int i=0;i<MapKeys.length;i++)
				{				
				   String strKey=MapKeys[i]; 
				   String strValue=(String) Map1.get(strKey).toString();
				   System.out.println(strKey + "--- "+ strValue);                 
				   MapVal[i]= strValue;
				   
				   
				   w2.getSheet(SheetName).addCell(new Label(intChNumCol, i+1, strKey));
				   w2.getSheet(SheetName).addCell(new Label(intChNameCol, i+1, strValue));
				   
				}
				
				w2.write();
				w2.close();
		}catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
		}
	}
	
	public static String FnGetRelativePath()
	{
		String rp=new File(System.getProperty("user.dir")).getAbsolutePath();
		return rp;
	}
	
	
	public void FnReadConfig() throws IOException
	{
		try {
		String dir=FnGetRelativePath();
		String conf=dir+"\\src\\Guide_Json\\config.properties";	
	
		Properties pConfig=new Properties();
		FileReader config=new FileReader(conf);
		
		pConfig.load(config);
		
		Strconfig[0]=pConfig.getProperty("AuthenticationService");
		Strconfig[1]=pConfig.getProperty("ExcelWrtiePath");
		Strconfig[2]=pConfig.getProperty("Password");
		Strconfig[3]=pConfig.getProperty("PROD");
		Strconfig[4]=pConfig.getProperty("STAGE");
		Strconfig[5]=pConfig.getProperty("SymphonyService");
		Strconfig[6]=pConfig.getProperty("UserName");	

		m1.put("AuthenticationService", Strconfig[0]);
		m1.put("ExcelWrtiePath", Strconfig[1]);
		m1.put("Password", Strconfig[2]);
		m1.put("PROD", Strconfig[3]);
		m1.put("STAGE", Strconfig[4]);
		m1.put("SymphonyService", Strconfig[5]);
		m1.put("UserName", Strconfig[6]);
		
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		}
	}

	public boolean Lfn_WaitforElement(String str)
	{
		client.waitForElement("NATIVE", str, 0, 30000);
		if(client.isElementFound("NATIVE", str))
		{
			System.out.println("Element is detected");
			return true;
		}
		else
		{
			System.out.println("Element is not detected for the path :"+str);
			return false;
		}
	}
	public boolean Lfn_Click(String str)
	{
		if(client.isElementFound("NATIVE", str))
		{
			client.click("NATIVE", str, 0, 1);
			return true;
		}
		else
		{
			System.out.println("Element is not identified");
		}
		return false;
	}
}
