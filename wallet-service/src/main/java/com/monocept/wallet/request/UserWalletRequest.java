package com.monocept.wallet.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWalletRequest {

    private String accountNumber;
    private Double balance;
}
