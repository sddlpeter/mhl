package com.hspedu.mhl.service;

import com.hspedu.mhl.dao.DiningTableDAO;
import com.hspedu.mhl.domain.DiningTable;

import java.util.List;

public class DiningTableService {
    DiningTableDAO diningTableDAO = new DiningTableDAO();
    public DiningTable getDiningTableById(Integer id) {
        String sql = "select * from diningTable where id = ?";
        return diningTableDAO.querySingle(sql, DiningTable.class, id);
    }

    public List<DiningTable> getAllTablesByState(String state) {
        String sql = "select * from diningTable where state = ?";
        List<DiningTable> tables = diningTableDAO.queryMulti(sql, DiningTable.class, state);
        System.out.println(tables);
        return tables;

    }


    public List<DiningTable> list() {
        String sql = "select id, state, orderName, orderTel from diningTable";
        List<DiningTable> tables = diningTableDAO.queryMulti(sql, DiningTable.class);
        // System.out.println(tables);
        // System.out.println(tables.get(0));
        return tables;

    }

    public boolean orderDiningTable(Integer tableNo, String name, String phone) {
        String sql = "update diningTable set orderName = ?, orderTel = ?, state = 'booked' where id = ?";
        int rows = diningTableDAO.update(sql, name, phone, tableNo);
        return rows > 0 ? true : false;
    }

    public void updateState(Integer id, String state) {
        String sql = "update diningTable set state = ? where id = ?";
        diningTableDAO.update(sql, state, id);
    }


    public void reset(Integer tableNo) {
        String sql = "update diningTable set state = 'empty', orderName = '', orderTel = '' where id = ?";
        diningTableDAO.update(sql, tableNo);
    }
}
