package iuh.fit.se.course;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;

@Controller
public class CourseController {
    @GetMapping("/courses")
    public String listCourses(Model model) {
        model.addAttribute("courses", Arrays.asList(
                new Course("CS101", "Cấu trúc dữ liệu", "Thầy An"),
                new Course("CS102", "Kiến trúc phần mềm", "Cô Bình")
        ));
        return "course-list"; // Trả về template trong domain course
    }
}