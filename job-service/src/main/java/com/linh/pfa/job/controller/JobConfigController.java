package com.linh.pfa.job.controller;

import java.util.List;

import javax.transaction.Transactional;

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
	
	@GetMapping("")
	public ResponseEntity<List<JobConfig>> getJobConfigs() {
		return ResponseEntity.ok(jobConfigRespository.findAll());
	}

	@PostMapping("")
	@Transactional
	public ResponseEntity<JobConfig> create(@RequestBody JobConfig jobConfig) {
		jobConfig.setCreatedBy(0L);
		return ResponseEntity.ok(jobConfigRespository.save(jobConfig));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<JobConfig> update(@PathVariable Long id, @RequestBody JobConfig jobConfig) {
    	JobConfig jobConfigExisting = jobConfigRespository.findById(id).orElse(null);
        if (!jobConfigRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // save original values 
        jobConfig.setCreatedBy(jobConfigExisting.getCreatedBy());
        jobConfig.setCreatedDate(jobConfigExisting.getCreatedDate());
        jobConfig.setIsDeleted(false);
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
}
