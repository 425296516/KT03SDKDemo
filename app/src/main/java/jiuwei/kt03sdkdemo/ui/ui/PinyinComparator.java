package jiuwei.kt03sdkdemo.ui.ui;

import java.util.Comparator;

import jiuwei.kt03sdkdemo.library.bean.Brand;


/**
 * 
 * @author jiangs
 *
 */
public class PinyinComparator implements Comparator<Brand> {

	public int compare(Brand o1, Brand o2) {
		if (o1.getSortLetters().equals("@")
				|| o2.getSortLetters().equals("#")) {
			return -1;
		} else if (o1.getSortLetters().equals("#")
				|| o2.getSortLetters().equals("@")) {
			return 1;
		} else {
			return o1.getSortLetters().compareTo(o2.getSortLetters());
		}
	}
	
	
}
