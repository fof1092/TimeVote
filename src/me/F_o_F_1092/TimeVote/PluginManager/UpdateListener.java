package me.F_o_F_1092.TimeVote.PluginManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.bukkit.Bukkit;

import me.F_o_F_1092.TimeVote.Main;

public class UpdateListener {

	static Main plugin = Main.getPlugin();
	
	
	static boolean updateAvailable = false;
	static double updateDouble;
	static String updateString;
	static String versionURL;
	static String tag;
	
	public static void initializeUpdateListener(double updateDouble, String updateString, String versionURL, String tag) {
		UpdateListener.updateDouble = updateDouble;
		UpdateListener.updateString = updateString;
		UpdateListener.versionURL = versionURL;
		UpdateListener.tag = tag;
	}
	
	public static double getUpdateDoubleVersion() {
		return updateDouble;
	}
	
	public static String getUpdateStringVersion() {
		return updateString;
	}
	
	public static void checkForUpdate() {
		Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
			@Override
			public void run() {
				try {
					TrustManager[] trustAllCerts = new TrustManager[] {
						new X509TrustManager() {
							public java.security.cert.X509Certificate[] getAcceptedIssuers() {
								return null;
							}
							
							public void checkClientTrusted(X509Certificate[] certs, String authType) {}
							
							public void checkServerTrusted(X509Certificate[] certs, String authType) {}
						}
					};
					
					SSLContext sslC = SSLContext.getInstance("SSL");
					sslC.init(null, trustAllCerts, new java.security.SecureRandom());
					
					HttpsURLConnection.setDefaultSSLSocketFactory(sslC.getSocketFactory());

					HostnameVerifier allHostsValid = new HostnameVerifier() {
						public boolean verify(String hostname, SSLSession session) {
							return true;
						}
					};
						    
					HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
						 
					URL url = new URL(versionURL);
					URLConnection connection = url.openConnection();
					final BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream())); 
					
					if (!reader.readLine().equals("Version: " + updateString)) {
						System.out.println(tag + " A new update is available.");
						updateAvailable = true;
					}
					
				} catch ( IOException e) {
					System.out.println("\u001B[31m" + tag + " Couldn't check for updates. [" + e.getMessage() +"]\u001B[0m");
				} catch (NoSuchAlgorithmException e) {
					System.out.println("\u001B[31m" + tag + " Couldn't check for updates. [" + e.getMessage() +"]\u001B[0m");
				} catch (KeyManagementException e) {
					System.out.println("\u001B[31m" + tag + " Couldn't check for updates. [" + e.getMessage() +"]\u001B[0m");
				}
			}
		}, 0L);
	}
	
	
	public static boolean isAnewUpdateAvailable() {
		return updateAvailable;
	}
}
