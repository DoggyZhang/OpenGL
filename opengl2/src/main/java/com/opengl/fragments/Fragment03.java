package com.opengl.fragments;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.View;

import com.opengl.R;
import com.opengl.fragments.base.BaseFragment;
import com.opengl.utils.ShaderHelper;
import com.opengl.utils.TextResourceReader;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/11/21.
 */

public class Fragment03 extends BaseFragment {

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
        private static final String A_POSITION = "a_Position";
        private static final String A_COLOR = "a_Color";
        private static final int POSITION_COMPONENT_COUNT = 2;
        private static final int COLOR_COMPONENT_COUNT = 3;
        private static final int BYTES_PER_FLOAT = 4;
        private static final int STRIDE =
                (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

        private final FloatBuffer vertexData;
        private final Context mContext;

        private int program;
        private int aPositionLocation;
        private int aColorLocation;

        public Renderer(Context context) {
            this.mContext = context;
            float[] tableVerticesWithTriangles = {
                    // Order of coordinates: X, Y, R, G, B

                    // Triangle Fan
                    0f,    0f,   1f,   1f,   1f,
                    -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                    0.5f, -0.5f, 0.7f, 0.7f, 0.7f,
                    0.5f,  0.5f, 0.7f, 0.7f, 0.7f,
                    -0.5f,  0.5f, 0.7f, 0.7f, 0.7f,
                    -0.5f, -0.5f, 0.7f, 0.7f, 0.7f,

                    // Line 1
                    -0.5f, 0f, 1f, 0f, 0f,
                    0.5f, 0f, 1f, 0f, 0f,

                    // Mallets
                    0f, -0.25f, 0f, 0f, 1f,
                    0f,  0.25f, 1f, 0f, 0f
            };

            vertexData = ByteBuffer
                    .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                    .order(ByteOrder.nativeOrder()).asFloatBuffer();

            vertexData.put(tableVerticesWithTriangles);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0f, 0f, 0f, 0f);

            String mVertexShaderSource = TextResourceReader.readTextResource(mContext, R.raw.simple_vertex_shader3);
            String mFragmentShaderSource = TextResourceReader.readTextResource(mContext, R.raw.simple_fragment_shader3);

            int vertexShader = ShaderHelper.compileVertexShader(mVertexShaderSource);
            int fragmentShader = ShaderHelper.compileFragmentShader(mFragmentShaderSource);

            program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

            GLES20.glUseProgram(program);

            aColorLocation = GLES20.glGetAttribLocation(program, A_COLOR);
            aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);

            vertexData.position(0);
            GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
            GLES20.glEnableVertexAttribArray(aPositionLocation);

            vertexData.position(POSITION_COMPONENT_COUNT);
            GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
            GLES20.glEnableVertexAttribArray(aColorLocation);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {

            GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            // Draw table
            GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);

            // Draw center line
            GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

            // Draw the first mallet blue.
            GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

            // Draw the second mallet red.
            GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
        }
    }

}
