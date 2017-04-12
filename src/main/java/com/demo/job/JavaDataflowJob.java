package com.demo.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.demo.entity.Foo;
import com.demo.process.FooRepository;
import com.demo.process.FooRepositoryFactory;

public class JavaDataflowJob implements DataflowJob<Foo> {

	Logger logger = LogManager.getLogger(JavaDataflowJob.class);

	private FooRepository fooRepository = FooRepositoryFactory
			.getFooRepository();

	@Override
	public List<Foo> fetchData(final ShardingContext shardingContext) {
		logger.info(String.format("Item: %s | Time: %s | Thread: %s | %s",
				shardingContext.getShardingItem(), new SimpleDateFormat(
						"HH:mm:ss").format(new Date()), Thread.currentThread()
						.getId(), "DATAFLOW FETCH"));
		return fooRepository.findTodoData(
				shardingContext.getShardingParameter(), 10);
	}

	@Override
	public void processData(final ShardingContext shardingContext,
			final List<Foo> data) {
		logger.info(String.format("Item: %s | Time: %s | Thread: %s | %s",
				shardingContext.getShardingItem(), new SimpleDateFormat(
						"HH:mm:ss").format(new Date()), Thread.currentThread()
						.getId(), "DATAFLOW PROCESS"));
		for (Foo each : data) {
			fooRepository.setCompleted(each.getId());
		}
	}
}
