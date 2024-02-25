package com.cc.member.service;


import com.cc.exception.ExpiredVerifyCodeException;
import com.cc.exception.FailedToSendEmailException;
import com.cc.exception.WrongVerifyCodeException;
import com.cc.member.domain.VerifyType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {

    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;

    public void sendVerifyCode(VerifyType type, String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String verifyCode = generateVerifyCode();

        try {
            mailMessage.setTo(email);
            mailMessage.setSubject("CC %s 인증 코드".formatted(type.getName()));
            mailMessage.setText("인증 코드: %s".formatted(verifyCode));
            javaMailSender.send(mailMessage);

            redisTemplate.opsForValue().set("verify-code:%s:%s".formatted(type, email), verifyCode, 5, TimeUnit.MINUTES);
        } catch (MailException e) {
            throw new FailedToSendEmailException();
        }
    }

    private String generateVerifyCode() {
        Random random = new Random();
        int randomCode = random.nextInt(10000);
        return String.format("%04d", randomCode);
    }

    public String checkVerifyCode(VerifyType type, String email, String code) {
        String verifyCode = Optional.ofNullable(redisTemplate.opsForValue().get("verify-code:%s:%s".formatted(type, email)))
                .orElseThrow(ExpiredVerifyCodeException::new);

        if (!code.equals(verifyCode)) {
            throw new WrongVerifyCodeException();
        }

        UUID uuid = UUID.randomUUID();
        String deviceId = uuid.toString().substring(0, 8);
        redisTemplate.opsForValue().set("verified-at:%s:%s".formatted(type, deviceId), email, 3, TimeUnit.HOURS);

        return deviceId;
    }
}
