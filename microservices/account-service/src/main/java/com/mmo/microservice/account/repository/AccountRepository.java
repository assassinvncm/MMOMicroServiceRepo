package com.mmo.microservice.account.repository;

import com.mmo.microservice.account.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT u FROM Account u  WHERE u.fb_id = :fb_id")
    Account getFbUserByFBID(@Param("fb_id") String fb_id);
}
