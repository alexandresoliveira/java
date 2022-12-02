package dev.alexandreoliveira.mp.registrationservice.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import dev.alexandreoliveira.mp.registrationservice.services.AppService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.constructors;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.fields;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

@AnalyzeClasses(packages = "dev.alexandreoliveira.mp.registrationservice.controllers")
class ControllerArchitectureTest {

  @ArchTest
  static final ArchRule mustControllersAreRestControllers =
    classes()
      .that()
      .haveSimpleNameEndingWith("Controller")
      .should()
      .beAnnotatedWith(RestController.class)
      .andShould()
      .beAnnotatedWith(RequestMapping.class);

  @ArchTest
  static final ArchRule mustControllerHaveOnlyServiceField =
    fields()
      .that()
      .areDeclaredInClassesThat()
      .haveSimpleNameEndingWith("Controller")
      .and()
      .haveRawType(AppService.class)
      .should()
      .bePrivate()
      .andShould()
      .beFinal();

  @ArchTest
  static final ArchRule mustControllersHaveConstructorWithAppServiceParam =
    constructors()
      .that()
      .areDeclaredInClassesThat()
      .haveSimpleNameEndingWith("Controller")
      .and()
      .arePublic()
      .should()
      .haveRawParameterTypes(AppService.class);

  @ArchTest
  static final ArchRule mustControllersHaveHandleMethod =
    methods()
      .that()
      .areDeclaredInClassesThat()
      .haveSimpleNameEndingWith("Controller")
      .should()
      .haveName("handle")
      .andShould()
      .bePublic();
}
