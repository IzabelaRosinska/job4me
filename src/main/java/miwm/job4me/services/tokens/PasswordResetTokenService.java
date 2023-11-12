package miwm.job4me.services.tokens;

import miwm.job4me.model.token.PasswordResetToken;
import miwm.job4me.services.BaseService;
import org.springframework.stereotype.Service;

@Service
public interface PasswordResetTokenService extends BaseService<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);
}
