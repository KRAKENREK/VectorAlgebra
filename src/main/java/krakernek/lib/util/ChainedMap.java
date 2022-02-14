package krakernek.lib.util;

import java.util.HashMap;
import java.util.Map;

public class ChainedMap<A, B> extends HashMap<A, B> implements Map<A, B> {

    public ChainedMap<A, B> putC(A key, B value) {
        super.put(key, value);
        return this;
    }
}
