package android.kodak.testcases.checklist;

import static org.testng.Assert.assertTrue;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import android.kodak.object.PageDashboard;
import android.kodak.object.PageGetStart;
import android.kodak.object.PageLogin;
import mobile.kodak.base.TestBaseAndroid;

public class LOGIN06_LogoutMustClearPasswdField extends TestBaseAndroid{
	
	@Test ()
	public void logoutMustClearPasswdField() {
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.signOut();
		
		PageLogin.getPasswordRevealButton().click();
		String revealedPasswd = PageLogin.getPasswordTextBox().getText();
		assertTrue(revealedPasswd.equals("Password"),"App still remember password after logout");
	}
	
	@AfterMethod
	public void reLogin() {
		try {
			PageGetStart.checkAndSignin(c_username, c_password);
		} catch (Exception e) {	}
	}

}
