package Toster.Bot.handler;

import Toster.Bot.State;
import Toster.entity.User;
import Toster.repositoryes.UserRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.List;

import static Toster.Bot.handler.QuizHandler.QUIZ_START;
import static Toster.Bot.util.TelegramUtil.createInlineKeyboardButton;
import static Toster.Bot.util.TelegramUtil.createMessageTemplate;

@Component
public class RegistrationHandler implements Handler {

    public static final String NAME_ACCEPT = "/enter_name_accept";
    public static final String NAME_CHANGE = "/enter_name";
    public static final String NAME_CHANGE_CANCEL = "/enter_name_cancel";

    private final UserRepository userRepository;

    public RegistrationHandler(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        // Проверяем тип полученного события

        if (message.equalsIgnoreCase(NAME_ACCEPT) || message.equalsIgnoreCase(NAME_CHANGE_CANCEL)) {
            return accept(user);
        } else if (message.equalsIgnoreCase(NAME_CHANGE)) {
            return changeName(user);
        }
        return checkName(user, message);
    }

    public List<PartialBotApiMethod<? extends Serializable>>accept(User user) {

// Если пользователь принял имя - меняем статус и сохраняем

        user.setBotState(State.NONE);
        userRepository.save(user);


// Создаем кнопку для начала игры

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                createInlineKeyboardButton("Start quiz", QUIZ_START));

        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));

        SendMessage result = createMessageTemplate(user);
        result.setText(String.format(
                "Your name is saved as: %s", user.getName()));
        result.setReplyMarkup(inlineKeyboardMarkup);
        return List.of(result);
    }

    private List<PartialBotApiMethod<? extends Serializable>> checkName(User user, String message) {

// При проверке имени мы превентивно сохраняем пользователю новое имя в базе


// идея для рефакторинга - добавить временное хранение имени

        user.setName(message);
        userRepository.save(user);


// Делаем кнопку для применения изменений

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                createInlineKeyboardButton("Accept", NAME_ACCEPT));

        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));

        SendMessage result = createMessageTemplate(user);
        result.setText(String.format("You have entered: %s%nIf this is correct - press the button", user.getName()));
        result.setReplyMarkup(inlineKeyboardMarkup);
        return List.of(result);
    }

    private List<PartialBotApiMethod<? extends Serializable>> changeName(User user) {

// При запросе изменения имени мы меняем State

        user.setBotState(State.ENTER_NAME);
        userRepository.save(user);


// Создаем кнопку для отмены операции

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                createInlineKeyboardButton("Cancel", NAME_CHANGE_CANCEL));

        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));

        SendMessage result = createMessageTemplate(user);
        result.setText(String.format("Your current name is: %s%nEnter new name or press the button to continue", user.getName()));
        result.setReplyMarkup(inlineKeyboardMarkup);
        return List.of(result);
    }

    @Override
    public State operatedBotState() {
        return State.ENTER_NAME;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(NAME_ACCEPT, NAME_CHANGE, NAME_CHANGE_CANCEL);
    }
}
