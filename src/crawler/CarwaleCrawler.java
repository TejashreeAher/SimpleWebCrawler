/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crawler;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author tejashree.aher
 */
public class CarwaleCrawler {

    /**
     * @param args the command line arguments
     */
        public static DB db = new DB();
 
	public static void main(String[] args) throws SQLException, IOException {
		db.runSql2("TRUNCATE Record;");
		processPage("http://www.carwale.com/ashokleyland-cars/stile/");
	}
 
	public static void processPage(String URL) throws SQLException, IOException{
		//check if the given URL is already in database
		String sql = "select * from Record where URL = '"+URL+"'";
		ResultSet rs = db.runSql(sql);
		if(rs.next()){
 
		}else{
                        //get useful information
			Document doc = Jsoup.connect("http://www.carwale.com/ashokleyland-cars/stile/").get();
                        String question = doc.select("#row .content_padding").text();
                        Element versionTable = doc.getElementById("versions");///gives the version table of this bike
                        Element table = doc.select("table").get(0); //select the first table.
                        Elements rows = table.select("tr");

                        for (int i = 1; i < rows.size(); i++) { //first row is the col names so skip it.
                            Element row = rows.get(i);
                            Elements cols = row.select("td");
                            String name="";
                            String advancedInfoLink = "";
                            String price="";
                            for(int j = 0; j<cols.size(); j++){
                                if(j==0){
                                     Element advancedInfo = cols.get(j).select("a").first();
                                     advancedInfoLink = advancedInfo.attr("href");
                                    name = cols.get(j).text();
                                    System.out.println(cols.get(j).text() + ": Advanced info link:" + advancedInfoLink);
                                }else{
                                    if(j==1){
                                        price = cols.get(j).select("span.fontblack").text();
                                        System.out.println("Price: "+ price);
                                    }
                                }
                            }
                            System.out.println("--------------------------------------");
                            sql = "INSERT INTO  `crawler`.`carwale_cars` " + "(`name`,`advancedUrl`, `price`) VALUES " + "(?,?,?);";
                            PreparedStatement stmt = db.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                            stmt.setString(1, name);
                            stmt.setString(2, advancedInfoLink);
                            stmt.setString(3, price);
                            stmt.execute();
                        }
                        
                        
//                        String urlNew = doc.select("meta[name=og:url]").first().attr("content");
//                        System.out.println(URL + "::::" + urlNew);
//                        System.out.println("--------------------------------------");
//			if(doc.text().contains("Ex-showroom")){
//				System.out.println(URL + "::::" + urlNew);
//				System.out.println("--------------------------------------");
//
//                        }
//                        processPage(urlNew);
 
//			//get all links and recursively call the processPage method
//			Elements questions = doc.select("a[href]");
//			for(Element link: questions){
//				if(link.attr("href").contains("mit.edu"))
//					processPage(link.attr("abs:href"));
//			}
		}
	}
    
    
}
