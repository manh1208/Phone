package com.superapp.goinoimangsieukhuyenmaiviettelvinaphonemobifone.Utils;

import java.util.Comparator;

/**
 * Created by ManhNV on 6/2/2016.
 */
public class NumberComparator implements Comparator<String> {


    @Override
    public int compare(String number1, String number2) {
        int result1 = DataUtil.CheckNumber(number1);
        int result = DataUtil.CheckNumber(number2);
        int s = 0;
        if (result == 1) {
            return 1;
        }
        if (result1 == 1) {
           return -1;
        }
        String operation1 = DataUtil.getOperationFromNumber(number1);
        String operation2 = DataUtil.getOperationFromNumber(number2);

        return operation1.toUpperCase().trim().compareToIgnoreCase(operation2.toUpperCase().trim());
    }
}
