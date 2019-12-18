package com.auto.core.utils;

public class MqttCommandResponse {
	String MAC;
	String time;
	String command;
	String[] data;
	
	public MqttCommandResponse(String input) {
// input example:
//		E048AF0004C7&time: 1551327777313&get_caminfo: flicker=50&flipup=0&fliplr=0&brate=550&svol=2&mvol=9&wifi=83&bat=14&hum=36&tem=27&hum_float=36.0&tem_float=27.1&storage=1&md=0:0:3:0&sd=0:2:3:0&td=1:3:1529:0&ir=0&lulla=0&res=720&sdcap=-113&sdfree=0&sdatrm=1&sdnoclips=10&mdled=1&ca=1&charge=0&lulvol=3&isp_idx=1&agc_lvl=3&ssid1=cinatic+automation+hotspot&ssid2=&ssid3=cinatic+automation+hotspot&hw_id=3&puscan=1&pu_ana_en=1&rtscan=1&
// -> split by "&":
// ->	E048AF0004C7
//		time: 1551327777313
//		get_caminfo: flicker=50
//		flipup=0
//		fliplr=0
//		brate=550
//		svol=2
//		mvol=9
//		wifi=83
//		bat=14
//		hum=36
//		tem=27
//		hum_float=36.0
//		tem_float=27.1
//		storage=1
//		md=0:0:3:0
//		sd=0:2:3:0
//		td=1:3:1529:0
//		ir=0
//		lulla=0
//		res=720
//		sdcap=-113
//		sdfree=0
//		sdatrm=1
//		sdnoclips=10
//		mdled=1
//		ca=1
//		charge=0
//		lulvol=3
//		isp_idx=1
//		agc_lvl=3
//		ssid1=cinatic+automation+hotspot
//		ssid2=
//		ssid3=cinatic+automation+hotspot
//		hw_id=3
//		puscan=1
//		pu_ana_en=1
//		rtscan=1

		this.data = input.split("&");
		
		//MAC is always at the first line
		MAC = data[0];
		
		// second line is always time
		time = data[1].split(":")[1];
		
		// third line is mixed between executed command and first attribue
		// example: get_caminfo: flicker=50
		// so the first part of this line is "command"
		command = data[2].split(":")[0];
	}
	public String getAttribute(String attribName) {
		
		// handle 3rd row first because it has different format
		// example:	get_caminfo: flicker=50
		// it's mixed up with "command"
		String[] tmp = data[2].split(":")[1].split("=");
		if(tmp[0].equals(attribName)) {
			return tmp[1];
		}
		
		for (int i=3; i< data.length; i++) {
			String[] row = data[i].split("=");
			if(row[0].equals(attribName)) {
				return row[1];
			}
		}
		return null;
	}
	
	public String[] getData() {
		return data;
	}

}
