package android.kodak.testcases.checklist;

import static org.testng.Assert.assertTrue;

import java.util.Random;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.cinatic.ApiHelper;
import com.cinatic.object.Terminal;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageStreamView;
import mobile.kodak.base.TestBaseAndroid;

public class MEL02_PlayMelodyWithTalkbackOn extends TestBaseAndroid{
	String deviceName ;
	Terminal camera1Terminal;
	@BeforeMethod
	public void prepare() throws Exception {
		ApiHelper apiHelper = new ApiHelper();
		apiHelper.userLogin(c_username, c_password);
		apiHelper.getDevices();
		deviceName = apiHelper.getDeviceByDeviceId(c_deviceid).getName();
		
		camera1Terminal  = new Terminal(c_comport);
	}
	
	@Test(priority = 20)
	public void playMelodyWhileTalkbackOn() {
		int randomMelody = (new Random()).nextInt(5) +1;

		PageDashboard.selectDeviceToView(deviceName);

		PageStreamView.enableTalkBack();
		assertTrue(PageStreamView.getTalkBackEnableImg().getWebElement() != null);
		PageStreamView.getMelodyImage().click();
		assertTrue(PageStreamView.getMelodyByName(randomMelody).getWebElement() == null);
		
		assertTrue(PageStreamView.getMuteSoundImage().getWebElement() != null);
		assertTrue(PageStreamView.getUnMuteSoundImage().getWebElement() == null);	
		
		PageStreamView.getMuteSoundImage().click();
		
		assertTrue(PageStreamView.getMuteSoundImage().getWebElement() != null);
		assertTrue(PageStreamView.getUnMuteSoundImage().getWebElement() == null);
		
		PageStreamView.disableTalkBack();
		PageStreamView.exitPage();
		
	}
	
	@Test(priority = 21)
	public void talkbackWhileMelodyOn() {
		int randomMelody = (new Random()).nextInt(5) +1;

		PageDashboard.selectDeviceToView(deviceName);
		
		PageStreamView.getMenuImage().click();
		
		PageStreamView.getMelodyImage().click();
		
		PageStreamView.playMelody(randomMelody);
		PageStreamView.getCancelMelodyButton().click();
		
		assertTrue(PageStreamView.getMelodyEnabledImage().getWebElement() != null);
		assertTrue(PageStreamView.getMelodyImage().getWebElement() == null);
		
		PageStreamView.getTalkBackDisabledImg().click();
		camera1Terminal.getTeratermLog().contains("P2P Talkback state 1");
		assertTrue(PageStreamView.getMelodyEnabledImage().getWebElement() == null);
		assertTrue(PageStreamView.getMelodyImage().getWebElement() != null);
		PageStreamView.exitPage();
	}
	
	@AfterMethod
	public void cleanup() {
		try {
			camera1Terminal.closePort();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
