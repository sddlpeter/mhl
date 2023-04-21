package com.hspedu.mhl.view;

import com.hspedu.mhl.domain.Bill;
import com.hspedu.mhl.domain.DiningTable;
import com.hspedu.mhl.domain.Dish;
import com.hspedu.mhl.domain.Employee;
import com.hspedu.mhl.service.BillService;
import com.hspedu.mhl.service.DiningTableService;
import com.hspedu.mhl.service.EmployeeService;
import com.hspedu.mhl.service.MenuService;
import com.hspedu.mhl.utils.Utility;

import java.util.List;
import java.util.UUID;

public class MHLView {
    public static void main(String[] args) {
        new MHLView().mainMenu();
    }

    private boolean loop = true;
    private String key;
    private EmployeeService employeeService = new EmployeeService();
    private DiningTableService diningTableService = new DiningTableService();
    private MenuService menuService = new MenuService();
    private BillService billService = new BillService();


    public void listDiningTables() {
        System.out.println("显示餐桌状态");
        List<DiningTable> list = diningTableService.list();
        System.out.println("餐桌号\t\t状态\t\t预定人\t\t预定电话");
        for (DiningTable t : list) {
            System.out.println(t);
        }
    }

    public void listMenu() {
        List<Dish> menu = menuService.getMenu();
        System.out.println("编号\t菜品名字\t品类\t价格");
        for (Dish d : menu) {
            System.out.println(d.getId() + "\t" + d.getName() + "\t" + d.getType() + "\t" + d.getPrice());
        }
    }

    public void placeOrder() {
        System.out.println("请选择点餐的桌号(-1退出)：");
        Integer tableNo = Utility.readInt();
        if (diningTableService.getDiningTableById(tableNo) == null) {
            System.out.println("您输入的桌号不存在!");
            return;
        }
        System.out.println("请选择菜品编号(-1退出)：");
        Integer menuId = Utility.readInt();
        if (menuService.getDishById(menuId) == null) {
            System.out.println("您输入的菜品不存在!");
            return;
        }
        System.out.println("请选择菜品数量(-1退出)：");
        Integer nums = Utility.readInt();
        System.out.println("确认点菜？(Y/N)");
        String s = Utility.readString(1);
        if ("n".equals(s)) {
            return;
        }
        billService.add(tableNo, menuId, nums);
        System.out.println("点餐成功~~");
    }

    public void listBill() {
        System.out.println("请输入餐桌号：");
        int i = Utility.readInt();
        List<Bill> bills = billService.listBillByTableId(i);
        System.out.println("\n账单编码\t账单号\t菜品号\t数量\t花费\t餐桌号\t日期\t状态");
        for (Bill b : bills) {
            System.out.println(b);
        }
    }

    public void checkout() {
        System.out.println("请选择要结账的餐桌编号(-1退出):");
        Integer tableNo = Utility.readInt();
        if (diningTableService.getDiningTableById(tableNo) == null) {
            System.out.println("您输入的桌号不存在!");
            return;
        }
        System.out.println("结账的方式(现金/Paypal/Zelle):");
        String payMethod = Utility.readString(20);
        System.out.println("确认是否结账(Y/N):");
        String check = Utility.readString(1);
        if ("n".equals(check)) {
            return;
        }
        billService.checkout(tableNo);
    }

    public void reservation() {
        System.out.println("============= 预定餐桌 ==============");
        System.out.println("请选择要预定的餐桌编号（-1退出）：");
        Integer tableNo = Utility.readInt();
        if ("-1".equals(tableNo)) {
            System.out.println("取消预定~~");
            return;
        }
        DiningTable t = diningTableService.getDiningTableById(tableNo);
        // System.out.println(t);
        if (t == null) {
            System.out.println("你要预定的餐桌编号不存在...");
            return;
        }
        if (!t.getState().equals("empty")) {
            System.out.println("您要预定的餐桌已被占用...");
            return;
        }
        System.out.println("确认是否预定（Y/N）：");
        String confirm = Utility.readString(1);
        if ("n".equals(confirm)) {
            return;
        }
        System.out.println("请输入预定人名字：");
        String name = Utility.readString(20);
        System.out.println("请输入预定人电话：");
        String phone = Utility.readString(20);
        boolean check = diningTableService.orderDiningTable(tableNo, name, phone);
        if (check) {
            System.out.println("预定成功~~");
        } else {
            System.out.println("预定失败~~");
        }
    }


    // 显示主菜单
    public void mainMenu() {
        while (loop) {
            System.out.println("============= 满汉楼 ==============");
            System.out.println("\t\t 1 登录满汉楼");
            System.out.println("\t\t 2 退出满汉楼");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println("输入员工号：");
                    String empId = Utility.readString(50);
                    System.out.println("输入密  码：");
                    String pwd = Utility.readString(50);
                    // 到数据库去判断

                    Employee employee = employeeService.getEmployeeByIdAndPwd(empId, pwd);
                    if (employee != null) {
                        System.out.println("============= 登陆成功["+ employee.getName() +"] ==============");
                        //显示二级菜单
                        while (loop) {
                            System.out.println("============= 满汉楼（二级菜单） ==============");
                            System.out.println("\t\t 1 显示餐桌状态");
                            System.out.println("\t\t 2 预定餐桌");
                            System.out.println("\t\t 3 显示所有菜品");
                            System.out.println("\t\t 4 点餐服务");
                            System.out.println("\t\t 5 查看账单");
                            System.out.println("\t\t 6 结账");
                            System.out.println("\t\t 9 退出满汉楼");
                            System.out.println("请输入你的选择：");

                            key = Utility.readString(1);
                            switch (key) {
                                case "1":
                                    listDiningTables();
                                    break;
                                case "2":
                                    System.out.println("========= 预定餐桌 =========");
                                    reservation();
                                    break;
                                case "3":
                                    System.out.println("========= 显示所有菜品 =========");
                                    listMenu();
                                    break;
                                case "4":
                                    System.out.println("========= 点餐服务 =========");
                                    placeOrder();
                                    break;
                                case "5":
                                    System.out.println("========= 查看账单 =========");
                                    listBill();
                                    break;
                                case "6":
                                    System.out.println("========= 结账 =========");
                                    checkout();
                                    break;
                                case "9":
                                    loop = false;
                                    break;
                                default:
                                    System.out.println("您的输入有误，请重新输入");
                                    break;
                            }

                        }
                    } else {
                        System.out.println("============= 登陆失败 ==============");

                    }

                    break;
                case "2":
                    loop = false;
                    break;
                default:
                    System.out.println("你的输入有误，请重新输入");

            }
        }

        System.out.println("你退出了满汉楼系统~~");
    }
}
