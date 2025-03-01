import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
  public static void main(String[] args) {
    int port = 9092;
    try (
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());) {
      serverSocket.setReuseAddress(true);
      out.writeInt(0);
      out.writeInt(7);
    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
