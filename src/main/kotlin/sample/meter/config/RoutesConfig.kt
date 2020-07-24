package sample.meter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router
import sample.meter.ConsumerController


@Configuration
class RoutesConfig {

    @Bean
    fun apis(consumerController: ConsumerController) = router {
        (accept(MediaType.APPLICATION_JSON)).nest {
            GET("/scenario1", consumerController::handleMessageScenario1)
            GET("/scenario2", consumerController::handleMessageScenario2)
        }
    }

}