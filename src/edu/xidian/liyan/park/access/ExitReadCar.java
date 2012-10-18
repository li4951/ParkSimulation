package edu.xidian.liyan.park.access;

import java.util.Calendar;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.drivelane.InLaneCar;
import edu.xidian.liyan.park.parkinglot.ParkingLot;

/**
 * 出口读卡车辆类（在出口处读卡）
 * @author 李延
 */
public class ExitReadCar extends Car{

	private Logger logger = Logger.getLogger(ExitReadCar.class);
	
	private int position;
	
	private Exit exit = Exit.getInstance();
	private ParkingLot parkingLot = ParkingLot.getInstance();
	
	public ExitReadCar(InLaneCar inLaneCar) {
		position = parkingLot.getDriveLaneManager().getLength() - 1;
		setEmployee(inLaneCar.getEmployee());
		setLength(inLaneCar.getLength());
		setSpeed(inLaneCar.getSpeed());
		setParkingTime(inLaneCar.getParkingTime());
		exit.read(this);
		setTimeStateBegin(Calendar.getInstance());
		exit.setUsed(true);
	}
	
	@Override
	public Car nextStep() {

		if(Exit.OPENED == exit.getState()){
			logger.debug("(" + getEmployee().getId() + ")" + " has opened, begin to leave");
			ExitPassCar exitPassCar = new ExitPassCar(this);
			return exitPassCar;
		}else if(exit.isReadOver()){
			String id = exit.read(this);
			if(id != null && parkingLot.contains(id)){
				logger.debug("(" + getEmployee().getId() + " in the exit)," + "Id is correct, begin to lift!");
				exit.lift();
			}
		}
		return null;
	}
	
}
