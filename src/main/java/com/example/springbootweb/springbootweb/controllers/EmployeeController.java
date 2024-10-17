package com.example.springbootweb.springbootweb.controllers;

import com.example.springbootweb.springbootweb.dto.EmployeeDTO;
import com.example.springbootweb.springbootweb.entities.EmployeeEntity;
import com.example.springbootweb.springbootweb.services.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(path = "/employees")
public class EmployeeController {

//    @GetMapping(path = "/xyz")
//    public String displaySecretMessage(){
//        return "Hi baby xyz";
//    }

//    private final EmployeeRepository employeeRepository;
//public EmployeeController(EmployeeRepository employeeRepository) {
//    this.employeeRepository = employeeRepository;
//}

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }



    //(Approach 1)
//    @GetMapping(path = "/{employeeID}")
//    public EmployeeDTO getEmployeeById(@PathVariable Long employeeID){
//        //public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeID") Long id) ---> If u want to chane the variable name
//        return new EmployeeDTO(employeeID, "sushanta nandy","nandysushanta9@gmail.com",26, LocalDate.of(2024,01,18),true);
//
//    }

    //Approach 2
//    @GetMapping(path = "/{employeeID}")
//    public EmployeeDTO getEmployeeById(@PathVariable Long employeeID){
//        //public EmployeeDTO getEmployeeById(@PathVariable(name = "employeeID") Long id) ---> If u want to chane the variable name
////        return new EmployeeDTO(employeeID, "sushanta nandy","nandysushanta9@gmail.com",26, LocalDate.of(2024,01,18),true);
////        return employeeRepository.findById(employeeID).orElse(null);  ---> This was with repository
//        return  employeeService.getEmployeeById(employeeID); // This was with service
//
//    }

    //Approach 3
    @GetMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Long employeeID){
        Optional<EmployeeDTO> employeeDTO=employeeService.getEmployeeById(employeeID);
        return employeeDTO.
                map(employeeDTO1 -> ResponseEntity.ok(employeeDTO1)).
                orElse(ResponseEntity.notFound().build());
    }

//    @GetMapping
//    public String getAllEmployees(@RequestParam Integer age,
//                                  @RequestParam(required = false) String sortBy){
//        //we use required to maked the field as optional
//        return  "Hi sushanta you are is : "+ age + sortBy;
//    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees(@RequestParam(required = false) Integer age,
                                @RequestParam(required = false) String sortBy){
        //we use required to maked the field as optional
//        return  employeeRepository.findAll();
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }



//    @PostMapping
//    public String createNewEmployee(){
//        return "Hi Onboard Sushanta";
//    }

//    @PostMapping
//    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
//        inputEmployee.setId(100L);
//        return inputEmployee;
//    }

    //Approach -1
//    @PostMapping
//    public EmployeeDTO createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
////        return employeeRepository.save(inputEmployee);
//        return employeeService.createNewEmployee(inputEmployee);
//    }

    //Approach -2
    @PostMapping
    public ResponseEntity<EmployeeDTO> createNewEmployee(@RequestBody EmployeeDTO inputEmployee){
        EmployeeDTO savedEmployee = employeeService.createNewEmployee(inputEmployee);
        return new ResponseEntity<>(savedEmployee, HttpStatus.CREATED);
    }


    @PutMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> updateEmployeeById(@RequestBody EmployeeDTO employeeDTO,
                                          @PathVariable Long employeeID){
        return ResponseEntity.ok(employeeService.updateEmployeeById(employeeDTO, employeeID));

    }

    @DeleteMapping(path = "/{employeeID}")
    public String deleteEmployeeById(@PathVariable Long employeeID){
        return employeeService.deleteEmployeeById(employeeID);

    }

    @PatchMapping(path = "/{employeeID}")
    public ResponseEntity<EmployeeDTO> updatePartialEmployeeById(@RequestBody Map<String, Objects> updates,
                                          @PathVariable Long employeeID){
        EmployeeDTO employeeDTO= employeeService.updatePartialEmployeeById(updates, employeeID);
        if (employeeDTO==null) return ResponseEntity.notFound().build();
        else return ResponseEntity.ok(employeeDTO);
    }
}
