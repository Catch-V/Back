package com.catchvbackend.usermanage.UserRepository.UserMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class User {

    private long id;

    private String userEmail;

    private String userPassword;

    private int loginstatus;

    public User() {
    }
}
