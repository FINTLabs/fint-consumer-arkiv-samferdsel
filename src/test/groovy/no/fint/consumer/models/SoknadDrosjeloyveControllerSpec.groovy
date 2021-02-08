package no.fint.consumer.models

import com.fasterxml.jackson.databind.ObjectMapper
import no.fint.consumer.event.SynchronousEvents
import no.fint.consumer.utils.RestEndpoints
import no.fint.event.model.Event
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification

import java.util.concurrent.BlockingQueue
import java.util.concurrent.TimeUnit

import static org.hamcrest.CoreMatchers.containsString
import static org.hamcrest.CoreMatchers.equalTo
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

/*
 * This specification asserts that the custom mappings for /mappeid/{year}/{sequence}
 * are present.
 */

@SpringBootTest(properties = 'fint.consumer.cache.disabled.soknaddrosjeloyve=true')
@AutoConfigureMockMvc
@ActiveProfiles("test")
class SoknadDrosjeloyveControllerSpec extends Specification {

    @Autowired
    MockMvc mockMvc

    @Autowired
    ObjectMapper objectMapper

    @SpringBean
    SynchronousEvents synchronousEvents = Mock()

    def "Verify that GET by mappeId works"() {
        given:
        def event = objectMapper.readValue('''{
    "corrId": "aaf3518e-c9b1-4152-a117-d536c166b0bb",
    "action": "GET_SOKNADDROSJELOYVE",
    "operation": "READ",
    "status": "DOWNSTREAM_QUEUE",
    "time": 1586843296434,
    "orgId": "mock.no",
    "source": "samferdsel",
    "client": "CACHE_SERVICE",
    "data": [
        {
            "mappeId": {
                "identifikatorverdi": "2020/42"
            },
            "organisasjonsnavn": "Spock"
        }
    ],
    "responseStatus": "ACCEPTED",
    "query": "mappeId/2020/42"
}''', Event)
        def queue = Mock(BlockingQueue)
        when:
        def response = mockMvc.perform(
                get(RestEndpoints.SOKNADDROSJELOYVE + '/mappeid/2020/42')
                        .header('x-org-id', 'test.org')
                        .header('x-client', 'Spock'))

        then:
        response.andExpect(status().is2xxSuccessful()).andExpect(jsonPath('$.organisasjonsnavn').value(equalTo('Spock')))
        1 * synchronousEvents.register({ it.request.query == 'mappeId/2020/42' }) >> queue
        1 * queue .poll(5, TimeUnit.MINUTES) >> event
    }

    def "Verify that PUT by mappeId works"() {
        when:
        def response = mockMvc.perform(
                put(RestEndpoints.SOKNADDROSJELOYVE + '/mappeid/2020/22')
                        .header('x-org-id', 'test.org')
                        .header('x-client', 'Spock')
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content("{}")
        )

        then:
        response
                .andExpect(status().is2xxSuccessful())
                .andExpect(header().string('Location', containsString('/status/')))
    }
}
