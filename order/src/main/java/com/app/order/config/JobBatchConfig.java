package com.app.order.config;

import com.app.order.job.JobDTO;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.TaskExecutorJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class JobBatchConfig {

    @Bean
    public Job job(JobRepository jobRepository, MeterRegistry meterRegistry, @Qualifier("orderStep") Step step) {
        return new JobBuilder("Order job", jobRepository)
            .preventRestart()
            .start(step)
            .meterRegistry(meterRegistry)
            .build();
    }

    @Bean("orderStep")
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager transactionManager,
                     ItemReader<JobDTO> itemReader,
                     ItemProcessor<JobDTO, JobDTO> itemProcessor,
                     ItemWriter<JobDTO> itemWriter) {

        return new StepBuilder("Order step", jobRepository)
            .<JobDTO, JobDTO>chunk(3, transactionManager)
            .reader(itemReader)
            .processor(itemProcessor)
            .writer(itemWriter)
            .build();
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new ResourcelessTransactionManager();
    }

    @Bean
    public JobRepository jobRepository(DataSource dataSource, PlatformTransactionManager transactionManager)
        throws Exception {
        JobRepositoryFactoryBean factoryBean = new JobRepositoryFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setTransactionManager(transactionManager);
        factoryBean.afterPropertiesSet();
        return factoryBean.getObject();
    }

    @Bean
    public JobLauncher jobLauncher(JobRepository jobRepository) throws Exception {
        TaskExecutorJobLauncher jobLauncher = new TaskExecutorJobLauncher();
        jobLauncher.setJobRepository(jobRepository);
        jobLauncher.afterPropertiesSet();
        return jobLauncher;
    }
}
