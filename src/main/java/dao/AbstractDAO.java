package dao;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import connection.ConnectionFactory;

public class AbstractDAO<T> {
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM `");
        sb.append(type.getSimpleName());
        sb.append("` WHERE " + field + " =?");
        return sb.toString();
    }

    public String createInsertQuery(String[] fields)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO `");
        sb.append(type.getSimpleName()+"`(");
        for(int i = 0; i< fields.length; ++i)
        {
            sb.append("`"+fields[i]+"`");
            if(i != fields.length-1){
                sb.append(",");
            }
        }
        sb.append(") VALUES (");
        for(int i=0; i<fields.length; ++i)
        {
            sb.append("?");
            if(i != fields.length-1){
                sb.append(",");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public String createDeleteQuery(String field)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM `");
        sb.append(type.getSimpleName());
        sb.append("` WHERE " + field + "= ?");

        return sb.toString();
    }

    public String createEditQuery(String[] args)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE `");
        sb.append(type.getSimpleName() + "` SET ");
        for(int i=1; i<args.length; ++i)
        {
            sb.append(args[i]+"=?");
            if(i!=args.length-1)
                sb.append(",");
        }
        sb.append(" WHERE " + args[0] + "=?");
        return sb.toString();
    }

    public List<T> findAll() {
        String query = new String("SELECT * FROM `"+type.getSimpleName()+"`");
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try
        {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            resultSet = statement.executeQuery();
            List<T> list = createList(resultSet);
            return (List<T>) list;
        }
        catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            return createObjects(resultSet).get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
            return null;
        }
        catch(IndexOutOfBoundsException | NullPointerException e)
        {
            LOGGER.log(Level.WARNING, "The "+type.getSimpleName() + " with the id="+id+" does not exists");
            return null;
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    private List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        Constructor[] constructors = type.getDeclaredConstructors();// constructors
        try {
            while (resultSet.next())
            {
                int index = 0 ;
                Object[] args = new Object[constructors[0].getGenericParameterTypes().length]; // take the first constructor
                for (Field field : type.getDeclaredFields()) { // iterate class fields

                    Object value = resultSet.getObject(field.getName()); // extract an Obj - A String that contains the column name
                    if (value instanceof Integer){
                        Integer aux = (Integer) value;
                        args[index] = aux ;
                    }
                    else{
                        String aux = (String) value;
                        args[index] = aux ;
                    }
                    index++;
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                }
                T newObject = (T) constructors[0].newInstance(args);
                list.add(newObject);
            }
        }
        catch (InstantiationException e) {
            e.printStackTrace();
        }
        catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(T t) throws IllegalAccessException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int nrFields = 0;
        for(Field field : t.getClass().getDeclaredFields()) {
            nrFields++;
        }
        Object[] args = new Object[nrFields];
        String[] fields = new String[nrFields];
        int cnt = 0;
        for(Field field : t.getClass().getDeclaredFields()){
            fields[cnt] = field.getName();
            field.setAccessible(true);
            Object value = field.get(t);
            if(value instanceof Integer){
                Integer x = (Integer)value;
                args[cnt++] = x;
            } else{
                String x = value.toString();
                args[cnt++] = x;
            }
        }
        String query = createInsertQuery(fields);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for(int i = 0 ;i  < cnt ; ++i){
                if(args[i] instanceof Integer){
                    statement.setInt(i+1, (Integer) args[i]);
                }
                else{
                    statement.setString(i+1, (String) args[i]);
                }
            }
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
            return false;
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return true;
    }

    public void update(T t) throws IllegalAccessException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        int nrFields = 0;
        for(Field field : t.getClass().getDeclaredFields()) {
            nrFields++;
        }
        Object[] args = new Object[nrFields];
        String[] fields = new String[nrFields];
        int cnt = 0;
        for(Field field : t.getClass().getDeclaredFields()){
            fields[cnt] = field.getName();
            field.setAccessible(true);
            Object value = field.get(t);
            if(value instanceof Integer){
                Integer x = (Integer)value;
                args[cnt++] = x;
            } else{
                String x = value.toString();
                args[cnt++] = x;
            }
        }
        String query = createEditQuery(fields);
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            for(int i = 1 ;i  < cnt ; ++i){
                if(args[i] instanceof Integer){
                    statement.setInt(i, (Integer) args[i]);
                }
                else{
                    statement.setString(i, (String) args[i]);
                }
            }
            statement.setInt(cnt,(Integer) args[0]);
            statement.execute();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }

    public List<T> createList(ResultSet resultSet) throws SQLException, InvocationTargetException, IllegalAccessException, IntrospectionException, InstantiationException, NoSuchMethodException
    {
        List<T> list = new ArrayList<T>();
        Constructor<?>[] constructor =  type.getConstructors();
        String fields = "" ;
        while(resultSet.next()) {
            Object[] args = new Object[constructor[0].getGenericParameterTypes().length];
            int i = 0;
            fields = "";
            for (Field field : type.getDeclaredFields()) {
                String fieldName = field.getName();
                fields = fields + "" + fieldName + " ";
                Object value = resultSet.getObject(fieldName);
                if (value instanceof Integer) {
                    Integer x = (Integer) value;
                    args[i++] = x;
                } else {
                    String x = value.toString();
                    args[i++] = x;
                }
                PropertyDescriptor propertyDescriptor = new PropertyDescriptor(fieldName, type);
                Method method = propertyDescriptor.getWriteMethod();
            }
            T obj = (T) constructor[0].newInstance(args);
            list.add(obj);
        }
        return list;
    }

    public void delete(T t) throws SQLException, IllegalAccessException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        Field field = t.getClass().getDeclaredFields()[0];
        field.setAccessible(true);
        Object value = field.get(t);
        String query = createDeleteQuery(field.getName());
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, (Integer) value);
            statement.execute();
        }
        catch (SQLException e)
        {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
        }
        finally
        {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
    }
}
