package dev.alexandreoliveira.mp.registrationservice.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import dev.alexandreoliveira.mp.registrationservice.services.AppService;
import org.springframework.stereotype.Service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

@AnalyzeClasses(packages = "dev.alexandreoliveira.mp.registrationservice.services")
class ServiceArchitectureTest {

  @ArchTest
  static final ArchRule mustServiceClassesHaveServiceAnnotation =
    classes()
      .that()
      .haveSimpleNameEndingWith("Service")
      .and()
      .areNotInterfaces()
      .should()
      .implement(AppService.class)
      .andShould()
      .beAnnotatedWith(Service.class);
}
