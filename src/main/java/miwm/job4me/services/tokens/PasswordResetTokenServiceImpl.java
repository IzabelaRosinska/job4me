package miwm.job4me.services.tokens;

import miwm.job4me.exceptions.InvalidArgumentException;
import miwm.job4me.exceptions.NoSuchElementFoundException;
import miwm.job4me.messages.ExceptionMessages;
import miwm.job4me.model.token.PasswordResetToken;
import miwm.job4me.repositories.users.PasswordTokenRepository;
import miwm.job4me.validators.fields.IdValidator;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

@Service
public class PasswordResetTokenServiceImpl implements PasswordResetTokenService {

    private final PasswordTokenRepository passwordTokenRepository;
    private final IdValidator idValidator;
    private final String ENTITY_TOKEN = "PasswordResetToken";

    public PasswordResetTokenServiceImpl(PasswordTokenRepository passwordTokenRepository, IdValidator idValidator) {
        this.passwordTokenRepository = passwordTokenRepository;
        this.idValidator = idValidator;
    }

    @Override
    public Set<PasswordResetToken> findAll() {
        return (Set<PasswordResetToken>) passwordTokenRepository.findAll();
    }

    @Override
    public PasswordResetToken findById(Long id) {
        idValidator.validateLongId(id, ENTITY_TOKEN);
        Optional<PasswordResetToken> token = passwordTokenRepository.findById(id);
        if(token.isPresent())
            return token.get();
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_TOKEN, id));
    }

    @Override
    @Transactional
    public PasswordResetToken save(PasswordResetToken token) {
        if(token != null)
            return passwordTokenRepository.save(token);
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_TOKEN));
    }

    @Override
    @Transactional
    public void delete(PasswordResetToken token) {
        if(token != null)
            passwordTokenRepository.delete(token);
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_TOKEN));
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        idValidator.validateLongId(id, ENTITY_TOKEN);
        if(findById(id) != null)
            passwordTokenRepository.deleteById(id);
        else
            throw new NoSuchElementFoundException(ExceptionMessages.elementNotFound(ENTITY_TOKEN, id));
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        if(token != null)
            return passwordTokenRepository.findByToken(token);
        else
            throw new InvalidArgumentException(ExceptionMessages.nullArgument(ENTITY_TOKEN));
    }
}
