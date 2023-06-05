//package com.mmo.microservice.account.event.listener;
//
//import com.mmo.microservice.account.event.SaveTokenEvent;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.event.EventListener;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//@Slf4j
//public class SaveTokenEventListener {
//
//    private final KafkaTemplate<String, SaveTokenEvent> kafkaTemplate;
//    private final ObservationRegistry observationRegistry;
//
//    @EventListener
//    public void handleSaveTokenEventListener(SaveTokenEvent event){
//
//    }
//}
