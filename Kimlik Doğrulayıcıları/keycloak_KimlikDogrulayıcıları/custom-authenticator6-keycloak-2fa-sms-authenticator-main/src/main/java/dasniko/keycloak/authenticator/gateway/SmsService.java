package dasniko.keycloak.authenticator.gateway;

import java.util.Map;

public interface SmsService {

	void send(String phoneNumber, String message);

}
