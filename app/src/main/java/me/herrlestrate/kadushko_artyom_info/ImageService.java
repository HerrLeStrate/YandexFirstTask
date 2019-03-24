package me.herrlestrate.kadushko_artyom_info;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.yandex.metrica.YandexMetrica;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

public class ImageService extends JobService {

    protected String mPath;

    @Override
    public void onCreate(){
        Log.d("Jobber","Created!");
        super.onCreate();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        System.out.println("onStartJob");
        YandexMetrica.reportEvent("Start job!");
        mPath = params.getExtras().getString("path");

        if(mPath == null || mPath.isEmpty()){
            Log.e("Error at service!","Some problems with path to image at service");
            //return true;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                    sendBroadcast(new Intent(BackgroundReceiver.UPDATE_BACKGROUND));

                    File file = new File(mPath);
                    URL url = new URL(getURL());
                    FileOutputStream oFile = new FileOutputStream(file);
                    Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, oFile);

                    System.out.println("Sended intent");
                    sendBroadcast(new Intent(BackgroundReceiver.UPDATE_BACKGROUND));

//                    File file = File.createTempFile(mPath);
                }
                catch (IOException e){
                    e.printStackTrace();
                    return;
                }
            }
        }).start();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }

    public static String getURL(){
        YandexMetrica.reportEvent("Request for new url");
        String sites[] = {
                "https://pp.userapi.com/c846216/v846216804/18163f/U6iqXzLTAQo.jpg",
                "https://loremflickr.com/720/1080",
                "https://picsum.photos/720/1080/?random"
        };
        Random rnd = new Random();
        return sites[rnd.nextInt(sites.length)];
    }
}
