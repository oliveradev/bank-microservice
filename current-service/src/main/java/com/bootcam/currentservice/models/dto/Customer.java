package com.bootcam.currentservice.models.dto;

import lombok.*;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private String name;
    private String customerIdentityType;
    private String customerIdentityNumber;
    private CustomerType customerType;
}
