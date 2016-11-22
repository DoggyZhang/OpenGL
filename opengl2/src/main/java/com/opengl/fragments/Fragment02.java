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

import static android.opengl.GLES20.glUniform4f;

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
        renderer = new Renderer(getContext());
        glSurfaceView.setRenderer(renderer);
    }

    @Override
    public void initEvent() {

    }

    private class Renderer implements GLSurfaceView.Renderer {
        private static final String U_COLOR = "u_Color";
        private static final String A_POSITION = "a_Position";
        private static final int POSITION_COMPONENT_COUNT = 2;
        private static final int BYTES_PER_FLOAT = 4;
        private final FloatBuffer vertexData;
        private Context mContext;
        private int program;
        private int uColorLocation;
        private int aPositionLocation;

        public Renderer(Context context) {
            this.mContext = context;


            float[] tableVerticesWithTriangles = {
                    // Triangle 1
                    -0.5f, -0.5f,
                    0.5f, 0.5f,
                    -0.5f, 0.5f,

                    // Triangle 2
                    -0.5f, -0.5f,
                    0.5f, -0.5f,
                    0.5f, 0.5f,

                    // Line 1
                    -0.5f, 0f,
                    0.5f, 0f,

                    // Mallets
                    0f, -0.25f,
                    0f, 0.25f
            };


            vertexData = ByteBuffer
                    .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer();
            vertexData.put(tableVerticesWithTriangles);
            vertexData.position(0);
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            // Set the background clear color to red. The first component is red,
            // the second is green, the third is blue, and the last component is
            // alpha, which we don't use in this lesson.
            GLES20.glClearColor(0f, 0f, 0f, 0f);

            String mVertexShaderSource = TextResourceReader.readTextResource(mContext, R.raw.simple_vertex_shader);
            String mFragmentShaderSource = TextResourceReader.readTextResource(mContext, R.raw.simple_fragment_shader);

            int vertexShader = ShaderHelper.compileVertexShader(mVertexShaderSource);
            int fragmentShader = ShaderHelper.compileFragmentShader(mFragmentShaderSource);

            program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

            GLES20.glUseProgram(program);

            uColorLocation = GLES20.glGetUniformLocation(program, U_COLOR);
            aPositionLocation = GLES20.glGetAttribLocation(program, A_POSITION);

            GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, 0, vertexData);
            GLES20.glEnableVertexAttribArray(aPositionLocation);

        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);
        }

        @Override
        public void onDrawFrame(GL10 gl) {

            GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

            // Draw table
            GLES20.glUniform4f(uColorLocation, 1f, 1f, 1f, 1f);
            GLES20.glDrawArrays(GLES20.GL_TRIANGLES, 0, 6);

            // Draw center line
            glUniform4f(uColorLocation, 0f, 1f, 0f, 1f);
            GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);

            // Draw the first mallet blue.
            GLES20.glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
            GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);

            // Draw the second mallet red.
            glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
            GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
        }
    }

}
