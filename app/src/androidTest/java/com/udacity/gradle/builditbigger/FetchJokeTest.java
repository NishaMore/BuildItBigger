package com.udacity.gradle.builditbigger;

import android.test.AndroidTestCase;
import android.test.mock.MockContext;

import org.mockito.Mock;

import java.util.concurrent.TimeUnit;

/**
 * Created by shani on 7/10/16.
 */
public class FetchJokeTest extends AndroidTestCase{
    @Mock  MockContext mockContext;
    FetchJoke fetchJoke;
    String joke;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        joke = null;
        fetchJoke = new FetchJoke(mockContext);
    
    }
    public void testFetchJoke(){
        try {
            if(fetchJoke!=null){
                joke = fetchJoke.execute().get(30, TimeUnit.SECONDS);
            }
            assertNotNull(joke);
        } catch (Exception e) {
            fail("Timeout");
        }

    }
}
