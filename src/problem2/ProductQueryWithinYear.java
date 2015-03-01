package problem2;

public class ProductQueryWithinYear {

	/*
	 * I misread the requirements and limited to total sales >= 1000 rather than
	 * limiting it to sales in the last year >= 1000
	 */
	
	
	//Query
	/**
		Select P.PRODUCT_ID, SUM(ORD.QUANTITY) from PRODUCT P 
		left JOIN PRODUCT discP 
		  ON (discP.REPLACEMENT_PRODUCT_ID = P.PRODUCT_ID
		  OR P.PRODUCT_ID = discP.PRODUCT_ID)
		left JOIN ORDER_ITEM ORD
		  ON (discP.PRODUCT_ID = ORD.PRODUCT_ID)
		left Join ORDER_HEADER OH
		  ON (ORD.ORDER_ID = OH.ORDER_ID)
		where (P.DISCONTINUE_DATE is null)
		group by P.PRODUCT_ID
		having SUM(case when OH.ORDER_DATE > NOW()-INTERVAL 1 YEAR
		           then ORD.QUANTITY end) >= 1000;
	 */
	
	
	//test schema
	//This was tested using sql fiddle - http://sqlfiddle.com/#!2/d2eaa/1
	/**
		CREATE TABLE PRODUCT
		(
		PRODUCT_ID int,
		DISCONTINUE_DATE datetime,
		REPLACEMENT_PRODUCT_ID int
		);
		
		CREATE TABLE ORDER_HEADER
		(
		ORDER_ID int,
		ORDER_DATE datetime
		);
		
		
		CREATE TABLE ORDER_ITEM
		(
		ORDER_ID int,
		PRODUCT_ID int,
		UNIT_PRICE decimal,
		QUANTITY int
		);
		
		INSERT INTO PRODUCT (PRODUCT_ID, DISCONTINUE_DATE, REPLACEMENT_PRODUCT_ID)
		VALUES (1, null, null);
		INSERT INTO PRODUCT (PRODUCT_ID, DISCONTINUE_DATE, REPLACEMENT_PRODUCT_ID)
		VALUES (2, CURRENT_TIMESTAMP, 1);
		INSERT INTO PRODUCT (PRODUCT_ID, DISCONTINUE_DATE, REPLACEMENT_PRODUCT_ID)
		VALUES (3, CURRENT_TIMESTAMP, 1);
		INSERT INTO PRODUCT (PRODUCT_ID, DISCONTINUE_DATE, REPLACEMENT_PRODUCT_ID)
		VALUES (4, null, null);
		INSERT INTO PRODUCT (PRODUCT_ID, DISCONTINUE_DATE, REPLACEMENT_PRODUCT_ID)
		VALUES (5, null, null);
		
		INSERT INTO ORDER_HEADER(ORDER_ID, ORDER_DATE)
		VALUES (1, NOW()-INTERVAL 2 year);
		INSERT INTO ORDER_ITEM(ORDER_ID, PRODUCT_ID, UNIT_PRICE, QUANTITY)
		VALUES (1, 1, 3.2, 400);
		
		INSERT INTO ORDER_HEADER(ORDER_ID, ORDER_DATE)
		VALUES (2, NOW());
		INSERT INTO ORDER_ITEM(ORDER_ID, PRODUCT_ID, UNIT_PRICE, QUANTITY)
		VALUES (2, 3, 2.8, 800);
		
		INSERT INTO ORDER_HEADER(ORDER_ID, ORDER_DATE)
		VALUES (3, NOW());
		INSERT INTO ORDER_ITEM(ORDER_ID, PRODUCT_ID, UNIT_PRICE, QUANTITY)
		VALUES (3, 2, 3.5, 800);
		
		INSERT INTO ORDER_HEADER(ORDER_ID, ORDER_DATE)
		VALUES (4, NOW()-INTERVAL 2 year);
		INSERT INTO ORDER_ITEM(ORDER_ID, PRODUCT_ID, UNIT_PRICE, QUANTITY)
		VALUES (4, 4, 2.2, 400);
		
		INSERT INTO ORDER_HEADER(ORDER_ID, ORDER_DATE)
		VALUES (5, NOW()-INTERVAL 35 DAY);
		INSERT INTO ORDER_ITEM(ORDER_ID, PRODUCT_ID, UNIT_PRICE, QUANTITY)
		VALUES (5, 4, 3.7, 700);
		
		INSERT INTO ORDER_HEADER(ORDER_ID, ORDER_DATE)
		VALUES (6, NOW());
		INSERT INTO ORDER_ITEM(ORDER_ID, PRODUCT_ID, UNIT_PRICE, QUANTITY)
		VALUES (6, 5, 4.2, 1100);
	 */
}