package com.hkmedium.jpamapping.event;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupInitialDataManager implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        System.out.println("This is SetupInitialDataManager:  >> " + "onApplicationEvent");

    }
}
