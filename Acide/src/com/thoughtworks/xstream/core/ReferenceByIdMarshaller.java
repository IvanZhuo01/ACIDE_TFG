package com.thoughtworks.xstream.core;

import com.thoughtworks.xstream.alias.ClassMapper;
import com.thoughtworks.xstream.converters.ConverterLookup;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.path.Path;
import com.thoughtworks.xstream.mapper.Mapper;

@SuppressWarnings({"deprecation"})
public class ReferenceByIdMarshaller extends AbstractReferenceMarshaller {

    private final IDGenerator idGenerator;

    public static interface IDGenerator {
        String next();
    }

    public ReferenceByIdMarshaller(HierarchicalStreamWriter writer,
                                   ConverterLookup converterLookup,
                                   Mapper mapper,
                                   IDGenerator idGenerator) {
        super(writer, converterLookup, mapper);
        this.idGenerator = idGenerator;
    }

    public ReferenceByIdMarshaller(HierarchicalStreamWriter writer,
                                   ConverterLookup converterLookup,
                                   Mapper mapper) {
        this(writer, converterLookup, mapper, new SequenceGenerator(1));
    }

    /**
     * @deprecated As of 1.2, use {@link #ReferenceByIdMarshaller(HierarchicalStreamWriter, ConverterLookup, Mapper, IDGenerator)}
     */
    public ReferenceByIdMarshaller(HierarchicalStreamWriter writer,
                                   ConverterLookup converterLookup,
                                   ClassMapper classMapper,
                                   IDGenerator idGenerator) {
        this(writer, converterLookup, (Mapper)classMapper, idGenerator);
    }

    /**
     * @deprecated As of 1.2, use {@link #ReferenceByIdMarshaller(HierarchicalStreamWriter, ConverterLookup, Mapper)}
     */
    public ReferenceByIdMarshaller(HierarchicalStreamWriter writer,
                                   ConverterLookup converterLookup,
                                   ClassMapper classMapper) {
        this(writer, converterLookup, (Mapper)classMapper);
    }

    protected String createReference(Path currentPath, Object existingReferenceKey) {
        return existingReferenceKey.toString();
    }

    protected Object createReferenceKey(Path currentPath) {
        return idGenerator.next();
    }

    protected void fireValidReference(Object referenceKey) {
        writer.addAttribute(getMapper().aliasForAttribute("id"), referenceKey.toString());
    }
}
