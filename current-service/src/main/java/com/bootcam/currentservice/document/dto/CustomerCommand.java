package com.bootcam.currentservice.document.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@AllArgsConstructor
@Data
@Builder
public class CustomerCommand {
    private String name;
    private String code;
    private String customerIdentityNumber;
}
