package com.acp.location.constants;

public interface LocationConstants {

    String FINDALL_FAILURE_MSG = "Failed to retrieve the location records from DB, Please contact DealDesk for support";
    String SAVE_FLUSH_FAILURE_MSG = "Failed to save the location records into DB, Please contact DealDesk for support";
    String NO_ACTIVE_LOCATION_MSG = "No Active locations found!!";
    String PRECONDITION_FAILURE_MSG = "The location doesn't exist Please load valid info";
    String LOCATION_EXISTS_MSG = "The Location you are trying to insert already exists, please provide a different name";
    String LOCATION_ID_EMPTY_MSG = "The location ID is a mandatory field";
    String LOCATION_NAME_BLANK_MSG = "The location name is a mandatory field";
    String LOCATION_NAME_SIZE_MSG = "The location name must be less than 100 characters";
    String LOCATION_IS_ACTIVE = "Please provide active field with true or false value";
    String LOGGEDUSER_INFO_BLANK_MSG = "LoggedInUser is a mandatory field";
    String LOGGEDUSER_SIZE_MSG = "The LoggedInUser must be less than 100 characters";

}
