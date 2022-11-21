package dev.alexandreoliveira.mp.registrationservice.services;

import org.springframework.test.context.DynamicPropertyRegistry;

public class H2BaseDAOImplTest extends BaseDAOImplTest {

  public static void buildRegistry(
    DynamicPropertyRegistry registry,
    Class<?> testClass,
    boolean useFlyway
  ) {
    final String databaseUrl = String.format(
      "jdbc:h2:%s/%sDB;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=false;INIT=create schema if not exists registration",
      sharedTempDir.toAbsolutePath(),
      testClass.getSimpleName()
    );
    final String databaseUsername = "";
    final String databasePassword = "";

    buildRegistry(
      registry,
      databaseUrl,
      databaseUsername,
      databasePassword,
      useFlyway
    );
  }
}
