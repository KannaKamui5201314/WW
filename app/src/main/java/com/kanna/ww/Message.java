package com.kanna.ww;


public class Message {

    private String playerName;
    private String messageString;

    Message(String playerName, String message){
        this.playerName = playerName;
        this.messageString = message;
    }

    String getPlayerName(){
        return playerName;
    }

    String getMessage(){
        return messageString;
    }
}
