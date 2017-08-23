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

    public static final String APPROVAL_PARAMS = "approvalParameters";
    public static final String KEY_APPROVER_NAMES = "approverNames";
    public static final String KEY_THRESHOLD = "threshold";

    public static final String APPROVERS_LIST = "approversList";
    public static final String APPROVAL_THRESHOLD = "approvalThreshold";

    public static final String APPROVER_NAME = "approverName";
    public static final String APPROVAL_DECISION = "approvalDecision";
    public static final String JOINT_DECISION = "jointDecision";
    public static final String JOINT_DECISION_RESULT = "jointDecision_result";
  }


  public enum Elements {
    ;
    public static final String START_EVENT_APPROVAL_REQUESTED = "start_event_approval_requested";
    public static final String RULE_TASK_APPROVAL_STEPS = "rule_task_approval_steps";
    public static final String RULE_TASK_APPROVAL_STEP_PARAMS = "rule_task_approval_step_params";

    public static final String SUB_MULTI_INSTANCE_EXECUTE_APPROVAL = "sub_mi_execute_approval#multiInstanceBody";

    public static final String START_EVENT_APPROVAL_STEP = "start_event_approval_step";
    public static final String USER_TASK_APPROVE = "user_task_approve";
    public static final String INTERMEDIATE_EVENT_APPROVED = "intermediate_event_approved";
    public static final String INTERMEDIATE_EVENT_DECLINED = "intermediate_event_declined";
    public static final String INTERMEDIATE_EVENT_RESUBMISSION = "intermediate_event_resubmission";
    public static final String SERVICE_TASK_COMPILE_JOINT_DECISION = "service_task_compile_joint_decision";

    public static final String END_EVENT_APPROVAL_STEP = "end_event_approval_step";
    public static final String END_EVENT_ESCALATE_DECISION_MADE = "end_event_escalate_decision_made";
    public static final String BOUNDARY_CATCH_ESCALATE_NOT_APPROVED = "boundary_catch_escalate_decision_made";

    public static final String SCRIPT_TASK_SET_NEXT_STEP = "script_task_set_next_step";
    public static final String SCRIPT_TASK_SET_PREVIOUS_STEP = "script_task_set_previous_step";

    public static final String END_EVENT_REQUEST_DECLINED = "end_event_request_declined";
    public static final String END_EVENT_REQUEST_APPROVED = "end_event_request_approved";
  }

  }
