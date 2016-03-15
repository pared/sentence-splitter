import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

public class SentenceSplitterImpl implements SentenceSplitter {
    public static final String SENTENCE_MARK = "<sentence>";
    public static final List<String> ABBREVATIONS = Arrays.asList(
            " prof.",
            " m.in.",
            " np.",
            " inż.",
            " godz.",
            " płn.",
            " ppoż.",
            " gosp.",
            " ul.",
            " m.st.",
            " sz.p.",
            " ang.",
            " fr.",
            " łac.",
            " niem.");

    @Override
    public List<Sentence> splitToSentences(String text) {
        return splitText(text);
    }

    private List<Sentence> splitText(String text) {
        final List<String> step1 = Arrays.asList(toSentences(text));
        final List<String> step2 = resolveNames(step1);
        final List<String> step3 = handleAbbrevations(step2);
        final List<String> step4 = resolveSmallLetters(step3);




        return step4.stream()
                .map(this::removeInitialSpace)
                .map(Sentence::new)
                .collect(toList());
    }

    private List<String> resolveNames(List<String> sentences) {
        final List<String> joined = new ArrayList<>();
        final Iterator<String> iterator = sentences.iterator();

        while (iterator.hasNext()) {
            String temp = iterator.next();
            while (iterator.hasNext() && hasNameIndicator(temp)) {
                temp = temp + iterator.next();
            }
            joined.add(temp);
        }
        return joined;
    }

    private boolean hasNameIndicator(final String sentence) {
        return IntStream.range(0, sentence.length())
                .map(value ->(int) (sentence.length() - value - 1))
                .filter(value1 -> sentence.charAt(value1)=='.')
                .mapToObj(value2 -> Character.isUpperCase(sentence.charAt(value2-1)) && sentence.charAt(value2-2)==' ')
                .findFirst().orElse(false);


    }

    private List<String> resolveSmallLetters(final List<String> sentences) {
        final List<String> joined = new ArrayList<>();
        final List<String> reversed = Lists.reverse(sentences);
        final Iterator<String> iterator = reversed.iterator();

        while (iterator.hasNext()) {
            String temp = iterator.next();
            while (iterator.hasNext() && startsWithLowerCase(temp)) {
                temp = iterator.next() + temp;
            }
            joined.add(temp);
        }

        return Lists.reverse(joined);
    }


    private boolean startsWithLowerCase(final String string) {
        return IntStream.range(0, string.length())
                .mapToObj(string::charAt)
                .filter(character -> Character.isLowerCase(character) || Character.isUpperCase(character))
                .findFirst()
                .map(Character::isLowerCase)
                .orElse(false);
    }

    private List<String> handleAbbrevations(List<String> probableSentences) {
        final List<String> joinedOnAbbrevations = new ArrayList<>();
        final Iterator<String> iterator = probableSentences.iterator();

        while (iterator.hasNext()) {
            String temp = iterator.next();

            while (endsWithAbbrevation(temp) && iterator.hasNext()) {
                temp = temp + iterator.next();
            }

            joinedOnAbbrevations.add(temp);
        }
        return joinedOnAbbrevations;
    }

    private boolean endsWithAbbrevation(String s) {
        return ABBREVATIONS.stream().anyMatch(str -> s.toLowerCase().endsWith(str));
    }

    private String removeInitialSpace(String s) {
        if (s.startsWith(" ")) {
            return s.substring(1);
        }
        return s;
    }

    private String[] toSentences(String text) {

        text = text.replaceAll("(\\.|\\?|!)" +
                "(?=\\s)", "$1" + SENTENCE_MARK);
        return text.split(SENTENCE_MARK);
    }
}
