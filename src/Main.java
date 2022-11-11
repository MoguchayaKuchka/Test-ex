import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Map.entry;

public class Main {
    private static final Map<String,Integer> tensWordsEng = Map.ofEntries(
            entry("ten",10),
            entry("twenty",20),
            entry("thirty",30),
            entry("forty",40),
            entry("fifty",50),
            entry("sixty",60),
            entry("seventy",70),
            entry("eighty",80),
            entry("ninety",90)
    );
    private static final Map<String,Integer> tensWordsRus = Map.ofEntries(
            entry("десять",10),
            entry("двадцать",20),
            entry("тридцать",30),
            entry("сорок",40),
            entry("пятьдесят",50),
            entry("шестьдесят",60),
            entry("семьдесят",70),
            entry("восемьдесят",80),
            entry("девяносто",90)
    );
    private static final Map<String,Integer> numsWordsEng = Map.ofEntries(
            entry("one",1),
            entry("two",2),
            entry("three",3),
            entry("four",4),
            entry("five",5),
            entry("six",6),
            entry("seven",7),
            entry("eight",8),
            entry("nine",9),
            entry("ten",10),
            entry("eleven",11),
            entry("twelve",12),
            entry("thirteen",13),
            entry("fourteen",14),
            entry("fifteen",15),
            entry("sixteen",16),
            entry("seventeen",17),
            entry("eighteen",18),
            entry("nineteen",19)
    );

    private static final Map<String,Integer> numsWordsRus = Map.ofEntries(
            entry("один",1),
            entry("одна",1),
            entry("два",2),
            entry("две",2),
            entry("три",3),
            entry("четыре",4),
            entry("пять",5),
            entry("шесть",6),
            entry("семь",7),
            entry("восемь",8),
            entry("девять",9),
            entry("десять",10),
            entry("одиннадцать",11),
            entry("двенадцать",12),
            entry("тринадцать",13),
            entry("четырнадцать",14),
            entry("пятнадцать",15),
            entry("шестнадцать",16),
            entry("семнадцать",17),
            entry("восемнадцать",18),
            entry("девятнадцать",19)
    );

