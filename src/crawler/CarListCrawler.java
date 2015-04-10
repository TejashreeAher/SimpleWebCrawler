/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crawler;

import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author tejashree.aher
 */
public class CarListCrawler implements BaseCrawler{
    
    private String url;
    private List<String> allCars = new ArrayList<String>();
    
    public CarListCrawler(String url){
        this.url = url;
    }
    
    public void updateDBData(){
        
    }

    @Override
    public void processPage(String URL) {
        try {
            Document doc = Jsoup.connect(URL).get();
            Element carNamesElement = doc.select("div.margin-top15.margin-bottom15").first();
            Elements makeElements = doc.select("#drpMakeNew.leftfloat");
            Elements options = makeElements.select("select > option");
             
            for(Element element : options)
            {
                if(!element.attr("value").equals("-1")){
                    System.out.println("id: "+ element.attr("value")+":"+element.text());
                    String car = element.text();
                    if(!StringUtils.isEmptyOrWhitespaceOnly(car)){
                        allCars.add(car);
                    }
                }
            }
            
            Elements carModels = doc.select("#drpModelNew.margin-left5.leftfloat");
            System.out.println("Models : "+ carModels.text());
        } catch (IOException ex) {
            Logger.getLogger(CarListCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args){
        CarListCrawler crawler = new CarListCrawler("http://www.carwale.com/");
        crawler.processPage(crawler.url);
    }
    
}
