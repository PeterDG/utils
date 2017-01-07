package com.peter.util.data.serializable.json;

import org.hibernate.type.AbstractSingleColumnStandardBasicType;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

/**
 * Created by Peter on 1/7/2017.
 */
public class JsonBinaryType
        extends AbstractSingleColumnStandardBasicType<Object>
        implements DynamicParameterizedType {

    public JsonBinaryType() {
        super(
                JsonBinarySqlTypeDescriptor.INSTANCE,
                new JsonTypeDescriptor()
        );
    }

    public String getName() {
        return "jsonb";
    }

    @Override
    public void setParameterValues(Properties parameters) {
        ((JsonTypeDescriptor) getJavaTypeDescriptor())
                .setParameterValues(parameters);
    }

}
