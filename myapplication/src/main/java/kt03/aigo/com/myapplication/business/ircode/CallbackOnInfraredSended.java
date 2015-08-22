package kt03.aigo.com.myapplication.business.ircode;

/**
 * Created by zhangcirui on 15/8/18.
 */

/**按键发送后回调*/
public  interface CallbackOnInfraredSended
{
    /**按键已发送*/
    public void onInfrardSended();

    public void onLongPress(int position);
}
