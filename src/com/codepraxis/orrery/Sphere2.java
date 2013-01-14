package com.codepraxis.orrery;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class Sphere2 {

	 private FloatBuffer sphereVertex;
	 private FloatBuffer sphereNormal;
	 private FloatBuffer sphereTexture;
	 private float radius;
	 private int slices, stacks;
	
	 public Sphere2(float radius, int slices, int stacks)
	 {

         this.radius = radius;
         this.slices = slices;
         this.stacks = stacks;
		 
		 plotSpherePoints(radius, stacks, slices);
	 
	 }
	 
	 private void plotSpherePoints(float radius, int stacks, int slices)
	 {
	
		ByteBuffer bb = ByteBuffer.allocateDirect(4 * 6 * stacks * (slices+1));
		bb.order(ByteOrder.nativeOrder());
		sphereVertex = bb.asFloatBuffer();
		 
		bb = ByteBuffer.allocateDirect(4 * 6 * stacks * (slices+1));
		bb.order(ByteOrder.nativeOrder());
		sphereNormal = bb.asFloatBuffer();
		
		bb = ByteBuffer.allocateDirect(4 * 4 * stacks * (slices+1));
		bb.order(ByteOrder.nativeOrder());
		sphereTexture = bb.asFloatBuffer();
		
	     int i, j;
	     float slicestep, stackstep;

	     stackstep = ((float)Math.PI) / stacks;

	     
	     slicestep = 2.0f * ((float)Math.PI) / slices;

	     for (i = 0; i < stacks; ++i)
	     {
	             float a = i * stackstep;
	             float b = a + stackstep;

	             float s0 =  (float)Math.sin(a);
	             float s1 =  (float)Math.sin(b);

	             float c0 =  (float)Math.cos(a);
	             float c1 =  (float)Math.cos(b);

	             float nv;
	             for (j = 0; j <= slices; ++j)
	             {
	                     float c = j * slicestep;
	                     float x = (float)Math.cos(c);
	                     float y = (float)Math.sin(c);

	                     nv=x * s0;
	                     sphereNormal.put(nv);
	                     sphereVertex.put( nv * radius);
	                     
	                     /*if (i >= stacks/2)
	                     {
	                    	 sphereTexture.put((float)Math.asin(nv)/(float)Math.PI + 0.5f);
	                     }
	                     else 
	                     {
	                    	 sphereTexture.put((float)Math.asin(nv)/(float)Math.PI + 0.5f);
	                     }*/
	                     
	                     nv=y * s0;
	                     sphereNormal.put(nv);
	                     sphereVertex.put( nv * radius);

	                     /*
	                     if (i >= stacks/2)
	                     {
	                    	 sphereTexture.put((float)Math.asin(nv)/(float)Math.PI + 0.5f);
	                     }
	                     else 
	                     {
	                    	 sphereTexture.put((float)Math.asin(nv)/(float)Math.PI + 0.5f);
	                     }*/
	                     
	                     nv=c0;

	                     sphereNormal.put(nv);
	                     sphereVertex.put( nv * radius);
	                     
	                     sphereTexture.put(c / (2.0f * (float)Math.PI));
	                     sphereTexture.put(a / (float)Math.PI);
	                     
	                     
	                     //sphereTexture.put(c0 * radius / (float)Math.sqrt(x * s0 * radius * x * s0 * radius + y * s0 * radius * y * s0 * radius + c0 * radius * c0 * radius));
	                     //sphereTexture.put(x * s0 * radius / (float)Math.sqrt(x * s0 * radius * x * s0 * radius + y * s0 * radius * y * s0 * radius + c0 * radius * c0 * radius));

	                     nv=x * s1;

	                     sphereNormal.put(nv);
	                     sphereVertex.put( nv * radius);
	                     
	                     /*
	                     if (i >= stacks/2)
	                     {
	                    	 sphereTexture.put((float)Math.asin(nv)/(float)Math.PI + 0.5f);
	                     }
	                     else 
	                     {
	                    	 sphereTexture.put((float)Math.asin(nv)/(float)Math.PI + 0.5f);
	                     }*/

	                     nv=y * s1;

	                     sphereNormal.put(nv);
	                     sphereVertex.put( nv * radius);
	                     /*
	                     if (i >= stacks/2)
	                     {
	                    	 sphereTexture.put((float)Math.asin(nv)/(float)Math.PI + 0.5f);
	                     }
	                     else 
	                     {
	                    	 sphereTexture.put((float)Math.asin(nv)/(float)Math.PI + 0.5f);
	                     }*/
	                     
	                     nv=c1;

	                     sphereNormal.put(nv);
	                     sphereVertex.put( nv * radius);
	                     
	                     sphereTexture.put(c / (2.0f * (float)Math.PI));
	                     sphereTexture.put(b / (float)Math.PI);
	                     
	                     
	                     //sphereTexture.put(c1 * radius / (float)Math.sqrt(x * s1 * radius * x * s1 * radius + y * s1 * radius * y * s1 * radius + c1 * radius * c1 * radius));
	                     //sphereTexture.put(x * s1 * radius / (float)Math.sqrt(x * s1 * radius * x * s1 * radius + y * s1 * radius * y * s1 * radius + c1 * radius * c1 * radius));
	                     
	             }
	     }
	     sphereNormal.position(0);
	     sphereVertex.position(0);
	     sphereTexture.position(0);
	 }	 
		 
	    public void draw(GL10 gl, boolean useTexture, int texture)
	    {
	            int i, triangles;
	/*
	            gl.glMatrixMode(GL10.GL_TEXTURE);
	            gl.glRotatef(90f, 0f, 0f, 1f);    // (1) Here you transform texture space
	            gl.glMatrixMode(GL10.GL_MODELVIEW);
	 */          
	            if (useTexture)
	            {
	            	gl.glEnable(GL10.GL_TEXTURE_2D);
	            }
	            gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
	
	            gl.glVertexPointer(3, GL10.GL_FLOAT, 0, sphereVertex);
	            gl.glNormalPointer(GL10.GL_FLOAT, 0, sphereNormal);
	            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, sphereTexture);
	
	            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
	            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
	            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	            
	            triangles = (slices + 1) * 2;
	
	            for(i = 0; i < stacks; i++)
	            {
	                    gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, i * triangles, triangles);
	            }
	            
	            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
	            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
	            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
	            gl.glDisable(GL10.GL_TEXTURE_2D);
	    }
}
