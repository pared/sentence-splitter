import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class Reader {
    private final File inputFile;

    public Reader(final String inputFileName) throws FileNotFoundException {
        inputFile = new File(inputFileName);
    }

   public String getTextAsSingleString() throws IOException {
       return Files.lines(inputFile.toPath())
               .reduce(this::reduceLines)
               .orElseThrow(() -> new RuntimeException("Could not reduce text to single string"));
   }

    private String reduceLines(String line1, String line2) {
        if(line1.endsWith("-") && !line1.endsWith(" -") && line1.length()>3) {
            line1 = line1.substring(0, line1.length()-2);
        }else {
            if(!line2.startsWith(" ") && !line1.endsWith(" ")) {
                line2 = " "+line2;
            }
        }
        return (line1+line2).replaceAll("\\s\\s", "\\s");
    }
}
