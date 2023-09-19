package dip.server.service;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendService  {

    JavaMailSender emailSender;
    public EmailSendService(JavaMailSender emailSender){
        this.emailSender=emailSender;
    }
    public void sendSimpleEmail(String toAddress, String nameCar, double fullPrice, double delivery, double auctionFee) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("gelyafeb@outlook.com");
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject("Покупка авто");
        simpleMailMessage.setText("Здрайствуйте, вы подтвердили покупку автомобиля"+nameCar +
                "\nВыигрышная ставка составила"+String.valueOf(fullPrice-delivery-auctionFee)+"\n" +
                "Аукционный сбор: "+String.valueOf(auctionFee)+", доставка: "+String.valueOf(delivery)+"\n" +
                "Полная стоимость автомобиля "+String.valueOf(fullPrice));
        this.emailSender.send(simpleMailMessage);
    }



    }


