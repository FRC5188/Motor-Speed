package org.usfirst.frc.team5188.robot;

import edu.wpi.first.wpilibj.CANTalon;
import modules.*;

public class Shooter implements PID_Actuator, PID_Sensor {
	private CANTalon talon;
//	public Pats_PID_Controller controller;
	public PID_Controller controller;
	public double lastSet;
	
	Shooter(int motor_pin, boolean inverted, PID_Controller controller){
		talon = new CANTalon(1);
		this.controller = controller;
//		controller = new Pats_PID_Controller(p, i, d, loopTime, this, this);
		controller.invert(inverted);
        controller.setPIDS(.00006, 0.000008, -0.000000001);
        controller.setLoopTime(10);
	}
	public void start_pid(){
		controller.start();
	}
	public void stop_pid(){
		controller.stop();
		talon.disable();
	}
	public void setPIDSpeed(double speed){
		controller.set(speed);
	}
	public void setThrottle(double speed){
		if(controller.isRunning()){
			controller.stop();
		}
		talon.set(speed);
	}
	public void stop(){
		if(controller.isRunning()){
			controller.stop();
		}
//		talon.disable();
	}
	public double getSetPoint(){
		return controller.getSet();
	}
	public double read() {
		return talon.getSpeed();	//in ticks per 100ms = time unit = t
	} 
	public void set(double value) {
//		System.out.println("Setting motor to: " + value);
		talon.set(value);
		lastSet = value;
	}
	public void setRPM(double d){
		this.setPIDSpeed(d * (512/75));	//(x rev/m)(1024*4 ticks/1rev)(1m/60s)(1s/10t) = 512x ticks/75 t
	}
}

