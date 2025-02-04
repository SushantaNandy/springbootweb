package com.example.springbootweb.springbootweb.services;

import com.example.springbootweb.springbootweb.dto.EmployeeDTO;
import com.example.springbootweb.springbootweb.entities.EmployeeEntity;
import com.example.springbootweb.springbootweb.exceptions.ResourceNotFoundException;
import com.example.springbootweb.springbootweb.repositories.EmployeeRepository;
import org.apache.el.util.ReflectionUtil;
import org.modelmapper.ModelMapper;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeService {


    private final EmployeeRepository employeeRepository;

    private final ModelMapper modelMapper;

    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

       // Approach-1
//    public EmployeeDTO getEmployeeById(Long employeeID) {
//        EmployeeEntity employeeEntity= employeeRepository.findById(employeeID).orElse(null);
//        return modelMapper.map(employeeEntity,EmployeeDTO.class);
//    }

    //Approach-2
    public Optional<EmployeeDTO> getEmployeeById(Long employeeID) {
        return employeeRepository.findById(employeeID).map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class));
    }

    public List<EmployeeDTO> getAllEmployees() {
        List<EmployeeEntity> employeeEntities=  employeeRepository.findAll();
        return  employeeEntities.
                stream().
                map(employeeEntity -> modelMapper.map(employeeEntity,EmployeeDTO.class)).
                collect(Collectors.toList());
    }

    public EmployeeDTO createNewEmployee(EmployeeDTO inputEmployee) {
        EmployeeEntity toSaveEntity= modelMapper.map(inputEmployee,EmployeeEntity.class);
        EmployeeEntity savedEmployeeEntity= employeeRepository.save(toSaveEntity);
        return modelMapper.map(savedEmployeeEntity,EmployeeDTO.class);
    }

    public EmployeeDTO updateEmployeeById(EmployeeDTO employeeDTO, Long employeeID) {
        EmployeeEntity employeeEntity = modelMapper.map(employeeDTO, EmployeeEntity.class);
        employeeEntity.setId(employeeID);
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(employeeEntity);
        return  modelMapper.map(savedEmployeeEntity, EmployeeDTO.class);
    }

    public boolean isExistByEmployeeId(Long employeeId){ return employeeRepository.existsById(employeeId);}

    public String deleteEmployeeById(Long employeeID) {
//        if (employeeRepository.existsById(employeeID)){
//            employeeRepository.deleteById(employeeID);
//            return "Deleted Sucessfully user by id "+employeeID;
//        }
//            return "Employee id doesn't exist";
        boolean exist = isExistByEmployeeId(employeeID);
        if (!exist) throw new ResourceNotFoundException("Employee Not found with id "+employeeID);
        employeeRepository.deleteById(employeeID);
        return "Deleted Sucessfully user by id "+employeeID;
    }

    public EmployeeDTO updatePartialEmployeeById(Map<String, Objects> updates, Long employeeID) {
        boolean exist = employeeRepository.existsById(employeeID);
        if (!exist) return null;
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeID).get();
        updates.forEach((field, value)-> {
          Field fieldToBeUpdated = ReflectionUtils.findRequiredField(EmployeeEntity.class,field);
            fieldToBeUpdated.setAccessible(true);
            ReflectionUtils.setField(fieldToBeUpdated,employeeEntity,value);
        });

        return modelMapper.map(employeeRepository.save(employeeEntity),EmployeeDTO.class);
    }
}
