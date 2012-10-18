package edu.xidian.liyan.park;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import edu.xidian.liyan.log.LogInfo;
import edu.xidian.liyan.log.WriterLogInfo;
import edu.xidian.liyan.park.access.*;
import edu.xidian.liyan.park.car.*;
import edu.xidian.liyan.park.parkinglot.ParkingLot;

/**
 * @author 李延
 * 停车场主类
 * 主要包含属性：入口，出口，车场内，所有车辆
 */
public class Park {
	
	private static Logger logger = Logger.getLogger(Park.class);
	private Access entrance;
	private Access exit;
	//场内管理对象
	private ParkingLot parkingLot;
	//所有车辆集合
	private List<Car> cars;
	//配置参数：员工数量
	private int e;
	//配置参数：单排车位数
	private int n;
	//日志文件输出对象
	private WriterLogInfo writerLogInfo = new WriterLogInfo();
	
	//以下两个属性的声明是为了方便打印场外入口车和出口离开后的车，由于设计时没有考虑到。故在此做修补，实属无奈。
	private EntranceReadCar entranceCar;
	private DeadCar exitCar;
	
	public Park(int e, int n) {
		this.e = e;
		this.n = n;
		entrance = Entrance.getInstance();
		exit = Exit.getInstance();
		parkingLot = ParkingLot.getInstance();
		parkingLot.initParkingLot(n);
		EmployeeDao.getInstance().initEmployees(e);
		cars = new ArrayList<Car>();
		logger.debug("Init success");
	}
	
	/**
	 * 停车场系统仿真函数，每秒钟刷新一次各相关对象状态
	 */
	public void simulate(){
		int timeCount = 0;
		int amountIn = 0;
		int amountOut = 0;
		int parkingTimeSum = 0;
		
		writerLogInfo.setTimeSimulateBegin(Calendar.getInstance());		
		while(true){
			System.out.println("第" + timeCount++ +"秒后");
			//每隔一分钟报告一次写入日志文件
			if(timeCount != 0 && timeCount % 60 == 0){
				LogInfo logInfo = new LogInfo(parkingLot.getSize(), amountIn, amountOut, amountOut==0? 0 : parkingTimeSum / amountOut);
				writerLogInfo.addInfo(logInfo);
				if(timeCount > 250 && parkingLot.getSize()== 0){
					break;
				}
			}
			//1:入口空闲 2:场内没满 3:(为仿真方便，前250秒内) 产生新的入场车
			if(!Entrance.getInstance().isUsed() && !parkingLot.isFull() && timeCount < 250){
				Car randomCar = CarFactory.produceCar(e);
				if(randomCar != null){
					cars.add(randomCar);
				}
			}
			//入口和出口对象每秒检查操作是否完成，并更新状态，例如栏杆是否完全降下，修改栏杆状态
			entrance.refresh();
			exit.refresh();
			//刷新所有车辆对象的状态
			int deleteCarNum = -1;
			for(int i = 0; i < cars.size(); i++){
				Car oldCar = cars.get(i);
				Car newCar = cars.get(i).nextStep();
				if(newCar != null){
					cars.set(i, newCar);
				}
				//每有一辆车入场，in++
				if(oldCar instanceof EntranceReadCar && newCar instanceof EntrancePassCar){
					amountIn++;
				}
				if(cars.get(i) instanceof EntranceReadCar){
					entranceCar = (EntranceReadCar)cars.get(i);
				}
				if(cars.get(i) instanceof DeadCar){
					deleteCarNum = i;
					exitCar = (DeadCar)cars.get(i);
					if(exitCar.getDeadReason().equals("hasPassed")){
						parkingTimeSum += exitCar.getParkingTime();
						amountOut++;
					}
				}
			}
			if(deleteCarNum != -1){
				cars.remove(deleteCarNum);	
			}
			//打印出车场状态
			print();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
			System.out.print("过了1秒，");
		}
		writerLogInfo.setTimeSimulateEnd(Calendar.getInstance());
		writerLogInfo.writeToFile();
	}
	
	/**
	 * 主控打印函数
	 */
	public void print(){
		printSpace(0);//0表示上排车位
		printEntrance();
		printLane();
		printExit();
		printSpace(1);//1表示下排车位
	}
	
	/**
	 * 打印停车位的状态：“--”表空闲，否则为车辆ID
	 * @param flag 标识0为打印上排车位，1为打印下排车位
	 */
	public void printSpace(int flag){
		System.out.print("  |##|");
		for(int i = flag; i < parkingLot.getCapacity(); i += 2){
			Car car = parkingLot.getSpaceManager().get(i);
			if(car != null){
				System.out.print(car.getEmployee().getId().length() < 2 ? "0" + car.getEmployee().getId() : car.getEmployee().getId());
			}else{
				System.out.print("--");
			}
			System.out.print("|");
		}
		System.out.println("##|");
	}

	/**
	 * 打印车道空闲时为空格，否则为车辆ID
	 */
	/*public void printLane(){
		for(int i = 0; i < parkingLot.getDriveLaneManager().getLength(); i++){
			Car car = parkingLot.getDriveLaneManager().getState(i);
			if(car != null){
				System.out.print(car.getId().length() < 2 ? "0" + car.getId() : car.getId());
			}else{
				System.out.print("  ");//两个空！
			}
			if(i != parkingLot.getDriveLaneManager().getLength() - 1){
				System.out.print(" ");
			}
		}
	}*/
	
	public void printLane(){
		for(int i = 0; i < parkingLot.getDriveLaneManager().getLength(); i++){
			Car car = parkingLot.getDriveLaneManager().getState(i);
			if(car != null){
				System.out.print(car.getEmployee().getId().length() < 2 ? "0" + car.getEmployee().getId() : car.getEmployee().getId());
			}else if(i + 1 < parkingLot.getDriveLaneManager().getLength() && parkingLot.getDriveLaneManager().getState(i + 1) != null){
				System.out.print("@@");//两个空！
			}else{
				System.out.print("  ");//两个空！
			}
			if(i != parkingLot.getDriveLaneManager().getLength() - 1){
				System.out.print(" ");
			}
		}
	}
	
	/**
	 * 打印入口 “o”即(open)入口开启; “c”即(closed)入口关闭
	 */
	public void printEntrance(){
		if(entranceCar != null){
			String entranceCarNum = entranceCar.getEmployee().getId();
			System.out.print(entranceCarNum.length() < 2 ? "0" + entranceCarNum : entranceCarNum);
			entranceCar = null;
		}else{
			System.out.print("  ");
		}
		if(entrance.getState() == Entrance.OPENED){
			System.out.print("O");
		}else{
			System.out.print("C");
		}
	}
	
	/**
	 * 打印出口 “o”即(open)出口开启; “c”即(closed)出口关闭
	 */
	public void printExit(){
		if(exit.getState() == Exit.OPENED){
			System.out.print("O");
		}else{
			System.out.print("C");
		}
		if(exitCar != null && exitCar.getDeadReason().equals("hasPassed")){
			String exitCarNum = exitCar.getEmployee().getId();
			System.out.print(exitCarNum.length() < 2 ? "0" + exitCarNum : exitCarNum);
			exitCar = null;
		}
		System.out.println();
	}
	
	public static void main(String[] args){
		PropertyConfigurator.configure("./config/log4j.properties");
		new Park(20, 15).simulate();
	}
}
