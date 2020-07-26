package sample.meter

import org.junit.jupiter.api.Test
import reactor.test.StepVerifier

class ProducerTest {

    @Test
    fun shouldProduce5AtTheRateOf10PerSec() {
        StepVerifier.create(Producer().produce(10, 5))
            .expectNext(1, 2, 3, 4, 5)
            .verifyComplete()
    }

}