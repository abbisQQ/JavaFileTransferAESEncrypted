public class SendManuallyEncryptedDataRunnable implements Runnable{

    private final ArrayList<String> data;

    public SendManuallyEncryptedDataRunnable(ArrayList<String> data) {
        this.data=data;

    }

    @Override
    public void run() {

        try {
        Socket sock = null;
        byte[] dataInBytes;
        IvParameterSpec iv =  generateIv();
        SecretKey key = getKeyFromPassword("P@ssw0rd","salt");
        byte[] encryptedData;
        File file;
        for(int i=0; i<data.size();i++) {



                file = new File(data.get(i));
                FileInputStream fileInputStreamReader = new FileInputStream(file);

                dataInBytes = new byte[(int)file.length()];
                fileInputStreamReader.read(dataInBytes);




                encryptedData = encrypt("AES/CBC/PKCS5Padding",dataInBytes,key,iv);

                sock = new Socket("192.168.1.7", 8888);

                OutputStream os = sock.getOutputStream();

                os.write(encryptedData, 0, encryptedData.length);

                os.flush();

                sock.close();



            }
        } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            e.printStackTrace();
            Log.println(Log.ERROR, "service", "Error");

        }
    }

    public static IvParameterSpec generateIv() {
        byte[] iv = {-87, 15, -59, 61, -93, -37, -20, -75, 111, 109, 101, 41, 66, 12, 26, 50};
        //new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    public static SecretKey getKeyFromPassword(String password, String salt)
            throws NoSuchAlgorithmException, InvalidKeySpecException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
        return new SecretKeySpec(factory.generateSecret(spec)
                .getEncoded(), "AES");
    }


    public static byte[] encrypt(String algorithm, byte[] input, SecretKey key,IvParameterSpec iv
    ) throws NoSuchPaddingException, NoSuchAlgorithmException,
            InvalidAlgorithmParameterException, InvalidKeyException,
            BadPaddingException, IllegalBlockSizeException {

        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key, iv);
        return cipher.doFinal(input);

    }

}
