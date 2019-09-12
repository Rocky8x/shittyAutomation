package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageHomeSetting extends PageBase{

	public static Element getDebugInfoSw() {
		String id = "sw_debug_info";
		return new Element(By.id(id)).setDescription("Show debug info switch");
	}
	
	public static Element getCelsiusUnit() {
		String id = "textview_temperature_c";
		return new Element(By.id(id)).setDescription("C temprature");
	}
	
	public static Element getFahrenheitUnit() {
		String id = "textview_temperature_f";
		return new Element(By.id(id)).setDescription("F temprature");
	}
	
	public static Element getPreviewSwitch() {
		String id = "use_hw_decode_sw";
		return new Element(By.id(id)).setDescription("Preview option switch");
	}
	
	public static Element getNaturalPtzSwitch() {
		String id = "sw_natural_ptz_direction";
		return new Element(By.id(id)).setDescription("Natural PTZ switch");
	}
	
	public static Element getBgAudioMonitoringSwitch() {
		String id = "sw_background_monitoring";
		return new Element(By.id(id)).setDescription("Background audio monitoring switch");
	}
		
	public static AudioMonitoringDialog focusAudioMonitoringDialog() {
		return new AudioMonitoringDialog();
	}
	
	public static class AudioMonitoringDialog {
		public static final String TITLE = "Background Audio Monitoring";
		public static final String MESSAGE= "In order to stay connected in the background, Background Audio Monitoring need to be in the whitelist of \"Battery Optimization\". Set KODAK Smart Home app to \"Do not optimized\" state.";
		
		public static Element getTitle() {
			String id = "text_dialog_title";
			return new Element(By.id(id)).setDescription("Title of Background Audio Monitoring Dialog");

		}
		
		public static Element getMessage() {
			String id = "text_dialog_msg";
			return new Element(By.id(id)).setDescription("Message of Background Audio Monitoring Dialog");

		}
		
		public static Element getNotNowBtn() {
			String xpathNotNowBtn = "//android.widget.Button[@text='Not now']";
			return new Element(By.xpath(xpathNotNowBtn)).setDescription("\'Not now\' button");

		}
		
		public static Element getOkBtn() {
			String xpathOkButton = "//android.widget.Button[@text='OK']";
			return new Element(By.xpath(xpathOkButton)).setDescription("\'OK\' button");

		}

	}
	
	public static Element getBackgroundAudioMonitoringArrowBtn() {
		String id = "img_background_monitoring_forward";
		return new Element(By.id(id)).setDescription("Arrow button to go to BAM setting");
	}
	
	public static void enableShowDebugInfo() {
		// check if the switch exist
		if (getDebugInfoSw().getWebElement() !=null) {
			getDebugInfoSw().setValue(true);			
		} else {
			exitPage();
			PageDashboard.handlePermissionsAndHintsWhenPageOpen(); 
			PageDashboard.gotoHomeMenuPage();
			PageHomeMenu.enableDebug();
			PageHomeMenu.gotoHomeSetingPage();
			getDebugInfoSw().setValue(true);
		}
	}
	
	public static Element getAllowBtn() {
		String xpath = "//android.widget.Button[@text='ALLOW']";
		return new Element(By.xpath(xpath)).setDescription("Allow button");
	}
	
	public static Element getDenyBtn() {
		String xpath = "//android.widget.Button[@text='ALLOW']";
		return new Element(By.xpath(xpath)).setDescription("Deny button");
	}
	
	public static void clickCelsiusUnit() {
		getCelsiusUnit().click();
	}
	
	public static void clickFahrenheitUnit() {
		getFahrenheitUnit().click();
	}
	
	public static void enablePreviewMode(boolean value) {
		if(getPreviewSwitch().getAttribute("checked").equals("false") && value == true) {
			getPreviewSwitch().setValue(value);
			clickConfirmButton();
		}else {
			getPreviewSwitch().setValue(value);
		}
	}
	
	public static boolean verifyPreviewMode(String value) {
		return getPreviewSwitch().getText().toUpperCase().equals(value.toUpperCase());
	}
}
