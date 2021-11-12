package com.zzz.login.entity;

import lombok.Data;

/**
 * @author lingqu
 * @date 2021/11/12
 * @apiNote
 */
@Data
public class User {
    private String username;
    private String password;
    private String token;

}
