package org.usfirst.frc.team5188.robot;
import java.lang.System;
//import java.util.concurrent.TimeUnit;

public class Pats_PID_Controller implements Runnable, PID_Controller{
	public double p,i,d;
	private double setPoint;
	private double accumulator = 0;
	private double lastError;
	public double loopTime;	//in milliseconds
	public double error;
	private long lastRun = System.currentTimeMillis();
	Thread t = new Thread(this);
	boolean running = false;
	private PID_Sensor sensor;
	private PID_Actuator actuator;
	private boolean invert = false;
	private boolean isRunning = false;
	public Pats_PID_Controller(){}
	public Pats_PID_Controller(double p, double i, double d, double loopTime, PID_Sensor sensor, PID_Actuator actuator){
		this.p = p;
		this.i = i;
		this.d = d;
		this.loopTime = loopTime;
		this.sensor = sensor;
		this.actuator = actuator;
	}
	public void setPIDS(double p, double i, double d){
		this.p = p;
		this.i = i;
		this.d = d;
	}
	public void set(double set){
		setPoint = set;
	}
	public double getSet(){
		return setPoint;
	}
	public void resetAccumulator(){
		accumulator = 0;
	}
	public double[] getPIDS(){
		double[] temp = {p,i,d};
		return temp;
	}
	public void invert(boolean inversionState){invert = inversionState;}
	public void start(){
		running = true;
		this.resetAccumulator();
		t.start();
	}
	public void stop(){running = false;}
	public void runIteration(){
		error = invert ? setPoint - sensor.read() : sensor.read() - setPoint;
		double p_result = p * error;
		accumulator += error;
		double i_result = i * accumulator;
		double d_result = d * (error - lastError) / (loopTime / 1000);
		actuator.set(p_result + i_result + d_result);
	}
	public void run(){
		while(running){
			while((System.currentTimeMillis() - lastRun) < loopTime){}
			lastRun = System.currentTimeMillis();
			runIteration();
//			long start = System.currentTimeMillis();
//			runIteration();
//			try {
//				this.t.sleep(loopTime - (System.currentTimeMillis() - start));
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			try {
//				TimeUnit.MILLISECONDS.sleep(loopTime - (System.currentTimeMillis() - start));
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}
	public PID_Sensor getSensor() {
		return sensor;
	}
	public PID_Actuator getActuator() {
		return actuator;
	}
	public void setSensor(PID_Sensor sensor) {
		this.sensor = sensor;
	}
	public void setActuator(PID_Actuator actuator) {
		this.actuator = actuator;
	}
	public boolean isRunning() {
		return this.running;
	}
}