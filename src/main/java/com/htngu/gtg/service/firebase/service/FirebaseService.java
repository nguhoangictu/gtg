package com.htngu.gtg.service.firebase.service;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.htngu.gtg.data.PnsRequest;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service
public class FirebaseService {

    public String pushNotification(PnsRequest pnsRequest) {
        Message message = Message.builder()
                .putData("content", pnsRequest.getContent())
                .setToken(pnsRequest.getFcmToken())
                .build();

        String response = null;
        try {
            response = FirebaseMessaging.getInstance().send(message);
        } catch (FirebaseMessagingException e) {
            e.printStackTrace();
        }
        return response;
    }


}