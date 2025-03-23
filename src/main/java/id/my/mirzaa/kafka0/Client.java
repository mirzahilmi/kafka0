package id.my.mirzaa.kafka0;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

final class Client {
    private static final Logger logger = LogManager.getLogger(Client.class);

    private final Socket socket;

    Client() {
        try {
            this.socket = new Socket(Server.DEFAULT_HOST, Server.DEFAULT_PORT);
            logger.info("client connected: host=[{}] port=[{}]", Server.DEFAULT_HOST, Server.DEFAULT_PORT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void send() {
        try (
            BufferedInputStream in = new BufferedInputStream(socket.getInputStream());
            DataOutputStream out =
                new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()))) {
            out.writeInt(35);
            out.writeShort(18);
            out.writeShort(-22277);
            out.writeInt(522627758);
            out.flush();

            int responseSize =
                Server.MESSAGE_SIZE + Server.CORRELATION_ID_SIZE + Server.ERROR_CODE_SIZE;
            byte[] raw = new byte[responseSize];
            in.read(raw, 0, responseSize);

            logger.debug("client received: bytes=[{}]", ByteHelper.toHexString(raw));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
