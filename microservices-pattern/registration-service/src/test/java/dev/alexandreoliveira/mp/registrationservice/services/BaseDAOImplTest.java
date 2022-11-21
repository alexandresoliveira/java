package dev.alexandreoliveira.mp.registrationservice.services;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.data.util.ReflectionUtils;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.springframework.data.util.ReflectionUtils.findField;
import static org.springframework.util.ReflectionUtils.FieldFilter;

@DirtiesContext
@ActiveProfiles({""})
public class BaseDAOImplTest {

  @TempDir
  static Path sharedTempDir;

  static void buildRegistry(
    DynamicPropertyRegistry registry,
    final String databaseUrl,
    final String databaseUsername,
    final String databasePassword,
    final boolean useFlyway
  ) {
    registry.add(
      "spring.jpa.hibernate.ddl-auto",
      () -> "update"
    );
    registry.add(
      "spring.jpa.show-sql",
      () -> "true"
    );
    registry.add(
      "spring.jpa.database-platform",
      () -> "org.hibernate.dialect.H2Dialect"
    );
    registry.add(
      "spring.datasource.url",
      () -> databaseUrl
    );
    registry.add(
      "spring.datasource.username",
      () -> databaseUsername
    );
    registry.add(
      "spring.datasource.password",
      () -> databasePassword
    );
    registry.add(
      "spring.datasource.driver-class-name",
      () -> "org.h2.Driver"
    );

    registry.add(
      "spring.flyway.enabled",
      () -> String.valueOf(useFlyway)
    );

    if (useFlyway) {
      registry.add(
        "spring.flyway.url",
        () -> databaseUrl
      );
      registry.add(
        "spring.flyway.user",
        () -> databaseUsername
      );
      registry.add(
        "spring.flyway.password",
        () -> databasePassword
      );
      registry.add(
        "spring.flyway.encoding",
        () -> "UTF-8"
      );
      registry.add(
        "spring.flyway.create-schemas",
        () -> "true"
      );
      registry.add(
        "spring.flyway.init-sql",
        () -> "CREATE SCHEMA IF NOT EXISTS REGISTRATION"
      );
      registry.add(
        "spring.flyway.schemas",
        () -> "registration"
      );
      registry.add(
        "spring.flyway.locations",
        () -> List.of("classpath:db/migration/postgresql")
      );
    }
  }

  public <T, ID> List<T> insertFakeDataFor(
    Class<T> entity,
    String[] fields,
    int numberOfEntities
  ) {
    Assert.isTrue(
      numberOfEntities <= 5,
      "Por favor, gerar no máximo 5 entidades para os testes"
    );

    var faker = new Faker();

    List<T> entities = new ArrayList<>();

    for (int index = 0; index < numberOfEntities; index++) {
      T newEntity = ReflectionUtils.createInstanceIfPresent(
        entity.getName(),
        null
      );

      Assert.notNull(
        newEntity,
        "Objeto (" + entity.getSimpleName() + ") não encontrado"
      );

      Stream
        .of(fields)
        .forEach(fieldClass -> {
          Assert.isTrue(
            fieldClass.contains("_"),
            "O campo não tem o tipo de dado desejado definido. Ex.: <nome_atributo>_<tipo_dado>. Os tipos podem ser encontrados em BaseDAOImplTest.TypesOfValueDataEnum"
          );

          String[] fieldClassSplit = fieldClass.split("_");
          String attribute = fieldClassSplit[0];
          String attributeType = fieldClassSplit[1];

          FieldFilter fieldFilter = field -> field.getName().equals(attribute);

          Field field = findField(
            entity,
            fieldFilter
          );

          Assert.notNull(
            field,
            "O atributo " + attribute + " não foi encontrado na classe " + entity.getSimpleName()
          );

          ReflectionUtils.setField(
            field,
            newEntity,
            value(
              field.getType(),
              attributeType,
              faker
            )
          );
        });

      entities.add(newEntity);
    }

    return entities;
  }

  private Object value(
    Class<?> fieldType,
    String attributeDataType,
    Faker faker
  ) {
    if (fieldType.isAssignableFrom(String.class)) {
      return TypesOfValueDataEnum.returnDataType(
        attributeDataType,
        faker
      );
    } else if (fieldType.isAssignableFrom(Integer.class)) {
      return TypesOfValueDataEnum.returnDataType(
        "NUMBER",
        faker
      );
    } else if (fieldType.isAssignableFrom(Double.class)) {
      return TypesOfValueDataEnum.returnDataType(
        "DECIMAL",
        faker
      );
    } else {
      return TypesOfValueDataEnum.returnDataType(
        "DEFAULT",
        faker
      );
    }
  }

  private enum TypesOfValueDataEnum {
    NAME,
    EMAIL,
    NUMBER,
    DECIMAL,
    PASSWORD,
    DEFAULT;

    static Object returnDataType(
      String type,
      Faker faker
    ) {
      return switch (TypesOfValueDataEnum.valueOf(type)) {
        case NAME -> faker.name().fullName();
        case EMAIL -> faker.bothify("????##@email.com");
        case NUMBER -> faker.number().randomDigit();
        case DECIMAL -> faker.number()
                             .randomDouble(
                               2,
                               0,
                               10
                             );
        case PASSWORD, DEFAULT -> faker.regexify("[a-z1-9]{10}");
      };
    }
  }
}
