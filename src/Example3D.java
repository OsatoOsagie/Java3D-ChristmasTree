
/**
 * This class depicts a cristmass tree(made up of five cones)  with snow(spheres) snalling towards it(position interpolator).
 * on this tree I have attached a rotating star(two cones attached together) using a rotation interpolator.
 * I also have decorations on the christmas tree randomly position to make it more astheticly pleasing(a combination of spheres and cubes).
 * Underneath the christmass tree I have positioned gifts(made using boxes) and 
 * two christmas decorations on the left and the right side of my tree(drawn shape with a sphere positioned inside the shape).
 * Furthermore, i have created two snow men(spheres) wearing hats(cylinders) on the left and right side of the tree. 
 * The snow man on the right hand side of my tree moves closer to the tree and the snow mans hat changes colour, 
 * when it comes in contact with one of the christmass decoration positioned underneath the tree. 
 * the sphere in the decoration also changes colour to the cour of the snowmans hat while the snow mans hat changes colour to that of the sphere(colour swap)
 * Also I have static snow(spheres) randomly positioned on the scene.
 * 
 *@author O.G.Osagie
 * @version 1.0
 *
 * Time-stamp: <2017-02-17 11:54:13 rlc3> edited by Greg Osagie
 *
 */
import java.awt.*;
import java.util.Random;

import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.universe.*;
import com.sun.prism.PhongMaterial;

import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.behaviors.keyboard.KeyNavigatorBehavior;
import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.geometry.ColorCube;
import com.sun.j3d.utils.geometry.Cone;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.GeometryInfo;
import com.sun.j3d.utils.geometry.NormalGenerator;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.geometry.Box;
import javax.swing.JFrame;

public class Example3D extends JFrame {
	// sort out your diagram(*mainTG)

	public Example3D() {

		// create a "standard" universe using SimpleUniverse
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		Canvas3D c = new Canvas3D(SimpleUniverse.getPreferredConfiguration());
		cp.add("Center", c);
		// Create a data structure to contain a group of objects.
		BranchGroup scene = createSceneGraph();
		// Create a virtual universe to contain your scene.
		SimpleUniverse u = new SimpleUniverse(c);
		// Add an object to the group
		u.getViewingPlatform().setNominalViewingTransform();
		u.addBranchGraph(scene);

		addLight(u);

		// *** create a viewing platform
		TransformGroup cameraTG = u.getViewingPlatform().getViewPlatformTransform();
		// starting postion of the viewing platform
		Vector3f translate = new Vector3f();
		Transform3D T3D = new Transform3D();
		// Position the viewer so that they are looking at the object
		// move along z axis by 10.0f ("move away from the screen")
		translate.set(0.0f, 2.5f, 30.9f);
		T3D.setTranslation(translate);
		cameraTG.setTransform(T3D);
		setTitle("A christmass tree");
		setSize(500, 500);
		setVisible(true);
	}

	/***********************************************************************************************************
	 * Creating a random number generator, this is used to position my now on
	 * the X,Y,Z axis
	 * 
	 ***********************************************************************************************************/

	public Vector3d randomgenerator() {
		Random r = new Random();
		// random position on the X-axis
		double randomValueX = -100.0 + (100.0 - -200.0) * r.nextDouble();
		// random position on the Z-axis
		double randomValueZ = -100.0 + (100.0 - -100.0) * r.nextDouble();
		// random position on the Y-axis
		double randomValueY = -100.0 + (100.0 - -100.0) * r.nextDouble();
		// creating a new vector3D which is used to set the translation on now
		// snow(whiteShepehers) on the X,Y and Z axis
		return new Vector3d(randomValueX, randomValueY, randomValueZ);
	}

	public BranchGroup createSceneGraph() {

		// creating a main transfrom3D this is used to set the scaling of my
		// scene
		Transform3D main = new Transform3D();
		// setting the scale of my main transform group so that the objects look
		// smaller
		main.setScale(.5f);
		// Creating a transForm group mainTG,which takes the main 3D object asa
		// parameter
		TransformGroup mainTG = new TransformGroup(main);
		mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		mainTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		// Create a brown light that shines for 100m from the origin
		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);

		// creating a branch group
		BranchGroup objRoot = new BranchGroup();
		objRoot.addChild(mainTG);
		// setting my background image
		TextureLoader myBackground = new TextureLoader("myImages/background.jpg",

				new Container());

		ImageComponent2D myImage = myBackground.getImage();
		Background bg = new Background();
		bg.setImage(myImage);
		bg.setApplicationBounds(bounds);
		bg.setImageScaleMode(bg.SCALE_FIT_MAX);
		objRoot.addChild(bg);
		/*********************************************************************************************************
		 * Creating the base of my tree
		 ********************************************************************************************************/
		// Transform3D tree= new Transform3D();
		TransformGroup treeTG = new TransformGroup();// creating a main
														// transForm group
														// object, which all the
														// elements of my tree
														// are added to.
		mainTG.addChild(treeTG);

