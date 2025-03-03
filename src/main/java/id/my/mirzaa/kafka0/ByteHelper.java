package id.my.mirzaa.kafka0;

final class ByteHelper {
    static void print(byte[] bytes, String prefix) {
        StringBuilder builder = new StringBuilder();
        builder.append(prefix);
        builder.append(" bytes: ");
        for (byte b : bytes) {
            builder.append(String.format("0x%x ", b));
        }
        System.out.println(builder);
    }
}
