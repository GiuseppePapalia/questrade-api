package com.giuseppepapalia.questrade;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import com.giuseppepapalia.questrade.exception.InvalidTokenException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class QuestradeAPI {

	private static final String REFRESH_LINK = "https://login.questrade.com/oauth2/token?grant_type=refresh_token&refresh_token=";
	private String accessToken;
	private String refreshToken;
	private String apiServer;
	private String tokenType;
	long expiryTime;

	public QuestradeAPI(String refreshToken) throws InvalidTokenException {
		this.refreshToken = refreshToken;
		refreshAccess();
	}

	private void refreshAccess() throws InvalidTokenException {
		try {
			URL url = new URL(REFRESH_LINK + refreshToken);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() != 200) {
				throw new InvalidTokenException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			String output = br.readLine();
			JsonElement jelem = new Gson().fromJson(output, JsonObject.class);
			JsonObject obj = jelem.getAsJsonObject();
			accessToken = obj.get("access_token").toString().replaceAll("\"", "");
			apiServer = obj.get("api_server").toString().replace("\\", "").replaceAll("\"", "");
			tokenType = obj.get("token_type").toString().replaceAll("\"", "");
			refreshToken = obj.get("refresh_token").toString().replaceAll("\"", "");
			expiryTime = System.currentTimeMillis() + TimeUnit.MILLISECONDS.convert(obj.get("expires_in").getAsLong(), TimeUnit.SECONDS);

			conn.disconnect();

		} catch (InvalidTokenException e) {
			throw new InvalidTokenException(e.getMessage());
		} catch (Exception e2) {
			e2.printStackTrace();
		}
	}

	public String getAuthorization() {
		if (System.currentTimeMillis() > expiryTime) {
			try {
				refreshAccess();
			} catch (InvalidTokenException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return tokenType + " " + accessToken;
	}

	public String getServer() {
		if (System.currentTimeMillis() > expiryTime) {
			try {
				refreshAccess();
			} catch (InvalidTokenException e) {
				e.printStackTrace();
			}
		}

		return apiServer;
	}
}
