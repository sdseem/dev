package com.web.dev.dto;

import lombok.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
public class UserInfo {
    public String email;
    public String fullName;
    public String phone;
    public String gender;
    public String age;
}
