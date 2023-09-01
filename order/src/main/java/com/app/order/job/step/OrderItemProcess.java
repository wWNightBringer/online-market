package com.app.order.job.step;

import com.app.order.job.JobDTO;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class OrderItemProcess implements ItemProcessor<JobDTO, JobDTO> {

    @Override
    public JobDTO process(JobDTO jobDTO) throws Exception {
        return jobDTO;
    }
}
