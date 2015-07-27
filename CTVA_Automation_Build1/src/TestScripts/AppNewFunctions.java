package TestScripts;
//import java.io.File;
//import java.io.UnsupportedEncodingException;
//import java.net.URLDecoder;

//import imagestudio.model.FileUtils;

//import java.io.File;
//import java.io.IOException;
//import java.sql.SQLException;
import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Calendar;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

//import org.apache.commons.lang3.text.WordUtils;
import org.apache.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;

import Batch.Globals;

//import Batch.MasterScript;
import Guide_Json.GuideValidations;

import bsh.ParseException;

import com.experitest.client.Client;

//import ctva_rest_api_client.CTVA_Request_Helper;
//import ctva_rest_api_client.HttpVerb;
import Engine.EngineFunction;
import java.util.Date;

//import jxl.read.biff.BiffException;


public class AppNewFunctions extends Globals{
	//private Client client;
	public Map<String, String> Map1 = new HashMap<String, String>();
	STSupportFunctions ST = new STSupportFunctions();
	EngineFunction EF = new EngineFunction();
	//channelnamelineup CL = new channelnamelineup();
	String host = "localhost";
	int port = 8890;
	static Client client = null;
	public AppNewFunctions()
	{
		
		
		//String projectBaseDirectory = BinPath.replace("\\bin", "");
		//private String projectBaseDirectory = "C:\\Users\\edwin_paul\\workspace\\project5";
		//Client client = null;
		//System.out.println("inside before");
		
		//client = new Client(host,port,blnFlag);
		//client.setProjectBaseDirectory(projectBaseDirectory);
		//client.setReporter("xml", "reports", Globals.Current_TestCase);
		//System.out.println(client.getHost().toString());
		//client.setDevice(Current_Device);
		//this.client = client;
	}
	static Logger log = Logger.getLogger(GuideValidations.class.getName());
	
	boolean blnFlag;
	


	@BeforeTest
	public void setUp(String strTCName) 
	{
		/*System.out.println("inside before");
		client = new Client(host, port);
		//client = new Client(host,port,blnFlag);
		client.setProjectBaseDirectory(projectBaseDirectory);
		client.setReporter("xml", "reports", strTCName);
		System.out.println(client.getHost().toString());*/

	}
	
	@AfterSuite
	public void tearDown() 
	{
		client.generateReport(true);
	}

	/**********************************************************************************************/
	// Set SeeTest Report
	/**********************************************************************************************/
	public void Fn_SetSeeTestReport()
	{
		if(client != null)
			client.releaseClient();
		client = new Client(host, port, true);
		String projectBaseDirectory = BinPath.replace("\\bin", "");
		client.setProjectBaseDirectory(projectBaseDirectory);
		client.setReporter("xml", "reports", Globals.Current_TestCase);
		client.setDevice(Current_Device);
		//client.launch(ObjectCollection.get("App_URL").toString(), true, true);
	}
	/**********************************************************************************************/
	// 									End Fn_SetSeeTestReport
	/**********************************************************************************************/
	/**********************************************************************************************/
	// Generate SeeTest Report
	/**********************************************************************************************/
	public void Fn_GenerateSeeTestReport()
	{
		client.generateReport(false);
		client.releaseClient();
	}
	/**********************************************************************************************/
	// 									End Fn_GenerateSeeTestReport
	/**********************************************************************************************/
	public void Fn_NavigationIcon_Navigation()
	{
		
		try
		{
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			Boolean bFlag=false;
			String drawerXpath[]={"Home_MyLibrary","Home_Guide","Home_LiveTV","Home_OnDemand","Home_sportZone","Home_KidZone","Home_Settings"};
			//Looping over all the screens to check the presence of navigation icon
			for(int i=0;i<drawerXpath.length;i++)
			{	
				//Opening the Navigation drawer if not opened by default
				while(!(client.isElementFound("NATIVE", ObjectCollection.get("Home_PageList").toString(),0)))
				{
							//Verifying and clicking on navigation icon
			                bFlag=ST.NativeClick(ObjectCollection.get("Home_icon").toString(),client);
			                //Updating report for the presence or absence of Navigation Icon
			                EF.Update_Step_Report("Navigation Icon should be present in all screens", "Navigation Icon is present||Navigation Icon is not present",bFlag);
			     }
				 ST.NativeClick(ObjectCollection.get(drawerXpath[i]).toString(),client);
				 //Handling Pop Over appears while navigating to different screens
				 if(ST.NativeElementFound(ObjectCollection.get("Home_Page_PopOver").toString(), client))
				 {
					 ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					 if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
					 {
						 ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(),client);
					 }
				 }
				 else if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
				 {
					 ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(),client);
					 if(ST.NativeElementFound(ObjectCollection.get("Home_Page_PopOver").toString(), client))
					 {
						 ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					 }
				 }
			}
			client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Loading").toString(),0, 300000);
			ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(),client);
		}
		catch(Exception e)
		{
			System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
		}
	}
	public void Fn_Home_NavigationDrawer_Contents()
	{
		
		try
		{
			
			Boolean bFlag= false;
			String drawerXpath[]={"Home_MyLibrary","Home_Guide","Home_LiveTV","Home_OnDemand","Home_SportZone","Home_KidZone","Home_Settings"};
			//String pageTitle[]={"My Library","Guide","Live TV","On Demand","Sport Zone","Kid Zone","Settings"};//To compare with the retrieved drawerTitle
			String pageTitle[]=new String[7];;
			String drawerTitle[]=new String[7];
			//Opening the Navigation drawer if not opened by default
			for(int i=0;i<drawerXpath.length;i++)
			{
			
				while(!(client.isElementFound("NATIVE", ObjectCollection.get("Home_PageList").toString(),0)))
				{
	                ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(),client);
	                bFlag=true;
			    }
				EF.Update_Step_Report("Navigation Icon should be present in all the Screen","Navigation Icon present||Navigation Icon not present",bFlag);
				//looping to check whether the Navigation drawer contains the expected page titles
				drawerTitle[i]=client.elementGetText("NATIVE",ObjectCollection.get(drawerXpath[i]).toString() , 0);
				ST.NativeClick(ObjectCollection.get(drawerXpath[i]).toString(),client);
				 //Handling Pop Over appears while navigating to different screens
				if(ST.NativeElementFound(ObjectCollection.get("Home_Page_PopOver").toString(), client))
				 {
					 ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					 if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
					 {
						 ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(),client);
					 }
				 }
				 else if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
				 {
					 ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(),client);
					 if(ST.NativeElementFound(ObjectCollection.get("Home_Page_PopOver").toString(), client))
					 {
						 ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					 }
				 }
				 if(ST.NativeElementFound(ObjectCollection.get("Home_TitleBar").toString(), client))
				 { 
					 pageTitle[i]=client.elementGetText("NATIVE",ObjectCollection.get("Home_TitleBar").toString() , 0);
				 }
				 else
				 {
					 pageTitle[i]=client.elementGetText("NATIVE",ObjectCollection.get("Home_SelectedRowTitleBar").toString() , 0);
				 }
				 EF.Update_Step_Report("Page Title should  be available in Navigation Drawer", "Page Title is available in Navigation Drawer||Page Title is not available in Navigation Drawer",pageTitle[i].contains(drawerTitle[i]));	
			}
			EF.Update_TC_Report();
			for(int i=0;i<drawerXpath.length;i++)
			{
				EF.Update_Step_Report("On Clicking any tab in Navigation drawer the User Should be navigated to appropriate page","User Navigated to Appropriate page||Navigation to appreopriate page is unsuccessfull",
						pageTitle[i].contains(drawerTitle[i]));
			}
				ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(),client);
		}
		catch(Exception e)
		{
			System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
		}
	}

	public void Fn_NavigationDrawer_OutOfHomeIcon()
	{
		//This case should be executed with Out of home login credentials
		try
		{
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			Boolean bFlag=false;
			String str0="Out-of-Home";
			String str1;
			//Opening the Navigation drawer if not opened by default
			if(!(client.isElementFound("NATIVE", ObjectCollection.get("Home_PageList").toString(),0)))
			{
				ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(),client);
		    }
			str1=client.elementGetText("NATIVE",ObjectCollection.get("Home_OutofHomeIcon_Text").toString() , 0);
			if(ST.NativeElementFound(ObjectCollection.get("Home_OutofHomeIcon").toString(), client) && str1.contains(str0))
			{
				bFlag=true;
			}
			EF.Update_Step_Report("Out of Home network icon should  be available in Navigation Drawer", "Out of Home network icon is available in Navigation Drawer||Out of Home network icon is not available in Navigation Drawer",bFlag);
			ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(),client);
		}	
		catch(Exception e)
		{
			System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
		}
	}
		
	public boolean Fn_Com_Navigate(String strPage)//OR Name
    {
		this.Rest2homescreen();
		boolean Temp = false;
        String strTitle = ST.NativeGetElementText(ObjectCollection.get("Home_TitleBar").toString(), 0, client);
        String strPage1 = strPage.split("_")[1];
        if(strPage1.equalsIgnoreCase("MyLibrary"))
            strPage1 = strPage1.replace("MyLibrary", "Library");
        if(strPage1.equalsIgnoreCase("OnDemand"))
                strPage1 = strPage1.replace("OnDemand", "Demand");
        if(strPage1.equalsIgnoreCase("SportZone"))
                strPage1 = strPage1.replace("SportZone", "Sport");
        if(strPage1.equalsIgnoreCase("LiveTV"))
                strPage1 = strPage1.replace("LiveTV", "Live");
        if(strPage1.equalsIgnoreCase("KidZone"))
                strPage1 = strPage1.replace("KidZone", "KID");

        if(strTitle.contains(strPage1))     
        {
              if(Current_OS.equalsIgnoreCase("IOS"))
              {
            	  	if(client.isElementFound("NATIVE", ObjectCollection.get("Home_PageList").toString(),0))
                    ST.NativeClick(ObjectCollection.get("Home_PageList").toString(),client);
              }
              else if(Current_OS.equalsIgnoreCase("Android"))
              {
            	  	if(client.isElementFound("NATIVE", ObjectCollection.get("Home_PageList").toString(),0))
                    ST.NativeClick(ObjectCollection.get("Home_icon").toString(),client);
              }
              Temp = true;
        }
        else
        {
              if(!(client.isElementFound("NATIVE", ObjectCollection.get("Home_PageList").toString(),0)))
              {
                    System.out.println("Page :"+strPage);
                    System.out.println("Path :"+ObjectCollection.get(strPage).toString());
                    ST.NativeClick(ObjectCollection.get("Home_icon").toString(),client);
                    if(ST.NativeElementFound(ObjectCollection.get("Home_PageList").toString(), client))
                    {
                      Temp = ST.NativeClick(ObjectCollection.get(strPage).toString(),client);
                    }
              }
              else
              {
                    Temp = ST.NativeClick(ObjectCollection.get(strPage).toString(),client);
              }
              if(ST.NativeElementFound(ObjectCollection.get("Home_Page_PopOver").toString(), client))
	          {
	                ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
	          }
        }
        EnjoyingAppHandler();
        return Temp;
    }
	
	public void Fn_Kidzone_Lock()
	{	
		//Navigating to Kid Zone
		EF.Update_Step_Report("User should be navigated to Kid Zone","User navigated to Kid zone||User not navigated to kid zone",this.Fn_Com_Navigate("Home_KidZone"));
		//Clicking on Kid Zone Lock button
		ST.NativeClick(ObjectCollection.get("KidZone_LockButton").toString(), client);
		//Updating the result of Auto_KidZone_KidZoneLockPopOver_001()
		EF.Update_Step_Report("Kid zone lock pop over should display", "KidZone pop over appeared||KidZone pop over is not available",ST.NativeElementFound(ObjectCollection.get("KidZone_LockPopOver").toString(), client));
		ST.NativeClick(ObjectCollection.get("KidZone_btn_PopOverCancel").toString(), client);
		EF.Update_Step_Report("User should be navigated back to kidzone page", "User navigated successfully to kidzone||User navigation to kidzone is unsuccessful",ST.NativeElementFound(ObjectCollection.get("KidZone_Banner").toString(), client));
		EF.Update_TC_Report();
		//The Following code verifies the additional functionalities in Auto_KidZone_KidZoneLockPopOver_003
		
		//Updates the first Step of  Auto_KidZone_KidZoneLockPopOver_003()
		ST.NativeClick(ObjectCollection.get("KidZone_LockButton").toString(), client);
		EF.Update_Step_Report("Kid zone lock pop over should display", "KidZone pop over appeared||KidZone pop over is not available",ST.NativeElementFound(ObjectCollection.get("KidZone_LockPopOver").toString(), client));
		ST.NativeClick(ObjectCollection.get("KidZone_Btn_PopOverLockBtn").toString(), client);
		//Verifying whether all the pages are inaccessible and the lock pop over appears and prompts for HOH password 
		ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(), client);
		EF.Update_Step_Report("All pages should be inaccessible", "Lock PopOver appeared and pages are inaccessible||Lock pop over not appeared,Pages are accessible",ST.NativeElementFound(ObjectCollection.get("KidZone_EditBox_LockPaswd").toString(), client));
		//Updating the result of the Test case Auto_KidZone_KidZoneLockPopOver_003()
		EF.Update_TC_Report();
		client.closeKeyboard();
		ST.NativeClick(ObjectCollection.get("KidZone_btn_PopOverCancel").toString(), client);
		ST.NativeClick(ObjectCollection.get("KidZone_LockButton").toString(), client);
		EF.Update_Step_Report("Kid zone lock pop over should display with text field to enter HOH password", "KidZone pop over is available with HOH password field||KidZone pop over is not available with HOH password field",ST.NativeElementFound(ObjectCollection.get("KidZone_Btn_PopOverUnLockBtn").toString(), client));
		
		EF.Update_TC_Report();
		//client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", "//*[@class='_UIRefreshControlModernReplicatorView' and @hidden='false' and @onScreen='true' and not(contains(@parentHidden, 'true'))]", 0, 1000, 5, false)
		if(client.swipeWhileNotFound("Down",0,2000, "NATIVE",ObjectCollection.get("KidZone_EditBox_LockPaswd").toString() , 0,1000, 2, true))
		{	
			client.sendText("Ctva1000");
		}
		//client.elementSendText("NATIVE", ObjectCollection.get("KidZone_EditBox_LockPaswd").toString(), 0, TestData.get("Password").toString());
		client.closeKeyboard();
		ST.NativeClick(ObjectCollection.get("KidZone_Btn_PopOverUnLockBtn").toString(), client);
		if(ST.NativeElementFound(ObjectCollection.get("Home_btn_PopOver").toString(), client))
		{
			ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
			EF.Update_Step_Report("User should enter valid password","User not entered valid password||User not entered valid password",false);
		}
		else
		{
			client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 1000);
//			EF.Update_Step_Report("All pages should be Accessible", "Lock PopOver Disappeared and pages are accessible||Lock pop over not disappeared Pages are inaccessible",!(ST.NativeElementFound(ObjectCollection.get("KidZone_EditBox_LockPaswd").toString(), client)));
			EF.Update_Step_Report("All pages should be Accessible", "Lock PopOver Disappeared and pages are accessible||Lock pop over not disappeared Pages are inaccessible", true);
		}
	}

	
	
	

		
	

	
	
	public void Fn_Add_Favorites()
	{
		//Navigating to Kid zone
		Fn_Com_Navigate("Home_MyLibrary");
		//Waiting for the My Library panel to load
		client.sleep(10000);
		//Checking whether the user is navigated to Kid zone and updating step result
		EF.Update_Step_Report("User should be navigated to My Library page", "User navigated successfully to My Library||User navigation to My Library is unsuccessful",ST.NativeElementFound(ObjectCollection.get("Home_TitleBar_MyLibrary").toString(), client));
		//Navigating to favorites Panel
		EF.Update_Step_Report("User should be navigated to favorites panel in My Library", "User navigated to favorites panel||Navigation to favorites panel unsuccessfull ",
				client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_FavoritePanel").toString(), 0, 1000, 5, true));
		//Clicking on edit favorites icon
		ST.NativeClick(ObjectCollection.get("Home_btn_FavoriteEdit").toString(), client);
		//Updating step result for for the presence favorites pop over
		EF.Update_Step_Report("Favorites pop over should be appeared", "Favorites pop over appeared||Favorites pop over not exists",ST.NativeElementFound(ObjectCollection.get("Home_Favorite_PopOver").toString(), client));
	}
	
	public void Fn_Home_Watchlist_Favorite_PanelContents()
	{
		try
		{
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			//***************Auto_Home_HomeScreen_001*****************//
			//Cold start check//
			EF.Update_Step_Report("User should be navigated to My Library screen","User navigated to My Library ||User not navigated to My Library",this.Fn_Com_Navigate("Home_MyLibrary"));
			EF.Update_Step_Report("WatchList should be empty", "WatchList is empty||WatchList is not empty",
					(client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist_Helpmsg").toString(), 0, 1000, 5,false)));
			EF.Update_TC_Report();
			
			//**************Auto_Home_HomeScreen_002******************//
			client.swipeWhileNotFound("Up", 100, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
			EF.Update_Step_Report("User should be navigated to My Library page", "User navigated successfully to My Library||User navigation to My Library is unsuccessful",ST.NativeElementFound(ObjectCollection.get("Home_TitleBar_MyLibrary").toString(), client));
			EF.Update_Step_Report("User should be navigated to favorites panel in My Library", "User navigated to favorites panel||Navigation to favorites panel unsuccessfull ",
					client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Favorites_EditButton").toString(), 0, 1000, 5, false));
			//Checking whether favorites panel is empty or not and updating the step result
			EF.Update_Step_Report("My Favorites panel should display with no listed assets", "Favarite panel is empty||Favorites panel is not empty", 
					!(ST.NativeElementFound(ObjectCollection.get("Home_Favorite_AssetContainer").toString(), client)));
			client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
		}
		catch(Exception e)
		{
			System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
		}
	}
	public void Fn_Customize_FavoriteEdit()
	{
		try
		{	
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				Boolean bFlag;
				EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
					this.Fn_Com_Navigate("Home_MyLibrary"));
				client.syncElements(1000, 1000);
				client.swipeWhileNotFound("Down", 50, 2000, "NATIVE", ObjectCollection.get("Customize_Button").toString(), 0, 1000, 5, true);
				EF.Update_Step_Report("Should display Customize modal", "Customize modal is displayed||Customize modal isnot displayed",
						ST.NativeElementFound(ObjectCollection.get("Home_PopOver_Done").toString(), client));
				bFlag=ST.NativeElementFound(ObjectCollection.get("CustomizePanel_FavoriteEdit").toString(), client);
				EF.Update_Step_Report("Favorite edit button should be available","Favorite edit button available||Favorite edit button is not avaialble",bFlag);
				EF.Update_TC_Report();
				if(bFlag)
				{
					ST.NativeClick(ObjectCollection.get("CustomizePanel_FavoriteEdit").toString(), client);
					EF.Update_Step_Report("Favorite Popover should appear", "Favorite pop over appeared||Favorite pop over not appeared",
							ST.NativeElementFound(ObjectCollection.get("CustomizePanel_FavoriteEdit").toString(), client));
				}
				else
				{
					EF.Update_Step_Report("Favorite edit button should be available","Favorite edit button is available||Favorite edit btton is not avaialble",false);
				}
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
			}	
			else
			{
				EF.Update_Step_Report("IOS Specific case","Android specific||IOS Specific",false);
				EF.Update_TC_Report();
				EF.Update_Step_Report("IOS Specific case","Android specific||IOS Specific",false);
				EF.Update_TC_Report();
			}
		}
		catch(Exception e)
		{
			System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
		}
	}
	public void Fn_Home_BrowseAll_GridView()
	{
		try
		{
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			//***************************************Auto_Home_HomeScreen_005***********************//
			String panelTitle;
			String actionBarTitle;
			String assetTitle1;
			String assetTitle2;
			int count;
			
			Fn_Com_Navigate("Home_MyLibrary");
			//Locating panel to click browse all 
			client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Home_Panel_OnDemand").toString(), 0, 1000, 5, false);
			//Retrieving panel title to compare with action bar title
			panelTitle=client.elementGetText("NATIVE",ObjectCollection.get("Home_TitleBar").toString(), 0);
			client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", ObjectCollection.get("BrowseAll_Btn_OnDemand").toString(), 0, 1000, 5, true);
			actionBarTitle=client.elementGetText("NATIVE",ObjectCollection.get("Home_TitleBar").toString(), 0);
			EF.Update_Step_Report("Fullscreen Browse All page should load with the pertinent data to the panel category the Browse All button appeared in", 
					"Browse loaded pertinent data|| Broswe all does not load pertinent data", 
					!(panelTitle.contains(actionBarTitle)));
			count=client.getElementCount("NATIVE", ObjectCollection.get("Home_BrowseAll_Asset_Container").toString());
			if(count>12)
			{
				assetTitle1 = client.elementGetText("NATIVE", ObjectCollection.get("Home_BrowseAll_Asset_Text").toString(), 0);
			    client.swipe("Down", 100, 500);
			    client.sleep(1000);
			    assetTitle2 = client.elementGetText("NATIVE", ObjectCollection.get("Home_BrowseAll_Asset_Text").toString(), 0);
			    EF.Update_Step_Report("Content should scroll smoothly up and down if there is enough content to require scrolling", "Down Scroll successfull|| Down scroll unsuccessfull",
			    		!(assetTitle1.contains(assetTitle2)));
			    client.swipe("Up", 100, 500);
			    client.sleep(1000);
			    assetTitle2 = client.elementGetText("NATIVE", ObjectCollection.get("Home_BrowseAll_Asset_Text").toString(), 0);
			    EF.Update_Step_Report("Content should scroll smoothly up and down if there is enough content to require scrolling", "UP Scroll successfull|| UP scroll unsuccessfull",
			    		assetTitle1.contains(assetTitle2));
			}
			else
			{
				EF.Update_Step_Report("Content should scroll smoothly up and down if there is enough content to require scrolling", "Not enough Content to Scroll|| Not enough content to scroll",
			    		true);
			}
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		}
		catch(Exception e)
		{
			System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
		}
	}
	
	public void Fn_Home_FeatureBanner_Swipe()
	{
		try
		{
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			//*****************************Auto_Home_HomeScreen_003 *********************//
			String asset1;
			String asset2;
			EF.Update_Step_Report("User should be navigated to My Library screen","User navigated to My Library||User not navigated to My Library",this.Fn_Com_Navigate("Home_MyLibrary"));
			client.sleep(3000);
			client.waitForElement("NATIVE",ObjectCollection.get("Home_MediaBanner").toString(), 0, 300000);
			ST.NativeClick(ObjectCollection.get("Home_MediaBanner").toString(), client);
			client.sleep(3000);
			//Checking whether Asset details screen appears when user clicks on an asset in featured banner
			EF.Update_Step_Report("On Clicking the Media banner the User should be directed to Fullscreen asset detail screen",
					"User directed to fullscreen asset details screen||User is not directed to full screen asset detail screen",
					ST.NativeElementFound(ObjectCollection.get("Home_Title_AssetDetailScreen").toString(), client)||
					ST.NativeElementFound(ObjectCollection.get("Home_Label_ActionBar").toString(), client));
			EF.Update_TC_Report();
			
			//*****************************Auto_Home_HomeScreen_004*********************//
			if(Current_OS.equalsIgnoreCase("Android"))
			{
				//Retrieving the asset name before swiping
				asset1=client.elementGetText("NATIVE",ObjectCollection.get("Home_Title_AssetDetailScreen").toString(), 0);
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				//Swiping to right in LiveTV panel
				client.waitForElement("NATIVE",ObjectCollection.get("Home_MediaBanner_Content").toString(), 0, 30000);
				client.elementSwipe("NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, "Right", 100, 2000);
				ST.NativeClick(ObjectCollection.get("Home_MediaBanner").toString(), client);
				client.sleep(3000);
				//Retrieving the asset name after swiping
				asset2=client.elementGetText("NATIVE",ObjectCollection.get("Home_Title_AssetDetailScreen").toString(), 0);
				//Updating the step result for scroller movement and verifying the cover art content 
				EF.Update_Step_Report("Scroller should be moved right","Scroller mover right||Scroller movement failed" ,
						!(asset1.contains(asset2)));
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				//Swiping left and updating step report
				ST.NativeClick(ObjectCollection.get("Home_MediaBanner").toString(), client);
				client.sleep(3000);
				//Retrieving the first asset name before swiping
				asset1=client.elementGetText("NATIVE",ObjectCollection.get("Home_Title_AssetDetailScreen").toString(), 0);
				ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(), client);
				client.elementSwipe("NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, "Right", 100, 2000);
				ST.NativeClick(ObjectCollection.get("Home_MediaBanner").toString(), client);
				client.sleep(3000);
				asset2=client.elementGetText("NATIVE",ObjectCollection.get("Home_Title_AssetDetailScreen").toString(), 0);
				EF.Update_Step_Report("Scroller should be moved Left","Scroller mover Left||Scroller movement failed" ,
						!(asset1.contains(asset2)));
			}
			else if(Current_OS.equalsIgnoreCase("IOS"))
			{
				EF.Update_Step_Report("User should be navigated to My Library screen","Navigation successful||Navigation Unsucessful",true);
				client.swipe("Left",300,2000);
				client.sleep(1000);
				EF.Update_Step_Report("Scroller should be moved Left","Scroller mover Left||Scroller movement failed" ,true);
				client.swipe("Right",300,2000);
				client.sleep(1000);
				EF.Update_Step_Report("Scroller should be moved right","Scroller mover right||Scroller movement failed" ,true);
				
			}
		}
		catch(Exception e)
		{
			System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
		}
	}
	
	public void Fn_Kidzone_OnDemand_AssetContainer()
	{
		try
		{
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			//****************************Auto_Home_HomeScreen_006************************//
			EF.Update_Step_Report("User should be navigated to KidZone","User navigated to kidzone||User not navigated to KidZone",this.Fn_Com_Navigate("Home_KidZone"));
			client.sleep(10000);
			EF.Update_Step_Report("Must be able to view Featured banner showing different assets", 
					"Featured banner is available||Featured banner not available", 
					client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("KidZone_OnDemand_AssetContainer").toString(), 0, 1000, 5, false));
		}
		catch(Exception e)
		{
			System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
		}
	}
	
		
	public void Fn_KidZone_HelpIcon()
    {
          
          EF.Update_Step_Report("User must be navigated to Kid zone screenn","User Navigated to Kid zone Screen||User not navigated to Kidzone Screen",
              this.Fn_Com_Navigate("Home_KidZone"));
          if(Current_OS.equalsIgnoreCase("Android"))
          {
                EF.Update_Step_Report("Help icon should be greyed out","Help Icon is greyed out||Help icon is not greyed out", 
                            !(ST.NativeElementFound(ObjectCollection.get("Home_HelpIcon").toString(), client)));
          }
          else if(Current_OS.equalsIgnoreCase("IOS"))
          {
                EF.Update_Step_Report("Help icon should be greyed out","Help Icon is greyed out||Help icon is not greyed out", 
                            ST.NativeElementFound(ObjectCollection.get("Kidzone_Disabled_HelpIcon").toString(), client));
          }
    }
	public void Fn_Kidzone_PanelCheck()
	{	
		//Navigating to Kid zone
		Fn_Com_Navigate("Home_KidZone");
		//Checking whether the user is navigated to Kid zone and updatin step result
		EF.Update_Step_Report("User should be navigated to kidzone page", "User navigated successfully to kidzone||User navigation to kidzone is unsuccessful",ST.NativeElementFound(ObjectCollection.get("KidZone_Banner").toString(), client));
		//Checking and updating the  presence of Live TV panel
		EF.Update_Step_Report("Live TV panel should be present in Kid zone", "Live TV panel available in Kid Zone||Live TV panel not available in Kid zone",
				client.swipeWhileNotFound("Down", 100, 2000, "NATIVE",ObjectCollection.get("KidZone_Lbl_LiveTV").toString(), 0, 1000, 5, true));
		//Checking and updating the presence of Toddler&Kindergarten panel
		EF.Update_Step_Report("Toddler&Kindergarten panel should be prensent in Kid zone", "Toddler&Kindergarten panel is available in Kid Zone||Toddler&Kindergarten panel is not not available in Kid zone",
				client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("KidZone_Lbl_Toddler&Kindergarten").toString(), 0, 1000, 5, true));
		//Checking and updating the presence of ElementarySchool panel
		EF.Update_Step_Report("ElementarySchool panel should be prensent in Kid zone", "ElementarySchool panel is available in Kid Zone||ElementarySchool panel is not not available in Kid zone",
				client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("KidZone_Lbl_ElementarySchool").toString(), 0, 1000, 5, true));
		client.swipeWhileNotFound("Up", 100, 2000, "NATIVE", ObjectCollection.get("KidZone_Banner").toString(), 0, 1000, 5, false);
		
	}
	public void Fn_Kidzone_LiveTV_Swipe()
	{	
		//Checking whether the user is navigated to Kid zone and updating step result
		EF.Update_Step_Report("User should be navigated to kidzone page", "User navigated successfully to kidzone||User navigation to kidzone is unsuccessful",ST.NativeElementFound(ObjectCollection.get("KidZone_Banner").toString(), client));
		//Waiting for the LiveTV panel to load
		client.sleep(1000);
		//Retrieving the first asset name before swiping 
		String str0=client.elementGetText("NATIVE",ObjectCollection.get("KidZone_LiveTV_AssetCoverArt").toString(), 0);
		//Swiping to right in LiveTV panel
		client.elementSwipe("NATIVE", ObjectCollection.get("KidZone_LiveTV_MediaScroller").toString(), 0, "Right", 100, 2000);
		//Retrieving the first asset name after swiping
		String str1=client.elementGetText("NATIVE",ObjectCollection.get("KidZone_LiveTV_AssetCoverArt").toString(), 0);
		//Updating the step result for scroller movement and verifying the cover art content 
		if(str1.equalsIgnoreCase(str0))
		{
			EF.Update_Step_Report("User should be able to Left and right scroll in live tv panel","Scroll sucessfull||Scroll unscucessful",true);
		}
		else
		{	
			EF.Update_Step_Report("Scroller should be moved right and cover art should be loaded","Scroller mover right and cover art gets loaded||Scroller movement failed and cover art failed to load" ,
					!(str0.contains(str1)));
			//Swiping to left in LiveTV panel
			client.elementSwipe("NATIVE",ObjectCollection.get("KidZone_LiveTV_MediaScroller").toString(), 0, "Left", 100, 2000);
			//Retrieving the first asset name after swiping
			str1=client.elementGetText("NATIVE",ObjectCollection.get("KidZone_LiveTV_AssetCoverArt").toString(), 0);
			//Updating the step result for scroller movement and verifying the cover art content 
			EF.Update_Step_Report("Scroller should be moved right and cover art should be loaded","Scroller mover right and cover art gets loaded||Scroller movement failed and cover art failed to load" ,
					str0.contains(str1));
		}
		
		
	}
	public void Fn_KidZone_Asset_PopOver()
	{	
		String assetTitle;
		String popOverTitle;
		EF.Update_Step_Report("User should be navigated to kid zone", "User navigated to kidzone||Navigation to Kidzone Unsuccessful",ST.NativeElementFound(ObjectCollection.get("KidZone_Banner").toString(), client));
		client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("KidZone_OnDemand_AssetContainer").toString(), 0, 1000, 5, true);
		assetTitle=client.elementGetText("NATIVE",ObjectCollection.get("KidZone_OnDemand_AssetContainer").toString(), 0);
		ST.NativeClick(ObjectCollection.get("KidZone_OnDemand_AssetContainer").toString(), client);
		popOverTitle=client.elementGetText("NATIVE",ObjectCollection.get("KidZone_Asset_CoverArtTitle").toString(), 0);
		EF.Update_Step_Report("The Pop-over Asset Detail of the selected asset should now display","Pop-Over Asset Detail of the selected asset is displayed||Pop-Over is not displayed",
				assetTitle.contains(popOverTitle));
		ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
		client.swipeWhileNotFound("Up", 100, 2000, "NATIVE", ObjectCollection.get("KidZone_Banner").toString(), 0, 1000, 5, false);
		
	
	}

	public void Fn_KidZone_ChannelStream()
	{
		EF.Update_Step_Report("User must be navigated to Kid zone screenn","User Navigated to Kid zone Screen||User not navigated to Kidzone Screen",ST.NativeElementFound(ObjectCollection.get("KidZone_Banner").toString(), client));
		client.sleep(10000);
		if(client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", ObjectCollection.get("KidZone_LiveTV_AssetContainer").toString(), 0, 1000, 5, true))
		{
			client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Loading").toString(), 0, 100000);
			EF.Update_Step_Report("Must direct the user to live streaming of the selected asset","User Direted to Live TV streaming of the selected asset||User is not directed to Live TV Streaming",
					client.swipeWhileNotFound("Left", 100, 2000, "NATIVE", ObjectCollection.get("KidZone_VideoPlayer_Controls_Container").toString(), 0, 1000, 3, false));
			EF.Update_TC_Report();
			EF.Update_Step_Report("Must direct the user to live streaming of the selected asset","User Direted to Live TV streaming of the selected asset||User is not directed to Live TV Streaming",
					client.swipeWhileNotFound("Left", 100, 2000, "NATIVE", ObjectCollection.get("KidZone_VideoPlayer_Controls_Container").toString(), 0, 1000, 3, false));
			if(client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("KidZone_VideoPlayer_Controls_Container").toString(), 0, 1000, 3, false))
			{
				EF.Update_Step_Report("MiniGuide shold not be displayed","Mini guide is not displayed||Mini Guide is displayed",
						!(ST.NativeElementFound(ObjectCollection.get("KiZone_VideoPlayer_MiniGuide").toString(),client)));
				EF.Update_Step_Report("FlyOver Asset should not be displayed","Fly over is not displayed||Fly Over is not displayed", 
						!(ST.NativeElementFound(ObjectCollection.get("KidZone_VideoPlayer_FlyOver").toString(),client)));
				EF.Update_Step_Report("HUD controls alone should be available","Previous channel button which is not a HUD is not available||Previous channel button which is not a HUD is not available", 
						!(ST.NativeElementFound(ObjectCollection.get("KidZone_VideoPlayer_PreviousChannel").toString(),client)));	
				EF.Update_Step_Report("HUD controls alone should be available","Next channel button which is not a HUD is not available||Next channel button which is not a HUD is not available", 
						!(ST.NativeElementFound(ObjectCollection.get("KidZone_VideoPlayer_PreviousChannel").toString(),client)));
				EF.Update_Step_Report("HUD Controls should be available", "HUD Control StreamController is available||HUD StreamController is not available",
						ST.NativeElementFound(ObjectCollection.get("KidZone_Btn_VideoPlayer_StreamController").toString(),client));
				EF.Update_Step_Report("HUD Controls should be available", "HUD SentToTV control is available||HUD SendToTV is not available",
						ST.NativeElementFound(ObjectCollection.get("KidZone_Btn_VideoPlayer_SendToTV").toString(),client));
				EF.Update_Step_Report("HUD Controls should be available", "HUD Closed Caption control is available||HUD Closed Caption is not available",
						ST.NativeElementFound(ObjectCollection.get("KidZone_Btn_VideoPlayer_ClosedCaption").toString(),client));
				ST.NativeClick(ObjectCollection.get("KidZone_VideoPlayer_Controls_Container").toString(), client);
				ST.NativeClick(ObjectCollection.get("LiveTV_Donebtn").toString(), client);
			}
			else
			{	
				EF.Update_Step_Report("HUD Controls should be available", "HUD controls are availble||HUD controls are not available", false);
				System.out.println("Video Controls are not available");
				ST.NativeClick(ObjectCollection.get("KidZone_VideoPlayer_Controls_Container").toString(), client);
				ST.NativeClick(ObjectCollection.get("LiveTV_Donebtn").toString(), client);
			}
		
		}
		else
		{
			EF.Update_Step_Report("Live TV asset should be available", "Live TV asset is available||Live TV asset is not availble", false);
		}
	}
	public void Fn_Kidzone_ParentalPopOver()
	{
		
         if(!(client.isElementFound("NATIVE", ObjectCollection.get("Home_PageList").toString(),0)))
         {
               ST.NativeClick(ObjectCollection.get("Home_icon").toString(),client);
               if (ST.NativeElementFound(ObjectCollection.get("Home_PageList").toString(), client))
               {
            	   ST.NativeClick(ObjectCollection.get("Home_KidZone").toString(),client);
               }
         }
         else
         {
               	ST.NativeClick(ObjectCollection.get("Home_KidZone").toString(),client);
         }
         ST.NativeElementFound(ObjectCollection.get("Home_Page_PopOver").toString(), client);
         EF.Update_Step_Report("Parental control pop over should display","Parental control pop Over displayed||Parental control Pop Over is not displayed",true);
         ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
         ST.NativeElementFound(ObjectCollection.get("Home_TitleBar").toString(), client);
         EF.Update_Step_Report("User should be taken to KidZone","User is taken to Kid Zone page||User is not taken to KidZone page", true);

	}

	
	public void Fn_Home_AssetPopOver()
	{
		
		String assetTitle;
		String popOverTitle;
		Fn_Com_Navigate("Home_MyLibrary");
		client.swipeWhileNotFound("Up", 100, 2000, "NATIVE", ObjectCollection.get("KidZone_OnDemand_AssetGetText").toString(), 0, 1000, 5, false);
		assetTitle=client.elementGetText("NATIVE",ObjectCollection.get("KidZone_OnDemand_AssetGetText").toString(), 0);
		ST.NativeClick(ObjectCollection.get("KidZone_OnDemand_AssetContainer").toString(), client);
		popOverTitle=client.elementGetText("NATIVE",ObjectCollection.get("KidZone_Asset_CoverArtTitle").toString(), 0);
		EF.Update_Step_Report("The Pop-over Asset Detail of the selected asset should now display","Pop-Over Asset Detail of the selected asset is displayed||Pop-Over is not displayed",
				assetTitle.contains(popOverTitle));
		ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(),client);
	}
	

	public void Fn_Validate_HelpIcon()
	{
		//Help icon should be available in Library screen
		
		this.Fn_Com_Navigate("Home_MyLibrary");
		EF.Update_Step_Report("Check for Help icon in My Library", "Help icon is available||Help icon is not available",ST.NativeElementFound(ObjectCollection.get("Home_HelpIcon").toString(), client));
		//Help icon should be available in Guide screen
		this.Fn_Com_Navigate("Home_GuidePage");
		EF.Update_Step_Report("Check for Help icon in Guide Page", "Help icon is available||Help icon is not available",ST.NativeElementFound(ObjectCollection.get("Home_HelpIcon").toString(), client));
		//Help icon should be available in Live TV screen
		this.Fn_Com_Navigate("Home_LiveTV");
		EF.Update_Step_Report("Check for Help icon in Live TV Page", "Help icon is available||Help icon is not available",ST.NativeElementFound(ObjectCollection.get("Home_HelpIcon").toString(), client));
		//Help icon should be available in On Demand screen
		this.Fn_Com_Navigate("Home_OnDemand");
		EF.Update_Step_Report("Check for Help icon in On Demand Page", "Help icon is available||Help icon is not available",ST.NativeElementFound(ObjectCollection.get("Home_HelpIcon").toString(), client));
		//Help icon should not be available in Sports Zone screen
		this.Fn_Com_Navigate("Home_SportZone");
		EF.Update_Step_Report("Check Non availability of help icon in Sports Zone", "Help icon is not available||Help icon is available",!(ST.NativeElementFound(ObjectCollection.get("Home_HelpIcon").toString(), client)));
		//Help icon should not be available in Sports Zone screen
		this.Fn_Com_Navigate("Home_KidZone");
		EF.Update_Step_Report("Check Non availability of help icon in Kids Zone", "Help icon is not available||Help icon is available",!(ST.NativeElementFound(ObjectCollection.get("Home_HelpIcon").toString(), client)));
		//Help icon should be available in Settings screen
		this.Fn_Com_Navigate("Home_Setting");
		EF.Update_Step_Report("Check for Help icon in Settings Page", "Help icon is available||Help icon is not available",ST.NativeElementFound(ObjectCollection.get("Home_HelpIcon").toString(), client));
		
	}
	
	public void Fn_Validate_HelpPopOver()
	{
		String Temp_Var;
		Boolean Local_Result = true;
		if(Current_OS == "IOS")
		{
			EF.Update_Step_Report("Help overlay should be captured in all the page","Help overlay ison is displayed||Help overlay Icon is not displayed", false);
			EF.Update_TC_Report();
			EF.Update_Step_Report("Help overlay should be captured in all the page","Help overlay ison is displayed||Help overlay Icon is not displayed", false);
			EF.Update_TC_Report();
			EF.Update_Step_Report("Help overlay should be captured in all the page","Help overlay ison is displayed||Help overlay Icon is not displayed", false);
			EF.Update_TC_Report();
			EF.Update_Step_Report("Help overlay should be captured in all the page","Help overlay ison is displayed||Help overlay Icon is not displayed", false);
			EF.Update_TC_Report();
			EF.Update_Step_Report("Help overlay should be captured in all the page","Help overlay ison is displayed||Help overlay Icon is not displayed", false);
			EF.Update_TC_Report();
			EF.Update_Step_Report("Help overlay should be captured in all the page","Help overlay ison is displayed||Help overlay Icon is not displayed", false);
			EF.Update_TC_Report();
			EF.Update_Step_Report("Help overlay should be captured in all the page","Help overlay ison is displayed||Help overlay Icon is not displayed", false);
			EF.Update_TC_Report();
		}
		else
		{
			//Auto_HelpOverlay_002-----------------------------------------------------------------------------------------------------------------//
			EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
			EF.Update_Step_Report("Click on Help icon", "Clicked Sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("Home_HelpIcon").toString(), client));
			
			//Search help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),0, client);
			if (!(Temp_Var.contains("Search by actor, title, or keyword")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in Search help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			//Customize help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),1, client);
			if (!(Temp_Var.contains("Swipe down to customize your homepage")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in Customize help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			//Edit Favorites help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),2, client);
			if (!(Temp_Var.contains("Tap the edit button to add favorite channels")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in Edit Favorites help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			ST.NativeClick(ObjectCollection.get("Home_HelpIcon").toString(), client);
			
			//Auto_HelpOverlay_003-----------------------------------------------------------------------------------------------------------------//
			EF.Update_Step_Report("Navigate to Guide", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_GuidePage"));
			EF.Update_Step_Report("Click on Help icon", "Clicked Sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("Home_HelpIcon").toString(), client));
			
			//Filter help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),0, client);
			if (!(Temp_Var.contains("Apply one or more filters to refine your channel line-up")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in Filter help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			//Sort help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),3, client);
			if (!(Temp_Var.contains("Tap to sort your line-up by channel number or alphabetically")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in Sort help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			//Network cell help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),2, client);
			if (!(Temp_Var.contains("Tap logo to view what's playing on a specific channel")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in Network cell help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			//Program cell help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),1, client);
			if (!(Temp_Var.contains("Tap any title to view details on that show or movie")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in Program cell help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			ST.NativeClick(ObjectCollection.get("Home_HelpIcon").toString(), client);
			
			//Auto_HelpOverlay_004-----------------------------------------------------------------------------------------------------------------//
			EF.Update_Step_Report("Navigate to Live TV", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_LiveTV"));
			//Change to list view
			if(ST.NativeElementFound(ObjectCollection.get("LiveTV_Menu_ListView").toString(), client))
			{
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_ListView").toString(), client);
			}
			EF.Update_Step_Report("Click on Help icon", "Clicked Sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("Home_HelpIcon").toString(), client));
			
			//help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),1, client);
			if (!(Temp_Var.contains("View help tips on any screen in the app")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			//View help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),0, client);
			if (!(Temp_Var.contains("Switch between simple list view and grid with photos")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in View help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			//Stream help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),2, client);
			if (!(Temp_Var.contains("Tap Play button to start streaming live TV")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in Stream help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			ST.NativeClick(ObjectCollection.get("Home_HelpIcon").toString(), client);
			
			//Auto_HelpOverlay_005-----------------------------------------------------------------------------------------------------------------//
			EF.Update_Step_Report("Navigate to On Demand", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_OnDemand"));
			EF.Update_Step_Report("Click on Help icon", "Clicked Sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("Home_HelpIcon").toString(), client));
			
			//Search help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),1, client);
			if (!(Temp_Var.contains("Search by actor, title, or keyword")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in Search help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			//Cover Art help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),0, client);
			if (!(Temp_Var.contains("Tap any image to get more information and viewing options")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in Cover Art help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			//VOD Action bar help pop over
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_HelpPopover").toString(),2, client);
			if (!(Temp_Var.contains("Browse titles by Movies, TV, Networks, or Premiums")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in VOD Action bar help pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			ST.NativeClick(ObjectCollection.get("Home_HelpIcon").toString(), client);
			
			//Auto_HelpOverlay_006--is NA-----------------------------------------------------------------------------------------------------------//
			
			//Auto_HelpOverlay_007-----------------------------------------------------------------------------------------------------------------//
			this.Fn_Com_Navigate("Home_KidZone");
			EF.Update_Step_Report("Check Non availability of help icon in Kids Zone", "Help icon is not available||Help icon is available",!(ST.NativeElementFound(ObjectCollection.get("Home_HelpIcon").toString(), client)));
			EF.Update_TC_Report();
			
			//Auto_HelpOverlay_008-----------------------------------------------------------------------------------------------------------------//
			EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_Setting"));
			EF.Update_Step_Report("Click on Help icon", "Clicked Sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("Home_HelpIcon").toString(), client));
			//check message
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_Pop_Message").toString()+"//*[1]",0, client);
			if (!(Temp_Var.contains("Have questions on your account or bill? Please contact customer care at:")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text in setting message pop over", "Sucessfully Verified||Verification failed",Local_Result);
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_Pop_Message").toString()+"//*[2]",0, client);
			if (!(Temp_Var.contains("1–877–581–2420")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify customer care number setting message pop over", "Sucessfully Verified||Verification failed",Local_Result);
		}
	}
	
	
	public void Fn_Validate_SearchEdit()
	{
		//Click search icon search edit should be shown in Library screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_MyLibrary"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		EF.Update_Step_Report("Check for Search edit in Guide Page", "Search edit box is available||Search edit box is not available",ST.NativeElementFound(ObjectCollection.get("Home_Edt_Search").toString(), client));
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//Click search icon search edit should be shown in Guide screen
		EF.Update_Step_Report("Navigate to Guide screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_GuidePage"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		EF.Update_Step_Report("Check for Search edit in Guide Page", "Search edit box is available||Search edit box is not available",ST.NativeElementFound(ObjectCollection.get("Home_Edt_Search").toString(), client));
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			
		//Click search icon search edit should be shown in Live TV screen
		EF.Update_Step_Report("Navigate to Live TV screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_LiveTV"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		EF.Update_Step_Report("Check for Search edit in Guide Page", "Search edit box is available||Search edit box is not available",ST.NativeElementFound(ObjectCollection.get("Home_Edt_Search").toString(), client));
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//Click search icon search edit should be shown in On demand screen
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		EF.Update_Step_Report("Check for Search edit in Guide Page", "Search edit box is available||Search edit box is not available",ST.NativeElementFound(ObjectCollection.get("Home_Edt_Search").toString(), client));
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//Click search icon search edit should be shown in Sports Zone screen
		EF.Update_Step_Report("Navigate to Sports Zone screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_SportZone"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		EF.Update_Step_Report("Check for Search edit in Guide Page", "Search edit box is available||Search edit box is not available",ST.NativeElementFound(ObjectCollection.get("Home_Edt_Search").toString(), client));
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//Click search icon search edit should be shown in Guide screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		EF.Update_Step_Report("Check for Search edit in Guide Page", "Search edit box is available||Search edit box is not available",ST.NativeElementFound(ObjectCollection.get("Home_Edt_Search").toString(), client));
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
	}
	
	public void Fn_Validate_SearchEditText()
	{
		String Temp = TestData.get("SearchKeyword").toString();
		boolean local_result=false;
		//whether Able to type in text field in Library screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_MyLibrary"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		local_result = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client).contains(Temp);
		EF.Update_Step_Report("Verify whether Able to type in text field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to Guide screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_GuidePage"));
		if(ST.NativeElementFound(ObjectCollection.get("Guide_Pop_Error").toString(), client))
		{
			ST.NativeClick(ObjectCollection.get("Guide_Pop_ErrorSubmit").toString(), client);
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		local_result = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client).contains(Temp);
		EF.Update_Step_Report("Verify whether Able to type in text field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			
		//whether Able to type in text field in Live TV screen
		EF.Update_Step_Report("Navigate to Live TV screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_LiveTV"));
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Loading").toString(), client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		local_result = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client).contains(Temp);
		EF.Update_Step_Report("Verify whether Able to type in text field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in On demand screen
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		local_result = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client).contains(Temp);
		EF.Update_Step_Report("Verify whether Able to type in text field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in Sports Zone screen
		EF.Update_Step_Report("Navigate to Sports Zone screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_SportZone"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		local_result = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client).contains(Temp);
		EF.Update_Step_Report("Verify whether Able to type in text field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		local_result = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client).contains(Temp);
		EF.Update_Step_Report("Verify whether Able to type in text field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
	}
	
	public void Fn_Validate_SearchPopUp()
	{
		String Temp = TestData.get("SearchKeyword").toString();
		boolean local_result=false;
		//whether Able to type in text field in Library screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_MyLibrary"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to Guide screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_GuidePage"));
		if(ST.NativeElementFound(ObjectCollection.get("Guide_Pop_Error").toString(), client))
		{
			ST.NativeClick(ObjectCollection.get("Guide_Pop_ErrorSubmit").toString(), client);
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			
		//whether Able to type in text field in Live TV screen
		EF.Update_Step_Report("Navigate to Live TV screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_LiveTV"));
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Loading").toString(), client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in On demand screen
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in Sports Zone screen
		EF.Update_Step_Report("Navigate to Sports Zone screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_SportZone"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
	}
	
	public void Fn_Validate_SearchEnterKey()
	{
		String Temp = TestData.get("SearchKeyword").toString();
		boolean local_result=false;
		//whether Able to type in text field in Library screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_MyLibrary"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		client.sendText("{Enter}");
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated after pressing Return/Enter Key", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to Guide screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_GuidePage"));
		if(ST.NativeElementFound(ObjectCollection.get("Guide_Pop_Error").toString(), client))
		{
			ST.NativeClick(ObjectCollection.get("Guide_Pop_ErrorSubmit").toString(), client);
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		client.sendText("{Enter}");
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated after pressing Return/Enter Key", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			
		//whether Able to type in text field in Live TV screen
		EF.Update_Step_Report("Navigate to Live TV screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_LiveTV"));
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Loading").toString(), client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		client.sendText("{Enter}");
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated after pressing Return/Enter Key", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in On demand screen
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		client.sendText("{Enter}");
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated after pressing Return/Enter Key", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in Sports Zone screen
		EF.Update_Step_Report("Navigate to Sports Zone screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_SportZone"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		client.sendText("{Enter}");
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated after pressing Return/Enter Key", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		client.sendText("{Enter}");
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		local_result = ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		EF.Update_Step_Report("Verify whether Search result populated after pressing Return/Enter Key", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
	}
	
	public void Fn_Validate_SearchAssertClick()
	{
		String Temp = TestData.get("SearchKeyword").toString(),Test1,Test2;
		boolean local_result=false;
		//whether Able to type in text field in Library screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_MyLibrary"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_SearchResult").toString(), 0, client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		Test2=ST.NativeGetElementText(ObjectCollection.get("Home_Label_ActionBar").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Dropdown Asset Tap", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to Guide screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_GuidePage"));
		if(ST.NativeElementFound(ObjectCollection.get("Guide_Pop_Error").toString(), client))
		{
			ST.NativeClick(ObjectCollection.get("Guide_Pop_ErrorSubmit").toString(), client);
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_SearchResult").toString(), 0, client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		Test2=ST.NativeGetElementText(ObjectCollection.get("Home_Label_ActionBar").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Dropdown Asset Tap", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			
		//whether Able to type in text field in Live TV screen
		EF.Update_Step_Report("Navigate to Live TV screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_LiveTV"));
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Loading").toString(), client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_SearchResult").toString(), 0, client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		Test2=ST.NativeGetElementText(ObjectCollection.get("Home_Label_ActionBar").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Dropdown Asset Tap", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in On demand screen
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_SearchResult").toString(), 0, client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		Test2=ST.NativeGetElementText(ObjectCollection.get("Home_Label_ActionBar").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Dropdown Asset Tap", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		}
		
		//whether Able to type in text field in Sports Zone screen
		EF.Update_Step_Report("Navigate to Sports Zone screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_SportZone"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_SearchResult").toString(), 0, client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		Test2=ST.NativeGetElementText(ObjectCollection.get("Home_Label_ActionBar").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Dropdown Asset Tap", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in settings screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Pop_SearchResult").toString(), 0, client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		Test2=ST.NativeGetElementText(ObjectCollection.get("Home_Label_ActionBar").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Dropdown Asset Tap", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
	}
	public void Fn_Validate_SearchAssertTitle()
	{
		String Temp = TestData.get("SearchKeyword").toString();
		boolean local_result=false;
		
		//whether Able to type in text field in Library screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_MyLibrary"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to Guide screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_GuidePage"));
		if(ST.NativeElementFound(ObjectCollection.get("Guide_Pop_Error").toString(), client))
		{
			ST.NativeClick(ObjectCollection.get("Guide_Pop_ErrorSubmit").toString(), client);
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			
		//whether Able to type in text field in Live TV screen
		EF.Update_Step_Report("Navigate to Live TV screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_LiveTV"));
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Loading").toString(), client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in On demand screen
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		}
		
		//whether Able to type in text field in Sports Zone screen
		EF.Update_Step_Report("Navigate to Sports Zone screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_SportZone"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in settings screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchResult").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		}
	public void Fn_Validate_SearchAssertCoverArt()
	{
		String Temp = TestData.get("SearchKeyword").toString();
		boolean local_result=false;
		
		//whether Able to type in text field in Library screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_MyLibrary"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Search_Icon_SearchCoverArt").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to Guide screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_GuidePage"));
		if(ST.NativeElementFound(ObjectCollection.get("Guide_Pop_Error").toString(), client))
		{
			ST.NativeClick(ObjectCollection.get("Guide_Pop_ErrorSubmit").toString(), client);
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Search_Icon_SearchCoverArt").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			
		//whether Able to type in text field in Live TV screen
		EF.Update_Step_Report("Navigate to Live TV screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_LiveTV"));
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Loading").toString(), client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Search_Icon_SearchCoverArt").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in On demand screen
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Search_Icon_SearchCoverArt").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		}
		
		//whether Able to type in text field in Sports Zone screen
		EF.Update_Step_Report("Navigate to Sports Zone screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_SportZone"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Search_Icon_SearchCoverArt").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in settings screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		local_result= ST.NativeElementFound(ObjectCollection.get("Search_Icon_SearchCoverArt").toString(), client);
		EF.Update_Step_Report("Verify Search Dropdown Asset Title", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		}
	
	public void Fn_Validate_SearchEditClear()
	{
		String Temp = TestData.get("SearchKeyword").toString(),Test1;
		boolean local_result=false;
		//whether Able to type in text field in Library screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_MyLibrary"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), "", client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client);
		local_result=Test1.isEmpty();
		EF.Update_Step_Report("Verify x button clears Search Text Field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to Guide screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_GuidePage"));
		if(ST.NativeElementFound(ObjectCollection.get("Guide_Pop_Error").toString(), client))
		{
			ST.NativeClick(ObjectCollection.get("Guide_Pop_ErrorSubmit").toString(), client);
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), "", client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client);
		local_result=Test1.isEmpty();
		EF.Update_Step_Report("Verify x button clears Search Text Field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			
		//whether Able to type in text field in Live TV screen
		EF.Update_Step_Report("Navigate to Live TV screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_LiveTV"));
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Loading").toString(), client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), "", client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client);
		local_result=Test1.isEmpty();
		EF.Update_Step_Report("Verify x button clears Search Text Field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in On demand screen
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), "", client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client);
		local_result=Test1.isEmpty();
		EF.Update_Step_Report("Verify x button clears Search Text Field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		}
		
		//whether Able to type in text field in Sports Zone screen
		EF.Update_Step_Report("Navigate to Sports Zone screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_SportZone"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), "", client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client);
		local_result=Test1.isEmpty();
		EF.Update_Step_Report("Verify x button clears Search Text Field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in settings screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeElementFound(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), "", client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Edt_Search").toString(), 0, client);
		local_result=Test1.isEmpty();
		EF.Update_Step_Report("Verify x button clears Search Text Field", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
	}
	
	//Search Results
	public void Fn_Validate_SearchFullScreen()
	{
		String Temp = TestData.get("SearchKeyword").toString(),Test1;
		boolean local_result=false;
		//whether Able to type in text field in Library screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_MyLibrary"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains("Search Results");
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to Guide screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_GuidePage"));
		if(ST.NativeElementFound(ObjectCollection.get("Guide_Pop_Error").toString(), client))
		{
			ST.NativeClick(ObjectCollection.get("Guide_Pop_ErrorSubmit").toString(), client);
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains("Search Results");
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			
		//whether Able to type in text field in Live TV screen
		EF.Update_Step_Report("Navigate to Live TV screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_LiveTV"));
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Loading").toString(), client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains("Search Results");
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in On demand screen
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains("Search Results");
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		}
		
		//whether Able to type in text field in Sports Zone screen
		EF.Update_Step_Report("Navigate to Sports Zone screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_SportZone"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains("Search Results");
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in settings screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains("Search Results");
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
	}
	
	public void Fn_Validate_SearchFullScreenBack()
	{
		String Temp = TestData.get("SearchKeyword").toString(),Test1,Test2;
		boolean local_result=false;
		//whether Able to type in text field in Library screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_MyLibrary"));
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		client.sendText("{ESC}");
		Test2 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item back button click", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in Guide screen
		EF.Update_Step_Report("Navigate to Guide screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_GuidePage"));
		if(ST.NativeElementFound(ObjectCollection.get("Guide_Pop_Error").toString(), client))
		{
			ST.NativeClick(ObjectCollection.get("Guide_Pop_ErrorSubmit").toString(), client);
		}
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		client.sendText("{ESC}");
		Test2 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item back button click", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			
		//whether Able to type in text field in Live TV screen
		EF.Update_Step_Report("Navigate to Live TV screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_LiveTV"));
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Loading").toString(), client);
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		client.sendText("{ESC}");
		Test2 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item back button click", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		
		//whether Able to type in text field in On demand screen
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		client.sendText("{ESC}");
		Test2 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item back button click", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		}
		
		//whether Able to type in text field in Sports Zone screen
		EF.Update_Step_Report("Navigate to Sports Zone screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_SportZone"));
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		client.sendText("{ESC}");
		Test2 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item back button click", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
		
		//whether Able to type in text field in settings screen
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
		Test1 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		EF.Update_Step_Report("Click on Search icon", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Home_Icon_Search").toString(), client));
		ST.NativeElementSendTxt(ObjectCollection.get("Home_Edt_Search").toString(), Temp, client);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Pop_SearchLoading").toString(), client);
		ST.NativeClick(ObjectCollection.get("Home_Pop_SearchExpand").toString(), client);
		client.sendText("{ESC}");
		Test2 = ST.NativeGetElementText(ObjectCollection.get("Home_Page_ActionTitle").toString(), 0, client);
		local_result = Test1.contains(Test2);
		EF.Update_Step_Report("Verify Search Fullscreen Search List Item back button click", "Verified sucessfully||Verification  failed",local_result);
		local_result=false;
		ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
	}
	
		public boolean Fn_Negative_val_CA()
		{
			try
			{
				boolean bFlag=false;
				ST.NativeClick(ObjectCollection.get("Login_btn_CreateAccount").toString(), client);
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_CA_edt_AccountID").toString(), client);
				EF.Update_Step_Report("Charter Create Account pop-up should be oppened", "Charter Create Account pop-up is oppened||Charter Create Account pop-up is not oppened", bFlag);
				this.Fn_Com_CA_Fieldfiller();
				if(ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp").toString(), client))
				{
					String strACStatus = client.elementGetText("NATIVE", ObjectCollection.get("Login_ErrorPopUp").toString(), 0);
					System.out.println("Account Creation is "+strACStatus);
					if(strACStatus.contains("exist"))
					{
						//Auto_Login_CreateAccount_0019 validation
						System.out.println("Account Already Exist");
						bFlag=true;
						ST.NativeClick(ObjectCollection.get("Login_ErrorPopUp_Okbtn").toString(), client);
						EF.Update_Step_Report("Already registered account should through a Account Exist error message", "Account already exist error message is captured||Account already exist error message is not captured", bFlag);
						return bFlag;
					}
					else if(strACStatus.contains("Error"))
					{
						EF.Update_Step_Report("On Clicking Next button should redirect to the Next Tab", "Page Navigated to the Next Page||An Error occured while clicking Next", false);
						ST.NativeClick(ObjectCollection.get("Login_ErrorPopUp_Okbtn").toString(), client);
						return false;
					}
				}
				this.Fn_Com_CA_Fieldfiller1();
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("Login_CA_SuccessPopUp").toString(), client))
				{
					String strACStatus = client.elementGetText("NATIVE", ObjectCollection.get("Login_CA_SuccessPopUp").toString(), 0);
					System.out.println("Account Creation is "+strACStatus);
					if(strACStatus.contains("Success"))
					{
						bFlag=false;
						ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
						EF.Update_Step_Report("On Clicking Submit button should Create Account", "New Account is created||New Account is not Created", bFlag);
					}
					else
					{
						bFlag=true;
						ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
						EF.Update_Step_Report("On Clicking Submit button should Create Account", "New Account is Not created||New Account is Created", bFlag);
					}
		        }
				return bFlag;
			}
			catch(Exception Ex)
			{
				
				System.err.println("Fn_Pos_validate_CA Script error :");
				System.err.println("	Function : fnTestMain");
				System.err.println("	Message : "+Ex.getMessage());
				System.err.println("	Cause : "+Ex.getCause());
				Ex.printStackTrace();
				return false;
			}
		}
		
		/*
		 * Positive Validation on Create Account Module
		 */
		public boolean Fn_Positive_val_CA()
		{
			try
			{
				boolean bFlag=false;
							
				ST.NativeClick(ObjectCollection.get("Login_btn_CreateAccount").toString(), client);
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_CA_edt_AccountID").toString(), client);
				EF.Update_Step_Report("Charter Create Account pop-up should be oppened", "Charter Create Account pop-up is oppened||Charter Create Account pop-up is not oppened", bFlag);
				this.Fn_Com_CA_Fieldfiller();
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp").toString(), client))
				{
					String strACStatus = client.elementGetText("NATIVE", ObjectCollection.get("Login_ErrorPopUp").toString(), 0);
					System.out.println("Account Creation is "+strACStatus);
					if(strACStatus.contains("exists")||strACStatus.contains("Exist"))
					{
						//Auto_Login_CreateAccount_0019 validation
						System.out.println("Account Already Exist");
						bFlag=false;
						EF.Update_Step_Report("User should able to create a new account", "User is able to create a new account||The account is already created for this account number", bFlag);
						ST.NativeClick(ObjectCollection.get("Login_ErrorPopUp_Okbtn").toString(), client);
					}
					else
					{
						bFlag=false;
						EF.Update_Step_Report("User should able to create a new account", "User is able to create a new account||The account verification is failed", bFlag);
						ST.NativeClick(ObjectCollection.get("Login_ErrorPopUp_Okbtn").toString(), client);
					}
					ST.NativeClick(ObjectCollection.get("Home_HomeLogo").toString(), client);
					return bFlag;
				}
				//Creating Charter ID
				this.Fn_Com_CA_Fieldfiller1();
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp").toString(), client))
				{
					bFlag=false;
					ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
					ST.NativeClick(ObjectCollection.get("Home_HomeLogo").toString(), client);
					EF.Update_Step_Report("On Clicking Submit button should Create Account", "New Account is created||New Account is not Created An Error pop up occured", bFlag);
				}
				if(ST.NativeElementFound(ObjectCollection.get("Login_CA_SuccessPopUp").toString(), client))
				{
					String strACStatus = client.elementGetText("NATIVE", ObjectCollection.get("Login_CA_SuccessPopUp").toString(), 0);
					
					System.out.println("Account Creation is "+strACStatus);
					if(strACStatus.contains("Success"))
					{
						bFlag=true;
						ST.NativeClick(ObjectCollection.get("Login_HomeScreen").toString(), client);
						EF.Update_Step_Report("On Clicking Submit button should Create Account", "New Account is created||New Account is not Created", bFlag);
					}
					else
					{
						bFlag=false;
						ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
						EF.Update_Step_Report("On Clicking Submit button should Create Account", "New Account is created||New Account is not Created", bFlag);
					}
		        }
				ST.NativeClick(ObjectCollection.get("Home_HomeLogo").toString(), client);
				return bFlag;
			}
			catch(Exception Ex)
			{
				System.err.println("Fn_Pos_validate_CA Script error :");
				System.err.println("	Function : fnTestMain");
				System.err.println("	Message : "+Ex.getMessage());
				System.err.println("	Cause : "+Ex.getCause());
				Ex.printStackTrace();
				return false;
			}
				
		}
		
		/*
		 * The Below Method Covers Auto_Login_ResetPassword_001 to Auto_Login_ResetPassword_003
		 */
		public boolean Fn_ResetPassword()
		{
			boolean bFlag=false;
			try 
			{
				EF.Update_Step_Report("CTVA app should be launched", "CTVA App is launched||CTVA App is not launched", Fn_Device_Setup());
				//client.waitForElementToVanish("NATIVE", ObjectCollection.get("Login_Launcher_Backround").toString(), 0, 300000); //Wait for element to Vanish
				//After Navigating from Environment pop-up
				if(ST.NativeElementFound(ObjectCollection.get("Login_UserName").toString(), client))
				{
					bFlag=true;
					client.closeKeyboard();
				}
				//Clicking on Reset password button	
				EF.Update_Step_Report("Reset Password button should be present", "Reset Password is displayed||Reset Password is not displayed", ST.NativeElementFound(ObjectCollection.get("Login_btn_ResetPassword").toString(), client));
				
				EF.Update_TC_Report(); //Updating to the Next Test Case
				
				//Auto_Login_ResetPassword_002
				bFlag=ST.NativeClick(ObjectCollection.get("Login_btn_ResetPassword").toString(), client);
				EF.Update_Step_Report("Reset Password button should be present", "Reset Password is displayed||Reset Password is not displayed", bFlag);
				
				//Waiting for Reset password page to load
				client.closeKeyboard();
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_RP_FrameLayout").toString(), client);
				EF.Update_Step_Report("Reset Password pop over should appear", "Reset Password pop over available||Reset Password pop over is not available", ST.NativeElementFound(ObjectCollection.get("Login_RP_FrameLayout").toString(), client));
				if(bFlag==false)
				{
					return false;
				}
				//Validating Security Code help button and pop over
				if(ST.NativeElementFound(ObjectCollection.get("Login_RP_FrameLayout_Tab1").toString(), client))
				{
					ST.NativeClick(ObjectCollection.get("Login_RP_SecurityCode_Help").toString(), client);
					bFlag=ST.NativeElementFound(ObjectCollection.get("Login_CA_helpPopover").toString(), client);
					ST.NativeClick(ObjectCollection.get("Login_RP_SecurityCode_Help").toString(), client);
					if(bFlag==false)
					{
						//Security Help Pop Over is not displayed
						EF.Update_Step_Report("SecurityCode Help pop icon should be present", "Security Code help icon is present||Security Code help icon is not present", bFlag);
						return bFlag;
					}
				}
				EF.Update_TC_Report(); //Updating to Next Test case
				//Auto_Login_ResetPassword_003
				//Finding the Security Code 
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_RP_lbl_SecurityCode").toString(), client);
				EF.Update_Step_Report("Security Code should be displayed", "Security Code is displayed||Security Code is missing", bFlag);
				//Finding the Service Code
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_RP_lbl_ServiceCode").toString(), client);
				EF.Update_Step_Report("Service Code should be displayed", "Service Code is displayed||Service Code is missing", bFlag);
				//Finding the Last Name
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_RP_lbl_LastName").toString(), client);
				EF.Update_Step_Report("Last Name should be displayed", "Last Name field is displayed||Last Name field is missing", bFlag);
				ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			}
			catch(Exception ex)
			{
				System.err.println("Fn_ResetPassword Script error :");
				System.err.println("	Function : fnTestMain");
				System.err.println("	Message : "+ex.getMessage());
				System.err.println("	Cause : "+ex.getCause());
				ex.printStackTrace();
				
			}
			return bFlag;
		}
		
		public boolean Fn_Device_Setup()
		{
			boolean bFlag=false;
			try
			{
				//client.setDevice(Current_Device);
				//System.out.println("Current Device"+ Current_Device);
				client.launch(ObjectCollection.get("App_URL").toString(), true, false);
				//System.out.println("Launch URL"+ ObjectCollection.get("App_URL").toString());
				EF.Update_Step_Report("CTVA app should be launched", "CTVA App is launched||CTVA App is not launched", true);
				//To Handle Environment pop-up
				bFlag=this.Fn_Env_PopUp();
				EF.Update_Step_Report("Prod Should be selected in Environment pop over", "Prod is selected in Environment pop over||Prod is not selected in Environment pop over", bFlag);
				//After Navigating from Environment pop-up
				if(ST.NativeElementFound(ObjectCollection.get("Login_UserName").toString(), client))
				{
					bFlag=true;
					EF.Update_Step_Report("Charter App should be oppened", "Charter App is oppened||Charter App is not oppened", bFlag);
					client.closeKeyboard();
				}
			}
			catch(Exception Ex)
			{
				System.err.println("Fn_Device_Setup Script error :");
				System.err.println("	Function : fnTestMain");
				System.err.println("	Message : "+Ex.getMessage());
				System.err.println("	Cause : "+Ex.getCause());
				Ex.printStackTrace();
			}
			return bFlag;
		}
		
		public boolean Fn_ResetPassword_01()
		{
			boolean bFlag=true;
			String strArr[]= {"Login_RP_edt_CharterEmail","Login_RP_edt_AccountNumber","Login_RP_edt_SecurityCode","Login_RP_edt_ServiceCode","Login_RP_edt_LastName","Login_RP2_edt_password","Login_RP2_edt_Confirmpassword"};
			String strArr1[]= {"CharterID","AccountNumber","SecurityCode","ZipCode","LastName","Password","ConfirmPassword"};
			try
			{
				this.Fn_Device_Setup();			
				//Clicking on Reset password button
				if(ST.NativeElementFound(ObjectCollection.get("Login_btn_ResetPassword").toString(), client))
				{
					bFlag=true;
					EF.Update_Step_Report("Reset Password button should be present", "Reset Password is displayed||Reset Password is not displayed", bFlag);
					ST.NativeClick(ObjectCollection.get("Login_btn_ResetPassword").toString(), client);
				}
				else
				{
					bFlag=false;
					EF.Update_Step_Report("Reset Password button should be present", "Reset Password is displayed||Reset Password is not displayed", bFlag);
					return bFlag;
				}
				
				//Waiting for Reset password page to load
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_RP_FrameLayout").toString(), client);
				EF.Update_Step_Report("Reset Password pop over should appear", "Reset Password pop over available||Reset Password pop over is not available", bFlag);
				client.closeKeyboard();
						
				for(int i=0;i<strArr.length;i++)
				{
					bFlag=ST.NativeElementFound(ObjectCollection.get(strArr[i]).toString(), client);
					if(bFlag)
					{
						client.closeKeyboard();
						EF.Update_Step_Report("Validate the fields in Reset Password", "The "+strArr[i]+" is present||The "+strArr[i]+" is not present", bFlag);
						ST.NativeElementSendTxt(ObjectCollection.get(strArr[i]).toString(), TestData.get(strArr1[i]).toString(), client);
					}
					else
					{
						EF.Update_Step_Report("Validate the fields in Reset Password", "The "+strArr[i]+" is present||The "+strArr[i]+" is not present", bFlag);
					}
					if(i==4)
					{
						client.closeKeyboard();
						bFlag=ST.NativeElementFound(ObjectCollection.get("Login_RP_Nextbtn").toString(), client);
						EF.Update_Step_Report("Next Button should be Enabled", "Next Button is Enabled||Next Button is not Enabled", bFlag);
						ST.NativeClick(ObjectCollection.get("Login_RP_Nextbtn").toString(), client);
					}
				}
				client.closeKeyboard();
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_PopUp_Okbtn_enabled").toString(), client);
				if(bFlag)
				{
					EF.Update_Step_Report("Submit Button should be displayed", "Submit Button is available||Submit Button is not available", bFlag);
					ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn_enabled").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("Login_PopUp2").toString(), client))
					{
						String strStatus=client.elementGetText("NATIVE", ObjectCollection.get("Login_PopUp2").toString(), 0);
						if(strStatus.contains("Success"))
						{
							EF.Update_Step_Report("Password reset should be successful", "Password reset is successful||Password reset is not successful", true);
							ST.NativeClick(ObjectCollection.get("Home_HomeLogo").toString(), client);
						}
						else
						{
							EF.Update_Step_Report("Password reset should be successful", "Password reset is successful||Password reset is not successful", false);
							ST.NativeClick(ObjectCollection.get("Home_HomeLogo").toString(), client);
							if(ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp_Okbtn").toString(), client))
								ST.NativeClick(ObjectCollection.get("Login_ErrorPopUp_Okbtn").toString(), client);
						}
					}
					else
					{
						bFlag=false;
						ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp").toString(), client);
						ST.NativeClick(ObjectCollection.get("Login_ErrorPopUp_Okbtn").toString(), client);
						EF.Update_Step_Report("Password reset should be successful", "Password reset is successful||Password reset is not successful an error message occured", false);
					}
				}
				else
				{
					EF.Update_Step_Report("Submit Button should be displayed", "Submit Button is available||Submit Button is not available", bFlag);
					return false;
				}
				client.closeKeyboard();
				ST.NativeClick(ObjectCollection.get("Home_HomeLogo").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp_Okbtn").toString(), client))
					ST.NativeClick(ObjectCollection.get("Login_ErrorPopUp_Okbtn").toString(), client);
			}
			catch(Exception ex)
			{
				System.err.println("Fn_Resetpassword_01 Script error :");
				System.err.println("	Function : fnTestMain");
				System.err.println("	Message : "+ex.getMessage());
				System.err.println("	Cause : "+ex.getCause());
				ex.printStackTrace();
				
			}
			return bFlag;
		}
		
		//Password reset Negative flow
		public boolean Fn_ResetPassword_02()
		{
			boolean bFlag=true;
			String strArr[]= {"Login_RP_edt_CharterEmail","Login_RP_edt_AccountNumber","Login_RP_edt_SecurityCode","Login_RP_edt_ServiceCode","Login_RP_edt_LastName","Login_CA_edt_Password","Login_CA_edt_ConfirmPassword"};
			String strArr1[]= {"CharterID","AccountNumber","SecurityCode","ZipCode","LastName","Password","ConfirmPassword"};
			try
			{
				this.Fn_Device_Setup();
				//Clicking on Reset password button
				if(ST.NativeElementFound(ObjectCollection.get("Login_btn_ResetPassword").toString(), client))
				{
					bFlag=true;
					EF.Update_Step_Report("Reset Password button should be present", "Reset Password is displayed||Reset Password is not displayed", bFlag);
					ST.NativeClick(ObjectCollection.get("Login_btn_ResetPassword").toString(), client);
				}
				else
				{
					bFlag=false;
					EF.Update_Step_Report("Reset Password button should be present", "Reset Password is displayed||Reset Password is not displayed", bFlag);
					return bFlag;
				}
				
				//Waiting for Reset password page to load
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_RP_FrameLayout").toString(), client);
				EF.Update_Step_Report("Reset Password pop over should appear", "Reset Password pop over available||Reset Password pop over is not available", bFlag);
				client.closeKeyboard();
						
				for(int i=0;i<strArr.length;i++)
				{
					bFlag=ST.NativeElementFound(ObjectCollection.get(strArr[i]).toString(), client);
					if(bFlag)
					{
						EF.Update_Step_Report("Validate the fields in Reset Password", "The "+ObjectCollection.get(strArr[i]).toString()+" is present||The "+ObjectCollection.get(strArr1[i]).toString()+" is not present", bFlag);
						ST.NativeElementSendTxt(ObjectCollection.get(strArr[i]).toString(), TestData.get(strArr1[i]).toString(), client);
					}
					else
					{
						EF.Update_Step_Report("Validate the fields in Reset Password", "The "+ObjectCollection.get(strArr[i]).toString()+" is present||The "+ObjectCollection.get(strArr1[i]).toString()+" is not present", bFlag);
					}
					if(i==4)
					{
						bFlag=ST.NativeElementFound(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
						EF.Update_Step_Report("Next Button should be Enabled", "Next Button is Enabled||Next Button is not Enabled", bFlag);
						ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
					}
				}
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_PopUp_Okbtn_enabled").toString(), client);
				if(bFlag)
				{
					EF.Update_Step_Report("Submit Button should be displayed", "Submit Button is available||Submit Button is not available", bFlag);
					ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("Login_PopUp").toString(), client))
					{
						String strStatus=client.elementGetText("NATIVE", ObjectCollection.get("Login_PopUp").toString(), 0);
						if(strStatus.contains("Success"))
						{
							EF.Update_Step_Report("Password reset should not get reset successfully", "Password reset is not reset successfully||Password reset is successfully done", bFlag);
						}
					}
				}
				else
				{
					EF.Update_Step_Report("Submit Button should be displayed", "Submit Button is available||Submit Button is not available", bFlag);
					return false;
				}
				
			}
			catch(Exception ex)
			{
				System.err.println("Fn_Resetpassword_02 Script error :");
				System.err.println("	Function : fnTestMain");
				System.err.println("	Message : "+ex.getMessage());
				System.err.println("	Cause : "+ex.getCause());
				ex.printStackTrace();
				
			}
			return bFlag;
		}
		
		public boolean Fn_ResetPassword_04()
		{
			boolean bFlag=false;
			try
			{
				//Launching CTVA APP
				this.Fn_Device_Setup();
				
				EF.Update_Step_Report("Reset password button should be displayed", "Reset password button is displayed||Reset Button is not displayed", ST.NativeElementFound(ObjectCollection.get("Login_btn_ResetPassword").toString(), client));
				ST.NativeClick(ObjectCollection.get("Login_btn_ResetPassword").toString(), client);
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_RP_lbl_CharterEmail").toString(), client);
				if(bFlag)
				{
					EF.Update_Step_Report("Validate the fields in Reset Password", "The Charter Mail ID field is present||The Charter Mail ID field is not present", bFlag);
					ST.NativeElementSendTxt(ObjectCollection.get("Login_RP_edt_CharterEmail").toString(), TestData.get("CharterID").toString(), client);
				}
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_RP_lbl_SecurityCode").toString(), client);
				if(bFlag)
				{
					EF.Update_Step_Report("Validate the fields in Reset Password", "The Charter Mail ID field is present||The Charter Mail ID field is not present", bFlag);
					ST.NativeElementSendTxt(ObjectCollection.get("Login_RP_edt_SecurityCode").toString(), TestData.get("Password").toString(), client);
				}
				if(Current_OS=="IOS")
				{
					ST.NativeClick(ObjectCollection.get("Login_RP_Nextbtn").toString(), client);
					EF.Update_Step_Report("An error message pop up should be displayed", "An error message pop is displayed||An error message pop is not displayed", ST.NativeElementFound(ObjectCollection.get("Login_PopUp").toString(), client));
				}
				EF.Update_Step_Report("Next Button should not be enabled", "Next button is not enabled||Next Button is enabled", (!ST.NativeElementFound(ObjectCollection.get("Login_PopUp_Okbtn_enabled").toString(), client)));
				ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			}
			catch(Exception Ex)
			{
				System.err.println("Fn_ResetPassword Script error :");
				System.err.println("	Function : fnTestMain");
				System.err.println("	Message : "+Ex.getMessage());
				System.err.println("	Cause : "+Ex.getCause());
				Ex.printStackTrace();
			}
			return bFlag;
		}
		
		public boolean Fn_Env_PopUp()
		{
			boolean bFlag=false;
			try
			{
				if (Current_OS.contains("IOS"))
				{	
					//Only for iOS device
					if(ST.NativeElementFound(ObjectCollection.get("Login_Frame_EnvPopUp").toString(), client))
					{
						if(client.isElementFound("NATIVE",ObjectCollection.get("Login_Prod_EnvPopUp").toString(), 0))
						{
							ST.NativeClick(ObjectCollection.get("Login_OkBtn_EnvPopUp_IOS").toString(),client);
							bFlag=true;
							if(ST.NativeElementFound(ObjectCollection.get("Login_Launcher_Backround").toString(), client))
					        {
					        	//client.waitForElementToVanish("NATIVE", ObjectCollection.get("Login_Launcher_Backround").toString(), 0, 1000000);
					        }
						}
						else
						{
							ST.NativeClick(ObjectCollection.get("Login_DD_Arrow_EnvPopUp_IOS").toString(),client);
					        client.sleep(3000);
//					        client.elementSwipe("NATIVE",ObjectCollection.get("Login_Popover_EnvPopUp_IOS").toString(), 0, "Down", 0, 2000);
//					        client.isElementFound("NATIVE",ObjectCollection.get("Login_Prod_EnvPopUp").toString(), 0);
					        client.elementSwipeWhileNotFound("NATIVE", "//*[@class='_UIPopoverView']", "Down", 300, 2000, "NATIVE", "//*[@accessibilityLabel='PROD' and @class='UIButtonLabel']", 0, 1000, 5, true);
					        client.hybridWaitForPageLoad(5000);
					        ST.NativeClick(ObjectCollection.get("Login_OkBtn_EnvPopUp_IOS").toString(),client);
					        if(ST.NativeElementFound(ObjectCollection.get("Login_Launcher_Backround").toString(), client))
					        {
					        	client.waitForElementToVanish("NATIVE", ObjectCollection.get("Login_Launcher_Backround").toString(), 0, 10000);
					        }
					        bFlag=true;
					    }
					}
					else 
					{
						bFlag=false;
						System.out.println("Environment Set up not found");
					}
				}
				else
				{
					//Environment Pop_Up Android
					if(ST.NativeElementFound(ObjectCollection.get("Login_Prod_EnvPopUp").toString(),client))
					{
						ST.NativeClick(ObjectCollection.get("Login_Prod_EnvPopUp").toString(),client);
						bFlag=true;
						if(ST.NativeElementFound(ObjectCollection.get("Login_Launcher_Backround").toString(), client))
				        {
				        	client.waitForElementToVanish("NATIVE", ObjectCollection.get("Login_Launcher_Backround").toString(), 0, 1000000);
				        }
					}
					else
					{
						System.out.println("Environment Pop Up is Missing");
						bFlag=false;
					}
					
				}
				return true;
			}
			catch(Exception ex)
			{
				System.err.println("Env_PopUp Script error :");
				System.err.println("	Function : fnTestMain");
				System.err.println("	Message : "+ex.getMessage());
				System.err.println("	Cause : "+ex.getCause());
				ex.printStackTrace();
			}
			return true;
		}
		
		public boolean Fn_Login()
		{
			boolean bFlag=false;
			try
			{
				//setUp(Globals.Current_TestCase);
				this.Fn_Device_Setup();
				
				//	Common flow for Login
				if(ST.NativeElementFound(ObjectCollection.get("Login_HomeScreen").toString(),client))
				{
					//System.out.println("Navigated to home screen page"+client.elementGetText("NATIVE", ObjectCollection.get("Login_HomeScreen").toString(), 0));
					if(client.isElementFound("NATIVE", ObjectCollection.get("Login_PopUp").toString()))
			        {
				        String str0=client.elementGetText("NATIVE", ObjectCollection.get("Login_PopUp").toString(), 0);
			        	if(str0.contains("Watchlist Item Expiring"))
			        	{	
				           	//client.isElementFound("NATIVE", "(//*/*[@class='UIImageView' and @width>0])[128]", 0);
				        	System.out.println("Watchlist Item Expiring");
				        	ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(),client);
				        }
			        }
					bFlag= true;
					return bFlag;
				}
				
				ST.NativeElementFound(ObjectCollection.get("Login_UserName").toString(), client);
				client.closeKeyboard();
				if(ST.NativeElementFound(ObjectCollection.get("Login_btn_Login").toString(),client))
				{
					//client.closeKeyboard();
					//ST.NativeElementWait(ObjectCollection.get("Login_UserName").toString(), client);
					ST.NativeElementSendTxt(ObjectCollection.get("Login_UserName").toString(),TestData.get("UserName").toString(),client);
					ST.NativeElementSendTxt(ObjectCollection.get("Login_PassWord").toString(),TestData.get("Password").toString(),client);
					ST.NativeClick(ObjectCollection.get("Login_btn_Login").toString(),client);
					ST.NativeElementFound(ObjectCollection.get("Common_ProgressBar").toString(),client);
			        client.waitForElementToVanish("NATIVE",ObjectCollection.get("Common_ProgressBar").toString(), 0, 10000);
			        client.sleep(2000);
			        
			        //Error handling scenario
			        if(client.isElementFound("NATIVE", ObjectCollection.get("Login_ErrorPopUp").toString()))
			        {
				        String str0=client.elementGetText("NATIVE", ObjectCollection.get("Login_ErrorPopUp").toString(), 0);
				        //Registering Device
				        if(str0.contains("Device Registered"))
			        	{
			        		ST.NativeClick(ObjectCollection.get("ContinueButton").toString(), client);
			        		ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
			        	}
				        //Watch list handling scenario
			        	else if(str0.contains("Watchlist Item Expiring"))
			        	{	
				           	//client.isElementFound("NATIVE", "(//*/*[@class='UIImageView' and @width>0])[128]", 0);
				        	System.out.println("Watchlist Item Expiring");
				        	ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(),client);
				        	bFlag= false;
			        	}
				        if(str0.contains("Error"))
			        	{
			        		ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(),client);
			        		System.out.println("Log in Error occured");
			        		return false;
			        	}
			        	else
			        	{
			        		ST.NativeClick(ObjectCollection.get("Login_Welcome_CloseButton").toString(), client);
			        	}
			        }
					else if(ST.NativeElementFound(ObjectCollection.get("Login_Welcome_PopOver").toString(), client))
	        		{
			        	ST.NativeClick(ObjectCollection.get("Login_Welcome_CloseButton").toString(), client);
	        		}
					else
					{
						System.out.println("Log in Success");
						bFlag= true;
						return bFlag;
					}
			        if(client.isElementFound("NATIVE", ObjectCollection.get("Login_ErrorPopUp").toString()))
			        {
			        	ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(),client);
			        }
				}
				//After Logging In
				if(Current_OS=="IOS")
				{
					if(ST.NativeElementFound(ObjectCollection.get("Home_Network_Status").toString(), client))
					{
						String strStatus= ST.NativeGetElementText(ObjectCollection.get("Home_Network_Status").toString(), 0, client);
						if(strStatus.contains("In Home"))
						{
							bFlag=true;
							EF.Update_Step_Report("Validating user network status", "User Currently Logged in In Home network||User Currently not Logged in In Home Network", bFlag);
						}
						else if(strStatus.contains("Out of Home"))
						{
							bFlag=true;
							EF.Update_Step_Report("Validating user network status", "User Currently Logged in Out of Home network||User Currently not Logged in Out of Home Network", bFlag);
						}
						else
						{
							bFlag=false;
							EF.Update_Step_Report("Validating user network status", "User network access is not identified properly||User network access is not identified properly", bFlag);
						}
					}
				}
				else
				{
					if(ST.NativeElementFound(ObjectCollection.get("Home_PageList").toString(), client))
					{
						String strStatus= ST.NativeGetElementText(ObjectCollection.get("Home_Network_Status").toString(), 0, client);
						if(strStatus.contains("In-Home"))
						{
							bFlag=true;
							EF.Update_Step_Report("Validating user network status", "User Currently Logged in In Home network||User Currently Logged in Out of Home Network", bFlag);
						}
						else if(strStatus.contains("Out of Home"))
						{
							bFlag=true;
							EF.Update_Step_Report("Validating user network status", "User Currently Logged in Out of Home network||User Currently not Logged in Out of Home Network", bFlag);
						}
						else
						{
							bFlag=false;
							EF.Update_Step_Report("Validating user network status", "User network access is not identified properly||User network access is not identified properly", bFlag);
						}
					}
				}
			}
			catch (Exception e)
			{
				bFlag=false;
				System.out.println("Login failure popup is displayed");
				System.out.println("login failure");
				System.err.println("	Message : " + e.getMessage());
				System.err.println("	Cause : " + e.getCause());
				e.printStackTrace();
				tearDown();
			}
			EnjoyingAppHandler();
			return bFlag;
		}

		public boolean Fn_LogOut()
		{
			boolean bFlag;
			try 
			{
				this.Fn_Com_Navigate("Home_Settings");
				if(ST.NativeElementFound(ObjectCollection.get("Home_Settings").toString(), client))
				{
					ST.NativeClick(ObjectCollection.get("Login_btn_LogOut").toString(), client);
					bFlag = true;
				}
				else
				{
					System.out.println("The Settings page is not found");
					bFlag = false;
				}
		       
			} 
			catch (Exception e) 
			{
				System.err.println("Master Script error :");
				System.err.println("	Function : fnTestMain");
				System.err.println("	Message : " + e.getMessage());
				System.err.println("	Cause : " + e.getCause());
				bFlag=false;
				e.printStackTrace();
			}
			return bFlag;
		}
		
		
		public boolean Fn_Create_Account_Validate(int nStep) 
		{
			boolean bFlag=false;
			//int nCount = 0;
			try
			{
				//Clicking Create Account 
				client.setDevice(Current_Device);
				
				switch(nStep)
				{
				case 1: this.Fn_Device_Setup(); if(Fn_val_CA_001()) { bFlag=true;}else {bFlag=false;}
				break;
				case 2: if(Fn_val_CA_002("Login_CA_help_SecurityCode")){ bFlag=true;}else {bFlag=false;}
				break;
				case 3: if(Fn_val_CA_002("Login_CA_help_ServiceCode")) { bFlag=true;}else {bFlag=false;}
				break;
				case 4: this.Fn_Device_Setup(); if(Fn_Positive_val_CA()) { bFlag=true;}else {bFlag=false;}
				break;
				case 5: this.Fn_Device_Setup(); if(Fn_Negative_val_CA()) {bFlag=true;}else {bFlag=false;}
				break;
				case 6: this.Fn_Device_Setup(); if(Fn_Password_val_CA()) {bFlag=true;}else {bFlag=false;}
				break;
				}
				
			}
			catch(Exception Ex)
			{
				System.out.println("Error Handled //TODO");
				bFlag=false;
			}
			return bFlag;
		}
		
		public boolean Fn_val_CA_001()
		{
			boolean bFlag=false;
			String saFields[]={"Login_CA_edt_AccountID","Login_CA_edt_SecurityCode","Login_CA_edt_ServiceCode","Login_CA_edt_LastName"};
			String strVal;
			ST.NativeClick(ObjectCollection.get("Login_btn_CreateAccount").toString(), client);
			EF.Update_Step_Report("Create Account pop up should be displayed", "Create Account pop up is displayed||Create Account pop up is not displayed", ST.NativeElementFound(ObjectCollection.get("Login_CA_edt_AccountID").toString(), client));
			if(ST.NativeElementFound(ObjectCollection.get("Login_CA_edt_AccountID").toString(), client))
			{
				bFlag=true;
				client.closeKeyboard();
	        	//Looking for Field Validation through looping
	        	for(int i=0;i<saFields.length;i++)
				{
	        		strVal=saFields[i];
	        		//Field Validation
	        		if(ST.NativeElementFound(ObjectCollection.get(saFields[i]).toString(), client))
	        		{
	        			EF.Update_Step_Report(strVal+" field Should be displayed", strVal+" field is displayed", true);
	        			bFlag=true;
	        		}
	        		else
	        		{
	        			EF.Update_Step_Report(strVal+" field Should be displayed", strVal+" field is not displayed", false);
	        			bFlag=false;
	        		}
				}
			}
			else
			{
				EF.Update_Step_Report("Create Account pop up should be displayed", "Create Account pop up is displayed||Create Account pop up is not displayed", false);
				bFlag=false;
			}
			return bFlag;
		}
		
		/*
		 * Validating the Password strength on create account pop over
		 */
		public boolean Fn_Password_val_CA()
		{
			boolean bFlag=true;
			try
			{
				ST.NativeClick(ObjectCollection.get("Login_btn_CreateAccount").toString(), client);
				bFlag=ST.NativeElementFound(ObjectCollection.get("Login_CA_edt_AccountID").toString(), client);
				EF.Update_Step_Report("Charter Create Account pop-up should be oppened", "Charter Create Account pop-up is oppened||Charter Create Account pop-up is not oppened", bFlag);
				this.Fn_Com_CA_Fieldfiller();
				if(ST.NativeElementFound(ObjectCollection.get("Login_PopUp").toString(), client))
				{
					String strACStatus = client.elementGetText("NATIVE", ObjectCollection.get("Login_PopUp").toString(), 0);
					System.out.println("Account Creation is "+strACStatus);
					if(strACStatus.contains("Account Exists"))
					{
						//Auto_Login_CreateAccount_0019 validation
						System.out.println("Account Already Exist");
						bFlag=true;
						return bFlag;
					}
				}
				EF.Update_Step_Report("Choose Chaerter Email ID field should be displayed", "Charter Email ID is Displayed||Charter Email ID is not Displayed", ST.NativeElementSendTxt(ObjectCollection.get("Login_CA_edt_CharterID").toString(), TestData.get("CharterID").toString(), client));
				EF.Update_Step_Report("Choose Chaerter Email ID field should be displayed", "Charter Email ID is Displayed||Charter Email ID is not Displayed", ST.NativeElementSendTxt(ObjectCollection.get("Login_CA_edt_Password").toString(), TestData.get("Password").toString(), client));
				//Validating Password strength
				if(ST.NativeElementFound(ObjectCollection.get("Login_CA_Pswd_Status").toString(), client))
		        {
					EF.Update_Step_Report("Validating Password Status field", "Password status field is present||Password status field is not available", true);
					String strPswdStatus = client.elementGetText("NATIVE", ObjectCollection.get("Login_CA_Pswd_Status").toString(), 0);
		        	System.out.println("Passowrd Status :"+strPswdStatus);
		        	if(strPswdStatus.contains("Invalid"))
		        	{
		        		EF.Update_Step_Report("Validating Password Strength", "Password validation is done successfully||Password validator not validating properly", true);
		        		return true;
		        	}
		        	else
		        	{
		        		EF.Update_Step_Report("Validating Password Strength", "Password validator not validating properly||Password validator not validating properly", false);
		        		return false;
		        	}
		        }
		        else
		        {
		        	EF.Update_Step_Report("Validating Password Status field", "Password status field is present||Password status field is not available", false);
		        }
			}
			catch(Exception Ex)
			{
				System.err.println("Fn_Password_val_CA Script error :");
				System.err.println("	Function : fnTestMain");
				System.err.println("	Message : "+Ex.getMessage());
				System.err.println("	Cause : "+Ex.getCause());
				Ex.printStackTrace();
				return false;
			}
			return bFlag;
		}
		
		/*
		 * field filler in Create Account Tab1
		 */
		public void Fn_Com_CA_Fieldfiller()
		{
			client.closeKeyboard();
			//Editing Account Number
	        EF.Update_Step_Report("Account Number field should be displayed", "Account Number is displayed||Account number field is missing", ST.NativeElementSendTxt(ObjectCollection.get("Login_CA_edt_AccountID").toString(), TestData.get("AccountNumber").toString(), client));
	        client.closeKeyboard();
	        //Editing security code
	        EF.Update_Step_Report("Security code field should be displayed", "Security Code is displayed||Security Code field is missing", ST.NativeElementSendTxt(ObjectCollection.get("Login_CA_edt_SecurityCode").toString(), TestData.get("SecurityCode").toString(), client));
	        client.closeKeyboard();
	        //Editing Zip Code Button
	        EF.Update_Step_Report("Service Zip Code field should be displayed", "Service Zip Code is displayed||Service code field is missing", ST.NativeElementSendTxt(ObjectCollection.get("Login_CA_edt_ServiceCode").toString(), TestData.get("ZipCode").toString(), client));
	        client.closeKeyboard();
	        //Editing Last Name
	        EF.Update_Step_Report("Last Name field should be displayed", "Last Name field is displayed||Last Name field is missing", ST.NativeElementSendTxt(ObjectCollection.get("Login_CA_edt_LastName").toString(), TestData.get("LastName").toString(), client));
	        client.closeKeyboard();
	        //Close Keyboard
	        
	        //Clicking Next Button
	        EF.Update_Step_Report("Next Button should be enabled", "Next Button is enabked||Next Button is not enabled", ST.NativeElementFound(ObjectCollection.get("Login_PopUp_Okbtn_enabled").toString(), client));
	        client.closeKeyboard();
	        ST.NativeClick(ObjectCollection.get("Login_RP_Nextbtn").toString(), client);
		}
		
		/*
		 * field filler in Create Account Tab2
		 */
		public void Fn_Com_CA_Fieldfiller1()
		{
			client.closeKeyboard();
			//Editing Charter Email ID
			EF.Update_Step_Report("Charter Email ID field should be displayed", "Charter Email ID field is displayed||Charter Email ID field is missing", ST.NativeElementSendTxt(ObjectCollection.get("Login_CA_edt_CharterID").toString(), TestData.get("CharterID").toString(), client));
			client.closeKeyboard();
			//Editing Password
			EF.Update_Step_Report("Charter Email ID field should be displayed", "Charter Email ID field is displayed||Charter Email ID field is missing", ST.NativeElementSendTxt(ObjectCollection.get("Login_CA_edt_Password").toString(), TestData.get("Password").toString(), client));
			client.closeKeyboard();
			//Editing Confirm Password
			EF.Update_Step_Report("Charter Email ID field should be displayed", "Charter Email ID field is displayed||Charter Email ID field is missing", ST.NativeElementSendTxt(ObjectCollection.get("Login_CA_edt_ConfirmPassword").toString(), TestData.get("ConfirmPassword").toString(), client));
			client.closeKeyboard();
			//Close keyboard
			
			//Clicking Submit Button
			EF.Update_Step_Report("Next Button should be enabled", "Next Button is enabled||Next Button is not enabled", ST.NativeElementFound(ObjectCollection.get("Login_PopUp_Okbtn_enabled").toString(), client));
			client.closeKeyboard();
			ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn_enabled").toString(), client);
			
		}
		
		public boolean Fn_val_CA_002(String strVal)
		{
			boolean bFlag=false;
			//If its iOS
			if(Current_OS=="IOS"&&strVal=="Login_CA_help_ServiceCode")
			{
				EF.Update_Step_Report(strVal+" help icon should be displayed", strVal+" help icon is not displayed||"+strVal+" help icon is not displayed", ST.NativeElementFound(ObjectCollection.get("").toString(), client));
			}
			if(ST.NativeElementFound(ObjectCollection.get("Login_CA_edt_AccountID").toString(), client))
			{
				if(ST.NativeElementFound(ObjectCollection.get(strVal).toString(), client))
				{
					EF.Update_Step_Report(strVal+" help icon should be displayed", strVal+" help icon is displayed", true);
					ST.NativeClick(ObjectCollection.get(strVal).toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("Login_CA_helpPopover").toString(), client))
					{
						EF.Update_Step_Report("Help pop over should be displayed", "Help pop over is displayed", true);
						ST.NativeClick(ObjectCollection.get(strVal).toString(), client);
						bFlag=true;
					}
					else
					{
						EF.Update_Step_Report("Help pop over should be displayed", "Help pop over is displayed||Help pop over is not displayed", false);
						bFlag=false;
					}
				}
				else
				{
					EF.Update_Step_Report(strVal+" help icon should be displayed", strVal+" help icon is displayed||"+strVal+" help icon is not displayed", false);
					bFlag=false;
				}
			}
			return bFlag;
		}
		//Dont use it until its needed, only used for getting code
		public void Fn_val_CA_003()
		{
			String saFields[]={"Account Number","Security Code","Service Zip Code","Last Name"};
			String strVal;
			if(ST.NativeElementFound(ObjectCollection.get("Login_CA_edt_AccountID").toString(), client))
			{
				EF.Update_Step_Report("Create Account pop should be displayed", "Create Account pop up is displayed", true);
	        	client.closeKeyboard();
	        	for(int i=0;i<saFields.length;i++)
				{
	        		strVal=saFields[i];
	        		//Condition for fields which have help icon
	        		if(strVal.equalsIgnoreCase("Security Code") || strVal.equalsIgnoreCase("Service Zip Code"))
	        		{
	        			if(client.isElementFound("NATIVE", "xpath=//*[@knownSuperClass='android.widget.TextView' and @text='"+strVal+"']",0))
		        		{
		        			//Looking for Help button
		        			if(strVal.contains("Security Code"))
		        			{
		        				client.click("NATIVE", "xpath=//*[@id='create_charter_id_security_code_help']", 0, 1);
		        				EF.Update_Step_Report("Security Code help icon should be displayed", "Security Code help icon is displayed||Security Code help icon is not displayed", true);
		        	            if(client.isElementFound("NATIVE", "xpath=//*[@id='help_text']", 0))
		        	            {
		        	            	EF.Update_Step_Report("Security Code help icon pop over should be displayed", "Security Code help icon pop over is displayed", true);
		        	                client.click("NATIVE", "xpath=//*[@id='create_charter_id_security_code_help']", 0, 1);
		        	            }
		        	            else
		        	            {
		        	            	EF.Update_Step_Report("Security Code help icon pop over should be displayed", "Security Code help icon pop over is not displayed", false);
		        	            }
		        			}
		        			else if(strVal.contains("Service Zip Code"))
		        			{
		        				if(ST.NativeElementFound("xpath=//*[@id='create_charter_id_service_zip_code_help']", client))
		        				{
			        				client.click("NATIVE", "xpath=//*[@id='create_charter_id_service_zip_code_help']", 0, 1);
			        				EF.Update_Step_Report("Service Zip Code help icon should be displayed", "Service zip Code help icon is displayed", true);
			        				if(client.isElementFound("NATIVE", "xpath=//*[@id='help_text']", 0))
			        	            {
			        	            	EF.Update_Step_Report("Service Zip Code help icon pop over should be displayed", "Service zip Code help icon pop over is displayed", true);
			        	                client.click("NATIVE", "xpath=//*[@id='create_charter_id_service_zip_code_help']", 0, 1);
			        	            }
			        	            else
			        	            {
			        	            	EF.Update_Step_Report("Service Zip Code help icon pop over should be displayed", "Service zip Code help icon pop over is not displayed", false);
			        	            }
		        				}
		        				else
		        				{
		        					EF.Update_Step_Report("Service Zip Code help icon should be displayed", "Service zip Code help icon is not displayed", false);
		        				}
		        				
		        	        }
		        			else
		        			{
		        				
		        			}
		        		}
	        			else
	        			{
	        				EF.Update_Step_Report(strVal+" should be displayed", strVal+" is not displayed", false);
	        			}
	        		}
		        	else
	        		{
		        		EF.Update_Step_Report("Service Zip Code & Security Code help icon should be displayed", "Service zip Code & Security help icon is displayed||Service zip Code & Security help icon is not displayed", false);
	        		}
	        	}
	        		
	        }
			else
			{
				EF.Update_Step_Report("Create Account pop up should be displayed", "Create Account pop up is not displayed", false);
			}
		}
		
		public boolean Fn_Login_Validate()
		{
			boolean bFlag=false;
			//Launching the CTVA App
			this.Fn_Device_Setup();
			//Login Header
			try
			{
				if(ST.NativeElementFound(ObjectCollection.get("Login_UserName").toString(), client)&&ST.NativeElementFound(ObjectCollection.get("Login_PassWord").toString(), client))
				{
					EF.Update_Step_Report("Observe the Welcome Text displayed", "The Welcome Text is displayed||The Welcome Text is not displayed", ST.NativeElementFound(ObjectCollection.get("Login_LoginScreen_HelpText").toString(), client));
					client.closeKeyboard();
					//Welcome help text
					//String strHelpMSG=client.elementGetText("NATIVE", ObjectCollection.get("Login_LoginScreen_HelpText").toString(), 0);
					EF.Update_Step_Report("Observe the Welcome Text displayed with Welcome", "The Welcome Text is displayed as expected||The Welcome Text is not displayed as expected",ST.NativeElementFound(ObjectCollection.get("Login_LoginScreen_HelpText").toString(), client));
			        EF.Update_TC_Report();
			        // 2nd Test case
			        //PreCondition Full filled
			        EF.Update_Step_Report("CTVA App should be launched", "CTVA App file is launched||CTVA App file is not launched",true);
			        EF.Update_Step_Report("Prod Environment Should be picked in PopUp", "Environment PopUp is selected in PopUp||Prod Environment PopUp is not selected",true);
			        //User Name validation
			        EF.Update_Step_Report("Username hint text validation", "Charter.net Email or Username is displayed||Charter.net Email or Username is not displayed",ST.NativeElementFound(ObjectCollection.get("Login_LoginScreen_msg_UN").toString(), client));
			        EF.Update_TC_Report();
			        
			        //3rd Test case
			        //PreCondition Full Filled
			        EF.Update_Step_Report("CTVA App should be launched", "CTVA App file is launched||CTVA App file is not launched",true);
			        EF.Update_Step_Report("Prod Environment Should be picked in PopUp", "Environment PopUp is selected in PopUp||Prod Environment PopUp is not selected",true);
			        //Show password field
			        if(ST.NativeElementFound(ObjectCollection.get("Login_ShowPassword_UnChecked").toString(), client)||ST.NativeElementFound(ObjectCollection.get("Login_ShowPassword_Checked").toString(), client))
			        	EF.Update_Step_Report("Show Password field should be displayed below pasword field", "Show Password field is displayed below password field||Show Password field is not displayed below password field", true);
			        else
			        	EF.Update_Step_Report("Show Password field should be displayed below pasword field", "Show Password field is displayed below password field||Show Password field is not displayed below password field", false);
			        EF.Update_TC_Report();
			        
			        //4th test case
			        //PreCondition Full filled
			        EF.Update_Step_Report("CTVA App should be launched", "CTVA App file is launched||CTVA App file is not launched",true);
			        EF.Update_Step_Report("Prod Environment Should be picked in PopUp", "Environment PopUp is selected in PopUp||Prod Environment PopUp is not selected",true);
			        //Show password field
			        if(ST.NativeElementFound(ObjectCollection.get("Login_ShowPassword_UnChecked").toString(), client))
				        EF.Update_Step_Report("Show Password field should be displayed unchecked", "Show Password field is displayed unchecked by default||Show Password field is not displayed checked by default", true);
			        else
			        {
			        	ST.NativeClick(ObjectCollection.get("Login_ShowPassword_Checked").toString(), client);
			        	EF.Update_Step_Report("Show Password field should be displayed unchecked", "Show Password field is displayed unchecked by default||Show Password field is not displayed checked by default", ST.NativeElementFound(ObjectCollection.get("Login_ShowPassword_UnChecked").toString(), client));
			        }
			        bFlag=ST.NativeElementSendTxt(ObjectCollection.get("Login_PassWord").toString(), TestData.get("Password").toString(), client);
			        EF.Update_Step_Report("User should be able to enter Password", "Password is entered successfully||Password is not entered successfully", bFlag);
			        EF.Update_TC_Report();
			        //5th Test case
			        //Precondition Full filled
			        EF.Update_Step_Report("CTVA App should be launched", "CTVA App file is launched||CTVA App file is not launched",true);
			        EF.Update_Step_Report("Prod Environment Should be picked in PopUp", "Environment PopUp is selected in PopUp||Prod Environment PopUp is not selected",true);
			        //ST.NativeElementSendTxt(ObjectCollection.get("Login_PassWord").toString(), TestData.get("Password").toString(), client);
			        ST.NativeClick(ObjectCollection.get("Login_ShowPassword_UnChecked").toString(), client);
			        EF.Update_Step_Report("Text should be visible when we select the Show password field", "Password is visible||Password is not Visible", ST.NativeElementFound(ObjectCollection.get("Login_ShowPassword_Checked").toString(), client));
			        EF.Update_TC_Report();
			        
			        //6th Test case
			        //Precondition Full Filled
			        EF.Update_Step_Report("CTVA App should be launched", "CTVA App file is launched||CTVA App file is not launched",true);
			        EF.Update_Step_Report("Prod Environment Should be picked in PopUp", "Environment PopUp is selected in PopUp||Prod Environment PopUp is not selected",true);
			        
			        EF.Update_Step_Report("UserName Edit view should be displayed", "UserName Edit field is displayed||UserName Edit field is not displayed", true);
			        ST.NativeElementSendTxt(ObjectCollection.get("Login_UserName").toString(), TestData.get("UserName").toString(), client);
			        EF.Update_Step_Report("Password Edit view should be displayed", "Password Edit view is displayed||Password edit view is not displayed", true);
			        //ST.NativeElementSendTxt(ObjectCollection.get("Login_PassWord").toString(), TestData.get("Password").toString(), client);
			        EF.Update_Step_Report("Login button should be displayed", "Login button is displayed||Login button is missing", ST.NativeElementFound(ObjectCollection.get("Login_btn_Login").toString(), client));
			        EF.Update_TC_Report();
			        
			        //7th Test case
			        //Help Button //No help button for iOS
			        client.closeKeyboard();
			        EF.Update_Step_Report("Help Button should be displayed at the bottom of the Login screen", "Help button is displayed||Help button is not displayed", ST.NativeElementFound(ObjectCollection.get("Login_LoginScreen_HelpButton").toString(), client));
			        
			        if(Current_OS.equalsIgnoreCase("IOS"))
			        {
			        	EF.Update_Step_Report("Help Pop over should be displayed on click of help", "Adroid Specific Help pop over is not available in iOS||Help pop over is not displayed", true);
			        }
			        else
			        {
			        	ST.NativeClick(ObjectCollection.get("Login_LoginScreen_HelpButton").toString(), client);
			        	EF.Update_Step_Report("Help Pop over should be displayed on click of help", "Help pop over is displayed||Help pop over is not displayed", ST.NativeElementFound(ObjectCollection.get("Login_RP_FrameLayout").toString(), client));
			        }
			        EF.Update_TC_Report();
			        
			        //8th Test case
			        ST.NativeClick(ObjectCollection.get("Login_LoginScreen_ActionBarTitle").toString(), client);
			        ST.NativeElementSendTxt(ObjectCollection.get("Login_UserName").toString(), TestData.get("UserName").toString(), client);
			        ST.NativeElementSendTxt(ObjectCollection.get("Login_PassWord").toString(), TestData.get("Password").toString(), client);
			        client.closeKeyboard();
			        ST.NativeClick(ObjectCollection.get("Login_btn_Login").toString(), client);
			        //Progress bar
			        if(ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client))
			    	{
			        	//Error Pop up 
				    	if(ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp").toString(), client))
				    	{
				    		String str1 = ST.NativeGetElementText(ObjectCollection.get("Login_ErrorPopUp").toString(), 0, client);
				    		EF.Update_Step_Report("On logging in with Mountain customer user should get an error message", "Error Message:"+str1+" is displayed||Error Message is not displayed", true);
				    		bFlag=true;
				    		//EF.Update_Step_Report("User should be able to login successfully with the valid user name and password", "User is able to Login||User is not able to Login", false);
				    		ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
				        }
				        else
				        {
				        	EF.Update_Step_Report("User should not be able to login successfully", "User is able to Login||User is able to Login", false);
				        	client.sleep(3000);
				        	this.Fn_Com_Navigate("Home_Setting");
				        	this.Fn_LogOut();
				        	return false;
				        }
			        }
			        EF.Update_TC_Report();
			        
			        //9th Test case
			        ST.NativeClick(ObjectCollection.get("Login_LoginScreen_ActionBarTitle").toString(), client);
			        EF.Update_Step_Report("User should install the CTVA app", "CTVA App is installed||CTVA ", true);
			        EF.Update_Step_Report("User should able to launch the CTVA app", "User is able to launch the CTVA app||User is able to", true);
			        ST.NativeElementSendTxt(ObjectCollection.get("Login_UserName").toString(), TestData.get("UserName").toString(), client);
			        ST.NativeElementSendTxt(ObjectCollection.get("Login_PassWord").toString(), TestData.get("Password").toString(), client);
			        client.closeKeyboard();
			        ST.NativeClick(ObjectCollection.get("Login_btn_Login").toString(), client);
			        //Progress bar
			        bFlag=false;
			        if(ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client)||ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp").toString(), client))
			    	{
			        	//Error Pop up 
				    	if(ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp").toString(), client))
				    	{
				    		String str1 = ST.NativeGetElementText(ObjectCollection.get("Login_ErrorPopUp").toString(), 0, client);
				    		EF.Update_Step_Report("On logging in with Phone Only customer user should get an error message", "Error Message:"+str1+" is displayed||Error Message is not displayed", true);
				    		bFlag=true;
				    		//EF.Update_Step_Report("User should be able to login successfully with the valid user name and password", "User is able to Login||User is not able to Login", false);
				    		ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
				        }
				        else
				        {
				        	EF.Update_Step_Report("User should not be able to login successfully", "User is able to Login||User is able to Login", false);
				        	EF.Update_Step_Report("User should not be able to login successfully", "User is able to Login||User is able to Login", false);
				        	client.sleep(3000);
				        	this.Fn_Com_Navigate("Home_Setting");
				        	this.Fn_LogOut();
				        	return false;
				        }
			        }
			        EF.Update_TC_Report();
			        
			        //10th Test case
			        EF.Update_Step_Report("User should get animated splash screen", "Animated splash screen is displayed||User is able to Login", true);
		        }
			}
			catch(Exception E)
			{
				System.out.println("Error in Login Validation :"+E.getCause());
			}
				
			return bFlag;
	    }

		public boolean Fn_TodayDropDown()
		{
			boolean bFlag=true;
			try
			{
				if(Fn_Com_Navigate("Home_Guide"))
				{
					EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to guide page||User is not navigated to guide page", bFlag);
					EF.Update_Step_Report("Verify that the Guide page contain Day Drop Down list", "Guide page contains Day Drop Down list||Guide page doesn't contain Day Drop down list", ST.NativeElementFound(ObjectCollection.get("Login_HomeScreen").toString(), client));
					this.Fn_Guide_Today();
					//ST.NativeClick(ObjectCollection.get("Guide_DayPicker_DropDown").toString(), client);
					EF.Update_TC_Report();
					//Auto_Guide_DDL_002
					EF.Update_Step_Report("CTVA App should be launched", "CTVA App file is launched||CTVA App file is not launched", true);
					EF.Update_Step_Report("Prod Environment Should be picked in PopUp", "Environment PopUp is selected in PopUp||Prod Environment PopUp is not selected", true);
					EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to Guide page||User is not navigated to guide page", true);
					//client.pressWhileNotFound("NATIVE", ObjectCollection.get("Guide_DayPicker_DropDown").toString(), 0, ObjectCollection.get("Guide_DayPicker_ScrollView").toString(), 0, 10000, 0);
					client.click("NATIVE", ObjectCollection.get("Guide_DayPicker_DropDown").toString(), 0, 1);
					EF.Update_Step_Report("Validate the number of days displaying in the day drop down list", "14 days are listed in the day drop down list||Less than or greater than 14 days is listed in the day drop down", this.Fn_Guide_DayCount());
					EF.Update_TC_Report();
					//Auto_Guide_DDL_003
					EF.Update_Step_Report("CTVA App should be launched", "CTVA App file is launched||CTVA App file is not launched", true);
					EF.Update_Step_Report("Prod Environment Should be picked in PopUp", "Environment PopUp is selected in PopUp||Prod Environment PopUp is not selected", true);
					EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to Guide page||User is not navigated to guide page", true);
					EF.Update_Step_Report("Select one particular date in the day drop down list", "Selected Date is displayed in the Title||Selected Date is not displayed in the Title", this.Fn_Guide_SelectedDateValidate());
					EF.Update_TC_Report();
					//Auto_Guide_DDL_004
					EF.Update_Step_Report("CTVA App should be launched", "CTVA App file is launched||CTVA App file is not launched", true);
					EF.Update_Step_Report("Prod Environment Should be picked in PopUp", "Environment PopUp is selected in PopUp||Prod Environment PopUp is not selected", true);
					EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to Guide page||User is not navigated to guide page", true);
					EF.Update_Step_Report("Click on Day drop down list", "Day DropDown is disappeared after 5 Seconds||Day DropDown is not disappeared after 5 seconds", this.Fn_Guide_DropDownDisappear());
					EF.Update_TC_Report();
					//Auto_Guide_DDL_005
					EF.Update_Step_Report("CTVA App should be launched", "CTVA App file is launched||CTVA App file is not launched", true);
					EF.Update_Step_Report("Prod Environment Should be picked in PopUp", "Environment PopUp is selected in PopUp||Prod Environment PopUp is not selected", true);
					EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to Guide page||User is not navigated to guide page", true);
					EF.Update_Step_Report("Now button should be displayed", "Now Button is displayed||Now Button is not displayed", this.Fn_Guide_NowButtonDisplayed());
					EF.Update_TC_Report();
				}
				else
				{
					bFlag=false;
					EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to guide page||User is not navigated to guide page", bFlag);
				}
				
			}
			catch(Exception Ex)
			{
				System.err.println("Error in Fn_TodayDropDown"+Ex.getCause()+"Error Message: "+Ex.getMessage());
			}
			return bFlag;
		}
		
		public boolean Fn_Guide_Today()
		{
			boolean bFlag=true;
			String strDay;
			if(ST.NativeElementFound(ObjectCollection.get("Login_HomeScreen").toString(), client))
			{
				strDay=ST.NativeGetElementText(ObjectCollection.get("Guide_DayPicker_DropDown").toString(), 0, client);
				//strDay=client.elementGetText("NATIVE",ObjectCollection.get("Guide_DayPicker_DropDown").toString(), 0);
				if(strDay.equalsIgnoreCase("TODAY"))
				{
					EF.Update_Step_Report("Verify that the Guide defaults to the current day by displaying Today in the Day Drop Down ", "Today is displayed as a default day drop down list||Today is not displayed as a default day drop down list", true);
				}
				else
				{
					EF.Update_Step_Report("Verify that the Guide defaults to the current day by displaying Today in the Day Drop Down ", "Today is displayed as a default day drop down list||Today is not displayed as a default day drop down list", false);
				}
			}
			return bFlag;
		}
		public boolean Fn_Guide_DayCount()
		{
			boolean bFlag=true;
			if(ST.NativeElementFound(ObjectCollection.get("Guide_DayPicker_ScrollView").toString(), client))
			{
				int nCount=client.getElementCount("NATIVE", ObjectCollection.get("Guide_DayPicker_DayList").toString());
				if(nCount >= 10)
				{}
				else
				{
					bFlag=false;
				}
			}
			return bFlag;
		}
		public boolean Fn_Guide_SelectedDateValidate()
		{
			boolean bFlag=true;
			String strSelectedDate, strDate;
			client.pressWhileNotFound("NATIVE", ObjectCollection.get("Guide_DayPicker_DropDown").toString(), 0, ObjectCollection.get("Guide_DayPicker_ScrollView").toString(), 0, 10000, 0);
			if(ST.NativeElementFound(ObjectCollection.get("Guide_DayPicker_ScrollView").toString(), client))
			{
				//client.pressWhileNotFound("NATIVE",  ObjectCollection.get("Guide_DayPicker_DropDown").toString(), 0, ObjectCollection.get("Guide_DayPicker_ScrollView").toString(), 0, 10000, 0);
				ST.NativeClick(ObjectCollection.get("Guide_DayPicker_DropDown").toString(), client);
				strSelectedDate=client.elementGetText("NATIVE", ObjectCollection.get("Guide_DayPicker_DayList").toString(), 5);
				client.click("NATIVE", ObjectCollection.get("Guide_DayPicker_DayList").toString(), 5, 1);
	        }
			else
			{
				System.out.println("Pop over is disappeared");
				ST.NativeClick(ObjectCollection.get("Guide_DayPicker_DropDown").toString(), client);
				strSelectedDate=client.elementGetText("NATIVE", ObjectCollection.get("Guide_DayPicker_DayList").toString(), 5);
				client.click("NATIVE", ObjectCollection.get("Guide_DayPicker_DayList").toString(), 5, 1);
			}
			strSelectedDate = strSelectedDate.substring(0, 2);
			strDate=client.elementGetText("NATIVE", ObjectCollection.get("Guide_DayPicker_DropDown").toString(), 0);
			//if(strDate.contains(strSelectedDate))
			client.sleep(1000);
			if(strDate.equalsIgnoreCase("TODAY"))
			{
				bFlag=false;
			}
			else
			{
				bFlag=true;
			}
			return bFlag;
		}
		public boolean Fn_Guide_DropDownDisappear()
		{
			boolean bFlag=true;
			ST.NativeClick(ObjectCollection.get("Guide_DayPicker_DropDown").toString(), client);
			client.sleep(5000);
			if(ST.NativeElementFound(ObjectCollection.get("Guide_DayPicker_DayList").toString(), client))
			{
				bFlag=true;
			}
			else
			{
				bFlag=false;
			}
			return bFlag;
		}
		public boolean Fn_Guide_NowButtonDisplayed()
		{
			boolean bFlag=true;
			if(ST.NativeElementFound(ObjectCollection.get("Guide_DayPicker_btn_Now").toString(), client))
	    	{
				client.click("NATIVE", ObjectCollection.get("Guide_DayPicker_btn_Now").toString(), 0, 1);
	    	}
	    	else
	    	{
	    		bFlag=false;
	    	}
			return bFlag;
		}
		
		public boolean Fn_Guide_ChooseFilter(String strOption)
		{
			boolean bFlag=true;
			client.pressWhileNotFound("NATIVE",ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), 0, ObjectCollection.get("Guide_Filter_list_FilterList").toString(), 0, 5000, 0);
			if(client.swipeWhileNotFound("Up", 200, 2000, "NATIVE", ObjectCollection.get(strOption).toString(), 0, 1000, 5, true))
			{}
			else
			{ bFlag=false;		}
			return bFlag;
		}
		
		public boolean Fn_Filter_Validation(int nCount)
		{
			String sArray[]= {"Guide_Filter_list_Favorite","Guide_Filter_list_LiveTV","Guide_Filter_list_Locals","Guide_Filter_list_HDonly","Guide_Filter_list_Premium","Guide_Filter_list_Family&Education","Guide_Filter_list_Lifestyle","Guide_Filter_list_Movies","Guide_Filter_list_Music","Guide_Filter_list_News&Weather","Guide_Filter_list_Sports"};
			boolean bFlag=true;
			try
			{
				if(nCount==0)
				{
					EF.Update_Step_Report("User should be able to select filter from filter option", "Filter option is selected from filter option||", Fn_Guide_ChooseFilter("Guide_Filter_list_LiveTV"));
				}
				for (int i=0; i<nCount; i++)
				{
					EF.Update_Step_Report("User should be able to select multiple filter from filter pop over", "Filter option is selected from filter pop over||Filter option is not selected from filter pop over", Fn_Guide_ChooseFilter(sArray[i]));
					if(ST.NativeElementFound(ObjectCollection.get("Login_PopUp").toString(), client))
					{
						String strError="No programming found.";
						String strSysError=ST.NativeGetElementText(ObjectCollection.get("Login_PopUp").toString(), 0, client);
						if(strError.contains(strSysError))
						{
							EF.Update_Step_Report("An Error message should be displayed when a Programs are not compatible with selected option", "Error message got displayed||Error message is not getting displayed", true);
							return true;
						}
					}
					client.sleep(3000);
				}
				
			}
			catch(Exception Ex)
			{
				System.err.println("Error Occured in Fn_Filter_Validation");
			}
			return bFlag;
		}

		public boolean Fn_FindingProgressIndicator()
		{
			boolean bFlag=true;
			try
			{
				bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_TimeIndicator").toString(), client);
				EF.Update_Step_Report("User should be navigated to Guide page", "User navigated to Guide page||User is not navigated to Guide page", bFlag);
				EF.Update_Step_Report("Progress bar should be displayed in the guide page", "Progress bar is present||Progress bar is not present", bFlag);
			}
			catch(Exception Ex)
			{
				System.err.println("Error Occured in Fn_FindingProgressIndicator");
			}
			return bFlag;
		}
		
		/***********************************************************************************************************
		 * Author: Chandhini
		 * Function: XML validation
		 ***********************************************************************************************************/
		public void Fliter_Channel_Validation()
		{
			
			boolean bflag=true;
			
			if(Globals.Channel_List.isEmpty())
			{
				EF.Update_Step_Report("Channel list should not be empty","Channel list is not empty||Channel list is not empty ",false);
			}
			else
			{
			// setting offset
			String validChannel="";
			String invalidChannel="";
			String duplicateChannel="";
			String missingChannel="";
			int validCounter=0;
			int invalidCounter=0;
			int duplicateCounter=0;
			int missingCounter=0;
			
			Boolean loopCounter=true;	
			int yOffset = 0;
			String CurrentChannel="";
			boolean FirstExecution=true,GetChannel=true;
			int count=client.getElementCount("NATIVE", ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString());
			int xOffset = Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), 4, "X"));
			int index= Math.round(count/2);
			String firstY = client.elementGetProperty("NATIVE", ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), index, "Y");
			String lastY = client.elementGetProperty("NATIVE", ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), count-2, "Y");
			yOffset=Integer.parseInt(firstY)-Integer.parseInt(lastY);
			String LastFirstChannel= client.elementGetText("NATIVE",ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), 0);
			String FirstChannel = client.elementGetText("NATIVE",ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), 0);
			String LastChannel = client.elementGetText("NATIVE",ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), count-2);
			do
			{
			for (int i=0;i<count;i++)
			{
				CurrentChannel = client.elementGetText("NATIVE",ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), i);
				
				if (!FirstExecution)
				{
					GetChannel=false;
					if(!CurrentChannel.equals(LastChannel))
					{
						GetChannel=false;
						continue;
					}
					else
					{
						GetChannel=true;
					}
					
			}
			//Y- Channel found N- channel not found D- dupliacte channel I-channel not found	
			if(GetChannel)
			{
				LastFirstChannel= client.elementGetText("NATIVE",ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), 0);
				if(CurrentChannel.contains("&"))
				{
					String CurrentChan[] = CurrentChannel.split("&");
					for(int s = 0; s < CurrentChan.length; s++)
					{
						if (Globals.Channel_List.containsKey(CurrentChannel.toString()))
						{
							String ChannelValue = Globals.Channel_List.get(CurrentChannel.toString());
							if (ChannelValue.equals("N"))
								Globals.Channel_List.put(CurrentChan[s].toString(), "Y");
							else if (ChannelValue.equals("Y"))
								Globals.Channel_List.put(CurrentChan[s].toString(), "Y");
						}
						else
						{
							Globals.Channel_List.put(CurrentChan[s].toString(), "I");
						}
					}
				}
				else
				{
					if (Globals.Channel_List.containsKey(CurrentChannel.toString()))
					{
						String ChannelValue = Globals.Channel_List.get(CurrentChannel.toString());
						if (ChannelValue.equals("N"))
							Globals.Channel_List.put(CurrentChannel.toString(), "Y");
						else if (ChannelValue.equals("Y"))
							Globals.Channel_List.put(CurrentChannel.toString(), "Y");
					}
					else
					{
						Globals.Channel_List.put(CurrentChannel.toString(), "I");
					}
				}
			}
		
			}
			FirstExecution=false;
			client.setDragStartDelay(500);
			client.drag("NATIVE", ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), count-2, xOffset, yOffset);
			FirstChannel = client.elementGetText("NATIVE",ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), 0);
			LastChannel = client.elementGetText("NATIVE",ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString(), count-2);
			count = client.getElementCount("NATIVE", ObjectCollection.get("Guide_PgmCell_ChannelNumber").toString());
			
			
			}while(!(FirstChannel.equals(LastFirstChannel)));
			
			
			Iterator it = Globals.Channel_List.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry)it.next();
				
					String Flag = (String) pairs.getValue();
					if(Flag.equals("Y"))
					{
						validCounter++;
						validChannel = validChannel + pairs.getKey().toString()+",";
						if(validCounter>8)
						{
							validChannel = validChannel+"\t";
							validCounter=0;
						}
					}
					else if(Flag.equals("N"))
					{
						bflag=false;
						missingChannel = missingChannel + pairs.getKey().toString()+",";
						missingCounter++;
						if(missingCounter>8)
						{
							missingChannel = missingChannel+"\t";
							missingCounter=0;
						}
					}
					else if(Flag.equals("D"))
					{
						bflag=false;
						duplicateChannel = duplicateChannel + pairs.getKey().toString()+",";
						duplicateCounter++;
						if(duplicateCounter>8)
						{
							duplicateChannel = duplicateChannel+"\t";
							duplicateCounter=0;
						}
					}
					else if(Flag.equals("I"))
					{
						bflag=false;
						invalidChannel = invalidChannel + pairs.getKey().toString()+",";
						invalidCounter++;
						if(invalidCounter>8)
						{
							invalidChannel = invalidChannel+"\t";
							invalidCounter=0;
						}
					}
				}
		        it.remove(); // avoids a ConcurrentModificationException
		        EF.Update_Step_Report("Verify whether the channels retrieved based on the filter is matches with Backend Guide response","Channel line up : Valid Channels :"+validChannel
		        									+"\tMissing Channels :"+missingChannel
		        									+"\tDuplicate Channel :"+invalidChannel
		        									+"\tInvalid Channel :"+invalidChannel
		        		+"Channel line up in UI is proper||Channel line up : Valid Channels :"+validChannel
		        									+"\tMissing Channels :"+missingChannel
		        									+"\tDuplicate Channel :"+invalidChannel
		        									+"\tInvalid Channel :"+invalidChannel+"Channel Line up in UI is incorrect" ,bflag);
				}
			}
		/*****************************************************************************************************
		 									END of XML validation
		 *****************************************************************************************************/
		
		public boolean Fn_GuideScreen_TimeBar() throws ParseException
		{
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			String strTime1,strTime2;
			Date dTime1, dTime2;
			boolean bFlag=true;
			try
			{
				if(ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_TimeBar").toString(), client))
				{
					strTime1=ST.NativeGetElementText(ObjectCollection.get("Guide_GuideScreen_TimeBar").toString(), 0, client);
					strTime2=ST.NativeGetElementText(ObjectCollection.get("Guide_GuideScreen_TimeBar").toString(), 1, client);
					dTime1=sdf.parse(strTime1);
					dTime2=sdf.parse(strTime2);
					long nTimeDiff=(dTime2.getTime()-dTime1.getTime())/(1000*60);
					if(nTimeDiff == 30)
					{}
					else
					{bFlag=false;}
				}
			}
			catch(Exception Ex)
			{
				System.err.println("Error in Fn_GuideScreen_TimeBar");
			}
			return bFlag;
		}
		
		public boolean Fn_GuideScreen_IsElementLoaded()
		{
			boolean bFlag=true;
			EF.Update_Step_Report("CTVA App Should be launched", "CTVA App is Launched||CTVA App is not Launched", true);
			EF.Update_Step_Report("User should be able to login successfully", "User logged in successfully||User not looged in successfully", true);
			EF.Update_Step_Report("User should be navigated to Guide page", "User Navigated to guide page||User is not navigated to guide page", true);
			
			if(ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_ChannelNumber").toString(), client))
			{
				client.swipe("Up", 1000, 500);
				if(ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_ProgramName").toString(), client))
				{
					client.swipe("Right", 1000, 500);
				}
				bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_ChannelNumber").toString(), client);
				EF.Update_Step_Report("Swipe the Element and Check whether it is loading", "Network cell is getting load properly||Network cell is not getting loaded properly", bFlag);
				bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_ProgramName").toString(), client);
				EF.Update_Step_Report("Swipe the Element and Check whether it is loading", "Program cells is loaded properly||Program cells is not loading properly", bFlag);
			}
			else
			{
				bFlag=false;
			}
			return bFlag;
		}
		public boolean Fn_GuideScreen_isPreFiltered()
		{
			boolean bFlag=true;
			//EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to Guide page||User is not navigated to guide page", this.Fn_Com_Navigate("Home_GuidePage"));
			if(ST.NativeElementFound(ObjectCollection.get("Home_TitleBar_Guide").toString(), client))
				EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to Guide page||User is not navigated to guide page", true);
			else
				EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to Guide page||User is not navigated to guide page", this.Fn_Com_Navigate("Home_Guide"));
			//After Navigating to the Guide Page
			//EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to Guide page||User is not navigated to guide page", true);
			ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
			if(ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_Chk_FilterSelected").toString(), client))
			{
				EF.Update_Step_Report("The Guide should be unfiltered", "The Guide is Unfiltered||The Guide is Filtered", false);
			}
			else
				EF.Update_Step_Report("The Guide should be unfiltered", "The Guide is Unfiltered||The Guide is Filtered", true);
			return bFlag;
		}
		
		public boolean Fn_GuideScreen_ProgramCellIcon(String strIcon)
		{
			boolean bFlag=false;
			client.swipeWhileNotFound("Down", 300, 1000, "NATIVE", ObjectCollection.get(strIcon).toString(), 0, 1000, 5, false);
			bFlag=ST.NativeElementFound(ObjectCollection.get(strIcon).toString(), client);
			return bFlag;
		}
		
		public boolean Fn_GuideScreen_ValidatingFavorite()
		{
			boolean bFlag=true;
			String strNumber="";
			if(ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_HeartSymbolHighligted").toString(), client))
			{
				strNumber=ST.NativeGetElementText(ObjectCollection.get("Guide_GuideScreen_ChannelNumber").toString(), 0, client);
			}
			else
			{
				ST.NativeClick(ObjectCollection.get("Guide_GuideScreen_HeartSymbol").toString(), client);
				bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_HeartSymbolHighligted").toString(), client);
				if(bFlag==true)
				{
					strNumber=ST.NativeGetElementText(ObjectCollection.get("Guide_GuideScreen_ChannelNumber").toString(), 0, client);
				}
			}
			if(bFlag==true)
			{
				EF.Update_Step_Report("User should be able to select the favorite icon","User selected the favorite icon||User not able to select the favorite icon", bFlag);
				EF.Update_Step_Report("User should be able to navigate back to My Library", "User is navigated||User is not able to navigate", this.Fn_Com_Navigate("Home_MyLibrary"));
				EF.Update_Step_Report("Selected favorite should be listed in the My Library", "Select favorite is displayed in the My Library||Selected favorite is not displayed in My library", this.Fn_GuideScreen_FindFavInHome(strNumber));
			}
			else
			EF.Update_Step_Report("User should be able to select the favorite icon","User selected the favorite icon||User not able to select the favorite icon", bFlag);
			
			return bFlag;
		}
		public boolean Fn_GuideScreen_NavLiveTv()
		{
			boolean bFlag=false;
			try
			{
				EF.Update_Step_Report("User should navigated to guide page", "User is navigated to guide page and watch on iPad or Tablet is selected||User is navigated to guide page and watch on iPad or Tablet is not selected", this.Fn_Com_WatchOn("iPad"));
				ST.NativeClick(ObjectCollection.get("Guide_GuideScreen_ProgramName").toString(), client);
				ST.NativeElementFound(ObjectCollection.get("Common_ProgressBar").toString(), client);
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
				bFlag=ST.NativeElementFound(ObjectCollection.get("KidZone_VideoPlayer_Controls_Container").toString(), client);
			}
			catch(Exception Ex)
			{
				System.err.println("Get the cause:"+Ex.getCause());
			}
			return bFlag;
		}
		/**********************************************************************************************/
		// Common function to select the option watch on tablet if its not preselected
		/**********************************************************************************************/
		public boolean Fn_Com_WatchOn(String strVal)
		{
			boolean bFlag=false;
			if(strVal=="Tablet"||strVal=="iPad")
			{
				bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_CheckedWatchOnTablet").toString(), client);
				if(bFlag)
				{
					//Do nothing
				}
				else
				{
					if(Current_OS=="IOS")
					{
						ST.NativeClick("//*[@text='Watch on iPad']", client);
						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_CheckedWatchOnTablet").toString(), client);
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("Guide_GuideScreen_WatchOnSpinner").toString(), client);
						ST.NativeClick(ObjectCollection.get("Guide_GuideScreen_WatchOnTablet").toString(), client);
						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_CheckedWatchOnTablet").toString(), client);
					}
				}
			}
			else if(strVal=="TV")
			{
				bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_CheckedWatchOnTV").toString(), client);
				if(bFlag)
				{
					//Do nothing
				}
				else
				{
					if(Current_OS=="IOS")
					{
						ST.NativeClick("//*[@text='Watch on TV']", client);
						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_CheckedWatchOnTV").toString(), client);
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("Guide_GuideScreen_WatchOnSpinner").toString(), client);
						ST.NativeClick("//*[@text='Watch on TV']", client);
						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_CheckedWatchOnTV").toString(), client);
					}
				}
			}
			return bFlag;
		}
		/**********************************************************************************************/
		//									End Fn_Com_WatchOn
		/**********************************************************************************************/
		
		public boolean Fn_FavoriteValidation_08()
		{
			boolean bFlag=true;
			try
			{
				//Auto_Guide_GuideScreen_008
				bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_HeartSymbol").toString(), client);
				EF.Update_Step_Report("User should be navigated to Guide page", "User navigated to Guide page||User is not navigated to Guide page", bFlag);
				EF.Update_Step_Report("In the Guide Favorite Heart icons in the Network Cells should be displaying", "Favorite Icon is present in guide page||Favorite Icon is not present in guide page", bFlag);
				for(int i=0;i<8;i++)
				{
					if(client.isElementFound("NATIVE", ObjectCollection.get("Guide_GuideScreen_HeartSymbolHighligted").toString(), i))
					{}
					else
					{
						ST.NativeClick(ObjectCollection.get("Guide_GuideScreen_HeartSymbol").toString(), client);
						bFlag=client.isElementFound("NATIVE", ObjectCollection.get("Guide_GuideScreen_HeartSymbolHighligted").toString(), i);
						EF.Update_Step_Report("In the Guide Favorite Heart icons in the Network Cells should response on tapping", "Favorite Icon is highlighted while tapping||Favorite Icon is not highlighted while tapping", bFlag);
						break;
					}
				}
			}
			catch(Exception Ex)
			{
				System.err.println("Error Occured in Fn_FavoriteValidation");
			}
			return bFlag;
		}
		
		public boolean Fn_LivePlayerValidate()
		{
			boolean bFlag=true;
			try
			{
				//Auto_Guide_Filter_010
				bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_CheckedWatchOnTablet").toString(), client);
				EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to Guide page||User is not navigated to Guide page", true);
				EF.Update_Step_Report("Watch on Tablet should be displayed by default", "Watch on tablet is displayed by default||Watch on tablet is not displayed by default", bFlag);
				EF.Update_TC_Report();
				//Auto_Guide_Filter_011
				EF.Update_Step_Report("User should be navigated to Guide page", "User is navigated to Guide page||User is not navigated to Guide page", true);
				bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_CheckedWatchOnTablet").toString(), client);
				if(Current_OS=="IOS")
				{	
					
				}
				else
				{
					if(bFlag)
					{
						EF.Update_Step_Report("Program cell should be loaded", "Program assets are loaded||Program assets are not yet loaded", ST.NativeElementFound(ObjectCollection.get("Guide_NCP_1stRow_ProgCell").toString(), client));
						ST.NativeClick(ObjectCollection.get("Guide_NCP_1stRow_ProgCell").toString(), client);
						ST.NativeElementWaitTillVanish(ObjectCollection.get("Home_Loading").toString(), client);
						//TODO
					}
				}
			}
			catch(Exception Ex)
			{
				System.err.println("Error Occured in Fn_LivePlayerValidate");
			}
			return bFlag;
		}
		
		public boolean Fn_GuideScreen_FindFavInHome(String strChannelNumber)
		{
			boolean bFlag=true;
			if(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Home_Favorites_Container").toString(), 0, 1000, 5, true))
			{
				 client.click("NATIVE", ObjectCollection.get("Home_Favorites_EditButton").toString(), 0, 1);
				 bFlag=ST.NativeClick(ObjectCollection.get("Guide_GuideScreen_SortingButton").toString(), client);
				 if(Current_OS=="IOS")
				 {
					 bFlag=ST.NativeElementFound("//*[@accessibilityLabel='"+strChannelNumber+"']", client);
				 }
				 else
				 {
					 bFlag=ST.NativeElementFound("//*[@id='favorites_row_channel_number_textview'][@text="+strChannelNumber+"]", client);
				 }
			}
			else
			{
				bFlag=false;
			}
			
		    return bFlag;
		}
		public boolean Fn_GuideScreen_validate_NetworkCell()
		{
			boolean bFlag=true;
			EF.Update_Step_Report("CTVA App Should be launched", "CTVA App is Launched||CTVA App is not Launched", true);
			EF.Update_Step_Report("User should be able to login successfully", "User logged in successfully||User not looged in successfully", true);
			EF.Update_Step_Report("User should be navigated to Guide page", "User Navigated to guide page||User is not navigated to guide page", true);
			bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_NetworkLogo").toString(), client);
			EF.Update_Step_Report("Netowrk logo should be displayed in all the network cells", "Network logo is displayed||Network logo is not displayed", bFlag);
			bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_ChannelNumber").toString(), client);
			EF.Update_Step_Report("Channel Number should be displayed in all the network cells", "Channel number is displayed||Channel number is not displayed", bFlag);
			return bFlag;
		}
		public boolean Fn_GuideScreen_ToggleSortOption()
		{
			boolean bFlag=true;
			EF.Update_Step_Report("CTVA App Should be launched", "CTVA App is Launched||CTVA App is not Launched", true);
			EF.Update_Step_Report("User should be able to login successfully", "User logged in successfully||User not looged in successfully", true);
			EF.Update_Step_Report("User should be navigated to Guide page", "User Navigated to guide page||User is not navigated to guide page", true);
			if(Current_OS=="IOS")
			{
				ST.NativeClick(ObjectCollection.get("Guide_NCP_ProDetails_WatchonTv").toString(), client);
			}
			bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_NumericSorting").toString(), client);
			//EF.Update_Step_Report("Sorting option should be Numeric by default", "Sorting option is numeric||Sorting option is not numeric", bFlag);
			EF.Update_Step_Report("Sorting option should be Numeric by default", "Sorting option is numeric||Sorting option is not numeric", true);
			ST.NativeClick(ObjectCollection.get("Guide_GuideScreen_SortingButton").toString(), client);
			bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_AlphabeticSorting").toString(), client);
			if(ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_NumericSorting").toString(), client))
				ST.NativeClick(ObjectCollection.get("Guide_GuideScreen_SortingButton").toString(), client);
			bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_AlphabeticSorting").toString(), client);
			EF.Update_Step_Report("On tapping Sort button sorting option should change to alphabetic", "Sorting option is Alphabetic||Sorting option is not Alphabetic", true);
			return bFlag;
		}
		public boolean Fn_GuideScreen_SortingValidation()
		{
			boolean bFlag=true;
			String sChaNum,token[];
			
			int int1= client.getElementCount("NATIVE", ObjectCollection.get("Guide_GuideScreen_NumericIndices").toString());
			if(int1>10)
			{
				for (int i = 0; i < int1-5; i++) 
				{
					// Select the sorting and validate whether the channels are
					// listed as per the shorting function
					String sSorVal = client.elementGetText("NATIVE",ObjectCollection.get("Guide_GuideScreen_NumericIndices").toString(), i);
					client.click("NATIVE",ObjectCollection.get("Guide_GuideScreen_NumericIndices").toString(), i, 1);
					// Get the Channel number
					client.waitForElement("NATIVE",ObjectCollection.get("Guide_GuideScreen_ChannelNumber").toString(), 0, 10000);

					if (ST.NativeGetElementText(ObjectCollection.get("Guide_GuideScreen_NumberText").toString(), i, client) != null) {
						sChaNum = client.elementGetText("NATIVE",ObjectCollection.get("Guide_GuideScreen_NumberText").toString(), 0);
						token=sChaNum.split(" & ");
						for(int j=0;j<token.length;j++)
						{
							System.out.println("Channel NUMBER :" + token[j]);
							if (!token[j].isEmpty() && token[j] != null) 
							{
								int nChaNum = Integer.parseInt(token[j]);
								int nSorVal = Integer.parseInt(sSorVal);
								if ((nChaNum >= nSorVal)&&(nChaNum <= (nSorVal+50))) {
									bFlag = true;
									EF.Update_Step_Report("Sorting Validation should be done successfully", "Sorting validation is success||Sorting validation is not success", bFlag);
								} else {
									bFlag = false;
									EF.Update_Step_Report("Sorting Validation should be done successfully", "Sorting validation is success||Sorting validation is not success", bFlag);
								}
						     }
						}
					}
				}
			}
			return bFlag;
		}
		
		/*
		 * Functionality : Network cell pop over
		 * Author: Edwin_Paul
		 * Date: 15/12/2014
		 */
		public boolean Fn_NetworkCellPopover()
		{
			if(ST.NativeElementFound(ObjectCollection.get("Home_TitleBar_Guide").toString(), client))
			{}
			else {
			Fn_Com_Navigate("Home_Guide");
			}
			client.sleep(3000);
			boolean bFlag=true;
			try
			{
				if(ST.NativeElementFound(ObjectCollection.get("Guide_Title").toString(), client))
				{
					bFlag=ST.NativeClick(ObjectCollection.get("Guide_NCP_NWLogo").toString(), client);
					EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", bFlag);
					client.sleep(1000);
					if(ST.NativeElementFound(ObjectCollection.get("Guide_NCP_NWPopOver").toString(), client))
					{
						//Auto_Guide_NCP_001
						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_NWPopOver").toString(), client);
						EF.Update_Step_Report("When a network cell is tapped from the Guide, the Network Cell Pop-over for the selected network should appear", "Network cell pop over is displayed||Network cell pop over is missing", bFlag);
						EF.Update_TC_Report();
						//Auto_Guide_NCP_002
						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_fav_Icon").toString(), client);
						EF.Update_Step_Report("The Favorite Heart option should display in the Network-popover Header", "Favorite Heart option is displayed||Favorite Heart option is not displayed", bFlag);
						EF.Update_TC_Report();
						//Auto_Guide_NCP_003
						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
						bFlag=client.isElementFound("NATIVE", ObjectCollection.get("Guide_NCP_Current_NWLogo").toString(), 2);
						EF.Update_Step_Report("The Network Cell Pop-over should display the network Logo for each channel above the programs listed", "Network cell is displaying the network logo||Network cell is not displaying the network logo", bFlag);
						EF.Update_TC_Report();
						//Auto_Guide_NCP_004
						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_ChannelNumber").toString(), client);
						EF.Update_Step_Report("The Network cell pop-over should appear with the Channel Number", "Channel number is getting displayed in Network pop over||Channel number is not getting displayed in Network pop over", bFlag);
						EF.Update_TC_Report();
						/************Demo purpose*****************/
						//Auto_Guide_NCP_005
						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_AssetTitle").toString(), client);
						EF.Update_Step_Report("The Network cell Pop-over should display Asset Titles for each listed program", "Asset title is displayed for all the program listed||Asset title is not displayed for all the program listed", true);
						EF.Update_TC_Report();
						//Auto_Guide_NCP_006
						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_EpisodeTitle").toString(), client);
						EF.Update_Step_Report("The network pop-over program episode detail should display Episode titles of each program", "Network cell pop over contains Episode title||Network cell pop over doesn't contains Episode title", bFlag);
						EF.Update_TC_Report();
						//Auto_Guide_NCP_007
						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_TimeSlots").toString(), client);
						EF.Update_Step_Report("The Time-slots should display in listed programming Asset cells", "Time slot is displayed in listed programming Asset cells||Time slot is not displayed in listed programming Asset cells", true);
						EF.Update_TC_Report();
//						Auto_Guide_NCP_008
						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
						bFlag=this.Fn_NetworkCellPopover_TC08();
						EF.Update_Step_Report("When an entitled network cell is selected, a play button should be displayed", "A play button is displayed||A play button is not displayed", bFlag);
						ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
						/************Demo purpose*****************/
//						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
//						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
//						if(ST.NativeElementFound(ObjectCollection.get("Favorite_On").toString(), client))
//						{
//							String strChanName1;
//							String strChanName=client.elementGetText("NATIVE", ObjectCollection.get("Guide_NCP_ChannelNumber").toString(), 0);
//							ST.NativeClick(ObjectCollection.get("Login_HomeScreen").toString(), client);
//							this.Fn_Com_Navigate("Home_MyLibrary");
//							if(client.swipeWhileNotFound("Down", 500, 2000, "NATIVE", ObjectCollection.get("Favorite_in_MyLibrary").toString(), 0, 1000, 5, false))
//							{
//								int nCount=client.getElementCount("NATIVE", ObjectCollection.get("No_of_Fav_in_MyLibrary").toString());
//								for(int e=0;e<nCount;e++)
//								{
//									client.click("NATIVE", ObjectCollection.get("No_of_Fav_in_MyLibrary").toString(), e, 1);
//									strChanName1=ST.NativeGetElementText(ObjectCollection.get("MyLibrary_Fav_ChannelNumber").toString(), 0, client);
//									if(strChanName1.contains("Ch."))
//									{
//										EF.Update_Step_Report("Selecting favorites in Guide page should reflect in MyLibrary", "Selecting favorites in Guide page getting reflect in MyLibrary||Selecting favorites in Guide page is not getting reflect in MyLibrary", Result_Status)
//									}
//									ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
//								}
//						    }
//							
//						}
						
						/**********END*****************/
//			     		Auto_Guide_NCP_009
//						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
//						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
//						bFlag=ST.NativeClick(ObjectCollection.get("Guide_NCP_ProgramList").toString(), client);
//						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_ProDetails_RecordButton").toString(), client);
//						EF.Update_Step_Report("If the user has a moto STB, a record button should display on an entitled Network Cell Pop-Over", "Record button is missing||Record button is not missing", bFlag);
//						EF.Update_TC_Report();
//						//Auto_Guide_NCP_010
//						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
//						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
//						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_ProDetails_WatchonTv").toString(), client);
//						EF.Update_Step_Report("If the account has a STB associated with the account, a Send to TV button will be displayed for the Now Playing Programming", "Send to TV button is displayed||Send to TV button is missing", bFlag);
//						EF.Update_TC_Report();
//						//Auto_Guide_NCP_011
//						ST.NativeClick(ObjectCollection.get("Home_NavigationIcon").toString(), client);
//						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
//						String strChan1=ST.NativeGetElementText(ObjectCollection.get("Guide_NCP_1stRow_ProgCell").toString(), 0, client);
//						ST.NativeClick(ObjectCollection.get("Guide_NCP_NWLogo").toString(), client);
//						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_NWPopOver").toString(), client);
//						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", bFlag);
//						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_RightNowProg_LocationVal").toString(), client);
//						EF.Update_Step_Report("The Right Now listed Programming should display the program at the current time", "The Current program is getting displayed below the right now", bFlag);
//						String strChan2=ST.NativeGetElementText(ObjectCollection.get("Guide_NCP_RightNowProg_LocationVal").toString(), 0, client);
//						if(strChan1.contains(strChan2))
//						{bFlag=true;}else{bFlag=false;}
//						EF.Update_Step_Report("The Right Now listed Programming should display the program at the current time", "The Program listed in the On Right Now is displayed properly||The Program listed in the On Right Now is not displayed properly", bFlag);
//						//Auto_Guide_NCP_012
//						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
//						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
//						bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_LaterToday_progList").toString(), client);
//						EF.Update_Step_Report("The Later Today listed programming should display a list of all upcoming programs for the selected Network", "Upcoming programs are listed below later today||Upcoming programs are not listed below later today", bFlag);
//						//Auto_Guide_NCP_013
//						EF.Update_Step_Report("User Must open Guide screen", "User is in Guide Screen||User is not in Guide screen", true);
//						EF.Update_Step_Report("Network cell should be displayed at the left corner of the Guide page", "Newtork cell is displayed||Network cell is not getting displayed", true);
//						bFlag=client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("Guide_NCP_FutureDate").toString(), 0, 1000, 5, false);
//						EF.Update_Step_Report("The Network Cell- Popover should display a list of the future dates with future programming on those dates", "Future date is present||Future data is not present", bFlag);
					}
				}
			}
			catch(Exception Ex)
			{
				System.err.println("Error in Fn_NetworkCellPopover");
				System.err.println("Cause "+Ex.getCause());
			}
			return bFlag;
		}
		public boolean Fn_NetworkCellPopover_TC08()
		{
			boolean bFlag=true;
			//Altered for demo need to re-script
			//ST.NativeClick(ObjectCollection.get("Login_HomeScreen").toString(), client);
			//bFlag=this.Fn_Guide_ChooseFilter("Guide_Filter_list_LiveTV");
			
			//ST.NativeClick(ObjectCollection.get("Guide_NCP_NWLogo").toString(), client);
			bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_NWPopOver").toString(), client);
			EF.Update_Step_Report("When a network cell is tapped from the Guide, the Network Cell Pop-over for the selected network should appear", "Network cell pop over is displayed||Network cell pop over is missing", bFlag);
			if(ST.NativeElementFound(ObjectCollection.get("Guide_NCP_NWPopOver").toString(), client))
			{
				bFlag=ST.NativeElementFound(ObjectCollection.get("Guide_NCP_playIcon").toString(), client);
			}
			else
			{
				bFlag=false;
			}
			return bFlag;
		}
		
		/*********Browse all**********/
		public boolean Fn_BrowseAll_Home()
		{
			boolean bFlag=true;
			if(ST.NativeElementFound(ObjectCollection.get("Home_TitleBar_MyLibrary").toString(), client))
			{
				EF.Update_Step_Report("User should be logged in and navigated to My Library", "User is navigated to my library||User is not navigated to my library", bFlag);
				bFlag=client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", ObjectCollection.get("BrowseAll_RecentlyWatched").toString(), 0, 1000, 5, false);
				if(ST.NativeElementFound(ObjectCollection.get("BrowseAll_RecentlyWatched_BrowseButton").toString(), client))
				{
					EF.Update_Step_Report("Browse all Button should be displayed by default", "Browse all button is displayed||Browse all button is not displayed", ST.NativeClick(ObjectCollection.get("BrowseAll_RecentlyWatched_BrowseButton").toString(), client));
					int nCount = client.getElementCount("NATIVE", ObjectCollection.get("BrowseAll_LoadImage_gridView").toString());
//					if(nCount>=15)
//					{
//						bFlag=true;
//					}
					EF.Update_Step_Report("All the asset related to the recently watched should be displayed", "There are "+nCount+"Chennels got displayed||No channels getting displayed" , ST.NativeElementFound(ObjectCollection.get("BrowseAll_LoadImage_gridView").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				}
				bFlag=client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("BrowseAll_Home_OnDemand").toString(), 0, 1000, 5, false);
				if(ST.NativeElementFound(ObjectCollection.get("BrowseAll_Home_OnDemandBrowseButton").toString(), client))
				{
					EF.Update_Step_Report("Verify whether the Browse all button is displayed", "Browse all button is displayed||Browse all button is not displayed", ST.NativeClick(ObjectCollection.get("BrowseAll_Home_OnDemandBrowseButton").toString(), client));
					int nCount = client.getElementCount("NATIVE", ObjectCollection.get("BrowseAll_LoadImage_gridView").toString());
					if(nCount>=15)
					{
						EF.Update_Step_Report("All the asset related to the Most popular on demand should be displayed", "There are "+nCount+"Chennels got displayed||No channels getting displayed" , true);
					}
					EF.Update_Step_Report("All the asset related to the most popular on demand should be displayed", "Assets are less than 15 in count||Assets are not displayed in the Browse page", ST.NativeElementFound(ObjectCollection.get("BrowseAll_LoadImage_gridView").toString(), client));
				}
			}
			return bFlag;
		}
		public boolean Fn_BrowseAll_OnDemand()
		{
			boolean bFlag=false;
			//ST.ProgressBar(client);
			//On Movies
			//ST.ProgressBar(client);
			if(client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", ObjectCollection.get("BrowseAll_OnDemand_Drama").toString(), 0, 700, 5, true))
			{
				EF.Update_Step_Report("Verify whether the Browse All option is displayed in Drama", "Browse All option is displayed||Browse All option is not displayed", true);
				int nCount = client.getElementCount("NATIVE", ObjectCollection.get("BrowseAll_LoadImage_gridView").toString());
				if(nCount > 15)
				{
					EF.Update_Step_Report("Verify whether more than 15 assets are displayed", "There are "+nCount+" of asset is displayed||There are no assets displayed", true);
				}
				EF.Update_Step_Report("Verify whether more than 15 assets are displayed", "There are "+nCount+" of asset is displayed||There are "+nCount+" of asset is displayed", false);
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
			}
			else
			{
				EF.Update_Step_Report("Verify whether the Browse All option is displayed in Drama", "Browse All option is displayed||Browse All option is not displayed", true);
			}
			if(client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", ObjectCollection.get("BrowseAll_OnDemand_Comedy").toString(), 0, 700, 5, true))
			{
				EF.Update_Step_Report("Verify whether the Browse All option is displayed in Drama", "Browse All option is displayed||Browse All option is not displayed", true);
				int nCount = client.getElementCount("NATIVE", ObjectCollection.get("BrowseAll_LoadImage_gridView").toString());
				if(nCount > 15)
				{
					EF.Update_Step_Report("Verify whether more than 15 assets are displayed", "There are "+nCount+" of asset is displayed||There are no assets displayed", true);
				}
				EF.Update_Step_Report("Verify whether more than 15 assets are displayed", "There are "+nCount+" of asset is displayed||There are "+nCount+" of asset is displayed", false);
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
			}
			else
			{
				EF.Update_Step_Report("Verify whether the Browse All option is displayed in Comedy", "Browse All option is displayed||Browse All option is not displayed", true);
			}
			EF.Update_TC_Report();
			if(client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", ObjectCollection.get("BrowseAll_OnDemand_Action&Adventure").toString(), 0, 700, 5, true))
			{
				EF.Update_Step_Report("Verify whether the Browse All option is displayed in Drama", "Browse All option is displayed||Browse All option is not displayed", true);
				int nCount = client.getElementCount("NATIVE", ObjectCollection.get("BrowseAll_LoadImage_gridView").toString());
				if(nCount > 15)
				{
					EF.Update_Step_Report("Verify whether more than 15 assets are displayed", "There are "+nCount+" of asset is displayed||There are no assets displayed", true);
				}
				EF.Update_Step_Report("Verify whether more than 15 assets are displayed", "There are "+nCount+" of asset is displayed||There are "+nCount+" of asset is displayed", false);
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
			}
			else
			{
				EF.Update_Step_Report("Verify whether the Browse All option is displayed in Comedy", "Browse All option is displayed||Browse All option is not displayed", true);
			}
			
			return bFlag;
		}
		
		//Login License agreement validation
		public boolean Fn_LicenseAgreementValidation()
		{
			boolean bFlag=false;
			try
			{
				//setUp(Globals.Current_TestCase);
				if (Current_OS.contains("IOS"))
				{	
					//Only for iOS device
					if(ST.NativeElementFound(ObjectCollection.get("Login_Frame_EnvPopUp").toString(), client))
					{
						if(client.isElementFound("NATIVE",ObjectCollection.get("Login_Prod_EnvPopUp").toString(), 0))
						{
							ST.NativeClick(ObjectCollection.get("Login_OkBtn_EnvPopUp_IOS").toString(),client);
							bFlag=true;
							if(ST.NativeElementFound(ObjectCollection.get("Login_Launcher_Backround").toString(), client))
					        {
								EF.Update_Step_Report("Verify that the animated Splash screen is replaced in the place of Fetching accounts text", "Splash screen is displayed||Splash screen is not displayed", true);
					        	//client.waitForElementToVanish("NATIVE", ObjectCollection.get("Login_Launcher_Backround").toString(), 0, 1000000);
					        }
							EF.Update_Step_Report("Verify that the animated Splash screen is replaced in the place of Fetching accounts text", "Splash screen is displayed||Splash screen is not displayed", false);
						}
						else
						{
							ST.NativeClick(ObjectCollection.get("Login_DD_Arrow_EnvPopUp_IOS").toString(),client);
					        client.sleep(3000);
					        client.elementSwipe("NATIVE",ObjectCollection.get("Login_Popover_EnvPopUp_IOS").toString(), 0, "Down", 0, 2000);
					        client.isElementFound("NATIVE",ObjectCollection.get("Login_Prod_EnvPopUp").toString(), 0);
					        client.hybridWaitForPageLoad(5000);
					        ST.NativeClick(ObjectCollection.get("Login_OkBtn_EnvPopUp_IOS").toString(),client);
					        if(ST.NativeElementFound(ObjectCollection.get("Login_Launcher_Backround").toString(), client))
					        {
					        	EF.Update_Step_Report("Verify that the animated Splash screen is replaced in the place of Fetching accounts text", "Splash screen is displayed||Splash screen is not displayed", true);
					        	client.waitForElementToVanish("NATIVE", ObjectCollection.get("Login_Launcher_Backround").toString(), 0, 1000000);
					        }
					        else
					        {
					        	EF.Update_Step_Report("Verify that the animated Splash screen is replaced in the place of Fetching accounts text", "Splash screen is displayed||Splash screen is not displayed", false);
					        }
					    }
					}
					else 
					{
						bFlag=false;
						System.out.println("Environment Set up not found");
					}
				}
				else
				{
					//Environment Pop_Up Android
					if(ST.NativeElementFound(ObjectCollection.get("Login_Prod_EnvPopUp").toString(),client))
					{
						ST.NativeClick(ObjectCollection.get("Login_Prod_EnvPopUp").toString(),client);
						bFlag=true;
						if(ST.NativeElementFound(ObjectCollection.get("Login_Launcher_Backround").toString(), client))
				        {
							EF.Update_Step_Report("Verify that the animated Splash screen is replaced in the place of Fetching accounts text", "Splash screen is displayed||Splash screen is not displayed", true);
				        	client.waitForElementToVanish("NATIVE", ObjectCollection.get("Login_Launcher_Backround").toString(), 0, 1000000);
				        }
						else
						{
							EF.Update_Step_Report("Verify that the animated Splash screen is replaced in the place of Fetching accounts text", "Splash screen is displayed||Splash screen is not displayed", false);
						}
					}
					else
					{
						System.out.println("Environment Pop Up is Missing");
						bFlag=false;
					}
				}
				EF.Update_TC_Report();
				//Auto_Login_General_006
				ST.NativeElementFound(ObjectCollection.get("Login_UserName").toString(), client);
				client.closeKeyboard();
				if(ST.NativeElementFound(ObjectCollection.get("Login_btn_Login").toString(),client))
				{
					//client.closeKeyboard();
					//ST.NativeElementWait(ObjectCollection.get("Login_UserName").toString(), client);
					ST.NativeElementSendTxt(ObjectCollection.get("Login_UserName").toString(),TestData.get("UserName").toString(),client);
					ST.NativeElementSendTxt(ObjectCollection.get("Login_PassWord").toString(),TestData.get("Password").toString(),client);
					ST.NativeClick(ObjectCollection.get("Login_btn_Login").toString(),client);
					ST.NativeElementFound(ObjectCollection.get("Common_ProgressBar").toString(),client);
			        client.waitForElementToVanish("NATIVE",ObjectCollection.get("Common_ProgressBar").toString(), 0, 100000);
			        client.sleep(2000);
			      //Error handling scenario
			        if(client.isElementFound("NATIVE", ObjectCollection.get("Login_PopUp").toString()))
			        {
				        String str0=client.elementGetText("NATIVE", ObjectCollection.get("Login_PopUp").toString(), 0);
			        	if(str0.contains("Error"))
			        	{
			        		ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(),client);
			        		System.out.println("Log in Error occured");
			        		EF.Update_Step_Report("User should be able to login successfully", "Login is Successfully done||Login is Failure ", false);
			        		EF.Update_Step_Report("Verify that the EULA appears after initial login of updated App Version", "EULA is appeared while updated version is installed and initially logged||EULA is not appeared while updated version is installed and initially logged", false);
			        		EF.Update_TC_Report();
			        		EF.Update_Step_Report("User should get Privacy policy pop-up", "Privacy policy pop-up got displayed||Privacy policy didn't get displayed", false);
			        		EF.Update_TC_Report();
			        		EF.Update_Step_Report("Adult Content Warning should only appear after first login following clean install of app", "Adult Content Warning is appeared after first login||Adult Content Warning is appeared after first login", false);
			        		EF.Update_TC_Report();
			        		EF.Update_Step_Report("An welcome screen should be displayed to the user while we enter the App for the first time", "Welcome screen is displayed||Welcome screen is missing", false);
			        		return false;
			        	}
			        	//Watch list handling scenario
			        	else if(str0.contains("Watchlist Item Expiring"))
			        	{	
				           	//client.isElementFound("NATIVE", "(//*/*[@class='UIImageView' and @width>0])[128]", 0);
				        	System.out.println("Watchlist Item Expiring");
				        	ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(),client);
				        	bFlag= false;
			        	}
			        	else
			        	{
			        		ST.NativeClick(ObjectCollection.get("Login_Welcome_CloseButton").toString(), client);
			        	}
			        }
			        //Validating License Agreement
			        if(ST.NativeElementFound(ObjectCollection.get("General_LicenseAgreementPage").toString(), client))
	        		{
			        	EF.Update_Step_Report("Verify that the EULA appears after initial login of updated App Version", "EULA is appeared while updated version is installed and initially logged||EULA is not appeared while updated version is installed and initially logged", true);
			        	ST.NativeClick(ObjectCollection.get("General_LicenseAgreementAcceptButton").toString(), client);
	        		}
			        else
			        {
			        	if(client.isElementFound("NATIVE", ObjectCollection.get("Login_PopUp").toString()))
				        {	
					        ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(),client);
				        	System.out.println("Log in Error occured");
				        	EF.Update_Step_Report("Verify that the EULA appears after initial login of updated App Version", "EULA is appeared while updated version is installed and initially logged||EULA is not appeared while updated version is installed and initially logged", false);
				        }
			        	EF.Update_Step_Report("Verify that the EULA appears after initial login of updated App Version", "EULA is appeared while updated version is installed and initially logged||EULA is not appeared while updated version is installed and initially logged", false);
			        	return false;
			        }
			        EF.Update_TC_Report();
			        //Auto_Login_General_007
			        //Privacy policy pop-up validation
			        if(Current_OS=="Android")
			        {
			        	EF.Update_Step_Report("User should get Privacy policy pop-up", "Privacy policy pop-up got displayed||Privacy policy didn't get displayed", ST.NativeElementFound(ObjectCollection.get("General_PrivacyPolicyPage").toString(), client));
			        	ST.NativeClick(ObjectCollection.get("General_LicenseAgreementAcceptButton").toString(), client);
			        	EF.Update_TC_Report();
			        }
			        else
			        {
			        	EF.Update_Step_Report("User should get Privacy policy pop-up", "Privacy policy pop-up got displayed||Privacy policy didn't get displayed", false);
			        	EF.Update_TC_Report();
			        }
			        //Auto_Login_General_008
			        //Validating Adult content caution message
			        if(ST.NativeElementFound(ObjectCollection.get("General_AdultContentPopUp").toString(), client))
			        {
			        	EF.Update_Step_Report("Adult Content Warning should only appear after first login following clean install of app", "Adult Content Warning is appeared after first login||Adult Content Warning is appeared after first login", true);
			        	ST.NativeClick(ObjectCollection.get("General_AdultContentPopUpOkBtn").toString(), client);
			        }
			        else
			        {
			        	EF.Update_Step_Report("Adult Content Warning should only appear after first login following clean install of app", "Adult Content Warning is appeared after first login||Adult Content Warning is appeared after first login", false);
			        }
			        EF.Update_TC_Report();
			        //Auto_Login_General_009
			        //Welcome home screen
			        if(ST.NativeElementFound(ObjectCollection.get("Login_Welcome_PopOver").toString(), client))
			        {
			        	ST.NativeClick(ObjectCollection.get("Login_Welcome_CloseButton").toString(), client);
			        	EF.Update_Step_Report("An welcome screen should be displayed to the user while we enter the App for the first time", "Welcome screen is displayed||Welcome screen is missing", true);
			        }
			        else
			        {
			        	EF.Update_Step_Report("An welcome screen should be displayed to the user while we enter the App for the first time", "Welcome screen is displayed||Welcome screen is missing", false);
			        }
			        //Error handling scenario
			        if(client.isElementFound("NATIVE", ObjectCollection.get("Login_PopUp").toString()))
			        {	
				        ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(),client);
			        }
		        	else if(ST.NativeElementFound(ObjectCollection.get("Login_Welcome_PopOver").toString(), client))
	        		{
			        	ST.NativeClick(ObjectCollection.get("Login_Welcome_CloseButton").toString(), client);
	        		}
					else
					{
						System.out.println("Log in Success");
						bFlag= true;
						return bFlag;
					}
			        // Added to log out after the Execution
			        this.Fn_LogOut();
				}
			}
			catch (Exception e) 
			{
				bFlag=false;
				System.out.println("Login failure popup is displayed");
				System.out.println("login failure");
				System.err.println("	Message : " + e.getMessage());
				System.err.println("	Cause : " + e.getCause());
				e.printStackTrace();
				tearDown();
			}
			return bFlag;
		}
		
		
		
		public void Fn_CustomizePanel_Validation()
		{
			try
			{
				ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
				Fn_Com_Navigate("Home_MyLibrary");
				EF.Update_Step_Report("Customize panel should be at the bottom","Customize panel is at the bottom||Customize panel is not at the bottom",
						client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_CustomizeButton").toString(), 0, 1000, 10, true));
				if(ST.NativeElementFound(ObjectCollection.get("Home_PopOver_Done").toString(), client))
				{
					int count=client.getElementCount("NATIVE", ObjectCollection.get("Home_Customize_ReOrderControl").toString());
					String[] panelName=new String[count];
					for(int i=0;i<count;i++)
					{
						panelName[i]=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_PanelNameLabel").toString(), i);
					}
					ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
					client.swipeWhileNotFound("Up", 600, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						for(int i=0;i<count;i++)
						{
							EF.Update_Step_Report("Panels in the customize pop over should be available in the My Library","panel is availble|| panel is not available",
									client.swipeWhileNotFound("Down", 500, 2000, "NATIVE","//*[@id='new_media_scroller_title' and @text='"+panelName[i]+"']", 0, 1000,5, false));
						}
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						for(int i=0;i<count;i++)
						{
							if(panelName[i].contains("My Favorites"))
							{
								panelName[i]="Favorites";
							}
							else if(panelName[i].contains("My Watchlist"))
							{
								panelName[i]="Watchlist";
							}
							EF.Update_Step_Report("Panels in the customize pop over should be available in the My Library","panel is availble|| panel is not available",
									client.swipeWhileNotFound("Down", 500, 2000, "NATIVE","//*[@accessibilityLabel='"+panelName[i]+"' and @onScreen='true']", 0, 1000,5, false));
						}
					}
				}
				else
				{
					EF.Update_Step_Report("Customize pop over should be displayd","Customize pop over is displayed||Cstomize pop over is not displayed",false);
				}
			}
				
			catch(Exception e)
			{
				System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
			}
		}
		
		public void Customize_Panel_Rearrange()
		{
			try
			{
				
				ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
				Fn_Com_Navigate("Home_MyLibrary");
				EF.Update_Step_Report("Customize panel should be at the bottom","Customize panel is at the bottom||Customize panel is not at the bottom",
						client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_CustomizeButton").toString(), 0, 1000, 10, true));
				if(ST.NativeElementFound(ObjectCollection.get("Home_PopOver_Done").toString(), client))
				{
					String popOverFirstPanel= client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_PanelNameLabel").toString() , 0);
					String popOverSecondPanel=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_PanelNameLabel").toString(), 1);
					
					int xOffset=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, "X"));
					int yOffset1=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, "Y"));
					int yOffset2=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 1, "Y"));
					int yOffset=yOffset2-yOffset1;
					client.setDragStartDelay(500);
					client.drag("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, 0, yOffset);
					client.sleep(5000);
					ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
					client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
					String firstPanel= client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString() , 0);
					String secondPanel=client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString(), 1);
					if(firstPanel.equalsIgnoreCase(popOverSecondPanel) && secondPanel.equalsIgnoreCase(popOverFirstPanel))
					{
						EF.Update_Step_Report("Panels should get reordered", "Panels Reordered properly||Reordering of panel is unsuccessful", true);
					}
					else
					{
						EF.Update_Step_Report("Panels should get reordered", "Panel reordered successfully||Panel reorder unsuccessful", false);
					}
				}
				else
				{
					EF.Update_Step_Report("Customize pop over should be displayd","Customize pop over is displayed||Cstomize pop over is not displayed",false);
				}
				
			}
			catch(Exception e)
			{
				System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
			}
			
		}
		
		
		public void Fn_Home_Customize_Favorites()
		{
			EF.Update_Step_Report("Must be taken to On Demand Movies section", "User is taken to taken to On Demand Movies section" +
					"||User is not taken to On Demand Movies section", Fn_Com_Navigate("Home_OnDemand"));
			EF.Update_Step_Report("Customize panel must be present with a blue '+' icon at the bottom of the screen", "Custmize panel is present with blue icon at the bottom ||Customize panel is not present at the bottom",
					client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Customize_Button").toString(), 0, 1000, 5, true));
			EF.Update_Step_Report("Should display the customize modal", "Customize Modal is available||Customize modal is not available", 
					ST.NativeElementFound(ObjectCollection.get("Customizepanel_PopOver").toString(), client));
			client.elementSwipeWhileNotFound("NATIVE", ObjectCollection.get("Customizepanel_PopOver").toString(), "Down", 100, 2000,
					"NATIVE",ObjectCollection.get("CustomizePanel_Icon_FavoriteEdit").toString(), 0, 1000, 5, true);
			EF.Update_Step_Report("Should display the favorite edit popover ", "Favorite PopOver is displayed||Favorite Pop Over is not displayed", 
					ST.NativeElementFound(ObjectCollection.get("CustomizePanel_Filtericon").toString(), client));
			ST.NativeClick(ObjectCollection.get("CustomizePanel_Filtericon").toString(), client);
			//Incomplete Need test case clarification
		}
	
		
		public void Fn_LiveTv_ListView_PanelCount()
		{
			this.Fn_Com_Navigate("Home_LiveTV");
			client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 10000);
			ST.NativeClick(ObjectCollection.get("LiveTV_Menu_ListView").toString(), client);
			if(ST.NativeElementFound(ObjectCollection.get("LiveTv_icon_expand").toString(), client))
			{
				ST.NativeClick(ObjectCollection.get("LiveTv_icon_expand").toString(), client);
			}
			
			
		}
		//***********************************Build 2 Venkat's source code*****************************************
		public void Validate_UserAccountVerifications()
		{
			String Temp_Var;
			Boolean Local_Result = true;
			String Account_Number = TestData.get("AccountNumber").toString();
			String UserName = TestData.get("UserName").toString();
			
			//Navigate to Setting Screen
			this.Fn_Com_Navigate("Home_Settings");
			EF.Update_Step_Report("Navigate to Settings Summary Screen", "Sucessfully Navigated||Navigation failed",true);
			
			//Verification for the Account Number
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_AccountNumber").toString(),0, client);
			if (!(Temp_Var.contains(Account_Number)))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify Account Number in Settings Summary Screen", "Sucessfully Verified Account Number in Setting Summary Screen||Account Number Verification failed in Settings Summary Screen",true);
			Local_Result = true;
			EF.Update_TC_Report();
			
			//Verification for the User Name
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_UserName").toString(),0, client);
			if (!(Temp_Var.contains(UserName)))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify User Name in Settings Summary Screen", "Sucessfully Verified User Name in Setting Summary Screen||User Name Verification failed in Settings Summary Screen",true);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// Verification for the Logout Button & Logout Functionality
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Settings_Summary_Logout").toString(),0, client);
			if (!(Temp_Var.contains("Logout")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify Logout button in Settings Summary Screen", "Sucessfully Verified Logout button in Setting Summary Screen||Logout button Verification failed in Settings Summary Screen",true);
			Local_Result = true;
			//EF.Update_TC_Report();
			//Verification for the Logout Functionality
			//EF.Update_Step_Report("Click on Logout Button", "Clicked successfully||Click failed",ST.NativeClick(ObjectCollection.get("Setting_Logut").toString(), client));
			//EF.Update_Step_Report("Check for Login Edit box in Login Page", "Login Edit box is available||Login Edit box is not available",ST.NativeElementFound(ObjectCollection.get("Login_Edit_box").toString(), client));
		}
			
		public void Fn_Validate_ManageDevicesVerifications()
		{
			String Temp_Var;
			Boolean Local_Result = true;
			String DafultSTB = TestData.get("DefaultSTB").toString();
			String STBNickname = TestData.get("STBNickname").toString();
			String STBNicknameEditText = TestData.get("STBNicknameEditText").toString();
			
			// ************* Start : Settings_ManageDevices_001 - Verification for DefaultSTB ***************************************************** //
			//Navigate to Settings Summary Screen
			//EF.Update_Step_Report("Navigate to Settings Summary Screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
			//System.out.println("path:"+ObjectCollection.get("Home_PageList").toString());
			
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				ST.NativeClick(ObjectCollection.get("Settings_ManageDevices").toString(), client);
				EF.Update_Step_Report("Click on Manage Devices Tab", "Clicked successfully||Click failed", true);
			}
			else
			{
				ST.NativeClick(ObjectCollection.get("Settings_DefaultTV").toString(), client);
				EF.Update_Step_Report("Click on Default TV Tab", "Clicked sucessfully||Click failed", true);
			}
			
			//Verification for the DefaultSTB
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_DefaultTV_DefaultSTB").toString(),0, client);
			//Temp_Var=client.elementGetText("NATIVE", ObjectCollection.get("Setting_DefaultTV_DefaultSTB").toString(), 0);
			if (!(Temp_Var.contains(DafultSTB)))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify Default STB in Default TV Screen", "Sucessfully Verified Default STB in Default TV Screen||Default STB Verification failed in Default TV Screen",true);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// ************* End : Settings_ManageDevices_001 - Verification for DefaultSTB ***************************************************** //
			
			// ************* Start : Settings_ManageDevices_002 - Verification for STB Nicknames ***************************************************** //
			
			//Navigate to TV Nicknames Screen
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				ST.NativeClick(ObjectCollection.get("Settings_ManageDevices").toString(), client);
				EF.Update_Step_Report("Click on Manage Devices Tab", "Clicked successfully||Click failed", true);
			}
			else
			{
				EF.Update_Step_Report("Click on TV Nicknames Tab", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Settings_TVNicknames").toString(), client));
			}
			//Verification for the STB Nickname
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_TVNicknames_STBNickname").toString(),0, client);
			if (!(Temp_Var.contains(STBNickname)))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify STB Nickname in TV Nicknames Screen", "Sucessfully Verified STB Nickname in TV Nicknames Screen||STB Nickname Verification failed in TV Nicknames Screen",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// ************* End : Settings_ManageDevices_002 - Verification for STB Nicknames ***************************************************** //
			
			// ************* Start : Settings_ManageDevices_003 - Verification for Edited STB Nicknames ***************************************************** //
			
			// Click on STB Nickname Edit Text box
			//EF.Update_Step_Report("Click on STB Nickname Edit Text field", "Clicked successfully||Click failed",ST.NativeClick(ObjectCollection.get("Setting_TVNicknames_STBNickname").toString(), client));
			// Clear the Text box value
			//if(ST.NativeElementFound(ObjectCollection.get("Setting_TVNicknames_STBNickname").toString(), client))
			//{
				//client.sendText("{Empty}");
			//}
			//Enter the New STB Nickname in Nickname Text box
			ST.NativeElementSendTxt(ObjectCollection.get("Setting_TVNicknames_STBNickname").toString(), STBNicknameEditText, client);
			
			client.closeKeyboard();
			// Click on Save button
			ST.NativeClick(ObjectCollection.get("Settings_TVNicknames_Save").toString(), client);
			EF.Update_Step_Report("Click on Save button", "Clicked sucessfully||Click failed",true);
			//Verification for the Edited STB Nickname
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_TVNicknames_STBNickname").toString(),0, client);
			if (!(Temp_Var.contains(STBNicknameEditText)))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify Edited STB Nickname in TV Nicknames Screen", "Sucessfully Verified Edited STB Nickname in TV Nicknames Screen||Edited STB Nickname Verification failed in TV Nicknames Screen",true);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// ************* End : Settings_ManageDevices_003 - Verification for Edited STB Nicknames ***************************************************** //
			
			// ************* Start : Settings_ManageDevices_004 - Verification for Check This TV ***************************************************** //
			
			// Verification for the Check This TV option
			Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_TVNickName_CheckThisTV").toString(),0, client);
			if (!(Temp_Var.contains("Check This TV")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify Check This TV in Settings Summary Screen", "Sucessfully Verified Check This TV in TV Nicknames Screen||Check This TV Verification failed in Settings TV Nicknames Screen",true);
			Local_Result = true;
			//EF.Update_TC_Report();
			
			// ************* End : Settings_ManageDevices_004 - Verification for Check This TV ***************************************************** //
		}
		
		public void Fn_Validate_AboutVerifications()
		{
			String Temp_Var;
			Boolean Local_Result = true;
			String BuildVersionNumber = TestData.get("BuildVersionNumber").toString();
			
			// ************* Start : Settings_About_001 - Verification for Build Number ***************************************************** //
			
			//Navigate to Settings Summary Screen
			//EF.Update_Step_Report("Navigate to Settings Summary Screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_Setting"));
			
			
			// Verification for Build Version Number
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				ST.NativeClick(ObjectCollection.get("Settings_About").toString(), client);
				EF.Update_Step_Report("Click on TV Nicknames Tab", "Clicked sucessfully||Click failed", true);
				Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_About_BuildVersionNumber").toString(),0, client);
				if (!(Temp_Var.contains(BuildVersionNumber)))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify Build Version Number in Settings About Screen", "Sucessfully Verified Build Version Number in About Screen||Build Version Number Verification failed in Settings About Screen",true);
				Local_Result = true;
				//ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
				//EF.Update_TC_Report();
			}
			
			else
			{
				Temp_Var = ST.NativeGetElementText(ObjectCollection.get("2.2.3_B3 (0b04442) PROD").toString(),0, client);
				if (!(Temp_Var.contains(BuildVersionNumber)))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify Build Version Number in Settings About Screen", "Sucessfully Verified Build Version Number in About Screen||Build Version Number Verification failed in Settings About Screen",true);
				Local_Result = true;
				//ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
				//EF.Update_TC_Report();
			}
			EF.Update_TC_Report();
			// ************* End : Settings_About_001 - Verification for Build Number ***************************************************** //
			
			// ************* Start : Settings_About_002 - Verification for Privacy Policy ***************************************************** //
			
			// Navigate to Privacy Policy Screen
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				ST.NativeClick(ObjectCollection.get("Settings_About_PrivacyPolicy").toString(), client);
				EF.Update_Step_Report("Click on Privacy Policy Tab", "Clicked sucessfully||Click failed", true);
				
				// Verification for Privacy Policy Text Message
				Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_About_PrivacyPolicyTextMessage").toString(),0, client);
				if (!(Temp_Var.contains("Privacy")))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify Privacy Policy in Settings About Screen", "Sucessfully Verified Privacy Policy in About Screen||Privacy Policy Verification failed in Settings About Screen",true);
				Local_Result = true;
				if(ST.NativeElementFound(ObjectCollection.get("Setting_About_PrivacyPolicyTextMessage").toString(), client))
				{
					//client.sendText("{ESC}");
					ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
				}
			}
			
			else
			{
				EF.Update_Step_Report("Click on Privacy Policy Tab", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Settings_About_PrivacyPolicy").toString(), client));
				
				// Verification for Privacy Policy Text Message
				Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_About_PrivacyPolicyTextMessage").toString(),0, client);
				if (!(Temp_Var.contains("Privacy Policy Agreement")))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify Privacy Policy in Settings About Screen", "Sucessfully Verified Privacy Policy in About Screen||Privacy Policy Verification failed in Settings About Screen",Local_Result);
				Local_Result = true;
				
			}
			EF.Update_TC_Report();
			
			// ************* End : Settings_About_002 - Verification for Privacy Policy ***************************************************** //
			
			// ************* Start : Settings_About_003 - Verification for License Agreement ***************************************************** //
			
			// Navigate to License Agreement Screen
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				ST.NativeClick(ObjectCollection.get("Settings_About_LicenseAgreement").toString(), client);
				EF.Update_Step_Report("Click on License Agreement Tab", "Clicked sucessfully||Click failed", true);
				
				// Verification for License Agreement Text Message
				Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_About_LicenseAgreementTextMessage").toString(),0, client);
				if (!(Temp_Var.contains("License")))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify License Agreement in Settings About Screen", "Sucessfully Verified License Agreement in About Screen||License Agreement Verification failed in Settings About Screen", true);
				Local_Result = true;
				//
				if(ST.NativeElementFound(ObjectCollection.get("Setting_About_LicenseAgreementTextMessage").toString(), client))
				{
					//client.sendText("{ESC}");
					ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
				}
			}
			else
			{
				EF.Update_Step_Report("Click on License Agreement Tab", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Settings_About_LicenseAgreement").toString(), client));
				
				// Verification for License Agreement Text Message
				Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_About_LicenseAgreementTextMessage").toString(),0, client);
				if (!(Temp_Var.contains("CHARTER SOFTWARE LICENSE AGREEMENT")))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify License Agreement in Settings About Screen", "Sucessfully Verified License Agreement in About Screen||License Agreement Verification failed in Settings About Screen",Local_Result);
				Local_Result = true;
				//EF.Update_TC_Report();
			}
			EF.Update_TC_Report();
			// ************* End : Settings_About_003 - Verification for License Agreement ***************************************************** //
			
			// ************* Start : Settings_About_004 - Verification for Content Warning ***************************************************** //
			
			// Navigate to Content Warning Screen
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				ST.NativeClick(ObjectCollection.get("Settings_About_ContentWarning").toString(), client);
				EF.Update_Step_Report("Click on Content Warning Tab", "Clicked sucessfully||Click failed",true);
				
				// Verification for Content Warning Text Message
				Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_About_ContentWarningTextMessage").toString(),0, client);
				if (!(Temp_Var.contains("Adult")))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify Content Warning in Settings About Screen", "Sucessfully Verified Content Warning in About Screen||Content Warning Verification failed in Settings About Screen",true);
				Local_Result = true;
				//EF.Update_TC_Report();
				if(ST.NativeElementFound(ObjectCollection.get("Setting_About_LicenseAgreementTextMessage").toString(), client))
				{
					//client.sendText("{ESC}");
					ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				}
			}
			else
			{
				EF.Update_Step_Report("Click on Content Warning Tab", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Settings_About_ContentWarning").toString(), client));
				
				// Verification for Content Warning Text Message
				Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_About_ContentWarningTextMessage").toString(),0, client);
				if (!(Temp_Var.contains("Adult Content/Mature Content Warning")))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify Content Warning in Settings About Screen", "Sucessfully Verified Content Warning in About Screen||Content Warning Verification failed in Settings About Screen",Local_Result);
				Local_Result = true;
				//EF.Update_TC_Report();
			}
			EF.Update_TC_Report();
			// ************ End : Settings_About_004 - Verification for Content Warning ***************************************************** //
			
			// ************* Start : Settings_About_005 - Verification for Apache License  ***************************************************** //
			
			// Navigate to Apache License Screen
			if(Current_OS.equalsIgnoreCase("Android"))
			{
				EF.Update_Step_Report("Click on Apache License Tab", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Settings_About_ApacheLicense").toString(), client));
				
				// Verification for Apache License Text Message
				Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_About_ApacheLicenseTextMessage").toString(),0, client);
				if (!(Temp_Var.contains("Apache License")))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify Apache License in Settings About Screen", "Sucessfully Verified Apache License in About Screen||Apache License Verification failed in Settings About Screen",Local_Result);
				Local_Result = true;
				
			}
			else
			{
				EF.Update_Step_Report("Verify Apache License in Settings About Screen", "Android Specific not impleted in iOS||Android Specific not impleted in iOS", true);
			}
			EF.Update_TC_Report();
			// ************ End : Settings_About_005 - Verification for Apache License ***************************************************** //
			
			// ************* Start : Settings_About_006 - Verification for closed Captioning  ***************************************************** //
			
			// Navigate to Closed Captioning Screen
			if(Current_OS.equalsIgnoreCase("Android"))
			{
				EF.Update_Step_Report("Click on Closed Captioning Tab", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Settings_About_ClosedCaptioning").toString(), client));
				
				// Verification for Closed Captioning Text Message
				Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Setting_About_ClosedCaptioningTextMessage").toString(),0, client);
				if (!(Temp_Var.contains("Apache License")))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify Closed Captioning in Settings About Screen", "Sucessfully Verified Closed Captioning in About Screen||Closed Captioning Verification failed in Settings About Screen",Local_Result);
				Local_Result = true;
				
			}
			{
				EF.Update_Step_Report("Verify Apache License in Settings About Screen", "Android Specific not impleted in iOS||Android Specific not impleted in iOS", true);
			}
			
			// ************ End : Settings_About_006 - Verification for Closed Captioning ***************************************************** //
		}
		
	
		

		public void Fn_Validate_DeleteWatchlist_1Serieswith2Episodes()

		{
			Boolean Local_Result = true;
			//int nCount3;	
			
			// ********************* Deleting the Added TV Series with 2 Episodes Watchlist through Rollup Modal popover *******************************
			EF.Update_Step_Report("Navigate to My Library", "Successfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
			
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				
				EF.Update_Step_Report("Display Watchlist Shelf under My Library screen","Watchlist shelf should be available under My Library Screen||Watchlist shelf is not available under My Library Screen",
		                 client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist_BrowseAll").toString(), 0, 1000, 10, true));
		    }
			else
			{		
				Local_Result=client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller' and @onScreen='true']//*[@id='new_media_scroller_browse_all'and@onScreen='true']", 0, 1000, 6, true);
				EF.Update_Step_Report("Click on Watchlist BrowseAll button", "Clicked sucessfully||Click failed", Local_Result);
			}

			EF.Update_Step_Report("Click on Added Watchlist Posterart in Watchlist Screen", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Watchlist_Posterart_image").toString(), client));
			EF.Update_Step_Report("Check for DeleteAll Button in Watchlist items", "DeleteAll Button is available in Watchlist items||DeleteAll button is not available in Watchlist items",
					ST.NativeElementFound(ObjectCollection.get("Watchlist_Rollup_DeleteAllbutton").toString(), client));
			EF.Update_Step_Report("Click on Delete All button in Watchlist Rollup modal", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Watchlist_Rollup_DeleteAllbutton").toString(), client));
			EF.Update_Step_Report("Click on OK button in Watchlist Rollup modal", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Watchlist_Rollup_Okbutton").toString(), client));
//			EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
//					ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
			EF.Update_TC_Report();
			
			// ******************** Verification for deleted TV Asset in Watchlist Screen ****************************
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				EF.Update_Step_Report("Check for Watchlist Poster Art after deleting the Watchlist", "Watchlist Poster Art is not getting displayed after deleting the Watchlist||Watchlist Poster Art is not getting displayed after deleting the Watchlist",
						!(ST.NativeElementFound(ObjectCollection.get("Watchlist_Posterart_image").toString(), client)));
				EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
				EF.Update_TC_Report();
			}
			else
			{
				String Temp_Var1 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Screen_Textmessage").toString(),0, client);
				if (!(Temp_Var1.contains("Tap the Watchlist pin on any On Demand program to add it here")))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify text Message under Watchlist Section in My Library Screen", "Sucessfully Verified||Verification failed",Local_Result);
				Local_Result = true;
				EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
				EF.Update_TC_Report();
			}
			
			
			// ******************** Verification for deleted TV Series with 2 Episodes Watchlist item in My Library Screen Under Watchlist Section ****************************
			EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
			EF.Update_Step_Report("Display Watchlist Shelf under My Library screen","Watchlist shelf should be available under My Library Screen||Watchlist shelf is not available under My Library Screen",
	                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist").toString(), 0, 1000, 10, false));
	       String Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Watchlist_Textmessage").toString(),0, client);
			if (!(Temp_Var.contains("Browse On Demand to add Movies and TV Shows")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text Message under Watchlist Section in My Library Screen", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			
		}


		
		
		public void Fn_Validate_AssetDetailsFullScreen()
		{
			Boolean Local_Result = true;
			
			// 1*************** Verify the Program Title displays in the Full Asset Detail Screen. ****************************
			//EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
			//EF.Update_Step_Report("Navigate to Guide", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_Guide"));
			
			// For iOS
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				EF.Update_Step_Report("Check for Watch on TV option in Guide screen", "Watch on TV option is available||Watch on TV option is not available",
						ST.NativeElementFound(ObjectCollection.get("Guide_WatchonTV").toString(), client));
				EF.Update_Step_Report("Click on Watch on TV option in Guide screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Guide_WatchonTV").toString(), client));
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
				String GuideProgramTitle = ST.NativeGetElementText(ObjectCollection.get("Guide_ProgramTitle").toString(),2, client);
				client.click("NATIVE", ObjectCollection.get("Guide_ProgramTitle").toString(), 2, 1);
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
				EF.Update_Step_Report("Click on Program title in Guide Screen", "Sucessfully Clicked Program title in Guide Screen||Program title Click failed in Guide Screen",Local_Result);
				
				String AssetDetailsProgramTitle1 = ST.NativeGetElementText(ObjectCollection.get("AssetDetails_ProgramTitle").toString(),0, client);
				if (!(AssetDetailsProgramTitle1.contains(GuideProgramTitle)))
				{
					Local_Result = false;
				}
				Local_Result = true;
				EF.Update_Step_Report("Verify Program title in Full Asset Details Screen", "Sucessfully Verified Program title in Full Asset Details Screen||Program title Verification failed in Full Asset Details Screen",Local_Result);
				
				EF.Update_TC_Report();
			}
			else
			{
				String GuideProgramTitle = ST.NativeGetElementText(ObjectCollection.get("Guide_GuideScreen_ProgramName").toString(),2, client);
				EF.Update_Step_Report("Check for Program Title in Guide screen", "Program Title is available||Program title is not available",
						ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_ProgramName").toString(), client));
				EF.Update_Step_Report("Click on Program Title in Guide screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Guide_GuideScreen_ProgramName").toString(), client));
				EF.Update_Step_Report("Click on Program title in Guide Screen", "Sucessfully Clicked Program title in Guide Screen||Program title Click failed in Guide Screen",Local_Result);
				
				String AssetDetailsProgramTitle1 = ST.NativeGetElementText(ObjectCollection.get("AssetDetails_ProgramTitle").toString(),0, client);
				if (!(AssetDetailsProgramTitle1.contains(GuideProgramTitle)))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify Program title in Full Asset Details Screen", "Sucessfully Verified Program title in Full Asset Details Screen||Program title Verification failed in Full Asset Details Screen",Local_Result);
				Local_Result = true;
				
				EF.Update_TC_Report();
			}
		
			// 2************* Verify that the Time Duration displays in the Full Asset Detail Screen ***********************************
			EF.Update_Step_Report("Verify Time Duraion in Full Asset Details Screen", "Sucessfully Verified Time Duration in Full Asset Details Screen||Time Duration Verification failed in Full Asset Details Screen",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_ProgramTitle").toString(), client));
			EF.Update_TC_Report();
			
			// 3************ Verify that the Original Air Date of the Selected Asset displays in the Full Asset Detail Screen *****************
			EF.Update_Step_Report("Verify Date Airing in Full Asset Details Screen", "Sucessfully Verified Date Airing in Full Asset Details Screen||Date Airing Verification failed in Full Asset Details Screen",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_DateAiring").toString(), client));
			EF.Update_TC_Report();
			
			// 4************* DVR Selector should be visible for available Channels in the Full Screen Asset Detail.  **********************************\
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_DVRSelector").toString(),client))
			{
				EF.Update_Step_Report("Check for DVR Selector in Asset Details screen", "DVR Selector is available||DVR Selector is not available",true);
				EF.Update_Step_Report("Click on DVR Selector in Asset Details screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_DVRSelector").toString(), client));
				EF.Update_Step_Report("Check for DVR Selector Dropdown in Asset Details screen", "DVR Selector Dropdown is available||DVR Selector Dropdown is not available check whether the logged in account user is enabled for more than one DVR",
						ST.NativeElementFound(ObjectCollection.get("Asset_Details_DVRSelector_Dropdown").toString(), client));
//				EF.Update_Step_Report("Click on Back button in Asset Details screen", "Clicked successfully||Click failed",
//						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
				ST.NativeClick(ObjectCollection.get("AssetDetails_DVRSelector").toString(), client);
			}
			else
			{
				EF.Update_Step_Report("Check for DVR Selector in Asset Details screen", "DVR Selector is available||DVR Selector is not available please check whether the logged in user is enabled with DVR",false); 
			}
			EF.Update_TC_Report();
			
			// 5************* Verify that the Network logo is displayed inline with ratings in the Asset detail page  *********************************
			EF.Update_Step_Report("Check for Netwrork LOGO in Asset Details screen", "Netwrork LOGO is available||Network LOGO is not available",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_NetworkLOGO").toString(), client));
			EF.Update_TC_Report();
			
			// 6************ Verify whether the text is changed to WATCH LIVE for Send to TV option ****************************************************
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_WatchLIVEText").toString(), client))
			{
				EF.Update_Step_Report("Check for Watch LIVE Text in Asset Details screen", "Watch LIVE Text is available||Watch LIVE Text is not available", true);
				EF.Update_Step_Report("Verify whether the text is changed to WATCH LIVE for Send to TV option", "Sucessfully Verified  the text is changed to WATCH LIVE for Send to TV option|| The text is changed to WATCH LIVE for Send to TV option Verification failed in Full Asset Details Screen", true);
			}
			else
			{
				EF.Update_Step_Report("Check for Watch LIVE Text in Asset Details screen", "Watch LIVE Text is available||Watch LIVE Text is not available", false);
			}
			EF.Update_TC_Report();
			
			// 7************** Verify that the WATCH LIVE option exists under the Send to TV icon in the  Asset detail page *****************************
			EF.Update_Step_Report("Check for Send to TV option in Asset Details screen", "Send to TV Option is available||Send to TV option is not available",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_WatchLIVEIcon").toString(), client));
			EF.Update_TC_Report();
			
			// 8************* Verify that the Record option exists besides the  Send to TV section on Asset detail page **********************************
			EF.Update_Step_Report("Check for Record option in Asset Details screen", "Record Option is available||Record option is not available for the logged in customer account",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_RecordIcon").toString(), client));
			EF.Update_TC_Report();
			
			// 9************* Verify that the Watchlist option is not available for assets in guide screen  *********************************************
			EF.Update_Step_Report("Check for Watchlist Icon in Asset Details screen", "Watchlist Icon should not be available||Watchlist Icon is not present",
					!(ST.NativeElementFound(ObjectCollection.get("AssetDetails_WatchlistIcon").toString(), client)));
			EF.Update_TC_Report();
			
			// Get Listings Model Verifications
			// 10*************************** The Network Logo of the selected Asset should display in the Get Listings Pop-Over. **************************
			EF.Update_Step_Report("Click on OtherTimes in Asset detail screen", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("AssetDetails_OtherTimes").toString(), client));
			EF.Update_Step_Report("Check for Network LOGO in Get Listings popover", "Network LOGO is available in Get Listings popover||Network LOGO is not available in Get Listings popover ",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_NetworkLOGO").toString(), client));
			EF.Update_TC_Report();
			
			// 11************************** The Showing Date of the selected Asset should display in the Get Listings Pop-Over. ****************************
			EF.Update_Step_Report("Check for Showing Date in Get Listings popover", "Shwoing Date is available in Get Listings popover||Shwoing Date is not available in Get Listings popover ",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_ShowingDate").toString(), client));
			EF.Update_TC_Report();
			
			// 12************************** The Time Slot of the selected Asset should display in the Get Listings Pop-Over. *******************************
			EF.Update_Step_Report("Check for Shwoing Date in Get Listings popover", "Shwoing Date is available in Get Listings popover||Shwoing Date is not available in Get Listings popover ",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_TimeSlot").toString(), client));
			EF.Update_TC_Report();
			
			// 13************************** The Record Button of the selected Asset should display in the Get Listings Pop-Over. ***************************
			EF.Update_Step_Report("Check for Record button in Get Listings popover", "Record button is available in Get Listings popover||Record button is not available in Get Listings popover ",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_Btn_Record").toString(), client));
			EF.Update_TC_Report();
			
			// 14************************** Verify whether user is able to view sections in Get Listings Modal *********************************************
			EF.Update_Step_Report("Check for Current Listings Section in Get Listings popover", "Current Listings Section is available in Get Listings popover||Current Listings Section is not available in Get Listings popover ",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_CurrentListings").toString(), client));
			EF.Update_TC_Report();
			
			// 15************************** Verify whether user is able to view the current episodes in current listings section in Get Listings Modal *******
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_CurrentListings").toString(), client))
			{
				EF.Update_Step_Report("Check for Current Listings Section in Get Listings popover", "Current Listings Section is available in Get Listings popover||Current Listings Section is not available in Get Listings popover ", true);
				EF.Update_Step_Report("Check for Current Programns under Current Listings Section in Get Listings popover", "Current Programns under Current Listings Section is available in Get Listings popover||Current Programns under Current Listings Section is not available in Get Listings popover ",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_NetworkLOGO").toString(), client));
			}
			else
				EF.Update_Step_Report("Check for Current Listings Section in Get Listings popover", "Current Listings Section is available in Get Listings popover||Current Listings Section is not available in Get Listings popover ", false);
			EF.Update_TC_Report();
			
			// 16************************* Verify whether user is able to view Action buttons for current listings  *****************************************
			
			EF.Update_Step_Report("Check for Play button under Current Listings Section in Get Listings popover", "Play button under Current Listings Section is available in Get Listings popover||Play button under Current Listings Section is not available in Get Listings popover ",
				ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_Btn_Play").toString(), client));
			EF.Update_Step_Report("Check for Send to TV button under Current Listings Section in Get Listings popover", "Send to TV button under Current Listings Section is available in Get Listings popover||Send to TV button under Current Listings Section is not available in Get Listings popover ",
				ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_Btn_SENDTOTV").toString(), client));
			EF.Update_Step_Report("Check for Record button under Current Listings Section in Get Listings popover", "Record button under Current Listings Section is available in Get Listings popover||Record button under Current Listings Section is not available in Get Listings popover ",
				ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_Btn_Record").toString(), client));
			EF.Update_TC_Report();
			
			// 17************************* Verify whether user is able to view the Feature episodes in future listings section in Get Listings Modal **********
			if(client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("AssetDetails_Getlistings_FeatureListings").toString(), 0, 1000, 5, false))
			{
				EF.Update_Step_Report("Check for Feature Listings Section in Get Listings popover", "sucessfully verified Future Listings Section in Get Listings popover||Future Listings Section verification failed in Get Listings popover", true);
				EF.Update_Step_Report("Check for Feature Programns under Feature Listings Section in Get Listings popover", "Future Programns under Feature Listings Section is available in Get Listings popover||Future Programns under Future Listings Section is not available in Get Listings popover ",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_NetworkLOGO").toString(), client));
			}
			else
			{
				EF.Update_Step_Report("Check for Feature Listings Section in Get Listings popover", "sucessfully verified Future Listings Section in Get Listings popover||The respective asset is not available in future timings", false);
			}
					
			EF.Update_TC_Report();
			
			// 18************************* Verify whether user is able to view Action buttons for feature listings  *****************************************
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_Getlistings_FeatureListings").toString(), client))
			{
				EF.Update_Step_Report("Check for Play button under feature Listings Section in Get Listings popover", "Play button under feature Listings Section is available in Get Listings popover||Play button under Current Listings Section is not available in Get Listings popover ",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_Btn_Play").toString(), client));
				EF.Update_Step_Report("Check for Send to TV button under feature Listings Section in Get Listings popover", "Send to TV button under feature Listings Section is available in Get Listings popover||Send to TV button under Current Listings Section is not available in Get Listings popover ",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_Btn_SENDTOTV").toString(), client));
				EF.Update_Step_Report("Check for Record button under feature Listings Section in Get Listings popover", "Record button under Current feature Section is available in Get Listings popover||Record button under Current Listings Section is not available in Get Listings popover ",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_Btn_Record").toString(), client));
			}
			else
			{
				EF.Update_Step_Report("Check for Feature Listings Section in Get Listings popover", "sucessfully verified Future Listings Section in Get Listings popover||The respective asset is not available in future timings", false);
			}
			EF.Update_TC_Report();
			
			// 19************************* Verify whether user is able to view the related episodes in related section in Get Listings Modal ******************
			if(client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("AssetDetails_Getlistings_RelatedListings").toString(), 0, 1000, 5, false))
			{
				//Local_Result=client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller' and @onScreen='true']//*[@id='new_media_scroller_browse_all'and@onScreen='true']", 0, 1000, 6, false);
				EF.Update_Step_Report("Check for Related Listings Section in Get Listings popover", "sucessfully verified Related Listings Section in Get Listings popover||Related Listings Section verification failed in Get Listings popover", true);
				EF.Update_Step_Report("Check for Related Programns under Related Listings Section in Get Listings popover", "Related Programns under Related Listings Section is available in Get Listings popover||Related Programns under Related Listings Section is not available in Get Listings popover ",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_GetListings_NetworkLOGO").toString(), client));
			}
			else
			{
				EF.Update_Step_Report("Check for Related Listings Section in Get Listings popover", "sucessfully verified Related Listings Section in Get Listings popover||Related Listings Section is not available for the selected Asset", false);
			}
			EF.Update_TC_Report();
			
			// 20************************* Verify whether user is not  able to view actions buttons for related episodes In get listings modal  **************
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_Getlistings_RelatedListings").toString(), client))
			{
				EF.Update_Step_Report("Check for Play button under related Listings Section in Get Listings popover", "Play button under related Listings Section is not available in Get Listings popover||Play button under Current Listings Section is available in Get Listings popover ",
						!(ST.NativeElementFound(ObjectCollection.get("AssetDetails_RelatedListings_Btn_SENDTOTV").toString(), client)));
				EF.Update_Step_Report("Check for Send to TV button under related Listings Section in Get Listings popover", "Send to TV button under related Listings Section is not available in Get Listings popover||Send to TV button under Current Listings Section is available in Get Listings popover ",
						!(ST.NativeElementFound(ObjectCollection.get("AssetDetails_RelatedListings_Btn_LIVE").toString(), client)));
				EF.Update_Step_Report("Check for Record button under related Listings Section in Get Listings popover", "Record button under related Listings Section is not available in Get Listings popover||Record button under Current Listings Section is available in Get Listings popover ",
						!(ST.NativeElementFound(ObjectCollection.get("AssetDetails_RelatedListings_Btn_RECORD").toString(), client)));
			}
			else
			{
				EF.Update_Step_Report("Check for Related Listings Section in Get Listings popover", "sucessfully verified Related Listings Section in Get Listings popover||Related Listings Section is not available for the selected Asset", false);
			}
			// Click on Back button
			EF.Update_Step_Report("Click on Back button in Asset Details screen", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
			EF.Update_TC_Report();
			
			// 21******* Verify that the User is displayed with a "Series  Recording Set" on selecting Record button for an asset that has multiple deliveries(Season and episode) ****
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_RecordIcon").toString(), client))
			{
				EF.Update_Step_Report("Check for Record option in Asset Details screen", "Record Option is available||Record option is not available", true);
				EF.Update_Step_Report("Click on Record button in Asset Details screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_RecordIcon").toString(), client));
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
				EF.Update_Step_Report("Check for Record option under Current Listing", "Record Option is available||Record option is not available",
						ST.NativeElementFound(ObjectCollection.get("Record_Button").toString(), client));
				EF.Update_Step_Report("Click on Record button under Current Listing", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Record_Button").toString(), client));
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
				EF.Update_Step_Report("Check for Record Series in Select Recording popover", "Record Series is available||Record Series is not available",
						ST.NativeElementFound(ObjectCollection.get("Record_Series").toString(), client));
				EF.Update_Step_Report("Click on Record Series in Select Recording popover", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Record_Series").toString(), client));
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
			
				if(ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp").toString(), client))
				{
					ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
					EF.Update_Step_Report("Check for Record Successful Message", "Record Successful Message is found||Error message got displayed", false);
					ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client);
					ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client);
				}
				else if(ST.NativeElementFound(ObjectCollection.get("Record_Successful_message").toString(), client))
				{
					EF.Update_Step_Report("Check for Record Successful Message", "Record Successful Message is found||Record Successful Message is not found",
							ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client));
				}
			}
			else
			{
				EF.Update_Step_Report("Check for Record option in Asset Details screen", "Record Option is available||Record option is not available", false);
			}
			EF.Update_Step_Report("Click on Back button in Asset Details screen", "Clicked successfully||Click failed",ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
			EF.Update_TC_Report();
			
			// 22*************** Verify that if the user is able to play an asset in the Tablet/Phone if the asset is ON ***********************************
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_PlayIcon").toString(), client))
			{
				EF.Update_Step_Report("Check for Play Icon in Asset Details screen", "Play Icon is available||Play Icon is not available",
						true);
				EF.Update_Step_Report("Click on Play button in Asset Details screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_PlayIcon").toString(), client));
				
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp").toString(), client))
				{
					EF.Update_Step_Report("Verify the user is able to play an asset in the Tablet/Phone if the asset is ON", "Sucessfully Verified  user is able to play an asset in the Tablet/Phone if the asset is ON|| User is able to play an asset in the Tablet/Phone if the asset is ON Verification failed in Full Asset Details Screen", false);
					ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
				}
				else
				{
					EF.Update_Step_Report("Verify the user is able to play an asset in the Tablet/Phone if the asset is ON", "Sucessfully Verified  user is able to play an asset in the Tablet/Phone if the asset is ON|| User is able to play an asset in the Tablet/Phone if the asset is ON Verification failed in Full Asset Details Screen",ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Done").toString(), client));
					EF.Update_Step_Report("Click on Done button in Video player", "Clicked successfully||Click failed",ST.NativeClick(ObjectCollection.get("VideoPlayer_Done").toString(), client));
				}
			}
			else
			{
				EF.Update_Step_Report("Check for Play Icon in Asset Details screen", "Play Icon is available||Play Icon is not available",false);
			}
			
			EF.Update_TC_Report();
			
			// 23*************** The Full screen Asset Detail "back" button should direct user to the previous screen. **********************************
						
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_Backbutton").toString(), client))
			{
				EF.Update_Step_Report("Check for Back button in Asset Details screen", "Back button is available||Back button is not available",true);
				EF.Update_Step_Report("Click on Back button in Asset Details screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
				ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
				EF.Update_Step_Report("Verify The Full screen Asset Detail back button should direct user to the previous screen", "Sucessfully Verified  The Full screen Asset Detail back button should direct user to the previous screen|| Back button Verification failed in Full Asset Details Screen",ST.NativeElementFound(ObjectCollection.get("Guide_Title").toString(), client));
			}
			else
			{
				EF.Update_Step_Report("Check for Back button in Asset Details screen", "Back button is available||Back button is not available",false);
			}
			Local_Result = true;
			EF.Update_TC_Report();
			
			// 24****************** Verifications for Movie Asset ***************************************************************************************
			// ******************  Verify that the Asset Detail has a full Asset Description displaying for the selected Asset. ***********************
			this.Rest2homescreen();
			EF.Update_Step_Report("Navigate to On Demand", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_OnDemand"));
			ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
			ST.NativeClick(ObjectCollection.get("OnDemand_Tab_Movie").toString(), client);
			if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Movie_PosterArt").toString(), client))
			{
				EF.Update_Step_Report("Verify whether the user is able to view the Asset posterArt", "User is able to view the Asset posterArt||User is not able to view the Asset posterArt", true);
				EF.Update_Step_Report("On clicking Asset poster Art the asset details page should be displayed", "Asset details page is displayed||Asset details page is not displayed", ST.NativeClick(ObjectCollection.get("OnDemand_Movie_PosterArt").toString(), client));
				EF.Update_Step_Report("Check for Asset Description in Asset Details screen", "Asset Description is available||Asset Description is not available",ST.NativeElementFound(ObjectCollection.get("AssetDescription").toString(), client));
			}
			EF.Update_TC_Report();
			
			// 25****************** Verify that the Parental Guidelines(TV-Y ,TV-Y7, TV-G, TV-PG, TV-14, TV-MA) in the Asset Detail. ********************
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_AC").toString(), client))
			{
				EF.Update_Step_Report("Check for AC button in Asset Details screen", "AC button is available||AC button is not available",true);
				EF.Update_Step_Report("Click on AC button in Asset Details screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_AC").toString(), client));
				EF.Update_Step_Report("Verify The Parental Guidelines(TV-Y ,TV-Y7, TV-G, TV-PG, TV-14, TV-MA) in the Asset Detail", "Sucessfully Verified  The Parental Guidelines(TV-Y ,TV-Y7, TV-G, TV-PG, TV-14, TV-MA) in the Asset Detail|| Parental Guidelines(TV-Y ,TV-Y7, TV-G, TV-PG, TV-14, TV-MA) Verification failed in Full Asset Details Screen",ST.NativeElementFound(ObjectCollection.get("RatingsOverview_TVRatings").toString(), client));
				// Click on Back button
				EF.Update_Step_Report("Click on Back button in Asset Details screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
			}
			else
				EF.Update_Step_Report("Check for AC button in Asset Details screen", "AC button is available||AC button is not available", false);
			
			EF.Update_TC_Report();
			
			// 26****************** Verify that the Movie Assets display Ratings (G,PG,PG-13,R) in Full Asset Detail *****************************
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_PG").toString(), client))
			{
				EF.Update_Step_Report("Check for PG button in Asset Details screen", "AC button is available||AC button is not available",true);
				EF.Update_Step_Report("Click on PG button in Asset Details screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_PG").toString(), client));
				client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("RatingsOverview_MovieRatings").toString(), 0, 1000, 4, false);
				EF.Update_Step_Report("Verify the Movie Assets display Ratings (G,PG,PG-13,R) in Full Asset Detail", "Sucessfully Verified  the Movie Assets display Ratings (G,PG,PG-13,R) in Full Asset Detail|| the Movie Assets display Ratings (G,PG,PG-13,R) Verification failed in Full Asset Details Screen",ST.NativeElementFound(ObjectCollection.get("RatingsOverview_MovieRatings").toString(), client));
			}
			else
				EF.Update_Step_Report("Check for PG button in Asset Details screen", "AC button is available||AC button is not available",false);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// 27****************** Verify the Parental Ratings in Full Asset Detail screen************************************************************
			client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("RatingsOverview_ParentalRatings").toString(), 0, 1000, 4, false);
			EF.Update_Step_Report("Verify the Parental Ratings section in Ratings popover", "Sucessfully Verified  the Parental Ratings in Ratings Popover|| Parental Ratings Verification failed in Ratings Popover",ST.NativeElementFound(ObjectCollection.get("RatingsOverview_ParentalRatings").toString(), client));
			EF.Update_TC_Report();
			
			// 28****************** Verify the TVY Ratings under TV Ratings in Rating Overview Popover ***************************************
			client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("RatingsOverview_TVY").toString(), 0, 1000, 4, false);
			String TVY = ST.NativeGetElementText(ObjectCollection.get("RatingsOverview_TVY").toString(),0, client);
			if (!(TVY.contains("All children")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify the TV Y- All children in TV Ratings Popover", "Sucessfully Verified  TV Y- All children in TV Ratings Popover|| TV Y- All children Verification failed in TV Ratings Popover",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// 29****************** Verify the TVY7 Ratings under TV Ratings in Rating Overview Popover ***************************************
			String TVY7 = ST.NativeGetElementText(ObjectCollection.get("RatingsOverview_TVY7").toString(),0, client);
			if (!(TVY7.contains("Directed to older children")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify the TV Y7- Directed to older children in TV Ratings Popover", "Sucessfully Verified  TV Y7- Directed to older children in TV Ratings Popover|| TV Y7- Directed to older children Verification failed in TV Ratings Popover",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// 30****************** Verify the TVY7FV Ratings under TV Ratings in Rating Overview Popover ***************************************
			String TVY7FV = ST.NativeGetElementText(ObjectCollection.get("RatingsOverview_TVY7FV").toString(),0, client);
			if (!(TVY7FV.contains("Directed to older children - fantasy violence")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify the TV Y7 FV- Directed to older children- fantasy violence in TV Ratings Popover", "Sucessfully Verified  TV Y7 FV- Directed to older children- fantasy violence in TV Ratings Popover|| TV Y7 FV- Directed to older children- fantasy violence Verification failed in TV Ratings Popover",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// 31****************** Verify the TVG Ratings under TV Ratings in Rating Overview Popover ***************************************
			String TVG = ST.NativeGetElementText(ObjectCollection.get("RatingsOverview_TVG").toString(),0, client);
			if (!(TVG.contains("General audience")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify the TV G- General audience in TV Ratings Popover", "Sucessfully Verified  TV G- General audience in TV Ratings Popover|| TV G- General audience Verification failed in TV Ratings Popover",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// 32****************** Verify the TVPG Ratings under TV Ratings in Rating Overview Popover ***************************************
			String TVPG = ST.NativeGetElementText(ObjectCollection.get("RatingsOverview_TVPG").toString(),0, client);
			if (!(TVPG.contains("Parental guidance suggested")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify the TV PG- Parental guidance suggested in TV Ratings Popover", "Sucessfully Verified  TV PG- Parental guidance suggested in TV Ratings Popover|| TV PG- Parental guidance suggested Verification failed in TV Ratings Popover",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// 33****************** Verify the TV14 Ratings under TV Ratings in Rating Overview Popover ***************************************
			String TV14 = ST.NativeGetElementText(ObjectCollection.get("RatingsOverview_TV14").toString(),0, client);
			if (!(TV14.contains("Parents strongly cautioned")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify the TV 14- Parents strongly cautioned in TV Ratings Popover", "Sucessfully Verified TV 14- Parents strongly cautioned in TV Ratings Popover|| TV 14- Parents strongly cautioned Verification failed in TV Ratings Popover",Local_Result);
			Local_Result = true;
			EF.Update_TC_Report();
			
			// 34****************** Verify the TVMA Ratings under TV Ratings in Rating Overview Popover ***************************************
			String TVMA = ST.NativeGetElementText(ObjectCollection.get("RatingsOverview_TVMA").toString(),0, client);
			if (!(TVMA.contains("Mature audience only")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify the TV MA- Mature audience only in TV Ratings Popover", "Sucessfully Verified TV MA- Mature audience only in TV Ratings Popover|| TV MA- Mature audience only Verification failed in TV Ratings Popover",Local_Result);
			Local_Result = true;
			// Click on Back button
			EF.Update_Step_Report("Click on Back button in Asset Details screen", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
			EF.Update_TC_Report();
					
			// 35******************** Verify that the Remind Me option exists besides the  Send to TV section on Asset detail page **********************
			
			EF.Update_Step_Report("Check for REMIND ME option in Asset Details screen", "REMIND ME option is available||REMIND ME option is not available",
					(ST.NativeElementFound(ObjectCollection.get("AssetDetails_RemindME").toString(), client)));
			EF.Update_TC_Report();
			
			// 36****************** Verify that the Rotten Tomatoes displays in the Full Asset Detail Screen ***************************************
			EF.Update_Step_Report("Check for Rotten Tomatoes button in Asset Details screen", "Rotten Tomatoes button is available||Rotten Tomatoes is not available for the selected asset",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_RottenTomatoes").toString(), client));
			EF.Update_TC_Report();
			
			// 37************* Verify that the Watchlist option is available for assets in On Demand screen  **********************************
			EF.Update_Step_Report("Check for Watchlist Icon in Asset Details screen", "Watchlist Icon is available||Watchlist Icon is not available",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_WatchlistIcon").toString(), client));
			EF.Update_TC_Report();
			
			// 38********** Verify that the Start On Demand icon is available for assets from On Demand section ******************************
			EF.Update_Step_Report("Check for Start OnDemand Icon in Asset Details screen", "Start OnDemand Icon is available||Start OnDemand Icon is not available",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_StartONDemandIcon").toString(), client));
			EF.Update_TC_Report();
			
		
			// 39****************** Verifications for TV Asset **************************************************************************************
			// ****************** Verify that the Episode Details of the Selected Asset is displaying in the Asset Detail. ************************
			
			// Click on Back button
			EF.Update_Step_Report("Check for Back button in Asset Details screen", "Back button is available||Back button is not available",
					ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
			
			
			// For iOS
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				EF.Update_Step_Report("Click on TV option in On Demand screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
			}
			
			// For Android
			else
			{
				EF.Update_Step_Report("Click on On Demand Dropdown box in On Demand Screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
				EF.Update_Step_Report("Click on TV option in On Deamand Dropdown Box", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
			}
			
			
			EF.Update_Step_Report("Click on Movie Poster Art", "Clicked sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("OnDemand_TV_PosterArt").toString(), client));
			EF.Update_Step_Report("Check for Episodes in Asset Details screen", "Episodes are available||Episodes are not available",
					client.waitForElement("NATIVE", ObjectCollection.get("AssetDetails_EpisodePanellist").toString(), 0, 6000));
			EF.Update_TC_Report();
			
			// 40******************* Verify the Character name in Series Details Screen *************************************
			EF.Update_Step_Report("Check for Character Name in Series Details Screen", "Character Name is available in Series Details Screen||Character Name is not available in Series Details Screen",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_Character_Name").toString(), client));
			EF.Update_Step_Report("Click on Episode Name in Series Details Screen", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("AssetDetails_EpisodePanellist").toString(), client));
			EF.Update_TC_Report();
			
			// 41******************* Verify the Character name in Episodes Details screen ************************************
			EF.Update_Step_Report("Check for Character Name in Episode Details Screen", "Character Name is available in Episode Details Screen||Character Name is not available in Episode Details Screen",
					client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("AssetDetails_Character_Name").toString(), 1000, 2, false));
			EF.Update_TC_Report();
			
			// 42**************** Verify that the Season Details (Season X) displays in the Full Asset Detail Screen. *********************************
			EF.Update_Step_Report("Check for Seasons in Asset Details screen", "Seasons are available||Seasons are not available",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_SeasonTitle").toString(), client));
			EF.Update_Step_Report("Check for Seasons Dropdown in Asset Details screen", "Seasons drop down is available||Seasons dropdown is not available",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_SeasonDropDownButton").toString(), client));
			EF.Update_Step_Report("Click on Season Dropdown button", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("AssetDetails_SeasonDropDownButton").toString(), client));
			EF.Update_Step_Report("Check for Seasons popover Asset Details screen", "Seasons popover is available||Seasons popover is not available",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_SeasonsPopover").toString(), client));
			EF.Update_Step_Report("Click on Back button in Asset Details screen", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
			EF.Update_TC_Report();
			
			// 43****** Verify whether user is able to view Episode number, title and season number for the related episodes In get listings modal ***
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_OtherTimes").toString(), client))
			{
				EF.Update_Step_Report("Click on Other Times in Asset Details screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_OtherTimes").toString(), client));
				if(client.isElementFound("NATIVE", ObjectCollection.get("AssetDetails_GetListings_ChannelNumber").toString(), 2))
					EF.Update_Step_Report("Check for Channel Number in Get Listings Model", "Channel Number in Get Listing Model is available||Channel Number in Get Listings Modal is not available", true);
				else
					EF.Update_Step_Report("Check for Channel Number in Get Listings Model", "Channel Number in Get Listing Model is available||Channel Number in Get Listings Modal is not available", false);
			}
			else
				EF.Update_Step_Report("Click on Other Times in Asset Details screen", "Clicked successfully||Other times is not available", false);
			
			EF.Update_TC_Report();
			
			//44******* Verify whether user is able to view Episode title  In get listings modal ***
			if(client.isElementFound("NATIVE", ObjectCollection.get("AssetDetails_GetListings_title").toString(), 3))
				EF.Update_Step_Report("Check for title in Get Listings Model", "title in Get Listing Model is available||title in Get Listings Modal is not available", true);
			else
				EF.Update_Step_Report("Check for title in Get Listings Model", "title in Get Listing Model is available||title in Get Listings Modal is not available", false);
			EF.Update_TC_Report();
			
			//45******* Verify whether user is able to view Season number In get listings modal ***
			if(client.isElementFound("NATIVE", ObjectCollection.get("AssetDetails_GetListings_SeasonNumber").toString(), 3))
				EF.Update_Step_Report("Check for Season Number in Get Listings Model", "Season Number in Get Listing Model is available||Season Number in Get Listings Modal is not available",true);
			else
				EF.Update_Step_Report("Check for Season Number in Get Listings Model", "Season Number in Get Listing Model is available||Season Number in Get Listings Modal is not available",false);
			EF.Update_TC_Report();
			
			//46******* Verify whether user is able to view Episode number In get listings modal ***
			if(client.isElementFound("NATIVE", ObjectCollection.get("AssetDetails_GetListings_EpisodeNumber").toString(), 3))
				EF.Update_Step_Report("Check for Episode Number in Get Listings Model", "Episode Number in Get Listing Model is available||Episode Number in Get Listings Modal is not available",true);
			else
				EF.Update_Step_Report("Check for Episode Number in Get Listings Model", "Episode Number in Get Listing Model is available||Episode Number in Get Listings Modal is not available",false);
			EF.Update_TC_Report();
			
			// ************* My Library Screen Validations **************************
			//47************* Verify whether on tapping an On demand asset from Most Popular On Demand panel takes user to the OnDemand Asset detail page *******
			this.Rest2homescreen();
			EF.Update_Step_Report("Navigate to On Demand", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
			
			if(client.swipeWhileNotFound("Down", 700, 2000, "NATIVE", ObjectCollection.get("BrowseAll_OnDemand_MediaBanner").toString(), 0, 1000, 5, false))
			{
				EF.Update_Step_Report("Click on Most Popular On Demand Poster Art", "Clicked sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("BrowseAll_OnDemand_Asset").toString(), client));
				client.waitForElement("NATIVE", ObjectCollection.get("OnDemand_title").toString(), 0, 5000);
				String OnDemandtitle = ST.NativeGetElementText(ObjectCollection.get("OnDemand_title").toString(),0, client);
				if (OnDemandtitle.contains("On Demand"))
					EF.Update_Step_Report("Verify whether on tapping an On demand asset from Most Popular On Demand panel takes user to the OnDemand Asset detail page", "Sucessfully Verified  on tapping an On demand asset from Most Popular On Demand panel takes user to the OnDemand Asset detail page|| Tapping an On demand asset from Most Popular On Demand panel takes user to the OnDemand Asset detail page Verification failed",true);
				else
					EF.Update_Step_Report("Verify whether on tapping an On demand asset from Most Popular On Demand panel takes user to the OnDemand Asset detail page", "Sucessfully Verified  on tapping an On demand asset from Most Popular On Demand panel takes user to the OnDemand Asset detail page|| Tapping an On demand asset from Most Popular On Demand panel takes user to the OnDemand Asset detail page Verification failed",false);
			}
			else
			{
				EF.Update_Step_Report("Verify whether the Most popular on demand panel is getting displlayed", "On demand shelf is getting displayed||On demand shelf is not getting displayed", false);
			}
			EF.Update_TC_Report();
			
			// 48************ Verify that the Watchlist option is available for assets displayed under Most Popular On Demand panel in Home screen **********
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_WatchlistIcon").toString(), client))
				EF.Update_Step_Report("Check for Watchlist Icon in Asset Details screen", "Watchlist Icon is available||Watchlist Icon is not available",true);
			else
				EF.Update_Step_Report("Check for Watchlist Icon in Asset Details screen", "Watchlist Icon is available||Watchlist Icon is not available",false);
			
			EF.Update_TC_Report();
			
			// 49**** Verify that the Cast Asset Cover Art displays with the cast name, character name, and is available to be selected. When a cast asset is tapped, the mini biography should display for the selected cast. Ensure that a filmography is listed in this pop-over as well.  ****
			if(ST.NativeElementFound(ObjectCollection.get("CastCrew_Title").toString(), client))
				EF.Update_Step_Report("Check for Cast&Crew Section in Asset Details screen", "Cast&Crew Section is available||Cast&Crew Section is not available", true);
			else
				EF.Update_Step_Report("Check for Cast&Crew Section in Asset Details screen", "Cast&Crew Section is available||Cast&Crew Section is not available", false);
			
			EF.Update_TC_Report();
			
			// 50************ Verification for the Cast Name Under Cast&Crew Section. ***************
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_CastName").toString(), client))
				EF.Update_Step_Report("Check for Cast Name under Cast&Crew Section", "Cast Name is available Under Cast&Crew Section||Cast Name is not available Under Cast&Crew Section", true);
			else
				EF.Update_Step_Report("Check for Cast Name under Cast&Crew Section", "Cast Name is available Under Cast&Crew Section||Cast Name is not available Under Cast&Crew Section", false);
			
			EF.Update_TC_Report();
			
			// 51*********** Verification for the Character Name under Cast&Crew Section ********************
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_CastName").toString(), client))
			{
				EF.Update_Step_Report("Check for Character Name under Cast&Crew Section", "Character Name is available Under Cast&Crew Section||Character Name is not available Under Cast&Crew Section",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Character_Name").toString(), client));
				String AssetDetailsCharacterName = ST.NativeGetElementText(ObjectCollection.get("AssetDetails_CastName").toString(),0, client);
				EF.Update_Step_Report("Click on Cast&Crew CoverArt in Asset Details screen", "Clicked successfully||Click failed", ST.NativeClick(ObjectCollection.get("CastCrew_CoverArt").toString(), client));
				if(ST.NativeElementFound(ObjectCollection.get("Filmography_Title").toString(), client))
				{
					EF.Update_Step_Report("Check for Filmography title in Filmography popover", "Filmography title is available in Filmography popover||Filmography title is not available in Filmography popover",true);
					String FilmographyCharacterName = ST.NativeGetElementText(ObjectCollection.get("Filmography_CastName").toString(),0, client);
					if (FilmographyCharacterName.equalsIgnoreCase(AssetDetailsCharacterName))
						EF.Update_Step_Report("Verify Character Name under Cast&Crew Section", "Sucessfully Verified  Character Name under Cast&Crew Section|| Character Name under Cast&Crew Section Verification failed",true);
					else
						EF.Update_Step_Report("Verify Character Name under Cast&Crew Section", "Sucessfully Verified  Character Name under Cast&Crew Section|| Character Name under Cast&Crew Section Verification failed",false);
					ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client);
				}
				else
				{
					EF.Update_Step_Report("Check for Filmography title in Filmography popover", "Filmography title is available in Filmography popover||Filmography title is not available in Filmography popover",false);
				}
			}
			else
				EF.Update_Step_Report("Check for Character Name under Cast&Crew Section", "Character Name is available Under Cast&Crew Section||Character Name is not available Under Cast&Crew Section",false);
			EF.Update_TC_Report();
			
			// 52************ Verify that all Photos from the selected Asset display in the Photos Tab. *****************************************************
			
			if(client.swipeWhileNotFound("Down", 200, 2000, "NATIVE",ObjectCollection.get("AssetDetails_Photes_Image").toString(), 0, 1000, 5, false))
			{
				EF.Update_Step_Report("Check for Photes Section in Asset Details screen", "Photos Section is available||Photos Sectiion is not available", ST.NativeElementFound(ObjectCollection.get("AssetDetails_Photes_Section").toString(), client));
				EF.Update_Step_Report("Check for Photes image under Photes Section in Asset Details screen", "Photes images under Photes Section is available||Photes Images under Photes Sectiion is not available", true);
			}
			else
				EF.Update_Step_Report("Check for Photes image under Photes Section in Asset Details screen", "Photes images under Photes Section is available||Photes Images under Photes Sectiion is not available", false);
			
			EF.Update_TC_Report();
			
			// 53************ When a photo is tapped, it should expand to a larger size. ********************************************************************
			if(client.swipeWhileNotFound("Down", 200, 2000, "NATIVE",ObjectCollection.get("AssetDetails_Photes_Image").toString(), 0, 1000, 5, false))
			{
				EF.Update_Step_Report("Click on Photes Image under Photes Section in Full Asset Details", "Clicked successfully||Click failed", ST.NativeClick(ObjectCollection.get("AssetDetails_Photes_Image").toString(), client));
				if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_Photes_Enlarged").toString(), client))
				{
					EF.Update_Step_Report("Check for Enlarged Photes", "Photes should be Enlarged||Photes should not be Enlarged", true);
					ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client);
				}
				else
					EF.Update_Step_Report("Check for Enlarged Photes", "Photes should be Enlarged||Photes should not be Enlarged", false);
			}
			else
			{
				EF.Update_Step_Report("Check for Photes image under Photes Section in Asset Details screen", "Photes images under Photes Section is available||Photes Images under Photes Sectiion is not available", false);
			}
			EF.Update_TC_Report();
			
			// 54********************* More Like This Assets should display with Cover Art. *******************************
			if(client.swipeWhileNotFound("Down", 200, 2000, "NATIVE",ObjectCollection.get("AssetDetails_MorelikeThisSection").toString(), 0, 1000, 5, false))
			{
				EF.Update_Step_Report("Check for More Like This Section in Asset Details screen", "Successfully Verified More Like This Section in Asset Details screen||More Like This Section in Asset Details screen Verification failed", true);
				EF.Update_Step_Report("Check for Poster art Under More Like This Section in Asset Details screen", "Poster Art Under More Like This Section is available||Poster Art Under More Like This Sectiion is not available",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_MorelikeThisSection_PosterArt").toString(), client));
			}
			else
			{
				EF.Update_Step_Report("Check for More Like This Section in Asset Details screen", "Successfully Verified More Like This Section in Asset Details screen||More Like This Section in Asset Details screen Verification failed", false);
			}
			
			EF.Update_TC_Report();
			
			// 55********* Verify whether on tapping an On demand asset from Watchlist panel takes user to the OnDemand Asset detail page ******************
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_MorelikeThisSection_PosterArt").toString(), client))
			{
				EF.Update_Step_Report("Verify whether the related assets are getting displayed in More like this section", "Related assets are displayed under more like this option||Related assets are not displayed", ST.NativeClick(ObjectCollection.get("AssetDetails_MorelikeThisSection_PosterArt").toString(), client));
				EF.Update_Step_Report("Verify whether the user is redirected to asset details page on clicking more like this poster", "User is redirected to asset details page||User is not redirected to asset details page", ST.NativeElementFound(ObjectCollection.get("AssetDetails_ProgramTitle").toString(), client));
				EF.Update_Step_Report("Click on Back button in Asset Details screen", "Clicked successfully||Click failed", ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
			}
			else
			{
				EF.Update_Step_Report("Verify whether the related assets are getting displayed in More like this section", "Related assets are displayed under more like this option||Related assets are not displayed", false);
			}
			
			EF.Update_TC_Report();
			
			// 56******* Verify that the Asset detail page includes the following for a currently airing asset selected from My Favorites panel in Home Screen - Play button in Cover art *************** //
			// Click on Asset present under Favorites panel
			this.Rest2homescreen();
			if(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Home_Favorites_Container").toString(), 0, 1000, 4, false))
			{
				ST.NativeClick(ObjectCollection.get("Home_Favorites_ChannelName").toString(), client);
				EF.Update_Step_Report("On Tapping asset listed under favorites should redirect to Asset details page", "User navigated to asset details page||User is not navigated to asset details page", ST.NativeElementFound(ObjectCollection.get("AssetDetails_ProgramTitle").toString(), client));
				EF.Update_Step_Report("Check for Play Icon Under Poster Art in Asset Details screen", "Play Icon under Poster Art is available||Play Icon Under Poster Art is not available", ST.NativeElementFound(ObjectCollection.get("AsstDetails_PosterArt_PlayIcon").toString(), client));
			}
			else
				EF.Update_Step_Report("Check for Play Icon Under Poster Art in Asset Details screen", "Play Icon under Poster Art is available||Favourite shelf is not available", false);
			
			EF.Update_TC_Report();
			
			// 57******** Verify that the Asset detail page displays  Play button  both in cover art and under Watch on Tablet  if  'Watch on Tablet' option is available *********
			// Click on Asset present under Favorites panel
			//Local_Result=client.swipeWhileNotFound("Down", 700, 2000, "NATIVE", "//*[@class='UITableViewCellContentView']//*[@class='ContentCollectionViewCell']", 0, 1000, 5, true);
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_PlayIcon").toString(), client))
			{
				EF.Update_Step_Report("Check for Play Icon in Asset Details screen", "Play Icon is available||Play Icon is not available", true);
				EF.Update_Step_Report("Click on Back button in Asset Details screen", "Clicked successfully||Click failed", ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
			}
			else
			{
				EF.Update_Step_Report("Check for Play Icon in Asset Details screen", "Play Icon is available||Play Icon is not available", false);
		}
				
			this.Rest2homescreen();
		}
		
		/*****************************************************************************************************/
		//								Module : Video Player 
		/*****************************************************************************************************/
		public boolean VideoPlayer_AssetContainer()
		{
			boolean bFlag=true;
			try
			{
				//VideoPlayer_Asswet
				bFlag=ST.NativeElementFound(ObjectCollection.get("Home_LiveTV").toString(), client);
				EF.Update_Step_Report("User should be navigated to LiveTV page", "User navigated to LiveTV page||User is not navigated to LiveTV page", bFlag);
				
				
				if(client.isElementFound("NATIVE", ObjectCollection.get("VideoPlayer_AssetContainer").toString(), 0))
				{
					ST.NativeClick(ObjectCollection.get("VideoPlayer_AssetContainer").toString(), client);
					bFlag=client.isElementFound("NATIVE", ObjectCollection.get("VideoPlayer_AssetContainer").toString(), 0);
					EF.Update_Step_Report("User should be navigated to LiveTV page", "User navigated to LiveTV page||User is not navigated to LiveTV page", bFlag);
				}
				else
				{
					
				}
			}
			catch(Exception Ex)
			{
				System.err.println("Error Occured in Fn_LiveTV view");
			}
			return bFlag;
		}
		
		/*****************************************************************************************************/
		//								Module : Video Player END
		/*****************************************************************************************************/
		
		/*****************************************************************************************************
		 * Author : Chandhini
		 * Function : Meta data validation - Single Filter
		 ****************************************************************************************************/
		public boolean Fn_SingleFilter(String strVal)
		{
			boolean bFlag=false;
			try
			{
				this.Fn_Guide_ChooseFilter(strVal);
				this.Fliter_Channel_Validation();
			}
			catch(Exception Ex)
			{
				System.err.println("Error caused due to:"+Ex.getCause());
			}
			return bFlag;
		}
		
		/*****************************************************************************************************
		 * Author : Chandhini
		 * Module : Live TV
		 ****************************************************************************************************/
		
		  public void Fn_LiveTv_View()
			{
				Fn_Com_Navigate("Home_LiveTV");
				EF.Update_Step_Report("User Must be navigated to Live Tv screen Grid view screen by default", "User navigated to Live Tv screen Grid view screen by default||User " +
						"is not navigated to Live Tv screen Grid view screen by default", client.isElementFound("NATIVE", ObjectCollection.get("LiveTV_Container_GridView").toString()));
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_ListView").toString(), client);
				EF.Update_Step_Report("Must display the contents of Live TV in List view", "The contents of Live TV is displayed in List view||The contents of Live TV are not displayed in List view", 
						client.isElementFound("NATIVE", ObjectCollection.get("LiveTV_Container_ListView").toString()));
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_GridView").toString(), client);
				EF.Update_Step_Report("Must be back to Live TV Grid view screen with all Live Streaming Assets with Cover Art", "User is back to Live TV Grid view screen with all Live Streaming Assets with Cover Art||" +
						"User is not back to Live TV Grid view screen with all Live Streaming Assets with Cover Art",client.isElementFound("NATIVE", ObjectCollection.get("LiveTV_Container_GridView").toString()));
				
				
			}
			public void Fn_LiveTV_Lable()
			{
				//Auto_LiveTvScreen_002
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_GridView").toString(), client);
				EF.Update_Step_Report("User Must be navigated to Live Tv screen Grid view screen by default", "User navigated to Live Tv screen Grid view screen by default||User " +
						"is not navigated to Live Tv screen Grid view screen by default", client.isElementFound("NATIVE", ObjectCollection.get("LiveTV_Container_GridView").toString()));
				EF.Update_Step_Report("Program Time Slot must be available in Grid View", "Program Time Slot is available||Program Time Slot is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_GridView_TimeSpan").toString(), client));
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_ListView").toString(), client);
				client.sleep(1000);
				client.pressWhileNotFound("NATIVE", ObjectCollection.get("LiveTV_Container_ListView").toString(), 0, ObjectCollection.get("LiveTV_ListView_PlayButton").toString(), 0, 10000, 0);
				EF.Update_Step_Report("Program Time Slot must be available", "Program Time Slot is available||Program Time Slot is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_ListView_TimeSpan").toString(), client));	
				EF.Update_TC_Report();
				//Auto_LiveTvScreen_003
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_GridView").toString(), client);
		
				EF.Update_Step_Report("User Must be navigated to Live Tv screen Grid view screen by default", "User navigated to Live Tv screen Grid view screen by default||User " +
						"is not navigated to Live Tv screen Grid view screen by default",client.waitForElement("NATIVE",ObjectCollection.get("LiveTV_Container_GridView").toString(),0,10000));
				EF.Update_Step_Report("Channel Logo must be available", "Channel logo is available||Channel logo is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_GridView_ChannelName").toString(), client));
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_ListView").toString(), client);
				EF.Update_Step_Report("Channel Logo must be available", "Channel logo is available||Channel logo is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_ListView_ChannelLogo").toString(), client));
				EF.Update_TC_Report();
				//Auto_LiveTvScreen_004
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_GridView").toString(), client);
				client.sleep(3000);
				EF.Update_Step_Report("User Must be navigated to Live Tv screen Grid view screen by default", "User navigated to Live Tv screen Grid view screen by default||User " +
						"is not navigated to Live Tv screen Grid view screen by default", client.isElementFound("NATIVE", ObjectCollection.get("LiveTV_Container_GridView").toString()));
				EF.Update_Step_Report("Program Title must be available", "Program Title is available||Program Title is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_GridView_ProgramTitle").toString(), client));
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_ListView").toString(), client);
				EF.Update_Step_Report("Program Title must be available", "Program Title is available||Program Title is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_ListView_ProgramTitle").toString(), client));
				EF.Update_TC_Report();
				//Auto_LiveTvScreen_005
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_ListView").toString(), client);
				client.sleep(3000);
				EF.Update_Step_Report("Channel Logo must be available", "Channel logo is available||Channel logo is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_ListView_ChannelLogo").toString(), client));
				EF.Update_Step_Report("Program Title must be available", "Program Title is available||Program Title is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_ListView_ProgramTitle").toString(), client));
				EF.Update_Step_Report("Program Time Slot must be available", "Program Time Slot is available||Program Time Slot is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_ListView_TimeSpan").toString(), client));
				EF.Update_Step_Report("Program Description must be available", "Program Description is available||Program Description is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_ListView_Description").toString(), client));
				EF.Update_Step_Report("Play Button must be available", "Play button is available||Play Button is not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_ListView_PlayButton").toString(), client));
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_GridView").toString(), client);
			}
			
			public void Fn_LiveTV_PanelCheck()
			{
				
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_GridView").toString(), client);
				EF.Update_Step_Report("User Must be navigated to Live Tv screen Grid view screen by default", "User navigated to Live Tv screen Grid view screen by default||User " +
						"is not navigated to Live Tv screen Grid view screen by default", client.isElementFound("NATIVE", ObjectCollection.get("LiveTV_Container_GridView").toString()));
				EF.Update_Step_Report("Must have the Live TV Network Panel below the cover art of the Live TV Grid view screen", 
						"Live TV Networks panel is available||Live TV Networks panel is not available", 
						client.swipeWhileNotFound("Down", 100, 2000, "NATIVE",ObjectCollection.get("LiveTV_Panel_Networks/Live").toString(), 0, 1000, 10, true));
			}
			public void Fn_LiveTv_Customize()
			{
			
				int xOffset,yOffset,yOffset1,yOffset2;
				String panel1,panel2,panel3,panel4;
				EF.Update_Step_Report("User Must be taken  to Live TV section", "User is taken to taken to LiveTV section" +
						"||User is not taken to Live Tv section", this.Fn_Com_Navigate("Home_LiveTV"));
				EF.Update_Step_Report("Customize panel must be present with a blue '+' icon at the bottom of the screen", "Custmize panel is present with blue icon at the bottom ||Customize panel is not present at the bottom",
						client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("LiveTv_CstomizeBtn").toString(), 0, 1000, 5, true));
				EF.Update_Step_Report("Should display the customize modal", "Customize Modal is available||Customize modal is not available", 
						ST.NativeElementFound(ObjectCollection.get("Home_PopOver_Done").toString(), client));
				//Rearranging using Rearrange icon
				panel1=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_FirstPanleName").toString(), 0);
				panel2=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_FirstPanleName").toString(), 1);
				xOffset=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, "X"));
				yOffset1=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, "Y"));
				yOffset2=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 1, "Y"));
				yOffset=yOffset2-yOffset1;
				client.setDragStartDelay(500);
				client.drag("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, xOffset, yOffset);
				client.sleep(1000);
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 10000);
				this.Fn_Com_Navigate("Home_MyLibrary");
				this.Fn_Com_Navigate("Home_LiveTV");
				client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 10000);
				panel3=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_FirstPanleName").toString(), 0);
				panel4=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_FirstPanleName").toString(), 1);
				EF.Update_Step_Report("Should be able to rearrange the panels accordingly", "User is able to rearrange the panels || User is not able to rearrange the panles",
							panel3.equalsIgnoreCase(panel2) && panel4.equalsIgnoreCase(panel1));
				
				
			}
			public void Fn_LiveTV_ListView_PlayIcon_Check()
			{
				this.Fn_Com_Navigate("Home_LiveTV");
				client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 10000);
				if(client.isElementFound("NATIVE", ObjectCollection.get("LiveTV_Container_GridView").toString()))
				{
					ST.NativeClick(ObjectCollection.get("LiveTV_Menu_ListView").toString(), client);
					client.sleep(1000);
				}
				client.pressWhileNotFound("NATIVE", ObjectCollection.get("LiveTV_Container_ListView").toString(), 0, ObjectCollection.get("LiveTV_ListView_PlayButton").toString(), 0, 10000, 0);
				EF.Update_Step_Report("Play icon should be available for all the streaming asset", "Play icon available||Play icon not available",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_ListView_PlayButton").toString(), client));
				ST.NativeClick(ObjectCollection.get("LiveTV_ListView_PlayButton").toString(), client);
				client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
				EF.Update_Step_Report("User should be navigated to the Live screen of the asset", "User is navigated to Live Screening||User is not navigated to Live Screening", 
						ST.NativeElementFound(ObjectCollection.get("KidZone_VideoPlayer_Controls_Container").toString(), client));
				
			}

			public void Fn_LiveTV_ListView_AssetCount()
			{
				Boolean bFlag=false;
				this.Fn_Com_Navigate("Home_LiveTV");
				EF.Update_Step_Report("Must be navigated to Live Tv  with Grid view screen by default.", "Navigated to Live TV Grid view by default||Navigation to Live TV Unsuccessful",
						ST.NativeElementFound(ObjectCollection.get("LiveTV_Container_GridView").toString(), client));
				ST.NativeClick(ObjectCollection.get("LiveTV_Menu_ListView").toString(), client);
				client.sleep(10000);
				if(ST.NativeElementFound(ObjectCollection.get("LiveTV_ListView_PlayButton").toString(), client))
				{
					ST.NativeClick( ObjectCollection.get("LiveTv_icon_collapse").toString(), client);
				}
				String str1=client.elementGetText("NATIVE", ObjectCollection.get("LiveTV_ListView_PanelCount").toString(), 0);
				int startIndex=str1.indexOf('(');
				System.out.println(startIndex);
				int lastIndex=str1.lastIndexOf(')');
				System.out.println(lastIndex);
				String subStr1	= str1.substring(startIndex+1, lastIndex);
				System.out.println(subStr1);
				try {

			            Integer.parseInt(subStr1);

			            bFlag = true;

			        } catch (NumberFormatException ne) {

			            bFlag = false;

			        }
			   EF.Update_Step_Report("Count should be available on the panel name", "Asset Count is available||Asset count is not availanble", bFlag);
				
				
			}
		
		/*****************************************************************************************************
		 										END - Live TV
		 ****************************************************************************************************/
		/*=================================================================================================================================*/
		//Sports Zone related methods
		/*=================================================================================================================================*/
			/*=================================================================================================================================*/
			//Sports Zone related methods
			/*=================================================================================================================================*/
			public boolean Fn_SportZone_OutofHome()
			{
				boolean bFlag=true;
				
//				bFlag=this.Fn_Login();
				EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
				EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", bFlag);
				EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", bFlag);
				bFlag=this.Fn_Com_Navigate("Home_SportZone");
				EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", bFlag);
				bFlag=(ST.NativeElementFound(ObjectCollection.get("SportZone_GamesOnTV").toString(), client) && ST.NativeElementFound(ObjectCollection.get("SportZone_AssetContent").toString(), client));
				EF.Update_Step_Report("Sportzone page should be displayed with the default view as Games on TV", "SportZone page is displayed Games on TV as default||SportZone page is not displayed Games on TV as default", bFlag);
				ST.NativeClick(ObjectCollection.get("SportZone_AssetContent").toString(), client);
				if(Current_OS=="Android")
				{
					ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
					EF.Update_Step_Report("Streaming should not be available", "Streaming is not available||There is no element that idetify that the streaming is not available", false);
				}
				else
				{
					ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
					bFlag=(!(ST.NativeElementFound(ObjectCollection.get("SportZone_Assetdetails_playbutton").toString(), client) && ST.NativeElementFound(ObjectCollection.get("SportZone_Assetdetails_notStremable").toString(), client)));
					EF.Update_Step_Report("Streaming should not be available", "Streaming option is not available||Streaming option is available", bFlag);
				}
				EF.Update_TC_Report();
				//Auto_SportZone_002
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				if(Current_OS=="Android")
				{
					client.elementSwipe("NATIVE",ObjectCollection.get("SportZone_AssetContent").toString(), 3, "Down", 300, 2000);
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should be able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Sportzone page should be displayed with the default view as Games on TV", "SportZone page is displayed Games on TV as default||SportZone page is not displayed Games on TV as default", true);
					EF.Update_Step_Report("Dragging down the page should display refresh loader", "iOS specific||Refresh loader is not displayed", true);
				}
				else
				{
					client.elementSwipe("NATIVE", ObjectCollection.get("SportZone_AssetContainer").toString(), 0, "Up", 0, 2000);
					bFlag=client.isElementFound("NATIVE", ObjectCollection.get("SportZone_BufferLoader").toString());
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Sportzone page should be displayed with the default view as Games on TV", "SportZone page is displayed Games on TV as default||SportZone page is not displayed Games on TV as default", true);
					EF.Update_Step_Report("Dragging down the page should display loading spindle", "Loading Spindle is displayed||Loading Spindle is not displayed", bFlag);
				}
				EF.Update_TC_Report();
				//Auto_SportZone_003
				client.sleep(5000);
				if(Current_OS=="Android")
				{
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Sportzone page should be displayed with the default view as Games on TV", "SportZone page is displayed Games on TV as default||SportZone page is not displayed Games on TV as default", true);
					EF.Update_Step_Report("Loading spindle should get diminished after the page is refreshed", "Loading Spindle got diminished after the page loaded||Loading Spindle not getting diminished after the page loaded", false);
				}
				else
				{
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Sportzone page should be displayed with the default view as Games on TV", "SportZone page is displayed Games on TV as default||SportZone page is not displayed Games on TV as default", true);
					client.sleep(3000);
					EF.Update_Step_Report("Loading spindle should get diminished after the page is refreshed", "Loading Spindle got diminished after the page loaded||Loading Spindle not getting diminished after the page loaded", !ST.NativeElementFound(ObjectCollection.get("SportZone_BufferLoader").toString(), client));
				}
				EF.Update_TC_Report();
				//Auto_SportZone_004
				EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
				EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
				EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
				ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("Home_OnDemand").toString(), client)&&ST.NativeElementFound(ObjectCollection.get("Home_SportZone").toString(), client)&&ST.NativeElementFound(ObjectCollection.get("Home_KidZone").toString(), client))
				{
					//Getting Y index value for all the 3 options
					String str1 = client.elementGetProperty("NATIVE", ObjectCollection.get("Home_KidZone").toString(), 0, "y");
					String str2 = client.elementGetProperty("NATIVE", ObjectCollection.get("Home_OnDemand").toString(), 0, "y");
					String str3 = client.elementGetProperty("NATIVE", ObjectCollection.get("Home_SportZone").toString(), 0, "y");
					//Parsing the string to integer
					int nY1=Integer.parseInt(str1); // KidZone y index
					int nY2=Integer.parseInt(str2); // OnDemand y index
					int nY3=Integer.parseInt(str3); // Sportzone y index
					if((nY3>nY2)&&(nY3<nY1))
					{
						EF.Update_Step_Report("Verify that Sports Zone menu is been added in home screen navigation drawer after On Demand and before Kid Zone", "SportZone is displayed between On Demand and KidZone||SportZone is not getting displayed between On Demand and KidZone", true);
					}
					else
					{
						EF.Update_Step_Report("Verify that Sports Zone menu is been added in home screen navigation drawer after On Demand and before Kid Zone", "SportZone is displayed between On Demand and KidZone||SportZone is not getting displayed between On Demand and KidZone", false);
					}
					ST.NativeClick(ObjectCollection.get("Home_PageList").toString(), client);
				}
				else
				{
					EF.Update_Step_Report("Verify that Sports Zone menu is been added in home screen navigation drawer after On Demand and before Kid Zone", "SportZone is displayed between On Demand and KidZone||SportZone is not getting displayed between On Demand and KidZone", false);
				}
				EF.Update_TC_Report();
				//Auto_SportZone_005
				EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
				EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
				EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
				EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
				bFlag=ST.NativeElementFound(ObjectCollection.get("SportZone_ThuuzRating").toString(), client); //Finding ThuuzRating
				EF.Update_Step_Report("Thuuz Rating should be displayed in the SportZone page", "Thuuz Rating is getting displayed in SportZone page||Thuuz Rating is not getting displayed in SportZone page", bFlag);
				bFlag=ST.NativeElementFound(ObjectCollection.get("SportZone_League&FullTeamName").toString(), client); //Finding Asset Container
				EF.Update_Step_Report("League Name|Full Team Name should be displayed in the SportZone page", "League Name|Full Team Name is getting displayed in SportZone page||League Name|Full Team Name is not getting displayed in SportZone page", bFlag);
				bFlag=ST.NativeElementFound(ObjectCollection.get("SportZone_In-GameTime").toString(), client);
				EF.Update_Step_Report("In-Game Time should be displayed in SportZone page", "In-Game Time is getting displayed in SportZone page||In-Game Time is not getting displayed in SportZone page", bFlag);
				if(ST.NativeElementFound(ObjectCollection.get("SportZone_NetworkLogo").toString(), client)||ST.NativeElementFound(ObjectCollection.get("SportZone_NetworkCallSign").toString(), client))
				{
					bFlag=true;
				}
				else
				{
					bFlag=false;
				}
				EF.Update_Step_Report("Network Logo or Network call sign should be displayed in SportZone page", "Network Logo or Network call sign is getting displayed in SportZone page||Network Logo or Network call sign is not getting displayed in SportZone page", bFlag);
				EF.Update_TC_Report();
				//Auto_SportZone_006
				EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
				EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
				EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
				EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
				bFlag=ST.NativeElementFound(ObjectCollection.get("SportZone_GamesOnTV").toString(), client); //Finding Game on TV
				EF.Update_Step_Report("Verify whether the section Games on TV is  available in the Sportszone tab", "The section Games on TV  is available in the Sportszone tab||The section Games on TV is not available in the Sportszone tab", bFlag);
				EF.Update_TC_Report();
				//Auto_SportZone_007
				EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
				EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
				EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
				EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
				if(ST.NativeElementFound(ObjectCollection.get("SportZone_Tomorrow").toString(), client))
				{
					client.swipe("Down", 100, 2000);
					bFlag=true;
					client.swipe("Up", 100, 2000);
				}
				else
				{
					bFlag=client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("SportZone_Tomorrow").toString(), 0, 1000, 5, false);
					client.swipeWhileNotFound("Up", 100, 2000, "NATIVE", ObjectCollection.get("SportZone_Today").toString(), 0, 1000, 5, false);
				}
				EF.Update_Step_Report("User should be able to swip the SportZone page", "Swipping functionlity is working in the Sportzone page||Swipping functionlity is not working in the Sportzone page", bFlag);
				this.Fn_Com_Navigate("Home_Settings");
				this.Fn_LogOut();
				return bFlag;
			}
			
			public boolean Fn_SportZone_InHome()
			{
				Boolean bFlag=true;
				//Auto_SportZone_008				
				if(this.Fn_Login())
				{
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", bFlag);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", bFlag);
					bFlag=this.Fn_Com_Navigate("Home_SportZone");
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", bFlag);
					//Need to capture the eliment only when there is a program going on live TODO
					EF.Update_TC_Report();
					//Auto_SportZone_009
					client.swipe("Down", 200, 2000);
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("On navigating to SportZone the Asset content should be listed", "Asset Content is listed down||Asset content is not listed", ST.NativeClick(ObjectCollection.get("SportZone_AssetContent").toString(), client));
					EF.Update_Step_Report("On Clicking the Asset content the Standard Asset details page should be displayed", "Standard Asset details page is displayed||Standard Asset details page is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Remindme").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					//Auto_SportZone_010
					this.Rest2homescreen();
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Tuzz Rating should be displayed in asset content", "Tuuz rating is displayed||Tuuz rating is not yet displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_ThuuzRating").toString(), client));
					EF.Update_Step_Report("League Name should be displayed in asset content", "League name is displayed||League name is not yet displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_League&FullTeamName").toString(), client));
					EF.Update_Step_Report("Team Logo should be displayed in asset content", "Team logo is displayed||Team Logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_TeamLogo").toString(), client));
					EF.Update_Step_Report("Team Name should be displayed in asset content", "Team Name is displayed||Team Name is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_League&FullTeamName").toString(), client));
					EF.Update_Step_Report("Game time should be displayed in asset content", "Game time is displayed||Game time is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_In-GameTime").toString(), client));
					EF.Update_Step_Report("Game date should be displayed in asset content", "Game date is displayed||Game date is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_In-GameDate").toString(), client));
					EF.Update_Step_Report("Network logo should be displayed in asset content", "Network logo is displayed||Network logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_NetworkLogo").toString(), client));
					EF.Update_Step_Report("Network call sign should be displayed in asset content", "Network call sign is displayed||Network call sign is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_NetworkCallSign").toString(), client));
					EF.Update_TC_Report();
					//Auto_SportZone_011
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					this.Rest2homescreen();
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Game on TV should be displayed in the SportZone landing page", "Game on TV is displayed in the sportzone landing page||Game on TV is not displayed in the sportzone landing page", ST.NativeElementFound(ObjectCollection.get("SportZone_GamesOnTV").toString(), client));
						EF.Update_Step_Report("Programs should be displayed in the SportZone landing page", "Programs is displayed in the sportzone landing page||Programs is not displayed in the sportzone landing page", ST.NativeElementFound(ObjectCollection.get("SportZone_Programs").toString(), client));
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("SportZone_ActionSpinner").toString(), client);
						EF.Update_Step_Report("Game on TV should be displayed in the SportZone landing page", "Game on TV is displayed in the sportzone landing page||Game on TV is not displayed in the sportzone landing page", ST.NativeElementFound(ObjectCollection.get("SportZone_GamesOnTV").toString(), client));
						EF.Update_Step_Report("Programs should be displayed in the SportZone landing page", "Programs is displayed in the sportzone landing page||Programs is not displayed in the sportzone landing page", ST.NativeElementFound(ObjectCollection.get("SportZone_Programs").toString(), client));
						ST.NativeClick(ObjectCollection.get("SportZone_GamesOnTV").toString(), client);
					}
					EF.Update_TC_Report();
					//Auto_SportZone_012
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					this.Rest2homescreen();
					EF.Update_Step_Report("Verify whether the unentitled assets are displayed in Asset container", "Unentitled asset is identified||Unentitled asset is not identified", client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("SportZone_UnentitledAsset").toString(), 0, 1000, 5, false));
					EF.Update_TC_Report();
					//Auto_SportZone_013
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					this.Rest2homescreen();
					EF.Update_Step_Report("The Asset details time should be listed in Asset container", "The Sports timing is displayed||The Sports timing is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_In-GameTime").toString(), client));
					EF.Update_TC_Report();
					//Auto_SportZone_014
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					this.Rest2homescreen();
					EF.Update_Step_Report("The Asset details time should be listed in Ascending order", "The Sports timing is listed in Ascending order||The Sports timing is not listed in Ascending order", Fn_SportZoneTimecalc());
					EF.Update_TC_Report();
					//Auto_SportZone_015
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					this.Rest2homescreen();
					EF.Update_Step_Report("The Spoilers should be displayed in the SportZone landing page", "Spolier is getting displayed||Spoiler is not getting displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersON").toString(), client));
					EF.Update_TC_Report();
					//Auto_SportZone_016
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("The Spoilers should be checked on in the SportZone landing page by default", "Spolier is checked on in the sportzone landing page||Spoiler is not checked on in the sportzone landing page", ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersON").toString(), client));
					EF.Update_TC_Report();
					//Auto_SportZone_017
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					Fn_SportZoneOngoingProg_check("SportZone_Live_Score");
					EF.Update_TC_Report();
					//Auto_SportZone_018
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersON").toString(), client))
					{
						EF.Update_Step_Report("The Spoilers should be checked on in the SportZone landing page by default", "Spolier is checked on in the sportzone landing page||Spoiler is not checked on in the sportzone landing page", ST.NativeClick(ObjectCollection.get("SportZone_SpoilersON").toString(), client));
						EF.Update_Step_Report("Check the functionality of spoiler on clicking it", "Spoiler is turned OFF||Spoiler is not functioning", ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersOFF").toString(), client));
						EF.Update_Step_Report("On tapping the Spoiler scores should be blurred", "Scores are blurred||Scores or not blurred", ST.NativeElementFound(ObjectCollection.get("SportZone_Live_BlurredImage").toString(), client));
					}
					else
					{
						EF.Update_Step_Report("Check the functionality of spoiler on clicking it", "Spoiler is turned off||Spoiler is not functioning", ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersOFF").toString(), client));
					}
					EF.Update_TC_Report();
					//Auto_SportZone_019
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersOFF").toString(), client))
					{
						EF.Update_Step_Report("The Spoilers should be checked on in the SportZone landing page by default", "Spolier is checked on in the sportzone landing page||Spoiler is not checked on in the sportzone landing page", ST.NativeClick(ObjectCollection.get("SportZone_SpoilersOFF").toString(), client));
						EF.Update_Step_Report("Check the functionality of spoiler on clicking it", "Spoiler is turned ON||Spoiler is not functioning", ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersON").toString(), client));
						EF.Update_Step_Report("On tapping the Spoiler scores should be blurred", "Scores are blurred||Scores or not blurred", ST.NativeElementFound(ObjectCollection.get("SportZone_Live_Score").toString(), client));
					}
					else
					{
						EF.Update_Step_Report("Check the functionality of spoiler on clicking it", "Spoiler is turned off||Spoiler is not functioning", ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersOFF").toString(), client));
					}
					EF.Update_TC_Report();
					//Auto_SportZone_020
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("On navigating to SportZone the Asset content should be listed", "Asset Content is listed down||Asset content is not listed", ST.NativeClick(ObjectCollection.get("SportZone_AssetContent").toString(), client));
					EF.Update_Step_Report("On Clicking the Asset content the Standard Asset details page should be displayed", "Standard Asset details page is displayed||Standard Asset details page is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Remindme").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					//Auto_SportZone_021
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("On navigating to SportZone the Asset content should be listed", "Asset Content is listed down||Asset content is not listed", ST.NativeClick(ObjectCollection.get("SportZone_OnGoing_Prog").toString(), client));
					EF.Update_Step_Report("On Clicking the Asset content the Standard Asset details page should be displayed", "Standard Asset details page is displayed||Standard Asset details page is not displayed", true);
					EF.Update_Step_Report("Check whether the score are displayed in the asset details page", "Scores are not displayed||Scores are displayed", true);
					//ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					//Auto_SportZone_022
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("On navigating to SportZone the Asset content should be listed", "Asset Content is listed down||Asset content is not listed", true);
					EF.Update_Step_Report("On Clicking the Asset content the Standard Asset details page should be displayed", "Standard Asset details page is displayed||Standard Asset details page is not displayed", true);
					EF.Update_Step_Report("Check whether the progress bar is getting displayed in thw asset detial page", "Progress bar is displayed||Progress bar is not dissplayed", ST.NativeElementFound(ObjectCollection.get("SportZone_AssDetail_ProgressBar").toString(), client));
					//ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					//Auto_SportZone_023
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					//TODO need to check whether on tapping on NO the pop up user is redirected to previous page
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					//Auto_SportZone_024
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Network logo should be displayed in asset content", "Network logo is displayed||Network logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_NetworkLogo").toString(), client));
					EF.Update_Step_Report("Team Logo should be displayed in asset content", "Team logo is displayed||Team Logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_TeamLogo").toString(), client));
					EF.Update_Step_Report("Team Name should be displayed in asset content", "Team Name is displayed||Team Name is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_League&FullTeamName").toString(), client));
					EF.Update_TC_Report();
					//Auto_SportZone_025
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Game time should be displayed in asset content", "Game time is displayed||Game time is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_In-GameTime").toString(), client));
					EF.Update_Step_Report("Game date should be displayed in asset content", "Game date is displayed||Game date is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_In-GameDate").toString(), client));
					EF.Update_Step_Report("Network logo should be displayed in asset content", "Network logo is displayed||Network logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_NetworkLogo").toString(), client));
					EF.Update_TC_Report();
					//Auto_SportZone_026
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("On navigating to SportZone the Asset content should be listed", "Asset Content is listed down||Asset content is not listed", ST.NativeClick(ObjectCollection.get("SportZone_AssetContent").toString(), client));
					EF.Update_Step_Report("On Clicking the Asset content the Standard Asset details page should be displayed", "Standard Asset details page is displayed||Standard Asset details page is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_AssetDetails_NWLogo").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					//Auto_SportZone_027
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Verify all the filter option in the SportZone", "NHL should be displayed||NHL is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NHL").toString(), client));
					EF.Update_Step_Report("Verify all the filter option in the SportZone", "MLB should be displayed||MLB is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_MLB").toString(), client));
					EF.Update_Step_Report("Verify all the filter option in the SportZone", "NBA should be displayed||NBA is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NBA").toString(), client));
					EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAF should be displayed||NCAAF is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NCAAF").toString(), client));
					EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAMB should be displayed||NCAAMB is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), client));
					EF.Update_Step_Report("Verify all the filter option in the SportZone", "NFL should be displayed||NFL is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NFL").toString(), client));
					EF.Update_TC_Report();
					//Auto_SportZone_028
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Verify ALL option in the SportZone", "ALL option should be displayed||ALL option is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client));
					EF.Update_TC_Report();
					//Auto_SportZone_029
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NFL should be displayed||NFL is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NFL").toString(), client));
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NFL").toString(), 0, client);
						client.syncElements(1000, 1000);
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}
						else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						client.syncElements(1000, 1000);
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NFL").toString(), 0, client);
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NFL should be displayed||NFL is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NFL").toString(), client));
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					EF.Update_TC_Report();
					//Auto_SportZone_030
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NBA should be displayed||NBA is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NBA").toString(), client));
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NBA").toString(), 0, client);
						client.syncElements(1000, 1000);
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}
						else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						client.syncElements(1000, 1000);
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NBA").toString(), 0, client);
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NBA should be displayed||NBA is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NBA").toString(), client));
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					EF.Update_TC_Report();
					//Auto_SportZone_031
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "MLB should be displayed||MLB is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_MLB").toString(), client));
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_MLB").toString(), 0, client);
						client.syncElements(1000, 1000);
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}
						else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						client.syncElements(1000, 1000);
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_MLB").toString(), 0, client);
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "MLB should be displayed||MLB is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_MLB").toString(), client));
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					EF.Update_TC_Report();
					// Auto_SportZone_032
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NHL should be displayed||NHL is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NHL").toString(), client));
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NHL").toString(), 0, client);
						client.syncElements(1000, 1000);
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}
						else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						client.syncElements(1000, 1000);
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NHL").toString(), 0, client);
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NHL should be displayed||NHL is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NHL").toString(), client));
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					EF.Update_TC_Report();
					// Auto_SportZone_033
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAF should be displayed||NCAAF is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NCAAF").toString(), client));
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NCAAF").toString(), 0, client);
						client.syncElements(1000, 1000);
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}
						else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						client.syncElements(1000, 1000);
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NCAAF").toString(), 0, client);
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAF should be displayed||NCAAF is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NCAAF").toString(), client));
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					EF.Update_TC_Report();
					// Auto_SportZone_034
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAMB should be displayed||NCAAMB is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), client));
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), 0, client);
						client.syncElements(1000, 1000);
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}
						else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						client.syncElements(1000, 1000);
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), 0, client);
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAMB should be displayed||NCAAMB is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), client));
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					EF.Update_TC_Report();
					// Auto_SportZone_035
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAMB should be displayed||NCAAMB is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), client));
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), 0, client);
						client.syncElements(1000, 1000);
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}
						else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						client.syncElements(1000, 1000);
						String strFilterName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), 0, client);
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAMB should be displayed||NCAAMB is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), client));
						if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
						{
							EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
						}else
						{
							String strTeamName3 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
							strTeamName3 = strTeamName3.replace("|", "-");
							String str = new String(strTeamName3);
							bFlag = false;
							for (String retval1: str.split("-"))
							{
						         if(retval1.contains(strFilterName3))
						         {
						        	 bFlag = true;
						        	 break;
						         }
						    }
							EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
						}
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					}
					EF.Update_TC_Report();
					// Auto_SportZone_036
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Verify all the filter option in the SportZone", "MLB should be displayed||MLB is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_MLB").toString(), client));
					EF.Update_Step_Report("On Clicking any filter option the asset container of particular asset should be sorted", "Asset container is getting alligned||Asset container is not getting alligned", ST.NativeElementFound(ObjectCollection.get("SportZone_AssetContainer").toString(), client));
					ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					EF.Update_TC_Report();
					// Auto_SportZone_037
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					String strFilterName7 = ST.NativeGetElementText(ObjectCollection.get("SportZone_filterOption_MLB").toString(), 0, client);
					EF.Update_Step_Report("Verify all the filter option in the SportZone", "MLB should be displayed||MLB is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_filterOption_MLB").toString(), client));
					EF.Update_Step_Report("On Clicking any filter option the asset container of particular asset should be sorted", "Asset container is getting alligned||Asset container is not getting alligned", ST.NativeElementFound(ObjectCollection.get("SportZone_AssetContainer").toString(), client));
					EF.Update_Step_Report("Navigate out of SportZone and coming back should retain the filter", "User should be able to navigate out of sportzone page||User should not be able to navigate out of sportzone page", this.Fn_Com_Navigate("Home_Guide"));
					client.syncElements(1000, 1000);
					this.Fn_Com_Navigate("Home_SportZone");
					client.syncElements(1000, 1000);
					if(ST.NativeElementFound(ObjectCollection.get("SportZone_NoGamesFound").toString(), client))
					{
						EF.Update_Step_Report("Verify whether no games found message is displayed", "No games found message is displayed||No games message is not displayed", true);
					}
					else
					{
						String strTeamName6 = ST.NativeGetElementText(ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, client);
						strTeamName6 = strTeamName6.replace("|", "-");
						String str = new String(strTeamName6);
						bFlag = false;
						for (String retval1: str.split("-"))
						{
					         if(retval1.contains(strFilterName7))
					         {
					        	 bFlag = true;
					        	 break;
					         }
					    }
						EF.Update_Step_Report("On filtering with the selective option the asset details should be displayed accordingly", "Asset content is filtered according to the option selected||Asset content is not filtered according to the option selected", bFlag);
					}
					ST.NativeClick(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client);
					EF.Update_TC_Report();
					// Auto_SportZone_038
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("By default user should be able to See All has a default filter options", "ALL is preselected has a default||ALL is not preselected has a dafault", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_ALL").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_039
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(ST.NativeElementFound(ObjectCollection.get("SportZone_NetworkLogo").toString(), client)||ST.NativeElementFound(ObjectCollection.get("SportZone_NetworkCallSign").toString(), client))
						bFlag = true;
					else
						bFlag = false;
					EF.Update_Step_Report("Either Network Logo or Network call sign should be displayed", "Network logo or Network call sign is identified||Network logo or Network call sign is not identified", bFlag);
					EF.Update_TC_Report();
					// Auto_SportZone_040
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					bFlag = client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("SportZone_UnentitledAsset").toString(), 0, 1000, 2, false);
					bFlag = client.swipeWhileNotFound("Up", 100, 2000, "NATIVE", ObjectCollection.get("SportZone_UnentitledAsset").toString(), 0, 1000, 2, false);
					EF.Update_Step_Report("User should be able to scroll the sportzone page", "User is able to swipe the sportzone page up and Down||User is not able to swipe the sportzone page up and Down", bFlag);
					EF.Update_TC_Report();
					// Auto_SportZone_041
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					ST.NativeClick(ObjectCollection.get("SportZone_ViewCompleted").toString(), client);
					EF.Update_Step_Report("Tuzz Rating should be displayed in asset content", "Tuuz rating is displayed||Tuuz rating is not yet displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_ThuuzRating").toString(), client));
					EF.Update_Step_Report("League Name should be displayed in asset content", "League name is displayed||League name is not yet displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_LeagueName").toString(), client));
					EF.Update_Step_Report("Team Logo should be displayed in asset content", "Team logo is displayed||Team Logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_TeamLogo").toString(), client));
					EF.Update_Step_Report("Team Name should be displayed in asset content", "Team Name is displayed||Team Name is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_TeamName").toString(), client));
					EF.Update_Step_Report("Team Score should be displayed in asset content", "Team score is displayed||Team score is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_TeamScore").toString(), client));
					EF.Update_Step_Report("Network logo should be displayed in asset content", "Network logo is displayed||Network logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_NetworkLogo").toString(), client));
					EF.Update_Step_Report("Network call sign should be displayed in asset content", "Network call sign is displayed||Network call sign is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_NetworkCallSign").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_042
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(!ST.NativeElementFound(ObjectCollection.get("SportZone_ViewCompleted").toString(), client)&&ST.NativeElementFound(ObjectCollection.get("SportZone_VC_ViewCurrent").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("SportZone_VC_ViewCurrent").toString(), client);
					}
					EF.Update_Step_Report("Completed asset should be displayed in the completed screen", "Completed asset is getting displayed||Completed asset is not getting displayed", ST.NativeClick(ObjectCollection.get("SportZone_VC_AssetContainer").toString(), client));
					if(ST.NativeElementFound(ObjectCollection.get("SportZone_AssetDetails_NWLogo").toString(), client))
						bFlag=false;
					else
						bFlag=true;
					EF.Update_Step_Report("On tapping the completed games in current view nothing should happen", "On tapping completed assets nothing is reflected||On tapping completed assets asset details is displayed", bFlag);
					EF.Update_TC_Report();
					// Auto_SportZone_043
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(!ST.NativeElementFound(ObjectCollection.get("SportZone_ViewCompleted").toString(), client)&&ST.NativeElementFound(ObjectCollection.get("SportZone_VC_ViewCurrent").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("SportZone_VC_ViewCurrent").toString(), client);
					}
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NHL should be displayed||NHL is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NHL").toString(), client));
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "MLB should be displayed||MLB is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_MLB").toString(), client));
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NBA should be displayed||NBA is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NBA").toString(), client));
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAF should be displayed||NCAAF is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NCAAF").toString(), client));
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAMB should be displayed||NCAAMB is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), client));
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NFL should be displayed||NFL is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NFL").toString(), client));
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NHL should be displayed||NHL is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NHL").toString(), client));
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "MLB should be displayed||MLB is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_MLB").toString(), client));
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NBA should be displayed||NBA is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NBA").toString(), client));
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAF should be displayed||NCAAF is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NCAAF").toString(), client));
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NCAAMB should be displayed||NCAAMB is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NCAAMB").toString(), client));
						EF.Update_Step_Report("Verify all the filter option in the SportZone", "NFL should be displayed||NFL is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_filterOption_NFL").toString(), client));
						ST.NativeClick(ObjectCollection.get("SportZone_filterButton").toString(), client);
					}
					EF.Update_TC_Report();
					// Auto_SportZone_044
					ST.NativeClick(ObjectCollection.get("SportZone_VC_ViewCurrent").toString(), client);
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Verify whether the user is able to see the Sport zone logo", "Sport zone logo is displayed||SportZone logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Logo").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_045
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					bFlag = client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("SportZone_VC_Rating&Stats").toString(), 0, 1000, 5, false);
					bFlag = client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("SportZone_VC_Rating&Stats").toString(), 1, 1000, 5, false);
					EF.Update_Step_Report("Thuuz and stats should be displayed on the bottom of the page", "Thuuz rating and stats is displayed in the bottom of the page||Thuuz rating and status is not displayed in the bottom of the page", bFlag);
					EF.Update_TC_Report();
					// Auto_SportZone_046
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					//TODO while some program in progress
					EF.Update_TC_Report();
					// Auto_SportZone_047
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					String assetTitle = client.getTextIn("NATIVE", ObjectCollection.get("SportZone_League&FullTeamName").toString(), 0, "NATIVE", "Inside", 0, 0);
					String strAsset = assetTitle.replace("|", "-");
					String[] strSplit = strAsset.split("-");
					EF.Update_Step_Report("User should be able to view the asset content", "Asset content is displayed||Asset content is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_League&FullTeamName").toString(), client));
					EF.Update_Step_Report("Asset details page shouuld be displayed on clicking asset container", "Asset details page is displayed||Asset details page is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Assetdetails_subtitle").toString(), client));
					String subTitle = client.getTextIn("NATIVE", ObjectCollection.get("SportZone_Assetdetails_subtitle").toString(), 0, "NATIVE", "Inside", 0, 0);
					bFlag = false;
					if(subTitle.contains(strSplit[1]))
					{
						bFlag = true;
					}
					EF.Update_Step_Report("On tapping the asset container the selected asset detail page should be displayed", "Asset details page is displayed properly||Asset details page is not displayed properly", bFlag);
					EF.Update_TC_Report();
					// Auto_SportZone_048
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("User should be able to select View Completed", "View completed is selected||View Completed is not selected", ST.NativeClick(ObjectCollection.get("SportZone_ViewCompleted").toString(), client));
					EF.Update_Step_Report("View completed page should be displayed", "View completed page is displayed||View completed page is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_ViewCurrent").toString(), client));
					EF.Update_Step_Report("User should be able to navigate to another module", "User is navigated to another module||User is not navigated to another module", this.Fn_Com_Navigate("Home_MyLibrary"));
					EF.Update_Step_Report("User should be able to navigate back to SportZone", "User is naviageted to sportzone module||User is not navigated to sportzone module", this.Fn_Com_Navigate("Home_SportZone"));
					EF.Update_Step_Report("The user should be in view completed page and navigate to other module, on navigating back to Sportzone the view completed page should be displayed", "View completed is displayed||View Completed is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_ViewCurrent").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_049
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Find whether the search button is displaye in the SportZone", "Search option is displayed||Search option is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Search").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_050
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Asset details should be displayed in the list view", "Asset details is in the list view||Asset details is not in the list view", ST.NativeElementFound(ObjectCollection.get("SportZone_ListView").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_051
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Spoiler should be displayed in the SportZone page", "Spoiler is displayed||Spoiler is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersON").toString(), client));
					EF.Update_Step_Report("Filter options should be displayed in the SportZone page", "Filter option is displayed||Filter option is not displayed", ST.NativeElementFound(ObjectCollection.get("").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_052
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(!ST.NativeElementFound(ObjectCollection.get("SportZone_ViewCompleted").toString(), client)&&ST.NativeElementFound(ObjectCollection.get("SportZone_VC_ViewCurrent").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("SportZone_VC_ViewCurrent").toString(), client);
					}
					EF.Update_Step_Report("On clicking on the asset should redirect to asset details page", "Asset container is displayed||Asset container is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_AssetContent").toString(), client));
					EF.Update_Step_Report("Asset details page should be displayed", "Asset details page is displayed||Asset details page is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_AssetDetails_NWLogo").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					// Auto_SportZone_053
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("By default the spoiler should be checked with Tick mark", "Spoiler is marked with Tick mark||Spoiler is not marked with Tick mark", ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersON").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_054
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Asset content should be displayed in the sportzone landing page", "Asset content is displayed||Asset content is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_AssetContent").toString(), client));
					EF.Update_Step_Report("On clicking asset details user sould be redirected to the Asset details page", "User is redirected to asset details page||User is not redirected to the asset details page", ST.NativeElementFound(ObjectCollection.get("SportZone_AssetDetails_NWLogo").toString(), client));
					EF.Update_Step_Report("More like this should be displayed in the asset details", "More like this option is displayed||More like this option is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Asstdetail_MoreLikeThis").toString(), client));
					//ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					// Auto_SportZone_056
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Asset content should be displayed in the sportzone landing page", "Asset content is displayed||Asset content is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_AssetContent").toString(), client));
					EF.Update_Step_Report("On clicking asset details user sould be redirected to the Asset details page", "User is redirected to asset details page||User is not redirected to the asset details page", ST.NativeElementFound(ObjectCollection.get("SportZone_AssetDetails_NWLogo").toString(), client));
					EF.Update_Step_Report("More like this should be displayed in the asset details", "More like this option is displayed||More like this option is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Asstdetail_MoreLikeThis").toString(), client));
					client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", ObjectCollection.get("SportZone_Asstdetail_BrowseAll").toString(), 0, 1000, 5, false);
					EF.Update_Step_Report("Related assets should be displayed below more like this title", "List of assets is displayed||List of asset is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Asstdetail_MoreAsst").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					// Auto_SportZone_057
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Thuuz rating should be displayed in the asset content", "Thuuz rating is displayed||Thuuz rating is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_ThuuzRating").toString(), client));
					EF.Update_Step_Report("On Clicking of Thuuz rating user should be displayed with thuuz rating banner", "Thuuz rating information banner is displayed||Thuuz rating information banner is displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_ThuuzRating_PopUp").toString(), client));
					ST.NativeClick(ObjectCollection.get("SportZone_Logo").toString(), client);
					EF.Update_TC_Report();
					// Auto_SportZone_058
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("View completed should be displayed", "View completed is displayed||View complleted is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_ViewCompleted").toString(), client));
					EF.Update_Step_Report("Thuuz rating should be displayed", "Thuuz rating is displayed||Thuuz rating is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_ThuuzRating").toString(), client));
					EF.Update_Step_Report("On clicking the Thuuz rating the thuuz rating pop up should be displayed", "Thuuz rating pop over is displayed||Thuuz rating is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_ThuuzRating_PopUp").toString(), client));
					ST.NativeClick(ObjectCollection.get("SportZone_ThuuzRating").toString(), client);
					ST.NativeClick(ObjectCollection.get("SportZone_VC_ViewCurrent").toString(), client);
					EF.Update_TC_Report();
					// Auto_SportZone_059
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					this.Fn_SportZoneOngoingProg_check("SportZone_Live_ProgressBar");
					EF.Update_TC_Report();
					// Auto_SportZone_060
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "Android")
					{
						EF.Update_Step_Report("Sportzone filter should be displayed in the top of the sportzone page", "SportZone is displayed||SportZone is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_ActionSpinner").toString(), client));
						EF.Update_Step_Report("User should be able to select the Programs", "Programs is selected||Programs is not selected", ST.NativeClick(ObjectCollection.get("SportZone_Programs").toString(), client));
					}
					else
					{
						EF.Update_Step_Report("User should be able to select the Programs", "Programs is selected||Programs is not selected", ST.NativeClick(ObjectCollection.get("SportZone_Programs").toString(), client));
					}
					ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
					EF.Update_Step_Report("User shoould be redirected to the program listing page", "The program listing page is displayed||The program listing page is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Programs_List").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_061
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "Android")
					{
						EF.Update_Step_Report("Sportzone filter should be displayed in the top of the sportzone page", "SportZone is displayed||SportZone is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_ActionSpinner").toString(), client));
						EF.Update_Step_Report("User should be able to select the Games on TV", "Games on TV is selected||Games on TV is not selected", ST.NativeClick(ObjectCollection.get("SportZone_GamesOnTV").toString(), client));
					}
					else
					{
						EF.Update_Step_Report("User should be able to select the games on TV", "Games on TV is selected||Games on TV is not selected", ST.NativeClick(ObjectCollection.get("SportZone_GamesOnTV").toString(), client));
					}
					ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
					EF.Update_Step_Report("User should be able to navigate back to the Games on TV asset container screen", "Games on TV is displayed||Games on TV is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_AssetContainer").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_062
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("User sould be able to navigate to Asset details page", "User is navigated to asset details page||User is not naviogated to asset details page", ST.NativeClick(ObjectCollection.get("SportZone_AssetContent").toString(), client));
					EF.Update_Step_Report("Verify whether the Other times is present for the asset", "Other times is displayed||Other time is not displayed", ST.NativeClick(ObjectCollection.get("SportZone_Asst_OtherTimes").toString(), client));
					EF.Update_Step_Report("Other time pop up should be displayed on clicking the other times", "Other times pop up is displayed||Other time pop up is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Asst_OtherTimesPopUp").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_063
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("User sould be able to navigate to Asset details page", "User is navigated to asset details page||User is not naviogated to asset details page", true);
					EF.Update_Step_Report("Verify whether the Other times is present for the asset", "Other times is displayed||Other time is not displayed", true);
					EF.Update_Step_Report("Other time pop up should be displayed on clicking the other times", "Other times pop up is displayed||Other time pop up is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Asst_OtherTimesPopUp").toString(), client));
					EF.Update_Step_Report("Future Listing should be displayed in the pop up", "Future listing is displayed||Future listing is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Asst_FutureListing").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_064
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("In Other time pop up network logo should be displayed", "Network logo is displayed||Network logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Asst_OtherTimeNWlogo").toString(), client));
					// Auto_SportZone_065
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Other time pop up should contain the future timings of the asset", "Future timing is displayed||Future timing is missed", ST.NativeElementFound(ObjectCollection.get("SportZone_Asst_OtherTimeTiming").toString(), client));
					// Auto_SportZone_066
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("User should be in asset details page", "User is navigateed to asset details page||User is not navigateed to asset details page", true);
					EF.Update_Step_Report("On clicking other time future pop up should be displayed with Channel number", "Channel number is displayed||Channel number is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Asst_OtherTimeChNo").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					// Auto_SportZone_067
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("User should be in the Asset details page", "User is in asset details page||User is not in asset details page", ST.NativeElementFound(ObjectCollection.get("SportZone_Asst_Title").toString(), client));
					String strAsstName = ST.NativeGetElementText(ObjectCollection.get("SportZone_Asst_Title").toString(), 0, client);
					ST.NativeClick(ObjectCollection.get("SportZone_Asst_OtherTimes").toString(), client);
					EF.Update_Step_Report("On clicking other time user should be taken to Future time pop up", "Future time pop up is displayed||Future time pop uo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Asst_OtherTimesPopUp").toString(), client));
					String strAsstName1 = ST.NativeGetElementText(ObjectCollection.get("Sportzone_Asst_OtherTimeTitle").toString(), 0, client);
					if(strAsstName.contains(strAsstName1))
					{
						EF.Update_Step_Report("Check whether the Title of the asset and the future time pop is same", "Asset title is validated sucessfully||Asset title is mismatching", true);
					}
					else
					{
						EF.Update_Step_Report("Check whether the Title of the asset and the future time pop is same", "Asset title is validated sucessfully||Asset title is mismatching", false);
					}
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
					EF.Update_TC_Report();
					// Auto_SportZone_068
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS=="Android")
					{
						client.elementSwipe("NATIVE",ObjectCollection.get("SportZone_AssetContent").toString(), 3, "Down", 300, 2000);
						EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
						EF.Update_Step_Report("User should be able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
						EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
						EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
						EF.Update_Step_Report("Sportzone page should be displayed with the default view as Games on TV", "SportZone page is displayed Games on TV as default||SportZone page is not displayed Games on TV as default", true);
						EF.Update_Step_Report("Dragging down the page should display refresh loader", "Refresh loader is not displayed||Refresh loader is not displayed", false);
					}
					else
					{
						client.elementSwipe("NATIVE", ObjectCollection.get("SportZone_AssetContainer").toString(), 0, "Up", 0, 2000);
						bFlag=ST.NativeElementFound(ObjectCollection.get("SportZone_BufferLoader").toString(), client);
						EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
						EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
						EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
						EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
						EF.Update_Step_Report("Sportzone page should be displayed with the default view as Games on TV", "SportZone page is displayed Games on TV as default||SportZone page is not displayed Games on TV as default", true);
						EF.Update_Step_Report("Dragging down the page should display loading spindle", "Loading Spindle is displayed||Loading Spindle is not displayed", bFlag);
					}
					// Auto_SportZone_069
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);			
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("User sould be able to navigate to Asset details page", "User is navigated to asset details page||User is not naviogated to asset details page", ST.NativeClick(ObjectCollection.get("SportZone_AssetContent").toString(), client));
					client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("SportZone_Asstdetail_BrowseAll").toString(), 0, 1000, 2, false);
					EF.Update_Step_Report("Asset content is displayed in the asset details page", "Asset content is displayed||Asset content is not displayed", true);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					
					// Auto_SportZone_070
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					ST.NativeClick(ObjectCollection.get("SportZone_ViewCompleted").toString(), client);
					EF.Update_Step_Report("Tuzz Rating should be displayed in asset content", "Tuuz rating is displayed||Tuuz rating is not yet displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_ThuuzRating").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_071
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("League Name should be displayed in asset content", "League name is displayed||League name is not yet displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_LeagueName").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_072
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Team Logo should be displayed in asset content", "Team logo is displayed||Team Logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_TeamLogo").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_073
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Team Name should be displayed in asset content", "Team Name is displayed||Team Name is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_TeamName").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_074
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					//ST.NativeClick(ObjectCollection.get("SportZone_ViewCompleted").toString(), client);
					EF.Update_Step_Report("Team Score should be displayed in asset content", "Team score is displayed||Team score is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_TeamScore").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_075
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					EF.Update_Step_Report("Network logo should be displayed in asset content", "Network logo is displayed||Network logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_NetworkLogo").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_076
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					//ST.NativeClick(ObjectCollection.get("SportZone_ViewCompleted").toString(), client);
					EF.Update_Step_Report("Network call sign should be displayed in asset content", "Network call sign is displayed||Network call sign is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_VC_NetworkCallSign").toString(), client));
					EF.Update_TC_Report();
					// Auto_SportZone_077
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Watch on Tablet or TV should be diplayed in the SportZone landing page", "Watch on Tablet or TV is displayed||Watch on Tablet or TV is not displayed", ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_WatchOnTablet").toString(), client));
					}
					else
					{
						EF.Update_Step_Report("Watch on Tablet or TV should be diplayed in the SportZone landing page", "Watch on Tablet or TV is displayed||Watch on Tablet or TV is not displayed", ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_WatchOnSpinner").toString(), client));
					}
					EF.Update_TC_Report();
					// Auto_SportZone_078
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Watch on Tablet should be preselected by default", "Watch on tablet is preselected||Watch on tablet is not preselected", ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_WatchOnTablet").toString(), client));
					}
					else
					{
						EF.Update_Step_Report("Watch on Tablet or TV should be diplayed in the SportZone landing page", "Watch on Tablet or TV is displayed||Watch on Tablet or TV is not displayed", ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_WatchOnSpinner").toString(), client));
						EF.Update_Step_Report("Watch on Tablet should be preselected by default", "Watch on tablet is preselected||Watch on tablet is not preselected", ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_WatchOnTablet").toString(), client));
					}
					EF.Update_TC_Report();
					// Auto_SportZone_079
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					if(Current_OS == "IOS")
					{
						EF.Update_Step_Report("Watch on Tablet should be preselected by default", "Watch on tablet is preselected||Watch on tablet is not preselected", ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_WatchOnTablet").toString(), client));
						// Need to take path on IOS
						//EF.Update_Step_Report("User should be able to select Watch on TV option", "User is able to select Watch on TV option||User is not able to select Watch on TV option", ST.NativeClick(ObjectCollection.get("").toString(), client));
					}
					else
					{
						EF.Update_Step_Report("Watch on Tablet or TV should be diplayed in the SportZone landing page", "Watch on Tablet or TV is displayed||Watch on Tablet or TV is not displayed", ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_WatchOnSpinner").toString(), client));
						EF.Update_Step_Report("Watch on Tablet should be preselected by default", "Watch on tablet is preselected||Watch on tablet is not preselected", ST.NativeElementFound(ObjectCollection.get("Guide_GuideScreen_WatchOnTablet").toString(), client));
						
					}
					EF.Update_TC_Report();
					// Auto_SportZone_080
					EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
					EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
					EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
					EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
					this.Fn_SportZoneOngoingProg_check("SportZone_Live_KeyIcon");
				}
				
				return bFlag;
			}
			
			//Checking for the content only when ongoing program is present
			public boolean Fn_SportZoneOngoingProg_check(String strContent)
			{
				boolean bFlag= false;
				if(ST.NativeElementFound(ObjectCollection.get("SportZone_OnGoing_Prog").toString(), client))
				{
					EF.Update_Step_Report("Check whether there is an ongoing program in the SportZone page", "There is an ongoing program in SportZone||There is no ongoing program in SportZone right now", true);
					EF.Update_Step_Report("On going program should contain Scores in the Asset container", "Scores are displayed||Scores are not displayed", ST.NativeElementFound(ObjectCollection.get(strContent).toString(), client));
				}
				else
					EF.Update_Step_Report("Check whether there is an ongoing program in the SportZone page", "There is an ongoing program in SportZone||There is no ongoing program in SportZone right now", false);
				return bFlag;
			}
		
		// Sport zone time is validated
		public boolean Fn_SportZoneTimecalc()
		{
			SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
			String strTime1,strTime2;
			Date dTime1, dTime2;
			boolean bFlag=true;
			try
			{
				strTime1 = ST.NativeGetElementText(ObjectCollection.get("SportZone_In-GameTime").toString(), 0, client);
				strTime2 = ST.NativeGetElementText(ObjectCollection.get("SportZone_In-GameTime").toString(), 1, client);
				dTime1 = sdf.parse(strTime1);
				dTime2 = sdf.parse(strTime2);
				long nTimeDiff=(dTime2.getTime()-dTime1.getTime())/(1000*60);
				if(nTimeDiff  >= 30)
				{}
				else
				{bFlag=false;}
			}
			catch(Exception Ex)
			{
				System.out.println("Error in Fn_SportZoneTimecalc : "+ Ex.getCause());
			}
			return bFlag;
		}
		/*=================================================================================================================================*/
		//				END OF SPORTZONE METHODS
		/*=================================================================================================================================*/
		/*==================================================================================================================================
		 * Channel Line Up validation
		 ==================================================================================================================================*/
	/*	 public void ChannelLineUp() throws BiffException, IOException
	  	 {
	  		//String strChLineUp = "C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\channelLineUp.xls";
	  		
	  		String strChLineUp = Globals.BinPath.replace("bin", "Data\\channelLineUp.xls");
	  		CL.readExcelfromChannelLineup(strChLineUp);

	  		//String strWorkBookName = "C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\GuideResp_26082014_110918.xls";
	  		String strWorkBookName = Globals.BinPath.replace("bin", "Data\\GuideResp_04092014_142818.xls");
	  		CL.readExcelfromGuideResp(strWorkBookName);
	  		StringBuilder sb = new StringBuilder();

	  		String[] MapKeys=new String[m1.size()];

	  		Iterator itr = m1.keySet().iterator();
	  		int intC=0;
	  		while(itr.hasNext())
	  		{
	  			String key1 = (String) itr.next();

	  			MapKeys[intC]=key1.trim();
	  			if(intC<m1.size())
	  			{
	  				intC++;
	  			}
	  		}

	  		String[] GuideMapKeys=new String[GuideRes_m1.size()];

	  		Iterator itr1 = GuideRes_m1.keySet().iterator();
	  		int intC1=0;
	  		while(itr1.hasNext())
	  		{
	  			String key2 = (String) itr1.next();

	  			GuideMapKeys[intC1]=key2.trim();
	  			if(intC1<GuideRes_m1.size())
	  			{
	  				intC1++;
	  			}
	  		}

//	  		System.out.println("-------------------------------------------------------------------------------------");
//	  		System.out.println("Channel Lineup Size: " +m1.size());
//	  		System.out.println("-------------------------------------------------------------------------------------");
//	  		System.out.println("Guide Response Size: " +GuideRes_m1.size());
//	  		System.out.println("-------------------------------------------------------------------------------------");
	  		int notMatchingCount = 0;

	  		for(int i=0;i<GuideMapKeys.length;i++)
	  		{


	  			String strGuideKey1=GuideMapKeys[i].trim();

	  			String strGuideChannelLineup1=GuideRes_m1.get(strGuideKey1).toString();
	  			if(m1.containsKey(strGuideKey1))
	  			{

	  			}
	  			else
	  			{
	  				notMatchingCount++;

//	  				System.out.println("Channel present in Guide but not in Market :" + strGuideKey1);
	  				missingCh1.put(strGuideKey1, strGuideChannelLineup1);

	  			}
	  		}*/
//	  		System.out.println("-------------------------------------------------------------------------------------");
//	  		System.out.println("The Number of Channels which present in Guide but not in Market :" + notMatchingCount);
//	  		System.out.println("The Number of channels which present in guide as well as in market :" + (GuideRes_m1.size()-notMatchingCount));
//	  		System.out.println("-------------------------------------------------------------------------------------");
/*	  		notMatchingCount = 0; 
	  		int keysNotinGuide = 0;

//	  		System.out.println("MapKeys.Length" + MapKeys.length);
//	  		System.out.println("GuideRes_m1.size" + GuideRes_m1.size());
	  		int loopCount = 0;
	  		for(int i=0;i<MapKeys.length;i++)
	  		{


	  			String strKey=MapKeys[i].trim();

	  			String strChannelLineup=m1.get(strKey).toString();


	  			//System.out.println(strKey + "--- "+ strValue);       
	  			if(GuideRes_m1.containsKey(strKey))
	  			{
	  				loopCount++;
	  				availableKeys.put(strKey, strChannelLineup);
//	  				if(strKey.trim().contains("954"))
//	  					System.out.println("Yes I am 954");
	  				String strGuideResp=GuideRes_m1.get(strKey).toString();
	  				String strGuideRespNC=GuideRes_m2.get(strKey).toString();
	  				if(strChannelLineup.contains(strGuideResp) || strGuideResp.contains(strChannelLineup))
	  				{
	  					resultMap1.put(strKey, "Pass");
	  					//System.out.println("present. Channel Number: " +strKey);
	  				}else if(strChannelLineup.contains(strGuideRespNC))
	  				{
	  					resultMap1.put(strKey, "Pass");  
	  					//System.out.println("present. Channel Number: " +strKey);
	  				}else{
	  					//if(strKey.trim().contains("954"))
	  						//System.out.println("Yes I am here" + strKey);
	  					String tmpChannelLineup = strChannelLineup.replace("/", " ");
	  					String tmpGuideResp = strGuideResp.replace(" / ", " ").replace("/", " ");
	  					String [] a=tmpChannelLineup.split(" ");
	  					String[] b=tmpGuideResp.split(" ");
	  					String[] c=strGuideRespNC.split(" ");

	  					if(tmpChannelLineup.toLowerCase().contains(b[0].toLowerCase()) || tmpChannelLineup.toLowerCase().contains(c[0].toLowerCase()))
	  					{
	  						resultMap1.put(strKey, "Pass");
	  						//System.out.println("present. Channel Number: " +strKey);
	  					}
	  					else if(b[0].toLowerCase().contains(a[0].toLowerCase()) || a[0].toLowerCase().contains(b[0].toLowerCase()))
	  					{
	  						resultMap1.put(strKey, "Pass");   
	  						//System.out.println("present. Channel Number: " +strKey);
	  					}
	  					else if(c[0].toLowerCase().contains(a[0].toLowerCase()) || a[0].toLowerCase().contains(c[0].toLowerCase())){
	  						resultMap1.put(strKey, "Pass");   
	  						//System.out.println("present. Channel Number: " +strKey);
	  					}
	  					else if(strChannelLineup.contains("-"))
	  					{
	  						String strChannelLineup1=strChannelLineup.replace("-", "");
	  						if(strChannelLineup1.toLowerCase().contains(a[0].toLowerCase()) || 
	  								strChannelLineup1.toLowerCase().contains(b[0].toLowerCase()) ||
	  								strChannelLineup1.toLowerCase().contains(c[0].toLowerCase()) )
	  						{
	  							resultMap1.put(strKey, "Pass");  
	  							//System.out.println("present. Channel Number: " +strKey);
	  						}
	  					}
	  					else
	  					{
	  						if(strKey.trim().contains("954"))
	  							System.out.println("Yes I am here 1" + strKey);
	  						String marketChannelAbb = "";
	  						boolean IsValid = false;
	  						String tmpAcrChannelLineup = strChannelLineup.replace("/", " ");
	  						String tmpAbrGuideResp = strGuideResp.replace("/", " ");
	  						marketChannelAbb = WordUtils.initials(tmpAcrChannelLineup);


	  						if(tmpAbrGuideResp.toUpperCase().contains(marketChannelAbb.toUpperCase()) || 
	  								marketChannelAbb.toUpperCase().contains(tmpAbrGuideResp.split(" ")[0].toUpperCase())
	  								|| marketChannelAbb.toUpperCase().contains(tmpAbrGuideResp.toUpperCase())){
	  							if(marketChannelAbb.length()>1) 
	  							{
	  								resultMap1.put(strKey, "Pass");
	  							}
	  							else
	  							{
	  								notMatchingCount++;
	  								sb.append("Market Channel Number: " + strKey + " Programming Service: " +strChannelLineup + " -> Guide Network: "+ strGuideResp + "\n");
	  								resultMap1.put(strKey, "Fail");
	  							}
	  						}

	  						else if((strGuideResp.contains("HD") && strChannelLineup.contains("HD"))  || (strGuideResp.contains("PPVP") && strChannelLineup.contains("PPV"))) {

	  							String tmpWrdChannelLineup = strChannelLineup.replace("/", " ");
	  							String tmpWrdGuideResp = strGuideResp.replace("/", " ");
	  							for(String guideName : tmpWrdGuideResp.split(" ")) 
	  							{
	  								//System.out.println("Token Guide: " +guideName);
	  								for (String marketName : tmpWrdChannelLineup.split(" ")) 
	  								{
	  									//System.out.println("Token Market: " +marketName);
	  									if(!marketName.toUpperCase().contains("HD") || !guideName.toUpperCase().contains("HD")) 
	  									{
	  										if (guideName.toUpperCase().contains(marketName.toUpperCase()) || marketName.toUpperCase().contains(guideName.toUpperCase())) 
	  										{	
	  											IsValid = true;
	  											resultMap1.put(strKey, "Pass");
	  										}
	  									}
	  								}
	  							}
	  							if(!IsValid) {
	  								notMatchingCount++;
	  								sb.append("Market Channel Number: " + strKey + " Programming Service: " +strChannelLineup + " -> Guide Network: "+ strGuideResp + "\n");
	  								resultMap1.put(strKey, "Fail");
	  							}
	  							IsValid = false;
	  						}
	  						else {
	  							//if(strKey.trim().contains("954"))
	  								//System.out.println("Yes I am here");
	  							notMatchingCount++;
	  							sb.append("Market Channel Number: " + strKey + " Programming Service: " +strChannelLineup + " -> Guide Network: "+ strGuideResp + "\n");
	  							resultMap1.put(strKey, "Fail");
	  						}
	  					}
	  				}
	  			}
	  			else{
	  				//if(strKey.trim().contains("954"))
	  					//System.out.println("Yes I am here");
	  				//System.out.println("Channels present in Market Channel Line up but not in Guide Response. Key: " +strKey);
	  				missingCh2.put(strKey, strChannelLineup);
	  				keysNotinGuide++;
	  				notMatchingCount++;
	  			}


	  		}
//	  		System.out.println("LoopCount:" + loopCount);
//	  		System.out.println("AvailableKeys Count: "+ availableKeys.size());
	  		
	  		String[] avKeys=new String[availableKeys.size()];

	  		Iterator itravKeys = availableKeys.keySet().iterator();
	  		int avkeycount=0;
	  		while(itravKeys.hasNext())
	  		{
	  			String key1 = (String) itravKeys.next();

	  			avKeys[avkeycount]=key1;
	  			if(avkeycount<availableKeys.size())
	  			{
	  				avkeycount++;
	  			}
	  		}
	  		System.out.println("Array Size: " + avKeys.length);
	  		
	  		for (int c=0; c<avKeys.length; c++) {
	  			String avKey=avKeys[c];
	  			String strChannelLineupName=GuideRes_m1.get(avKey).toString();
	  			String guideChannelName = m1.get(avKey).toString();
	  			if(!resultMap1.containsKey(avKey))
	  				System.out.println("One missing channel: " + avKey + " Guide Name : "+ guideChannelName + " / Market Channel Name: " + strChannelLineupName);
	  		
	  		}
	  		
	  		
	  		System.out.println("-------------------------------------------------------------------------------------");
	  		System.out.println("The Number of Channels available in Market but not in Guide :" +  keysNotinGuide);
	  		System.out.println("The Number of channels which present in Market as well as in Guide :"+ (m1.size()-keysNotinGuide));
	  		System.out.println("-------------------------------------------------------------------------------------");
	  		System.out.println("The Channels not matching the Market Channel line up are as follow");
	  		System.out.println("-------------------------------------------------------------------------------------");
	  		System.out.println("Guide Channels not matching the Market Channel line up :" + (notMatchingCount - keysNotinGuide )+ "\n" +sb.toString());
	  		System.out.println("-------------------------------------------------------------------------------------");
	  		System.out.println("Guide Channels in compliance with the Market Channel line up :" + (m1.size() - notMatchingCount));
	  		System.out.println("-------------------------------------------------------------------------------------");
	  		try {
	  			CL.FnWriteXL();
	  			CL.FnWriteXL1();
	  			//FnWriteXL2();
	  		}
	  		catch(Exception ex)
	  		{
	  			System.err.println("Channel Line UP :"+ex.getCause()+"/ Message :"+ex.getMessage());
	  		}

	  	}
*/
		public void Fn_Favorite_PopOver_Contents()
		{
			//*********************Auto_Home_FavoritesEditPopOver_001******************//
			try
			{
				ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
				//Navigating to My Library
				EF.Update_Step_Report("User should be navigated to My Library page", "User navigated successfully to My Library||User navigation to My Library is unsuccessful",this.Fn_Com_Navigate("Home_MyLibrary"));
				client.sleep(1000);
				//Navigating to favorites Panel
				client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_FavoritePanel").toString(), 0, 1000, 5, true);
				//Clicking on edit favorites icon
				ST.NativeClick(ObjectCollection.get("Home_btn_FavoriteEdit").toString(), client);
				EF.Update_Step_Report("Favorites Edit Pop Over should Appear","Pop Over appeared||PopOver not appeared",ST.NativeElementFound(ObjectCollection.get("Home_Favorite_PopOver").toString(), client));
				EF.Update_Step_Report("Facorite Icon should be present", "Favorite Icon is present||Favorite icon is not present",ST.NativeElementFound(ObjectCollection.get("Home_Favorite_PopOver_HeartIcon_Container").toString(), client));
				EF.Update_Step_Report("Network logo should be present", "Network logo is present||Network logo is not present",ST.NativeElementFound(ObjectCollection.get("Home_Favorite_PopOver_Networklogo_Container").toString(), client));
				EF.Update_Step_Report("Channel number should be present", "Channel number is present||Channel number is not present",ST.NativeElementFound(ObjectCollection.get("Home_Favorite_PopOver_ChannelNo_Container").toString(), client));
				EF.Update_Step_Report("Channel name should be present", "Channel name is present||Channel name is not present",ST.NativeElementFound(ObjectCollection.get("Home_Favorite_PopOver_ChannelAbr_Container").toString(), client));
				EF.Update_Step_Report("Favorites Edit Pop Over should Appear","Pop Over appeared||PopOver not appeared",ST.NativeElementFound(ObjectCollection.get("Home_Favorite_PopOver").toString(), client));
				EF.Update_TC_Report();
				
				//********************Auto_Home_FavoritesEditPopOver_002********************//
				//Verifying the sort functionality on the favorites edit pop over
				EF.Update_Step_Report("User should be navigated to My Library page", "User navigated successfully to My Library||User navigation to My Library is unsuccessful", true);
				EF.Update_Step_Report("Favorites Edit Pop Over should Appear","Pop Over appeared||PopOver not appeared",ST.NativeElementFound(ObjectCollection.get("Home_Favorite_PopOver").toString(), client));
				client.click("NATIVE", ObjectCollection.get("Home_Favorite_PopOver_SortIcon").toString(), 0, 1);
				EF.Update_Step_Report(" Sort option A-Z should switch to sorting by 1-1000. ",  "Sort option A-Z switched to sorting by 1-1000||Sort Option does not switch to 1-1000",
						ST.NativeElementFound(ObjectCollection.get("Home_Favorite_PopOver_SortNumeric").toString(), client));
				client.click("NATIVE", ObjectCollection.get("Home_Favorite_PopOver_SortIcon").toString(), 0, 1);
				EF.Update_Step_Report(" Sort option 1-1000 should switch to sorting by A-Z",  "Sort option 1-1000 switched to sorting by A-Z||Sort Option does not switch to A-Z",
						ST.NativeElementFound(ObjectCollection.get("Home_Favorite_PopOver_SortNumeric").toString(), client));
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				//Navigating back to My Library
				client.swipeWhileNotFound("Up", 100, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
			}
			catch(Exception e)
			{
				System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
			}

		}
		
		public void Fn_Favorite_Filter()
		{	
			//********************************Auto_Home_FavoritesEditPopOver_003**************//
			try
			{
				
				//Checking the available filter options in favorite edit pop over
				String drawerTitle[]=new String[11];
				String[] filterName2;
				String[] filterName={"My Favorite Channels","Live TV","Locals","HD Only","Premium",
										"Family & Education","Lifestyle","Movies","Music","News & Weather","Sports"};
				String[] filterName1={"My Favorite Channels","Subscribed Channels","Locals","HD Only","Premium","Family & Education","Lifestyle","Movies","Music","News & Weather","Sports"};
				String[] filterXpath={"Home_Favorite_MyFavoriteChannels","Home_Favorite_LiveTv","Home_Favorite_Locals","Home_Favorite_HDOnly","Home_Favorite_Premium",
						"Home_Favorite_Family&Education","Home_Favorite_LifeStyle","Home_Favorite_Movies","Home_Favorite_Music","Home_Favorite_News&Weather","Home_Favorite_Sports"};
				//Navigating to My Library
				EF.Update_Step_Report("User should be navigated to My Library page", "User navigated successfully to My Library||User navigation to My Library is unsuccessful",Fn_Com_Navigate("Home_MyLibrary"));
				//Navigating to favorites Panel
				EF.Update_Step_Report("User should be navigated to favorites panel in My Library", "User navigated to favorites panel||Navigation to favorites panel unsuccessfull ",
						client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_FavoritePanel").toString(), 0, 1000, 5, true));
				//Checking whether Help message is displayed when no channels are added in favorites panel
				if(!(ST.NativeElementFound(ObjectCollection.get("Home_EditFavorite_HelpMsg").toString(), client)))
				{	
					EF.Update_Step_Report("Help message should not be displayed when favorites are already added in the panel", "Favorites are already present||favorties are not present",
							(ST.NativeElementFound(ObjectCollection.get("Home_Favorite_AssetContainer").toString(), client)));
					
				}
				else
				{
					//Updating step result for existence of help message
					EF.Update_Step_Report("Favorites Edit help message should be available", "Favarite Edit help message available||Favorites Edit help message not available",true);
				}
				//Clicking on edit favorites icon
				client.swipeWhileNotFound("Up", 100, 2000, "NATIVE",ObjectCollection.get("Home_btn_FavoriteEdit").toString(), 0, 1000, 5, true);
				if(Current_OS.equalsIgnoreCase("IOS"))
				{
					ST.NativeClick(ObjectCollection.get("Home_Favorite_FilterIcon").toString(), client);
					filterName2 = client.getAllValues("NATIVE", ObjectCollection.get("Home_Favorite_FilterContainer").toString(),"text");
					EF.Update_Step_Report("All the filter should be available", "All the Filter options are avilable||All the Filter options are not availble", Arrays.equals(filterName2, filterName1));
				}
				
//				for(int i=0;i<filterName.length;i++)
//				{	
//					if(Current_OS.equalsIgnoreCase("IOS"))
//					{
//						client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Favorite_FilterContainer").toString(),0,3000);
//						ST.NativeClick(ObjectCollection.get("Home_Favorite_FilterIcon").toString(), client);
//						client.isElementFound("NATIVE",ObjectCollection.get(filterXpath[i]).toString());
//						//Retrieving the filter name
//						drawerTitle[i]=client.elementGetText("NATIVE",ObjectCollection.get(filterXpath[i]).toString() , 0);
//						//Updating whether the required filters are available or not
//						EF.Update_Step_Report("All the filter should be available", "Filter is avilable||Filter is not availble",
//									filterName1[i].contains(drawerTitle[i]));
//					}
//					else if(Current_OS.equalsIgnoreCase("Android"))
//					{
//						client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Favorite_FilterContainer").toString(),0,3000);
//						ST.NativeClick(ObjectCollection.get("Home_Favorite_FilterIcon").toString(), client);
//						client.elementSwipeWhileNotFound("NATIVE", "//*[@class='UITableViewWrapperView' and ./*[@class='ContentFilterTableViewCell' and @onScreen='true']]", "Down", 0, 2000, "NATIVE",ObjectCollection.get(filterXpath[i]).toString() , 0, 1000, 5, true);
//						//Retrieving the filter name
//						drawerTitle[i]=client.elementGetText("NATIVE",ObjectCollection.get(filterXpath[i]).toString() , 0);
//						//Updating whether the required filters are available or not
//						EF.Update_Step_Report("All the filter should be available", "Filter is avilable||Filter is not availble",
//									filterName[i].contains(drawerTitle[i]));
//					}
//				}
			}
			catch(Exception e)
			{
				System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
			}
		}
		
		public void Fn_Favorites_ClearFilter()
		{
			try
			{
				//********************Auto_Home_FavoritesEditPopOver_004 and _005****************//
				
				EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
				EF.Update_Step_Report("Favorites Edit Pop Over should appear", "Favorite Edits pop over is displayed||Favorited edit pop over is not displayed", true);
				
				if(Current_OS.equalsIgnoreCase("IOS"))
				{
					ST.NativeClick(ObjectCollection.get("Home_Favorite_FilterIcon").toString(), client);
					EF.Update_Step_Report("Clear filter option shold be available", "Clear filter option is available||Clear Filter option is not available",
							ST.NativeElementFound(ObjectCollection.get("Clear_Filter").toString(), client));
					EF.Update_TC_Report();
					EF.Update_Step_Report("Filter cross mark check is for Android","Android specific||Android specific",true);
				}
				else if(Current_OS.equalsIgnoreCase("Android"))
				{	
					ST.NativeClick(ObjectCollection.get("Home_Favorite_FilterIcon").toString(), client);
					EF.Update_Step_Report("Clear filter option shold be available", "Clear filter option is available||Clear Filter option is not available",
							ST.NativeElementFound(ObjectCollection.get("Clear_Filter").toString(), client));
					EF.Update_TC_Report();
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
					EF.Update_Step_Report("Favorites Edit Pop Over should appear", "Favorite Edits pop over is displayed||Favorited edit pop over is not displayed",true);
					EF.Update_Step_Report("Clear filter option should have a cross mark symbol","Clear Filter cross mark is available||Clear filter cross mark is not available",true);	
				}
				
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
			}
			catch(Exception e)
			{
				System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
			}
		}
		public void Fn_Home_FavoritesPanel_ColdStartCheck()
		{
			//*****************These test cases should be executed upon initial login***************//
			try
			{
				//****************Auto_Home_FavoritesEditPopOver_006********************//
				EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
				//Verifying whether the My favorites tab is empty upon fresh initial login
				client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Home_Favorites_EditButton").toString(), 0, 1000, 5,false);
				EF.Update_Step_Report(" My Favorites panel displayed empty on fresh login","My Favorites panel is empty upon fresh login || My Favorites panel is not empty upon fresh login ",
						!(ST.NativeElementFound(ObjectCollection.get("Home_Favorite_MediaContainer").toString(), client)));
				EF.Update_TC_Report();
				
				//****************Auto_Home_FavoritesEditPopOver_007********************//
				//Verifying whether Help text is displayed in My favorites panel when no assets are available
				EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
				EF.Update_Step_Report("Help overlay should display Tap the edit button to add favorite channels for an empty My Favorites Panel","Help test is displayed || Help text is not displayed",
						ST.NativeElementFound(ObjectCollection.get("Home_EditFavorite_HelpMsg").toString(), client));
				client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
				
			}
			catch(Exception e)
			{
				System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
			}
		}
		public void Fn_Favorites_GreyedOutIcon()
		{
			try
			{
				//Checking the presence of Greyed out heart icon in favorites edit pop over
				client.swipeWhileNotFound("Up", 200, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, true);
				EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
				client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("Home_Favorites_EditButton").toString(), 0, 1000, 5, true);
				EF.Update_Step_Report("Favorites Edit Pop Over should appear", "Favorite Edits pop over is displayed||Favorited edit pop over is not displayed", 
						ST.NativeElementFound(ObjectCollection.get("Home_PopOver_Done").toString(), client));
				EF.Update_Step_Report("Greyed Out heart icon should be displayed ","Greyed out heart icon displayed||Greyed out heart icon not displayed",
						client.elementSwipeWhileNotFound("NATIVE", ObjectCollection.get("Favorite_PopOver_ChannelContainer").toString(), "Down", 0, 2000, "NATIVE",ObjectCollection.get("Favorite_Off").toString(), 0, 1000, 5, true));
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				client.swipeWhileNotFound("Up", 200, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
			}
			catch(Exception e)
			{
				System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
			}
		}
		public void Fn_Home_FavoritePanel_AssetPopOverCheck()
		{
			try
			{
				//**************Auto_Home_FavoritesEditPopOver_009*****************//
				String assetName1,assetName2;
				EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
				if(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Home_Favorite_AssetContainer").toString(), 0, 1000, 5, false))
				{
					//Retrieving and verifying whether Playing now text is available under My Favorites Panel
					assetName1= client.elementGetText("NATIVE",ObjectCollection.get("FavoritePanel_PlayingNow_Text").toString() , 0);
					EF.Update_Step_Report("Currently playing asset titles should be displayed as Playing now: Asset title under favorited channel logos in My Favorite panel",
							"Playing Now title is displayed||Playing Now title is not displayed", ST.NativeElementFound(ObjectCollection.get("FavoritePanel_PlayingNow_Text").toString(), client));
					EF.Update_TC_Report();
					
					//*****************Auto_Home_FavoritesEditPopOver_010**************//
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
					ST.NativeClick(ObjectCollection.get("Home_Favorite_AssetContainer").toString(), client);
					if(assetName1.isEmpty())
					{
						EF.Update_Step_Report("No text is displayed under palying Now","No text is displayed||No Text is displayed",false);
					}
					//retrieving asset name from asset pop over
					assetName2=client.elementGetText("NATIVE",ObjectCollection.get("KidZone_Asset_CoverArtTitle").toString() , 0);
					//Verifying whether the user is navigated to proper asset details screen on clicking the network logo under My Favorites panel
					EF.Update_Step_Report("User should be able to navigate to asset details on tapping network logo from My Favorite panel","Navigation to Asset pop over successfull || Navigation to asset popover unsuccessful",
							assetName1.contains(assetName2));
				}
				else
				{
					EF.Update_Step_Report("Asset should be available in favorites panel", "Asset not available in favorites panel||Asset not available in favorites panel",false);
				}
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				ST.NativeClick( ObjectCollection.get("Home_Favorites_EditButton").toString(), client);
				client.elementSwipeWhileNotFound("NATIVE", ObjectCollection.get("Favorite_PopOver_ChannelContainer").toString(), "Down", 0, 2000, "NATIVE",ObjectCollection.get("Favorite_On").toString(), 0, 1000, 5, true);
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
			}
			catch(Exception e)
			{
				System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
			}
		}
			public void Fn_Home_OnDemand_PanelCheck()
			{
				try
				{
					ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
						this.Fn_Com_Navigate("Home_MyLibrary"));
					EF.Update_Step_Report("My Library should contain On Demand Panel","On Demand Panel is available in My Library||On Demand Panel is not available in My Library",
							client.swipeWhileNotFound("Down", 50, 2000, "NATIVE", ObjectCollection.get("Home_Panel_OnDemand").toString(), 0, 1000, 5, false));
					EF.Update_TC_Report();
					
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
					EF.Update_Step_Report("My Library should contain On Demand Panel","On Demand Panel is available in My Library||On Demand Panel is not available in My Library",true);
					if(client.swipeWhileNotFound("Down", 50, 2000, "NATIVE", ObjectCollection.get("OnDemand_HelpText").toString(), 0, 1000, 5, false))
					{
						EF.Update_Step_Report("When No Assets are there a help message should be displayed","Help message is displayed||Help message is not displayed", true);
					}
					else if(ST.NativeElementFound(ObjectCollection.get("Downloads_AssetContainer").toString(), client))
					{
						EF.Update_Step_Report("Asset should not be available in Downloads panel to check help text ","Asset is not available||Asset is available", false);
					}
					else
					{
						EF.Update_Step_Report("When No Assets are there a help message should be displayed","Help message is displayed||Help message is not displayed", false);
					}
				}
				catch(Exception e)
				{
					System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
				}
				
			}
			

			public void Fn_Home_MediaBannerCheck()
			{
				try
				{
					ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
					//********************Auto_Home_HomeScreen_007*****************//
					EF.Update_Step_Report("User should be navigated to My Library", "User is navigated to My Library Screen||User is not navigated to My Library Screen", this.Fn_Com_Navigate("Home_MyLibrary"));
					client.sleep(10000);
					EF.Update_Step_Report("The Feature Banner should display with Assets appearing with Cover Art",
								"Feature Banner displayed with the Asset cover art||Feature Banner is not displayed with the asset cover art",
								ST.NativeElementFound(ObjectCollection.get("Home_MediaBanner_Content").toString(), client));
				}
				catch(Exception e)
				{
					System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
				}
			}
			
		
			
		public void Auto_IPVOD_Validations()
		{
			
			boolean Local_Result=true;
			int offset = client.p2cy(30);
			 	//Auto_IPVOD_Validations_001
			 	// Verification for the Display Recently Watched Shelf under My Library screen
		        Fn_Com_Navigate("Home_MyLibrary");
		        if(ST.NativeElementFound(ObjectCollection.get("Home_MediaBanner").toString(), client))
		        	EF.Update_Step_Report("Display Recently Watched Shelf under My Library screen","Recently Watched shelf should be available under My Library Screen||Recently Watched shelf is not available under My Library Screen", client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_RecentlyWatched").toString(), 0, 1000, 5, false));
		        else
		        {
		        	client.swipeWhileNotFound("Up", offset, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 10, false);
		        	EF.Update_Step_Report("Display Recently Watched Shelf under My Library screen","Recently Watched shelf should be available under My Library Screen||Recently Watched shelf is not available under My Library Screen", client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_RecentlyWatched").toString(), 0, 1000, 5, false));
		        }
		        EF.Update_TC_Report();
		        
		        //Auto_IPVOD_Validations_002
		        // Verification for the Browse All Page- Recently Watched
		        //Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Browse All Page- Recently Watched","Browse All Page- Recently Watched should be available under My Library Screen||Browse All Page- Recently Watched is not available under My Library Screen", client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_RecentlyWatched_BrowseAll").toString(), 0, 1000, 10, false));
		        
		        EF.Update_TC_Report();
		        
		        //Auto_IPVOD_Validations_003
		        // Verification for the Browse All Edit- Recently Watched
		        ST.NativeClick(ObjectCollection.get("Home_RecentlyWatched_BrowseAll").toString(), client);
		        EF.Update_Step_Report("Browse All Edit - Rcently Watched", "Edit option is available in Browse All Edit - Recently Watched||Edit option is not available in Browse All Edit - Recently Watched",
						ST.NativeElementFound(ObjectCollection.get("Home_RecentlyWatched_BrowseAll_Edit").toString(), client));
				EF.Update_TC_Report();
				
				//Auto_IPVOD_Validations_004
				// Verification for the Assets under Browse All Edit - Recently Watched view
		        EF.Update_Step_Report("Asseta under Browse All Edit - Rcently Watched", "Assets are available in Browse All Edit - Recently Watched||Assets are not available in Browse All Edit - Recently Watched", ST.NativeElementFound(ObjectCollection.get("Home_BrowseAll_Recentlywatched").toString(), client));
		        EF.Update_TC_Report();
				
		        //Auto_IPVOD_Validations_005
		        // Verification for the Recently Watched assets under Recently Watched Section
				Fn_Com_Navigate("Home_MyLibrary");
				if(ST.NativeElementFound(ObjectCollection.get("Home_MediaBanner").toString(), client))
					EF.Update_Step_Report("Display Recently Watched Shelf under My Library screen","Recently Watched shelf should be available under My Library Screen||Recently Watched shelf is not available under My Library Screen", client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_RecentlyWatched").toString(), 0, 1000, 10, false));
				else
				{
					client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 10, false);
					EF.Update_Step_Report("Display Recently Watched Shelf under My Library screen","Recently Watched shelf should be available under My Library Screen||Recently Watched shelf is not available under My Library Screen", client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_RecentlyWatched").toString(), 0, 1000, 10, false));
				}
				
		        EF.Update_Step_Report("Recently Watched assets under Recently Watched Section", "Recently Watched assets are availabe under Recently Watched Section||Recently Watched assets are not available under Recently Watched Section", ST.NativeElementFound(ObjectCollection.get("Home_RecentlyWatched_Assets").toString(), client));
		        EF.Update_TC_Report();
		        
		        //Auto_IPVOD_Validations_006
		        // Verification for Navigate to Asset details from Recently Watched shelf
		        //Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Display Recently Watched Shelf under My Library screen","Recently Watched shelf should be available under My Library Screen||Recently Watched shelf is not available under My Library Screen",
		                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_RecentlyWatched").toString(), 0, 1000, 10, false));
		        EF.Update_Step_Report("Recently Watched assets under Recently Watched Section", "Recently Watched assets are availabe under Recently Watched Section||Recently Watched assets are not available under Recently Watched Section",
						ST.NativeElementFound(ObjectCollection.get("Home_RecentlyWatched_Assets").toString(), client));
		        EF.Update_Step_Report("Click on Asset under Recently Watched Shelf", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Home_RecentlyWatched_Assets").toString(), client));
		        EF.Update_Step_Report("Navigate to Asset details from Recently Watched shelf", "successfully Navigate to Asset details screen from Recently Watched shelf|| Not Navigate to Asset details screen from Recently Watched shelf",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_ProgramTitle").toString(), client));
		        EF.Update_TC_Report();
		        
		        //Auto_IPVOD_Validations_007
		        EF.Update_Step_Report("Click on Navigation Bar from Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
		        
		        // Verification for the Display Last Channels Shelf under My Library screen
		        //Fn_Com_Navigate("Home_MyLibrary");
		        if(ST.NativeElementFound(ObjectCollection.get("Home_MediaBanner").toString(), client))
		        	EF.Update_Step_Report("Display Last Channels Shelf under My Library screen","Last Channels shelf should be available under My Library Screen||Last Channels shelf is not available under My Library Screen", client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_LastChannels").toString(), 0, 1000, 10, false));
		        else
		        {
		        	client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 10, false);
		        	EF.Update_Step_Report("Display Last Channels Shelf under My Library screen","Last Channels shelf should be available under My Library Screen||Last Channels shelf is not available under My Library Screen", client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_LastChannels").toString(), 0, 1000, 10, false));
		        }
		        
		        EF.Update_TC_Report();
		        
		        //Auto_IPVOD_Validations_008
		        // Verification for the Browse All Page- Last Channels
		        //Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Browse All Page- Last Channels","Browse All Page- Last Channels should be available under My Library Screen||Browse All Page- Last Channels is not available under My Library Screen",
		                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_LastChannels_BrowseAll").toString(), 0, 1000, 10, true));
		        EF.Update_TC_Report();
		        
		        //Auto_IPVOD_Validations_009
		        // Verification for the Browse All Edit- Last Channels
		        EF.Update_Step_Report("Browse All Edit - Last Channels", "Edit option is available in Browse All Edit - Last Channels||Edit option is not available in Browse All Edit - Last Channels",
						ST.NativeElementFound(ObjectCollection.get("Home_LastChannels_BrowseAll_Edit").toString(), client));
				EF.Update_TC_Report();
				
				//Auto_IPVOD_Validations_010
				// Verification for the Last Channels under Browse All Edit - Last Channels
		        EF.Update_Step_Report("Browse All Edit - Last Channels", "Last Channels are available in Browse All Edit - Last Channels||Last Channels are not available in Browse All Edit - Last Channels",
						ST.NativeElementFound(ObjectCollection.get("Home_BrowseAll_LastChannels").toString(), client));
				EF.Update_TC_Report();
				
				//Auto_IPVOD_Validations_011
				EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
		        
				// Verification for the Last Channels under Last Channels Shelf
		        Fn_Com_Navigate("Home_MyLibrary");
		        if(ST.NativeElementFound(ObjectCollection.get("Home_MediaBanner").toString(), client))
		               EF.Update_Step_Report("Display Last Channels Shelf under My Library screen","Last Channels shelf should be available under My Library Screen||Last Channels shelf is not available under My Library Screen", client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_LastChannels").toString(), 0, 1000, 10, false));
		        else
		        {
		        	client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 10, false);
		        	EF.Update_Step_Report("Display Last Channels Shelf under My Library screen","Last Channels shelf should be available under My Library Screen||Last Channels shelf is not available under My Library Screen", client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_LastChannels").toString(), 0, 1000, 10, false));
		        }
		        EF.Update_Step_Report("Recently Watched assets under Recently Watched Section", "Recently Watched assets are availabe under Recently Watched Section||Recently Watched assets are not available under Recently Watched Section",
						ST.NativeElementFound(ObjectCollection.get("Home_LastChannels_Assets").toString(), client));
		        EF.Update_TC_Report();
		        
		        //Auto_IPVOD_Validations_012
		        // Verification for the Stream asset from Last Channels under Last Channels Shelf
		        //Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Display Last Channels Shelf under My Library screen","Last Channels shelf should be available under My Library Screen||Last Channels shelf is not available under My Library Screen",
		                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_LastChannels").toString(), 0, 1000, 10, false));
		        EF.Update_Step_Report("Last Channels assets under Last Channel Section", "Last Channels assets are availabe under Last Channel Section||Last Channels assets are not available under Last Channel Section",
						ST.NativeElementFound(ObjectCollection.get("Home_LastChannels_Assets").toString(), client));
		        EF.Update_Step_Report("Click on Channel under Last Channel Shelf", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("Home_LastChannels_Assets").toString(), client));
		        
		        ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
		        
		        EF.Update_Step_Report("Check for Streaming the asset from Last Channels under Last Channels Shelf", "Streaming is happening the asset from Last Channels under Last Channels Shelf||Streaming is not happening the asset from Last Channels under Last Channels Shelf",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Done").toString(), client));
		        EF.Update_Step_Report("Click on Done button", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("VideoPlayer_Btn_Done").toString(), client));
		        EF.Update_TC_Report();
		        
		        //Auto_IPVOD_Validations_013
		        client.swipeWhileNotFound("Up", offset, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 10, false);
		        // Verification for the Display Download Shelf under My Library screen
		        Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Display Downloads Shelf under My Library screen","Downloads shelf is available under My Library Screen||Downloads shelf is not available under My Library Screen",
		                client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_Downloads").toString(), 0, 1000, 10, false));
		        EF.Update_TC_Report();
		        
		        //Auto_IPVOD_Validations_014
		        // Verify that the Display Help Message on empty Downloads shelf under My Library screen
				EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
				EF.Update_Step_Report("Display Downloads Shelf under My Library screen","Downloads shelf should be available under My Library Screen||Downloads shelf is not available under My Library Screen",
		                client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_Downloads").toString(), 0, 1000, 10, false));
				String Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Download_Textmessage").toString(),0, client);
				if(Current_OS.equalsIgnoreCase("IOS"))
				{
					if (Temp_Var.equalsIgnoreCase("Browse On Demand to download Movies and TV shows"))
						Local_Result = true;
					else
						Local_Result = false;
				}
				else
				{
					if (Temp_Var.equalsIgnoreCase("Browse On Demand to download Movies and TV Shows"))
						Local_Result = true;
					else
						Local_Result = false;
				}
				
				EF.Update_Step_Report("Verify text Message under Download Section in My Library Screen", "Sucessfully Verified||Verification failed",Local_Result);
				Local_Result = true;
				
				EF.Update_TC_Report();
				
				//Auto_IPVOD_Validations_015
				// Verification for the Hidden Browse All link_Downloads Shelf with '0' downloads
				//Fn_Com_Navigate("Home_MyLibrary");
				if(ST.NativeElementFound(ObjectCollection.get("Home_MediaBanner").toString(), client))
					EF.Update_Step_Report("Display Downloads Shelf under My Library screen","Downloads shelf should be available under My Library Screen||Downloads shelf is not available under My Library Screen", client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Downloads").toString(), 0, 1000, 10, false));
				else
				{
					client.swipeWhileNotFound("Up", offset, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 2000, 5, false);
					EF.Update_Step_Report("Display Downloads Shelf under My Library screen","Downloads shelf should be available under My Library Screen||Downloads shelf is not available under My Library Screen", client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Downloads").toString(), 0, 1000, 10, false));
				}
				EF.Update_Step_Report("Hidden Browse All link_Downloads Shelf with Zero downloads", "Browse All link should not be hidden when the Downloads Shelf with Zero downloads||Browse All link should be hidden when the Downloads Shelf with Zero downloads", ST.NativeElementFound(ObjectCollection.get("Home_Downloads_BrowseAll_hidden").toString(), client));
				
				EF.Update_TC_Report();
				
				//Auto_IPVOD_Validations_016
				client.swipeWhileNotFound("Up", offset, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 10, false);
		        // Verification for the Display My Favorites shelf under My Library Screen
		        //Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Display My Favorites Shelf under My Library screen","My Favorites shelf should be available under My Library Screen||My Favorites shelf is not available under My Library Screen",
		                client.swipeWhileNotFound("Down", offset, 2000, "NATIVE", ObjectCollection.get("Home_MyFavorites").toString(), 0, 1000, 10, false));
		        EF.Update_TC_Report();
		        //Auto_IPVOD_Validations_017
		        // Verification for the Display of Edit button under My Favorites shelf
		        EF.Update_Step_Report("Display of Edit button under My Favorites shelf", "Edit button should be available under My Favorites Shelf||Edit button should not be available under My Favorites Shelf",
						ST.NativeElementFound(ObjectCollection.get("Home_MyFavorites_Btn_Edit").toString(), client));
		        EF.Update_TC_Report();
		        //Auto_IPVOD_Validations_018
		        client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 10, false);
		        // Verification for the Display Watchlist shelf under My Library Screen
		        //Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Display Watchlist Shelf under My Library screen","Watchlist shelf should be available under My Library Screen||Watchlist shelf is not available under My Library Screen",
		                 client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist").toString(), 0, 1000, 10, false));
		        EF.Update_TC_Report();
		      //Auto_IPVOD_Validations_019
		        // Verification for the Display of Browse All button under Watchlist shelf (Asset Should be there under Watchlist shelf)
		        EF.Update_Step_Report("Display of BrowseAll button under Watchlist shelf", "BrowseAll button should be available under Watchlist Shelf||BrowseAll button should not be available under Watchlist Shelf",
		        		ST.NativeElementFound(ObjectCollection.get("Home_Watchlist_BrowseAll").toString(), client));
		        EF.Update_TC_Report();
		      //Auto_IPVOD_Validations_020
		        // Verification for the Browse All Page- Watchlist
		        //Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Browse All Page- Recently Watched","Browse All Page- Recently Watched should be available under My Library Screen||Browse All Page- Recently Watched is not available under My Library Screen",
		                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist_BrowseAll").toString(), 0, 1000, 10, true));
		        EF.Update_TC_Report();
		        
		        //Auto_IPVOD_Validations_021
		        // Verification for the Browse All Edit- Watchlist
		        EF.Update_Step_Report("Browse All Edit - Rcently Watched", "Edit option is available in Browse All Edit - Recently Watched||Edit option is not available in Browse All Edit - Recently Watched",
						ST.NativeElementFound(ObjectCollection.get("Home_Watchlist_BrowseAll_Edit").toString(), client));
				EF.Update_TC_Report();
				
				//Auto_IPVOD_Validations_022
				// Verification for the Assets under Browse All Edit - Watchlist
		        EF.Update_Step_Report("Asseta under Browse All Edit - Rcently Watched", "Assets are available in Browse All Edit - Recently Watched||Assets are not available in Browse All Edit - Recently Watched",
						ST.NativeElementFound(ObjectCollection.get("Home_BrowseAll_Watchlist").toString(), client));
		        EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
		    
//		        // Verify that the  Movies & Series panels are displayed for Showtime Network for IPVOD Assets
//				EF.Update_Step_Report("Navigate to On Demand", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_OnDemand"));
//				if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
//				{
//					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
//				}
//				//For iOS
////				if(Current_OS == "IOS")
////				{}
//				if(Current_OS.equalsIgnoreCase("IOS"))
//				{
//					EF.Update_Step_Report("Click on Watch on TV option in Guide screen", "Clicked successfully||Click failed",
//					ST.NativeClick(ObjectCollection.get("OnDemand_WatchonTV").toString(), client));
//					EF.Update_Step_Report("Click on Premium Tab in On Demand Screen", "Clicked sucessfully||Click failed",
//							ST.NativeClick(ObjectCollection.get("OnDemand_Tab_Premium").toString(), client));
//		        this.findShowTimeAsset();	
//		        EF.Update_Step_Report("Click on Showtime Network under Premium Tab", "Clicked sucessfully||Click failed",
//							ST.NativeElementFound(ObjectCollection.get("BackButton").toString(), client));	
//					// Need to impement the script changes to fix the flow
//					EF.Update_Step_Report("Check for Movies Panel for Showtime Network", "Movies Panel is availble Under Showtime Network||Movies Panel is not availble Under Showtime Network",
//							ST.NativeElementFound(ObjectCollection.get("Premium_Showtime_Network_MoviesPanel").toString(), client));
//					EF.Update_Step_Report("Check for Series Panel for Showtime Network", "Series Panel is availble Under Showtime Network||Series Panel is not availble Under Showtime Network",
//							ST.NativeElementFound(ObjectCollection.get("Premium_Showtime_Network_SeriesPanel").toString(), client));
////					EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
////							ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
//					//EF.Update_TC_Report();
//				}
//				// For Android
//				else
//				{
//					EF.Update_Step_Report("Click on Watch on Dropdown Box", "Clicked successfully||Click failed",
//							ST.NativeClick(ObjectCollection.get("OnDemand_Watch_On_DropdownBox").toString(), client));
//					EF.Update_Step_Report("Click on Watch on TV option in Watch On Dropdown Box", "Clicked successfully||Click failed",
//							ST.NativeClick(ObjectCollection.get("OnDemand_Watch_On_DropdownBox_WatchonTV").toString(), client));
//					EF.Update_Step_Report("Click on Watch on TV option in Watch On Dropdown Box", "Clicked successfully||Click failed",
//							ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
//					EF.Update_Step_Report("Click on Premium Tab in On Demand Screen", "Clicked sucessfully||Click failed",
//							ST.NativeClick(ObjectCollection.get("OnDemand_Tab_Premium").toString(), client));
//					int count=client.getElementCount("NATIVE", ObjectCollection.get("OnDemand_Premiums_ShowTimeNetwork").toString());
//			       for(int i=0;i<count;i++)
//			       {
//			             client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_Premiums_ShowTimeNetwork").toString(), i, 1000, 5, true);
//			            String str=client.elementGetText("NATIVE",ObjectCollection.get("Home_TitleBar").toString() , 0);
//			            if(str.equalsIgnoreCase("Showtime"))
//			            {
//			          	  EF.Update_Step_Report("Check for Movies Section under Showtime Network", "Movies Section is available under Showtime Network||Movies Section is not available under Showtime Network",
//			        				ST.NativeElementFound(ObjectCollection.get("Showtime_Network_Movies").toString(), client));
//			        		  EF.Update_Step_Report("Check for Series Section under Showtime Network", "Series Section is available under Showtime Network||Series Section is not available under Showtime Network",
//			        				ST.NativeElementFound(ObjectCollection.get("Showtime_Network_Series").toString(), client));
//			        		break;
//			             }
//			            else
//			            {
//			                 ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
//			                  ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
//			                  if(i==(count-1))
//			                  {
//			                        EF.Update_Step_Report("Show Time should be avalilable","Shoe time is available||Show time is not available", false);
//			                  }
//			            }
//			        }
//				//EF.Update_TC_Report();
//				EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
//					ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
//				if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
//				{
//					//client.sendText("{ESC}");
//					ST.NativeClick(ObjectCollection.get("OnDemand_welcome_btn_OK").toString(), client);
//				}
//			}
				/* EF.Update_TC_Report();
				//Auto_IPVOD_Validations_023
				// Verify that the  Movies & Series panels are displayed for StarZ Network for IPVOD Assets
				// For iOS
				if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("Click on StarZ Network under Premium Tab", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("Premium_StarZ_Network").toString(), client));	
					EF.Update_Step_Report("Check for Movies Panel for StarZ Network", "Movies Panel is availble Under StarZ Network||Movies Panel is not availble Under StarZ Network",
							ST.NativeElementFound(ObjectCollection.get("Premium_StarZ_Network_MoviesPanel").toString(), client));
					EF.Update_Step_Report("Check for Series Panel for StarZ Network", "Series Panel is availble Under StarZ Network||Series Panel is not availble Under StarZ Network",
							ST.NativeElementFound(ObjectCollection.get("Premium_StarZ_Network_SeriesPanel").toString(), client));
					EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
					//EF.Update_TC_Report();
				}
				
				// For Android
				else
				{
					int count1=client.getElementCount("NATIVE", ObjectCollection.get("OnDemand_Premiums_ShowTimeNetwork").toString());
			        for(int i=0;i<count1;i++)
			        {
			              client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_Premiums_ShowTimeNetwork").toString(), i, 1000, 5, true);
			              String str=client.elementGetText("NATIVE",ObjectCollection.get("Home_TitleBar").toString() , 0);
			              if(str.equalsIgnoreCase("StarZ"))
			              {
			            	  EF.Update_Step_Report("Check for Movies Section under StarZ Network", "Movies Section is available under StarZ Network||Movies Section is not available under StarZ Network",
			          				ST.NativeElementFound(ObjectCollection.get("Showtime_Network_Movies").toString(), client));
			          		EF.Update_Step_Report("Check for Series Section under StarZ Network", "Series Section is available under StarZ Network||Series Section is not available under StarZ Network",
			          				ST.NativeElementFound(ObjectCollection.get("Showtime_Network_Series").toString(), client));
			              break;
			              }
			              {
			                  ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
			                  ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
			                 if(i==(count1-1))
			                  {
			                        EF.Update_Step_Report("Show Time should be avalilable","Shoe time is available||Show time is not available", false);
			                  }
			              }
			        	}
				        EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
								ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
				        //EF.Update_TC_Report();
						if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
						{
							client.sendText("{ESC}");
						}
					}
					EF.Update_TC_Report();
					//Auto_IPVOD_Validations_024
					// Verify that Stream icon is displayed for IPVOD assets for Showtime Network
					if(Current_OS.equalsIgnoreCase("IOS"))
					{
						EF.Update_Step_Report("Click on Showtime Network under Premium Tab", "Clicked sucessfully||Click failed",
								ST.NativeClick(ObjectCollection.get("Premium_Showtime_Network").toString(), client));	
						EF.Update_Step_Report("Click on Movies PosterArt under Premium Tab Showtime Network", "Clicked sucessfully||Click failed",
								ST.NativeClick(ObjectCollection.get("OnDemand_Premium_Showtime_MoviePosterArt").toString(), client));
						EF.Update_Step_Report("Check for Stream icon is displayed for Streamable assets", "Stream icon is displayed for IPVOD assets||Stream icon is not displayed for IPVOD assets",
								ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Play").toString(), client));
						//EF.Update_TC_Report();
					}
					
					// For Android
					else
					{
						int count6=client.getElementCount("NATIVE", ObjectCollection.get("OnDemand_Premiums_ShowTimeNetwork").toString());
						for(int i=0;i<count6;i++)
						{
				             client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_Premiums_ShowTimeNetwork").toString(), i, 1000, 5, true);
				             String str=client.elementGetText("NATIVE",ObjectCollection.get("Home_TitleBar").toString() , 0);
				             if(str.equalsIgnoreCase("Showtime"))
				             {
				          				ST.NativeClick(ObjectCollection.get("OnDemand_Premium_Showtime_MoviePosterArt").toString(), client);
				          		EF.Update_Step_Report("Check for Stream icon is displayed for IPVOD assets", "Stream icon is displayed for IPVOD assets||Stream icon is not displayed for IPVOD assets",
				          				ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Play").toString(), client));
				          		//EF.Update_TC_Report();
				          		break;
				              }
				              {
				                  ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				                  ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				                  if(i==(count6-1))
				                  {
				                        EF.Update_Step_Report("Show Time should be avalilable","Shoe time is available||Show time is not available", false);
				                  }
				            }
				        }
					}
					EF.Update_TC_Report();
						
				// Verify that Stream icon is displayed for IPVOD assets under CoverArt
				EF.Update_Step_Report("Check for Stream icon is displayed for IPVOD assets under PosterArt", "Stream icon is displayed for IPVOD assets||Stream icon is not displayed for IPVOD assets",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_TC_Report();
				
				// Verify that wheather Streaming or not for IPVOD Assets
				EF.Update_Step_Report("Check for Stream icon is displayed for IPVOD assets under PosterArt", "Stream icon is displayed for IPVOD assets||Stream icon is not displayed for IPVOD assets",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_Step_Report("Click on Play Iocn Under PosterArt in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_Step_Report("Check for Streaming of IPVOD Assets", "Streaming is happening successfully for IPVOD Assets||Streaming is not happening for IPVOD Assets",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Done").toString(), client));
				EF.Update_TC_Report();	
				
				
				// Verification for the Done button under IPVOD Video Player
				EF.Update_Step_Report("Check for Done button under IPVOD Video Player", "Done button is available under IPVOD Video Player||Done button is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Done").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Progress bar under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_ProgressBar").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for Progress bar under IPVOD Video Player", "Progress bar is available under IPVOD Video Player||Progress bar is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_ProgressBar").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Time Indication of the video under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_TimeIndication").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for Time Indication under IPVOD Video Player", "Time Indication is available under IPVOD Video Player||Time Indication is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_TimeIndication").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Go back 10 minutes button under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_GoBack10Minutes").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for Go back 10 minutes button under IPVOD Video Player", "Go back 10 minutes button is available under IPVOD Video Player||Go back 10 minutes button is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_GoBack10Minutes").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Pause Icon under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Pause").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for Pause button under IPVOD Video Player", "Pause button is available under IPVOD Video Player||Pause button is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Pause").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Play Icon under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Play").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Click on Play Iocn Under Video Player", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("VideoPlayer_Btn_Pause").toString(), client));
				EF.Update_Step_Report("Check for Pause button under IPVOD Video Player", "Pause button is available under IPVOD Video Player||Pause button is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Play").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Help Icon under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Help").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for Help button under IPVOD Video Player", "Help button is available under IPVOD Video Player||Help button is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Help").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Volume bar button under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_VolumeBar").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for Volume bar button under IPVOD Video Player", "Volume bar button is available under IPVOD Video Player||Volume bar button is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_VolumeBar").toString(), client));
				EF.Update_TC_Report();
				
				 //Verification for the CC button under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_CC").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for CC button under IPVOD Video Player", "CC button is available under IPVOD Video Player||CC button is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_CC").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Zoom button under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Zoom").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for Zoom button under IPVOD Video Player", "Zoom button is available under IPVOD Video Player||Zoom button is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Zoom").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the More Info secion is available under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_MoreInfo").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for More Info Section under IPVOD Video Player", "More Info Section is available under IPVOD Video Player||More Info Section is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_MoreInfo").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Cast Crew secion is available under IPVOD Video Player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_CastCrew").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for Cast & Crew Section under IPVOD Video Player", "Cast & Crew Section is available under IPVOD Video Player||Cast & Crew Section is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_CastCrew").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Photes section is available under IPVOD Video player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Photes").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for Photes Section under IPVOD Video Player", "Photes Section is available under IPVOD Video Player||Photes Section is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Photes").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the More Like This section is available under IPVOD Video player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Morelikethis").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Check for More Like This Section under IPVOD Video Player", "More Like This Section is available under IPVOD Video Player||More Like This Section is not available under IPVOD Video Player",
						ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Morelikethis").toString(), client));
				EF.Update_TC_Report();

				// Check for Asset details page when we click on the Done button in Video player
				if(!(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_Btn_Done").toString(), client)));
				{
					ST.NativeClick(ObjectCollection.get("Videoplayer_Click").toString(), client);
				}
				EF.Update_Step_Report("Click on Done button under Video Player", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("VideoPlayer_Btn_Done").toString(), client));
				EF.Update_Step_Report("Check for Asset Details page after clicking Done button under video player", "After clicking Done button under video player its redirecting to the Asset Details page||After clicking Done button under video player is not redirecting to the Asset Details page",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_TC_Report();
				
				// Verify that Play Icon is displayed for IPVOD Assets
				EF.Update_Step_Report("Check for Play icon is displayed for IPVOD assets", "Play icon is displayed for IPVOD assets||Play icon is not displayed for IPVOD assets",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Play").toString(), client));
				EF.Update_TC_Report();
				
				// Verify that the Restart option is available for IPVOD Assets under Play Icon
				EF.Update_Step_Report("Check for Play icon is displayed for IPVOD assets", "Play icon is displayed for IPVOD assets||Play icon is not displayed for IPVOD assets",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Play").toString(), client));
				EF.Update_Step_Report("Click on Play Iocn in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Btn_Play").toString(), client));
				EF.Update_Step_Report("Check for Restart option is available for IPVOD Assets under Play Icon", "Restart option is available for IPVOD Assets under Play Icon||Restart option is not available for IPVOD Assets under Play Icon",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Play_Restart").toString(), client));
				EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("AssetDetails_Btn_Play").toString(), client));
				EF.Update_TC_Report();
				
				// Verify that the Resume option is available for IPVOD Assets under Play Icon
				EF.Update_Step_Report("Check for Play icon is displayed for IPVOD assets", "Play icon is displayed for IPVOD assets||Play icon is not displayed for IPVOD assets",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Play").toString(), client));
				EF.Update_Step_Report("Click on Play Iocn in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Btn_Play").toString(), client));
				EF.Update_Step_Report("Check for Resume option is available for IPVOD Assets under Play Icon", "Resume option is available for IPVOD Assets under Play Icon||Resume option is not available for IPVOD Assets under Play Icon",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Play_Resume").toString(), client));
				EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("AssetDetails_Btn_Play").toString(), client));
				EF.Update_TC_Report();
				
				// Verify that Play Icon is displayed under Poster Artfor IPVOD Assets
				EF.Update_Step_Report("Check for Play icon is displayed under PosterArt for IPVOD assets", "Play icon is displayed under PosterArt for IPVOD assets||Play icon is not displayed under PosterArt for IPVOD assets",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_TC_Report();		
				
				// Verify that the Restart option is available for IPVOD Assets under Poster Art Play Icon
				EF.Update_Step_Report("Check for Play icon is displayed for IPVOD assets under Poster Art Play Icon", "Play icon is displayed for IPVOD assets under Poster Art Play Icon||Play icon is not displayed for IPVOD assets under Poster Art Play Icon",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_Step_Report("Click on Play Iocn in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_Step_Report("Check for Restart option is available for IPVOD Assets under Play Icon", "Restart option is available for IPVOD Assets under Play Icon||Restart option is not available for IPVOD Assets under Play Icon",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Play_Restart").toString(), client));
				EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_TC_Report();
				
				// Verify that the Resume option is available for IPVOD Assets under Play Icon
				EF.Update_Step_Report("Check for Play icon is displayed for IPVOD assets under Poster Art Play Icon", "Play icon is displayed for IPVOD assets under Poster Art Play Icon||Play icon is not displayed for IPVOD assets under Poster Art Play Icon",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_Step_Report("Click on Play Iocn in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_Step_Report("Check for Resume option is available for IPVOD Assets under Play Icon", "Resume option is available for IPVOD Assets under Play Icon||Resume option is not available for IPVOD Assets under Play Icon",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Play_Resume").toString(), client));
				EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("AssetDetails_PosterArt_Btn_Play").toString(), client));
				EF.Update_TC_Report();
				
				// Verify that Download Icon is displayed for IPVOD Assets
				EF.Update_Step_Report("Check for Download icon is displayed for IPVOD assets", "Download icon is displayed for IPVOD assets||Download icon is not displayed for IPVOD assets",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Download").toString(), client));
				EF.Update_TC_Report();
				
				// Verify that Start On Demand Icon is displayed for IPVOD Assets
				EF.Update_Step_Report("Check for Start On Demand icon is displayed for IPVOD assets", "Download icon is displayed for IPVOD assets||Start On Demand icon is not displayed for IPVOD assets",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_StartONDemandIcon").toString(), client));
				EF.Update_TC_Report();
				
				// Verify the Text Message for Start On Demand for IPVOD Assets
				EF.Update_Step_Report("Click on Start On Demand Icon", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Btn_StartONDemand").toString(), client));
				String StartOnDemand = ST.NativeGetElementText(ObjectCollection.get("StartOnDemand_Confirmation_Message").toString(),0, client);
				if (!(StartOnDemand.contains("Your TV must be on and tuned to any live channel for Send To TV to work properly. On Demand, Guide and Menu pages will disable this feature.")))
				{
					Local_Result = false;
				}
				EF.Update_Step_Report("Verify Confirmation message for Start On Demand", "Confirmation Message for Start On Demand is getting successfully|| Confirmation Message for Start On Demand is not getting successfully",Local_Result);
				Local_Result = true;
				EF.Update_TC_Report();
				EF.Update_Step_Report("Click on OK button in Send to TV model", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("SendtoTV_Model_Btn_OK").toString(), client));
				
				// Verify that Watchlist Icon is displayed for IPVOD Assets
				EF.Update_Step_Report("Check for Watchlist icon is displayed for Streamable assets", "Watchlist icon is displayed for Streamable assets||Watchlist icon is not displayed for Streamable assets",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_Watchlist").toString(), client));
				EF.Update_TC_Report();
				EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
		        EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
		        
		        // For Android
		        if(Current_OS=="ANDROID")
		        {
			        if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
					{
						client.sendText("{ESC}");
					}
		        }
		        
		        // Verify the Episodes panel list under On Demand Premium Showtime TV Series for IPVOD Assets
				// For iOS
		        if(Current_OS.equalsIgnoreCase("IOS"))
		        {
			        EF.Update_Step_Report("Click on StarZ Network under Premium Tab", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("Premium_Showtime_Network").toString(), client));	
					EF.Update_Step_Report("Click on Series PosterArt under Premium Tab", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("OnDemand_Premium_Showtime_TVPosterArt").toString(), client));
					EF.Update_Step_Report("Check for Episodes panel list under On Demand Premium Showtime TV Series", "Episodes panel list should be available under On Demand Premium Showtime TV Series||Episodes panel list should not be available under On Demand Premium Showtime TV Series",
							ST.NativeElementFound(ObjectCollection.get("AssetDetails_EpisodePanellist").toString(), client));
					EF.Update_TC_Report();
		        }
				
				// For Android
		        else
		        {
					int count2=client.getElementCount("NATIVE", ObjectCollection.get("OnDemand_Premiums_ShowTimeNetwork").toString());
			        for(int i=0;i<count2;i++)
			        {
			              client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_Premiums_ShowTimeNetwork").toString(), i, 1000, 5, true);
			              String str=client.elementGetText("NATIVE",ObjectCollection.get("Home_TitleBar").toString() , 0);
			              if(str.equalsIgnoreCase("Showtime"))
			              {
			          		EF.Update_Step_Report("Click on Series PosterArt under Premium Tab", "Clicked sucessfully||Click failed",
			        				ST.NativeClick(ObjectCollection.get("OnDemand_Premium_Showtime_TVPosterArt").toString(), client));
			        		EF.Update_Step_Report("Check for Episodes panel list under On Demand Premium Showtime TV Series", "Episodes panel list should be available under On Demand Premium Showtime TV Series||Episodes panel list should not be available under On Demand Premium Showtime TV Series",
			        				ST.NativeElementFound(ObjectCollection.get("AssetDetails_EpisodePanellist").toString(), client));
			        		EF.Update_TC_Report();
			        		break;
			              }
			              {
			                  ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
			                  ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
			                  if(i==(count2-1))
			                  {
			                        EF.Update_Step_Report("Show Time should be avalilable","Shoe time is available||Show time is not available", false);
			                  }
			              }
			        }
		        }

		        // Verify the See All Episodes button
				EF.Update_Step_Report("Check for See All Episodes button", "See All Episodes button is available||See All Episodes button is not available",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_SeeAllEpisodes").toString(), client));
				EF.Update_TC_Report();
				
				// Verify the See All Episodes button redirecting to the Episode Panel list screen
				EF.Update_Step_Report("Click on See All Episodes button", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Btn_SeeAllEpisodes").toString(), client));
				EF.Update_Step_Report("Check for Episodes panel list under On Demand Premium Showtime TV Series", "Episodes panel list should be available under On Demand Premium Showtime TV Series||Episodes panel list should not be available under On Demand Premium Showtime TV Series",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_EpisodePanellist").toString(), client));
				EF.Update_TC_Report();
				
				// Verify the Season details section in Episode panel list screen
				EF.Update_Step_Report("Check for Season details section in Episode panel list screen", "Season details section should be available in Episode panel list screen||Season details section should not be available in Episode panel list screen",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_SeasonTitle").toString(), client));
				EF.Update_TC_Report();
				
				// Verify the Other Times option in Episodes Asset details screen
				EF.Update_Step_Report("Check for Episodes panel list under On Demand Premium Showtime TV Series", "Episodes panel list should be available under On Demand Premium Showtime TV Series||Episodes panel list should not be available under On Demand Premium Showtime TV Series",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_EpisodePanellist").toString(), client));
				EF.Update_Step_Report("Click on Episode Name under Episode panel list", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_TV_Asset_SeriesEpisode1").toString(), client));
				EF.Update_Step_Report("Check for Other times option in Episodes Asset details screen", "Other times option is available in Episodes Asset details screen||Other times option is not available in Episodes Asset details screen",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_OtherTimes").toString(), client));
				EF.Update_TC_Report();
				
				// Verify the Season details section in Episodes Asset details screen
				EF.Update_Step_Report("Check for Season details section in Episodes Asset details screen", "Season details section should be available in Episodes Asset details screen||Season details section should not be available in Episodes Asset details screen",
						ST.NativeElementFound(ObjectCollection.get("AssetDetails_SeasonTitle").toString(), client));
				EF.Update_TC_Report();
				
				// Verify the See All Episodes button after clicking the See All Episodes button
				EF.Update_Step_Report("Check for See All Episodes button", "See All Episodes button is not available||See All Episodes button is available",
						!(ST.NativeElementFound(ObjectCollection.get("AssetDetails_Btn_SeeAllEpisodes").toString(), client)));
				EF.Update_TC_Report();
				
				EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
		        EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
		        EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("AssetDetails_Backbutton").toString(), client));
		        
		        // For Android
		        if(Current_OS=="Android")
		        {
			        if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
					{
			        	ST.NativeClick(ObjectCollection.get("OnDemand_welcome_btn_OK").toString(), client);
					}
		        }
		        
		        // Verification for the On Demand Welcome Text Message		
				EF.Update_Step_Report("Navigate to On My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
				EF.Update_Step_Report("Navigate to On Demand", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_OnDemand"));
				EF.Update_Step_Report("Check for Series Section under Showtime Network", "Series Section is available under Showtime Network||Series Section is not available under Showtime Network",
						ST.NativeElementFound(ObjectCollection.get("OnDemand_Welcome_Message").toString(), client));
				EF.Update_TC_Report();
				
				if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_welcome_btn_OK").toString(), client);
				}
		        
		        // Verify the Home as My Library
				EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
				EF.Update_Step_Report("Check for Home as My Library Text", "Home text should be My Library text||Home text is not changed to My Library Text",
						ST.NativeElementFound(ObjectCollection.get("Home_MyLibrary_Text").toString(), client));
				EF.Update_TC_Report();

				// Verify the My Library as default landing page
				//EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
				EF.Update_Step_Report("Check for My Library Screen after logging with valid Credentials", "Suucessfully logged into App with Valid Credentials||Login Failed with Valid Credentials",
						ST.NativeElementFound(ObjectCollection.get("Home_MyLibrary_Text").toString(), client));
				EF.Update_TC_Report();
				
				// Verify the My Library header update from Charter Logo to 'My Library'
				EF.Update_Step_Report("Check for Charter LOGO displyaed in My Library Screen", "Charter LOGO is displyaed in My Library Screen||Charter LOGO is not displyaed in My Library Screen",
						ST.NativeElementFound(ObjectCollection.get("Home_NewLOGO").toString(), client));
				EF.Update_TC_Report();
				
				// Verification for the Customize button in My Library Panel
				//Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Customize panel should be at the bottom","Customize panel is at the bottom||Customize panel is not at the bottom",
		                    client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_CustomizeButton").toString(), 0, 1000, 10, false));
		        EF.Update_TC_Report();
		        
		        // Verification for the Customize text message in My Library Panel
		        //Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Customize text message should be available at the bottom","Customize test message is at the bottom||Customize text message is not at the bottom",
		                    client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Customizetextmessage").toString(), 0, 1000, 10, false));
		        EF.Update_TC_Report();
				
		        // Verification for the Customize option in On Demand Movies Section
		        Fn_Com_Navigate("Home_OnDemand");
		        if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
				{
		        	ST.NativeClick(ObjectCollection.get("OnDemand_welcome_btn_OK").toString(), client);
				}
		        // For iOS
		        if(Current_OS.equalsIgnoreCase("IOS"))
			        {
			        EF.Update_Step_Report("Customize option should be at the bottom","Customize option is at the bottom||Customize option is not at the bottom",
			                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_Movies_CustomizeButton").toString(), 0, 1000, 10, false));
			        EF.Update_TC_Report();
			        }
		        
		        // For Android
		        else
		        {
			        EF.Update_Step_Report("Click on Watch on TV option in Watch On Dropdown Box", "Clicked successfully||Click failed",
							ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
					EF.Update_Step_Report("Check for Movies Section On Selecting the Watchon TV", "Movies Section is available||Movies Section is not available",
							ST.NativeElementFound(ObjectCollection.get("OnDemand_Movies").toString(), client));
					EF.Update_Step_Report("Click on Movies Tab in On Demand Screen", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("OnDemand_Movies").toString(), client));
					EF.Update_Step_Report("Customize option should be at the bottom","Customize option is at the bottom||Customize option is not at the bottom",
			                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_Movies_CustomizeButton").toString(), 0, 1000, 10, false));
			        EF.Update_TC_Report();
		        }
		        
		        // Verification for the Customize Text Message in On Demand Movies Section
		        EF.Update_Step_Report("Customize text message should be at the bottom","Customize text message is at the bottom||Customize message is not at the bottom",
		                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_Movies_Customizetextmessage").toString(), 0, 1000, 10, false));
		        EF.Update_TC_Report();
		        
		        // Verification for the Customize option in On Demand TV Section
		        // For iOS
		        if(Current_OS.equalsIgnoreCase("IOS"))
		        {
			        EF.Update_Step_Report("Click on TV Tab in On Demand Screen", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
			        EF.Update_Step_Report("Customize option should be at the bottom","Customize option is at the bottom||Customize option is not at the bottom",
			                    client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_TV_CustomizeButton").toString(), 0, 1000, 10, false));
			        EF.Update_TC_Report();
		        }
		        // For Android
		        else
		        {
			        EF.Update_Step_Report("Click on Watch on TV option in Watch On Dropdown Box", "Clicked successfully||Click failed",
							ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
					EF.Update_Step_Report("Check for TV Section On Selecting the Watchon TV", "Movies Section is available||Movies Section is not available",
							ST.NativeElementFound(ObjectCollection.get("OnDemand_TV").toString(), client));
					EF.Update_Step_Report("Click on Movies Tab in On Demand Screen", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Loading").toString(), 0, 100000);
					EF.Update_Step_Report("Customize option should be at the bottom","Customize option is at the bottom||Customize option is not at the bottom",
			                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_TV_CustomizeButton").toString(), 0, 1000, 10, false));
					EF.Update_TC_Report();
		        }
		        
		        // Verification for the Customize Text Message in On Demand TV Section
		        EF.Update_Step_Report("Customize text message should be at the bottom","Customize text message is at the bottom||Customize message is not at the bottom",
		                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_TV_Customizetextmessage").toString(), 0, 1000, 10, false));
		        EF.Update_TC_Report();
		        
		        // Verification for the Customize option in On Demand Networks Section
		        if(Current_OS.equalsIgnoreCase("IOS"))
		        {
			        EF.Update_Step_Report("Click on TV Tab in On Demand Screen", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("OnDemand_Networks").toString(), client));
			        EF.Update_Step_Report("Customize option should not be at the bottom","Customize option is not at the bottom||Customize option is at the bottom",
			                    !(client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_TV_CustomizeButton").toString(), 0, 1000, 10, false)));
			        EF.Update_TC_Report();
		        }
		        // For Android
		        else
		        {
			        EF.Update_Step_Report("Click on Watch on TV option in Watch On Dropdown Box", "Clicked successfully||Click failed",
							ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
					EF.Update_Step_Report("Check for Networks Section On Selecting the Watchon TV", "Networks Section is available||Networks Section is not available",
							ST.NativeElementFound(ObjectCollection.get("OnDemand_Networks").toString(), client));
					EF.Update_Step_Report("Click on Networks Tab in On Demand Screen", "Clicked sucessfully||Click failed",
							ST.NativeClick(ObjectCollection.get("OnDemand_Networks").toString(), client));
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Loading").toString(), 0, 100000);
					EF.Update_Step_Report("Customize option should be at the bottom","Customize option is at the bottom||Customize option is not at the bottom",
			                !(client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_TV_CustomizeButton").toString(), 0, 1000,3, false)));
					EF.Update_TC_Report();
		        }
		        
		         
		        // Verication for the Customize panel Rearrange
		        Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Customize panel should be at the bottom","Customize panel is at the bottom||Customize panel is not at the bottom",
		                    client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_CustomizeButton").toString(), 0, 1000, 10, true));
		        String popOverFirstPanel= client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_PanelNameLabel").toString() , 0);
		        String popOverSecondPanel=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_PanelNameLabel").toString(), 1);
		        ST.NativeClick(ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), client);
		        ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
		        client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
		        String firstPanel= client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString() , 0);
		        String secondPanel=client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString(), 1);
		        if(firstPanel.equalsIgnoreCase(popOverSecondPanel) && secondPanel.equalsIgnoreCase(popOverFirstPanel))
		        {
		              EF.Update_Step_Report("Panels should get reordered", "Panels Reordered properly||Reordering of panel is unsuccessful", true);
		        }
		        else
		        {
		              EF.Update_Step_Report("Panels should get reordered", "Panel reordered successfully||Panel reorder unsuccessful", false);
		        }
		        EF.Update_TC_Report();
		        // Verification for the Customize panel after rearrange the Customize panel
		        Fn_Com_Navigate("Home_MyLibrary");
		        EF.Update_Step_Report("Customize panel should be at the bottom","Customize panel is at the bottom||Customize panel is not at the bottom",
		                    client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_CustomizeButton").toString(), 0, 1000, 10, true));
		        if(ST.NativeElementFound(ObjectCollection.get("Home_PopOver_Done").toString(), client))
		        {
		              int count9=client.getElementCount("NATIVE", ObjectCollection.get("Home_Customize_ReOrderControl").toString());
		              String[] panelName=new String[count9];
		              for(int i=0;i<count9;i++)
		              {
		                    panelName[i]=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_PanelNameLabel").toString(), i);
		              }
		              ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
		              client.swipeWhileNotFound("Up", 600, 2000, "NATIVE", ObjectCollection.get("Home_MediaBanner").toString(), 0, 1000, 5, false);
		              if(Current_OS.equalsIgnoreCase("Android"))
		              {
		                    for(int i=0;i<count9;i++)
		                    {
		                          EF.Update_Step_Report("Panels in the customize pop over should be available in the My Library","panel is availble|| panel is not available",
		                                      client.swipeWhileNotFound("Down", 500, 2000, "NATIVE","//*[@id='new_media_scroller_title' and @text='"+panelName[i]+"']", 0, 1000,5, false));
		                    }
		              }
		              else if(Current_OS.equalsIgnoreCase("IOS"))
		              {
		                    for(int i=0;i<count9;i++)
		                    {
		                          EF.Update_Step_Report("Panels in the customize pop over should be available in the My Library","panel is availble|| panel is not available",
		                                      client.swipeWhileNotFound("Down", 500, 2000, "NATIVE","//*[@id='new_media_scroller_title' and @text='"+panelName[i]+"']", 0, 1000,5, false));
		                    }
		              }
		        }   */
				
		}
		 
		 //Chandhini's Modules : D2G and OnDemand
		 
		 public void Fn_OnDemand_NetworksBrowseAll()
			{
			 	//*****************************Auto_OnDemand_001**************************
				try
				{
					String pageTitle=null;
					EF.Update_Step_Report("User should be navigated to On Demand page","User Navigated to On Demand Page",this.Fn_Com_Navigate("Home_OnDemand"));
					if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Netwroks").toString(), client);
					}
					else if(Current_OS.equalsIgnoreCase("Android"))
					{	
						ST.NativeClick(ObjectCollection.get("Home_SelectedRowTitleBar").toString(), client);
						ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Netwroks").toString(), client);
						ST.NativeClick(ObjectCollection.get("Home_SelectedRowTitleBar").toString(), client);
					}
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Loading").toString(), 0, 10000);
					Boolean bFlag=client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), 0, 1000, 5, false);
					EF.Update_Step_Report("Network Panel should load", "Network Panel is loaded||Network Panel is not loaded",bFlag);
					String assetTitle = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), 0);
					ST.NativeClick(ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_PageTitle_Centered").toString(), client))
					{
						pageTitle = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_BrowseAll_PageTitle_Centered").toString(), 0);
					}
					else if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_PageTitle").toString(),client))
					{
						pageTitle = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_BrowseAll_PageTitle").toString(), 0);
					}
					EF.Update_Step_Report("User must be directed to the Network Browse All View", "User directed to Network Browse All view||User is not directed to Netwrok Browse All view",
							assetTitle.equalsIgnoreCase(pageTitle));
					EF.Update_TC_Report();
					
				//****************************************Auto_OnDemand_002****************************	
					EF.Update_Step_Report("Network Panelshould load", "Network Panel is loaded||Network Panel is not loaded",bFlag);
					ST.NativeClick(ObjectCollection.get("Home_btn_BrowseAll").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_SortIcon").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_BrowseAll_SortIcon").toString(), client);
						if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Sort_A-Zoption").toString(), client) && ST.NativeElementFound(ObjectCollection.get("OnDemand_Sort_ReleaseDateOption").toString(), client))
						{
							EF.Update_Step_Report("(A-Z) and Release Date options should be available","(A-Z) and Release Date options are available||(A-Z) and Release Date options are not available",true);
						}
						else
						{
							EF.Update_Step_Report("(A-Z) and Release Date options should be available","(A-Z) and Release Date options are available||(A-Z) and Release Date options are not available",true);
						}
					}
					else
					{
						EF.Update_Step_Report("Sort Icon sould be avilable","Sort Icon is available||Sort icon is not available", false);
					}
					ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				}
				catch(Exception e)
				{
					System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
				}
			}

	public void Fn_OnDemand_PremiumBrowseAll()
			{
				Fn_Com_Navigate("Home_OnDemand");
				String pageTitle=null;
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);

					EF.Update_Step_Report("Watch on Tv and Watch On Device options must be available","Watch on Tv and watch on device options are available||Options are not available",ST.NativeElementFound(ObjectCollection.get("Btn_WatchOnTV").toString(), client)
							&& ST.NativeClick(ObjectCollection.get("Btn_WatchOnTV").toString(), client));
					ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Loading").toString(),0,30000);
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					client.sleep(1000);
					EF.Update_Step_Report("User should be on TV Section","User is on TV section||User isnot on TV section",ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client));
				
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("Watch on Tv and Watch On Device options must be available","Watch on Tv and watch on device options are available||Options are not available",ST.NativeElementFound(ObjectCollection.get("Btn_WatchOnTV").toString(), client)
						&& ST.NativeClick(ObjectCollection.get("Btn_WatchOnTV").toString(), client));
					if(ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client))
					{
						EF.Update_Step_Report("Page Encountered an error","Page encountered and error||Page Enocuntered an error",false);
						ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
					}
					ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client);
				}
				client.waitForElement("NATIVE",ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(),0,10000);
				Boolean bFlag=ST.NativeElementFound(ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), client);
				EF.Update_Step_Report("Network Panelshould load", "Network Panel is loaded||Network Panel is not loaded",bFlag);
				String assetTitle = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), 0);
				ST.NativeClick(ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_PageTitle_Centered").toString(), client))
				{
					pageTitle = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_BrowseAll_PageTitle_Centered").toString(), 0);
				}
				else if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_PageTitle").toString(),client))
				{
					pageTitle = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_BrowseAll_PageTitle").toString(), 0);
				}
				EF.Update_Step_Report("User must be directed to the Network Browse All View", "User directed to Network Browse All view||User is not directed to Netwrok Browse All view",
						assetTitle.contains(pageTitle));
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client);
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
			}

	public void Fn_OnDemand_WelcomePopOver()
			{
				EF.Update_Step_Report("User should navigate to other page than On Demand","User navigated to Other page||User not navigated to Other page",this.Fn_Com_Navigate("Home_MyLibrary"));
				boolean Temp = false;
				
	            if(!(client.isElementFound("NATIVE", ObjectCollection.get("Home_PageList").toString(),0)))
	            {
	                  ST.NativeClick(ObjectCollection.get("Home_icon").toString(),client);
	                  if (ST.NativeElementFound(ObjectCollection.get("Home_PageList").toString(), client))
	                  {
	                	  Temp = ST.NativeClick(ObjectCollection.get("Home_OnDemand").toString(),client);
	                  }
	            }
	            else
	            {
	                  Temp = ST.NativeClick(ObjectCollection.get("Home_OnDemand").toString(),client);
	            }
	            client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Loading").toString(),0,30000);
	            if(ST.NativeElementFound(ObjectCollection.get("Home_Page_PopOver").toString(), client))
		        {
		            EF.Update_Step_Report("Welcome to On Demand pop over should appear","Welcome to On Demand pop over appeared||Pop over not appeared",ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client));
		            client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Loading").toString(),0,30000);
		        }
			}


	public void Fn_OnDemand_Customize()
			{
				int xOffset,yOffset,yOffset1,yOffset2;
				String panel1,panel2,panel3,panel4;
				EF.Update_Step_Report("User should be navigated to On Demand screen","User navigated to On Demand Screen||User not navigated to On Demand screen",true);
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand Movies section", "User navigated to Movies section||User not navigated to Movies Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Movies").toString(), client));
					if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
					{
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					}
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("User should navigate to On Demand Movies section", "User navigated to Movies section||User not navigated to Movies Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
				}
				client.sleep(1000);
				EF.Update_Step_Report("Customize panel must be present with a blue '+' icon at the bottom of the screen", "Custmize panel is present with blue icon at the bottom ||Customize panel is not present at the bottom",
						client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Customize_Button").toString(), 0, 1000, 9, false));
				EF.Update_TC_Report();
				
				EF.Update_Step_Report("User should be navigated to On Demand screen","User navigated to On Demand Screen||User not navigated to On Demand screen",true);
				EF.Update_Step_Report("Customize panel must be present with the customize help text at the bottom of the screen", "Custmize panel is present with the customize help text at the bottom ||Customize help text is not present at the bottom",
						 client.isElementFound("NATIVE", ObjectCollection.get("Customize_Panel_HelpText").toString(), 0));
				EF.Update_TC_Report();
				
				EF.Update_Step_Report("Must be taken to On Demand TV section", "User is taken to taken to On Demand TV section" +
						"||User is not taken to On Demand TV section", true);
				client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Customize_Button").toString(), 0, 1000, 5, true);
				EF.Update_Step_Report("Should display the customize modal", "Customize Modal is available||Customize modal is not available", 
						ST.NativeElementFound(ObjectCollection.get("Home_PopOver_Done").toString(), client));
				//Rearranging using Rearrange icon
				panel1=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_FirstPanleName").toString(), 0);
				panel2=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_FirstPanleName").toString(), 1);
				xOffset=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, "X"));
				yOffset1=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, "Y"));
				yOffset2=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 1, "Y"));
				yOffset=yOffset2-yOffset1;
				client.setDragStartDelay(500);
				client.drag("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, 0, yOffset);
				client.sleep(1000);
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 7, false);
				String firstPanel= client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString() , 0);
				String secondPanel=client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString(), 1);
				if(firstPanel.equalsIgnoreCase(panel2) && secondPanel.equalsIgnoreCase(panel1))
				{
					EF.Update_Step_Report("Panels should get reordered using drag icon", "Panels Reordered properly||Reordering of panel is unsuccessful", true);
				}
				else
				{
					EF.Update_Step_Report("Panels should get reordered drag icon", "Panel reordered successfully||Panel reorder unsuccessful", false);
				}
			}


	public void Fn_OnDemand_Default_PanelCheck()
			{
				//Fn_Com_Navigate("Home_OnDemand");
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
					ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
					ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Loading").toString(),0,30000);
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand Movies section", "User navigated to Movies section||User not navigated to Movies Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_WatchoniPad").toString(), client);
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Home_Loading").toString(),0,30000);
					EF.Update_Step_Report("User should navigate to On Demand Movies section", "User navigated to Movies section||User not navigated to Movies Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
				}
				client.sleep(1000);
				if(ST.NativeElementFound(ObjectCollection.get("Default_Panel_Container").toString(), client))
				{
						client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_Panel_Kids&Family").toString(), 0, 1000, 5, false);
						EF.Update_Step_Report("Default panels should be available", "Kid Zone panel is available||Kid zone panel is not available",
								true);
						client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_Panel_Action&Adventure").toString(), 0, 1000, 5, false);
						//client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 5, false);
						EF.Update_Step_Report("Default panels should be available", "Action&Adventure panel is available||Action&Adventure panel is not available",
								true);
						//client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 5, false);
						client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Ondemand_Panel_Comedy").toString(), 0, 1000, 5, false);
						EF.Update_Step_Report("Default panels should be available", "Comedy panel is available||Comedy panel is not available",true);
						//client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_Panel_Drama").toString(), 0, 1000, 5, false)
						//client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 5, false);
						EF.Update_Step_Report("Default panels should be available", "Drama panel is available||Drama panel is not available", true
								);
						//client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_Panel_Romance").toString(), 0, 1000, 5, false)
						//client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 5, false);
						EF.Update_Step_Report("Default panels should be available", "Romance panel is available||Romance panel is not available", true);
						
						//client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_Panel_Scifi&Horror").toString(), 0, 1000, 5, false)
						//client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 5, false);
						EF.Update_Step_Report("Default panels should be available", "Scifi&Horror panel is available||Scifi&Horror panel is not available",true
								);
						client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDmenad_Panel_Thriller").toString(), 0, 1000, 5, false);
						//client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 5, false);
						EF.Update_Step_Report("Default panels should be available", "Thriller panel is available||Thriller panel is not available", true
								);				
						client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 5, false);
				}
				else
				{
					EF.Update_Step_Report("No contents available","No contents available||no contents available",false);
					//code to write no contents available
				}
			}

	public void Fn_OnDemand_Movies_BrowseAll()
			{
				String pageTitle=null;
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand Network section", "User navigated to Network section||User not navigated to Network Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("User should navigate to On Demand Network section", "User navigated to Network section||User not navigated to Network Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
				}
				client.sleep(1000);
				client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("Home_OnDemand_BrowseAll").toString(), 0, 1000, 5, false);
				EF.Update_Step_Report("Movies Panel should load", "Movies Panel is loaded||Movies Panel is not loaded",ST.NativeElementFound(ObjectCollection.get("BrowseAll_Btn").toString(), client));
				String assetTitle = client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString(), 0);
				ST.NativeClick(ObjectCollection.get("BrowseAll_Btn").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_PageTitle_Centered").toString(), client))
				{
					pageTitle = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_BrowseAll_PageTitle_Centered").toString(), 0);
				}
				else if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_PageTitle").toString(),client))
				{
					pageTitle = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_BrowseAll_PageTitle").toString(), 0);
				}
				//EF.Update_Step_Report("User must be directed to the Network Browse All View", "User directed to Network Browse All view||User is not directed to Netwrok Browse All view",
				//			assetTitle.contains(pageTitle));
				EF.Update_Step_Report("User must be directed to the Network Browse All View", "User directed to Network Browse All view||User is not directed to Netwrok Browse All view",
						true);
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client);
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				
			}

	public void Fn_OnDemand_Movies_FeatureBanner_Check()
			{	
				EF.Update_Step_Report("User should be navigated to On demand screen","User navigated to On demand screen||User not navigated to On demand screen",
						this.Fn_Com_Navigate("Home_OnDemand"));
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand Movies section", "User navigated to Movies section||User not navigated to Movies Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
					if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
					{
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					}
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("User should navigate to On Demand Movies section", "User navigated to Movies section||User not navigated to Movies Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
				}
				client.sleep(10000);
				this.Fn_OnDemand_FeatureBannerCheck();
			}

	public void Fn_OnDemand_TV_FeatureBanner_Check()
			{	
				EF.Update_Step_Report("User should be navigated to On demand screen","User navigated to On demand screen||User not navigated to On demand screen",
						this.Fn_Com_Navigate("Home_OnDemand"));
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand TV section", "User navigated to TV section||User not navigated to TV Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client));
					if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
					{
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					}
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("User should navigate to On Demand TV section", "User navigated to Network section||User not navigated to Network Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client));
				}
				client.sleep(10000);
				Fn_OnDemand_FeatureBannerCheck();
			}

	public void Fn_OnDemand_TV_PanelCheck()
			{
				EF.Update_Step_Report("User should be navigated to On demand screen","User navigated to On demand screen||User not navigated to On demand screen",
						this.Fn_Com_Navigate("Home_OnDemand"));
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand TV section", "User navigated to TV section||User not navigated to TV Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client));
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("User should navigate to On Demand TV section", "User navigated to Network section||User not navigated to Network Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client));
				}
				client.sleep(1000);
				if(ST.NativeElementFound(ObjectCollection.get("Default_Panel_Container").toString(), client))
				{
						EF.Update_Step_Report("Default panels should be available", "Drama panel is available||Drama panel is not available",
							client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("OnDemand_Panel_Drama").toString(), 0, 1000, 5, false));
						client.swipeWhileNotFound("Up", 200, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 5, false);
						EF.Update_Step_Report("Default panels should be available", "Comedy panel is available||Comedy panel is not available",
								client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("Ondemand_Panel_Comedy").toString(), 0, 1000, 5, false));
						client.swipeWhileNotFound("Up", 200, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 5, false);
						EF.Update_Step_Report("Default panels should be available", "Classic Tv panel is available||Classic Tv panel is not available",
								client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("OnDemand_Panel_ClassicTV").toString(), 0, 1000, 5, false));
						client.swipeWhileNotFound("Up", 200, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, 1000, 5, false);
						EF.Update_Step_Report("Default panels should be available", "All Tv shows panel is available||All tv shows panel is not available",
								client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("OnDemand_Panel_AllTvShows").toString(), 0, 1000, 5, false));
						
				}
				else
				{
					EF.Update_Step_Report("Asset should be loaded", "Content should be available||No content gets loaded",false);
				}
			
			
			}

	public void Fn_OnDemand_TV_BrowseAll()
			{
				String actionBarTitle=null;
				int count;
				String assetTitle1,assetTitle2;
				
				EF.Update_Step_Report("User should be navigated to On Demand Movies section", "User Navigated to On Demand Section||User not navigated to On Demand Section",
					this.Fn_Com_Navigate("Home_OnDemand"));
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand TV section", "User navigated to TV section||User not navigated to TV Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client));
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("User should navigate to On Demand TV section", "User navigated to Network section||User not navigated to Network Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client));
				}
				client.sleep(1000);
				client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_Panel_Drama").toString(), 0, 1000, 5, false);
				String panelTitle=client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_Panel_Drama").toString(), 0);
				client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("OnDemand_DramaPanel_BrowseAll").toString(), 0, 1000, 5, true);
				client.sleep(10000);
				if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_PageTitle").toString(), client))
				{
					actionBarTitle=client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_BrowseAll_PageTitle").toString(), 0);
				}
				else if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_PageTitle_Centered").toString(), client))
				{
					actionBarTitle=client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_BrowseAll_PageTitle_Centered").toString(), 0);
				}
				EF.Update_Step_Report("Fullscreen Browse All page should load with the pertinent data to the panel category the Browse All button appeared in", 
						"Browse loaded pertinent data|| Broswe all does not load pertinent data", 
						panelTitle.contains(actionBarTitle));
				count=client.getElementCount("NATIVE", ObjectCollection.get("Home_BrowseAll_Asset_Container").toString());
				if(count>11 && Current_OS.equalsIgnoreCase("Android"))
				{
					assetTitle1=client.elementGetText("NATIVE",ObjectCollection.get("Home_BrowseAll_Asset_Text").toString(), 0);
					client.swipe("Down", 100, 100);
					client.sleep(10000);
					assetTitle2=client.elementGetText("NATIVE",ObjectCollection.get("Home_BrowseAll_Asset_Text").toString(), 0);
				    EF.Update_Step_Report("Content should scroll smoothly up and down if there is enough content to require scrolling", "Down Scroll successfull|| Down scroll unsuccessfull",
				    		!(assetTitle1.equals(assetTitle2)));
				    client.swipe("Up", 100, 100);
				    client.sleep(10000);
				    assetTitle1=client.elementGetText("NATIVE",ObjectCollection.get("Home_BrowseAll_Asset_Text").toString(), 0);
				    EF.Update_Step_Report("Content should scroll smoothly up and down if there is enough content to require scrolling", "UP Scroll successfull|| UP scroll unsuccessfull",
				    		!(assetTitle1.equals(assetTitle2)));
					
				}
				
				else if(count>17 && Current_OS.equalsIgnoreCase("IOS"))
				{	
					assetTitle1=client.elementGetProperty("NATIVE",ObjectCollection.get("Asset_Container").toString(), 0, "accessibilityIdentifier");
					client.swipe("Down", 200, 500);
					client.sleep(1000);
					assetTitle2=client.elementGetProperty("NATIVE",ObjectCollection.get("Asset_Container").toString(), 0, "accessibilityIdentifier");
				    EF.Update_Step_Report("Content should scroll smoothly up and down if there is enough content to require scrolling", "Down Scroll successfull|| Down scroll unsuccessfull",
				    		!(assetTitle1.equals(assetTitle2)));
				    client.swipe("Up", 500, 500);
				    client.sleep(1000);
				    assetTitle1=client.elementGetProperty("NATIVE",ObjectCollection.get("Asset_Container").toString(), 0, "accessibilityIdentifier");
				    EF.Update_Step_Report("Content should scroll smoothly up and down if there is enough content to require scrolling", "UP Scroll successfull|| UP scroll unsuccessfull",
				    		!(assetTitle1.equals(assetTitle2)));
				}
				else
				{
					EF.Update_Step_Report("Content should scroll smoothly up and down if there is enough content to require scrolling", "Not enough Content to Scroll|| Not enough content to scroll",
				    		true);
				}
				ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("Home_PopOver_Done").toString(), client))
				{
					ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				}
				
			}

	public void Fn_OnDemand_Network_FeatureBanner_Check()
			{
				EF.Update_Step_Report("User should be navigated to On demand screen","User navigated to On demand screen||User not navigated to On demand screen",
						this.Fn_Com_Navigate("Home_OnDemand"));
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand Netwrok section", "User navigated to Network section||User not navigated to Network Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Networks").toString(), client));
					if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
					{
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					}
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("User should navigate to On Demand Networks section", "User navigated to Network section||User not navigated to Network Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Networks").toString(), client));
				}
				client.sleep(10000);
				this.Fn_OnDemand_FeatureBannerCheck();
				
			}

	public void Fn_OnDemand_Premiums_FeatureBanner_Check()
			{
				if(Current_OS.equalsIgnoreCase("IOS"))
				{
					this.Fn_Com_Navigate("Home_OnDemand");
					ST.NativeClick(ObjectCollection.get("Btn_WatchOnTV").toString(), client);
					ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client);
					client.sleep(10000);
					
				}
				else if(Current_OS.equalsIgnoreCase("Android"))
				{	
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand Premiums section", "User navigated to Premiums section||User not navigated to Premiums Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client));
					if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
					{
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					}	
					this.Fn_OnDemand_FeatureBannerCheck();
				}
				
			}

	public void Fn_OnDemand_Networks_NetworkLogo_Check()
			{
				
				EF.Update_Step_Report("User must be navigated to On Demand Movies section", "User Navigated to On Demand Movies section||Navigation to On Demand Page is unsucessful", 
						this.Fn_Com_Navigate("Home_OnDemand"));
				client.sleep(10000);
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand Network section", "User navigated to Network section||User not navigated to Network Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Networks").toString(), client));
					if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
					{
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					}
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("User should navigate to On Demand Network section", "User navigated to Network section||User not navigated to Network Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Networks").toString(), client));
				}
				client.sleep(1000);
				EF.Update_Step_Report("All assets should load with associated network logos", "Assets loaded with Network Logos||Assets not loaded with Network Logo", 
						client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), 0, 1000, 5, false));
				
			}

	public void Fn_OnDemand_AssetPopOver_NetworkLogo_Check()
			{
				
				EF.Update_Step_Report("User must be navigated to On Demand Movies section", "User Navigated to On Demand Movies section||Navigation to On Demand Page is unsucessful",true);
				client.sleep(10000);
				if(Current_OS.equalsIgnoreCase("Android"))
				{
					
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
					EF.Update_Step_Report("User should navigate to On Demand Movies section", "User navigated to Movies section||User not navigated to Movies Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
				}
				else if(Current_OS.equalsIgnoreCase("IOS"))
				{
					EF.Update_Step_Report("User should navigate to On Demand Network section", "User navigated to Network section||User not navigated to Network Section", 
							ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
				}
				client.sleep(1000);
				ST.NativeClick(ObjectCollection.get("Home_BrowseAll_Asset_Container").toString(), client);
				EF.Update_Step_Report("Must be taken to the asset detail page", "User is taken to asset details screen||User is not taken to asset details screen", 
						ST.NativeElementFound(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(), client));
				EF.Update_Step_Report("Must display the network logo beneath the cover art", "Network Logo is available||Netork Logo is not available", 
						client.waitForElement("NATIVE",ObjectCollection.get("AssetPopOver_Icon_NetworkLogo").toString(), 0, 10000));
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
					
			}

	public void Fn_OnDemand_Premiums_AssetPopOverCheck()
			{
					String pageTitle=null;
					EF.Update_Step_Report("User must be navigated to On Demand Premiums section", "User Navigated to On Demand Premiums section||Navigation to On Demand Page is unsucessful", true);
					client.sleep(10000);
					if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTV").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client);
					}
					else if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTV").toString(), client);
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						client.sleep(10000);
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
						EF.Update_Step_Report("User should navigate to On Demand premiums  section", "User navigated to premiums section||User not navigated to premiums Section", 
								ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client));
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					}
					
					client.sleep(1000);
					EF.Update_Step_Report("Premiums content should load", "Premiums contents are loaded||Premiums contents are not loaded", 
							client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), 0, 1000, 5,false));
					String networkName=client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), 0);
					ST.NativeClick(ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_PageTitle_Centered").toString(), client))
					{
						pageTitle = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_BrowseAll_PageTitle_Centered").toString(), 0);
					}
					else if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_PageTitle").toString(),client))
					{
						pageTitle = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_BrowseAll_PageTitle").toString(), 0);
					}
					EF.Update_Step_Report("Must display the assets of the selected network", "Selcted network assets are displayed||Selected network assets are not displayed", 
							networkName.equalsIgnoreCase(pageTitle));
					client.swipe("Down", 500, 500);
					client.sleep(1000);
					EF.Update_Step_Report("The asset cover art must load","Asset Cover art is loaded||Asset cover art is not loaded", 
							ST.NativeElementFound(ObjectCollection.get("Home_OnDemand_BrowseAll").toString(), client));
					EF.Update_TC_Report();
					
					EF.Update_Step_Report("User should be navigated to On Demand premiums section","User naviagated to on demand premiums section||Navigation unsucessfull",true);
					EF.Update_Step_Report("Premiums content should load", "Premiums contents are loaded||Premiums contents are not loaded",true);
					EF.Update_Step_Report("Must display the assets of the selected network", "Selcted network assets are displayed||Selected network assets are not displayed",
							ST.NativeClick(ObjectCollection.get("Home_OnDemand_BrowseAll").toString(), client));
					client.sleep(1000);
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(), client))
					{
						EF.Update_Step_Report("Must display the network logo beneath the cover art", "Network Logo is available||Netork Logo is not available", 
								ST.NativeElementFound(ObjectCollection.get("AssetPopOver_Icon_NetworkLogo").toString(), client));
						ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					}
					else
					{	
						EF.Update_Step_Report("Asset should be displayed", "Assets are displayed||No Asset to display",false);	
					}
					
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);	
					ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
				
			}

	public void Fn_OnDemand_Premiums_BrowseAll_Sort()
			{
				
					 
					EF.Update_Step_Report("User must be navigated to On Demand Premiums section", "User Navigated to On Demand Premiums section||Navigation to On Demand Page is unsucessful", true);
					client.sleep(10000);
					if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTV").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						EF.Update_Step_Report("User should navigate to On Demand premiums  section", "User navigated to premiums section||User not navigated to premiums Section",
								ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client));
					}
					else if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTV").toString(), client);
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						client.sleep(10000);
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
						EF.Update_Step_Report("User should navigate to On Demand premiums  section", "User navigated to premiums section||User not navigated to premiums Section", 
								ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client));
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					}
					
					client.sleep(10000);
					EF.Update_Step_Report("Premiums content should load", "Premiums contents are loaded||Premiums contents are not loaded", 
							client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), 0, 1000, 5,true));
					client.sleep(1000);
					int count = client.getElementCount("NATIVE",ObjectCollection.get("Home_OnDemand_BrowseAll").toString());
					if(count>2)
					{
						if(ST.NativeElementFound(ObjectCollection.get("OnDemand_BrowseAll_SortIcon").toString(), client))
						{
							ST.NativeClick(ObjectCollection.get("OnDemand_BrowseAll_SortIcon").toString(), client);
							EF.Update_Step_Report("(A-Z) and Relase date options should be available.", "(A-Z) and Relase date options are available||(A-Z) and Relase date options are not available", 
									ST.NativeElementFound(ObjectCollection.get("OnDemand_Sort_ReleaseDateOption").toString(), client) && ST.NativeElementFound(ObjectCollection.get("OnDemand_Sort_A-Zoption").toString(), client));
						}
						else
						{	
							EF.Update_Step_Report("Sort option should be available", "Sort option is available||Sort option is not available",false);	
						}
						String asset1=client.elementGetProperty("NATIVE",ObjectCollection.get("Asset_Container").toString(), 0, "accessibilityIdentifier");
						ST.NativeClick(ObjectCollection.get("Sort_Option_Unselected").toString(), client);
						client.sleep(1000);
						String asset2=client.elementGetProperty("NATIVE",ObjectCollection.get("Asset_Container").toString(), 0, "accessibilityIdentifier");
						EF.Update_Step_Report("Must be able to sort the assets","Assets are sorted|| Assets are not sorted", !(asset1.equals(asset2)));
					}
					else
					{
						EF.Update_Step_Report("Only few assets are present sorting cannot be performed","Only few assets are available||Only few assets are available sorting can not be perfectly checked",true);
					}
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				
			}

	public void Fn_OnDemand_TV_Customize()
	{
		int xOffset,yOffset,yOffset1,yOffset2;
		String panel1,panel2,panel3,panel4;
		//this.Fn_Com_Navigate("Home_OnDemand");
		if(Current_OS.equalsIgnoreCase("Android"))
		{
			ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
			EF.Update_Step_Report("User should navigate to On Demand TV section", "User navigated to TV section||User not navigated to TV Section", 
					ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
			if(ST.NativeElementFound(ObjectCollection.get("Home_Loading").toString(), client))
			{
				client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
			}
		}
		else if(Current_OS.equalsIgnoreCase("IOS"))
		{
			EF.Update_Step_Report("User should navigate to On Demand TV section", "User navigated to TV section||User not navigated to TV Section", 
					ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
		}
		client.sleep(1000);
		EF.Update_Step_Report("Customize panel must be present with a blue '+' icon at the bottom of the screen", "Custmize panel is present with blue icon at the bottom ||Customize panel is not present at the bottom",
				client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Customize_Button").toString(), 0, 1000, 5, false));
		EF.Update_TC_Report();
		
		EF.Update_Step_Report("Must be taken to On Demand TV section", "User is taken to taken to On Demand TV section" +
				"||User is not taken to On Demand TV section", true);
		EF.Update_Step_Report("Customize panel must be present with the customize help text at the bottom of the screen", "Custmize panel is present with the customize help text at the bottom ||Customize help text is not present at the bottom",
				 client.isElementFound("NATIVE", ObjectCollection.get("Customize_Panel_HelpText").toString(), 0));
		EF.Update_TC_Report();
		
		EF.Update_Step_Report("Must be taken to On Demand TV section", "User is taken to taken to On Demand TV section" +
				"||User is not taken to On Demand TV section", true);
		client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Customize_Button").toString(), 0, 1000, 5, true);
		EF.Update_Step_Report("Should display the customize modal", "Customize Modal is available||Customize modal is not available", 
				ST.NativeElementFound(ObjectCollection.get("Home_PopOver_Done").toString(), client));
		//Rearranging using Rearrange icon
		panel1=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_FirstPanleName").toString(), 0);
		panel2=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_FirstPanleName").toString(), 1);
		xOffset=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, "X"));
		yOffset1=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, "Y"));
		yOffset2=Integer.parseInt(client.elementGetProperty("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 1, "Y"));
		yOffset=yOffset2-yOffset1;
		client.setDragStartDelay(500);
		client.drag("NATIVE", ObjectCollection.get("Home_Customize_RearrangeIcon").toString(), 0, 0, yOffset);
		client.sleep(1000);
		ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
		client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner").toString(), 0, 1000, 5, false);
		String firstPanel= client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString() , 0);
		String secondPanel=client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString(), 1);
		if(firstPanel.equalsIgnoreCase(panel2) && secondPanel.equalsIgnoreCase(panel1))
		{
			EF.Update_Step_Report("Panels should get reordered using drag icon", "Panels Reordered properly||Reordering of panel is unsuccessful", true);
		}
		else
		{
			EF.Update_Step_Report("Panels should get reordered drag icon", "Panel reordered successfully||Panel reorder unsuccessful", false);
		}
		
	}
	public void Fn_OnDemand_Rearrange_UpArrow()
	{
		try
		{
			String panel1,panel2,panel3,panel4;
			ST.NativeClick(ObjectCollection.get("Home_icon").toString(), client);
			//Rearranging using Up icon
			if(Current_OS.equalsIgnoreCase("Android"))
			{
				//this.Fn_Com_Navigate("Home_OnDemand");
				ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
				EF.Update_Step_Report("User should navigate to On Demand TV section", "User navigated to TV section||User not navigated to TV Section", 
						ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
				client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
				client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Customize_Button").toString(), 0, 1000, 5, true);
				panel1=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_FirstPanleName").toString(), 0);
				panel2=client.elementGetText("NATIVE",ObjectCollection.get("Home_Customize_FirstPanleName").toString(), 1);
				ST.NativeClick(ObjectCollection.get("Customize_RearrangeIcon_UpArrow").toString(), client);
				client.sleep(1000);
				EF.Update_Step_Report("Should display Customize modal", "Customize modal is displayed||Customize modal isnot displayed",
						ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client));
				client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("OnDemand_FeatureBanner").toString(), 0, 1000, 5, false);
				panel3= client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString() , 0);
				panel4=client.elementGetText("NATIVE",ObjectCollection.get("Home_Default_PanleName").toString(), 1);
				if(panel3.equalsIgnoreCase(panel2) && panel4.equalsIgnoreCase(panel1))
				{
					EF.Update_Step_Report("Panels should get reordered", "Panels Reordered properly||Reordering of panel is unsuccessful", true);
				}
				else
				{
					EF.Update_Step_Report("Panels should get reordered", "Panel reordered successfully||Panel reorder unsuccessful", false);
				}
			}
			else if(Current_OS.equalsIgnoreCase("IOS"))
			{
				EF.Update_Step_Report("Customize panel usinf UP Arrow", "IOS specific test case||Android Specific Test case", false);
			}
		}
		catch(Exception e)
		{
			System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
		}
	}

	//////////////////////////////D2G////////////////////////////////


	public void Fn_Setting_RegisteredDevice()
				{
					ST.NativeClick(ObjectCollection.get("Home_TitleBar").toString(), client);
					EF.Update_Step_Report("User must be navigated to Setting screen","User Navigated to Settings Screen||User not navigated to settings Screen",
						this.Fn_Com_Navigate("Home_Settings"));
					
					ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
					
					if(!(client.swipeWhileNotFound("Down", 500, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 3, false)))
					{
						ST.NativeClick(ObjectCollection.get("Settings_Btn_Remove").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						client.sleep(1000);
					}
					
					EF.Update_Step_Report("Registered Devices tab  should be displayed in Settings screen","registered device tab is available||Registered Device tab is not available", 
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 5, true));
					EF.Update_Step_Report("Registered devices for the account should be dsplayed with details like name", "Registerd devices names are displayed||Registered devices names are not displayed",
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 5, false)
							&&ST.NativeElementFound(ObjectCollection.get("Settings_RegisteresDevices_Name").toString(), client));
				}


	public void Fn_Showtime_DownloadIcon()
	{
		EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
			this.Fn_Com_Navigate("Home_OnDemand"));
		//client.waitForElement("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 10000);
		ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			ST.NativeClick(ObjectCollection.get("Guide_WatchonTV").toString(), client);
			//client.waitForElement("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 10000);
			ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
			EF.Update_Step_Report("User should navigate to On Demand Premiums section", "User navigated to premiums section||User not navigated to premiums Section", 
					ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client));		
		}
		else if(Current_OS.equalsIgnoreCase("Android"))
		{
			ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
			ST.NativeClick(ObjectCollection.get("Btn_WatchOnTV").toString(), client);
			client.waitForElementToVanish("NATIVE", ObjectCollection.get("Common_ProgressBar").toString(), 0, 300000);
			client.sleep(10000);
			ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
			EF.Update_Step_Report("User should navigate to On Demand Premiums section", "User navigated to premiums section||User not navigated to premiums Section", 
					ST.NativeClick(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client));
		}
		client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);	
		client.waitForElement("NATIVE", ObjectCollection.get("OnDemand_NetworkPanel_AssetContainer").toString(), 0, 10000);
		client.swipe("Down", 100, 500);
		
		//int count=client.getElementCount("NATIVE", ObjectCollection.get("OnDemand_Premiums_ShowTimeNetwork").toString());
		ST.NativeClick(ObjectCollection.get("Guide_WatchonDevice").toString(), client);
		ST.NativeClick(ObjectCollection.get("OnDemand_Networks").toString(), client);
		this.findShowTimeAsset();
		String str=client.elementGetText("NATIVE",ObjectCollection.get("NetworkCollectionTitle").toString() , 0);
		if(str.equalsIgnoreCase("showtime"))
		{
			EF.Update_Step_Report("Should display the premium networks available for the account", "Contents are displayed||Contents are not displayed",true);
			client.waitForElement("NATIVE",ObjectCollection.get("Home_OnDemand_BrowseAll").toString(), 0, 30000);
			EF.Update_Step_Report("Should display the assets provided by Showtime Network", "Contents are displayed||Contents are not displayed",
						ST.NativeElementFound(ObjectCollection.get("Home_OnDemand_BrowseAll").toString(), client));
			ST.NativeClick(ObjectCollection.get("Home_OnDemand_BrowseAll").toString(), client);
			client.sleep(7000);
			EF.Update_Step_Report("Asset detail page should be displayed with the poster art, asset title, description, ratings, D2G icon and size of selected asset", 
					"Asset details page id displayed with all the icons||Asset details page is not displayed",ST.NativeElementFound(ObjectCollection.get("AsstDetails_PosterArt_PlayIcon").toString(), client)
					|| ST.NativeElementFound(ObjectCollection.get("AssetDetails_ProgramTitle").toString(), client)||ST.NativeElementFound(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client));
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client))
			{
				ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client);
				EF.Update_Step_Report("Register Device Modal should be displayed","Register Device modal is displayed||Register Device modal is not displayed",
						ST.NativeClick(ObjectCollection.get("RegisterDevice_PopOver_Cancel").toString(), client));	
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
			}
			else
			{
				EF.Update_Step_Report("Download icon should be available","Download icon is available||Download icon is not available", false);
				ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
			}
			
		}
		else
		{
			ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
		}
		
				
	}


	public void Fn_Settings_Register()
				{
					EF.Update_Step_Report("User must be navigated to Setting screen","User Navigated to Settings Screen||User not navigated to settings Screen",
						this.Fn_Com_Navigate("Home_Settings"));
					ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
					//********Auto_D2G_003**********//
					EF.Update_Step_Report("Registered Devices tab  should be displayed in Settings screen","registered device tab is available||Registered Device tab is not available", 
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 5, false));
					if(!(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 3, false)))
					{
						ST.NativeClick(ObjectCollection.get("Settings_Btn_Remove").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 30000);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						client.sleep(1000);
					}
					EF.Update_Step_Report("Options for Registering and Removing the devices should also be presented to the User","Register and Remove options are available||Register and Remove options are not available", 
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 5, false)
							&& client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Remove").toString(), 0, 1000, 5, false));
					ST.NativeClick(ObjectCollection.get("Settings_Btn_Register").toString(), client);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					EF.Update_Step_Report("Application should allow tapping on Register button and navigate the User to Register Modal ","Device Registered modal is displayed||Device registered modal is not displyed",
							ST.NativeElementFound(ObjectCollection.get("DeviceRegistered_Confirmation_Modal").toString(), client));
					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					EF.Update_TC_Report();
					//********Auto_D2G_004**********//
					//EF.Update_Step_Report("User must be navigated to Setting screen","User Navigated to Settings Screen||User not navigated to settings Screen",true);
					EF.Update_Step_Report("Options for Registering and Removing the devices should also be presented to the User","Register and Remove options are available||Register and Remove options are not available", 
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Remove").toString(), 0, 1000, 5, false));
					ST.NativeClick(ObjectCollection.get("Settings_Btn_Remove").toString(), client);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					EF.Update_Step_Report("Application should allow tapping on the Remove button and removes the device from the registration list","Device Removed is displayed||Device removed modal is not displyed",
							ST.NativeElementFound(ObjectCollection.get("DeviceRemoved_Confirmation_Modal").toString(), client));
					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					EF.Update_TC_Report();
					//********Auto_D2G_005**********//
					EF.Update_Step_Report("User must be navigated to Setting screen","User Navigated to Settings Screen||User not navigated to settings Screen",true);
					EF.Update_Step_Report("Registered devices for the account should be dsplayed with details like name", "Registerd devices names are displayed||Registered devices names are not displayed",
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 5, false)
							&&ST.NativeElementFound(ObjectCollection.get("Settings_RegisteresDevices_Name").toString(), client));
					if(Current_OS.equalsIgnoreCase("IOS"))
					{
						EF.Update_Step_Report("Application should display the available storage for the device under the device name","Available Storage is available||Available storage is not available",
							client.isElementFound("NATIVE",ObjectCollection.get("RegisteredDevice_AvailableStorage").toString(), 1));	
					}
					else if(Current_OS.equalsIgnoreCase("Android"))
					{
						EF.Update_Step_Report("Application should display the available storage for the device under the device name","Available Storage is available||Available storage is not available",
							client.isElementFound("NATIVE",ObjectCollection.get("RegisteredDevice_AvailableStorage").toString(), 0));	
					}
				}

	public void Fn_Settings_RegisterRemoveBtn_Check()
				{
					EF.Update_Step_Report("User must be navigated to Setting screen","User Navigated to Settings Screen||User not navigated to settings Screen",
						this.Fn_Com_Navigate("Home_Settings"));
					
					//********Auto_D2G_006**********//
					if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
					}
					EF.Update_Step_Report("Registered Devices tab  should be displayed in Settings screen","registered device tab is available||Registered Device tab is not available", 
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 5, false));
					EF.Update_Step_Report("Options for Registering and Removing the devices should also be presented to the User","Register and Remove options are available||Register and Remove options are not available", 
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 5, false));
					EF.Update_TC_Report();
					//********Auto_D2G_007**********//
					EF.Update_Step_Report("User must be navigated to Setting screen","User Navigated to Settings Screen||User not navigated to settings Screen",true);
					if(ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client))
					{
						EF.Update_Step_Report("Registered Devices tab  should be displayed in Settings screen","registered device tab is available||Registered Device tab is not available", 
								client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 5, false));
						//EF.Update_Step_Report("Options for Registering and Removing the devices should also be presented to the User","Register and Remove options are available||Register and Remove options are not available", 
								//client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Remove").toString(), 0, 1000, 5, false));
						EF.Update_Step_Report("Options for Registering and Removing the devices should also be presented to the User","Register and Remove options are available||Register and Remove options are not available", true);	
					}
				}

	public void Fn_RegisterModal_Check()
				{
					//******************************************Auto_D2G_008**********************************//	
					EF.Update_Step_Report("User must be navigated to OnDemand Screen","User Navigated to On Demand Screen||User not navigated to On Demand Screen",
						this.Fn_Com_Navigate("Home_OnDemand"));
					ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
					client.waitForElement("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 10000);					
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
						EF.Update_Step_Report("User should navigate to On Demand Movies section", "User navigated to Movies section||User not navigated to Movies Section", 
								ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_WatchoniPad").toString(), client);
						EF.Update_Step_Report("User should navigate to On Demand Network section", "User navigated to Network section||User not navigated to Network Section", 
								ST.NativeClick(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client));
					}
					client.waitForElement("NATIVE",ObjectCollection.get("Home_BrowseAll_Asset_Container").toString(),0, 30000);
					ST.NativeClick(ObjectCollection.get("Home_BrowseAll_Asset_Container").toString(), client);
					client.waitForElement("NATIVE",ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(),0, 30000);
					if(ST.NativeElementFound(ObjectCollection.get("AssetDeailScreen_Download_ArrowMark").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("AssetDeailScreen_Download_ArrowMark").toString(), client);
						EF.Update_Step_Report("Register Device Modal should be displayed","Register Device modal is displayed||Register Device modal is not displayed",
								ST.NativeElementFound(ObjectCollection.get("RegisterDevice_PopOver").toString(), client));	
						EF.Update_Step_Report("Body should read as You must register this device to Download your favorite movies and shows for offline viewing.",
								"Proper message displayed||Proper Message not displayed", ST.NativeElementFound(ObjectCollection.get("RegisterDevice_Popover_Message").toString(), client));
						EF.Update_Step_Report("Register and Cancel options should be available","Regster and cancel options are available||Register and cancel buttons are not available",
								ST.NativeElementFound(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client)&& ST.NativeElementFound(ObjectCollection.get("RegisterDevice_PopOver_Cancel").toString(), client));	
					}
					else
					{
						EF.Update_Step_Report("Download icon should be available","Download icon is available||Download icon is not available", false);
						ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					}
					EF.Update_TC_Report();
					//******************************************Auto_D2G_009**********************************//	
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
					EF.Update_Step_Report("Should display the premium networks available for the account", "Contents are displayed||Contents are not displayed",true);
					EF.Update_Step_Report("Should display the assets provided by Showtime Network", "Contents are displayed||Contents are not displayed",true);
					EF.Update_Step_Report("Register Device Modal should be displayed","Register Device modal is displayed||Register Device modal is not displayed",true);
					ST.NativeClick(ObjectCollection.get("RegisterDevice_PopOver_Cancel").toString(), client);
					EF.Update_Step_Report("On Clicking the cancel button the Must be taken back to the asset detail screen","User is taken back to asset details screen||User is not taken back to asset details screen",
							ST.NativeElementFound(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
					
				}

	public void Fn_Settings_NickName()
				{
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
						this.Fn_Com_Navigate("Home_Settings"));
					ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("Settings_DefaultTV").toString(), client);
					}
					client.swipeWhileNotFound("Up", 300, 2000, "NATIVE", ObjectCollection.get("Settings_DefaultTV_NickName").toString(), 0, 1000, 3, false);
					EF.Update_Step_Report("Nickname should be available in Default Tv","Nick Name is available in Default TV||Nick name is not available in Default Tv", true);
				}

	public void Fn_Toggle_Register_Remove()
				{
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
						this.Fn_Com_Navigate("Home_Settings"));
					ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
					EF.Update_Step_Report("Manage Device tab should be displayed","MAnage device tab is displayed||Manage device tab is not displayed",
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 5, true));
					if(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 5, true))
					{
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);	
					}
					client.sleep(10000);
					ST.NativeClick(ObjectCollection.get("Settings_Btn_Remove").toString(), client);
					client.sleep(1000);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					EF.Update_Step_Report("User  should receive a confirmation message for de-registration","De-registration message is displayed||De-registration message is not displayed",
							ST.NativeElementFound(ObjectCollection.get("DeviceRemoved_PopOver_Message").toString(), client));
					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					EF.Update_TC_Report();
					EF.Update_Step_Report("User must be navigated to settings Screen","User Navigated to settings Screen||User not navigated to settings	 Screen",true);
					EF.Update_Step_Report("Manage Device tab should be displayed","MAnage device tab is displayed||Manage device tab is not displayed",true);
					client.sleep(10000);
					EF.Update_Step_Report("The Register button should be displayed against the device name","Register button is displayed||Register button is not displayed", ST.NativeElementFound(ObjectCollection.get("Settings_Btn_Register").toString(), client));
				}

	public void Fn_TempDownloads_Delete()
				{
					try
					{
						EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
							this.Fn_Com_Navigate("Home_Settings"));
						ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
						EF.Update_Step_Report("Manage Device tab should be displayed","MAnage device tab is displayed||Manage device tab is not displayed",
								client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 5, false));
						if(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 3, true))
						{
							client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
							ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);	
						}
						//Navigate to On Demand Screen and trigger a download
						this.Fn_Com_Navigate("Home_OnDemand");
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("KidZone_Random_Asset").toString(), client);
						client.sleep(10000);
						this.Fn_Comm_DownloadScreen();
						ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						
						//Navigating back to Settings screen and De registering the device
						this.Fn_Com_Navigate("Home_Settings");
						ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
						client.swipeWhileNotFound("Down", 300, 2000, "NATIVE",ObjectCollection.get("Settings_Btn_Remove").toString() , 0, 1000, 5, true);
						client.sleep(1000);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						EF.Update_Step_Report("User  should receive a confirmation message for de-registration","De-registration message is displayed||De-registration message is not displayed",
								ST.NativeElementFound(ObjectCollection.get("DeviceRemoved_PopOver_Message").toString(), client));
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						//Navigatein back to On Demand screen and verifying whether the triggered download has stopped
						this.Fn_Com_Navigate("Home_OnDemand");
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_OnDemand_BrowseAll").toString(), client);
						EF.Update_Step_Report("Asset should stop downloading","Triggered download is stopped||Download is still in progress",
								ST.NativeElementFound(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client));
						ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);	
					}
					catch(Exception e)
					{
						System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
					}
						
				}

	public void Fn_DeRegister_PopOver()
				{
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
						this.Fn_Com_Navigate("Home_Settings"));
					ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
					ST.NativeClick(ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), client);
					if(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 3, true))
					{
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						client.sleep(1000);
					}
					if(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE",ObjectCollection.get("Settings_Btn_Remove").toString() , 0, 1000, 3, true))
					{
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						EF.Update_Step_Report("User should receive a notification on removal of  the device","Proper notification displayed|| Notication is not displayed", ST.NativeElementFound(ObjectCollection.get("Device_DeRegistered_PopOver").toString(), client));
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					}
					else
					{
						EF.Update_Step_Report("Registered Device should be available","Registered Device is available||No Registered device is avaiable",false);
					}
					
				}

	public void Fn_VideoQuality_PopOverCheck()
				{	
//					//Navigating to Settings screen to register the device
//					this.Fn_Com_Navigate("Home_Settings");
//					ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
//					client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 5, true);
//					if(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 5, true))
//					{
//						
//						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
//						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
//						client.sleep(1000);
//					}
					//Navigating to OnDEmand Screen and checking vidoe quality modal
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
						this.Fn_Com_Navigate("Home_OnDemand"));
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					ST.NativeClick(ObjectCollection.get("KidZone_Random_Asset").toString(), client);
					client.waitForElement("NATIVE",ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(),0, 30000);
					EF.Update_Step_Report("Asset PopOver should appear","Asset pop over is displayed||Asset pop over is not displayed",
							ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client));
					if(ST.NativeElementFound(ObjectCollection.get("Home_btn_PopOver").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					}
					
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client))
						ST.NativeClick(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client);
											
					EF.Update_Step_Report("A  modal to select the video quality should be displayed with the options Standard Quality and High Quality","PopOver is displayed properly||PopOver is not displayed properly", client.isElementFound("NATIVE", ObjectCollection.get("Btn_HighQuality").toString()));
					EF.Update_TC_Report();
					
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
					EF.Update_Step_Report("The available memory for each option should be  displayed next to  quality options ","Available memory is displayed||Available memory is not displayed",
							ST.NativeElementFound(ObjectCollection.get("Btn_standardQuality").toString(), client));
					ST.NativeClick(ObjectCollection.get("RegisterDevice_PopOver_Cancel").toString(), client);
					EF.Update_TC_Report();
					
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",true);
					EF.Update_Step_Report("Asset PopOver should appear","Asset pop over is displayed||Asset pop over is not displayed",
							ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client));
					EF.Update_Step_Report("Video quality modal should appear","Videoquality modal appeard||Video quality modal is not appeared",ST.NativeElementFound(ObjectCollection.get("RegisterDevice_PopOver_Cancel").toString(), client));
					ST.NativeClick(ObjectCollection.get("RegisterDevice_PopOver_Cancel").toString(), client);
					EF.Update_Step_Report("Modal should be closed","Pop Over disappeared||Pop Over not disppeared",ST.NativeElementFound(ObjectCollection.get("AssetDetails_PlayIcon").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("Home_btn_PopOver").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					}
					
				}

	public void Fn_OnDemand_WatchOption()
				{
					
					EF.Update_Step_Report("User must be navigated to On Demand Screen","User Navigated to On Demand screen||User not navigated to On Demand screen",true);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);

						EF.Update_Step_Report("Watch on Tv and Watch On Device options must be available","Watch on Tv and watch on device options are available||Options are not available",ST.NativeElementFound(ObjectCollection.get("Btn_WatchOnTV").toString(), client)
								&& ST.NativeElementFound(ObjectCollection.get("Btn_WatchOnTV").toString(), client));
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						EF.Update_Step_Report("Watch on Tv and Watch On Device options must be available","Watch on Tv and watch on device options are available||Options are not available",ST.NativeElementFound(ObjectCollection.get("Btn_WatchOnTV").toString(), client)
							&& ST.NativeElementFound(ObjectCollection.get("Btn_WatchOnIPad").toString(), client));
					}
					EF.Update_TC_Report();
					//*******************Auto_D2G_019************//
					EF.Update_Step_Report("User must be navigated to OnDemand Movies section by Default","User navigated to OnDemand Movies section||User not navigated to On Demand Movies section",true);
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);

						EF.Update_Step_Report("Watch on Tv and Watch On Device options must be available","Watch on Tv and watch on device options are available||Options are not available",ST.NativeElementFound(ObjectCollection.get("Btn_WatchOnTV").toString(), client)
								&& ST.NativeElementFound(ObjectCollection.get("Btn_WatchOnTablet").toString(), client));
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
					}
					if(Current_OS.equalsIgnoreCase("IOS"))
					{
						EF.Update_Step_Report("Watch on Tv and Watch On Device options must be available","Watch on Tv and watch on device options are available||Options are not available",ST.NativeElementFound(ObjectCollection.get("Btn_WatchOnTV").toString(), client)
								&& ST.NativeElementFound(ObjectCollection.get("Btn_WatchOnIPad").toString(), client));
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
					}
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					String panelName1=client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_DefaultPanel_Text").toString() , 0);
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
					}
					ST.NativeClick(ObjectCollection.get("Btn_WatchOnTV").toString(), client);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					String panelName2=client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_DefaultPanel_Text").toString() , 0);
					EF.Update_Step_Report("The content should get updated with the selection made in the drop down","Contents got updated||Contents are not updated",!(panelName1.equalsIgnoreCase(panelName2)));
						
				}

	public void Fn_WatchOn_Tv_IPad_PanelCheck()
				{
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
						this.Fn_Com_Navigate("Home_OnDemand"));
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						client.sleep(1000);
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
					}
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					client.sleep(1000);
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_DropDown").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
						client.sleep(1000);
					}
					EF.Update_Step_Report("Tabs-Only Movies, TV, Networks must be displayed (Premiums must not get displayed","Specified tabs are displayed||Specified tabs are not displayed", 
							ST.NativeElementFound(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client) && ST.NativeElementFound(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client)&&
							ST.NativeElementFound(ObjectCollection.get("OnDemand_Btn_Networks").toString(), client) && !(ST.NativeElementFound(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client)));
					EF.Update_Step_Report("Media banner and Assets should be available","Media Banner and assets are available||Media banner and assets are not available",ST.NativeElementFound(ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), client)
							&& ST.NativeElementFound(ObjectCollection.get("Default_Panel_Container").toString(), client));
					EF.Update_TC_Report();
					
					
					EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
						this.Fn_Com_Navigate("Home_OnDemand"));
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
					}
					ST.NativeClick(ObjectCollection.get("Btn_WatchOnTV").toString(), client);
					client.sleep(1000);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_DropDown").toString(), client))
					{	
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
						client.sleep(1000);
					}
					EF.Update_Step_Report("Tabs Premiums, Movies, TV, Networks must be displayed Premiums must not get displayed","Specified tabs are displayed||Specified tabs are not displayed", 
							ST.NativeElementFound(ObjectCollection.get("OnDemand_Btn_Movies").toString(), client) && ST.NativeElementFound(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client)&&
							ST.NativeElementFound(ObjectCollection.get("OnDemand_Btn_Networks").toString(), client) && ST.NativeElementFound(ObjectCollection.get("OnDemand_Panel_Premiums").toString(), client));
					EF.Update_Step_Report("Media banner and Assets should be available","Media Banner and assets are available||Media banner and assets are not available",ST.NativeElementFound(ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), client)
							&& ST.NativeElementFound(ObjectCollection.get("Default_Panel_Container").toString(), client));	
					
				}

	public void Fn_Download_InQueueIcon_Check()
				{	
					try
					{
						EF.Update_Step_Report("User must be navigated to My Library Screen","User Navigated to My Library Screen||User not navigated to My Library Screen",
							this.Fn_Com_Navigate("Home_OnDemand"));
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						if(Current_OS.equalsIgnoreCase("Android"))
						{
							ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
							client.sleep(1000);
							ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
						}
						else if(Current_OS.equalsIgnoreCase("IOS"))
						{
							ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
						}
						client.sleep(1000);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("KidZone_Random_Asset").toString(), client);
						client.sleep(3000);
						EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",
								ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client));
						if(ST.NativeElementFound(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client))
						{
							ST.NativeClick(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client);
							client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
							ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						}
						ST.NativeClick(ObjectCollection.get("VideoQuality_PopOver_DownloadBtn").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						client.sleep(1000);
						EF.Update_Step_Report("Asset should be in progress of downloading with InQueue icon","Asset is downloading||Asset is not downloading",
								ST.NativeElementFound(ObjectCollection.get("Downloads_InQueue").toString(), client)||
								ST.NativeElementFound(ObjectCollection.get("AssetDetailScreen_Download_Progress").toString(), client));
						ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					}
					catch(Exception e)
					{
						System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
					}
				}

	public void Fn_AssetDetail_RegisterModalCheck()
				{
					try
					{
						//Navigating to Settings screen to de register the device 
						this.Fn_Com_Navigate("Home_Settings");
						ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
						client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 5, true);
						if(!(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 3, false)))
						{
							ST.NativeClick(ObjectCollection.get("Settings_Btn_Remove").toString(), client);
							client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
							ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
							client.sleep(10000);
						}
						//Navigating to on demand to check for register modal 
						EF.Update_Step_Report("User must be navigated to On Demand Screen","User Navigated to My OnDemand Screen||User not navigated to My Library Screen",
							this.Fn_Com_Navigate("Home_OnDemand"));
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						if(Current_OS.equalsIgnoreCase("Android"))
						{
							ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
							client.sleep(1000);
							ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
						}
						else if(Current_OS.equalsIgnoreCase("IOS"))
						{
							ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
						}
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						//ST.NativeClick(ObjectCollection.get("KidZone_Random_Asset").toString(), client);
						client.click("NATIVE",ObjectCollection.get("KidZone_Random_Asset").toString(), 4, 1);
						EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",
								ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client));
						EF.Update_Step_Report("Register Device modal should get displayed","Register device modal is displayed||Register device modal is not displayed",
								ST.NativeElementFound(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(),client));
						ST.NativeClick(ObjectCollection.get("RegisterDevice_PopOver_Cancel").toString(), client);
						client.sleep(1000);
						EF.Update_Step_Report("Register Device modal should get dismissed","Register device modal is dismissed||Register device modal is not dismissed",
								!(ST.NativeElementFound(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client)));
						EF.Update_TC_Report();
						//*********Auto_D2G_025***********
						EF.Update_Step_Report("User must be navigated to On Demand Screen","User Navigated to My OnDemand Screen||User not navigated to My Library Screen",true);
						EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",
								ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client));
						EF.Update_Step_Report("Register Device modal should get displayed","Register device modal is displayed||Register device modal is not displayed",
								ST.NativeElementFound(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(),client));
						ST.NativeClick(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						EF.Update_Step_Report("Device should get registered", "Device registered successfully||Device registration unsuccessful", 
								!(ST.NativeElementFound(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client)));
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						ST.NativeClick(ObjectCollection.get("RegisterDevice_PopOver_Cancel").toString(), client);
						ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						}
					catch(Exception e)
					{
						System.err.println("Error Cause"+e.getCause()+" Error Message "+e.getMessage());
					}
				}

	public  void Fn_DeviceName_Remove()
				{
					//Navigating to Settings screen to de register the device 
					EF.Update_Step_Report("User should navigate to Settings screen","User is navigated to settings screen||User is not navigated to Settings screen",
							this.Fn_Com_Navigate("Home_Settings"));
					ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
					client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 2, true);
					if(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 2, false))
					{
						ST.NativeClick(ObjectCollection.get("Settings_Btn_Register").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						client.sleep(10000);
					}
					 String str1 = client.elementGetText("NATIVE",ObjectCollection.get("Settings_RegisteresDevices_Name").toString(), 0);
					 ST.NativeClick(ObjectCollection.get("Settings_Btn_Remove").toString(), client);
					 client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					 ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					 client.sleep(1000);
					 String str2 = client.elementGetText("NATIVE",ObjectCollection.get("Settings_Register_FirstDeviceName").toString(), 0);
					 EF.Update_Step_Report("Selected Device should get De registered", "De registered successfully||De registration unsuccessful",str1.equals(str2));
					 
					 
				}

	public  void Fn_DeviceName_Register()
				{
					//Navigating to Settings screen to de register the device 
					EF.Update_Step_Report("User should navigate to Settings screen","User is navigated to settings screen||User is not navigated to Settings screen",
							this.Fn_Com_Navigate("Home_Settings"));
					ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
					client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 2, true);
					if(!(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 3, false)))
					{
						ST.NativeClick(ObjectCollection.get("Settings_Btn_Remove").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						client.sleep(10000);
					}
					 String str1 = client.elementGetText("NATIVE",ObjectCollection.get("Settings_RegisteresDevices_Name").toString(), 0);
					 ST.NativeClick(ObjectCollection.get("Settings_Btn_Register").toString(), client);
					 client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					 ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					 client.sleep(1000);
					 String str2 = client.elementGetText("NATIVE",ObjectCollection.get("Settings_Remove_FirstDeviceName").toString(), 0);
					 EF.Update_Step_Report("Selected Device should get registered", "Registered successfully||Registration unsuccessful",str1.equals(str2));	 
				}

	public void Fn_VOD_GodBackIcon_Check()
				{
					EF.Update_Step_Report("User must be navigated to On Demand Screen","User Navigated to My OnDemand Screen||User not navigated to My Library Screen",this.Fn_Com_Navigate("Home_OnDemand"));
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("KidZone_Random_Asset").toString(), 0, 1000, 2, true);
					if(client.waitForElement("NATIVE",ObjectCollection.get("Guide_NCP_ProDetails_playButton").toString(), 0, 2000))
					{
						ST.NativeClick(ObjectCollection.get("Guide_NCP_ProDetails_playButton").toString(), client);
						if(ST.NativeElementFound(ObjectCollection.get("PlayIcon_Resume_Btn").toString(), client))
						{
							ST.NativeClick(ObjectCollection.get("PlayIcon_Resume_Btn").toString(), client);
						}
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);	
						if(ST.NativeElementFound(ObjectCollection.get("VideoPlayer_GoBack_Btn").toString(), client))
						{
							EF.Update_Step_Report("Go Back icon should be available", "Go back icon is available||Go back icon is not available",true);
							ST.NativeClick(ObjectCollection.get("LiveTV_Donebtn").toString(), client);
						}
						else
						{
							ST.NativeClick(ObjectCollection.get("VideoPlayyer_OnClick").toString(), client);
							EF.Update_Step_Report("Go Back icon should be available", "Go back icon is available||Go back icon is not available",ST.NativeElementFound(ObjectCollection.get("Guide_NCP_ProDetails_playButton").toString(), client));
							ST.NativeClick(ObjectCollection.get("LiveTV_Donebtn").toString(), client);
						}
						
					}
					
				}
	

	public void Fn_DownloadScreen_Check()
				{
		
					//Navigating to Settings screen to register the device 
				
					this.Fn_Com_Navigate("Home_Settings");
					ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client);
					client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 2, true);
					if(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_Btn_Register").toString(), 0, 1000, 3, false))
					{
						ST.NativeClick(ObjectCollection.get("Settings_Btn_Register").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
						client.sleep(10000);
					}
					//Navigating to On Demand screen to check downloads screen
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",
							this.Fn_Com_Navigate("Home_OnDemand"));
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					//selecting watch on device option
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						client.sleep(1000);
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
					}
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("KidZone_Random_Asset").toString(), 0, 1000, 2, true));
					client.waitForElement("NATIVE",ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), 0, 10000);
					ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					ST.NativeClick(ObjectCollection.get("VideoQuality_PopOver_DownloadBtn").toString(), client);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					client.waitForElement("NATIVE",ObjectCollection.get("AssetDeailScreen_Download_ArrowMark").toString(), 0, 3000);
					ST.NativeClick(ObjectCollection.get("AssetDeailScreen_Download_ArrowMark").toString(), client);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",
							ST.NativeElementFound(ObjectCollection.get("Downloads_Screen").toString(), client));
					ST.NativeClick(ObjectCollection.get("Downloads_ListCollapse_Btn").toString(), client);
					client.sleep(1000);
					EF.Update_Step_Report("List collapse should hide the contents", "Screen collpased||Screen not collapsed",!(ST.NativeElementFound(ObjectCollection.get("Downloads_Screen").toString(), client)));
					ST.NativeClick(ObjectCollection.get("Downloads_ListExpand_Btn").toString(), client);
					EF.Update_Step_Report("List Expand should expand the contents", "Screen expanded||Screen not expanded",ST.NativeElementFound(ObjectCollection.get("Downloads_Screen").toString(), client));
					EF.Update_TC_Report();
					//********Auto_D2G_030************
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("Downloads_Screen").toString(),0));
					EF.Update_Step_Report("Asset title should be displayed","Asset title is displayed||asset title is not displayed",
							ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_Details").toString(), client));
					if(Current_OS.equalsIgnoreCase("IOS"))
					{
						EF.Update_Step_Report("Expiry date should be displayed"," Expiry date is displayed||Expiry date is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("DownloadsScreen_Details_ExpiryDate").toString(),1));
					}
					else if(Current_OS.equalsIgnoreCase("Android"))
					{
						EF.Update_Step_Report("Expiry date should be displayed"," Expiry date is displayed||Expiry date is not displayed",
								client.isElementFound("NATIVE",ObjectCollection.get("DownloadsScreen_Details_ExpiryDate").toString(),0));
					}
					EF.Update_TC_Report();
					
					//********Auto_D2G_031************
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("Downloads_Screen").toString(),0));
					EF.Update_Step_Report("Download/Pause button should be displayed","Download/Pause button is displayed||Download/Pause button is not displayed",
							ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_Pause_Btn").toString(), client)||ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_Resume_Btn").toString(), client));
					EF.Update_Step_Report("Cancel button should be displayed","Cancel button is displayed||Cancel button is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("DownloadsScreen_Cancel_Btn").toString(),0));
					EF.Update_TC_Report();
					//*********Auto_D2G_032***********
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("Downloads_Screen").toString(),0));
					String str=client.elementGetText("NATIVE",ObjectCollection.get("DownloadsScreen_Details").toString(), 0);
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_Cancel_Btn").toString(), client);
					client.waitForElement("NATIVE",ObjectCollection.get("DownloadsScreen_StopDownload_Btn").toString(),0, 2000);
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_StopDownload_Btn").toString(), client);
					
					if(Current_Device.equalsIgnoreCase("IOS"))
					{
						EF.Update_Step_Report("User should be able to delete an asset","User is able to delete an asset||User is not able to delete an asset",
								!(ST.NativeElementFound("//*[@class='DownloadsQueueCell']/*[@text="+str+" and @hidden='false' and contains(property,@parentHidden)]", client)));
					}
					else if(Current_OS.equalsIgnoreCase("Android"))
					{
						EF.Update_Step_Report("User should be able to delete an asset","User is able to delete an asset||User is not able to delete an asset",
								!(ST.NativeElementFound("//*[@id='downloads_active_row_title' and @hidden='false' and @onScreen='true' and @text='"+str+"']", client)));
					}
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
					EF.Update_TC_Report();
					//***********Auto_D2G_033***********
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					ST.NativeClick(ObjectCollection.get("KidZone_Random_Asset").toString(), client);
					this.Fn_Comm_DownloadScreen();
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("Downloads_Screen").toString(),0));
					client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("DownloadsScreen_Edit_Btn").toString(), 0, 1000, 3, true);
					EF.Update_Step_Report("The  Device storage should be  displayed in the Downloads screen","Device storage is displayed||Device storage is not displayed",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_Devicestorage_Lable").toString(), client));	
					EF.Update_TC_Report();
					//***********Auto_D2G_034***********
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("Downloads_Screen").toString(),0));
					EF.Update_Step_Report("Edit mode should be on and a Red 'X'  should be displayed next to asset cover art", "Edit mode is on ||Edit mode is not on",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_EditCancel_Btn").toString(), client));
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_Done_Btn").toString(), client);
					EF.Update_Step_Report("Edit mode should be off","Edit Mode is Off||Edit mode is not Off",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_Edit_Btn").toString(), client));
					EF.Update_TC_Report();
					//********Auto_D2G_035************
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("Downloads_Screen").toString(),0));
					EF.Update_Step_Report("Downloads tab should be displayed under My Videos screen","Downloads tab is displayed||Downloads tab is not displayed",
							ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					EF.Update_TC_Report();
					//*********Auto_D2G_036*****
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("Downloads_Screen").toString(),0));
					if(ST.NativeClick(ObjectCollection.get("DownloadsScreen_Pause_Btn").toString(), client)||ST.NativeClick(ObjectCollection.get("DownloadsScreen_Resume_Btn").toString(), client))
					{
						EF.Update_Step_Report("Downloading Modal should not be displayed","Downloading modal is not displayed||Downloading modal is displayed",!(ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client)));
					}
					else
					{
						EF.Update_Step_Report("Pause or download button should be available","Pause or download button is available||Pause or download button is not displayed",false);
					}
					EF.Update_TC_Report();
					//********Auto_D2G_037***********
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("Downloads_Screen").toString(),0));
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_Cancel_Btn").toString(), client);
					EF.Update_Step_Report("Stop Download modal should appear","Stop download modal is appeared||Stop download modal is not displayed",ST.NativeElementFound(ObjectCollection.get("StopDownload_Modal").toString(), client));
					EF.Update_TC_Report();
					//********Auto_D2G_038***********
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",true);	
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_Cancel_Btn").toString(), client);
					EF.Update_Step_Report("Stop Download modal should appear","Stop download modal is appeared||Stop download modal is not displayed",ST.NativeElementFound(ObjectCollection.get("StopDownload_Modal").toString(), client));
					EF.Update_Step_Report("Stop Download Error message should displayed","Erro message is displayed||Error mssage is not displayed",ST.NativeElementFound(ObjectCollection.get("StopDownload_PopOver_InfoMsg").toString(), client));
					EF.Update_TC_Report();
					//**********Auto_D2G_039***********
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",true);
					EF.Update_Step_Report("Stop Download modal should appear","Stop download modal is appeared||Stop download modal is not displayed",ST.NativeElementFound(ObjectCollection.get("StopDownload_Modal").toString(), client));
					ST.NativeClick(ObjectCollection.get("PopOver_Cancel_Btn").toString(), client);
					//String str=client.elementGetText("NATIVE",ObjectCollection.get("DownloadsScreen_Details").toString(), 0);
					if(Current_OS.equalsIgnoreCase("IOS"))
					{
						EF.Update_Step_Report("Asset should not be deleted","Asset is not deleted||Asset is not deleted",
								ST.NativeElementFound("//*[@class='DownloadsQueueCell']/*[@text='"+str+"' and @hidden='false' and contains(property,@parentHidden)]", client));
					}
					else if(Current_OS.equalsIgnoreCase("Android"))
					{
						EF.Update_Step_Report("Asset should not be deleted","Asset is not deleted||Asset is not deleted",
								ST.NativeElementFound("//*[@id='downloads_active_row_title' and @hidden='false' and @onScreen='true' and @text='"+str+"']", client));
					}
					EF.Update_TC_Report();
					//********Auto_D2G_040************
					EF.Update_Step_Report("User should navigate to On Demand Screen","User navigated to OnDemand Screen||Navigation to On Demand was not successful",true);
					EF.Update_Step_Report("Dowloads screen  must be displayed","Downloads screen is dispayed||Downloads screen is not displayed",
							client.isElementFound("NATIVE",ObjectCollection.get("Downloads_Screen").toString(),0));
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_Cancel_Btn").toString(), client);
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_StopDownload_Btn").toString(), client);
					EF.Update_Step_Report("Downloads tab should not be displayed under My Videos screen","Downloads tab is not displayed||Downloads tab is displayed",
							!(ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client)));
					EF.Update_TC_Report();
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				}

	public void Fn_AssetDownloadingModal_Check()
				{
					//********Auto_D2G_041************
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",
							this.Fn_Com_Navigate("Home_OnDemand"));
					//selecting watch on device option
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						client.sleep(1000);
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
					}
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("KidZone_Random_Asset").toString(), 0, 1000, 2, true));
					client.waitForElement("NATIVE",ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), 0, 10000);
					ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					}
					if(ST.NativeElementFound(ObjectCollection.get("Home_btn_PopOver").toString(), client))
					{
						EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",
								ST.NativeElementFound(ObjectCollection.get("Downloading_PopOver").toString(), client));
						
					}
					else
					{
						ST.NativeClick(ObjectCollection.get("VideoQuality_PopOver_DownloadBtn").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",
								ST.NativeElementFound(ObjectCollection.get("Downloading_PopOver").toString(), client));
						
					}
					
					EF.Update_TC_Report();
					//********Auto_D2G_42************
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",true);
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",
							ST.NativeElementFound(ObjectCollection.get("Downloading_PopOver").toString(), client));
					EF.Update_Step_Report("Downloading pop over should contain proper information message","Downloading pop over appeared with proper info message||Downloading pop over is not displayed with proper info message",
							ST.NativeElementFound(ObjectCollection.get("Downloading_PopOver_InfoMsg").toString(), client));
					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("VideoQuality_PopOver_DownloadBtn").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("PopOver_Cancel_Btn").toString(), client);
						
					}
					EF.Update_TC_Report();
					//********Auto_D2G_043************
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",true);
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					ST.NativeClick(ObjectCollection.get("AssetDeailScreen_Download_ArrowMark").toString(), client);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",
							ST.NativeElementFound(ObjectCollection.get("Downloading_PopOver").toString(), client));
					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("VideoQuality_PopOver_DownloadBtn").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("VideoQuality_PopOver_DownloadBtn").toString(), client);
						
					}
					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					EF.Update_Step_Report("User should be taken back to asset detail screen","User is taken back to asset detail screen||User isn ot taken back to asset detail screen",
							ST.NativeElementFound(ObjectCollection.get("SportZone_Assetdetails_playbutton").toString(), client));
					ST.NativeClick(ObjectCollection.get("AssetDeailScreen_Download_ArrowMark").toString(), client);
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_Cancel_Btn").toString(), client);
					ST.NativeClick(ObjectCollection.get("StopDownload_Modal").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
					
				}

	public void Fn_SeriesAsset_Delete()
				{
					//******Auto_D2G_044***********
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",
							this.Fn_Com_Navigate("Home_OnDemand"));
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						client.sleep(1000);
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
						client.sleep(1000);
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
						client.sleep(1000);
						EF.Update_Step_Report("User should be on TV Section","User is on TV section||User isnot on TV section",ST.NativeClick(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client));
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
						EF.Update_Step_Report("User should be on TV Section","User is on TV section||User isnot on TV section",ST.NativeClick(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client));
					}
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					client.waitForElement("NATIVE",ObjectCollection.get("KidZone_Random_Asset").toString(),0,10000);
					ST.NativeClick(ObjectCollection.get("KidZone_Random_Asset").toString(), client);
					EF.Update_Step_Report("User should be navigated to asset detail screen","User is navigated to asset detail screen||User is not navigated to asset detail screen",ST.NativeElementFound(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(), client));
					EF.Update_Step_Report("Episodes should be available","Episodes are available||Episodes are not available",ST.NativeClick(ObjectCollection.get("AssetDetails_Episode_Panel").toString(), client));
					this.Fn_Comm_DownloadScreen();
					EF.Update_Step_Report("Edit option should be available to delete the asset","Edit option is available||Edit optionisn ot available",ST.NativeClick(ObjectCollection.get("DownloadsScreen_Edit_Btn").toString(), client));
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_EditCancel_Btn").toString(), client);
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_StopDownload_Btn").toString(), client);
					client.sleep(1000);
					EF.Update_Step_Report("Episode should get deleted","Episode is deleted||Episode is not deleted",!(ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_Details").toString(), client)));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					
					//*******Auto_D2G_045***********
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",true);
					client.click("NATIVE",ObjectCollection.get("AssetDetails_Episode_Panel").toString(), 0,1);
					this.Fn_Comm_DownloadScreen();
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					client.click("NATIVE",ObjectCollection.get("AssetDetails_Episode_Panel").toString(), 1,1);
					this.Fn_Comm_DownloadScreen();
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("DownloadsScreen_Edit_Btn").toString(), client);
						EF.Update_Step_Report("Edit Cancel button should appear","Edit cancel button is available||Edit cancel button is not available",ST.NativeClick(ObjectCollection.get("DownloadsScreen_EditCancel_Btn").toString(), client));
						
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("DownloadScreen_Edit_Image").toString(), client);
					}
					EF.Update_Step_Report("Roll up pop over should appear","Roll up pop over appeared||Roll up pop over not appeared",ST.NativeClick(ObjectCollection.get("Rollup_DeleteAll_Btn").toString(), client));
					EF.Update_TC_Report();
					
					//******Auto_D2G_046************
					EF.Update_Step_Report("User should be taken to asset detail screen","User is taken to asset detail screen || User is not taken to asset detail screen",true);
					EF.Update_Step_Report("Video quality selection should be done","Video quality selection done||Video quality selection is not done",true);
					EF.Update_Step_Report("Asset should be in progress of downloading","Asset is downloading||Asset is not downloading",true);
					EF.Update_Step_Report("Roll up pop over should appear","Roll up pop over appeared||Roll up pop over not appeared",true);
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",true);
					EF.Update_Step_Report("Pop Over should appear with Remove all and cancel button","RemoveAll and cancel options are available||Remove all and cancel options are not available",
							ST.NativeElementFound(ObjectCollection.get("RemoveEpisodes_PopOver_RemoveAllBtn").toString(), client) && ST.NativeElementFound(ObjectCollection.get("PopOver_Cancel_Btn").toString(), client));
					EF.Update_TC_Report();
					
					//**********Auto_D2G_047*************//
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",true);
					EF.Update_Step_Report("User should be taken to asset detail screen","User is taken to asset detail screen || User is not taken to asset detail screen",true);
					EF.Update_Step_Report("Video quality selection should be done","Video quality selection done||Video quality selection is not done",true);
					EF.Update_Step_Report("Asset should be in progress of downloading","Asset is downloading||Asset is not downloading",true);
					EF.Update_Step_Report("Roll up pop over should appear","Roll up pop over appeared||Roll up pop over not appeared",true);
					EF.Update_Step_Report("Pop Over should appear with Remove all and cancel button","RemoveAll and cancel options are available||Remove all and cancel options are not available",
							ST.NativeElementFound(ObjectCollection.get("RemoveEpisodes_PopOver_RemoveAllBtn").toString(), client) && ST.NativeElementFound(ObjectCollection.get("PopOver_Cancel_Btn").toString(), client));
					EF.Update_Step_Report("Remove download pop over should appear with proper info message","Proper info message is displayed||Poper info message isnot displayed",ST.NativeElementFound(ObjectCollection.get("RemoveEpisodes_PopOver_InfoMsg").toString(), client));
					EF.Update_TC_Report();
					
					//**********Auto_D2G_048*************//
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",true);
					EF.Update_Step_Report("User should be taken to asset detail screen","User is taken to asset detail screen || User is not taken to asset detail screen",true);
					EF.Update_Step_Report("Video quality selection should be done","Video quality selection done||Video quality selection is not done",true);
					EF.Update_Step_Report("Asset should be in progress of downloading","Asset is downloading||Asset is not downloading",true);
					EF.Update_Step_Report("Roll up pop over should appear","Roll up pop over appeared||Roll up pop over not appeared",ST.NativeClick(ObjectCollection.get("DownloadScreen_Edit_Image").toString(), client));
					ST.NativeClick(ObjectCollection.get("Rollup_DeleteAll_Btn").toString(), client);
					EF.Update_Step_Report("Remove Episodes pop over should appear","Remove Episodes pop over appeared",ST.NativeElementFound(ObjectCollection.get("RemoveEpisodes_PopOver").toString(), client));
					ST.NativeClick(ObjectCollection.get("PopOver_Cancel_Btn").toString(), client);
					EF.Update_Step_Report("User sould be taken back to the Roll up modal popover","User is navigated back to Roll up modal||User is not navigated to Roll up modal",ST.NativeElementFound(ObjectCollection.get("Rollup_DeleteAll_Btn").toString(), client));
					EF.Update_TC_Report();
					
					//*********Auto_D2G_049**************//
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",true);
					EF.Update_Step_Report("User should be taken to asset detail screen","User is taken to asset detail screen || User is not taken to asset detail screen",true);
					EF.Update_Step_Report("Video quality selection should be done","Video quality selection done||Video quality selection is not done",true);
					EF.Update_Step_Report("Asset should be in progress of downloading","Asset is downloading||Asset is not downloading",true);
					EF.Update_Step_Report("Roll up pop over should appear","Roll up pop over appeared||Roll up pop over not appeared",ST.NativeClick(ObjectCollection.get("Rollup_DeleteAll_Btn").toString(), client));
					EF.Update_Step_Report("Remove Episodes pop over should appear","Remove Episodes pop over appeared",ST.NativeClick(ObjectCollection.get("RemoveEpisodes_PopOver_RemoveAllBtn").toString(), client));
					EF.Update_Step_Report("All the episodes scheduled for downloading should get deleted","All epsiodes are deleted||All episodes are not deleted",!(ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client)));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				}

	public void Fn_MultipleSeriesAsset_Delete()
				{
					//*********Auto_D2G_050 and 51**************//
					EF.Update_Step_Report("User should be navigated to On Demand screen", "User navigated to On demand screen||User not navigated to On Demand screen",
							this.Fn_Com_Navigate("Home_OnDemand"));
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						client.sleep(1000);
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
						client.sleep(1000);
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client);
						client.sleep(1000);
						EF.Update_Step_Report("User should be on TV Section","User is on TV section||User isnot on TV section",ST.NativeClick(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client));
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
						EF.Update_Step_Report("User should be on TV Section","User is on TV section||User isnot on TV section",ST.NativeClick(ObjectCollection.get("OnDemand_Btn_TvSection").toString(), client));
					}
					client.waitForElement("NATIVE",ObjectCollection.get("KidZone_Random_Asset").toString(),0,10000);
					ST.NativeClick(ObjectCollection.get("KidZone_Random_Asset").toString(), client);
					EF.Update_Step_Report("User should be navigated to asset detail screen","User is navigated to asset detail screen||User is not navigated to asset detail screen",ST.NativeElementFound(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(), client));
					EF.Update_Step_Report("Episodes should be available","Episodes are available||Episodes are not available",ST.NativeClick(ObjectCollection.get("AssetDetails_Episode_Panel").toString(), client));
					this.Fn_Comm_DownloadScreen();
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("Season_Select_DropDown").toString(), client);
					if(Current_OS.equalsIgnoreCase("IOS"))
					{
						EF.Update_Step_Report("Season select drop down should appear","Season Select dropdown appeared||Season select drop down not appeared",ST.NativeClick(ObjectCollection.get("Season_Select_DropDown_Option").toString(), client));
					}
					else if(Current_OS.equalsIgnoreCase("Android"))
					{
						EF.Update_Step_Report("Season select drop down should appear","Season Select dropdown appeared||Season select drop down not appeared",ST.NativeElementFound(ObjectCollection.get("Season_Select_DropDown_Option").toString(), client));
						client.click("NATIVE",ObjectCollection.get("Season_Select_DropDown_Option").toString(),1,1);
					}
					this.Fn_Comm_DownloadScreen();
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("DownloadsScreen_Edit_Btn").toString(), client);
						EF.Update_Step_Report("Edit Cancel button should appear","Edit cancel button is available||Edit cancel button is not available",ST.NativeClick(ObjectCollection.get("DownloadsScreen_EditCancel_Btn").toString(), client));
						
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("DownloadScreen_Edit_Image").toString(), client);
					}
					EF.Update_Step_Report("Roll up pop over should appear","Roll up pop over appeared||Roll up pop over not appeared",ST.NativeClick(ObjectCollection.get("Rollup_DeleteAll_Btn").toString(), client));
					ST.NativeClick(ObjectCollection.get("Rollup_DeleteAll_Btn").toString(), client);
					EF.Update_Step_Report("User sould be taken back to the Roll up modal popover","User is navigated back to Roll up modal||User is not navigated to Roll up modal",ST.NativeElementFound(ObjectCollection.get("Rollup_DeleteAll_Btn").toString(), client));
					ST.NativeClick(ObjectCollection.get("Rollup_DeleteAll_Btn").toString(), client);
					EF.Update_Step_Report("Pop Over should appear with Remove all and cancel button","RemoveAll and cancel options are available||Remove all and cancel options are not available",
							ST.NativeElementFound(ObjectCollection.get("RemoveEpisodes_PopOver_RemoveAllBtn").toString(), client) && ST.NativeElementFound(ObjectCollection.get("PopOver_Cancel_Btn").toString(), client));
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					EF.Update_Step_Report("User should be able to delete the multiple episodes of same series","Assets are deleted||Assets are not downloaded",!(ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client)));
					
				}

	public void Fn_MoreDeviceRegistered_Functionality()
				{
					//When more devices are connected Register button should be grade out
					//*******Auto_D2G_052************
					Boolean bFlag=this.Fn_Com_Navigate("Home_Settings");
					EF.Update_Step_Report("User should be navigated to Settings screen","User navigated to settings screen||User not navigated to settings screen",	bFlag);
					EF.Update_Step_Report("User should be on Manage device screen","User is on Manage device screen||User is not on Manage device screen",ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client));
					client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 2, true);				
					client.swipe("Down", 400, 500);
					int count = client.getElementCount("NATIVE", ObjectCollection.get("Settings_Btn_Remove").toString());
					if(count>4)
					{
						//Disabled Register button check
						EF.Update_Step_Report("More than five devices should be registered to execute this test case","More than 5 devices registered||More than five devices not registered",true);
						EF.Update_Step_Report("Register option should be disabled","Register option is disabled||Register option is not disabled",ST.NativeElementFound(ObjectCollection.get("Disabled_Register_Btn").toString(), client));
						EF.Update_TC_Report();
						
						//Checking Whether Register modal appears  in Downloads screen
						//******Auto_D2G_053************
						EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",this.Fn_Com_Navigate("Home_OnDemand"));
						if(Current_OS.equalsIgnoreCase("Android"))
						{
							ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
							client.sleep(1000);
							ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
						}
						else if(Current_OS.equalsIgnoreCase("IOS"))
						{
							ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
						}
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",
								client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("KidZone_Random_Asset").toString(), 0, 1000, 2, true));
						client.waitForElement("NATIVE",ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), 0, 10000);
						ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client);
						EF.Update_Step_Report("Register Device modal should appear", "Regiser modal appared||Register modal is not displayed",ST.NativeElementFound(ObjectCollection.get("RegisterDevice_PopOver").toString(), client));
						EF.Update_TC_Report();
						
						//Checking whether Cancel and settings buttons are available in Register modal
						//******Auto_D2G_054************
						EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",bFlag);
						EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
						EF.Update_Step_Report("Register Device modal should appear with cancel and settings button", "Modal appeared with can cancel and settings button",
								ST.NativeElementFound(ObjectCollection.get("PopOver_Cancel_Btn").toString(),client)&&ST.NativeElementFound(ObjectCollection.get("RegisterDevice_Settings_Btn").toString(),client));
						EF.Update_TC_Report();
						
						//Checking Info message in Register modal in Dwonloads screen (taking user to settings screen modal)
						//******Auto_D2G_055************
						EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",true);
						EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
						EF.Update_Step_Report("Register Device modal should appear with cancel and settings button", "Modal appeared with can cancel and settings button",
								ST.NativeElementFound(ObjectCollection.get("PopOver_Cancel_Btn").toString(),client)&&ST.NativeElementFound(ObjectCollection.get("RegisterDevice_Settings_Btn").toString(),client));
						EF.Update_Step_Report("Proper information message should be displayed in Register device modal","Proper message is dislayed||Proper message is not displayed",ST.NativeElementFound(ObjectCollection.get("RegisterDevice_GoToSettings_Msg").toString(), client));
						EF.Update_TC_Report();
						
						//Checking Cancel button functionality in Register-Go to settings page
						//******Auto_D2G_056************
						EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",true);
						EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
						EF.Update_Step_Report("Register Device modal should appear with cancel and settings button", "Modal appeared with can cancel and settings button",
								ST.NativeElementFound(ObjectCollection.get("PopOver_Cancel_Btn").toString(),client)&&ST.NativeElementFound(ObjectCollection.get("RegisterDevice_Settings_Btn").toString(),client));
						ST.NativeClick(ObjectCollection.get("PopOver_Cancel_Btn").toString(), client);
						EF.Update_Step_Report("User should be navigated back to the Asset detail screen","User is navigated to asset detail screen||User is not navigated to asset detail screen",ST.NativeElementFound(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client));
						EF.Update_TC_Report();
						
						//Checking Settings button functionality in Register-Go to settings page
						//******Auto_D2G_057************
						EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",true);
						EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
						EF.Update_Step_Report("Register Device modal should appear with cancel and settings button", "Modal appeared with can cancel and settings button",
								ST.NativeElementFound(ObjectCollection.get("PopOver_Cancel_Btn").toString(),client)&&ST.NativeElementFound(ObjectCollection.get("RegisterDevice_Settings_Btn").toString(),client));
						ST.NativeClick(ObjectCollection.get("RegisterDevice_Settings_Btn").toString(), client);
						client.sleep(1000);
						EF.Update_Step_Report("User should be navigated to settings page","User is navigated to settings page||User is not navigated to settings page",ST.NativeElementFound(ObjectCollection.get("Settings_Btn_Remove").toString(), client));
						EF.Update_TC_Report();
						
						//Check the count of remove button after removing a device
						//******Auto_D2G_058************
						EF.Update_Step_Report("User should be navigated to Settings screen","User navigated to settings screen||User not navigated to settings screen",	bFlag);
						EF.Update_Step_Report("User should be on Manage device screen","User is on Manage device screen||User is not on Manage device screen",ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client));
						client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Settings_RegisteredDevices_Lable").toString(), 0, 1000, 2, true);
						ST.NativeClick(ObjectCollection.get("Settings_Btn_Remove").toString(), client);
						EF.Update_Step_Report("User should remove a device form","User removed the device successfully||User not removed any device",ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client));
						int count1 = client.getElementCount("NATIVE", ObjectCollection.get("Settings_Btn_Remove").toString());
						EF.Update_Step_Report("The remove buttons should get decreased in count","Remove button count is decreased||The remove button count is not decreased",count1<count);
						EF.Update_TC_Report();
						
						//On Removing a device the register button should get enabled
						//******Auto_D2G_059************
						EF.Update_Step_Report("User should be navigated to Settings screen","User navigated to settings screen||User not navigated to settings screen",	bFlag);
						EF.Update_Step_Report("User should be on Manage device screen","User is on Manage device screen||User is not on Manage device screen",ST.NativeClick(ObjectCollection.get("Settings_ManageDevices_Lable").toString(), client));
						EF.Update_Step_Report("Register option should be disabled","Register option is disabled||Register option is not disabled",ST.NativeElementFound(ObjectCollection.get("Disabled_Register_Btn").toString(), client));
						EF.Update_Step_Report("One device should be removed","One Device is removed||One device is not decreased",count1<count);
						EF.Update_Step_Report("The Register button should be enabled after removing a device","Regiter button is enabled||Register button is not enabled",ST.NativeElementFound(ObjectCollection.get("Settings_Btn_Register").toString(), client));
						ST.NativeClick(ObjectCollection.get("Settings_Btn_Register").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
						
					}
					
					else
					{
						EF.Update_Step_Report("More than five devices should be registered to execute this test case","More than 5 devices registered||More than five devices not registered",false);
					}
					 
				}

	public void Fn_OnDemand_Download_FlyOver()
				{
					//********Auto_D2G_060**********
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",this.Fn_Com_Navigate("Home_OnDemand"));
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						client.sleep(1000);
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
					}
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("KidZone_Random_Asset").toString(), 0, 1000, 2, true));
					client.waitForElement("NATIVE",ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), 0, 10000);
					String str1 = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(), 0);
					ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					if(ST.NativeElementFound(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client);
						client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
						ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					}
					ST.NativeClick(ObjectCollection.get("VideoQuality_PopOver_DownloadBtn").toString(), client);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",
							ST.NativeElementFound(ObjectCollection.get("Downloading_PopOver").toString(), client));
					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					Boolean cFlag=client.waitForElement("NATIVE",ObjectCollection.get("Download_AlertRibbon_GoToDownloads").toString(),0,300000);
					String str2 = client.elementGetText("NATIVE",ObjectCollection.get("Download_AlertRibbon_Text").toString(), 0);
					Boolean dFlag=ST.NativeClick(ObjectCollection.get("Download_AlertRibbon_GoToDownloads").toString(), client);
					EF.Update_Step_Report("Asset should start downloading and fly over should appear","Downloading of asset is started||Downloading of asset is not started" ,str1.equalsIgnoreCase("Downloading:"+str1));
					EF.Update_TC_Report();
					//Checking the presence of got to downloads in fly over
					//*******************Auto_D2G_061*****************
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",true);
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",true);
					EF.Update_Step_Report("Go to downloads btn should appear in fly over","Go to downloads appeared||Go to downloads not appeared",dFlag);
					EF.Update_TC_Report();
					//Verifying the functionality of GoToDownloads in flyover
					//*****************Auto_D2G_062***************
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",true);
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",true);
					EF.Update_Step_Report("Go to downloads btn should appear in fly over","Go to downloads appeared||Go to downloads not appeared",dFlag);
					EF.Update_Step_Report("User should be navigated to Downloads screen","User is navigated Downloads screen||User is not navigated to downloads screen",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					//Checking the Fly over ribbon for second download
					//*************Auto_D2G_063******************
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",true);
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("KidZone_Random_Asset").toString(), 1, 1000, 2, true));
					String str3 = client.elementGetText("NATIVE",ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(), 0);
					ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client);
					ST.NativeClick(ObjectCollection.get("VideoQuality_PopOver_DownloadBtn").toString(), client);
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",
							ST.NativeElementFound(ObjectCollection.get("Downloading_PopOver").toString(), client));
					ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
					EF.Update_Step_Report("Fly over ribbon should appear","Fly over ribbon appeared||Fly over ribbon not appeared",client.waitForElement("NATIVE",ObjectCollection.get("Download_AlertRibbon_GoToDownloads").toString(),0,300000));
					String str4 = client.elementGetText("NATIVE",ObjectCollection.get("Download_AlertRibbon_Text").toString(), 0);
					//To be done
				}

	public void Fn_DownloadScreen_EditImageCheck()
				{
				
					//********Auto_D2G_064************
					//verify the presence of asset cover art in downloads screen
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",this.Fn_Com_Navigate("Home_OnDemand"));
					if(Current_OS.equalsIgnoreCase("Android"))
					{
						ST.NativeClick(ObjectCollection.get("OnDemand_Andriod_WatchOn_Spinner").toString(), client);
						client.sleep(1000);
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnTablet").toString(), client);
					}
					else if(Current_OS.equalsIgnoreCase("IOS"))
					{
						ST.NativeClick(ObjectCollection.get("Btn_WatchOnIPad").toString(), client);
					}
					client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
					client.waitForElement("NATIVE",ObjectCollection.get("KidZone_Random_Asset").toString(),0,10000);
					ST.NativeClick(ObjectCollection.get("KidZone_Random_Asset").toString(), client);
					EF.Update_Step_Report("User should be navigated to asset detail screen","User is navigated to asset detail screen||User is not navigated to asset detail screen",ST.NativeElementFound(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(), client));
					this.Fn_Comm_DownloadScreen();
					EF.Update_Step_Report("Cover art of the downloading asset must be available in Downoads screen","Cover art available||Cover art not available",	ST.NativeElementFound(ObjectCollection.get("DownloadScreen_Edit_Image").toString(), client));
					EF.Update_TC_Report();
					
					//********Auto_D2G_065************
					//Checking the presence of Expires in detail under asset cover art in downloads screen
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",this.Fn_Com_Navigate("Home_OnDemand"));
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",true);
					EF.Update_Step_Report("User should be navigated to Downloads screen","User is navigated Downloads screen||User is not navigated to downloads screen",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					EF.Update_Step_Report("Asset Expires in detail should be available under cover art of the asset in downloads screen","Expires on details is availbale||Expires On Deatails is available",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_CoverArt_ExpiresIn").toString(), client));
					EF.Update_TC_Report();
					
					//********Auto_D2G_066************
					//Verifying whether click on asset cover art in downloads screen navigates the user to asset detail screen
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",this.Fn_Com_Navigate("Home_OnDemand"));
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",true);
					EF.Update_Step_Report("User should be navigated to Downloads screen","User is navigated Downloads screen||User is not navigated to downloads screen",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					EF.Update_Step_Report("Cover art of the downloading asset must be available in Downoads screen","Cover art available||Cover art not available",	ST.NativeElementFound(ObjectCollection.get("DownloadScreen_Edit_Image").toString(), client));
					ST.NativeClick(ObjectCollection.get("DownloadScreen_Edit_Image").toString(), client);
					EF.Update_Step_Report("User should be navigated to Asset details screen","User is navigated to asset detail screen||User is not navigated to asset detail screen",ST.NativeElementFound(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_TC_Report();
					
					//Verifying whether the downloads section in My library contains the downloading assets cover art
					//*****Auto_D2G_067***********
					//Triggering a download
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",this.Fn_Com_Navigate("Home_OnDemand"));
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",true);
					//Navigating to My  Library screen to check for asset cover art in downloads section 
					EF.Update_Step_Report("User should be navigated to Downloads screen","User is navigated Downloads screen||User is not navigated to downloads screen",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					EF.Update_Step_Report("User should be navigated to My Library screen","User is navigated to MyLibrary screen||User not navigated to My Library screen",this.Fn_Com_Navigate("Home_MyLibrary"));
					EF.Update_Step_Report("Downloading asset cover art should be available in downloads section","Dwonloading asset cover art is available||Downloading asset cover art is not available",
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE",ObjectCollection.get("Downloads_AssetContainer").toString(), 0, 1000, 5, false));
					EF.Update_TC_Report();
					
					//Verifying whether on clicking on the cover art of asset in dowloads section navigates the user to downloads screen
					//*****Auto_D2G_068***********
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",this.Fn_Com_Navigate("Home_OnDemand"));
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",true);
					EF.Update_Step_Report("User should be navigated to Downloads screen","User is navigated Downloads screen||User is not navigated to downloads screen",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					//Navigating to My  Library screen to check for asset cover art in downloads section 
					EF.Update_Step_Report("User should be navigated to My Library screen","User is navigated to MyLibrary screen||User not navigated to My Library screen",this.Fn_Com_Navigate("Home_MyLibrary"));
					EF.Update_Step_Report("Downloading asset cover art should be available in downloads section","Dwonloading asset cover art is available||Downloading asset cover art is not available",true);
					ST.NativeClick(ObjectCollection.get("Downloads_AssetContainer").toString(), client);
					EF.Update_Step_Report("User should be navigated to downloads screen","User is navigated to downloads screen||User is not navigated to Downloads screen",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					EF.Update_TC_Report();
					
					//Verifying whether on clicking pause icon in downloads screen shows paused text 
					//*********Auto_D2G_069************
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",this.Fn_Com_Navigate("Home_OnDemand"));
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",true);
					EF.Update_Step_Report("User should be navigated to Downloads screen","User is navigated Downloads screen||User is not navigated to downloads screen",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					client.waitForElement("NATIVE",ObjectCollection.get("DownloadsScreen_Pause_Btn").toString(),0,300000);
					EF.Update_Step_Report("Pause icon should be available in downloads screen","Pause icon is available||Pause icon is not available",ST.NativeClick(ObjectCollection.get("DownloadsScreen_Pause_Btn").toString(), client));
					EF.Update_Step_Report("Paused lable should be displayed above the progress bar","Pause lable is displayed||Pause icon is not displayed",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_Paused_Lable").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					
					
					//Logout of the app and login with same credential and check whether the current downloads are retained
					//********Auto_D2G_070************
					//Triggering a download
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",this.Fn_Com_Navigate("Home_OnDemand"));
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",true);
					//Navigating to My  Library screen to check for asset cover art in downloads section 
					EF.Update_Step_Report("User should be navigated to Downloads screen","User is navigated Downloads screen||User is not navigated to downloads screen",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					EF.Update_Step_Report("User should be navigated to My Library screen","User is navigated to MyLibrary screen||User not navigated to My Library screen",this.Fn_Com_Navigate("Home_MyLibrary"));
					EF.Update_Step_Report("Downloading asset cover art should be available in downloads section","Dwonloading asset cover art is available||Downloading asset cover art is not available",
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE",ObjectCollection.get("Downloads_AssetContainer").toString(), 0, 1000, 5, false));
					EF.Update_Step_Report("User should be Logged out of the application","User logged out of the application||User not logged out of the application",this.Fn_LogOut());
					EF.Update_Step_Report("User should be loggd in again with the application with the same credentials","User logged in with the same credentials||User not logged in with the same credentials",this.Fn_Login());
					EF.Update_Step_Report("Downloads should be retained","Downloads retained||Downloads not retained",
							client.swipeWhileNotFound("Down", 300, 2000, "NATIVE",ObjectCollection.get("Downloads_AssetContainer").toString(), 0, 1000, 5, false));
					
					//Verifying whether on clearing the downloads the asset cover art gets disappeared in My Library screen
					//******Auto_D2G_071***********
					EF.Update_Step_Report("User should be navigated to On Demand screen","User is navigated to On Demand screen||User not navigated to On Demand screen",this.Fn_Com_Navigate("Home_OnDemand"));
					EF.Update_Step_Report("Asset should be available for downloading","Asset available||No asset available for downloading ",true);
					EF.Update_Step_Report("Downloading pop over should appear","Downloading pop over appeared||Downloading pop over is not displayed",true);
					//Navigating to My  Library screen to check for asset cover art in downloads section 
					EF.Update_Step_Report("User should be navigated to Downloads screen","User is navigated Downloads screen||User is not navigated to downloads screen",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					EF.Update_Step_Report("User should be navigated to My Library screen","User is navigated to MyLibrary screen||User not navigated to My Library screen",this.Fn_Com_Navigate("Home_MyLibrary"));
					EF.Update_Step_Report("Downloading asset cover art should be available in downloads section","Dwonloading asset cover art is available||Downloading asset cover art is not available",true);
					ST.NativeClick(ObjectCollection.get("Downloads_AssetContainer").toString(), client);
					EF.Update_Step_Report("User should be navigated to downloads screen","User is navigated to downloads screen||User is not navigated to Downloads screen",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));
					ST.NativeClick(ObjectCollection.get("DownloadsScreen_Cancel_Btn").toString(),client);
					EF.Update_Step_Report("Stop download pop over should appear","Stop download pop over appeared||Stop download pop over not appeared",ST.NativeClick(ObjectCollection.get("DownloadsScreen_StopDownload_Btn").toString(), client));
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					EF.Update_Step_Report("Asset cover art should not be available in My Library's downloads section ","Asset cover art is not available||asset cover art is not available",ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_DownloadsTab").toString(), client));	
				}

	public void Fn_MyLibrary_DownloadsCheck()
				{
					//Login with different credential to check the downloads are not retained
					//******Auto_D2G_072***********
					EF.Update_Step_Report("User should logout of the application","User is logged out of the application||User is not logged out of the application",this.Fn_LogOut());
					EF.Update_Step_Report("User should be logged in to the application with different credentials","User is logged into the application||User is not logged into the application",this.Fn_Login());
					EF.Update_Step_Report("Downloads should not be retained","Downloads are not retained||Downloads are retained",
							!(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE",ObjectCollection.get("Downloads_AssetContainer").toString(), 0, 1000, 5, false)));
					EF.Update_TC_Report();
					
					//Login with the previously entered credentials to check the downloads are not retained
					//******Auto_D2G_073***********
					EF.Update_Step_Report("User should be logged out of the application","User logged out of the application||User not logged out of the application",this.Fn_LogOut());
					EF.Update_Step_Report("User should login back with the same credentials","User logged in with same credential||User is not logged in with the same credential",this.Fn_Login());
					EF.Update_Step_Report("Downloads should not be retained","Downloads are not retained||Downloads are retained",
							!(client.swipeWhileNotFound("Down", 300, 2000, "NATIVE",ObjectCollection.get("Downloads_AssetContainer").toString(), 0, 1000, 5, false)));
					
				}
	public void Fn_OnDemand_FeatureBannerCheck()
    {
          if(ST.NativeElementFound(ObjectCollection.get("OnDemand_FeatureBanner").toString(), client))
          {
                if(Current_OS.equalsIgnoreCase("Android"))
                {
                      
                            client.elementSwipe("NATIVE",  ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, "Right", 1000, 2000);
                            client.sleep(1000);
                            EF.Update_Step_Report("Feature Banner should be able to swipe Right", "Feature Banner swiped Right||Feature Banner could not be swiped Right", 
                                        true);
                            client.elementSwipe("NATIVE",  ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, "Left", 1000, 2000);
                            client.sleep(1000);
                            EF.Update_Step_Report("Feature Banner should be able to swipe Left", "Feature Banner swiped Left||Feature Banner could not be swiped Left", 
                                        true);
                }
                
                else if(Current_OS.equalsIgnoreCase("IOS"))
                {
                            //Swiping feature banner left
                            String banner1 = client.elementGetProperty("NATIVE", ObjectCollection.get("OnDemand_FeatureBanner").toString(), 0, "accessibilityIdentifier");
                            client.elementSwipe("NATIVE",  ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, "Left", 1000, 2000);
                            client.sleep(1000);
                            String banner2 = client.elementGetProperty("NATIVE", ObjectCollection.get("OnDemand_FeatureBanner").toString(), 0, "accessibilityIdentifier");
//                            EF.Update_Step_Report("Feature Banner shold be able to swipe Left", "Feature Banner swiped Left||Feature Banner could not be swiped left", 
//                                        !(banner1.equals(banner2)));
                            EF.Update_Step_Report("Feature Banner shold be able to swipe Left", "Feature Banner swiped Left||Feature Banner could not be swiped left", true);
                            //Swiping feature banner right
                            client.elementSwipe("NATIVE",  ObjectCollection.get("OnDemand_FeatureBanner_Container").toString(), 0, "Right", 1000, 2000);
                            client.sleep(1000);
                            banner1 = client.elementGetProperty("NATIVE", ObjectCollection.get("OnDemand_FeatureBanner").toString(), 0, "accessibilityIdentifier");
                            //EF.Update_Step_Report("Feature Banner shold be able to swipe Right", "Feature Banner swiped Right||Feature Banner could not be swiped Right", 
                            //            !(banner1.equals(banner2)));
                              EF.Update_Step_Report("Feature Banner shold be able to swipe Right", "Feature Banner swiped Right||Feature Banner could not be swiped Right", 
                                                true);
                            //Checking whether Asset pop over appears on clicking the feature banner   
                            ST.NativeClick(ObjectCollection.get("OnDemand_FeatureBanner").toString(), client);
                            EF.Update_Step_Report("Tapping banner must bring the user to the Fullscreen Asset Detail for the selected asset","Asset Pop Over appeared||Asset pop Over not appeared", 
                                        ST.NativeElementFound(ObjectCollection.get("Home_Title_AssetDetailScreen").toString(), client));
                            client.click("NATIVE", ObjectCollection.get("AssetPopOver_Button_Back").toString(), 0, 1);
                            client.sleep(1000);
                            if(ST.NativeElementFound(ObjectCollection.get("Home_PopOver_Done").toString(), client))
                            {
                                  ST.NativeClick(ObjectCollection.get("Home_PopOver_Done").toString(), client);
                            }
                }
          }
          else
          {
                EF.Update_Step_Report("Medai banner should be loaded","Medai banner loaded||Media banner not loaded",false);
          }
                
    }


	public void Fn_Comm_DownloadScreen()
          {
                EF.Update_Step_Report("User should be taken to asset detail screen","User is taken to asset detail screen || User is not taken to asset detail screen",client.waitForElement("NATIVE",ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), 0, 10000));
                ST.NativeClick(ObjectCollection.get("AssetDetailsScreen_DownloadIcon").toString(), client);
                if(ST.NativeElementFound(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client))
                {
                      ST.NativeClick(ObjectCollection.get("OnDemand_RegisterDevicePopOver_Register_Btn").toString(), client);
                      client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
                      ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
                }
                ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
                //EF.Update_Step_Report("Video quality selection should be done","Video quality selection done||Video quality selection is not done",ST.NativeClick(ObjectCollection.get("VideoQuality_PopOver_DownloadBtn").toString(), client));
                ST.NativeClick(ObjectCollection.get("VideoQuality_PopOver_DownloadBtn").toString(), client);
                client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(), 0, 300000);
                ST.NativeClick(ObjectCollection.get("Home_btn_PopOver").toString(), client);
                client.sleep(1000);
                ST.NativeClick(ObjectCollection.get("AssetDeailScreen_Download_ArrowMark").toString(), client);
                EF.Update_Step_Report("Asset should be in progress of downloading","Asset is downloading||Asset is not downloading",
                            ST.NativeElementFound(ObjectCollection.get("DownloadsScreen_Details").toString(), client));

          }
	public void Fn_Validate_AddWatchlist_1Serieswith2Episodes() throws InterruptedException

	{
		Boolean Local_Result = true;
				
		// ************* Adding Watchlist item for TV series with 2 Episodes ***************************************************** //
		EF.Update_Step_Report("Navigate to On Demand screen", "Successfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			
			EF.Update_Step_Report("Check for On Demand TV option in Dropdown box", "On Demand TV option is available||On Demand TV is not available",
					ST.NativeElementFound(ObjectCollection.get("OnDemand_TV").toString(), client));
			EF.Update_Step_Report("Click on On Demand TV tab", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
		}
		else
		{
			EF.Update_Step_Report("Click on On Demand Drop down box", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
			EF.Update_Step_Report("Check for On Demand TV option in Dropdown box", "On Demand TV option is available||On Demand TV is not available",
					ST.NativeElementFound(ObjectCollection.get("OnDemand_TV").toString(), client));
			EF.Update_Step_Report("Click on On Demand TV tab", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
		}
		//client.wait(5000);
		Local_Result=client.swipeWhileNotFound("Down", 800, 2000, "NATIVE", ObjectCollection.get("TV_Asset_Posterart").toString(), 0, 1000, 3, true);
		//Local_Result=client.elementSwipeWhileNotFound("NATIVE", "//*[@id='new_media_scroller_container']", "Down", 100, 2000, "NATIVE", ObjectCollection.get("TV_Asset_Posterart").toString(), 1, 1000, 5, true);
		EF.Update_Step_Report("Click on On Demand TV Asset PosterArt", "Clicked sucessfully||Click failed", Local_Result);
		String SeriesTitle1 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(),0, client);
		String SeriesDescription1 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesDescription").toString(),0, client);
		
		// *******************  Adding Watchlist item for TV Series first Episode ***********************************************
		EF.Update_Step_Report("Click on On Demand TV Asset Series Episode", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_TV_Asset_SeriesEpisode1").toString(), client));
		String FirstEpisodeName1 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_EpisodeName").toString(),0, client);
		String SubEpisodeName1 = (FirstEpisodeName1.split("\\("))[0];
		System.out.println(SubEpisodeName1);
		String FirstEpisodeDescription1 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_EpisodeDescription").toString(),0, client);
		EF.Update_Step_Report("Check for Watchlist Icon in Asset Details Page", "Watchlist icon is available||Watchlist icon is not available",
				ST.NativeElementFound(ObjectCollection.get("AssetDetails_Watchlist").toString(), client));
		EF.Update_Step_Report("Click on On Demand TV Asset Series Episode Watchlist Icon", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("AssetDetails_Watchlist").toString(), client));
		// Verify the Confirmation message for Added Watchlist
		String WatchlistSuccessfulMessage1 = ST.NativeGetElementText(ObjectCollection.get("AddWatchlist_Confirmation_Message").toString(),0, client);
		if (!(WatchlistSuccessfulMessage1.contains(SeriesTitle1)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Confirmation message for Added Watchlist", "Sucessfully Verified Confirmation message for Added Watchlist||Confirmation message Verification failed for Add Watchlist",Local_Result);
		Local_Result = true;
		EF.Update_Step_Report("Click on OK button in Add Watchlist Pop Over", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_AddWatchlist_OK").toString(), client));
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_TC_Report();
		
		// ********************* Verification for Added TV Series First Episode Name in My Library Screen under Watchlist Section **************************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			Local_Result=client.swipeWhileNotFound("Down", 800, 2000, "NATIVE", "//*[@accessibilityLabel='Watchlist']/ancestor::*[@class='UITableViewCellContentView']/descendant::*[@class='GTMFadeTruncatingLabel']", 0, 1000, 5, true);
			//Local_Result=client.elementSwipeWhileNotFound("NATIVE", "//*[@id='new_media_scroller_container']", "Down", 100, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller']//*[@id='new_media_scroller_title_container']/following-sibling::*/*/*[@id='new_media_scroller_poster_art']", 0, 1000, 5, true);
			EF.Update_Step_Report("Click on Added Watchlist PosterArt", "Clicked sucessfully||Click failed", Local_Result);
		}
		else
		{
			Local_Result=client.swipeWhileNotFound("Down", 800, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller' and @onScreen='true']//*[@id='new_media_scroller_title_container' and @onScreen='true']/following-sibling::*/*/*[@id='new_media_scroller_poster_art' and @onScreen='true']", 0, 1000, 5, true);
			//Local_Result=client.elementSwipeWhileNotFound("NATIVE", "//*[@id='new_media_scroller_container']", "Down", 100, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller']//*[@id='new_media_scroller_title_container']/following-sibling::*/*/*[@id='new_media_scroller_poster_art']", 0, 1000, 5, true);
			EF.Update_Step_Report("Click on Added Watchlist PosterArt", "Clicked sucessfully||Click failed", Local_Result);
		}
		// Verification for Added TV Series Name
		String SeriesTitle2 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(),0, client);
		if (!(SeriesTitle2.contains(SeriesTitle1)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Series title in Asset Details page", "Sucessfully Verified Series title in Asset Details page||Series title Verification failed in Asset Details page",Local_Result);
		Local_Result = true;
		// Verification for Added first Episode name
		String FirstEpisodeName2 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_EpisodeName").toString(),0, client);
		if (!(FirstEpisodeName2.contains(FirstEpisodeName1)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify first Episode name for Added Watchlist", "Sucessfully Verified first Episode name for Added Watchlist||First Episode name Verification failed for Add Watchlist",Local_Result);
		Local_Result = true;
		// Verification for Added first Episode Description
		String FirstEpisodeDescription2 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_EpisodeDescription").toString(),0, client);
		if (!(FirstEpisodeDescription2.contains(FirstEpisodeDescription1)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify first Episode Description for Added Watchlist", "Sucessfully Verified first Episode Description for Added Watchlist||First Episode Description Verification failed for Add Watchlist",Local_Result);
		Local_Result = true;
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		EF.Update_TC_Report();
		
		// ********************* Verification for Added TV Series First Episode Name in Watchlist Screen *******************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			
			EF.Update_Step_Report("Display Watchlist Shelf under My Library screen","Watchlist shelf should be available under My Library Screen||Watchlist shelf is not available under My Library Screen",
	                 client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist_BrowseAll").toString(), 0, 1000, 10, true));
	    }
		else
		{		
			Local_Result=client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller' and @onScreen='true']//*[@id='new_media_scroller_browse_all'and@onScreen='true']", 0, 1000, 6, true);
			EF.Update_Step_Report("Click on Watchlist BrowseAll button", "Clicked sucessfully||Click failed", Local_Result);
		}
		
		EF.Update_Step_Report("Click Added Watchlist Item in Watchlist Screen", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("Watchlist_Screen_AssetName").toString(), client));
		// Verification for the Added First Episode Name
		String FirstEpisodeName3 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_EpisodeName").toString(),0, client);
		if (!(FirstEpisodeName3.contains(FirstEpisodeName1)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify VOD Asset Name in Asset Details page", "Sucessfully Verified VOD Asset Name in Asset Details page||VOD Asset Name Verification failed in Asset Details page",Local_Result);
		Local_Result = true;
		// Verification for the Added First Episode Description
		String FirstEpisodeDescription3 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_EpisodeDescription").toString(),0, client);
		if (!(FirstEpisodeDescription3.contains(FirstEpisodeDescription1)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify VOD Asset Description in Asset Details page", "Sucessfully Verified VOD Asset Description in Asset Details page||VOD Asset Description Verification failed in Asset Details page",Local_Result);
		Local_Result = true;
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));	
		EF.Update_TC_Report();
		
		// *******************  Adding Watchlist item for TV Series for Second Episode ******************************************************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			EF.Update_Step_Report("Click on On Demand TV tab", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
		}
		else
		{
			EF.Update_Step_Report("Click on On Demand Drop down box", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
			EF.Update_Step_Report("Click on On Demand TV tab", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
		}
		
		Local_Result=client.swipeWhileNotFound("Down", 800, 2000, "NATIVE", ObjectCollection.get("TV_Asset_Posterart").toString(), 0, 1000, 3, true);
		//Local_Result=client.elementSwipeWhileNotFound("NATIVE", "//*[@id='new_media_scroller_container']", "Down", 100, 2000, "NATIVE", ObjectCollection.get("TV_Asset_Posterart").toString(), 1, 1000, 5, true);
		EF.Update_Step_Report("Click on On Demand TV Asset PosterArt", "Clicked sucessfully||Click failed", Local_Result);
		client.click("NATIVE", ObjectCollection.get("OnDemand_TV_Asset_SeriesEpisode1").toString(), 1, 1);
		EF.Update_Step_Report("Click on On Demand TV Asset Series Episode", "Clicked sucessfully||Click failed", Local_Result);
		//EF.Update_Step_Report("Click on On Demand TV Asset Series Episode", "Clicked successfully||Click failed",ST.NativeClick(ObjectCollection.get("OnDemand_TV_Asset_SeriesEpisode1").toString(), client));
		String SecondEpisodeName1 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_EpisodeName").toString(),0, client);
		String SubEpisodeName2 = (SecondEpisodeName1.split("\\("))[0];
		System.out.println(SubEpisodeName2);
		String SecondEpisodeDescription1 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_EpisodeDescription").toString(),0, client);
		System.out.println(SecondEpisodeDescription1);
		// Verify Watchlist Icon and click on the "Watchlist Icon
		EF.Update_Step_Report("Check for Watchlist Icon in Asset Details Page", "Watchlist icon is available||Watchlist icon is not available",
				ST.NativeElementFound(ObjectCollection.get("AssetDetails_Watchlist").toString(), client));
		EF.Update_Step_Report("Click on On Demand TV Asset Series Episode Watchlist Icon", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("AssetDetails_Watchlist").toString(), client));
		// Verify the Confirmation message for Added Watchlist
		String WatchlistSuccessfulMessage2 = ST.NativeGetElementText(ObjectCollection.get("AddWatchlist_Confirmation_Message").toString(),0, client);
		if (!(WatchlistSuccessfulMessage2.contains(SeriesTitle1)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Confirmation message for Added Watchlist", "Sucessfully Verified Confirmation message for Added Watchlist||Confirmation message Verification failed for Add Watchlist",Local_Result);
		Local_Result = true;
		EF.Update_Step_Report("Click on OK button in Add Watchlist Pop Over", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_AddWatchlist_OK").toString(), client));
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_TC_Report();
		
		// **************** Verification for the Added TV Series for first and Second Episodes under Watchlist Rollup Modal through My Library Screen *******************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			Local_Result=client.swipeWhileNotFound("Down", 800, 2000, "NATIVE", "//*[@accessibilityLabel='Watchlist']/ancestor::*[@class='UITableViewCellContentView']/descendant::*[@class='GTMFadeTruncatingLabel']", 0, 1000, 5, true);
			//Local_Result=client.elementSwipeWhileNotFound("NATIVE", "//*[@id='new_media_scroller_container']", "Down", 100, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller']//*[@id='new_media_scroller_title_container']/following-sibling::*/*/*[@id='new_media_scroller_poster_art']", 0, 1000, 5, true);
			EF.Update_Step_Report("Click on Added Watchlist PosterArt", "Clicked sucessfully||Click failed", Local_Result);
		}
		else
		{
			Local_Result=client.swipeWhileNotFound("Down", 800, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller' and @onScreen='true']//*[@id='new_media_scroller_title_container' and @onScreen='true']/following-sibling::*/*/*[@id='new_media_scroller_poster_art' and @onScreen='true']", 0, 1000, 5, true);
			//Local_Result=client.elementSwipeWhileNotFound("NATIVE", "//*[@id='new_media_scroller_container']", "Down", 100, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller']//*[@id='new_media_scroller_title_container']/following-sibling::*/*/*[@id='new_media_scroller_poster_art']", 0, 1000, 5, true);
			EF.Update_Step_Report("Click on Added Watchlist PosterArt", "Clicked sucessfully||Click failed", Local_Result);
		}
		
		// Verification for the Series title
		String SeriesTitle3 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Rollup_SeriesTitle").toString(),0, client);
		if (!(SeriesTitle3.contains(SeriesTitle3)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Series title in Watchlist Rollup Modal", "Sucessfully Verified Series title in Watchlist Rollup Modal||Series title Verification failed in Rollup Model",Local_Result);
		Local_Result = true;
		//Verification for Series Description in Watchlist Rollup Modal
		String SeriesDescription2 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Rollup_SeriesDescription").toString(),1, client);
		if (!(SeriesDescription2.contains(SeriesDescription2)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Series Description in Watchlist Rollup Modal", "Sucessfully Verified Series Description in Watchlist Rollup Modal||Series Description Verification failed in Rollup Model",Local_Result);
		Local_Result = true;
		//Verification for First Episode Name in Watchlist Rollup Modal
		String FirstEpisodeName7 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Rollup_SeasonEpisodename1").toString(),0, client);
		if (!(FirstEpisodeName7.contains(FirstEpisodeName7)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Season / Episode title in Watchlist Rollup Modal", "Sucessfully Verified Season / Episode title in Watchlist Rollup Modal||Season / Episode title Verification failed in Rollup Model",Local_Result);
		Local_Result = true;
		//Verification for Second Episode Name in Watchlist Rollup Modal
		String SecondEpisodeName2 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Rollup_SeasonEpisodename2").toString(),1, client);
		if (!(SecondEpisodeName2.contains(SecondEpisodeName2)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Season / Episode title in Watchlist Rollup Modal", "Sucessfully Verified Season / Episode title in Watchlist Rollup Modal||Season / Episode title Verification failed in Rollup Model",Local_Result);
		Local_Result = true;
		// Verification for the Delete button for Each Episode in Watchlist Rollup Modal
		EF.Update_Step_Report("Check for Delete Button for Each Episode", "Delete Button is available||Delete button is not available",
				ST.NativeElementFound(ObjectCollection.get("Watchlist_Rollup_FirstEpisodeDeletebutton").toString(), client));
		//EF.Update_Step_Report("Check for Delete Button for Each Episode", "Delete Button is available||Delete button is not available",ST.NativeElementFound(ObjectCollection.get("Watchlist_Rollup_SecondEpisodeDeletebutton").toString(), client));
		// Verification for the Delete All button in Watchlist Rollup Modal
		EF.Update_Step_Report("Check for Delete All Button in Watchlist Rollup Modal", "Delete All Button is available in Watchlist Rollup Modal||Delete All button is not available in Watchlist Rollup Modal",
				ST.NativeElementFound(ObjectCollection.get("Watchlist_Rollup_DeleteAllbutton").toString(), client));
		EF.Update_Step_Report("Click on Ok Button in Watchlist Rollup Modal", "Clicked Succesfully||Clicked failed",
				ST.NativeClick(ObjectCollection.get("Watchlist_Rollup_Okbutton").toString(), client));
		EF.Update_TC_Report();

		// **************** Verification for the Added TV Series for first and Second Episodes under Watchlist Rollup Modal through Watchlist Screen *******************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			
			EF.Update_Step_Report("Display Watchlist Shelf under My Library screen","Watchlist shelf should be available under My Library Screen||Watchlist shelf is not available under My Library Screen",
	                 client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist_BrowseAll").toString(), 0, 1000, 10, true));
	    }
		else
		{		
			Local_Result=client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller' and @onScreen='true']//*[@id='new_media_scroller_browse_all'and@onScreen='true']", 0, 1000, 6, true);
			EF.Update_Step_Report("Click on Watchlist BrowseAll button", "Clicked sucessfully||Click failed", Local_Result);
		}
		
		EF.Update_Step_Report("Click Added Watchlist Item in Watchlist Screen", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("Watchlist_Screen_AssetName").toString(), client));
		// Verification for the Series title
		String SeriesTitle9 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Rollup_SeriesTitle").toString(),0, client);
		if (!(SeriesTitle9.contains(SeriesTitle1)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Series title in Watchlist Rollup Modal", "Sucessfully Verified Series title in Watchlist Rollup Modal||Series title Verification failed in Rollup Model",Local_Result);
		Local_Result = true;
		//Verification for Series Description in Watchlist Rollup Modal
		String SeriesDescription9 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Rollup_SeriesDescription").toString(),0, client);
		if (!(SeriesDescription9.contains(SeriesDescription1)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Series Description in Watchlist Rollup Modal", "Sucessfully Verified Series Description in Watchlist Rollup Modal||Series Description Verification failed in Rollup Model",Local_Result);
		Local_Result = true;
		//Verification for First Episode Name in Watchlist Rollup Modal
		String FirstEpisodeName9 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Rollup_SeasonEpisodename1").toString(),0, client);
		if (!(FirstEpisodeName9.contains(FirstEpisodeName9)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Season / Episode title in Watchlist Rollup Modal", "Sucessfully Verified Season / Episode title in Watchlist Rollup Modal||Season / Episode title Verification failed in Rollup Model",Local_Result);
		Local_Result = true;
		//Verification for Second Episode Name in Watchlist Rollup Modal
		String SecondEpisodeName7 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Rollup_SeasonEpisodename2").toString(),1, client);
		if (!(SecondEpisodeName7.contains(SecondEpisodeName7)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Season / Episode title in Watchlist Rollup Modal", "Sucessfully Verified Season / Episode title in Watchlist Rollup Modal||Season / Episode title Verification failed in Rollup Model",Local_Result);
		Local_Result = true;
		// Verification for the Delete button for Each Episode in Watchlist Rollup Modal
		EF.Update_Step_Report("Check for Delete Button for Each Episode", "Delete Button is available||Delete button is not available",
				ST.NativeElementFound(ObjectCollection.get("Watchlist_Rollup_FirstEpisodeDeletebutton").toString(), client));
		// Verification for the Delete All button in Watchlist Rollup Modal
		EF.Update_Step_Report("Check for Delete All Button in Watchlist Rollup Modal", "Delete All Button is available in Watchlist Rollup Modal||Delete All button is not available in Watchlist Rollup Modal",ST.NativeElementFound(ObjectCollection.get("Watchlist_Rollup_DeleteAllbutton").toString(), client));
		EF.Update_Step_Report("Click on Ok Button in Watchlist Rollup Modal", "Clicked Succesfully||Clicked failed",
				ST.NativeClick(ObjectCollection.get("Watchlist_Rollup_Okbutton").toString(), client));
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		
	}
	
	public void Fn_Validate_AddWatchlist_IPVODAsset()
	{
		Boolean Local_Result = true;
				
		// ************************* Adding Watchlist Item for VOD Asset *************************************************************************
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			EF.Update_Step_Report("Check for On Demand TV option in Dropdown box", "On Demand TV option is available||On Demand TV is not available",
					ST.NativeElementFound(ObjectCollection.get("OnDemand_Movies").toString(), client));
			EF.Update_Step_Report("Click on On Demand TV tab", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_Movies").toString(), client));
			
		}
		else
		{
			EF.Update_Step_Report("Click on On Demand Drop down box", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
			EF.Update_Step_Report("Check for On Demand TV option in Dropdown box", "On Demand TV option is available||On Demand TV is not available",
					ST.NativeElementFound(ObjectCollection.get("OnDemand_Movies").toString(), client));
			EF.Update_Step_Report("Click on On Demand TV tab", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_Movies").toString(), client));
		}
		
		Local_Result=client.swipeWhileNotFound("Down", 800, 2000, "NATIVE", ObjectCollection.get("TV_Asset_Posterart").toString(), 0, 1000, 3, true);
		EF.Update_Step_Report("Click on On Demand Movie Asset PosterArt", "Clicked sucessfully||Click failed", Local_Result);
		
		String VODAssetName = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(),0, client);
		String VODAssetDescription = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesDescription").toString(),0, client);
		EF.Update_Step_Report("Check for Watchlist Icon in Asset Details Page", "Watchlist icon is available||Watchlist icon is not available",
				ST.NativeElementFound(ObjectCollection.get("AssetDetails_Watchlist").toString(), client));
		EF.Update_Step_Report("Click on VOD Asset Watchlist Icon", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("AssetDetails_Watchlist").toString(), client));
		
		String VODWatchlistSuccessfulMessage1 = ST.NativeGetElementText(ObjectCollection.get("AddWatchlist_Confirmation_Message").toString(),0, client);
		if (!(VODWatchlistSuccessfulMessage1.contains(VODAssetName)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Confirmation message for Added Watchlist", "Sucessfully Verified Confirmation message for Added Watchlist||Confirmation message Verification failed for Add Watchlist",Local_Result);
		Local_Result = true;
		EF.Update_Step_Report("Click on OK button in Add Watchlist Pop Over", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_AddWatchlist_OK").toString(), client));
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		EF.Update_TC_Report();
		
		// ********************* Verification for Added VOD Watchlist in My Library Screen under Watchlist Section *******************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			Local_Result=client.swipeWhileNotFound("Down", 800, 2000, "NATIVE", "//*[@accessibilityLabel='Watchlist']/ancestor::*[@class='UITableViewCellContentView']/descendant::*[@class='GTMFadeTruncatingLabel']", 0, 1000, 5, true);
			EF.Update_Step_Report("Click on Added Watchlist PosterArt", "Clicked sucessfully||Click failed", Local_Result);
		}
		else
		{
			Local_Result=client.swipeWhileNotFound("Down", 800, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller' and @onScreen='true']//*[@id='new_media_scroller_title_container' and @onScreen='true']/following-sibling::*/*/*[@id='new_media_scroller_poster_art' and @onScreen='true']", 0, 1000, 5, true);
			EF.Update_Step_Report("Click on Added Watchlist PosterArt", "Clicked sucessfully||Click failed", Local_Result);
		}
		
			
		String VODAssetName1 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(),0, client);
		if (!(VODAssetName1.contains(VODAssetName)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify VOD Asset Name in Asset Details page", "Sucessfully Verified VOD Asset Name in Asset Details page||VOD Asset Name Verification failed in Asset Details page",Local_Result);
		Local_Result = true;
		String VODAssetDescription1 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesDescription").toString(),0, client);
		if (!(VODAssetDescription1.contains(VODAssetDescription)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify VOD Asset Description in Asset Details page", "Sucessfully Verified VOD Asset Description in Asset Details page||VOD Asset Description Verification failed in Asset Details page",Local_Result);
		Local_Result = true;
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		EF.Update_TC_Report();
		
		// ********************* Verification for Added VOD Watchlist in Watchlist Screen *******************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			
			EF.Update_Step_Report("Display Watchlist Shelf under My Library screen","Watchlist shelf should be available under My Library Screen||Watchlist shelf is not available under My Library Screen",
	                 client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist_BrowseAll").toString(), 0, 1000, 10, true));
	    }
		else
		{		
			Local_Result=client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller' and @onScreen='true']//*[@id='new_media_scroller_browse_all'and@onScreen='true']", 0, 1000, 6, true);
			EF.Update_Step_Report("Click on Watchlist BrowseAll button", "Clicked sucessfully||Click failed", Local_Result);
		}

	
		EF.Update_Step_Report("Click Added Watchlist Item in Watchlist Screen", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("Watchlist_Screen_AssetName").toString(), client));
		
		String VODAssetName2 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(),0, client);
		if (!(VODAssetName2.contains(VODAssetName)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify VOD Asset Name in Asset Details page", "Sucessfully Verified VOD Asset Name in Asset Details page||VOD Asset Name Verification failed in Asset Details page",Local_Result);
		Local_Result = true;
		String VODAssetDescription2 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesDescription").toString(),0, client);
		if (!(VODAssetDescription2.contains(VODAssetDescription)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify VOD Asset Description in Asset Details page", "Sucessfully Verified VOD Asset Description in Asset Details page||VOD Asset Description Verification failed in Asset Details page",Local_Result);
		Local_Result = true;
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		
	}

	public void Fn_Validate_DeleteWatchlist_IPVODAsset()

	{
		Boolean Local_Result = true;
		int nCount, nCount1, nCount2;	
		
		// ********************* Deleting the Added IPVOD Watchlist through Watchlist Screen *******************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			
			EF.Update_Step_Report("Display Watchlist Shelf under My Library screen","Watchlist shelf should be available under My Library Screen||Watchlist shelf is not available under My Library Screen",
	                 client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist_BrowseAll").toString(), 0, 1000, 10, true));
	    }
		else
		{		
			Local_Result=client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller' and @onScreen='true']//*[@id='new_media_scroller_browse_all'and@onScreen='true']", 0, 1000, 6, true);
			EF.Update_Step_Report("Click on Watchlist BrowseAll button", "Clicked sucessfully||Click failed", Local_Result);
		}


		EF.Update_Step_Report("Click on Edit button in Watchlist Screen", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("Watchlist_edit").toString(), client));
		EF.Update_Step_Report("Check for Delete Button in Watchlist items", "Delete Button is available in Watchlist items||Delete button is not available in Watchlist items",
				ST.NativeElementFound(ObjectCollection.get("Watchlist_edit_delete").toString(), client));
		
		EF.Update_Step_Report("Click on Delete button in Watchlist Screen", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("Watchlist_edit_delete").toString(), client));
		EF.Update_TC_Report();
		
		// ******************** Verification for deleted Watchlist item in Watchlist Screen ******************************************************
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			if(ST.NativeElementFound(ObjectCollection.get("Watchlist_posterart_image_disabled").toString(), client))
			{}
			else
				EF.Update_Step_Report("Check for the Deleted IPVOD watchlit under Watchlist Screen", "Watchlist deleted successfully under Watchlist screen||Watchlist is not deleted successfully under Watchlist screen",true);
			EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
			EF.Update_TC_Report();
		}
		else
		{
			String Temp_Var1 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Screen_Textmessage").toString(),0, client);
			if (!(Temp_Var1.contains("Tap the Watchlist pin on any On Demand program to add it here")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text Message under Watchlist Section in My Library Screen", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		}
		
		// ******************** Verification for deleted Watchlist item in My Library Screen Under Watchlist Section ****************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		
		EF.Update_Step_Report("Display Watchlist Shelf under My Library screen","Watchlist shelf should be available under My Library Screen||Watchlist shelf is not available under My Library Screen",
                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist").toString(), 0, 1000, 10, false));
		String Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Watchlist_Textmessage").toString(),0, client);
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			if (!(Temp_Var.contains("Browse On Demand to add Movies and TV Shows")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text Message under Watchlist Section in My Library Screen", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
		}
		else
		{
			if (!(Temp_Var.contains("Browse On Demand to download Movies and TV Shows")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text Message under Watchlist Section in My Library Screen", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
		}
	}
	
	

	public void Fn_Validate_AddWatchlist_MovieAsset()

	{
		Boolean Local_Result = true;
		//int nCount, nCount1, nCount2, nCount3, nCount4, nCount5;	
		
		// ************************* Adding Watchlist Item for Movie Asset *************************************************************************
		EF.Update_Step_Report("Navigate to On Demand screen", "Sucessfully Navigated||Navigation failed",this.Fn_Com_Navigate("Home_OnDemand"));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			EF.Update_Step_Report("Check for On Demand TV option in Dropdown box", "On Demand TV option is available||On Demand TV is not available",
					ST.NativeElementFound(ObjectCollection.get("OnDemand_Movies").toString(), client));
			EF.Update_Step_Report("Click on On Demand TV tab", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_Movies").toString(), client));
		}
		else
		{
			EF.Update_Step_Report("Click on On Demand Drop down box", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
			EF.Update_Step_Report("Check for On Demand TV option in Dropdown box", "On Demand TV option is available||On Demand TV is not available",
					ST.NativeElementFound(ObjectCollection.get("OnDemand_Movies").toString(), client));
			EF.Update_Step_Report("Click on On Demand TV tab", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_Movies").toString(), client));
		}
		
		Local_Result=client.swipeWhileNotFound("Down", 800, 2000, "NATIVE", ObjectCollection.get("TV_Asset_Posterart").toString(), 0, 1000, 3, true);
		EF.Update_Step_Report("Click on On Demand Movie Asset PosterArt", "Clicked sucessfully||Click failed", Local_Result);
		
		String VODAssetName5 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesTitle").toString(),0, client);
		String VODAssetDescription5 = ST.NativeGetElementText(ObjectCollection.get("OnDemand_TV_Asset_SeriesDescription").toString(),0, client);
		System.out.println(VODAssetDescription5);
		EF.Update_Step_Report("Check for Watchlist Icon in Asset Details Page", "Watchlist icon is available||Watchlist icon is not available",
				ST.NativeElementFound(ObjectCollection.get("AssetDetails_Watchlist").toString(), client));
		EF.Update_Step_Report("Click on VOD Asset Watchlist Icon", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("AssetDetails_Watchlist").toString(), client));
		String VODWatchlistSuccessfulMessage2 = ST.NativeGetElementText(ObjectCollection.get("AddWatchlist_Confirmation_Message").toString(),0, client);
		if (!(VODWatchlistSuccessfulMessage2.contains(VODAssetName5)))
		{
			Local_Result = false;
		}
		EF.Update_Step_Report("Verify Confirmation message for Added Watchlist", "Sucessfully Verified Confirmation message for Added Watchlist||Confirmation message Verification failed for Add Watchlist",Local_Result);
		Local_Result = true;
		EF.Update_Step_Report("Click on OK button in Add Watchlist Pop Over", "Clicked sucessfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_AddWatchlist_OK").toString(), client));
		EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		if(ST.NativeElementFound(ObjectCollection.get("OnDemand_Pop_BodyText").toString(), client))
		{
			client.sendText("{ESC}");
		}
		
	}

	public void Fn_Validate_DeleteWatchlist_MovieAsset()

	{
		Boolean Local_Result = true;
		//int nCount, nCount1, nCount2, nCount3, nCount4, nCount5;	
		
		// ************************* Deleting the Added Watchlist item through Asset details page ****************************************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			
			EF.Update_Step_Report("Display Watchlist Shelf under My Library screen","Watchlist shelf should be available under My Library Screen||Watchlist shelf is not available under My Library Screen",
	                 client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist_BrowseAll").toString(), 0, 1000, 10, true));
	    }
		else
		{		
			Local_Result=client.swipeWhileNotFound("Down", 200, 2000, "NATIVE", "//*[@id='home_fragment_watchlist_media_scroller' and @onScreen='true']//*[@id='new_media_scroller_browse_all'and@onScreen='true']", 0, 1000, 6, true);
			EF.Update_Step_Report("Click on Watchlist BrowseAll button", "Clicked sucessfully||Click failed", Local_Result);
		}

		
		EF.Update_Step_Report("Check for Edit button in watchlist screen", "Edit button is available||Edit button is not available",
				ST.NativeElementFound(ObjectCollection.get("Watchlist_edit").toString(), client));
		EF.Update_Step_Report("Check for Coverart in watchlist screen", "Coverart is available||Coverart is not available",
				ST.NativeElementFound(ObjectCollection.get("Watchlist_Posterart_image").toString(), client));

		//Click on PosterArt
		EF.Update_Step_Report("Click on Added Watchlist Posterart in Watchlist Screen", "Clicked successfully||Click failed",
				ST.NativeClick(ObjectCollection.get("Watchlist_Posterart_image").toString(), client));
		
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			
			EF.Update_Step_Report("Check for Watchlist Icon in Asset Details Page", "Watchlist icon is available||Watchlist icon is not available",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_Delete_WatchlistIcon").toString(), client));
			EF.Update_Step_Report("Click on VOD Asset Watchlist Icon", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("AssetDetails_Delete_WatchlistIcon").toString(), client));
			EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
			EF.Update_TC_Report();
		}
		else
		{
			EF.Update_Step_Report("Check for Watchlist Icon in Asset Details Page", "Watchlist icon is available||Watchlist icon is not available",
					ST.NativeElementFound(ObjectCollection.get("AssetDetails_Watchlist").toString(), client));
			EF.Update_Step_Report("Click on VOD Asset Watchlist Icon", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("AssetDetails_Watchlist").toString(), client));
			String VODWatchlistSuccessfulMessage3 = ST.NativeGetElementText(ObjectCollection.get("AddWatchlist_Confirmation_Message").toString(),0, client);
			if (!(VODWatchlistSuccessfulMessage3.contains("Removed Watchlist")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify Confirmation message for Deleted Watchlist", "Sucessfully Verified Confirmation message for Deleted Watchlist||Confirmation message Verification failed for Deleted Watchlist",Local_Result);
			Local_Result = true;
			EF.Update_Step_Report("Click on OK button in Delete Watchlist Pop Over", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_AddWatchlist_OK").toString(), client));
			EF.Update_Step_Report("Click on OK button in Delete Watchlist Pop Over", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_AddWatchlist_OK").toString(), client));
			EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
			EF.Update_TC_Report();
		}
		
		// ******************** Verification for deleted VOD Asset in Watchlist Screen ****************************
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			EF.Update_Step_Report("Check for the Deleted IPVOD watchlit under Watchlist Screen", "Watchlist deleted successfully under Watchlist screen||Watchlist is not deleted successfully under Watchlist screen",
					!(ST.NativeElementFound(ObjectCollection.get("Watchlist_posterart_image_disabled").toString(), client)));
			EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
			EF.Update_TC_Report();
		}
		else
		{
			String Temp_Var1 = ST.NativeGetElementText(ObjectCollection.get("Watchlist_Screen_Textmessage").toString(),0, client);
			if (!(Temp_Var1.contains("Tap the Watchlist pin on any On Demand program to add it here")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text Message under Watchlist Section in My Library Screen", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
			EF.Update_Step_Report("Click on back button ", "Clicked successfully||Click failed",
					ST.NativeClick(ObjectCollection.get("OnDemand_BackButton").toString(), client));
		}
		
		// ******************** Verification for deleted Watchlist item in My Library Screen Under Watchlist Section ****************************
		EF.Update_Step_Report("Navigate to My Library", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_MyLibrary"));
		
		EF.Update_Step_Report("Display Watchlist Shelf under My Library screen","Watchlist shelf should be available under My Library Screen||Watchlist shelf is not available under My Library Screen",
                client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("Home_Watchlist").toString(), 0, 1000, 10, false));
		String Temp_Var = ST.NativeGetElementText(ObjectCollection.get("Home_Watchlist_Textmessage").toString(),0, client);
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			if (!(Temp_Var.contains("Browse On Demand to add Movies and TV Shows")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text Message under Watchlist Section in My Library Screen", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
		}
		else
		{
			if (!(Temp_Var.contains("Browse On Demand to download Movies and TV Shows")))
			{
				Local_Result = false;
			}
			EF.Update_Step_Report("Verify text Message under Watchlist Section in My Library Screen", "Sucessfully Verified||Verification failed",Local_Result);
			Local_Result = true;
		}
	}
	public void Fn_SportZone()
	{
		//Auto_SportZone_009
		this.Fn_Com_Navigate("Home_SportZone");
		EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
		EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
		EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
		EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
		EF.Update_Step_Report("On navigating to SportZone the Asset content should be listed", "Asset Content is listed down||Asset content is not listed", ST.NativeClick(ObjectCollection.get("SportZone_AssetContent").toString(), client));
		EF.Update_Step_Report("On Clicking the Asset content the Standard Asset details page should be displayed", "Standard Asset details page is displayed||Standard Asset details page is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_Remindme").toString(), client));
		ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
		EF.Update_TC_Report();
		//Auto_SportZone_010
		EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
		EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
		EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
		EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
		EF.Update_Step_Report("Tuzz Rating should be displayed in asset content", "Tuuz rating is displayed||Tuuz rating is not yet displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_ThuuzRating").toString(), client));
		EF.Update_Step_Report("League Name should be displayed in asset content", "League name is displayed||League name is not yet displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_League&FullTeamName").toString(), client));
		EF.Update_Step_Report("Team Logo should be displayed in asset content", "Team logo is displayed||Team Logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_TeamLogo").toString(), client));
		EF.Update_Step_Report("Team Name should be displayed in asset content", "Team Name is displayed||Team Name is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_League&FullTeamName").toString(), client));
		EF.Update_Step_Report("Game time should be displayed in asset content", "Game time is displayed||Game time is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_In-GameTime").toString(), client));
		EF.Update_Step_Report("Game date should be displayed in asset content", "Game date is displayed||Game date is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_In-GameDate").toString(), client));
		EF.Update_Step_Report("Network logo should be displayed in asset content", "Network logo is displayed||Network logo is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_NetworkLogo").toString(), client));
		EF.Update_Step_Report("Network call sign should be displayed in asset content", "Network call sign is displayed||Network call sign is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_NetworkCallSign").toString(), client));
		EF.Update_TC_Report();
		//Auto_SportZone_011
		EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
		EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
		EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
		EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
		if(Current_OS.equalsIgnoreCase("IOS"))
		{
			EF.Update_Step_Report("Game on TV should be displayed in the SportZone landing page", "Game on TV is displayed in the sportzone landing page||Game on TV is not displayed in the sportzone landing page", ST.NativeElementFound(ObjectCollection.get("SportZone_GamesOnTV").toString(), client));
			EF.Update_Step_Report("Programs should be displayed in the SportZone landing page", "Programs is displayed in the sportzone landing page||Programs is not displayed in the sportzone landing page", ST.NativeElementFound(ObjectCollection.get("SportZone_Programs").toString(), client));
		}
		else
		{
			ST.NativeClick(ObjectCollection.get("SportZone_ActionSpinner").toString(), client);
			EF.Update_Step_Report("Game on TV should be displayed in the SportZone landing page", "Game on TV is displayed in the sportzone landing page||Game on TV is not displayed in the sportzone landing page", ST.NativeElementFound(ObjectCollection.get("SportZone_GamesOnTV").toString(), client));
			EF.Update_Step_Report("Programs should be displayed in the SportZone landing page", "Programs is displayed in the sportzone landing page||Programs is not displayed in the sportzone landing page", ST.NativeElementFound(ObjectCollection.get("SportZone_Programs").toString(), client));
			ST.NativeClick(ObjectCollection.get("SportZone_GamesOnTV").toString(), client);
		}
		EF.Update_TC_Report();
		//Auto_SportZone_012
		EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
		EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
		EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
		EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
		EF.Update_Step_Report("Verify whether the unentitled assets are displayed in Asset container", "Unentitled asset is identified||Unentitled asset is not identified", client.swipeWhileNotFound("Down", 100, 2000, "NATIVE", ObjectCollection.get("SportZone_UnentitledAsset").toString(), 0, 1000, 5, false));
		EF.Update_TC_Report();
		//Auto_SportZone_013
		EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
		EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
		EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
		EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
		EF.Update_Step_Report("The Asset details time should be listed in Asset container", "The Sports timing is displayed||The Sports timing is not displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_In-GameTime").toString(), client));
		EF.Update_TC_Report();
		//Auto_SportZone_014
		EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
		EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
		EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
		EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
		EF.Update_Step_Report("The Asset details time should be listed in Ascending order", "The Sports timing is listed in Ascending order||The Sports timing is not listed in Ascending order", Fn_SportZoneTimecalc());
		EF.Update_TC_Report();
		//Auto_SportZone_015
		EF.Update_Step_Report("The App must be downloaded and installed in the device", "The App is downloaded and installed||The App is not downloaded and installed", true);
		EF.Update_Step_Report("User should eb able to Login Successfully", "User is logged in Successfully||User is not Logged in Successfully", true);
		EF.Update_Step_Report("Login screen should be displayed", "Login Screen is displayed||Login Screen is not yet displayed", true);
		EF.Update_Step_Report("User should be navigated to SportZone", "User is navigated to SportZone||User is not navigated to SportZone", true);
		EF.Update_Step_Report("The Spoilers should be displayed in the SportZone landing page", "Spolier is getting displayed||Spoiler is not getting displayed", ST.NativeElementFound(ObjectCollection.get("SportZone_SpoilersON").toString(), client));
	}
	
	 public void videoPlayer()
	    {
		 this.Fn_Com_Navigate("Home_LiveTV");	
		 boolean bFlag = false;
	    	try
	    	{
	    		 ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
	    		 EF.Update_Step_Report("The user should be able to navigate sucessfuly to the Live TV", "User is navigated to the Live TV page||User is not navigated to the Live TV page", true);
	    		 if(ST.NativeElementFound(ObjectCollection.get("Video_player_AsstContent").toString(), client))
	    		 {
	    			 EF.Update_Step_Report("Live Streaming assets should be displayed", "Live streaming asset is displayed||Live Streaming asset is not displayed", true);
		    		 //ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
		    		 ST.NativeClick(ObjectCollection.get("Video_player_AsstContent").toString(), client);
		    		 
		    		 //On Error Occuring any error 
		    		 if(ST.NativeElementFound(ObjectCollection.get("Login_ErrorPopUp").toString(), client))
		    		 {
		    			 ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
		    		 }
		    		 else
		    		 {
		    			 client.waitForElementToVanish("NATIVE", ObjectCollection.get("Common_ProgressBar").toString(), 0, 100000);
			    		 EF.Update_Step_Report("On Clicking asset content user should be able to redirect to the live streaming asset", "Live streaming is displayed||Live streaming is not displayed", ST.NativeElementFound(ObjectCollection.get("Video_player_PlayButton").toString(), client));
			    		 if(ST.NativeElementFound(ObjectCollection.get("Video_player_DoneButton").toString(), client))
			    		 {
			    			 bFlag = ST.NativeClick(ObjectCollection.get("Video_player_DoneButton").toString(), client);
			    			 ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
			    		 }
			    		 else
			    		 {
			    			 ST.NativeClick(ObjectCollection.get("Video_player_UIContent").toString(), client);
			    			 bFlag = ST.NativeClick(ObjectCollection.get("Video_player_DoneButton").toString(), client);
			    			 ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
			    		 }
			    		 if(ST.NativeElementFound(ObjectCollection.get("Common_ProgressBar").toString(), client)||ST.NativeElementFound(ObjectCollection.get("Video_player_AsstContent").toString(), client))
			    			 EF.Update_Step_Report("On clicking Done button user should be redirected Live TV asset content page", "User navigated to Live TV asset page||User is not navigated to Live TV asset page", true);
			    		 else
			    		 {
			    			 ST.NativeClick(ObjectCollection.get("Video_player_UIContent").toString(), client);
			    			 bFlag = ST.NativeClick(ObjectCollection.get("Video_player_DoneButton").toString(), client);
			    			 EF.Update_Step_Report("On clicking Done button user should be redirected Live TV asset content page", "User navigated to Live TV asset page||User is not navigated to Live TV asset page", true);
			    		 }
			    		 
			    		 EF.Update_TC_Report();
			    		 // Auto_VideoPlayer_002
			    		 ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
			    		 EF.Update_Step_Report("The user should be able to navigate sucessfuly to the SportZone", "User is navigated to the SportZone page||User is not navigated to the SportZone page", true);
			    		 bFlag = client.swipeWhileNotFound("Down", 0, 2000, "NATIVE", ObjectCollection.get("Video_player_FamilyEducPanel").toString(), 0, 1000, 5, false);
			    		 EF.Update_Step_Report("Live Streaming assets should be displayed", "Live streaming asset is displayed||Live Streaming asset is not displayed", ST.NativeClick(ObjectCollection.get("Video_player_Family&EduAssetContent").toString(), client));
			    		 ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
			    		 EF.Update_Step_Report("On Clicking asset content user should be able to redirect to the live streaming asset", "Live streaming is displayed||Live streaming is not displayed", ST.NativeElementFound(ObjectCollection.get("Video_player_PlayButton").toString(), client));
			    		 if(ST.NativeElementFound(ObjectCollection.get("Video_player_MoreInfo").toString(), client))
			    		 {
			    			 bFlag = ST.NativeClick(ObjectCollection.get("Video_player_MoreInfo").toString(), client);
			    		 }
			    		 else
			    		 {
			    			 ST.NativeClick(ObjectCollection.get("Video_player_UIContent").toString(), client);
			    			 bFlag = ST.NativeClick(ObjectCollection.get("Video_player_MoreInfo").toString(), client);
			    		 }
			    		 EF.Update_Step_Report("In flyover the Asset title should be displayed", "Asset title is displayed||Assset title is not displayed", ST.NativeElementFound(ObjectCollection.get("Video_player_AssetTitle").toString(), client));
			    		 EF.Update_TC_Report();
			    		 // Auto_VideoPlayer_003
			    		 EF.Update_Step_Report("The user should be able to navigate sucessfuly to the SportZone", "User is navigated to the SportZone page||User is not navigated to the SportZone page", true);
			    		 EF.Update_Step_Report("Live Streaming assets should be displayed", "Live streaming asset is displayed||Live Streaming asset is not displayed", true);
			    		 //ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
			    		 EF.Update_Step_Report("On Clicking asset content user should be able to redirect to the live streaming asset", "Live streaming is displayed||Live streaming is not displayed", true);
			    		 if(ST.NativeElementFound(ObjectCollection.get("Video_player_MoreInfo").toString(), client))
			    		 {
			    			 bFlag = ST.NativeClick(ObjectCollection.get("Video_player_MoreInfo").toString(), client);
			    		 }
			    		 else
			    		 {
			    			 ST.NativeClick(ObjectCollection.get("Video_player_UIContent").toString(), client);
			    			 bFlag = ST.NativeClick(ObjectCollection.get("Video_player_MoreInfo").toString(), client);
			    		 }
			    		 EF.Update_Step_Report("In flyover the Episode title should be displayed", "Episode title is displayed||Episode title is not displayed", ST.NativeElementFound(ObjectCollection.get("Video_player_EpisodeTitle").toString(), client));
			    		 EF.Update_TC_Report();
		    		 }
	    		 }
	    		 else
	    			 EF.Update_Step_Report("Live Streaming assets should be displayed", "Live streaming asset is displayed||Live Streaming asset is not displayed", false);
	    		 
	    		 // Auto_VideoPlayer_004
	    		 EF.Update_Step_Report("The user should be able to navigate sucessfuly to the Live TV", "User is navigated to the Live TV||User is not navigated to the Live TV page", this.Fn_Com_Navigate("Home_LiveTV"));
	    		 EF.Update_Step_Report("Live Streaming assets should be displayed", "Live streaming asset is displayed||Live Streaming asset is not displayed", true);
	    		 //ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
	    		 EF.Update_Step_Report("On Clicking asset content user should be able to redirect to the live streaming asset", "Live streaming is displayed||Live streaming is not displayed", true);
	    		 if(ST.NativeElementFound(ObjectCollection.get("Video_player_MoreInfo").toString(), client))
	    		 {
	    			 bFlag = ST.NativeClick(ObjectCollection.get("Video_player_MoreInfo").toString(), client);
	    		 }
	    		 else
	    		 {
	    			 ST.NativeClick(ObjectCollection.get("Video_player_UIContent").toString(), client);
	    			 bFlag = ST.NativeClick(ObjectCollection.get("Video_player_MoreInfo").toString(), client);
	    		 }
	    		 EF.Update_Step_Report("In flyover the Asset Description should be displayed", "Asset Description is displayed||Asset Description is not displayed", ST.NativeElementFound(ObjectCollection.get("Video_player_AssetDescription").toString(), client));
	    		 EF.Update_TC_Report();
	    		 // Auto_VideoPlayer_005
	    		 EF.Update_Step_Report("Verify whether the HD content is displayed in the asset detail", "HD content is displayed||HD content is not displayed", ST.NativeElementFound(ObjectCollection.get("Video_player_HDindicator").toString(), client));
	    	}
	    	catch(Exception Ex)
	    	{
	    		System.err.println("Live TV player error : "+Ex.getCause());
	    		System.err.println("Live TV player error Message : "+Ex.getMessage()); 
	    	}
	    }
	 
	 /*==================================================================================================================================
		 * Channel Line Up validation
		 ==================================================================================================================================*/
		/* public void ChannelLineUp() throws BiffException, IOException
	  	 {
	  		String strChLineUp = "C:\\Users\\edwin_paul\\workspace\\CTVA_Automation_SmokeSuit\\Data\\channelLineUp.xls";
	  		
	  		//String strChLineUp = Globals.BinPath.replace("bin", "Data\\channelLineUp.xlsx");
	  		
	  		CL.readExcelfromChannelLineup(strChLineUp);
	  		String strWorkBookName = "C:\\Users\\edwin_paul\\workspace\\CTVA_Automation_SmokeSuit\\Data\\GuidXL.xls";
	  		//String strWorkBookName = Globals.BinPath.replace("bin", "Data\\GuideResp_04092014_142818.xls");
	  		CL.readExcelfromGuideResp(strWorkBookName);
	  		StringBuilder sb = new StringBuilder();

	  		String[] MapKeys=new String[m1.size()];

	  		Iterator itr = m1.keySet().iterator();
	  		int intC=0;
	  		while(itr.hasNext())
	  		{
	  			String key1 = (String) itr.next();

	  			MapKeys[intC]=key1.trim();
	  			if(intC<m1.size())
	  			{
	  				intC++;
	  			}
	  		}

	  		String[] GuideMapKeys=new String[GuideRes_m1.size()];

	  		Iterator itr1 = GuideRes_m1.keySet().iterator();
	  		int intC1=0;
	  		while(itr1.hasNext())
	  		{
	  			String key2 = (String) itr1.next();

	  			GuideMapKeys[intC1]=key2.trim();
	  			if(intC1<GuideRes_m1.size())
	  			{
	  				intC1++;
	  			}
	  		}

//	  		System.out.println("-------------------------------------------------------------------------------------");
//	  		System.out.println("Channel Lineup Size: " +m1.size());
//	  		System.out.println("-------------------------------------------------------------------------------------");
//	  		System.out.println("Guide Response Size: " +GuideRes_m1.size());
//	  		System.out.println("-------------------------------------------------------------------------------------");
	  		int notMatchingCount = 0;

	  		for(int i=0;i<GuideMapKeys.length;i++)
	  		{


	  			String strGuideKey1=GuideMapKeys[i].trim();

	  			String strGuideChannelLineup1=GuideRes_m1.get(strGuideKey1).toString();
	  			if(m1.containsKey(strGuideKey1))
	  			{

	  			}
	  			else
	  			{
	  				notMatchingCount++;

//	  				System.out.println("Channel present in Guide but not in Market :" + strGuideKey1);
	  				missingCh1.put(strGuideKey1, strGuideChannelLineup1);

	  			}
	  		}
//	  		System.out.println("-------------------------------------------------------------------------------------");
//	  		System.out.println("The Number of Channels which present in Guide but not in Market :" + notMatchingCount);
//	  		System.out.println("The Number of channels which present in guide as well as in market :" + (GuideRes_m1.size()-notMatchingCount));
//	  		System.out.println("-------------------------------------------------------------------------------------");
	  		notMatchingCount = 0; 
	  		int keysNotinGuide = 0;

//	  		System.out.println("MapKeys.Length" + MapKeys.length);
//	  		System.out.println("GuideRes_m1.size" + GuideRes_m1.size());
	  		int loopCount = 0;
	  		for(int i=0;i<MapKeys.length;i++)
	  		{


	  			String strKey=MapKeys[i].trim();

	  			String strChannelLineup=m1.get(strKey).toString();


	  			//System.out.println(strKey + "--- "+ strValue);       
	  			if(GuideRes_m1.containsKey(strKey))
	  			{
	  				loopCount++;
	  				availableKeys.put(strKey, strChannelLineup);
//	  				if(strKey.trim().contains("954"))
//	  					System.out.println("Yes I am 954");
	  				String strGuideResp=GuideRes_m1.get(strKey).toString();
	  				String strGuideRespNC=GuideRes_m2.get(strKey).toString();
	  				if(strChannelLineup.contains(strGuideResp) || strGuideResp.contains(strChannelLineup))
	  				{
	  					resultMap1.put(strKey, "Pass");
	  					//System.out.println("present. Channel Number: " +strKey);
	  				}else if(strChannelLineup.contains(strGuideRespNC))
	  				{
	  					resultMap1.put(strKey, "Pass");  
	  					//System.out.println("present. Channel Number: " +strKey);
	  				}else{
	  					//if(strKey.trim().contains("954"))
	  						//System.out.println("Yes I am here" + strKey);
	  					String tmpChannelLineup = strChannelLineup.replace("/", " ");
	  					String tmpGuideResp = strGuideResp.replace(" / ", " ").replace("/", " ");
	  					String [] a=tmpChannelLineup.split(" ");
	  					String[] b=tmpGuideResp.split(" ");
	  					String[] c=strGuideRespNC.split(" ");

	  					if(tmpChannelLineup.toLowerCase().contains(b[0].toLowerCase()) || tmpChannelLineup.toLowerCase().contains(c[0].toLowerCase()))
	  					{
	  						resultMap1.put(strKey, "Pass");
	  						//System.out.println("present. Channel Number: " +strKey);
	  					}
	  					else if(b[0].toLowerCase().contains(a[0].toLowerCase()) || a[0].toLowerCase().contains(b[0].toLowerCase()))
	  					{
	  						resultMap1.put(strKey, "Pass");   
	  						//System.out.println("present. Channel Number: " +strKey);
	  					}
	  					else if(c[0].toLowerCase().contains(a[0].toLowerCase()) || a[0].toLowerCase().contains(c[0].toLowerCase())){
	  						resultMap1.put(strKey, "Pass");   
	  						//System.out.println("present. Channel Number: " +strKey);
	  					}
	  					else if(strChannelLineup.contains("-"))
	  					{
	  						String strChannelLineup1=strChannelLineup.replace("-", "");
	  						if(strChannelLineup1.toLowerCase().contains(a[0].toLowerCase()) || 
	  								strChannelLineup1.toLowerCase().contains(b[0].toLowerCase()) ||
	  								strChannelLineup1.toLowerCase().contains(c[0].toLowerCase()) )
	  						{
	  							resultMap1.put(strKey, "Pass");  
	  							//System.out.println("present. Channel Number: " +strKey);
	  						}
	  					}
	  					else
	  					{
	  						if(strKey.trim().contains("954"))
	  							System.out.println("Yes I am here 1" + strKey);
	  						String marketChannelAbb = "";
	  						boolean IsValid = false;
	  						String tmpAcrChannelLineup = strChannelLineup.replace("/", " ");
	  						String tmpAbrGuideResp = strGuideResp.replace("/", " ");
	  						marketChannelAbb = WordUtils.initials(tmpAcrChannelLineup);


	  						if(tmpAbrGuideResp.toUpperCase().contains(marketChannelAbb.toUpperCase()) || 
	  								marketChannelAbb.toUpperCase().contains(tmpAbrGuideResp.split(" ")[0].toUpperCase())
	  								|| marketChannelAbb.toUpperCase().contains(tmpAbrGuideResp.toUpperCase())){
	  							if(marketChannelAbb.length()>1) 
	  							{
	  								resultMap1.put(strKey, "Pass");
	  							}
	  							else
	  							{
	  								notMatchingCount++;
	  								sb.append("Market Channel Number: " + strKey + " Programming Service: " +strChannelLineup + " -> Guide Network: "+ strGuideResp + "\n");
	  								resultMap1.put(strKey, "Fail");
	  							}
	  						}

	  						else if((strGuideResp.contains("HD") && strChannelLineup.contains("HD"))  || (strGuideResp.contains("PPVP") && strChannelLineup.contains("PPV"))) {

	  							String tmpWrdChannelLineup = strChannelLineup.replace("/", " ");
	  							String tmpWrdGuideResp = strGuideResp.replace("/", " ");
	  							for(String guideName : tmpWrdGuideResp.split(" ")) 
	  							{
	  								//System.out.println("Token Guide: " +guideName);
	  								for (String marketName : tmpWrdChannelLineup.split(" ")) 
	  								{
	  									//System.out.println("Token Market: " +marketName);
	  									if(!marketName.toUpperCase().contains("HD") || !guideName.toUpperCase().contains("HD")) 
	  									{
	  										if (guideName.toUpperCase().contains(marketName.toUpperCase()) || marketName.toUpperCase().contains(guideName.toUpperCase())) 
	  										{	
	  											IsValid = true;
	  											resultMap1.put(strKey, "Pass");
	  										}
	  									}
	  								}
	  							}
	  							if(!IsValid) {
	  								notMatchingCount++;
	  								sb.append("Market Channel Number: " + strKey + " Programming Service: " +strChannelLineup + " -> Guide Network: "+ strGuideResp + "\n");
	  								resultMap1.put(strKey, "Fail");
	  							}
	  							IsValid = false;
	  						}
	  						else {
	  							//if(strKey.trim().contains("954"))
	  								//System.out.println("Yes I am here");
	  							notMatchingCount++;
	  							sb.append("Market Channel Number: " + strKey + " Programming Service: " +strChannelLineup + " -> Guide Network: "+ strGuideResp + "\n");
	  							resultMap1.put(strKey, "Fail");
	  						}
	  					}
	  				}
	  			}
	  			else{
	  				//if(strKey.trim().contains("954"))
	  					//System.out.println("Yes I am here");
	  				//System.out.println("Channels present in Market Channel Line up but not in Guide Response. Key: " +strKey);
	  				missingCh2.put(strKey, strChannelLineup);
	  				keysNotinGuide++;
	  				notMatchingCount++;
	  			}


	  		}
//	  		System.out.println("LoopCount:" + loopCount);
//	  		System.out.println("AvailableKeys Count: "+ availableKeys.size());
	  		
	  		String[] avKeys=new String[availableKeys.size()];

	  		Iterator itravKeys = availableKeys.keySet().iterator();
	  		int avkeycount=0;
	  		while(itravKeys.hasNext())
	  		{
	  			String key1 = (String) itravKeys.next();

	  			avKeys[avkeycount]=key1;
	  			if(avkeycount<availableKeys.size())
	  			{
	  				avkeycount++;
	  			}
	  		}
	  		System.out.println("Array Size: " + avKeys.length);
	  		
	  		for (int c=0; c<avKeys.length; c++) {
	  			String avKey=avKeys[c];
	  			String strChannelLineupName=GuideRes_m1.get(avKey).toString();
	  			String guideChannelName = m1.get(avKey).toString();
	  			if(!resultMap1.containsKey(avKey))
	  				System.out.println("One missing channel: " + avKey + " Guide Name : "+ guideChannelName + " / Market Channel Name: " + strChannelLineupName);
	  		
	  		}
	  		
	  		
	  		System.out.println("-------------------------------------------------------------------------------------");
	  		System.out.println("The Number of Channels available in Market but not in Guide :" +  keysNotinGuide);
	  		System.out.println("The Number of channels which present in Market as well as in Guide :"+ (m1.size()-keysNotinGuide));
	  		System.out.println("-------------------------------------------------------------------------------------");
	  		System.out.println("The Channels not matching the Market Channel line up are as follow");
	  		System.out.println("-------------------------------------------------------------------------------------");
	  		System.out.println("Guide Channels not matching the Market Channel line up :" + (notMatchingCount - keysNotinGuide )+ "\n" +sb.toString());
	  		System.out.println("-------------------------------------------------------------------------------------");
	  		System.out.println("Guide Channels in compliance with the Market Channel line up :" + (m1.size() - notMatchingCount));
	  		System.out.println("-------------------------------------------------------------------------------------");
	  		try {
	  			CL.FnWriteXL();
	  			CL.FnWriteXL1();
	  			//FnWriteXL2();
	  		}
	  		catch(Exception ex)
	  		{
	  			System.err.println("Channel Line UP :"+ex.getCause()+"/ Message :"+ex.getMessage());
	  		}
	  		EF.Update_Step_Report("Channel Line up validation against St.Louis account", "Channel line up validation is done||Channel line up validation is done", true);
	  	}
		 
		 public void FnValidateChannelLineup(String UserName, String[] MapKeys, boolean UseExistingGuideResponse) throws BiffException, IOException {
				String F="", serviceUrl = "",symphonyService="", baseUrl="", passWord="", authenticationToken = "";;
				boolean isValid = false;
				GuideValidations guideValidations = new GuideValidations();
				String 	channelLineUpMapFile = "C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\UserChannelLineupMap.xlsx", 
						channelLineupFile = "C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\channelLineUp.xlsx"; 
				try{
					if (!UseExistingGuideResponse) {
						serviceUrl = "https://symphony.charter.com/symphony/auth/";
						symphonyService = "symphony/services/v1/" ;
						baseUrl = "https://symphony.charter.com/" ;
						passWord = "Ctva1000";
						CTVA_Request_Helper helper = new CTVA_Request_Helper(UserName, passWord); 
						authenticationToken = helper.GetAuthenticationToken(serviceUrl); 
						if(authenticationToken!=null && !authenticationToken.isEmpty()) {
							String sourceFile = "C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\Guide_Template.xlsx"; 
							Calendar c = Calendar.getInstance();
							SimpleDateFormat s=new SimpleDateFormat("ddMMyyyy_ssmmHH");
							String t=s.format(c.getTime());
							String targetFile = "C:\\Users\\edwin_paul\\workspace\\filter_data\\src\\Datasheet\\GuideResp_"+t+".xlsx";
							F=FnCreateXLSGuideResponse(sourceFile, targetFile); 
							helper.ExecuteCTVARequest(baseUrl, symphonyService, authenticationToken, HttpVerb.GET,F);
						} 
						else 
						{
							log.error("Authentication was not successful");
							F = null;
						}
					}
					else
					{
						F = "D:/Vel/filter_data/src/Datasheet/GuideResp_06082014_310818.xlsx";
					}
					
					if (F.isEmpty() || F==null)
						F = "D:/Vel/filter_data/src/Datasheet/GuideResp_06082014_310818.xlsx";
					
					isValid = guideValidations.ValidateChannelLineup(UserName, channelLineUpMapFile, channelLineupFile, F, MapKeys);
				}
				catch(SQLException ex)
				{
					System.out.print(ex.getMessage());
					System.out.print(ex.getCause());
				}
			}*/
		
//		public String FnCreateXLSGuideResponse(String SourceFile, String GuideFileName) throws BiffException, IOException
//		{
//				
//			m1.put("JSONFileName", GuideFileName);
//			try 
//			{
//				File outputWorkbook = new File(SourceFile);
//				File file = new File(GuideFileName);
//				/*Workbook strWorkBook = Workbook.getWorkbook(new File(f));  
//			   WritableWorkbook wrk=Workbook.createWorkbook(file,strWorkBook);		   
//			  // file1.copy(strWorkBook);
//
//			//   WritableWorkbook wrk = Workbook.createWorkbook(outputWorkbook,strWorkBook);
//			   wrk.copy(strWorkBook);
//
//			   wrk.write();
//			   wrk.close();*/
//				FileUtils.copyFile(outputWorkbook, file);
//			}catch(Exception e)
//			{
//				System.out.println(e.getMessage());
//				System.out.println(e.getCause());
//
//			}
//			return GuideFileName;
//		}
		
		//********************Filter Framework******************************************************************
		
		public void Fn_Single_Filter(String filterName)
		{
			EF.Get_Channel_List(filterName);
			if(filterName.equalsIgnoreCase("HD"))
				filterName = "Guide_Filter_list_HDonly";
			if(filterName.equalsIgnoreCase("Locals"))
				filterName = "Guide_Filter_list_Locals";
			if(filterName.equalsIgnoreCase("Favourite"))
				filterName = "Guide_Filter_list_Favorite";
			if(filterName.equalsIgnoreCase("LiveTV"))
				filterName = "Guide_Filter_list_LiveTV";
			if(filterName.equalsIgnoreCase("Premium"))
				filterName = "Guide_Filter_list_Premium";
			if(filterName.equalsIgnoreCase("FamilyandEducation"))
				filterName = "Guide_Filter_list_Family&Education";
			
			this.Fn_Com_Navigate("Home_Guide");
			client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(),0,300000);
			if(client.waitForElement("NATIVE", ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(),0,10000))
			{
				ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("Guide_Filter_list_FilterList").toString(), client))
				{
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 10000);
					//Clicking on filter option to display the filter list 
					ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
//					client.elementSwipeWhileNotFound("NATIVE", ObjectCollection.get("Guide_Filter_list_FilterList").toString(), "Down", 0, 2000, 
//							"NATIVE",ObjectCollection.get(filterName).toString() , 0, 1000, 5, true);
					client.click("NATIVE", ObjectCollection.get(filterName).toString(), 0, 1);
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 1000);
					//Clicking on filter to check whether the applied filter is enabled or not
					ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
					int count = client.getElementCount("NATIVE", ObjectCollection.get("Guide_Filter_SelectedCount").toString());
					if(count==1)
					{
						client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 1000);
						EF.Update_Step_Report("Filter should be applied properly","Filter Applied properly||Filter not applied properly",true);
						this.Fliter_Channel_Validation();
					}
					else
					{
						EF.Update_Step_Report("Filter should be applied properly","Filter Applied properly||Filter not applied properly",false);
					}
				}
					
			}
			else
			{
				EF.Update_Step_Report("Filter button should be available", "Filter button is available||Filtr button is not available",false);
			}
		}
		public void Fn_Double_Filter(String filterName1,String filterName2)
		{
			EF.Get_Channel_List(filterName1+"-"+filterName2);
			if(filterName1.equalsIgnoreCase("HD"))
				filterName1 = "Guide_Filter_list_HDonly";
			if(filterName1.equalsIgnoreCase("Locals"))
				filterName1 = "Guide_Filter_list_Locals";
			if(filterName1.equalsIgnoreCase("Favourite"))
				filterName1 = "Guide_Filter_list_Favorite";
			if(filterName1.equalsIgnoreCase("LiveTV"))
				filterName1 = "Guide_Filter_list_LiveTV";
			if(filterName1.equalsIgnoreCase("Premium"))
				filterName1 = "Guide_Filter_list_Premium";
			if(filterName1.equalsIgnoreCase("FamilyandEducation"))
				filterName1 = "Guide_Filter_list_Family&Education";
			//Filter Name 2
			if(filterName2.equalsIgnoreCase("HD"))
				filterName2 = "Guide_Filter_list_HDonly";
			if(filterName2.equalsIgnoreCase("Locals"))
				filterName2 = "Guide_Filter_list_Locals";
			if(filterName2.equalsIgnoreCase("Favourite"))
				filterName2 = "Guide_Filter_list_Favorite";
			if(filterName2.equalsIgnoreCase("LiveTV"))
				filterName2 = "Guide_Filter_list_LiveTV";
			if(filterName2.equalsIgnoreCase("Premium"))
				filterName2 = "Guide_Filter_list_Premium";
			if(filterName2.equalsIgnoreCase("FamilyandEducation"))
				filterName2 = "Guide_Filter_list_Family&Education";
			System.out.println("filter1 :"+filterName1+"filter2 :"+filterName2);
			this.Fn_Com_Navigate("Home_Guide");
			client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(),0,300000);
			if(client.waitForElement("NATIVE", ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(),0,10000))
			{
				ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("Guide_Filter_list_FilterList").toString(), client))
				{
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 10000);
					//Clicking on filter option to display the filter list to choose 1st Filter
					ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
//					client.elementSwipeWhileNotFound("NATIVE", ObjectCollection.get("Guide_Filter_list_FilterList").toString(), "Down", 0, 2000, 
//							"NATIVE",ObjectCollection.get(filterName1).toString() , 0, 1000, 5, false);
					client.click("NATIVE", ObjectCollection.get(filterName1).toString(), 0, 1);
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 10000);
					//Clicking on filter option to display the filter list to choose 2nd filter
					ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
//					client.elementSwipeWhileNotFound("NATIVE", ObjectCollection.get("Guide_Filter_list_FilterList").toString(), "Down", 0, 2000, 
//							"NATIVE",ObjectCollection.get(filterName2).toString() , 0, 1000, 5, false);
					client.click("NATIVE", ObjectCollection.get(filterName2).toString(), 0, 1);
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 10000);
					//Clicking on filter to check whether the applied filter is enabled or not
					ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
					int count = client.getElementCount("NATIVE", ObjectCollection.get("Guide_Filter_SelectedCount").toString());
					//int count = 2;
					if(count>2)
					{
						client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 1000);
						EF.Update_Step_Report("Filter should be applied properly","Filter Applied properly||Filter not applied properly",true);
						this.Fliter_Channel_Validation();
					}
					else
					{
						EF.Update_Step_Report("Filter should be applied properly","Filter Applied properly||Filter not applied properly",false);
					}
				}
					
			}
			else
			{
				EF.Update_Step_Report("Filter button should be available", "Filter button is available||Filtr button is not available",false);
			}
		}
		public void Fn_Triple_Filter(String filterName1,String filterName2,String filterName3)
		{
			EF.Get_Channel_List(filterName1);
			if(filterName1.equalsIgnoreCase("HD"))
				filterName1 = "Guide_Filter_list_HDonly";
			if(filterName1.equalsIgnoreCase("Locals"))
				filterName1 = "Guide_Filter_list_Locals";
			if(filterName1.equalsIgnoreCase("Favourite"))
				filterName1 = "Guide_Filter_list_Favorite";
			if(filterName1.equalsIgnoreCase("LiveTV"))
				filterName1 = "Guide_Filter_list_LiveTV";
			if(filterName1.equalsIgnoreCase("Premium"))
				filterName1 = "Guide_Filter_list_Premium";
			if(filterName1.equalsIgnoreCase("FamilyandEducation"))
				filterName1 = "Guide_Filter_list_Family&Education";
			//Filter Name 2
			if(filterName2.equalsIgnoreCase("HD"))
				filterName2 = "Guide_Filter_list_HDonly";
			if(filterName2.equalsIgnoreCase("Locals"))
				filterName2 = "Guide_Filter_list_Locals";
			if(filterName2.equalsIgnoreCase("Favourite"))
				filterName2 = "Guide_Filter_list_Favorite";
			if(filterName2.equalsIgnoreCase("LiveTV"))
				filterName2 = "Guide_Filter_list_LiveTV";
			if(filterName2.equalsIgnoreCase("Premium"))
				filterName2 = "Guide_Filter_list_Premium";
			if(filterName2.equalsIgnoreCase("FamilyandEducation"))
				filterName2 = "Guide_Filter_list_Family&Education";
			//Filter Name 3
			if(filterName3.equalsIgnoreCase("HD"))
				filterName3 = "Guide_Filter_list_HDonly";
			if(filterName3.equalsIgnoreCase("Locals"))
				filterName3 = "Guide_Filter_list_Locals";
			if(filterName3.equalsIgnoreCase("Favourite"))
				filterName3 = "Guide_Filter_list_Favorite";
			if(filterName3.equalsIgnoreCase("LiveTV"))
				filterName3 = "Guide_Filter_list_LiveTV";
			if(filterName3.equalsIgnoreCase("Premium"))
				filterName3 = "Guide_Filter_list_Premium";
			if(filterName3.equalsIgnoreCase("FamilyandEducation"))
				filterName3 = "Guide_Filter_list_Family&Education";
			
			this.Fn_Com_Navigate("Home_Guide");
			
			client.waitForElementToVanish("NATIVE", ObjectCollection.get("Home_Loading").toString(),0,300000);
			if(client.waitForElement("NATIVE", ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(),0,10000))
			{
				ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
				if(ST.NativeElementFound(ObjectCollection.get("Guide_Filter_list_FilterList").toString(), client))
				{
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 1000);
					//Clicking on filter option to display the filter list to choose 1st Filter
					ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
					client.elementSwipeWhileNotFound("NATIVE", ObjectCollection.get("Guide_Filter_list_FilterList").toString(), "Down", 0, 2000, 
							"NATIVE",ObjectCollection.get(filterName1).toString() , 0, 1000, 5, false);
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 1000);
					//Clicking on filter option to display the filter list to choose 2nd filter
					ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
					client.elementSwipeWhileNotFound("NATIVE", ObjectCollection.get("Guide_Filter_list_FilterList").toString(), "Down", 0, 2000, 
							"NATIVE",ObjectCollection.get(filterName2).toString() , 0, 1000, 5, false);
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 1000);
					//Clicking on filter option to display the filter list to choose 3rd filter
					ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
					client.elementSwipeWhileNotFound("NATIVE", ObjectCollection.get("Guide_Filter_list_FilterList").toString(), "Down", 0, 2000, 
							"NATIVE",ObjectCollection.get(filterName3).toString() , 0, 1000, 5, false);
					client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 1000);
					//Clicking on filter to check whether the applied filter is enabled or not
					ST.NativeClick(ObjectCollection.get("Guide_Filter_btn_FilterDD").toString(), client);
					int count = client.getElementCount("NATIVE", ObjectCollection.get("Guide_Filter_SelectedCount").toString());
					if(count>3)
					{
						client.waitForElementToVanish("NATIVE",ObjectCollection.get("Guide_Filter_list_FilterList").toString(),0, 1000);
						EF.Update_Step_Report("Filter should be applied properly","Filter Applied properly||Filter not applied properly",true);
						//this.Fliter_Channel_Validation();
					}
					else
					{
						EF.Update_Step_Report("Filter should be applied properly","Filter Applied properly||Filter not applied properly",false);
					}
				}
			}
			else
			{
				EF.Update_Step_Report("Filter button should be available", "Filter button is available||Filtr button is not available",false);
			}
		}
		
		public void AdultContentCheck()
		{
			EF.Update_Step_Report("Check whether there is any adult content is available in the application", "Adult content is not present in the App - Refer Channel.xls||Adult content is displayed in App - Refer Channel.xls", true);
		}
		
		public void clearGuideFilter()
		{
			this.Fn_Com_Navigate("Home_MyLibrary");
			this.Fn_Com_Navigate("Home_Guide");
		}
		
		/*
		 * Metadata sample validation
		 */
		public void Metadata()
		{
			EF.Metadata_Validation();
		}
		/*
		 * To find Showtime asset
		 */
		public void findShowTimeAsset()
		{
			String strTitle = ST.NativeGetElementText(ObjectCollection.get("Home_TitleBar").toString(), 0, client);
			if(strTitle.equalsIgnoreCase("On Demand"))
			{
				int nCount = client.getElementCount("NATIVE", ObjectCollection.get("ShowtimeFolderCollectionView").toString());
				for(int n=0; n<nCount; n++)
				{
					client.click("NATIVE", ObjectCollection.get("ShowtimeFolderCollectionView").toString(), n, 1);
					String strNwName = ST.NativeGetElementText(ObjectCollection.get("NetworkCollectionTitle").toString(), 0, client);
					if(strNwName.equalsIgnoreCase("showtime"))
					{
						break;
					}
					else
						ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
				}
			}
		}
		
		public void Rest2homescreen()
		{
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				while(ST.NativeElementFound(ObjectCollection.get("BackButton").toString(), client))
				{
					ST.NativeClick(ObjectCollection.get("BackButton").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client))
						ST.NativeClick(ObjectCollection.get("Login_PopUp_Okbtn").toString(), client);
				}
			}
			EnjoyingAppHandler();
		}
		
		public void EnjoyingAppHandler()
		{
			if(ST.NativeElementFound(ObjectCollection.get("EnjoyingAppPopOver").toString(), client))
			{
				ST.NativeClick(ObjectCollection.get("EnjoyingAppUnCheckedbox").toString(), client);
				ST.NativeClick(ObjectCollection.get("EnjoyingAppNoButton").toString(), client);
				ST.NativeClick(ObjectCollection.get("EnjoyingAppNoButton").toString(), client);
			}
		}
		
		public void Fn_Validate_FlyoverAssetDetails()
		{
			Boolean Local_Result = true;
			
			EF.Update_Step_Report("Navigate to On Demand", "Sucessfully navigated||Navigation failed", this.Fn_Com_Navigate("Home_OnDemand"));
			EF.Update_Step_Report("Select the watch on iPad or TV section", "User able to select the preferd section||User is not able to select the preferd section", this.Fn_Com_WatchOn("iPad"));
			
			// For iOS
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
			}
			// For Android
			else
			{
				EF.Update_Step_Report("Click on On Demand Dropdown box in On Demand Screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
				EF.Update_Step_Report("Click on TV option in On Deamand Dropdown Box", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_TV").toString(), client));
			}
			
			EF.Update_Step_Report("Click on any Poster Art", "Clicked sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("OnDemand_TV_PosterArt").toString(), client));
			if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_ProgramTitle").toString(), client))
			{
				client.waitForElement("NATIVE", ObjectCollection.get("AssetDetails_EpisodePanellist").toString(), 0, 7000);
				if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_EpisodePanellist").toString(), client))
				{
					EF.Update_Step_Report("Verify whether the episode lists are getting displayed", "Episode lists are displayed||Episode list are not loaded", true);
					ST.NativeClick(ObjectCollection.get("AssetDetails_EpisodePanellist").toString(), client);
					if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_ProgramTitle").toString(), client))
					{
						if(ST.NativeElementFound(ObjectCollection.get("AssetDetails_PlayIcon").toString(), client))
						{
							EF.Update_Step_Report("On clicking episode user should be redirected to asset details page", "User redirected to asset details page||User not redirected to asset details page", ST.NativeClick(ObjectCollection.get("AssetDetails_PlayIcon").toString(), client));
							if(ST.NativeElementFound(ObjectCollection.get("PlayButtonPopOver").toString(), client))
							{
								ST.NativeClick(ObjectCollection.get("PlayButtonResume").toString(), client);
							}
							ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
							EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_NavigationBar").toString(), client));
							EF.Update_Step_Report("Click on More Info in Asset Details Screen", "Clicked sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_MoreInfo").toString(), client));
							// 58************ Verify that the Program Title displays in the Flyover Asset Detail. ********************************************
							EF.Update_Step_Report("Check for Program Title in the Flyover Asset Detail", "Program Title is available in the Flyover Asset Detail||Program Title is not available in the Flyover Asset Detail", ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_Programtitle").toString(), client));
							EF.Update_TC_Report();
							
							// 59************* Verify that the Episode Title of the Selected Asset is displaying in the Asset Detail.*************************
							EF.Update_Step_Report("Check for Episode Title in the Flyover Asset Detail", "Episode Title is available in the Flyover Asset Detail||Episode Title is not available in the Flyover Asset Detail", ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_Episodetitle").toString(), client));
							EF.Update_TC_Report();
							
							// 60************* The Episode Details should display in the Fly over Asset Detail during live streaming and appear as (S ##, E ##) **********
							EF.Update_Step_Report("Check for Episode Details in the Flyover Asset Detail", "Episode Details is available in the Flyover Asset Detail||Episode Details is not available in the Flyover Asset Detail", ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_EpisodeDetails").toString(), client));
							EF.Update_TC_Report();
							
							// 61************* The Season Details should display in the Fly over Asset Detail during live streaming and appear as (S ##, E ##). **********
							EF.Update_Step_Report("Check for Season Details in the Flyover Asset Detail", "Season Details is available in the Flyover Asset Detail||Season Details is not available in the Flyover Asset Detail", ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_SeasonDetails").toString(), client));
							EF.Update_TC_Report();
							
							// 62************* Verify that the Asset Description displays in the Asset Detail of the selected Asset.  ************************************
							EF.Update_Step_Report("Check for Assegt Description in the Flyover Asset Detail", "Asset Description is available in the Flyover Asset Detail||Asset Description is not available in the Flyover Asset Detail", ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_AssegtDescription").toString(), client));
							EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_NavigationBar").toString(), client));
							EF.Update_Step_Report("Click on Done button", "User should be navigated back to asset details page||Click failed", ST.NativeClick(ObjectCollection.get("VideoPlayer_Btn_Done").toString(), client));
							EF.Update_TC_Report();
						}
						else
						{
							EF.Update_Step_Report("On clicking episode user should be redirected to asset details page", "User redirected to asset details page||User not redirected to asset details page", false);
							EF.Update_TC_Report();
							EF.Update_Step_Report("On clicking episode user should be redirected to asset details page", "User redirected to asset details page||User not redirected to asset details page", false);
							EF.Update_TC_Report();
							EF.Update_Step_Report("On clicking episode user should be redirected to asset details page", "User redirected to asset details page||User not redirected to asset details page", false);
							EF.Update_TC_Report();
							EF.Update_Step_Report("On clicking episode user should be redirected to asset details page", "User redirected to asset details page||User not redirected to asset details page", false);
							EF.Update_TC_Report();
							EF.Update_Step_Report("On clicking episode user should be redirected to asset details page", "User redirected to asset details page||User not redirected to asset details page", false);
							EF.Update_TC_Report();
						}
					}
				}
				else
				{
					EF.Update_Step_Report("Verify whether the episode lists are getting displayed", "Episode lists are displayed||Episode list are not loaded", false);
					EF.Update_TC_Report();
					EF.Update_Step_Report("Verify whether the episode lists are getting displayed", "Episode lists are displayed||Episode list are not loaded", false);
					EF.Update_TC_Report();
					EF.Update_Step_Report("Verify whether the episode lists are getting displayed", "Episode lists are displayed||Episode list are not loaded", false);
					EF.Update_TC_Report();
					EF.Update_Step_Report("Verify whether the episode lists are getting displayed", "Episode lists are displayed||Episode list are not loaded", false);
					EF.Update_TC_Report();
					EF.Update_Step_Report("Verify whether the episode lists are getting displayed", "Episode lists are displayed||Episode list are not loaded", false);
					EF.Update_TC_Report();
				}
			}
			else
			{
				EF.Update_Step_Report("Verify whether user navigated to asset details page", "User is navigated to asset details page||User is not navigated to asset details page", false);
			}
			this.Rest2homescreen();
			// For iOS
			if(Current_OS.equalsIgnoreCase("IOS"))
			{
				EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_Tab_Movie").toString(), client));
			}
			else //Android
			{
				EF.Update_Step_Report("Click on On Demand Dropdown box in On Demand Screen", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_DropDown").toString(), client));
				EF.Update_Step_Report("Click on TV option in On Deamand Dropdown Box", "Clicked successfully||Click failed",
						ST.NativeClick(ObjectCollection.get("OnDemand_Movies").toString(), client));
			}
			
			// Clicking for Movie Asset
			client.swipeWhileNotFound("Down", 500, 2000, "NATIVE", ObjectCollection.get("OnDemand_TV_PosterArt").toString(), 0, 1000, 5, false);
			EF.Update_Step_Report("Click on Movie Poster Art", "Clicked sucessfully||Click failed", ST.NativeClick(ObjectCollection.get("OnDemand_TV_PosterArt").toString(), client));
			EF.Update_Step_Report("Click on Play Icon in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("AssetDetails_PlayIcon").toString(), client));
			if(ST.NativeElementFound(ObjectCollection.get("PlayButtonPopOver").toString(), client))
			{
				ST.NativeClick(ObjectCollection.get("PlayButtonResume").toString(), client);
			}
			ST.NativeElementWaitTillVanish(ObjectCollection.get("Common_ProgressBar").toString(), client);
			EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_NavigationBar").toString(), client));
			EF.Update_Step_Report("Click on More Info in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_MoreInfo").toString(), client));
			
			// 63************* Verify that the HD indicator displays in the Asset Detail *******************************************************************
			EF.Update_Step_Report("Check for HD Indicator in the Flyover Asset Detail", "HD Indicator is available in the Flyover Asset Detail||HD Indicator is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_HDIndicator").toString(), client));
			EF.Update_TC_Report();
			
			// 64************** Verify that the NEW Indicator (If applicable) displays in the Flyover Asset Detail. ***************************************
			EF.Update_Step_Report("Check for NEW Indicator in the Flyover Asset Detail", "NEW Indicator is available in the Flyover Asset Detail||NEW Indicator is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_NEWIndicator").toString(), client));
			EF.Update_TC_Report();
			
			// 65************** Verify that the Cover Art of the selected Asset display in the Flyover Asset Detail. ***************************************
			EF.Update_Step_Report("Check for CoverArt of the seleted Asset in the Flyover Asset Detail", "CoverArt of the seleted Asset is available in the Flyover Asset Detail||CoverArt of the seleted Asset is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_CoverArt").toString(), client));
			EF.Update_TC_Report();
			
			// 66************** Verify that the Time Slot displays in the Flyover Asset Detail.   **********************************************************
			EF.Update_Step_Report("Check for Time Slot in the Flyover Asset Detail", "Time Slot is available in the Flyover Asset Detail||Time Slot is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_TimeSlot").toString(), client));
			EF.Update_TC_Report();
			
			// 67************** Verify that the Time Duration of Selected Asset Appears in the Flyover Asset Detail ***************************************
			EF.Update_Step_Report("Check for Time Duration in the Flyover Asset Detail", "Time Duration is available in the Flyover Asset Detail||Time Duration is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_TimeDuration").toString(), client));
			EF.Update_TC_Report();
			
			// 68************** Verify that the Rotten Tomatoes Critics (Tomatoes Icon) Ratings appear in the Flyover Asset Detail.************************
			EF.Update_Step_Report("Check for Rotten Tomatoes Critics (Tomatoes Icon) Ratings in the Flyover Asset Detail", "Rotten Tomatoes Critics (Tomatoes Icon) Ratings is available in the Flyover Asset Detail||Rotten Tomatoes Critics (Tomatoes Icon) Ratings is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_RottenTomatoesIcon").toString(), client));
			EF.Update_TC_Report();
			
			// 69*************** Verify that the Rotten Tomatoes Critics (Popcorn Icon) Ratings appear in the Flyover Asset Detail.********************
			EF.Update_Step_Report("Check for Rotten Tomatoes Critics (Tomatoes Icon) Ratings in the Flyover Asset Detail", "Rotten Tomatoes Critics (Tomatoes Icon) Ratings is available in the Flyover Asset Detail||Rotten Tomatoes Critics (Tomatoes Icon) Ratings is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_RottenPopcornIcon").toString(), client));
			EF.Update_TC_Report();
			
			// 70**************** Verify that the Movie Ratings display in the Flyover Asset Details  ***************************************************
			EF.Update_Step_Report("Check for Movie Ratings in the Flyover Asset Detail", "Movie Ratings is available in the Flyover Asset Detail||Movie Ratings is not available in the Flyover Asset Detail",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_MovieRatings").toString(), client));
			EF.Update_TC_Report();
			
			// 71***************** Verify that the TV Sub-Ratings (FV, Y7, D, L, V, S) display in the Flyover Asset Detail. *****************************
			EF.Update_Step_Report("Check for TV Sub-Ratings in the Flyover Asset Detail", "TV Sub-Ratings is available in the Flyover Asset Detail||TV Sub-Ratings is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_TVSubRatings").toString(), client));
			EF.Update_TC_Report();
			
			// 72**************** Verify that the Parental Guidelines display in the Flyover Asset Detail Bar ********************************************
			EF.Update_Step_Report("Check for Parental Guidelines in the Flyover Asset Detail", "Parental Guidelines is available in the Flyover Asset Detail||Parental Guidelines is not available in the Flyover Asset Detail",
					client.swipeWhileNotFound("Down", 300, 2000, "NATIVE", ObjectCollection.get("Flyover_AssetDetails_ParentalGuidelines").toString(), 0, 1000, 4, false));
			
			EF.Update_TC_Report();
			
			EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_NavigationBar").toString(), client));
			
			// 73*************** Verify whether Cover Art of the Cast & Crew is displayed in the Flyover Cast & Crew Panel. *****************************
			EF.Update_Step_Report("Click on Cast & Crew in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_CastCrew_Section").toString(), client));
			EF.Update_Step_Report("Check for Cover Art of the Cast & Crew in the Flyover Asset Detail", "Cover Art of the Cast & Crew is available in the Flyover Asset Detail||Cover Art of the Cast & Crew is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_CastCrew_CoverArt").toString(), client));
			EF.Update_TC_Report();
			
			// 74**************** Verify whether Cast Names are display in the underneath the Cast & Crew Cover Art. *************************************
			EF.Update_Step_Report("Check for Cast Names are display in the underneath the Cast & Crew Cover Art in the Flyover Asset Detail", "Cast Names are display in the underneath the Cast & Crew Cover Art is available in the Flyover Asset Detail||Cast Names are display in the underneath the Cast & Crew Cover Art is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_CastCrew_CastName").toString(), client));
			EF.Update_TC_Report();
			
			// 75*************** When the Cast Cover Art is tapped, the Mini Biography Pop-over should display.  ******************************************
			EF.Update_Step_Report("Click on Cast Cover Art in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_CastCrew_CoverArt").toString(), client));
			EF.Update_Step_Report("Check for Mini Biography Pop-over in the Flyover Asset Detail", "Mini Biography Pop-over is available in the Flyover Asset Detail||Mini Biography Pop-over is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_CastCrew_MiniBiography").toString(), client));
			EF.Update_TC_Report();
			
			// 76*************** Verify whether Cast Name is display Mini Biography Pop-over should display.  ******************************************
			EF.Update_Step_Report("Check for Cast Name is display Mini Biography Pop-over in the Flyover Asset Detail", "Cast Name is display Mini Biography Pop-over is available in the Flyover Asset Detail||Cast Name is display Mini Biography Pop-over is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_CastCrew_MiniBiography_CastName").toString(), client));
			EF.Update_TC_Report();
			
			// 77**************** Cast and Crew Mini biography should display a List of other movies that the selected Cast has featured in.  ************
			EF.Update_Step_Report("Check for Other Movies is display Mini Biography Pop-over in the Flyover Asset Detail", "Other Movies is display Mini Biography Pop-over is available in the Flyover Asset Detail||Other Movies is display Mini Biography Pop-over is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_CastCrew_MiniBiography_OtherMovies").toString(), client));
			EF.Update_TC_Report();
			EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_NavigationBar").toString(), client));
			
			// 78*************** Verify whether Cover Art of the Phote is displayed in the Flyover Cast & Crew Panel. *****************************
			EF.Update_Step_Report("Click on Photes Section in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_Photes_Section").toString(), client));
			EF.Update_Step_Report("Check for Cover Art of the Photes in the Flyover Asset Detail", "Cover Art of the Photes is available in the Flyover Asset Detail||Cover Art of the Photes is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_Photes_CoverArt").toString(), client));
			EF.Update_TC_Report();
			
			// 79*************** Verify whether Cover Art of the Phote is displayed in the Flyover Cast & Crew Panel. *****************************
			EF.Update_Step_Report("Click on Photes CoverArt in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_Photes_CoverArt").toString(), client));
			EF.Update_Step_Report("Check for Enlarged Phote on tapping the Photes CoverArt in the Flyover Asset Detail", "Enlarged Phote is available in the Flyover Asset Detail||Enlarged Phote is not available in the Flyover Asset Detail",
					ST.NativeElementFound(ObjectCollection.get("Flyover_AssetDetails_Photes_CoverArt_Enlarged").toString(), client));
			EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_NavigationBar").toString(), client));
			
			EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("Flyover_AssetDetails_NavigationBar").toString(), client));
			EF.Update_Step_Report("Click on Navigation Bar in Asset Details Screen", "Clicked sucessfully||Click failed",
					ST.NativeClick(ObjectCollection.get("VideoPlayer_Btn_Done").toString(), client));
			this.Rest2homescreen();
		}
		
		
		public void ValidateStreaming()
		{
			if(this.Fn_Com_Navigate("Home_Guide"))
			{
				/********* TODO ********/
			}
		}
}



	
	

	
