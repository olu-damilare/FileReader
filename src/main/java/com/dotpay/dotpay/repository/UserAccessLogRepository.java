package com.dotpay.dotpay.repository;

import com.dotpay.dotpay.models.UserAccessLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface UserAccessLogRepository extends JpaRepository<UserAccessLog, Long> {

    @Query(value = "SELECT id, date, ip_address, request, status_code, user_agent, count(*) as request_count\n" +
            "from req_limit.user_access_log \n" +
            "WHERE date BETWEEN ?1 AND ?2 \n" +
            "group by ip_address\n" +
            "having count(ip_address) > ?3", nativeQuery = true)
    List<UserAccessLog> findUserAccessLogByDateBetweenAndLimit(LocalDateTime start,
                                                               LocalDateTime end,
                                                               long limit);
}
