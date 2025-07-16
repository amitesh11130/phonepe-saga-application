package com.monocept.wallet.controller;

import com.monocept.wallet.entity.UserWallet;
import com.monocept.wallet.request.UserWalletRequest;
import com.monocept.wallet.service.UserWalletService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/wallet")
@RequiredArgsConstructor
public class UserWalletController {

    private final UserWalletService walletService;

    @PostMapping("/save")
    public UserWallet saveUser(@RequestBody UserWalletRequest walletRequest) {
       return walletService.saveUserWallet(walletRequest);
    }

    @GetMapping("/getAll")
    public List<UserWallet> getAll(){
        return walletService.getAll();
    }

    @GetMapping("/getByAccNo")
    public UserWallet getByAccountNumber(@RequestParam String accountNumber){
        return walletService.getUserByAccountNumber(accountNumber);
    }
}