		Transform3D Floor = new Transform3D();// creating a new transForm 3d
												// object for the floor, in
												// order to be able to set the
												// scaling and translation of
												// the box shape
		Floor.rotX(Math.PI / 2);// rotating my box shape 180 degrees
		Floor.setScale(new Vector3d(3.0, 2.0, 5.0));// setting the scale of my
													// box shape to give it a
													// flat appearance
		Floor.setTranslation(new Vector3d(0.0, -10, 0.0));// setting the
															// translate of my
															// box to -10 on the
															// y axis to give an
															// appearance of the
															// ground
		TransformGroup floorTG = new TransformGroup(Floor);// creating the
		floorTG.setCollidable(false); // transform group
		// for the floor of
		// my scene
		// Set up colors

		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);// creating a black
														// colour, which is used
														// to set the materials
														// of my appearances

		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);// creating a white
														// colour, which is used
														// to set the materials
														// of my appearances

		// creating a grey colour, which is used to set the materials of my
		// appearances
		Color3f grey = new Color3f(0.753f, 0.753f, 0.753f);

		// Setting up the texture map

		TextureLoader loader4 = new TextureLoader("myImages/floor.jpg",

				"LUMINANCE", new Container());

		Texture texture4 = loader4.getTexture();

		texture4.setBoundaryModeS(Texture.WRAP);

		texture4.setBoundaryModeT(Texture.WRAP);

		texture4.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

		// Set up the texture attributes

		// could be REPLACE, BLEND or DECAL instead of MODULATE

		TextureAttributes texAttrForFloor = new TextureAttributes();

		texAttrForFloor.setTextureMode(TextureAttributes.MODULATE);
		// creating and appearance
		Appearance apForFloor = new Appearance();

		apForFloor.setTexture(texture4);

		apForFloor.setTextureAttributes(texAttrForFloor);

		// set up the material for appearance

		apForFloor.setMaterial(new Material(white, grey, white, grey, 1.0f));

		// Create a box to act as the floor of my scene

		int primflagsForFloor = Primitive.GENERATE_NORMALS +

				Primitive.GENERATE_TEXTURE_COORDS;

		Box floorPlan = new Box(20.8f, 25.8f, .1f, primflagsForFloor, apForFloor);// creating
																					// the
																					// box
																					// shape
																					// and
																					// setting
																					// the
																					// appearance
																					// and
																					// texture;
		floorPlan.setCollidable(false);

		floorTG.addChild(floorPlan);// adding the box created to the floor
									// transformGroup
		mainTG.addChild(floorTG);// adding the floor to the main tg

		/*********************************************************************************************************
		 * CREATING BASE FOR MY TREE
		 ********************************************************************************************************/

		/*
		 * creating a transform3D objects Rotating them along the X axis by
		 * PI/2(180 degrees) setting the scale and translation for each
		 * transform3D object creating transForm group box bases for my tree and
		 * adding the transForm3D object to the transform group creating a base
		 * tg where all the boxes are added to.
		 */

		Transform3D firstBaseBox = new Transform3D();
		firstBaseBox.rotX(Math.PI / 2);
		firstBaseBox.setScale(new Vector3d(2.4, 2.0, 5.0));
		firstBaseBox.setTranslation(new Vector3d(0.0, -6.5, 0.0));
		TransformGroup boxTGforBase1 = new TransformGroup(firstBaseBox);

		Transform3D secondBaseBox = new Transform3D();
		secondBaseBox.rotX(Math.PI / 2);
		secondBaseBox.setScale(new Vector3d(2.8, 2.0, 5.0));
		secondBaseBox.setTranslation(new Vector3d(0.0, -7.5, 0.0));
		TransformGroup boxTGforBase2 = new TransformGroup(secondBaseBox);

		Transform3D thirdBaseBox = new Transform3D();
		thirdBaseBox.rotX(Math.PI / 2);
		thirdBaseBox.setScale(new Vector3d(3.0, 2.0, 5.0));
		thirdBaseBox.setTranslation(new Vector3d(0.0, -8, 0.0));
		TransformGroup boxTGforBase3 = new TransformGroup(thirdBaseBox);

		Transform3D base = new Transform3D();
		base.setTranslation(new Vector3d(0.0, -1.5, 0.0));
		TransformGroup baseTG = new TransformGroup(base);

		// Set up colors for texture

		Color3f brown = new Color3f(0.545f, 0.271f, 0.075f);

		// Set up the texture map

		TextureLoader loader = new TextureLoader("myImages/tree.jpg",

				"LUMINANCE", new Container());

		Texture texture = loader.getTexture();

		texture.setBoundaryModeS(Texture.WRAP);

		texture.setBoundaryModeT(Texture.WRAP);

		texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));

		// Set up the texture attributes

		// could be REPLACE, BLEND or DECAL instead of MODULATE

		TextureAttributes texAttr = new TextureAttributes();

		texAttr.setTextureMode(TextureAttributes.MODULATE);

		Appearance apForBase = new Appearance();

		apForBase.setTexture(texture);

		apForBase.setTextureAttributes(texAttr);

		// set up the material

		apForBase.setMaterial(new Material(brown, black, brown, black, 1.0f));

		// Create a box to demonstrate textures

		int primflags = Primitive.GENERATE_NORMALS +

				Primitive.GENERATE_TEXTURE_COORDS;
		// creating the first box and setting its appearance
		Box firstBase = new Box(0.8f, 0.8f, .1f, primflags, apForBase);
		// setting the colliadable to false so that it doesnt interfere with my
		// collision detection
		firstBase.setCollidable(false);
		// creating the second box and setting its appearance
		Box secondBase = new Box(0.8f, 0.8f, .1f, primflags, apForBase);
		// setting the colliadable to false so that it doesnt interfere with my
		// collision detection
		secondBase.setCollidable(false);
		// creating the third box and setting its appearance
		Box thirdBase = new Box(0.8f, 0.8f, .1f, primflags, apForBase);
		// setting the colliadable to false so that it doesnt interfere with my
		// collision detection
		thirdBase.setCollidable(false);

		// adding the box shapes to their various transform groups
		boxTGforBase1.addChild(firstBase);
		boxTGforBase2.addChild(secondBase);
		boxTGforBase3.addChild(thirdBase);
		// adding those transform groups to the base transform group for all the
		// boxes
		baseTG.addChild(boxTGforBase1);
		baseTG.addChild(boxTGforBase2);
		baseTG.addChild(boxTGforBase3);
		// adding the base transform group, as an element of the tree transform
		// group
		treeTG.addChild(baseTG);
		// setting the colliadable to false so that it doesnt interfere with my
		// collision detection
		treeTG.setCollidable(false);
		/*********************************************************************************************************
		 * creating cylinder fortrunk of tree
		 ********************************************************************************************************/
		// Set up the texture map
		TextureLoader loaderForTrunk = new TextureLoader("myImages/treetrunk.jpg",
				"LUMINANCE", new Container());
		Texture textureForTrunk = loaderForTrunk.getTexture();
		textureForTrunk.setBoundaryModeS(Texture.WRAP);
		textureForTrunk.setBoundaryModeT(Texture.WRAP);
		textureForTrunk.setBoundaryColor(new Color4f(0.0f, 1.0f, 0.0f, 0.0f));
		// Set up the texture attributes for trunk
		TextureAttributes texAttForTrunk = new TextureAttributes();
		texAttForTrunk.setTextureMode(TextureAttributes.MODULATE);
		Appearance apFortrunk = new Appearance();
		apFortrunk.setTexture(textureForTrunk);
		apFortrunk.setTextureAttributes(texAttForTrunk);
		// set up the material for trunk
		apFortrunk.setMaterial(new Material(brown, black, brown, black, 1.0f));
		// Create a cylinder to demonstrate textures
		int primflagsForTrunk = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
		/*
		 * creating cylinder used as trunk and setting its texture and
		 * appearance adding the cylinder as a child of the base transForm group
		 * 
		 */
		Cylinder cylinderForTrunk = new Cylinder(0.5f, 17f, primflagsForTrunk, apFortrunk);
		baseTG.addChild(cylinderForTrunk);
		cylinderForTrunk.setCollidable(false);

		/*********************************************************************************************************
		 * Creating the leafs for my tree
		 * 
		 ********************************************************************************************************/
		/*
		 * creating transForm3D object for my conebase used in my tree setting
		 * the translations for the transform3D object on the Y-axis creating a
		 * transformgroup and adding the transform 3d to the trandForm group
		 * creating a transform groups for all the cones used as leafs
		 */
		Transform3D conebaseForFirstLayerOfTree = new Transform3D();
		conebaseForFirstLayerOfTree.setTranslation(new Vector3d(0.0, -1.5, 0.0));
		TransformGroup coneTGforBaseForFirstLayerOfTree = new TransformGroup(conebaseForFirstLayerOfTree);

		Transform3D conebaseForSecondLayerOfTree = new Transform3D();
		conebaseForSecondLayerOfTree.setTranslation(new Vector3d(0.0, 1.0, 0.0));
		TransformGroup coneTGforBaseForSecondLayerOfTree = new TransformGroup(conebaseForSecondLayerOfTree);

		Transform3D conebaseForThirdLayerOfTree = new Transform3D();
		conebaseForThirdLayerOfTree.setTranslation(new Vector3d(0.0, 3.5, 0.0));
		TransformGroup coneTGforBaseForThirdLayerOfTree = new TransformGroup(conebaseForThirdLayerOfTree);

		Transform3D conebaseForFourthLayerOfTree = new Transform3D();
		conebaseForFourthLayerOfTree.setTranslation(new Vector3d(0.0, 5.5, .0));
		TransformGroup coneTGforBaseForFourthLayerOfTree = new TransformGroup(conebaseForFourthLayerOfTree);

		Transform3D conebaseForFifthLayerOfTree = new Transform3D();
		conebaseForFifthLayerOfTree.setTranslation(new Vector3d(0.0, 7.5, 0.0));
		TransformGroup coneTGforBaseForFifthLayerOfTree = new TransformGroup(conebaseForFifthLayerOfTree);

		TransformGroup coneTGforAllCones = new TransformGroup();

		// Set up the texture map

		TextureLoader loaderForLeafs = new TextureLoader("myImages/treeLeaves.jpg",

				new Container());

		Texture textureForLeafs = loaderForLeafs.getTexture();

		textureForLeafs.setBoundaryModeS(Texture.WRAP);

		textureForLeafs.setBoundaryModeT(Texture.WRAP);

		// Set up the texture attributes
		// could be REPLACE, BLEND or DECAL instead of MODULATE

		TextureAttributes texAttrForLeafs = new TextureAttributes();

		texAttrForLeafs.setTextureMode(TextureAttributes.MODULATE);

		Appearance apForleafs = new Appearance();

		apForleafs.setTexture(textureForLeafs);

		apForleafs.setTextureAttributes(texAttrForLeafs);

		// Create a cones to demonstrate textures

		int primflagsForLeafs = Primitive.GENERATE_NORMALS +

				Primitive.GENERATE_TEXTURE_COORDS;
		// creating cones used as leafs for my tree
		Cone coneBaseForFirstLayerOfTree = new Cone(7f, 4f, primflagsForLeafs, apForleafs);
		coneBaseForFirstLayerOfTree.setCollidable(false);

		Cone coneBaseForSecondLayerOfTree = new Cone(6.5f, 4f, primflagsForLeafs, apForleafs);
		coneBaseForSecondLayerOfTree.setCollidable(false);

		Cone coneBaseForThirdLayerOfTree = new Cone(6f, 4f, primflagsForLeafs, apForleafs);
		coneBaseForThirdLayerOfTree.setCollidable(false);

		Cone coneBaseForFourthLayerOfTree = new Cone(5.5f, 4f, primflagsForLeafs, apForleafs);
		coneBaseForFourthLayerOfTree.setCollidable(false);

		Cone coneBaseForFifthLayerOfTree = new Cone(5f, 4f, primflagsForLeafs, apForleafs);
		coneBaseForFifthLayerOfTree.setCollidable(false);

		// adding the cones created as as a child of their various transform
		// groups
		coneTGforBaseForFirstLayerOfTree.addChild(coneBaseForFirstLayerOfTree);
		coneTGforBaseForSecondLayerOfTree.addChild(coneBaseForSecondLayerOfTree);
		coneTGforBaseForThirdLayerOfTree.addChild(coneBaseForThirdLayerOfTree);
		coneTGforBaseForFourthLayerOfTree.addChild(coneBaseForFourthLayerOfTree);
		coneTGforBaseForFifthLayerOfTree.addChild(coneBaseForFifthLayerOfTree);

		// adding those transform groups as a child of the transform group for
		// all cones used as leafs.
		coneTGforAllCones.addChild(coneTGforBaseForFirstLayerOfTree);
		coneTGforAllCones.addChild(coneTGforBaseForSecondLayerOfTree);
		coneTGforAllCones.addChild(coneTGforBaseForThirdLayerOfTree);
		coneTGforAllCones.addChild(coneTGforBaseForFourthLayerOfTree);
		coneTGforAllCones.addChild(coneTGforBaseForFifthLayerOfTree);
		// adding the transform group for all the cones to the tree transform
		// group
		treeTG.addChild(coneTGforAllCones);

		/*********************************************************************************************************
		 * Making star on top of my tree
		 * 
		 ********************************************************************************************************/

		/*
		 * creating transForm3D object for my star used in my tree creating the
		 * transform groups used for my individual cones and for my star and
		 * setting the transform 3d object setting the translations and scaling
		 * for the transform3D object
		 */

		Transform3D coneForStarOnTopOfTree = new Transform3D();
		coneForStarOnTopOfTree.setTranslation(new Vector3d(0.0, 10.9, 0.0));
		coneForStarOnTopOfTree.setScale(new Vector3d(.4, .4, .2));
		TransformGroup coneTGforStarOnTopOfTree = new TransformGroup(coneForStarOnTopOfTree);

		Transform3D coneForStar2OnTopOfTree = new Transform3D();
		coneForStar2OnTopOfTree.setTranslation(new Vector3d(0.0, 10.2, 0.0));
		coneForStar2OnTopOfTree.setScale(new Vector3d(-.4, -.4, -.2));
		TransformGroup coneTGforStarOnTopOfTree2 = new TransformGroup(coneForStar2OnTopOfTree);

		TransformGroup starTGforStarOnTopTree = new TransformGroup();
		starTGforStarOnTopTree.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		starTGforStarOnTopTree.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		// creating a yellow appearance for the star on top my tree and setting
		// its polygon attribute to give it a line appearance
		Appearance yellowAppForStar = new Appearance();
		Color3f yellowColor2 = new Color3f(1.000f, 1.000f, 0.000f);
		ColoringAttributes yellowCA2 = new ColoringAttributes();
		yellowCA2.setColor(yellowColor2);
		yellowAppForStar.setColoringAttributes(yellowCA2);

		yellowAppForStar.setPolygonAttributes(

				new PolygonAttributes(PolygonAttributes.POLYGON_LINE,

						PolygonAttributes.CULL_BACK, 0.0f));

		/*
		 * creating the cone shapes used as star setting the collidab le to
		 * false to allow my collision detection to work adding the cones for
		 * star to their individual transform groups then adding the individual
		 * transform groups for the star to a transform group that acts as a
		 * transform group for the whole star
		 * 
		 */
		Cone coneBaseForStar = new Cone(3f, 4f, yellowAppForStar);
		coneBaseForStar.setCollidable(false);

		Cone coneBaseForStar2 = new Cone(3f, 4f, yellowAppForStar);
		coneBaseForStar2.setCollidable(false);

		coneTGforStarOnTopOfTree2.addChild(coneBaseForStar2);
		coneTGforStarOnTopOfTree.addChild(coneBaseForStar);
		starTGforStarOnTopTree.addChild(coneTGforStarOnTopOfTree2);
		starTGforStarOnTopTree.addChild(coneTGforStarOnTopOfTree);

		/***************************************************************************************************************************
		 * making star rotate using a rotation interpolator
		 * 
		 ***************************************************************************************************************************/

		TransformGroup TGForStarRotation = new TransformGroup();
		// set the capability to TRANSFORM the TGA (by ROTATION)
		TGForStarRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		TGForStarRotation.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		Transform3D yAxisStarTG = new Transform3D();
		Alpha rotationAlphaStarTG = new Alpha(-1, 4000);
		// you can set the time for the Alpha object to begin (eg after 10
		// seconds)
		// rotationAlphaboxTG2.setStartTime(System.currentTimeMillis() + 10000);
		// this rotator is for boxTG2 ie box b
		RotationInterpolator rotatorboxTG2 = new RotationInterpolator(rotationAlphaStarTG, starTGforStarOnTopTree,
				yAxisStarTG, 1.0f, (float) Math.PI * (2.0f));
		rotatorboxTG2.setSchedulingBounds(bounds);

		TGForStarRotation.addChild(starTGforStarOnTopTree);
		starTGforStarOnTopTree.addChild(rotatorboxTG2);// rotatorSphereTG
		mainTG.addChild(TGForStarRotation);

		/***************************************************************************************************************************
		 * making star decoration for tree
		 * 
		 ***************************************************************************************************************************/

		Transform3D decorations = new Transform3D();
		TransformGroup decorationsTG = new TransformGroup(decorations);
		/*
		 * creating 3d transform object to set the scaling and translation of
		 * the individual cones used for star creating individual transform
		 * groups for the star objects and adding the 3d objects previously
		 * created creating a 3d object used for the whole star, in order to
		 * position it on top of the tree and to set the scaling of the star as
		 * a whole creating a transform group for the whole star decoration and
		 * setting the 3d object as a parameter
		 */
		Transform3D coneForStarDecoration = new Transform3D();
		coneForStarDecoration.setTranslation(new Vector3d(0.0, 5.9, 5.0));
		coneForStarDecoration.setScale(new Vector3d(.4, .4, .2));
		TransformGroup coneTGforStarDecoration = new TransformGroup(coneForStarDecoration);

		Transform3D coneForStar2Decoration = new Transform3D();
		coneForStar2Decoration.setTranslation(new Vector3d(0.0, 5.2, 5.0));
		coneForStar2Decoration.setScale(new Vector3d(-.4, -.4, -.2));
		TransformGroup coneTGforStar2Decoration = new TransformGroup(coneForStar2Decoration);

		Transform3D starforStarDecoration = new Transform3D();
		starforStarDecoration.setScale(new Vector3d(0.5, 0.5, 1));
		starforStarDecoration.setTranslation(new Vector3d(1.0, 0.0, 0.0));
		TransformGroup starTGforStarDecoration = new TransformGroup(starforStarDecoration);
		starTGforStarDecoration.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		starTGforStarDecoration.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

		// yellow app for star decoration
		Appearance yellowApp = new Appearance();
		Color3f yellowColor = new Color3f(1.000f, 1.000f, 0.000f);
		ColoringAttributes yellowCA = new ColoringAttributes();
		yellowCA.setColor(yellowColor);
		yellowApp.setColoringAttributes(yellowCA);

		// orange app for star decoration

		Appearance orangeApp = new Appearance();
		Color3f orangeColor = new Color3f(1.000f, 0.549f, 0.000f);
		ColoringAttributes orangeCA = new ColoringAttributes();
		orangeCA.setColor(orangeColor);
		orangeApp.setColoringAttributes(orangeCA);

		// creating the shapes used for decoration and setting the collidable to
		// stop it from interacting with my collision detection
		Cone coneBaseForStarDecoration = new Cone(3f, 4f, orangeApp);
		coneBaseForStarDecoration.setCollidable(false);
		Cone coneBaseForStar2Decoration = new Cone(3f, 4f, orangeApp);
		coneBaseForStar2Decoration.setCollidable(false);
		// adding the shapes to their individual transform groups previously
		// created
		coneTGforStarDecoration.addChild(coneBaseForStarDecoration);
		coneTGforStar2Decoration.addChild(coneBaseForStar2Decoration);
		// adding the individual transform groups to the transform group for the
		// whole star
		starTGforStarDecoration.addChild(coneTGforStarDecoration);
		starTGforStarDecoration.addChild(coneTGforStar2Decoration);
		decorationsTG.addChild(starTGforStarDecoration);

		/***************************************************************************************************************************
		 * making star another star decoration for tree
		 * 
		 ***************************************************************************************************************************/
		/*
		 * creating 3d transform object to set the scaling and translation of
		 * the individual cones used for star creating individual transform
		 * groups for the star objects and adding the 3d objects previously
		 * created creating a 3d object used for the whole star, in order to
		 * position it on top of the tree and to set the scaling of the star as
		 * a whole creating a transform group for the whole star decoration and
		 * setting the 3d object as a parameter
		 */
		Transform3D coneForStarDecoration2 = new Transform3D();
		coneForStarDecoration2.setTranslation(new Vector3d(0.0, 5.9, 5.0));
		coneForStarDecoration2.setScale(new Vector3d(.4, .4, .2));
		TransformGroup coneTGforStarDecoration2 = new TransformGroup(coneForStarDecoration2);

		Transform3D coneForStar2Decoration2 = new Transform3D();
		coneForStar2Decoration2.setTranslation(new Vector3d(0.0, 5.2, 5.0));
		coneForStar2Decoration2.setScale(new Vector3d(-.4, -.4, -.2));
		TransformGroup coneTGforStar2Decoration2 = new TransformGroup(coneForStar2Decoration2);

		Transform3D starforStarDecoration2 = new Transform3D();
		starforStarDecoration2.setScale(new Vector3d(0.5, 0.5, 1));
		starforStarDecoration2.setTranslation(new Vector3d(-2.0, -2.0, 0.0));
		TransformGroup starTGforStarDecoration2 = new TransformGroup(starforStarDecoration2);
		starTGforStarDecoration2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		starTGforStarDecoration2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		// creating lightblueApp for star decoration on tree
		Appearance lightBlueApp = new Appearance();
		Color3f lightBlueColor = new Color3f(0.000f, 1.000f, 1.000f);
		ColoringAttributes lightBlueCA = new ColoringAttributes();
		lightBlueCA.setColor(lightBlueColor);
		lightBlueApp.setColoringAttributes(lightBlueCA);
		// creating the shapes used for decoration and setting the collidable to
		// stop it from interacting with my collision detection
		Cone coneBaseForStarDecoration2 = new Cone(3f, 4f, lightBlueApp);
		coneBaseForStarDecoration2.setCollidable(false);
		Cone coneBaseForStar2Decoration2 = new Cone(3f, 4f, lightBlueApp);
		coneBaseForStar2Decoration2.setCollidable(false);
		// adding the shapes to their individual transform groups previously
		// created
		coneTGforStarDecoration2.addChild(coneBaseForStarDecoration2);
		coneTGforStar2Decoration2.addChild(coneBaseForStar2Decoration2);
		// adding the individual transform groups to the transform group for the
		// whole star
		starTGforStarDecoration2.addChild(coneTGforStarDecoration2);
		starTGforStarDecoration2.addChild(coneTGforStar2Decoration2);
		decorationsTG.addChild(starTGforStarDecoration2);
		/***************************************************************************************************************************
		 * making star another star decoration for tree
		 * 
		 ***************************************************************************************************************************/
		/*
		 * creating 3d transform object to set the scaling and translation of
		 * the individual cones used for star creating individual transform
		 * groups for the star objects and adding the 3d objects previously
		 * created creating a 3d object used for the whole star, in order to
		 * position it on top of the tree and to set the scaling of the star as
		 * a whole creating a transform group for the whole star decoration and
		 * setting the 3d object as a parameter
		 */
		Transform3D coneForStarDecoration3 = new Transform3D();
		coneForStarDecoration3.setTranslation(new Vector3d(0.0, 5.9, 5.0));
		coneForStarDecoration3.setScale(new Vector3d(.4, .4, .2));
		TransformGroup coneTGforStarDecoration3 = new TransformGroup(coneForStarDecoration3);

		Transform3D coneForStar2Decoration3 = new Transform3D();
		coneForStar2Decoration3.setTranslation(new Vector3d(0.0, 5.2, 5.0));
		coneForStar2Decoration3.setScale(new Vector3d(-.4, -.4, -.2));
		TransformGroup coneTGforStar2Decoration3 = new TransformGroup(coneForStar2Decoration3);

		Transform3D starforStarDecoration3 = new Transform3D();
		starforStarDecoration3.setScale(new Vector3d(0.5, 0.5, 1));
		starforStarDecoration3.setTranslation(new Vector3d(2.0, -4.5, 0.0));
		TransformGroup starTGforStarDecoration3 = new TransformGroup(starforStarDecoration3);
		starTGforStarDecoration3.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		starTGforStarDecoration3.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		// creating purpleApp for star decoration on tree

		Appearance purpleApp = new Appearance();
		Color3f purpleColor = new Color3f(1.000f, 0.000f, 1.000f);
		ColoringAttributes purpleCA = new ColoringAttributes();
		purpleCA.setColor(purpleColor);
		purpleApp.setColoringAttributes(purpleCA);
		// creating the shapes used for decoration and setting the collidable to
		// stop it from interacting with my collision detection
		Cone coneBaseForStarDecoration3 = new Cone(3f, 4f, purpleApp);
		coneBaseForStarDecoration3.setCollidable(false);
		Cone coneBaseForStar2Decoration3 = new Cone(3f, 4f, purpleApp);
		coneBaseForStar2Decoration3.setCollidable(false);
		// adding the shapes to their individual transform groups previously
		// created
		coneTGforStarDecoration3.addChild(coneBaseForStarDecoration3);
		coneTGforStar2Decoration3.addChild(coneBaseForStar2Decoration3);
		// adding the individual transform groups to the transform group for the
		// whole star
		starTGforStarDecoration3.addChild(coneTGforStarDecoration3);
		starTGforStarDecoration3.addChild(coneTGforStar2Decoration3);
		decorationsTG.addChild(starTGforStarDecoration3);
		/*********************************************************************************************************
		 * Creating spheres used as decorations on the tree
		 ********************************************************************************************************/
		/*
		 * creating a transform3D object for the spheres setting the
		 * translations for the transform3D object creating a transform group
		 * for the transForm 3D object then adding it to the
		 */
		Transform3D sphere1 = new Transform3D();
		sphere1.setTranslation(new Vector3d(-2.5, 3.2, 3.0));
		TransformGroup sphereTG1 = new TransformGroup(sphere1);

		Transform3D sphere2 = new Transform3D();
		sphere2.setTranslation(new Vector3d(-4.5, 5.0, 2.0));
		TransformGroup sphereTG2 = new TransformGroup(sphere2);

		Transform3D sphere3 = new Transform3D();
		sphere3.setTranslation(new Vector3d(4.5, 1.0, 0.0));
		TransformGroup sphereTG3 = new TransformGroup(sphere3);

		Transform3D sphere4 = new Transform3D();
		sphere4.setTranslation(new Vector3d(4.8, 4.9, 0.0));
		TransformGroup sphereTG4 = new TransformGroup(sphere4);

		Transform3D sphere5 = new Transform3D();
		sphere5.setTranslation(new Vector3d(-6.5, -1.5, 0.0));
		TransformGroup sphereTG5 = new TransformGroup(sphere5);

		Transform3D sphere6 = new Transform3D();
		sphere6.setTranslation(new Vector3d(-1.5, -2.5, 5.9));
		TransformGroup sphereTG6 = new TransformGroup(sphere6);

		Transform3D sphere7 = new Transform3D();
		sphere7.setTranslation(new Vector3d(-.4, 6.5, 4.2));
		TransformGroup sphereTG7 = new TransformGroup(sphere7);

		Transform3D sphere9 = new Transform3D();
		sphere9.setTranslation(new Vector3d(2, 4.5, 4.2));
		TransformGroup sphereTG9 = new TransformGroup(sphere9);

		Transform3D sphere10 = new Transform3D();
		sphere10.setTranslation(new Vector3d(-4.7, 1.3, 4.2));
		TransformGroup sphereTG10 = new TransformGroup(sphere10);

		Transform3D sphere15 = new Transform3D();
		sphere15.setTranslation(new Vector3d(-2.0, 4.5, 4.2));
		TransformGroup sphereTG15 = new TransformGroup(sphere15);
		// red app for spheres
		Color3f ambientsphereColour1 = new Color3f(1.000f, 0.000f, 0.000f);
		Color3f emissivesphereColour2 = new Color3f(0.0f, 0.0f, 0.0f);
		Color3f diffusesphereColour3 = new Color3f(1.000f, 0.000f, 0.000f);
		Color3f specularsphereColour4 = new Color3f(1.000f, 0.000f, 0.000f);
		// Generate the appearance and apply material to cube
		float shininessSphere = 5.0f;

		Appearance sphereAppRed = new Appearance();
		sphereAppRed.setMaterial(new Material(ambientsphereColour1, emissivesphereColour2, diffusesphereColour3,
				specularsphereColour4, shininessSphere));
		sphereAppRed.setPolygonAttributes(

				new PolygonAttributes(PolygonAttributes.POLYGON_LINE,

						PolygonAttributes.CULL_BACK, 0.0f));

		// blue app for spheres
		Appearance blueApp2 = new Appearance();
		Color3f blueColor2 = new Color3f(0.000f, 0.000f, 1.000f);
		ColoringAttributes blueCA2 = new ColoringAttributes();
		blueCA2.setColor(blueColor2);
		blueApp2.setColoringAttributes(blueCA2);
		blueApp2.setPolygonAttributes(

				new PolygonAttributes(PolygonAttributes.POLYGON_LINE,

						PolygonAttributes.CULL_BACK, 0.0f));

		// creating the spheres used as designs on my tree
		Sphere sphere = new Sphere(0.4f, sphereAppRed);
		sphere.setCollidable(false);
		Sphere sphereTwo = new Sphere(0.4f, sphereAppRed);
		sphereTwo.setCollidable(false);
		Sphere sphereThree = new Sphere(0.4f, sphereAppRed);
		sphereThree.setCollidable(false);
		Sphere sphereFour = new Sphere(0.4f, sphereAppRed);
		sphereFour.setCollidable(false);
		Sphere sphereFive = new Sphere(0.4f, sphereAppRed);
		sphereFive.setCollidable(false);
		Sphere sphereSix = new Sphere(0.4f, sphereAppRed);
		sphereSix.setCollidable(false);
		Sphere sphereSeven = new Sphere(0.4f, sphereAppRed);
		sphereSeven.setCollidable(false);
		Sphere sphereNine = new Sphere(0.4f, blueApp2);
		sphereNine.setCollidable(false);
		Sphere sphereTen = new Sphere(0.4f, blueApp2);
		sphereTen.setCollidable(false);
		Sphere sphereFifteen = new Sphere(0.4f, yellowAppForStar);
		sphereFifteen.setCollidable(false);
		// setting the sphere shapes created as a child of their individual
		// transform groups previously created.
		sphereTG7.addChild(sphereSeven);
		sphereTG1.addChild(sphere);
		sphereTG3.addChild(sphereThree);
		sphereTG2.addChild(sphereTwo);
		sphereTG4.addChild(sphereFour);
		sphereTG5.addChild(sphereFive);
		sphereTG6.addChild(sphereSix);
		sphereTG9.addChild(sphereNine);
		sphereTG10.addChild(sphereTen);
		sphereTG15.addChild(sphereFifteen);
		// setting the individual transform groups as children of the tranform
		// group for all sphere decorations.
		decorationsTG.addChild(sphereTG7);
		decorationsTG.addChild(sphereTG1);
		decorationsTG.addChild(sphereTG2);
		decorationsTG.addChild(sphereTG3);
		decorationsTG.addChild(sphereTG4);
		decorationsTG.addChild(sphereTG5);
		decorationsTG.addChild(sphereTG6);
		decorationsTG.addChild(sphereTG9);
		decorationsTG.addChild(sphereTG10);
		decorationsTG.addChild(sphereTG15);
		// adding the decorations to the main tg
		mainTG.addChild(decorationsTG);

		/***************************************************************************************************************************
		 * moving snow balls snow balls
		 * 
		 ***************************************************************************************************************************/

		int numberofLayers = 30;
		// creating a white appearance for the snow balls.
		Appearance whiteApp = new Appearance();
		Color3f whiteColor = new Color3f(1.000f, 1.000f, 1.000f);
		ColoringAttributes whiteCA = new ColoringAttributes();
		whiteCA.setColor(whiteColor);
		whiteApp.setColoringAttributes(whiteCA);
		TransformGroup TGForSphereMovement = new TransformGroup();
		TGForSphereMovement.setCollidable(false);
		mainTG.addChild(TGForSphereMovement);

		// a for loop to create the layers of snow and to make the white spheres
		// fall
		for (int i = 0; i < numberofLayers; i++) {
			TransformGroup movingSphereTG = new TransformGroup();
			movingSphereTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			movingSphereTG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

			// set the capability to TRANSFORM the TGA (by ROTATION)
			TGForSphereMovement.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
			TGForSphereMovement.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
			// stransform 3d object to make the snow appear as if it is falling
			// from the sky
			Transform3D yAxisSphereTG = new Transform3D();
			yAxisSphereTG.rotZ(-Math.PI / 2);
			Alpha positionAlphaTG = new Alpha(-1, 4000);
			// position interpolator to make the snow move
			PositionInterpolator positionSphereTG = new PositionInterpolator(positionAlphaTG, movingSphereTG,
					yAxisSphereTG, -40.0f, (float) Math.PI * (2.0f));
			positionSphereTG.setSchedulingBounds(bounds);

			int numberOfLayers = 40;
			int numsnow = 20;
			for (int t = 0; t < numberOfLayers; t++) {
				Sphere snow[] = new Sphere[numsnow];

				Transform3D spheres[] = new Transform3D[numsnow];
				TransformGroup sphereTG[] = new TransformGroup[numsnow];
				for (int k = 0; k < numsnow; k++) {
					snow[k] = new Sphere(0.1f, whiteApp);
					snow[k].setCollidable(false);
					spheres[k] = new Transform3D();
					spheres[k].setTranslation(randomgenerator());
					sphereTG[k] = new TransformGroup(spheres[k]);

				}

				for (int j = 0; j < numsnow; j++) {
					movingSphereTG.addChild(sphereTG[j]);
					sphereTG[j].addChild(snow[j]);
					sphereTG[j].setCollidable(false);
				}
			}

			TGForSphereMovement.addChild(movingSphereTG);
			movingSphereTG.addChild(positionSphereTG);

		}
		/***************************************************************************************************************************
		 * Creating presents underneath tree
		 **************************************************************************************************************************/

		/*
		 * creating a transform 3d object to set the scaling and translating of
		 * the transform 3D object creating a transform group for yellow rapping
		 * paper and setting the transform 3d object as a parameter
		 */
		Transform3D wrappingPapetYellow = new Transform3D();
		wrappingPapetYellow.setTranslation(new Vector3d(-10, -6.9, 4.5));
		wrappingPapetYellow.setScale(new Vector3d(1.0, 1.0, .5));
		TransformGroup wrappingPaperTGYellow = new TransformGroup(wrappingPapetYellow);

		Transform3D wrappingPaperYellow2 = new Transform3D();
		wrappingPaperYellow2.rotY(Math.PI / 2);
		wrappingPaperYellow2.setScale(new Vector3d(1.4, 1.0, .5));
		wrappingPaperYellow2.setTranslation(new Vector3d(-10, -6.5, 4.5));
		TransformGroup wrappingPaperYellowTG2 = new TransformGroup(wrappingPaperYellow2);
		// box for red present transform 3d object and transform group
		Transform3D redPresent = new Transform3D();
		redPresent.setTranslation(new Vector3d(-10, -7, 4.5));
		TransformGroup redPresentTG = new TransformGroup(redPresent);
		// transForm group for red present, allowing me to move all the shapes
		// together
		Transform3D firstPresentRed = new Transform3D();
		firstPresentRed.setTranslation(new Vector3d(6.9, -1.8, -5.5));
		TransformGroup presentRedBoxTG = new TransformGroup(firstPresentRed);
		// box for blue present transform 3d and transform group
		Transform3D blueBox = new Transform3D();
		blueBox.setScale(new Vector3d(1.0, .7, .5));
		blueBox.setTranslation(new Vector3d(-3, -6.5, 6.4));
		TransformGroup blueBoxTG = new TransformGroup(blueBox);
		// White wrapping transform 3d and transform group
		Transform3D whiteBoxWrapping = new Transform3D();
		whiteBoxWrapping.rotY(Math.PI / 2);
		whiteBoxWrapping.setScale(new Vector3d(2.0, 1.0, 0.1));
		whiteBoxWrapping.setTranslation(new Vector3d(-3, -6.5, 6.4));
		TransformGroup whiteBoxWrappingTG = new TransformGroup(whiteBoxWrapping);
		// Second white wrapping transform 3d object and transform group
		Transform3D whiteBoxWrappingTwo = new Transform3D();
		whiteBoxWrappingTwo.rotX(Math.PI / 2);
		whiteBoxWrappingTwo.setScale(new Vector3d(1.01, .5, .5));
		whiteBoxWrappingTwo.setTranslation(new Vector3d(-3.0, -6.5, 6.4));
		TransformGroup whiteBoxWrappingTGTwo = new TransformGroup(whiteBoxWrappingTwo);
		// second presentBlue transform 3d and transform group
		Transform3D secondpresentPresentBlue = new Transform3D();
		secondpresentPresentBlue.setTranslation(new Vector3d(6.2, -2.5, -7.5));
		TransformGroup secondpresentBoxTGBlue = new TransformGroup(secondpresentPresentBlue);
		// creating a red appearance for gifts under christmass tree
		Appearance redApp = new Appearance();
		Color3f redColor = new Color3f(1.000f, 0.000f, 0.000f);
		ColoringAttributes redCA = new ColoringAttributes();
		redCA.setColor(redColor);
		redApp.setColoringAttributes(redCA);
		// creating a blue appearance for gifts under christmass tree
		Appearance blueApp = new Appearance();
		Color3f blueColor = new Color3f(0.000f, 0.000f, 1.000f);
		ColoringAttributes blueCA = new ColoringAttributes();
		blueCA.setColor(blueColor);
		blueApp.setColoringAttributes(blueCA);
		// Red present with yellow wrapping
		Box yellowWrapping = new Box(0.8f, 0.8f, .1f, yellowApp);
		yellowWrapping.setCollidable(false);
		Box redBox = new Box(0.8f, 0.8f, 1f, redApp);
		redBox.setCollidable(false);
		Box yellowWrapping2 = new Box(0.8f, 0.4f, .1f, yellowApp);
		yellowWrapping2.setCollidable(false);
		// blue present with white wrapping
		Box blueBoxPresent = new Box(0.8f, 0.8f, 2f, blueApp);
		blueBoxPresent.setCollidable(false);
		Box whiteBoxwrapping = new Box(0.5f, 0.6f, 1.3f, whiteApp);
		whiteBoxwrapping.setCollidable(false);
		Box whiteBoxwrapping2 = new Box(0.9f, 0.5f, 1.28f, whiteApp);
		whiteBoxwrapping2.setCollidable(false);

		/*adding the shapes created above to their individual transform groups 
		 * adding the transform groups to the transform groups for the individual present
		 * adding the transform groups for the individual presents to the main tg
		 */
		wrappingPaperTGYellow.addChild(yellowWrapping);
		redPresentTG.addChild(redBox);
		wrappingPaperYellowTG2.addChild(yellowWrapping2);

		blueBoxTG.addChild(blueBoxPresent);
		whiteBoxWrappingTG.addChild(whiteBoxwrapping);
		whiteBoxWrappingTGTwo.addChild(whiteBoxwrapping2);

		presentRedBoxTG.addChild(wrappingPaperYellowTG2);
		presentRedBoxTG.addChild(redPresentTG);
		presentRedBoxTG.addChild(wrappingPaperTGYellow);
		//setting collidable to false for collision purposes
		presentRedBoxTG.setCollidable(false);

		secondpresentBoxTGBlue.addChild(blueBoxTG);
		secondpresentBoxTGBlue.addChild(whiteBoxWrappingTG);
		secondpresentBoxTGBlue.addChild(whiteBoxWrappingTGTwo);

		mainTG.addChild(presentRedBoxTG);
		mainTG.addChild(secondpresentBoxTGBlue);

		/*********************************************************************************************************
		 * //snow man
		 * 
		 ********************************************************************************************************/

		//creating a black appearance forsnow mans hat
		Appearance blackAppForSnowMansHat = new Appearance();
		Color3f blackColor2 = new Color3f(0.000f, 0.000f, 0.000f);
		ColoringAttributes blackCA2 = new ColoringAttributes();
		blackCA2.setColor(blackColor2);
		blackAppForSnowMansHat.setColoringAttributes(blackCA2);
		
		int numberOfSnowMenSpheres = 3;
		int heightOfSphereOnYAxis = -4;
		float scaleOfSphersForSnowMen = 5.4f;
