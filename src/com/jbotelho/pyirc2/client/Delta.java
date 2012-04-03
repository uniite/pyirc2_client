package com.jbotelho.pyirc2.client;

import java.util.ArrayList;


@PushObject
public class Delta {
    public NumberOrText[] target;
    public String event;
    public String dataType;
}
