/*
 */
package simplystocks.helpers;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Fredrik Grönqvist <fredrik.groqvist+nb@gmail.com>
 */
public class GenericErrorHandlerTest {
    
    GenericErrorHandler instance;
    
    public GenericErrorHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        instance = new GenericErrorHandler();
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddError() {
        instance.addError(ErrorHandler.ERROR_CODES.VALIDATION_FAILED, "TEST");
        String result = instance.getErrorMessage();
        String expected = "TEST";
        assertEquals(expected, result);
    }
    
    @Test
    public void testHasErrorsWithNoErrors(){
        boolean result = instance.hasErrors();
        boolean expected = false;
        assertEquals(expected, result);
    }
    
    @Test
    public void testHasErrorsWithErrors(){
        instance.addError(ErrorHandler.ERROR_CODES.VALIDATION_FAILED, "TEST");
        boolean result = instance.hasErrors();
        boolean expected = true;
        assertEquals(expected, result);
    }
    
    @Test
    public void testGetErrorMessageWithNoError(){
        String result = instance.getErrorMessage();
        String expected = null;
        assertEquals(expected, result);
    }
}
