package com.peter.util.data.serializable.json;

import org.hibernate.type.descriptor.WrapperOptions;
import org.hibernate.type.descriptor.java.AbstractTypeDescriptor;
import org.hibernate.type.descriptor.java.MutableMutabilityPlan;
import org.hibernate.usertype.DynamicParameterizedType;

import java.util.Properties;

/**
 * Created by Peter on 1/7/2017.
 */
public class JsonTypeDescriptor
        extends AbstractTypeDescriptor<Object>
        implements DynamicParameterizedType {

    private Class<?> jsonObjectClass;

    @Override
    public void setParameterValues(Properties parameters) {
        jsonObjectClass = ( (ParameterType) parameters.get( PARAMETER_TYPE ) )
                .getReturnedClass();

    }

    public JsonTypeDescriptor() {
        super( Object.class, new MutableMutabilityPlan<Object>() {
            @Override
            protected Object deepCopyNotNull(Object value) {
                return JSONMapper.clone(value);
            }
        });
    }

    @Override
    public boolean areEqual(Object one, Object another) {
        if ( one == another ) {
            return true;
        }
        if ( one == null || another == null ) {
            return false;
        }
        return JSONMapper.toJsonNode(JSONMapper.toString(one)).equals(
                JSONMapper.toJsonNode(JSONMapper.toString(another)));
    }

    @Override
    public String toString(Object value) {
        return JSONMapper.toString(value);
    }

    @Override
    public Object fromString(String string) {
        return JSONMapper.fromString(string, jsonObjectClass);
    }

    @SuppressWarnings({ "unchecked" })
    @Override
    public <X> X unwrap(Object value, Class<X> type, WrapperOptions options) {
        if ( value == null ) {
            return null;
        }
        if ( String.class.isAssignableFrom( type ) ) {
            return (X) toString(value);
        }
        if ( Object.class.isAssignableFrom( type ) ) {
            return (X) JSONMapper.toJsonNode(toString(value));
        }
        throw unknownUnwrap( type );
    }

    @Override
    public <X> Object wrap(X value, WrapperOptions options) {
        if ( value == null ) {
            return null;
        }
        return fromString(value.toString());
    }

}
