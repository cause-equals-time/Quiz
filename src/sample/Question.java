package sample;

public class Question {

    private String text;
    private int time;

    public Question(String text) {
        this.text = text;
        this.time=10;
    }

    public Question(String text, int time) {
        this.text = text;
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void decreaseTime(){
        this.time--;
    }

    @Override
    public String toString() {
        return this.text + "?";
    }
}
