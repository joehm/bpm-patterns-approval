package de.holisticon.bpm.patterns.approval.process;


public class ApprovalProcess {

  public static final String RESOURCE = "ApprovalProcess.bpmn";
  public static final String KEY = "process_approval";

  public enum Variables {
    ;
    public static final String APPROVAL_TITLE = "title";
    public static final String APPROVAL_VALUE = "value";
    public static final String APPROVAL_RISK = "risk";
    public static final String COMMENTS = "comments";

    public static final String APPROVAL_STEPS = "approvalSteps";
    public static final String STEP_NUMBER = "stepNumber";
    public static final String APPROVER_NAMES = "approverNames";
    public static final String APPROVERS_LIST = "approversList";

    public static final String APPROVER_NAME = "approverName";
    public static final String APPROVAL_DECISION = "approved";
  }


  public enum Elements {
    ;
    public static final String START_EVENT_APPROVAL_REQUESTED = "start_event_approval_requested";
    public static final String START_EVENT_APPROVAL_STEP = "start_event_approval_step";

    public static final String RULE_TASK_APPROVAL_ITERATIONS = "rule_task_approval_iterations";
    public static final String RULE_TASK_DETERMINE_APPROVERS = "rule_task_determine_approvers";
    public static final String SUB_MULTI_INSTANCE_EXECUTE_APPROVAL = "sub_mi_execute_approval#multiInstanceBody";
    public static final String USER_TASK_APPROVE = "user_task_approve";

    public static final String END_EVENT_APPROVED = "end_event_approved";
    public static final String END_EVENT_ESCALATE_NOT_APPROVED = "end_event_escalate_not_approved";
    public static final String BOUNDARY_CATCH_ESCALATE_NOT_APPROVED = "boundary_catch_escalate_not_approved";
    public static final String END_EVENT_APPROVAL_FINISHED = "end_event_approval_finished";
  }

  }
