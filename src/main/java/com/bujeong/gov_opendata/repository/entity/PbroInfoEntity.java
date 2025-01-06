package com.bujeong.gov_opendata.repository.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "PUBLIC_BICYCLE_RENTAL_OFFICE_INFO")
@Entity
public class PbroInfoEntity {
    @Id
    @Column(length = 7, nullable = false)
    private String rntstnId;                   //대여소 아이디

    @Column(length = 300, nullable = true)
    private String rntstnNm;                   //대여소명

    @Column(length = 10, nullable = true)
    private String lcgvmnInstCd;                   //지자체 코드

    @Column(length = 100, nullable = true)
    private String lcgvmnInstNm;                   //지자체명

    @Column(length = 300, nullable = true)
    private String roadNmAddr;                //소재지도로명주소

    @Column(length = 15, nullable = true)
    private String lat;                         //위도

    @Column(length = 15, nullable = true)
    private String lot;                         //경도

    @Column(length = 5, nullable = true)
    private String operBgngHrCn;             //운영시작시각

    @Column(length = 5, nullable = true)
    private String operEndHrCn;              //운영종료시각

    @Column(length = 1, nullable = true)
    private String rpfactInstlYn;             //수리대설치여부

    @Column(length = 1, nullable = true)
    private String arinjcInstlYn;             //공기주입기비치여부

    @Column(length = 10, nullable = true)
    private String rntstnFcltTypeNm;         //자전거대여소구분

    @Column(length = 30, nullable = true)
    private String rntstnOperDayoffDayCn;   //휴무일

    @Column(length = 10, nullable = true)
    private String rntFeeTypeNm;             //요금구분
}
