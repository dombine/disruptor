package com.demo;

import com.lmax.disruptor.EventHandler;

public class DataEventSmsHandler implements EventHandler<DataEvent> {

    @Override
    public void onEvent(DataEvent event, long sequence, boolean endOfBatch) throws Exception {
        long threadId = Thread.currentThread().getId();
        String carLicense = event.getCarLicense();
        System.out.println(String.format("Thread Id %s send %s in plaza sms to user --- %s", threadId, carLicense, sequence));
    }
}
