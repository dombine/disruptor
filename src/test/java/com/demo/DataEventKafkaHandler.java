package com.demo;

import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.WorkHandler;

public class DataEventKafkaHandler implements EventHandler<DataEvent>, WorkHandler<DataEvent> {

    @Override
    public void onEvent(DataEvent event, long sequence, boolean endOfBatch) throws Exception {

        long threadId = Thread.currentThread().getId();
        String carLicense = event.getCarLicense();
        System.out.println(String.format("Thread Id %s send %s in plaza messsage to kafka --- %s", threadId, carLicense, sequence));
    }

    @Override
    public void onEvent(DataEvent event) throws Exception {
        long threadId = Thread.currentThread().getId();
        String carLicense = event.getCarLicense();
        System.out.println(String.format("Thread Id %s send %s in plaza messsage to kafka", threadId, carLicense));
    }
}