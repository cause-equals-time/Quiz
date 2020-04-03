package sample;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class QuestionSet {

    private String description;
    private ArrayList<Question> set;
    int currentQuestion;

    public QuestionSet(String fileLocation){
        this.set = new ArrayList<>();
        this.currentQuestion = 0;

        try {
            Scanner sc = new Scanner(new FileReader(fileLocation));
            String line = sc.nextLine();
            String[] qnt;

            if (!line.contains("?")) this.description = line;
            else {
                this.description = "";
                qnt = line.split(Pattern.quote("?"), 2);
                int time = handleTime(qnt[1]);
                if (time == 0) set.add(new Question(qnt[0]));
                else set.add(new Question(qnt[0], time));
            }

            while (sc.hasNextLine()) {
                qnt = sc.nextLine().split(Pattern.quote("?"), 2);
                int time = handleTime(qnt[1]);
                if (time == 0) set.add(new Question(qnt[0]));
                else set.add(new Question(qnt[0], time));
            }
        } catch (Exception ignored){
            System.out.println("Invalid path or filename\n");
        }
    }

    private int handleTime(String s){
        int time=0;
        if (s!=null){
            s=s.replaceAll("\\s","");
            try{
                time = Integer.parseInt(s);
            } catch (Exception ignored){
            };
        }
        return time;
    }

    public Question getCurrent(){
        return this.set.get(this.currentQuestion);
    }

    public void next(){
        if (isLastQ()) first();
        else this.currentQuestion++;
    }

    public void previous(){
        if (isFirstQ()) last();
        else this.currentQuestion--;
    }

    public void index(int i){
        if (i>=0 && i<this.set.size()) this.currentQuestion=i;
    }

    public void last(){
        this.currentQuestion=this.set.size()-1;
    }

    public void first(){
        this.currentQuestion=0;
    }

    public boolean isFirstQ(){
        return this.currentQuestion==0;
    }

    public boolean isLastQ(){
        return this.currentQuestion>=this.set.size()-1;
    }

    public boolean hasQuestion(){
        return !this.set.isEmpty();
    }

    public void pop(){
        this.set.remove(this.currentQuestion);
        if (this.currentQuestion>=this.set.size()) first(); //in case the last element is popped.
    }

    @Override
    public String toString() {
        return " " + this.description.toLowerCase() + " (" + this.set.size() + " questions)";
    }
}
