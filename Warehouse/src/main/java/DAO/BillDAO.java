package DAO;

import Model.Bill;
import Connection.ConnectionFactory;

import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa prin intermediul careia se executa operatii
 * (insert, view all) la nivelul tabelului Bill.
 *
 * @author Denis Puscas
 * @since 18-05-2023
 */
public class BillDAO {
    protected static final Logger LOGGER = Logger.getLogger(BillDAO.class.getName());
    private static final String insertStatementString = "INSERT INTO warehousedb.log VALUES (?,?,?,?,?,?,?,?,?)";
    private static final String findStatementString = "SELECT * FROM warehousedb.log";
    private static final String findLastIdStatementString = "SELECT * FROM warehousedb.log ORDER BY id DESC LIMIT 1;";

    /**
     * Cauta si returneaza toate obiectele din tabelul Bill.
     * @return Lista cu elementele tabelului Bill.
     */
    public List<Bill> findBill() {
        List<Bill> list = new ArrayList<>();
        Connection dbConnection = null;
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try {
            dbConnection = ConnectionFactory.getConnection();
            findStatement = dbConnection.prepareStatement(findStatementString);
            rs = findStatement.executeQuery();
            while (rs.next()){
                list.add(new Bill(rs.getInt(1), rs.getInt(2), rs.getString(3),
                        rs.getString(4), rs.getString(5), rs.getInt(6),
                        rs.getFloat(7), rs.getFloat(8), rs.getString(9)));
            }
            return list;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING,"StudentDAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
        return null;
    }

    /**
     * Realizeaza inserarea obiectelor Bill in tabel.
     * @param bill Obiectul care urmeaza sa fie inserat.
     * @return Obiectul care a fost inserat.
     */
    public Bill insertBill(Bill bill) {
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        try {
            insertStatement = dbConnection.prepareStatement(insertStatementString);
            insertStatement.setInt(1, bill.getId());
            insertStatement.setInt(2, bill.getOrderId());
            insertStatement.setString(3, bill.getClientName());
            insertStatement.setString(4, bill.getPhone());
            insertStatement.setString(5, bill.getProductName());
            insertStatement.setInt(6, bill.getQuantity());
            insertStatement.setFloat(7, bill.getPrice());
            insertStatement.setFloat(8, bill.getTotal());
            insertStatement.setString(9, bill.getDate());
            insertStatement.executeUpdate();

            return bill;
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "BillDAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return null;
    }

    /**
     * Cauta care este cel mai mare id in tabelul Bill.
     * @return int Ultimul id din tabelul Bill.
     */
    public int findLastId(){
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findLastIdStatementString);
            rs = statement.executeQuery();
            rs.next();
            return rs.getInt(1);
        } catch (SQLException e) {
            return 0;
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public DefaultTableModel getTableData(List<Bill> obj){
        DefaultTableModel table = new DefaultTableModel();
        for (Field field : Bill.class.getDeclaredFields()){
            field.setAccessible(true);
            table.addColumn(field.getName());
        }
        for (Bill t: obj){
            Vector<Object> data = new Vector<>();
            for (Field field : Bill.class.getDeclaredFields()){
                field.setAccessible(true);
                Object value;
                try{
                    value = field.get(t);
                    data.add(value);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            table.addRow(data);
        }
        return table;
    }
}
