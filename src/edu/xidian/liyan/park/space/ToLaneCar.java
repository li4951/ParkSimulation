package edu.xidian.liyan.park.space;

import java.util.Calendar;

import org.apache.log4j.Logger;

import edu.xidian.liyan.park.car.Car;
import edu.xidian.liyan.park.space.OnSpaceCar;
import edu.xidian.liyan.park.drivelane.DriveLaneManager;
import edu.xidian.liyan.park.drivelane.InLaneCar;
import edu.xidian.liyan.park.parkinglot.ParkingLot;


/**
 * �ӳ�λ��ͣ�������ϵĳ����ࣨ����ֻ�Ǹ������࣬costTime��ö�����ʧ���µ�״̬�ĳ���������
 * @author ����
 */
public class ToLaneCar extends Car {

	private Logger logger = Logger.getLogger(ToLaneCar.class);
	//�뿪��λ���ڳ����ϵ�����
	private int position;
	//��Ӧ�ĳ�λ��
	private int spacePosition;
	//�ƶ�������������ʱ��
	private int costTime = 2;
	
	private ParkingLot parkingLot = ParkingLot.getInstance();
	private SpaceManager spaceManager = parkingLot.getSpaceManager();
	private DriveLaneManager driveLaneManager = parkingLot.getDriveLaneManager(); 
	
	public int getPosition() {
		return position;
	}
	
	public int getSpacePosition() {
		return spacePosition;
	}

	public ToLaneCar(OnSpaceCar onSpaceCar){
		this.spacePosition = onSpaceCar.getSpacePosition();
		this.position = onSpaceCar.getSpacePosition() / 2 + 1;
		setEmployee(onSpaceCar.getEmployee());
		setLength(onSpaceCar.getLength());
		setSpeed(onSpaceCar.getSpeed());
		setParkingTime(onSpaceCar.getParkingTime());
		setTimeStateBegin(Calendar.getInstance());
		
	}
	
	@Override
	public Car nextStep() {
		long passedTime = (System.currentTimeMillis() - getTimeStateBegin().getTimeInMillis()) / 1000;
		if(passedTime >= costTime){
			logger.debug("(" + getEmployee().getId() + ")" + "lay on the lane successfully");
			driveLaneManager.setState(position, this);
			spaceManager.remove(spacePosition);
			InLaneCar inLaneCar = new InLaneCar(this);
			return inLaneCar;
		}
		return null;
	}

}
