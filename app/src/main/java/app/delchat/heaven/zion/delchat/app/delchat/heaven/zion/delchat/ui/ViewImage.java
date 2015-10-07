package app.delchat.heaven.zion.delchat.app.delchat.heaven.zion.delchat.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import app.delchat.heaven.zion.delchat.R;

public class ViewImage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        ImageView imageView = (ImageView) findViewById(R.id.viewImageActivity);

        Uri imageUri = getIntent().getData();
        Picasso.with(this).load(imageUri.toString()).into(imageView);
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                finish();
            }
        },15*1000); // 15 Seconds
    }

}
