import java.util.Random;

public class Main {
    public static int bossHealth = 2000;
    public static int bossDamage = 50;
    public static boolean isBossSleep = false;
    public static String bossDefenceType = "";
    public static int[] heroesHealth = {260, 280, 250, 300, 500, 320, 350, 400};
    public static int[] heroesDamage = {20, 25, 15, 0, 5, 10, 30, 35};
    public static String[] heroesAttackType = {"Physical",
            "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", " Thor"};


    public static void main(String[] args) {
        System.out.println("Game started:");
        printStatistics();
        while (!isGameFinished()) {
            round();
        }
    }

    public static void round() {
        if (bossHealth > 0) {
            changeBossDefence();
            bossHits();
        }
        heroesHit();
        healAHeroes();
        golem();
        lucky();
        berserk();
        thor();
        printStatistics();
    }

    public static void thor() {
        if (heroesHealth[7] > 0) {
            Random random = new Random();
            boolean sleep = random.nextBoolean();
            isBossSleep = sleep;
        }
    }

    public static void berserk() {
        if (heroesHealth[6] > 0) {
            heroesHealth[6] += bossDamage / 2;
            bossHealth -= heroesDamage[6] + (bossDamage / 2);
        }
    }


    public static void lucky() {
        Random ran = new Random();
        boolean lacky = ran.nextBoolean();
        if (lacky) heroesHealth[5] += bossDamage;
        System.out.println(heroesAttackType[5] + " " + lacky);

    }

    private static void golem() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (i == 4) continue;
                heroesHealth[i] += bossDamage / 5;
                heroesHealth[4] -= bossDamage / 4;
                System.out.println(heroesAttackType[i] + " golem " + heroesHealth[i]);
            }
        }
    }

    public static void healAHeroes() {
        for (int i = 0; i < heroesHealth.length; i++) {
            Random ran = new Random();
            int heal = ran.nextInt(50);
            if (heroesHealth[i] < 100 && heroesHealth[i] > 0) {
                heroesHealth[i] += heal;
                System.out.println(heroesAttackType[i] + " heal " + heroesHealth[i]);
            }
        }
    }

    public static void changeBossDefence() {
        Random r = new Random();
        int randomIndex = r.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefenceType = heroesAttackType[randomIndex];
        System.out.println("Boss choose " + bossDefenceType);
    }

    public static void bossHits() {
        if (isBossSleep) {
            System.out.println("boss sleep");
            isBossSleep = false;
        } else {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] > 0) {
                    if (heroesHealth[i] - bossDamage < 0) {
                        heroesHealth[i] = 0;
                    } else {
                        heroesHealth[i] = heroesHealth[i] - bossDamage;
                    }
                }
            }
        }
    }

    public static void heroesHit() {

        for (int i = 0; i < heroesDamage.length; i++) {

            if (bossHealth > 0 && heroesHealth[i] > 0) {

                if (bossDefenceType == heroesAttackType[i]) {
                    if (bossHealth - heroesDamage[i] < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i];
                    }
                } else {
                    Random r = new Random();
                    int coeff = r.nextInt(5) + 2; //2,3,4,5,6
                    if (bossHealth - heroesDamage[i] * coeff < 0) {
                        bossHealth = 0;
                    } else {
                        bossHealth = bossHealth - heroesDamage[i] * coeff;
                    }
                    System.out.println(heroesAttackType[i]
                            + " hits critically "
                            + heroesDamage[i] * coeff);
                }
            }
        }
    }

    public static void printStatistics() {
        System.out.println("_______________");
        System.out.println("Boss health: " + bossHealth);

        for (int i = 0; i < heroesAttackType.length; i++) {
            System.out.println(heroesAttackType[i]
                    + " health: " + heroesHealth[i]);
        }
        System.out.println("_______________");
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDied = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDied = false;
                break;
            }
        }

        if (allHeroesDied) {
            System.out.println("Boss won!!!");
        }

        return allHeroesDied;
    }
}
