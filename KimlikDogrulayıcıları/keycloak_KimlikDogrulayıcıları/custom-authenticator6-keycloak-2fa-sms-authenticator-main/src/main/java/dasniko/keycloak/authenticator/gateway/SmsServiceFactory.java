package dasniko.keycloak.authenticator.gateway;

import dasniko.keycloak.authenticator.SmsConstants;
import org.jboss.logging.Logger;  // org.jboss.logging.Logger kullanarak log değişkenini elle tanımlıyoruz

import java.util.Map;


public class SmsServiceFactory {

	private static final Logger log = Logger.getLogger(SmsServiceFactory.class);  // log değişkenini tanımlıyoruz

	public static SmsService get(Map<String, String> config) {
		if (Boolean.parseBoolean(config.getOrDefault(SmsConstants.SIMULATION_MODE, "false"))) {
			return (phoneNumber, message) ->
				log.warn(String.format("***** SIMULATION MODE ***** Would send SMS to %s with text: %s", phoneNumber, message));
		} else {
			return new AwsSmsService(config);
		}
	}
}
