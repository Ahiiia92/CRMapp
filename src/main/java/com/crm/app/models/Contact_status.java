package com.crm.app.models;

import io.swagger.annotations.ApiModel;

/**
 * The contact status refers to the stage of the sale process
 */
@ApiModel(description = "The contact status refers to the stage of the sale process")
public enum Contact_status {
    SEEN,
    LEAD,
    OPPORTUNITY,
    CLOSE_WIN,
    CLOSE_LOST
}
