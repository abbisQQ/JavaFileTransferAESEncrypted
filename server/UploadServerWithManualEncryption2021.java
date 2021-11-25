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
            SecretKey key = AESManual.getKeyFromPassword("P@ssw0rd","salt");
            byte[] data;
            byte[] encryptedData;

            System.out.println("Server started");
            while(true) {
                // Waiting for socket connection
                Socket s = ss.accept();

                DataInputStream inp = new DataInputStream(s.getInputStream());
                data = IOUtils.readAllBytes(inp);

                encryptedData = AESManual.decrypt("AES/CBC/PKCS5Padding",data,key,iv);

                Date date = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");

                FileOutputStream fos = new FileOutputStream("path" + dateFormat.format(date) + "any extention the file may have e.x .pdf");
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
