package krakernek;

import krakernek.lib.DimData;
import krakernek.lib.point.Point;
import krakernek.lib.util.ChainedMap;
import krakernek.lib.vector.Vector;

import javax.swing.*;
import java.math.BigDecimal;
import java.util.HashMap;

public class Run {

    public static void main(String[] args) {


        System.out.println(
            new Vector(Point.ZERO, new DimData(new ChainedMap<Integer, BigDecimal>().putC(1, BigDecimal.valueOf(0)).putC(2, BigDecimal.valueOf(2))))
        );

    }

}
