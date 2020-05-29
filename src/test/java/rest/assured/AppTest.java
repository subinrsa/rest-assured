package rest.assured;

import org.junit.Test;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class AppTest {
    @Test public void test() {
        assertThat(1, is(1));
        }

    }
