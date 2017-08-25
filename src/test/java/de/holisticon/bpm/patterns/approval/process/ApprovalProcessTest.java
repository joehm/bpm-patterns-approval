package de.holisticon.bpm.patterns.approval.process;

import org.camunda.bpm.engine.ProcessEngineConfiguration;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.camunda.bpm.engine.impl.cfg.StandaloneInMemProcessEngineConfiguration;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.camunda.bpm.engine.test.Deployment;
import org.camunda.bpm.engine.test.ProcessEngineRule;
import org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions;
import org.camunda.bpm.engine.test.mock.MockExpressionManager;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Map;

import static org.camunda.bpm.engine.test.assertions.bpmn.AbstractAssertions.init;
import static org.camunda.bpm.engine.test.assertions.bpmn.BpmnAwareAssertions.assertThat;

/**
 * @author Jo Ehm (Holisticon)
 */
public class ApprovalProcessTest {

  private final ProcessEngineConfiguration configuration = new StandaloneInMemProcessEngineConfiguration() {{
    this.jobExecutorActivate = false;
    this.expressionManager = new MockExpressionManager();
    this.databaseSchemaUpdate = ProcessEngineConfigurationImpl.DB_SCHEMA_UPDATE_DROP_CREATE;
    this.isMetricsEnabled = false;
  }};

  @Rule
  public ProcessEngineRule rule = new ProcessEngineRule(configuration.buildProcessEngine());

  RuntimeService runtimeService;
  private Driver driver;
  private ProcessInstance instance;

  @Before
  public void testSetup() {

    AbstractAssertions.init(rule.getProcessEngine());
    runtimeService = rule.getRuntimeService();
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
