package br.com.jean.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import br.com.jean.Service.utils.Utils;
import software.amazon.awssdk.core.SdkBytes;
import software.amazon.awssdk.services.ses.SesClient;
import software.amazon.awssdk.services.ses.model.RawMessage;
import software.amazon.awssdk.services.ses.model.SendRawEmailRequest;
import software.amazon.awssdk.services.ses.model.SesException;

public class SESService {
	
	public static void sendMessage() {
		String sender = "tavares1988@live.com";
		String recipient = "tavares1988@live.com";
		String subject = "E-mail de teste";

		SesClient client = Utils.getSesClient();

		String bodyText = "Hello,\r\n" + "See the list of emails. ";

		String bodyHTML = "<html>" + "<head></head>" + "<body>" + "<h1>Hello!</h1>" + "<p> See the list of emails.</p>" + "</body>" + "</html>";

		try {
			send(client, sender, recipient, subject, bodyText, bodyHTML);
			client.close();
			System.out.println("Done");

		}catch(IOException| MessagingException e) {
			e.getStackTrace();
		}
	}

	private static void send(SesClient client,
      String sender,
      String recipient,
      String subject,
      String bodyText,
      String bodyHTML
      ) throws AddressException, MessagingException, IOException {

		Session session = Session.getDefaultInstance(new Properties());
		MimeMessage message = new MimeMessage(session);
		
		// Add subject, from and to lines
		message.setSubject(subject, "UTF-8");
		message.setFrom(new InternetAddress(sender));
		message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient));
		
		MimeMultipart msgBody = new MimeMultipart();
		
		MimeBodyPart textPart = new MimeBodyPart();
		textPart.setContent(bodyText, "text/plain; charset=UTF-8");
		
		MimeBodyPart htmlPart = new MimeBodyPart();
		htmlPart.setContent(bodyHTML, "text/html; charset=UTF-8");
		
		msgBody.addBodyPart(textPart);
		msgBody.addBodyPart(htmlPart);
		
		message.setContent(msgBody);
		
		try {
			System.out.println("Attempting to send an email through Amazon SES " + "using the AWS SDK for Java...");
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			message.writeTo(outputStream);
			ByteBuffer buf = ByteBuffer.wrap(outputStream.toByteArray());
			
			byte[] arr = new byte[buf.remaining()];
			buf.get(arr);
			
			SdkBytes data = SdkBytes.fromByteArray(arr);
			RawMessage rawMessage = RawMessage.builder()
				.data(data)
				.build();
			
			SendRawEmailRequest rawEmailRequest = SendRawEmailRequest.builder()
				.rawMessage(rawMessage)
				.build();
			
			client.sendRawEmail(rawEmailRequest);
		
		} catch (SesException e) {
			System.err.println(e.awsErrorDetails().errorMessage());
			System.exit(1);
		}
		
	}
}
