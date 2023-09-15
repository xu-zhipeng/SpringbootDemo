package com.youjun.api.modules.test.demo;

import com.youjun.common.api.CommonResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author kirk
 * @since 2023/9/15
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    TestService testService;

    @GetMapping("{id}")
    public CommonResult<TestPojo> selectById(@PathVariable("id") Long id) {
        return CommonResult.success(this.testService.selectById(id));
    }

    @GetMapping("list")
    public CommonResult<List<TestPojo>> list() {
        List<TestPojo> testPojos = this.testService.selectAll();
        return CommonResult.success(testPojos);
    }


    @PostMapping("add")
    public CommonResult<Boolean> add(TestPojo test) {
        return CommonResult.success(this.testService.add(test));
    }


    @PutMapping("update")
    public CommonResult<Boolean> update(TestPojo test) {
        return CommonResult.success(this.testService.update(test));
    }

}
