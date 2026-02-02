package com.compost.bean;
import java.sql.Date;
public class CollectionTxn {
        private int txnID;
	    private String subscriberID;
	    private Date collectionDate;
	    private String collectionWindow;
	    private double collectedKg;
	    private double computedFeeAmount;
	    private String paymentFlag;
	    private String collectionStatus;
	    private String collectorStaffID;
	    private String remarks;

	    public int getTxnID() {
	        return txnID;
	    }
	    public void setTxnID(int txnID) {
	        this.txnID = txnID;
	    }

	    public String getSubscriberID() {
	        return subscriberID;
	    }
	    public void setSubscriberID(String subscriberID) {
	        this.subscriberID = subscriberID;
	    }

	    public Date getCollectionDate() {
	        return collectionDate;
	    }
	    public void setCollectionDate(Date collectionDate) {
	        this.collectionDate = collectionDate;
	    }

	    public String getCollectionWindow() {
	        return collectionWindow;
	    }
	    public void setCollectionWindow(String collectionWindow) {
	        this.collectionWindow = collectionWindow;
	    }

	    public double getCollectedKg() {
	        return collectedKg;
	    }
	    public void setCollectedKg(double collectedKg) {
	        this.collectedKg = collectedKg;
	    }

	    public double getComputedFeeAmount() {
	        return computedFeeAmount;
	    }
	    public void setComputedFeeAmount(double computedFeeAmount) {
	        this.computedFeeAmount = computedFeeAmount;
	    }

	    public String getPaymentFlag() {
	        return paymentFlag;
	    }
	    public void setPaymentFlag(String paymentFlag) {
	        this.paymentFlag = paymentFlag;
	    }

	    public String getCollectionStatus() {
	        return collectionStatus;
	    }
	    public void setCollectionStatus(String collectionStatus) {
	        this.collectionStatus = collectionStatus;
	    }

	    public String getCollectorStaffID() {
	        return collectorStaffID;
	    }
	    public void setCollectorStaffID(String collectorStaffID) {
	        this.collectorStaffID = collectorStaffID;
	    }

	    public String getRemarks() {
	        return remarks;
	    }
	    public void setRemarks(String remarks) {
	        this.remarks = remarks;
	    }
	}



