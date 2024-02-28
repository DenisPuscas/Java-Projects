package BusinessLogic;

import DAO.BillDAO;
import Model.Bill;

import javax.swing.table.DefaultTableModel;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Clasa BillBLL contine metode prin intermediul
 * carora se apeleaza operatiile pe tabelul Bill
 * din baza de date.
 * @author Denis Puscas
 * @since 18-05-2023
 */
public class BillBLL {
    private final BillDAO billDAO;

    public BillBLL(){
        billDAO = new BillDAO();
    }

    public List<Bill> findBill(){
        List<Bill> list = billDAO.findBill();
        if (list == null) {
            throw new NoSuchElementException("The logs was not found!");
        }
        return list;
    }

    public void insertBill(Bill b) {
        if (billDAO.insertBill(b) == null) {
            throw new NoSuchElementException("The bill was not inserted!");
        }
    }

    public int findBillLastId(){
        int id = billDAO.findLastId();
        if (id == -1){
            throw new NoSuchElementException("The id was not found!");
        }
        return id;
    }

    public DefaultTableModel getTableData(){
        DefaultTableModel data = billDAO.getTableData(findBill());
        if (data == null) {
            throw new NoSuchElementException("The clients was not found!");
        }
        return data;
    }

}
