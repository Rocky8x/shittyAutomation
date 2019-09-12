package ios.kodak.testcases.checklist;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageTimeline;
import mobile.kodak.base.TestBaseIOS;

public class MEDIA05_FilterEvents extends TestBaseIOS{

	@Test(priority = 30, description = "In Timeline tab, filter events by own devices and shared devices")
	public void filterEventsInTimeline() {
		int count = 0;
		
		// Check and sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		List<String> listDevices = PageDashboard.getListDeviceNameInDashboard();
		PageDashboard.gotoTimelinePage();
		
		// Filter by device and verify result in timeline
		for(String device : listDevices) {
			PageTimeline.filterEventByDevice(device);
			// Verify there are no event in device
			if(PageTimeline.vefifyNoEventInTimelinePage()) {
				count ++;
			}else {
				// Get event in timeline and verify
				boolean rs = PageTimeline.verifyFilterEventByDeviceName(device);
				Assert.assertTrue(rs, "Filter events by device: " + device + " wrong.");
			}
		}
		if (count == listDevices.size()) {
			Assert.fail("There are no event detected from device");
		}
	}
}
