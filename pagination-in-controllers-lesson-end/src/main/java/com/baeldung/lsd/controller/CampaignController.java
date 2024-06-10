package com.baeldung.lsd.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.lsd.persistence.model.Campaign;
import com.baeldung.lsd.persistence.repository.CampaignRepository;

@RestController
@RequestMapping("/campaigns")
public class CampaignController {

    @Autowired
    private CampaignRepository campaignRepository;

    @GetMapping
    public Iterable<Campaign> list() {
        return campaignRepository.findAll();
    }
}
