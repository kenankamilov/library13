package com.example.library.management.Controller;

import com.example.library.management.Model.Student;
import com.example.library.management.Service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/students")
public class StudentController {

    private static final Logger logger = LoggerFactory.getLogger(StudentController.class);

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(Model model) {
        logger.info("Bütün tələbələr gətirilir");
        model.addAttribute("students", studentService.getAllStudents());
        return "students/list";
    }

    @GetMapping("/create")
    public String createStudentForm(Model model) {
        logger.info("Tələbə əlavəetmə forması göstərilir");
        model.addAttribute("student", new Student());
        return "students/create";
    }

    @PostMapping("/create")
    public String createStudent(@ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Tələbə əlavə edərkən səhv baş verdi: {}", result.getAllErrors());
            return "students/create";
        }
        logger.info("Yeni tələbə yaradılır: {}", student.getName());
        studentService.createStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/update/{id}")
    public String updateStudentForm(@PathVariable Long id, Model model) {
        logger.info("ID-si {} olan tələbə üçün yeniləmə forması göstərilir", id);
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Tələbə tapılmadı, ID: " + id));
        model.addAttribute("student", student);
        return "students/update";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            logger.error("Tələbə yeniləyərkən səhv baş verdi: {}", result.getAllErrors());
            return "students/update";
        }
        logger.info("ID-si {} olan tələbə yenilənir", id);
        studentService.updateStudent(id, student);
        return "redirect:/students";
    }

    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        logger.info("ID-si {} olan tələbənin detalları göstərilir", id);
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Tələbə tapılmadı, ID: " + id));
        model.addAttribute("student", student);
        return "students/view";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        logger.info("ID-si {} olan tələbə silinir", id);
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}
