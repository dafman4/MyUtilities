package com.squedgy.utilities.maps;

import java.util.Map;
import java.util.HashMap;;

public abstract class MapUtils {

    public static <T1, T2> Map<T1, T2> of(
        T1 a, T2 b, 
        T1 c, T2 d,
        T1 e, T2 f,
        T1 g, T2 h,
        T1 i, T2 j
    ) {
        Map<T1, T2> map = new HashMap<>();
        map.put(a, b);
        map.put(c, d);
        map.put(e, f);
        map.put(g, h);
        map.put(i, j);
        return map;
    }

}