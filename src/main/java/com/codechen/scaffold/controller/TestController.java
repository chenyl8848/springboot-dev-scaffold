package com.codechen.scaffold.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author cyl
 * @date 2023-03-22 16:34
 * @description 测试接口
 */
@Api(tags = "测试接口")
@RestController
public class TestController {

    @ApiOperation(value = "接口测试")
    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
