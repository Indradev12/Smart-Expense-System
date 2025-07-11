package com.alertservice.ExternalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.alertservice.Entities.User;
import com.alertservice.Service.AlertService;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class JavaMailService {

    @Autowired
    public JavaMailSender mailSender;

    @Autowired
    public AlertService fService;

    public void sendAlertMail(String userId, String toMail){

        User u = fService.getUser(userId);

        Double budget = fService.getUserBudget(userId);

        String message = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "  <meta charset='UTF-8'>"
                    + "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                    + "  <style>"
                    + "    body {"
                    + "      font-family: Arial, sans-serif;"
                    + "      background-color: #f4f4f4;"
                    + "      margin: 0;"
                    + "      padding: 0;"
                    + "    }"
                    + "    .container {"
                    + "      max-width: 600px;"
                    + "      margin: 20px auto;"
                    + "      background: #ffffff;"
                    + "      padding: 20px;"
                    + "      border-radius: 8px;"
                    + "      box-shadow: 0 0 10px rgba(0,0,0,0.1);"
                    + "    }"
                    + "    .header {"
                    + "      text-align: center;"
                    + "      padding-bottom: 20px;"
                    + "      border-bottom: 1px solid #dddddd;"
                    + "    }"
                    + "    .header h2 {"
                    + "      color: #333333;"
                    + "    }"
                    + "    .content {"
                    + "      margin-top: 20px;"
                    + "      color: #555555;"
                    + "      line-height: 1.6;"
                    + "    }"
                    + "    .highlight {"
                    + "      color: #e74c3c;"
                    + "      font-weight: bold;"
                    + "    }"
                    + "    .footer {"
                    + "      margin-top: 30px;"
                    + "      font-size: 12px;"
                    + "      color: #999999;"
                    + "      text-align: center;"
                    + "    }"
                    + "  </style>"
                    + "</head>"
                    + "<body>"
                    + "  <div class='container'>"
                    + "    <div class='header'>"
                    + "      <h2>Budget Alert 🚨</h2>"
                    + "    </div>"
                    + "    <div class='content'>"
                    + "      <p>Hi <strong>"+u.getName()+"</strong>,</p>"
                    + "      <p>This is a friendly reminder that your spending for the category "
                    + "      Your Budget has reached "
                    + "      <span class='highlight'>80%</span> of your budget limit.</p>"
                    + "      <p>Your total budget for this month is "
                    + "      <strong>₹"+budget+"</strong>.</p>"
                    + "      <p>Please review your expenses to avoid overspending.</p>"
                    + "    </div>"
                    + "    <div class='footer'>"
                    + "      <p>Smart Daily Expense Tracker</p>"
                    + "    </div>"
                    + "  </div>"
                    + "</body>"
                    + "</html>";

    MimeMessage mimeMessage =mailSender.createMimeMessage();
    try {
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
            mimeMessageHelper.setSubject("Smart Expense System: Budget Alert");
            mimeMessageHelper.setFrom(new InternetAddress("xyz@taskmate.com"));
            // mimeMessageHelper.setBcc(mentor.getEmail());
            mimeMessageHelper.setTo(toMail);
            // Set the email content as HTML
            mimeMessageHelper.setText(message, true); // true indicates HTML content
            mailSender.send(mimeMessageHelper.getMimeMessage());
    } catch (Exception e) {
        e.printStackTrace();
    }



    }
}
