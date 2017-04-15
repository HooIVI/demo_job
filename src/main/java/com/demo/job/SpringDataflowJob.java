package com.demo.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.demo.entity.Foo;
import com.demo.process.FooRepository;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class SpringDataflowJob implements DataflowJob<Foo> {

    Logger logger = LogManager.getLogger(SpringDataflowJob.class);

    @Resource
    private FooRepository fooRepository;

    @Override
    public List<Foo> fetchData(final ShardingContext shardingContext) {
        logger.info(String.format(
                "Item: %s | Time: %s | Thread: %s | %s", shardingContext
                        .getShardingItem(), new SimpleDateFormat("HH:mm:ss")
                        .format(new Date()), Thread.currentThread().getId(),
                "DATAFLOW FETCH"));
        return fooRepository.findTodoData(
                shardingContext.getShardingParameter(), 10);
    }

    @Override
    public void processData(final ShardingContext shardingContext,
                            final List<Foo> data) {
        logger.info(String.format(
                "Item: %s | Time: %s | Thread: %s | %s", shardingContext
                        .getShardingItem(), new SimpleDateFormat("HH:mm:ss")
                        .format(new Date()), Thread.currentThread().getId(),
                "DATAFLOW PROCESS"));
        for (Foo each : data) {
            fooRepository.setCompleted(each.getId());
        }
    }
}
