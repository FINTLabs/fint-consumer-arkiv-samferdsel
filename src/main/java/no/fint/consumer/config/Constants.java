package no.fint.consumer.config;

public enum Constants {
;

    public static final String COMPONENT = "arkiv-samferdsel";
    public static final String COMPONENT_CONSUMER = COMPONENT + " consumer";
    public static final String CACHE_SERVICE = "CACHE_SERVICE";

    
    public static final String CACHE_INITIALDELAY_SOKNADDROSJELOYVE = "${fint.consumer.cache.initialDelay.soknaddrosjeloyve:900000}";
    public static final String CACHE_FIXEDRATE_SOKNADDROSJELOYVE = "${fint.consumer.cache.fixedRate.soknaddrosjeloyve:900000}";
    

}
