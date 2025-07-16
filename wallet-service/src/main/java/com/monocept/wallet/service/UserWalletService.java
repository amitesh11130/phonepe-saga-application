package com.monocept.wallet.service;

import com.monocept.wallet.entity.UserWallet;
import com.monocept.wallet.kafka.KafkaProduce;
import com.monocept.wallet.repository.UserWalletRepository;
import com.monocept.wallet.request.UserWalletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserWalletService {

    private final UserWalletRepository walletRepository;
    private final KafkaProduce kafkaProduce;


    public UserWallet saveUserWallet(UserWalletRequest walletRequest) {
        return walletRepository.save(UserWallet.builder()
                .accountNumber(walletRequest.getAccountNumber())
                .balance(walletRequest.getBalance()).build());
    }


    public List<UserWallet> getAll() {
        return walletRepository.findAll();
    }

    public UserWallet getUserByAccountNumber(String accountNumber) {
        return walletRepository.findByAccountNumber(accountNumber).get();
    }
}
