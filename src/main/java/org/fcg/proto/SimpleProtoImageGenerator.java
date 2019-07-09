package org.fcg.proto;

import org.springframework.stereotype.Component;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.Objects;

/**
 * Simple logic to combine RGB channel into one.
 * Ideas borrowed from internet. Credits acknowledged.
 */
@Component
public class SimpleProtoImageGenerator implements ProtoImageGenerator {
    @Override
    public BufferedImage generate(BufferedImage redImage, BufferedImage greenImage, BufferedImage blueImage) {
        Arrays.asList(redImage, greenImage, blueImage).forEach(imageFile -> validateNotNull(imageFile));

        int width = blueImage.getWidth();
        int height = blueImage.getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int p = redImage.getRGB(x, y);
                int a = (p >> 24) & 0xff;
                int r = (p >> 16) & 0xff;
                int g = (greenImage.getRGB(x, y) >> 8) & 0xff;
                int b = (blueImage.getRGB(x, y) >> 0) & 0xff;

                int rgb = ((a & 0xFF) << 24) | ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
                img.setRGB(x, y, rgb);
            }
        }
        return img;
    }

    @Override
    public BufferedImage generate(BufferedImage blueImage) {
        validateNotNull(blueImage);

        int width = blueImage.getWidth();
        int height = blueImage.getHeight();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int b = blueImage.getRGB(x, y);
                int a = (b >> 24) & 0xff;
                int rgb = ((a & 0xFF) << 24) | (0 << 16) | (0 << 8) | (b & 0xFF);
                img.setRGB(x, y, rgb);
            }
        }
        return img;
    }

    private void validateNotNull(BufferedImage imageFile) {
        if (Objects.isNull(imageFile)) {
            throw new ProtoImageNotFoundException();
        }
    }

}