    private static final Map<String,Integer> hundredsWordsRus = Map.ofEntries(
            entry("сто",100),
            entry("двести",200),
            entry("триста",300),
            entry("четыреста",400),
            entry("пятьсот",500),
            entry("шестьсот",600),
            entry("семьсот",700),
            entry("восемьсот",800),
            entry("девятьсот",900)
    );
    private static final String []tensEng = {
            "",//Для упрощения индексации
            " ten",
            " twenty",
            " thirty",
            " forty",
            " fifty",
            " sixty",
            " seventy",
            " eighty",
            " ninety"
    };
    private static final String [] tensRus = {
            "",
            " десять",
            " двадцать",
            " тридцать",
            " сорок",
            " пятьдесят",
            " шестьдесят",
            " семьдесят",
            " восемьдесят",
            " девяносто"
    };
    private static final String [] numsEng ={
            "",//909 к примеру, мы не должны ничего ставить в разряд десятков
            " one",
            " two",
            " three",
            " four",
            " five",
            " six",
            " seven",
            " eight",
            " nine",
            " ten",
            " eleven",
            " twelve",
            " thirteen",
            " fourteen",
            " fifteen",
            " sixteen",
            " seventeen",
            " eighteen",
            " nineteen"
    };
    private static final String[] numsRus={
            "",
            " один",
            " два",
            " три",
            " четыре",
            " пять",
            " шесть",
            " семь",
            " восемь",
            " девять",
            " десять",
            " одиннадцать",
            " двенадцать",
            " тринадцать",
            " четырнадцать",
            " пятнадцать",
            " шестнадцать",
            " семнадцать",
            " восемнадцать",
            " девятнадцать"
    };
    private static final String[] hundredsRus={
            "",
            " сто",
            " двести",
            " триста",
            " четыреста",
            " пятьсот",
            " шестьсот",
            " семьсот",
            " восемьсот",
            " девятьсот",
    };
    private static List<String> convertUnderOneThousand(int number){
        LinkedList<String> output = new LinkedList<>();

        StringBuilder outputEng = new StringBuilder();
        StringBuilder outputRus = new StringBuilder();
        if(number%100<20){
            outputEng.append(numsEng[number%100]);
            outputRus.append(numsRus[number%100]);

            number/=100;
        }
        else{
            outputEng.append(numsEng[number%10]);
            outputRus.append(numsRus[number%10]);
            number/=10;

            outputEng.insert(0,tensEng[number%10]);
            outputRus.insert(0,tensRus[number%10]);
            number/=10;
        }
        if(number!=0){
            outputEng.insert(0,numsEng[number]+" hundred");
            outputRus.insert(0,hundredsRus[number]);

        }
        output.add(outputEng.toString());
        output.add(outputRus.toString());
        return output;

    }
    private static int convertUnderOneThousand(List<String> words){

        int output=0;
        if(words.contains("hundred")){
            output+=numsWordsEng.get(words.get(0))*100;
            words.remove(0);
            words.remove(0);
        }
        else if(hundredsWordsRus.containsKey(words.get(0))){
            output+=hundredsWordsRus.get(words.get(0));
            words.remove(0);
        }
        if(!words.isEmpty()){
            if(tensWordsEng.containsKey(words.get(0))){
                output+=tensWordsEng.get(words.get(0));
                words.remove(0);
            }
            else if(tensWordsRus.containsKey(words.get(0))){
                output+=tensWordsRus.get(words.get(0));
                words.remove(0);
            }
            if(!words.isEmpty()){
                if(numsWordsEng.containsKey(words.get(0)))
                output+=numsWordsEng.get(words.get(0));
                else{
                    output+=numsWordsRus.get(words.get(0));
                }
            }
        }

        return output;
    }
    private static boolean containsWithEndings(String num,List<String> words){
       return words.stream().anyMatch(s->s.startsWith(num));
    }
    private static String convert(long number){
        if(number==0) return "zero\nноль";

        boolean isMinus = false;
        if(number<0){
            isMinus = true;
            number*=-1;
        }
        StringBuilder outputEng = new StringBuilder();
        StringBuilder outputRus = new StringBuilder();

        long checkBill = number/1000000000;//для проверки окончания
        if(checkBill!=0){
            outputEng.append(convertUnderOneThousand((int)checkBill).get(0));
            outputEng.append(" billion");

            outputRus.append(convertUnderOneThousand((int)checkBill).get(1));
            if(checkBill%100/10==1 ||checkBill%10>4||checkBill%10==0){
                outputRus.append(" миллиардов");
            }
            else if(checkBill%10==1){
                outputRus.append(" миллиард");
            }
            else {
                outputRus.append(" миллиарда");
            }

        }
        long checkMill = number/1000000%1000;
        if(checkMill!=0){
            outputEng.append(convertUnderOneThousand((int)checkMill).get(0));
            outputEng.append(" million");

            outputRus.append(convertUnderOneThousand((int)checkMill).get(1));
            if(checkMill%100/10==1 ||checkMill%10>4||checkMill%10==0){
                outputRus.append(" миллионов");
            }
            else if(checkMill%10==1){
                outputRus.append(" миллион");
            }
            else {
                outputRus.append(" миллиона");
            }
        }
        long checkThousand = number/1000%1000;
        if(checkThousand!=0){

            outputEng.append(convertUnderOneThousand((int)(checkThousand)).get(0));
            outputEng.append(" thousand");

            if(checkThousand%10!=1)
            outputRus.append(convertUnderOneThousand((int)checkThousand).get(1));
            else
                outputRus.append(convertUnderOneThousand((int)checkThousand).get(1).replace("ин","на"));
            if(checkThousand%100/10==1 ||checkThousand%10>4||checkThousand%10==0){
                outputRus.append(" тысяч");
            }
            else if(checkThousand%10==1){
                outputRus.append(" тысяча");
            }
            else {
                outputRus.append(" тысячи");
            }
        }
        outputEng.append(convertUnderOneThousand((int)(number%1000)).get(0));
        outputRus.append(convertUnderOneThousand((int)(number%1000)).get(1));

        outputEng.delete(0,1);
        outputRus.delete(0,1);//удаляем первый пробел

        if(isMinus){
            outputEng.insert(0,"minus ");
            outputRus.insert(0,"минус ");
        }
        return outputEng.append("\n").append(outputRus).toString();


    }
    private static long convert(List<String> words){
        boolean isMinus=false;
        if(words.get(0).equals("minus")||words.get(0).equals("минус")){
            words.remove(0);
            isMinus= true;
        }
        long output = 0;

        if(words.contains("billion")){
            output+= 1_000_000_000L *convertUnderOneThousand(words.subList(0,words.indexOf("billion")));
            words.subList(0, words.indexOf("billion") + 1).clear();
        }
        else if(containsWithEndings("миллиард",words)){
            output+= 1_000_000_000L *convertUnderOneThousand(words.subList(0,words.indexOf(
                    words.stream().filter(s->s.startsWith("миллиард")).findFirst().get()//игнорим get т.к. "миллиард" точно есть
            )));
            words.subList(0, words.indexOf(
                    words.stream().filter(s->s.startsWith("миллиард")).findFirst().get()) + 1).clear();
        }
        if(words.contains("million")){
            output+= 1_000_000L *convertUnderOneThousand(words.subList(0,words.indexOf("million")));
            words.subList(0, words.indexOf("million") + 1).clear();
        }
        else if(containsWithEndings("миллион",words)){
            output+= 1_000_000L *convertUnderOneThousand(words.subList(0,words.indexOf(
                    words.stream().filter(s->s.startsWith("миллион")).findFirst().get()
            )));
            words.subList(0, words.indexOf(
                    words.stream().filter(s->s.startsWith("миллион")).findFirst().get()) + 1).clear();
        }
        if(words.contains("thousand")){
            output+= 1_000L *convertUnderOneThousand(words.subList(0,words.indexOf("thousand")));
            words.subList(0, words.indexOf("thousand") + 1).clear();
        }
        else if(containsWithEndings("тысяч",words)){
            output+= 1_000L *convertUnderOneThousand(words.subList(0,words.indexOf(
                    words.stream().filter(s->s.startsWith("тысяч")).findFirst().get()
            )));
            words.subList(0, words.indexOf(
                    words.stream().filter(s->s.startsWith("тысяч")).findFirst().get()) + 1).clear();
        }
        if(!words.isEmpty()) {
            output += convertUnderOneThousand(words);
        }

        if(isMinus){
            output*=-1;
        }
        return output;
    }
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Enter the number in numeric format or as words: ");
        String num = br.readLine();
        try {
            System.out.println(convert(Long.parseLong(num.replaceAll("\\s+", ""), 10)));
        }
        catch(NumberFormatException ex){
            System.out.printf("%,d%n",convert(new LinkedList<>(Arrays.asList(num.split("\\s+")))));
        }



    }
}