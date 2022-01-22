package it.pie.qrcode;


import com.google.zxing.*;
import com.google.zxing.Reader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.*;
import java.util.Hashtable;

/**
 * QRCode generator that generates QRCode storing it as a image with the type of file you prefer.
 */
public class QrCodeGenerator {

    /**
     * Creates the QRCode that contains the specified string with the specified name and the specified type of file.
     * Furthermore, it will be stored in the destination folder specified by the file path.
     *
     * @param s          the string will be contained inside the QRCode
     * @param dst_file   the destionation file path where QRCode will be stored
     * @param QrCodename the name will be associated to the QRCode
     * @param type_file  the type will have the QRCode file
     */
    public void createQRCode(String s, File dst_file, String QrCodename, String type_file) {
        try {
            RenderedImage image = this.setQRCodeSize(s, 500, 500);
            if (System.getProperty("os.name").startsWith("Windows"))
                ImageIO.write(image, type_file, new File(dst_file.getAbsolutePath() + "\\" + QrCodename + "." + type_file));
            else
                ImageIO.write(image, type_file, new File(dst_file.getAbsolutePath() + "/" + QrCodename + "." + type_file));
        } catch (IOException | WriterException e) {
            e.printStackTrace();
        }
    }

    private RenderedImage setQRCodeSize(String s, int width, int height) throws WriterException {
        Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(s, BarcodeFormat.QR_CODE, width, height, hintMap);
        int matrixWidth = byteMatrix.getWidth();
        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);
        graphics.setColor(Color.BLACK);
        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }
        return image;
    }

    /**
     * Reads the String cointained inside the file in the specified path.
     *
     * @param filePath the path that contains the file QRCode to read
     * @return the contained string in the QRCode
     */
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
        return result != null ? result.getText() : "";
    }

}
