package com.linh.pfa.stock.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.linh.pfa.stock.entity.Stock;

public abstract class HtmlStockPriceRetriever implements StockPriceRetriever {
	private String url;
	private String pattern;
	
    abstract String getStockCode(Stock stock);
    
    void setUrl(String url, String pattern) {
    	this.url = url;
    	this.pattern = pattern;
    }

    @Override
    public double getPrice(Stock stock)
    {
        String code = getStockCode(stock);
        String s = String.format(url, code);
        String result;
		try {
			result = getHtml(s);
		} catch (Exception e) {
			return 0.00;
		}

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(result);

        String value = "0.00";
        if (m.find( )) {
        	value = m.group(1); // group 0 is the whole matching!!!
		}

        return Double.parseDouble(value);
    }
    
    private String getHtml(String s) throws Exception {
        URL web = new URL(s);
        BufferedReader in = new BufferedReader(new InputStreamReader(web.openStream()));
        StringBuilder sb = new StringBuilder(); 

        String inputLine;
        while ((inputLine = in.readLine()) != null)
            sb.append(inputLine);
        in.close();
        return sb.toString();
    }
}
