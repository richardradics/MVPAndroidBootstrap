package com.richardradics.commons.util;

import java.text.ParseException;
import java.text.RuleBasedCollator;

/**
 * Created by User on 2014.12.19..
 */
public class HungarianCollation extends RuleBasedCollator {

    /**
     * Constructs a new instance of {@code RuleBasedCollator} using the
     * specified {@code rules}. (See the {@link java.text.RuleBasedCollator class description}.)
     * <p/>
     * Note that the {@code rules} are interpreted as a delta to the
     * default sort order. This differs
     * from other implementations which work with full {@code rules}
     * specifications and may result in different behavior.
     *
     * @param rules the collation rules.
     * @throws NullPointerException     if {@code rules == null}.
     * @throws java.text.ParseException if {@code rules} contains rules with invalid collation rule
     *                                  syntax.
     */
    public HungarianCollation() throws ParseException {
        super(hungarianRules);
    }

    /**
     * Hungarian Collator Rules.
     */
    public static String hungarianRules = ("&9 < a,A < á,Á < b,B < c,C < cs,Cs < d,D < dz,Dz < dzs,Dzs "
            + "< e,E < é,É < f,F < g,G < gy,Gy < h,H < i,I < í,Í < j,J "
            + "< k,K < l,L < ly,Ly < m,M < n,N < ny,Ny < o,O < ó,Ó "
            + "< ö,Ö < õ,Õ < p,P < q,Q < r,R < s,S < sz,Sz < t,T "
            + "< ty,Ty < u,U < ú,Ú < v,V < w,W < x,X < y,Y < z,Z < zs,Zs");


}
