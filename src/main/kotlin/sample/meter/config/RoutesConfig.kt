package sample.meter.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.router
import sample.meter.Consumer


@Configuration
class RoutesConfig {

    @Bean
    fun apis(consumer: Consumer) = router {
        (accept(MediaType.APPLICATION_JSON)).nest {
            GET("/scenario1", consumer::handleMessageScenario1)
            GET("/scenario2", consumer::handleMessageScenario2)
            GET("/scenario3", consumer::handleMessageScenario3)
        }
    }

}