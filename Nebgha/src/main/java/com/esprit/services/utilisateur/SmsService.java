package com.esprit.services.utilisateur;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class SmsService {
    public static final String ACCOUNT_SID = "ACbdda076c145669838d51f303a3fece16";
    public static final String AUTH_TOKEN = "dd89c5976b0f1a956dc18ce5fa754022";
    public static final String FROM_PHONE_NUMBER = "+16562162257";

    public boolean sendResetCodeSMS(String phoneNumber, String resetCode) {
        try {

            Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

            Message message = Message.creator(
                            new PhoneNumber(phoneNumber),
                            new PhoneNumber(FROM_PHONE_NUMBER),
                            "Your reset code is " + resetCode)
                    .create();

            System.out.println("SMS sent: " + message.getSid());
            return true;
        } catch (ApiException e) {
            System.err.println("Error sending SMS: " + e.getMessage());
            return false;
        }
    }
}
