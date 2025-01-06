package com.bujeong.gov_opendata.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
public class PbroBicycleInfoDTO {
	private String rntstnId;                   //대여소 아이디

    private String rntstnNm;                   //대여소명

    private String lcgvmnInstCd;                 //지자체 코드

    private int bcyclTpkctNocs;                 //자전거 주차 총 건수

    private String lat;                         //위도

    private String lot;                         //경도
}
