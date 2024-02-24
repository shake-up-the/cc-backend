package com.cc.member.service;


import com.cc.exception.FailedToSendEmailException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class EmailVerifyService {

    private final JavaMailSender javaMailSender;
    private final RedisTemplate<String, String> redisTemplate;

    public void sendVerifyCode(String email) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        String verifyCode = generateVerifyCode();
        redisTemplate.opsForValue().set("verify-code:" + email, verifyCode, 5, TimeUnit.MINUTES);

        try {
            mailMessage.setTo(email);
            mailMessage.setSubject("CC 이메일 인증 코드");
            mailMessage.setText("인증 코드: " + verifyCode);
            javaMailSender.send(mailMessage);
        } catch (MailException e) {
            throw new FailedToSendEmailException();
        }
    }

    private String generateVerifyCode() {
        Random random = new Random();
        int randomCode = random.nextInt(10000);
        return String.format("%04d", randomCode);
    }
}
