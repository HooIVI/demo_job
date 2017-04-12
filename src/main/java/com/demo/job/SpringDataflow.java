package com.demo.job;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.demo.entity.Foo;
import com.demo.process.FooRepository;

public class SpringDataflow implements DataflowJob<Foo> {

	@Resource
	private FooRepository fooRepository;

	@Override
	public List<Foo> fetchData(final ShardingContext shardingContext) {
		System.out.println(String.format(
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
		System.out.println(String.format(
				"Item: %s | Time: %s | Thread: %s | %s", shardingContext
						.getShardingItem(), new SimpleDateFormat("HH:mm:ss")
						.format(new Date()), Thread.currentThread().getId(),
				"DATAFLOW PROCESS"));
		for (Foo each : data) {
			fooRepository.setCompleted(each.getId());
		}
	}
}
