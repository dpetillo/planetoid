package com.codepraxis.orrery;

import java.util.Date;

import android.content.Context;
import android.view.SurfaceHolder;

public interface OrreryViewInterface {

	public abstract void updateDate(Date newDate);
	public abstract void updateObjectPicked(String objectName);
	public abstract void updateSpeedChange(int speed);
	
	public abstract Context getContext();

	public abstract SurfaceHolder getSurfaceHolder();
	
	
}