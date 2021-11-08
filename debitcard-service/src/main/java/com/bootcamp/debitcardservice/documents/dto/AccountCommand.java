package com.bootcamp.debitcardservice.documents.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCommand {
    private String numberOfAccount;
    private String typeOfAccount;
}
