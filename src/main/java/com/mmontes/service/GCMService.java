package com.mmontes.service;

import com.mmontes.util.Constants;
import com.mmontes.util.PrivateConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class GCMService {

	public void sendMessageTypesUpdated() { sendMessageGlobal(Constants.GCM_TYPES_UPDATED);}

	public void sendMessageGlobal(String message) {
		sendMessageByTopic("global", message);
	}
	
	public void sendMessageByTopic(String topic, String message) {

		try {
			JSONObject jGcmData = new JSONObject();
			// Where to send GCM message.
			jGcmData.put("to", "/topics/" + topic);

			// What to send in GCM message.
			JSONObject jData = new JSONObject();
			jData.put("message", message);
			jGcmData.put("data", jData);

			// Create connection to send GCM Message request.
			URL url = new URL(Constants.GCM_BASE_URL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestProperty("Authorization", "key=" + PrivateConstants.GCM_API_KEY);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			conn.setDoOutput(true);

			// Send GCM message content.
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(jGcmData.toString().getBytes());

			// Read GCM response.
			InputStream inputStream = conn.getInputStream();
			String resp = IOUtils.toString(inputStream);

		} catch (IOException e) {
		}
	}
}
