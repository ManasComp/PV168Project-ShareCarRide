package cz.muni.fi.pv168.project.business.service.export.batch;

/**
 * @author Sabrina Orálková, 525089
 */
public class BatchOperationException extends RuntimeException {
    public BatchOperationException(String message) {
        super(message);
    }

    public BatchOperationException(String message, Throwable cause) {
        super(message, cause);
    }
}