//		transform group for snowman on the right of the tree
		TransformGroup spheresTGSnowMan1 = new TransformGroup();
		// creating a sphere shape used for snow men
		Sphere sphereSnowMan1 = new Sphere();
		// a for loop to create the body of a snowman by adding spheres on top of eachother
		for (int i = 0; i < numberOfSnowMenSpheres; i++) {
			Transform3D sphereSnowMan = new Transform3D();
			// sphereSnowMan.setScale(new Vector3d(scale,scale ,scale));
			sphereSnowMan.setTranslation(new Vector3d(-7.5, heightOfSphereOnYAxis, 0.0));
			TransformGroup sphereTGSnowMan = new TransformGroup(sphereSnowMan);

			TransformGroup spheresTGSnowManTGforAllBody = new TransformGroup(sphereSnowMan);

			sphereSnowMan1 = new Sphere(scaleOfSphersForSnowMen, whiteApp);
			sphereTGSnowMan.addChild(sphereSnowMan1);

			spheresTGSnowManTGforAllBody.addChild(sphereTGSnowMan);
			spheresTGSnowMan1.addChild(spheresTGSnowManTGforAllBody);

			scaleOfSphersForSnowMen -= 1.2;
			heightOfSphereOnYAxis += 2.5;
		}

		int numberOfSpheresForSnowMen = 3;
		int heightOfSphereOnYAxis2 = -4;
		float scaleOfSperesforsnowmen2 = 5.4f;
		TransformGroup spheresTGSnowManTGforAll2 = new TransformGroup();
		spheresTGSnowManTGforAll2.setCollidable(false);
		//a for loop to make a second snow man on the left

		for (int i = 0; i < numberOfSpheresForSnowMen; i++) {
			Transform3D sphereSnowMan2 = new Transform3D();
			// sphereSnowMan.setScale(new Vector3d(scale,scale ,scale));
			sphereSnowMan2.setTranslation(new Vector3d(7.5, heightOfSphereOnYAxis2, 0.0));
			TransformGroup sphereTGSnowMan2 = new TransformGroup(sphereSnowMan2);

			spheresTGSnowManTGforAll2 = new TransformGroup(sphereSnowMan2);

			Sphere sphereSnowMan3 = new Sphere(scaleOfSperesforsnowmen2, whiteApp);
			sphereTGSnowMan2.addChild(sphereSnowMan3);

			spheresTGSnowManTGforAll2.addChild(sphereTGSnowMan2);
			mainTG.addChild(spheresTGSnowManTGforAll2);
			scaleOfSperesforsnowmen2 -= 1.2;
			heightOfSphereOnYAxis2 += 2.5;
		}
		// buttons for snow man on the left
		float scaleButton = .4f;
		int heightOfSphereOnYAxisOfButtons = -1;
		int numberOfButtons = 4;
		TransformGroup spheresTGSnowManTGforAllButtons = new TransformGroup();
		spheresTGSnowManTGforAllButtons.setCollidable(false);
		for (int i = 0; i < numberOfButtons; i++) {
			Transform3D sphereButtonsForSnowMann = new Transform3D();
			// sphereSnowMan.setScale(new Vector3d(scale,scale ,scale));
			sphereButtonsForSnowMann.setTranslation(new Vector3d(-7.0, heightOfSphereOnYAxisOfButtons, 3.5));
			TransformGroup sphereTGButtonsForSnowMan = new TransformGroup(sphereButtonsForSnowMann);
			sphereTGButtonsForSnowMan.setCollidable(false);
			spheresTGSnowManTGforAllButtons = new TransformGroup(sphereButtonsForSnowMann);

			sphereSnowMan1 = new Sphere(scaleButton, redApp);
			sphereSnowMan1.setCollidable(false);

			sphereTGButtonsForSnowMan.addChild(sphereSnowMan1);

			spheresTGSnowManTGforAllButtons.addChild(sphereTGButtonsForSnowMan);
			spheresTGSnowMan1.addChild(spheresTGSnowManTGforAllButtons);
			heightOfSphereOnYAxisOfButtons -= 1.0;

		}
		
		// buttons for snow man on the right
		float scaleButton2 = .4f;
		int heightOfSphereOnYAxisOfButtons2 = -1;
		int numberOfButtons2 = 4;
		// a transform group for each individual button on the snow man
		TransformGroup sphereTGButtonsForSnowMan2 = new TransformGroup();
		//transform group for the buttons on snow man
		TransformGroup spheresTGSnowManTGforAllButtons2 = new TransformGroup();
		spheresTGSnowManTGforAllButtons2.setCollidable(false);

		//a for loop to create the buttons on snow man
		for (int i = 0; i < numberOfButtons2; i++) {
			Transform3D sphereButtonsForSnowMann2 = new Transform3D();
			sphereButtonsForSnowMann2.setTranslation(new Vector3d(7.0, heightOfSphereOnYAxisOfButtons2, 3.5));
			 sphereTGButtonsForSnowMan2 = new TransformGroup(sphereButtonsForSnowMann2);

			spheresTGSnowManTGforAllButtons2 = new TransformGroup(sphereButtonsForSnowMann2);

			Sphere sphereSnowMan2 = new Sphere(scaleButton2, blackAppForSnowMansHat);
			sphereSnowMan2.setCollidable(false);
			sphereTGButtonsForSnowMan2.addChild(sphereSnowMan2);

			spheresTGSnowManTGforAllButtons2.addChild(sphereTGButtonsForSnowMan2);
			mainTG.addChild(spheresTGSnowManTGforAllButtons2);
			heightOfSphereOnYAxisOfButtons2 -= 1.0;

		}
		//transform 3d object for the snow mans hat
		Transform3D cylinderForSnowManHat = new Transform3D();
		cylinderForSnowManHat.setScale(new Vector3d(-2, -2, -2));
		cylinderForSnowManHat.setTranslation(new Vector3d(-14.5, -10.9, 1.2));
		//a transform group for the base of the snow mans hat
		TransformGroup cylinderForSnowManHatTG = new TransformGroup(cylinderForSnowManHat);
		
		//transform 3d object that add both the top and base of the snow mans hat
		Transform3D snowMansHat = new Transform3D();
		snowMansHat.setTranslation(new Vector3d(0.0, 15.6, 0.0));
		//transform group that add both the top and base of the snow mans hat
		TransformGroup snowMansHatTG = new TransformGroup(snowMansHat);
		spheresTGSnowMan1.addChild(snowMansHatTG);
		//creating a cylinder shape for snow mans hat
		Cylinder cylinderHat = new Cylinder(1.9f, .2f, redApp);
		cylinderHat.setCollidable(false);
		//creating another cylinder shape for collision purposes
		Cylinder cylinderHatSecondColor = new Cylinder(1.9f, .2f, lightBlueApp);
		cylinderHatSecondColor.setCollidable(false);
		snowMansHatTG.addChild(cylinderForSnowManHatTG);
		// declaring a colour switch of the red snow mans hat so it changes to
		// light blue
		Switch colourSwitchforHatBase = new Switch();
		colourSwitchforHatBase.setCapability(Switch.ALLOW_SWITCH_WRITE);
		
		// The red cylinder should be visible in the beginning.
		colourSwitchforHatBase.setWhichChild(0);
		colourSwitchforHatBase.addChild(cylinderHat); // child 0
		colourSwitchforHatBase.addChild(cylinderHatSecondColor); // child 1
		// A transformation group for the Switch (the cylinder).
		cylinderForSnowManHatTG.addChild(colourSwitchforHatBase);
		BoundingSphere boundsForcolision = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), Double.MAX_VALUE);
		// The CollisionBounds for the cube.
		sphereSnowMan1.setCollisionBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 0f));

		// The CollisionBounds for the cylinders
		// when we want objects to collide we neeed to detech when they colide.
		// we do this by setting the colision bounds
		colourSwitchforHatBase.setCollisionBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1.0));
		// Enabled for collision purposes
		colourSwitchforHatBase.setCollidable(true);

		// creating a new transform 3d object for the top of the red snow mans
		// hat
		Transform3D cylinderForSnowManHatTop = new Transform3D();
		cylinderForSnowManHatTop.setTranslation(new Vector3d(-14.5, -9.2, 1));
		// creating a transform group for the top of the red snow mans hat
		TransformGroup cylinderForSnowManHatTGTop = new TransformGroup(cylinderForSnowManHatTop);
		// creating a shape for the top of the red snow mans hat
		Cylinder cylinderHatTop = new Cylinder(2.3f, 4f, redApp);
		// creating a new cylinder shape for collision purposes
		Cylinder cylinderHatTopForColisionDetection = new Cylinder(2.3f, 4f, lightBlueApp);
		// disabled for collision purposes
		cylinderHatTopForColisionDetection.setCollidable(false);
		snowMansHatTG.addChild(cylinderForSnowManHatTGTop);
		// declaring a colour switch of the red snow mans hat so it changes to
		// light blue
		Switch colourSwitchFortopOfRedHat = new Switch();
		colourSwitchFortopOfRedHat.setCapability(Switch.ALLOW_SWITCH_WRITE);
		colourSwitchFortopOfRedHat.setWhichChild(0);
		// The Switch node controls which of its children will be rendered.
		// Add the spheres to the Switch.
		colourSwitchFortopOfRedHat.addChild(cylinderHatTop); // child 0
		colourSwitchFortopOfRedHat.addChild(cylinderHatTopForColisionDetection); // child1
		// The light blue sphere should be visible in the beginning.
		// A transformation group for the Switch (the spheres).
		cylinderForSnowManHatTGTop.addChild(colourSwitchFortopOfRedHat);
		// The CollisionBounds for the snowman.
		sphereSnowMan1.setCollisionBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1.0 * 0.2f));
		colourSwitchFortopOfRedHat.setCollisionBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1000.0));
		// disabled for collision purposes
		colourSwitchFortopOfRedHat.setCollidable(false);

		//  snow man on left hat transform 3d
		Transform3D cylinderForSnowManHat2 = new Transform3D();
		cylinderForSnowManHat2.setScale(new Vector3d(-2, -2, -2));
		cylinderForSnowManHat2.setTranslation(new Vector3d(14.5, -10.9, 1.2));
		// a transform group for the black hat on the snow man on the right
		TransformGroup cylinderForSnowManHatTG2 = new TransformGroup(cylinderForSnowManHat2);
		//disabled for collision purposes
		cylinderForSnowManHatTG2.setCollidable(false);
		Transform3D snowMansHat2 = new Transform3D();
		snowMansHat2.setTranslation(new Vector3d(0.0, 15.6, 0.0));
		TransformGroup snowMansHatTG2 = new TransformGroup(snowMansHat2);
		// shape for creating the base of the snow mans hat
		Cylinder cylinderHat2 = new Cylinder(1.9f, .2f, blackAppForSnowMansHat);
		// disabled for collision purposes
		cylinderHat2.setCollidable(false);
		cylinderForSnowManHatTG2.addChild(cylinderHat2);
		snowMansHatTG2.addChild(cylinderForSnowManHatTG2);
		mainTG.addChild(snowMansHatTG2);

		Transform3D cylinderForSnowManHatTop2 = new Transform3D();

		cylinderForSnowManHatTop2.setTranslation(new Vector3d(14.5, -9.2, 1));
		// transform group for the start of the snow mans hat
		TransformGroup cylinderForSnowManHatTGTop2 = new TransformGroup(cylinderForSnowManHatTop2);
		// creating a cylindershape for the top of the snow mans hat
		Cylinder cylinderHatTop2 = new Cylinder(2.3f, 4f, blackAppForSnowMansHat);
		cylinderHatTop2.setCollidable(false);
		cylinderForSnowManHatTGTop2.addChild(cylinderHatTop2);
		snowMansHatTG2.addChild(cylinderForSnowManHatTGTop2);

		// This transformation group is needed for the movement of the
		// redsnowman.
		TransformGroup tgmSnowManRed = new TransformGroup();
		tgmSnowManRed.addChild(spheresTGSnowMan1);
		// The movement from left to right (along the x axis)
		Transform3D xAxis = new Transform3D();
		float maxRight = 6f;

		// An alpha for the left to right movement
		// Alpha(number_of_times_for_movement,time_movement_takes)
		Alpha snowManRedAlphaR = new Alpha(1, 5000);
		// The starting time is first postponed until "infinity".
		// cylAlphaR.setStartTime(Long.MAX_VALUE);
		// The interpolator for the movement.
		// PosInt(theAlpha, TGroup_to_attach_to,
		// axis_of_movement_default_X_Axis, start_position, end_position)
		PositionInterpolator cylMoveR = new PositionInterpolator(snowManRedAlphaR, tgmSnowManRed, xAxis, 0.0f,
				maxRight);
		// An alpha for the right to left movement
		Alpha snowManRedHatAlphaL = new Alpha(-1, 4000);
		// The starting time is first postponed for 10 seconds".
		// The interpolator for the movement.
		PositionInterpolator cylMoveL = new PositionInterpolator(snowManRedHatAlphaL, tgmSnowManRed, xAxis, maxRight,
				0.0f);
		// API: The scheduling region defines a spatial volume
		// that serves to enable the scheduling of Behavior nodes.
		cylMoveR.setSchedulingBounds(bounds);
		cylMoveL.setSchedulingBounds(bounds);

		// Add the movements to the transformation group.
		tgmSnowManRed.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tgmSnowManRed.addChild(cylMoveR);
		tgmSnowManRed.addChild(cylMoveL);

		// adding the snowman to the main tg
		mainTG.addChild(tgmSnowManRed);
		// --- end cylinder movement --
		/*********************************************************************************************************
		 * Drwing a cube, and nesting a blue sphere inside of it
		 ********************************************************************************************************/
		// creating an appearance for my cube shape
		Appearance appForDrawnShap = new Appearance();

		QuadArray cubeArray = new QuadArray(48, QuadArray.COORDINATES | QuadArray.NORMALS);
		Vector3f[] normals = new Vector3f[24];
		for (int i = 0; i < 24; i++)
			normals[i] = new Vector3f();
		Point3f[] pts = new Point3f[24];
		for (int i = 0; i < 24; i++)
			pts[i] = new Point3f();
		Color3f[] clrs = new Color3f[24];
		for (int i = 0; i < 24; i++)
			clrs[i] = new Color3f(0.5f, 0.5f, 0.5f);
		// drawing a cube using 6 quadrilaterals
		// first quad
		pts[0].x = -0.5f;
		pts[0].y = -0.5f;
		pts[0].z = -0.5f;
		pts[1].x = -0.5f;
		pts[1].y = -0.5f;
		pts[1].z = 0.5f;
		pts[2].x = -0.5f;
		pts[2].y = 0.5f;
		pts[2].z = 0.5f;
		pts[3].x = -0.5f;
		pts[3].y = 0.5f;
		pts[3].z = -0.5f;
		normals[0].x = -1;
		normals[1].x = -1;
		normals[2].x = -1;
		normals[3].x = -1;
		// second quad
		pts[4].x = 0.5f;
		pts[4].y = -0.5f;
		pts[4].z = -0.5f;
		pts[5].x = 0.5f;
		pts[5].y = -0.5f;
		pts[5].z = 0.5f;
		pts[6].x = 0.5f;
		pts[6].y = 0.5f;
		pts[6].z = 0.5f;
		pts[7].x = 0.5f;
		pts[7].y = 0.5f;
		pts[7].z = -0.5f;
		normals[4].x = 1;
		normals[5].x = 1;
		normals[6].x = 1;
		normals[7].x = 1;

		// third quad
		pts[8].x = -0.5f;
		pts[8].y = -0.5f;
		pts[8].z = -0.5f;
		pts[9].x = 0.5f;
		pts[9].y = -0.5f;
		pts[9].z = -0.5f;
		pts[10].x = 0.5f;
		pts[10].y = 0.5f;
		pts[10].z = -0.5f;
		pts[11].x = -0.5f;
		pts[11].y = 0.5f;
		pts[11].z = -0.5f;
		normals[8].z = -1;
		normals[9].z = -1;
		normals[10].z = -1;
		normals[11].z = -1;
		// fourth quad
		pts[12].x = -0.5f;
		pts[12].y = -0.5f;
		pts[12].z = 0.5f;
		pts[13].x = 0.5f;
		pts[13].y = -0.5f;
		pts[13].z = 0.5f;
		pts[14].x = 0.5f;
		pts[14].y = 0.5f;
		pts[14].z = 0.5f;
		pts[15].x = -0.5f;
		pts[15].y = 0.5f;
		pts[15].z = 0.5f;
		normals[12].z = 1;
		normals[13].z = 1;
		normals[14].z = 1;
		normals[15].z = 1;
		// fifth quad
		pts[16].x = -0.5f;
		pts[16].y = -0.5f;
		pts[16].z = -0.5f;
		pts[17].x = -0.5f;
		pts[17].y = -0.5f;
		pts[17].z = 0.5f;
		pts[18].x = 0.5f;
		pts[18].y = -0.5f;
		pts[18].z = 0.5f;
		pts[19].x = 0.5f;
		pts[19].y = -0.5f;
		pts[19].z = -0.5f;
		normals[16].y = -1;
		normals[17].y = -1;
		normals[18].y = -1;
		normals[19].y = -1;
		// sixth quad
		pts[20].x = -0.5f;
		pts[20].y = 0.5f;
		pts[20].z = -0.5f;
		pts[21].x = -0.5f;
		pts[21].y = 0.5f;
		pts[21].z = 0.5f;
		pts[22].x = 0.5f;
		pts[22].y = 0.5f;
		pts[22].z = 0.5f;
		pts[23].x = 0.5f;
		pts[23].y = 0.5f;
		pts[23].z = -0.5f;
		normals[20].y = 1;
		normals[21].y = 1;
		normals[22].y = 1;
		normals[23].y = 1;
		cubeArray.setNormals(0, normals);
		cubeArray.setCoordinates(0, pts);
		//creating a shape3d object for my drwan cube
		Shape3D cubeDrwan = new Shape3D();
