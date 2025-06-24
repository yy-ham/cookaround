package com.project.cookaround.domain.member.service;

import com.project.cookaround.domain.member.entity.EmailVerificationResult;
import jakarta.servlet.http.HttpSession;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;

@Service
public class EmailVerificationService {

    private final JavaMailSender javaMailSender;

    public EmailVerificationService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    // 이메일 인증코드 발송
    public String sendVerificationEmail(String email) {
        String verificationCode = generateVerificationCode();

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("[요리조리쿡] 회원가입 인증 코드 안내");
            message.setText("인증코드 : " + verificationCode);
            javaMailSender.send(message);
            return verificationCode;
        } catch (MailException e) {
            System.out.println("이메일 발송 실패 : " + e.getMessage());
            return null;
        }
    }

    // 인증코드 6자리 생성
    private String generateVerificationCode() {
        Random random = new Random();
        int code = random.nextInt(900000) + 100000;
        return String.valueOf(code);
    }

    // 이메일 및 인증코드 일치 여부 검사
    public EmailVerificationResult verifyVerificationCode(String email, String verificationCode, HttpSession session) {
        LocalDateTime now = LocalDateTime.now();
        String storedEmail = session.getAttribute("email").toString();
        String storedVerificationCode = session.getAttribute("verificationCode").toString();
        LocalDateTime storedIssuedAt = (LocalDateTime) session.getAttribute("issuedAt");
        long diffMinutes = Duration.between(storedIssuedAt, now).toMinutes();

        if (diffMinutes >= 5) {
            return EmailVerificationResult.EXPIRED;
        }

        if (email.equals(storedEmail) && verificationCode.equals(storedVerificationCode)) {
            return EmailVerificationResult.SUCCESS;
        } else {
            return EmailVerificationResult.INVALID;
        }
    }

}


