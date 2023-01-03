package com.example.RabbitmqTest.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Map;

@Getter
@Setter
@Entity
@Table(name = "excel_message")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExcelMessage implements Serializable {

    @Id //PK
    @Column(name = "request_id")
    private String requestId;

    @Column(name = "excel_type")
    private String excelType;

    @Column(name = "request_date_time")
    private String requestDateTime;

    @Column(name = "processing_date_time")
    private String processingDateTime;

    @Column(name = "excel")
    private String excel;

    @Column(name = "flag")
    private String flag; //0 : 발송전 , 1 : 발송헀음

    @Override
    public String toString() {
        return "ExcelMessage{" +
                "requestId='" + requestId + '\'' +
                ", requestDateTime='" + requestDateTime + '\'' +
                ", excelType='" + excelType + '\'' +
                ", excel='" + excel + '\'' +
                ", processingDateTime='" + processingDateTime + '\'' +
                ", flag='" + flag + '\'' +
                '}';
    }
}
