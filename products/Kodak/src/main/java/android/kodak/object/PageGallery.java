package android.kodak.object;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.cinatic.element.Element;

public class PageGallery extends PageBase{
	
	public static Element getSelectTextView() {
		String id = "text_action_select";
		return new Element(By.id(id)).setDescription("Gallery: Select mode button");
	}
	
	public static Element getSelectAllTextView() {
		String id = "text_action_select_all";
		return new Element(By.id(id)).setDescription("Gallery: Select all button");
	}
	
	public static Element getDeselectAllTextView() {
		String id = "text_action_deselect_all";
		return new Element(By.id(id)).setDescription("Gallery: Deselect all buton");
	}
	
	public static Element getCancelTextView() {
		String id = "text_action_cancel";
		return new Element(By.id(id)).setDescription("Gallery: Cancel selection button");
	}
	
	public static Element getDeleteImage() {
		String id = "img_gallery_delete";
		return new Element(By.id(id)).setDescription("Gallery: Delete button");
	}
	
	public static Element getShareImage() {
		String id = "img_gallery_share";
		return new Element(By.id(id)).setDescription("Gallery: Share button");
	}
	
	public static Element getListVideoItemsPanel() {
		String id = "list_video_items";
		return new Element(By.id(id)).setDescription("Gallery: Videos section");
	}
	
	public static Element getNumberVideoSeletedTextView() {
		String id = "text_selected_count";
		return new Element(By.id(id)).setDescription("Gallery: Number of selected Videos");
	}
	
	public static List<WebElement> getListNumberVideoSeleted() {
		String id = "imgSelected";
		List<WebElement> lstEle = getListVideoItemsPanel().findElements(By.id(id));
		return lstEle;
	}
	
	public static List<WebElement> getListExistedVideo(){
		String id = "rootView";
		return getListVideoItemsPanel().findElements(By.id(id));
	}
	
	public static Element getEmptyVideoIcon() {
		String id = "layout_empty_gallery";
		return new Element(By.id(id)).setDescription("Gallery: Icon show when ther's video");
	}
	
	public static Element getDeleteButton() {
		String deleteBtn = "//android.widget.Button[@resource-id='android:id/button1']";
		return new Element(By.xpath(deleteBtn)).setDescription("Gallery: Delete button");
	}
	
	public static boolean verifyListMotionVideo() {
		return getListVideoItemsPanel().getWebElement() != null;
	}
	
	public static boolean verifyNumberVideoSeleted(int expected) {
		 return getListNumberVideoSeleted().size() == expected && getNumberVideoSeletedTextView().getText().contains(expected+"");
	}
	
	public static boolean verifyEmptyMotionVideo() {
		return getEmptyVideoIcon().getWebElement() != null;
	}
	
	public static void deleteAllMotionVideo() {
		getSelectTextView().click();
		getSelectAllTextView().click();
		getDeleteImage().click();
		getDeleteButton().click();
		getCancelTextView().click();
	}
	
	public static void shareMotionVideo() {
//		getSelectTextView().click();
//		selectMotionClip();
		getShareImage().click();
		getGmailApp().click();
	}
	
	public static int selectMotionClip() {
		getSelectTextView().click();
		int numVideo = getListExistedVideo().size();
		if (numVideo > 0) {
			for(WebElement ele : getListExistedVideo()) {
				ele.click();
			}
		}
		return numVideo;
	}
	
	public static void clickCancelSelected() {
		getCancelTextView().click();
	}
}
