package sample.meter;

import org.HdrHistogram.Histogram;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.assertj.core.api.Assertions.assertThat;

public class LowPercentileThanMeanTest {

    @Test
    public void testThat99PercentileCanBeBelowMean() {
        Histogram histogram = new Histogram(3);

        //Record 99+ low values - say in 1 to 5 range
        ThreadLocalRandom rnd = ThreadLocalRandom.current();

        for (int i = 1; i <= 99; i++) {
            int value = 1 + rnd.nextInt(5);
            histogram.recordValue(value);
        }

        //Record a high value
        histogram.recordValue(1000L);

        double mean = histogram.getMean();
        long p90 = histogram.getValueAtPercentile(90);
        long p99 = histogram.getValueAtPercentile(99);

        System.out.println("mean: " + mean);
        System.out.println("90 Percentile: " + p90);
        System.out.println("99 Percentile: " + p99);

        assertThat((double)p99).isLessThan(mean);
    }

}
