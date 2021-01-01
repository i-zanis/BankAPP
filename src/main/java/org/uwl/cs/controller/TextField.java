package org.uwl.cs.controller;

import static org.uwl.cs.Constant.EMPTY_STRING;

public class TextField extends javafx.scene.control.TextField {

    public boolean isEmpty() {
        return this.getText().equals(EMPTY_STRING);
    }
}

