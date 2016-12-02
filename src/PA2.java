/**
 * PA2.java - driver for the spider model simulation
 * 
 * History:
 * 
 * 18 October 2016
 * 
 * - modified to spider model instead of hand model.
 * 
 * (Omayr Abdelgany <omaaa@bu.edu>)
 * 
 * 19 February 2011
 * 
 * - added documentation
 * 
 * (Jeffrey Finkelstein <jeffrey.finkelstein@gmail.com>)
 * 
 * 16 January 2008
 * 
 * - translated from C code by Stan Sclaroff
 * 
 * (Tai-Peng Tian <tiantp@gmail.com>)
 * 
 */


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.awt.GLCanvas;//for new version of gl
import javax.media.opengl.GLCapabilities;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.glu.GLU;
import javax.swing.JFrame;

import com.jogamp.opengl.util.FPSAnimator;//for new version of gl
import com.jogamp.opengl.util.gl2.GLUT;//for new version of gl

/**
 * The main class which drives the spider model simulation.
 * @author Omayr Abdelgany 
 * 		   omaaa@bu.edu
 *         U54298732
 *         Fall 2016
 *         
 * Modified template given online.
 */
public class PA2 extends JFrame implements GLEventListener, KeyListener,
    MouseListener, MouseMotionListener {

  /**
   * A leg which has a rotatorJoint, a bodyJoint, a middleJoint, and a distalJoint.
   */
	
	private class Leg {
		private final Component rotatorJoint;
		private final Component distalJoint;
		private final Component middleJoint;
		private final Component bodyJoint;
		private final List<Component> joints;
		
	    public Leg(final Component rotatorJoint, final Component bodyJoint, final Component middleJoint, final Component distalJoint){
	    	this.rotatorJoint = rotatorJoint;
	    	this.bodyJoint = bodyJoint;
	    	this.middleJoint = middleJoint;
	    	this.distalJoint = distalJoint;
	    	this.joints = Collections.unmodifiableList(Arrays.asList(this.distalJoint, this.middleJoint, this.bodyJoint));
	    }
	    
	    
	    Component distalJoint() {
	    	return this.distalJoint;
	    }
	    
	    Component middleJoint() {
	    	return this.middleJoint;
	    }
	    
	    Component bodyJoint() {
	    	return this.bodyJoint;
	    }
	    
	    Component rotatorJoint() {
	    	return this.rotatorJoint;
	    }
	    
	    List<Component> joints() {
	      return this.joints;
	    }
	    
  }		


  /** The color for components which are selected for rotation. */
  public static final FloatColor ACTIVE_COLOR = FloatColor.RED;
  /** The default width of the created window. */
  public static final int DEFAULT_WINDOW_HEIGHT = 512;
  /** The default height of the created window. */
  public static final int DEFAULT_WINDOW_WIDTH = 512;
  /** The height of the distal joint on each of the legs. */
  public static final double DISTAL_JOINT_HEIGHT = 0.4;
  /** The height of the middle joint on each of the legs. */
  public static final double MIDDLE_JOINT_HEIGHT = 0.1;
  /** The height of the body joint on each of the legs. */
  public static final double BODY_JOINT_HEIGHT = 0.5;
  /** The height of the head. */
  public static final double HEAD_HEIGHT = 0.57;
  /** The height of the body. */
  public static final double BODY_HEIGHT = 1.3;
  /** The height of the distal joint on each pincer. */
  public static final double PINCER_DISTAL_JOINT_HEIGHT = 0.25;
  /** The height of the middle joint on each pincer. */
  public static final double PINCER_MIDDLE_JOINT_HEIGHT = 0.05;
  /** The height of the body joint on each pincer. */
  public static final double PINCER_BODY_JOINT_HEIGHT = 0.3;
  /** The radius of the pincer. */
  public static final double PINCER_LEG_RADIUS = 0.05;
  /** The radius of each joint which comprises the leg. */
  public static final double BODY_LEG_RADIUS = 0.06;
  public static final double MIDDLE_LEG_RADIUS = 0.06;
  public static final double DISTAL_LEG_RADIUS = 0.06;
  public static final double ROTATOR_RADIUS = 0.07;
  /** The radius of the head. */
  public static final double HEAD_RADIUS = 0.4;
  /** The radius of the body. */
  public static final double BODY_RADIUS = 0.3;
  /** The radius of the pincer rotator. */
  public static final double PINCER_ROTATOR_RADIUS = 0.05;
  /** The color for components which are not selected for rotation. */
  public static final FloatColor INACTIVE_COLOR = FloatColor.ORANGE;
  /** The initial position of the top level component in the scene. */
  public static final Point3D INITIAL_POSITION = new Point3D(1.5, 1.5, 2);
  /** The angle by which to rotate the joint on user request to rotate. */
  public static final double ROTATION_ANGLE = 2.0;
  /** Randomly generated serial version UID. */
  private static final long serialVersionUID = -7060944143920496524L;


  /**
   * Runs the hand simulation in a single JFrame.
   * 
   * @param args
   *          This parameter is ignored.
   */
  public static void main(final String[] args) {
    new PA2().animator.start();
  }

  /**
   * The animator which controls the framerate at which the canvas is animated.
   */
  final FPSAnimator animator;
  /** The canvas on which we draw the scene. */
  private final GLCanvas canvas;
  /** The capabilities of the canvas. */
  private final GLCapabilities capabilities = new GLCapabilities(null);
  /** The legs on the head to be modeled. */
  private final Leg[] legs;
  /** The OpenGL utility object. */
  private final GLU glu = new GLU();
  /** The OpenGL utility toolkit object. */
  private final GLUT glut = new GLUT();
  /** The head to be modeled. */
  private final Component head;
  /** The body to be modeled. */
  private final Component body;
  /** The last x and y coordinates of the mouse press. */
  private int last_x = 0, last_y = 0;
  /** Whether the world is being rotated. */
  private boolean rotate_world = false;
  /** The axis around which to rotate the selected joints. */
  private Axis selectedAxis = Axis.X;
  /** The set of components which are currently selected for rotation. */
  private final Set<Component> selectedComponents = new HashSet<Component>(18);
  /**
   * The set of legs which have been selected for rotation.
   * 
   * Selecting a joint will only affect the joints in this set of selected
   * legs.
   **/
  private final Set<Leg> selectedLegs = new HashSet<Leg>(8);
  /** Whether the state of the model has been changed. */
  private boolean stateChanged = true;
  /**
   * The top level component in the scene which controls the positioning and
   * rotation of everything in the scene.
   */
  private final Component topLevelComponent;
  /** The quaternion which controls the rotation of the world. */
  private Quaternion viewing_quaternion = new Quaternion();
  /** The set of all components. */
  private final List<Component> components;

  public static String LEG1_ROTATOR_NAME = "leg1 rotator";
  public static String LEG1_BODY_NAME = "leg1 body";
  public static String LEG1_MIDDLE_NAME = "leg1 middle";
  public static String LEG1_DISTAL_NAME = "leg1 distal";
  public static String LEG2_ROTATOR_NAME = "leg2 rotator";
  public static String LEG2_BODY_NAME = "leg2 body";
  public static String LEG2_MIDDLE_NAME = "leg2 middle";
  public static String LEG2_DISTAL_NAME = "leg2 distal";
  public static String LEG3_ROTATOR_NAME = "leg3 rotator";
  public static String LEG3_BODY_NAME = "leg3 body";
  public static String LEG3_MIDDLE_NAME = "leg3 middle";
  public static String LEG3_DISTAL_NAME = "leg3 distal";
  public static String LEG4_ROTATOR_NAME = "leg4 rotator";
  public static String LEG4_BODY_NAME = "leg4 body";
  public static String LEG4_MIDDLE_NAME = "leg4 middle";
  public static String LEG4_DISTAL_NAME = "leg4 distal";
  public static String LEG5_ROTATOR_NAME = "leg5 rotator";
  public static String LEG5_BODY_NAME = "leg5 body";
  public static String LEG5_MIDDLE_NAME = "leg5 middle";
  public static String LEG5_DISTAL_NAME = "leg5 distal";
  public static String LEG6_ROTATOR_NAME = "leg6 rotator";
  public static String LEG6_BODY_NAME = "leg6 body";
  public static String LEG6_MIDDLE_NAME = "leg6 middle";
  public static String LEG6_DISTAL_NAME = "leg6 distal";
  public static String LEG7_ROTATOR_NAME = "leg7 rotator";
  public static String LEG7_BODY_NAME = "leg7 body";
  public static String LEG7_MIDDLE_NAME = "leg7 middle";
  public static String LEG7_DISTAL_NAME = "leg7 distal";
  public static String LEG8_ROTATOR_NAME = "leg8 rotator";
  public static String LEG8_BODY_NAME = "leg8 body";
  public static String LEG8_MIDDLE_NAME = "leg8 middle";
  public static String LEG8_DISTAL_NAME = "leg8 distal";
  public static String LEG9_PINCER_ROTATOR_NAME = "leg9 pincer rotator";
  public static String LEG9_PINCER_BODY_NAME = "leg9 pincer body";
  public static String LEG9_PINCER_MIDDLE_NAME = "leg9 pincer middle";
  public static String LEG9_PINCER_DISTAL_NAME = "leg9 pincer distal";
  public static String LEG10_PINCER_ROTATOR_NAME = "leg10 pincer rotator";
  public static String LEG10_PINCER_BODY_NAME = "leg10 pincer body";
  public static String LEG10_PINCER_MIDDLE_NAME = "leg10 pincer middle";
  public static String LEG10_PINCER_DISTAL_NAME = "leg10 pincer distal";
  public static String HEAD_NAME = "head";
  public static String BODY_NAME = "body";
  public static String TOP_LEVEL_NAME = "top level";

  /**
   * Initializes the necessary OpenGL objects and adds a canvas to this JFrame.
   */
  public PA2() {
    this.capabilities.setDoubleBuffered(true);

    this.canvas = new GLCanvas(this.capabilities);
    this.canvas.addGLEventListener(this);
    this.canvas.addMouseListener(this);
    this.canvas.addMouseMotionListener(this);
    this.canvas.addKeyListener(this);
    // this is true by default, but we just add this line to be explicit
    this.canvas.setAutoSwapBufferMode(true);
    this.getContentPane().add(this.canvas);

    // refresh the scene at 60 frames per second
    this.animator = new FPSAnimator(this.canvas, 60);

    this.setTitle("CS480/CS680 PA2: Spider Simulator");
    this.setSize(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setVisible(true);
    
    
    // all the distal joints
    final Component distal1 = new Component(new Point3D(0, 0,
        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), LEG1_DISTAL_NAME);
    final Component distal2 = new Component(new Point3D(0, 0,
        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), LEG2_DISTAL_NAME);
    final Component distal3 = new Component(new Point3D(0, 0,
        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), LEG3_DISTAL_NAME);
    final Component distal4 = new Component(new Point3D(0, 0,
        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), LEG4_DISTAL_NAME);
    final Component distal5 = new Component(new Point3D(0, 0,
        MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
        DISTAL_JOINT_HEIGHT, this.glut), LEG5_DISTAL_NAME);
    final Component distal6 = new Component(new Point3D(0, 0,
            MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
            DISTAL_JOINT_HEIGHT, this.glut), LEG6_DISTAL_NAME);
    final Component distal7 = new Component(new Point3D(0, 0,
            MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
            DISTAL_JOINT_HEIGHT, this.glut), LEG7_DISTAL_NAME);
    final Component distal8 = new Component(new Point3D(0, 0,
            MIDDLE_JOINT_HEIGHT), new RoundedCylinder(DISTAL_LEG_RADIUS,
            DISTAL_JOINT_HEIGHT, this.glut), LEG8_DISTAL_NAME);
    
    final Component distal9_pincer = new Component(new Point3D(0, 0,
            PINCER_MIDDLE_JOINT_HEIGHT), new RoundedCylinder(PINCER_LEG_RADIUS,
            PINCER_DISTAL_JOINT_HEIGHT, this.glut), LEG9_PINCER_DISTAL_NAME);
    
    final Component distal10_pincer = new Component(new Point3D(0, 0,
            PINCER_MIDDLE_JOINT_HEIGHT), new RoundedCylinder(PINCER_LEG_RADIUS,
            PINCER_DISTAL_JOINT_HEIGHT, this.glut), LEG10_PINCER_DISTAL_NAME);

    // all the middle joints
    final Component middle1 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), LEG1_MIDDLE_NAME);
    final Component middle2 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), LEG2_MIDDLE_NAME);
    final Component middle3 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), LEG3_MIDDLE_NAME);
    final Component middle4 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), LEG4_MIDDLE_NAME);
    final Component middle5 = new Component(new Point3D(0, 0,
        BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
        MIDDLE_JOINT_HEIGHT, this.glut), LEG5_MIDDLE_NAME);
    final Component middle6 = new Component(new Point3D(0, 0,
            BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
            MIDDLE_JOINT_HEIGHT, this.glut), LEG6_MIDDLE_NAME);
    final Component middle7 = new Component(new Point3D(0, 0,
            BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
            MIDDLE_JOINT_HEIGHT, this.glut), LEG7_MIDDLE_NAME);
    final Component middle8 = new Component(new Point3D(0, 0,
            BODY_JOINT_HEIGHT), new RoundedCylinder(MIDDLE_LEG_RADIUS,
            MIDDLE_JOINT_HEIGHT, this.glut), LEG8_MIDDLE_NAME);
    final Component middle9_pincer = new Component(new Point3D(0, 0,
            PINCER_BODY_JOINT_HEIGHT), new RoundedCylinder(PINCER_LEG_RADIUS,
            PINCER_MIDDLE_JOINT_HEIGHT, this.glut), LEG9_PINCER_MIDDLE_NAME);
    final Component middle10_pincer = new Component(new Point3D(0, 0,
            PINCER_BODY_JOINT_HEIGHT), new RoundedCylinder(PINCER_LEG_RADIUS,
            PINCER_MIDDLE_JOINT_HEIGHT, this.glut), LEG10_PINCER_MIDDLE_NAME);

    // all the body joints
    final Component body1 = new Component(new Point3D(0, 0, ROTATOR_RADIUS),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG1_BODY_NAME);
    final Component body2 = new Component(new Point3D(0, 0, ROTATOR_RADIUS),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG2_BODY_NAME);
    final Component body3 = new Component(new Point3D(0, 0, ROTATOR_RADIUS),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG3_BODY_NAME);
    final Component body4 = new Component(new Point3D(0, 0, ROTATOR_RADIUS),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG4_BODY_NAME);
    final Component body5 = new Component(new Point3D(0, 0, ROTATOR_RADIUS),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG5_BODY_NAME);
    final Component body6 = new Component(new Point3D(0, 0, ROTATOR_RADIUS),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG6_BODY_NAME);
    final Component body7 = new Component(new Point3D(0, 0, ROTATOR_RADIUS),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG7_BODY_NAME);
    final Component body8 = new Component(new Point3D(0, 0, ROTATOR_RADIUS),
        new RoundedCylinder(BODY_LEG_RADIUS, BODY_JOINT_HEIGHT, this.glut),
        LEG8_BODY_NAME);
    final Component body9_pincer = new Component(new Point3D(0, 0, PINCER_ROTATOR_RADIUS),
        new RoundedCylinder(PINCER_LEG_RADIUS, PINCER_BODY_JOINT_HEIGHT, this.glut),
        LEG9_PINCER_BODY_NAME);
    final Component body10_pincer = new Component(new Point3D(0, 0, PINCER_ROTATOR_RADIUS),
        new RoundedCylinder(PINCER_LEG_RADIUS, PINCER_BODY_JOINT_HEIGHT, this.glut),
        LEG10_PINCER_BODY_NAME);
    
    //All the rotator joints, displaced by various amounts from the body
    final Component rotator1 = new Component(new Point3D(0.2, 0, 0.1), new Rotator(
            ROTATOR_RADIUS, this.glut), LEG1_ROTATOR_NAME);
    final Component rotator2 = new Component(new Point3D(-0.2, 0, 0.1), new Rotator(
    		ROTATOR_RADIUS, this.glut), LEG2_ROTATOR_NAME);
    final Component rotator3 = new Component(new Point3D(0.3, 0, 0.25), new Rotator(
    		ROTATOR_RADIUS, this.glut), LEG3_ROTATOR_NAME);
    final Component rotator4 = new Component(new Point3D(-0.3, 0, 0.25), new Rotator(
    		ROTATOR_RADIUS, this.glut), LEG4_ROTATOR_NAME);
    final Component rotator5 = new Component(new Point3D(0.3, 0, 0.4), new Rotator(
    		ROTATOR_RADIUS, this.glut), LEG5_ROTATOR_NAME);
    final Component rotator6 = new Component(new Point3D(-0.3, 0, 0.4), new Rotator(
    		ROTATOR_RADIUS, this.glut), LEG6_ROTATOR_NAME);
    final Component rotator7 = new Component(new Point3D(0.3, 0, 0.55), new Rotator(
    		ROTATOR_RADIUS, this.glut), LEG7_ROTATOR_NAME);
    final Component rotator8 = new Component(new Point3D(-0.3, 0, 0.55), new Rotator(
    		ROTATOR_RADIUS, this.glut), LEG8_ROTATOR_NAME);
    final Component rotator9_pincer = new Component(new Point3D(0.175, 0, 0.65), new Rotator(
    		PINCER_ROTATOR_RADIUS, this.glut), LEG9_PINCER_ROTATOR_NAME);
    final Component rotator10_pincer = new Component(new Point3D(-0.175, 0, 0.65), new Rotator(
    		PINCER_ROTATOR_RADIUS, this.glut), LEG10_PINCER_ROTATOR_NAME);
    		
    		
    // put together the legs for easier selection by keyboard input later on
    this.legs = new Leg[] { new Leg(rotator1, body1, middle1, distal1),
        new Leg(rotator2, body2, middle2, distal2),
        new Leg(rotator3, body3, middle3, distal3),
        new Leg(rotator4, body4, middle4, distal4),
        new Leg(rotator5, body5, middle5, distal5),
        new Leg(rotator6, body6, middle6, distal6),
        new Leg(rotator7, body7, middle7, distal7),
        new Leg(rotator8, body8, middle8, distal8),
        new Leg(rotator9_pincer, body9_pincer, middle9_pincer, distal9_pincer),
        new Leg(rotator10_pincer, body10_pincer, middle10_pincer, distal10_pincer)};

    // the head and body
    this.head = new Component(new Point3D(0, 0, HEAD_HEIGHT-0.1), new Head(
        HEAD_RADIUS, this.glut), HEAD_NAME);
    this.body = new Component(new Point3D(0, 0, BODY_HEIGHT), new Body(
            BODY_RADIUS, this.glut), BODY_NAME);
    // the top level component which provides an initial position and rotation
    // to the scene (but does not cause anything to be drawn)
    this.topLevelComponent = new Component(INITIAL_POSITION, TOP_LEVEL_NAME);

    //Attach all the components together
    this.topLevelComponent.addChild(this.body);
    this.body.addChild(this.head);
    this.head.addChildren(rotator1, rotator2, rotator3, rotator4, rotator5, rotator6, rotator7, rotator8, rotator9_pincer, rotator10_pincer);
    rotator1.addChild(body1);
    rotator2.addChild(body2);
    rotator3.addChild(body3);
    rotator4.addChild(body4);
    rotator5.addChild(body5);
    rotator6.addChild(body6);
    rotator7.addChild(body7);
    rotator8.addChild(body8);
    rotator9_pincer.addChild(body9_pincer);
    rotator10_pincer.addChild(body10_pincer);
    
    body1.addChild(middle1);
    body2.addChild(middle2);
    body3.addChild(middle3);
    body4.addChild(middle4);
    body5.addChild(middle5);
    body6.addChild(middle6);
    body7.addChild(middle7);
    body8.addChild(middle8);
    body9_pincer.addChild(middle9_pincer);
    body10_pincer.addChild(middle10_pincer);
    
    
    middle1.addChild(distal1);
    middle2.addChild(distal2);
    middle3.addChild(distal3);
    middle4.addChild(distal4);
    middle5.addChild(distal5);
    middle6.addChild(distal6);
    middle7.addChild(distal7);
    middle8.addChild(distal8);
    middle9_pincer.addChild(distal9_pincer);
    middle10_pincer.addChild(distal10_pincer);
    
    // rotate the camera to starting position
    this.topLevelComponent.rotate(Axis.Y, 220);
    this.topLevelComponent.rotate(Axis.X, -130);
    
    // rotate the body parts to be in the stop position.
    body10_pincer.rotate(Axis.Y, -20);
    body9_pincer.rotate(Axis.Y, 20);
    body8.rotate(Axis.Y, -60);
    body7.rotate(Axis.Y, 60);
    body6.rotate(Axis.Y, -85);
    body5.rotate(Axis.Y, 85);
    body4.rotate(Axis.Y, -105);
    body3.rotate(Axis.Y, 105);
    body2.rotate(Axis.Y, -130);
    body1.rotate(Axis.Y, 130);
    
    rotator1.rotate(Axis.Z, -50);
    rotator2.rotate(Axis.Z, 50);
    rotator3.rotate(Axis.Z, -50);
    rotator4.rotate(Axis.Z, 50);
    rotator5.rotate(Axis.Z, -50);
    rotator6.rotate(Axis.Z, 50);
    rotator7.rotate(Axis.Z, -50);
    rotator8.rotate(Axis.Z, 50);
    rotator9_pincer.rotate(Axis.X, 50);
    rotator10_pincer.rotate(Axis.X, 50);
    
    distal1.rotate(Axis.X, -90);
    distal2.rotate(Axis.X, -90);
    distal3.rotate(Axis.X, -90);
    distal4.rotate(Axis.X, -90);
    distal5.rotate(Axis.X, -90);
    distal6.rotate(Axis.X, -90);
    distal7.rotate(Axis.X, -90);
    distal8.rotate(Axis.X, -90);
    distal9_pincer.rotate(Axis.X, -90);
    distal10_pincer.rotate(Axis.X, -90);
    
    
    
    // set rotation limits for the head.
    this.head.setXPositiveExtent(20);
    this.head.setXNegativeExtent(-20);
    this.head.setYPositiveExtent(25);
    this.head.setYNegativeExtent(-25);
    this.head.setZPositiveExtent(10);
    this.head.setZNegativeExtent(-10);


    // set rotation limits for the rotator joints of the legs, followed by those of the pincers.
    for (final Component rotatorJoint : Arrays.asList(rotator1, rotator2, rotator3, 
    		rotator4, rotator5, rotator6, rotator7, rotator8)) {
      rotatorJoint.setXPositiveExtent(30);
      rotatorJoint.setXNegativeExtent(-20);
      rotatorJoint.setYPositiveExtent(30);
      rotatorJoint.setYNegativeExtent(-30);
      rotatorJoint.setZPositiveExtent(90);
      rotatorJoint.setZNegativeExtent(-100);
    }
    
    for (final Component rotatorJoint : Arrays.asList(rotator9_pincer, rotator10_pincer)) {
      rotatorJoint.setXPositiveExtent(70);
      rotatorJoint.setXNegativeExtent(-20);
      rotatorJoint.setYPositiveExtent(20);
      rotatorJoint.setYNegativeExtent(-20);
      rotatorJoint.setZPositiveExtent(40);
      rotatorJoint.setZNegativeExtent(-40);
    }
    
    // set rotation limits for the middle joints of the legs and the pincers.
    for (final Component middleJoint : Arrays.asList(middle1, middle2, middle3, 
    		middle4, middle5, middle6, middle7, middle8, middle9_pincer, middle10_pincer)) {
        middleJoint.setXPositiveExtent(10);
        middleJoint.setXNegativeExtent(-20);
        middleJoint.setYPositiveExtent(5);
        middleJoint.setYNegativeExtent(-5);
        middleJoint.setZPositiveExtent(25);
        middleJoint.setZNegativeExtent(-25);
      }
    
    // set rotation limits for the distal joints of the legs and the pincers.
    for (final Component distalJoint : Arrays.asList(distal1, distal2, distal3, 
    		distal4, distal5, distal6, distal7, distal8, distal9_pincer, distal10_pincer)) {
        distalJoint.setXPositiveExtent(0);
        distalJoint.setXNegativeExtent(-150);
        distalJoint.setYPositiveExtent(15);
        distalJoint.setYNegativeExtent(-12.5);
        distalJoint.setZPositiveExtent(25);
        distalJoint.setZNegativeExtent(-25);
      }
    
    // create the list of all the components for debugging purposes.
    this.components = Arrays.asList(rotator1, body1, middle1, distal1,rotator2, body2, middle2,
        distal2, rotator3, body3, middle3, distal3, rotator4, body4, middle4, distal4, rotator5, body5,
        middle5, distal5, rotator6, body6, middle6, distal6, rotator7, body7, middle7, distal7,
        rotator8, body8, middle8, distal8, rotator9_pincer, body9_pincer, middle9_pincer, distal9_pincer,
        rotator10_pincer, body10_pincer, middle10_pincer, distal10_pincer,this.head, this.body);
        
        
}
  /**
   * Redisplays the scene containing the hand model.
   * 
   * @param drawable
   *          The OpenGL drawable object with which to create OpenGL models.
   */
  public void display(final GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    // clear the display
    gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

    // from here on affect the model view
    gl.glMatrixMode(GL2.GL_MODELVIEW);

    // start with the identity matrix initially
    gl.glLoadIdentity();

    // rotate the world by the appropriate rotation quaternion
    gl.glMultMatrixf(this.viewing_quaternion.toMatrix(), 0);

    // update the position of the components which need to be updated
    // TODO only need to update the selected and JUST deselected components
    if (this.stateChanged) {
      this.topLevelComponent.update(gl);
      this.stateChanged = false;
    }

    // redraw the components
    this.topLevelComponent.draw(gl);
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param drawable
   *          This parameter is ignored.
   * @param modeChanged
   *          This parameter is ignored.
   * @param deviceChanged
   *          This parameter is ignored.
   */
  public void displayChanged(GLAutoDrawable drawable, boolean modeChanged,
      boolean deviceChanged) {
    // intentionally unimplemented
  }

  /**
   * Initializes the scene and model.
   * 
   * @param drawable
   *          {@inheritDoc}
   */
  public void init(final GLAutoDrawable drawable) {
    final GL2 gl = (GL2)drawable.getGL();

    // perform any initialization needed by the hand model
    this.topLevelComponent.initialize(gl);

    // initially draw the scene
    this.topLevelComponent.update(gl);

    // set up for shaded display of the hand
    final float light0_position[] = { 1, 1, 1, 0 };
    final float light0_ambient_color[] = { 0.25f, 0.25f, 0.25f, 1 };
    final float light0_diffuse_color[] = { 1, 1, 1, 1 };

    gl.glPolygonMode(GL.GL_FRONT, GL2.GL_FILL);
    gl.glEnable(GL2.GL_COLOR_MATERIAL);
    gl.glColorMaterial(GL.GL_FRONT, GL2.GL_AMBIENT_AND_DIFFUSE);

    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
    gl.glShadeModel(GL2.GL_SMOOTH);

    // set up the light source
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, light0_position, 0);
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, light0_ambient_color, 0);
    gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, light0_diffuse_color, 0);

    // turn lighting and depth buffering on
    gl.glEnable(GL2.GL_LIGHTING);
    gl.glEnable(GL2.GL_LIGHT0);
    gl.glEnable(GL2.GL_DEPTH_TEST);
    gl.glEnable(GL2.GL_NORMALIZE);
  }

  /**
   * Interprets key presses according to the following scheme:
   * 
   * up-arrow, down-arrow: increase/decrease rotation angle
   * 
   * @param key
   *          The key press event object.
   */
  public void keyPressed(final KeyEvent key) {
    switch (key.getKeyCode()) {
    case KeyEvent.VK_KP_UP:
    case KeyEvent.VK_UP:
      for (final Component component : this.selectedComponents) {
        component.rotate(this.selectedAxis, ROTATION_ANGLE);
      }
      this.stateChanged = true;
      break;
    case KeyEvent.VK_KP_DOWN:
    case KeyEvent.VK_DOWN:
      for (final Component component : this.selectedComponents) {
        component.rotate(this.selectedAxis, -ROTATION_ANGLE);
      }
      this.stateChanged = true;
      break;
    default:
      break;
    }
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param key
   *          This parameter is ignored.
   */
  public void keyReleased(final KeyEvent key) {
    // intentionally unimplemented
  }

  private final TestCases testCases = new TestCases();

  private void setModelState(final Map<String, Angled> state) {
    this.head.setAngles(state.get(HEAD_NAME));
    this.body.setAngles(state.get(BODY_NAME));
    this.legs[0].rotatorJoint().setAngles(state.get(LEG1_ROTATOR_NAME));
    this.legs[0].bodyJoint().setAngles(state.get(LEG1_BODY_NAME));
    this.legs[0].middleJoint().setAngles(state.get(LEG1_MIDDLE_NAME));
    this.legs[0].distalJoint().setAngles(state.get(LEG1_DISTAL_NAME));
    this.legs[1].rotatorJoint().setAngles(state.get(LEG2_ROTATOR_NAME));
    this.legs[1].bodyJoint().setAngles(state.get(LEG2_BODY_NAME));
    this.legs[1].middleJoint().setAngles(state.get(LEG2_MIDDLE_NAME));
    this.legs[1].distalJoint().setAngles(state.get(LEG2_DISTAL_NAME));
    this.legs[2].rotatorJoint().setAngles(state.get(LEG3_ROTATOR_NAME));
    this.legs[2].bodyJoint().setAngles(state.get(LEG3_BODY_NAME));
    this.legs[2].middleJoint().setAngles(state.get(LEG3_MIDDLE_NAME));
    this.legs[2].distalJoint().setAngles(state.get(LEG3_DISTAL_NAME));
    this.legs[3].rotatorJoint().setAngles(state.get(LEG4_ROTATOR_NAME));
    this.legs[3].bodyJoint().setAngles(state.get(LEG4_BODY_NAME));
    this.legs[3].middleJoint().setAngles(state.get(LEG4_MIDDLE_NAME));
    this.legs[3].distalJoint().setAngles(state.get(LEG4_DISTAL_NAME));
    this.legs[4].rotatorJoint().setAngles(state.get(LEG5_ROTATOR_NAME));
    this.legs[4].bodyJoint().setAngles(state.get(LEG5_BODY_NAME));
    this.legs[4].middleJoint().setAngles(state.get(LEG5_MIDDLE_NAME));
    this.legs[4].distalJoint().setAngles(state.get(LEG5_DISTAL_NAME));
    this.legs[5].rotatorJoint().setAngles(state.get(LEG6_ROTATOR_NAME));
    this.legs[5].bodyJoint().setAngles(state.get(LEG6_BODY_NAME));
    this.legs[5].middleJoint().setAngles(state.get(LEG6_MIDDLE_NAME));
    this.legs[5].distalJoint().setAngles(state.get(LEG6_DISTAL_NAME));
    this.legs[6].rotatorJoint().setAngles(state.get(LEG7_ROTATOR_NAME));
    this.legs[6].bodyJoint().setAngles(state.get(LEG7_BODY_NAME));
    this.legs[6].middleJoint().setAngles(state.get(LEG7_MIDDLE_NAME));
    this.legs[6].distalJoint().setAngles(state.get(LEG7_DISTAL_NAME));
    this.legs[7].rotatorJoint().setAngles(state.get(LEG8_ROTATOR_NAME));
    this.legs[7].bodyJoint().setAngles(state.get(LEG8_BODY_NAME));
    this.legs[7].middleJoint().setAngles(state.get(LEG8_MIDDLE_NAME));
    this.legs[7].distalJoint().setAngles(state.get(LEG8_DISTAL_NAME));
    this.legs[8].rotatorJoint().setAngles(state.get(LEG9_PINCER_ROTATOR_NAME));
    this.legs[8].bodyJoint().setAngles(state.get(LEG9_PINCER_BODY_NAME));
    this.legs[8].middleJoint().setAngles(state.get(LEG9_PINCER_MIDDLE_NAME));
    this.legs[8].distalJoint().setAngles(state.get(LEG9_PINCER_DISTAL_NAME));
    this.legs[9].rotatorJoint().setAngles(state.get(LEG10_PINCER_ROTATOR_NAME));
    this.legs[9].bodyJoint().setAngles(state.get(LEG10_PINCER_BODY_NAME));
    this.legs[9].middleJoint().setAngles(state.get(LEG10_PINCER_MIDDLE_NAME));
    this.legs[9].distalJoint().setAngles(state.get(LEG10_PINCER_DISTAL_NAME));

    this.stateChanged = true;
  }

  /**
   * Interprets typed keys according to the following scheme:
   * 
   * 1 : toggle the first finger leg active in rotation
   * 
   * 2 : toggle the second leg active in rotation
   * 
   * 3 : toggle the third leg active in rotation
   * 
   * 4 : toggle the fourth leg active in rotation
   * 
   * 5 : toggle the fifth leg active in rotation
   * 
   * 6 : toggle the sixth leg active in rotation
   * 
   * 7 : toggle the seventh leg active in rotation
   * 
   * 8 : toggle the eighth leg active in rotation
   * 
   * 9 : toggle the first pincer active in rotation
   * 
   * 0 : toggle the second pincer active in rotation
   * 
   * - : toggle the head active in rotation
   * 
   * = : toggle the body
   * 
   * X : use the X axis rotation at the active joint(s)
   * 
   * Y : use the Y axis rotation at the active joint(s)
   * 
   * Z : use the Z axis rotation at the active joint(s)
   * 
   * C : resets the spider to the stop sign
   * 
   * M : select middle joint
   * 
   * D : select last (distal) joint
   * 
   * R : resets the view to the initial rotation
   * 
   * K : prints the angles of the five fingers for debugging purposes
   * 
   * Q, Esc : exits the program
   * 
   */
  public void keyTyped(final KeyEvent key) {
    switch (key.getKeyChar()) {
    case 'Q':
    case 'q':
    case KeyEvent.VK_ESCAPE:
      new Thread() {
        @Override
        public void run() {
          PA2.this.animator.stop();
        }
      }.start();
      System.exit(0);
      break;

    // print the angles of the components
    case 'K':
    case 'k':
      printJoints();
      break;

    // resets to the stop sign
    case 'C':
    case 'c':
      this.setModelState(this.testCases.stop());
      break;

    // set the state of the hand to the next test case
    case 'T':
    case 't':
      this.setModelState(this.testCases.next());
      break;

    // set the viewing quaternion to 0 rotation
    case 'R':
    case 'r':
      this.viewing_quaternion.reset();
      break;

    // Toggle which leg(s) are affected by the current rotation
    case '1':
      toggleSelection(this.legs[0]);
      break;
    case '2':
      toggleSelection(this.legs[1]);
      break;
    case '3':
      toggleSelection(this.legs[2]);
      break;
    case '4':
      toggleSelection(this.legs[3]);
      break;
    case '5':
      toggleSelection(this.legs[4]);
      break;
    case '6':
      toggleSelection(this.legs[5]);
      break;
    case '7':
      toggleSelection(this.legs[6]);
      break;
    case '8':
      toggleSelection(this.legs[7]);
      break;
    // Toggle which pincer(s) are active in rotation
    case '9':
      toggleSelection(this.legs[8]);
      break;
    case '0':
      toggleSelection(this.legs[9]);
      break;
    // Toggle the head
    case '-':
      toggleSelection(this.head);
      break;
    // Toggle the body
    case '=':
      toggleSelection(this.body);
      break;

    // toggle which joints are affected by the current rotation
    case 'D':
    case 'd':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.distalJoint());
      }
      break;
    case 'M':
    case 'm':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.middleJoint());
      }
      break;
    case 'B':
    case 'b':
      for (final Leg leg : this.selectedLegs) {
        toggleSelection(leg.rotatorJoint());
      }
      break;

    // change the axis of rotation at current active joint
    case 'X':
    case 'x':
      this.selectedAxis = Axis.X;
      break;
    case 'Y':
    case 'y':
      this.selectedAxis = Axis.Y;
      break;
    case 'Z':
    case 'z':
      this.selectedAxis = Axis.Z;
      break;
    default:
      break;
    }
  }

  /**
   * Prints the joints on the System.out print stream.
   */
  private void printJoints() {
    this.printJoints(System.out);
  }

  /**
   * Prints the joints on the specified PrintStream.
   * 
   * @param printStream
   *          The stream on which to print each of the components.
   */
  private void printJoints(final PrintStream printStream) {
    for (final Component component : this.components) {
      printStream.println(component);
    }
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseClicked(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * Updates the rotation quaternion as the mouse is dragged.
   * 
   * @param mouse
   *          The mouse drag event object.
   */
  public void mouseDragged(final MouseEvent mouse) {
	if (this.rotate_world) {
		// get the current position of the mouse
		final int x = mouse.getX();
		final int y = mouse.getY();
	
		// get the change in position from the previous one
		final int dx = x - this.last_x;
		final int dy = y - this.last_y;
	
		// create a unit vector in the direction of the vector (dy, dx, 0)
		final double magnitude = Math.sqrt(dx * dx + dy * dy);
		final float[] axis = magnitude == 0 ? new float[]{1,0,0}: // avoid dividing by 0
			new float[] { (float) (dy / magnitude),(float) (dx / magnitude), 0 };
	
		// calculate appropriate quaternion
		final float viewing_delta = 3.1415927f / 180.0f;
		final float s = (float) Math.sin(0.5f * viewing_delta);
		final float c = (float) Math.cos(0.5f * viewing_delta);
		final Quaternion Q = new Quaternion(c, s * axis[0], s * axis[1], s
				* axis[2]);
		this.viewing_quaternion = Q.multiply(this.viewing_quaternion);
	
		// normalize to counteract acccumulating round-off error
		this.viewing_quaternion.normalize();
	
		// save x, y as last x, y
		this.last_x = x;
		this.last_y = y;
	}
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseEntered(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseExited(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * This method is intentionally unimplemented.
   * 
   * @param mouse
   *          This parameter is ignored.
   */
  public void mouseMoved(MouseEvent mouse) {
    // intentionally unimplemented
  }

  /**
   * Starts rotating the world if the left mouse button was released.
   * 
   * @param mouse
   *          The mouse press event object.
   */
  public void mousePressed(final MouseEvent mouse) {
    if (mouse.getButton() == MouseEvent.BUTTON1) {
      this.last_x = mouse.getX();
      this.last_y = mouse.getY();
      this.rotate_world = true;
    }
  }

  /**
   * Stops rotating the world if the left mouse button was released.
   * 
   * @param mouse
   *          The mouse release event object.
   */
  public void mouseReleased(final MouseEvent mouse) {
    if (mouse.getButton() == MouseEvent.BUTTON1) {
      this.rotate_world = false;
    }
  }

  /**
   * {@inheritDoc}
   * 
   * @param drawable
   *          {@inheritDoc}
   * @param x
   *          {@inheritDoc}
   * @param y
   *          {@inheritDoc}
   * @param width
   *          {@inheritDoc}
   * @param height
   *          {@inheritDoc}
   */
  public void reshape(final GLAutoDrawable drawable, final int x, final int y,
      final int width, final int height) {
    final GL2 gl = (GL2)drawable.getGL();

    // prevent division by zero by ensuring window has height 1 at least
    final int newHeight = Math.max(1, height);

    // compute the aspect ratio
    final double ratio = (double) width / newHeight;

    // reset the projection coordinate system before modifying it
    gl.glMatrixMode(GL2.GL_PROJECTION);
    gl.glLoadIdentity();

    // set the viewport to be the entire window
    gl.glViewport(0, 0, width, newHeight);

    // set the clipping volume
    this.glu.gluPerspective(25, ratio, 0.1, 100);

    // camera positioned at (0,0,6), look at point (0,0,0), up vector (0,1,0)
    this.glu.gluLookAt(0, 0, 12, 0, 0, 0, 0, 1, 0);

    // switch back to model coordinate system
    gl.glMatrixMode(GL2.GL_MODELVIEW);
  }

  private void toggleSelection(final Component component) {
    if (this.selectedComponents.contains(component)) {
      this.selectedComponents.remove(component);
      component.setColor(INACTIVE_COLOR);
    } else {
      this.selectedComponents.add(component);
      component.setColor(ACTIVE_COLOR);
    }
    this.stateChanged = true;
  }

  private void toggleSelection(final Leg leg) {
    if (this.selectedLegs.contains(leg)) {
      this.selectedLegs.remove(leg);
      this.selectedComponents.removeAll(leg.joints());
      for (final Component joint : leg.joints()) {
        joint.setColor(INACTIVE_COLOR);
      }
    } else {
      this.selectedLegs.add(leg);
    }
    this.stateChanged = true;
  }

@Override
public void dispose(GLAutoDrawable drawable) {
	// TODO Auto-generated method stub
	
}
}
