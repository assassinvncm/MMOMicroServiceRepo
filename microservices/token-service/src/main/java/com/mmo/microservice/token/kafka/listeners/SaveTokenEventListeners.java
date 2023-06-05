package com.mmo.microservice.token.kafka.listeners;

import com.mmo.microservice.token.kafka.event.SaveTokenEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SaveTokenEventListeners {

    @KafkaListener(topics = "createTokenTopic")
    public void handleSaveToken(SaveTokenEvent saveTokenEvent) {
        log.info("Handle save token event: "+saveTokenEvent.getType());
        // send out an email notification
    }

}
