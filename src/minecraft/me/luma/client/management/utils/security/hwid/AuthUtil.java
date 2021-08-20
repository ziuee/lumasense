package me.luma.client.management.utils.security.hwid;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.minecraft.client.Minecraft;

public class AuthUtil {
	public static boolean check() {
		int lines = 0;
		try {
			URL url = new URL("https://crystalclient.000webhostapp.com/hwids/" + HWID.getHWID());
			URLConnection urlConnection = url.openConnection();
			InputStream inputStream = urlConnection.getInputStream();
			if (!url.toString().contains("https://crystalclient.000webhostapp.com/hwids/")) return false;
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				String line = null;
				while ((line = bufferedReader.readLine()) != null) {
				lines += 1;
			}
		} catch (Exception ex) {
        	return false;
    	}
		return true;
	}

	public static void close() {
		Minecraft.getMinecraft().shutdown();
	}
}
