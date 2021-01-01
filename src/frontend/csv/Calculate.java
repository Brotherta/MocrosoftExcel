package frontend.csv;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculate {

    public static double min(double[] doubleList){
        double min = doubleList[0];
        for (int i=1; i<doubleList.length; i++){
            if (doubleList[i]<min){
                min = doubleList[i];
            }
        }
        return min;
    }

    public static double max(double[] doubleList){
        double max = doubleList[0];
        for (int i=1; i<doubleList.length; i++){
            if (doubleList[i]>max){
                max = doubleList[i];
            }
        }
        return max;
    }

    public static double average(double[] doubleList){
        double average = 0;
        for (int i=0; i<doubleList.length; i++){
            average += doubleList[i];
        }
        return average/doubleList.length;
    }

    public static double standartDeviation(double[] doubleList){
        double average = average(doubleList);
        double sD = 0;
        for (int i=0; i<doubleList.length; i++){
            sD += Math.pow(doubleList[i],2)-Math.pow(average,2);
        }
        sD = Math.sqrt(sD/doubleList.length);
        return sD;
    }


    public static double roundDouble(double value, int places){
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

}
