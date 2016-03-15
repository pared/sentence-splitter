import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Sentence {
    private String value;

    @Override
    public String toString() {
        return value;
    }
}
