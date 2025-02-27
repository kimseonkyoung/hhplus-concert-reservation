package kr.hhplus.be.server.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class UserBalanceCSVGenerator {
    private static final int TOTAL_USERS = 1_000_000; // 유저 수 100만명
    private static final String OUTPUT_FILE = "./db-data/user_balance.csv";
    private static final int MIN_BALANCE = 1_000;  // 최소 충전 금액
    private static final int MAX_BALANCE = 100_000; // 최대 충전 금액

    public static void main(String[] args) {
        ensureDbDataFolder();
        generateUserBalanceCSV();
    }

    private static void generateUserBalanceCSV() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE))) {
            writer.write("user_id,balance_value\n"); // CSV 헤더 추가
            Random rand = new Random();

            for (int i = 1; i <= TOTAL_USERS; i++) {
                int balance = rand.nextInt(MAX_BALANCE - MIN_BALANCE + 1) + MIN_BALANCE;
                writer.write(String.format("%d,%d\n", i, balance));

                if (i % 100_000 == 0) {
                    System.out.println("✅ " + i + "명 생성 완료...");
                }
            }
            System.out.println("✅ user_balance.csv 파일 생성 완료: " + OUTPUT_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void ensureDbDataFolder() {
        File folder = new File("./db-data");
        if (!folder.exists()) {
            boolean created = folder.mkdirs();
            if (created) {
                System.out.println("📂 db-data 폴더 생성 완료!");
            }
        }
    }
}
