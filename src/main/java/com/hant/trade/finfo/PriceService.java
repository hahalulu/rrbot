package com.hant.trade.finfo;

import com.hant.trade.finfo.model.*;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.logging.BotLogger;

import java.util.List;

@Component
public class PriceService {
    private final String finfoUrl = "https://finfo-api.vndirect.com.vn";
    private RestTemplate restTemplate;

    public PriceService(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    /**
     * https://finfo-api.vndirect.com.vn/v4/derivative_prices?q=deriCode:VN30F1M~date:gte:2020-06-12&size=1
     * @param symbol
     * @return
     */
    public List<DerivativePrice> getDerivativePrices(String symbol) {
        StringBuilder endpoint = new StringBuilder(finfoUrl);
        endpoint.append("/v4/derivative_prices?q=code:").append(symbol);
        endpoint.append("~date:gte:").append("2019-07-20");
        endpoint.append("&size=5000");

        BotLogger.info("PriceEODController", endpoint.toString());
        ResponseEntity<PageDerivativePrice> exchange = restTemplate.exchange(endpoint.toString(), HttpMethod.GET, null, PageDerivativePrice.class);

        return exchange.getBody().getData();
    }

    /**
     * https://finfo-api.vndirect.com.vn/v4/stock_prices?sort=date&q=code:CTR~date:gte:2021-07-12~date:lte:2021-07-15&size=15&page=1
     * @param symbol
     * @return
     */
    public List<StockPrice> getStockPrices(String symbol) {
        StringBuilder endpoint = new StringBuilder(finfoUrl);
        endpoint.append("/v4/stock_prices?q=code:").append(symbol);
        endpoint.append("~date:gte:").append("2019-07-20");
        endpoint.append("&size=5000");

        BotLogger.info("PriceEODController", endpoint.toString());
        ResponseEntity<PageStockPrice> exchange = restTemplate.exchange(endpoint.toString(), HttpMethod.GET, null, PageStockPrice.class);

        return exchange.getBody().getData();
    }

    /**
     * https://finfo-api.vndirect.com.vn/index/securities/vnmarket?fromDate=2019-07-10&toDate=2019-07-20
     * @return
     */
    public List<IndexInfo> getVnIndex(String symbol) {
        StringBuilder endpoint = new StringBuilder(finfoUrl);
        endpoint.append("/index/securities/vnmarket?indexCodes=").append(symbol);
        endpoint.append("&fromDate=").append("2019-07-20");
        endpoint.append("&size=5000");

        BotLogger.info("PriceEODController", endpoint.toString());
        ResponseEntity<PageIndexInfo> exchange = restTemplate.exchange(endpoint.toString(), HttpMethod.GET, null, PageIndexInfo.class);

        return exchange.getBody().getData();
    }

}
