/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package crawler;

import java.io.IOException;
import java.sql.SQLException;

/**
 *
 * @author tejashree.aher
 */
public interface BaseCrawler {

    /**
     *
     * @param URL
     */
    public void processPage(String URL);
}
