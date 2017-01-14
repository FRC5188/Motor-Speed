package org.usfirst.frc.team5188.robot;

import java.util.regex.PatternSyntaxException;

import edu.wpi.first.wpilibj.CANTalon;

public class Shooter implements PID_Actuator, PID_Sensor {
	private CANTalon talon;
	public Pats_PID_Controller controller;
	public double lastSet;
	
	Shooter(int motor_pin, double p, double i, double d, double loopTime, boolean inverted){
		talon = new CANTalon(1);
		controller = new Pats_PID_Controller(p, i, d, loopTime, this, this);
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

