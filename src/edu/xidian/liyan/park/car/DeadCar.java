package edu.xidian.liyan.park.car;

public class DeadCar extends Car {

	private String deadReason;
	
	public DeadCar(Car car, String deadReason){
		this.deadReason = deadReason;
		setEmployee(car.getEmployee());
		setParkingTime(car.getParkingTime());
	}
	
	public String getDeadReason() {
		return deadReason;
	}

	public void setDeadReason(String deadReason) {
		this.deadReason = deadReason;
	}

	@Override
	public Car nextStep() {
		return null;
	}

}
