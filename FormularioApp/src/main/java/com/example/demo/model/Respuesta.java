package com.example.demo.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Pattern;

@Entity
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;         
    
    @Column(name = "EMPLOYEE_ID", unique = true)
    @NotBlank(message = "El ID del empleado es obligatorio")
    private String employeeId;

    @Column(name = "FIRST_NAME")
    @NotBlank(message = "El nombre es obligatorio")
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotBlank(message = "El apellido es obligatorio")
    private String lastName;

    @Column(name = "EMAIL", unique = true)
    @Email(message = "El formato del email no es válido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @Column(name = "PHONE_NUMBER", unique = true)
    @Pattern(regexp = "^[0-9\\-\\+\\(\\)\\s]+$", message = "El formato del teléfono no es válido")
    @NotBlank(message = "El número de teléfono es obligatorio")
    private String phoneNumber;

    @Column(name = "HIRE_DATE")
    @NotBlank(message = "La fecha de contratación es obligatoria")
    private String hireDate;

    @Column(name = "SALARY")
    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario no puede ser negativo")
    private Double salary;

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getEmployeeId() {return employeeId;}
    public void setEmployeeId(String employeeId) {this.employeeId = employeeId;}

    public String getFirstName() {return firstName;}
    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}
    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public String getPhoneNumber() {return phoneNumber;}
    public void setPhoneNumber(String phoneNumber) {this.phoneNumber = phoneNumber;}

    public String getHireDate() {return hireDate;}
    public void setHireDate(String hireDate) {this.hireDate = hireDate;}

    public Double getSalary() {return salary;}
    public void setSalary(Double salary) {this.salary = salary;}
}
