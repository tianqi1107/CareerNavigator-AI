package com.careernavigator.controller;

import com.careernavigator.common.result.R;
import com.careernavigator.dto.LoginRequest;
import com.careernavigator.dto.RegisterRequest;
import com.careernavigator.service.UserService;
import com.careernavigator.vo.LoginVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "认证管理", description = "登录注册相关接口")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public R<LoginVO> register(@Valid @RequestBody RegisterRequest request) {
        return R.ok(userService.register(request));
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public R<LoginVO> login(@Valid @RequestBody LoginRequest request) {
        return R.ok(userService.login(request));
    }
}
