package com.hant.trade.finfo;

import com.hant.trade.finfo.model.DerivativePrice;
import com.hant.trade.finfo.model.PageDerivativePrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.List;

@RestController()
public class PriceEODController {
    private final String finfoUrl = "https://finfo-api.vndirect.com.vn/v4";
    private RestTemplate restTemplate;

    @Autowired
    public PriceEODController(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    /**
     * https://finfo-api.vndirect.com.vn/v4/derivative_prices?q=deriCode:VN30F1M~date:gte:2020-06-12&size=1
     * @param symbol
     * @param fromDate
     * @param toDate
     * @return
     */
    @GetMapping("/derivative-prices")
    public List<DerivativePrice> derivativePrices(
            @RequestParam(value = "symbol") String symbol,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate
    ) {
        StringBuilder endpoint = new StringBuilder(finfoUrl);
        endpoint.append("/derivative_prices?q=code:").append(symbol);
        if (!StringUtils.isEmpty(fromDate)) endpoint.append("~date:gte:").append(fromDate);
        if (!StringUtils.isEmpty(toDate)) endpoint.append("~date:lte:").append(toDate);
        endpoint.append("&size=5000");

        BotLogger.info("PriceEODController", endpoint.toString());
        ResponseEntity<PageDerivativePrice> exchange = restTemplate.exchange(endpoint.toString(), HttpMethod.GET, null, PageDerivativePrice.class);

        return exchange.getBody().getData();
    }
}
