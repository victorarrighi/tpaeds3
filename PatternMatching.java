import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


class PatternMatching {
    // Implementação do algoritmo KMP (Knuth-Morris-Pratt)
    public static List<Integer> KMPSearch(String pattern, String text) {
        List<Integer> positions = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        int[] lps = computeLPSArray(pattern);
        int i = 0; // índice para text
        int j = 0; // índice para pattern

        while (i < text.length()) {
            if (pattern.charAt(j) == text.charAt(i)) {
                j++;
                i++;
            }
            if (j == pattern.length()) {
                positions.add(i - j);
                j = lps[j - 1];
            }
            else if (i < text.length() && pattern.charAt(j) != text.charAt(i)) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo de busca KMP: " + (endTime - startTime) + "ms");

        return positions;
    }

    private static int[] computeLPSArray(String pattern) {
        int[] lps = new int[pattern.length()];
        int len = 0;
        int i = 1;

        while (i < pattern.length()) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            }
            else {
                if (len != 0) {
                    len = lps[len - 1];
                }
                else {
                    lps[i] = len;
                    i++;
                }
            }
        }

        return lps;
    }

    // Implementação do algoritmo Boyer-Moore
    public static List<Integer> boyerMooreSearch(String pattern, String text) {
        List<Integer> positions = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        int[] badChar = new int[256];
        Arrays.fill(badChar, -1);

        for (int i = 0; i < pattern.length(); i++) {
            badChar[pattern.charAt(i)] = i;
        }

        int shift = 0;
        while (shift <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;

            while (j >= 0 && pattern.charAt(j) == text.charAt(shift + j)) {
                j--;
            }

            if (j < 0) {
                positions.add(shift);
                shift += (shift + pattern.length() < text.length()) ?
                        pattern.length() - badChar[text.charAt(shift + pattern.length())] : 1;
            }
            else {
                shift += Math.max(1, j - badChar[text.charAt(shift + j)]);
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Tempo de busca Boyer-Moore: " + (endTime - startTime) + "ms");

        return positions;
    }
}