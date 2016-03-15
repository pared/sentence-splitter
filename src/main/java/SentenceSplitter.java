import java.util.List;

public interface SentenceSplitter {
    List<Sentence> splitToSentences(String text);
}
