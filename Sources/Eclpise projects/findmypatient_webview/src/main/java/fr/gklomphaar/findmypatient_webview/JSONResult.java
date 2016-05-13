package fr.gklomphaar.findmypatient_webview;

import org.json.JSONObject;

/**
 * Helper class which creates generic JSON objects based on input
 * @author Gerard
 *
 */
public class JSONResult {
	
	/**
	 * Create a simple JSON message
	 * @param isSucces success parameter
	 * @param message the message related to succes or not
	 * @return the newly create JSON object containing the input parameters
	 */
	public static JSONObject CreateSimpleMessage(Boolean isSucces, String message) {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("succes", isSucces);
		jsonResult.put("message", message);
		
		return jsonResult;
	}
}
