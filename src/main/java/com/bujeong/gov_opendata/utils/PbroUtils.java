package com.bujeong.gov_opendata.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;

import com.bujeong.gov_opendata.dto.PbroInfoDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PbroUtils {
	
	@Value("${apis.numOfRows}")
	private String numOfRows;
	
	@Value("${apis.resultType}")
	private String resultType;
	
	@Value("${apis.keys.encoding}")
	private String serviceKey;
	
	@Value("${apis.urls.base}")
	private String baseUrl;
	
	@Value("${apis.urls.operation.officeInfo}")
	private String officeInfoUrl;
	
	@Value("${apis.urls.operation.officeBicycleInfo}")
	private String officeBicycleInfoUrl;
	
	private WebClient webClient;

	public JsonNode getPbroInfo(int pageNo) {
		
		DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory();
		factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
		
		webClient = WebClient.builder().uriBuilderFactory(factory).build();
		
		String responseBody = webClient.get()
				.uri(builder -> builder
						.scheme("http")
						.host(baseUrl)
						.path(officeInfoUrl)
						.queryParam("serviceKey", serviceKey)
						.queryParam("lcgvmnInstCd", "3000000000")
						.queryParam("pageNo", pageNo)
						.queryParam("numOfRows", numOfRows)
						.queryParam("type", resultType)
						.build())
				.retrieve()
				.bodyToMono(String.class)
				.block();
		
		return parseJson(responseBody);
	}
	
	public JsonNode parseJson(String responseBody) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(responseBody);
        } catch (IOException e) {
            // 예외 처리 필요
            e.printStackTrace();
            return null;
        }
    }
	
    public List<PbroInfoDTO> getPbroInfoAsDtoList() {
    	int pageNo = 1;
    	
    	// 최소 호출 pageNo 1 , numOfRows 100
    	JsonNode jsonNode = getPbroInfo(pageNo);

        if (jsonNode != null) {
            List<PbroInfoDTO> PbroInfoDTOList = new ArrayList<>();
            
            JsonNode items = jsonNode.get("body").get("item");
            
            for (JsonNode node : items) {
            	PbroInfoDTO PbroInfoDTO = convertJsonToDto(node);
            	PbroInfoDTOList.add(PbroInfoDTO);
            }
            
            // 최초 응답에서 총 row수 확인
            long totCount = Long.parseLong(new ObjectMapper().convertValue(jsonNode.get("body").get("totalCount"), String.class));
            
            // 전체 데이터수집을 위한 페이지수
            long totPageNo = totCount / Long.parseLong(numOfRows) + 1;
            
            if (totPageNo > 1) {
                for (int i = pageNo+1; i <= totPageNo; i++) {
                	JsonNode pagingJsonNode = getPbroInfo(i);
                    
                    JsonNode pagingItems = pagingJsonNode.get("body").get("item");
                    
                    for (JsonNode node : pagingItems) {
                    	PbroInfoDTO PbroInfoDTO = convertJsonToDto(node);
                    	PbroInfoDTOList.add(PbroInfoDTO);
                    }
                }
            }
            
            return PbroInfoDTOList;
        }

        return Collections.emptyList();
    }

    public PbroInfoDTO convertJsonToDto(JsonNode jsonNode) {
        ObjectMapper objectMapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        try {
            return objectMapper.treeToValue(jsonNode, PbroInfoDTO.class);
        } catch (JsonProcessingException e) {
            // 예외 처리 필요
            e.printStackTrace();
            return null;
        }
    }
}
