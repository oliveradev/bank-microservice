package com.bootcamp.productreportservice.documents.entities;

import com.bootcamp.productreportservice.documents.dto.CustomerCommand;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductReport {
    private CustomerCommand customer;
    private List<Object> productos;
}
