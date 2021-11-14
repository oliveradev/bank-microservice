package com.bootcamp.yankiservice.documents.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCommand{

    private String name;

    private String identityNumber;

    private String identityType;

    private String phoneNumber;

}
