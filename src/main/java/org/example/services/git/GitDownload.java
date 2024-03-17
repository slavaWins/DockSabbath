package org.example.services.git;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.example.core.yml.YmlConfig;
import org.example.core.yml.YmlParser;
import org.example.helpers.FileHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class GitDownload {

    public class FileData {
        public String name;
        public String download_url;
    }

    public static boolean downloadGitFromSettings(String nameKey) {

        YmlConfig config = YmlParser.read("./ns/" + nameKey + ".yml");

        if (config == null) {
            System.out.println("not read file");
            return false;
        }

        String token = config.get("git.token"); // Ваш токен авторизации
        String owner = config.get("git.owner"); // Владелец репозитория
        String repo = config.get("git.repo"); // Название репозитория
        String branch = config.get("git.branch"); // Название ветки
        String path = config.get("git.path"); // Путь к файлу в репозитории

        if (owner == null) return false;
        if (repo == null) return false;
        if (branch == null) return false;

        if (path.equalsIgnoreCase("/")) path = "";


        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/zipball/" + branch;
        System.out.println(url);

        File mainTempDir = new File("_temp/");
        mainTempDir.mkdirs();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", "token " + token)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                System.out.println("File downloaded successfully:");

                File zipFile = new File(mainTempDir, nameKey + ".zip");
                FileOutputStream fileOutputStream = new FileOutputStream(zipFile);
                fileOutputStream.write(response.body().bytes());
                fileOutputStream.close();
                System.out.println("Git branch file saved to: " + zipFile.getAbsolutePath());

                File to = new File(mainTempDir, "output");
                if (to.exists()) {
                    FileHelper.deleteFile(to);
                }

                FileHelper.unzip(zipFile, to);


                File firestFile = FileHelper.getFirstFolder(to);

                FileHelper.copyFile(firestFile, new File("ns-files/" + nameKey));


            } else {
                System.out.println("Failed to download file: " + response.code() + " " + response.message());
                return false;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    private static void downloadAndSaveFile(String fileUrl, String fileName) throws IOException {

        System.out.println(fileName);
        String p = "example/" + fileName;

        if (fileName.indexOf(".") == -1) {

            File file = new File(p);
            file.mkdirs();

            return;
        }

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(fileUrl)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                try (FileOutputStream fos = new FileOutputStream(p)) {
                    fos.write(response.body().bytes());
                }
            } else {
                System.out.println("Failed to download file " + fileName + ": " + response.code() + " " + response.message());
            }
        }
    }
}
