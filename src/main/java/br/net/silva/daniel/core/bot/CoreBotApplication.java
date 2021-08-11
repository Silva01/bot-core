package br.net.silva.daniel.core.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import javax.annotation.PostConstruct;

@SpringBootApplication
@Slf4j
public class CoreBotApplication {

    @Autowired
    private BotConfig botConfig;

    public static void main(String[] args) {
        SpringApplication.run(CoreBotApplication.class, args);
    }

    @PostConstruct
    private void botExecution(){
        try {
            log.info("Iniciando Bot ");
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(botConfig);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
