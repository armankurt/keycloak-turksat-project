package com.example.keycloak.authenticator;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.models.UserModel;
import org.keycloak.models.KeycloakSession;
import org.keycloak.models.RealmModel;

import okhttp3.*; // OkHttp
import java.io.IOException;
import java.util.Random;

public class EsignNumberAuthenticator implements Authenticator {

    private static final Logger logger = Logger.getLogger(EsignNumberAuthenticator.class);
    private static final String SESSION_TRANSACTION_CODE = "transactionCode";
    private static final String SESSION_VERIFIED_STATUS = "transactionCodeVerified";
    private static final String API_STORE_URL = "http://host.docker.internal:7248/api/TransactionCode/store";
    private static final String API_VERIFY_URL = "http://host.docker.internal:7248/api/TransactionCode/verify";

    private final OkHttpClient httpClient = new OkHttpClient();

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        logger.info("authenticate() started");

        if (isCodeAlreadyVerified(context)) {
            logger.info("Transaction code already verified. Proceeding...");
            context.success();
            return;
        }

        String transactionCode = generateTransactionCode();
        if (transactionCode == null || transactionCode.isEmpty()) {
            logger.error("Failed to generate transaction code.");
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
                    context.form().setError("Transaction code generation failed.").createForm("login-esign-number.ftl"));
            return;
        }

        context.getAuthenticationSession().setAuthNote(SESSION_TRANSACTION_CODE, transactionCode);
        String sessionId = context.getAuthenticationSession().getParentSession().getId();

        if (!storeTransactionCode(transactionCode, sessionId)) {
            logger.error("Failed to store transaction code.");
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
                    context.form().setError("Transaction code storage failed.").createForm("login-esign-number.ftl"));
            return;
        }

        context.challenge(context.form().setAttribute("transactionCode", transactionCode).createForm("login-esign-number.ftl"));
    }

    @Override
    public void action(AuthenticationFlowContext context) {
        logger.info("action() started");

        String transactionCode = context.getAuthenticationSession().getAuthNote(SESSION_TRANSACTION_CODE);
        if (transactionCode == null || transactionCode.isEmpty()) {
            context.failureChallenge(AuthenticationFlowError.INTERNAL_ERROR,
                    context.form().setError("Transaction code not found.").createForm("login-esign-number.ftl"));
            return;
        }

        if (isCodeVerified(transactionCode)) {
            logger.info("Transaction code verified successfully.");
            context.getAuthenticationSession().setAuthNote(SESSION_VERIFIED_STATUS, "true");
            context.success();
        } else {
            logger.warn("Transaction code verification failed.");
            context.challenge(context.form()
                    .setAttribute("transactionCode", transactionCode)
                    .setError("Kod doğrulanmadı. Lütfen doğrulamayı yapın ve yeniden kontrol edin.")
                    .createForm("login-esign-number.ftl"));
        }
    }

    private boolean isCodeAlreadyVerified(AuthenticationFlowContext context) {
        return "true".equals(context.getAuthenticationSession().getAuthNote(SESSION_VERIFIED_STATUS));
    }

    private String generateTransactionCode() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }

    private boolean storeTransactionCode(String code, String sessionId) {
        String jsonBody = String.format("{\"transactionCode\":\"%s\", \"sessionId\":\"%s\"}", code, sessionId);
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(API_STORE_URL).post(body).build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            return response.isSuccessful();
        } catch (IOException e) {
            logger.error("Failed to store transaction code", e);
            return false;
        }
    }

    private boolean isCodeVerified(String transactionCode) {
        String jsonBody = String.format("{\"transactionCode\":\"%s\"}", transactionCode);
        RequestBody body = RequestBody.create(jsonBody, MediaType.parse("application/json"));
        Request request = new Request.Builder().url(API_VERIFY_URL).post(body).build();

        try (okhttp3.Response response = httpClient.newCall(request).execute()) {
            if (response.isSuccessful() && response.body() != null) {
                String responseBody = response.body().string();
                logger.info("API Response: " + responseBody);
                return responseBody.contains("\"status\":\"verified\"");
            }
        } catch (IOException e) {
            logger.error("Failed to verify transaction code", e);
        }
        return false;
    }

    @Override
    public boolean requiresUser() {
        return false;
    }

    @Override
    public boolean configuredFor(KeycloakSession session, RealmModel realm, UserModel user) {
        return true;
    }

    @Override
    public void setRequiredActions(KeycloakSession session, RealmModel realm, UserModel user) {
    }

    @Override
    public void close() {
    }
}
