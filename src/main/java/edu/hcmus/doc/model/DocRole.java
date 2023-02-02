package edu.hcmus.doc.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DocRole {

  APPROVER("APPROVER"),
  REVIEWER("REVIEWER"),
  SUBMITTER("SUBMITTER");

  public final String value;
}
