package Batch;
import java.io.File;
import java.io.IOException;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;
public class testData {	
	/*public void dataMapping(String[] arr) throws IOException, RowsExceededException, WriteException
	{
		File file = new File("data.xls");
		WritableWorkbook wrk = Workbook.getWorkbook("C:\\Users\\edwin_paul\\workspace\\filter_data\\Data");
		WritableSheet wrkSheet = wrk.createSheet("Data", 0);
		for (int i=0;i < arr.length;i++)
		{
		Label lb1 = new Label(0,0,arr[i]);
		wrkSheet.addCell(lb1);
		}
		
		wrk.write();
		wrk.close();
		
	}*/
	/*public void writeData(String[] arr, String[] arr1)
	{
		File file = new File("C:\\Users\\edwin_paul\\workspace\\filter_data\\Data");
		Workbook wrk1 = Workbook.getWorkbook(file);
		Sheet sheet1 = wrk1.getSheet("data_sheet");
		Label lb1 = new Label(0,0,"");
		sheet1.getRow(0);
		wrk1.write();
		wrk1.close();
	}*/
	
	public void FnWriteXL(String[] arr, String[] arr1) throws IOException, RowsExceededException, WriteException
	{
		int nRow=0;
		File file = new File("C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\UIValidations.xls");
		WritableWorkbook wrk = Workbook.createWorkbook(file);
		wrk.createSheet("Data", 0);
		
		wrk.getSheet("Data").addCell(new Label(0, 0, "Channel Number"));
		wrk.getSheet("Data").addCell(new Label(1, 0, "Channel Name"));
		 
		
		//WritableSheet wrkSheet = 
		System.out.println("Array val length: " +arr.length);
        for(int i=0;i<arr.length;i++)
        {
      	  String val=arr[i].toString();
      	  if(val.isEmpty())
      	  {
      		  System.out.println("Values is null");      	  
      	  }else{      	 
	          wrk.getSheet("Data").addCell(new Label(nRow, i+1, val));      
	          
      	  }
        }
        
        System.out.println("String array name length : "+ arr1.length );
        for(int i=0;i<arr1.length;i++)
        {
      	  String val=arr1[i].toString();
      	  if(val.isEmpty())
      	  {
      		  System.out.println("Values is null");      	  
      	  }else{      	 
	          wrk.getSheet("Data").addCell(new Label(nRow+1, i+1, val));
	          
	          //wrk.getSheet("data_sheet").addCell(new Label(i, nRow1+1, val));      
      	  }
        }        
        wrk.write();
        wrk.close();	
	}
	
	/*public void FnWriteXL(String[] arr, String[] arr1)
	{
		int intRow=0;
		int intRow1=1;

		  try
	      {	         
			  String strWorkBookName = "C:\\Users\\edwin_paul\\workspace\\filter_data\\Data\\data.xls";
	          
			  File outputWorkbook = new File(strWorkBookName);
	          
	          Workbook strWorkBook = Workbook.getWorkbook(new File(strWorkBookName));
	          WritableWorkbook w2 = Workbook.createWorkbook(outputWorkbook, strWorkBook);
	          w2.copy(strWorkBook);   
	          
	          System.out.println("Array val length: " +arr.length);
	          for(int i=0;i<arr.length;i++)
	          {
	        	  String val=arr[i].toString();
	        	  if(val.isEmpty())
	        	  {
	        		  System.out.println("Values is null");
	        	  
	        	  }else{
	        	 
		          w2.getSheet("data_sheet").addCell(new Label(i, intRow+1, val));
	        	  
	        	  }
	          }
	          System.out.println("String array name length : "+ arr1.length );
	          for(int i=0;i<arr1.length;i++)
	          {
	        	  String val=arr[i].toString();
	        	  if(val.isEmpty())
	        	  {
	        		  System.out.println("Values is null");
	        	  
	        	  }else{
	        	 
		          w2.getSheet("data_sheet").addCell(new Label(i, intRow1+1, val));
	        	  
	        	  }
	          }
	          w2.write();
	          w2.close();
	          //calling the method to get the XML data
	          
	      }catch(Exception e)
	      {
	    	  System.err.print(e.getMessage());
	    	  System.err.print(e.getCause());
	      }
	}
*/
}
