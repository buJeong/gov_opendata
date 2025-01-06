package com.bujeong.gov_opendata.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class PbroInfoDTO {
    private String rntstnId;                   //대여소 아이디

    private String rntstnNm;                   //대여소명

    private String lcgvmnInstCd;                   //지자체 코드

    private String lcgvmnInstNm;                   //지자체명

    private String roadNmAddr;                //소재지도로명주소

    private String lat;                         //위도

    private String lot;                         //경도

    private String operBgngHrCn;             //운영시작시각

    private String operEndHrCn;              //운영종료시각

    private String rpfactInstlYn;             //수리대설치여부

    private String arinjcInstlYn;             //공기주입기비치여부

    private String rntstnFcltTypeNm;         //자전거대여소구분

    private String rntstnOperDayoffDayCn;   //휴무일

    private String rntFeeTypeNm;             //요금구분
}
