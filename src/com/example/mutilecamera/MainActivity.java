package com.example.mutilecamera;

import java.io.IOException;

import android.hardware.Camera;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("NewApi")
public class MainActivity extends Activity implements SurfaceHolder.Callback {

	SurfaceView sf = null;
	SurfaceHolder hf = null;

	SurfaceView sb = null;
	SurfaceHolder hb = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sf = (SurfaceView) findViewById(R.id.surfaceView1);
		hf = sf.getHolder();
		hf.addCallback(this);
		hf.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		sb = (SurfaceView) findViewById(R.id.surfaceView2);
		hb = sb.getHolder();
		hb.addCallback(this);
		hb.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@SuppressLint("NewApi")
	private int FindFrontCamera() {
		int cameraCount = 0;
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		cameraCount = Camera.getNumberOfCameras(); // get cameras number

		for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
				// ��������ͷ�ķ�λ��Ŀǰ�ж���ֵ�����ֱ�ΪCAMERA_FACING_FRONTǰ�ú�CAMERA_FACING_BACK����
				return camIdx;
			}
		}
		return -1;
	}

	@SuppressLint("NewApi")
	private int FindBackCamera() {
		int cameraCount = 0;
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		cameraCount = Camera.getNumberOfCameras(); // get cameras number

		for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo); // get camerainfo
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
				// ��������ͷ�ķ�λ��Ŀǰ�ж���ֵ�����ֱ�ΪCAMERA_FACING_FRONTǰ�ú�CAMERA_FACING_BACK����
				return camIdx;
			}
		}
		return -1;
	}

	Camera cf = null;
	Camera cb = null;

	public void surfaceCreated(SurfaceHolder holder) {
		if (holder == hb) {
			int cammeraIndex = FindBackCamera();
			if (cammeraIndex == -1) {

			} else {
				cb = Camera.open(cammeraIndex);
			}
			return;
		}

		if (holder == hf) {
			int cammeraIndex = FindFrontCamera();
			if (cammeraIndex == -1) {

			} else {
				cf = Camera.open(cammeraIndex);
			}
			return;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		// �Ѿ����Surface��width��height������Camera�Ĳ���

		// Camera.Parameters parameters = cf.getParameters();
		//
		// parameters.setPreviewSize(width, height);
		//
		// cf.setParameters(parameters);

		if(holder == hf){
		try {

			// ������ʾ

			cf.setPreviewDisplay(holder);

		} catch (IOException exception) {

			cf.release();

			cf = null;

		}

		// ��ʼԤ��

		cf.startPreview();
		
		return;
		}
		
		if(holder == hb){
			try {

				// ������ʾ

				cb.setPreviewDisplay(holder);

			} catch (IOException exception) {

				cb.release();

				cb = null;

			}

			// ��ʼԤ��

			cb.startPreview();
			
			return;
			}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}
}
