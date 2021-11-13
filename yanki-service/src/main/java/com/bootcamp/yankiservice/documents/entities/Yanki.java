package com.bootcamp.yankiservice.documents.entities;

import org.springframework.data.annotation.Id;

public class Yanki {
    @Id
    private String id;

    private String identityNumber;

    private String identityType;

    private String name;

    private String phoneNumber;
}
