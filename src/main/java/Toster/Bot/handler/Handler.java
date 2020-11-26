package Toster.Bot.handler;

import Toster.Bot.State;
import Toster.entity.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;

import java.util.List;

import java.io.Serializable;

public interface Handler {

    // основной метод, который будет обрабатывать действия пользователя

    List<PartialBotApiMethod<? extends Serializable>> handle(User user, String message);
// метод, который позволяет узнать, можем ли мы обработать текущий State у пользователя

    State operatedBotState();
// метод, который позволяет узнать, какие команды CallBackQuery мы можем обработать в этом классе

    List<String> operatedCallBackQuery();
}
