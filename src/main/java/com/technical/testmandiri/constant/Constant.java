package com.technical.testmandiri.constant;

public interface Constant {

    final Integer ERROR_404_CODE = 30000;
    final Integer ERROR_409_CODE = 30001;
    final Integer ERROR_422_CODE = 30002;
    final Integer ERROR_500_CODE = 80000;
    final String ERROR_404_MESSAGE = "Cannot find resource";
    final String ERROR_409_MESSAGE = "Record with unique value {value} already exists in the system";
    final String ERROR_422_MESSAGE = "Invalid value for field {field_name}, rejected value: {value}";
    final String ERROR_500_MESSAGE = "System error, we're unable to process your request at the moment";


}
