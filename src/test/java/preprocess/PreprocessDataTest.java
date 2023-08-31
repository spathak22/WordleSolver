package preprocess;

import org.junit.Assert;
import org.junit.Test;
import preprocess.PreprocessData;

import java.util.Map;

public class PreprocessDataTest {
    PreprocessData preprocess = new PreprocessData();

    @Test
    public void getFiveLetterWordsShouldWork(){
        Map map = preprocess.getFiveLetterWords();
        Assert.assertNotNull(map);
    }

}
