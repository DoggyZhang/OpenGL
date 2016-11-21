package com.opengl.fragments;

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

public class Fragment05 extends BaseFragment {

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
    }

    @Override
    public void initEvent() {

    }

    private class Renderer implements GLSurfaceView.Renderer {

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }
    }

}
