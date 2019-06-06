package textToAudio;

import java.io.IOException;
import java.io.InputStream;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.polly.AmazonPollyClient;
import com.amazonaws.services.polly.model.DescribeVoicesRequest;
import com.amazonaws.services.polly.model.DescribeVoicesResult;
import com.amazonaws.services.polly.model.OutputFormat;
import com.amazonaws.services.polly.model.SynthesizeSpeechRequest;
import com.amazonaws.services.polly.model.SynthesizeSpeechResult;
import com.amazonaws.services.polly.model.Voice;
import java.io.ByteArrayOutputStream;

import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;
import org.apache.commons.codec.binary.Base64;

public class PollyDemo {

    private final AmazonPollyClient polly;
    private final Voice voice;
//    private static final String SAMPLE = "Congratulations. You have successfully built this working demo of Amazon Polly in Java. Have fun";

    public PollyDemo(Region region) {
        // create an Amazon Polly client in a specific region
        polly = new AmazonPollyClient(new DefaultAWSCredentialsProviderChain(),
                new ClientConfiguration());
        polly.setRegion(region);
        // Create describe voices request.
        DescribeVoicesRequest describeVoicesRequest = new DescribeVoicesRequest().withLanguageCode("en-US");

        // Synchronously ask Amazon Polly to describe available TTS voices.
        DescribeVoicesResult describeVoicesResult = polly.describeVoices(describeVoicesRequest);
        voice = describeVoicesResult.getVoices().get(0);
    }

    public InputStream synthesize(String text, OutputFormat format) throws IOException {
        SynthesizeSpeechRequest synthReq =
                new SynthesizeSpeechRequest().withText(text).withVoiceId(voice.getId())
                        .withOutputFormat(format);
        SynthesizeSpeechResult synthRes = polly.synthesizeSpeech(synthReq);

        return synthRes.getAudioStream();
    }

    public static String runPolly (final String voice)throws Exception {
        //create the test class
        PollyDemo helloWorld = new PollyDemo(Region.getRegion(Regions.US_EAST_1));
        //get the audio stream
        InputStream speechStream = helloWorld.synthesize(voice, OutputFormat.Mp3);
        
        //create an MP3 player
//        AdvancedPlayer player = new AdvancedPlayer(speechStream, javazoom.jl.player.FactoryRegistry.systemRegistry().createAudioDevice());

//        player.setPlayBackListener(new PlaybackListener() {
//            @Override
//            public void playbackStarted(PlaybackEvent evt) {
//                System.out.println("Playback started");
//                System.out.println(voice);
//            }
//
//            @Override
//            public void playbackFinished(PlaybackEvent evt) {
//                System.out.println("Playback finished");
//            }
//        });
//
//
//        // play it!
//        player.play();

        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[4096];
        int count = -1;
        while((count = speechStream.read(data, 0, 4096)) != -1){
            outStream.write(data, 0, count);
        }
        data = null;
        String voiceStream = new String(Base64.encodeBase64(outStream.toByteArray()));
        return voiceStream;
    }
}