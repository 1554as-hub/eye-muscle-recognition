import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eyescloud.entity.FileModle;
import com.eyescloud.service.FileHandlerService;
import eyescloud.FileConsumerMain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest(classes = FileConsumerMain.class)
public class FileModelTest {

    @Autowired
    private FileHandlerService fileHandlerService;



    @Test
    public void test(){
        List<FileModle> userFileHistory = fileHandlerService.getUserFileHistory("2e041e67-e264-4d28-a1ab-c3e187ac140c");
        userFileHistory.forEach(System.out::println);
    }

    @Test
    public void testPage() {

        Page<FileModle> page = new Page<>(1 , 10);
        IPage<FileModle> userFileHistory = fileHandlerService.getUserFileHistory(page, "2e041e67-e264-4d28-a1ab-c3e187ac140c");
        System.out.println(userFileHistory.getTotal());
    }

}
