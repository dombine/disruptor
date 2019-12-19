package com.demo;

import java.util.concurrent.CountDownLatch;

import com.lmax.disruptor.dsl.Disruptor;

public class DataEventPublisher implements Runnable {

    private Disruptor<DataEvent> disruptor;
    private CountDownLatch latch;
    private static int LOOP = 10; //模拟10车辆入场


    public DataEventPublisher(CountDownLatch latch, Disruptor<DataEvent> disruptor) {
        this.disruptor = disruptor;
        this.latch = latch;
    }

    @Override
    public void run() {
        DataEventTranslator tradeTransloator = new DataEventTranslator();
        for (int i = 0; i < LOOP; i++) {

            // 第一步：先去获取RingBuffer上的一个可用位置，
            // 第二步：在可用位置上发布数据
            disruptor.publishEvent(tradeTransloator);
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        latch.countDown();
        System.out.println("生产者写完" + LOOP + "个消息");
    }

}