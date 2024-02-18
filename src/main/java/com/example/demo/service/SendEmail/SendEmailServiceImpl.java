package com.example.demo.service.SendEmail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.example.demo.model.MemberInfo;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;

import io.github.cdimascio.dotenv.Dotenv;

@Service
public class SendEmailServiceImpl implements SendEmailService {

	Dotenv dotenv = Dotenv.load();

	@Autowired
	private SendGrid sg;

	@Autowired
	public SendEmailServiceImpl(SendGrid sendGrid) {
		this.sg = sendGrid;
	}

	private static final Logger log = LoggerFactory.getLogger(SendEmailServiceImpl.class);

	/**
	 * 新規会員登録完了メール
	 * 
	 * @param 会員情報
	 */
	@Override
	public void sendMemberRegistrationEmail(MemberInfo memberInfo) {

		try {
			String mailTemplateID = dotenv.get("MAIL_TEMPLATE_ID_MEMBER_REGISTRATION");
			Request request = new Request();
			request.setMethod(Method.POST);
			request.setEndpoint("mail/send");
			request.setBody("{\"personalizations\":[{\"to\":[{\"email\":\"sendEmail\"}],"
					+ "\"dynamic_template_data\":{\"name\": \"sendName\", \"email\":\"sendEmail\", \"phoneNumber\":\"sendPhoneNumber\", \"age\":\"sendAge\", \"gender\":\"sendGender\"},"
					+ "}]," + "\"from\":{\"email\":\"test@test.com\"}," + "\"template_id\": \"sendMailTemplateID\"}");
			String replaceBody = request.getBody().replace("sendEmail", memberInfo.getEmail())
					.replace("sendName", memberInfo.getLastName() + memberInfo.getFirstName())
					.replace("sendPhoneNumber", memberInfo.getPhoneNumber())
					.replace("sendAge", memberInfo.getAge().toString()).replace("sendGender", memberInfo.getGender())
					.replace("sendMailTemplateID", mailTemplateID);
			request.setBody(replaceBody);
			log.info("requestBody：" + request.getBody());
			Response response = sg.api(request);
			if (response.getStatusCode() == HttpStatus.ACCEPTED.value()) {
				log.info("メール送信成功");
			} else {
				log.error("メール送信失敗：" + response.getBody());
			}
		} catch (Exception e) {
			log.error("メール送信失敗：" + e.getMessage());
		}
	}

}
