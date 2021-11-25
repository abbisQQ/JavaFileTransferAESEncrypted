import sun.misc.IOUtils;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadServerWithManualEncryption2021 {

    public static void main(String[] args) {


        try
        {
            ServerSocket ss = new ServerSocket(8888);
            // Communication Endpoint for the client and server.

            IvParameterSpec iv = AESManual.generateIv();
            SecretKey key = AESManual.getKeyFromPassword("ssj2P@ssw0rd","kaioken");
            byte[] data;
            byte[] encryptedData;

            int i =0;

            System.out.println("Server started");
            while(i<1000000000) {
                // Waiting for socket connection
                Socket s = ss.accept();

                DataInputStream inp = new DataInputStream(s.getInputStream());
                data = IOUtils.readAllBytes(inp);

                encryptedData = AESManual.decrypt("AES/CBC/PKCS5Padding",data,key,iv);

                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
               //FileOutputStream fos = new FileOutputStream("C:\\temp\\" + dateFormat.format(date) + i + ".jpg"); //change path of image according to you
                //for linux
                FileOutputStream fos = new FileOutputStream("/media/pi/I/images/" + dateFormat.format(date) + i + ".jpg");
                fos.write(encryptedData);


                i++;
                fos.close();
                inp.close();
                s.close();
            }

    }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
