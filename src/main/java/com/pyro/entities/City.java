package com.pyro.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class City extends AbstractEntity {

    @Column(unique = true)
    private String name;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER
    )
    private List<Message> messages= new ArrayList<>();

    public City(){
    }

    public City(String name) {
        this.name = name;
    }

    public City(String name, Message msg) {
        this.name = name;
        this.messages.add(msg);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public void addMessage(Message msg){
        this.messages.add(msg);
    }

    public void addMessage(String msg){
        this.messages.add(new Message(msg));
    }

    public void clearMessages(){
        this.messages.clear();
    }

    public String printMessages(){
        StringBuilder output = new StringBuilder();
        for(Message i : messages) {
            output.append(i.getText());
            output.append("\n");
        }
        return output.toString();
    }
}
