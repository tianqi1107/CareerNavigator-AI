package com.careernavigator.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.careernavigator.common.exception.BusinessException;
import com.careernavigator.common.result.ResultCode;
import com.careernavigator.common.utils.JwtUtils;
import com.careernavigator.dto.LoginRequest;
import com.careernavigator.dto.RegisterRequest;
import com.careernavigator.entity.User;
import com.careernavigator.mapper.UserMapper;
import com.careernavigator.service.UserService;
import com.careernavigator.vo.LoginVO;
import com.careernavigator.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Override
    public LoginVO register(RegisterRequest request) {
        // Check username
        if (userMapper.selectCount(
                new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername())) > 0) {
            throw new BusinessException(ResultCode.USERNAME_EXISTS);
        }
        // Check email
        if (request.getEmail() != null && userMapper.selectCount(
                new LambdaQueryWrapper<User>().eq(User::getEmail, request.getEmail())) > 0) {
            throw new BusinessException(ResultCode.EMAIL_EXISTS);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setNickname(request.getNickname() != null ? request.getNickname() : request.getUsername());
        user.setMajor(request.getMajor());
        user.setEducation(request.getEducation());
        user.setUniversity(request.getUniversity());
        user.setRole("USER");
        user.setStatus(1);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.insert(user);

        String token = jwtUtils.generateToken(user.getId(), user.getUsername(), user.getRole());
        return LoginVO.builder()
                .token(token)
                .user(toVO(user))
                .build();
    }

    @Override
    public LoginVO login(LoginRequest request) {
        User user = userMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, request.getUsername())
        );
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }
        if (user.getStatus() == 0) {
            throw new BusinessException(ResultCode.FORBIDDEN);
        }

        user.setLastLoginTime(LocalDateTime.now());
        userMapper.updateById(user);

        String token = jwtUtils.generateToken(user.getId(), user.getUsername(), user.getRole());
        return LoginVO.builder()
                .token(token)
                .user(toVO(user))
                .build();
    }

    @Override
    public UserVO getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        return toVO(user);
    }

    @Override
    public UserVO updateUserInfo(Long userId, User updateData) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        if (updateData.getNickname() != null) user.setNickname(updateData.getNickname());
        if (updateData.getAvatar() != null) user.setAvatar(updateData.getAvatar());
        if (updateData.getMajor() != null) user.setMajor(updateData.getMajor());
        if (updateData.getEducation() != null) user.setEducation(updateData.getEducation());
        if (updateData.getUniversity() != null) user.setUniversity(updateData.getUniversity());
        if (updateData.getGraduationYear() != null) user.setGraduationYear(updateData.getGraduationYear());
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateById(user);
        return toVO(user);
    }

    @Override
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException(ResultCode.USER_NOT_FOUND);
        }
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new BusinessException(ResultCode.PASSWORD_ERROR);
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setUpdatedAt(LocalDateTime.now());
        userMapper.updateById(user);
    }

    private UserVO toVO(User user) {
        return UserVO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .phone(user.getPhone())
                .nickname(user.getNickname())
                .avatar(user.getAvatar())
                .role(user.getRole())
                .major(user.getMajor())
                .education(user.getEducation())
                .university(user.getUniversity())
                .graduationYear(user.getGraduationYear())
                .lastLoginTime(user.getLastLoginTime())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
