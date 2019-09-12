package ios.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;
import com.cinatic.driver.DriverManager;
import com.cinatic.element.Element;
import com.cinatic.log.Log;

import io.appium.java_client.MobileBy;

public class PageTestFlight extends PageBase{

	/*
	 * ELEMENTS
	 */
	
	public static Element getUpdateBtn() {
		String btn = "//XCUIElementTypeButton[@name='UPDATE']";
		return new Element(By.xpath(btn)).setDescription("Update button");
	}
	
	public static Element getAppVersionField() {
		String field = "type=='XCUIElementTypeStaticText' AND !name BEGINSWITH 'Expires' AND !name==null";
		return new Element(MobileBy.iOSNsPredicateString(field)).setDescription("App version");
	}
	
	public static Element getOpenButton() {
		return new Element(By.name("OPEN"), 300).setDescription("Open button");
	}
	
	public static Element getKodakApp() {
		String app = "type=='XCUIElementTypeCell' AND name=='KODAK Smart Home'";
		return new Element(MobileBy.iOSNsPredicateString(app)).setDescription("Kodak app");
	}
	
	public static Element getPreviousBuildEle() {
		return new Element(By.name("Previous Builds")).setDescription("Previous Builds");
	}
	
	public static List<WebElement> getListBuilds(String build){
		String lstBuild = "type=='XCUIElementTypeStaticText' AND name BEGINSWITH '%s'";
		return DriverManager.getDriver().findElements(MobileBy.iOSNsPredicateString(String.format(lstBuild, build)));
	}
	
	public static Element getInstallBtn() {
		return new Element(By.name("INSTALL")).setDescription("INSTALL");
	}
	/*
	 * ACTIONS
	 */
	
	public static String getAppVersion() {
		return getAppVersionField().getText();
	}
	
	public static void clickUpdateApp() {
		getUpdateBtn().click();
	}
	
	public static void clickOpenApp() {
		getOpenButton().click();
	}
	
	public static boolean updateKodakApp(String build) {
		boolean rs = false;
		getKodakApp().click();
		getPreviousBuildEle().click();
		TimeHelper.sleep(5000);
		String builds = StringHelper.getStringByString(build, "", ".", true);
		System.out.println(builds);
		List<WebElement> lstBuild = getListBuilds(builds);
		lstBuild.get(0).click();
		TimeHelper.sleep(15000);
		String currentBuild = getAppVersion();

		if (compareVersions(currentBuild, build) > 0 && getInstallBtn().getWebElement() != null) {
			Log.info("The new version is available on TestFlight: " + currentBuild);
			getInstallBtn().click();
			rs = true;
		}else {
			Log.info("There is no new version, the current version on TestFlight is: " + currentBuild);
		}
		clickOpenApp();
		return rs;
	}
	
	private static int compareVersions(String v1, String v2) {
	    String[] components1 = v1.split("\\.|\\)|\\(");
	    String[] components2 = v2.split("\\.|\\)|\\(");
	    int length = Math.min(components1.length, components2.length);
	    for(int i = 0; i < length; i++) {
	        int result = new Integer(components1[i].trim()).compareTo(Integer.parseInt(components2[i].trim()));
	        if(result != 0) {
	            return result;
	        }
	    }
	    return Integer.compare(components1.length, components2.length);
	}
}
