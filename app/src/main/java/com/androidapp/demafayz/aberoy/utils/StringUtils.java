package com.androidapp.demafayz.aberoy.utils;

import java.util.List;

/**
 * Created by DemaFayz on 27.11.2016.
 */

public class StringUtils {

    public static String stringTransfer(List<String> texts) {
        String result = null;
        for (int i = 0; i < texts.size(); i++) {
            String text = texts.get(i);
            if (i == 0) {
                result = texts.get(i);
            } else if (i == texts.size() - 1) {
                result += " и " + texts.get(i);
            } else {
                result += ", " + texts.get(i);
            }
        }
        return result;
    }
}
