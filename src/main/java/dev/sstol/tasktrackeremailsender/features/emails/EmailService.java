package dev.sstol.tasktrackeremailsender.features.emails;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sstol.tasktrackeremailsender.features.users.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import static dev.sstol.tasktrackeremailsender.infrastructure.rabbitmq.RabbitMQConfig.USER_CREATED_QUEUE;

/**
 * @author Sergey Stol
 * 2024-10-12
 */
@Service
@RequiredArgsConstructor
@RabbitListener(queues = USER_CREATED_QUEUE)
public class EmailService {
   private final JavaMailSender emailSender;
   private final ObjectMapper objectMapper;

   public void sendSimpleMessage(String to, String subject, String text) {
      SimpleMailMessage message = new SimpleMailMessage();
      message.setFrom("sergeystol123@gmail.com");
      message.setTo(to);
      message.setSubject(subject);
      message.setText(text);
      emailSender.send(message);
   }

   @RabbitHandler
   public void receiveMessage(String message) throws JsonProcessingException {
      System.out.println("Received <" + message + ">");
      UserDto userDto = objectMapper.readValue(message, UserDto.class);
      String emailText = """
        Congratulations! A new user had been created.
        id: %s
        email: %s
        """.formatted(userDto.id(), userDto.email());
      sendSimpleMessage("1asspromo@gmail.com", "Registered information", emailText);
   }
}
