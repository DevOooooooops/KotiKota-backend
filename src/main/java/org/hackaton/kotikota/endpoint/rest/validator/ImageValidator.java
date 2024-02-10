package org.hackaton.kotikota.endpoint.rest.validator;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Base64;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import org.springframework.stereotype.Component;

@Component
public class ImageValidator {
  public StringBuilder getImageType(String base64EncodedImage, StringBuilder exceptionMessage) {
    try {
      byte[] decodedBytes = Base64.getDecoder().decode(base64EncodedImage);
      ByteArrayInputStream bis = new ByteArrayInputStream(decodedBytes);
      ImageInputStream iis = ImageIO.createImageInputStream(bis);
      if (iis == null) {
        exceptionMessage.append("Invalid image type");
        return exceptionMessage;
      }
      String format = ImageIO.getImageReaders(iis).next().getFormatName();
      if(format.equalsIgnoreCase("jpeg") || format.equalsIgnoreCase("jpg") || format.equalsIgnoreCase("png")) {
        return exceptionMessage;
      }
      exceptionMessage.append("Invalid image type");
      return exceptionMessage;
    } catch (IOException e) {
      exceptionMessage.append("Invalid image type");
      return exceptionMessage;
    }
  }
}
