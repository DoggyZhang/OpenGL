package com.opengl.fragments;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.opengl.R;
import com.opengl.fragments.base.BaseFragment;
import com.opengl.fragments.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/11/21.
 */

public class Fragment02 extends BaseFragment {

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
        glSurfaceView.setRenderer(renderer);
    }

    @Override
    public void initEvent() {

    }

    private class Renderer implements GLSurfaceView.Renderer {
        private Context mContext;

        private static final int POSITION_COMPONENT_COUNT = 2;
        private static final int BYTES_PER_FLOAT = 4;

        private String mVertexShaderSource;
        private String mFragmentShaderSource;

        public Renderer(Context context) {
            this.mContext = context;
            mVertexShaderSource = TextResourceReader.readTextResource(context, R.raw.simple_vertex_shader);
            mFragmentShaderSource = TextResourceReader.readTextResource(context, R.raw.simple_fragment_shader);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            float[] tableVertices = {
                    0f, 0f,
                    0f, 14f,
                    9f, 14f,
                    9f, 0f,
            };

            float[] tableVerticesWithTriangles = {
                    // Triangle 1
                    0f, 0f,
                    9f, 14f,
                    0f, 14f,

                    // Triangle 2
                    0f, 0f,
                    9f, 0f,
                    9f, 14f,

                    // Line 1
                    0f, 7f,
                    9f, 7f,

                    // Mallets
                    4.5f, 2f,
                    4.5f, 12f,
            };

            FloatBuffer vertexData = ByteBuffer.allocateDirect(tableVertices.length * BYTES_PER_FLOAT)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            vertexData.put(tableVertices);
            vertexData.position(0);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {

        }

        @Override
        public void onDrawFrame(GL10 gl) {

        }
    }

}
