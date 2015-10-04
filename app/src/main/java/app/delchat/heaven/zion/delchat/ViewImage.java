package app.delchat.heaven.zion.delchat;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ViewImage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);

        ImageView imageView = (ImageView) findViewById(R.id.viewImageActivity);

        Uri imageUri = getIntent().getData();
        Picasso.with(this).load(imageUri.toString()).into(imageView);
    }

}
