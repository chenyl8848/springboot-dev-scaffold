package com.codechen.scaffold.controller;

import com.codechen.scaffold.core.entity.Result;
import com.codechen.scaffold.core.entity.UserEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author cyl
 * @date 2023-03-22 16:34
 * @description 测试接口
 */
@Api(tags = "测试接口")
@RestController
@Slf4j
public class TestController {

    @ApiOperation(value = "接口测试")
    @GetMapping("/test")
    public String test() {
        log.info("测试接口...........");
        log.error("报错接口...........");

        throw new IllegalArgumentException("参数错误");
//        return "test";
    }


    @ApiOperation(value = "参数校验")
    @PostMapping("/validParams")
    public Result validParams(@Valid @RequestBody UserEntity userEntity) {
        return Result.success("操作成功", userEntity);

    }
}