//		setting the materials and appearance for my drwan shape
		Material materialForDrawnCube = new Material();
		materialForDrawnCube.setEmissiveColor(new Color3f(0.1f, 0.5f, 0.5f));
		materialForDrawnCube.setAmbientColor(new Color3f(0.1f, 0.1f, 0.1f));
		materialForDrawnCube.setDiffuseColor(new Color3f(0.8f, 0.3f, 0.4f));
		materialForDrawnCube.setSpecularColor(new Color3f(0.8f, 0.3f, 0.2f));
		materialForDrawnCube.setLightingEnable(true);
		RenderingAttributes renderingAttributeForCube = new RenderingAttributes();
		renderingAttributeForCube.setIgnoreVertexColors(true);
		ColoringAttributes colouringAttributeForDrawnCube = new ColoringAttributes();
		colouringAttributeForDrawnCube.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
		colouringAttributeForDrawnCube.setColor(new Color3f(0.545f, 0.271f, 0.075f));
		appForDrawnShap.setColoringAttributes(colouringAttributeForDrawnCube);
		appForDrawnShap.setRenderingAttributes(renderingAttributeForCube);
		appForDrawnShap.setMaterial(materialForDrawnCube);
		
		cubeDrwan.setGeometry(cubeArray);
		cubeDrwan.setAppearance(appForDrawnShap);
		cubeDrwan.setCollidable(false);
		//creating a new transform 3d object for my drwan shape
		Transform3D drawnShape = new Transform3D();
		drawnShape.setScale(new Vector3d(1.5, 1.5, 1.5));
		drawnShape.setTranslation(new Vector3d(-3, -6.5, 6.4));
