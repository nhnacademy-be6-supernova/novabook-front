package store.novabook.front.common.util;

import java.util.Base64;

import org.json.JSONObject;

public class JwtDecoderUtil {

	private JwtDecoderUtil() {
		throw new IllegalStateException("Utility class");
	}

	public static boolean decodeJWT(String jwtToken) {
		String[] parts = jwtToken.split("\\.");
		if (parts.length >= 2) {
			String payload = new String(Base64.getUrlDecoder().decode(parts[1]));
			JSONObject payloadJson = new JSONObject(payload);
			long exp = payloadJson.getLong("exp");
			long currentTimeInSeconds = System.currentTimeMillis() / 1000;

			return exp < currentTimeInSeconds;
		} else {
			return false;
		}
	}
}