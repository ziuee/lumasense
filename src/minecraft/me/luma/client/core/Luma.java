package me.luma.client.core;

public class Luma {
	
	public static String clientName = "Luma";
	public static String clientDev = "ziue";
	public static String version = "1.1";
	public static String build = "1023";
	
	private Luma(String clientName, String version, String clientDev) {
		this.clientName = clientName;
		this.version = version;
		this.clientDev = clientDev;
	}
	
	public String getClientName() {
		return this.clientName;
	}
	
	public String getVersion() {
		return this.version;
	}
	
	public String getClientDevs() {
		return this.clientDev;
	}
	
}
