package dev.alexandreoliveira.mp.registrationservice.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.syntax.elements.ClassesThat;
import com.tngtech.archunit.lang.syntax.elements.GivenClassesConjunction;
import dev.alexandreoliveira.mp.registrationservice.services.AppService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@AnalyzeClasses(packages = "dev.alexandreoliveira.mp.registrationservice.controllers")
class ControllerArchitectureTest {

  private static ClassesThat<GivenClassesConjunction> CLASSES_THAT = null;

  @ArchTest
  static final ArchRule mustControllersAreRestControllers =
    classesThatSingleton()
      .haveSimpleNameEndingWith("Controller")
      .should()
      .beAnnotatedWith(RestController.class)
      .andShould()
      .beAnnotatedWith(RequestMapping.class);


  @ArchTest
  static final ArchRule mustControllersContainsOnlyServiceField =
    classesThatSingleton()
      .haveSimpleNameEndingWith("Controller")
      .should()
      .getField(
        AppService.class,
        "service"
      );

  static ClassesThat<GivenClassesConjunction> classesThatSingleton() {
    if (Objects.isNull(CLASSES_THAT)) {
      CLASSES_THAT = classes().that();
    }
    return CLASSES_THAT;
  }
}
