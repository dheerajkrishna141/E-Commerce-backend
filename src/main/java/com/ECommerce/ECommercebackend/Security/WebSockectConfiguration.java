//package com.ECommerce.ECommercebackend.Security;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.security.authorization.AuthorizationManager;
//import org.springframework.web.socket.config.annotation.EnableWebSocket;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocket
//@EnableWebSocketMessageBroker
//public class WebSockectConfiguration implements WebSocketMessageBrokerConfigurer {
//
//	@Override
//	public void registerStompEndpoints(StompEndpointRegistry registry) {
//
//		registry.addEndpoint("/websocket").setAllowedOriginPatterns("**").withSockJS();
//	}
//
//	@Override
//	public void configureMessageBroker(MessageBrokerRegistry registry) {
//		registry.enableSimpleBroker("/topic");
//		registry.setApplicationDestinationPrefixes("/app");
//	}
//
//	private AuthorizationManager<Message<?>> makeMessageAuthorizationMananger() {
//		return null;
//
//	}
//
//}
