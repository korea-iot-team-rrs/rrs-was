package com.korit.projectrrs.service.implement;

import com.korit.projectrrs.provider.JwtProvider;
import com.korit.projectrrs.service.MailService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;
    private final JwtProvider jwtProvider;

    @Value("${spring.mail.username}")
    private String senderEmail;

    @Override
    public MimeMessage createMailForId (String email, String username, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);

        String subject;
        String body;

        subject = "RRS 이메일 인증 링크";
        body = "<h3> RRS 이메일 인증 링크입니다.</h3>";
        body += "<a href=\"http://localhost:3000/find-id/" + token + "\"> 해당 링크를 클릭하여 인증을 완료해 주세요.</a>";
        body += "<p>감사합니다.</p>";

        message.setSubject(subject);
        message.setText(body, "UTF-8", "html");
        return message;
    }

    @Override
    public MimeMessage createMailForPw(String email, String username, String token) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        message.setFrom(senderEmail);
        message.setRecipients(MimeMessage.RecipientType.TO, email);

        String subject;
        String body;

        subject = "RRS 이메일 인증 링크";
        body = "<h3>" + username + "님 RRS 이메일 인증 링크입니다.</h3>";
        body += "<a href=\"http://localhost:3000/find-password/" + token + "\"> 해당 링크를 클릭하여 인증을 완료해 주세요.</a>";
        body += "<p>감사합니다.</p>";

        message.setSubject(subject);
        message.setText(body, "UTF-8", "html");
        return message;
    }
}