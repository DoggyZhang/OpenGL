package com.opengl.fragments;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.os.Bundle;
import android.view.View;

import com.opengl.R;
import com.opengl.fragments.base.BaseFragment;
import com.opengl.objects.Mallet;
import com.opengl.objects.Table;
import com.opengl.programs.ColorShaderProgram;
import com.opengl.programs.TextureShaderProgram;
import com.opengl.utils.MatrixHelper;
import com.opengl.utils.TextureHelper;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/11/21.
 */
// Texture
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
        glSurfaceView.setEGLContextClientVersion(2);
        glSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        renderer = new Renderer(getContext());
        glSurfaceView.setRenderer(renderer);
    }

    @Override
    public void initEvent() {

    }

    private class Renderer implements GLSurfaceView.Renderer {

        private final Context mContext;
        private final float[] mProjectionMatrix = new float[16];
        private final float[] mModelMatrix = new float[16];

        private Table mTable;
        private Mallet mMallet;

        private TextureShaderProgram mTextureShaderProgram;
        private ColorShaderProgram mColorShaderProgram;

        private int mTexture;

        public Renderer(Context context) {
            this.mContext = context;
        }

        @Override
        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            GLES20.glClearColor(0f, 0f, 0f, 0f);

            mTable = new Table();
            mMallet = new Mallet();

            mTextureShaderProgram = new TextureShaderProgram(mContext) {
                @Override
                public int setVertexShaderResourceId() {
                    return R.raw.texture_vertex_shader5;
                }

                @Override
                public int setFragmentShaderResourceId() {
                    return R.raw.texture_fragment_shader5;
                }
            };

            mColorShaderProgram = new ColorShaderProgram(mContext) {
                @Override
                public int setVertexShaderResourceId() {
                    return R.raw.simple_vertex_shader5;
                }

                @Override
                public int setFragmentShaderResourceId() {
                    return R.raw.simple_fragment_shader5;
                }
            };

            mTexture = TextureHelper.loadTexture(mContext, R.mipmap.air_hockey_surface);
        }

        @Override
        public void onSurfaceChanged(GL10 gl, int width, int height) {
            GLES20.glViewport(0, 0, width, height);

            MatrixHelper.perspectiveM(mProjectionMatrix, 45, (float) width
                    / (float) height, 1f, 10f);

            Matrix.setIdentityM(mModelMatrix, 0);
            Matrix.translateM(mModelMatrix, 0, 0f, 0f, -2.5f);
            Matrix.rotateM(mModelMatrix, 0, -60f, 1f, 0f, 0f);

            final float[] temp = new float[16];
            Matrix.multiplyMM(temp, 0, mProjectionMatrix, 0, mModelMatrix, 0);
            System.arraycopy(temp, 0, mProjectionMatrix, 0, temp.length);
        }

        @Override
        public void onDrawFrame(GL10 gl) {
            GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT | GLES20.GL_DEPTH_BUFFER_BIT);

            // Draw the table
            mTextureShaderProgram.useProgram();
            mTextureShaderProgram.setUniforms(mProjectionMatrix, mTexture);
            mTable.bindData(mTextureShaderProgram);
            mTable.draw();

            // Draw the mallets
            mColorShaderProgram.useProgram();
            mColorShaderProgram.setUniforms(mProjectionMatrix);
            mMallet.bindData(mColorShaderProgram);
            mMallet.draw();
        }
    }

}
