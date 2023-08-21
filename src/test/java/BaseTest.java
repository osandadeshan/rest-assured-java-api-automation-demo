import com.maxsoft.testngtestresultsanalyzer.TestAnalyzeReportListener;
import net.andreinc.mockneat.MockNeat;
import org.testng.annotations.Listeners;

/**
 * Project Name    : rest-assured-java-api-automation-demo
 * Developer       : Osanda Deshan
 * Version         : 1.0.0
 * Date            : 21/8/23
 * Time            : 5:35 pm
 * Description     :
 **/

@Listeners(TestAnalyzeReportListener.class)
public class BaseTest {
    public MockNeat getMockService() {
        return MockNeat.threadLocal();
    }
}
