package com.bootcam.currentservice.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {
    private String name;
    private String customerIdentityNumber;
    private String customerIdentityType;
    private CustomerTypeRequest customerType;
}
