package it.pie.qrcode;

import java.io.File;

public class Main {

    public static final File dst_path = new File("C:\\Users\\Piermuz\\Desktop\\QRCodes\\");

    public static void main(String[] args) {
        QrCodeGenerator generator = new QrCodeGenerator();
        generator.createQRCode("This is the first generated QRCode",dst_path,"0","PNG");
        generator.createQRCode("This is the second generated QRCode",dst_path,"1","PNG");
        generator.createQRCode("This is the third generated QRCode",dst_path,"2","PNG");
        for(int i = 0; i < 3; i++)
            System.out.println("QRCode content: "+generator.readQRCode(dst_path.getAbsolutePath()+"\\"+i+".PNG"));
    }

}
