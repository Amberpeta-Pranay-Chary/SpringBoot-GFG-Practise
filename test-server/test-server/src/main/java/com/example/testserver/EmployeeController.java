package com.example.testserver;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class EmployeeController {
    /*
    Crud Operations that can Handle Employee Life Cycle
     */

    HashMap<Integer,Employee> employeesHashMap=new HashMap<>();



    @GetMapping("/employee")
    public List<Employee> getEmployees()
    {
        return this.employeesHashMap.values().stream().collect(Collectors.toList());
    }

    @GetMapping("/employee/id/{id}")
    public Employee getEmployee(@PathVariable("id") Integer id)
    {
        return this.employeesHashMap.get(id);
    }

    @PostMapping("/employee")
    public void Employee(@RequestBody Employee employee) throws Exception {
        if(employee.getId()==null)
        {
            throw new Exception("Employee ID cannot be null");
        }
        this.employeesHashMap.put(employee.getId(),employee);
    }

    @PutMapping("/employee/id/{id}")
    public Employee UpdateEmployee(@RequestBody Employee employee) throws Exception
    {
        if(employee.getId()==null||(employeesHashMap.containsKey(employee)==false))
            throw new Exception("Unable to add the Emplaoyee Detail, Id may be null or He doesn't exist at all");
        return this.employeesHashMap.put(employee.getId(), employee);
    }
    @PatchMapping("/employee/id/{id}")
    public String patchUpdate(@RequestBody Employee employee)
    {
        Employee employeeFromHashMap=employeesHashMap.get(employee.getId());
        String Updated="";
        if(employee.getAge()!=null)
        {
            Updated+="Employee Age : "+employee.getAge();
            employeeFromHashMap.setAge(employee.getAge());
        }
        if(employee.getName()!=null)
            {if(Updated !="")
            {
                Updated+=" & ";
            }
            Updated+="Employee Name : "+employee.getName();
            employeeFromHashMap.setName(employee.getName());
        }
        return "Updated the below Fields\n"+Updated;

    }

    @DeleteMapping("/employee")
    public Employee deleteEmployee(@RequestParam("id") Integer id)
    {
        return this.employeesHashMap.remove(id);
    }



}
