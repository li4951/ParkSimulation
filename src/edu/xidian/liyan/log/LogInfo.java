package edu.xidian.liyan.log;

/**
 * 日志信息类
 * @author 李延
 */
public class LogInfo {
	// 当前场内车数
	private int amountInPark;
	// 本次仿真累计入场车数
	private int amountEnter;
	// 本次仿真累计出场车数
	private int amountExit;
	// 本次仿真汽车的平均停车时间
	private int parkingTimeAverage;

	public LogInfo(int amountInPark, int amountEnter, int amountExit,
			int parkingTimeAverage) {
		super();
		this.amountInPark = amountInPark;
		this.amountEnter = amountEnter;
		this.amountExit = amountExit;
		this.parkingTimeAverage = parkingTimeAverage;
	}

	public int getAmountInPark() {
		return amountInPark;
	}

	public void setAmountInPark(int amountInPark) {
		this.amountInPark = amountInPark;
	}

	public int getAmountEnter() {
		return amountEnter;
	}

	public void setAmountEnter(int amountEnter) {
		this.amountEnter = amountEnter;
	}

	public int getAmountExit() {
		return amountExit;
	}

	public void setAmountExit(int amountExit) {
		this.amountExit = amountExit;
	}

	public int getParkingTimeAverage() {
		return parkingTimeAverage;
	}

	public void setParkingTimeAverage(int parkingTimeAverage) {
		this.parkingTimeAverage = parkingTimeAverage;
	}

}
