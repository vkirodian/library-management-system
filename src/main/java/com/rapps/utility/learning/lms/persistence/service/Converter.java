package com.rapps.utility.learning.lms.persistence.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.util.CollectionUtils;

/**
 * Provides an instance of ModelMapper for Bean to Model and vice versa
 * conversion.
 * 
 * @author vkirodian
 *
 */
public class Converter {

	private static final ModelMapper modelMapper = new ModelMapper();

	private Converter() {
	}

	static {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		modelMapper.getConfiguration().setFieldMatchingEnabled(true);
	}

	/**
	 * Provides an instance of ModelMapper.
	 * 
	 * @return ModelMapper
	 */
	private static ModelMapper getMapper() {
		return modelMapper;
	}

	/**
	 * Maps source to an instance of destinationType. Mapping is performed
	 * according to the corresponding TypeMap.
	 * 
	 * @param source
	 *            object to map from
	 * @param destination
	 *            type to map to
	 * @return fully mapped instance of destinationType
	 */
	public static <D> D convertObject(Object source, Class<D> destination) {
		return getMapper().map(source, destination);
	}

	/**
	 * Maps source list to an instance of destinationType list. Mapping is
	 * performed according to the corresponding TypeMap.
	 * 
	 * @param sourceList
	 *            list to map from
	 * @param destClass
	 *            list to map to
	 * @return fully mapped instance of destinationType list
	 */
	public static <S, D> List<D> convertList(List<S> sourceList, Class<D> destClass) {
		List<D> destItems = new ArrayList<>();
		if (!CollectionUtils.isEmpty(sourceList)) {
			for (S srcItem : sourceList) {
				destItems.add(getMapper().map(srcItem, destClass));
			}
		}
		return destItems;
	}
}
