package de.holisticon.bpm.patterns.approval.process.listener;

import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVAL_DECISION;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVER_NAME;

/**
 * @author Jo Ehm (Holisticon)
 */
@Component
@Slf4j
public class ExecuteApprovalInitVariables implements ExecutionListener {

  public static final String NAME = "executeApprovalInitVariables";

  public void notify(DelegateExecution delegateExecution) throws Exception {

    // local variable for each multi-instance execution
    delegateExecution.setVariableLocal(APPROVAL_DECISION, null);
  }
}
