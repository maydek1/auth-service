package com.modsen.software.authservice.config

import feign.Feign
import feign.Retryer
import feign.form.FormEncoder
import feign.gson.GsonDecoder
import feign.gson.GsonEncoder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FeignConfig {
    @Bean
    fun feignRetryer(): Retryer {
        return Retryer.Default(RETRYER_PERIOD.toLong(), RETRYER_MAX_PERIOD.toLong(), RETRYER_MAX_ATTEMPTS)
    }
    @Bean
    fun feignBuilder(): Feign.Builder {
        return Feign.builder()
            .encoder(FormEncoder(GsonEncoder()))
            .decoder(GsonDecoder())
    }

    companion object {
        private const val RETRYER_PERIOD = 1000
        const val RETRYER_MAX_PERIOD = 5000
        const val RETRYER_MAX_ATTEMPTS = 3
    }
}
