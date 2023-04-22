package com.hspedu.mhl.service;

import com.hspedu.mhl.dao.BillDAO;
import com.hspedu.mhl.domain.Bill;
import com.hspedu.mhl.domain.DiningTable;
import com.hspedu.mhl.domain.MultiTableBean;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class BillService {
    MenuService menuService = new MenuService();
    DiningTableService diningTableService = new DiningTableService();
    // MultiTableService multiTableService = new MultiTableService();

    BillDAO billDAO = new BillDAO();
    public List<Bill> listBills() {
        String sql = "select * from bill";
        return billDAO.queryMulti(sql, Bill.class);
    }

    public void add(Integer tableNo, Integer menuId, Integer nums) {
        String sql = "insert into bill values(null, ?, ?, ?, ?, ?, ?, 'unpaid')";
        String uuid = java.util.UUID.randomUUID().toString();
        billDAO.update(sql,uuid, menuId, nums, menuService.getPriceById(menuId) * nums, tableNo, new java.util.Date());
        diningTableService.updateState(tableNo, "dining");
    }

    public List<Bill> listBillByTableId(Integer tableId) {
        String sql = "select * from bill where diningTableId = ? and state = 'unpaid'";
        return billDAO.queryMulti(sql, Bill.class, tableId);
    }

    public void checkout(Integer tableNo) {
        List<Bill> bills = listBillByTableId(tableNo);
        System.out.println("\n账单编码\t账单号\t菜品号\t数量\t花费\t餐桌号\t日期\t状态");
        Double total = 0.0;
        for (Bill bill : bills) {
            System.out.println(bill);
            total += bill.getMoney();
        }
        System.out.println("----------- 总计 ------------");
        System.out.println("需支付： " + total);



        String sql = "update bill set state = 'paid' where diningTableId = ? and state = 'unpaid'";
        billDAO.update(sql, tableNo);
        diningTableService.reset(tableNo);


    }
}


//    create table bill (
//    -> id int primary key auto_increment,
//            -> billId varchar(50) not null default '',
//        -> menuId int not null default 0,
//        -> nums int not null default 0,
//        -> money double not null default 0,
//        -> diningTableId int not null default 0,
//        -> billDate Date not null,
//        -> state varchar(50) not null default '') charset=utf8;
