package com.arpaul.avantari_qs2.dataobjects;

import java.util.LinkedHashMap;

/**
 * Created by Aritra on 15-08-2016.
 */
public class ParserDO extends BaseDO {

    public LinkedHashMap<ParseType, Object> linkHash = new LinkedHashMap<>();

    public enum ParseType {
        TYPE_DCTIONARY_DATA,
    }
}
