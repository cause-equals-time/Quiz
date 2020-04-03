package sample;

import java.io.FileWriter;

public class Logger {

    FileWriter log;
    private int i;

    public Logger() {
        this.log=null;
        this.i=1;
        try{
            this.log = new FileWriter("quizlog");
        } catch (Exception ignored){
            System.out.println("Invalid path or filename");
        }
    }

    public Logger(String fileLocation) {

        this.log=null;
        this.i=1;
        try {
            this.log = new FileWriter(fileLocation);
        } catch (Exception ignored) {
            System.out.println("Invalid path or filename");
        }
    }

    public void write(Question q, String answer){

        try{
            log.write("Q" + i++ + ": " + q + " " + answer + "\n");
        } catch (Exception ignored){
            System.out.println("Cannot write to the file");
        }

    }

    public void close(){

        try{
            log.close();
        } catch (Exception e){
            System.out.println("Unable to close file");
        }
    }
    }
