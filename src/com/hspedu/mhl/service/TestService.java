package com.hspedu.mhl.service;

public class TestService {
    public static void main(String[] args) {
        // new EmployeeService().getEmployeeByIdAndPwd("8612", "123456");
        DiningTableService diningTableService = new DiningTableService();
        diningTableService.getAllTablesByState("empty");
        diningTableService.list();
    }
}
