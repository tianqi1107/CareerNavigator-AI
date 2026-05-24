package com.careernavigator.service;

import com.careernavigator.dto.LoginRequest;
import com.careernavigator.dto.RegisterRequest;
import com.careernavigator.entity.User;
import com.careernavigator.vo.LoginVO;
import com.careernavigator.vo.UserVO;

public interface UserService {

    LoginVO register(RegisterRequest request);

    LoginVO login(LoginRequest request);

    UserVO getUserInfo(Long userId);

    UserVO updateUserInfo(Long userId, User user);

    void updatePassword(Long userId, String oldPassword, String newPassword);
}
