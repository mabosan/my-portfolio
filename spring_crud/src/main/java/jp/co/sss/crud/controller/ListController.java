package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jp.co.sss.crud.form.EmployeeForm;
import jp.co.sss.crud.repository.DepartmentRepository;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class ListController {

    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("/list")
    public String list(Model model) {
//        Employee loggedInUser = (Employee) session.getAttribute("user");
        model.addAttribute("emps", employeeRepository.findAll());
        return "list/list";
    }
    
    @GetMapping("/list/empName")
    public String empName(EmployeeForm employeeForm ,String empName, Model model) {
        if (empName == null || empName.isEmpty()) {
            model.addAttribute("emps", employeeRepository.findAll()); // 全社員表示
        } else {
            model.addAttribute("emps", employeeRepository.findByEmpNameContaining(empName));
        }
        return "list/list";
    }
    
    @GetMapping("/list/deptId")
    public String searchByDept(@RequestParam(defaultValue = "1") Integer deptId, Model model) {
        // 部署情報のリストを取得し、モデルに追加
        model.addAttribute("departments", departmentRepository.findAll());
        
        // 選択された部署に所属する社員情報を取得
        model.addAttribute("emps", employeeRepository.findByDepartmentDeptId(deptId));
        
        // 検索に使用した部署IDを再表示するために保存
        model.addAttribute("selectedDeptId", deptId);
        
        return "list/list";
    }


}
