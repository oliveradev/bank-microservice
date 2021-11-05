package com.bootcam.currentservice.models.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CustomerDTO {
    private String name;
    private String code;
    private String customerIdentityNumber;
}
