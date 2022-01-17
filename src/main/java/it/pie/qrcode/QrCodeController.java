package it.pie.qrcode;


import com.google.zxing.*;
import com.google.zxing.Reader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class QrCodeController {

    public static final String dst_path = "C:\\Users\\Piermuz\\Desktop\\QRCodes\\";
    private int count = 0;

    public void createQRCode(String s) {
        ByteArrayOutputStream output = QRCode.from(s).to(ImageType.PNG).stream();
        try {
            FileOutputStream fout = new FileOutputStream(new File(dst_path + (this.count + ".PNG")));
            fout.write(output.toByteArray());
            fout.flush();
            this.count++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readQRCode(String filePath) {
        Result result = null;
        try {
            InputStream qrcodeInputStream = new FileInputStream(filePath);
            BufferedImage qrcodeBufferedImage = ImageIO.read(qrcodeInputStream);
            LuminanceSource source = new BufferedImageLuminanceSource(qrcodeBufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Reader reader = new MultiFormatReader();
            result = reader.decode(bitmap);
        } catch (FormatException | NotFoundException | ChecksumException | IOException e) {
            e.printStackTrace();
        }
        return result.getText();
    }

}
