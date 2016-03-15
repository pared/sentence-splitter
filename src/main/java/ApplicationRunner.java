import java.io.IOException;

public class ApplicationRunner {
    public static void main(String[] args) throws IOException {
        if(args.length < 2) {
            System.out.println("Not enough arguments, please put input file name as first argument and output file name as second argument");
            System.exit(1);
        }

        Reader reader = new Reader(args[0]);
        SentenceWriter writer = new SentenceWriterImpl(args[1]);
        SentenceSplitter splitter = new SentenceSplitterImpl();
        writer.writeExecutionResult(splitter.splitToSentences(reader.getTextAsSingleString()));
    }
}
