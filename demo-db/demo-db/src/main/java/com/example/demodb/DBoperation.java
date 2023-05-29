package com.example.demodb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBoperation {

    private  Connection connection=null;
    Logger logger= LoggerFactory.getLogger(DBoperation.class);
    DBoperation(){

        try {
            createConnection();
            //After Getting the connection from the db we can create the table in that db here the db is employee_id
            createEmployeeTable();
        }
        catch(Exception e)
        {
            logger.error("Exception has been raised on creating the connection ",e);
        }
    }
    private void createConnection() throws SQLException {
        String url="jdbc:mysql://localhost:3306/employee_db";
         this.connection= DriverManager.getConnection(url,"root","Pranay@5");
    }

    public void createEmployeeTable() throws SQLException {
        //create table employee(id int,name varchar,age int)
        String sqlQuery="create table  if not exists employee(id int primary key auto_increment,name varchar(30), age int)";
        Statement statement=this.connection.createStatement();
        statement.execute(sqlQuery);

    }

    public void createEmployee(Employee employee) throws SQLException {
        /*
        Inserts an Employee in DB
        Insert into Employee(id,name,age) values(1,Pranay,age)
         */
        String sqlQuery="insert into employee(name,age) values('"+employee.getName()+"',"+employee.getAge()+")";
        Statement statement=this.connection.createStatement();
        int result=statement.executeUpdate(sqlQuery);
        logger.info("No of Employees Created {}",result);
    }

    public void createEmployeeDynamic(Employee employee) throws SQLException {
        /*
        Inserts an Employee in DB
        Insert into Employee(id,name,age) values(1,Pranay,age)
         */
        String sqlQuery="insert into employee(name,age) values(?,?)";

        PreparedStatement statement=this.connection.prepareStatement(sqlQuery);
        statement.setString(1, employee.getName());
        statement.setInt(2,employee.getAge());
        int result=statement.executeUpdate();
        logger.info("No of Employees Created {}",result);
    }
    public Employee getEmployee(Integer employeeId) throws SQLException {
        /*
        THis function returns the logic to return an employee by  giving his id.
        select * from employee where id=employeeId
         */
        String sqlQuery="select * from employee where id="+employeeId;
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sqlQuery);
        Employee employee=null;
        while(resultSet.next())
        {
            Integer id=resultSet.getInt(1);
            String name=resultSet.getString("name");
            Integer age=resultSet.getInt(3);
            if(id==employeeId)
            {
                employee=new Employee(id,name,age);
                return employee;
            }
            //here there are two overloaded functions for getInt via ID and the Colomn name thats up to you, Iam using via coloumn index and coloumn name randomly

        }
        return null;

    }

    public Employee getEmployeeDynamic(Integer employeeId) throws SQLException {
        /*
        THis function returns the logic to return an employee by  giving his id.
        select * from employee where id=employeeId
         */
        String sqlQuery="select * from employee where id=?";
        PreparedStatement statement=connection.prepareStatement(sqlQuery);
        statement.setInt(1,employeeId);
        ResultSet resultSet=statement.executeQuery();
        Employee employee=null;
        while(resultSet.next())
        {
            Integer id=resultSet.getInt(1);
            String name=resultSet.getString("name");
            Integer age=resultSet.getInt(3);
            if(id==employeeId)
            {
                employee=new Employee(id,name,age);
                return employee;
            }
            //here there are two overloaded functions for getInt via ID and the Colomn name thats up to you, Iam using via coloumn index and coloumn name randomly

        }
        return null;

    }

    public List<Employee> getEmployees() throws SQLException {
        /*
        This function has the logic to get all the employees from the data base;
        Even if you Use Prepared Statements here You cant get the Optimization because there are no parameters here
         */
        String sqlQuery="select * from employee";
        Statement statement=connection.createStatement();
        ResultSet resultSet=statement.executeQuery(sqlQuery);
        List<Employee> employeeList=new ArrayList<>();
        while(resultSet.next())
        {
            Integer id=resultSet.getInt(1);
            //here there are two overloaded functions for getInt via ID and the Colomn name thats up to you, Iam using via coloumn index and coloumn name randomly
            String name=resultSet.getString("name");
            Integer age=resultSet.getInt(3);
            Employee employee=new Employee(id,name,age);
            employeeList.add(employee);
        }
       return employeeList;
    }

    public int deleteEmployee(Integer id) throws SQLException {
        Statement statement=connection.createStatement();
        String sqlQuery="delete from table employee where id ="+id;
        return statement.executeUpdate(sqlQuery);
    }

    public int deleteEmployeeDynamic(Integer id) throws SQLException {
        String sqlQuery="delete from employee where id =?";
        PreparedStatement statement=connection.prepareStatement(sqlQuery);
        statement.setInt(1,id);
        return statement.executeUpdate();
    }

    public int putUpdateEmployeeDynamic(Employee employee) throws SQLException {
        String sqlQuery="Update employee set name=?,age=? where id=?";
        PreparedStatement preparedStatement=this.connection.prepareStatement(sqlQuery);
        preparedStatement.setString(1,employee.getName());
        preparedStatement.setInt(2,employee.getAge());
        preparedStatement.setInt(3,employee.getId());
        return preparedStatement.executeUpdate();
    }

    public int patchUpdateEmployeeDynamic(Employee employee) throws SQLException {
        boolean Name=false,Age=false;
        String innerQuery="";
        if(employee.getName()!=null)
        {
            Name=true;
            innerQuery+="name=?,";
        }
        if(employee.getAge()!=null)
        {
            Age=true;
            innerQuery+="age=?,";
        }
        innerQuery=innerQuery.substring(0,innerQuery.length()-1);
        String sqlQuery="Update employee set "+innerQuery+" where id=?";
        PreparedStatement preparedStatement=this.connection.prepareStatement(sqlQuery);
        int c=1;
        if(Name)
        {
            preparedStatement.setString(c,employee.getName());
            c++;
        }
        if(Age)
        {
            preparedStatement.setInt(c,employee.getAge());
            c++;
        }
        preparedStatement.setInt(c,employee.getId());
        return preparedStatement.executeUpdate();
    }

}
