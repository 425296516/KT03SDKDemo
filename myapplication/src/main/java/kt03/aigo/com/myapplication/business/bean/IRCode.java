package kt03.aigo.com.myapplication.business.bean;

import android.util.Log;

/**
 *键码对象
 */
public class IRCode {
	private int frequency;//键码频率 也就是键码code中的第一个数据的
	private int[] datas;
	private IRCode irCode;

	public IRCode(int frequency, int[] datas) {
		this.frequency = frequency;
		this.datas = datas;
	}

	public IRCode() {

	}

	public IRCode getIrCode() {
		return irCode;
	}

	public void setIrCode(IRCode irCode) {
		this.irCode = irCode;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void setDatas(int[] datas) {
		this.datas = datas;
	}

	public int getFrequency() {
		return frequency;
	}

	public int[] getDatas() {
		return datas;
	}

	@Override
	public String toString() {
		if (isValid()) {
			StringBuilder ret = new StringBuilder();
			ret.append(frequency).append(",");

			for (int index = 0; index < datas.length; index++) {
				ret.append(datas[index]);
				if (index + 1 != datas.length) {
					ret.append(",");
				}
			}
			return ret.toString();
		} else {
			return "";
		}
	}

	public boolean isValid() {
		if (frequency < 0 || frequency > 600000)
			return false;
		if (datas == null) {
			return false;
		}
		if (datas.length < 2) {
			return false;
		}
		for (int d : datas) {
			if (d <= 4) {
				return false;
			}
		}

		return true;
	}

	public IRCode(String code) {
		if (code != null) {
			String[] codeStrs = code.split(",");
			int freq = 0;
			int n;
			int[] ds = null;
			try {
				freq = Integer.parseInt(codeStrs[0]);
				int len = codeStrs.length - 1;
				int odd = len % 2;
				int m = 0;
				if (odd == 1) {
					len++;
					m = 1;
				}
				ds = new int[len];

				for (n = 1; n < codeStrs.length; n++) {
					ds[(n - 1)] = Integer.parseInt(codeStrs[n]);
                    Log.d("code=",ds[n-1]+"");
				}
				if (m != 0)
					ds[(len - 1)] = 254;
			} catch (Exception localException1) {
				localException1.printStackTrace();

			}
			this.frequency = freq;
			this.datas = ds;
		}
	}
}
