package com.example.RabbitmqTest.repository;

import com.example.RabbitmqTest.entity.ExcelMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ExcelRepository extends JpaRepository<ExcelMessage, String> {
    List<ExcelMessage> findByFlag(String flag);



    ExcelMessage findByRequestId(String requestId);


    // Select * from excel_message where request_id = ? and flag = ?
    ExcelMessage findByRequestIdAndFlag(String requestId, String flag);



    @Modifying(clearAutomatically = true)
    @Query("UPDATE ExcelMessage e SET e.flag = :flag where e.requestId = :requestId")
    String updateFlag(@Param("flag") String flag, @Param("requestId") String requestId);
   // String updateFlag(@Param(value="flag") String flag, @Param(value="id") Long id);
//    String updateFlag(String flag, Long id);
}
///@Param(name = "Flag") String flag