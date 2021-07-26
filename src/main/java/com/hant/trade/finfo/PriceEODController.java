package com.hant.trade.finfo;

import com.hant.trade.finfo.model.DerivativePrice;
import com.hant.trade.finfo.model.IndexInfo;
import com.hant.trade.finfo.model.StockPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController()
public class PriceEODController {
    private PriceService priceService;

    @Autowired
    public PriceEODController(PriceService priceService) {
        this.priceService = priceService;
    }

    @GetMapping("/derivative-prices/export")
    public void exportDeriToCSV(HttpServletResponse response,
                            @RequestParam(value = "symbol") String symbol) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" + symbol + "_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<DerivativePrice> derivativePrices = priceService.getDerivativePrices(symbol);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"code", "deriCode", "date", "time", "floor", "open", "high", "low", "close", "nmVolume", "ptVolume", "change", "lastPrice"};
        String[] nameMapping = {"code", "deriCode", "date", "time", "floor", "open", "high", "low", "close", "nmVolume", "ptVolume", "pctChange", "lastPrice"};

        csvWriter.writeHeader(csvHeader);

        for (DerivativePrice price : derivativePrices) {
            csvWriter.write(price, nameMapping);
        }
        csvWriter.close();
    }


    /**
     * @param response
     * @param symbol
     * @throws IOException
     */
    @GetMapping("/stock-prices/export")
    public void exportStockToCSV(HttpServletResponse response,
                            @RequestParam(value = "symbol") String symbol) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=" +symbol+ "_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<StockPrice> stockPrices = priceService.getStockPrices(symbol);

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"code", "date", "floor", "open", "high", "low", "close", "nmVolume", "ptVolume", "change", "lastPrice"};
        String[] nameMapping = {"code", "date", "floor", "open", "high", "low", "close", "nmVolume", "ptVolume", "pctChange", "lastPrice"};

        csvWriter.writeHeader(csvHeader);

        for (StockPrice price : stockPrices) {
            csvWriter.write(price, nameMapping);
        }
        csvWriter.close();
    }

    @GetMapping("/vnindex/export")
    public void exportVnindexToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vnindex_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<IndexInfo> indexInfos = priceService.getVnIndex("VNINDEX");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"code", "date", "open", "high", "low", "close", "volume", "change", "lastPrice"};
        String[] nameMapping = {"indexCode", "tradingDate", "openPrice", "highestPrice", "lowestPrice", "closePrice", "totalQtty", "percentageChange", "lastPrice"};

        csvWriter.writeHeader(csvHeader);

        for (IndexInfo price : indexInfos) {
            csvWriter.write(price, nameMapping);
        }
        csvWriter.close();
    }

    @GetMapping("/vn30/export")
    public void exportVn30ToCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=vnindex_" + currentDateTime + ".csv";
        response.setHeader(headerKey, headerValue);

        List<IndexInfo> indexInfos = priceService.getVnIndex("VN30");

        ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);

        String[] csvHeader = {"code", "date", "open", "high", "low", "close", "volume", "change", "lastPrice"};
        String[] nameMapping = {"indexCode", "tradingDate", "openPrice", "highestPrice", "lowestPrice", "closePrice", "totalQtty", "percentageChange", "lastPrice"};

        csvWriter.writeHeader(csvHeader);

        for (IndexInfo price : indexInfos) {
            csvWriter.write(price, nameMapping);
        }
        csvWriter.close();
    }

}
