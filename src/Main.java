import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import static java.util.Map.entry;

public class Main {
    private static final Map<String,Integer> tens_words = Map.ofEntries(
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
    private static final Map<String,Integer> nums_words = Map.ofEntries(
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
    private static final String []tens = {
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
    private static final String [] nums ={
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
    private static StringBuilder convertUnderOneThousand(int number){
        StringBuilder output = new StringBuilder();
        if(number%100<20){
            output.append(nums[number%100]);
            number/=100;
        }
        else{
            output.append(nums[number%10]);
            number/=10;

            output.insert(0,tens[number%10]);
            number/=10;
        }
        if(number!=0){
            output.insert(0,nums[number]+" hundred");
        }
        return output;

    }
    private static int convertUnderOneThousand(List<String> words){

        int output=0;
        if(words.contains("hundred")){
            output+=nums_words.get(words.get(0))*100;
            words.remove(0);
            words.remove(0);
        }
        if(!words.isEmpty()){
            if(tens_words.containsKey(words.get(0))){
                output+=tens_words.get(words.get(0));
                words.remove(0);
            }
            if(!words.isEmpty()){
                output+=nums_words.get(words.get(0));
            }
        }

        return output;
    }
    private static String convert(long number){
        if(number==0) return "zero";
        boolean isMinus = false;
        if(number<0){
            isMinus = true;
            number*=-1;
        }
        StringBuilder output = new StringBuilder();
        if(number/1000000000!=0){
            output.append(convertUnderOneThousand((int)(number/1000000000)));
            output.append(" billion");
        }
        if(number/1000000!=0){
            output.append(convertUnderOneThousand((int)(number/1000000%1000)));
            output.append(" million");
        }
        if(number/1000!=0){
            output.append(convertUnderOneThousand((int)(number/1000%1000)));
            output.append(" thousand");
        }
        output.append(convertUnderOneThousand((int)(number%1000)));
        output.delete(0,1);//удаляем первый пробел
        if(isMinus) output.insert(0,"minus ");
        return output.toString();


    }
    private static long convert(List<String> words){
        boolean isMinus=false;
        if(words.get(0).equals("minus")){
            words.remove(0);
            isMinus= true;
        }
        long output = 0;
        if(words.contains("billion")){
            output+= 1_000_000_000L *convertUnderOneThousand(words.subList(0,words.indexOf("billion")));
            words.subList(0, words.indexOf("billion") + 1).clear();
        }
        if(words.contains("million")){
            output+= 1_000_000L *convertUnderOneThousand(words.subList(0,words.indexOf("million")));
            words.subList(0, words.indexOf("million") + 1).clear();
        }
        if(words.contains("thousand")){
            output+= 1_000L *convertUnderOneThousand(words.subList(0,words.indexOf("thousand")));
            words.subList(0, words.indexOf("thousand") + 1).clear();
        }
        output+=convertUnderOneThousand(words);


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