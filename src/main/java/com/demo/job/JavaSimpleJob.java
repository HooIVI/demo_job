package com.demo.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.demo.entity.Foo;
import com.demo.process.FooRepository;
import com.demo.process.FooRepositoryFactory;

public class JavaSimpleJob implements SimpleJob {

	Logger logger = LogManager.getLogger(JavaSimpleJob.class);

	private FooRepository fooRepository = FooRepositoryFactory
			.getFooRepository();

	@Override
	public void execute(final ShardingContext shardingContext) {
		logger.info(String.format("Item: %s | Time: %s | Thread: %s | %s",
				shardingContext.getShardingItem(), new SimpleDateFormat(
						"HH:mm:ss").format(new Date()), Thread.currentThread()
						.getId(), "SIMPLE"));
		List<Foo> data = fooRepository.findTodoData(
				shardingContext.getShardingParameter(), 10);
		for (Foo each : data) {
			fooRepository.setCompleted(each.getId());
		}
	}
}
