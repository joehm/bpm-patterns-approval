package de.holisticon.bpm.patterns.approval.process.listener;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.ExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVAL_DECISION;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVERS_LIST;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVER_NAME;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVER_NAMES;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.STEP_NUMBER;

/**
 * @author Jo Ehm (Holisticon)
 */
@Component
@Slf4j
public class BuildApproversList implements ExecutionListener {

  public static final String NAME = "buildApproversList";
  public void notify(DelegateExecution delegateExecution) throws Exception {

    final String approverNames = (String) delegateExecution.getVariable(APPROVER_NAMES);
    final List<String> approversList = Arrays.asList(StringUtils.split(approverNames, ".,;:/|& "));

    delegateExecution.setVariable(APPROVERS_LIST, approversList);

    log.info("Approval step #{}: {}", delegateExecution.getVariable(STEP_NUMBER), approverNames);
  }
}
