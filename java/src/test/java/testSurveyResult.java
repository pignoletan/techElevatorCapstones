
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import com.techelevator.DAOIntegrationTest;
import com.techelevator.models.JdbcSurveyResultDAO;

public class testSurveyResult extends DAOIntegrationTest {

	@Test
	public void testGetVotesByParkCode() {
		JdbcSurveyResultDAO thisResult = new JdbcSurveyResultDAO(getDataSource());
		boolean isEqual = thisResult.getVotesByParkCode("MRNP") == 2;
		assertTrue("Incorrect number of votes",isEqual);
	}
	
}
