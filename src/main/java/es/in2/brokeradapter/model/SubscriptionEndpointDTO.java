package es.in2.brokeradapter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class SubscriptionEndpointDTO {

	@JsonProperty("uri")
	private String uri;

	@JsonProperty("receiverInfo")
	private List<RetrievalInfoContentTypeDTO> receiverInfo = List.of(new RetrievalInfoContentTypeDTO());

}
