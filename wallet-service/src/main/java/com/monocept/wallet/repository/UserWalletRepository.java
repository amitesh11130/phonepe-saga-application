package com.monocept.wallet.repository;

import com.monocept.wallet.entity.UserWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserWalletRepository extends JpaRepository<UserWallet, Integer> {
    Optional<UserWallet> findByAccountNumber(String accountNumber);

}
