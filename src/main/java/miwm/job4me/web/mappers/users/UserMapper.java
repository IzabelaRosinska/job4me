package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Account;
import miwm.job4me.web.model.users.UserDto;

public class UserMapper {

    public UserDto toDto(Account user) {
        UserDto userDto = UserDto.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .role(user.getUserRole().getAuthority()).build();
        return userDto;
    }
}
