package Toster.Bot.handler;

import Toster.Bot.State;
import Toster.entity.User;
import Toster.repositoryes.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Collections;

import static Toster.Bot.util.TelegramUtil.createMessageTemplate;

@Component
public class StartHandler implements Handler{

    //@Value("${bot.name}")
    private String botUsername ="CrazyHumburgerBot";

    private final UserRepository userRepository;

    public StartHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        // Приветствуем пользователя
        SendMessage welcomeMessage = createMessageTemplate(user);
        welcomeMessage.setText(String.format(
                        "Aloha! I'm *%s*%nI am here to help you learn Java", botUsername
                ));
// Просим назваться
        SendMessage registrationMessage = createMessageTemplate(user);
        registrationMessage.setText("In order to start our journey tell me your name");
// Меняем пользователю статус на - "ожидание ввода имени"
        user.setBotState(State.ENTER_NAME);
        userRepository.save(user);
        return List.of(welcomeMessage, registrationMessage);
    }

    @Override
    public State operatedBotState() {
        return State.START;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return Collections.emptyList();
    }
}
