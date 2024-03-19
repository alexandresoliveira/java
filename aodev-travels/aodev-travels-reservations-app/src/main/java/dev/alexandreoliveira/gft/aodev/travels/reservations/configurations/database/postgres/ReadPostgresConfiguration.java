package dev.alexandreoliveira.gft.aodev.travels.reservations.configurations.database.postgres;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.DestinationEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.ReservationEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities.UserEntity;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.destinations.ReadDestinationsRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.reservations.ReadReservationsRepository;
import dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.repositories.users.ReadUsersRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.repository.support.DefaultJpaContext;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Configuration
@EnableJpaRepositories(
        entityManagerFactoryRef = "readEntityManagerFactory",
        transactionManagerRef = "readTransactionManager",
        basePackageClasses = {
                ReadDestinationsRepository.class,
                ReadUsersRepository.class,
                ReadReservationsRepository.class
        })
public class ReadPostgresConfiguration {

    private final ReadPostgresConfigurationProperties properties;

    public ReadPostgresConfiguration(ReadPostgresConfigurationProperties properties) {
        this.properties = properties;
    }

    @Bean(name = "readDataSource")
    public DataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setReadOnly(true);
        hikariConfig.setJdbcUrl(properties.url());
        hikariConfig.setUsername(properties.username());
        hikariConfig.setPassword(properties.password());

        hikariConfig.setMinimumIdle(properties.hikari().minimumIdle());
        hikariConfig.setMaximumPoolSize(properties.hikari().maximumPoolSize());
        hikariConfig.setIdleTimeout(properties.hikari().idleTimeout());
        hikariConfig.setMaxLifetime(properties.hikari().maxLifetime());
        hikariConfig.setConnectionTimeout(properties.hikari().connectionTimeout());
        hikariConfig.setPoolName(properties.hikari().poolName());

        return new HikariDataSource(hikariConfig);
    }

    @Bean(name = "readEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            @Qualifier("readDataSource") DataSource dataSource
    ) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("dev.alexandreoliveira.gft.aodev.travels.reservations.infrastructure.dataproviders.postgresql.entities");

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);

        Map<String, Object> hibernateProperties = new HashMap<>();
        hibernateProperties.put(AvailableSettings.HBM2DDL_AUTO, properties.hibernate().ddlAuto());
        hibernateProperties.put(AvailableSettings.SHOW_SQL, properties.hibernate().showSql());
        entityManagerFactoryBean.setJpaPropertyMap(hibernateProperties);

        return entityManagerFactoryBean;
    }

    @Bean(name = "readTransactionManager")
    public PlatformTransactionManager readTransactionManager(
            @Qualifier("readEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactory
    ) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory.getObject());
        return transactionManager;
    }

    @Bean(name = "readJpaContext")
    public JpaContext readJpaContext(
            @Qualifier("readEntityManagerFactory") LocalContainerEntityManagerFactoryBean entityManagerFactoryBean
    ) {
        EntityManager entityManager = Objects.requireNonNull(entityManagerFactoryBean.getObject()).createEntityManager();
        return new DefaultJpaContext(Set.of(entityManager));
    }

    @Bean(name = "readJpaUsersRepository")
    public JpaRepository<UserEntity, UUID> readJpaUsersRepository(@Qualifier("readJpaContext") JpaContext jpaContext) {
        return new SimpleJpaRepository<>(UserEntity.class, jpaContext.getEntityManagerByManagedType(UserEntity.class));
    }

    @Bean(name = "readJpaDestinationsRepository")
    public JpaRepository<DestinationEntity, UUID> readJpaDestinationsRepository(@Qualifier("readJpaContext") JpaContext jpaContext) {
        return new SimpleJpaRepository<>(DestinationEntity.class, jpaContext.getEntityManagerByManagedType(DestinationEntity.class));
    }

    @Bean(name = "readJpaReservationsRepository")
    public JpaRepository<ReservationEntity, UUID> readJpaReservationsRepository(@Qualifier("readJpaContext") JpaContext jpaContext) {
        return new SimpleJpaRepository<>(ReservationEntity.class, jpaContext.getEntityManagerByManagedType(ReservationEntity.class));
    }
}
