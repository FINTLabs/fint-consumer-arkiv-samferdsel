package no.novari.fint.consumer.config;

import java.util.Map;
import com.google.common.collect.ImmutableMap;
import no.novari.fint.consumer.utils.RestEndpoints;
import no.novari.fint.model.arkiv.noark.Klasse;
import no.novari.fint.model.arkiv.samferdsel.SoknadDrosjeloyve;

public class LinkMapper {

    public static Map<String, String> linkMapper(String contextPath) {
        return ImmutableMap.<String,String>builder()
            .put(Klasse.class.getName(), contextPath + RestEndpoints.KLASSE)
            .put(SoknadDrosjeloyve.class.getName(), contextPath + RestEndpoints.SOKNADDROSJELOYVE)
            .put("no.novari.fint.model.felles.kodeverk.iso.Landkode", "/model/felles/kodeverk/iso/landkode")
            .put("no.novari.fint.model.arkiv.kodeverk.DokumentStatus", "/model/arkiv/kodeverk/dokumentstatus")
            .put("no.novari.fint.model.arkiv.kodeverk.DokumentType", "/model/arkiv/kodeverk/dokumenttype")
            .put("no.novari.fint.model.arkiv.kodeverk.TilknyttetRegistreringSom", "/model/arkiv/kodeverk/tilknyttetregistreringsom")
            .put("no.novari.fint.model.arkiv.noark.Arkivressurs", "/model/arkiv/noark/arkivressurs")
            .put("no.novari.fint.model.arkiv.kodeverk.Format", "/model/arkiv/kodeverk/format")
            .put("no.novari.fint.model.arkiv.kodeverk.Variantformat", "/model/arkiv/kodeverk/variantformat")
            .put("no.novari.fint.model.arkiv.noark.Dokumentfil", "/model/arkiv/noark/dokumentfil")
            .put("no.novari.fint.model.arkiv.kodeverk.JournalpostType", "/model/arkiv/kodeverk/journalposttype")
            .put("no.novari.fint.model.arkiv.kodeverk.JournalStatus", "/model/arkiv/kodeverk/journalstatus")
            .put("no.novari.fint.model.arkiv.noark.AdministrativEnhet", "/model/arkiv/noark/administrativenhet")
            .put("no.novari.fint.model.arkiv.kodeverk.Tilgangsgruppe", "/model/arkiv/kodeverk/tilgangsgruppe")
            .put("no.novari.fint.model.arkiv.noark.Arkivdel", "/model/arkiv/noark/arkivdel")
            .put("no.novari.fint.model.utdanning.kodeverk.Skolear", "/model/utdanning/kodeverk/skolear")
            .put("no.novari.fint.model.utdanning.kodeverk.Termin", "/model/utdanning/kodeverk/termin")
            .put("no.novari.fint.model.utdanning.utdanningsprogram.Arstrinn", "/model/utdanning/utdanningsprogram/arstrinn")
            .put("no.novari.fint.model.utdanning.utdanningsprogram.Skole", "/model/utdanning/utdanningsprogram/skole")
            .put("no.novari.fint.model.utdanning.elev.Undervisningsforhold", "/model/utdanning/elev/undervisningsforhold")
            .put("no.novari.fint.model.utdanning.elev.Klassemedlemskap", "/model/utdanning/elev/klassemedlemskap")
            .put("no.novari.fint.model.utdanning.elev.Kontaktlarergruppe", "/model/utdanning/elev/kontaktlarergruppe")
            .put("no.novari.fint.model.arkiv.kodeverk.KorrespondansepartType", "/model/arkiv/kodeverk/korrespondanseparttype")
            .put("no.novari.fint.model.arkiv.kodeverk.Merknadstype", "/model/arkiv/kodeverk/merknadstype")
            .put("no.novari.fint.model.arkiv.kodeverk.PartRolle", "/model/arkiv/kodeverk/partrolle")
            .put("no.novari.fint.model.arkiv.kodeverk.Skjermingshjemmel", "/model/arkiv/kodeverk/skjermingshjemmel")
            .put("no.novari.fint.model.arkiv.kodeverk.Tilgangsrestriksjon", "/model/arkiv/kodeverk/tilgangsrestriksjon")
            .put("no.novari.fint.model.arkiv.kodeverk.Saksmappetype", "/model/arkiv/kodeverk/saksmappetype")
            .put("no.novari.fint.model.arkiv.kodeverk.Saksstatus", "/model/arkiv/kodeverk/saksstatus")
            /* .put(TODO,TODO) */
            .build();
    }

}
