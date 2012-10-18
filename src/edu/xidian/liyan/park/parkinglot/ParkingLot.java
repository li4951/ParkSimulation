package edu.xidian.liyan.park.parkinglot;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import edu.xidian.liyan.park.car.*;
import edu.xidian.liyan.park.drivelane.DriveLaneManager;
import edu.xidian.liyan.park.space.SpaceManager;

/**
 * 场内管理类
 * 完成对停车场内的管理，包括车道和车位
 * 作为唯一存在的管理类，同样为单例模式，构造方法私有，通过getInstance获得此管理对象
 * @author 李延
 */
public class ParkingLot {

	//场内容量
	private int capacity;
	//场内已有车辆数
	private int size;
	//场内车辆集合
	private Set<Car> carSet;
	//车道管理对象
	private DriveLaneManager driveLaneManager;
	//车位管理对象
	private SpaceManager spaceManager;
	
	private static ParkingLot parkingLot = null;
	
	private ParkingLot() {
		super();
	}
	
	/**
	 * @return 获得此管理类的一个实例
	 */
	public static ParkingLot getInstance(){  
        if(parkingLot == null){  
            parkingLot = new ParkingLot();  
        }  
        return parkingLot;  
    }  
	
	/**
	 * 初始化
	 * @param capacity 系统外部设置参数"n"(伪容量)，真正的总容量须乘2
	 */
	public void initParkingLot(int capacity) {
		this.capacity = capacity * 2;
		size = 0;
		carSet = new HashSet<Car>();
		driveLaneManager = new DriveLaneManager(capacity + 2);
		spaceManager = new SpaceManager(capacity * 2);
	}
	
	public int getCapacity() {
		return capacity;
	}

	public int getSize() {
		return size;
	}

	public DriveLaneManager getDriveLaneManager() {
		return driveLaneManager;
	}

	public SpaceManager getSpaceManager() {
		return spaceManager;
	}
	
	/**
	 * @param id 带检测车辆的id
	 * @return 场内是否已经包含此id
	 */
	public boolean contains(String id){
		for(Iterator<Car> it = carSet.iterator(); it.hasNext();){
			if(id != null && id.equals(it.next().getEmployee().getId())){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return 场内车辆是否已满
	 */
	public boolean isFull(){
		if(size >= capacity){
			return true;
		}
		return false;
	}
	
	/**
	 * @param car 待添加车对象
	 */
	public void add(Car car){
		if(carSet.add(car)){
			size++;
		}
	}
	
	/**
	 * @param car 待移除车队向
	 */
	public boolean remove(Car car){
		if(carSet.remove(car)){
			size--;
			return true;
		}
		return false;
	}
	
}
