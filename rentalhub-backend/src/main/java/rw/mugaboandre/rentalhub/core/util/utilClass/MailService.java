package rw.mugaboandre.rentalhub.core.util.utilClass;



import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    public void sendVerificationEmail(String toEmail, String verificationLink){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Verify Your Account - ShopEase");
        message.setText("Click the following link to verify your email:\n\n" + verificationLink);
        mailSender.send(message);
    }


    public void sendOtpEmail(String toEmail, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Login OTP - ShopEase");
        message.setText("Use this OTP to complete your login: " + otpCode + "\nIt expires in 90 seconds.");
        mailSender.send(message);
    }


}
