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