package org.example;

import com.github.wslf.levenshteindistance.LevenshteinCalculator;

import java.util.List;
import java.util.stream.Collectors;

public class StringSorter {

    public static List<String> sortBySimilarity(List<String> words, String s) {
        return words.stream()
                .sorted((word1, word2) -> {
                    double score1 = similarity(word1, s);
                    double score2 = similarity(word2, s);
                    return Double.compare(score2, score1); // sort in descending order of score
                })
                .collect(Collectors.toList());
    }

    public static double similarity(String s1, String s2) {
        String longer = s1, shorter = s2;
        if (s1.length() < s2.length()) { // longer should always have greater length
            longer = s2; shorter = s1;
        }
        int longerLength = longer.length();
        if (longerLength == 0) { return 1.0; /* both strings are zero length */ }

        LevenshteinCalculator levenshteinDistance = new LevenshteinCalculator();
        return (longerLength - levenshteinDistance.getLevenshteinDistanceIgnoreCase(longer, shorter)) / (double) longerLength;
    }
}

