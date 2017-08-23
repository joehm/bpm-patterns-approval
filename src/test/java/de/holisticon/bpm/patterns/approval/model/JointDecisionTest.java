package de.holisticon.bpm.patterns.approval.model;

import org.junit.Test;

import static de.holisticon.bpm.patterns.approval.model.ApprovalDecision.APPROVED;
import static de.holisticon.bpm.patterns.approval.model.ApprovalDecision.DECLINED;
import static de.holisticon.bpm.patterns.approval.model.ApprovalDecision.RESUBMISSION;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

/**
 * @author Jo Ehm (Holisticon)
 */
public class JointDecisionTest {

  @Test
  public void should_create_joint_decision_by_voters_only() {

    JointDecision jointDecision = JointDecision.of(1);
    assertThat(jointDecision.getVoters(), is(1L));
    assertThat(jointDecision.getThreshold(), is(1L));
    assertNoVotesYet(jointDecision);

    jointDecision = JointDecision.of(2);
    assertThat(jointDecision.getVoters(), is(2L));
    assertThat(jointDecision.getThreshold(), is(2L));
    assertNoVotesYet(jointDecision);

    jointDecision = JointDecision.of(3);
    assertThat(jointDecision.getVoters(), is(3L));
    assertThat(jointDecision.getThreshold(), is(2L));
    assertNoVotesYet(jointDecision);

    jointDecision = JointDecision.of(4);
    assertThat(jointDecision.getVoters(), is(4L));
    assertThat(jointDecision.getThreshold(), is(3L));
    assertNoVotesYet(jointDecision);

    jointDecision = JointDecision.of(5);
    assertThat(jointDecision.getVoters(), is(5L));
    assertThat(jointDecision.getThreshold(), is(3L));
    assertNoVotesYet(jointDecision);

    jointDecision = JointDecision.of(6);
    assertThat(jointDecision.getVoters(), is(6L));
    assertThat(jointDecision.getThreshold(), is(4L));
    assertNoVotesYet(jointDecision);

    jointDecision = JointDecision.of(7);
    assertThat(jointDecision.getVoters(), is(7L));
    assertThat(jointDecision.getThreshold(), is(4L));
    assertNoVotesYet(jointDecision);
  }

  @Test
  public void should_create_joint_decision_by_voters_and_threshold() {

    // single decision
    JointDecision jointDecision = JointDecision.of(1, 1);
    assertThat(jointDecision.getVoters(), is(1L));
    assertThat(jointDecision.getThreshold(), is(1L));
    assertNoVotesYet(jointDecision);

    jointDecision = JointDecision.of(3, 2);
    assertThat(jointDecision.getVoters(), is(3L));
    assertThat(jointDecision.getThreshold(), is(2L));
    assertNoVotesYet(jointDecision);

    // unanimous decision
    jointDecision = JointDecision.of(5, 5);
    assertThat(jointDecision.getVoters(), is(5L));
    assertThat(jointDecision.getThreshold(), is(5L));
    assertNoVotesYet(jointDecision);
  }

