package jp.co.sss.crud.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
public class UpdateController {

	@Autowired
	private EmployeeRepository repository;

	@Autowired
	private DepartmentRepository Drepository;

	// Update Input Screen Display
	@GetMapping("/update/input")
	public String show(EmployeeForm employeeForm, Model model, HttpSession session) {
		// セッションから取得したEmployeeの情報をEmployeeFormにコピー
		Employee employee = (Employee) session.getAttribute("user"); // セッションから取得
		if (employee != null) {
			BeanUtils.copyProperties(employee, employeeForm);
		}

		// EmployeeFormをモデルに追加
		model.addAttribute("emp", employeeForm);

		return "update/update_input";
	}

	// Editing form submission with a valid employee ID
	@PostMapping("/employee/edit/{empId}")
	public String userUpdate(@ModelAttribute EmployeeForm employeeForm, @PathVariable Integer empId, Model model) {
		Employee employee = repository.getReferenceById(empId);

		if (employee != null) {
			BeanUtils.copyProperties(employee, employeeForm); // Employee のプロパティを EmployeeForm にコピー
			model.addAttribute("emp", employeeForm); // フォームデータをモデルに追加
		}
		return "update/update_input";
	}

	// Check screen after input
	@PostMapping("/update/check")
	public String check(@Valid @ModelAttribute EmployeeForm employeeForm, BindingResult result, Model model) {
		if (result.hasErrors()) {
			return "update/update_input";
		} else {
			Department dept = Drepository.getReferenceById(employeeForm.getDeptId());
			model.addAttribute("deptName", dept.getDeptName());
			model.addAttribute("emp", employeeForm);
			return "update/update_check";
		}
	}

	// Final update process
	@PostMapping("/update/complete")
	public String update(@Valid @ModelAttribute EmployeeForm employeeForm, Model model, HttpSession session) {
		//更新処理
		Employee employee = BeanCopy.copyFormToEmployee(employeeForm);
		employee = repository.save(employee);
		//表示用の処理
		EmployeeBean bean = new EmployeeBean();
		BeanUtils.copyProperties(employee, bean);
		model.addAttribute("employee", bean);
		//sessionの上書き
		Employee employeeUser = (Employee) session.getAttribute("user");
		if (employeeUser.getEmpId() == employee.getEmpId()) {
			employeeUser.setEmpName(employee.getEmpName());
		}
		return "update/update_complete";
	}

	// Back button action
	@PostMapping("/update/back")
	public String back(@ModelAttribute EmployeeForm employeeForm, Model model) {
		model.addAttribute("emps", employeeForm);
		return "update/update_input";
	}
}
