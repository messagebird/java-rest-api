package com.messagebird.objects.integrations;

import java.util.List;

public class HSMQualityScore {
    private String score;
    private long date;
    private List<String> reasons;

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public List<String> getReasons() {
        return reasons;
    }

    public void setReasons(List<String> reasons) {
        this.reasons = reasons;
    }

    @Override
    public String toString() {
        return "HSMQualityScore{" +
                "score='" + score + '\'' +
                ", date=" + date +
                ", reasons=" + reasons +
                '}';
    }
}
