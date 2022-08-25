package org.java;

import org.java.dbUtils.DBRecord;
import org.java.dto.Employee;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    @Test
    void dbDemoTest() {
        DBRecord<Employee> employeeDBRecord = new DBRecord<>(Employee.class);
        Employee employee = employeeDBRecord.get(41848L);
        assertThat(employee.getId()).isEqualTo(41848L);
    }
}