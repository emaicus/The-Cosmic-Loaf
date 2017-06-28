package com.skidgame.cosmicloaf;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import org.newdawn.slick.Graphics;

import com.skidgame.cosmicloaf.game.TheCosmicLoafGame;

import static org.lwjgl.opengl.GL11.*;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class CosmicMain 
{
	public static int width;
	public static int height;
	private static TheCosmicLoafGame game;
	public static Graphics myGraphics;
	
	public static boolean VSynch;
	
	private static void initGL() 
	{
		glEnable(GL_BLEND); 
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glEnable(GL11.GL_TEXTURE_2D);
		glClearColor(0,0,0,0); //sets the null color to black.

		glMatrixMode(GL_PROJECTION); //sets it up to use a projection matrix.
		GL11.glLoadIdentity();

		glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1,  1); //Left Right Bottom Top Near Far
		glMatrixMode(GL_MODELVIEW);
		
//		 GL11.glViewport(0,0,width,height);
//	        GL11.glMatrixMode(GL11.GL_PROJECTION);
//	        GL11.glLoadIdentity();
//	        GLU.gluPerspective(45.0f, ((float)width/(float)height),0.1f,100.0f);
//	        GL11.glMatrixMode(GL11.GL_MODELVIEW);
//	        GL11.glLoadIdentity();
//	        GL11.glShadeModel(GL11.GL_SMOOTH);
//	        GL11.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
//	        GL11.glClearDepth(1.0f);
//	        GL11.glEnable(GL11.GL_DEPTH_TEST);
//	        GL11.glDepthFunc(GL11.GL_LEQUAL);
//	        GL11.glHint(GL11.GL_PERSPECTIVE_CORRECTION_HINT,
//	        GL11.GL_NICEST);
		
}
	
	private static void initDisplay() 
	{
		try 
		{
			System.out.println(width + " " + height);
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.create();
			Keyboard.create();
			Display.setVSyncEnabled(VSynch); //allows v sync
		} 
		catch (LWJGLException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void init()
	{
		initDisplay();
		initGL();
		myGraphics = new Graphics(width, height);
		Graphics.setCurrent(myGraphics);

			
	}
	
	
	private static void gameLoop() {
		while(!Display.isCloseRequested())
		{
			getInput();
			update();
			render();
		}
	}

	private static void render() {
		glClear(GL_COLOR_BUFFER_BIT);
		glLoadIdentity();
		game.render();
		Display.update();
		if(VSynch)
		{
			Display.sync(60); //locks at 60fps.
		}
	}

	private static void update() {
		game.update();
	}

	private static void cleanUp() {
		Display.destroy();
		Keyboard.destroy();
	}
	
	private static void getInput() {
		game.getInput();
	}
	
	private static void run() {
		gameLoop();		
	}
	
	private static void linuxLoader(){
		String osArch = System.getProperty("os.arch");
		boolean is64bit = "amd64".equals(osArch) || "x86_64".equals(osArch);
		 
		java.awt.Toolkit.getDefaultToolkit(); // loads libmawt.so (needed by jawt)
		
		String javaHome = System.getProperty("java.home");
		System.out.println(javaHome);
		
		if (is64bit) System.load(javaHome + "/lib/amd64/libjawt.so");
		else System.load(javaHome + "/lib/i386/libjawt.so");
	}

	public static void main(String...args)
	{
		//linuxLoader();
		VSynch = true;
		boolean HD = false;
		if(!HD)
		{
			height = 480;
			width = 720;
		}
		else
		{
			height = 1080;
			width = 1920;
		}
		
		
		
		init();
		setUpShaders();
		game = new TheCosmicLoafGame();
		run();
		cleanUp();
		
	}
	
	/*
	    * if the shaders are setup ok we can use shaders, otherwise we just
	    * use default settings
	    */
	    private static boolean useShader;
	     
	    /*
	    * program shader, to which is attached a vertex and fragment shaders.
	    * They are set to 0 as a check because GL will assign unique int
	    * values to each
	    */
	    public static int program=0;
	 
	    public static void setUpShaders(){
	    	System.out.println("In here");
	        int vertShader = 0, fragShader = 0;
	         
	        try {
	            vertShader = createShader("screen.vert", ARBVertexShader.GL_VERTEX_SHADER_ARB);
	            fragShader = createShader("screen.frag", ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
	            System.out.println("WE MADE IT!");
	        }
	        catch(Exception exc) {
	            System.out.println("Failed!");
	            exc.printStackTrace();
	            return;
	        }
	        finally {
	            if(vertShader == 0 || fragShader == 0)
	                return;
	        }
	         
	        program = ARBShaderObjects.glCreateProgramObjectARB();
	         
	        if(program == 0)
	            return;
	         
	        /*
	        * if the vertex and fragment shaders setup sucessfully,
	        * attach them to the shader program, link the sahder program
	        * (into the GL context I suppose), and validate
	        */
	        ARBShaderObjects.glAttachObjectARB(program, vertShader);
	        ARBShaderObjects.glAttachObjectARB(program, fragShader);
	         
	        ARBShaderObjects.glLinkProgramARB(program);
	        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL11.GL_FALSE) {
	            System.err.println(getLogInfo(program));
	            return;
	        }
	         
	        ARBShaderObjects.glValidateProgramARB(program);
	        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL11.GL_FALSE) {
	            System.err.println(getLogInfo(program));
	            return;
	        }
	         
	        useShader = true;
	    }
	     
	    /*
	    * If the shader was setup succesfully, we use the shader. Otherwise
	    * we run normal drawing code.
	    */
	    public void draw(){
	        if(useShader)
	            ARBShaderObjects.glUseProgramObjectARB(program);
	         
	        GL11.glLoadIdentity();
	        GL11.glTranslatef(0.0f, 0.0f, -10.0f);
	        GL11.glColor3f(1.0f, 1.0f, 1.0f);//white
	 
	        GL11.glBegin(GL11.GL_QUADS);
	        GL11.glVertex3f(-1.0f, 1.0f, 0.0f);
	        GL11.glVertex3f(1.0f, 1.0f, 0.0f);
	        GL11.glVertex3f(1.0f, -1.0f, 0.0f);
	        GL11.glVertex3f(-1.0f, -1.0f, 0.0f);
	        GL11.glEnd();
	         
	        //release the shader
	        if(useShader)
	            ARBShaderObjects.glUseProgramObjectARB(0);
	 
	    }
	     
	    /*
	    * With the exception of syntax, setting up vertex and fragment shaders
	    * is the same.
	    * @param the name and path to the vertex shader
	    */
	    private static int createShader(String filename, int shaderType) throws Exception {
	    	System.out.println("In box.");
	        int shader = 0;
	        try {
	        	System.out.println("1");
	            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
	        	System.out.println("2");

	            if(shader == 0)
	                return 0;
	        	System.out.println("3");

	            ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
	        	System.out.println("4");
	            ARBShaderObjects.glCompileShaderARB(shader);
	        	System.out.println("5");

	            if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL11.GL_FALSE)
	                throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
	        	System.out.println("6");

	            return shader;
	        }
	        catch(Exception exc) {
	            ARBShaderObjects.glDeleteObjectARB(shader);
	            throw exc;
	        }
	    }
	     
	    private static String getLogInfo(int obj) {
	        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
	    }
	     
	    private static String readFileAsString(String filename) throws Exception {
	        StringBuilder source = new StringBuilder();
	         
	        FileInputStream in = new FileInputStream(filename);
	         
	        Exception exception = null;
	         
	        BufferedReader reader;
	        try{
	            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
	             
	            Exception innerExc= null;
	            try {
	                String line;
	                while((line = reader.readLine()) != null)
	                    source.append(line).append('\n');
	            }
	            catch(Exception exc) {
	                exception = exc;
	            }
	            finally {
	                try {
	                    reader.close();
	                }
	                catch(Exception exc) {
	                    if(innerExc == null)
	                        innerExc = exc;
	                    else
	                        exc.printStackTrace();
	                }
	            }
	             
	            if(innerExc != null)
	                throw innerExc;
	        }
	        catch(Exception exc) {
	            exception = exc;
	        }
	        finally {
	            try {
	                in.close();
	            }
	            catch(Exception exc) {
	                if(exception == null)
	                    exception = exc;
	                else
	                    exc.printStackTrace();
	            }
	             
	            if(exception != null)
	                throw exception;
	        }
	         
	        return source.toString();
	    }


}