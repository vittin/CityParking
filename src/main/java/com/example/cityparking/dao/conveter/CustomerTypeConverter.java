package com.example.cityparking.dao.conveter;

import com.example.cityparking.dao.model.CustomerType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class CustomerTypeConverter implements AttributeConverter<CustomerType, String> {

    @Override
    public String convertToDatabaseColumn(CustomerType customerType) {
        return customerType.toShortName(customerType);
    }

    @Override
    public CustomerType convertToEntityAttribute(String dbData) {
        return CustomerType.fromShortName(dbData);
    }

}
