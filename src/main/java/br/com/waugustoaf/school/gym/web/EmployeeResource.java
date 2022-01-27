package br.com.waugustoaf.school.gym.web;

import br.com.waugustoaf.school.gym.domain.Employee;
import br.com.waugustoaf.school.gym.domain.User;
import br.com.waugustoaf.school.gym.exception.AppException;
import br.com.waugustoaf.school.gym.service.EmployeeService;
import br.com.waugustoaf.school.gym.service.UserService;
import br.com.waugustoaf.school.gym.util.JwtUtil;
import io.jsonwebtoken.MalformedJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/employees")
public class EmployeeResource {
    private Logger log = LoggerFactory.getLogger(EmployeeResource.class);
    private final EmployeeService employeeService;
    private final UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    public EmployeeResource(EmployeeService employeeService, UserService userService) {
        this.employeeService = employeeService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<Employee>> index() {
        List<Employee> employees = this.employeeService.findAll();
        return ResponseEntity.ok().body(employees);
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> show(@PathVariable("id") UUID id) {
        Optional<Employee> employee = this.employeeService.findOne(id);

        if(employee.isPresent()) {
            return ResponseEntity.ok().body(employee.get());
        } else {
            throw new AppException("Nenhum funcionário com esse ID encontrado.", "employees.notFound");
        }
    }

    @PostMapping
    public ResponseEntity<Employee> store(@RequestBody Map<String, String> json) throws URISyntaxException, ParseException {
        if(json.get("id") != null) {
            throw new AppException("Um novo funcionário não espera um ID.");
        }

        Optional<User> userAlreadyExists = this.userService.findByCPF(json.get("cpf"));

        if(userAlreadyExists.isPresent()) {
            throw new AppException("Já existe uma conta cadastrada com esse CPF.", "users.alreadyExists");
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        User user = new User();
        user.setName(json.get("name"));
        user.setEmail(json.get("email"));
        user.setPassword(json.get("password"));
        user.setCpf(json.get("cpf"));
        user.setPhone(json.get("phone"));
        user.setBirth_date(formatter.parse(json.get("birth_date")));
        user.setRole(User.Role.employee);
        user.setCreated_at(new Date());
        user.setUpdated_at(new Date());

        User userResponse = this.userService.save(user);

        Employee employee = new Employee();
        employee.setHoursPerDay(Integer.valueOf(json.get("hoursPerDay")));
        employee.setWage(Integer.valueOf(json.get("wage")));
        employee.setEducation(json.get("education"));
        employee.setPayment_day(Integer.valueOf(json.get("payment_day")));
        employee.setUser(userResponse);
        employee.setCreated_at(new Date());
        employee.setUpdated_at(new Date());

        Employee employeeResponse = this.employeeService.save(employee);

        return ResponseEntity
                .created(new URI("/employees/" + employeeResponse.getId()))
                .body(employeeResponse);
    }

    @PutMapping("{id}")
    public ResponseEntity<Employee> update(@RequestBody Map<String, String> json, @PathVariable("id") UUID id) throws URISyntaxException, ParseException {
        Optional<Employee> employeeExists = this.employeeService.findOne(id);

        if(!employeeExists.isPresent()) {
            throw new AppException("Funcionário não encontrado", "employees.notFound");
        }

        employeeExists.get().setHoursPerDay(Integer.valueOf(json.get("hoursPerDay")));
        employeeExists.get().setWage(Integer.valueOf(json.get("wage")));
        employeeExists.get().setEducation(json.get("education"));
        employeeExists.get().setPayment_day(Integer.valueOf(json.get("payment_day")));
        employeeExists.get().setUpdated_at(new Date());

        User user = employeeExists.get().getUser();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        user.setName(json.get("name"));
        user.setEmail(json.get("email"));
        user.setCpf(json.get("cpf"));
        user.setPhone(json.get("phone"));
        user.setBirth_date(formatter.parse(json.get("birth_date")));
        user.setRole(User.Role.employee);
        user.setUpdated_at(new Date());

        User userResponse = this.userService.save(user);

        employeeExists.get().setUser(userResponse);
        Employee employeeResponse = this.employeeService.save(employeeExists.get());

        return ResponseEntity
                .created(new URI("/employees/" + employeeResponse.getId()))
                .body(employeeResponse);
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable("id") UUID id) {
        Optional<Employee> employee = this.employeeService.findOne(id);

        if(employee.isEmpty()) {
            throw new AppException("Funcionário não encontrado.");
        }

        this.employeeService.delete(id);

        this.userService.delete(employee.get().getUser().getId());

        return ResponseEntity.status(204).build();
    }
}