  @Test
  public void should_create_singleton_joint_decision() {

    JointDecision singletonDecision = JointDecision.singleton();
    assertThat(singletonDecision.getVoters(), is(1L));
    assertThat(singletonDecision.getThreshold(), is(1L));
    assertNoVotesYet(singletonDecision);

    assertThat(singletonDecision, is(JointDecision.of(1)));
    assertThat(singletonDecision, is(JointDecision.of(1, 1)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_not_create_decision_without_voters() {
    JointDecision.of(0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_not_create_decision_without_voters_but_threshold() {
    JointDecision.of(0, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_not_create_decision_without_negative_number_of_voters() {
    JointDecision.of(-5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_not_create_decision_without_negative_number_of_voters_but_threshold() {
    JointDecision.of(-5, 3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_not_create_decision_without_threshold() {
    JointDecision.of(3, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_not_create_decision_with_negative_threshold() {
    JointDecision.of(1, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void should_not_create_decision_with_threshold_higher_than_number_of_voters() {
    JointDecision.of(3, 4);
  }

  @Test
  public void should_compile_singleton_decision() {

    JointDecision singletonDecision = JointDecision.singleton();
    singletonDecision.add(APPROVED);
    assertThat(singletonDecision.getApprovedCount(), is(1L));
    assertThat(singletonDecision.getDeclinedCount(), is(0L));
    assertThat(singletonDecision.getResubmissionCount(), is(0L));
    assertThat(singletonDecision.isDecisionMade(), is(true));
    assertThat(singletonDecision.getFinalDecision(), is(APPROVED));
  }

  @Test
  public void should_approve_decision_as_soon_as_threshold_reached() {

    JointDecision jointDecision = JointDecision.of(4, 2);
    jointDecision.add(APPROVED);
    assertThat(jointDecision.getApprovedCount(), is(1L));
    assertThat(jointDecision.getDeclinedCount(), is(0L));
    assertThat(jointDecision.getResubmissionCount(), is(0L));
    assertThat(jointDecision.isDecisionMade(), is(false));
    assertThat(jointDecision.getFinalDecision(), nullValue());

    jointDecision.add(DECLINED);
    assertThat(jointDecision.getApprovedCount(), is(1L));
    assertThat(jointDecision.getDeclinedCount(), is(1L));
    assertThat(jointDecision.getResubmissionCount(), is(0L));
    assertThat(jointDecision.isDecisionMade(), is(false));
    assertThat(jointDecision.getFinalDecision(), nullValue());

    jointDecision.add(APPROVED);
    assertThat(jointDecision.getApprovedCount(), is(2L));
    assertThat(jointDecision.getDeclinedCount(), is(1L));
    assertThat(jointDecision.getResubmissionCount(), is(0L));
    assertThat(jointDecision.isDecisionMade(), is(true));      // gotcha!
    assertThat(jointDecision.getFinalDecision(), is(APPROVED));
  }

  @Test
  public void should_approve_decision_when_no_more_otstanding_votes() {

    JointDecision jointDecision = JointDecision.of(4, 2);
    jointDecision.add(APPROVED);
    assertThat(jointDecision.getApprovedCount(), is(1L));
    assertThat(jointDecision.getDeclinedCount(), is(0L));
    assertThat(jointDecision.getResubmissionCount(), is(0L));
    assertThat(jointDecision.isDecisionMade(), is(false));
    assertThat(jointDecision.getFinalDecision(), nullValue());

    jointDecision.add(DECLINED);
    assertThat(jointDecision.getApprovedCount(), is(1L));
    assertThat(jointDecision.getDeclinedCount(), is(1L));
    assertThat(jointDecision.getResubmissionCount(), is(0L));
    assertThat(jointDecision.isDecisionMade(), is(false));
    assertThat(jointDecision.getFinalDecision(), nullValue());

    jointDecision.add(DECLINED);
    assertThat(jointDecision.getApprovedCount(), is(1L));
    assertThat(jointDecision.getDeclinedCount(), is(2L));
    assertThat(jointDecision.getResubmissionCount(), is(0L));
    assertThat(jointDecision.isDecisionMade(), is(false));      // not yet - there's still a possibility for a yes!
    assertThat(jointDecision.getFinalDecision(), nullValue());

    jointDecision.add(APPROVED);
    assertThat(jointDecision.getApprovedCount(), is(2L));
    assertThat(jointDecision.getDeclinedCount(), is(2L));
    assertThat(jointDecision.getResubmissionCount(), is(0L));
    assertThat(jointDecision.isDecisionMade(), is(true));
    assertThat(jointDecision.getFinalDecision(), is(APPROVED)); // Approval overrides decline by definition
  }

  @Test
  public void should_make_decision_when_no_more_chamnce_for_approval() {

    JointDecision jointDecision = JointDecision.of(4, 2);
    jointDecision.add(RESUBMISSION);
    assertThat(jointDecision.getApprovedCount(), is(0L));
    assertThat(jointDecision.getDeclinedCount(), is(0L));
    assertThat(jointDecision.getResubmissionCount(), is(1L));
    assertThat(jointDecision.isDecisionMade(), is(false));
    assertThat(jointDecision.getFinalDecision(), nullValue());

    jointDecision.add(DECLINED);
    assertThat(jointDecision.getApprovedCount(), is(0L));
    assertThat(jointDecision.getDeclinedCount(), is(1L));
    assertThat(jointDecision.getResubmissionCount(), is(1L));
    assertThat(jointDecision.isDecisionMade(), is(false));
    assertThat(jointDecision.getFinalDecision(), nullValue());

    jointDecision.add(RESUBMISSION);
    assertThat(jointDecision.getApprovedCount(), is(0L));
    assertThat(jointDecision.getDeclinedCount(), is(1L));
    assertThat(jointDecision.getResubmissionCount(), is(2L));
    assertThat(jointDecision.isDecisionMade(), is(true));
    assertThat(jointDecision.getFinalDecision(), is(RESUBMISSION)); // no more chance for approval

    jointDecision.add(DECLINED);
    assertThat(jointDecision.getApprovedCount(), is(0L));
    assertThat(jointDecision.getDeclinedCount(), is(2L));
    assertThat(jointDecision.getResubmissionCount(), is(2L));
    assertThat(jointDecision.isDecisionMade(), is(true));
    assertThat(jointDecision.getFinalDecision(), is(RESUBMISSION)); // Resubmission overrides decline by definition

  }

  private void assertNoVotesYet(JointDecision jointDecision) {
    assertThat(jointDecision.getApprovedCount(), is(0L));
    assertThat(jointDecision.getDeclinedCount(), is(0L));
    assertThat(jointDecision.getResubmissionCount(), is(0L));
    assertThat(jointDecision.getVotesCount(), is(0L));
    assertThat(jointDecision.getOutstandingVotesCount(), is(jointDecision.getVoters()));
    assertThat(jointDecision.isDecisionMade(), is(false));
    assertThat(jointDecision.getFinalDecision(), nullValue());

  }
}
