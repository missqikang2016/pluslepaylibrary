package com.lechinepay.pluslepay.sdk.listener;

/**
 * Created by Administrator on 2016/8/13 0013.
 */
public interface ChannelsPayListener {

    void OnChannelsPayStart();

    void OnChannelsPayEnd(String result);


}
