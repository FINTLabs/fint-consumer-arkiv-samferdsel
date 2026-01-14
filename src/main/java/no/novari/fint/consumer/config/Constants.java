package no.novari.fint.consumer.config;

public enum Constants {
;

    public static final String COMPONENT = "arkiv-samferdsel";
    public static final String COMPONENT_CONSUMER = COMPONENT + " consumer";
    public static final String CACHE_SERVICE = "CACHE_SERVICE";

    
    public static final String CACHE_INITIALDELAY_KLASSE = "${fint.consumer.cache.initialDelay.klasse:900000}";
    public static final String CACHE_FIXEDRATE_KLASSE = "${fint.consumer.cache.fixedRate.klasse:900000}";
    
    public static final String CACHE_INITIALDELAY_SOKNADDROSJELOYVE = "${fint.consumer.cache.initialDelay.soknaddrosjeloyve:1000000}";
    public static final String CACHE_FIXEDRATE_SOKNADDROSJELOYVE = "${fint.consumer.cache.fixedRate.soknaddrosjeloyve:900000}";
    

}
