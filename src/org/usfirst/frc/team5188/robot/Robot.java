
package org.usfirst.frc.team5188.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import java.lang.System;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
	
    SuperJoystickPlus controller;
    Shooter shooter;
    int counter = 0;
    
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
        controller = new SuperJoystickPlus(0);
        shooter = new Shooter(0, .00007, 0.0000003, -0.00000001, 20, true);
        controller.setDeadzone(.01);
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }

    /**
     * This function is called periodically during autonomous
     */
    public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    }

    /**
     * This function is called periodically during operator control
     */
    public void teleopPeriodic() {
        shooter.setThrottle(controller.get(CTRL_AXIS.LY));
        counter ++;
		System.out.println("IN PID LOOP: " + " Speed: " + shooter.read() + " Set Point: " + shooter.getSetPoint() + " Error: " + shooter.controller.error + " Motor Throttle: " + shooter.lastSet);
        if(controller.isButtonPushed(CTRL_BTN.A)){
//        	int counter = 0;
//        	shooter.start_pid();
        	while(!controller.isButtonPushed(CTRL_BTN.B)){
        		counter ++;
        		if((counter % 1000) == 0){System.out.println("IN PID LOOP: " + "Throttle: " + controller.get(CTRL_AXIS.LY) + " Speed: " + shooter.read() + " Set Point: " + shooter.getSetPoint() + " Error: " + shooter.controller.error + " Motor Throttle: " + shooter.lastSet);}
        		shooter.setRPM(controller.get(CTRL_AXIS.LY) * 1000);
        		shooter.controller.runIteration();
//        		System.out.println("Throttle: " + controller.get(CTRL_AXIS.LY) + " Speed: " + shooter.read() + " Set Point: " + shooter.getSetPoint());
        	}
        	shooter.stop();
        }
    }
    
    /**
     * This function is called periodically during test mode
     */
    public void testPeriodic() {
    
    }
    
}
