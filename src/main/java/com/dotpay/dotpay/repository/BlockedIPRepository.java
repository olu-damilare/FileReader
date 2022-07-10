package com.dotpay.dotpay.repository;

import com.dotpay.dotpay.models.BlockedIP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlockedIPRepository extends JpaRepository<BlockedIP, Long> {

}
