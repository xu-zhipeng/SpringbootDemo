package com.youjun.api.modules.test.demo;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2023/9/15
 */
@Service
public class TestServiceImpl implements TestService {
    @Override
    public TestPojo selectById(Long id) {
        return new TestPojo(id, "test");
    }

    @Override
    public List<TestPojo> selectAll() {
        return Arrays.asList(new TestPojo(1L, "test"),new TestPojo(2L, "test2"));
    }

    @Override
    public boolean add(TestPojo test) {
        if(Objects.nonNull(test)){
            return true;
        }
        return false;
    }

    @Override
    public boolean update(TestPojo test) {
        if(Objects.nonNull(test)){
            return true;
        }
        return false;
    }
}
