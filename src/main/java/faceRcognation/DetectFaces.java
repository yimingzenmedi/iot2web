package faceRcognation;

import com.amazonaws.services.rekognition.AmazonRekognition;
import com.amazonaws.services.rekognition.model.*;
import faceRcognation.ClientFactory;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class DetectFaces {
    private void printFaceDetails(FaceDetail faceDetail) {
        System.out.println("###############");

        AgeRange ageRange = faceDetail.getAgeRange();
        System.out.println("Age range: " + ageRange.getLow() + "-" + ageRange.getHigh());

        Beard beard = faceDetail.getBeard();
        System.out.println("Beard: " + beard.getValue() + "; confidence=" + beard.getConfidence());

        BoundingBox bb = faceDetail.getBoundingBox();
        System.out.println("BoundingBox: left=" + bb.getLeft() +
                ", top=" + bb.getTop() + ", width=" + bb.getWidth() +
                ", height=" + bb.getHeight());

        Float confidence = faceDetail.getConfidence();
        System.out.println("Confidence: " + confidence);

        List<Emotion> emotions = faceDetail.getEmotions();
        for (Emotion emotion : emotions) {
            System.out.println("Emotion: " + emotion.getType() +
                    "; confidence=" + emotion.getConfidence());
        }

        Eyeglasses eyeglasses = faceDetail.getEyeglasses();
        System.out.println("Eyeglasses: " + eyeglasses.getValue() +
                "; confidence=" + eyeglasses.getConfidence());

        EyeOpen eyesOpen = faceDetail.getEyesOpen();
        System.out.println("EyeOpen: " + eyesOpen.getValue() +
                "; confidence=" + eyesOpen.getConfidence());

        Gender gender = faceDetail.getGender();
        System.out.println("Gender: " + gender.getValue() +
                "; confidence=" + gender.getConfidence());

        List<Landmark> landmarks = faceDetail.getLandmarks();
        for (Landmark lm : landmarks) {
            System.out.println("Landmark: " + lm.getType()
                    + ", x=" + lm.getX() + "; y=" + lm.getY());
        }

        MouthOpen mouthOpen = faceDetail.getMouthOpen();
        System.out.println("MouthOpen: " + mouthOpen.getValue() +
                "; confidence=" + mouthOpen.getConfidence());

        Mustache mustache = faceDetail.getMustache();
        System.out.println("Mustache: " + mustache.getValue() +
                "; confidence=" + mustache.getConfidence());

        Pose pose = faceDetail.getPose();
        System.out.println("Pose: pitch=" + pose.getPitch() +
                "; roll=" + pose.getRoll() + "; yaw" + pose.getYaw());

        ImageQuality quality = faceDetail.getQuality();
        System.out.println("Quality: brightness=" +
                quality.getBrightness() + "; sharpness=" + quality.getSharpness());

        Smile smile = faceDetail.getSmile();
        System.out.println("Smile: " + smile.getValue() +
                "; confidence=" + smile.getConfidence());

        Sunglasses sunglasses = faceDetail.getSunglasses();
        System.out.println("Sunglasses=" + sunglasses.getValue() +
                "; confidence=" + sunglasses.getConfidence());

        System.out.println("###############");
    }
    public void detectFaces(String path){

        String imgPath = path;
        byte[] bytes;
        try {
            bytes = Files.readAllBytes(Paths.get(imgPath));
        } catch (IOException e) {
            System.err.println("Failed to load image: " + e.getMessage());
            return;
        }
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        AmazonRekognition rekognition = ClientFactory.createClient();

        DetectFacesRequest request = new DetectFacesRequest()
                .withImage(new Image().withBytes(byteBuffer))
                .withAttributes(Attribute.ALL);
        DetectFacesResult result = rekognition.detectFaces(request);

        String orientationCorrection = result.getOrientationCorrection();
        System.out.println("Orientation correction: " + orientationCorrection);

        List<FaceDetail> faceDetails = result.getFaceDetails();
        System.out.println("facedetails: " + faceDetails);
        for (FaceDetail faceDetail : faceDetails) {
            printFaceDetails(faceDetail);
        }
//        return 
    }
}
