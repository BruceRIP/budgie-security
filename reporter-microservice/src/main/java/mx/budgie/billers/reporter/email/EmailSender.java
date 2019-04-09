/**
 * 
 */
package mx.budgie.billers.reporter.email;

import java.io.StringWriter;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import mx.budgie.billers.reporter.exception.BillersEmailException;
import mx.budgie.billers.reporter.vo.EmailSenderVO;
import mx.budgie.billers.reporter.vo.EmailTemplateType;

/**
 * @author bruce
 *
 */
@Component
public class EmailSender{

	private static final Logger LOGGER = LogManager.getLogger(EmailSender.class);
	
	@Value("${billers.core.smtp.username}")
	private String username;
	@Value("${billers.core.smtp.password}")
	private String password;
	@Value("${billers.core.smtp.host}")
	private String host;
	@Value("${billers.core.smtp.port}")
	private String port;
	@Value("${billers.core.smtp.bcc}")
	private String bcc;	
	
	public void send(EmailSenderVO emailSender) throws BillersEmailException{		
		try {
			LOGGER.info("Setting message from & to");
			Message message = new MimeMessage(configureProperties());
			message.setFrom(new InternetAddress(emailSender.getFrom()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailSender.getTo()));
			message.setRecipients(Message.RecipientType.BCC, InternetAddress.parse(bcc));			
			message.setSubject(emailSender.getSubject());
			LOGGER.info("Setting body part message");
			Multipart multipart = new MimeMultipart("related");
			BodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(configTemplate(emailSender.getTemplateType(), emailSender.getMessage(), emailSender.getLink()), "text/html");
			LOGGER.info("Attaching file");			
//			DataSource source = new FileDataSource(transmitter.getFileXML().getAbsolutePath());
//			messageBodyPart.setDataHandler(new DataHandler(source));
//			messageBodyPart.setFileName(transmitter.getFileXML().getName());
			multipart.addBodyPart(messageBodyPart);
			
//			addAttachment(multipart, transmitter.getString("fileXMLAbsolutePath"), transmitter.getString("fileXMLName"));
//			addAttachment(multipart, transmitter.getString("filePDFAbsolutePath"), transmitter.getString("filePDFName"));						
//			
			// Send the complete message parts
			message.setContent(multipart);
			LOGGER.info("Sending message");
			// Send message
			Transport.send(message);
			LOGGER.info("Message sended");
			/*LOGGER.info("Removing file");
			transmitter.getFileXML().delete();
			transmitter.getFilePDF().delete();
			LOGGER.info("File was removed");	*/
		} catch (MessagingException e) {
			LOGGER.error("Error ocurred: {}", e);
			throw new BillersEmailException(99, e.getMessage(), e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Error ocurred: {}", e);
			throw new BillersEmailException(99, e.getMessage(), e.getMessage());
		}
	}
	
	/**
	 * Configure properties
	 * @return
	 */
	private Session configureProperties(){
		LOGGER.info("Configuring Email properties");
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.port", port);
		return Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				LOGGER.info("Setting user and password");
				return new PasswordAuthentication(username, password);
			}
		});
	}
	
	/**
	 * Configure template
	 * @param templateType
	 * @param message
	 * @param link
	 * @return
	 * @throws Exception
	 */
	protected String configTemplate(EmailTemplateType templateType, String message, String link) throws Exception {
		VelocityContext context = new VelocityContext();
		context.put("action", (link == null || link.isEmpty()) ? "https://www.google.com": link);
		context.put("message", message);
		context.put("fname", "Zubayer");
		context.put("lname", "Ahamed");
		context.put("proprietor", "coderslab.com");
		return buildingMerge(context, templateType, message);		
	}
	
	/**
	 * getting a specific template
	 * @param templateType
	 * @return
	 * @throws Exception
	 */
	private Template getTemplateType(EmailTemplateType templateType) throws Exception {		 
		VelocityEngine ve = new VelocityEngine();
		ve.init();
		switch (templateType) {
		case ACTIVATE_ACCOUNT:
			return ve.getTemplate("src/main/resources/static/email-templates/activate-account.vm", "UTF8");			
		default:
			LOGGER.warn("You must setting email template");
			throw new Exception("Email template must be present");
		}
	}
	
	/**
	 * Merge template
	 * @param context
	 * @param templateType
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private String buildingMerge(VelocityContext context, EmailTemplateType templateType, String message) throws Exception {
		Template template = getTemplateType(templateType);
		StringWriter out = new StringWriter();
		template.merge(context, out);
		return out.toString();
	}
	
	public void addAttachment(Multipart multipart, String pathFilename, String filename) {
		LOGGER.info("Attaching file");	
		DataSource source = new FileDataSource(pathFilename);
	    BodyPart messageBodyPart = new MimeBodyPart();        
	    try {
			messageBodyPart.setDataHandler(new DataHandler(source));
			messageBodyPart.setFileName(filename);
		    multipart.addBodyPart(messageBodyPart);
		} catch (MessagingException e) {			
			LOGGER.error(e);
		}	    
	}
}