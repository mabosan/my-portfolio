package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class DeleteController {

    @Autowired
    private EmployeeRepository repository;

    // Check screen after input
    @PostMapping("/employee/delete/{empId}")
    public String check(@PathVariable Integer empId,Model model) {
    	System.out.println(empId);
    	model.addAttribute("emp", repository.getReferenceById(empId)); // フォームデータをモデルに追加
        return "delete/delete_check";
    }

    // Final update process
    @PostMapping("/delete/check")
    public String delete(@ModelAttribute EmployeeForm employeeForm, Model model) {
        repository.deleteById(employeeForm.getEmpId());
        return "delete/delete_complete";
    }

    // Back button action
    @GetMapping("/delete/back")
    public String back(@ModelAttribute EmployeeForm employeeForm ,Model model) {
        model.addAttribute("emps", repository.findAll());
        return "list/list";
    }
}
