package edu.xidian.liyan.park.car;

import java.util.ArrayList;
import java.util.List;

/**
 * 员工数据访问对象，作为管理员工对象的唯一接口，同样使用单例模式
 * 构造方法为私有，通过getInstance获取此对象
 * @author 李延
 */
public class EmployeeDao {

	//员工集合
	private List<Employee> employees = new ArrayList<Employee>();
	//员工数量
	private int employeeAmount;
	
	private static EmployeeDao employeeDao = null;

	/**
	 * @return 获取员工数据访问对象
	 */
	public static EmployeeDao getInstance() {
		if (employeeDao == null) {
			employeeDao = new EmployeeDao();
		}
		return employeeDao;
	}
 
	private EmployeeDao() {
		super();
	}
	
	/**
	 * 初始化员工信息
	 * @param employeeAmount 员工数量
	 */
	public void initEmployees(int employeeAmount){
		for(int i = 1; i <= employeeAmount; i++){
			employees.add(new Employee(String.valueOf(i)));
		}
		this.employeeAmount = employeeAmount;
	}
	
	/**
	 * @param id 判断是否合法的id
	 * @return 返回该id是否属于系统中员工
	 */
	public boolean contains(String id){
		for(int i = 0; i < employeeAmount; i++){
			if(employees.get(i).getId().equals(id)){
				return true;
			}
		}
		return false;
	}
	
}
