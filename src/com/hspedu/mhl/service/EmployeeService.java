package com.hspedu.mhl.service;

import com.hspedu.mhl.dao.EmployeeDAO;
import com.hspedu.mhl.domain.Employee;

import java.io.UnsupportedEncodingException;
import java.security.*;


public class EmployeeService {
    private EmployeeDAO employeeDAO = new EmployeeDAO();

    public Employee getEmployeeByIdAndPwd(String empId, String pwd) {
        String sql = "select * from employee where empId = ? and pwd = MD5(?)";
        Employee employee = employeeDAO.querySingle(sql, Employee.class, empId, pwd);
        return employee;
    }
}
