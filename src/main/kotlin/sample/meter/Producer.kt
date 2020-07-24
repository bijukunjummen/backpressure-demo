package sample.meter

import io.micrometer.core.instrument.Metrics
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import reactor.core.publisher.SynchronousSink

@Component
class Producer {

    private val counter = Metrics.counter("producer")

    fun produce(targetRate: Int, upto: Long): Flux<Long> {
        val delayBetweenEmits: Long = 1000L / targetRate

        return Flux
            .generate<Long, Long>(
                { 1L },
                { state: Long, sink: SynchronousSink<Long> ->
                    Thread.sleep(delayBetweenEmits)
                    val nextState: Long = state + 1
                    if (state > upto) {
                        sink.complete()
                        nextState
                    } else {
                        LOGGER.info("Emitted {}", state)
                        sink.next(state)
                        counter.increment()
                        nextState
                    }
                }
            )
    }

    companion object {
        val LOGGER = loggerFor<Producer>()
    }
}