package miwm.job4me.web.mappers.users;

import miwm.job4me.model.users.Account;
import miwm.job4me.web.model.users.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {

    @InjectMocks
    private UserMapper userMapper;

    private Account account;

    @BeforeEach
    public void setUp() {
        account = new Account(1L, "1234567890", "test@test.com", "password", false, new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Test
    public void testToDtoWhenValidPersonThenReturnUserDto() {
        // Act
        UserDto userDto = userMapper.toDto(account);

        // Assert
        assertEquals(account.getEmail(), userDto.getUsername());
        assertEquals(account.getPassword(), userDto.getPassword());
        assertEquals(account.getUserRole().getAuthority(), userDto.getRole());
    }
}