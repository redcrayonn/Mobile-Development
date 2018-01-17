package nl.inholland.imready.service.mock;

public abstract class MockBaseService {
    protected void requireResult(Object obj) throws Exception {
        if (obj == null)
            throw new Exception("obj was null");
    }

    protected void requireResult(Object obj, String message) throws Exception {
        if (obj == null)
            throw new Exception(message);
    }
}
