package com.bootcam.currentservice.handlers;

import com.bootcam.currentservice.services.ICreditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountHandler.class);

    @Autowired
    ICreditService service;




}
