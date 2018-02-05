package ideas.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ideas.server.Server;
import ideas.version.SoftwareVersionFinder;

public class SoftwareVersionFinderTest {
	private SoftwareVersionFinder versionFinder;

	@Before
	public void setUp() throws Exception {
		versionFinder = new SoftwareVersionFinder();
	}

	@Test
	public void whenInputFileIsInvalidThenReturnNull() {
		List<Server> serverDetails = versionFinder.readInputFile(null);
		assertNull("Server list should be null", serverDetails);

		serverDetails = versionFinder.readInputFile("");
		assertNull("Server list should be null", serverDetails);

		serverDetails = versionFinder.readInputFile("invalid_file");
		assertNull("Server list should be null", serverDetails);
	}

	@Test
	public void whenInputFileContainsInvalidFormatThenReturnNull() {
		List<Server> serverDetails = versionFinder.readInputFile("src/test/resources/invalid_input.txt");
		assertNull("Server list should be null", serverDetails);
	}

	@Test
	public void whenInputFileIsVaidThenReturnParsedServerDetails() {
		List<Server> serverDetails = versionFinder.readInputFile("src/test/resources/input.txt");
		assertNotNull("Server list should not be null", serverDetails);
		assertEquals("Server list should contain 6 elemets", 6, serverDetails.size());
	}

	@Test
	public void whenServerDetailsArePassedThenShowSoftwareWithOutdatedVersion() {
		List<Server> serverDetails = versionFinder.readInputFile("src/test/resources/input.txt");
		List<String> result = versionFinder.processData(serverDetails);
		assertEquals("Incorrect output", "[Python]", result.toString());
	}

	@Test
	public void whenInvalidProcessedDataIsPassedThenNoFileShouldBeCreated() {
		versionFinder.writeOutputFile("src/test/resources/output.txt", null);
		boolean exists = Files.exists(Paths.get("src/test/resources/output.txt"));
		assertFalse("File should not be created", exists);

		try {
			Files.deleteIfExists(Paths.get("src/test/resources/output.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		versionFinder.writeOutputFile("src/test/resources/output.txt", new ArrayList<>());
		exists = Files.exists(Paths.get("src/test/resources/output.txt"));
		assertFalse("File should not be created", exists);

		try {
			Files.deleteIfExists(Paths.get("src/test/resources/output.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void whenProcessedDataIsPassedThenFileShouldBeCreatedAndWrittenCorrectly() {
		List<Server> serverDetails = versionFinder.readInputFile("src/test/resources/input.txt");
		List<String> result = versionFinder.processData(serverDetails);

		String ofile = "src/test/resources/output.txt";
		versionFinder.writeOutputFile(ofile, result);

		boolean exists = Files.exists(Paths.get(ofile));
		assertTrue("File should exist", exists);

		try {
			List<String> lines = Files.readAllLines(Paths.get(ofile));
			assertTrue("Output file invalid", lines.contains("Python"));
			Files.deleteIfExists(Paths.get(ofile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
