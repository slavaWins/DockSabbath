package org.example.services.hashing;

public class HashingService {

    public static void main() {
        String token = "safasfasf";
        String data = "hellohellohellohellohellohellohellohellohellohellohellohellohhellohellohellohellohellohello";

        String hash = HashingApi.hash(data, token);
        System.out.println(hash);

        System.out.println(HashingApi.verify(hash, data, token));
    }
}
