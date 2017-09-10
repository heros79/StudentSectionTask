package am.david.jcl.controller;

import am.david.jcl.model.Section;
import am.david.jcl.model.Student;
import am.david.jcl.service.SectionService;
import am.david.jcl.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by David on 9/9/2017.
 */
@Controller
public class PageController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private SectionService sectionService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String selectStudent (Model model) {

        Student student = new Student();
        model.addAttribute("selectStudent", student);
        model.addAttribute("studentsList", studentService.findAll());

        Section section = new Section(studentService.findAll());

        model.addAttribute("selectSection", section);
        model.addAttribute("sectionsList", student.getSections());

        return "main";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String selectStudent(@ModelAttribute("selectStudent") Student selectStudent, Model model){

        model.addAttribute("studentsList", studentService.findAll());

        Section section = new Section(studentService.findAll());

        model.addAttribute("selectSection", section);

        for (Student students: section.getStudents()
             ) {

            if (students.getStudentName().equals(selectStudent.getStudentName())) {
                model.addAttribute("sectionsList", students.getSections());
            }

        }
        return "main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String registration (Model model) {

        model.addAttribute("studentForm", new Student());

        model.addAttribute("sectionForm", new  Section());

        return "main";
    }

    @RequestMapping(value = "/main", method = RequestMethod.POST)
    public String registration (@ModelAttribute("userForm") Student studentForm, @ModelAttribute ("sectionForm") Section sectionForm) {

        if (studentForm != null)
        studentService.saveStudent(studentForm);
        if (sectionForm != null)
        sectionService.saveSection(sectionForm);

        return "main";
    }
}
