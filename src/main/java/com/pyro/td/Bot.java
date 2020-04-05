package com.pyro.td;

import com.pyro.entities.City;
import com.pyro.repositories.CityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
public class Bot extends TelegramLongPollingBot {

    static {
        ApiContextInitializer.init();
    }

    @PostConstruct
    public void registerBot(){

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    @Autowired
    CityRepository cityRepository;
    /**
     * Метод для приема сообщений.
     * @param update Содержит сообщение от пользователя.
     */

    @Transactional
    @Override
    public void onUpdateReceived(Update update) {
        String message = update.getMessage().getText();
        City city = cityRepository.findByNameContainsIgnoreCase(message);
        String response ="Извини, я не знаю ничего об этом городе(";
        if(city!=null)
            response=city.printMessages();
        sendMsg(update.getMessage().getChatId().toString(), response);
    }

    /**
     * Метод для настройки сообщения и его отправки.
     * @param chatId id чата
     * @param s Строка, которую необходимот отправить в качестве сообщения.
     */
    public synchronized void sendMsg(String chatId, String s) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.enableMarkdown(true);
        sendMessage.setChatId(chatId);
        sendMessage.setText(s);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
           // log.log(Level.SEVERE, "Exception: ", e.toString());
        }
    }

    /**
     * Метод возвращает имя бота, указанное при регистрации.
     * @return имя бота
     */
    @Override
    public String getBotUsername() {
        return "CityTouristBot";
    }

    /**
     * Метод возвращает token бота для связи с сервером Telegram
     * @return token для бота
     */
    @Override
    public String getBotToken() {
        return "1153567925:AAEWoiILPEa8JaZoC1nab_Pa4L0uCR_B-Tw";
    }
}