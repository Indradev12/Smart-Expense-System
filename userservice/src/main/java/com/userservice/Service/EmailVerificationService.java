package com.userservice.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.userservice.Entities.Tokens;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailVerificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TokensService tokensService;

    @Autowired
    private UserService uService;

    // ============ 1. SEND VERIFICATION EMAIL ============
    public ResponseEntity<?> sendEmail(String email) {
        if (uService.UserExistsByEmail(email)) {
            return ResponseEntity.badRequest().body("Email already exists.");
        }

        // Save user with verified = false
        // user.setVerified(false);
        // uService.saveUser(user);

        // Create token
        Tokens token = new Tokens();
        token.setUserEmail(email);
        long otp = 100_000 + new SecureRandom().nextInt(900_000);
        token.setConfirmationToken(String.valueOf(otp));
        token.setCreatedDate(LocalDateTime.now());
        tokensService.saveToken(token);

        // Send mail
        try {
            MimeMessage mime = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mime, true);
            helper.setSubject("Smart Expense System: verify your email");
            helper.setFrom(new InternetAddress("xyz@taskmate.com"));
            helper.setTo(email);
            helper.setText(
                    "Your one-time verification code is: <b>" + otp + "</b><br><br>" +
                            "Or click the link: http://localhost:3000/user/security/confirm-account?token=" +
                            token.getConfirmationToken(),
                    true);
            mailSender.send(mime);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Failed to send email.");
        }

        return ResponseEntity.ok().body(Map.of("message","Verification email sent."));
    }

    // ============ 2. VERIFY TOKEN ============
    public ResponseEntity<?> verifyEmail(String token) {
        Tokens verificationToken = tokensService.findByConfirmationToken(token);

        if (verificationToken == null) {
            return ResponseEntity.badRequest().body("Invalid verification link or token.");
        }

        // Check expiry
        if (verificationToken.getCreatedDate()
                .isBefore(LocalDateTime.now().minusMinutes(3))) {

            // if (!verificationToken.isVerified()) {
                tokensService.deleteToken(verificationToken);
                // userRepo.delete(user);
                System.out.println("Token Expired.");
                // return ResponseEntity.badRequest().body("Token expired. Please register again.");
            // }

            return ResponseEntity.badRequest().body("Token has expired.");
        }

    
        verificationToken.setVerified(true);
        tokensService.saveToken(verificationToken);
        // tokensService.deleteToken(verificationToken);
        System.out.println("Email Verified Successfully..");

        return ResponseEntity.ok("Email verified successfully. You can now log in.");
    }

    // ============ 3. SCHEDULED CLEANUP ============
    @Scheduled(fixedRate = 15 * 60 * 1000) // every 15 minutes
    public void cleanUpExpiredTokensAndUsers() {
        List<Tokens> expiredTokens = tokensService.findAll().stream()
                .filter(t -> t.getCreatedDate().isBefore(LocalDateTime.now().minusMinutes(15)))
                .toList();

        for (Tokens token : expiredTokens) {
            // User user = token.getUserId();
            if (!token.isVerified()) {
                tokensService.deleteToken(token);
                // userRepo.delete(user);
                System.out.println("Scheduled cleanup: deleted unverified user " + token.getUserEmail());
            } else {
                tokensService.deleteToken(token);
            }
        }
    }
}
