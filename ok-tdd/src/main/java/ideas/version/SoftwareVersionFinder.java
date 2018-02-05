package ideas.version;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ideas.server.Server;

public class SoftwareVersionFinder {
	String ifileName = "src/main/resources/input.txt", ofileName = "src/main/resources/output.txt";

	public static void main(String[] args) {
		SoftwareVersionFinder versionFinder = new SoftwareVersionFinder();
		List<Server> serverDetails = versionFinder.readInputFile(versionFinder.ifileName);

		if (serverDetails != null) {
			List<String> processedData = versionFinder.processData(serverDetails);
			versionFinder.writeOutputFile(versionFinder.ofileName, processedData);
		}
	}

	public void writeOutputFile(String ofileName, List<String> processedData) {
		if (processedData != null && !processedData.isEmpty()) {
			try {
				Files.write(Paths.get(ofileName), processedData);
			} catch (IOException e) {
				System.err.println("Error in writing output file.");
			}
		} else {
			System.out.println("No data to write");
		}
	}

	public List<String> processData(List<Server> serverDetails) {
		Map<String, List<String>> softwareVersionMap = new HashMap<>();
		serverDetails.forEach(sd -> {
			if (softwareVersionMap.get(sd.getSoftwareName()) == null) {
				softwareVersionMap.put(sd.getSoftwareName(),
						Stream.of(sd.getSoftwareVersion()).collect(Collectors.toList()));
			} else {
				List<String> versions = softwareVersionMap.get(sd.getSoftwareName());
				if (!versions.contains(sd.getSoftwareVersion())) {
					versions.add(sd.getSoftwareVersion());
					softwareVersionMap.put(sd.getSoftwareName(), versions);
				}
			}
		});

		List<String> result = softwareVersionMap.entrySet().stream().filter(sd -> sd.getValue().size() >= 3)
				.map(sd -> sd.getKey()).collect(Collectors.toList());
		return result;
	}

	public List<Server> readInputFile(String ifileName) {
		if (ifileName != null && !ifileName.isEmpty()) {
			List<Server> serverDetails = new ArrayList<>();
			try (Stream<String> lines = Files.lines(Paths.get(ifileName))) {
				lines.forEach(line -> {
					String[] parsedLine = line.split(",\\s*");
					if (parsedLine.length == 4) {
						serverDetails.add(new Server(parsedLine[0], parsedLine[1], parsedLine[2], parsedLine[3]));
					} else {
						throw new RuntimeException("Invalid input encountered: " + line);
					}
				});
				return serverDetails;
			} catch (IOException e) {
				System.err.println("Error in reading input file.");
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
		return null;
	}
}
