import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/")
public class ApiController {

    @GetMapping("/user")
    public String getDetails() {

        String details = """
                naam : himanshu panchal\s
                kaam : berozgaar \s
                from : panipat \s
                currentCity : delhi \s
                LivingOfstandard : Nawab-style \s
                perception : chutiya \s
                founded : intelligent\s
                signatureWord : Veera""";

        // Replace newline characters with HTML line break tags
        details = details.replace("\n", "<br>");
        return "<html><body>" + details + "</body></html>";
    }
}
