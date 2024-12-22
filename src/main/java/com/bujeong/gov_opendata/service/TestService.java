package com.bujeong.gov_opendata.service;

import com.bujeong.gov_opendata.repository.TestRepository;
import com.bujeong.gov_opendata.repository.entity.TestEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class TestService {

    private final TestRepository testRepository;

    public void test() {
        TestEntity testEntity = TestEntity.builder().id(1).build();
        log.debug("count :: {}", testRepository.count());
        log.debug("findById :: {}", testRepository.findById((long) testEntity.getId()));
    }
}
