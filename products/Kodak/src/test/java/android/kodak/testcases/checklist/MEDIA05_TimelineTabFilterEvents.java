package android.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import android.kodak.object.PageBase;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageTimeline;
import mobile.kodak.base.TestBaseAndroid;

public class MEDIA05_TimelineTabFilterEvents extends TestBaseAndroid {

	@Test(priority = 11, description = "In Timeline tab, filter events by own devices and shared devices")
	public void verifyFilterEventsByOwnDevice() {
		int count = 0;
		
		PageGetStart.checkAndSignin(c_username, c_password);
		PageBase.gotoToTimelinePage();
		PageTimeline.clickFilterButton();
		List<String> listDevice = PageTimeline.getListDevices();
		
		// Verify clear filter function 
		// Tap clear filter then Aplly -> all device will be select again after tap Apply
		PageTimeline.clickClearFilter();
		PageTimeline.clickDoneFilter();
		PageTimeline.clickFilterButton();
		for(String deviceName : listDevice) {
			Assert.assertTrue(PageTimeline.verifyDeviceSeleted(deviceName), "Device name should not selected.");
		}
		PageTimeline.clickDoneFilter();
		
		// Verify select device filter: clear filter then select a devices
		for(String deviceName : listDevice) {
			PageTimeline.clickFilterButton();
			PageTimeline.clickClearFilter();
			// Select device filter and verify filter selected
			PageTimeline.selectDeviceFilterByName(deviceName);
			Assert.assertTrue(PageTimeline.verifyDeviceSeleted(deviceName), "Device name should seleted.");
			PageTimeline.clickDoneFilter();
			// Verify events in time line
			if(PageBase.verifyEmptyEvent()) {
				count++;
			}else {
				Assert.assertTrue(PageTimeline.verifyDetectionByDeviceName(deviceName), String.format("Filter by device: %s wrong.", deviceName));
			}
		}
		if(count == listDevice.size()) {
			Assert.fail("There are no event detect from device");
		}
	}
	
	// @TBD
	public void filterEventsBySharedDevcies() {
		
	}
}
