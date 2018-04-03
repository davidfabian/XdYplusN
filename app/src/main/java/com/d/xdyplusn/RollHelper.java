package com.d.xdyplusn;

import java.util.Random;

/**
 * Created by d on 4/2/2018.
 */

public class RollHelper {

    Random random = new Random();


    public int xDyPlusN(int x, int y, int n) {
        int dieRolls = 0;
        for (int i = 0; i < x; i++) {
            dieRolls += random.nextInt(y) + 1;
        }
        return dieRolls + n;
    }
}
