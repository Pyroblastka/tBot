package com.pyro.api;

import com.pyro.entities.City;
import com.pyro.entities.Message;
import com.pyro.repositories.CityRepository;
import com.pyro.repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MessageController {


    @Autowired
    CityRepository cityRepository;

    @Autowired
    MessageRepository messageRepository;

    @GetMapping("/messages")
    List<Message> all() {
        return messageRepository.findAll();
    }

    @GetMapping("/city/{id}/messages")
    List<Message> messagesOfOne(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
            City city = cityRepository.findById(id)
                    .orElseThrow(ChangeSetPersister.NotFoundException::new);
        return city.getMessages();
    }

    @GetMapping("/message/{id}")
     Message oneMessage(@PathVariable Long id) throws ChangeSetPersister.NotFoundException {
        return messageRepository.findById(id)
                .orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    @PostMapping("/city/{id}/message")
    City newMessageCity(@PathVariable Long id, @RequestParam String newMessage) {
        City city = cityRepository.findById(id).get();
        city.addMessage(new Message(newMessage));
        return cityRepository.saveAndFlush(city);
    }

    @PatchMapping("message/{id}")
    Message updateMessage(@PathVariable Long id, @RequestParam String newMessage){
        Message message = messageRepository.getOne(id);
        message.setText(newMessage);
        return messageRepository.saveAndFlush(message);
    }

    @DeleteMapping("/message/{id}")
    void deleteOne(@PathVariable Long id) {
         City city = cityRepository.findByMessagesContains(messageRepository.getOne(id));
         List<Message> list = city.getMessages();
         list.removeIf(o ->(o.getId()==id));
         city.setMessages(list);
         cityRepository.saveAndFlush(city);
    }

    @DeleteMapping("/city/{id}/messages")
    void clearCityMessages(@PathVariable Long id) {
        City city = cityRepository.findById(id).get();
        city.clearMessages();
        cityRepository.saveAndFlush(city);
    }
}
