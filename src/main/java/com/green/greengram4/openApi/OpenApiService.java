package com.green.greengram4.openApi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.green.greengram4.common.OpenApiProperties;
import com.green.greengram4.openApi.model.ApartmentTransactionDetailDto;
import com.green.greengram4.openApi.model.ApartmentTransactionDetailVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilder;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OpenApiService {

    private final OpenApiProperties openApiProperties;

    public List<ApartmentTransactionDetailVo> getApartmentTransactionList
            (ApartmentTransactionDetailDto dto) throws Exception{

        DefaultUriBuilderFactory factory = new DefaultUriBuilderFactory
                (openApiProperties.getApartment().getBaseUrl());
        factory.setEncodingMode(DefaultUriBuilderFactory.EncodingMode.NONE);
        // ==, +는 주소로 사용x 인코딩 필요 특히 +는 인코딩이 안됩니다.
        // 그래서 인코딩 되지 말라고 셋팅. 인코딩이 두번 되면 에러
        // url 인코딩/디코딩 검색

        // 로그 찍어주기
        WebClient webClient = WebClient.builder()
                .uriBuilderFactory(factory)
                .filters(exchangeFilterFunctions -> exchangeFilterFunctions.add(logRequest()))
                .baseUrl(openApiProperties.getApartment().getBaseUrl())
                .build();

        String data = webClient.get().uri(uriBuilder -> {
                    UriBuilder ub = uriBuilder
                            .path(openApiProperties.getApartment().getDataUrl())
                            .queryParam("DEAL_YMD", dto.getDealYm())
                            .queryParam("LAND_CD", dto.getLawdCd())
                            .queryParam("serviceKey", openApiProperties.getApartment().getServiceKey());
                            if(dto.getPageNo() > 0){
                                ub.queryParam("pageNm", dto.getPageNo());
                            }
                            if(dto.getNumOfRows() > 0){
                                ub.queryParam("numOfRows", dto.getNumOfRows());
                            }
                            return ub.build();
                }
        ).retrieve()
                .bodyToMono(String.class)
                // 응답을 1개짜리
                .block();
        log.info("data : {}", data);

        ObjectMapper om = new XmlMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 넘어온 컬럼을 다 안쓰도록 설정, 직렬화(객체를 한줄로 나열)를 안한다.
        JsonNode jsonNode = om.readTree(data);
        List<ApartmentTransactionDetailVo> list = om.convertValue(jsonNode
                .path("body")
                .path("items")
                .path("item"), new TypeReference<List<ApartmentTransactionDetailVo>>() {});
        // 응답을 비동기로
        return list;
    }

    // 로그 찍어주기
    private ExchangeFilterFunction logRequest() {
        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
            if (log.isDebugEnabled()) {
                StringBuilder sb = new StringBuilder("Request: \n");
                //append clientRequest method and url
                clientRequest
                        .headers()
                        .forEach((name, values) -> values.forEach(value -> log.info(value)));
                log.debug(sb.toString());
            }
            return Mono.just(clientRequest);
        });
    }
}
