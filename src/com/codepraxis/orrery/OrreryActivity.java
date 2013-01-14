package com.codepraxis.orrery;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.os.Bundle;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

public class OrreryActivity extends Activity implements OrreryViewInterface, OnClickListener {
	
	private GLSurfaceView mGLView;
	private OrreryRenderer orreryRenderer;
	private SeekBar mSeekBar;
	//private TextView mObjectPicked;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        setContentView(R.layout.main);
        
        orreryRenderer = new OrreryRenderer(this);
        
        mGLView = (GLSurfaceView)this.findViewById(R.id.glSurface);
        
        mGLView.setDebugFlags(GLSurfaceView.DEBUG_CHECK_GL_ERROR);

        mGLView.setRenderer(orreryRenderer);
        
                
        mGLView.setOnTouchListener(orreryRenderer);
        
        ImageButton ib;
        ib = (ImageButton)this.findViewById(R.id.closeButton);
        ib.setOnClickListener(this);
       
        ib = (ImageButton)this.findViewById(R.id.homeButton);
        ib.setOnClickListener(this);

        //mObjectPicked = (TextView)this.findViewById(R.id.object);
        
        mSeekBar = (SeekBar)this.findViewById(R.id.seekBar);
        mSeekBar.setOnSeekBarChangeListener( new SeekBar.OnSeekBarChangeListener() {
			
			public void onStopTrackingTouch(SeekBar seekBar) {
				
			}
			
			public void onStartTrackingTouch(SeekBar seekBar) {
							}
			
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				
				orreryRenderer.onSetSpeed(progress);
			}
		});
        
	} 
	
    /*  
	@Override
	public void onAttachedToWindow() {
	    super.onAttachedToWindow();
	    Window window = getWindow();
	    window.setFormat(PixelFormat.RGBA_8888);
	}
	*/


    /* (non-Javadoc)
	 * @see com.codepraxis.orrery.OrreryViewInterface#updateDate(java.util.Date)
	 */
    public void updateDate(Date newDate)
    {
    	final Date closureRef = newDate;
    	this.runOnUiThread(new Runnable() {
    			 
			public void run() {

		    	TextView textView = (TextView)OrreryActivity.this.findViewById(R.id.date);
		    	DateFormat df = new SimpleDateFormat("MMMM dd, yyyy GG");

		    	String reportDate = df.format(closureRef);
		    	textView.setText(reportDate);
    		}
    	}); 	
    }
    
    public Context getContext()
    {
    	return this;
    }
        
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
       
        return true;
    }    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
    	switch (item.getItemId()) {
	        case R.id.today:
	        	orreryRenderer.onHome();
	        	orreryRenderer.onStop();

	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        //mGLView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //mGLView.onResume();
    }

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId())
		{
			case R.id.closeButton: 
			{
            	this.finish();
                break;
			} 
			/*case R.id.playButton:
			{
				playButton.setVisibility(View.GONE);
				stopButton.setVisibility(View.VISIBLE);
				orreryRenderer.onPlay();
				break;
			}
			case R.id.stopButton:
			{
				playButton.setVisibility(View.VISIBLE);
				stopButton.setVisibility(View.GONE);
				orreryRenderer.onStop();
				break;
			}
			case R.id.fastForwardButton:
			{
				orreryRenderer.onFastForward();
				break;
			}
			case R.id.rewindButton:
			{
				orreryRenderer.onRewind();
				break;
			}*/
			case R.id.homeButton:
			{
				orreryRenderer.onHome();
				
				
				
				break;
			}

		}
	}
    
	public SurfaceHolder getSurfaceHolder() {
		// TODO Auto-generated method stub
		return mGLView.getHolder();
	}

	public void updateObjectPicked(final String objectName) {
				/*this.runOnUiThread(new Runnable() {
			 
			public void run() {
				mObjectPicked.setText(objectName);

    		}
    	}); */
	}

	public void updateSpeedChange(final int speed) {
		this.runOnUiThread(new Runnable() {
			 
			public void run() {

		    	mSeekBar.setProgress(speed);
    		}
    	}); 	
		
	}
}