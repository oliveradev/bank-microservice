package com.bootcamp.savingservice.documents.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor


public class CustomerCommand {
    private String name;
    private String code;
    private String customerIdentityNumber;
}
