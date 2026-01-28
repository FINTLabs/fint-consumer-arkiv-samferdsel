package no.fint.consumer.models.soknaddrosjeloyve;

import no.fint.consumer.exceptions.EventResponseException;
import no.fint.consumer.utils.RestEndpoints;
import no.fint.event.model.HeaderConstants;
import no.novari.fint.model.resource.arkiv.personal.PersonalmappeResource;
import no.novari.fint.model.resource.arkiv.samferdsel.SoknadDrosjeloyveResource;
import no.fint.relations.FintRelationsMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = RestEndpoints.SOKNADDROSJELOYVE, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class SoknadDrosjeloyveCustomController {
    @Autowired
    private SoknadDrosjeloyveController soknadDrosjeloyveController;

    @GetMapping("/mappeid/{ar}/{sekvensnummer}")
    public SoknadDrosjeloyveResource getSoknadDrosjeloyveByMappeArSekvensnummer(
            @PathVariable String ar,
            @PathVariable String sekvensnummer,
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client) throws InterruptedException {
        return soknadDrosjeloyveController.getSoknadDrosjeloyveByMappeId(ar + "/" + sekvensnummer, orgId, client);
    }

    @PutMapping("/mappeid/{ar}/{sekvensnummer}")
    public ResponseEntity putSoknadDrosjeloyveByMappeArSekvensnummer(
            @PathVariable String ar,
            @PathVariable String sekvensnummer,
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client,
            @RequestBody SoknadDrosjeloyveResource body) {
        return soknadDrosjeloyveController.putSoknadDrosjeloyveByMappeId(ar + "/" + sekvensnummer, orgId, client, body);
    }

    //
    // Exception handlers
    //
    @ExceptionHandler(EventResponseException.class)
    public ResponseEntity handleEventResponseException(EventResponseException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getResponse());
    }
}
