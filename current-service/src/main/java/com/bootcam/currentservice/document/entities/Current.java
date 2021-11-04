package com.bootcam.currentservice.document.entities;

import com.bootcam.currentservice.document.dto.CustomerCommand;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "current")
public class Current {
    @Id
    private String id;
    /**
     * @param accountNumber, example "9999-4444-3333-1111"
     */

    private String accountNumber;

    private String typeAccount;

    private double amount;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOperation = LocalDateTime.now();

    /**
     * Limits Movements
     */

    private int movementPerMonth;

    private int maxLimitMovementPerMonth;
    /**
     * Customer Information
     */
    private CustomerCommand customer;

    private String customerIdentityNumber;
}