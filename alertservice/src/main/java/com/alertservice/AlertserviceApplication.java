package com.alertservice;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafkaStreams;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
@EnableKafkaStreams
public class AlertserviceApplication {

	public static void main(String[] args) {
		// deleteKafkaStreamsState();
		SpringApplication.run(AlertserviceApplication.class, args);
	}


	private static void deleteKafkaStreamsState() {
        try {
            File stateDir = new File(System.getProperty("java.io.tmpdir"), "kafka-streams/alertservic-v1");
            if (stateDir.exists()) {
                Files.walk(stateDir.toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
                System.out.println("Deleted Kafka Streams state dir: " + stateDir.getAbsolutePath());
            }
        } catch (IOException e) {
            System.err.println("Failed to delete Kafka Streams state dir: " + e.getMessage());
        }
    }
}
