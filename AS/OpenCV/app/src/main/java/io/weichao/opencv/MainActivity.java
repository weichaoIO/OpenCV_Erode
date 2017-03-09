package io.weichao.opencv;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {
    static {
        if (OpenCVLoader.initDebug()) {
            System.loadLibrary("opencv");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView srcImage = (ImageView) findViewById(R.id.src_image);
        ImageView destImage = (ImageView) findViewById(R.id.dest_image);

        /*显示原图*/
        Bitmap srcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.test);
        srcImage.setImageBitmap(srcBitmap);

        /*处理图像*/
        int width = srcBitmap.getWidth();
        int height = srcBitmap.getHeight();
        int[] pixelArray = new int[width * height];
        srcBitmap.getPixels(pixelArray, 0, width, 0, 0, width, height);
        pixelArray = nCorrode(pixelArray, width, height);

        /*显示处理后的图像*/
        Bitmap destBitmap = Bitmap.createBitmap(pixelArray, width, height, Bitmap.Config.ARGB_8888);
        destImage.setImageBitmap(destBitmap);
    }

    private native int[] nCorrode(int[] pixelArray, int width, int height);
}
