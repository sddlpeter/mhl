package com.hspedu.mhl.service;

import com.hspedu.mhl.dao.MenuDAO;
import com.hspedu.mhl.domain.Dish;

import java.util.List;

public class MenuService {
    MenuDAO menuDAO = new MenuDAO();

    public List<Dish> getMenu() {
        String sql = "select * from menu";
        List<Dish> dishes = menuDAO.queryMulti(sql, Dish.class);
        // System.out.println(dishes);
        return dishes;
    }

    public Double getPriceById(Integer id) {
        Object o = menuDAO.queryScalar("select price from menu where id = ?", id);
        return (Double) o;
    }

    public Dish getDishById(Integer id) {
        return menuDAO.querySingle("select * from menu where id = ?", Dish.class, id);
    }
}
