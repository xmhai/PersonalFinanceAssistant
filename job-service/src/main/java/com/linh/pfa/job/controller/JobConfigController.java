package com.linh.pfa.job.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.linh.pfa.job.config.JobConfig;
import com.linh.pfa.job.config.JobConfigRepository;

@Controller
@RequestMapping("/job/configs")
public class JobConfigController {
	@Autowired
	private JobConfigRepository jobConfigRespository;
	@Autowired
    private Scheduler scheduler;
	
	@GetMapping("")
	public ResponseEntity<List<JobConfig>> getJobConfigs() {
		return ResponseEntity.ok(jobConfigRespository.findAll());
	}

	@PostMapping("")
	@Transactional
	public ResponseEntity<JobConfig> create(@RequestBody JobConfig jobConfig) {
		return ResponseEntity.ok(jobConfigRespository.save(jobConfig));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<JobConfig> update(@PathVariable Long id, @RequestBody JobConfig jobConfig) {
        if (!jobConfigRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(jobConfigRespository.save(jobConfig));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	JobConfig jobConfig = jobConfigRespository.findById(id).orElse(null);
        if (jobConfig == null) {
            return ResponseEntity.badRequest().build();
        }

        jobConfigRespository.delete(jobConfig);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/run/{id}")
	@Transactional
    public ResponseEntity<JobConfig> trigger(@PathVariable Long id) throws Exception {
    	JobConfig jobConfig = jobConfigRespository.findById(id).orElse(null);
        if (jobConfig == null) {
            return ResponseEntity.badRequest().build();
        }

        JobKey jobKey = JobKey.jobKey(jobConfig.getName(), "mygroup");
        scheduler.triggerJob(jobKey);
        return ResponseEntity.ok().build();
    }

}
