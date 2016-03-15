import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.IntStream;
@Setter
public class SentenceWriterImpl implements SentenceWriter {
    private final static String HEADER =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<text>\n<p s xml:id=\"1-p\">";

    private final static String FOOTER ="</p>\n</text>";


    private final PrintWriter printWriter;

    public SentenceWriterImpl(final String outputFileName) throws FileNotFoundException {
        System.out.println("Purging old " + outputFileName);
        new File(outputFileName).delete();
        printWriter = new PrintWriter(outputFileName);

    }

    public void writeExecutionResult(List<Sentence> sentences) throws IOException {
        System.out.println("Writing splitting result");
        printWriter.println(HEADER);


        IntStream.range(0, sentences.size())
                .mapToObj(index -> toXmlString(sentences.get(index), index))
                .forEach(this::writeRow);

        printWriter.println(FOOTER);

        printWriter.close();


    }

    private void writeRow(final String row) {
        printWriter.println(row);

    }

    private String toXmlString(final Sentence sentence, final int index) {
        return String.format("<s xml:id=\"1-%d-s\">%s</s>", index+1, sentence);
    }


}
