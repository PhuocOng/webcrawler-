package com.example.webcrawler;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;


import com.google.cloud.vision.v1.AnnotateImageRequest;
import com.google.cloud.vision.v1.AnnotateImageResponse;
import com.google.cloud.vision.v1.BatchAnnotateImagesResponse;
import com.google.cloud.vision.v1.EntityAnnotation;
import com.google.cloud.vision.v1.Feature;
import com.google.cloud.vision.v1.Feature.Type;
import com.google.cloud.vision.v1.Image;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.google.protobuf.ByteString;


public class LabelsDetectCallable implements Callable<List<String>> {

    private String url;

    public void setUrl(String url) {
        this.url = url;
    }

   
    @Override
    public List<String> call() throws Exception {
        ArrayList<String> labelsList = new ArrayList<String>();
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {

            // URL of the image to annotate
            String imageUrl = this.url;

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
                    return labelsList;
                }

                for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                    System.out.println(annotation.getDescription());
                    labelsList.add(annotation.getDescription());
                }
            }
        }
        return labelsList;
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
