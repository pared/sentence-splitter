import org.junit.Test;

public class SentenceWriterImplTest {

    @Test
    public void testWriting() throws Exception {
        SentenceWriter writer = new SentenceWriterImpl("output.txt");
        Reader reader = new Reader("input.txt");
        SentenceSplitter splitter = new SentenceSplitterImpl();
        writer.writeExecutionResult(splitter.splitToSentences(reader.getTextAsSingleString()));
    }

}