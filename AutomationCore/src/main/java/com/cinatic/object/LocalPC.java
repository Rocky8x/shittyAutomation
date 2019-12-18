package com.cinatic.object;

import java.io.IOException;

import com.auto.core.helpers.TerminalHelper;

public class LocalPC {

	public static void disableHotspot() throws InterruptedException, IOException {
		TerminalHelper.exeBashCommand("sudo nmcli connection down Hotspot");
	}
	
	public static void enableHotspot() throws InterruptedException, IOException {
		TerminalHelper.exeBashCommand("sudo nmcli connection up Hotspot");
	}
	
	public static void switchHotspotNoInternet() throws InterruptedException, IOException {
		TerminalHelper.exeBashCommand("sudo nmcli connection up NoInternet");
	}
	
	public static void switchHotspotInternet() throws InterruptedException, IOException {
		TerminalHelper.exeBashCommand("sudo nmcli connection up Internet");
	}
}
