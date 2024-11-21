package com.codechen.scaffold.admin.controller;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.spring.application.ImageCaptchaApplication;
import cloud.tianai.captcha.spring.vo.CaptchaResponse;
import cloud.tianai.captcha.spring.vo.ImageCaptchaVO;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import com.codechen.scaffold.common.domain.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author：Java陈序员
 * @date 2023-07-05 9:45
 * @description 验证码
 */
@Api(tags = "验证码")
@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private ImageCaptchaApplication imageCaptchaApplication;

    @ApiOperation(value = "生成验证码")
    @GetMapping("/generate")
    public Result generateCaptcha(@ApiParam(value = "验证码类型") @RequestParam(value = "captchaType", required = false) String captchaType) {
        if (StringUtils.isBlank(captchaType)) {
            captchaType = CaptchaTypeConstant.SLIDER;
        }
        CaptchaResponse<ImageCaptchaVO> captchaResponse = imageCaptchaApplication.generateCaptcha(captchaType);

        return Result.success(captchaResponse);
    }

    @ApiOperation(value = "校验验证码")
    @PostMapping("/check")
    public Result checkCaptcha(@RequestParam("id") String id,
                               @RequestBody ImageCaptchaTrack imageCaptchaTrack) {
        boolean success = imageCaptchaApplication.matching(id, imageCaptchaTrack)
                .isSuccess();
        return Result.success(success);
    }
}