//		creating a new transform group for my drawn shape
		TransformGroup drawnShapeTG = new TransformGroup(drawnShape);

		// creating transform group for sphere that is nested inside of a cube
		Sphere sphereLightBlue = new Sphere(0.8f, lightBlueApp);
		Sphere sphereRedForCollision = new Sphere(0.8f, redApp);

		// creating transform group for object
		Transform3D cubeDecoration = new Transform3D();
		cubeDecoration.setScale(new Vector3d(1.2, 1.2, 1.2));

		cubeDecoration.setTranslation(new Vector3d(0.3, -1.0, -6.9));
		TransformGroup cubeDecorationTG = new TransformGroup(cubeDecoration);
		// The Switch node controls which of its children will be rendered.
		// Add the spheres to the Switch.

		Transform3D sphere8 = new Transform3D();
		sphere8.setScale(new Vector3d(1.2, 1.2, 1.2));

		sphere8.setTranslation(new Vector3d(-3.0, -6.5, 6.4));
		TransformGroup sphereTGForTreeDecoration = new TransformGroup(sphere8);
		
		cubeDecorationTG.addChild(sphereTGForTreeDecoration);
		cubeDecorationTG.addChild(drawnShapeTG);
		drawnShapeTG.addChild(cubeDrwan);

		mainTG.addChild(cubeDecorationTG);
		
