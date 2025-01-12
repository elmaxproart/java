package Sea.incubator.sgdeb.service.impl;

import Sea.incubator.sgdeb.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationImp implements NotificationService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Override
    public void sendNotification(String message) {
        messagingTemplate.convertAndSend("/topic/notifications", message);
    }
}
