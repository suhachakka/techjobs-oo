package org.launchcode.controllers;

import org.launchcode.models.*;
import org.launchcode.models.data.JobFieldData;
import org.launchcode.models.forms.JobForm;
import org.launchcode.models.data.JobData;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by LaunchCode
 */
@Controller
@RequestMapping(value = "job")
public class JobController {

    private JobData jobData = JobData.getInstance();

    // The detail display for a given Job at URLs like /job?id=17
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String index(Model model,int id) {

        // TODO #1 - get the Job with the given ID and pass it into the view
        Job someJob = jobData.findById(id) ;
        model.addAttribute("someJob", someJob);

        return "job-detail";
    }

    @RequestMapping(value = "add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new JobForm());
        return "new-job";
    }

    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(Model model, @ModelAttribute  @Valid JobForm jobForm, Errors errors) {

        // TODO #6 - Validate the JobForm model, and if valid, create a
        // new Job and add it to the jobData data store. Then
        // redirect to the job detail view for the new Job.
        if(errors.hasErrors()) {
            return "new-job";
        }
        Employer anEmployer = jobData.getEmployers().findById(jobForm.getEmployerId());
        Location anLocation = jobData.getLocations().findById(jobForm.getLocationId());
        CoreCompetency anCoreCompetency = jobData.getCoreCompetencies().findById(jobForm.getCoreCompetencyId());
        PositionType anPositionType = jobData.getPositionTypes().findById(jobForm.getPositionTypeId());

        Job newJob = new Job(jobForm.getName(),anEmployer,anLocation,anPositionType,anCoreCompetency);
        jobData.add(newJob);

        return "redirect:/job?id="+newJob.getId();

    }

}
