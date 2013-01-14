package com.codepraxis.orrery;

import java.nio.ByteBuffer;
import java.util.Date;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PointF;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.opengl.GLUtils;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

class OrreryRenderer implements GLSurfaceView.Renderer, OnTouchListener {

	// private Sphere sphere = new Sphere(1.0f, 0.0f, 0.0f, 0.0f);
	private Sphere2 sphere2 = new Sphere2(1.0f, 30, 30);
	private float totalScrollX = 0;
	private float totalScrollY = 0;
	// We can be in one of these 3 states
	static final int NONE = 0;
	static final int DRAG = 1;
	static final int ZOOM = 2;
	static final int PRESS = 3;
	private static final String TAG = "Touch";
	// Remember some things for zooming
	private PointF start = new PointF();
	private PointF mid = new PointF();
	private float oldDist = 1f;
	private float touchPinchDelta = 0;
	private OrreryViewInterface orreryViewInterface;

	private Square2 square2;
	boolean pause;

	// texture pointers
	private int[] textures = new int[10];

	public OrreryRenderer(OrreryViewInterface orreryViewInterface) {
		this.orreryViewInterface = orreryViewInterface;
	}

	public void onHome() {
		this.defaults();
	}

	public void onStop() {
		timeChange = 0;
		this.orreryViewInterface.updateSpeedChange(getSpeed());
	}
	/*
	 * 
	 * 	public void onPlay() {
		pause = false;
	}
	 * 
	public void onRewind() {
		timeChange -= 0.25f;
		//skip over zero
		if (timeChange == 0)
		{
			timeChange -= 0.25f;
		}
	}

	public void onFastForward() {
		timeChange += 0.25f;
		//skip over zero
		if (timeChange == 0)
		{
			timeChange += 0.25f;
		}
	}
	*/
	
	/// speed is 0-40, needs to be translated to an exponential scale
	public void onSetSpeed(int speed)
	{
		timeChange = (speed - 20.0) / 10.0;
		
			
	}
	
	private int getSpeed()
	{
		return (int)((timeChange * 10) + 20);
	}
	

	private void loadGLTexture(GL10 gl, Context context, int[] textures,
			int idx, int drawable) {
		// loading texture
		// BitmapFactory.Options bfo = new BitmapFactory.Options();

		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
				drawable);

