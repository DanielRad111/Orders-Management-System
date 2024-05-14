package bll;

import dao.BillDAO;
import model.Bill;
import model.Order;

import java.sql.SQLException;
import java.util.List;
import java.util.NoSuchElementException;

public class BillBLL {
    private BillDAO billDAO;
    public BillBLL() {
        this.billDAO = new BillDAO();
    }

    public List<Bill> findAllBills(){
        List<Bill> bills = billDAO.findAll();
        if(bills == null){
            throw new NoSuchElementException();
        }
        return bills;
    }

    public Bill findBillById(int id){
        Bill bill = billDAO.findById(id);
        if(bill == null){
            throw new NoSuchElementException();
        }
        return bill;
    }

    public boolean insertBill(Bill bill) throws IllegalAccessException {
        return billDAO.insert(bill);
    }

    public void updateBill(Bill bill) throws IllegalAccessException {
        billDAO.update(bill);
    }

    public void deleteBill(Bill bill) throws IllegalAccessException, SQLException {
        billDAO.delete(bill);
    }
}
