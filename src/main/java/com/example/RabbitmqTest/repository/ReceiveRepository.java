package com.example.RabbitmqTest.repository;


import com.example.RabbitmqTest.entity.ReceiveMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import java.util.List;


public interface ReceiveRepository extends JpaRepository<ReceiveMessage, String> {
//    List<ReceiveMessage> save();
//
//    ReceiveMessage findAll(ReceiveMessage r);


}
