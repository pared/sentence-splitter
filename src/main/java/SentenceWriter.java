import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public interface SentenceWriter {
    void writeExecutionResult(List<Sentence> sentences) throws IOException;
}
