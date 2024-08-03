package task1631;

import task1631.common.*;

public class ImageReaderFactory {
    public static ImageReader getImageReader(ImageTypes imageType) {
        switch (imageType) {
            case BMP:
                return new BmpReader();
            case JPG:
                return new JpgReader();
            case PNG:
                return new PngReader();
            default:
                throw new IllegalArgumentException();
        }
    }
}