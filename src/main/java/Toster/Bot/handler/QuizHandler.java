package Toster.Bot.handler;

import Toster.Bot.State;
import Toster.entity.Question;
import Toster.entity.User;
import Toster.repositoryes.QuestionRepository;
import Toster.repositoryes.UserRepository;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static Toster.Bot.util.TelegramUtil.createInlineKeyboardButton;
import static Toster.Bot.util.TelegramUtil.createMessageTemplate;

public class QuizHandler implements Handler{

    //Храним поддерживаемые CallBackQuery в виде констант

    public static final String QUIZ_CORRECT = "/quiz_correct";
    public static final String QUIZ_INCORRECT = "/quiz_incorrect";
    public static final String QUIZ_START = "/quiz_start";

//Храним варианты ответа

    private static final List<String> OPTIONS = List.of("A", "B", "C", "D");

    private final UserRepository userRepository;
    private final QuestionRepository questionRepository;

    public QuizHandler(UserRepository userRepository, QuestionRepository questionRepository) {
        this.userRepository = userRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message) {
        if (message.startsWith(QUIZ_CORRECT)) {
// действие на коллбек с правильным ответом
            return correctAnswer(user, message);
        } else if (message.startsWith(QUIZ_INCORRECT)) {
// действие на коллбек с неправильным ответом
            return incorrectAnswer(user);
        } else {
            return startNewQuiz(user);
        }
    }

    private List<PartialBotApiMethod<? extends Serializable>> correctAnswer(User user, String message) {
        //Log.info("correct");
        final int currentScore = user.getScore() + 1;
        user.setScore(currentScore);
        userRepository.save(user);

        return nextQuestion(user);
    }

    private List<PartialBotApiMethod<? extends Serializable>> incorrectAnswer(User user) {
        final int currentScore = user.getScore();

// Обновляем лучший итог

        if (user.getHighScore() < currentScore) {
            user.setHighScore(currentScore);
        }

// Меняем статус пользователя

        user.setScore(0);
        user.setBotState(State.NONE);
        userRepository.save(user);


// Создаем кнопку для повторного начала игры

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = List.of(
                createInlineKeyboardButton("Try again?", QUIZ_START));

        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne));
        SendMessage result = createMessageTemplate(user);
        result.setText(String.format("Incorrect!%nYou scored *%d* points!", currentScore));
        result.setReplyMarkup(inlineKeyboardMarkup);
        return List.of(result);
    }

    private List<PartialBotApiMethod<? extends Serializable>> startNewQuiz(User user) {
        user.setBotState(State.PLAYING_QUIZ);
        userRepository.save(user);

        return nextQuestion(user);
    }

    private List<PartialBotApiMethod<? extends Serializable>> nextQuestion(User user) {
        Question question = questionRepository.getRandomQuestion();
// Собираем список возможных вариантов ответа
        List<String> options = new ArrayList<>(List.of(question.getCorrectAnswer(), question.getOptionOne(), question.getOptionTwo(), question.getOptionThree()));
// Перемешиваем
        Collections.shuffle(options);
// Начинаем формировать сообщение с вопроса
        StringBuilder sb = new StringBuilder();
        sb.append('*')
                .append(question.getQuestion())
                .append("*\n\n");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
// Создаем два ряда кнопок
        List<InlineKeyboardButton> inlineKeyboardButtonsRowOne = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonsRowTwo = new ArrayList<>();
// Формируем сообщение и записываем CallBackData на кнопки
        for (int i = 0; i < options.size(); i++) {
            InlineKeyboardButton button = new InlineKeyboardButton();
            final String callbackData = options.get(i).equalsIgnoreCase(String.valueOf(question.getAnswers())) ? QUIZ_CORRECT : QUIZ_INCORRECT;
            button.setText(OPTIONS.get(i));
            button.setCallbackData(String.format("%s %d", callbackData, question.getId()));
            if (i < 2) {
                inlineKeyboardButtonsRowOne.add(button);
            } else {
                inlineKeyboardButtonsRowTwo.add(button);
            }
            sb.append(OPTIONS.get(i)).append(". ").append(options.get(i));
            sb.append("\n");
        }
        inlineKeyboardMarkup.setKeyboard(List.of(inlineKeyboardButtonsRowOne, inlineKeyboardButtonsRowTwo));
        SendMessage result = createMessageTemplate(user);
        result.setText(sb.toString());
        result.setReplyMarkup(inlineKeyboardMarkup);
        return List.of(result);
    }


    @Override
    public State operatedBotState() {
        return null;
    }

    @Override
    public List<String> operatedCallBackQuery() {
        return List.of(QUIZ_START, QUIZ_CORRECT, QUIZ_INCORRECT);
    }
}
