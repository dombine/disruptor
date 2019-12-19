package com.demo;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class DataEventDbHandler implements EventHandler<DataEvent>, WorkHandler<DataEvent> {

    @Override
    public void onEvent(DataEvent event) throws Exception {
        long threadId = Thread.currentThread().getId();
        String carLicense = event.getCarLicense();
        System.out.println(String.format("Thread Id %s save %s into db", threadId, carLicense));
    }

    @Override
    public void onEvent(DataEvent event, long sequence, boolean endOfBatch) throws Exception {
        long threadId = Thread.currentThread().getId();
        String carLicense = event.getCarLicense();
        System.out.println(String.format("Thread Id %s save %s into db --- %s", threadId, carLicense, sequence));
    }

}