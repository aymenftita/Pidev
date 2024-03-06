package com.esprit.services.utilisateur;

import org.json.JSONException;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.io.IOException;
import java.net.http.HttpResponse;

public class EmailVerifier {

    public boolean verifyEmail(String email) {
        try {
            String url = "https://emailvalidation.abstractapi.com/v1/?api_key=ad2b3d3601f04cc5890f9faf8ff819de&email=" + email;
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                JSONObject jsonObject = new JSONObject(response.body());
                String deliverability = jsonObject.getString("deliverability");
                boolean isValidFormat = jsonObject.getJSONObject("is_valid_format").getBoolean("value");
                boolean isFreeEmail = jsonObject.getJSONObject("is_free_email").getBoolean("value");
                boolean isDisposableEmail = jsonObject.getJSONObject("is_disposable_email").getBoolean("value");
                boolean isRoleEmail = jsonObject.getJSONObject("is_role_email").getBoolean("value");
                boolean isCatchallEmail = jsonObject.getJSONObject("is_catchall_email").getBoolean("value");
                boolean isMxFound = jsonObject.getJSONObject("is_mx_found").getBoolean("value");
                boolean isSmtpValid = jsonObject.getJSONObject("is_smtp_valid").getBoolean("value");

                System.out.println("isValidFormat: " + isValidFormat);
                System.out.println("isFreeEmail: " + isFreeEmail);
                System.out.println("isDisposableEmail: " + isDisposableEmail);
                System.out.println("isRoleEmail: " + isRoleEmail);
                System.out.println("isCatchallEmail: " + isCatchallEmail);
                System.out.println("isMxFound: " + isMxFound);
                System.out.println("isSmtpValid: " + isSmtpValid);


                if (deliverability.equals("DELIVERABLE") && isValidFormat && isFreeEmail &&
                        !isDisposableEmail && !isRoleEmail && !isCatchallEmail && isMxFound && isSmtpValid) {
                    return true;
                } else {
                    return false;
                }
            } else {
                System.out.println("Failed to verify email.");
                return false; // Return false if verification failed
            }

        } catch (IOException | InterruptedException | JSONException e) {
            e.printStackTrace();
            return false; // Return false if an exception occurs
        }
    }


}
