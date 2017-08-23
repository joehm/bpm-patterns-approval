package de.holisticon.bpm.patterns.approval.process.delegate;

import de.holisticon.bpm.patterns.approval.model.ApprovalDecision;
import de.holisticon.bpm.patterns.approval.model.JointDecision;
import lombok.extern.slf4j.Slf4j;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.APPROVAL_DECISION;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.JOINT_DECISION;
import static de.holisticon.bpm.patterns.approval.process.ApprovalProcess.Variables.JOINT_DECISION_RESULT;

/**
 * @author Jo Ehm (Holisticon)
 */
@Component
@Slf4j
public class CompileJointDecision implements JavaDelegate {

  public static final String NAME = "compileJointDecision";

  public void execute(DelegateExecution delegateExecution) throws Exception {

    final ApprovalDecision decision = ApprovalDecision.valueOf((String) delegateExecution.getVariableLocal(APPROVAL_DECISION));

    synchronized (delegateExecution.getProcessInstanceId().intern()) {

      final JointDecision jointDecision = (JointDecision) delegateExecution.getVariable(JOINT_DECISION);
      jointDecision.add(decision);
      delegateExecution.setVariable(JOINT_DECISION, jointDecision);

      final ApprovalDecision finalDecision = jointDecision.getFinalDecision();
      delegateExecution.setVariable(JOINT_DECISION_RESULT, finalDecision != null ? finalDecision.name() : null);
    }
  }
}