//		CollisionBehaviour1 class takes care of changing the colour of the cylinder when the snowman touches the sphere nested in the cube.
		CollisionBehaviour1 cylinderCollisionBehaviour = new CollisionBehaviour1(sphereLightBlue, colourSwitchFortopOfRedHat,
				boundsForcolision);
		mainTG.addChild(cylinderCollisionBehaviour);//adds colision into main file
		CollisionBehaviour1 cylindercolisonDHatTop = new CollisionBehaviour1(sphereLightBlue, colourSwitchforHatBase, bounds);
		mainTG.addChild(cylindercolisonDHatTop);//adds colision into main file
		
		
		Switch colourSwitchForSphereDecoration = new Switch();
		colourSwitchForSphereDecoration.setCapability(Switch.ALLOW_SWITCH_WRITE);
		// seeting the initial colour of the switch to the blue sphere so when
		// the scene loads
		colourSwitchForSphereDecoration.setWhichChild(0);

		// The Switch node controls which of its children will be rendered.
		// Add the spheres to the Switch.
		colourSwitchForSphereDecoration.addChild(sphereLightBlue); // child 0
		colourSwitchForSphereDecoration.addChild(sphereRedForCollision); // child
		// A transformation group for the Switch (the spheres).

		sphereTGForTreeDecoration.addChild(colourSwitchForSphereDecoration);

		// The CollisionBounds for the cube.
		sphereSnowMan1.setCollisionBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1.0 * 0));
		colourSwitchForSphereDecoration.setCollisionBounds(new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 1.0));
		// Enabled for collision purposes
		colourSwitchForSphereDecoration.setCollidable(false);
