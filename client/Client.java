
public class Client {

    public static void main(String[] args) {

       ArrayList<String> data = getData();
       SendManuallyEncryptedDataRunnable runnable = new SendManuallyEncryptedDataRunnable(images);
       Thread thread = new Thread(runnable);
       thread.start();
      
    }
  
  public ArrayList<String> getData(){
  //this must be implement depending on the data you would like to send
}
