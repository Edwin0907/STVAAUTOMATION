package TestScripts;

import com.experitest.client.Client;

public class STSupportFunctions {

	/*=====================================================================================================
	 * Function Name: NativeClick
	 * Description: Checking existence of a object and clicking on the same
	 * Parameter: Object Reference, See test Client object	
	 * Author: Deepak_t04
	 * Date: 12/2/2014
	/*=====================================================================================================*/
	public boolean NativeClick(String ObjRef,Client client)
	{
		if(this.NativeElementFound(ObjRef, client))
		{
			client.click("NATIVE",ObjRef ,0, 1, 0, 0);
			return true;
		}
		else
		{
			System.out.println(ObjRef+"not found");
			return false;
		}
	 }
	/*=====================================================================================================
	NativeClick End .....
	=====================================================================================================*/
	

	/*=====================================================================================================
	 * Function Name: NativeElementSendTxt
	 * Description: Checking existence of a object and sending text  for the same
	 * Parameter: Object Reference, Text to be sent, See test Client object	
	 * Author: Deepak_t04
	 * Date: 12/2/2014
	/*=====================================================================================================*/
	public boolean NativeElementSendTxt(String ObjRef,String Value,Client client)
	{
		if(this.NativeElementFound(ObjRef, client))
		{
			client.elementSendText("NATIVE", ObjRef, 0, Value);
			return true;
		}
		else
		{
			System.out.println(ObjRef+"not found");
			return false;
		 }
	}
	/*=====================================================================================================
	NativeElementSendTxt End .....
	=====================================================================================================*/
	
	/*=====================================================================================================
	 * Function Name: NativeElementFound
	 * Description: Checking existence of a object 
	 * Parameter: Object Reference, See test Client object	
	 * Author: Deepak_t04
	 * Date: 12/2/2014
	/*=====================================================================================================*/
	public boolean NativeElementFound(String ObjRef,Client client)
	{
		boolean Found=false;
		try
		{
			//if(client.waitForElement("NATIVE", "xpath=//*[@id='home']", 0, 10000))
			if(client.waitForElement("NATIVE", ObjRef, 0, 3000))
			{
				Found = true;
			}
		}
		catch(Exception e)
	    {
		
	    }
		return Found;
	}
	/*=====================================================================================================
	NativeElementFound End .....
	=====================================================================================================*/
	
	/*=====================================================================================================
	 * Function Name: NativeElementFound
	 * Description: Checking existence of a object 
	 * Parameter: Object Reference, See test Client object	
	 * Author: Deepak_t04
	 * Date: 12/2/2014
	/*=====================================================================================================*/
	public String NativeGetElementText(String ObjRef,int Index,Client client)
	{
		String Temp_Var="";
		if(this.NativeElementFound(ObjRef, client))
		{
			Temp_Var=client.elementGetText("NATIVE", ObjRef, Index);
		}
		else
		{
			System.out.println(ObjRef+"not found");
		}
		return Temp_Var;
	}
	/*=====================================================================================================
	NativeElementFound End .....
	=====================================================================================================*/
	/*=====================================================================================================
	 * Function Name: NativeElementFound
	 * Description: Checking existence of a object 
	 * Parameter: Object Reference, See test Client object	
	 * Author: Deepak_t04
	 * Date: 12/2/2014
	/*=====================================================================================================*/
	public boolean NativeElementWaitTillVanish(String ObjRef,Client client)
	{
		boolean Found=false;
		try
		{
			if(client.isElementFound("NATIVE", ObjRef))
			{
				if(client.waitForElementToVanish("NATIVE",ObjRef , 0, 30000))
				{
					Found = true;
				}
			}
		}
		catch(Exception e)
	    {
		
	    }
		return Found;
	}
	/*=====================================================================================================
	NativeElementFound End .....
	=====================================================================================================*/
	

}


