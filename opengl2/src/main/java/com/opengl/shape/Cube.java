package com.opengl.shape;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by Administrator on 2016/11/21.
 */

public class Cube {

    private final FloatBuffer mFVertexButter;
    private final ByteBuffer mColorBuffer;
    private final ByteBuffer mTfan1;
    private final ByteBuffer mTfan2;

    public Cube() {

        float vertices[] = {
                -1f, 1f, 1f,
                1f, 1f, 1f,
                1f, -1f, 1f,
                -1f, -1f, 1f,

                -1f, 1f, -1f,
                1f, 1f, -1f,
                1f, -1f, -1f,
                -1f, -1f, -1f
        };

        byte maxColor = (byte) 255;

        byte colors[] = {
//                maxColor, maxColor, 0, maxColor,
//                0, maxColor, maxColor, maxColor,
//                0, 0, 0, maxColor,
//                maxColor, 0, maxColor, maxColor,
//
//                maxColor, 0, 0, maxColor,
//                0, maxColor, 0, maxColor,
//                0, 0, maxColor, maxColor,
//                0, 0, 0, maxColor,

                maxColor, maxColor, 0, maxColor,
                0, maxColor, maxColor, maxColor,
                0, 0, 0, maxColor,
                maxColor, 0, maxColor, maxColor,

                maxColor, 0, 0, maxColor,
                0, maxColor, 0, maxColor,
                0, 0, maxColor, maxColor,
                0, 0, 0, maxColor,
        };

        byte tfan1[] = {
                1, 0, 3,
                1, 3, 2,
                1, 2, 5,
                1, 6, 5,
                1, 5, 4,
                1, 4, 0,
        };

        byte tfan2[] = {
                7, 4, 5,
                7, 5, 6,
                7, 6, 2,
                7, 2, 3,
                7, 3, 0,
                7, 0, 4
        };

        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mFVertexButter = vbb.asFloatBuffer();
        mFVertexButter.put(vertices);
        mFVertexButter.position(0);

        mColorBuffer = ByteBuffer.allocateDirect(colors.length);
        mColorBuffer.put(colors);
        mColorBuffer.position(0);

        mTfan1 = ByteBuffer.allocateDirect(tfan1.length);
        mTfan1.put(tfan1);
        mTfan1.position(0);

        mTfan2 = ByteBuffer.allocateDirect(tfan2.length);
        mTfan2.put(tfan2);
        mTfan2.position(0);
    }

    public void draw(GL10 gl) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexButter);
        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, mColorBuffer);

        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, mTfan1);
        gl.glDrawElements(GL10.GL_TRIANGLE_FAN, 6 * 3, GL10.GL_UNSIGNED_BYTE, mTfan2);

    }
}
