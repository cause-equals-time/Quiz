package sample;


import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class UserInterface {

    QuestionSet quiz;
    Logger log;
    Scanner sc;

    public UserInterface(){
        sc = new Scanner(System.in);
    }

    public void run() {
        boolean quit = false;
        while (!quit) {
            printMenu();
            switch (sc.nextLine()) {

                case "0":
                    if (this.quiz!=null){
                        System.out.println("\nWelcome to the Quiz" + this.quiz + "\n" +
                                "You may be given different amounts of time to answer a given question" + "\n\n" +
                                "Enter 0 to start or anything else to go back\n");
                        if (sc.nextLine().equals("0")){
                            startQuiz();
                        }
                    }
                    break;
                case "1":
                    System.out.println("\nPlease indicate what file Quiz you intend to use:\n");
                    this.quiz = new QuestionSet(sc.nextLine());
                    break;
                case "2":
                    System.out.println("\nPlease specify where you wish to save the logs:\n");
                    this.log = new Logger(sc.nextLine());
                    break;
                case "3":
                    quit = true;
                    break;
                default:
                    System.out.println("\nInvalid option\n");
            }
        }
    }


    private void startQuiz() {

        if (this.log==null) this.log = new Logger();
        while (this.quiz.hasQuestion()){

            System.out.println("\nn - next | p - previous | f - first | l - last | >x - question x");
            System.out.println(quiz.getCurrent() + " (" + quiz.getCurrent().getTime() + " seconds to answer)");
            Timer t = new Timer();
            if (quiz.getCurrent().getTime()>0) {
                t.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        quiz.getCurrent().decreaseTime();
                        //System.out.println(quiz.getCurrent().getTime());
                        if (quiz.getCurrent().getTime() <= 0) {
                            t.cancel();
                            //Wanted to emulate an ENTER keypress here, checked several options, Robot class and whatnot but I could not make it work, and as such even though the timer elapses the app does not jump to the next question because it is stuck waiting for user input. There must be a way around this, with multi threading I imagine, but I still don't know how would it work in a way that not only would somehow the ENTER keydown take place but also the current time on the timer displayed on the screen without messing with the user input, since we have only one active line. There must be a way to have a separate thread dealing with the user input but still, we would have only one console right?
                        }
                    }
                }, 0, 1000);
            }
            System.out.println("\nAnswer: ");
            String answer = sc.nextLine();
            switch (answer){
                case "n":
                    if (quiz.getCurrent().getTime()>0) t.cancel();
                    quiz.next();
                    break;
                case "p":
                    if (quiz.getCurrent().getTime()>0) t.cancel();
                    quiz.previous();
                    break;
                case "f":
                    if (quiz.getCurrent().getTime()>0) t.cancel();
                    quiz.first();
                    break;
                case "l":
                    if (quiz.getCurrent().getTime()>0) t.cancel();
                    quiz.last();
                    break;
                case ">":
                    if (quiz.getCurrent().getTime()>0) t.cancel();
                    quiz.index(new Scanner(System.in).nextInt());
                    break;
                default:
                    if (quiz.getCurrent().getTime()>0){
                        t.cancel();
                        log.write(quiz.getCurrent(), answer + " | " + quiz.getCurrent().getTime() + " seconds remaining");
                    }
                    else log.write(quiz.getCurrent(), answer + " | Not answered on time");
                    quiz.pop();
            }
        }
            log.close();

    }


    public void printMenu(){
        System.out.println("\nWelcome to the Quizapp\n");
        if (this.quiz!=null) System.out.println("0 to start the loaded Quiz");
        System.out.println("1 to load a Quiz file");
        System.out.println("2 to set the log file");
        System.out.println("3 to quit\n");
    }


}
