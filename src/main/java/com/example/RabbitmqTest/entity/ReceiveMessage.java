package com.example.RabbitmqTest.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "receive_message")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiveMessage implements Serializable {

//    @Id //PK
//    @Column(name = "receive_id")
//    private String receiveId;
//
//    @Column(name = "receive_result")
//    private String receiveResult;
//
//    @Column(name = "receive_request_date_time")
//    private String receiveRequestDateTime;
//
//    @Column(name = "receive_processing_date_time")
//    private String receiveProcessingDateTime;

    @Id //PK
    @Column(name = "receive_id")
    private String requestId;

    @Column(name = "receive_result")
    private String result;

    @Column(name = "receive_request_date_time")
    private String requestDateTime;

    @Column(name = "receive_processing_date_time")
    private String processingDateTime;


    @Override
    public String toString() {
        return "ReceiveMessage{" +
                "requestId='" + requestId + '\'' +
                ", result='" + result + '\'' +
                ", requestDateTime='" + requestDateTime + '\'' +
                ", processingDateTime='" + processingDateTime + '\'' +
                '}';
    }
}
