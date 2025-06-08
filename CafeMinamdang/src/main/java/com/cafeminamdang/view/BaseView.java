package com.cafeminamdang.view;
import javafx.scene.layout.Pane;

public interface BaseView {
    Pane getRoot();
    void refresh();
}