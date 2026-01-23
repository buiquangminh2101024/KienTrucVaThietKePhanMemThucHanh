package iuh.fit.se.enrollment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class EnrollmentController {

    @GetMapping("/enroll")
    public String enrollPage(Model model) {
        model.addAttribute("message", "Vui lòng chọn khóa học để đăng ký!");
        return "enroll-page";
    }

    @PostMapping("/enroll/submit")
    public String handleEnroll(@RequestParam String studentName,
                               @RequestParam String courseId,
                               Model model) {
        // Giả lập logic đăng ký thành công
        String successMsg = "Chúc mừng " + studentName + " đã đăng ký thành công môn " + courseId;
        model.addAttribute("message", successMsg);
        return "enroll-page";
    }
}