package jp.co.sss.crud.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jp.co.sss.crud.entity.Employee;
import jp.co.sss.crud.form.LoginForm;
import jp.co.sss.crud.repository.EmployeeRepository;

@Controller
public class IndexController {
	
	@Autowired
	EmployeeRepository employeeRepository;

	@Autowired
	HttpSession session;

	@RequestMapping(path = "/", method = RequestMethod.GET)
	public String index(@ModelAttribute LoginForm loginForm) {
		session.invalidate();
		return "index";
	}
	
	// ログイン処理
    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm loginForm , BindingResult result,Model model) {
        Employee employee = employeeRepository.findByEmpIdAndEmpPass(loginForm.getEmpId(), loginForm.getEmpPass());

        if (result.hasErrors()) {
        	return "index";
        }
        if(employee != null) {
            session.setAttribute("user", employee); // ログインユーザーをセッションに保存
            return "redirect:/list";
        } else {
        	String errmessages ="社員ID、またはパスワードが間違っています。";
        	model.addAttribute("errMessage", errmessages);
            return "index";
        }
    }

    // ログアウト処理
    @GetMapping("/logout")
    public String logout() {
        session.invalidate();
        return "redirect:/";
    }

}
