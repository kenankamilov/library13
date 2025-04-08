package com.example.library.management.Controller;


import com.example.library.management.Model.Student;
import com.example.library.management.Service.StudentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String listStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "students/list";
    }

    @GetMapping("/create")
    public String createStudentForm(Model model) {
        model.addAttribute("student", new Student());
        return "students/create";
    }

    @PostMapping("/create")
    public String createStudent( @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "students/create";
        }
        studentService.createStudent(student);
        return "redirect:/students";
    }

    @GetMapping("/update/{id}")
    public String updateStudentForm(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        model.addAttribute("student", student);
        return "students/update";
    }

    @PostMapping("/update/{id}")
    public String updateStudent(@PathVariable Long id,  @ModelAttribute Student student, BindingResult result) {
        if (result.hasErrors()) {
            return "students/update";
        }
        studentService.updateStudent(id, student);
        return "redirect:/students";
    }

    @GetMapping("/view/{id}")
    public String viewStudent(@PathVariable Long id, Model model) {
        Student student = studentService.getStudentById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
        model.addAttribute("student", student);
        return "students/view";
    }

    @PostMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }
}