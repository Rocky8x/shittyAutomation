package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageHomeMenu {
	public static Element getAppVerTextview() {
		String id = "text_app_version";
		return new Element(By.id(String.format(id))).setDescription("App Version text view");
	};
	
	public static Element getSettingTextview() {
		String tvSetting = "//android.widget.TextView[@text='Settings']";
		return new Element(By.xpath(tvSetting)).setDescription("Settings text view");
	}	
	
	public static Element getHomeButton() {
		String id = "account_setting_home";
		return new Element(By.id(id)).setDescription("Home button");
	}
	
	public static Element getProfileTextView() {
		String tvProfile = "//android.widget.TextView[@text='User Profile']";
		return new Element(By.xpath(tvProfile)).setDescription("Profile text view");
	}
	
	public static Element getDevicesTextView() {
		String tvDevices = "//android.widget.TextView[@text='Devices']";
		return new Element(By.xpath(tvDevices)).setDescription("Devices text view");
	}
	
	public static Element getGrantAccestTextView() {
		String grantAcc = "//android.widget.TextView[@text='Grant Access']";
		return new Element(By.xpath(grantAcc)).setDescription("Grant Access text view");
	}
	
	public static Element getDashBoardTextView() {
		String dashboard = "//android.widget.TextView[contains(@resource-id,'account_setting_title') and @text='Dashboard']";
		return new Element(By.xpath(dashboard)).setDescription("Dashboard text view");
	}
	
	public static Element getDoNotDisturbTextBtn() {
		String notifyTextView = "//android.widget.TextView[@text='Do Not Disturb']";
		return new Element(By.xpath(notifyTextView)).setDescription("Do Not Disturb button in Home menu");
	}
	
	public static Element getHelpAndSupportTextView() {
		String helpTextView = "//android.widget.TextView[@text='Help & Support']";
		return new Element(By.xpath(helpTextView)).setDescription("Help & Support text view");
	}
	
	public static Element getReportIssueFeedBackTextView() {
		String feedbackTv = "//android.widget.TextView[@text='Report Issue/Feedback']";
		return new Element(By.xpath(feedbackTv)).setDescription("Report Issue/Feedback text view");
	}
	
	public static Element getPreviewModeSwitch() {
		String id = "account_setting_switch";
		return new Element(By.id(id)).setDescription("Preview Mode switch");
	}
	
	public static Element getDevicesOrderTextView() {
		String deviceOrder = "//android.widget.TextView[@text='Device Order']";
		return new Element(By.xpath(deviceOrder)).setDescription("Device order text view");
	}

	public static Element getManageCloudStorageTextView() {
		String manageStorage = "//android.widget.TextView[@text='Manage Cloud Storage']";
		return new Element(By.xpath(manageStorage)).setDescription("Manage Cloud Storage text view");
	}
	
	public static Element getCloudStoragePlanTextView() {
		String storagePlan = "//android.widget.TextView[@text='Cloud Storage Plan']";
		return new Element(By.xpath(storagePlan)).setDescription("Cloud Storage Plan text view");
	}
	
	public static Element getSignOutMenuItem(){ 
		String xpath = "//android.widget.TextView[@text='Sign Out']";
		return new Element(By.xpath(xpath)).setDescription("Sign out button in home menu"); 
	}
	
	public static void enableDebug() {
		for (int i=0; i<10; i++) {
			getAppVerTextview().click();
		}		
	}	
	
	public static void gotoHomeSetingPage() {
		getSettingTextview().click();
	}
	
	public static PageDashboard exitPage() {
		getHomeButton().click();
		return new PageDashboard();
	}
	
	public static void gotoHomeProfilePage() {
		getProfileTextView().click();
	}
	
	public static void gotoGrantAccessPage() {
		getDevicesTextView().click();
		getGrantAccestTextView().click();
	}
	
	public static void clickOnHomeDashboardTextView() {
		getDashBoardTextView().click();
	}
	
	public static void enablePreviewMode(boolean value) {
		getPreviewModeSwitch().setValue(value);
	}
	
	public static boolean verifyPreviewEnable(boolean value) {
		return getPreviewModeSwitch().getAttribute("checked").equals(value + "");
	}
	
	public static void gotoNotificationPage() {
		getDevicesTextView().click();
		getDoNotDisturbTextBtn().click();
	}
	
	public static void gotoFeedbackPage() {
		getHelpAndSupportTextView().click();
		getReportIssueFeedBackTextView().click();
	}
	
	public static void gotoDevicesOrderpage() {
		getDevicesOrderTextView().click();
	}
	
	public static PageManageCloudStorage gotoDevices_ManageCloudStoragePage() {
		getDevicesTextView().click();
		getManageCloudStorageTextView().click();
		return new PageManageCloudStorage();
	}
	
	public static void gotoCloudStoragePlanPage() {
		getDevicesTextView().click();
		getCloudStoragePlanTextView();
	}
}
