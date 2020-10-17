package ua.univ.vsynytsyn.timetable;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class GeneticAlgorithmApplication {

    public static void main(String[] args) {
        SpringApplication.run(GeneticAlgorithmApplication.class, args);
    }

}
