package dev.alexandreoliveira.gft.aodev.travels.reservations;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ConfigurationPropertiesScan("dev.alexandreoliveira.gft.aodev.travels.reservations.configurations")
public class AODevTravelsReservationsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AODevTravelsReservationsApplication.class, args);
    }
}
