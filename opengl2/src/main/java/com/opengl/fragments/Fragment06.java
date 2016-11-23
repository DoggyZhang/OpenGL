package com.opengl.fragments;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.opengl.R;
import com.opengl.fragments.base.BaseFragment;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/11/21.
 */
// 构建简单物体
public class Fragment06 extends BaseFragment {

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
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        renderer = new Renderer(getContext());
        glSurfaceView.setRenderer(renderer);
    }

    @Override
    public void initEvent() {

    }

    private class Renderer implements GLSurfaceView.Renderer {
        private Context mContext;

        public Renderer(Context context) {
            this.mContext = context;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0f, 0f, 0f, 0f);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        }
    }

}
