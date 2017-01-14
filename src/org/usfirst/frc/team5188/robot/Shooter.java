package org.usfirst.frc.team5188.robot;

import java.util.regex.PatternSyntaxException;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.TalonSRX;

public class Shooter implements PID_Actuator, PID_Sensor {
	private CANTalon talon;
	private Pats_PID_Controller controller;
	
	Shooter(int motor_pin, double p, double i, double d, int loopTime, boolean inverted){
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
		talon.disable();
	}
	public double getSetPoint(){
		return controller.getSet();
	}
	public double read() {
		return talon.getSpeed();	//in ticks per 100ms
	} 
	public void set(double value) {
		talon.set(value);
	}
}
