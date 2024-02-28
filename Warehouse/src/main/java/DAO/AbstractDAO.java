package DAO;

import Connection.ConnectionFactory;

import javax.swing.table.DefaultTableModel;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clasa abstracta care realizeaza operatii in baza de date.
 * Datorita reflexiei, poate fi apelata cu oricare dintre obiectele
 * din pachetul Model (mai putin Bill), realizand operatiile
 * necesare in baza de date: inserare, stergere, editare, cautare
 * dupa id, afisarea tuturor elementelor.
 *
 * @param <T> Orice obiect.
 * @author Denis Puscas
 * @since 18-05-2023
 */
public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());
    private final String DATABASE = "warehousedb.";
    private String insertStatementString;
    private final String selectStatementString;
    private final String updateStatementString;
    private final String deleteStatementString;
    private final String findAllStatementString;
    private final String findLastIdStatementString;
    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        insertStatementString = "INSERT INTO " + DATABASE + type.getSimpleName().toLowerCase() + " VALUES (";
        for (int i = 0; i < type.getDeclaredFields().length - 1; i++) {
            insertStatementString += "?,";
        }
        insertStatementString += "?);";
        updateStatementString = "UPDATE " + DATABASE + type.getSimpleName() + " SET ";
        findAllStatementString = "SELECT * FROM " + DATABASE + type.getSimpleName() + ";";
        deleteStatementString = "DELETE FROM " + DATABASE + type.getSimpleName() + " WHERE id = ?;";
        findLastIdStatementString = "SELECT * FROM " + DATABASE + type.getSimpleName() + " ORDER BY id DESC LIMIT 1;";
        selectStatementString = "SELECT * FROM " + DATABASE + type.getSimpleName() + " WHERE id =?;";
    }

    /**
     * Aceasta metoda cauta in tabel id-ul cu numarul
     * cel mai mare si este folosita la inserarea
     * elementelor pentru incrementarea id-ului.
     * @return int Ultimul id sau null daca tabelul este gol.
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
//            LOGGER.log(Level.WARNING, type.getName() + "DAO:findLastId " + e.getMessage());
            return 0;
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Metoda care returneaza o lista cu toate
     * obiectele aflate in tabel, folosita pentru
     * afisarea acestora.
     * @return List Lista cu toate obiectele din tabel,
     * null daca tabelul este gol.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(findAllStatementString);
            rs = statement.executeQuery();
            return createObjects(rs);
        } catch (SQLException e) {
            return new ArrayList<>();
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Aceasta metoda cauta obiecut din tabel
     * care are id-ul primit ca parametru.
     * @param id Id-ul obiectului cautat.
     * @return object Obiectul cautat sau null
     * daca acesta nu a fost gasit.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(selectStatementString);
            statement.setInt(1, id);
            rs = statement.executeQuery();
            List<T> list = createObjects(rs);
            if (list.isEmpty()){return null;}
            return list.get(0);
        } catch (SQLException e) {
            return null;
        } finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    /**
     * Metoda apelata in procesele de cautare.
     * Creaza o lista cu obiectele ale caror
     * parametrii se afla in baza de date.
     * @param resultSet Datele obtinute in urma executiei
     *                  interogarii in baza de date.
     * @return List<object> Lista cu obiectele create.
     */
    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();
        Constructor[] ctors = type.getDeclaredConstructors();
        Constructor ctor = null;
        for (int i = 0; i < ctors.length; i++) {
            ctor = ctors[i];
            if (ctor.getGenericParameterTypes().length == 0)
                break;
        }
        try {
            while (resultSet.next()) {
                ctor.setAccessible(true);
                T instance = (T)ctor.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    String fieldName = field.getName();
                    Object value = resultSet.getObject(fieldName);
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException |
                 InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * Se ocupa de inserarea obiectelor in baza de date.
     * @param t Obiectul care urmeaza sa fie inserat.
     * @return object Obiectul care a fost inserat.
     * @throws Exception Eroare rezultata in timpul inserarii.
     */
    public T insert(T t) throws Exception {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement insertStatement = null;
        int index = 1;
        try {
            insertStatement = connection.prepareStatement(insertStatementString);
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, type);
                Method getter = pd.getReadMethod();
                if (field.getType().getName().equals("int")){
                    insertStatement.setInt(index++, (int)getter.invoke(t));
                }else if (field.getType().getName().equals("float")){
                    insertStatement.setFloat(index++, (float)getter.invoke(t));
                }else{
                    insertStatement.setString(index++, (String)getter.invoke(t));
                }
            }
            insertStatement.executeUpdate();
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new Exception(e.getMessage());
        } finally {
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    /**
     * Se ocuma de editarea atributelor unui obiect
     * in baza de date.
     * @param t Obiectul a carui atribute urmeaza
     *          sa fie editate.
     * @return object Obiectul care a fost editat
     * sau null daca acesta nu a fost actualizat
     */
    public T update(T t) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        try {
            int id = -1;
            String updateString = updateStatementString;
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                PropertyDescriptor pd = new PropertyDescriptor(fieldName, type);
                Method getter = pd.getReadMethod();
                if (fieldName.equals("id")){
                    id = (int)getter.invoke(t);
                    continue;
                }
                updateString += fieldName + " = '" + getter.invoke(t) + "', ";
            }
            updateString = updateString.substring(0, updateString.length() - 2);
            updateString += " WHERE id = " + id + ";";
            updateStatement = connection.prepareStatement(updateString);
            updateStatement.executeUpdate();
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, "ClioentDAO: update " + e.getMessage());
            return null;
        } finally {
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(connection);
        }
        return t;
    }

    public DefaultTableModel getTableData(List<T> obj){
        DefaultTableModel table = new DefaultTableModel();
        for (Field field : type.getDeclaredFields()){
            field.setAccessible(true);
            table.addColumn(field.getName());
        }
        for (T t: obj){
            Vector<Object> data = new Vector<>();
            for (Field field : type.getDeclaredFields()){
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

    /**
     * Se ocupa ce stergerea obiectelor din baza de date.
     * @param t Obiectul care urmeaza sa fie sters.
     * @return true daca obiectul a fost sters, false in caz contrar.
     */
    public boolean delete(T t) {
        Connection connection = ConnectionFactory.getConnection();
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = connection.prepareStatement(deleteStatementString);
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                if (fieldName.equals("id")){
                    PropertyDescriptor pd = new PropertyDescriptor(fieldName, type);
                    Method getter = pd.getReadMethod();
                    deleteStatement.setInt(1, (int)getter.invoke(t));
                    break;
                }
            }
            deleteStatement.executeUpdate();
        } catch (SQLException | IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            LOGGER.log(Level.WARNING, "ClioentDAO: delete " + e.getMessage());
            return false;
        } finally {
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(connection);
        }
        return true;
    }

}
