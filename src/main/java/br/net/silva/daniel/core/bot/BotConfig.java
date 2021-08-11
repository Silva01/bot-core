package br.net.silva.daniel.core.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
@Slf4j
public class BotConfig extends TelegramLongPollingBot {

    @Value("${bot.telegram.token}")
    private String botToken;

    @Value("${bot.telegram.name}")
    private String botUsername;

    private boolean trava;

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
        log.info("Iniciando bot");
        if (update.hasMessage()) {
            Message message = update.getMessage();

            try {
                if (message.hasText()) {
                    SendMessage send = new SendMessage();
                    String messageUser = message.getText();
                    String userIdTelegram = message.getChatId().toString();
                    if (trava) {
                        trava = false;
                        send.setChatId(userIdTelegram);
                        send.enableHtml(true);
                        send.setText(String.format("Olá! Sou a Camila, o senhor falou %s", messageUser));
                        execute(send);
                    } else if (messageUser.trim().toUpperCase().startsWith(String.format("%s!", getBotUsername().toUpperCase()))) {
                        trava = true;
                        send.setChatId(userIdTelegram);
                        send.enableHtml(true);
                        send.setText("Olá! Sou a Camila, estou a sua disposição mestre");
                        execute(send);
                    } else if (messageUser.trim().toUpperCase().startsWith(String.format("%s,", getBotUsername().toUpperCase()))) {
                        send.setChatId(userIdTelegram);
                        send.enableHtml(true);
                        send.setText("Olá! Sou a Camila, estou a sua disposição mestre para qualquer coisa");
                        execute(send);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
