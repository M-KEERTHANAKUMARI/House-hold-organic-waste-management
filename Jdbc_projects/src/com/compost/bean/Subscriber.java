package com.compost.bean;
import java.sql.Date;
public class Subscriber {
	    private String subscriberID;
	    private String houseOrFlatNo;
	    private String streetOrBlockName;
	    private String residentName;
	    private String contactNumber;
	    private String email;
	    private String collectionPlanType;
	    private String preferredCollectionWindow;
	    private double perKgRate;
	    private double maxDailyKg;
	    private Date subscriptionStartDate;
	    private String subscriptionStatus;
	    private double pendingAmount;

	    public String getSubscriberID() {
	        return subscriberID;
	    }
	    public void setSubscriberID(String subscriberID) {
	        this.subscriberID = subscriberID;
	    }

	    public String getHouseOrFlatNo() {
	        return houseOrFlatNo;
	    }
	    public void setHouseOrFlatNo(String houseOrFlatNo) {
	        this.houseOrFlatNo = houseOrFlatNo;
	    }

	    public String getStreetOrBlockName() {
	        return streetOrBlockName;
	    }
	    public void setStreetOrBlockName(String streetOrBlockName) {
	        this.streetOrBlockName = streetOrBlockName;
	    }

	    public String getResidentName() {
	        return residentName;
	    }
	    public void setResidentName(String residentName) {
	        this.residentName = residentName;
	    }

	    public String getContactNumber() {
	        return contactNumber;
	    }
	    public void setContactNumber(String contactNumber) {
	        this.contactNumber = contactNumber;
	    }

	    public String getEmail() {
	        return email;
	    }
	    public void setEmail(String email) {
	        this.email = email;
	    }

	    public String getCollectionPlanType() {
	        return collectionPlanType;
	    }
	    public void setCollectionPlanType(String collectionPlanType) {
	        this.collectionPlanType = collectionPlanType;
	    }

	    public String getPreferredCollectionWindow() {
	        return preferredCollectionWindow;
	    }
	    public void setPreferredCollectionWindow(String preferredCollectionWindow) {
	        this.preferredCollectionWindow = preferredCollectionWindow;
	    }

	    public double getPerKgRate() {
	        return perKgRate;
	    }
	    public void setPerKgRate(double perKgRate) {
	        this.perKgRate = perKgRate;
	    }

	    public double getMaxDailyKg() {
	        return maxDailyKg;
	    }
	    public void setMaxDailyKg(double maxDailyKg) {
	        this.maxDailyKg = maxDailyKg;
	    }

	    public Date getSubscriptionStartDate() {
	        return subscriptionStartDate;
	    }
	    public void setSubscriptionStartDate(Date subscriptionStartDate) {
	        this.subscriptionStartDate = subscriptionStartDate;
	    }

	    public String getSubscriptionStatus() {
	        return subscriptionStatus;
	    }
	    public void setSubscriptionStatus(String subscriptionStatus) {
	        this.subscriptionStatus = subscriptionStatus;
	    }

	    public double getPendingAmount() {
	        return pendingAmount;
	    }
	    public void setPendingAmount(double pendingAmount) {
	        this.pendingAmount = pendingAmount;
	    }
	}


