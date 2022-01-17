package it.pie.qrcode;

public class Main {

    public static void main(String[] args) {
        QrCodeController controller = new QrCodeController();
        controller.createQRCode("Dieghito babeeeee");
        controller.createQRCode("Piermichelangelooooo");
        controller.createQRCode("Mettimi le aliiii");
        for(int i = 0; i < 3; i++)
            System.out.println("QRCode content: "+controller.readQRCode(QrCodeController.dst_path+i+".PNG"));
    }

}
