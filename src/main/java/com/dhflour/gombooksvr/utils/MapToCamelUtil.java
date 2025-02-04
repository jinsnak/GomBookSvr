package com.dhflour.gombooksvr.utils;

import java.util.HashMap;
import com.google.common.base.CaseFormat;

public class MapToCamelUtil extends HashMap {
    private static final long serialVersionUID = 1l;

    public Object put(Object key, Object value) {
        return super.put(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, (String) key) , value);
    }
}