		// ...and bind it to our array
		gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[idx]);

		// create nearest filtered texture
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER,
				GL10.GL_NEAREST);
		gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER,
				GL10.GL_LINEAR);
		/*
		 * gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_WRAP_S,
		 * GL10.GL_CLAMP_TO_EDGE); gl.glTexParameterf(GL10.GL_TEXTURE_2D,
		 * GL10.GL_TEXTURE_WRAP_T,GL10.GL_CLAMP_TO_EDGE);
		 */
		gl.glTexEnvf(GL10.GL_TEXTURE_ENV, GL10.GL_TEXTURE_ENV_MODE,
				GL10.GL_REPLACE);

		// Use Android GLUtils to specify a two-dimensional texture image from
		// our bitmap
		GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, bitmap, 0);

		// Clean up
		bitmap.recycle();
	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		square2 = new Square2();
		

		dataprep(gl);

		// generate texture pointers
		gl.glGenTextures(9, textures, 0);

		loadGLTexture(gl, this.orreryViewInterface.getContext(), textures, 0,
				R.drawable.mercurysquare);
		loadGLTexture(gl, this.orreryViewInterface.getContext(), textures, 1,
				R.drawable.venussquare);
		loadGLTexture(gl, this.orreryViewInterface.getContext(), textures, 2,
				R.drawable.earthsquare);
		loadGLTexture(gl, this.orreryViewInterface.getContext(), textures, 3,
				R.drawable.marssquare);
		loadGLTexture(gl, this.orreryViewInterface.getContext(), textures, 4,
				R.drawable.jupitersquare);
		loadGLTexture(gl, this.orreryViewInterface.getContext(), textures, 5,
				R.drawable.saturnsquare);
		loadGLTexture(gl, this.orreryViewInterface.getContext(), textures, 6,
				R.drawable.neptunesquare);
		loadGLTexture(gl, this.orreryViewInterface.getContext(), textures, 7,
				R.drawable.uranussquare);
		loadGLTexture(gl, this.orreryViewInterface.getContext(), textures, 8,
				R.drawable.sunsquare);

		//Text.CreateTextTexture(gl, this.orreryViewInterface.getContext(), textures, 9, "HELLO WORLD!");
		
		// Set the background color to black ( rgba ).
		gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		// Enable Smooth Shading, default not really needed.
		gl.glShadeModel(GL10.GL_SMOOTH);
		// Depth buffer setup.
		gl.glClearDepthf(1.0f);
		// Enables depth testing.
		gl.glEnable(GL10.GL_DEPTH_TEST);
		// The type of depth testing to do.
		gl.glDepthFunc(GL10.GL_LEQUAL);
		// Really nice perspective calculations.
		gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_NICEST);
		
		
		//gl.glEnable(GL10.GL_POLYGON_SMOOTH_HINT);
		//gl.glHint(GL10.GL_POLYGON_SMOOTH_HINT, GL10.GL_NICEST);
		
		gl.glEnable(GL10.GL_BLEND);
		//gl.glBlendFunc(GL10.GL_SRC_ALPHA_SATURATE, GL10.GL_ONE);
		
		gl.glDisable(GL10.GL_CULL_FACE);

	}

	public void onSurfaceChanged(GL10 gl, int w, int h) {
		// gl.glViewport(0, 0, w, h);

		xt = w;
		yt = h;

		// Sets the current view port to the new size.
		gl.glViewport(0, 0, w, h);
		// Select the projection matrix
		gl.glMatrixMode(GL10.GL_PROJECTION);
		// Reset the projection matrix
		gl.glLoadIdentity();
		// Calculate the aspect ratio of the window
		GLU.gluPerspective(gl, 45.0f, (float) w / (float) h, 0.1f, 100.0f);
		// Select the modelview matrix
		gl.glMatrixMode(GL10.GL_MODELVIEW);
		// Reset the modelview matrix
		gl.glLoadIdentity();

	}

	double[][] planets = new double[9][6];
	

	String[] planetNames = new String[] { "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune" };
	
	/* A global for our current Julian Date, and a variable to change it */
	double julianDay, timeChange, moonAngle, IoAngle,
			EuropaAngle, GanymedeAngle, CallistoAngle, TitanAngle;

	/* Used for describing the viewport, used to be xt, yt, xm, ym */
	int xt, yt;
	double solMag, innerPlanetMag, outerPlanetMag;
	
	int mode;
	boolean pressed = false; 
	Date startDate;

	/* A little structure to hold our Gregorian Date */

	class Timestamp {
		public int year;
		public int month;
		public int day;
		public int hour;
		public int minute;
		public int second;
	};

	private float dot(float[] p, float[] q) {
		return ((p)[0] * (q)[0] + (p)[1] * (q)[1] + (p)[2] * (q)[2]);
	}

	private void defaults() {

		
		this.solMag = 2;
		this.innerPlanetMag = 32.0;
		this.outerPlanetMag = 12.0;
		
		this.timeChange = 0.5;
		this.moonAngle = 0.0;
		this.IoAngle = 25.0;
		this.EuropaAngle = 56.0;
		this.GanymedeAngle = 0.0;
		this.CallistoAngle = 184.0;
		this.TitanAngle = 0.0;
		this.pause = false;

		this.startDate = new Date();

		this.touchPinchDelta = 0;
		this.totalScrollX = 0;
		this.totalScrollY = 0;

		this.julianDay = this.convertGregToJDN(startDate);
		
		this.orreryViewInterface.updateSpeedChange(getSpeed());
	}

	private void dataprep(GL10 gl) {
		defaults();

		/* Initialize the Sun's Characteristics */

		planets[8][0] = 0.0464913;
		planets[8][1] = planets[8][2] = planets[8][3] = planets[8][4] = planets[8][5] = 0.0;

		initPlanets();

	}

	int pickedPlanetNum = -1;
	
	public void onDrawFrame(GL10 gl) {
		gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
		gl.glLoadIdentity();

		orreryViewInterface.updateDate(this.startDate);

		//run object picking on current position (before recalc)
		//if (pressed)
		/*{
			//object picking algorithm
			drawall(gl, true);
		    ByteBuffer pixels = ByteBuffer.allocate(4);
			gl.glReadPixels((int)start.x, yt - (int)start.y, 1, 1, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, pixels);
			
			if (pixels.get(1) != 0)
			{
				pickedPlanetNum = Math.abs(-255 / pixels.get(1)) - 1;
				if (pickedPlanetNum >= 0 && pickedPlanetNum < 8)
				{
					this.orreryViewInterface.updateObjectPicked(planetNames[pickedPlanetNum]);
				}
				else
				{
					pickedPlanetNum = -1;
				}
			}
			gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);	
			gl.glLoadIdentity();
			//pressed = false;
		}
		*/

		int p;
		for (p = 0; p < 8; p++) {
			compute_Position(julianDay, p + 1, 0);
		}
		
		drawall(gl, false);

		if (!pause) {
			julianDay += timeChange;
		}

		updateGregorian();
		
	}

	private void drawall(GL10 gl, boolean picking) {
		
		//Canvas c = this.orreryViewInterface.getSurfaceHolder().lockCanvas();
		
		//TODO: testing dithering, performace issue??
		if (!picking)
		{
			gl.glEnable(GL10.GL_DITHER);
		}
		
		gl.glPushMatrix();
		// pull back to see the scene
		// Exponential zoom?
		gl.glTranslatef(0, 0, - 2.5f * (float) Math.exp(touchPinchDelta / 25000.0f));

		gl.glPushMatrix();

		
		
		gl.glRotatef(this.totalScrollX / 200, 0, 1, 0);
		gl.glRotatef(this.totalScrollY / 200, 1, 0, 0);

		gl.glRotatef(-90.0f, 1.0f, 0.0f, 0.0f);
		
		gl.glPushMatrix();

		drawSol(gl);
		for (int p = 0; p < 8; p++) {
			drawPlanet(gl, p, picking);
		}
		gl.glPopMatrix();
		gl.glPopMatrix();
		gl.glPopMatrix();
		
		gl.glDisable(GL10.GL_DITHER);
		/*
		//draw title
		gl.glPushMatrix();
		gl.glTranslatef(0, 0, -5.0f);
		//gl.glRotatef(90.0f, 1.0f, 0.0f, 0.0f);
		gl.glPushMatrix();
		//square2.draw(gl, textures[9]);
		gl.glPopMatrix();
		gl.glPopMatrix();
		*/
		
		//this.orreryViewInterface.getSurfaceHolder().unlockCanvasAndPost(c);
	}

	private void drawPlanet(GL10 gl, int planetNum, boolean picking) {
		
		float radius_au, helioCX, helioCY, helioCZ;
		radius_au = (float) planets[planetNum][0];
		helioCX = (float) planets[planetNum][3];
		helioCY = (float) planets[planetNum][4];
		helioCZ = (float) planets[planetNum][5];

		
		gl.glPushMatrix();
		gl.glTranslatef(helioCX, helioCY, helioCZ);

		if (picking)
		{
			gl.glColor4f(1.0f, 1.0f / (planetNum + 1), 1.0f / (planetNum + 1), 1.0f);
		}
		
		double planetMag = 0;
		if (planetNum < 6)
		{
			planetMag = innerPlanetMag;
		}
		else
		{
			planetMag = outerPlanetMag;
		}
		
		gl.glScalef((float) planetMag * radius_au, (float) planetMag
				* radius_au, (float) planetMag * radius_au);

		sphere2.draw(gl, !picking, this.textures[planetNum]);

		/*
		 * if(planetNum == 5) { drawSaturnRings(); drawSaturnMoons(); }else
		 * if(planetNum == 4) drawJupiterMoons(); else if(planetNum == 2)
		 * drawMoon();
		 */
		gl.glPopMatrix();
		gl.glDisable(GL10.GL_TEXTURE_2D);
	}

	private void drawSol(GL10 gl) {
		float radius_au, helioCX, helioCY, helioCZ;
		// GLubyte tex;
		radius_au = (float) planets[8][0];
		helioCX = (float) planets[8][3];
		helioCY = (float) planets[8][4];
		helioCZ = (float) planets[8][5];

		if (solMag < 1)
			solMag = 1.0;

		gl.glPushMatrix();
		// gl.glEnable(GL_TEXTURE_2D);
		gl.glTranslatef(helioCX, helioCY, helioCZ);

		gl.glColor4f(1.0f, 1.0f, 0.0f, 1.0f);

		gl.glScalef((float) solMag * radius_au, (float) solMag * radius_au,
				(float) solMag * radius_au);

		// square2.draw(gl, this.textures[0] );
		sphere2.draw(gl, true, this.textures[8]);
		// sphere.DisplaySphere(gl, this.textures[0]);

		gl.glPopMatrix();

	}

	void initPlanets() {
		/*
		 * Initialize the radius of all the planets(The Sun was already
		 * initialized
		 */
		planets[0][0] = OrreryData.MERCURY_RADIUS;
		planets[1][0] = OrreryData.VENUS_RADIUS;
		planets[2][0] = OrreryData.EARTH_RADIUS;
		planets[3][0] = OrreryData.MARS_RADIUS;
		planets[4][0] = OrreryData.JUPITER_RADIUS;
		planets[5][0] = OrreryData.SATURN_RADIUS;
		planets[6][0] = OrreryData.URANUS_RADIUS;
		planets[7][0] = OrreryData.NEPTUNE_RADIUS;
	}

	private void compute_Position(double julianDate, int planet, int orbit) {
		int era = getDate(this.startDate);

		if (era == 1)
			compute_Position_AD(julianDate, planet, orbit);
		else if (era == 2)
			compute_Position_BCandAD(julianDate, planet, orbit);
	}

	private void compute_Position_AD(double julianDate, int planet, int orbit) {

		/*
		 * Yeah, a lot of temporary variables to hold ontothings while we
		 * compute the planetary positions
		 */
		double sm_axis, sm_error, eccentricity, inclination, meanLong, longPeri, longAscNode, relativeDate, argPeri, meanAnom, eccDegrees, oldEccAnom, dEccAnom, newEccAnom, dMeanAnom, absDAnom;
		double ecc_error, inc_error, mLong_error, lPeri_error, lAscNode_error;
		double helioCX, helioCY, helioCZ;

		sm_axis = sm_error = eccentricity = inclination = meanLong = 0.0;
		longPeri = longAscNode = relativeDate = dEccAnom = newEccAnom = 0.0;
		dMeanAnom = absDAnom = ecc_error = inc_error = mLong_error = 0.0;
		lPeri_error = lAscNode_error = helioCX = helioCY = helioCZ = 0.0;

		/*
		 * This switch statement simply retrieves the Keplerianelements that we
		 * are going to be computing with. Nothinginteresting here.
		 */

		switch (planet) {
		case 1:
			sm_axis = OrreryData.MERCURY_SM_AXIS_A;
			sm_error = OrreryData.MERCURY_SM_ERROR_A;
			eccentricity = OrreryData.MERCURY_ECC_A;
			ecc_error = OrreryData.MERCURY_ECC_ERROR_A;
			inclination = OrreryData.MERCURY_INC_A;
			inc_error = OrreryData.MERCURY_INC_ERROR_A;
			meanLong = OrreryData.MERCURY_MLONG_A;
			mLong_error = OrreryData.MERCURY_MLONG_ERROR_A;
			longPeri = OrreryData.MERCURY_PERI_A;
			lPeri_error = OrreryData.MERCURY_PERI_ERROR_A;
			longAscNode = OrreryData.MERCURY_ASC_A;
			lAscNode_error = OrreryData.MERCURY_ASC_ERROR_A;
			break;
		case 2:
			sm_axis = OrreryData.VENUS_SM_AXIS_A;
			sm_error = OrreryData.VENUS_SM_ERROR_A;
			eccentricity = OrreryData.VENUS_ECC_A;
			ecc_error = OrreryData.VENUS_ECC_ERROR_A;
			inclination = OrreryData.VENUS_INC_A;
			inc_error = OrreryData.VENUS_INC_ERROR_A;
			meanLong = OrreryData.VENUS_MLONG_A;
			mLong_error = OrreryData.VENUS_MLONG_ERROR_A;
			longPeri = OrreryData.VENUS_PERI_A;
			lPeri_error = OrreryData.VENUS_PERI_ERROR_A;
			longAscNode = OrreryData.VENUS_ASC_A;
			lAscNode_error = OrreryData.VENUS_ASC_ERROR_A;
			break;
		case 3:
			sm_axis = OrreryData.EARTH_SM_AXIS_A;
			sm_error = OrreryData.EARTH_SM_ERROR_A;
			eccentricity = OrreryData.EARTH_ECC_A;
			ecc_error = OrreryData.EARTH_ECC_ERROR_A;
			inclination = OrreryData.EARTH_INC_A;
			inc_error = OrreryData.EARTH_INC_ERROR_A;
			meanLong = OrreryData.EARTH_MLONG_A;
			mLong_error = OrreryData.EARTH_MLONG_ERROR_A;
			longPeri = OrreryData.EARTH_PERI_A;
			lPeri_error = OrreryData.EARTH_PERI_ERROR_A;
			longAscNode = OrreryData.EARTH_ASC_A;
			lAscNode_error = OrreryData.EARTH_ASC_ERROR_A;
			break;
		case 4:
			sm_axis = OrreryData.MARS_SM_AXIS_A;
			sm_error = OrreryData.MARS_SM_ERROR_A;
			eccentricity = OrreryData.MARS_ECC_A;
			ecc_error = OrreryData.MARS_ECC_ERROR_A;
			inclination = OrreryData.MARS_INC_A;
			inc_error = OrreryData.MARS_INC_ERROR_A;
			meanLong = OrreryData.MARS_MLONG_A;
			mLong_error = OrreryData.MARS_MLONG_ERROR_A;
			longPeri = OrreryData.MARS_PERI_A;
			lPeri_error = OrreryData.MARS_PERI_ERROR_A;
			longAscNode = OrreryData.MARS_ASC_A;
			lAscNode_error = OrreryData.MARS_ASC_ERROR_A;
			break;
		case 5:
			sm_axis = OrreryData.JUPITER_SM_AXIS_A;
			sm_error = OrreryData.JUPITER_SM_ERROR_A;
			eccentricity = OrreryData.JUPITER_ECC_A;
			ecc_error = OrreryData.JUPITER_ECC_ERROR_A;
			inclination = OrreryData.JUPITER_INC_A;
			inc_error = OrreryData.JUPITER_INC_ERROR_A;
			meanLong = OrreryData.JUPITER_MLONG_A;
			mLong_error = OrreryData.JUPITER_MLONG_ERROR_A;
			longPeri = OrreryData.JUPITER_PERI_A;
			lPeri_error = OrreryData.JUPITER_PERI_ERROR_A;
			longAscNode = OrreryData.JUPITER_ASC_A;
			lAscNode_error = OrreryData.JUPITER_ASC_ERROR_A;
			break;
		case 6:
			sm_axis = OrreryData.SATURN_SM_AXIS_A;
			sm_error = OrreryData.SATURN_SM_ERROR_A;
			eccentricity = OrreryData.SATURN_ECC_A;
			ecc_error = OrreryData.SATURN_ECC_ERROR_A;
			inclination = OrreryData.SATURN_INC_A;
			inc_error = OrreryData.SATURN_INC_ERROR_A;
			mLong_error = OrreryData.SATURN_MLONG_ERROR_A;
			meanLong = OrreryData.SATURN_MLONG_A;
			longPeri = OrreryData.SATURN_PERI_A;
			lPeri_error = OrreryData.SATURN_PERI_ERROR_A;
			longAscNode = OrreryData.SATURN_ASC_A;
			lAscNode_error = OrreryData.SATURN_ASC_ERROR_A;
			break;
		case 7:
			sm_axis = OrreryData.URANUS_SM_AXIS_A;
			sm_error = OrreryData.URANUS_SM_ERROR_A;
			eccentricity = OrreryData.URANUS_ECC_A;
			ecc_error = OrreryData.URANUS_ECC_ERROR_A;
			inclination = OrreryData.URANUS_INC_A;
			inc_error = OrreryData.URANUS_INC_ERROR_A;
			meanLong = OrreryData.URANUS_MLONG_A;
			mLong_error = OrreryData.URANUS_MLONG_ERROR_A;
			longPeri = OrreryData.URANUS_PERI_A;
			lPeri_error = OrreryData.URANUS_PERI_ERROR_A;
			longAscNode = OrreryData.URANUS_ASC_A;
			lAscNode_error = OrreryData.URANUS_ASC_ERROR_A;
			break;
		case 8:
			sm_axis = OrreryData.NEPTUNE_SM_AXIS_A;
			sm_error = OrreryData.NEPTUNE_SM_ERROR_A;
			eccentricity = OrreryData.NEPTUNE_ECC_A;
			ecc_error = OrreryData.NEPTUNE_ECC_ERROR_A;
			inclination = OrreryData.NEPTUNE_INC_A;
			inc_error = OrreryData.NEPTUNE_INC_ERROR_A;
			meanLong = OrreryData.NEPTUNE_MLONG_A;
			mLong_error = OrreryData.NEPTUNE_MLONG_ERROR_A;
			longPeri = OrreryData.NEPTUNE_PERI_A;
			lPeri_error = OrreryData.NEPTUNE_PERI_ERROR_A;
			longAscNode = OrreryData.NEPTUNE_ASC_A;
			lAscNode_error = OrreryData.NEPTUNE_ASC_ERROR_A;
			break;
		}

		/* Okay, now we have our elements, let's start the computing. */
		/*
		 * First we need to find the elements at this point in time, so we
		 * offset according to the date
		 */
		relativeDate = (julianDate - 2451545.0) / 36525.0;
		sm_axis = sm_axis + (sm_error * relativeDate);
		eccentricity = eccentricity + (ecc_error * relativeDate);
		inclination = inclination + (inc_error * relativeDate);
		meanLong = meanLong + (mLong_error * relativeDate);
		longPeri = longPeri + (lPeri_error * relativeDate);
		longAscNode = longAscNode + (lAscNode_error * relativeDate);

		argPeri = longPeri - longAscNode;
		meanAnom = meanLong - longPeri;
		/*
		 * #ifdef DEBUG cout << "Current Mean Anomaly: "; cout << meanAnom <<
		 * endl; #endif
		 */
		double half = 360.0; // Was 180, but only half of the orbit drew
		int temp;
		temp = (int) (meanAnom / half);
		meanAnom = meanAnom - ((float) (temp)) * half;

		/* This part was added to make it go from -180 <= M <= 180 */
		if (meanAnom > 180)
			meanAnom -= 360;

		eccDegrees = eccentricity * (180 / Math.PI);
		newEccAnom = meanAnom + eccDegrees
				* (Math.sin(Math.PI / 180 * meanAnom));
		do {
			/*
			 * #ifdef DEBUG cout << "Attempting to get more accurate" << endl;
			 * #endif
			 */
			oldEccAnom = newEccAnom;
			dMeanAnom = meanAnom
					- (oldEccAnom - eccDegrees
							* (Math.sin(Math.PI / 180 * oldEccAnom)));
			dEccAnom = dMeanAnom
					/ (1 - eccentricity * Math.cos(Math.PI / 180 * oldEccAnom));
			// dEccAnom = dMeanAnom/(1-eccDegrees*C(oldEccAnom));
			newEccAnom = oldEccAnom + dEccAnom;
			if (dEccAnom < 0)
				absDAnom = -1 * dEccAnom;
			else
				absDAnom = dEccAnom;
		} while (absDAnom > (Math.pow(10.0, -6.0)));
		/*
		 * #ifdef DEBUG cout << "Returned from Mean Anomaly Calculation..." <<
		 * endl; #endif
		 */
		/* We now have our Eccentric Anomaly, and we can get the coordinates */

		if (orbit == 0) {
			planets[planet - 1][1] = sm_axis
					* (Math.cos((Math.PI / 180) * newEccAnom) - eccentricity);
			planets[planet - 1][2] = sm_axis
					* (Math.sqrt(1 - (eccentricity) * (eccentricity)))
					* Math.sin(newEccAnom * Math.PI / 180);
		}

		helioCX = (Math.cos(Math.PI / 180 * argPeri)
				* Math.cos(Math.PI / 180 * longAscNode) - Math.sin(Math.PI
				* argPeri)
				* Math.sin(Math.PI * longAscNode)
				* Math.cos(Math.PI / 180 * inclination))
				* (planets[planet - 1][1]);
		helioCX = helioCX
				- (Math.sin(Math.PI * argPeri)
						* Math.cos(Math.PI / 180 * longAscNode) + Math
						.cos(Math.PI / 180 * argPeri)
						* Math.sin(Math.PI * longAscNode)
						* Math.cos(Math.PI / 180 * inclination))
				* (planets[planet - 1][2]);

		helioCY = (Math.cos(Math.PI / 180 * argPeri)
				* Math.sin(Math.PI * longAscNode) + Math.sin(Math.PI * argPeri)
				* Math.cos(Math.PI / 180 * longAscNode)
				* Math.cos(Math.PI / 180 * inclination))
				* (planets[planet - 1][1]);
		helioCY = helioCY
				- (Math.sin(Math.PI * argPeri)
						* Math.sin(Math.PI * longAscNode) - Math.cos(Math.PI
						/ 180 * argPeri)
						* Math.cos(Math.PI / 180 * longAscNode)
						* Math.cos(Math.PI / 180 * inclination))
				* (planets[planet - 1][2]);

		helioCZ = (Math.sin(Math.PI * argPeri) * Math
				.sin(Math.PI * inclination))
				* (planets[planet - 1][1])
				+ (Math.cos(Math.PI / 180 * argPeri) * Math.sin(Math.PI
						* inclination)) * (planets[planet - 1][2]);

		if (orbit == 0) {
			planets[planet - 1][3] = helioCX;
			planets[planet - 1][4] = helioCY;
			planets[planet - 1][5] = helioCZ;
		} else {
			/*
			 * planetsOrbit[planet-1][0] = helioCX; planetsOrbit[planet-1][1] =
			 * helioCY; planetsOrbit[planet-1][2] = helioCZ;
			 */
		}
	}

	private void compute_Position_BCandAD(double julianDate, int planet,
			int orbit) {

		double sm_axis, sm_error, eccentricity, inclination, meanLong, longPeri, longAscNode, relativeDate, argPeri, meanAnom, eccDegrees, oldEccAnom, dEccAnom, newEccAnom, dMeanAnom, b = 0, c = 0, s = 0, f = 0, temp, absDAnom;
		double ecc_error, inc_error, mLong_error, lPeri_error, lAscNode_error;
		double helioCX, helioCY, helioCZ;

		sm_axis = sm_error = eccentricity = inclination = meanLong = 0.0;
		longPeri = longAscNode = relativeDate = dEccAnom = newEccAnom = 0.0;
		dMeanAnom = absDAnom = ecc_error = inc_error = mLong_error = 0.0;
		lPeri_error = lAscNode_error = helioCX = helioCY = helioCZ = 0.0;

		/*
		 * This switch statement simply retrieves the Keplerianelements that we
		 * are going to be computing with. Nothinginteresting here.
		 */

		switch (planet) {
		case 1:
			sm_axis = OrreryData.MERCURY_SM_AXIS_B;
			sm_error = OrreryData.MERCURY_SM_ERROR_B;
			eccentricity = OrreryData.MERCURY_ECC_B;
			ecc_error = OrreryData.MERCURY_ECC_ERROR_B;
			inclination = OrreryData.MERCURY_INC_B;
			inc_error = OrreryData.MERCURY_INC_ERROR_B;
			meanLong = OrreryData.MERCURY_MLONG_B;
			mLong_error = OrreryData.MERCURY_MLONG_ERROR_B;
			longPeri = OrreryData.MERCURY_PERI_B;
			lPeri_error = OrreryData.MERCURY_PERI_ERROR_B;
			longAscNode = OrreryData.MERCURY_ASC_B;
			lAscNode_error = OrreryData.MERCURY_ASC_ERROR_B;
			break;
		case 2:
			sm_axis = OrreryData.VENUS_SM_AXIS_B;
			sm_error = OrreryData.VENUS_SM_ERROR_B;
			eccentricity = OrreryData.VENUS_ECC_B;
			ecc_error = OrreryData.VENUS_ECC_ERROR_B;
			inclination = OrreryData.VENUS_INC_B;
			inc_error = OrreryData.VENUS_INC_ERROR_B;
			meanLong = OrreryData.VENUS_MLONG_B;
			mLong_error = OrreryData.VENUS_MLONG_ERROR_B;
			longPeri = OrreryData.VENUS_PERI_B;
			lPeri_error = OrreryData.VENUS_PERI_ERROR_B;
			longAscNode = OrreryData.VENUS_ASC_B;
			lAscNode_error = OrreryData.VENUS_ASC_ERROR_B;
			break;
		case 3:
			sm_axis = OrreryData.EARTH_SM_AXIS_B;
			sm_error = OrreryData.EARTH_SM_ERROR_B;
			eccentricity = OrreryData.EARTH_ECC_B;
			ecc_error = OrreryData.EARTH_ECC_ERROR_B;
			inclination = OrreryData.EARTH_INC_B;
			inc_error = OrreryData.EARTH_INC_ERROR_B;
			meanLong = OrreryData.EARTH_MLONG_B;
			mLong_error = OrreryData.EARTH_MLONG_ERROR_B;
			longPeri = OrreryData.EARTH_PERI_B;
			lPeri_error = OrreryData.EARTH_PERI_ERROR_B;
			longAscNode = OrreryData.EARTH_ASC_B;
			lAscNode_error = OrreryData.EARTH_ASC_ERROR_B;
			break;
		case 4:
			sm_axis = OrreryData.MARS_SM_AXIS_B;
			sm_error = OrreryData.MARS_SM_ERROR_B;
			eccentricity = OrreryData.MARS_ECC_B;
			ecc_error = OrreryData.MARS_ECC_ERROR_B;
			inclination = OrreryData.MARS_INC_B;
			inc_error = OrreryData.MARS_INC_ERROR_B;
			meanLong = OrreryData.MARS_MLONG_B;
			mLong_error = OrreryData.MARS_MLONG_ERROR_B;
			longPeri = OrreryData.MARS_PERI_B;
			lPeri_error = OrreryData.MARS_PERI_ERROR_B;
			longAscNode = OrreryData.MARS_ASC_B;
			lAscNode_error = OrreryData.MARS_ASC_ERROR_B;
			break;
		case 5:
			sm_axis = OrreryData.JUPITER_SM_AXIS_B;
			sm_error = OrreryData.JUPITER_SM_ERROR_B;
			eccentricity = OrreryData.JUPITER_ECC_B;
			ecc_error = OrreryData.JUPITER_ECC_ERROR_B;
			inclination = OrreryData.JUPITER_INC_B;
			inc_error = OrreryData.JUPITER_INC_ERROR_B;
			meanLong = OrreryData.JUPITER_MLONG_B;
			mLong_error = OrreryData.JUPITER_MLONG_ERROR_B;
			longPeri = OrreryData.JUPITER_PERI_B;
			lPeri_error = OrreryData.JUPITER_PERI_ERROR_B;
			longAscNode = OrreryData.JUPITER_ASC_B;
			lAscNode_error = OrreryData.JUPITER_ASC_ERROR_B;
			b = OrreryData.JUPITER_b;
			c = OrreryData.JUPITER_c;
			s = OrreryData.JUPITER_s;
			f = OrreryData.JUPITER_f;
			break;
		case 6:
			sm_axis = OrreryData.SATURN_SM_AXIS_B;
			sm_error = OrreryData.SATURN_SM_ERROR_B;
			eccentricity = OrreryData.SATURN_ECC_B;
			ecc_error = OrreryData.SATURN_ECC_ERROR_B;
			inclination = OrreryData.SATURN_INC_B;
			inc_error = OrreryData.SATURN_INC_ERROR_B;
			mLong_error = OrreryData.SATURN_MLONG_ERROR_B;
			meanLong = OrreryData.SATURN_MLONG_B;
			longPeri = OrreryData.SATURN_PERI_B;
			lPeri_error = OrreryData.SATURN_PERI_ERROR_B;
			longAscNode = OrreryData.SATURN_ASC_B;
			lAscNode_error = OrreryData.SATURN_ASC_ERROR_B;
			b = OrreryData.SATURN_b;
			c = OrreryData.SATURN_c;
			s = OrreryData.SATURN_s;
			f = OrreryData.SATURN_f;
			break;
		case 7:
			sm_axis = OrreryData.URANUS_SM_AXIS_B;
			sm_error = OrreryData.URANUS_SM_ERROR_B;
			eccentricity = OrreryData.URANUS_ECC_B;
			ecc_error = OrreryData.URANUS_ECC_ERROR_B;
			inclination = OrreryData.URANUS_INC_B;
			inc_error = OrreryData.URANUS_INC_ERROR_B;
			meanLong = OrreryData.URANUS_MLONG_B;
			mLong_error = OrreryData.URANUS_MLONG_ERROR_B;
			longPeri = OrreryData.URANUS_PERI_B;
			lPeri_error = OrreryData.URANUS_PERI_ERROR_B;
			longAscNode = OrreryData.URANUS_ASC_B;
			lAscNode_error = OrreryData.URANUS_ASC_ERROR_B;
			b = OrreryData.URANUS_b;
			c = OrreryData.URANUS_c;
			s = OrreryData.URANUS_s;
			f = OrreryData.URANUS_f;
			break;
		case 8:
			sm_axis = OrreryData.NEPTUNE_SM_AXIS_B;
			sm_error = OrreryData.NEPTUNE_SM_ERROR_B;
			eccentricity = OrreryData.NEPTUNE_ECC_B;
			ecc_error = OrreryData.NEPTUNE_ECC_ERROR_B;
			inclination = OrreryData.NEPTUNE_INC_B;
			inc_error = OrreryData.NEPTUNE_INC_ERROR_B;
			meanLong = OrreryData.NEPTUNE_MLONG_B;
			mLong_error = OrreryData.NEPTUNE_MLONG_ERROR_B;
			longPeri = OrreryData.NEPTUNE_PERI_B;
			lPeri_error = OrreryData.NEPTUNE_PERI_ERROR_B;
			longAscNode = OrreryData.NEPTUNE_ASC_B;
			lAscNode_error = OrreryData.NEPTUNE_ASC_ERROR_B;
			b = OrreryData.NEPTUNE_b;
			c = OrreryData.NEPTUNE_c;
			s = OrreryData.NEPTUNE_s;
			f = OrreryData.NEPTUNE_f;
			break;
		}

		/* Okay, now we have our elements, let's start the computing. */
		/* First we need to find the semimajor axis at this point in time */
		relativeDate = (julianDate - 2451545.0) / 36525.0;
		sm_axis = sm_axis + (sm_error * relativeDate);
		eccentricity = eccentricity + (ecc_error * relativeDate);
		inclination = inclination + (inc_error * relativeDate);
		meanLong = meanLong + (mLong_error * relativeDate);
		longPeri = longPeri + (lPeri_error * relativeDate);
		longAscNode = longAscNode + (lAscNode_error * relativeDate);

		argPeri = longPeri - longAscNode;
		if (planet >= 5) {
			temp = b * (relativeDate) * (relativeDate);
			temp = temp + c * Math.cos(relativeDate * f);
			temp = temp + s * Math.sin(relativeDate * f);
			meanAnom = meanLong - longPeri + temp;
		} else
			meanAnom = meanLong - longPeri;

		/*
		 * Everything that follows is the exact same as the function abovebut
		 * since it's only used twice, copy and past was easierthan writing a
		 * function and passing all the orbital parametersto it
		 */

		/*
		 * #ifdef DEBUG cout << "Current Mean Anomaly: "; cout << meanAnom <<
		 * endl; #endif
		 */
		double half = 360.0; // Was 180, but only half of the orbit drew
		int temp1;
		temp1 = (int) (meanAnom / half);
		meanAnom = meanAnom - ((float) (temp1)) * half;
		if (meanAnom > 180)
			meanAnom -= 360;

		eccDegrees = eccentricity * (180 / Math.PI);
		newEccAnom = meanAnom + eccDegrees
				* (Math.sin(Math.PI / 180 * meanAnom));
		do {
			/*
			 * #ifdef DEBUG cout << "Attempting to get more accurate" << endl;
			 * #endif
			 */
			oldEccAnom = newEccAnom;
			dMeanAnom = meanAnom
					- (oldEccAnom - eccDegrees
							* (Math.sin(Math.PI / 180 * oldEccAnom)));
			dEccAnom = dMeanAnom
					/ (1 - eccentricity * Math.cos(Math.PI / 180 * oldEccAnom));
			newEccAnom = oldEccAnom + dEccAnom;
			if (dEccAnom < 0)
				absDAnom = -1 * dEccAnom;
			else
				absDAnom = dEccAnom;
		} while (absDAnom > (Math.pow(10.0, -6.0)));

		/*
		 * #ifdef DEBUG cout << "Returned from Mean Anomaly Calculation..." <<
		 * endl; #endif
		 */

		/* We now have our Eccentric Anomaly, and we can get the coordinates */

		if (orbit == 0) {
			planets[planet - 1][1] = sm_axis
					* (Math.cos((Math.PI / 180) * newEccAnom) - eccentricity);
			planets[planet - 1][2] = sm_axis
					* (Math.sqrt(1 - (eccentricity) * (eccentricity)))
					* Math.sin((Math.PI / 180) * newEccAnom);
		}

		helioCX = (Math.cos(Math.PI / 180 * argPeri)
				* Math.cos(Math.PI / 180 * longAscNode) - Math.sin(Math.PI
				/ 180 * argPeri)
				* Math.sin(Math.PI / 180 * longAscNode)
				* Math.cos(Math.PI / 180 * inclination))
				* (planets[planet - 1][1]);
		helioCX = helioCX
				- (Math.sin(Math.PI / 180 * argPeri)
						* Math.cos(Math.PI / 180 * longAscNode) + Math
						.cos(Math.PI / 180 * argPeri)
						* Math.sin(Math.PI / 180 * longAscNode)
						* Math.cos(Math.PI / 180 * inclination))
				* (planets[planet - 1][2]);

		helioCY = (Math.cos(Math.PI / 180 * argPeri)
				* Math.sin(Math.PI / 180 * longAscNode) + Math.sin(Math.PI
				/ 180 * argPeri)
				* Math.cos(Math.PI / 180 * longAscNode)
				* Math.cos(Math.PI / 180 * inclination))
				* (planets[planet - 1][1]);
		helioCY = helioCY
				- (Math.sin(Math.PI / 180 * argPeri)
						* Math.sin(Math.PI / 180 * longAscNode) - Math
						.cos(Math.PI / 180 * argPeri)
						* Math.cos(Math.PI / 180 * longAscNode)
						* Math.cos(Math.PI / 180 * inclination))
				* (planets[planet - 1][2]);

		helioCZ = (Math.sin(Math.PI / 180 * argPeri) * Math.sin(Math.PI / 180
				* inclination))
				* (planets[planet - 1][1])
				+ (Math.cos(Math.PI / 180 * argPeri) * Math.sin(Math.PI / 180
						* inclination)) * (planets[planet - 1][2]);

		if (orbit == 0) {
			planets[planet - 1][3] = helioCX;
			planets[planet - 1][4] = helioCY;
			planets[planet - 1][5] = helioCZ;
		} else {
			/*
			 * planetsOrbit[planet-1][0] = helioCX; planetsOrbit[planet-1][1] =
			 * helioCY; planetsOrbit[planet-1][2] = helioCZ;
			 */
		}
	}

	private void updateGregorian() {
		int j, g, dg, c, dc, b, db, a, da, y, m, d, Y, M, D;
		j = (int) julianDay + 32044;
		g = (int) (j / 146097);
		dg = j % 146097;
		c = ((dg / 36524 + 1) * 3) / 4;
		dc = dg - c * 36524;
		b = dc / 1461;
		db = dc % 1461;
		a = ((db / 365 + 1) * 3) / 4;
		da = db - a * 365;
		y = g * 400 + c * 100 + b * 4 + a;
		m = (da * 5 + 308) / 153 - 2;
		d = da - (m + 4) * 153 / 5 + 122;
		Y = y - 4800 + (m + 2) / 12;
		M = ((m + 2) % 12) + 1;
		D = d + 1;

		startDate = new Date(Date.UTC(Y, M, D, 0, 0, 0));

	}

	private double convertGregToJDN(Date startDate) {
		double julDate;
		int a, y, m, myyear, mymonth, myday, myhour, myminute, mysecond;

		myyear = startDate.getYear();
		mymonth = startDate.getMonth();
		myday = startDate.getDate();
		myhour = startDate.getHours();
		myminute = startDate.getMinutes();
		mysecond = startDate.getSeconds();

		a = (14 - mymonth) / 12; // Note the truncation to type int
		y = myyear + 4800 - a;
		m = mymonth + 12 * a - 3;

		// Again, truncation will be used in the next line, since
		// we are only dealing with integer days

		julDate = myday + (153 * m + 2) / 5 + 365 * y + y / 4 - y / 100;
		julDate = julDate + y / 400 - 32045;

		// Now, since we want the fraction of the day, we cast
		// our integers to type double
		julDate = julDate + ((double) myhour - 12.0) / 24.0;
		julDate = julDate + ((double) myminute) / 1440.0;
		julDate = julDate + ((double) mysecond) / 86400.0;
		return julDate;
	}

	int getDate(Date startDate) {

		if ((startDate.getYear() >= 1850) && (startDate.getYear() <= 2050))
			return 1;

		if ((startDate.getYear() >= -2999) && (startDate.getYear() <= 3000))
			return 2;

		return 0; // This is a flag that the date given is not a valid
					// start date for our equations
	}

	public boolean onTouch(View v, MotionEvent event) {

		// Handle touch events here...
		switch (event.getAction() & MotionEvent.ACTION_MASK) {

		case MotionEvent.ACTION_DOWN:
			start.set(event.getX(), event.getY());
			Log.d(TAG, "mode=DRAG");
			mode = DRAG;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			oldDist = spacing(event);
			Log.d(TAG, "oldDist=" + oldDist);
			if (oldDist > 10f) {
				midPoint(mid, event);
				mode = ZOOM;
				Log.d(TAG, "mode=ZOOM");
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_POINTER_UP:
			if (mode != NONE)
			{
				pressed = true;
			}
			mode = NONE;
			Log.d(TAG, "mode=NONE");
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == DRAG) {
				// do Drag --- i.e. ROTATE
				totalScrollX += event.getX() - start.x;
				totalScrollY += event.getY() - start.y;
			} else if (mode == ZOOM) {
				float newDist = spacing(event);
				Log.d(TAG, "newDist=" + newDist);
				if (newDist > 10f) {
					touchPinchDelta += oldDist - newDist;
				}
			}
			//mode = NONE;
			break;
		}

		return true;
	}
	
	

	/** Determine the space between the first two fingers */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/** Calculate the mid point of the first two fingers */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}

}
