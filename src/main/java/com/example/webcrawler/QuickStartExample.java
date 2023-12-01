package com.example.webcrawler;

import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;
import java.net.URL;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class QuickStartExample {
  public static void main(String... args) throws Exception {
    // Initialize the ImageAnnotatorClient
    try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

      // URL of the image to annotate
      String imageUrl = "https://crl2020.imgix.net/img/kami-logo-not-round.png?auto=format,compress";

      // Fetch the image from the URL
      ByteString imgBytes = fetchImageFromURL(imageUrl);

      // Builds the image annotation request
      List<AnnotateImageRequest> requests = new ArrayList<>();
      Image img = Image.newBuilder().setContent(imgBytes).build();
      Feature feat = Feature.newBuilder().setType(Type.LABEL_DETECTION).build();
      AnnotateImageRequest request = 
          AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
      requests.add(request);

      // Perform label detection on the image file
      BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
      List<AnnotateImageResponse> responses = response.getResponsesList();

      for (AnnotateImageResponse res : responses) {
        if (res.hasError()) {
          System.out.format("Error: %s%n", res.getError().getMessage());
          return;
        }

        for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
          System.out.println(annotation.getDescription());
         
        }
      }
    }
  }

  private static ByteString fetchImageFromURL(String imageUrl) throws Exception {
    try (InputStream in = new URL(imageUrl).openStream(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      byte[] buffer = new byte[1024];
      int bytesRead;
      while ((bytesRead = in.read(buffer)) != -1) {
        out.write(buffer, 0, bytesRead);
      }
      return ByteString.copyFrom(out.toByteArray());
    }
  }
}
