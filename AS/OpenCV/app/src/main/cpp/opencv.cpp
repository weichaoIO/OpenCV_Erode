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
