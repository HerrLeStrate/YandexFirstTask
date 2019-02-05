package me.herrlestrate.herrlestrateapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Gravity;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.microsoft.appcenter.AppCenter;
import com.microsoft.appcenter.analytics.Analytics;
import com.microsoft.appcenter.crashes.Crashes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        AppCenter.start(getApplication(), "7adff281-c7b5-4c1a-918e-f370ded917af", Analytics.class, Crashes.class);
        //setContentView(R.layout.activity_main);

        ImageView imageView = new ImageView(this);
        imageView.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        int RoundSize = Math.min(height/2,width/2);
        imageView.setMaxHeight(RoundSize);
        imageView.setMaxWidth(RoundSize);
        imageView.setImageResource(R.drawable.ava_circle);

        TextView textViewName = new TextView(this);
        textViewName.setLayoutParams(
                new RelativeLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                )
        );
        textViewName.setText("\nArtyom Kadushko\nherrlestrate");
        textViewName.setTextSize(TypedValue.COMPLEX_UNIT_DIP,50);


        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
        ll.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ll.addView(imageView);
        ll.addView(textViewName);
        setContentView(ll);
    }



}
