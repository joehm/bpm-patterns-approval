package de.holisticon.bpm.patterns.approval.process;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.extension.needle.ProcessEngineNeedleRule;
import org.camunda.bpm.extension.needle.ProcessEngineNeedleRuleBuilder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import javax.inject.Inject;
import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;

/**
 * @author Jo Ehm (Holisticon)
 */
public class ApprovalProcessTest {

  @Rule
  public ProcessEngineNeedleRule rule = new ProcessEngineNeedleRuleBuilder(this).build();

  @Inject
  RuntimeService runtimeService;

  @Inject
  TaskService taskService;

  private Driver driver;
  private ProcessInstance instance;

  @Before
  public void testSetup() {

    init(rule.getProcessEngine());
    driver = new Driver();

    // automatically mock all delegates
    // DelegateExpressions.autoMock(ApprovalProcess.RESOURCE);
  }

  @Test
  @Deployment(resources = ApprovalProcess.RESOURCE)
  public void should_deploy() {
    // just deploy to verify correct BPMN
  }

  private class Driver {

    void startProcess(Map<String, Object> variables) {
      instance = runtimeService.startProcessInstanceByKey(ApprovalProcess.KEY, variables);
      assertThat(instance).isNotNull();
    }

    void startProcessBeforeActivity(String activityName, Map<String, Object> variables) {
      instance = runtimeService.createProcessInstanceByKey(ApprovalProcess.KEY)
          .startBeforeActivity(activityName)
          .setVariables(variables)
          .execute();
    }
  }



}
