package com.example.demodb;

import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
@RestController
public class EmployeeController {
    HashMap<Integer,Employee> employeesHashMap=new HashMap<>();

    private DBoperation dBoperation;
    EmployeeController()
    {
        this.dBoperation=new DBoperation();
    }
    @GetMapping("/employee")
    public List<Employee> getEmployees() throws SQLException {
        return dBoperation.getEmployees();
    }

    @GetMapping("/employee/id/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id) throws SQLException {
        return dBoperation.getEmployeeDynamic(id);
    }

    @PostMapping("/employee")
    public void Employee(@RequestBody Employee employee) throws Exception {

        dBoperation.createEmployeeDynamic(employee);

    }

    @PutMapping("/employee/id/{id}")
    public String UpdateEmployee(@RequestBody Employee employee) throws Exception
    {
        if(dBoperation.putUpdateEmployeeDynamic(employee)>=1)
        {
            return "Employee with id : "+employee.getId()+" Updated";
        }
        return "Employee not Updated, Try to give the Proper Employee ID!";

    }
    @PatchMapping("/employee/id/{id}")
    public String patchUpdate(@RequestBody Employee employee) throws SQLException {
        if(dBoperation.patchUpdateEmployeeDynamic(employee)>=1)
            return "Employee with id : "+employee.getId()+" Updated!";
        return "Employee not Updated ,  Try to give the Proper Employee ID!";
    }

    @DeleteMapping("/employee")
    public String deleteEmployee(@RequestParam("id") Integer id) throws SQLException {
        int x=dBoperation.deleteEmployeeDynamic(id);
        if(x==1)
            return "Employee Removed with Id : "+id;
        return "Employee not present in the data base";

    }


}
