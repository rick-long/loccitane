package org.spa.vo.sales;

import java.io.Serializable;

public class ReviewStatisticalVO implements Serializable {
   private long reviewCount;//review数
    private long reviewRatingTreatmentCount;//评论数
    private long badReviewCount;//差评数
    private long highPraiseSum;//好评
    private long  orderCount;//总订单数
    private double notReview;//未提交百分比
    private double customerServicesAvg;//shopcustomerService平均分
    private double shopCleanlinessAvg;//shopCleanliness平均分
    private long reviewStar5;//5星百分比评论数
    private long reviewStar4;
    private long reviewStar3;
    private long reviewStar2;
    private long reviewStar1;
    private double highPraisePercentage;
    private long badReviewPercentage;
    private  double satisfactionLevelStarAvg ;
    private double valueForMoneyStarAvg;
    private double staffStarAvg;
    private  double nps;

    public double getNps() {
        return nps;
    }

    public void setNps(double nps) {
        this.nps = nps;
    }

    public double getSatisfactionLevelStarAvg() {
        return satisfactionLevelStarAvg;
    }

    public void setSatisfactionLevelStarAvg(double satisfactionLevelStarAvg) {
        this.satisfactionLevelStarAvg = satisfactionLevelStarAvg;
    }

    public double getValueForMoneyStarAvg() {
        return valueForMoneyStarAvg;
    }

    public void setValueForMoneyStarAvg(double valueForMoneyStarAvg) {
        this.valueForMoneyStarAvg = valueForMoneyStarAvg;
    }

    public double getStaffStarAvg() {
        return staffStarAvg;
    }

    public void setStaffStarAvg(double staffStarAvg) {
        this.staffStarAvg = staffStarAvg;
    }

    public long getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(long reviewCount) {
        this.reviewCount = reviewCount;
    }

    public long getReviewRatingTreatmentCount() {
        return reviewRatingTreatmentCount;
    }

    public void setReviewRatingTreatmentCount(long reviewRatingTreatmentCount) {
        this.reviewRatingTreatmentCount = reviewRatingTreatmentCount;
    }

    public long getBadReviewCount() {
        return badReviewCount;
    }

    public void setBadReviewCount(long badReviewCount) {
        this.badReviewCount = badReviewCount;
    }

    public long getHighPraiseSum() {
        return highPraiseSum;
    }

    public void setHighPraiseSum(long highPraiseSum) {
        this.highPraiseSum = highPraiseSum;
    }

    public long getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(long orderCount) {
        this.orderCount = orderCount;
    }

    public double getNotReview() {
        return notReview;
    }

    public void setNotReview(double notReview) {
        this.notReview = notReview;
    }

    public double getCustomerServicesAvg() {
        return customerServicesAvg;
    }

    public void setCustomerServicesAvg(double customerServicesAvg) {
        this.customerServicesAvg = customerServicesAvg;
    }

    public double getShopCleanlinessAvg() {
        return shopCleanlinessAvg;
    }

    public void setShopCleanlinessAvg(double shopCleanlinessAvg) {
        this.shopCleanlinessAvg = shopCleanlinessAvg;
    }

    public long getReviewStar5() {
        return reviewStar5;
    }

    public void setReviewStar5(long reviewStar5) {
        this.reviewStar5 = reviewStar5;
    }

    public long getReviewStar4() {
        return reviewStar4;
    }

    public void setReviewStar4(long reviewStar4) {
        this.reviewStar4 = reviewStar4;
    }

    public long getReviewStar3() {
        return reviewStar3;
    }

    public void setReviewStar3(long reviewStar3) {
        this.reviewStar3 = reviewStar3;
    }

    public long getReviewStar2() {
        return reviewStar2;
    }

    public void setReviewStar2(long reviewStar2) {
        this.reviewStar2 = reviewStar2;
    }

    public long getReviewStar1() {
        return reviewStar1;
    }

    public void setReviewStar1(long reviewStar1) {
        this.reviewStar1 = reviewStar1;
    }

    public double getHighPraisePercentage() {
        return highPraisePercentage;
    }

    public void setHighPraisePercentage(double highPraisePercentage) {
        this.highPraisePercentage = highPraisePercentage;
    }

    public long getBadReviewPercentage() {
        return badReviewPercentage;
    }

    public void setBadReviewPercentage(long badReviewPercentage) {
        this.badReviewPercentage = badReviewPercentage;
    }
}
