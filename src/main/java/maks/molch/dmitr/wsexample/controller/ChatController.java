package maks.molch.dmitr.wsexample.controller;

import lombok.AllArgsConstructor;
import maks.molch.dmitr.wsexample.model.ChatMessage;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@AllArgsConstructor
public class ChatController {
    private SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(@Payload ChatMessage chatMessage) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(@Payload ChatMessage chatMessage) {
        chatMessage.setType(ChatMessage.MessageType.JOIN);
        return chatMessage;
    }

    @MessageMapping("/chat.leaveUser")
    @SendTo("/topic/public")
    public ChatMessage leaveUser(@Payload ChatMessage chatMessage) {
        chatMessage.setType(ChatMessage.MessageType.LEAVE);
        return chatMessage;
    }

    @MessageMapping("/chat.sendPrivateMessage")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSendToUser(chatMessage.getRecipient(), "/private", chatMessage);
    }
}

