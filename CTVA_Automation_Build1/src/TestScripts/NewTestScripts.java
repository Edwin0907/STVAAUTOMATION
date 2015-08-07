package TestScripts;
import org.testng.annotations.Test;

import bsh.ParseException;

public class NewTestScripts extends AppNewFunctions
{
	
	AppNewFunctions AF = new AppNewFunctions();
	
	/*=====================================================================================================
	* Author: Chandhini_A01
	* Module : Home
	* Test Cases : Auto_Home_FavoritesEditPopOver_001,Auto_Home_FavoritesEditPopOver_002
	=====================================================================================================*/
	public  void Auto_Home_FavoritesEditPopOver_001()
	{
 		try
 		{ 
 			Fn_Favorite_PopOver_Contents();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_Home_FavoritesEditPopOver_001()");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
	/*=====================================================================================================
	* Author: Chandhini_A01
	* Module : Home
	* Test Cases : Auto_Home_FavoritesEditPopOver_003
	=====================================================================================================*/
	
	public  void  Auto_Home_FavoritesEditPopOver_003()
	{
 		try
 		{ 
 			Fn_Favorite_Filter();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_Home_FavoritesEditPopOver_005()");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
	/*=====================================================================================================
		* Author: Chandhini
		* Module : Home_FavoritesEditPopOver
		* Test Cases :Auto_Home_FavoritesEditPopOver_004,005
	====================================================================================================*/
		@Test(groups = {"seetest"})
	    public  void Auto_Home_FavoritesEditPopOver_004()
		{
			try
	 		{ 	
				Fn_Favorites_ClearFilter();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Fn_Favorites_ClearFilter()");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}	
		}
	 	
 	/*=====================================================================================================
 		* Author: Chandhini
 		* Module : Home_FavoritesEditPopOver
 		* Test Cases :Auto_Home_FavoritesEditPopOver_006,007
 	====================================================================================================*/
 		@Test(groups = {"seetest"})
 	    public  void Auto_Home_FavoritesEditPopOver_006()
 		{

 			try
 	 		{ 	
 				Fn_Home_FavoritesPanel_ColdStartCheck();
 	 		}
 	 		catch(Exception e)
 	    	{
 	    		System.err.println("Error in Fn_Home_FavoritesPanel_ColdStartCheck()");
 				System.err.println("	Message : "+e.getMessage());
 				System.err.println("	Cause : "+e.getCause());
 				e.printStackTrace();
 				tearDown();
 	    	}	
	}
 	/*=====================================================================================================
 		* Author: Chandhini
 		* Module : Home_FavoritesEditPopOver
 		* Test Cases :Auto_Home_FavoritesEditPopOver_008
 	====================================================================================================*/
 		@Test(groups = {"seetest"})
 	    public  void Auto_Home_FavoritesEditPopOver_008()

 		{
 			try
 	 		{ 	
 				Fn_Favorites_GreyedOutIcon();
 	 		}
 	 		catch(Exception e)
 	    	{
 	    		System.err.println("Error in Fn_Favorites_GreyedOutIcon()");
 				System.err.println("	Message : "+e.getMessage());
 				System.err.println("	Cause : "+e.getCause());
 				e.printStackTrace();
 				tearDown();
 	    	}	
	}
		 
 	/*=====================================================================================================
 		* Author: Chandhini
 		* Module : Home_FavoritesEditPopOver
 		* Test Cases :Auto_Home_FavoritesEditPopOver_009,010
 	====================================================================================================*/
 		@Test(groups = {"seetest"})
 	    public  void Auto_Home_FavoritesEditPopOver_009()

 		{
 			try
 	 		{ 	
 				Fn_Home_FavoritePanel_AssetPopOverCheck();
 	 		}
 	 		catch(Exception e)
 	    	{
 	    		System.err.println("Error in Fn_Home_Favorites_HelpText()");
 				System.err.println("	Message : "+e.getMessage());
 				System.err.println("	Cause : "+e.getCause());
 				e.printStackTrace();
 				tearDown();
 	    	}	
	}
 		/*=====================================================================================================
 		* Author: Chandhini_A01
 		* Module : Home
 		* Test Cases : 	Auto_Home_NavigationDrawer_001
 		=====================================================================================================*/
	 		public  void  Auto_Home_NavigationDrawer_001()
	 		{
	 	 		try
	 	 		{ 
	 	 			Fn_NavigationIcon_Navigation();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_NavigationIcon_Navigation()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}
	 		}
 		/*=====================================================================================================
 		* Author: Chandhini_A01
 		* Module : Home
 		* Test Cases : Auto_Home_NavigationDrawer_002
 		=====================================================================================================*/
	 		public  void  Auto_Home_NavigationDrawer_002()
	 		{
	 	 		try
	 	 		{ 
	 	 			Fn_NavigationDrawer_OutOfHomeIcon();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_NavigationDrawer_OutOfHomeIcon()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}
	 		}
 		/*=====================================================================================================
 		* Author: Chandhini_A01
 		* Module : Home
 		* Test Cases : 	Auto_Home_NavigationDrawer_003,Auto_Home_NavigationDrawer_004
 		=====================================================================================================*/
	 		public  void  Auto_Home_NavigationDrawer_003()
	 		{
	 	 		try
	 	 		{ 
	 	 			Fn_Home_NavigationDrawer_Contents();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_Home_NavigationDrawer_Contents()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}
	 		}
	 		
 		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : Home
		* Test Cases : Auto_Home_HomeCustomizePanel_001
		=====================================================================================================*/
			public void Auto_Home_HomeCustomizePanel_001()
			{
				try
		 		{ 
					Fn_CustomizePanel_Validation();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Fn_CustomizePanel_Validation()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
			
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : Home
		* Test Cases :Auto_Home_HomeCustomizePanel_002
		=====================================================================================================*/
			public void Auto_Home_HomeCustomizePanel_002()
			{
				try
		 		{ 
					Customize_Panel_Rearrange();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Customize_Panel_Rearrange()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : Home
		* Test Cases :Auto_Home_HomeCustomizePanel_003
		=====================================================================================================*/
			public void Auto_Home_HomeCustomizePanel_003()
			{
				try
		 		{ 
					Fn_OnDemand_Rearrange_UpArrow();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Fn_OnDemand_Rearrange_UpArrow()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}

	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : Home_HomeCustomizePanel
	 		* Test Cases :Auto_Home_HomeCustomizePanel_004,Auto_Home_HomeCustomizePanel_005
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_Home_HomeCustomizePanel_004()
	 		{
	 			try
	 	 		{ 	
	 				Fn_Customize_FavoriteEdit();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_Customize_FavoriteEdit()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/

		
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : Home
		* Test Cases : Auto_Home_HomeScreen_001,Auto_Home_HomeScreen_002
		=====================================================================================================*/
		public  void  Auto_Home_HomeScreen_001()
		{
	 		try
	 		{
	 			Fn_Home_Watchlist_Favorite_PanelContents();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Auto_Home_HomeScreen_002()");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}
		}
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : Home
		* Test Cases : Auto_Home_HomeScreen_003,004
		=====================================================================================================*/
		public  void  Auto_Home_HomeScreen_003()
		{
	 		try
	 		{ 
	 			Fn_Home_FeatureBanner_Swipe();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Auto_Home_HomeScreen_002()");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}
		}
	
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : Home
		* Test Cases : Auto_Home_HomeScreen_005
		=====================================================================================================*/
		public  void  Auto_Home_HomeScreen_005()
		{
	 		try
	 		{ 
	 			Fn_Home_BrowseAll_GridView();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Fn_Home_BrowseAll_GridView()");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}
		}
	
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : Home
		* Test Cases : Auto_Home_HomeScreen_006
		=====================================================================================================*/
		public  void  Auto_Home_HomeScreen_006()
		{
	 		try
	 		{ 
	 			Fn_Kidzone_OnDemand_AssetContainer();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Fn_Kidzone_OnDemand_AssetContainer()");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}
		}

	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : Home
	 		* Test Cases : Auto_Home_HomeScreen_007
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_Home_HomeScreen_007()
	 		{
	 			try
	 	 		{ 	
	 				Fn_Home_MediaBannerCheck();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_Home_MediaBannerCheck()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		
 		/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : Home
	 		* Test Cases :Auto_Home_HomeScreen_008

	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_Home_HomeScreen_008()
	 		{
	 			try
	 	 		{ 	
	 				Fn_Home_OnDemand_PanelCheck();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_Home_OnDemand_PanelCheck()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
	 	////////////////////////////////////LIVE TV MODULE//////////////////////////////////////////////////////
	 		
	 		/*=====================================================================================================
	 		* Author: Chandhini_A01
	 		* Module : Live TV
	 		* Test Cases : Auto_LiveTvScreen_001
	 		=====================================================================================================*/
	 		public void Auto_LiveTvScreen_001()
	 		{
	 			try
	 	 		{ 
	 				Fn_LiveTv_View();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Auto_LiveTvScreen_001()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}
	 	 			
	 		}
	 		/*=====================================================================================================
	 		* Author: Chandhini_A01
	 		* Module : Live TV
	 		* Test Cases : Auto_LiveTvScreen_002,Auto_LiveTvScreen_003,Auto_LiveTvScreen_004,Auto_LiveTvScreen_005
	 		=====================================================================================================*/
	 		public void Auto_LiveTvScreen_002()
	 		{
	 			try
	 	 		{ 
	 				Fn_LiveTV_Lable();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Auto_LiveTvScreen_002()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}
	 	 			
	 		}
	 		/*=====================================================================================================
	 		* Author: Chandhini_A01
	 		* Module : LiveTV
	 		* Test Cases : Auto_LiveTvScreen_006
	 		=====================================================================================================*/
	 		public void Auto_LiveTvScreen_006()
	 		{
	 			try
	 	 		{ 
	 				Fn_LiveTV_PanelCheck();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Auto_LiveTvScreen_006()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}
	 	 			
	 		}
	 		/*=====================================================================================================
	 		* Author: Chandhini_A01
	 		* Module : Live TV
	 		* Test Cases : Auto_LiveTvScreen_007
	 		=====================================================================================================*/
	 		public void Auto_LiveTvScreen_007()
	 		{
	 			try
	 	 		{ 
	 				Fn_LiveTv_Customize();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_OnDemand_Movies_BrowseAll()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}
	 			
	 		}
	 		/*=====================================================================================================
	 		* Author: Chandhini_A01
	 		* Module : Live TV
	 		* Test Cases : Auto_LiveTvScreen_008
	 		=====================================================================================================*/
	 		public void Auto_LiveTvScreen_009()
	 		{
	 			try
	 	 		{ 
	 				Fn_LiveTV_ListView_PlayIcon_Check();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_OnDemand_Movies_BrowseAll()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}
	 			
	 		}
	 		/*=====================================================================================================
	 			* Author: Chandhini
	 			* Module : Home
	 			* Test Cases : Auto_LiveTvScreen_009()
	 		====================================================================================================*/
	 			@Test(groups = {"seetest"})
	 		    public  void Auto_LiveTvScreen_008()
	 			{
	 				try
	 		 		{ 	
	 					Fn_LiveTV_ListView_AssetCount();
	 		 		}
	 		 		catch(Exception e)
	 		    	{
	 		    		System.err.println("Error in Fn_LiveTV_ListView_AssetCount()");
	 					System.err.println("	Message : "+e.getMessage());
	 					System.err.println("	Cause : "+e.getCause());
	 					e.printStackTrace();
	 					tearDown();
	 		    	}	
	 		}
	 	/*=====================================================================================================*/		

		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : KidZone
		* Test Cases : Auto_KidZone_KidZoneLockPopOver_001,Auto_KidZone_KidZoneLockPopOver_002,
		* 			   Auto_KidZone_KidZoneLockPopOver_003,Auto_KidZone_KidZoneLockPopOver_004
		=====================================================================================================*/
		public void Auto_KidZone_KidZoneLockPopOver_001()
		{
			try
	 		{ 
				if(super.Fn_Com_Navigate("Home_KidZone"))
					Fn_Kidzone_Lock();
			}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Auto_KidZone_KidZoneLockPopOver_001()");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}
	 		tearDown();
		}
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : KidZone
		* Test Cases : Auto_KidZone_001
		=====================================================================================================*/
		public void Auto_KidZone_002()
		{
			try
	 		{ 
				Fn_Kidzone_PanelCheck();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Auto_KidZone_001()");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}
	 			
		}
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : KidZone
		* Test Cases : Auto_KidZone_002
		=====================================================================================================*/
		public void Auto_KidZone_003()
		{
			try
	 		{ 
				Fn_Kidzone_LiveTV_Swipe();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Auto_KidZone_002()	");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}
	 			
		}
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : KidZone
		* Test Cases : Auto_KidZone_003
		=====================================================================================================*/
		public void Auto_KidZone_004()
		{
			try
	 		{ 
				Fn_KidZone_Asset_PopOver();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Auto_KidZone_003()	");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}
	 			
		}
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : KidZone
		* Test Cases : Auto_KidZone_004,Auto_KidZone_005
		=====================================================================================================*/
		public void Auto_KidZone_005()
		{
			try
	 		{ 
				Fn_KidZone_ChannelStream();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Auto_KidZone_KidZone_005()");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}
		}
		/*=====================================================================================================
		* Author: Chandhini_A01
		* Module : KidZone
		* Test Cases : Auto_KidZone_001
		=====================================================================================================*/
		public void Auto_KidZone_001()
		{
			try
	 		{ 
				//Fn_Login();
				Fn_Kidzone_ParentalPopOver();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Auto_KidZone_007()");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}
	 			
		}

	/*=====================================================================================================
	* Author: Deepak_t04
	* Module : HelpOverlay
	* Test Cases : Auto_HelpOverlay_001
	=====================================================================================================*/
	@Test(groups = {"seetest"})
    public  void Auto_HelpOverlay_001()
	{
		try
 		{ 	
 			AF.Fn_Validate_HelpIcon();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_HelpOverlay_001");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
	/*=====================================================================================================*/
  	/*=====================================================================================================
		* Author: Venkat
		* Module : About
		* Test Cases : Settings_About_001, Settings_About_002, Settings_About_oo3, Settings_About_005
		* 			   Settings_About_006
	=====================================================================================================*/
		
	    public  void Auto_Settings_About__BuildNumber_008()
		{
			try
	 		{	
	 			Fn_Validate_AboutVerifications();
	 		}
	 		catch(Exception e)
	    	{
	    		System.err.println("Error in Settings_Managedevices_010");
				System.err.println("	Message : "+e.getMessage());
				System.err.println("	Cause : "+e.getCause());
				e.printStackTrace();
				tearDown();
	    	}	
	}
  
	  	
	  	/*=====================================================================================================
	  		* Author: Venkat
	  		* Module : Settings
	  		* Test Cases : Settings_Managedevices_001, Settings_Managedevices_002, Settings_Managedevices_003,
	  		* 			   Settings_Managedevices_004
	  	=====================================================================================================*/
	  		
	  	    public  void Auto_Settings_Managedevices_DefaultSTB_004()
	  		{
	  			try
	  	 		{ 	
	  				Fn_Validate_ManageDevicesVerifications();
	  	 		}
	  	 		catch(Exception e)
	  	    	{
	  	    		System.err.println("Error in Settings_Managedevices_001");
	  				System.err.println("	Message : "+e.getMessage());
	  				System.err.println("	Cause : "+e.getCause());
	  				e.printStackTrace();
	  				tearDown();
	  	    	}	
	 	}
	  		/*=====================================================================================================
	  		* Author: Venkat
	  		* Module : Settings
	  		* Test Cases : Settings_Summary_001, Settings_Summary_002, Settings_Summary_003 and Setting_Summary_004
	  		======================================================================================================*/
	  		
	  	    public  void Auto_Settings_summary_AccountNumber_001()
	  		{
	  			try
	  	 		{ 	
	  				//this.Fn_LogOut();
	  				//this.Fn_Login();
	  	 			Validate_UserAccountVerifications();
	  	 		}
	  	 		catch(Exception e)
	  	    	{
	  	    		System.err.println("Error in Settings_Summary_001");
	  				System.err.println("	Message : "+e.getMessage());
	  				System.err.println("	Cause : "+e.getCause());
	  				e.printStackTrace();
	  				tearDown();
	  	    	}	
	 	}
	 	
	/*=====================================================================================================
	* Author: Deepak_t04
	* Module : HelpOverlay
	* Test Cases : Auto_HelpOverlay_002,Auto_HelpOverlay_003,Auto_HelpOverlay_004,Auto_HelpOverlay_005,
	 			   Auto_HelpOverlay_006,Auto_HelpOverlay_007,Auto_HelpOverlay_008
	=====================================================================================================*/
	@Test(groups = {"seetest"})
    public  void Auto_HelpOverlay_002()
	{
		try
 		{ 	
 			AF.Fn_Validate_HelpPopOver();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_HelpOverlay_002");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
	/*=====================================================================================================*/
	
	/*=====================================================================================================
	* Author: Deepak_t04
	* Module : Search
	* Test Cases : Auto_Search_GeneralSearch_001
	=====================================================================================================*/
	@Test(groups = {"seetest"})
    public  void Auto_Search_GeneralSearch_001()
	{
		try
 		{ 	
 			AF.Fn_Validate_SearchEdit();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_Search_GeneralSearch_001");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
	/*=====================================================================================================*/
	
	/*=====================================================================================================
	* Author: Deepak_t04
	* Module : Search
	* Test Cases : Auto_Search_GeneralSearch_002
	=====================================================================================================*/
	@Test(groups = {"seetest"})
    public  void Auto_Search_GeneralSearch_002()
	{
		try
 		{ 	
 			AF.Fn_Validate_SearchEditText();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_Search_GeneralSearch_002");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
	/*=====================================================================================================*/
	
	/*=====================================================================================================
	* Author: Deepak_t04
	* Module : Search
	* Test Cases : Auto_Search_GeneralSearch_003
	=====================================================================================================*/
	@Test(groups = {"seetest"})
    public  void Auto_Search_GeneralSearch_003()
	{
		try
 		{ 	
 			AF.Fn_Validate_SearchPopUp();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_Search_GeneralSearch_003");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
	/*=====================================================================================================*/
	
	/*=====================================================================================================
	* Author: Deepak_t04
	* Module : Search
	* Test Cases : Auto_Search_GeneralSearch_004
	=====================================================================================================*/
	@Test(groups = {"seetest"})
    public  void Auto_Search_GeneralSearch_004()
	{
		try
 		{ 	
 			AF.Fn_Validate_SearchEnterKey();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_Search_GeneralSearch_004");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
	/*=====================================================================================================*/
	
	/*=====================================================================================================
	* Author: Deepak_t04
	* Module : Search
	* Test Cases : Auto_Search_GeneralSearch_005
	=====================================================================================================*/
	@Test(groups = {"seetest"})
    public  void Auto_Search_GeneralSearch_005()
	{
		try
 		{ 	
 			AF.Fn_Validate_SearchAssertClick();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_Search_GeneralSearch_005");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
	/*=====================================================================================================*/
	
	/*=====================================================================================================
	* Author: Deepak_t04
	* Module : Search
	* Test Cases : Auto_Search_GeneralSearch_006
	=====================================================================================================*/
	@Test(groups = {"seetest"})
    public  void Auto_Search_GeneralSearch_006()
	{
		try
 		{ 	
 			AF.Fn_Validate_SearchEditClear();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_Search_GeneralSearch_006");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
	/*=====================================================================================================*/
	
	/*=====================================================================================================
	* Author: Deepak_t04
	* Module : Search
	* Test Cases : Auto_Search_PopoverList_001
	=====================================================================================================*/
	@Test(groups = {"seetest"})
    public  void Auto_Search_PopoverList_001()
	{
		try
 		{ 	
 			AF.Fn_Validate_SearchPopUp();
 		}
 		catch(Exception e)
    	{
    		System.err.println("Error in Auto_Search_PopoverList_001");
			System.err.println("	Message : "+e.getMessage());
			System.err.println("	Cause : "+e.getCause());
			e.printStackTrace();
			tearDown();
    	}
	}
		/*=====================================================================================================*/
	
		/*=====================================================================================================
 		* Author: Deepak_t04
 		* Module : Search
 		* Test Cases : Auto_Search_PopoverList_002
 		=====================================================================================================*/
 		@Test(groups = {"seetest"})
 	    public  void Auto_Search_PopoverList_002()
 		{
 			try
 	 		{ 	
 	 			AF.Fn_Validate_SearchAssertTitle();
 	 		}
 	 		catch(Exception e)
 	    	{
 	    		System.err.println("Error in Auto_Search_PopoverList_002");
 				System.err.println("	Message : "+e.getMessage());
 				System.err.println("	Cause : "+e.getCause());
 				e.printStackTrace();
 				tearDown();
 	    	}	
	}
	/*=====================================================================================================*/
 		
 		/*=====================================================================================================
 		* Author: Deepak_t04
 		* Module : Search
 		* Test Cases : Auto_Search_PopoverList_003
 		=====================================================================================================*/
 		@Test(groups = {"seetest"})
 	    public  void Auto_Search_PopoverList_003()
 		{
 			try
 	 		{ 	
 	 			AF.Fn_Validate_SearchAssertCoverArt();
 	 		}
 	 		catch(Exception e)
 	    	{
 	    		System.err.println("Error in Auto_Search_PopoverList_002");
 				System.err.println("	Message : "+e.getMessage());
 				System.err.println("	Cause : "+e.getCause());
 				e.printStackTrace();
 				tearDown();
 	    	}	
	}
	/*=====================================================================================================*/
 	
 		/*=====================================================================================================
 		* Author: Deepak_t04
 		* Module : Search
 		* Test Cases : Auto_Search_FullscreenSearch_001
 		=====================================================================================================*/
 		@Test(groups = {"seetest"})
 	    public  void Auto_Search_FullscreenSearch_001()
 		{
 			try
 	 		{ 	
 	 			AF.Fn_Validate_SearchFullScreen();
 	 		}
 	 		catch(Exception e)
 	    	{
 	    		System.err.println("Error in Auto_Search_FullscreenSearch_001");
 				System.err.println("	Message : "+e.getMessage());
 				System.err.println("	Cause : "+e.getCause());
 				e.printStackTrace();
 				tearDown();
 	    	}	
	}
	/*=====================================================================================================*/

 		/*=====================================================================================================
 		* Author: Deepak_t04
 		* Module : Search
 		* Test Cases : Auto_Search_FullscreenSearch_002
 		=====================================================================================================*/
 		@Test(groups = {"seetest"})
 	    public  void Auto_Search_FullscreenSearch_002()
 		{
 			try
 	 		{ 	
 	 			AF.Fn_Validate_SearchFullScreenBack();
 	 		}
 	 		catch(Exception e)
 	    	{
 	    		System.err.println("Error in Auto_Search_FullscreenSearch_002");
 				System.err.println("	Message : "+e.getMessage());
 				System.err.println("	Cause : "+e.getCause());
 				e.printStackTrace();
 				tearDown();
 	    	}	
	}
	/*=====================================================================================================*/
 		/*
 		 * Author: Edwin_Paul
 		 * Module: Login
 		 * Sub-Module: Login Screen
 		 * Test Cases: Auto_Login_LoginScreen_001 to Auto_Login_LoginScreen_006
 		 */
 			@Test(groups = {"seetest"})
 		    public void Auto_Login_LoginScreen_001()
 			{
 		 		try
 		 		{ 	
 		 			//Fn_Login();
 		 			Fn_Login_Validate();
 		 		}
 		 		catch(Exception e)
 		    	{
 		    		System.err.println("Error in Auto_Login_LoginScreen_001");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 					tearDown();
 		    	}
 			}
 			/******************************************************************************************************************/
 			
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test cases:  Auto_Login_CreateAccount_001
 			 * Function: Field Validating in the Create Account 1st tab
 			 */
 			public void Auto_Login_CreateAccount_001()
 			{
 				try
 				{
 					//For Demo purpose
 					if(Fn_Create_Account_Validate(4))
 					{
	 					super.Fn_LicenseAgreementValidation();
	 					super.Fn_LogOut();
 					}
 					else
 					{
 						System.out.println("Create account failed");
 					}
 					//Fn_Create_Account_Validate(1);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_001");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test cases:  Auto_Login_CreateAccount_002
 			 * Function: Validating Security Code help pop over in the Create Account pop-up 1st tab
 			 */
 			public void Auto_Login_CreateAccount_002()
 			{
 				try
 				{
 					//For Smoke Run
 					if(Fn_Create_Account_Validate(4))
 					{
	 					super.Fn_LicenseAgreementValidation();
	 					super.Fn_LogOut();
 					}
 					else
 					{
 						System.out.println("Create account failed");
 					}
 					//Fn_Create_Account_Validate(4);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_002");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test cases:  Auto_Login_CreateAccount_003
 			 * Function: Validating Service zip Code help pop over in the Create Account pop-up 1st tab
 			 */
 			public void Auto_Login_CreateAccount_003()
 			{
 				try
 				{
 					//for Smoke Run
 					if(Fn_Create_Account_Validate(4))
 					{
	 					super.Fn_LicenseAgreementValidation();
	 					super.Fn_LogOut();
 					}
 					else
 					{
 						System.out.println("Create account failed");
 					}
 					//Fn_Create_Account_Validate(3);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_003");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test cases:  Auto_Login_CreateAccount_004
 			 * Function: Validating the CharterID field by entering less than 6 Characters
 			 */
 			public void Auto_Login_CreateAccount_004()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(5);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_004");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test cases:  Auto_Login_CreateAccount_005
 			 * Function: Validating the CharterID field by entering more than 40 Characters
 			 */
 			public void Auto_Login_CreateAccount_005()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(5);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_005");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test cases: Auto_Login_CreateAccount_006
 			 * Function:Validating the CharterID by adding special characters on both beginning and end characters
 			 */
 			public void Auto_Login_CreateAccount_006()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(5);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_006");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_007
 			 * Function: Validating the CharterID by adding special characters in middle of the characters 
 			 */
 			public void Auto_Login_CreateAccount_007()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(4);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_007");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_009
 			 * Function: Validating the Charter ID by entering <8 characters in password
 			 */
 			public void Auto_Login_CreateAccount_009()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(6);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_009");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_010
 			 * Function: Validating the Charter ID by entering >30 characters in password
 			 */
 			public void Auto_Login_CreateAccount_010()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(6);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_010");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_011
 			 * Function: Validating the Password != Confirm password throwing error
 			 */
 			public void Auto_Login_CreateAccount_011()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(5);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_011");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_012
 			 * Function: Validating whether the Account Creation is done successfully with Account Number and Last Name
 			 */
 			public void Auto_Login_CreateAccount_012()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(4);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_012");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_013
 			 * Function: Validating whether the Account Creation is done successfully with Account Number and Security Code
 			 */
 			public void Auto_Login_CreateAccount_013()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(4);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_013");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_014
 			 * Function: Validating whether the Account Creation is done successfully with Account Number and Service Zip Code
 			 */
 			public void Auto_Login_CreateAccount_014()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(4);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_014");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_015
 			 * Function: Validating whether the Password field is accepting the Special Characters
 			 */
 			public void Auto_Login_CreateAccount_015()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(4);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_015");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_016
 			 * Function: Validating whether the Password field is accepting the Special Characters (%)
 			 */
 			public void Auto_Login_CreateAccount_016()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(4);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_016");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_017
 			 * Function: Validating whether the Password field is accepting the Special Characters ('%', '$' , '!')
 			 */
 			public void Auto_Login_CreateAccount_017()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(4);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_017");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_018
 			 * Function: Validating whether the Password field is accepting the Special Characters ( '%',':' ,'?')
 			 */
 			public void Auto_Login_CreateAccount_018()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(4);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_018");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub Module: Create Account
 			 * Test Cases: Auto_Login_CreateAccount_019
 			 * Function: Validating whether the Already existing account is highlighted
 			 */
 			public void Auto_Login_CreateAccount_019()
 			{
 				try
 				{
 					Fn_Create_Account_Validate(4);
 				}
 				catch(Exception e)
 				{
 					System.err.println("Error in Auto_Login_CreateAccount_019");
 					System.err.println("	Message : "+e.getMessage());
 					System.err.println("	Cause : "+e.getCause());
 					e.printStackTrace();
 				}
 			}
 			/************************************************************************************************************************/
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub-Module: Reset Password
 			 * Test Cases: Auto_Login_ResetPassword_001, Auto_Login_ResetPassword_002
 			 */
 			public void Auto_Login_ResetPassword_001()
 			{
 				try
 				{
 					if(Fn_ResetPassword_01())
 					{
 						super.Fn_Login();
 						super.Fn_LogOut();
 					}
 	 			}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_ResetPassword_001");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			
 			public void Auto_Login_ResetPassword_002()
 			{
 				try
 				{
 					if(Fn_ResetPassword_01())
 					{
 						super.Fn_Login();
 						super.Fn_LogOut();
 					}
 	 			}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_ResetPassword_001");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub-Module: Reset Password
 			 * Test Cases: Auto_Login_ResetPassword_003
 			 * Function: Field validation
 			 */
 			public void Auto_Login_ResetPassword_003()
 			{
 				try
 				{
 					if(Fn_ResetPassword_01())
 					{
 						super.Fn_Login();
 						super.Fn_LogOut();
 					}
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_ResetPassword_003");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub-Module: Reset Password
 			 * Test Cases: Auto_Login_ResetPassword_005,
 			 * Function: Negative validation- Verify reset password failure
 			 */
 			public void Auto_Login_ResetPassword_005()
 			{
 				try
 				{
 					Fn_ResetPassword_01();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_ResetPassword_005");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub-Module: Reset Password
 			 * Test Cases: Auto_Login_ResetPassword_006
 			 * Function: Verify whether the reset password is done by entering Account Number and Zip code
 			 */
 			public void Auto_Login_ResetPassword_006()
 			{
 				try
 				{
 					Fn_ResetPassword_01();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_ResetPassword_006");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub-Module: Reset Password
 			 * Test Cases: Auto_Login_ResetPassword_007
 			 * Function: Verify whether the reset password is done by entering Account Number and Last Name
 			 */
 			public void Auto_Login_ResetPassword_007()
 			{
 				try
 				{
 					Fn_ResetPassword_01();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_ResetPassword_007");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub-Module: Reset Password
 			 * Test Cases: Auto_Login_ResetPassword_008,
 			 * Function: Verify whether the reset password is done by entering Account Number and Security Code
 			 */
 			public void Auto_Login_ResetPassword_008()
 			{
 				try
 				{
 					Fn_ResetPassword_01();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_ResetPassword_008");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub-Module: Reset Password
 			 * Test Cases: Auto_Login_ResetPassword_009,
 			 * Function: Verify whether the reset password is done by entering Account Number and Security Code
 			 
 			public void Auto_Login_ResetPassword_009()
 			{
 				try
 				{
 					Fn_ResetPassword_01();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_ResetPassword_008");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}*/
 			
 			 
 			 /*
 			 * Author: Edwin_Paul
 			 * Module: Login
 			 * Sub-Module: Reset Password
 			 * Test Cases: Auto_Login_ResetPassword_004
 			 */
 			public void Auto_Login_ResetPassword_004()
 			{
 				try
 				{
 					Fn_ResetPassword_04();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_ResetPassword_003");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*******************************************************************************************************************/
 			
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Day DropDown List
 			 * Test Cases: Auto_Guide_DDL_001 to Auto_Guide_DDL_005
 			 */
 			public void Auto_Guide_DDL_001()
 			{
 				try
 				{
 					if(Fn_Login())
 					{
 						Fn_TodayDropDown();
 					}
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_DDL_001");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/***********************************************************************************************************************/
 			
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Filter
 			 * Test Cases: Auto_Guide_Filter_001 
 			 */
 			public void Auto_Guide_GuideScreen_001()
 			{
 				try
 				{
 					if(Fn_Login())
 						Fn_GuideScreen_isPreFiltered();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_Filter_001");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Filter
 			 * Test Cases: Auto_Guide_Filter_002
 			 */
 			public void Auto_Guide_GuideScreen_002()
 			{
 				try
 				{
 					Fn_GuideScreen_IsElementLoaded();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_Filter_002");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Filter
 			 * Test Cases: Auto_Guide_Filter_003
 			 */
 			public void Auto_Guide_GuideScreen_003()
 			{
 				try
 				{
 					Fn_GuideScreen_validate_NetworkCell();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_Filter_003");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Filter
 			 * Test Cases: Auto_Guide_Filter_004
 			 */
 			public void Auto_Guide_GuideScreen_004() throws ParseException
 			{
 				try
 				{
 					String strIcon="Guide_GuideScreen_ONDemandIcon"; 
 					String strIcon1="Guide_GuideScreen_NewIcon";
 					super.Fn_GuideScreen_TimeBar();
 					EF.Update_Step_Report("Time Bar should be split up 30min interval", "The Time bar is split up in 30min interval||The Time bar is not split up in 30min interval", true);
 					EF.Update_Step_Report("Verify whether the program cells have On Demand Icon", "Program cells have On Demand Icon||Program cells dont have On Demand Icon", Fn_GuideScreen_ProgramCellIcon(strIcon));
 					EF.Update_Step_Report("Verify whether the program cells have On Demand Icon", "Program cells have On Demand Icon||Program cells dont have On Demand Icon", Fn_GuideScreen_ProgramCellIcon(strIcon1));
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_Filter_004");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Filter
 			 * Test Cases: Auto_Guide_Filter_005
 			 */
 			public void Auto_Guide_GuideScreen_005()
 			{
 				try
 				{
 					Fn_GuideScreen_ToggleSortOption();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_Filter_005");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Filter
 			 * Test Cases: Auto_Guide_Filter_006
 			 */
 			public void Auto_Guide_GuideScreen_006()
 			{
 				try
 				{
 					Fn_GuideScreen_ToggleSortOption();
 					Fn_GuideScreen_SortingValidation();
 					//Fn_LogOut();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_Filter_006");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/////////**************Build 2*****************/////////////
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Filter
 			 * Test Cases: Auto_Guide_Filter_007
 			 */
 			public void Auto_Guide_GuideScreen_007()
 			{
 				try
 				{
 					Fn_FindingProgressIndicator();
 					
 					//Fn_LogOut();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_Filter_007");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Filter
 			 * Test Cases: Auto_Guide_Filter_008
 			 */
 			public void Auto_Guide_GuideScreen_008()
 			{
 				try
 				{
 					Fn_FavoriteValidation_08();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_Filter_008");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			
 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Filter
 			 * Test Cases: Auto_Guide_Filter_009
 			 */
 			public void Auto_Guide_GuideScreen_009()
 			{
 				try
 				{
 					Fn_GuideScreen_ValidatingFavorite();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_Filter_009");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}

 			/*
 			 * Author: Edwin_Paul
 			 * Module: Guide
 			 * Sub-Module: Filter
 			 * Test Cases: Auto_Guide_Filter_010
 			 */
 			public void Auto_Guide_GuideScreen_010()
 			{
 				try
 				{
 					//TODO
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_Filter_009");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*************************************************************************************************/
 			/*=====================================================================================================
 	 		* Author: Edwin_Paul
 	 		* Module : Guide
 	 		* Sub-Module: Network cell pop over
 	 		* Test Cases : Auto_Guide_NCP_001 to Auto_Guide_NCP_013
 	 		=====================================================================================================*/

 			public void Auto_Guide_NCP_001()
 			{
 				try
 				{
 					super.Fn_NetworkCellPopover();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Guide_NCP_001");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/****************************************************************************************************/
 			/*=====================================================================================================
 	 		* Author: Edwin_Paul
 	 		* Module : Browse all
 	 		* Sub-Module: Browse all
 	 		* Test Cases : Auto_BrowseAll_001
 	 		=====================================================================================================*/
 			
 			public void Auto_BrowseAll_001()
 			{
 				try
 				{
 					Fn_BrowseAll_Home();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_BrowseAll_001");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*=====================================================================================================
 	 		* Author: Edwin_Paul
 	 		* Module : Browse all
 	 		* Sub-Module: Browse all
 	 		* Test Cases : Auto_BrowseAll_002 to Auto_BrowseAll_003
 	 		=====================================================================================================*/
 			public void Auto_BrowseAll_002()
 			{
 				try
 				{
 					Fn_Com_Navigate("Home_OnDemand");
 					Fn_BrowseAll_OnDemand();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_BrowseAll_002");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*=====================================================================================================
 	 		* Author: Edwin_Paul
 	 		* Module : Login	
 	 		* Sub-Module: General
 	 		* Test Cases : Auto_Login_General_005 to Auto_Login_General_009
 	 		=====================================================================================================*/
 			public void Auto_Login_General_005()
 			{
 				try
 				{
 					Fn_LicenseAgreementValidation();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_General_001");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*=====================================================================================================
 	 		* Author: Edwin_Paul
 	 		* Module : Login	
 	 		* Sub-Module: General
 	 		* Test Cases : Auto_Login_General_001
 	 		=====================================================================================================*/
 			public void Auto_Login_General_001()
 			{
 				try
 				{
 					Fn_Login_Validate();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_General_005");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*=====================================================================================================
 	 		* Author: Edwin_Paul
 	 		* Module : Login	
 	 		* Sub-Module: General
 	 		* Test Cases : Auto_Login_General_002
 	 		=====================================================================================================*/
 			public void Auto_Login_General_002()
 			{
 				try
 				{
 					Fn_Login_Validate();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_General_006");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*=====================================================================================================
 	 		* Author: Edwin_Paul
 	 		* Module : Login	
 	 		* Sub-Module: General
 	 		* Test Cases : Auto_Login_General_003
 	 		=====================================================================================================*/
 			public void Auto_Login_General_003()
 			{
 				try
 				{
 					Fn_Login_Validate();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_General_007");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*=====================================================================================================
 	 		* Author: Edwin_Paul
 	 		* Module : Login	
 	 		* Sub-Module: General
 	 		* Test Cases : Auto_Login_General_004
 	 		=====================================================================================================*/
 			public void Auto_Login_General_004()
 			{
 				try
 				{
 					Fn_Login_Validate();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_Login_General_008");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			/*=====================================================================================================
 	 		* Author: Edwin_Paul
 	 		* Module : SportZone
 	 		* Sub-Module: 
 	 		* Test Cases : Auto_SportZone_001
 	 		=====================================================================================================*/
 			public void Auto_SportZone_001()
 			{
 				try
 				{
					super.Fn_LogOut();
 					if(super.Fn_Login())
					{
						if(this.Fn_Com_Navigate("Home_SportZone"))
 							Fn_SportZone_OutofHome();
 						else
							System.out.println("Navigation Failed - SportZone"); 
					}
 					else
						System.out.println("Loggin Failed - SportZone");
 					
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_SportZone_001");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
 			}
 			
 			/*=====================================================================================================
 	 		* Author: Edwin_Paul
 	 		* Module : SportZone
 	 		* Sub-Module: 
 	 		* Test Cases : Auto_SportZone_001
 	 		=====================================================================================================*/
 			public void On_Demand_001()
 			{
 				try
 				{
 					Fn_OnDemand_Customize();
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Add_watchlist_001");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 					tearDown();
 				}
 				
 				tearDown();
 			}
 			
 			
 			/*=====================================================================================================
 	 		* Author: Venkat
 	 		* Module : Settings
 	 		* Test Cases : Settings_Summary_001, Settings_Summary_002, Settings_Summary_003 and Setting_Summary_004
 	 	=====================================================================================================*/
 	 		@Test(groups = {"seetest"})
 	 	    public  void Settings_Summary_001()
 	 		{
 	 			try
 	 	 		{ 	
 	 	 			Validate_UserAccountVerifications();
 	 	 		}
 	 	 		catch(Exception e)
 	 	    	{
 	 	    		System.err.println("Error in Settings_Summary_001");
 	 				System.err.println("	Message : "+e.getMessage());
 	 				System.err.println("	Cause : "+e.getCause());
 	 				e.printStackTrace();
 	 				tearDown();
 	 	    	}	
 		}
 		/*=====================================================================================================*/
 	 	
 	 
 	 	
 	 	/*=====================================================================================================
 	 		* Author: Venkat
 	 		* Module : Settings
 	 		* Test Cases : Settings_Managedevices_001, Settings_Managedevices_002, Settings_Managedevices_003,
 	 		* 			   Settings_Managedevices_004
 	 	=====================================================================================================*/
 	 		@Test(groups = {"seetest"})
 	 	    public  void Settings_Managedevices_005()
 	 		{
 	 			try
 	 	 		{ 	
 	 				Fn_Validate_ManageDevicesVerifications();
 	 	 		}
 	 	 		catch(Exception e)
 	 	    	{
 	 	    		System.err.println("Error in Settings_Managedevices_001");
 	 				System.err.println("	Message : "+e.getMessage());
 	 				System.err.println("	Cause : "+e.getCause());
 	 				e.printStackTrace();
 	 				tearDown();
 	 	    	}	
 		}
 		/*=====================================================================================================*/
 	 		
 	 		
 	 	/*=====================================================================================================
 	 		* Author: Venkat
 	 		* Module : About
 	 		* Test Cases : Settings_About_001, Settings_About_002, Settings_About_oo3, Settings_About_005
 	 		* 			   Settings_About_006
 	 	=====================================================================================================*/
 	 		@Test(groups = {"seetest"})
 	 	    public  void Settings_About_010()
 	 		{
 	 			try
 	 	 		{	
 	 	 			Fn_Validate_AboutVerifications();
 	 	 		}
 	 	 		catch(Exception e)
 	 	    	{
 	 	    		System.err.println("Error in Settings_Managedevices_010");
 	 				System.err.println("	Message : "+e.getMessage());
 	 				System.err.println("	Cause : "+e.getCause());
 	 				e.printStackTrace();
 	 				tearDown();
 	 	    	}	
 		}
 		/*=====================================================================================================*/
 	 		
 	 	/*=====================================================================================================
 	 		* Author: Venkat
 	 		* Module : Watchlist
 	 		* Test Cases : Add_1Serieswith2Episodes_Watchlist_001(), Add_1Serieswith2Episodes_Watchlist_002(), Add_1Serieswith2Episodes_Watchlist_003()
 	 		* Add_1Serieswith2Episodes_Watchlist_004(), Add_1Serieswith2Episodes_Watchlist_005(), Add_1Serieswith2Episodes_Watchlist_006()
 	 		* 	1) Adding Watchlist item for TV Series first Episode
 	 		* 	2) Verification for Added TV Series First Episode Name in My Library Screen under Watchlist Section
 	 		* 	3) Verification for Added TV Series First Episode Name in Watchlist Screen
 	 		* 	4) Adding Watchlist item for TV Series for Second Episode
 	 		* 	5) Verification for the Added TV Series for first and Second Episodes under Watchlist Rollup Modal through My Library Screen
 	 		* 	6) Verification for the Added TV Series for first and Second Episodes under Watchlist Rollup Modal through Watchlist Screen
 	 	=====================================================================================================*/
 	 		@Test(groups = {"seetest"})
 	 	    public  void Add_Watchlist_1Serieswith2Episodes_001()
 	 		{
 	 			try
 	 	 		{ 	
 	 	 			Fn_Validate_AddWatchlist_1Serieswith2Episodes();
 	 	 		}
 	 	 		catch(Exception e)
 	 	    	{
 	 	    		System.err.println("Error in Add_1Serieswith2Episodes_Watchlist_001");
 	 				System.err.println("	Message : "+e.getMessage());
 	 				System.err.println("	Cause : "+e.getCause());
 	 				e.printStackTrace();
 	 				tearDown();
 	 	    	}	
 		}
 		/*=====================================================================================================*/


 	/*=====================================================================================================
 	 		* Author: Venkat
 	 		* Module : Watchlist
 	 		* Test Cases : Delete_1Serieswith2Episodes_Watchlist_001(), Delete_1Serieswith2Episodes_Watchlist_002()
 	 		* 	1) Deleting the Watchlist item for TV series with 2 Episodes through Rollup Modal popover
 	 		* 	2) Verification for deleted Watchlist item for TV series with 2 Episodes in My Library Screen under Watchlist Section
 	 	====================================================================================================*/
 	 		@Test(groups = {"seetest"})
 	 	    public  void Delete_1Serieswith2Episodes_Watchlist_014()
 	 		{
 	 			try
 	 	 		{ 	
 	 	 			Fn_Validate_DeleteWatchlist_1Serieswith2Episodes();
 	 	 		}
 	 	 		catch(Exception e)
 	 	    	{
 	 	    		System.err.println("Error in Delete_1Serieswith2Episodes_Watchlist_014");
 	 				System.err.println("	Message : "+e.getMessage());
 	 				System.err.println("	Cause : "+e.getCause());
 	 				e.printStackTrace();
 	 				tearDown();
 	 	    	}	
 		}
 		/*=====================================================================================================*/

 	/*=====================================================================================================
 	 		* Author: Venkat
 	 		* Module : Others
 	 		* Test Cases :  Verify that Welcome to On Demand Modal Text is updated to the New Message 
 	 	====================================================================================================*/
 	 		@Test(groups = {"seetest"})
 	 	    public  void Auto_IPVOD_Validations_001()
 	 		{
 	 			try
 	 	 		{
// 	 				System.out.println("Return Current Time : "+getCurrenttime()); //Formal method to retrieve the Current time
 	 				if(super.Fn_Login())
 	 					Auto_IPVOD_Validations();
 	 	 		}
 	 	 		catch(Exception e)
 	 	    	{
 	 	    		System.err.println("Error in Auto_IPVOD_Validations_001");
 	 				System.err.println("	Message : "+e.getMessage());
 	 				System.err.println("	Cause : "+e.getCause());
 	 				e.printStackTrace();
 	 				tearDown();
 	 	    	}	
 		}
 		/*=====================================================================================================*/
 	 		
 	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :		
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_001()
	 		{
	 			try
	 	 		{ 	
	 				Fn_Setting_RegisteredDevice();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_Setting_RegisteredDevice()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
	 		}
	 		/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_002
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_002()
	 		{
	 			try
	 	 		{ 	
	 				Fn_Showtime_DownloadIcon();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Auto_D2G_002()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_003,Auto_D2G_004,Auto_D2G_005
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_003()
	 		{
	 			try
	 	 		{ 	
	 				Fn_Settings_Register();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_Settings_Register()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 		/*=====================================================================================================*/
	 		/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_006,Auto_D2G_007
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_006()
	 		{
	 			try
	 	 		{ 	
	 				Fn_Settings_RegisterRemoveBtn_Check();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_Settings_RegisterRemoveBtn_Check()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_008,Auto_D2G_009
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_008()
	 		{
	 			try
	 	 		{ 	
	 				Fn_RegisterModal_Check();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_RegisterModal_Check()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_010
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_010()
	 		{
	 			try
	 	 		{ 	
	 				Fn_Settings_NickName();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_Settings_NickName()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				EnjoyingAppHandler();
	 				tearDown();
	 	    	}	
		}
	 	/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_011,Auto_D2G_012
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_011()
	 		{
	 			try
	 	 		{ 	
	 				Fn_Toggle_Register_Remove();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_Toggle_Register_Remove()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				EnjoyingAppHandler();
	 				tearDown();
	 	    	}	
		}

	 		/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_013
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_013()
	 		{
	 			try
	 	 		{ 	
	 				Fn_TempDownloads_Delete();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_TempDownloads_Delete()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				EnjoyingAppHandler();
	 				tearDown();
	 	    	}	
		}
	 	/*=====================================================================================================
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_014
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_014()
	 		{
	 			try
	 	 		{ 	
	 				Fn_DeRegister_PopOver();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_DeRegister_PopOver()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				EnjoyingAppHandler();
	 				tearDown();
	 	    	}	
		}
	 	/*=====================================================================================================
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_015,Auto_D2G_016,Auto_D2G_017
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_015()
	 		{
	 			try
	 	 		{ 	
	 				Fn_VideoQuality_PopOverCheck();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_VideoQuality_PopOverCheck()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				EnjoyingAppHandler();
	 				tearDown();
	 	    	}	
		}
	 	/*=====================================================================================================
	 	
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_018,Auto_D2G_019
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_018()
	 		{
	 			try
	 	 		{ 	
	 				Fn_OnDemand_WatchOption();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_OnDemand_WatchOption()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				EnjoyingAppHandler();
	 				tearDown();
	 	    	}	
		}
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_020,Auto_D2G_021
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_020()
	 		{
	 			try
	 	 		{ 	
	 				Fn_WatchOn_Tv_IPad_PanelCheck();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_WatchOn_Tv_IPad_PanelCheck()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				EnjoyingAppHandler();
	 				tearDown();
	 	    	}	
		}
	 		/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_022
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_022()
	 		{
	 			try
	 	 		{ 	
	 				Fn_Download_InQueueIcon_Check();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_Download_InQueueIcon_Check()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
	 	/*=====================================================================================================	
	 	//Auto_D2G_023 Skipped
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_024,Auto_D2G_025
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_024()
	 		{
	 			try
	 	 		{ 	
	 				Fn_AssetDetail_RegisterModalCheck();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_AssetDetail_RegisterModalCheck()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
	 	
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_026
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_026()
	 		{
	 			try
	 	 		{ 	
	 				Fn_DeviceName_Remove();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_DeviceName_Remove()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_027
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_027()
	 		{
	 			try
	 	 		{ 	
	 				Fn_DeviceName_Register();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_DeviceName_Register()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_028
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_028()
	 		{
	 			try
	 	 		{ 	
	 				Fn_VOD_GodBackIcon_Check();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_VOD_GodBackIcon_Check()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_029,Auto_D2G_030,Auto_D2G_031,Auto_D2G_032,Auto_D2G_033
	 		* 			Auto_D2G_034,Auto_D2G_035,Auto_D2G_036,Auto_D2G_037,Auto_D2G_038,Auto_D2G_039,
	 		* 			Auto_D2G_040
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_029()
	 		{
	 			try
	 	 		{ 	
	 				Fn_DownloadScreen_Check();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_DownloadScreen_Check())");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_041,42,43
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_041()
	 		{
	 			try
	 	 		{ 	
	 				Fn_AssetDownloadingModal_Check();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_DownloadScreen_Check())");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 		/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_044 to Auto_D2G_049
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_044()
	 		{
	 			try
	 	 		{ 	
	 				Fn_SeriesAsset_Delete();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_SeriesAsset_Delete()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
	 		}
		/*=====================================================================================================*/
	 	
	 	
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_050,52
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_050()
	 		{
	 			try
	 	 		{ 	
	 				Fn_MultipleSeriesAsset_Delete();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_AssetDownloadingModal_Check()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_053 to 059
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_053()
	 		{
	 			try
	 	 		{ 	
	 				Fn_MoreDeviceRegistered_Functionality();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_MoreDeviceRegistered_Functionality())");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
	 		}
		/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_060 to 063
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_D2G_060()
	 		{
	 			try
	 	 		{ 	
	 				Fn_OnDemand_Download_FlyOver();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_OnDemand_Download_FlyOver()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_D2G_064 to 071
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_IPVOD_064()
	 		{
	 			try
	 	 		{ 	
	 				Fn_DownloadScreen_EditImageCheck();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_DownloadScreen_EditImageCheck()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	/*=====================================================================================================
	 		* Author: Chandhini
	 		* Module : D2G
	 		* Test Cases :Auto_IPVOD_072 and73
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_IPVOD_072()
	 		{
	 			try
	 	 		{ 	
	 				
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_MyLibrary_DownloadsCheck()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 
	 		/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand
			* Test Cases : Auto_OnDemand_001,Auto_OnDemand_002
			=====================================================================================================*/
			public void Auto_OnDemand_001()
			{
				try
		 		{ 
					if(super.Fn_Login())
						Fn_OnDemand_NetworksBrowseAll();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Auto_OnDemand_001()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					EnjoyingAppHandler();
					tearDown();
		    	}
				
			}
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand
			* Test Cases : Auto_OnDemand_003
			=====================================================================================================*/
			public void Auto_OnDemand_003()
			{
				try
		 		{ 
					Fn_OnDemand_PremiumBrowseAll();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Auto_OnDemand_003()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
					EnjoyingAppHandler();
		    	}
				
			}
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand
			* Test Cases : Auto_OnDemand_004
			=====================================================================================================*/
			public void Auto_OnDemand_004()
			{
				try
		 		{ 
					Fn_OnDemand_WelcomePopOver();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Auto_OnDemand_003()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
		
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand
			* Test Cases : Auto_OnDemand_005,Auto_OnDemand_006,Auto_OnDemand_007
			=====================================================================================================*/
			public void Auto_OnDemand_005()
			{
				try
		 		{ 
					Fn_OnDemand_Customize();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Auto_OnDemand_004()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
			
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand
			* Test Cases : Auto_OnDemand_008
			=====================================================================================================*/
			public void Auto_OnDemand_008()
			{
				try
		 		{ 
					Fn_OnDemand_Default_PanelCheck();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Fn_OnDemand_Default_PanelCheck()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand
			* Test Cases : Auto_OnDemand_009
			=====================================================================================================*/
			public void Auto_OnDemand_009()
			{
				try
		 		{ 
					Fn_OnDemand_Movies_BrowseAll();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Fn_OnDemand_Movies_BrowseAll()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
		
				
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand
			* Test Cases : Auto_OnDemand_010
			=====================================================================================================*/
			public void Auto_OnDemand_010()
			{
				try
		 		{ 
					Fn_OnDemand_Movies_FeatureBanner_Check();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Fn_OnDemand_FeatureBannerCheck()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand
			* Test Cases : Auto_OnDemand_011
			=====================================================================================================*/
			public void Auto_OnDemand_011()
			{
				try
		 		{ 
					Fn_OnDemand_TV_FeatureBanner_Check();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Fn_OnDemand_FeatureBannerCheck()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand
			* Test Cases : Auto_OnDemand_012
			=====================================================================================================*/
			public void Auto_OnDemand_012()
			{
				try
		 		{ 
					Fn_OnDemand_TV_PanelCheck();
					
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Fn_OnDemand_TV_PanelCheck()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand 
			* Test Cases : Auto_OnDemand_013
			=====================================================================================================*/
			public void Auto_OnDemand_013()
			{
				try
		 		{ 
					
					Fn_OnDemand_TV_BrowseAll();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Fn_OnDemand_Network_FeatureBanner_Check()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand 
			* Test Cases : Auto_OnDemand_014
			=====================================================================================================*/
			public void Auto_OnDemand_014()
			{
				try
		 		{ 
					
					Fn_OnDemand_Network_FeatureBanner_Check();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Fn_OnDemand_Network_FeatureBanner_Check()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
			/*=====================================================================================================
			* Author: Chandhini_A01
			* Module : On Demand 
			* Test Cases : Auto_OnDemand_015
			=====================================================================================================*/
			public void Auto_OnDemand_015()
			{
				try
		 		{ 
					
					Fn_OnDemand_Premiums_FeatureBanner_Check();
		 		}
		 		catch(Exception e)
		    	{
		    		System.err.println("Error in Fn_OnDemand_Premiums_FeatureBanner_Check()");
					System.err.println("	Message : "+e.getMessage());
					System.err.println("	Cause : "+e.getCause());
					e.printStackTrace();
					tearDown();
		    	}
				
			}
			/*=====================================================================================================
			* Author: Chandhini
			* Module : On Demand
			* Test Cases : Auto_OnDemand_016
		 	====================================================================================================*/
		 		
		 		@Test(groups = {"seetest"})
		 	    public  void Auto_OnDemand_016()
		 		{
		 			try
		 	 		{ 	
		 				Fn_OnDemand_Networks_NetworkLogo_Check();
		 	 		}
		 	 		catch(Exception e)
		 	    	{
		 	    		System.err.println("Error in Fn_OnDemand_Networks_NetworkLogo_Check()");
		 				System.err.println("	Message : "+e.getMessage());
		 				System.err.println("	Cause : "+e.getCause());
		 				e.printStackTrace();
		 				tearDown();
		 	    	}	
			}
			/*=====================================================================================================*/
		 	
		 	/*=====================================================================================================
		 		* Author: Chandhini
		 		* Module : On Demand
		 		* Test Cases : Auto_OnDemand_017
		 	====================================================================================================*/
		 		@Test(groups = {"seetest"})
		 	    public  void Auto_OnDemand_017()
		 		{
		 			try
		 	 		{ 	
		 				Fn_OnDemand_AssetPopOver_NetworkLogo_Check();
		 	 		}
		 	 		catch(Exception e)
		 	    	{
		 	    		System.err.println("Error in Fn_OnDemand_AssetPopOver_NetworkLogo_Check()");
		 				System.err.println("	Message : "+e.getMessage());
		 				System.err.println("	Cause : "+e.getCause());
		 				e.printStackTrace();
		 				tearDown();
		 	    	}	
			}
			/*=====================================================================================================*/
		 		
		 	/*=====================================================================================================
		 		* Author: Chandhini
		 		* Module : On Demand
		 		* Test Cases : Auto_OnDemand_018,Auto_OnDemand_019
		 	====================================================================================================*/
		 		@Test(groups = {"seetest"})
		 	    public  void Auto_OnDemand_018()
		 		{
		 			try
		 	 		{ 	
		 				Fn_OnDemand_Premiums_AssetPopOverCheck();
		 	 		}
		 	 		catch(Exception e)
		 	    	{
		 	    		System.err.println("Error in Fn_OnDemand_Premiums_AssetPopOverCheck()");
		 				System.err.println("	Message : "+e.getMessage());
		 				System.err.println("	Cause : "+e.getCause());
		 				e.printStackTrace();
		 				tearDown();
		 	    	}	
			}
			/*=====================================================================================================*/
		
		 	/*=====================================================================================================
		 		* Author: Chandhini
		 		* Module : On Demand
		 		* Test Cases : Auto_OnDemand_020
		 	====================================================================================================*/
		 		@Test(groups = {"seetest"})
		 	    public  void Auto_OnDemand_020()
		 		{
		 			try
		 			{
		 				Fn_OnDemand_Premiums_BrowseAll_Sort();
		 	 		}
		 	 		catch(Exception e)
		 	    	{
		 	    		System.err.println("Error in Fn_OnDemand_Premiums_BrowseAll_Sort()");
		 				System.err.println("	Message : "+e.getMessage());
		 				System.err.println("	Cause : "+e.getCause());
		 				e.printStackTrace();
		 				tearDown();
		 	    	}	
			}
			/*=====================================================================================================*/
		 		/*=====================================================================================================
		 		* Author: Chandhini
		 		* Module : On Demand
		 		* Test Cases : Auto_OnDemand_021,Auto_OnDemand_022,Auto_OnDemand_023
		 	====================================================================================================*/
		 		@Test(groups = {"seetest"})
		 	    public  void Auto_OnDemand_021()
		 		{
		 			try
		 	 		{ 	
		 				Fn_OnDemand_TV_Customize();
		 	 		}
		 	 		catch(Exception e)
		 	    	{
		 	    		System.err.println("Error in Fn_OnDemand_Premiums_BrowseAll_Sort()");
		 				System.err.println("	Message : "+e.getMessage());
		 				System.err.println("	Cause : "+e.getCause());
		 				e.printStackTrace();
		 				tearDown();
		 	    	}	
			}
			/*=====================================================================================================*/
		 	/*=====================================================================================================
		 		* Author: Chandhini
		 		* Module : On Demand
		 		* Test Cases :Auto_OnDemand_024
		 	====================================================================================================*/
		 		@Test(groups = {"seetest"})
		 	    public  void Auto_OnDemand_024()
		 		{
		 			try
		 	 		{ 	
		 				Fn_OnDemand_Rearrange_UpArrow();
		 	 		}
		 	 		catch(Exception e)
		 	    	{
		 	    		System.err.println("Error in Fn_OnDemand_Rearrange_UpArrow()");
		 				System.err.println("	Message : "+e.getMessage());
		 				System.err.println("	Cause : "+e.getCause());
		 				e.printStackTrace();
		 				tearDown();
		 	    	}	
			}
		 	/*=====================================================================================================*/
		 	/*=====================================================================================================
		 		* Author: Chandhini
		 		* Module : On Demand
		 		* Test Cases :Auto_OnDemand_025,026
		 	====================================================================================================*/
		 		@Test(groups = {"seetest"})
		 	    public  void Auto_OnDemand_025()
		 		{
		 			try
		 	 		{ 	
		 				Fn_OnDemand_WatchOption();
		 	 		}
		 	 		catch(Exception e)
		 	    	{
		 	    		System.err.println("Error in Fn_OnDemand_Rearrange_UpArrow()");
		 				System.err.println("	Message : "+e.getMessage());
		 				System.err.println("	Cause : "+e.getCause());
		 				e.printStackTrace();
		 				tearDown();
		 	    	}	
			}	
			/*=====================================================================================================*/
		 	/*=====================================================================================================
		 		* Author: Chandhini
		 		* Module : On Demand
		 		* Test Cases :Auto_OnDemand_027,028
		 	====================================================================================================*/
		 		@Test(groups = {"seetest"})
		 	    public  void Auto_OnDemand_027()
		 		{
		 			try
		 	 		{ 	
		 				Fn_WatchOn_Tv_IPad_PanelCheck();
		 	 		}
		 	 		catch(Exception e)
		 	    	{
		 	    		System.err.println("Error in Fn_OnDemand_Rearrange_UpArrow()");
		 				System.err.println("	Message : "+e.getMessage());
		 				System.err.println("	Cause : "+e.getCause());
		 				e.printStackTrace();
		 				tearDown();
		 	    	}	
			}	
			/*=====================================================================================================*/
		 
	 		/*=====================================================================================================
	 		* Author: Venkat
	 		* Module : Watchlist
	 		* Test Cases : Add_1Serieswith2Episodes_Watchlist_001(), Add_1Serieswith2Episodes_Watchlist_002(), Add_1Serieswith2Episodes_Watchlist_003()
	 		* Add_1Serieswith2Episodes_Watchlist_004(), Add_1Serieswith2Episodes_Watchlist_005(), Add_1Serieswith2Episodes_Watchlist_006()
	 		* 	1) Adding Watchlist item for TV Series first Episode
	 		* 	2) Verification for Added TV Series First Episode Name in My Library Screen under Watchlist Section
	 		* 	3) Verification for Added TV Series First Episode Name in Watchlist Screen
	 		* 	4) Adding Watchlist item for TV Series for Second Episode
	 		* 	5) Verification for the Added TV Series for first and Second Episodes under Watchlist Rollup Modal through My Library Screen
	 		* 	6) Verification for the Added TV Series for first and Second Episodes under Watchlist Rollup Modal through Watchlist Screen
	 	=====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Add_1Serieswith2Episodes_Watchlist_001()
	 		{
	 			try
	 	 		{ 	
	 				super.Fn_Com_Navigate("	OnDemand");
	 	 			Fn_Validate_AddWatchlist_1Serieswith2Episodes();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Add_1Serieswith2Episodes_Watchlist_001");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	
	 	/*=====================================================================================================
	 		* Author: Venkat
	 		* Module : Watchlist
	 		* Test Cases : Delete_1Serieswith2Episodes_Watchlist_001(), Delete_1Serieswith2Episodes_Watchlist_002()
	 		* 	1) Deleting the Watchlist item for TV series with 2 Episodes through Rollup Modal popover
	 		* 	2) Verification for deleted Watchlist item for TV series with 2 Episodes in My Library Screen under Watchlist Section
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Delete_1Serieswith2Episodes_Watchlist_008()
	 		{
	 			try
	 	 		{ 	
	 	 			Fn_Validate_DeleteWatchlist_1Serieswith2Episodes();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Delete_1Serieswith2Episodes_Watchlist_014");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/	
	 	
	 	/*=====================================================================================================
	 		* Author: Venkat
	 		* Module : Watchlist
	 		* Test Cases : Add_IPVODAsset_Watchlist_001(), Add_IPVODAsset_Watchlist_002(), Add_IPVODAsset_Watchlist_003()
	 		* 	1) Adding the Watchlist item for IPVOD Asset
	 		* 	2) Verification for Added VOD Watchlist in My Library Screen under Watchlist Section
	 		* 	3) Verification for Added VOD Watchlist in Watchlist Screen
	 	=====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Add_IPVODAsset_Watchlist_011()
	 		{
	 			try
	 	 		{ 	
	 	 			Fn_Validate_AddWatchlist_IPVODAsset();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Add_IPVODAsset_Watchlist_008");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 	
	 	/*=====================================================================================================
	 		* Author: Venkat
	 		* Module : Watchlist
	 		* Test Cases : Delete_IPVODAsset_Watchlist_001(), Delete_IPVODAsset_Watchlist_002(), Delete_IPVODAsset_Watchlist_003()
	 		* 	1) Deleting the Watchlist item for IPVOD Asset in Watchlist Screen
	 		* 	2) Verification for deleted IPVOD Asset in My Library Screen under Watchlist Section
	 		* 	3) Verification for deleted IPVOD Asset in Watchlist Screen.
	 	=====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Delete_IPVODAsset_Watchlist_014()
	 		{
	 			try
	 	 		{ 	
	 	 			Fn_Validate_DeleteWatchlist_IPVODAsset();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Delete_IPVODAsset_Watchlist_011");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 		
	 	
	 		
	 	/*=====================================================================================================
	 		* Author: Venkat
	 		* Module : Watchlist
	 		* Test Cases : 
	 		* 	1) Adding the Watchlist item for Movie Asset
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Add_MovieAsst_Watchlist_017()
	 		{
	 			try
	 	 		{ 	
	 	 			Fn_Validate_AddWatchlist_MovieAsset();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Add_MovieAsst_Watchlist_017");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
		/*=====================================================================================================*/
	 		
	 	/*=====================================================================================================
	 		* Author: Venkat
	 		* Module : Watchlist
	 		* Test Cases : Delete_MovieAsset_Watchlist_001(), Delete_MovieAsset_Watchlist_002()
	 		* 	1) Deleting the Watchlist item for Movie Asset through Asset Details Screen
	 		* 	2) Verification for deleted Movie Asset in Watchlist Screen.
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Delete_MovieAsset_Watchlist_018()
	 		{
	 			try
	 	 		{ 	
	 	 			Fn_Validate_DeleteWatchlist_MovieAsset();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Delete_MovieAsset_Watchlist_018");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
		}
	 		/*=====================================================================================================
	 		* Author: Venkat
	 		* Module : Watchlist
	 		* Test Cases : Delete_MovieAsset_Watchlist_001(), Delete_MovieAsset_Watchlist_002()
	 		* 	1) Deleting the Watchlist item for Movie Asset through Asset Details Screen
	 		* 	2) Verification for deleted Movie Asset in Watchlist Screen.
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_SportsZone_001()
	 		{
	 	 		try
 				{
// 					if(ST.NativeElementFound(ObjectCollection.get("Home_TitleBar").toString(), client))
//	  					super.Fn_LogOut();
					if(super.Fn_Login())
					{
						if(this.Fn_Com_Navigate("Home_SportZone"))
							Fn_SportZone_OutofHome();
 						else
							System.out.println("Navigation Failed - SportZone"); 
					}
 					else
						System.out.println("Loggin Failed - SportZone");
 					
 				}
 				catch(Exception Ex)
 				{
 					System.err.println("Error in Auto_SportZone_001");
 					System.err.println("	Message : "+Ex.getMessage());
 					System.err.println("	Cause : "+Ex.getCause());
 					Ex.printStackTrace();
 				}
	 		}
	 		/*=====================================================================================================
	 		* Author: Venkat
	 		* Module : Watchlist
	 		* Test Cases : Delete_MovieAsset_Watchlist_001(), Delete_MovieAsset_Watchlist_002()
	 		* 	1) Deleting the Watchlist item for Movie Asset through Asset Details Screen
	 		* 	2) Verification for deleted Movie Asset in Watchlist Screen.
	 	====================================================================================================*/
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_VideoPlayer_001()
	 		{
	 			try
	 	 		{ 	
	 				videoPlayer();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_SportZone()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
	 		}
	 		
	 		@Test(groups = {"seetest"})
	 	    public  void Auto_VideoPlayer_004()
	 		{
	 			try
	 	 		{ 	
	 				videoPlayer4();
	 	 		}
	 	 		catch(Exception e)
	 	    	{
	 	    		System.err.println("Error in Fn_SportZone()");
	 				System.err.println("	Message : "+e.getMessage());
	 				System.err.println("	Cause : "+e.getCause());
	 				e.printStackTrace();
	 				tearDown();
	 	    	}	
	 		}
	 		
	 		
	 		
	 		public  void Auto_AssetDetails_FullScreen_001()
	  		{
	  			try
	  	 		{
//	  				super.Fn_LogOut();
	  				if(super.Fn_Login())
	  				{
	  					if(super.Fn_Com_Navigate("Home_Guide"))
			  				Fn_Validate_AssetDetailsFullScreen();
		  				else
		  					System.out.println("Not Navigated");
	  				}
	  			}
	  	 		catch(Exception e)
	  	    	{
	  	    		System.err.println("Error in Watchlist_006");
	  				System.err.println("	Message : "+e.getMessage());
	  				System.err.println("	Cause : "+e.getCause());
	  				e.printStackTrace();
	  				tearDown();
	  	    	}	
	  		}
	 		 
	 		 
	 		  	public  void Auto_AssetDetails_Flyover_058()
		 		{
		 			try
		 	 		{ 	
		 	 			Fn_Validate_FlyoverAssetDetails();
		 	 		}
		 	 		catch(Exception e)
		 	    	{
		 	    		System.err.println("Error in Auto_Flyover_AssetDetails_058");
		 				System.err.println("	Message : "+e.getMessage());
		 				System.err.println("	Cause : "+e.getCause());
		 				e.printStackTrace();
		 				tearDown();
		 	    	}	
		 		}
	 		  	
	 		  	public void Auto_SportZone_008()
	 			{
	 				try
	 				{
//	 					if(ST.NativeElementFound(ObjectCollection.get("Home_TitleBar").toString(), client))
//	 						super.Fn_LogOut();
						if(super.Fn_Login())
						{
							if(this.Fn_Com_Navigate("Home_SportZone"))
								Fn_SportZone_InHome();
	 						else
								System.out.println("Navigation Failed - SportZone"); 
						}
	 					else
							System.out.println("Loggin Failed - SportZone");
	 					
	 				}
	 				catch(Exception Ex)
	 				{
	 					System.err.println("Error in Auto_SportZone_001");
	 					System.err.println("	Message : "+Ex.getMessage());
	 					System.err.println("	Cause : "+Ex.getCause());
	 					Ex.printStackTrace();
	 				}
	 			}
}



