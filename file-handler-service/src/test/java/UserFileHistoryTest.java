import com.eyescloud.FileService;
import com.eyescloud.entity.FileModle;
import com.eyescloud.service.FileHandlerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = FileService.class)

public class UserFileHistoryTest {

    @Autowired
    private FileHandlerService handlerService;

    @Test
    public void test(){
        List<FileModle> userFileHistory = handlerService.getUserFileHistory("2e041e67-e264-4d28-a1ab-c3e187ac140c");
        userFileHistory.forEach(System.out::println);
    }

}
