import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Day4 {

    private static final Set<String> EYE_COLORS = Set.of("amb", "blu", "brn", "gry", "grn", "hzl", "oth");

    private static final Map<String, Predicate<String>> REQUIRED_FIELDS = Map.of(
            "byr", byr -> isNumberBetween(byr, 1920, 2002),
            "iyr", iyr -> isNumberBetween(iyr, 2010, 2020),
            "eyr", eyr -> isNumberBetween(eyr, 2020, 2030),
            "hgt", hgt -> {
                String height = hgt.substring(0, hgt.length() - 2);
                if (hgt.endsWith("cm")) {
                    return isNumberBetween(height, 150, 193);
                } else if (hgt.endsWith("in")) {
                    return isNumberBetween(height, 59, 76);
                }
                return false;
            },
            "hcl", hcl -> hcl.matches("#[0-9a-f]+"),
            "ecl", EYE_COLORS::contains,
            "pid", pid -> pid.length() == 9 && Integer.parseInt(pid) > -1
    );

    public static void main(String... args) {
        String[] rawPassports = Utils.readFile("day4.txt").split("\\n\\n");

        List<Map<String, String>> passports = Arrays.stream(rawPassports)
                .map(rawPassport -> Arrays.stream(rawPassport.split("[\\n\\s]"))
                        .map(pair -> {
                            String[] fieldAndVal = pair.split(":");
                            if (fieldAndVal.length != 2) throw Utils.fail("bad entry " + pair);
                            return fieldAndVal;
                        })
                        .collect(Collectors.toUnmodifiableMap(
                                fieldAndVal -> fieldAndVal[0],
                                fieldAndVal -> fieldAndVal[1])))
                .collect(Collectors.toList());

        long validPassportsP1 = passports.stream()
                .filter(passport -> passport.keySet().containsAll(REQUIRED_FIELDS.keySet()))
                .count();

        System.out.println("Day4 part1: " + validPassportsP1);

        long validPassportsP2 = passports.stream()
                .filter(passport -> passport.keySet().containsAll(REQUIRED_FIELDS.keySet())
                        && passport.entrySet().stream()
                                .allMatch(entry -> REQUIRED_FIELDS.getOrDefault(entry.getKey(), _v -> true)
                                        .test(entry.getValue())))
                .count();

        System.out.println("Day4 part2: " + validPassportsP2);
    }

    private static boolean isNumberBetween(String val, int min, int max) {
        int i = Integer.parseInt(val);
        return i >= min && i <= max;
    }
}
