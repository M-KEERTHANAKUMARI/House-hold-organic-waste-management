package com.compost.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.compost.bean.Subscriber;
import com.compost.util.DBUtil;
public class SubscriberDAO {
	    public Subscriber findSubscriber(String subscriberID) throws Exception {

	        Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps =
	            con.prepareStatement("SELECT * FROM SUBSCRIBER_TBL2 WHERE Subscriber_ID=?");

	        ps.setString(1, subscriberID);
	        ResultSet rs = ps.executeQuery();

	        Subscriber s = null;

	        if (rs.next()) {
	            s = new Subscriber();
	            s.setSubscriberID(rs.getString("Subscriber_ID"));
	            s.setHouseOrFlatNo(rs.getString("House_Flat_No"));
	            s.setStreetOrBlockName(rs.getString("Street_Block_Name"));
	            s.setResidentName(rs.getString("Resident_Name"));
	            s.setContactNumber(rs.getString("Contact_Number"));
	            s.setEmail(rs.getString("Email"));
	            s.setCollectionPlanType(rs.getString("Collection_Plan_Type"));
	            s.setPreferredCollectionWindow(rs.getString("Preferred_Collection_Window"));
	            s.setPerKgRate(rs.getDouble("Per_Kg_Rate"));
	            s.setMaxDailyKg(rs.getDouble("Max_Daily_Kg"));
	            s.setSubscriptionStartDate(rs.getDate("Subscription_Start_Date"));
	            s.setSubscriptionStatus(rs.getString("Subscription_Status"));
	            s.setPendingAmount(rs.getDouble("Pending_Amount"));
	        }

	        con.close();
	        return s;
	    }
	    public List<Subscriber> viewAllActiveSubscribers() throws Exception {

	        List<Subscriber> list = new ArrayList<>();
	        Connection con = DBUtil.getDBConnection();

	        PreparedStatement ps =
	            con.prepareStatement("SELECT * FROM SUBSCRIBER_TBL2 WHERE Subscription_Status='ACTIVE'");
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            Subscriber s = new Subscriber();
	            s.setSubscriberID(rs.getString("Subscriber_ID"));
	            s.setResidentName(rs.getString("Resident_Name"));
	            s.setContactNumber(rs.getString("Contact_Number"));
	            s.setPendingAmount(rs.getDouble("Pending_Amount"));
	            list.add(s);
	        }

	        con.close();
	        return list;
	    }
	    public boolean insertSubscriber(Subscriber subscriber) throws Exception {

	        Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps =
	            con.prepareStatement("INSERT INTO SUBSCRIBER_TBL2 VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");

	        ps.setString(1, subscriber.getSubscriberID());
	        ps.setString(2, subscriber.getHouseOrFlatNo());
	        ps.setString(3, subscriber.getStreetOrBlockName());
	        ps.setString(4, subscriber.getResidentName());
	        ps.setString(5, subscriber.getContactNumber());
	        ps.setString(6, subscriber.getEmail());
	        ps.setString(7, subscriber.getCollectionPlanType());
	        ps.setString(8, subscriber.getPreferredCollectionWindow());
	        ps.setDouble(9, subscriber.getPerKgRate());
	        ps.setDouble(10, subscriber.getMaxDailyKg());
	        ps.setDate(11, subscriber.getSubscriptionStartDate());
	        ps.setString(12, subscriber.getSubscriptionStatus());
	        ps.setDouble(13, subscriber.getPendingAmount());

	        boolean result = ps.executeUpdate() == 1;
	        con.close();
	        return result;
	    }
	    public boolean updateSubscriptionStatus(String subscriberID, String newStatus) throws Exception {

	        Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps =
	            con.prepareStatement("UPDATE SUBSCRIBER_TBL2 SET Subscription_Status=? WHERE Subscriber_ID=?");

	        ps.setString(1, newStatus);
	        ps.setString(2, subscriberID);

	        boolean result = ps.executeUpdate() == 1;
	        con.close();
	        return result;
	    }
	    public boolean updatePendingAmount(String subscriberID, double newPendingAmount) throws Exception {

	        Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps =
	            con.prepareStatement("UPDATE SUBSCRIBER_TBL2 SET Pending_Amount=? WHERE Subscriber_ID=?");

	        ps.setDouble(1, newPendingAmount);
	        ps.setString(2, subscriberID);

	        boolean result = ps.executeUpdate() == 1;
	        con.close();
	        return result;
	    }
	}


