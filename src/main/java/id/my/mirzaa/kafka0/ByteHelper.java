package id.my.mirzaa.kafka0;

final class ByteHelper {
    static String toHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("0x%x ", b));
        }
        return builder.toString();
    }
}
