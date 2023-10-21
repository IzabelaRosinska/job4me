package miwm.job4me.mappers;

import miwm.job4me.model.users.Person;
import miwm.job4me.model.users.UserDto;

public class UserMapper {

    public UserDto userToUserDto(Person user) {
        UserDto userDto = UserDto.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .role(user.getUserRole().getAuthority()).build();
        return userDto;
    }
}
