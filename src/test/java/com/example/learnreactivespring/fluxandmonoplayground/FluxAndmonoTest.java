package com.example.learnreactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class FluxAndmonoTest {

    @Test
    public void fluxTest(){

        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive spring")
                                        //.concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .concatWith(Flux.just("After Error"))
                                        .log();
        stringFlux.subscribe(System.out::println, (e) -> System.err.println("Exception is" + e)
        , () -> System.out.println("Completed"));
    }

    @Test
    public void fluxTestElements_withoutError(){
        Flux<String> stringFlux = Flux.just("Spring", "Spring boot", "Reactive spring")
                .concatWith(Flux.error(new RuntimeException("Exception Occurred")))
                .log();
        StepVerifier.create(stringFlux).expectNext("Spring")
                .expectNextCount(3)
                .expectErrorMessage("Exception Occurred")
                .verify();
    }

    @Test
    public void monoTest(){
        Mono<String> stringMono = Mono.just("Spring");
        StepVerifier.create(stringMono.log())
                    .expectNext("Spring")
                .verifyComplete();
    }

    @Test
    public void monoTest_error(){
        Mono<String> stringMono = Mono.just("Spring");
        StepVerifier.create(Mono.error(new RuntimeException("Exception Occurred")))
                .expectError(RuntimeException.class)
                .verify();
    }
}
