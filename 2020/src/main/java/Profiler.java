import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Profiler {

    static class ProfileResult {
        public final long p99;
        public final long p90;
        public final long p50;
        public final double avg;
        public final long max;

        public ProfileResult(long p99, long p90, long p50, double avg, long max) {
            this.p99 = p99;
            this.p90 = p90;
            this.p50 = p50;
            this.avg = avg;
            this.max = max;
        }

        public static ProfileResult generate(List<Long> nanoTimes) {
            return new ProfileResult(
                    percentile(nanoTimes, 99),
                    percentile(nanoTimes, 90),
                    percentile(nanoTimes, 50),
                    nanoTimes.stream().mapToLong(i -> i).average().getAsDouble(),
                    percentile(nanoTimes, 100));
        }

        @Override
        public String toString() {
            return "ProfileResult{" +
                    "p99=" + p99 +
                    ", p90=" + p90 +
                    ", p50=" + p50 +
                    ", avg=" + avg +
                    ", max=" + max +
                    '}';
        }
    }

    public static ProfileResult profile(int times, Runnable runnable) {
        ArrayList<Long> nanoDurations = new ArrayList<>(times);
        for (int i = 0; i < times; i++) {
            long start = System.nanoTime();
            runnable.run();
            nanoDurations.add(System.nanoTime() - start);
        }
        return ProfileResult.generate(nanoDurations);
    }

    public static void compare(String name, int times, Runnable... runnables) {
        int i = 1;
        for (Runnable runnable : runnables) {
            System.out.println(String.format("Profiling %s-%d: %s", name, i++, profile(times, runnable)));
        }
    }

    public static long percentile(List<Long> latencies, double percentile) {
        Collections.sort(latencies);
        int index = (int) Math.ceil(percentile / 100.0 * latencies.size());
        return latencies.get(index-1);
    }
}
