package com.opengl.fragments;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import com.opengl.R;
import com.opengl.fragments.base.BaseFragment;

import java.math.BigDecimal;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/11/21.
 */

public class Fragment01 extends BaseFragment {

    private GLSurfaceView glSurfaceView;
    private Renderer renderer;

    @Override
    public int getLayoutID() {
        return R.layout.fragment_01;
    }

    @Override
    public void initArgument(Bundle arguments) {

    }

    @Override
    public void initView(View root) {
        glSurfaceView = (GLSurfaceView) root.findViewById(R.id.glSurfaceView);
        renderer = new Renderer();
        glSurfaceView.setRenderer(renderer);
        glSurfaceView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    @Override
    public void initEvent() {
        glSurfaceView.setOnTouchListener(new View.OnTouchListener() {
            float preX;
            float preY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {

                final float deltaX = preX - event.getX();
                final float deltaY = preY - event.getY();

                preX = event.getX();
                preY = event.getY();

                glSurfaceView.queueEvent(new Runnable() {
                    @Override
                    public void run() {
                        renderer.setRotateX(-deltaX);
                        renderer.setRotateY(-deltaY);
                        glSurfaceView.requestRender();
                    }
                });
                return true;
            }
        });

//        glSurfaceView.setOnTouchListener(new TouchListener(getContext()));
    }

    public class TouchListener implements View.OnTouchListener {
        private Context context;

        public TouchListener(Context context) {
            this.context = context;
            mScaleDetector = new ScaleGestureDetector(context, new TouchListener.ScaleListener());
            mTapDetector = new GestureDetector(context, new TouchListener.TapListener());
        }

        private ScaleGestureDetector mScaleDetector;
        private GestureDetector mTapDetector;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getPointerCount() > 1) {
                mScaleDetector.onTouchEvent(event);
                return true;
            } else {
                mTapDetector.onTouchEvent(event);
                return true;
            }
        }

        private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

            float prevScaleFactor;

            @Override
            public boolean onScale(ScaleGestureDetector detector) {
                float scaleFactor = detector.getScaleFactor();
                scaleFactor = BigDecimal.valueOf(scaleFactor).setScale(3, BigDecimal.ROUND_HALF_UP).floatValue();
                if (Float.compare(scaleFactor, 1.0f) == 0 || Float.compare(scaleFactor, prevScaleFactor) == 0) {
                    return true;
                }
                if (scaleFactor > 1f) {
                    renderer.setzNear(renderer.getzNear() + 1.0f);
                    glSurfaceView.requestRender();
                }
                if (scaleFactor < 1f) {
                    renderer.setzNear(renderer.getzNear() - 1.0f);
                    glSurfaceView.requestRender();
                }
                prevScaleFactor = scaleFactor;
                return true;
            }

        }

        private class TapListener extends GestureDetector.SimpleOnGestureListener {

            @Override
            public boolean onSingleTapConfirmed(MotionEvent event) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                float deltaX = e1.getX() - e2.getX();
                float deltaY = e1.getY() - e2.getY();
                if (deltaX > 0) {
                    renderer.setRotateX(renderer.getRotateX() + 1.0f);
                } else {
                    renderer.setRotateX(renderer.getRotateX() - 1.0f);
                }
                if (deltaY > 0) {
                    renderer.setRotateY(renderer.getRotateY() + 1.0f);
                } else {
                    renderer.setRotateY(renderer.getRotateY() - 1.0f);
                }
                glSurfaceView.requestRender();
                return false;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (glSurfaceView != null) {
            glSurfaceView.onResume();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (glSurfaceView != null) {
            glSurfaceView.onPause();
        }
    }


    private class Renderer implements GLSurfaceView.Renderer {

        private Cube mCube;
        private float rotateX;
        private float rotateY;
        private float zFar;
        private static final float MIN_Z_NEAR = 5;
        private static final float MAX_Z_NEAR = 800;
        private float zNear;
        private float mTransY = 0;
        private float aspectRatio;
        private float fieldOfView;
        private float size;

        public void setRotateX(float rotateX) {
            this.rotateX += rotateX % 360;
        }

        public void setRotateY(float rotateY) {
            this.rotateY += rotateY % 360;
        }

        public float getRotateX() {
            return rotateX;
        }

        public float getRotateY() {
            return rotateY;
        }

        public void setzFar(float zFar) {
            this.zFar = zFar;
        }

        public void setzNear(float zNear) {
            if (zNear < MIN_Z_NEAR) {
                this.zNear = MIN_Z_NEAR;
            } else if (zNear > MAX_Z_NEAR) {
                this.zNear = MAX_Z_NEAR;
            } else {
                this.zNear = zNear;
            }
        }

        public float getzNear() {
            return zNear;
        }

        public Renderer() {
//            float vertices[] = {
//                    -1.0f, -1.0f,
//                    1.0f, -1.0f,
//                    -1.f, 1.0f,
//                    1.0f, 1.0f
//            };
//
//            byte maxColor = (byte) 255;
//
//            byte colors[] = {
//                    maxColor, maxColor, 0, maxColor,
//                    0, maxColor, maxColor, maxColor,
//                    0, 0, 0, maxColor,
//                    maxColor, 0, maxColor, maxColor
//            };
//
//            byte indices[] = {
//                    0, 3, 1,
//                    0, 2, 3
//            };
            mCube = new Cube();
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            gl.glClearColor(1, 1, 1, 1);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            gl.glViewport(0, 0, width, height);

            zNear = 0.1f;
            zFar = 1000;
            fieldOfView = 30.0f / (180 / 3.1415926f);

            gl.glEnable(GL10.GL_NORMALIZE);

            aspectRatio = width * 1.0f / height;

            gl.glMatrixMode(GL10.GL_PROJECTION);

            size = zNear * (float) Math.tan(fieldOfView / 2);

            gl.glFrustumf(-size, size, -size / aspectRatio, size / aspectRatio, zNear, zFar);

            gl.glMatrixMode(GL10.GL_MODELVIEW);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            gl.glClearColor(1, 1, 1, 1);

            gl.glMatrixMode(GL10.GL_MODELVIEW);
            gl.glLoadIdentity();

            gl.glTranslatef(0, (float) Math.sin(mTransY), -5f);
//            gl.glRotatef((float) Math.sin(mTransY) * 360, 0, 1, 0);
//            mTransY += 0.01f;
            gl.glRotatef(rotateX, 0, 1, 0);
            gl.glRotatef(rotateY, 1, 0, 0);

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

            mCube.draw(gl);
        }
    }

}
