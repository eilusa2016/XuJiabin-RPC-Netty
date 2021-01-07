package com.opencv;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

import com.opencv.face.FaceId;

public class MainFace {

	public static void main(String[] args) {

		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    	// 实例化相机
        VideoCapture videoCapture = new VideoCapture();
        // 如果要从摄像头获取视频 则要在 VideoCapture 的构造方法写 0
        if (!videoCapture.open(0)) {
            System.out.println("相机打开失败");
            return;
        }
        while (true) {
            Mat img = new Mat();
            if (!videoCapture.read(img)) {
                return;

            }
            Mat rgb = new Mat();
            // 灰度化
            Imgproc.cvtColor(img, rgb, Imgproc.COLOR_BGR2RGB);

            Mat gray = new Mat();

            Imgproc.cvtColor(rgb, gray, Imgproc.COLOR_RGB2GRAY);
            FaceId.faceRecognition(img, gray);
            FaceId.eyeRecognition(img, gray);
            HighGui.waitKey(10);

        }
	}
}
