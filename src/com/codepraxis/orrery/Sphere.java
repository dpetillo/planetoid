package com.codepraxis.orrery;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;



class VERTICES 
{
   
	public float X;
    
	public float Y;
    
	public float Z;
    

    
	public float U;
    
	public float V;
};


public class Sphere {
	
	
	int[] texture = new int[1];
	
	double angle = 0;
	
	private int space = 15;

	private int VertexCount = (90 / space) * (360 / space) * 4;


	VERTICES[] VERTEX = new VERTICES[VertexCount];
	
	FloatBuffer vertexBuffer, texBuffer;
	FloatBuffer vertexBuffer1; 
	ShortBuffer indexBuffer;

	void DisplaySphere (GL10 gl, int texture)
	{
		
		gl.glBindTexture(GL10.GL_TEXTURE_2D, texture);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
        
        // Set the face rotation
        gl.glFrontFace(GL10.GL_CW);
		        
		gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
	    gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, texBuffer);
	
	    
		gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, VertexCount );
		
		
		//Disable the client state before leaving
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		
		
	}
	
	
	
	public Sphere(float R, float H, float K, float Z) 
	{
		int n;
		float theta;
		float phi;
		n = 0;
		    
		for( phi = 0; phi <= 90 - space; phi+=space){
			for( theta = 0; theta <= 360 - space; theta+=space){
			

		    	VERTEX[n] = new VERTICES();
		    	
		    	VERTEX[n].X = (float) (R * Math.sin((theta) / 180 * Math.PI) * Math.sin((phi) / 180 * Math.PI) - H);
		    	VERTEX[n].Y = (float) (R * Math.cos((theta) / 180 * Math.PI) * Math.sin((phi) / 180 * Math.PI) + K);
		    	VERTEX[n].Z = (float) (R * Math.cos((phi) / 180 * Math.PI) - Z);
		    	VERTEX[n].V = (phi) / 360;
		    	VERTEX[n].U = (theta) / 360;
		    	
		    	VERTEX[n].U = 0.0f; //VERTEX[n].X / (float)Math.sqrt(VERTEX[n].X * VERTEX[n].X + VERTEX[n].Y * VERTEX[n].Y + VERTEX[n].Z * VERTEX[n].Z);
		    	VERTEX[n].V = 1.0f; //VERTEX[n].Y / (float)Math.sqrt(VERTEX[n].X * VERTEX[n].X + VERTEX[n].Y * VERTEX[n].Y + VERTEX[n].Z * VERTEX[n].Z);
		    	
		    	//VERTEX[n].V = (float) (Math.acos(VERTEX[n].X / R) / Math.PI);
		    	//VERTEX[n].U = (float) ((Math.acos(VERTEX[n].X/(R * Math.sin(VERTEX[n].V * Math.PI))) ) / (2 * Math.PI)); 
		    	
		    	n++;
		    	VERTEX[n] = new VERTICES();
		    	VERTEX[n].X = (float) (R * Math.sin((theta) / 180 * Math.PI) * Math.sin((phi + space) / 180 * Math.PI) - H);
		    	VERTEX[n].Y = (float) (R * Math.cos((theta) / 180 * Math.PI) * Math.sin((phi + space) / 180 * Math.PI) + K);
		    	VERTEX[n].Z = (float) (R * Math.cos((phi + space) / 180 * Math.PI) - Z);
		    	VERTEX[n].V = ((phi + space)) / 360;
		    	VERTEX[n].U = (theta) / 360;

		    	VERTEX[n].U = 0.0f; //VERTEX[n].X / (float)Math.sqrt(VERTEX[n].X * VERTEX[n].X + VERTEX[n].Y * VERTEX[n].Y + VERTEX[n].Z * VERTEX[n].Z);
		    	VERTEX[n].V = 0.0f; //VERTEX[n].Y / (float)Math.sqrt(VERTEX[n].X * VERTEX[n].X + VERTEX[n].Y * VERTEX[n].Y + VERTEX[n].Z * VERTEX[n].Z);
		    	//VERTEX[n].V = (float) (Math.acos(VERTEX[n].X / R) / Math.PI);
		    	//VERTEX[n].U = (float) ((Math.acos(VERTEX[n].X/(R * Math.sin(VERTEX[n].V * Math.PI))) ) / (2 * Math.PI)); 
		    	
		    	n++;
		    	VERTEX[n] = new VERTICES();
		    	VERTEX[n].X = (float) (R * Math.sin((theta + space) / 180 * Math.PI) * Math.sin((phi) / 180 * Math.PI) - H);
		    	VERTEX[n].Y = (float) (R * Math.cos((theta + space) / 180 * Math.PI) * Math.sin((phi) / 180 * Math.PI) + K);
		    	VERTEX[n].Z = (float) (R * Math.cos((phi) / 180 * Math.PI) - Z);
		    	VERTEX[n].V = (phi) / 360;
		    	VERTEX[n].U = (theta + space) / 360;
		    	
		    	VERTEX[n].U = 1.0f; //VERTEX[n].X / (float)Math.sqrt(VERTEX[n].X * VERTEX[n].X + VERTEX[n].Y * VERTEX[n].Y + VERTEX[n].Z * VERTEX[n].Z);
		    	VERTEX[n].V = 1.0f; //VERTEX[n].Y / (float)Math.sqrt(VERTEX[n].X * VERTEX[n].X + VERTEX[n].Y * VERTEX[n].Y + VERTEX[n].Z * VERTEX[n].Z);
		    	//VERTEX[n].V = (float) (Math.acos(VERTEX[n].X / R) / Math.PI);
		    	//VERTEX[n].U = (float) ((Math.acos(VERTEX[n].X/(R * Math.sin(VERTEX[n].V * Math.PI))) ) / (2 * Math.PI)); 
		    	
		    	n++;
		    	VERTEX[n] = new VERTICES();
		    	VERTEX[n].X = (float) (R * Math.sin((theta + space) / 180 * Math.PI) * Math.sin((phi + space) / 180 * Math.PI) - H);
		    	VERTEX[n].Y = (float) (R * Math.cos((theta + space) / 180 * Math.PI) * Math.sin((phi + space) / 180 * Math.PI) + K);
		    	VERTEX[n].Z = (float) (R * Math.cos((phi + space) / 180 * Math.PI) - Z);
		    	VERTEX[n].V = ((phi + space)) / 360;
		    	VERTEX[n].U = (theta + space) / 360;
		    	
		    	VERTEX[n].U = 1.0f; //VERTEX[n].X / (float)Math.sqrt(VERTEX[n].X * VERTEX[n].X + VERTEX[n].Y * VERTEX[n].Y + VERTEX[n].Z * VERTEX[n].Z);
		    	VERTEX[n].V = 0.0f; //VERTEX[n].Y / (float)Math.sqrt(VERTEX[n].X * VERTEX[n].X + VERTEX[n].Y * VERTEX[n].Y + VERTEX[n].Z * VERTEX[n].Z);
		    	//VERTEX[n].V = (float) (Math.acos(VERTEX[n].X / R) / Math.PI);
		    	//VERTEX[n].U = (float) ((Math.acos(VERTEX[n].X/(R * Math.sin(VERTEX[n].V * Math.PI))) ) / (2 * Math.PI)); 
		    	
		    	n++;
				            
		
		    }       
	    }

		
		ByteBuffer bb = ByteBuffer.allocateDirect(VertexCount * 3 * 4 * 2);
		bb.order(ByteOrder.nativeOrder());
		vertexBuffer = bb.asFloatBuffer();
		
		bb = ByteBuffer.allocateDirect(VertexCount * 2 * 4 * 2);
		texBuffer = bb.asFloatBuffer();
		
		int b2;
		
		for ( b2 = 0; b2 < VertexCount; b2++){
		
			
			
			texBuffer.put(VERTEX[b2].U);
			texBuffer.put(VERTEX[b2].V);
			
			vertexBuffer.put(VERTEX[b2].X);
			vertexBuffer.put(VERTEX[b2].Y);
			vertexBuffer.put(VERTEX[b2].Z);
			
		}		
		
		vertexBuffer.position(0);
		texBuffer.position(0);
		
		
		
	}

	
	 
	}
