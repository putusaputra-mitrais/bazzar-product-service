package com.putusaputra.bazzar.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringCustomContext implements ApplicationContextAware {
    private static ApplicationContext ctx;
    
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        // TODO Auto-generated method stub
        ctx = applicationContext;
    }
    
    public static ApplicationContext getApplicationContext() {
        return ctx;
    }

}
