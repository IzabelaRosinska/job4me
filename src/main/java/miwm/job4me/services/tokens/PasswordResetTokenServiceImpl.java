package miwm.job4me.services.tokens;

import miwm.job4me.model.token.PasswordResetToken;
import miwm.job4me.repositories.users.PasswordTokenRepository;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordTokenRepository passwordTokenRepository;

    public PasswordResetTokenServiceImpl(PasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }


    @Override
    public Set<PasswordResetToken> findAll() {
        return (Set<PasswordResetToken>) passwordTokenRepository.findAll();
    }

    @Override
    public PasswordResetToken findById(Long id) {
        Optional<PasswordResetToken> token = passwordTokenRepository.findById(id);
        if(token.isPresent())
            return token.get();
        else
            return null;
    }

    @Override
    @Transactional
    public PasswordResetToken save(PasswordResetToken token) {
        return passwordTokenRepository.save(token);
    }

    @Override
    @Transactional
    public void delete(PasswordResetToken token) {
        passwordTokenRepository.delete(token);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        passwordTokenRepository.deleteById(id);
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }
}
