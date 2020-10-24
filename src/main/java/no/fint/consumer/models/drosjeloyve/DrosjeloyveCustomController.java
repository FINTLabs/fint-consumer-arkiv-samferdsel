package no.fint.consumer.models.drosjeloyve;

import no.fint.consumer.utils.RestEndpoints;
import no.fint.event.model.HeaderConstants;
import no.fint.model.resource.arkiv.samferdsel.DrosjeloyveResource;
import no.fint.relations.FintRelationsMediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = RestEndpoints.DROSJELOYVE, produces = {FintRelationsMediaType.APPLICATION_HAL_JSON_VALUE, MediaType.APPLICATION_JSON_UTF8_VALUE})
public class DrosjeloyveCustomController {
    @Autowired
    private DrosjeloyveController sakController;

    @GetMapping("/mappeid/{ar}/{sekvensnummer}")
    public DrosjeloyveResource getSakByMappeArSekvensnummer(
            @PathVariable String ar,
            @PathVariable String sekvensnummer,
            @RequestHeader(name = HeaderConstants.ORG_ID, required = false) String orgId,
            @RequestHeader(name = HeaderConstants.CLIENT, required = false) String client) throws InterruptedException {
        return sakController.getDrosjeloyveByMappeId(ar + "/" + sekvensnummer, orgId, client);
    }

}