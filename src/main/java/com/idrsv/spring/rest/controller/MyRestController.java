package com.idrsv.spring.rest.controller;

import com.idrsv.spring.rest.entity.Employee;
import com.idrsv.spring.rest.exception_handling.EmployeeIncorrectData;
import com.idrsv.spring.rest.exception_handling.NoSuchEmployeeException;
import com.idrsv.spring.rest.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController          //контроллер, управляющий Rest запросами и ответами
@RequestMapping("/api")
public class MyRestController {

    @Autowired
    private EmployeeService employeeService;

    @GetMapping("/employees")
    public List<Employee> showAllEmployees() {
        List<Employee> allEmployees = employeeService.getAllEmployees();

        return allEmployees;
    }

    @GetMapping("/employees/{id}")  //получаем id из самого url адреса
    public Employee showEmployee(@PathVariable int id) {
        Employee employee = employeeService.getEmployee(id);

        if (employee == null) {
            throw new NoSuchEmployeeException("There is no  employee with ID = " + id + " in DataBase");
        }

        return employee;
    }


}


