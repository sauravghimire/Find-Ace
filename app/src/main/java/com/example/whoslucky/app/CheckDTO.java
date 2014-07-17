package com.example.whoslucky.app;

/**
 * Created by saurav on 7/17/14.
 */
public class CheckDTO {
    private boolean isAce;
    private boolean isTurnOver;
    private int cardId;

    public CheckDTO(boolean isAce, boolean isTurnOver, int cardId) {
        this.isAce = isAce;
        this.isTurnOver = isTurnOver;
        this.cardId = cardId;
    }

    public boolean isAce() {
        return isAce;
    }

    public void setAce(boolean isAce) {
        this.isAce = isAce;
    }

    public boolean isTurnOver() {
        return isTurnOver;
    }

    public void setTurnOver(boolean isTurnOver) {
        this.isTurnOver = isTurnOver;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }
}
