package com.baeldung.lsd.service;

import java.io.IOError;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.model.Task;
import com.baeldung.lsd.persistence.model.TaskStatus;
import com.baeldung.lsd.persistence.repository.CampaignRepository;
import com.baeldung.lsd.persistence.repository.TaskRepository;

@Service
public class CampaignService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CampaignRepository campaignRepository;

    @Transactional(noRollbackFor = IOError.class)
    public void endCampaign(Campaign campaign) {
        // check if there are tasks in progress
        Set<Task> unfinishedTasks = campaign.getTasks()
            .stream()
            .filter(t -> !t.getStatus()
                .equals(TaskStatus.DONE))
            .collect(Collectors.toSet());

        if (!unfinishedTasks.isEmpty()) {
            // create continuation Campaign
            Campaign continuationCampaign = new Campaign(
                    campaign.getCode()
                .concat("-CONT"),
                campaign.getName()
                    .concat(" - Cont"),
                campaign.getDescription());
            campaignRepository.save(continuationCampaign);

            // update Task references
            unfinishedTasks.forEach(t -> t.setCampaign(continuationCampaign));
            taskRepository.saveAll(unfinishedTasks);

            writeToExternalLog();
        }
    }

    private void writeToExternalLog() {
        throw new IOError(null);
    }

}
