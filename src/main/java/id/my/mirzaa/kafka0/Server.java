package id.my.mirzaa.kafka0;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class Server implements AutoCloseable, Runnable {
    public static final String DEFAULT_HOST = "localhost";
    public static final int DEFAULT_PORT = 9092;

    public static final int MESSAGE_SIZE = 4;
    public static final int REQUEST_API_KEY_SIZE = 2;
    public static final int REQUEST_API_VERSION_SIZE = 2;
    public static final int CORRELATION_ID_SIZE = 4;
    public static final int ERROR_CODE_SIZE = 2;

    private final ServerSocket server;

    public Server(int port) {
        try {
            this.server = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listen() {
        System.out.println("server listening...");
        try (
                Socket socket = server.accept();
                BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
                DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {

            int requestSize = MESSAGE_SIZE + REQUEST_API_KEY_SIZE + REQUEST_API_VERSION_SIZE + CORRELATION_ID_SIZE;
            byte[] raw = new byte[requestSize];
            in.read(raw, 0, requestSize);

            byte[] messageSize = Arrays.copyOfRange(raw, 0, MESSAGE_SIZE);
            byte[] requestApiKey = Arrays.copyOfRange(raw, MESSAGE_SIZE,
                    MESSAGE_SIZE +
                            REQUEST_API_KEY_SIZE);
            byte[] requestApiVersion = Arrays.copyOfRange(raw, MESSAGE_SIZE +
                    REQUEST_API_KEY_SIZE,
                    MESSAGE_SIZE + REQUEST_API_KEY_SIZE
                            + REQUEST_API_VERSION_SIZE);
            byte[] correlationId = Arrays.copyOfRange(raw, MESSAGE_SIZE +
                    REQUEST_API_KEY_SIZE
                    + REQUEST_API_KEY_SIZE,
                    MESSAGE_SIZE + REQUEST_API_KEY_SIZE
                            + REQUEST_API_VERSION_SIZE
                            + CORRELATION_ID_SIZE);

            int apiVersion = ByteBuffer.wrap(requestApiVersion).getShort();
            int errorCode = apiVersion >= 0 && apiVersion <= 4 ? 0 : 35;

            out.write(messageSize);
            out.write(correlationId);
            out.writeShort(errorCode);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void run() {
        listen();
    }

    @Override
    public void close() throws Exception {
        server.close();
    }
}
