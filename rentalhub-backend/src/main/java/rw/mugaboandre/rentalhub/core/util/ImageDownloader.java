package rw.mugaboandre.rentalhub.core.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class ImageDownloader {
    public static byte[] downloadImage(String imageUrl) throws Exception {
        URL url = new URL(imageUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        try (InputStream in = conn.getInputStream();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            return out.toByteArray(); // raw bytes
        }
    }

    public static void main(String[] args) throws Exception {
        String imageUrl = "https://plus.unsplash.com/premium_photo-1689568126014-06fea9d5d341?w=900&auto=format&fit=crop&q=60&ixlib=rb-4.1.0&ixid=M3wxMjA3fDB8MHxzZWFyY2h8MXx8cHJvZmlsZXxlbnwwfHwwfHx8MA%3D%3D";

        // get bytes
        byte[] imageBytes = downloadImage(imageUrl);
        System.out.println("Byte array size: " + imageBytes.length);

        // convert to Base64
        String base64Image = Base64.getEncoder().encodeToString(imageBytes);
        System.out.println("Base64: " + base64Image.substring(0, 100) + "..."); // print only first 100 chars
    }
}
