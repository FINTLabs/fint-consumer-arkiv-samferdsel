package no.fint.consumer.models.soknaddrosjeloyve;

import no.fint.model.resource.arkiv.samferdsel.SoknadDrosjeloyveResource;
import no.fint.model.resource.arkiv.samferdsel.SoknadDrosjeloyveResources;
import no.fint.relations.FintLinker;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Objects.isNull;
import static org.springframework.util.StringUtils.isEmpty;

@Component
public class SoknadDrosjeloyveLinker extends FintLinker<SoknadDrosjeloyveResource> {

    public SoknadDrosjeloyveLinker() {
        super(SoknadDrosjeloyveResource.class);
    }

    public void mapLinks(SoknadDrosjeloyveResource resource) {
        super.mapLinks(resource);
    }

    @Override
    public SoknadDrosjeloyveResources toResources(Collection<SoknadDrosjeloyveResource> collection) {
        return toResources(collection.stream(), 0, 0, collection.size());
    }

    @Override
    public SoknadDrosjeloyveResources toResources(Stream<SoknadDrosjeloyveResource> stream, int offset, int size, int totalItems) {
        SoknadDrosjeloyveResources resources = new SoknadDrosjeloyveResources();
        stream.map(this::toResource).forEach(resources::addResource);
        addPagination(resources, offset, size, totalItems);
        return resources;
    }

    @Override
    public String getSelfHref(SoknadDrosjeloyveResource soknaddrosjeloyve) {
        return getAllSelfHrefs(soknaddrosjeloyve).findFirst().orElse(null);
    }

    @Override
    public Stream<String> getAllSelfHrefs(SoknadDrosjeloyveResource soknaddrosjeloyve) {
        Stream.Builder<String> builder = Stream.builder();
        if (!isNull(soknaddrosjeloyve.getMappeId()) && !isEmpty(soknaddrosjeloyve.getMappeId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(soknaddrosjeloyve.getMappeId().getIdentifikatorverdi(), "mappeid"));
        }
        if (!isNull(soknaddrosjeloyve.getSystemId()) && !isEmpty(soknaddrosjeloyve.getSystemId().getIdentifikatorverdi())) {
            builder.add(createHrefWithId(soknaddrosjeloyve.getSystemId().getIdentifikatorverdi(), "systemid"));
        }
        
        return builder.build();
    }

    int[] hashCodes(SoknadDrosjeloyveResource soknaddrosjeloyve) {
        IntStream.Builder builder = IntStream.builder();
        if (!isNull(soknaddrosjeloyve.getMappeId()) && !isEmpty(soknaddrosjeloyve.getMappeId().getIdentifikatorverdi())) {
            builder.add(soknaddrosjeloyve.getMappeId().getIdentifikatorverdi().hashCode());
        }
        if (!isNull(soknaddrosjeloyve.getSystemId()) && !isEmpty(soknaddrosjeloyve.getSystemId().getIdentifikatorverdi())) {
            builder.add(soknaddrosjeloyve.getSystemId().getIdentifikatorverdi().hashCode());
        }
        
        return builder.build().toArray();
    }

}

