package com.rhayven.prelim.exception;

/**
 * Exception thrown when a resource (e.g., employee) is not found by ID.
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * Creates a new exception with a resource name and ID.
     *
     * @param resource the resource type (e.g., "Employee")
     * @param id       the ID that was not found
     */
    public ResourceNotFoundException(String resource, int id) {
        super(resource + " with ID " + id + " not found.");
    }
}
