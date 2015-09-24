package com.coobud.common.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CommonUtils {

	   public static <V> List<V> mk_list(V... args) {
	        ArrayList<V> rtn = new ArrayList<V>();
	        for (V o : args) {
	            rtn.add(o);
	        }
	        return rtn;
	    }

	    public static <V> List<V> mk_list(java.util.Set<V> args) {
	        ArrayList<V> rtn = new ArrayList<V>();
	        if (args != null) {
	            for (V o : args) {
	                rtn.add(o);
	            }
	        }
	        return rtn;
	    }

	    public static <V> List<V> mk_list(Collection<V> args) {
	        ArrayList<V> rtn = new ArrayList<V>();
	        if (args != null) {
	            for (V o : args) {
	                rtn.add(o);
	            }
	        }
	        return rtn;
	    }

	    public static <V> V[] mk_arr(V... args) {
	        return args;
	    }
}
