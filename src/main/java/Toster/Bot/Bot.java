package Toster.Bot;


import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class Bot extends TelegramLongPollingBot {

//    @PostConstruct
//    public void Construct(){
//        System.out.println("флюгигенхайме");
//    }

    //@Value("${bot.name}")
    private String botUsername ="CrazyHumburgerBot";

    //@Value("${bot.token}")
    private String botToken = "1422971722:AAH8N1pkvZY8bZhUHPtO6hQh0d9MSMiXe5A";

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        try {
            if (update.hasMessage() && update.getMessage().hasText()) {
                Message inMessage = update.getMessage();
                SendMessage outMessage = new SendMessage();
                outMessage.setChatId(String.valueOf(inMessage.getChatId()));
                outMessage.setText(inMessage.getText());
                execute(outMessage);
            }
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}
