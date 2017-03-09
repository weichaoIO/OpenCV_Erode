
[toc]

---

#<center>**OpenCV 腐蚀图片**</center>

---

#***TODO***

- [] 【在 VS 上配置 OpenCV 工程】上传到个人网站。

---

#**搭建工程**

##**VS 工程**

VS 工程搭建参考【在 VS 上配置 OpenCV 工程】。

VS 工程目录结构：

![](https://github.com/weichao66666/OpenCV_Corrode/raw/master/README.md-images/01.png)

##**AS 工程**

AS 工程搭建参考：

* [【AS 导入 OpenCV 工程之 face-detection】](https://github.com/weichao66666/Face_Detection "https://github.com/weichao66666/Face_Detection")

* [【OpenCV_Min】](https://github.com/weichao66666/OpenCV_Min "https://github.com/weichao66666/OpenCV_Min")

AS 工程目录结构：

![](https://github.com/weichao66666/OpenCV_Corrode/raw/master/README.md-images/02.png)

---

#**修改工程**

##**VS 工程**

###**修改 main.cpp**

    #include <opencv2\highgui\highgui.hpp>
    #include <opencv2\imgproc\imgproc.hpp>
    
    using namespace cv;
    
    int main() {
    	Mat srcImageMat = imread("test.png");
    	imshow("srcImage", srcImageMat);
    
    	Mat destImageMat;
    	Mat element = getStructuringElement(MORPH_RECT, Size(10, 10));
    	erode(srcImageMat, destImageMat, element);
    	imshow("destImage", destImageMat);
    
    	waitKey(0);
    	return 0;
    }

###**运行结果**

![](https://github.com/weichao66666/OpenCV_Corrode/raw/master/README.md-images/03.png)

##**AS 工程**

所有修改内容：

![](https://github.com/weichao66666/OpenCV_Corrode/raw/master/README.md-images/05.png)

###**修改 activity_main.xml**

    <?xml version="1.0" encoding="utf-8"?>
    <LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context="io.weichao.opencv.MainActivity">
    
        <ImageView
            android:id="@+id/src_image"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />
    
        <ImageView
            android:id="@+id/dest_image"
            android:layout_width="match_parent"
            android:layout_height="wrap_content" />
    </LinearLayout>

###**添加 test.png**

![](https://github.com/weichao66666/OpenCV_Corrode/raw/master/README.md-images/test.png)

###**修改 opencv.cpp**

    #include <jni.h>
    
    #include <opencv2/imgproc/imgproc.hpp>
    
    using namespace cv;
    
    extern "C"
    JNIEXPORT jintArray JNICALL
    Java_io_weichao_opencv_MainActivity_nCorrode(JNIEnv *env, jobject obj /* this */,
                                                 jintArray pixelArray,
                                                 int width, int height) {
        jint *pixelPoint;
        pixelPoint = env->GetIntArrayElements(pixelArray, JNI_FALSE);
        Mat srcImageMat(height, width, CV_8UC4, (unsigned char *) pixelPoint);
    
        Mat destImageMat;
        Mat element = getStructuringElement(MORPH_RECT, Size(10, 10));
        erode(srcImageMat, destImageMat, element);
    
        int size = width * height;
        jintArray retArray = env->NewIntArray(size);
        jint *ptr = (jint *) destImageMat.ptr(0);
        env->SetIntArrayRegion(retArray, 0, size, ptr);
        env->ReleaseIntArrayElements(pixelArray, pixelPoint, 0);
    
        return retArray;
    }

###**修改 MainActivity.java**

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

###**运行结果**

![](https://github.com/weichao66666/OpenCV_Corrode/raw/master/README.md-images/04.png)

---





