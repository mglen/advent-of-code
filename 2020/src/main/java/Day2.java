import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day2 {

    public static void main(String... args) {
        Pattern linePattern = Pattern.compile("(\\d+)-(\\d+) (\\w): (\\w+)");

        List<PasswordAndPolicy> passwordAndPolicyList = Utils.readLines("day2.txt")
                .map(line -> {
                    Matcher matcher = linePattern.matcher(line);
                    if (!matcher.matches()) {
                        throw Utils.fail("Got bad line: " + line);
                    }
                    return new PasswordAndPolicy(
                            Integer.parseInt(matcher.group(1)),
                            Integer.parseInt(matcher.group(2)),
                            matcher.group(3).charAt(0),
                            matcher.group(4));
                })
                .collect(Collectors.toList());

        part1(passwordAndPolicyList);
        part2(passwordAndPolicyList);
    }

    private static void part1(List<PasswordAndPolicy> passwordAndPolicyList) {
        long goodPasswords = passwordAndPolicyList.stream()
                .filter(pp -> {
                    long letterOccurrences = pp.password.chars().filter(i -> i == pp.letter).count();
                    return letterOccurrences <= pp.int_1 && letterOccurrences >= pp.int_2;
                })
                .count();

        System.out.println("Day2 part1: " + goodPasswords);
    }

    private static void part2(List<PasswordAndPolicy> passwordAndPolicyList) {
        long goodPasswords = passwordAndPolicyList.stream()
                .filter(pp -> pp.password.charAt(pp.int_2 -1) == pp.letter
                        ^ pp.password.charAt(pp.int_1 -1) == pp.letter)
                .count();

        System.out.println("Day2 part2: " + goodPasswords);
    }

    static class PasswordAndPolicy {
        int int_2;
        int int_1;
        char letter;
        String password;

        public PasswordAndPolicy(int int_2, int int_1, char letter, String password) {
            this.int_2 = int_2;
            this.int_1 = int_1;
            this.letter = letter;
            this.password = password;
        }
    }
}
