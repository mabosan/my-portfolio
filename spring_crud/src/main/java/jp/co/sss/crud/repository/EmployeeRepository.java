package jp.co.sss.crud.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import jp.co.sss.crud.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
	// 社員IDとパスワードで社員を検索するメソッド
	Employee findByEmpIdAndEmpPass(Integer empId, String empPass);
	
	List<Employee>findAll();
	
	List<Employee>findByEmpNameContaining(String empName);
	 // 部署IDに基づいて社員情報を取得する
    List<Employee> findByDepartmentDeptId(Integer deptId);
    
//    @Query("SELECT e FROM Employee e WHERE e.empId = :empId")
//    List<Employee>findByEmpIdQuery(@Param("empId") Integer empId);
}
