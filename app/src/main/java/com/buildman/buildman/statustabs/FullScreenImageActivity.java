package com.buildman.buildman.statustabs;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.Window;

import android.widget.ImageView;


import com.buildman.buildman.activity.R;
import com.buildman.buildman.helper.TouchImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FullScreenImageActivity extends Activity {
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        setContentView(R.layout.activity_full_screen_image);
        TouchImageView img = new TouchImageView(this);
        img.setMaxZoom(4f);
        setContentView(img);
        BitmapFactory.Options bfOptions=new BitmapFactory.Options();
        bfOptions.inDither=false;                     //Disable Dithering mode
        bfOptions.inPurgeable=true;                   //Tell to gc that whether it needs free memory, the Bitmap can be cleared
        bfOptions.inInputShareable=true;              //Which kind of reference will be used to recover the Bitmap data after being clear, when it will be used in the future
        bfOptions.inTempStorage=new byte[32 * 1024];
        FileInputStream fs = null;
        Bitmap bm;
        try {
            fs = new FileInputStream(new File(getIntent().getStringExtra("img")));

            if(fs!=null)
            {
                bm=BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
//                ImageView imageView = (ImageView)findViewById(R.id.full_image_view);
//                imageView.setLayoutParams( new RelativeLayout.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT));
                img.setImageBitmap(bm);

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        finally{
            if(fs!=null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
