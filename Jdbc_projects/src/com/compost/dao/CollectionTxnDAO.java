package com.compost.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.compost.bean.CollectionTxn;
import com.compost.util.DBUtil;

public class CollectionTxnDAO {
	public int generateTxnID() throws Exception {
            Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps =
	            con.prepareStatement("SELECT txn_seq.NEXTVAL FROM dual");
	        ResultSet rs = ps.executeQuery();

	        rs.next();
	        int id = rs.getInt(1);
	        con.close();
	        return id;
	    }

	    public boolean insertCollectionTxn(CollectionTxn txn) throws Exception {

	        Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps =
	            con.prepareStatement(
	                "INSERT INTO COLLECTION_TXN_TBL VALUES (?,?,?,?,?,?,?,?,?,?)");

	        ps.setInt(1, txn.getTxnID());
	        ps.setString(2, txn.getSubscriberID());
	        ps.setDate(3, txn.getCollectionDate());
	        ps.setString(4, txn.getCollectionWindow());
	        ps.setDouble(5, txn.getCollectedKg());
	        ps.setDouble(6, txn.getComputedFeeAmount());
	        ps.setString(7, txn.getPaymentFlag());
	        ps.setString(8, txn.getCollectionStatus());
	        ps.setString(9, txn.getCollectorStaffID());
	        ps.setString(10, txn.getRemarks());

	        boolean result = ps.executeUpdate() == 1;
	        con.close();
	        return result;
	    }
	    public CollectionTxn findCollectionTxn(int txnID) throws Exception {

	        Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps =
	            con.prepareStatement("SELECT * FROM COLLECTION_TXN_TBL WHERE Txn_ID=?");

	        ps.setInt(1, txnID);
	        ResultSet rs = ps.executeQuery();

	        CollectionTxn txn = null;

	        if (rs.next()) {
	            txn = new CollectionTxn();
	            txn.setTxnID(rs.getInt("Txn_ID"));
	            txn.setSubscriberID(rs.getString("Subscriber_ID"));
	            txn.setCollectionDate(rs.getDate("Collection_Date"));
	            txn.setCollectedKg(rs.getDouble("Collected_Kg"));
	            txn.setComputedFeeAmount(rs.getDouble("Computed_Fee_Amount"));
	            txn.setPaymentFlag(rs.getString("Payment_Flag"));
	            txn.setCollectionStatus(rs.getString("Collection_Status"));
	        }

	        con.close();
	        return txn;
	    }
	    public List<CollectionTxn> findCollectionsBySubscriber(String subscriberID) throws Exception {

	        List<CollectionTxn> list = new ArrayList<>();
	        Connection con = DBUtil.getDBConnection();

	        PreparedStatement ps =
	            con.prepareStatement(
	                "SELECT * FROM COLLECTION_TXN_TBL WHERE Subscriber_ID=? ORDER BY Collection_Date");

	        ps.setString(1, subscriberID);
	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            CollectionTxn txn = new CollectionTxn();
	            txn.setTxnID(rs.getInt("Txn_ID"));
	            txn.setCollectionDate(rs.getDate("Collection_Date"));
	            txn.setCollectedKg(rs.getDouble("Collected_Kg"));
	            txn.setComputedFeeAmount(rs.getDouble("Computed_Fee_Amount"));
	            txn.setCollectionStatus(rs.getString("Collection_Status"));
	            list.add(txn);
	        }

	        con.close();
	        return list;
	    }

	
	    public List<CollectionTxn> findCollectionsByDateRange(
	            java.sql.Date fromDate, java.sql.Date toDate) throws Exception {

	        List<CollectionTxn> list = new ArrayList<>();
	        Connection con = DBUtil.getDBConnection();

	        PreparedStatement ps =
	            con.prepareStatement(
	                "SELECT * FROM COLLECTION_TXN_TBL WHERE Collection_Date BETWEEN ? AND ?");

	        ps.setDate(1, fromDate);
	        ps.setDate(2, toDate);

	        ResultSet rs = ps.executeQuery();

	        while (rs.next()) {
	            CollectionTxn txn = new CollectionTxn();
	            txn.setTxnID(rs.getInt("Txn_ID"));
	            txn.setSubscriberID(rs.getString("Subscriber_ID"));
	            txn.setCollectedKg(rs.getDouble("Collected_Kg"));
	            txn.setCollectionStatus(rs.getString("Collection_Status"));
	            list.add(txn);
	        }

	        con.close();
	        return list;
	    }

	   
	    public boolean updateCollectionTxn(CollectionTxn txn) throws Exception {

	        Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps =
	            con.prepareStatement(
	                "UPDATE COLLECTION_TXN_TBL SET Collected_Kg=?, Computed_Fee_Amount=?, Collection_Status=?, Remarks=? WHERE Txn_ID=?");

	        ps.setDouble(1, txn.getCollectedKg());
	        ps.setDouble(2, txn.getComputedFeeAmount());
	        ps.setString(3, txn.getCollectionStatus());
	        ps.setString(4, txn.getRemarks());
	        ps.setInt(5, txn.getTxnID());

	        boolean result = ps.executeUpdate() == 1;
	        con.close();
	        return result;
	    }

	    public double calculateUnpaidAmountForSubscriber(String subscriberID) throws Exception {

	        Connection con = DBUtil.getDBConnection();
	        PreparedStatement ps =
	            con.prepareStatement(
	                "SELECT NVL(SUM(Computed_Fee_Amount),0) FROM COLLECTION_TXN_TBL WHERE Subscriber_ID=? AND Payment_Flag='NO'");

	        ps.setString(1, subscriberID);
	        ResultSet rs = ps.executeQuery();

	        rs.next();
	        double amount = rs.getDouble(1);

	        con.close();
	        return amount;
	    }
	}


