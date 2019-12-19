package com.demo;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.EventHandlerGroup;
import com.lmax.disruptor.dsl.ProducerType;
import org.junit.Test;

public class TestDisruptor {

    @Test
    public void testDisruptor() throws InterruptedException {
        long beginTime = System.currentTimeMillis();

        // ringbuffer大小，必须是2的n次方
        int bufferSize = 1024;

        //使用默认线程池
        ThreadFactory factory = Executors.defaultThreadFactory();

        //构造缓冲区与事件生成
        Disruptor<DataEvent> disruptor = new Disruptor<>(
            new DataEventFactory(),
            bufferSize,
            factory,
            ProducerType.SINGLE,
            new YieldingWaitStrategy()
        );

        //使用disruptor创建消费者组 C1,C2
        EventHandlerGroup<DataEvent> handlerGroup = disruptor.handleEventsWith(new DataEventKafkaHandler(), new DataEventDbHandler());

        DataEventSmsHandler smsHandler = new DataEventSmsHandler();
        //建立依赖关系，声明在C1,C2之后执行C3
        handlerGroup.then(smsHandler);

        //启动
        disruptor.start();

        CountDownLatch latch = new CountDownLatch(1);
        //生产者准备
        Thread t = factory.newThread(new DataEventPublisher(latch, disruptor));
        t.start();

        latch.await();//等待生产者结束
        disruptor.shutdown();

        System.out.println("总耗时:" + (System.currentTimeMillis() - beginTime));
    }
}
