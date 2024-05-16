package presentation.bill;

import bll.BillBLL;
import model.Bill;
import model.Order;

import java.util.List;

public class BillController {
    private BillTableView billTableView;
    private BillBLL billBLL;

    public BillController(BillTableView billTableView, BillBLL billBLL) {
        this.billTableView = billTableView;
        this.billBLL = billBLL;

        refreshTable();
    }

    public void refreshTable() {
        List<Bill> billList = billBLL.findAllBills();
        billTableView.displayBills(billList);
    }
}
