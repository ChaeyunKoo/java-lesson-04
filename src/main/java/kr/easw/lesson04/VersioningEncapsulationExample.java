package kr.easw.lesson04;

public class VersioningEncapsulationExample {
    public static String VALUE = "Hello, World!";

    public static String RESULT_SECOND = "Hello, World?";

    public static String RESULT_THIRD = "Hello,World?";

    public static void main(String[] args) {
        TestInterface test = new TestV1();
        if (!test.doAction(VALUE).equals(VALUE)) {
            System.out.println("오답입니다.");
            return;
        }
        test = new TestV2();
        if (!test.doAction(VALUE).equals(RESULT_SECOND)) {
            System.out.println("오답입니다.");
            return;
        }
        test = new TestV3();
        if (!test.doAction(VALUE).equals(RESULT_THIRD)) {
            System.out.println("오답입니다.");
            return;
        }
        System.out.println("정답입니다.");
    }

    interface TestInterface {
        String doAction(String type);
    }

    static class TestV1 implements TestInterface {
        @Override
        public String doAction(String type) {
            return type;
        }
    }

    static class TestV2 implements TestInterface {
        @Override
        public String doAction(String type) {
            return type.replace('!', '?');
        }
    }

    static class TestV3 implements TestInterface {
        @Override
        public String doAction(String type) {
            return type.replace('!', '?').replaceAll(" ", "");
        }
    }
}
