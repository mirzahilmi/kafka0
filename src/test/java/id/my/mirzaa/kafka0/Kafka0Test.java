package id.my.mirzaa.kafka0;

import org.junit.jupiter.api.Test;

final class Kafka0Test {
    @Test
    void Create_server_listener_should_block_until_request_received() {
        try (Server server = new Server(Server.DEFAULT_PORT)) {
            Thread serverThread = new Thread(server);
            serverThread.start();

            Client client = new Client();
            Thread clientThread = new Thread(() -> client.send());
            clientThread.start();

            serverThread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
