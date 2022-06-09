package dev.alexandreoliveira.apps.tictactoe;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.*;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@DisplayName("dev.alexandreoliveira.apps.tictactoe.TicTacToeArchitectureTest")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class TicTacToeArchitectureTest {

  @Test
  @Order(1)
  void services_should_only_be_accessed_by_controllers_and_other_services() {
    ImportOption ignoreTests = location -> !location.contains("/test/");

    JavaClasses importPackages = new ClassFileImporter()
      .withImportOption(ignoreTests)
      .importPackages("dev.alexandreoliveira.apps.tictactoe.usecases");

    ArchRule myRule = classes()
      .that()
      .resideInAPackage("..usecases..")
      .should()
      .onlyBeAccessed()
      .byAnyPackage(
        "..controllers..",
        "..usecases.."
      );

    myRule.check(importPackages);
  }

  @Test
  @Order(2)
  void must_be_entities_inside_right_package() {
    ImportOption ignoreTests = location -> !location.contains("/test/");

    JavaClasses importPackages = new ClassFileImporter()
      .withImportOption(ignoreTests)
      .importPackages("dev.alexandreoliveira.apps.tictactoe");

    ArchRule myRule = classes()
      .that()
        .haveNameMatching(".*Entity")
      .should()
        .resideInAPackage("..entities");

    myRule.check(importPackages);
  }
}
