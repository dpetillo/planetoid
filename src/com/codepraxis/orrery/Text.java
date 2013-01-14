package com.codepraxis.orrery;

import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.opengl.GLUtils;

public class Text {

	
	public static void CreateTextTexture(GL10 gl, Context context, int[] textures, int idx, String text)
	{
		// Draw the text
		Paint textPaint = new Paint();
		textPaint.setTextSize(32);
		textPaint.setAntiAlias(true);
		textPaint.setARGB(0xaa, 0xff, 0xff, 0xff);
		
		Rect textSize = new Rect();
		textPaint.getTextBounds("Hello World", 0, "Hello World".length(), textSize);
		
		int maxDim = textSize.width();
		if (textSize.height() > maxDim)
		{
			maxDim = textSize.height();
		}
		int textureSize = (int)Math.floor(Math.log(maxDim)/Math.log(2));
		
		// Create an empty, mutable bitmap
		Bitmap bitmap = Bitmap.createBitmap(textureSize, textureSize, Bitmap.Config.ARGB_4444);
		// get a canvas to paint over the bitmap
		Canvas canvas = new Canvas(bitmap);
		//bitmap.eraseColor(0);

		canvas.drawText("Hello World", (textureSize-textSize.width())/2.0f ,(textureSize - textSize.height())/2.0f, textPaint);
		
		// get a background image from resources
		// note the image format must match the bitmap format
		/*	
	  	Drawable background = context.getResources().getDrawable(R.drawable.marssquare);
		background.setBounds(0, 0, 256, 256);
		background.draw(canvas); // draw the background to our bitmap
		*/
		
		
		// draw the text centered
		

		//Generate one texture pointer...
		gl.glGenTextures(1, textures, idx);
		//...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[idx]);

		//Create Nearest Filtered Texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

		//Different possible texture parameters, e.g. GL10.GL_CLAMP_TO_EDGE
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S, GL10.GL_REPEAT);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_T, GL10.GL_REPEAT);

		//Use the Android GLUtils to specify a two-dimensional texture image from our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		//Clean up
		bitmap.recycle();

	}
	
}
