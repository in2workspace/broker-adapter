package es.in2.brokeradapter.domain;

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

	@JsonProperty("accept")
	@Builder.Default
	private String accept = "application/json";

	@JsonProperty("receiverInfo")
	@Builder.Default
	private List<RetrievalInfoContentTypeDTO> receiverInfo = List.of(new RetrievalInfoContentTypeDTO());

}
