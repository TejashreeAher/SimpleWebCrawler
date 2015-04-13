/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crawler;

import com.mysql.jdbc.StringUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONBuilder;
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
    private Map<String, List<String>> makerToModelListMap = new HashMap<String, List<String>>();
    
    public CarListCrawler(String url){
        this.url = url;
    }
    
    public void updateDBData(){
        
    }

    private void setCarModelsList(String URL){
        try {
            Document doc = Jsoup.connect(URL).get();
            Element carNamesElement = doc.select("div.margin-top15.margin-bottom15").first();
            Elements makeElements = doc.select("#drpMakeNew.leftfloat");
            Elements options = makeElements.select("select > option");
             
            for(Element element : options)
            {
                if(!element.attr("value").equals("-1")){
                    System.out.println("id: "+ element.attr("value")+":"+element.text());
                    String id = element.attr("value");
                    Document modelDoc = Jsoup.connect("http://www.carwale.com/webapi/carmodeldata/GetCarModelsByType/?type=new&makeId="+id).get();
                    System.out.println("For id: "+ id+", Models are ----------"+modelDoc.text());
                    //[{"ModelId":444,"ModelName":"Pajero Sport","MaskingName":"pajerosport"}]
                    String modelsString = modelDoc.text();
                    
                    System.out.println("---------------------------------------");

                    String car = element.text();
                    if(!StringUtils.isEmptyOrWhitespaceOnly(car)){
                        allCars.add(car);
                    }
                }
            }
            
        } catch (IOException ex) {
            Logger.getLogger(CarListCrawler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @Override
    public void processPage(String URL) {
        
    }
    
    public static void main(String[] args){
        CarListCrawler crawler = new CarListCrawler("http://www.carwale.com/");
        crawler.setCarModelsList(crawler.url);
    }
    
}
