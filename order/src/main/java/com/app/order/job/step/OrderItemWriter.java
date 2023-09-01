package com.app.order.job.step;

import com.app.order.job.JobDTO;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@Component
public class OrderItemWriter implements ItemWriter<JobDTO> {
    @Override
    public void write(Chunk<? extends JobDTO> chunk) throws Exception {

    }
}
