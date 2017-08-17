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

import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.END_EVENT_APPROVAL_FINISHED;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Elements.START_EVENT_APPROVAL_REQUESTED;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Elements.USER_TASK_APPROVE;
import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.complete;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareTests.task;

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
    // DelegateExpressions.autoMock(ShipmentProcess.RESOURCE);
  }

  @Test
  @Deployment(resources = ApprovalProcess.RESOURCE)
  public void should_deploy() {
    // just deploy
  }

  @Test
  @Deployment(resources = ApprovalProcess.RESOURCE)
  public void should_start_process() {

    // given
    driver.startProcess(null);

    // then
    assertThat(instance).isWaitingAt(USER_TASK_APPROVE);

    // when
    complete(task(USER_TASK_APPROVE));

    // then
    assertThat(instance).isEnded();
    assertThat(instance).hasPassedInOrder(
        START_EVENT_APPROVAL_REQUESTED,
        USER_TASK_APPROVE,
        END_EVENT_APPROVAL_FINISHED
    );
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
