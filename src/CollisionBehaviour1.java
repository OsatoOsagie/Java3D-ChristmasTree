import java.util.*;

import javax.media.j3d.*;

import com.sun.j3d.utils.geometry.*;


/**
 * March 2018
 *
* This class implements the change between two states (Switches)
* when a collision occurs.
*
* @author R. Crole (based on code by F. Klawonn)
*
*/

public class CollisionBehaviour1 extends Behavior {

   // This class uses the sphere and cylinder 

   // The children of Switch are the red/green/blue spheres 

   public Switch snowManHatSwitch;
   public Primitive sphere; 

   // declare initial and process stimulus (arrays of) criteria 

   public WakeupCriterion[] initialCriteria;
   public WakeupCriterion[] procStimCriteria;

   // API: public WakeupAnd(WakeupCriterion[] criteria) - logical AND / OR of the (objects in the) criteria  

   // in lectures, we take BOTH as And or BOTH as Or

   public WakeupAnd initial_wakeUpCondition;
   public WakeupAnd  procStim_wakeUpCondition; 

   /* BEGIN THE CONSTRUCTOR FOR THIS CLASS  --------------------------------- */
   // this is called within Example3D.java in the collision detection section

    public CollisionBehaviour1(Primitive theSphere, Switch theSwitch, Bounds theBounds)
    {
       sphere = theSphere;
       snowManHatSwitch = theSwitch;
       setSchedulingBounds(theBounds);
    }
   /* END THE CONSTRUCTOR FOR THIS CLASS  --------------------------------- */

  /* BEGIN INITIALISE BEHAVIOR  --------------------------------- */
   public void initialize()
   {
     initialCriteria = new WakeupCriterion[2];
     //code will wake up and do somehing when the parameter is satisfied, you can use the actual objects you want to wake up as the parameter
     //in your case you want to wake on enty of the snow man to the box
     //
     initialCriteria[0] = new WakeupOnCollisionEntry(sphere);
     initialCriteria[1] = new WakeupOnCollisionEntry(sphere);

     // eg API: WakeupOr(WakeupCriterion[] criteria) 

     // eg API: WakeupAnd(WakeupCriterion[] criteria) 

     initial_wakeUpCondition = new WakeupAnd(initialCriteria);
     // API: wakeupOn(WakeUpCondition wakeupcondition)

     // this method ensures that processStimulus is called when the 

     // [initial_]wakeUpCondition is satisfied ... 

     // ... and [initial]Criteria is passed to processStimulus as an enumeration 

     wakeupOn(initial_wakeUpCondition); 

     // NOTE: process Stimulus wake up criteria may well be different to initial criteria 

     procStimCriteria = new WakeupCriterion[2];
     procStimCriteria[0] = new WakeupOnCollisionEntry(sphere);
     procStimCriteria[1] = new WakeupOnCollisionEntry(sphere);

     System.out.println(); 
     if (((WakeupCondition) initial_wakeUpCondition) instanceof WakeupOr) {
     System.out.println("*initial new colour switch of snow man hat*"); 
     } 
   }
  /* END INITIALISE BEHAVIOR  --------------------------------- */

  /* BEGIN PROCESS STIMULUS BEHAVIOR  --------------------------------- */

   // API: criteria - an enumeration of triggered wakeup criteria for this behavior

   // !! Look up Behavior class in the API !! 

    
   public void processStimulus(Enumeration criteria)
   {
       //Here we define what happens when a collision occurs.

       System.out.println("process stimulus called"); 

       // -- EDIT HERE

       // a simple process to swap green and red spheres for either a WakeupOr or a WakeupAnd 

       if (snowManHatSwitch.getWhichChild() == 0) {snowManHatSwitch.setWhichChild(1); System.out.println("snow mans hat cahnged from *red --> lightBlue*");
       System.out.println("sphere in box cahnged from *lightBlue --> red*"); }
       else {snowManHatSwitch.setWhichChild(0); System.out.println(" snow mans hat *lightBlue --> red*");
       System.out.println("sphere changed *red --> lightBlue*"); 
       
       }
       // -- */

// end while 

//
      procStim_wakeUpCondition = new WakeupAnd(procStimCriteria);
      wakeupOn(procStim_wakeUpCondition); 

      System.out.println(); 
      if (((WakeupCondition) procStim_wakeUpCondition) instanceof WakeupOr) {
	  System.out.println("*procStim new wakeupOn collision sphere ENTRY*"); 
      } else {
	  System.out.println("*procStim new wakeupOn collision sphere ENTRY*"); 
      }

   } // end process stimulus 


   /* END PROCESS STIMULUS BEHAVIOR  --------------------------------- */
 
} 