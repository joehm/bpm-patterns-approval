package de.holisticon.bpm.patterns.approval.process;


public class ApprovalProcess {

  public static final String RESOURCE = "ApprovalProcess.bpmn";
  public static final String KEY = "process_approval";

  public enum Variables {
    ;
    public static final String APPROVAL_TITLE = "title";
    public static final String APPROVAL_VALUE = "value";
    public static final String APPROVAL_RISK = "risk";
    public static final String COMMENT = "comment";

    public static final String APPROVED_DECISION = "approved";
    public static final String APPROVAL_ITERATIONS = "approvalIterations";
    public static final String APPROVERS = "approvers";
  }


  public enum Elements {
    ;
    public static final String START_EVENT_APPROVAL_REQUESTED = "start_event_approval_requested";

    public static final String RULE_TASK_APPROVAL_ITERATIONS = "rule_task_approval_iterations";
    public static final String RULE_TASK_NEXT_APPROVERS = "rule_task_next_approvers";
    public static final String USER_TASK_APPROVE = "user_task_approve";
  }
    public static final String END_EVENT_APPROVAL_FINISHED = "end_event_approval_finished";

}
