package sample.meter.web

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.test.web.reactive.server.WebTestClient
import sample.meter.ConsumerController
import sample.meter.Producer
import sample.meter.config.RoutesConfig

@WebFluxTest(controllers = arrayOf(RoutesConfig::class, ConsumerController::class, Producer::class))
class ConsumerControllerControllerTest {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    @Test
    fun testCallToMessageEndpoint() {
        webTestClient.get().uri("/scenario1")
            .exchange()
            .expectStatus().isAccepted
    }


}