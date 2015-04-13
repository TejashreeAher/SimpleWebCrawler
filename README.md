# SimpleWebCrawler
Demos a simple crawler using jsoup and how to extract data from html elements (table, divs etc.)

Main class: CarwaleCrawler.java -> Gets the price details of all the variants of a particular model
Main class : CarListCrawler.java -> Gets all the models of all the makers 



Needs DB named : crawler
And the following tables:

CREATE TABLE `carwale_cars` (
  `name` varchar(100) NOT NULL,
  `advancedUrl` varchar(100) DEFAULT NULL,
  `price` varchar(100) DEFAULT NULL
)

CREATE TABLE `CarWaleMakerToModel` (
  `maker` varchar(50) DEFAULT NULL,
  `maker_id` int(11) NOT NULL DEFAULT '0',
  `model` varchar(100) DEFAULT NULL,
  `model_id` int(11) NOT NULL DEFAULT '0',
  `MaskingName` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`maker_id`,`model_id`)
)

Code to insert into `CarWaleMakerToModel` is still not implemented.
