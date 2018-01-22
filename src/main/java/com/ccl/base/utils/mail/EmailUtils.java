/*package com.ccl.base.utils.mail;

import java.io.File;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

public class EmailUtils {
	
	private static Logger logger = LoggerFactory.getLogger(EmailUtils.class);
	
    private JavaMailSender mailSender;//spring配置中定义  
    
    private String from;//spring配置中定义  
    
    *//** 
     * 发送普通文本邮件 
     * 
     *//*  
    public void sendText(Msg msg){  
    	SimpleMailMessage m = new SimpleMailMessage();
        m.setTo(msg.getTo()); //接收人    
        m.setFrom(this.from); //发送人,从配置文件中取得 
        if(msg.getBcc()!=null){
        	m.setBcc(msg.getBcc());
        }
        if(msg.getCc()!=null){
        	 m.setCc(msg.getCc());
        };
        m.setSubject(msg.getSubject());  
        m.setText(msg.getContent());
        mailSender.send(m);
    } 
    
    *//** 
     * 发送普通Html邮件 
     * @throws MessagingException 
     * 
     *//*  
    public void sendHtml(Msg msg) throws MessagingException{  
    	
        MimeMessage mimeMessage = mailSender.createMimeMessage();  
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage,true);  
        try {  
            messageHelper.setTo(msg.getTo()); 
            messageHelper.setFrom(this.from);
            messageHelper.setSubject(msg.getSubject());  
            messageHelper.setText(msg.getContent(),msg.isHtml()); 
            if(msg.getBcc()!=null){
            	messageHelper.setBcc(msg.getBcc());
            }
            if(msg.getCc()!=null){
            	 messageHelper.setCc(msg.getCc());
            }
            String[] attachment = msg.getAttachment();
            FileSystemResource resource = null; 
            if(attachment!=null){
            	for(String a:attachment){
            		resource = new FileSystemResource(new File(a)); 
            		messageHelper.addAttachment(resource.getFilename(),resource);  
            		
            	}
            }
            //图片必须这样子：<img src='cid:image'/> ,还有种方式直接是图片服务器路径 
            Map<String,String> image = msg.getInlineImage();
            if(image!=null&&msg.isHtml()){
            	for(Iterator<Entry<String,String>>it = image.entrySet().iterator();it.hasNext();){
            		Entry<String,String> en = it.next();
            		resource = new FileSystemResource(new File(en.getValue())); 
            		messageHelper.addInline(en.getKey(),resource);  
            	}
            }
            mailSender.send(mimeMessage); 
        } catch (MessagingException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }   
    }  
   
    public static class Msg{
    	
		private String[] to;
		private String[] cc;
	    private String subject;  
	    private String content;
	    private String[] bcc;
	    private String[] attachment;
	    private Map<String,String> inlineImage;
	    private boolean html = false;
	    
	    
	   public Msg() {
		// TODO Auto-generated constructor stub
	   }
		public boolean isHtml() {
			return html;
		}

		public void setHtml(boolean html) {
			this.html = html;
		}

		public Map<String, String> getInlineImage() {
			return inlineImage;
		}

		public void setInlineImage(Map<String, String> inlineImage) {
			this.inlineImage = inlineImage;
		}

		public String[] getAttachment() {
			return attachment;
		}

		public void setAttachment(String[] attachment) {
			this.attachment = attachment;
		}


		public String[] getCc() {
			return cc;
		}

		public void setCc(String[] cc) {
			this.cc = cc;
		}

		public String[] getBcc() {
			return bcc;
		}

		public void setBcc(String[] bcc) {
			this.bcc = bcc;
		}

		public Msg(String[] to, String subject, String content) {
			
			this.to = to;
			this.subject = subject;
			this.content = content;
		}
		
		public Msg(String[] to,String[] cc, String subject, String content) {
			
			this.to = to;
			this.cc = cc;
			this.subject = subject;
			this.content = content;
		}
		
		public Msg(String[] to,String[] cc,String[] bcc, String subject, String content) {
			
			this.to = to;
			this.subject = subject;
			this.content = content;
			this.bcc = bcc;
			this.cc = cc;
		}
		
		public String[] getTo() {
			return to;
		}

		public void setTo(String[] to) {
			this.to = to;
		}

		public String getSubject() {
			return subject;
		}
		public void setSubject(String subject) {
			this.subject = subject;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}    
    }

	public String getFrom() {
		return from;
	}
	public void setFrom(String from) {
		this.from = from;
	}
	
    public JavaMailSender getMailSender() {  
        return mailSender;  
    }  
    public void setMailSender(JavaMailSender mailSender) {  
        this.mailSender = mailSender;  
    }  
}  */