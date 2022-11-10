import java.util.Scanner;

public class Main {
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
    private static String convert(long number){
        if(number==0) return "zero";
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
        return output.toString();


    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println(convert(scanner.nextLong()));
    }
}