package com.opencv.face;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class FaceId {
	/**
	 * OpenCV人脸识别
	 * 
	 * @param rgb
	 * @param gray
	 */
	public static void faceRecognition(Mat rgb, Mat gray) {

		// 读取OpenCV的人脸特征识别文件
		CascadeClassifier cascade = new CascadeClassifier(
				"D:/Soft/opencv/opencv/sources/data/haarcascades/haarcascade_frontalface_alt.xml");
		// 在路径上没有找到相关文件返回1
		if (cascade.empty()) {
			System.out.println("人脸识别文件读取失败");
			// return;
		}

		MatOfRect rect = new MatOfRect();
		// 检测人脸
		cascade.detectMultiScale(gray, rect);
		// 为每张识别到的人脸画一个圈
		for (Rect re : rect.toArray()) {

			Imgproc.rectangle(rgb, new Point(re.x, re.y), new Point(re.x + re.width, re.y + re.height),
					new Scalar(0, 0, 255));

		}
		// 图形界面显示
		HighGui.imshow("人脸识别", rgb);

	}

	/**
	 * OpenCV人眼识别
	 * 
	 * @param rgb
	 * @param gray
	 */
	public static void eyeRecognition(Mat rgb, Mat gray) {

		// 读取OpenCV的人眼特征识别文件
		CascadeClassifier cascade = new CascadeClassifier(
				"D:/Soft/opencv/opencv/sources/data/haarcascades/haarcascade_eye_tree_eyeglasses.xml");
		if (cascade.empty()) {
			System.out.println("人眼识别文件读取失败");
			return;
		}

		MatOfRect rect = new MatOfRect();

		cascade.detectMultiScale(gray, rect);

		for (Rect re : rect.toArray()) {
			Imgproc.rectangle(rgb, new Point(re.x, re.y), new Point(re.x + re.width, re.y + re.height),
					new Scalar(255, 0, 0));

		}

		// HighGui.imshow("人脸识别", rgb);

	}
}
