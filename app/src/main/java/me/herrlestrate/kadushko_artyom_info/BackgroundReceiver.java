package me.herrlestrate.kadushko_artyom_info;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

import me.herrlestrate.kadushko_artyom_info.fragments.launcher.SetBackgroundAsyncTask;

public class BackgroundReceiver extends BroadcastReceiver {

    public static final String UPDATE_BACKGROUND = "herrlestrate.IMAGE_SERVICE.UPDATE_BACKGROUND";
    public static final String UPDATE_BACKGROUND_ONCE = "herrlestrate.IMAGE_SERVICE.UPDATE_BACKGROUND_ONCE";

    protected final View mView;
    protected final String mPath;


    public BackgroundReceiver(View view, String path) {
        mView = view;
        mPath = path;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.i("Intent",intent.getAction());
        if(intent.getAction().equals(UPDATE_BACKGROUND) && mView.getHeight()!=0 && mView.getWidth()!=0){
            new SetBackgroundAsyncTask(mView,mPath).execute();

        }else if (intent.getAction().equals(UPDATE_BACKGROUND_ONCE)){


            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
//                    sendBroadcast(new Intent(BackgroundReceiver.UPDATE_BACKGROUND));

                        File file = new File(mPath);
                        URL url = new URL(ImageService.getURL());
                        FileOutputStream oFile = new FileOutputStream(file);
                        Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                        bmp.compress(Bitmap.CompressFormat.PNG, 100, oFile);


                        mView.getContext().sendBroadcast(new Intent(BackgroundReceiver.UPDATE_BACKGROUND));
//                        Log.d(TAG,"update background broadcast sent");
//                    File file = File.createTempFile(mPath);
                    }
                    catch (IOException e){
                        return;
                    }
                }
            }).start();
        }
    }


}
