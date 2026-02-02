package com.compost.app;
import java.sql.Date;
import java.util.Scanner;

import com.compost.bean.Subscriber;
import com.compost.service.CompostService;
import com.compost.util.CollectionWeightExceededException;
import com.compost.util.ValidationException;

public class CompostMain {

	    private static CompostService service = new CompostService();

	    public static void main(String[] args) {

	        Scanner sc = new Scanner(System.in);
	        System.out.println("--- Household Compost Collection Console ---");
	        try {
	            Subscriber s = new Subscriber();
	            s.setSubscriberID("SB2002");
	            s.setHouseOrFlatNo("C-404");
	            s.setStreetOrBlockName("Green Residency Block C");
	            s.setResidentName("Meenakshi Rao");
	            s.setContactNumber("9998887771");
	            s.setEmail("meenakshi.rao@example.com");
	            s.setCollectionPlanType("DAILY");
	            s.setPreferredCollectionWindow("06:30-07:30");
	            s.setPerKgRate(4.00);
	            s.setMaxDailyKg(8.00);
	            s.setSubscriptionStartDate(new Date(System.currentTimeMillis()));
	            s.setSubscriptionStatus("ACTIVE");
	            s.setPendingAmount(0.0);

	            boolean ok = service.registerNewSubscriber(s);
	            System.out.println(ok ? "SUBSCRIBER REGISTERED"
	                                  : "SUBSCRIBER REGISTRATION FAILED");

	        } catch (ValidationException e) {
	            System.out.println("Validation Error: " + e.toString());
	        } catch (Exception e) {
	            System.out.println("System Error: " + e.getMessage());
	        }
	        try {
	            Date today = new Date(System.currentTimeMillis());

	            boolean ok = service.recordCollection(
	                    "SB2002",
	                    today,
	                    "07:00-07:10",
	                    5.5,
	                    "SUCCESS",
	                    "COL201",
	                    "Minor plastic pieces found, advised resident"
	            );

	            System.out.println(ok ? "COLLECTION RECORDED"
	                                  : "COLLECTION RECORD FAILED");

	        } catch (CollectionWeightExceededException e) {
	            System.out.println("Weight Error: " + e.toString());
	        } catch (ValidationException e) {
	            System.out.println("Validation Error: " + e.toString());
	        } catch (Exception e) {
	            System.out.println("System Error: " + e.getMessage());
	        }

	        sc.close();
	    }
	}



