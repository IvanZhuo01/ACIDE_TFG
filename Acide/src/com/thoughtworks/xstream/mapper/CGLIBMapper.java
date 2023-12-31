package com.thoughtworks.xstream.mapper;

import net.sf.cglib.proxy.Enhancer;

/**
 * Mapper that detects proxies generated by the CGLIB enhancer. The implementation modifies 
 * the name, so that it can identify these types. Note, that this mapper relies on the CGLIB
 * converters:
 * <ul>
 * <li>CGLIBEnhancedConverter</li>
 * </ul>
 *
 * @author J&ouml;rg Schaible
 * @since 1.2
 */

@SuppressWarnings({"rawtypes"})
public class CGLIBMapper extends MapperWrapper {
    
    private static String DEFAULT_NAMING_MARKER = "$$EnhancerByCGLIB$$";
    private final String alias;
    
    public interface Marker {
    }

    public CGLIBMapper(Mapper wrapped) {
        this(wrapped, "CGLIB-enhanced-proxy");
    }

    public CGLIBMapper(Mapper wrapped, String alias) {
        super(wrapped);
        this.alias = alias;
    }

    public String serializedClass(Class type) {
        return type.getName().indexOf(DEFAULT_NAMING_MARKER) > 0 && Enhancer.isEnhanced(type) 
            ? alias 
            : super.serializedClass(type);
    }

    public Class realClass(String elementName) {
        return elementName.equals(alias) ? Marker.class : super.realClass(elementName);
    }
}
