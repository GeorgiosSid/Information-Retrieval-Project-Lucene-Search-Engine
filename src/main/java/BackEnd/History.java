package BackEnd;

import org.apache.commons.io.input.ReversedLinesFileReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class History {
    private  ArrayList<String> recommend;
    private String historyPath="C://Programming//Lucene//Project_4674_4789//history.txt";

    public History() throws IOException {
        this.recommend=new ArrayList<String>();
        createHistory();
        extendedHistory();
    }

    public  void createHistory() throws IOException {
        File file = new File(this.historyPath);

        if (!file.exists()) file.createNewFile();
    }

    public void extendedHistory() throws IOException {
        ReversedLinesFileReader fileReader = new ReversedLinesFileReader(new File(this.historyPath));
        String line;
        this.recommend.clear();

        do {
            line = fileReader.readLine();

            if(line==null)break;

            String[] words=line.split("\\s+");
            words= Arrays.copyOf(words, words.length - 2);

            if(words.length==10){
                this.recommend.add(words[1]);
                this.recommend.add(words[5]);
            }

            if(words.length==6) this.recommend.add(words[1]);

            if(words.length==7 && !words[1].equalsIgnoreCase("prefix") && !words[1].equalsIgnoreCase("fuzzy")){
                this.recommend.add(words[1]);
                this.recommend.add(words[2]);
            }

            if(words.length==7 && (words[1].equalsIgnoreCase("prefix") || words[1].equalsIgnoreCase("fuzzy")))this.recommend.add(words[2]);

        } while(true);

        fileReader.close();
    }

    public void writeHistory(String lastSearched) throws IOException {
        FileWriter fileWriter = new FileWriter("history.txt",true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        printWriter.print(lastSearched+" "+dtf.format(now) + "\n");

        printWriter.close();
    }

    public void addHistory(String lastSearched) throws IOException {
        writeHistory(lastSearched);
        extendedHistory();
    }

    public void clearHistory() throws IOException {
        FileWriter fileWriter=new FileWriter(new File(this.historyPath));
        fileWriter.write("");
        fileWriter.close();
    }

    public  String searchedField(int number){
        if(number==1)return "text";
        if(number==2)return "title";
        return " ";
    }

    public  String showField(int number){
        if(number==1)return "artist";
        if(number==2)return "link";
        if(number==3)return "title";
        return " ";
    }

    public  String showRelop(int number){
        if(number==0)return " %not% ";
        if(number==1)return " %and% ";
        if(number==2)return " %or% ";
        return " ";
    }

    public  ArrayList<String> getRecommendQuery(){
        ArrayList<String> temp = new ArrayList<String>();
        Random rand= new Random();

        if(this.recommend.size()==0)return  temp;

        for(int index1=0;index1<this.recommend.size();index1++)
            if(this.recommend.get(index1).contains("*"))this.recommend.set(index1,this.recommend.get(index1).replace(Character.toString('*'),""));

        for(int index=0;index<5;index++) {
            int randomQuery = rand.nextInt(6) + 1;
            int randomTerm = rand.nextInt(this.recommend.size());
            int randomSearchedField = (int) (Math.random() * 2) + 1;
            int randomShowField = (int) (Math.random() * 3) + 1;

            if (randomQuery == 1) temp.add("search " + this.recommend.get(randomTerm) + " on " + searchedField(randomSearchedField) + " show " + showField(randomShowField));

            if (randomQuery == 2) temp.add("search " + this.recommend.get(randomTerm) + "* on " + searchedField(randomSearchedField) + " show " + showField(randomShowField));

            if (randomQuery == 3) temp.add("search prefix " + this.recommend.get(randomTerm) + " on " + searchedField(randomSearchedField) + " show " + showField(randomShowField));

            if (randomQuery == 4) temp.add("search fuzzy " + this.recommend.get(randomTerm) + " on " + searchedField(randomSearchedField) + " show " + showField(randomShowField));

            if (randomQuery == 5) {
                int counter=10;
                int secondRandomTerm=randomTerm;
                int secondRandomField=randomSearchedField;

                while (secondRandomTerm==randomTerm){
                    secondRandomTerm=rand.nextInt(this.recommend.size());
                    if(counter==0)break;
                    counter--;
                }

                counter=10;

                while (secondRandomField==randomSearchedField){
                    secondRandomField=(int) (Math.random() * 2) + 1;
                    if(counter==0)break;
                    counter--;
                }

                temp.add("search " + this.recommend.get(randomTerm) + " on " +
                        searchedField(randomSearchedField) + showRelop(rand.nextInt(3)) + this.recommend.get(secondRandomTerm) + " on " + searchedField(secondRandomField)
                        + " show " + showField(randomShowField));
            }

            if (randomQuery == 6){
                int counter=10;
                int secondRandomTerm=randomTerm;

                while (secondRandomTerm==randomTerm){
                    secondRandomTerm=rand.nextInt(this.recommend.size());
                    if(counter==0)break;
                    counter--;
                }

                temp.add("search " + this.recommend.get(randomTerm) + " "
                        + this.recommend.get(secondRandomTerm) + " on " + searchedField((int) (Math.random() * 2) + 1)
                        + " show " + showField(randomShowField));
            }
        }
        return temp;
    }
}