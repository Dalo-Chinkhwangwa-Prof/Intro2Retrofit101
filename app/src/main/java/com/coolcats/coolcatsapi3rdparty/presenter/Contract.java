package com.coolcats.coolcatsapi3rdparty.presenter;

import com.coolcats.coolcatsapi3rdparty.model.JikanItem;

import java.util.List;

public interface Contract {

    interface Presenter {
        void getResults(String query);
    }

    interface View {
        void displayResults(List<JikanItem> items);
        void setStatus(JikanPresenter.Status status);
    }

}
