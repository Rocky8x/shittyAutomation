package ios.kodak.testcases.checklist;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.cinatic.StringHelper;
import com.cinatic.TimeHelper;

import ios.kodak.object.PageDashboard;
import ios.kodak.object.PageGetStart;
import ios.kodak.object.PageProfiles;
import mobile.kodak.base.TestBaseIOS;

public class LOGIN08_ChangeFirstNameLastName extends TestBaseIOS{

	@Test(priority = 15 , description = "Verify that user cannot change first name and last name with invalid data")
	public void changeFirstNameLastNameWithInvalidData() {
		String invalidName = StringHelper.randomString("Name Is:", 23);
		// Sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.gotoUserProfilePage();
		
		String currentFirstName = PageProfiles.getFirstNameValue();
		String currentLastName = PageProfiles.getLastNameValue();
		
		// Update First name with over 30 characters
		PageProfiles.updateFirstName(invalidName);
		TimeHelper.sleep(1000);
		Assert.assertTrue(PageProfiles.verifyChangeButtonEnable(false), "User should not input first name over 30 characters.");
		PageProfiles.clickCancelButton();
		
		// Update First name with blank value
		PageProfiles.updateFirstName("");
		TimeHelper.sleep(1000);
		Assert.assertTrue(PageProfiles.verifyChangeButtonEnable(false), "User should not input first name with blank value.");
		PageProfiles.clickCancelButton();
		
		// Update Last name with over 30 characters
		PageProfiles.updateLastName(invalidName);
		TimeHelper.sleep(1000);
		Assert.assertTrue(PageProfiles.verifyChangeButtonEnable(false), "User should not input last name over 30 characters.");
		PageProfiles.clickCancelButton();
		
		// Update Last name with blank value
		PageProfiles.updateLastName("");
		TimeHelper.sleep(1000);
		Assert.assertTrue(PageProfiles.verifyChangeButtonEnable(false), "User should not input last name with blank value.");
		PageProfiles.clickCancelButton();
	}
	
	@Test(priority = 15, description = "Verify that user can change first name and last name with valid data")
	public void changeFirstNameLastNameWithValidData() {
		String validName = StringHelper.randomString("Name Is:", 22);
		// Sign in
		PageGetStart.checkAndSignin(c_username, c_password);
		
		PageDashboard.gotoUserProfilePage();
		
		String currentFirstName = PageProfiles.getFirstNameValue();
		String currentLastName = PageProfiles.getLastNameValue();
		
		// Update First name with 30 characters
		PageProfiles.updateFirstName(validName);
		TimeHelper.sleep(1000);
		Assert.assertTrue(PageProfiles.verifyChangeButtonEnable(true), "Change button should enable when input first name with 30 characters.");
		PageProfiles.clickChangeButton();
		PageProfiles.clickOkButton();
		Assert.assertEquals(PageProfiles.getFirstNameValue(), validName, "User can change first name with 30 characters");
		
		// Update First name with 1 character
		PageProfiles.updateFirstName("F");
		TimeHelper.sleep(1000);
		Assert.assertTrue(PageProfiles.verifyChangeButtonEnable(true), "Change button should enable when input first name with 1 character.");
		PageProfiles.clickChangeButton();
		PageProfiles.clickOkButton();
		Assert.assertEquals(PageProfiles.getFirstNameValue(), "F", "User can change first name with 1 character");
		
		// Revert to default first name
		PageProfiles.updateFirstName(currentFirstName);
		PageProfiles.clickChangeButton();
		PageProfiles.clickOkButton();
		Assert.assertEquals(PageProfiles.getFirstNameValue(), currentFirstName);
		
		// Update Last name with 30 characters
		PageProfiles.updateLastName(validName);
		TimeHelper.sleep(1000);
		Assert.assertTrue(PageProfiles.verifyChangeButtonEnable(true), "Change button should enable when input last name with 30 characters.");
		PageProfiles.clickChangeButton();
		PageProfiles.clickOkButton();
		Assert.assertEquals(PageProfiles.getLastNameValue(), validName, "User can change last name with 30 characters");
		
		
		// Update Last name with 1 character
		PageProfiles.updateLastName("L");
		TimeHelper.sleep(1000);
		Assert.assertTrue(PageProfiles.verifyChangeButtonEnable(true), "Change button should enable when input last name with 1 character.");
		PageProfiles.clickChangeButton();
		PageProfiles.clickOkButton();
		Assert.assertEquals(PageProfiles.getLastNameValue(), "L", "User can change last name with 1 character");
		
		// Revert to default last name
		PageProfiles.updateLastName(currentLastName);
		PageProfiles.clickChangeButton();
		PageProfiles.clickOkButton();
		Assert.assertEquals(PageProfiles.getLastNameValue(), currentLastName);
	}
	
}
