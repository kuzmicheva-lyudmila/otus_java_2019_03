package ru.otus.hw;

public class AnnotationsTest {

    AnnotationsTest() {
        System.out.println("Call of the constructor");
    }

    @BeforeAll
    static void beforeAllTest() { System.out.println("BeforeAllTest"); }

    @AfterAll
    static void afterAllTest() {
        System.out.println("AfterAllTest");
    }

    @Before
    void beforeEachTest1() {
        System.out.println("BeforeEachTest1");
    }

    @Before
    void beforeEachTest2() {
        System.out.println("BeforeEachTest2");
    }

    @After
    void afterEachTest() { System.out.println("AfterEachTest"); }

    @Test
    void testOne() {
        System.out.println("testOne");
    }

    @Test
    void testTwo() {
        System.out.println("testTwo");
    }
}
