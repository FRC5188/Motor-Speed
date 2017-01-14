package org.usfirst.frc.team5188.robot;

import java.util.regex.PatternSyntaxException;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Talon;

public class Shooter implements PID_Actuator, PID_Sensor {
	private Talon talon;
	private Encoder encoder;
	private Pats_PID_Controller controller;
	
	Shooter(int motor_pin, int encoder_a, int encoder_b, double p, double i, double d, int loopTime, boolean inverted){
		talon = new Talon(motor_pin);
		encoder = new Encoder(encoder_a, encoder_b);
		controller = new Pats_PID_Controller(p, i, d, loopTime, this, this);
	}
	public void start_pid(){
		controller.start();
	}
	public void stop_pid(){
		controller.stop();
		talon.disable();
	}
	public void setSpeed(double speed){
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
	
	public double read() {
		return encoder.getRate();
	} 
	public void set(double value) {
		talon.set(value);
	}
}
