package jp.co.sss.crud.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.crud.bean.EmployeeBean;
import jp.co.sss.crud.entity.Department;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;
import jp.co.sss.crud.util.BeanCopy;

@Controller
public class RegistrationController {
	@Autowired
	private EmployeeRepository repository;
	
	@Autowired
	private DepartmentRepository Drepository;
	
	@GetMapping("/regist/input")
	public String show(@ModelAttribute EmployeeForm employeeForm,Model model) {
		model.addAttribute("emp", employeeForm);
		return "regist/regist_input";
        
	}
	
	@PostMapping("/regist/check")
	public String check(@Valid EmployeeForm employeeForm,BindingResult result,Model model , HttpSession session) {
		if (result.hasErrors()) {
            return "regist/regist_input";
        }else {
		Department dept = Drepository.getReferenceById(employeeForm.getDeptId());
		model.addAttribute("deptName", dept.getDeptName());
		model.addAttribute("emp", employeeForm);
		return "regist/regist_check";
        }
	}
	
	@PostMapping("/regist/complete")
	public String regist(@Valid EmployeeForm employeeForm ,Model model) {
		Employee employee = BeanCopy.copyFormToEmployee(employeeForm);
		employee = repository.save(employee);
		EmployeeBean bean = new EmployeeBean();
		BeanUtils.copyProperties(employee, bean);
		model.addAttribute("employee", bean);
		return "regist/regist_complete";
	}
	
}