//		CollisionBehaviour1 class takes care of changing the colour of the sphere when the snowman touches it.
		CollisionBehaviour1 sphereDecoration = new CollisionBehaviour1(sphereLightBlue, colourSwitchForSphereDecoration,
				boundsForcolision);
		mainTG.addChild(sphereDecoration);//adds colision into main file
		/*********************************************************************************************************
		 * Drwing a cube, and nesting an orange sphere inside of it
		 ********************************************************************************************************/

		Appearance appForDrawnShap2 = new Appearance();

		QuadArray cubeArray2 = new QuadArray(48, QuadArray.COORDINATES | QuadArray.NORMALS);
		Vector3f[] normals2 = new Vector3f[24];
		for (int i = 0; i < 24; i++)
			normals2[i] = new Vector3f();
		Point3f[] pts2 = new Point3f[24];
		for (int i = 0; i < 24; i++)
			pts2[i] = new Point3f();
		Color3f[] clrs2 = new Color3f[24];
		for (int i = 0; i < 24; i++)
			clrs2[i] = new Color3f(0.5f, 0.5f, 0.5f);
		// drawing a cube using 6 quadrilaterals
		// first quad
		pts2[0].x = -0.5f;
		pts2[0].y = -0.5f;
		pts2[0].z = -0.5f;
		pts2[1].x = -0.5f;
		pts2[1].y = -0.5f;
		pts2[1].z = 0.5f;
		pts2[2].x = -0.5f;
		pts2[2].y = 0.5f;
		pts2[2].z = 0.5f;
		pts2[3].x = -0.5f;
		pts2[3].y = 0.5f;
		pts2[3].z = -0.5f;
		normals2[0].x = -1;
		normals2[1].x = -1;
		normals2[2].x = -1;
		normals2[3].x = -1;
		// second quad
		pts2[4].x = 0.5f;
		pts2[4].y = -0.5f;
		pts2[4].z = -0.5f;
		pts2[5].x = 0.5f;
		pts2[5].y = -0.5f;
		pts2[5].z = 0.5f;
		pts2[6].x = 0.5f;
		pts2[6].y = 0.5f;
		pts2[6].z = 0.5f;
		pts2[7].x = 0.5f;
		pts2[7].y = 0.5f;
		pts2[7].z = -0.5f;
		normals2[4].x = 1;
		normals2[5].x = 1;
		normals2[6].x = 1;
		normals2[7].x = 1;

		// third quad
		pts2[8].x = -0.5f;
		pts2[8].y = -0.5f;
		pts2[8].z = -0.5f;
		pts2[9].x = 0.5f;
		pts2[9].y = -0.5f;
		pts2[9].z = -0.5f;
		pts2[10].x = 0.5f;
		pts2[10].y = 0.5f;
		pts2[10].z = -0.5f;
		pts2[11].x = -0.5f;
		pts2[11].y = 0.5f;
		pts2[11].z = -0.5f;
		normals2[8].z = -1;
		normals2[9].z = -1;
		normals2[10].z = -1;
		normals2[11].z = -1;
		// fourth quad
		pts2[12].x = -0.5f;
		pts2[12].y = -0.5f;
		pts2[12].z = 0.5f;
		pts2[13].x = 0.5f;
		pts2[13].y = -0.5f;
		pts2[13].z = 0.5f;
		pts2[14].x = 0.5f;
		pts2[14].y = 0.5f;
		pts2[14].z = 0.5f;
		pts2[15].x = -0.5f;
		pts2[15].y = 0.5f;
		pts2[15].z = 0.5f;
		normals2[12].z = 1;
		normals2[13].z = 1;
		normals2[14].z = 1;
		normals2[15].z = 1;
		// fifth quad
		pts2[16].x = -0.5f;
		pts2[16].y = -0.5f;
		pts2[16].z = -0.5f;
		pts2[17].x = -0.5f;
		pts2[17].y = -0.5f;
		pts2[17].z = 0.5f;
		pts2[18].x = 0.5f;
		pts2[18].y = -0.5f;
		pts2[18].z = 0.5f;
		pts2[19].x = 0.5f;
		pts2[19].y = -0.5f;
		pts2[19].z = -0.5f;
		normals2[16].y = -1;
		normals2[17].y = -1;
		normals2[18].y = -1;
		normals2[19].y = -1;
		// sixth quad
		pts2[20].x = -0.5f;
		pts2[20].y = 0.5f;
		pts2[20].z = -0.5f;
		pts2[21].x = -0.5f;
		pts2[21].y = 0.5f;
		pts2[21].z = 0.5f;
		pts2[22].x = 0.5f;
		pts2[22].y = 0.5f;
		pts2[22].z = 0.5f;
		pts2[23].x = 0.5f;
		pts2[23].y = 0.5f;
		pts2[23].z = -0.5f;
		normals2[20].y = 1;
		normals2[21].y = 1;
		normals2[22].y = 1;
		normals2[23].y = 1;
		cubeArray2.setNormals(0, normals2);
		cubeArray2.setCoordinates(0, pts2);
		//creating a shape3d object for my drwan cube
		Shape3D cubeDrwan2 = new Shape3D();
