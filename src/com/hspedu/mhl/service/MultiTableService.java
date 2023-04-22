package com.hspedu.mhl.service;

import com.hspedu.mhl.dao.MultiTableBeanDAO;
import com.hspedu.mhl.domain.MultiTableBean;

import java.util.List;

public class MultiTableService {
    MultiTableBeanDAO multiTableBeanDAO = new MultiTableBeanDAO();

    public List<MultiTableBean> listBill(int tableNo) {
        String sql = "select a.*,b.name as dishName from bill as a join menu as b on a.menuId = b.id where a.diningTableId = ? and state = 'unpaid'";
        return multiTableBeanDAO.queryMulti(sql, MultiTableBean.class, tableNo);
    }
}
