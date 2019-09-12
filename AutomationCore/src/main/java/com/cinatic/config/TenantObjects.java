package com.cinatic.config;

public class TenantObjects {

	public static Class<?>	tenantInUsed;
	public String			STAGING;
	public String			PRODUCTION;
	public String			BASIC_AUTH_USER;
	public String			BASIC_AUTH_PASSWD;
	public String			apiGetDevices	= "/v1/users/devices";
	public String			mqttPort		= "8894";
	public String			mqttPortSt		= "8894";

	public static class Kodak extends TenantObjects {

		public Kodak() {

			tenantInUsed		= this.getClass();
			STAGING				= "https://gl-tn001-st01.perimetersafe.com/v1/services";
			PRODUCTION			= "https://gl-tn001-reg01.perimetersafe.com/v1/services";
			BASIC_AUTH_USER		= "21f9953eeda87b70dc115e524568159cc6a172613b8e20c01530fc686877396d";
			BASIC_AUTH_PASSWD	= "4d09bfe1da254bdc708b130b135fc066588261fc566ad31a20c5b09e6751d582";
		}
	}

	public static class Alecto extends TenantObjects {

		public Alecto() {

			tenantInUsed		= this.getClass();
			STAGING				= "https://gl-t02-st01.perimetersafe.com/v1/services";
			PRODUCTION			= "";
			BASIC_AUTH_USER		= "a64d655e31ed5f423243d8e4c1915756e95031dc5a3dfc6bb0f7ce9ae0ba2151";
			BASIC_AUTH_PASSWD	= "ad35f226ccafd2312b8704c58b93e278cbaf50f2c1dbfa161493de46f57b28ce";
		}
	}

	public static class AirPurifier extends TenantObjects {

		public AirPurifier() {

			tenantInUsed		= this.getClass();
			STAGING				= "https://gl-tn001-st01.perimetersafe.com/v1/services";
			PRODUCTION			= "https://gl-t03-r2.perimetersafe.com/v1/services";
			BASIC_AUTH_USER		= "aira862657578608343b9fd386307995c56191301bbcf5c627716f2998e3fdd6";
			BASIC_AUTH_PASSWD	= "air20e8b0e4edc9e76397fb8f34ff5071d4e2a3f929219158d942b114da7aa08";
			apiGetDevices		= "/v2/users/devices/ap";
			mqttPort			= "8895";
			mqttPortSt			= "8894";
		}
	}

	public static Class<?> getTenantByAppName(String appName) {

		if (appName.contains("kodak")) { return TenantObjects.Kodak.class; }
		if (appName.contains("alecto")) { return TenantObjects.Alecto.class; }
		if (appName.contains("purifier")) { return TenantObjects.AirPurifier.class; }
		return null;
	}
	
	public static TenantObjects getInUsedTenantOjbect () throws Exception{
		return (TenantObjects) Class.forName(TenantObjects.tenantInUsed.getName()).newInstance();
	}
}
