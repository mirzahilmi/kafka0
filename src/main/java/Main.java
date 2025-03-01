import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

public class Main {
  public static void main(String[] args) {
    int port = 9092;
    try (
        ServerSocket serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        InputStream in = clientSocket.getInputStream();
        DataOutputStream out = new DataOutputStream(clientSocket.getOutputStream());) {

      serverSocket.setReuseAddress(true);

      byte[] header = in.readNBytes(12);
      byte[] messageSize = Arrays.copyOfRange(header, 8, header.length);

      out.writeInt(0);
      out.write(messageSize);

    } catch (IOException e) {
      System.out.println("IOException: " + e.getMessage());
    }
  }
}
