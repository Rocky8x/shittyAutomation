package android.kodak.object;

import static org.testng.Assert.assertEquals;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageBAMSetting {
	
	public static final String DESCRIPTION = "While background monitoring is On, you can listen to your camera even if the app is at the background. To activate, goto video screen. Once you see the live stream, you can go to home screen to activate.";
	public static final String NOTE = "Note: This option can be enabled only when camera and mobile device are both in the same Wi-Fi network connection. Sometimes, app could not detect the camera in the same local network. You may need to kill the app, then run again, so that the app can try to locate the camera in the local network."; 
	public static final String BAM_TIME_DESCRIPTION = "Set how long you would like to keep Background audio active";
	
	public static Element getOnOffSwitch() {
		String id = "switch_bg_monitoring";
		return new Element(By.id(id)).setDescription("BAM on-off switch");
	}
	
	public static Element getIncreaseTimeBtn() {
		String id = "number_picker_up";
		return new Element(By.id(id)).setDescription("Increase BAM time");
	}

	public static Element getDecreaseTimeBtn() {
		String id = "number_picker_down";
		return new Element(By.id(id)).setDescription("Decrease BAM time");
	}
	
	public static Element getDescriptionTextview() {
		String id = "text_bg_monitoring_desc";
		return new Element(By.id(id)).setDescription("BAM description");
	}
	
	public static Element getTimeOptionDescription() {
		String id = "text_bg_monitoring_duration_desc";
		return new Element(By.id(id)).setDescription("Time option description");
	}
	
	public static Element getTimeTextbox() {
		String id = "number_picker_value";
		return new Element(By.id(id)).setDescription("BAM active time");
	}
	
	public static Element getNoteTextview() {
		String id = "text_bg_monitoring_activated_condition";
		return new Element(By.id(id)).setDescription("BAM note about how to use the feature");
	}
	
	public static void enableBAM() {
		getOnOffSwitch().setValue(true);
	}
	
	public static void disableBAM() {
		getOnOffSwitch().setValue(false);
	}
	
	public static void verifyBAMDescription () {
		assertEquals(DESCRIPTION, getDescriptionTextview().getText());
	}
	
	public static void verifyBAMTimeDescription() {
		assertEquals(BAM_TIME_DESCRIPTION, getTimeOptionDescription().getText());
	}
	
	public static void verifyBAMNote() {
		assertEquals(NOTE, getNoteTextview().getText());
	}
	
	public static void increaseTime () {
		getIncreaseTimeBtn().click();
	}
	
	public static void decreaseTime () {
		getDecreaseTimeBtn().click();
	}
	
	public static String getCurrentTimeSetting() {
		return getTimeTextbox().getText();
	}
	
	public static void exitPage() {
		String xpath = "imgMain";
		new Element(By.id(xpath)).setDescription("Exit page").click();
	}
}
