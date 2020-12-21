package cultureapp.common;

import java.util.List;

public class ImageTestData {
    public static List<byte[]> IMAGE_BYTES = List.of(new byte[]{}, new byte[]{});
    public static List<String> IMAGE_PATHS = List.of("Path1", "Path2");
    public static List<Long> IMAGE_IDS = List.of(1L, 2L);

    public static String IMAGE_PATH = "Path";

    public static String VALID_MIME_TYPE = "image/jpeg";
    public static String INVALID_MIME_TYPE = "invalid";

    public static List<String> VALID_MIME_TYPES = List.of(VALID_MIME_TYPE, VALID_MIME_TYPE);

    public static List<String> INVALID_MIME_TYPES = List.of(INVALID_MIME_TYPE, INVALID_MIME_TYPE);


}