//		setting the materials and appearance for my drwan shape

		Material materialForDrawnCube2 = new Material();
		materialForDrawnCube2.setEmissiveColor(new Color3f(0.1f, 0.5f, 0.5f));
		materialForDrawnCube2.setAmbientColor(new Color3f(0.1f, 0.1f, 0.1f));
		materialForDrawnCube2.setDiffuseColor(new Color3f(0.8f, 0.3f, 0.4f));
		materialForDrawnCube2.setSpecularColor(new Color3f(0.8f, 0.3f, 0.2f));
		materialForDrawnCube2.setLightingEnable(true);
		RenderingAttributes renderingAttributeForCube2 = new RenderingAttributes();
		renderingAttributeForCube2.setIgnoreVertexColors(true);
		ColoringAttributes colouringAttributeForDrawnCube2 = new ColoringAttributes();
		colouringAttributeForDrawnCube2.setShadeModel(ColoringAttributes.SHADE_GOURAUD);
		colouringAttributeForDrawnCube2.setColor(new Color3f(0.545f, 0.271f, 0.075f));
		appForDrawnShap2.setColoringAttributes(colouringAttributeForDrawnCube2);
		appForDrawnShap2.setRenderingAttributes(renderingAttributeForCube2);
		appForDrawnShap2.setMaterial(materialForDrawnCube2);
		
		cubeDrwan2.setGeometry(cubeArray2);
		cubeDrwan2.setAppearance(appForDrawnShap2);
		cubeDrwan2.setPickable(true);
		cubeDrwan2.setCollidable(false);
		//creating a new transform 3d object for my drwan shape
		Transform3D drawnShape2 = new Transform3D();
		drawnShape2.setScale(new Vector3d(1.5, 1.5, 1.5));
		drawnShape2.setTranslation(new Vector3d(-3, -6.5, 6.4));
		TransformGroup drawnShapeTG2 = new TransformGroup(drawnShape2);

		// creating transform group for sphere that is nested inside of a cube
		Sphere sphereLightBlue2 = new Sphere(0.8f, orangeApp);
		sphereLightBlue2.setCollidable(false);
		
		//creating a transform group object for the cube and sphere decoration, in order to be able to set the scaling and translation for the shapes together
		Transform3D cubeDecoration2 = new Transform3D();
		cubeDecoration2.setScale(new Vector3d(1.2, 1.2, 1.2));
		cubeDecoration2.setTranslation(new Vector3d(6, -1.0, -6.9));
		// creating transform group for the cube, which allows me to put the cube and the circle in the sma etransform group
		TransformGroup cubeDecorationTG2 = new TransformGroup(cubeDecoration2);
		//creating a new trans form 3d object for the cube on the right of the tree
		Transform3D sphereForcubeOntheright = new Transform3D();
		sphereForcubeOntheright.setScale(new Vector3d(1.2, 1.2, 1.2));
		sphereForcubeOntheright.setTranslation(new Vector3d(-3.0, -6.5, 6.4));
		//creating a new transform group object for the cube on the right
		TransformGroup sphereTGForTreeDecoration2 = new TransformGroup(sphereForcubeOntheright);
		
		//adding the shape to the main tg
		sphereTGForTreeDecoration2.addChild(sphereLightBlue2);
		cubeDecorationTG2.addChild(sphereTGForTreeDecoration2);
		cubeDecorationTG2.addChild(drawnShapeTG2);
		drawnShapeTG2.addChild(cubeDrwan2);
		mainTG.addChild(cubeDecorationTG2);

		/*********************************************************************************************************
		 * //creating static snow on the floor
		 ********************************************************************************************************/
       
		Transform3D staticSphere = new Transform3D();
		staticSphere.setTranslation(new Vector3d(0.0, -9.0, 0.0));
		TransformGroup staticSphereTG = new TransformGroup(staticSphere);
		staticSphereTG.setCollidable(false);

		int numsnowFloor = 10000;
		Sphere snowFloor[] = new Sphere[numsnowFloor];
		Transform3D spheresFloor[] = new Transform3D[numsnowFloor];
		TransformGroup sphereTGFloor[] = new TransformGroup[numsnowFloor];
		// a for loop that creates the snow on the floor using a random number generator to set its translation
		
		for (int i = 0; i < numsnowFloor; i++) {
			snowFloor[i] = new Sphere(0.1f, whiteApp);
			snowFloor[i].setCollidable(false);
			spheresFloor[i] = new Transform3D();
			spheresFloor[i].setTranslation(randomgenerator());
			sphereTGFloor[i] = new TransformGroup(spheresFloor[i]);

		}
		// a floor into transform groups
		for (int i = 0; i < numsnowFloor; i++) {
			staticSphereTG.addChild(sphereTGFloor[i]);
			sphereTGFloor[i].addChild(snowFloor[i]);

		}
		mainTG.addChild(staticSphereTG);// adding the snow on the floor to the main tg
		/*********************************************************************************************************
		 * //creating adding the transform group of the shapes into the main
		 * transform group.
		 ********************************************************************************************************/
		// Create the rotate behavior node
		MouseRotate behavior = new MouseRotate();
		behavior.setTransformGroup(mainTG);
		objRoot.addChild(behavior);
		behavior.setSchedulingBounds(bounds);

		// Create the zoom behavior node
		MouseZoom behavior2 = new MouseZoom();
		behavior2.setTransformGroup(mainTG);
		objRoot.addChild(behavior2);
		behavior2.setSchedulingBounds(bounds);

		// Create the translate behavior node
		MouseTranslate behavior3 = new MouseTranslate();
		behavior3.setTransformGroup(mainTG);
		objRoot.addChild(behavior3);
		behavior3.setSchedulingBounds(bounds);

		// In order to allow navigation through the scene with the keyboard,
		// everything must be collected in a separate transformation group to
		// which
		// the KeyNavigatorBehavior is applied.
		KeyNavigatorBehavior knb = new KeyNavigatorBehavior(mainTG);
		knb.setSchedulingBounds(bounds);
		mainTG.addChild(knb);

		objRoot.compile();
		return objRoot;
	}

	public static void main(String[] args) {

		Example3D scene = new Example3D();
	}

	public void addLight(SimpleUniverse su) {
		/*********************************************************************************************************
		 * LIGHTING --------------------------------- Placing different light
		 * sources into the environment.
		 ********************************************************************************************************/

		BranchGroup bgLight = new BranchGroup();

		BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
		// Set up the global lights
		// first the ambient light
		// light from all directions
		// typically use white or 'gray' light
		Color3f alColor = new Color3f(0.6f, 0.6f, 0.6f);
		AmbientLight aLgt = new AmbientLight(alColor);
		aLgt.setInfluencingBounds(bounds);
		bgLight.addChild(aLgt);

		// next the directional light
		// parallel light rays come FROM infinity TOWARDS the vector lightDir1
		Color3f lightColour1 = new Color3f(1.0f, 1.0f, 1.0f);
		// try out x, y and z each being + or - 1, and other coords = 0
		// with (0,1,0) the bottom of the cube should be lit
		// Vector3f lightDir1 = new Vector3f(0.0f,1.0f,0.0f);
		Vector3f lightDir1 = new Vector3f(-1.0f, 0.0f, -.7f);
		DirectionalLight light1 = new DirectionalLight(lightColour1, lightDir1);
		// light has no effect, ie Influence, outside of the bounds
		light1.setInfluencingBounds(bounds);
		bgLight.addChild(light1);

		Vector3f lightDir2 = new Vector3f(-1.0f, 1.0f, -1.0f);
		DirectionalLight light2 = new DirectionalLight(lightColour1, lightDir2);
		light2.setInfluencingBounds(bounds);
		bgLight.addChild(light2);

		su.addBranchGraph(bgLight);

		/* END LIGHTING --------------------------------- */

	} // ------------------ end addLight

	// RIGHT ONE

}
