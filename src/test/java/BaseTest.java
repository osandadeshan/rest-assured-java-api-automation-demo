import com.maxsoft.testngtestresultsanalyzer.TestAnalyzeReportListener;
import net.andreinc.mockneat.MockNeat;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;

import static constant.CommonConstants.EXECUTION_ENV_NAME;

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
    private final Logger logger = LogManager.getLogger();

    @BeforeSuite
    public void oneTimeSetup() {
        logger.debug("Test execution environment: {}", EXECUTION_ENV_NAME);
    }

    public MockNeat getMockService() {
        return MockNeat.threadLocal();
    }
}
