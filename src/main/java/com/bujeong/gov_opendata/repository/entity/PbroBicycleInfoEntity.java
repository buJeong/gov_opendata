package com.bujeong.gov_opendata.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PUBLIC_BICYCLE_RENTAL_OFFICE_BICYCLE_INFO")
@Entity
public class PbroBicycleInfoEntity {
    @Id
    @Column(length = 7, nullable = false)
    private String rntstnId;                   //대여소 아이디

    @Column(length = 300, nullable = true)
    private String rntstnNm;                   //대여소명

    @Column(nullable = true)
    private int lcgvmnInstCd;                 //지자체 코드

    @Column(nullable = true)
    private int bcyclTpkctNocs;                 //자전거 주차 총 건수

    @Column(length = 15, nullable = true)
    private String lat;                         //위도

    @Column(length = 15, nullable = true)
    private String lot;                         //경도
}
