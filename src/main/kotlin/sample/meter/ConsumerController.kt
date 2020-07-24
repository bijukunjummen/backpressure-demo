package sample.meter

import io.micrometer.core.instrument.Metrics
import org.slf4j.Logger
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import reactor.core.publisher.Mono
import reactor.core.scheduler.Schedulers
import reactor.util.concurrent.Queues
import java.lang.Thread.sleep
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@Service
class ConsumerController(private val producer: Producer) {

    private val logger: Logger = loggerFor<ConsumerController>()

    private val counter = Metrics.counter("consumer")

    private val executorService: ExecutorService = Executors.newFixedThreadPool(5)

    fun handleMessageScenario1(req: ServerRequest): Mono<ServerResponse> {
        val count: Long = req.queryParam("count").orElse("100").toLong()
        val producerRate: Int = req.queryParam("producerRate").orElse("5").toInt()
        val consumerRate: Int = req.queryParam("consumerRate").orElse("1").toInt()
        val delayBetweenConsumes: Long = 1000L / consumerRate
        executorService.submit {
            producer.produce(producerRate, count)
                .subscribe { value: Long ->
                    sleep(delayBetweenConsumes)
                    logger.info("Consumed {}", value)
                    counter.increment()
                }
        }
        return ServerResponse.accepted().build()
    }

    fun handleMessageScenario2(req: ServerRequest): Mono<ServerResponse> {
        val count: Long = req.queryParam("count").orElse("100").toLong()
        val producerRate: Int = req.queryParam("producerRate").orElse("5").toInt()
        val consumerRate: Int = req.queryParam("consumerRate").orElse("1").toInt()
        val delayBetweenConsumes: Long = 1000L / consumerRate
        val prefetch: Int = req.queryParam("prefetch").map { p -> p.toInt() }.orElse(Queues.SMALL_BUFFER_SIZE)


        executorService.submit {
            producer.produce(producerRate, count)
                .publishOn(Schedulers.boundedElastic(), prefetch)
                .subscribe { value: Long ->
                    sleep(delayBetweenConsumes)
                    logger.info("Consumed {}", value)
                    counter.increment()
                }
        }
        return ServerResponse.accepted().build()
    }

}