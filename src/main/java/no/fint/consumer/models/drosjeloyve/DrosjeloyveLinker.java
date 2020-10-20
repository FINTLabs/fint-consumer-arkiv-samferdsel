package no.fint.consumer.models.drosjeloyve;

import no.fint.model.resource.arkiv.samferdsel.DrosjeloyveResource;
import no.fint.model.resource.arkiv.samferdsel.DrosjeloyveResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class DrosjeloyveLinker extends FintLinker<DrosjeloyveResource> {

    public DrosjeloyveLinker() {
        super(DrosjeloyveResource.class);
    }

    public void mapLinks(DrosjeloyveResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public DrosjeloyveResources toResources(Collection<DrosjeloyveResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public DrosjeloyveResources toResources(Stream<DrosjeloyveResource> stream, int offset, int size, int totalItems) {
        DrosjeloyveResources resources = new DrosjeloyveResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(DrosjeloyveResource drosjeloyve) {
        return getAllSelfHrefs(drosjeloyve).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(DrosjeloyveResource drosjeloyve) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(drosjeloyve.getMappeId()) && !isEmpty(drosjeloyve.getMappeId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(drosjeloyve.getMappeId().getIdentifikatorverdi(), "mappeid"));
        }
        if (!isNull(drosjeloyve.getSystemId()) && !isEmpty(drosjeloyve.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(drosjeloyve.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(DrosjeloyveResource drosjeloyve) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(drosjeloyve.getMappeId()) && !isEmpty(drosjeloyve.getMappeId().getIdentifikatorverdi())) {
            builder.add(drosjeloyve.getMappeId().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(drosjeloyve.getSystemId()) && !isEmpty(drosjeloyve.getSystemId().getIdentifikatorverdi())) {
            builder.add(drosjeloyve.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

