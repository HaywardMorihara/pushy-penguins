/**
 * Copyright (C) 2020 Urban Compass, Inc.
 */
package com.nathanielmorihara.pushypenguins.io;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.nathanielmorihara.pushypenguins.json.Score;

/**
 * @author nathaniel.morihara
 */
public class HighScoreFileHandler {

  private final String HIGH_SCORE_FILE_PATH = "data/high_scores.json";
  private final int MAX_NUMBER_OF_HIGH_SCORES = 10;

  private FileHandle highScoreFileHandle;

  public HighScoreFileHandler() {
    this.highScoreFileHandle = Gdx.files.local(HIGH_SCORE_FILE_PATH);
  }

  public boolean existsHighScoreFile() {
    return this.highScoreFileHandle.exists();
  }

  public void initializeEmptyScores() {
    List<Score> emptyScores = new ArrayList();
    for (int i = 0; i < MAX_NUMBER_OF_HIGH_SCORES; i++) {
      Score emptyScore = new Score(0, "Murphy");
      emptyScores.add(emptyScore);
    }
    writeHighScores(emptyScores);
  }

  public List<Score> readHighScores() {
    Json json = new Json();
    return json.fromJson(List.class, Gdx.files.local(HIGH_SCORE_FILE_PATH));

  }

  public boolean isNewHighScore(int score) {
    List<Score> highScores = readHighScores();
    return score > highScores.get(MAX_NUMBER_OF_HIGH_SCORES - 1).getScore();
  }

  public void writeNewHighScore(Score newHighScore) {
    List<Score> prevHighScores = readHighScores();
    List<Score> newHighScores = new ArrayList();
    boolean scoresHasBeenAdded = false;
    for (int i = 0; i < MAX_NUMBER_OF_HIGH_SCORES - 1; i++) {
      Score prevScore = prevHighScores.get(i);
      if (!scoresHasBeenAdded && newHighScore.getScore() >= prevScore.getScore()) {
        newHighScores.add(newHighScore);
        scoresHasBeenAdded = true;
      }
      newHighScores.add(prevScore);
    }
    writeHighScores(newHighScores);
  }


  private void writeHighScores(List<Score> highScores) {
    Json json = new Json();
    this.highScoreFileHandle.writeString(json.toJson(highScores), false);
  }
}
