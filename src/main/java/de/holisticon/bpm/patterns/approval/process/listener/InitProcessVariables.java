package de.holisticon.bpm.patterns.approval.process.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVAL_DECISION;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVER_NAME;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.STEP_NUMBER;

/**
 * @author Jo Ehm (Holisticon)
 */
@Component
@Slf4j
public class InitProcessVariables implements ExecutionListener {

  public static final String NAME = "initProcessVariables";

  public void notify(DelegateExecution delegateExecution) throws Exception {

    delegateExecution.setVariable(STEP_NUMBER, 1);
    log.info("Approval process initialized...");
  }
}
