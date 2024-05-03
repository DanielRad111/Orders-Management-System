package dao;

import connection.ConnectionFactory;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO <T>{
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    private final Class<T> type;

    @SuppressWarnings("unchecked")
    public AbstractDAO(){
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public String createSelectQuery(String field){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT ");
        stringBuilder.append(" * ");
        stringBuilder.append("FROM `");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append("` WHERE " + field + " = ?");
        return stringBuilder.toString();
    }

    public String createInsertQuery(String[] fields){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO `");
        stringBuilder.append(type.getSimpleName() + "`(");
        for (int i = 0; i < fields.length; i++) {
            stringBuilder.append("`" + fields[i] + "`");
            if(i != fields.length-1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(") VALUES (");
        for(int i = 0; i < fields.length; i++){
            stringBuilder.append("?");
            if(i != fields.length-1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public String createDeleteQuery(String field){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM `");
        stringBuilder.append(type.getSimpleName());
        stringBuilder.append("` WHERE " + field + " = ?");
        return stringBuilder.toString();
    }

    public String createUpdateQuery(String[] args){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE `");
        stringBuilder.append(type.getSimpleName() + "` SET ");
        for(int i = 1; i < args.length; i++){
            stringBuilder.append(args[i] + "=?");
            if(i != args.length-1){
                stringBuilder.append(",");
            }
        }
        stringBuilder.append(" WHERE " + args[0] + " = ?");
        return stringBuilder.toString();
    }

    public List<T> findAll(){
        String query = new String("SELECT * FROM `" + type.getSimpleName() + "`");
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        try{
            findStatement = dbConnection.prepareStatement(query);
            rs = findStatement.executeQuery();
            List<T> list = createList(rs);
            return (List<T>) list;
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } catch (IntrospectionException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
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
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }


    public T findByName(String name){
        Connection dbConnection = null;
        PreparedStatement findStatement = null;
        ResultSet rs = null;
        String query = createSelectQuery("name");
        try{
            dbConnection = ConnectionFactory.getConnection();
            findStatement = dbConnection.prepareStatement(query);
            findStatement.setString(1, name);
            rs = findStatement.executeQuery();

            return createObjects(rs).get(0);
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
            return null;
        }finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(findStatement);
            ConnectionFactory.close(dbConnection);
        }
    }

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
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
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



    public List<T> createList(ResultSet rs) throws SQLException, IntrospectionException, InvocationTargetException, InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<T>();
        Constructor<?>[] constructor = type.getConstructors();
        String fields = "";
        while(rs.next()){
            Object[] args = new Object[constructor[0].getGenericParameterTypes().length];
            int i = 0;
            fields = "";
            for(Field field : type.getDeclaredFields()){
                String fieldName = field.getName();
                fields = fields + "" + fieldName + " ";
                Object value = rs.getObject(fieldName);
                if(value instanceof Integer){
                    Integer x = (Integer)value;
                    args[i++] = x;
                }else{
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

    public boolean insert(T t) throws IllegalAccessException{
        Connection dbConnection = null;
        PreparedStatement insertStatement = null;
        ResultSet rs = null;

        int nrFields = 0;
        for(Field field : t.getClass().getDeclaredFields()){
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
            }else{
                String x = value.toString();
                args[cnt++] = x;
            }
        }
        String query = createInsertQuery(fields);
        try{
            dbConnection = ConnectionFactory.getConnection();
            insertStatement = dbConnection.prepareStatement(query);
            for(int i = 0 ; i < cnt; i++){
                if(args[i] instanceof Integer){
                    insertStatement.setInt(i+1, (Integer) args[i]);
                }else{
                    insertStatement.setString(i+1, (String)args[i]);
                }
            }
            insertStatement.execute();
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:insert " + e.getMessage());
            return false;
        }finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(insertStatement);
            ConnectionFactory.close(dbConnection);
        }
        return true;
    }

    public void update(T t) throws IllegalAccessException{
        Connection dbConnection = ConnectionFactory.getConnection();
        PreparedStatement updateStatement = null;
        ResultSet rs = null;
        int nrFields = 0;
        for(Field field : t.getClass().getDeclaredFields()){
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
            }else{
                String x = value.toString();
                args[cnt++] = x;
            }
        }
        String query = createUpdateQuery(fields);
        try{
            updateStatement = dbConnection.prepareStatement(query);
            for(int i = 1; i < cnt; i++){
                if(args[i] instanceof Integer){
                    updateStatement.setInt(i, (Integer)args[i]);
                }else{
                    updateStatement.setString(i, (String)args[i]);
                }
            }
            updateStatement.setInt(cnt, (Integer) args[0]);
            updateStatement.execute();
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:update " + e.getMessage());
        }finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(updateStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
    public void delete(int id){
        Connection dbConnection = null;
        PreparedStatement deleteStatement = null;
        ResultSet rs = null;
        String query = createDeleteQuery("id");

        try{
            dbConnection = ConnectionFactory.getConnection();
            deleteStatement = dbConnection.prepareStatement(query);
            deleteStatement.setInt(1, id);
            rs = deleteStatement.executeQuery();
        }catch (SQLException e){
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        }finally {
            ConnectionFactory.close(rs);
            ConnectionFactory.close(deleteStatement);
            ConnectionFactory.close(dbConnection);
        }
    }
}
