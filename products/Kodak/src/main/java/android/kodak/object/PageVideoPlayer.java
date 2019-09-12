package android.kodak.object;

import org.openqa.selenium.By;

import com.cinatic.element.Element;

public class PageVideoPlayer {
	public static Element getProgressBar() {
		String id = "seekbar_progress";
		return new Element(By.id(id)).setDescription("Progress bar");
	}
	
	public static Element getReplayButton() {
		String id = "img_refresh";
		return new Element(By.id(id)).setDescription("Replay button");
	}
	
	public static Element getTimeVideoPlaying() {
		String id = "time_playing";
		return new Element(By.id(id)).setDescription("Current clip playing time");
	}
	
	public static Element getTimeVideoLength() {
		String id = "time_total";
		return new Element(By.id(id)).setDescription("Clip duration");
	}
	
	public static Element getMaximizePlayerButton() {
		String id = "img_maximize";
		return new Element(By.id(id)).setDescription("Full screen button");
	}
	
	public static Element getVideoPlayFailTextView() {
		String id = "text_load_fail";
		return new Element(By.id(id),12);
	}
	
	public static Element getVideoLoading() {
		String id = "img_loading";
		return new Element(By.id(id), 3).setDescription("MD clip loading image"); 
	};
}
