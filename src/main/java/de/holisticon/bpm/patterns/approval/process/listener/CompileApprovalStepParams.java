package de.holisticon.bpm.patterns.approval.process.listener;

import de.holisticon.bpm.patterns.approval.model.JointDecision;
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
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.KEY_APPROVER_NAMES;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.KEY_THRESHOLD;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.STEP_NUMBER;

/**
 * @author Jo Ehm (Holisticon)
 */
@Component
@Slf4j
public class CompileApprovalStepParams implements ExecutionListener {

  public static final String NAME = "compileApprovalStepParams";

  public void notify(DelegateExecution delegateExecution) throws Exception {

    // DMN table results map
    final Map approvalParams = (Map) delegateExecution.getVariable(APPROVAL_PARAMS);

    // extract approvers
    final String approverNames = (String) approvalParams.get(KEY_APPROVER_NAMES);
    final List<String> approversList = Arrays.asList(StringUtils.split(approverNames, ".,;:/|& "));

    // get threshold (fallback = 1)
    Integer approvalThreshold = (Integer) approvalParams.get(KEY_THRESHOLD);
    if (approvalThreshold == null || approvalThreshold < 1) approvalThreshold = 1;

    delegateExecution.setVariable(APPROVERS_LIST, approversList);
    delegateExecution.setVariable(JOINT_DECISION, JointDecision.of(approversList.size(), approvalThreshold));

    log.info("Approval step #{}: {} [threshold = {}]", delegateExecution.getVariable(STEP_NUMBER), approverNames, approvalThreshold);

  }
}
