import id.my.mirzaa.kafka0.Server;

final class Main {
  public static void main(String[] args) {
    try (Server server = new Server(Server.DEFAULT_PORT)) {
      server.listen();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}
