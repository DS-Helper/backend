package com.project.ds_helper.domain.user.dto.response;

import com.project.ds_helper.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String userId;

    private String email;

    private String role;

    private String name;

    private String gender;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .role(user.getRole().name())
                .name(user.getName())
                .gender(user.getGender())
                .build();
    }
}
