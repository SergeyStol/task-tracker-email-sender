package dev.sstol.tasktrackeremailsender.infrastructure.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Sergey Stol
 * 2024-10-12
 */
@Configuration
public class RabbitMQConfig {
   public static final String USER_CREATED = "USER_CREATED";
   public static final String USER_CREATED_QUEUE = "USER_CREATED_QUEUE";
   public static final String EXCHANGE_NAME = "TASKS_TRACKER_EXCHANGE";

   @Bean
   public Queue queue() {
      return new Queue(USER_CREATED_QUEUE, true);
   }

   @Bean
   public Binding bindingT1(Queue queue, DirectExchange exchange) {
      return BindingBuilder.bind(queue).to(exchange).with(USER_CREATED);
   }

   @Bean
   public DirectExchange directExchange() {
      return new DirectExchange(EXCHANGE_NAME);
   }

   @Bean
   public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
      RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
      rabbitTemplate.setMessageConverter(jsonMessageConverter());
      return rabbitTemplate;
   }

   @Bean
   public Jackson2JsonMessageConverter jsonMessageConverter() {
      return new Jackson2JsonMessageConverter();
   }
}