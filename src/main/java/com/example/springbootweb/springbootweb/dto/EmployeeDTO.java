package com.example.springbootweb.springbootweb.dto;

import com.example.springbootweb.springbootweb.annotations.EmployeeRoleValidation;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
//@Data //No more need to define a Constructor, and other

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    //Entity about employees

    private Long id;
    @NotNull(message = "Employee key and value must contain")
    @NotEmpty(message = "Emploee key must cointains some value")
    @NotBlank(message = "Name of the employee can't be blank")
    @Size(min = 3, max = 10, message = "Name of the characters should be in between [3,10]")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Max(value = 60, message = "Age of Employee should be valid")
    @Min(value = 18, message = "Age of Employee should be valid")
    private Integer age;

    @PastOrPresent(message = "Invalid date")
    private LocalDate dateOfJoining;

//    @Pattern(regexp = "^(USER|ADMIN)$", message = "Role should be valid")
    @EmployeeRoleValidation
    @NotNull(message = "role can't be null")
    private String role;

    @AssertTrue(message = "Employee's status should be active")
    //@AssertFalse(message = "Choose wisely")
    private Boolean isActive;

//    public EmployeeDTO(Long id, String name, String email, Integer age, LocalDate dateOfJoining, Boolean isActive) {
//        this.id = id;
//        this.name = name;
//        this.email = email;
//        this.age = age;
//        this.dateOfJoining = dateOfJoining;
//        this.isActive = isActive;
//
//
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public Integer getAge() {
//        return age;
//    }
//
//    public void setAge(Integer age) {
//        this.age = age;
//    }
//
//    public LocalDate getDateOfJoining() {
//        return dateOfJoining;
//    }
//
//    public void setDateOfJoining(LocalDate dateOfJoining) {
//        this.dateOfJoining = dateOfJoining;
//    }
//
//    public Boolean getIsActive() {
//        return isActive;
//    }
//
//    public void setIsActive(Boolean active) {
//        isActive = active;
//    }
}
