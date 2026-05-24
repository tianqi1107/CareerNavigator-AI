package com.careernavigator.controller;

import com.careernavigator.common.result.R;
import com.careernavigator.common.utils.SecurityUtils;
import com.careernavigator.entity.User;
import com.careernavigator.service.UserService;
import com.careernavigator.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "用户管理", description = "用户信息相关接口")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/me")
    public R<UserVO> getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        return R.ok(userService.getUserInfo(userId));
    }

    @Operation(summary = "更新个人信息")
    @PutMapping("/me")
    public R<UserVO> updateCurrentUser(@RequestBody User updateData) {
        Long userId = SecurityUtils.getCurrentUserId();
        return R.ok(userService.updateUserInfo(userId, updateData));
    }

    @Operation(summary = "修改密码")
    @PutMapping("/me/password")
    public R<Void> updatePassword(@RequestBody Map<String, String> body) {
        Long userId = SecurityUtils.getCurrentUserId();
        userService.updatePassword(userId, body.get("oldPassword"), body.get("newPassword"));
        return R.ok();
    }
}
