package org.lequochai.non_reactive_jee.models;
import java.util.List;

import org.lequochai.non_reactive_jee.system.Setting;

import java.util.ArrayList;

public class EmployeeContainer {
    // Static fields:
    private static EmployeeContainer instance;

    // Static methods:
    public static EmployeeContainer getInstance() {
        if (instance == null) {
            instance = new EmployeeContainer();
        }

        return instance;
    }

    // Fields:
    private List<Employee> employees;

    // Constructors:
    public EmployeeContainer() {
        employees = new ArrayList<>();

        for (int i = 0;i<=Setting.EMPLOYEE_GENERATE_MAX;i++) {
            employees.add(
                new Employee(i, "Employee " + i)
            );

            System.out.println("Added employee " + i + " !");

            try {
                Thread.sleep(1000);
            }
            catch (Exception e){
                e.printStackTrace();
                break;
            }
        }
    }

    // Methods:
    public List<Employee> getAll() {
        return this.employees;
    }

    public List<Employee> get(String keyword) {
        List<Employee> result = new ArrayList<>();

        for (Employee employee : employees) {
            if (
                employee.toString()
                    .toLowerCase()
                    .contains(
                        keyword.toLowerCase()
                    )
            ) {
                result.add(employee);
            }
        }

        return result;
    }

    public Employee get(int id) {
        for (Employee employee : this.employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    public void insert(Employee employee) {
        Employee target = get(employee.getId());

        if (target != null) {
            throw new Error("Employee with given ID already exist!");
        }

        employees.add(employee);
    }

    public void update(Employee employee) {
        Employee target = get(employee.getId());

        if (target == null) {
            throw new Error("Employee with given ID doesn't exist!");
        }

        target.setName(employee.getName());
    }

    public void delete(int id) {
        Employee target = get(id);

        if (target == null) {
            throw new Error("Employee with given ID doesn't exist!");
        }

        employees.remove(target);
    }
}
