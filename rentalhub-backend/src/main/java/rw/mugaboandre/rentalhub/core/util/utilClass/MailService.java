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
        message.setSubject("Verify Your Account - RentalHUb");
        message.setText("Click the following link to verify your email:\n\n" + verificationLink);
        mailSender.send(message);
    }


    public void sendOtpEmail(String toEmail, String otpCode) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Your Login OTP - RentalHub");
        message.setText("Use this OTP to complete your login: " + otpCode + "\nIt expires in 90 seconds.");
        mailSender.send(message);
    }

    public void sendUnsubscribeEmail(String toEmail, String firstName) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(toEmail);
        message.setSubject("Unsubscription Confirmation - RentalHub");
        message.setText("Hello " + firstName + ",\n\n" +
                "We’re sorry to see you go. Your account has been unsubscribed successfully.\n\n" +
                "If this was a mistake or you’d like to restore your account, please contact our support team.\n\n" +
                "Best regards,\nRentalHub Team");
        mailSender.send(message);
    }



}
