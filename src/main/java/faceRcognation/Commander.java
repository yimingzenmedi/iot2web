package faceRcognation;

import java.io.File;
import textToAudio.PollyDemo;

import java.io.IOException;
import java.util.Scanner;

public class Commander {

    public static void main(String [] args) throws IOException {
        while (true) {
            Scanner sc = new Scanner(System.in);
            System.out.println("please input command：");
            String com = sc.nextLine();
            switch (com) {
                case "detect-labels":
                    DetectLabels detectLabels = new DetectLabels();
                    detectLabels.detecLable("work.jpg");
                    break;
                case "detect-faces":
                    DetectFaces detectFaces = new DetectFaces();
                    detectFaces.detectFaces("work.jpg");
                    break;
                case "create-collection":
                    CreateCollection cc = new CreateCollection();
                    cc.createCollection("testColl");
                    break;
                case "list-collections":
                    ListCollections lc = new ListCollections();
                    lc.ListCollections();
                    break;
                case "delete-collection":
                    DeleteCollection dc = new DeleteCollection();
                    dc.deleteCollection("testColl");
                    break;
                case "describe-collection":
                    DescribeCollection descc = new DescribeCollection();
                    descc.describeCollection("testColl");
                    break;
                case "index-faces":
                    
//                    File directory = new File("");//设定为当前文件夹 
//                    try{ 
//                        System.out.println(directory.getCanonicalPath());//获取标准的路径 
//                        System.out.println(directory.getAbsolutePath());//获取绝对路径 
//                    }catch(Exception e){} 

                    IndexFaces indf = new IndexFaces();
                    indf.indexFaces("testColl", "2.jpg");
                    break;
                case "search-faces-by-image":
                    SearchFacesByImage sfbi = new SearchFacesByImage();
                    sfbi.searchFacesByImage("testColl", "me.jpg");
                    break;
                case "search-celebrity":
                    RecognizeCelebrities rc = new RecognizeCelebrities();
                    try {
                        rc.recognizeCelebrities("aobama.jpg");
                    } catch (Exception e) {
                    }
                    break;
                case "text-to-audio":
                    String voice = "hello hello hello hello hello";
                    try {
                        textToAudio.PollyDemo.runPolly(voice);
                    } catch (Exception e) {
                    }
                    break;
                default:
                    System.err.println("Unknown argument: " + sc);
                    return;
            }
        }
    }
}

