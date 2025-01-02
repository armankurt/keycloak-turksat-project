FROM quay.io/keycloak/keycloak:latest
COPY target/login-listener-1.0-SNAPSHOT.jar /opt/keycloak/providers/login-listener.jar

ENTRYPOINT ["/opt/keycloak/bin/kc.sh", "start"]

