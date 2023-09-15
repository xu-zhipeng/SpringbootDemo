package com.youjun.api.modules.test.demo;

import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2023/9/15
 */
public interface TestService {
    TestPojo selectById(Long id);

    List<TestPojo> selectAll();

    boolean add(TestPojo test);

    boolean update(TestPojo test);
}
