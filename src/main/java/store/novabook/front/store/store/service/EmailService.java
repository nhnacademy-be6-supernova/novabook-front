package store.novabook.front.store.store.service;

import java.io.UnsupportedEncodingException;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import store.novabook.front.common.exception.ErrorCode;
import store.novabook.front.common.exception.InternalServerException;
import store.novabook.front.store.store.dto.SendEmailRequest;

@Service
@RequiredArgsConstructor
public class EmailService {

	public static final String TO_MAIL = "dev2say@gmail.com";
	private final JavaMailSender mailSender;

	public void sendEmail(SendEmailRequest request) {
		try {
			MimeMessage message = mailSender.createMimeMessage();
			message.setFrom(new InternetAddress(request.email(), request.name()));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(TO_MAIL));

			String emailSubject = (request.orderNumber() != null && !request.orderNumber().isEmpty()) ?
				"[#" + request.orderNumber() + "] " + request.subject() : request.subject();

			message.setSubject(emailSubject);
			String fullMessage = "보낸 사람: " + request.email() + "\n\n주문 번호: " + request.orderNumber() + "\n\n메시지:\n" + request.message();
			message.setText(fullMessage);
			mailSender.send(message);
		} catch (MessagingException | UnsupportedEncodingException e) {
			throw new InternalServerException(ErrorCode.SENDING_EMAIL_ERROR);
		}
	}
}