package edu.xidian.liyan.park.car;

import java.util.ArrayList;
import java.util.List;

/**
 * Ա�����ݷ��ʶ�����Ϊ����Ա�������Ψһ�ӿڣ�ͬ��ʹ�õ���ģʽ
 * ���췽��Ϊ˽�У�ͨ��getInstance��ȡ�˶���
 * @author ����
 */
public class EmployeeDao {

	//Ա������
	private List<Employee> employees = new ArrayList<Employee>();
	//Ա������
	private int employeeAmount;
	
	private static EmployeeDao employeeDao = null;

	/**
	 * @return ��ȡԱ�����ݷ��ʶ���
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
	 * ��ʼ��Ա����Ϣ
	 * @param employeeAmount Ա������
	 */
	public void initEmployees(int employeeAmount){
		for(int i = 1; i <= employeeAmount; i++){
			employees.add(new Employee(String.valueOf(i)));
		}
		this.employeeAmount = employeeAmount;
	}
	
	/**
	 * @param id �ж��Ƿ�Ϸ���id
	 * @return ���ظ�id�Ƿ�����ϵͳ��Ա��
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
