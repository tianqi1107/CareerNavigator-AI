package com.careernavigator.vo;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class UserVO {
    private Long id;
    private String username;
    private String email;
    private String phone;
    private String nickname;
    private String avatar;
    private String role;
    private String major;
    private String education;
    private String university;
    private Integer graduationYear;
    private LocalDateTime lastLoginTime;
    private LocalDateTime createdAt;
}
