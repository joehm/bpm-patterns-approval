package de.holisticon.bpm.patterns.approval.process.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVAL_PARAMS;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVAL_THRESHOLD;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVERS_LIST;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.JOINT_DECISION;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.JOINT_DECISION_RESULT;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.KEY_APPROVER_NAMES;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.KEY_THRESHOLD;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.STEP_NUMBER;

/**
 * @author Jo Ehm (Holisticon)
 */
@Component
@Slf4j
public class ResetApprovalStepParams implements ExecutionListener {

  public static final String NAME = "resetApprovalStepParams";

  public void notify(DelegateExecution delegateExecution) throws Exception {

    delegateExecution.removeVariable(APPROVAL_PARAMS);
    delegateExecution.removeVariable(APPROVERS_LIST);
    delegateExecution.removeVariable(JOINT_DECISION);
    delegateExecution.removeVariable(JOINT_DECISION_RESULT);

    // prevent step number < 1
    int stepNumber = (Integer) delegateExecution.getVariable(STEP_NUMBER);
    if (stepNumber < 1) {
      log.warn("Step number = {} ??? Cannot go further back than step #1 :-o", stepNumber);
      delegateExecution.setVariable(STEP_NUMBER, 1);
    }
  }
}
