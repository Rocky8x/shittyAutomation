package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageDoorbellCall {
	
	/***ELEMENT***/	
	
	/*Accept call button*/
	public static Element getAcceptButton(){ 
		String id = "btn_accept";
		return new Element(By.id(id), 15).setDescription("Doorbell: Accept call button"); 
	}
	
	/*Decline call button*/
	public static Element getDeclineButton(){ 
		String id = "btn_decline";
		return new Element(By.id(id), 15).setDescription("Doorbell: Decline call button"); 
	}
	
}
