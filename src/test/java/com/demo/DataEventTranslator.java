package com.demo;

import com.lmax.disruptor.EventTranslator;

public class DataEventTranslator implements EventTranslator<DataEvent> {

    @Override
    public void translateTo(DataEvent event, long sequence) {
        long threadId = Thread.currentThread().getId();
        int num = (int) (Math.random() * 8000);
        num = num + 1000;
        event.setCarLicense("京Z" + num);
        System.out.println();
        System.out.println(String.format("Thread Id %s prod %s --- %s", threadId, event.getCarLicense(), sequence));
    }
}
