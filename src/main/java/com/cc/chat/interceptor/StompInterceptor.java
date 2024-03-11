package com.cc.chat.interceptor;

import com.cc.auth.JwtTokenProvider;
import com.cc.exception.InvalidTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
public class StompInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        final StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {
            String token = resolveToken(accessor);
            if(token == null || !jwtTokenProvider.isValidToken(token)) {
                throw new InvalidTokenException();
            }
        }
        // 테스트 환경에서 subscribe header 에 값을 줄 수가 없어 주석 처리
//        else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
//            String token = resolveToken(accessor);
//
//            if(token == null || !jwtTokenProvider.isValidToken(token)) {
//                throw new InvalidTokenException();
//            } else {
//                String SubscribeDestination = accessor.getDestination();
//                String roomId = SubscribeDestination.split("/")[3];
//                // TODO: Check if user is allowed to subscribe to this room
//            }
//        }

        return message;
    }

    private String resolveToken(StompHeaderAccessor accessor) {
        String bearerToken = accessor.getFirstNativeHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
