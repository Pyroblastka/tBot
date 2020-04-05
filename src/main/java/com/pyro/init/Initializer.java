package com.pyro.init;


import com.pyro.entities.City;
import com.pyro.entities.Message;
import com.pyro.repositories.CityRepository;
import com.pyro.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.Ordered;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

//@RequiredArgsConstructor
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class Initializer implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    MessageRepository messageRepository;

    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {

        if (cityRepository.findAll().size() == 0) {
            System.out.println("\n______________INITIALIZATION______________");
            City city = new City("New York");
            Message message = new Message("Spider-Man lives here!");
            city.addMessage(message);
            message = new Message("Тут можно посмотреть на Статую Свободы, Центральный парк и Таймс-Сквер.");
            city.addMessage(message);
            cityRepository.saveAndFlush(city);

            city = new City("Москва");
            message = new Message("Не забудьте посетить Красную Площадь.");
            city.addMessage(message);
            message = new Message("Ну а в ЦУМ можно и не заходить))).");
            city.addMessage(message);
            cityRepository.saveAndFlush(city);

            city = new City("Минск");
            message = new Message("Можно сходить на площадь Победы, посмотреть на Национальную библиотеку.");
            city.addMessage(message);
            message = new Message("Там есть Музей истории Великой Отечественной войны.");
            city.addMessage(message);
            cityRepository.saveAndFlush(city);

            city = new City("Полоцк");
            message = new Message("Можно посмотреть на Софийский собор.");
            city.addMessage(message);
            message = new Message("Стоит посетить кадетский корпус Полоцкого Государственного Университета. Там в столовой отлично кормят ;)");
            city.addMessage(message);
            cityRepository.saveAndFlush(city);
        }
        System.out.println("done");
    }

}
