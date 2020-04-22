package com.aaroncarlson.controllers;

import com.aaroncarlson.models.Speaker;
import com.aaroncarlson.repositories.SpeakerRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakerController {

    @Autowired
    private SpeakerRepository speakerRepository;

    @GetMapping
    public List<Speaker> getSpeakers() {
        return speakerRepository.findAll();
    }

    @GetMapping("/{speakerId}")
    public Speaker getSpeakerById(@PathVariable Long speakerId) {
        return speakerRepository.getOne(speakerId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Speaker createSpeaker(@Valid @RequestBody final Speaker speaker) {
        return speakerRepository.saveAndFlush(speaker);
    }

    @PutMapping("/{speakerId}")
    public Speaker updateSpeakerById(@PathVariable Long speakerId,
                                     @Valid @RequestBody Speaker speaker) {
        // Because this is a PUT, we expect all attributes to be passed in. A PATCH would only update attributes provided
        // TODO: Add validation that all attributes are passed in, otherwise return a 400 bad payload
        Speaker existingSpeaker = speakerRepository.getOne(speakerId);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakerRepository.saveAndFlush(existingSpeaker);
    }

    @DeleteMapping("/{speakerId}")
    public void deleteSpeakerById(@PathVariable Long speakerId) {
        // Also need to check for children records before deleting
        speakerRepository.deleteById(speakerId);
    }

}
