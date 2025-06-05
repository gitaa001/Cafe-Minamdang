package com.cafeminamdang.View;

import javafx.scene.layout.Pane;

public interface BaseView {
    Pane getRoot();
    void initialize();
    void refresh();
}