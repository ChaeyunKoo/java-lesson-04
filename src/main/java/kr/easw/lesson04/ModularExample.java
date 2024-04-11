package kr.easw.lesson04;

public class ModularExample {
    public static int MAX_TICK = 5000;
    public static int INITIAL_FUEL = 500;

    public static void main(String[] args) {
        Vehicle vehicle = getVehicle();
        VehicleType type = vehicle.getType();
        int leftFuel = INITIAL_FUEL;
        int leftTick = 0;
        int totalEnergy = 0;
        int tickUsed = 0;
        for (; tickUsed < MAX_TICK; tickUsed++) {
            if (leftTick-- > 0) {
                continue;
            }
            Energy energy = vehicle.getEnergy();
            leftTick = Math.max(0, type.tickModify() + energy.tickModify());
            vehicle.onTick(tickUsed, leftFuel);
            if (leftFuel < energy.fuelUsage() + type.getCost()) {
                break;
            }
            leftFuel -= energy.fuelUsage() + type.getCost();
            totalEnergy += energy.createEnergy(tickUsed);
        }
        int percentage = (int) (((double) tickUsed) / ((double) (MAX_TICK)) * 100.0);
        System.out.println("주행이 종료되었습니다!");
        System.out.println("수행률 : " + percentage + "%");
        System.out.println("총 이동거리: " + totalEnergy);
        System.out.println("남은 연료: " + leftFuel);
        System.out.println("최종 점수: " + calculateScore(tickUsed, totalEnergy, leftFuel));
    }

    private static int calculateScore(int totalTick, int totalEnergy, int leftFuel) {
        double fuelUsage = 2.0 - ((double) leftFuel / (double) INITIAL_FUEL);
        double tickUsage = 1.5 - ((double) totalTick / (double) MAX_TICK);
        return (int) (fuelUsage * tickUsage * totalEnergy);
    }

    public static Vehicle getVehicle() {
        // 수정된 부분: 여기서 원하는 차량 객체를 반환합니다.
        return new Car(); // Car 객체를 반환하도록 수정
    }

    static abstract class Vehicle {
        public abstract Energy getEnergy();

        public abstract VehicleType getType();

        public abstract void onTick(int currentTick, int fuel);
    }

    static interface VehicleType {
        int getCost();

        int tickModify();
    }

    static class Bike implements VehicleType {
        @Override
        public int getCost() {
            return 0;
        }

        @Override
        public int tickModify() {
            return -2;
        }
    }

    static class Car extends Vehicle implements VehicleType {

        @Override
        public int getCost() {
            // 수정된 부분: 차량의 틱당 비용을 조정합니다.
            return 5; // 예를 들어, 5로 수정
        }

        @Override
        public int tickModify() {
            // 수정된 부분: 차량의 틱 수정을 조정합니다.
            return 2; // 예를 들어, 2로 수정
        }

        @Override
        public Energy getEnergy() {
            return new CoalEnergy(); // 예시로 CoalEnergy를 반환하도록 수정
        }

        @Override
        public VehicleType getType() {
            return this;
        }

        @Override
        public void onTick(int currentTick, int fuel) {
            // 여기에 차량의 주행 로직을 구현
        }
    }

    interface Energy {
        int createEnergy(int tick);

        int fuelUsage();

        int tickModify();
    }

    static class HumanEnergy implements Energy {

        @Override
        public int createEnergy(int tick) {
            if (tick % 5 == 0)
                return 30;
            return 0;
        }

        @Override
        public int fuelUsage() {
            return 0;
        }

        @Override
        public int tickModify() {
            return 5;
        }
    }

    static class CoalEnergy implements Energy {

        @Override
        public int createEnergy(int tick) {
            return 45;
        }

        @Override
        public int fuelUsage() {
            return 10;
        }

        @Override
        public int tickModify() {
            return 5;
        }
    }

    static class SunlightEnergy implements Energy {

        @Override
        public int createEnergy(int tick) {
            return 5;
        }

        @Override
        public int fuelUsage() {
            return 0;
        }

        @Override
        public int tickModify() {
            return 10;
        }
    }
}
