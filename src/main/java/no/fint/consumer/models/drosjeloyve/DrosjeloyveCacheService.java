package no.fint.consumer.models.drosjeloyve;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;
import no.fint.cache.CacheService;
import no.fint.cache.model.CacheObject;
import no.fint.consumer.config.Constants;
import no.fint.consumer.config.ConsumerProps;
import no.fint.consumer.event.ConsumerEventUtil;
import no.fint.event.model.Event;
import no.fint.event.model.ResponseStatus;
import no.fint.model.arkiv.samferdsel.Drosjeloyve;
import no.fint.model.arkiv.samferdsel.SamferdselActions;
import no.fint.model.felles.kompleksedatatyper.Identifikator;
import no.fint.model.resource.arkiv.samferdsel.DrosjeloyveResource;
import no.fint.relations.FintResourceCompatibility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@ConditionalOnProperty(name = "fint.consumer.cache.disabled.drosjeloyve", havingValue = "false", matchIfMissing = true)
public class DrosjeloyveCacheService extends CacheService<DrosjeloyveResource> {

    public static final String MODEL = Drosjeloyve.class.getSimpleName().toLowerCase();

    @Value("${fint.consumer.compatibility.fintresource:true}")
    private boolean checkFintResourceCompatibility;

    @Autowired
    private FintResourceCompatibility fintResourceCompatibility;

    @Autowired
    private ConsumerEventUtil consumerEventUtil;

    @Autowired
    private ConsumerProps props;

    @Autowired
    private DrosjeloyveLinker linker;

    private final JavaType javaType;

    private final ObjectMapper objectMapper;

    public DrosjeloyveCacheService() {
        super(MODEL, SamferdselActions.GET_ALL_DROSJELOYVE, SamferdselActions.UPDATE_DROSJELOYVE);
        objectMapper = new ObjectMapper();
        javaType = objectMapper.getTypeFactory().constructCollectionType(List.class, DrosjeloyveResource.class);
        objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @PostConstruct
    public void init() {
        props.getAssets().forEach(this::createCache);
    }

    @Scheduled(initialDelayString = Constants.CACHE_INITIALDELAY_DROSJELOYVE, fixedRateString = Constants.CACHE_FIXEDRATE_DROSJELOYVE)
    public void populateCacheAll() {
        props.getAssets().forEach(this::populateCache);
    }

    public void rebuildCache(String orgId) {
		flush(orgId);
		populateCache(orgId);
	}

    @Override
    public void populateCache(String orgId) {
		log.info("Populating Drosjeloyve cache for {}", orgId);
        Event event = new Event(orgId, Constants.COMPONENT, SamferdselActions.GET_ALL_DROSJELOYVE, Constants.CACHE_SERVICE);
        consumerEventUtil.send(event);
    }


    public Optional<DrosjeloyveResource> getDrosjeloyveByMappeId(String orgId, String mappeId) {
        return getOne(orgId, mappeId.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(DrosjeloyveResource::getMappeId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(mappeId::equals)
                .orElse(false));
    }

    public Optional<DrosjeloyveResource> getDrosjeloyveBySystemId(String orgId, String systemId) {
        return getOne(orgId, systemId.hashCode(),
            (resource) -> Optional
                .ofNullable(resource)
                .map(DrosjeloyveResource::getSystemId)
                .map(Identifikator::getIdentifikatorverdi)
                .map(systemId::equals)
                .orElse(false));
    }


	@Override
    public void onAction(Event event) {
        List<DrosjeloyveResource> data;
        if (checkFintResourceCompatibility && fintResourceCompatibility.isFintResourceData(event.getData())) {
            log.info("Compatibility: Converting FintResource<DrosjeloyveResource> to DrosjeloyveResource ...");
            data = fintResourceCompatibility.convertResourceData(event.getData(), DrosjeloyveResource.class);
        } else {
            data = objectMapper.convertValue(event.getData(), javaType);
        }
        data.forEach(linker::mapLinks);
        if (SamferdselActions.valueOf(event.getAction()) == SamferdselActions.UPDATE_DROSJELOYVE) {
            if (event.getResponseStatus() == ResponseStatus.ACCEPTED || event.getResponseStatus() == ResponseStatus.CONFLICT) {
                List<CacheObject<DrosjeloyveResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
                addCache(event.getOrgId(), cacheObjects);
                log.info("Added {} cache objects to cache for {}", cacheObjects.size(), event.getOrgId());
            } else {
                log.debug("Ignoring payload for {} with response status {}", event.getOrgId(), event.getResponseStatus());
            }
        } else {
            List<CacheObject<DrosjeloyveResource>> cacheObjects = data
                    .stream()
                    .map(i -> new CacheObject<>(i, linker.hashCodes(i)))
                    .collect(Collectors.toList());
            updateCache(event.getOrgId(), cacheObjects);
            log.info("Updated cache for {} with {} cache objects", event.getOrgId(), cacheObjects.size());
        }
    }
}
