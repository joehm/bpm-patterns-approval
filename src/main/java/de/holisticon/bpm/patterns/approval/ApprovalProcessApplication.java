package de.holisticon.bpm.patterns.approval;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Jo Ehm (Holisticon)
 */
@SpringBootApplication
@EnableProcessApplication
public class ApprovalProcessApplication {

  public static void main(final String... args) throws Exception {
    SpringApplication.run(ApprovalProcessApplication.class, args);
  }

}
