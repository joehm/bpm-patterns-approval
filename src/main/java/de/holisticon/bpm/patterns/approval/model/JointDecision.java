package de.holisticon.bpm.patterns.approval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

import static de.holisticon.bpm.patterns.approval.model.ApprovalDecision.APPROVED;
import static de.holisticon.bpm.patterns.approval.model.ApprovalDecision.DECLINED;
import static de.holisticon.bpm.patterns.approval.model.ApprovalDecision.RESUBMISSION;

/**
 * @author Jo Ehm (Holisticon)
 */
@Data
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Setter(AccessLevel.NONE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class JointDecision implements Serializable {

  private long voters = 1;
  private long threshold = 1;

  private long approvedCount = 0;
  private long declinedCount = 0;
  private long resubmissionCount = 0;

  private ApprovalDecision finalDecision = null;

  private JointDecision(long voters, long threshold) {
    if (voters < 1) {
      throw new IllegalArgumentException("Number of voters must be at least 1!");
    }
    if (threshold > voters) {
      throw new IllegalArgumentException("Threshold value must not be greater than the number of voters!");
    }
    if (threshold < 1) {
      throw new IllegalArgumentException("Threshold value must be at least 1!");
    }
    this.voters = voters;
    this.threshold = threshold;
  }

  public static JointDecision of(long voters) {
    return new JointDecision(voters, Math.round((voters + 1) * 0.5));
  }

  public static JointDecision of(long voters, long threshold) {
    return new JointDecision(voters, threshold);
  }

  public static JointDecision singleton() {
    return new JointDecision(1, 1);
  }

  public void add(ApprovalDecision decision) {
    switch (decision) {
      case APPROVED:
        approvedCount++;
        break;
      case DECLINED:
        declinedCount++;
        break;
      case RESUBMISSION:
        resubmissionCount++;
    }
    compile();
  }

  long getVotesCount() {
    return approvedCount + declinedCount + resubmissionCount;
  }

  long getOutstandingVotesCount() {
    return voters - getVotesCount();
  }

  boolean isDecisionMade() {
    final long outstandingVotesCount = getOutstandingVotesCount();
    return approvedCount >= threshold  // approval threshold reached
        || outstandingVotesCount == 0  // all votes available
        || approvedCount + outstandingVotesCount < threshold; // no more chance for approval
  }

  private void compile() {
    if (isDecisionMade()) {
      this.finalDecision = approvedCount >= threshold ? APPROVED
          : declinedCount > resubmissionCount ? DECLINED
          : RESUBMISSION;
    }
    else {
      this.finalDecision = null;
    }
  }


}


