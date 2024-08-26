package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;
import me.haroldmartin.objective.ObjectiveClient;
import me.haroldmartin.objective.models.index.Index;

@SpringBootApplication
@RestController
public class DemoApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    String apiKey = System.getenv("OBJECTIVE_KEY");
    ObjectiveClient client = new ObjectiveClient(apiKey, true, kotlinx.coroutines.Dispatchers.getIO());

    @GetMapping("/indexes")
    public String indexes() throws InterruptedException {
        List<Index> indexes = BuildersKt.runBlocking(
            EmptyCoroutineContext.INSTANCE,
            (scope, continuation) -> client.getIndexes(continuation)
        );
        return indexes.stream().map(Index::getId).collect(Collectors.joining("<br />"));
    }
}