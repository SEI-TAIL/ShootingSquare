package shooting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Score {
	private int score = 0;
	private int highScore = 0;

	void add(int point) {
		this.score += point;
	}
	void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	int getScore() {
		return this.score;
	}
	int getHighScore() {
		return this.highScore;
	}

	public void readHighScore(String fileName) {
		try {
			File file = new File(fileName);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);

			String tmp_str = br.readLine();
			System.out.println(tmp_str);
			try {
				this.highScore = Integer.parseInt(tmp_str);
			} catch (NumberFormatException e) {
				this.highScore = 0;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void writeHighScore(String fileName) {
		System.out.println(score + " " + highScore);
		if (score > highScore) {
			try {
				FileWriter fileWr = new FileWriter(fileName);
				fileWr.write(Integer.toString(score));
				fileWr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
