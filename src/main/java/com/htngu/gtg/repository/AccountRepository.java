package com.htngu.gtg.repository;

import com.htngu.gtg.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("select a from Account a where a.userName = ?1 and a.password = ?2")
    Account login(String username, String password);

    @Query("select a from Account a where a.phoneNumber = ?1")
    Account checkPhone(String phone);

    @Query("select a from Account a where a.phoneNumber = ?1")
    Account findByPhone(String phone);

    @Modifying
    @Query("update Account u set u.password = ?1 where u.phoneNumber = ?2")
    void updatePass(String phone, String pass);

}
