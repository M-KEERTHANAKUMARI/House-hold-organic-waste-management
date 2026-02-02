package com.compost.service;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

import com.compost.bean.CollectionTxn;
import com.compost.bean.Subscriber;
import com.compost.dao.CollectionTxnDAO;
import com.compost.dao.SubscriberDAO;
import com.compost.util.CollectionWeightExceededException;
import com.compost.util.DBUtil;
import com.compost.util.PendingAmountExistsException;
import com.compost.util.ValidationException;

public class CompostService {

    private SubscriberDAO subscriberDAO = new SubscriberDAO();
    private CollectionTxnDAO txnDAO = new CollectionTxnDAO();
    public Subscriber viewSubscriberDetails(String subscriberID) throws Exception {

        if (subscriberID == null || subscriberID.trim().isEmpty()) {
            throw new ValidationException("Subscriber ID cannot be empty");
        }
        return subscriberDAO.findSubscriber(subscriberID);
    }
    public List<Subscriber> viewAllActiveSubscribers() throws Exception {
        return subscriberDAO.viewAllActiveSubscribers();
    }
    public boolean registerNewSubscriber(Subscriber subscriber) throws Exception {

        if (subscriber.getSubscriberID() == null || subscriber.getSubscriberID().isEmpty()
                || subscriber.getResidentName() == null || subscriber.getResidentName().isEmpty()
                || subscriber.getContactNumber() == null || subscriber.getContactNumber().isEmpty()
                || subscriber.getCollectionPlanType() == null || subscriber.getCollectionPlanType().isEmpty()) {
            throw new ValidationException("Mandatory subscriber details missing");
        }

        if (subscriber.getPerKgRate() <= 0 || subscriber.getMaxDailyKg() <= 0) {
            throw new ValidationException("Rate and Max Daily Kg must be positive");
        }

        if (subscriber.getSubscriptionStartDate() == null) {
            throw new ValidationException("Subscription start date is required");
        }
        if (subscriberDAO.findSubscriber(subscriber.getSubscriberID()) != null) {
            throw new ValidationException("Subscriber ID already exists");
        }
        subscriber.setSubscriptionStatus("ACTIVE");
        subscriber.setPendingAmount(0.0);

        return subscriberDAO.insertSubscriber(subscriber);
    }
    public List<CollectionTxn> listCollectionsBySubscriber(String subscriberID) throws Exception {
        return txnDAO.findCollectionsBySubscriber(subscriberID);
    }
    public List<CollectionTxn> listCollectionsByDateRange(Date fromDate, Date toDate) throws Exception {
        return txnDAO.findCollectionsByDateRange(fromDate, toDate);
    }
    public boolean recordCollection(String subscriberID, Date collectionDate,
            String collectionWindow, double collectedKg,
            String collectionStatus, String collectorStaffID,
            String remarks) throws Exception {

        if (subscriberID == null || subscriberID.isEmpty()
                || collectionWindow == null || collectionWindow.isEmpty()
                || collectionStatus == null || collectionStatus.isEmpty()
                || collectorStaffID == null || collectorStaffID.isEmpty()
                || collectionDate == null) {
            throw new ValidationException("Invalid collection input");
        }

        if (collectedKg < 0) {
            throw new ValidationException("Collected Kg cannot be negative");
        }

        Subscriber sub = subscriberDAO.findSubscriber(subscriberID);
        if (sub == null || !"ACTIVE".equals(sub.getSubscriptionStatus())) {
            return false;
        }
        if ("SUCCESS".equals(collectionStatus)) {
            if (collectedKg > sub.getMaxDailyKg() + 1.0) {
                throw new CollectionWeightExceededException("Collected Kg exceeds daily limit");
            }
        }

        double fee = "SUCCESS".equals(collectionStatus)
                ? collectedKg * sub.getPerKgRate()
                : 0.0;

        Connection con = DBUtil.getDBConnection();
        con.setAutoCommit(false);

        try {
            int txnID = txnDAO.generateTxnID();

            CollectionTxn txn = new CollectionTxn();
            txn.setTxnID(txnID);
            txn.setSubscriberID(subscriberID);
            txn.setCollectionDate(collectionDate);
            txn.setCollectionWindow(collectionWindow);
            txn.setCollectedKg(collectedKg);
            txn.setComputedFeeAmount(fee);
            txn.setPaymentFlag("NO");
            txn.setCollectionStatus(collectionStatus);
            txn.setCollectorStaffID(collectorStaffID);
            txn.setRemarks(remarks);

            txnDAO.insertCollectionTxn(txn);

            if (fee > 0) {
                double newPending = sub.getPendingAmount() + fee;
                subscriberDAO.updatePendingAmount(subscriberID, newPending);
            }

            con.commit();
            return true;

        } catch (Exception e) {
            con.rollback();
            return false;
        } finally {
            con.close();
        }
    }
    public boolean correctCollectionEntry(int txnID, double newCollectedKg,
            String newCollectionStatus, String newRemarks) throws Exception {

        if (txnID <= 0 || newCollectedKg < 0) {
            throw new ValidationException("Invalid correction input");
        }

        CollectionTxn oldTxn = txnDAO.findCollectionTxn(txnID);
        if (oldTxn == null) {
            return false;
        }

        Subscriber sub = subscriberDAO.findSubscriber(oldTxn.getSubscriberID());
        if (sub == null) {
            return false;
        }

        double oldFee = oldTxn.getComputedFeeAmount();
        double newFee = "SUCCESS".equals(newCollectionStatus)
                ? newCollectedKg * sub.getPerKgRate()
                : 0.0;

        Connection con = DBUtil.getDBConnection();
        con.setAutoCommit(false);

        try {
            oldTxn.setCollectedKg(newCollectedKg);
            oldTxn.setCollectionStatus(newCollectionStatus);
            oldTxn.setComputedFeeAmount(newFee);
            oldTxn.setRemarks(newRemarks);

            txnDAO.updateCollectionTxn(oldTxn);

            double adjustedPending = sub.getPendingAmount() - oldFee + newFee;
            subscriberDAO.updatePendingAmount(sub.getSubscriberID(), adjustedPending);

            con.commit();
            return true;

        } catch (Exception e) {
            con.rollback();
            return false;
        } finally {
            con.close();
        }
    }
    public boolean deactivateSubscriber(String subscriberID) throws Exception {

        double unpaid = txnDAO.calculateUnpaidAmountForSubscriber(subscriberID);
        if (unpaid > 0) {
            throw new PendingAmountExistsException("Pending amount exists. Cannot deactivate.");
        }
        return subscriberDAO.updateSubscriptionStatus(subscriberID, "INACTIVE");
    }
}

