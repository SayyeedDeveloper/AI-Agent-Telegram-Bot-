package sayyeed.dev.aiagenttelegrambot.service;

import org.springframework.stereotype.Service;
import sayyeed.dev.aiagenttelegrambot.entitly.UserEntity;
import sayyeed.dev.aiagenttelegrambot.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }


    public void userOnboarding(Long chatId, String userName) {
        Optional<UserEntity> userEntityOptional = repository.findByChatId(chatId);
        if (userEntityOptional.isEmpty()){
            UserEntity user = new UserEntity();
            user.setChatId(chatId);
            user.setUserName(userName);
            repository.save(user);
        }else {
            UserEntity existingUser = userEntityOptional.get();
            existingUser.setUserName(userName);
            repository.save(existingUser);
        }
    }

    public String getUserId(Long telegramId) {
        Optional<UserEntity> userEntityOptional = repository.findByChatId(telegramId);
        return userEntityOptional.map(UserEntity::getId).orElse(null);
    }

}
