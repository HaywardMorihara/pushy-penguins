/**
 * Copyright (C) 2020 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.json;

/**
 * @author nathaniel.morihara
 */
public class Score {
  private int score;
  private String owner;

  // Empty constructor needed for serialization
  public Score() {}

  public Score(int score, String owner) {
    this.score = score;
    this.owner = owner;
  }

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }
}
