package co.edu.uniquindio.grid.users.dto;

import co.edu.uniquindio.grid.users.User;
import io.quarkus.elytron.security.common.BcryptUtil;

public record UserView(String username, String password) {
    public User toUser(){
        return User.builder().username(username).password(BcryptUtil.bcryptHash(password)).build();
    }
}
