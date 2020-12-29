package com.mnuo.fopink.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutingConfig {
	/**
	 * routing
	 * @return
	 */
	@Bean
	public Queue queueOne(){
		return new Queue("one", true);
	}
	
	@Bean
	public DirectExchange directExchange(){
		return new DirectExchange("direct_exchange");
	}
	@Bean
	public Binding queueOneBind(){
		return BindingBuilder.bind(queueOne()).to(directExchange()).with("one")
				;
	}
	
	/**
	 * topic
	 * @return
	 */
	 //创建交换器
	@Bean
	public Queue queueTwo(){
		return new Queue("two", true);
	}
    @Bean
    TopicExchange exchange() {
        return new TopicExchange("topic_exchange");
    }
    @Bean
    public Binding queueTwoBind(){
    	return BindingBuilder.bind(queueTwo()).to(exchange()).with("topic.#")
    			;//*表示一个词,#表示零个或多个词
    }
    
    //fanout
    @Bean
	public Queue queueThree(){
		return new Queue("three", true);
	}
    public Queue queueFouth(){
    	return new Queue("fouth", true);
    }
    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout_exchange");
    }
    @Bean
    public Binding queueThreeBind(){
    	return BindingBuilder.bind(queueThree()).to(fanoutExchange());
    }
    @Bean
    public Binding queueFouthBind(){
    	return BindingBuilder.bind(queueFouth()).to(fanoutExchange());
    }
}
