package com.peter.util.data.serializable.json.hibernate;


import com.peter.util.data.serializable.json.hibernate.model.Label;

public class LabelsUserType extends JacksonListUserType {

    @Override
    public Class returnedClass() {
        return Label.class;
    }
}
