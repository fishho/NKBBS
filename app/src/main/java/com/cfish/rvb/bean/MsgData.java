package com.cfish.rvb.bean;

import java.util.List;

/**
 * Created by GKX100217 on 2016/3/19.
 */
public class MsgData {
    //private Friend friend;
    private String count;
    private List<Letter>  letters;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<Letter> getLetters() {
        return letters;
    }

    public void setLetters(List<Letter> letters) {
        this.letters = letters;
    }
}

